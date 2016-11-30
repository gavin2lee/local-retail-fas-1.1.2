<div data-options="region:'center',border:false">
  <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""  pageSize="20" showFooter="true" 
          isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = "" rownumbers="true" 
           columnsJsonList="[
                 {field : 't', checkbox:true, width : 30, notexport:true,},
                 {field : 'salerName', title : '公司名称', width : 300, 
                 	editor:{type:'test_combogrid',
                 		options:{type:'company',required:true,datagridId:'dtlDataGrid',valueField:'salerNo',
                 			queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole'}
                 	}, align:'left'},
                 {field : 'salerNo', title : '公司编号', width:80, editor:{type:'readonlytext'}},
                 {field : 'buyerName', title : '客户名称', width : 300, editor:{type:'test_combogrid',options:{type:'wholesale_zone_customer',required:true,datagridId:'dtlDataGrid',valueField:'buyerNo'}}, align:'left'},
                 {field : 'buyerNo', title : '客户编号', width:80, editor:{type:'readonlytext'}},
 				 {field : 'deductionDate',title:'日期',width:100,editor:{type:'datebox',options:{required:true}}},
                 {field : 'brandName', title : '品牌', width : 100, editor:{type:'test_combogrid',options:{type:'brand',required:true,datagridId:'dtlDataGrid',valueField:'brandNo'}}, align:'left'},
                 {field : 'brandNo', title : '品牌编号', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
                 {field : 'categoryName', title : '一级大类', width : 80, editor:{type:'test_combogrid',options:{type:'category',required:true,datagridId:'dtlDataGrid',valueField:'categoryNo'}}, align:'left'},
                 {field : 'categoryNo', title : '大类编号', hidden:'true', notexport:true, editor:{type:'readonlytext'}},
				 {field : 'fineAmount',title:'其他扣项',width:100,align:'right',halign:'center', editor:{type:'numberbox',options:{precision:2,validType:'maxLength[12]'}}},
 				 {field : 'deductionAmount', hidden:true, title:'合计金额',width:100},
 				 {field : 'type',title : '扣项类型',width : 100,formatter: function(value,row,index){
	                  		if(row.type == '0') {
	                  			return '其他扣项';
	                  		}else if (row.type == '1') {
	                  			return '返利';
	                  		}else if (row.type == '2') {
	                  			return '其他费用';
	                  		}
				 }},	
 				 {field : 'remark',title:'备注',width:200,align:'left',halign:'center',editor:{type:'validatebox',options:{validType:'maxLength[200]'}}},
 				 {field : 'createUser',title:'创建人',width:100},
 				 {field : 'createTime',title:'创建时间',width:150},
 				 {field : 'status',title:'是否更新余额',width:150,formatter: function(value,row,index){
	                  		if(row.status == ' 1') {
	                  			return '是';
	                  		}else if (row.status == '0') {
	                  			return '否';
	                  		}
	             }},
 				 {field : 'billNo',title:'关联出库单号',width:150},
 				 {field : 'orderNo',title:'关联订单号',width:150},
 				 {field : 'balanceNo',title:'结算单号',width:150, formatter: function(value,row,index){
					return billNoMenuRedirect.billNoMenuFormat(value,row,index,'AW-结算单');
				}},{field : 'balanceStatus',title:'结算单状态',width:120,formatter:common_util.balanceStatusFormat}]"	
	          jsonExtend='{
                   onDblClickRow:function(rowIndex, rowData){
                	  //双击方法
                   	   otherDeduction.edit(rowIndex, rowData);
              }}'
			/>
</div>