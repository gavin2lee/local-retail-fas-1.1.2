<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
	<div class="easyui-layout" data-options="fit:true,border:false" >
	   	<form name="dataForm" id="dataFormView"  method="post"  class="pd10">
 			<div data-options="region:'north',border:false,height:160" class="pd15">
				<div class="easyui-panel" data-options="title:'客户-收款条款信息',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="1" cellspacing="1" class="form-tb">
                       <tr>
                       		<th width="110" align='right'>
                       			<span class="ui-color-red">*</span>公司：
                       		</th>
                        	<td width="140" align='left'>
                            	<input class="easyui-company ipt"  name="companyName" id="companyNameView" data-options="required:true,inputWidth:200,inputNoField:'companyNo',inputNameField:'companyName'" />
                            	<input type="hidden" name="companyNo" id="companyNo"/>
                            </td>
                            <th width="110" align='right'>
                            	<span class="ui-color-red">*</span>客户：
                            </th>
                        	<td width="140" align='left'>
                            	<input class="easyui-wholesale_zone_customer ipt" name="customerName" id="termNameView" data-options="required:true,inputWidth:200,inputNoField:'customerNo',inputNameField:'customerName'"/>
                            	<input type="hidden"  name="customerNo" id="customerNo"/>
                            </td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>
								<span class="ui-color-red">*</span>条款：
							</th>
                        	<td width="140" align='left'>
                        		<input class="easyui-term ipt" name="termName" id="customerNameView" data-options="required:true,inputWidth:200"/>
                        		<input type="hidden" name="termNo" id="termNo" />
                        	</td>
							<th width="110" align='right'>启用保证金控制：</th>
                            <td width="140" align='left'>
                            	<input type="checkbox" name="returnOwnFlagView" id="returnOwnFlagView"/>
                            </td>
                        </tr>
                        <tr>
                            <th width="110" align='right'>保证金额度：</th>
                            <td width="140" align='left'><input class="easyui-numberbox ipt" style='width:190px' name="marginAmount" id="marginAmountView" data-options="min:0,precision:2"/></td>
                            <th width="110" align='right'>保证金余额：</th>
                            <td width="140" align='left'><input class="easyui-numberbox ipt readonly" readonly="readonly" style='width:190px' name="marginRemainderAmount" id="marginRemainderAmount" data-options="min:0,precision:2"/></td>
                        </tr>
					</table>
				</div>
			</div>
			<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
				<div class="easyui-layout" data-options="border:true,fit:true" >
					<div data-options="region:'north',border:false">
						<@p.toolbar id="dtltoolbarView" listData=[
								 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "type":0},
					             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del", "type":0}
					           ]
					  	/>
					</div>
						<div data-options="region:'center',border:false" id="dtlDataGrid">
									<@p.datagrid id="dtlDataGridDivView" emptyMsg = ""
										isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
										columnsJsonList="[
							 	{field : 't', checkbox:true, width : 30, notexport:true},
								{field:'year',title:'年份',width:100, editor:{type:'combobox',options:{required:true, editable:false, data:yearData,valueField: 'value',textField: 'text'}}},
								{field:'rebateAmount',title:'返利额度',width:120, editor:{type:'numberbox',options:{required:true}}},
								{field:'hasRebateAmount',title:'已返利',width:120, editor:{type:'readonlytext'}},
								{field:'remark',title:'备注',width:200, editor:{type:'validatebox'}}
								]"
							jsonExtend='{
				                           onDblClickRow:function(rowIndex, rowData){
						                   	   editor.editRow(rowIndex, rowData);
						           		 }}'
						  />
					</div>
				</div>
			</div>
	</form>
</div>
</div>
</body>
</html>