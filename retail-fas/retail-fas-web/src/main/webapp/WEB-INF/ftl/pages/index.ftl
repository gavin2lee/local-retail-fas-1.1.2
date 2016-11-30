<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="Keywords" content="百丽零售FAS系统" />
<meta name="Description" content="百丽零售FAS管理系统" />
<title>百丽零售FAS管理系统</title>
<#include  "/WEB-INF/ftl/common/header.ftl"/>
<script  src="${resourcesUrl}/fas/resources/common/other-lib/index.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<link type="text/css" rel="stylesheet" href="${resourcesUrl}/fas/resources/css/home.css" />
<link type="text/css" rel="stylesheet" href="${resourcesUrl}/fas/resources/css/styles/showLoading.css" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/js/jquery.showLoading.min.js"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/billNoMenuRedirect.js?version=${version}"></script>
<script src="${resourcesUrl}/fas/resources/js/schedule.js"></script>
<script src="${resourcesUrl}/fas/resources/js/home.js"></script>
</head>
<body class="easyui-layout">
<div id="loading"><p>加载中，请稍后...</p></div>
<div class="header" data-options="region: 'north',border: false">
	<div class="wrapper">
        <div class="logo retail-logo">
            <h1>FAS</h1>
        </div>
        <@env_identify env="train">
    		<div class="flag-train"></div>
		</@env_identify>
		<@env_identify env="online">
    		<div class="flag-pro"></div>
		</@env_identify>
        <div class="nav">
            <div class="system">
            	<span class="welcome" style="margin-right:5px;">版本:${version!}</span>
                <span class="welcome">你好！${(Session["session_user"].username)!} 欢迎进入！</span>
                <span class="welcome" id="hd_zone"></span>&nbsp;
                <span class="welcome"><a href="javascript:updatePassword();">修改密码</a></span>
                <span class="logout"><a href="<@s.url "/sso_logout"/>">退出</a></span>
            </div>
            <div class="subSys" id="subSystem">
                
            </div>
        </div>
	</div>
</div>
<div id="left" data-options="region:'west',split:true,title:'财务辅助(FAS)',iconCls:'text-list-bullets',minWidth:200,maxWidth:300,minSplit:true,searchBox:true ">
    <div id="leftMenu">
    </div>
