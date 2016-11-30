var taskStatus = 0;
var warnStatus = 0;
var userHasMenus;

$(function(){
// 首页暂不上
//	getUserHasMenus();
//	taskList();
//	warnList();
//	memorandum();

////	itemList();
////	addItemList();
});



function refreshBefore(divId){
	$("#"+divId).css("backgroundColor","#F8F8F8");
	$("#"+divId + " a").removeAttr('href');
	$("#"+divId + " a").removeAttr('onclick');
}

function refreshAfter(divId){
	$("#"+divId).css("backgroundColor","#FFFFFF");
}

function refreshTask() {
//	$('#taskPanelDiv').showLoading();
	if(taskStatus==1){
		alert('数据刷新中,请稍后......');
		return;
	}
	taskStatus = 1;
	refreshBefore("taskPanel");
	$.ajax({
		  type: 'POST',
		  url: BasePath+'/pre_warn_template/check.json?checkType=0',
		  data: {},
		  cache: true,
		  async : true,
		  dataType : 'json',
		  success: function(data) {
			  if(data.status){
					alert('强制刷新待办消息成功！');
					taskList();
				}else{
					alert('error');
				}
			  taskStatus = 0;
			  refreshAfter("taskPanel");
//			  $('#taskPanelDiv').hideLoading();
		  }
	});
}

function refreshWarn() {
//	$('#warnPanelDiv').showLoading();
	if(warnStatus==1){
		alert('数据刷新中,请稍后......');
		return;
	}
	warnStatus = 1;
	refreshBefore("warnPanelTemp");
	$.ajax({
		  type: 'POST',
		  url: BasePath+'/pre_warn_template/check.json?checkType=1',
		  data: {},
		  cache: true,
		  async : true,
		  dataType : 'json',
		  success: function(data) {
			  if(data.status){
					alert('强制刷新预警消息成功！');
					warnList();
				}else{
					alert('error');
				}
			  warnStatus = 0;
			  refreshAfter("warnPanelTemp");
//			  $('#warnPanelDiv').hideLoading();
		  }
	});
}

function addItemList(){
	ajaxRequestAsync( BasePath+'/often_use_menu/getAuthorityModel.json',null,function(data){
		var htmlStr="";
		if(data.length>0){
			for(var i=0;i<data.length;i++){
				htmlStr+="<div class='fas-func-item'>";
				htmlStr+="<i class='func-icon'></i>";
//				htmlStr+="<div class='func-text'><a href='javascript:void(0)' onclick=todo('"+data[i].moduleUrl+"','"+data[i].moduleName+"')><h4 class='text-ellipsis'>"+data[i].menuName+"</h4><h5 class='c9 text-ellipsis'>"+data[i].moduleName+"</h5></a></div>";
				htmlStr+="<div class='func-text'><a href='javascript:void(0)'><h4 class='text-ellipsis'>"+data[i].menuName+"</h4><h5 class='c9 text-ellipsis'>"+data[i].moduleName+"</h5></a></div>";
				htmlStr+="</div>";
			}
		}else{
			htmlStr = "<div><span>暂无数据</span></div>";
		}
		$("#addItemDiv").html(htmlStr);
	});
}

function getUserHasMenus(){
	ajaxRequestAsync( BasePath+'/often_use_menu/getAuthorityModel.json',null,function(data){
		userHasMenus = data;
	});
}

function checkAuthorityMenus(data){
	var newData = new Array();
	if(userHasMenus.length > 0){
		for(var i=0;i<data.length;i++){
			for(var j=0;j<userHasMenus.length;j++){
				if(data[i].moduleName==userHasMenus[j].moduleName){
					newData.push(data[i]);
					break;
				}
			}
		}
		return newData;
	}
}

