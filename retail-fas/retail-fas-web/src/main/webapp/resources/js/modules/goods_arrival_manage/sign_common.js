/**
 * 手机签收管理
 */
var sign_common={};

/**
 * 手机签收
 * 1:判读密码是否正确
 * 2:判读托运是否有效及托运单时是否签收
 */
sign_common.sign = function(actionType,page) {
	var dtlTotalboxcount= jQuery.trim($("#dtlTotalboxcount").val());
	var dtlTotalCount= jQuery.trim($("#dtlTotalCount").val());
 	var totalBoxCount= jQuery.trim($("#totalBoxCount").val());
	var totalCount= jQuery.trim($("#totalCount").val());
	$("#signResult").html("");
	if(actionType=="1"){
		if(totalBoxCount==""){
 	 		$("#signResult").html("请输入签收件数.");
	 		return ;
	 	}
	   if(!/^(\+|-)?(\d+)(\.\d*)?$/g.test(totalBoxCount)){
 			$("#signResult").html("签收件数只能为数字");	 
			return ;
		 }
	   if(totalCount!=""&&!/^(\+|-)?(\d+)(\.\d*)?$/g.test(totalCount)){
 			$("#signResult").html("签收数量只能为数字.");	 
			return ;
	   } 
	 }
	 var password= jQuery.trim($("#password").val());
	 var transport_point_no=$("#transport_point_no").val();
	 if(password==""){
 		    $("#signResult").html("签收密码不能为空.");	 
	 		$("#password").focus();
	 		return;
	 	}

  	  var url = BasePath + '/moblie_sign_tms_bill_transport/put_tms_bill_transport_status/list.json';
  	  var billNo=$("#billNo").val();
  	  var truckNo=$("#truckNo").val();
  	  var checkUrl= BasePath + '/moblie_sign_tms_bill_transport/getTmsBillTruckDtl/list.json';
  	  var checkData = {
			"billNo" : billNo,    
			"truckNo" : truckNo
   	  }
  	 //判读托运是否有效及托运单时是否签收
  	  var object="";
  	  sign_common.ajaxRequest(checkUrl, checkData, function(result,returnMsg) {
  		  object=result;
   		});		
    	var status=object.status;
 		var signTime=object.signTime;
    		if (typeof(status) != "undefined"&&status=='1'){
    		 sign_common.promptContent(page,"这是无效的托运单.")
   			//$("#signResult").html('这是无效的托运单!')
  			 return;
  		 }
       if (typeof(signTime) != "undefined" &&signTime!=null&&signTime!=''){
  			// alert('该托运单已经签收,请不要重要提交!');
  			//$("#signResult").html("该托运单已经签收,请不要重要提交!");
  			 sign_common.promptContent(page,"该托运单已经签收,请不要重要签收.")
  			 return;
  		 }
   		//判读密码是否正确
   		var checkUrl = BasePath + '/tms_transport_point_password/get_tmsTransport_password/list';
   	 	var checkDataNo = {
   			"transportPointNo" : transport_point_no,
   			"signPassword" : password,
   			"_method" : "get"
   			};	
   	  var checkFlag=sign_common.checkPassword(checkUrl,checkDataNo);
     	  if(checkFlag){
   		  //$("#signResult").html('签收密码错误!');
    	  sign_common.promptContent(page,"签收密码错误.")
  		  return;
  	  } 	
	  var shiperName=$("#shiperName").val();
	  var shiperTel=$("#shiperTel").val();
	  var total=$("#total").val();
	  var remark=$("#remark").val();
	   var openFlag=$("#openFlag").val();
	   var repeatSumbmitFlag=$("#repeatSumbmitFlag").val();
	   var transport_point_name=$("#transport_point_name").val();
	   
  	  var data = {
			"billNo" : billNo,
			"shiperName" : shiperName,
			"shiperTel" : shiperTel,
			"totalBoxCount" : totalBoxCount,
			"totalCount" : totalCount,
			"openFlag" : openFlag,
			"remark" : remark,
			"truckNo" : truckNo,
			"actionType":actionType,//拒收
			"transportPointName":transport_point_name,
			"_method" : "put"
			};
  	    //签收
    	//var fromObjStr = convertArray($('#signForm').serializeArray());
  		sign_common.ajaxRequest(url,data, function(result,returnMsg) {
  			if(result == 'success') {
				if(actionType=="1"){
				    sign_common.promptContent(page,"签收成功.")
 					sign_common.common(page);
 				}else{
 					sign_common.promptContent(page,"拒签成功.")
  					sign_common.common(page);
				}
			}else{
				sign_common.promptContent(page,"签收失败.")
				//alert('签收失败!');
			} 		
	 });		 			
}

sign_common.promptContent=function(page,content){
	if(page=="pc"){
		alert(content);
 	}else{
 		$("#signResult").html(content);
	}	
}

sign_common.common=function(page){
	if(page=='mobile'){
		$('#rejectSignBut').attr('disabled', 'disabled');
		$('#signBut').attr('disabled', 'disabled');
	}else if(page=='pc'){
		pc_sign.closeWindow();
		pc_sign.doSearch();
	}
} 
sign_common.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : false,
		async : false,
 		success : callback
	});
};   
 

sign_common.checkPassword = function(checkUrl,checkDataNo) {
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
 


