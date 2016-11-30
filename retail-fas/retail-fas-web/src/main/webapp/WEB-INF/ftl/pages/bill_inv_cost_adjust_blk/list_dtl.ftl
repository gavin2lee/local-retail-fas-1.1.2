<div id="dtl_panel" class="easyui-layout" data-options="fit:true,border:false">
	<div id="queryConditionDiv" data-options="region:'north',border:false">
	
		<@p.toolbar id="dtlbar" listData=[
			{"id":"dtl_btn_search","title":"浏览","iconCls":"icon-search","action":"ctrl.returnTab(0)","type":0},
			{"id":"add","title":"新增","iconCls":"icon-add","action":"ctrl.add()","type":1} ,
			{"id":"save","title":"保存","iconCls":"icon-save","action":"ctrl.save()","type":7} ,
			{"id":"confirm","title":"确认","iconCls":"icon-aduit","action":"ctrl.confirm()","type":18} ,
			{"id":"export","title":"导出","iconCls":"icon-export","action":"ctrl.exportExcel()","type":4}
		 ]/>
		 <div class="search-div">
            <#-- 主档信息  -->
            <form id="dtlDataForm"  method="post">
            	 <table class="form-tb">
            	    <col width="80px"/>
            	 	<col />
            	 	<col width="80px"/>
            	 	<col />
            	 	<col width="80px"/>
            	 	<col />
                    <tbody>
                        <tr>
                            <th>单据编号：</th>
                            <td><input class="readonly ipt" disabled="true" name="billNo" id="billNo" data-options=""/></td>
                            <th>单据状态：</th>
							<td><input  class='ipt' disabled="true" name="status" id="statusCondition"  /></td>  
							<th><span class="ui-color-red">*</span>调整年份：</th>
                            <td>
                            	<input class="easyui-combobox ipt" name="year" id="yearCondition" data-options="required:true"/>
                            </td>
                        </tr>
                        <tr>
                        	<th><span class="ui-color-red">*</span>调整月份：</th>
                            <td><input class="easyui-combobox ipt" name="month" id="monthCondition" data-options="required:true"/></td>	
                            <th><span class="ui-color-red">*</span>公司名称：</th>
                            <td colspan="3">
                            	<input class="ipt easyui-company"  name="companyName" id="companyName1" data-options="inputWidth:170,required:true,inputNoField:'companyNo1', inputNameField:'companyName1'"/>
								<input type="hidden"  name="companyNo" id="companyNo1" 	/>	
                            </td>
                        </tr>
                        <tr>
                            <th>备注：</th>
                            <td colspan="7"><input class="easyui-validatebox ipt" style="width:99%" name="remark" id="remark"/></td>
                        </tr>
                    </tbody>
                </table>
			 </form>
        </div>
	</div>
	
	<div data-options="region:'center',border:false" style="height:350px;">
    	<div class="easyui-layout" data-options="fit:true,border:false">
	        <div data-options="region:'north',border:false">
	        	<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "ctrl.addDetail()", "type":2},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "ctrl.deleteDtl()","type":2}
		             {"id":"btn-import","title":"明细导入","iconCls":"icon-import","action":"ctrl.importDetail()","type":2}
		           ]
				/>
	        </div>
	        
	        <div data-options="region:'center',border:false">
                <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
					isHasToolBar="false" divToolbar="" height="387"    pageSize="500" 
					onClickRowEdit="true" onClickRowAuto="false" pagination="true" rownumbers="true"
					columnsJsonList="[ {
							field : 'styleNo',
							title : '款号编码',
							width : 120,
							editor : {
								type : 'styleNo',
								options : {
									required : true,
									callback : function(row) {
										ctrl.billDetail.selectorStyleNoCallBack(row);
									}
								}
							}
						},{
								field : 'brandNo',
								title : '品牌编码',
								width : 80,
								align : 'left',
								editor : {
									type : 'readonlytext',
								}
							},
							{
							field : 'sizeKind',
							title : '尺寸分类',
							width : 90,
							align : 'left',
							editor : {
								type : 'readonlytext'
							}
						},{
							field : 'adjustCost',
							title : '调整的成本',
							width : 120, 
							align : 'right',
							editor : {
								type : 'numberbox',
								options : {
									required : true,
								    tipPosition:'none',
									min:0,
									precision:4,
									onChange : function() {
			 							billInvCostAdjust.invCostAdjustChange($(this).val());
			 						}
								}
							}
						}, {
							field : 'unitCost',
							title : '调整前成本',
							width : 120,
							align : 'right',
							editor : {
								type : 'readonlytext'
							}
						}, {
							field : 'closingQty',
							title : '当前调整的数量',
							width : 120,
							align : 'right',
							editor : {
								type : 'readonlytext'
							}
						}, {
							field : 'closingAmount',
							title : '系统当前调整的余额',
							width : 120,
							align : 'right',
							editor : {
								type : 'readonlytext'
							}
						}, {
							field : 'adjustAmount',
							title : '调整后的余额',
							hidden : true,
							width : 120,
							align : 'right',
							editor : {
								type : 'readonlytext'
							}
						}, {
							field : 'diverAmount',
							title : '库存成本调整差异',
							width : 120,
							align : 'right',
							editor : {
								type : 'readonlytext'
							}
						}]" 
					jsonExtend="{onDblClickRow:function(rowIndex,rowData){
						
						}}" />
                </div>
        </div>
 	</div>
</div>