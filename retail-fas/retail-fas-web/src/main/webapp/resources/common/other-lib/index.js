var maxTab = false;//最大化
var dgSelectorOpts=null;//弹窗选择器
//document.domain = "belle.net.cn";

function parsePage(){
	//memorandum();
	buildSubSys_mps();
	
	InitLeftMenu('#leftMenu');
	$('#tabToolsFullScreen').click(function(){
		changeScreen();
	});
	
	$('#lnkDesk').click(function(){
		addTab({
		title: '系统桌面'
		});
	});
	$('#loading').remove();
//	seajs.use(['gms/authority'], function (authority) {
//	    authority.zoneSwitcher.init($('#hd_zone'),true); //require 为true时强制校验用户大区权限，如果没有分配大区权限则强制跳回登录页面。
//	});
    seajs.use(['gms/authority','gms/repository'], function (authority,repository) {
        authority.zoneSwitcher.init($('#hd_zone'),true,function(){
            InitLeftMenu('#leftMenu');
        });
        repository.url = "/gms/";
        if( window.location.href.indexOf('dev')>=0){            
            repository.url = "http://dev.gms.belle.net.cn/gms/";
        }
        repository.syncState();
    });
}
/**
*根据JSON数据生成子系统
*/
function buildSubSys_mps(){
	var callback = function(data){
		if(typeof data == "string"){
			data =  jQuery.parseJSON(data);
		}
		buildSubSysCommon(data);
		 $('.subSys').scrollmenu();
	};
	$.getJSON(BasePath+"/uc_sub_system.json",callback);
}

//初始化左侧菜单
function InitLeftMenu(obj) {
	 $.getJSON(BasePath+"/uc_user_tree.json", function(data){
		 if(typeof data == "string"){
				data =  jQuery.parseJSON(data);
		 }
		 buildMenuCommon(data[0].children);
	 });
}

//全屏切换
function changeScreen() {
    if (maxTab) {
       // $('body').layout('show', 'west');
        $('body').layout('show', 'north');
        $('body').layout('show', 'south');
				if ($.util.supportsFullScreen) {
         	$.util.cancelFullScreen();
       	 }
        maxTab = false;
        $('#tabToolsFullScreen').linkbutton({
            iconCls: "icon-window-max",
            text: "全屏"
        });
    } else {
        $('body').layout('hidden', 'south');
       // $('body').layout('hidden', 'west');
        $('body').layout('hidden', 'north');
				
				if ($.util.supportsFullScreen) {
						$.util.requestFullScreen();
				} 
        maxTab = true;
        $('#tabToolsFullScreen').linkbutton({
            iconCls: "icon-window-min",
            text: "正常"
        });
    }
}

function setTimerSpan(){
	var timerSpan = $("#timerSpan"), 
	interval = function () { 
		timerSpan.text($.date.toLongDateTimeString(new Date()));
	};
	interval();
	window.setInterval(interval, 1000);
};

//打开修改密码窗口
function updatePassword(){
  var fromObj = $('#dataForm');
  fromObj.form('clear');
  $('#passwordDialog').show();
  $('#passwordDialog').window('open');
};

//校验保存
function savePassword(){
  //验证表单
  var fromObj = $('#dataForm');
  // 1.校验必填项
  var validateForm = fromObj.form('validate');
  if (validateForm == false) {
     return;
  }
  //两次输入密码一致性检验
  var newPwd = $("#current").val();
  var newPwd2 = $("#current2").val();
  if(newPwd != newPwd2){
     showWarn("两次密码输入不一致！");
     return;
  }
  // 2. 保存
  var v_save_url = BasePath + '/user/updatepwd';
  fromObj.form('submit', {
		url : v_save_url,
		dataType : 'json',
		onSubmit : function(data) {
			var param = fromObj.form('getData');
			return param;
		},
		success : function(returnData){
			if(typeof returnData == "string"){
				returnData =  jQuery.parseJSON(returnData);
			}
			if (returnData.code == 0) {
				showSuc('修改成功!');
				$('#passwordDialog').hide();
				$('#passwordDialog').window('close');
			}else{
				showWarn(returnData.msg);
			}
	    },
	    error : function() {
            showError('修改失败,请联系管理员!');
        }
	});
};

//关闭窗口
function closeWindow(){
  $('#passwordDialog').hide();
  $('#passwordDialog').window('close');
};

 