function itemList(){
	ajaxRequestAsync( BasePath+'/often_use_menu/findItemsByUserId.json',null,function(data){
		var htmlStr="";
		if(data.length>0){
			for(var i=0;i<data.length;i++){
//				htmlStr+="<div class='fas-event-item'>";
//				htmlStr+="<i class='func-icon func-account'></i>";
//				htmlStr+="<div class='func-text'><a href='javascript:void(0)' onclick=todo('"+data[i].url+"','"+data[i].name+"')><h4 class='text-ellipsis'>"+data[i].name+"</h4><h5 class='c9 text-ellipsis'>"+data[i].name+"</h5></a></div>";
//				htmlStr+="<div class='func-item-delete'></div>";
//				htmlStr+="</div>";
				
				htmlStr+="<div>";
				htmlStr+="<i></i>";
				htmlStr+="<div><a href='javascript:void(0)' onclick=todo('"+data[i].url+"','"+data[i].name+"')><h4 class='text-ellipsis'>"+data[i].name+"</h4><h5 class='c9 text-ellipsis'>"+data[i].name+"</h5></a></div>";
				htmlStr+="<div></div>";
				htmlStr+="</div>";
			}
		}else{
			htmlStr = "<div><span>暂无数据</span></div>";
		}
		$("#oftenUserMenuItemDiv").html(htmlStr);
	});
};

function taskList(){
	ajaxRequestAsync( BasePath+'/pre_warn_message/getCheckMessage.json',{type:0},function(data){
		var htmlStr="";
		if(data.length>0){
			data = checkAuthorityMenus(data);
			for(var i=0;i<data.length;i++){
				htmlStr+="<div class='fas-event-item'>";
				htmlStr+="<div class='event-index'>"+(i+1)+"</div>";
				htmlStr+="<div class='event-title text-ellipsis'>";
				htmlStr+="<a href='#'>"+data[i].nodeName+"</a></div>";
				htmlStr+="<div class='event-description'>";
				htmlStr+="<a href=javascript:void(0) onclick=toLink('"+data[i].postUrl+"','"+data[i].showUrl+"','"+data[i].tabTitle+"')>"+data[i].preTitle+"<span class='cblue' style='color:red'>"+data[i].num+"</span> 单"+data[i].sufTitle+"</a>";
				htmlStr+="</div>";
				htmlStr+="</div>";
			}
		}else{
			htmlStr = "<div><span>暂无数据</span></div>";
		}
		
		$("#taskPanel").html(htmlStr);
	});
};

function warnList(){
	ajaxRequestAsync( BasePath+'/pre_warn_message/getCheckMessage.json',{type:1},function(data){
		var htmlStr="";
		if(data.length>0){
			data = checkAuthorityMenus(data);
			for(var i=0;i<data.length;i++){
				htmlStr+="<div class='fas-event-item'>";
				htmlStr+="<div class='event-index'>"+(i+1)+"</div>";
				htmlStr+="<div class='event-content'>";
				htmlStr+="<a href=javascript:void(0) onclick=toLink('"+data[i].postUrl+"','"+data[i].showUrl+"','"+data[i].tabTitle+"')><h4 class='text-ellipsis'>"+data[i].nodeName+"</h4><span class='cblue'>"+data[i].num+"</span> 单"+data[i].sufTitle+"</a>";
				htmlStr+="</div>";
				htmlStr+="</div>";
			}
		}else{
			htmlStr = "<div><span>暂无数据</span></div>";
		}
		$("#warnPanel").html(htmlStr);
	});
};



