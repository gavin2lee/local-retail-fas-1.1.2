<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="Keywords" content="百丽物流总部管理平台" />
<meta name="Description" content="百丽物流总部管理平台" />
<title>百丽物流总部管理平台</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script  src="${resourcesUrl}/fas/resources/common/other-lib/ucindex.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<div id="loading"><p>加载中，请稍后...</p></div>
<div class="header" data-options="region: 'north',border: false"> 
    <div class="wrapper">
        <div class="logo">
            <h1>零售系统</h1>
        </div>
        <div class="nav">
            <div class="system">
                <span class="welcome">你好！${(Session["session_user"].username)!}  欢迎进入！</span>
                <span class="logout"><a href="<@s.url "/uc_logout"/>">退出</a></span>
             </div>           
                <div class="subSys">
	                <a href="#">门店销售</a>
	                <a href="#">营运管理</a>
	                <a href="#">会员管理</a>
	                <a href="#">补订货管理</a>
	                <a href="#">货品管理</a>
	                <a href="#" class="current">财务辅助</a>
	                <a href="#">基础数据</a>
            	</div>
        </div>
     </div>
</div>

<div id="left" data-options="region:'west',split:true,title:'目录导航',iconCls:'text-list-bullets',minWidth:180,maxWidth:180,minSplit:true">
    <div class="easyui-accordion" id="leftMenu" data-options="split:true,border:false,fit: true, animate: false"  style="border:none;" >
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
</div>
<div id="tab-tools" style="display:none;border-left:none;border-top:none;">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" id="tabToolsFullScreen" iconCls="icon-window-max">全屏</a>
</div>
</body>
</html>
