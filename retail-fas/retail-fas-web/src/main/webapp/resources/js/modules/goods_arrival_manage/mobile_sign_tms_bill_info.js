/**
 * 手机签收管理
 */
var mobile_sign_tms_bill_sp_info={};
$(function() {
   var data=mobile_sign_tms_bill_sp_info.searchTmsBillTransport(1);
   var autocompleteDatas=[]
  $("#billOriginalRefBillNo").keyup(function(){
	  var billOriginalRefBillNo=jQuery.trim($("#billOriginalRefBillNo").val());
	  billOriginalRefBillNo=billOriginalRefBillNo.toLocaleUpperCase();
 	  var i=0;
 	  autocompleteDatas.length = 0;
	  $.each(data,function(n,value) {
  		  if(value.indexOf(billOriginalRefBillNo)!=-1){
 			  i++;
 			  if(i<9){
 				 autocompleteDatas.push(value);
 			  }
 		  }
        });  
	  autocompleteDatas.join(",");
     });
   
 $( "#billOriginalRefBillNo" ).autocomplete({
	 source: autocompleteDatas,
  });
});


mobile_sign_tms_bill_sp_info.common=function(page){
	if(page=='mobile'){
		$('#rejectSignBut').attr('disabled', 'disabled');
		$('#signBut').attr('disabled', 'disabled');
	}else if(page=='pc'){
		pc_sign.closeWindow();
		pc_sign.doSearch();
	}
} 
mobile_sign_tms_bill_sp_info.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : false,
		async : false,
 		success : callback
	});
};   
 

mobile_sign_tms_bill_sp_info.checkPassword = function(checkUrl,checkDataNo) {
 	var checkFlag=true;
 	var status = "";
	$.ajax({
		type : 'get',
		url : checkUrl,
		data : checkDataNo,
		cache : true,
		async : false, // 一定要,同步执行完成
		success : function(result) {
			 if (result == 'success') {
	 			   checkFlag=false;
		 		 }
		}
	});
	return checkFlag;
};

/**
 * 手机补单搜索出要补单的数据
 */
mobile_sign_tms_bill_sp_info.appendDivshow = function() {
	$("#appendPassword").val("");
    $("#appendTBoxCount").val("");
    $("#appendTotalCount").val("");
    $("#appendremark").val("");
    $("#billOriginalRefBillNo").val("");
    var truckNo=$("#truckNo").val();
 	var data = {
			"truckNo" : truckNo,
			"_method":'get'
		 	};
 	var url=BasePath+'/receipt/get_tms_bill_transportpoint_by_id/list.json';
    var checkFlag=false;
    //var status=receipt.checkExistStatus(url,data);
    if(mobile_sign_tms_bill_sp_info.checkExistStatus(url,data)=='40'||
    		mobile_sign_tms_bill_sp_info.checkExistStatus(url,data)=='60' || 
    		mobile_sign_tms_bill_sp_info.checkExistStatus(url,data)=='65'){
    	checkFlag=true;
    }
   	if(!checkFlag){
  		//alert('你选择了无效的派车单,请重新选择,派车单状态必须为:【配送中】,【等待回单】,【回单处理中】');
 		$("#signResult").html("你选择了无效的派车单,请重新选择,派车单状态必须为:【配送中】,【等待回单】,【回单处理中】!");
		return;
	}
 	$("#mobile_append_tms_bill_stansport").show();
 	$("#goBackBut2").show();
 	//document.getElementById("mobile_append_tms_bill_stansport").style.display="";
	$("#mobile_tms_bill_stansport").hide();
	$("#searchResultDiv").hide();
	document.title='配送点司机手机补单';
	$("#appendResult").html("");
}

mobile_sign_tms_bill_sp_info.goBack = function() {
	document.title='配送点手机签收';
	$("#signResult").html("");
 	$("#mobile_append_tms_bill_stansport").hide();
	$("#mobile_tms_bill_stansport").show();
 }

