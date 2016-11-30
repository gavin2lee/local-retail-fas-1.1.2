<div class="easyui-layout" data-options="fit:true,plain:true">	
	<!--列表-->
	<div data-options="region:'center',border:false">
     <@p.datagrid id="returnSupplierDetailDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
	           rownumbers="true"  singleSelect="true" 
	           columnsJsonList="[		           
					{field : 'zoneName', title : '地区', width : 60}, 
						{field : 'organNameFrom', title : '管理城市 ', width : 80},                                                                                                                                                                                                                                      
						{field : 'salerName', title : '地区公司 ', width : 200},
						{field : 'brandUnitNo', title : '品牌 ', width : 80},                                                                                                                                                                                                                             
						{field : 'bizTypeName', title : '自营/批发', width : 80},
						{field : 'returnNo', title : '召回编号 ', width : 80,align:'left',halign:'center'}, 
						{field : 'rcAttribute', title : '召回属性 ', width : 80,align:'left',halign:'center'}, 
						{field : 'reDate', title : '召回时间', width : 90, align:'right'}, 
						{field : 'totalQty01', title : '召回数量', width : 60, align:'right',exportType:'number'},   
						{field : 'totalAmount01', title : '召回金额', width : 90, align:'right',exportType:'number'}, 
						{field : 'sendDate', title : ' 确认时间', width : 90, align:'right'}, 
						{field : 'qty', title : '确认数量', width : 90, align:'right',exportType:'number'}, 
						{field : 'amount', title : '确认金额', width : 90, align:'right',exportType:'number'},
						{field : 'tagAmount', title : '降折损失', width : 90, align:'right',exportType:'number'},
						{field : 'noAffirmDate', title : '不确认转销时间', width : 90, align:'right'},   
						{field : 'totalQty02', title : '未确认转销数量', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount02', title : '未确认转销金额 ', width : 90, align:'right',exportType:'number'},
						{field : 'applyDate', title : '给总部开票时间', width : 90, align:'right'},   
						{field : 'totalQty04', title : '给总部开票数量 ', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount04', title : '给总部开票金额', width : 90, align:'right',exportType:'number'},
						{field : 'totalQty03', title : '未转销数量 ', width : 90, align:'right',exportType:'number'},
						{field : 'totalAmount03', title : '未转销金额', width : 90, align:'right',exportType:'number'},
						{field : 'remark', title : '备注', width : 90, align:'right',exportType:'number'}]" 
         />
	</div>
</div>