</div>
<div id="main" data-options="region:'center'">
  		<div id="mainTabs" class="easyui-tabs" data-options="fit:true,border:false" tools="#tab-tools">
         <div title="系统桌面"  data-options="icon:'icon-home'">
            <div class="pd10">
               		<img src="${resourcesUrl}/fas/resources/images/welcome.jpg" />
            </div>
          </div>
        </div>
        <!--
        <div id="mainTabs" class="easyui-tabs" data-options="fit:true,border:false" tools="#tab-tools">
            <div title="财务首页"  data-options="icon:'icon-home'">
                <div class="finance-row">
                    <div class="finance-column">
                        <div class="easyui-panel fas-home-panel funcs-panel" title="常用功能" style="height:230px">
                            <div class="fas-funcs">
                                <div class="func-buttons">
                                    <div class="func-btn func-delete">
                                        <i class="func-icon"></i>
                                    </div>
                                    <div class="func-btn func-ok" style="display:none;">
                                        <i class="func-icon"></i>
                                        <span class="func-btn-text">完成</span>
                                    </div>
                                    
                                    <div class="func-btn func-add">
                                        <i class="func-icon"></i>
                                        <div class="fas-func-add">
                                            <div class="fas-func-status">
                                            	已选中 <span class="cred current">3</span> 个，不超过<span class="total">6</span>个
                                            </div>
                                            <div id="addItemDiv" style="height:230px; overflow: auto;">
	                                           <div class="fas-func-item">
	                                                <i class="func-icon"></i>
	                                                <div class="func-text">
	                                                    <a href="#">
	                                                        <h4 class="text-ellipsis">结算间</h4>
	                                                        <h5 class="c9 text-ellipsis">地区间结算</h5>
	                                                    </a>
	                                                </div>
	                                            </div>
                                           </div>
                                       
                                            <div class="fas-func-item">
                                                <i class="func-icon"></i>
                                                <div class="func-text">
                                                    <a href="#">
                                                        <h4 class="text-ellipsis">地区报表</h4>
                                                        <h5 class="c9 text-ellipsis">地区间结算</h5>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="fas-func-item">
                                                <i class="func-icon"></i>
                                                <div class="func-text">
                                                    <a href="#">
                                                        <h4 class="text-ellipsis">地区报表</h4>
                                                        <h5 class="c9 text-ellipsis">地区间结算</h5>
                                                    </a>
                                                </div>
                                            </div>
                                        
                                        </div>
                                    </div>
                                </div>
	                             <div id="oftenUserMenuItemDiv" class="fas-func-item">
	                                   <i class="func-icon func-account"></i>
	                                    <div class="func-text">
	                                        <a href="#">
	                                            <h4 class="text-ellipsis">结算间</h4>
	                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
	                                        </a>
	                                    </div>
	                                    <div class="func-item-delete"></div> 
	                             </div>
                                <div class="fas-func-item">
                                    <i class="func-icon func-table"></i>
                                    <div class="func-text">
                                        <a href="#">
                                            <h4 class="text-ellipsis">地区报表</h4>
                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
                                        </a>
                                    </div>
                                    <div class="func-item-delete"></div>
                                </div>
                                <div class="fas-func-item">
                                    <i class="func-icon func-table-2"></i>
                                    <div class="func-text">
                                        <a href="#">
                                            <h4 class="text-ellipsis">地区报表</h4>
                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
                                        </a>
                                    </div>
                                    <div class="func-item-delete"></div>
                                </div>
                                <div class="fas-func-item">
                                    <i class="func-icon func-region-account"></i>
                                    <div class="func-text">
                                        <a href="#">
                                            <h4 class="text-ellipsis">地区间结算</h4>
                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
                                        </a>
                                    </div>
                                    <div class="func-item-delete"></div>
                                </div>
                                <div class="fas-func-item">
                                    <i class="func-icon func-invoice"></i>
                                    <div class="func-text">
                                        <a href="#">
                                            <h4 class="text-ellipsis">地区发票</h4>
                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
                                        </a>
                                    </div>
                                    <div class="func-item-delete"></div>
                                </div>
                                <div class="fas-func-item">
                                    <i class="func-icon func-invoice-2"></i>
                                    <div class="func-text">
                                        <a href="#">
                                            <h4 class="text-ellipsis">地区发票</h4>
                                            <h5 class="c9 text-ellipsis">地区间结算</h5>
                                        </a>
                                    </div>
                                    <div class="func-item-delete"></div>
                                </div>
                            </div>
                        </div>
                        <div id="taskPanelDiv">
	                        <div class="easyui-panel fas-home-panel" title="待办事宜" style="height:250px; " data-options="refreshCls:'icon-refresh',tools:'#taskIco'" >
	                            <div class="fas-events fas-tasks" id="taskPanel">数据加载中,请稍后...</div>
	                        </div>
                        </div>
                    </div>
                    <div class="finance-column">
                        <div id="warnPanelDiv">
	                        <div id="warnPanelTemp" class="easyui-panel fas-home-panel" title="预警消息" style="height:230px; overflow: auto;" data-options="refreshCls:'icon-refresh',tools:'#warnIco'">
	    						<div class="fas-events fas-warnings" id="warnPanel">数据加载中,请稍后...</div>
	    					</div>
	    				</div>
                        <input value="${resourcesUrl}" id="url" type="hidden"/>
                        <div class="easyui-panel fas-home-panel" onload="getMemorandum()" title="备忘录" data-options="iconCls:'icon-save',tools:'#memorandumIco'" style="height:250px">
                            <div class="fas-memo"  id="memorandum">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  	<div id="memorandumIco">
		<img src="${resourcesUrl}/fas/resources/images/3.bmp;"  style="cursor: pointer;" onclick="addShow()"/>
	</div>
	<div id="taskIco">
		<img src="${resourcesUrl}/fas/resources/images/4.bmp;"  style="cursor: pointer;" onclick="refreshTask()"/>
	</div>
	<div id="warnIco">
		<img src="${resourcesUrl}/fas/resources/images/4.bmp;"  style="cursor: pointer;" onclick="refreshWarn()"/>
	</div>
   -->