mobile_sign_tms_bill_sp_info.searchTmsBillTransport = function(actionType) {
 	var billOriginalRefBillNo=jQuery.trim($("#billOriginalRefBillNo").val());
 	if(billOriginalRefBillNo==""&&actionType==0){
  		$("#appendResult").html("请输入要搜索的单号");
  		return;
 	}
 	var autocompleteDatas = [];
 	var url=BasePath+'/moblie_sign_tms_bill_transport/tms_bill_transport/list';
 	 var data = {
				"billOriginalRefBillNo" : billOriginalRefBillNo,
				"currentBillstatusSearch" : 10,
				"openFlag":0,
				"_method" : 'get',
				"actionType":actionType
	 			};
	 var object="";
 	 mobile_sign_tms_bill_sp_info.ajaxRequest(url,data,function(result){
 			$.each(result, function(index, item) {
  				autocompleteDatas.push(item.billNo);
  				object=result[0];
			});
			autocompleteDatas.join(",");	
 	     /* for(var  i=0;i<result.length;i++){
	  		 object=result[0];
	 	   }*/
 	 });
 	  if (typeof(object) != "undefined" &&object!=null&&object!=''&&actionType==0){
	  		$("#searchResultDiv").show();
	  		//document.getElementById("searchResultDiv").style.display="";
	  		$("#billNoNew").val(object.billNo);
	  		$("#consigneeNoNameLable").html(object.consigneeNoName);
	  		$("#shiperNoNameLable").html(object.shiperNoName);
	  	 	$("#billNoLable").html(object.billNo);
	  		$("#billNoOriginalLable").html(object.billNoOriginal);
	  		$("#refBillNoLable").html(object.refBillNo);
	  		$("#totalBoxcountLable").html(object.totalBoxcount);
	  		$("#totalCountLable").html(object.totalCount);
	  		$("#appendTBoxCount").val(object.totalBoxcount);
	  		$("#appendTotalCount").val(object.totalCount);
	  		$("#goBackBut2").hide();
	  		$("#appendResult").html("");
 	    }else{
	    	$("#appendResult").html("没有搜索到记录");
	    }
	 return autocompleteDatas;
	
}

/**
 * 手机补单
 * (1)判读司机手机密码是否成功
 * (2)补单到派车单  
 */
mobile_sign_tms_bill_sp_info.append = function(actionType,page) {
  	var appendPassword= jQuery.trim($("#appendPassword").val());
	var quartzcenterNo= jQuery.trim($("#quartzcenterNo").val());
 	var driverNo= jQuery.trim($("#driverNo").val());
 	
    var appendTBoxCount= jQuery.trim($("#appendTBoxCount").val());
    var appendTotalCount= jQuery.trim($("#appendTotalCount").val());
    var billNo=$("#billNoNew").val();
    var truckNo=$("#truckNo").val();
    var appendRemark=$("#appendremark").val();
  	 
   	if(appendTBoxCount==""){
 	 		$("#appendResult").html("请输入签收件数");
	 		return ;
	 	}
	   if(!/^(\+|-)?(\d+)(\.\d*)?$/g.test(appendTBoxCount)){
			$("#appendResult").html("签收件数只能为数字");	 
 			return ;
	  }
	   if(appendTotalCount!=""&&!/^(\+|-)?(\d+)(\.\d*)?$/g.test(appendTotalCount)){
		   $("#appendResult").html("签收数量只能为数字");	 
			//alert("签收数量只能为数字");	 
			return ;
	   } 
   	 if(appendPassword==""){
 	 	 $("#appendResult").html("请输入签收密码");
	 	 $("#appendPassword").focus();
	 		return;
	 	}
    	//判读密码是否正确
   		var checkUrl = BasePath + '/moblie_sign_tms_bill_transport/get_driver_password/list';
   	 	var checkDataNo = {
    		"appendPassword" : appendPassword,
   			"quartzcenterNo" : quartzcenterNo,
   			"driverNo" : driverNo,
   			"_method" : "get"
   			};
    	  var checkFlag=mobile_sign_tms_bill_sp_info.checkPassword(checkUrl,checkDataNo);
    	  if(checkFlag){
   		  $("#appendResult").html('签收密码错误!');
  		  return;
  	  } 	

  	  url =BasePath+'/receipt/put_append_tms_transpoint/list';
   	  var data = {
			"billNo" : billNo,
			"truckNo" : truckNo,
 			"totalBoxCount" : appendTBoxCount,
			"totalCount" : appendTotalCount,
			"signRemarks" : appendRemark,
			"appendType": "1",	
			"_method":'get'
			};
   	    $('#appendButNew').attr('disabled', 'disabled');
  	    //签收
  		mobile_sign_tms_bill_sp_info.ajaxRequest(url, data, function(result,returnMsg) {
  			if(result == 'success') {
 					$("#appendResult").html("补单成功!");
					//alert('补单成功!');
					//$('#appendButNew').attr('disabled', 'disabled');
					//$('#goBackBut').attr('disabled', 'disabled');
  			}else{
				//alert('补单失败!');
				$("#appendResult").html("补单失败!");
			} 		
	 });		 			
}

//验证
mobile_sign_tms_bill_sp_info.checkExistStatus = function(url, checkColumnJsonData) {
		var status = "";
		$.ajax({
		type : 'get',
		url : url,
		data : checkColumnJsonData,
		cache : true,
		async : false, // 一定要
		success : function(result) {
			status=result;
 		}
		});
		return status;
};

 


