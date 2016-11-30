<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" id="dtlDGTab">
    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
		isHasToolBar="false" divToolbar=""    pageSize="500" 
		checkOnSelect="true"  selectOnCheck="true" singleSelect="false"
		onClickRowAuto="false" rownumbers="true" 
		columnsJsonList="[
			{field : 'id', hidden: true, title : '编号',width : 200, align:'left'},
			{field : 'billTypeName',title : '单据类型',width : 100, align:'left',formatter: function(value,row,index){
	                  		if(row.bizType == '21' || row.bizType == '29') {
	                  			return '批发出库';
	                  		}else if (row.bizType == '22' || row.bizType == '43' || row.bizType == '6' || row.bizType == '30') {
	                  			return '批发退货';
	                  		}else if (row.bizType == '2') {
	                  			return '收款单';
	                  		}else if (row.bizType == '5') {
	                  			return '其他扣项';
	                  		}
			}},
			{field : 'bizTypeName',title : '业务类型',width : 100, align:'left'},
			{field : 'refNo',title : '单据编码',width : 100, align:'left'},			
			{field : 'billDate',title : '交易时间',width : 200, align:'center'},
			{field : 'money',title : '交易金额',width : 200, align:'right'},
			{field : 'remainingAmount',title : '客户可用余额',width : 200, align:'right'},
			{field : 'rebateAmount',title : '返利金额',width : 200, align:'right'},
			{field : 'otherPrice',title : '其他费用',width : 200, align:'right'},
			{field : 'frozenAmount',title : '客户冻结余额',width : 200, align:'right'},
			{field : 'remark',title : '备注信息',width : 400, align:'left'}
			]"		
		 jsonExtend="{}"/>
    </div>
</div>