<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>模块管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    
<link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/styles/easyui.css"/>" />
<link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/styles/validator.css"/>"/>
<link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/styles/icon.css" />"/>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/authority_module.js"/>"></script>	
<script type="text/javascript">
	var operationsUrl="<@s.url '/authority_operations/get_biz'/>";
	var data;
	$(function($){
		$.ajax({
			url: operationsUrl,
			async: false,
			success: function(d){
		    	data=d;
		   	}
	 	});
	});
</script>
<script>
  $(window).resize(function(){
   
    $('#module').datagrid('resize', {
        width:function(){return document.body.clientWidth;},
    });
 
    
    $('#queryConditionDiv').panel('resize',{
	    width:function(){return document.body.clientWidth;},
   });

    
  
    
   });

</script>

</head>
<body >
<div>
        <div id="zoneInfoToolDiv">
			<a href="javascript:authority_module.insertRowauthority_module('module')" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
			<a href="javascript:authority_module.removeBySelected('module')" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:authority_module.save('module')" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存</a>
 		</div>
 		
 	   <#-- 条件查询区域div -->		
	  <div style="background:#F4F4F4;padding:0px;">
	       <@p.queryConditionDiv id="queryConditionDiv" dbTableName="authority_module" queryGridId="module"  queryDataUrl="${BasePath}/authority_module/list.json?sort=MODULE_SORT&order=desc" collapsed="true" height="120" />
	  </div> 
 		
 	<div id="soneInfoList" >
 		<@p.datagrid id="module" name="" title="模块列表" loadUrl="/authority_module/list.json?sort=MODULE_SORT&order=desc" saveUrl="" defaultColumn="" 
 			isHasToolBar="false"  divToolbar=""  height="500" width="" onClickRowEdit="true" singleSelect="true" pageSize='50'  
			pagination="true" rownumbers="true"
 			columnsJsonList="[
 				{field:'id',title:'模块编号',width:70,hidden:true
 				},
 				{field:'name',title:'模块名称',width:150,
 					editor:{
 						type:'validatebox',
 						options:{
	 						required:true,
	 						missingMessage:'模块名称为必填项!'
 						}
 					}
 				},
 				{field:'url',title:'模块链接',width:150,
 					editor:{
 						type:'validatebox',
 						options:{
	 						required:true,
	 						missingMessage:'模块链接为必填项!'
 						}
 					}
 				},
			  {field:'sort',title:'排序',width:100,
 					editor:{
 						type:'numberbox',
 						options:{
	 						min:1,
	 						max:9999,
	 						required:true,
	 						missingMessage:'排序为必填项!'
 						}
 					}
 				},
 				{field:'remark',title:'备注',width:200,
 				 	editor:{
 						type:'validatebox'
 					}
 				},
 				{field:'operations',title:'操作权限',width:200,
		 			formatter: function(value,row,index){
		 				var displayValue='';
		 				if(value!=''&&value!=undefined){
							$.each(value.split(','),function(index, row_value){
								$.each(data,function(j,d){
									if(d.id==row_value){
										displayValue+=displayValue.length==0?d.text:','+d.text;
									}
								});
							});
		 				}
						return displayValue;
					},
					editor:{
 						type:'combotree',
 						options:{
 						  required:true,
 						  multiple:true,
 						  data:data
 						}
 					}
 				}
 			]"
 		/>
 	</div>	
 		

</div>



</body>
</html>