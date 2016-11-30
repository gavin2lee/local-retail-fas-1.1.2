<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
    	<#-- 工具栏  -->
  		<@p.toolbar id="clientDtltoolbar" listData=[
				 {"id":"btn-add-detail1","title":"新增","iconCls":"icon-add-dtl", "action" : "insideBizTypeDetailEditor.clientAdd()", "type":0},
	             {"id":"btn-del-detail1","title":"删除","iconCls":"icon-del-dtl","action" : "insideBizTypeDetailEditor.clientDel()", "type":0}
	           ]
		  />
  	</div>
	<div id="clientDtlDataGridDiv" data-options="region:'center',border:false" >
		<@p.datagrid id="clientDtlDataGrid" emptyMsg = ""
			isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
			columnsJsonList="[
			 	{field : 't', checkbox:true, width : 30, notexport:true},
			 	{field : 'dtlType',title : '明细类型',hidden : 'true',align:'center',notexport:true},
			 	{field:'shopNo',title:'客户编码',  align:'left',width : 125,halign:'center',
                  	editor:{
                  		type:'validatebox',
                  		options:{
                  			required:true,
                  			validType:'maxLength[20]'
                  		}
                  	}
                  },
                  {field : 'shopName', title : '客户名称', align:'left',width : 150,halign:'center',
                  	editor:{
                  		type:'validatebox',
                  		options:{
                  			required:true,
                  			validType:'maxLength[32]'
                  		}
                  	}
                  }]"
		  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
											insideBizTypeDetailEditor.clientEdit(rowIndex, rowData);
							             }}' 
		  />
	</div>
</div>