</div>
<div id="tab-tools" style="display:none;border-left:none;border-top:none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" id="tabToolsFullScreen" iconCls="icon-window-max">全屏</a>
</div>
<div id="passwordDialog"  class="easyui-window" title="修改密码"
        style="width:400px;height:300px;padding:1px;display:none;" 
        data-options="iconCls:'icon-mini-tis',modal:true,resizable:false,draggable:true,collapsible:false,closed:true,closable:true, minimizable:false,maximizable:false">
       <form name="dataForm" id="dataForm" method="post" style="padding-top:20px;">
           <table class="form-tb" align="center">
              <tr height="20">
           
       <td width="80" align='right'>
       <span class="ui-color-red">&nbsp;*&nbsp;</span>原始密码：
       </td>&nbsp; 
                 <td width="120" align='left'>
                     <input class="easyui-validatebox ipt" style="width:130px" name="older" id="older" required="true" type="password"/>
                 </td>
              <tr>  
                 <td align='right'><span class="ui-color-red">&nbsp;*&nbsp;</span>新密码：</td>
                 <td align='left'>
                     <input class="easyui-validatebox ipt" style="width:130px" name="current" id="current" required="true" type="password" />
                 </td>
              </tr>
              <tr>
                 <td align='right'><span class="ui-color-red">&nbsp;*&nbsp;</span>重复密码：</td>
                 <td align='left'>
                     <input class="easyui-validatebox ipt" style="width:130px" name="current2" id="current2" required="true" type="password"/>
                 </td>
              </tr>
              <tr>
              <td width="90" align='right' colspan="2">
                 &nbsp;
              </td>
              </tr>
              <tr height="10">
                 <td width="90" align='right' colspan="2">
                    <div>
                       <a id="info_save"   href="javascript:savePassword();"   class="easyui-linkbutton" data-options="iconCls:'icon-save'"  >保存</a>
                       <a id="info_cancel" href="javascript:closeWindow();"    class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                   </div> 
                 </td>
              </tr>
           </table>
       </form>   
</div>
<!--
<div id="memorandumDialog"  class="easyui-window" title="新增备忘录"
        style="width:300px;height:225px;padding:1px;display:none;" 
        data-options="iconCls:'icon-mini-tis',modal:true,resizable:false,draggable:true,collapsible:false,closed:true,closable:true, minimizable:false,maximizable:false">
       <form name="memeorandumForms"  id="memeorandumForms" method="post" style="padding-top:-20px;">
           <table class="form-tb" align="center">
              <tr height="20">
              	<input type="hidden" name="id" id="id" />
       			<td align='right'>
      		 		<span class="ui-color-red">&nbsp;*&nbsp;</span>执行时间：
       			</td>&nbsp; 
                 <td width="120" align='left'>
                 	<input id="executionTime" name="executionTime" required="true" class="easyui-datebox easyui-validatebox ipt"  style="width:130px"/>
                 </td>
              <tr>  
                 <td align='right'><span class="ui-color-red">&nbsp;*&nbsp;</span>内&nbsp;*&nbsp;容：</td>
                 <td align='left'>
                     <textarea class="easyui-validatebox ipt"  style="height:60px;width:178px"  name="title" id="title" required="true"></textarea>
                 </td>
              </tr>
              <tr>
              <td width="90" align='right' colspan="2">
                 &nbsp;
              </td>
              </tr>
              <tr>
                 <td width="90" align='right' colspan="2">
                    <div>
                       <a id="info_save"   href="javascript:saveMemorandum();"   class="easyui-linkbutton" data-options="iconCls:'icon-save'"  >保存</a>
                       <a id="info_cancel" href="javascript:closeMemorandum();"    class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
                   </div> 
                 </td>
              </tr>
           </table>
       </form>   
</div>
-->
</body>
</html>
