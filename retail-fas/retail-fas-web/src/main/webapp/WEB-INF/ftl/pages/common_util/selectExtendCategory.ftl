<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>选择扩展分类</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/selectExtendCategory.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/common_util.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<div data-options="region:'center',border:false" id="extendCategoryDiv">
			      <@p.datagrid id="extendCategoryDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" title=""
	                isHasToolBar="false" onClickRowEdit="false" pagination="false" emptyMsg=""
	                rownumbers="false" 
					columnsJsonList="[
						{field:'extendCategoryType',title:'分类',width:100},
						{field:'extendCategoryTypeCode',title:'分类编码',hidden : true,editor:{type:'readonlytext'}},
						{field:'extendCategoryName',title:'类别名称',width : 220,editor:{type:'readonlytext'}},
						{field:'extendCategoryCode',title:'类别编码',width : 180,editor:{type:'readonlytext'}}]"	
						jsonExtend="{onDblClickRow:function(rowIndex,rowData){
				 			selectExtendCategory.beginEdit(rowIndex);
				 		}}"	
						/>
			</div>
		 </div>
	</div>
	
</body>
</html>