function memorandum(){
	$.getJSON(BasePath+'/memorandum/getMemorandum', function(data){
		var htmlStr="";
		var url=$("#url").val();
		for(var i=0;i<data.length;i++){
			htmlStr+="<div class='fas-memo-item'>";
			htmlStr+="<img  src='"+url+"/fas/resources/images/1.bmp;'  onclick='updateData("+data[i].id+",&quot;"+data[i].title+"&quot;,&quot;"+data[i].executionTime+"&quot;)' style='cursor: pointer;position:relative;top:-20px;left:117px;'/>";
			htmlStr+="<img  src='"+url+"/fas/resources/images/2.bmp;' onclick='deleteId("+data[i].id+")' style='cursor: pointer;position:relative;top:-20px;left:117px;'/>";
			htmlStr+="<tr><input  type='text' readonly='readonly' value='"+data[i].executionTime+"' id='executionTimes' class='executionTime' style='background:none;border:none;position:relative;top:-15px;right:22px;width:100px;height:28px;font-size:16px;'/></th>";
			htmlStr+="<td><textarea readonly='readonly' class='title' style='background:none;position:relative;top:-16px;width:145px; height:50px; border:NONE;'>"+data[i].title+"</textarea></td>";
			htmlStr+="</div>";
		}
		$("#memorandum").html(htmlStr);
	 });
	
}
//新增备忘录
function addShow(){
	 document.getElementById("id").value="";
	document.getElementById ("memorandumDialog").setAttribute("title","新增备忘录");
	//$("#memorandumDialog").attr("setTitle","新增备忘录");
	 var fromObj = $("#memeorandumForms");
	  fromObj.form('clear');
	  $('#memorandumDialog').show();
	  $('#memorandumDialog').window('open');
}
function saveMemorandum(){
	 //验证表单
	  var fromObj = $('#memeorandumForms');
	  // 1.校验必填项
	  var validateForm = fromObj.form('validate');
	  if (validateForm == false) {
	     return;
	  }
	  var params = {
		title:$('#title').val(),
		executionTime:$('#executionTime').val(),
		id:$('#id').val()	 
	  };
	  var id=$('#id').val();
	  if(id=="" ||undefined || null){
		  var v_save_url = BasePath + '/memorandum/saves';
	      ajaxRequestAsync(v_save_url, params, function(result){
	    	  if (result!=null && result["success"]==true) {
	    		  showSuc('保存成功');
	    		  closeMemorandum();
	    		  memorandum(); 
	    	  }else{
	    	      showSuc('保存失败');
	    	  }
	    });
	  }else{
		  var v_save_url = BasePath + '/memorandum/updateId';
	      ajaxRequestAsync(v_save_url, params, function(result){
	    	  if (result!=null && result["success"]==true) {
	    		  showSuc('修改成功');
	    		  closeMemorandum();
	    		  memorandum(); 
	    	  }else{
	    	      showSuc('修改失败');
	    	  }
	    });
	  }
	 

};
//打开修改备忘录窗口
function updateData(id,title,executionTime){
	var fromObj = $("#memeorandumForms");
	fromObj.form('clear');
	$('#title').val(title);
	$('#id').val(id);
	$('#memeorandumForms').find('input[name="title"]')
	$('#memeorandumForms').find('input[name="executionTime"]').datebox('setValue',executionTime);
	$('#memorandumDialog').show();
	$('#memorandumDialog').window('open');
  
};
//删除备忘录
function deleteId(id){
	if (id) {
		$.messager.confirm("确认", "确定要删除当前单据?", function(r) {
			if (r) {
				var url = BasePath+'/memorandum/delete';
				var params = {
						id : id,
					};
				ajaxRequestAsync(url, params, function(data) {
					if (data!=null &&data["success"]==true) {
						$.fas.showMsg('删除成功',2000);
						memorandum();
					} else {
						showError('删除失败');
					}
				});
			}
		});
	}
};
//关闭备忘录操作窗口
function closeMemorandum(){
  $('#memorandumDialog').hide();
  $('#memorandumDialog').window('close');
};

function todo(url, title){
	openNewPane(BasePath +"/"+ url, null, title);
}

function toLink(postUrl, showUrl, title){	
	var url;
	if(showUrl.indexOf('?')>0){
		url = BasePath + showUrl + "&warnPostUrl=";
	}else {
		url = BasePath + showUrl + "?warnPostUrl=";
	};
	url = url + postUrl.replaceAll("&", ":");
	openNewPane(url, null, title);
}



