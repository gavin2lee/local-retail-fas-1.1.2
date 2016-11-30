
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<@p.billToolBar type="bill_backsection_split_operaBar"/>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" id="subLayout">
			<div data-options="region:'north',border:false">
				<div class="search-div">
					<#-- 主档信息  -->
					<form id="mainDataForm"  method="post">
						<input type="hidden" name="id" id="id" />
						<table class="form-tb">
							<col width="100px"/><col />
							<col width="100px"/><col />
							<col width="100px"/><col />
							<tbody>
								<tr>
									<td align="right" width="110">单据编码：</td>
									<td><input class="easyui-validatebox ipt" name="backsectionNo" id="backsectionNo" />  </td>
									<td align="right" width="110"><span class="ui-color-red">*</span>公司：</td>
									<td align="left" width="140">
									
										<input class="ipt easyui-validate easyui-company" data-options="inputNoField:'companyNo',inputNameField:'companyName',required:true" name="companyName" id="companyName"/>
										<input type="hidden" name="companyNo" id="companyNo"/>
									<td align="right" width="110"><span class="ui-color-red">*</span>回款方：</td>
									<td align="left" width="140">
										<input class="easyui-customerInvoiceInfo" name="backName" id="backNameId" 
											data-options="
												queryUrl: BasePath + '/base_setting/invoice_info_set/list.json?status=1&clientTypes=3,4',
												inputNoField:'backNoId',inputNameFeild:'backNameId',required:true" />
										<input type="hidden" name="backNo" id="backNoId"/>
									</td>
									<td width="100" align="right"><span class="ui-color-red">*</span>回款日期：</td>
									<td align="left">
										<input class="easyui-datebox ipt"  data-options="required:true" name="backDate" id="backDate"/>
									</td>
								</tr>
								<tr>
									<td width="100" align="right"><span class="ui-color-red">*</span>回款金额：</td>
									<td>
										<input class="easyui-numberbox ipt"  name="backAmount" id="backAmount" data-options="required:true,precision:2, validType:['maxLength[12]']" />
									<td width="50" align="right">备注：</td>
									<td colspan="3"><input type="text"  class="easyui-validatebox" name="remark" id="remark" style="width:400px;"/></td>
									
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
			<#--列表-->
			<div  data-options="region:'center',border:false">
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<#--工具栏   -->
					<div data-options="region:'north',border:false">
						<@p.billToolBar type="bill_backsection_splitdtl_operaBar"/>
					</div>
					
					<div data-options="region:'center',border:false">
						<@p.datagrid id="backSplitDtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
							isHasToolBar="false" onClickRowEdit="false" pagination="false" selectOnCheck="true" 
							checkOnSelect="true" rownumbers="true" singleSelect="false" 
							columnsJsonList="[
								{field : 't',checkbox:true,width : 30},
								{field : 'id',hidden : 'true',align:'center'},
								{field : 'shortName', title : '店铺名称', align:'left',width : 160,
									editor:{type:'readonlybox',options:{id:'shortName_',required:false}}
								},
								{field : 'shopNo', hidden:true, title : '店铺编码', align:'left',width : 80, 
									editor:{type:'hiddenbox',options:{id:'shopNo_',name:'shopNo'}}
								}, 
								{field : 'month',title : '结算月',width : 80,align:'center',
									editor:{type:'readonlybox',options:{id:'month_',required:false}}
								},	
								{field : 'balanceNo', title : '结算单号', width : 150,
									editor:{
				                  		type:'combogrid',
				                  		options:{
				                  			id:'balanceNo_',
				                  			name:'balanceNo',
				                  			panelWidth:550,
				                  			required:true,
				                  			idField:'balanceNo',
				                  			textField:'balanceNo',
				                  			pagination:false,
				                  			columns:[[
				                  				{field : 'balanceNo',title : '结算单号',width : 150, halign : 'center', align : 'left'},
				                  				{field : 'shortName',title : '店铺名称',width : 150, halign : 'center', align : 'left'},
							                	{field : 'month',title : '结算月',width : 80, halign : 'center', align : 'center'},
							                	{field : 'billingAmount',title : '开票金额',width : 80, halign : 'center', align : 'right'},
							                	{field : 'ticketDeductionAmount',title : '票后帐扣费用',width : 80, halign : 'center', align : 'right'}
							 	            ]],
							 	            onShowPanel:function() {
							 	            	var url = BasePath + '/bill_backsection_split_dtl/select_add_dtl.json?companyNo=' 
							 	            		+ $('#companyNo').val() 
							 	            		+ '&backNo=' + $('#backNoId').val()
							 	            		+ '&backDate=' + $('#backDate').val();
							 	            	$(this).combogrid('grid').datagrid('options').url= url;
							 	            	$(this).combogrid('grid').datagrid('load');
							 	            },
				                  			callback : function(rowData) {
				                  				var rows = $('#backSplitDtlDataGrid').datagrid('getRows');
												var flag = true;
												$.each(rows,function(index,row){
													if(row.balanceNo == rowData.balanceNo){
														$('#balanceNo_').combogrid('clear');
														showWarn('结算单号已存在，请勿重复添加');
														flag = false;
													}
												});
												if(flag){
								 	            	$('#shopNo_').val(rowData.shopNo);
								 	            	$('#shortName_').val(rowData.shortName);
								 	            	$('#month_').val(rowData.month);
								 	            	$('#billingAmount_').val(rowData.billingAmount);
								 	            	$('#ticketDeductionAmount_').val(rowData.ticketDeductionAmount);
								 	            	$('#receiveAmount_').val(rowData.receiveAmount);
								 	            	$('#alreadyReceiveAmount_').val(rowData.alreadyReceiveAmount);
								 	            	$('#notReceiveAmount_').val(rowData.notReceiveAmount);
								 	            	$('#thePaymentAmount_').numberbox('setValue', (rowData.receiveAmount - rowData.alreadyReceiveAmount).toFixed(2));
								 	            	$('#diffAmount_').val((0).toFixed(2));
								 	            	
								 	            	$('#mallNo_').val(rowData.mallNo);
								 	            	$('#mallName_').val(rowData.mallName);
								 	            	$('#bsgroupsNo_').val(rowData.bsgroupsNo);
								 	            	$('#bsgroupsName_').val(rowData.bsgroupsName);
								 	            	$('#organNo_').val(rowData.organNo);
													$('#organName_').val(rowData.organName);
								 	            	$('#companyNo_1').val(rowData.companyNo);
								 	            	$('#companyName_1').val(rowData.companyName);
								 	            	
								 	            	$('#thePaymentAmount_').focus();
								 	            	$('#thePaymentAmount_').select();
							 	            	}
							 	            }
				                  		}
				                  	}
								},
								{field : 'billingAmount', title : '开票金额', align:'right',width : 80, 
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'billingAmount_',name:'billingAmount'}}
								},
								{field : 'ticketDeductionAmount',title : '票后帐扣费用',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'ticketDeductionAmount_',name:'ticketDeductionAmount'}}
								},
								{field : 'receiveAmount',title : '应收款',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'receiveAmount_',name:'receiveAmount'}}
								},
								{field : 'alreadyReceiveAmount',title : '前期累计回款',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'alreadyReceiveAmount_',name:'alreadyReceiveAmount'}}
								},
								{field : 'notReceiveAmount', hidden:true,title : '未回款',width : 90,align:'right',
									editor:{type:'readonlybox',options:{id:'notReceiveAmount_', name:'notReceiveAmount'}}
								},
								{field : 'thePaymentAmount',title : '本次回款',width : 90,align:'right',
									editor:{type:'fasnumberbox',options:{required:true,precision:2, validType:['maxLength[12]'],id:'thePaymentAmount_',name:'thePaymentAmount'}}
								},
								{field : 'diffAmount',title : '应收款余额',width : 90,align:'right',
									editor:{type:'readonlybox',options:{required:false,precision:2,validType:['maxLength[12]'],id:'diffAmount_',name:'diffAmount'}}
								},
								{field : 'diffReason',title : '差异原因',width : 150,align:'left',
									editor:{type:'validatebox'}
								},
								{field : 'mallNo', hidden:true,title : '商场编码', width : 160, align:'left',
									editor:{type:'hiddenbox',options:{id:'mallNo_', name:'mallNo'}}
								},
								{field : 'mallName', title : '商场名称', width : 150,align:'left', 
									editor:{type:'searchboxname',options:{readonly:true,id:'mallName_',name:'mallName',width:'180px'}}
								},
								{field : 'bsgroupsNo',hidden:true, title : '商业集团编码', align:'left',width : 80, 
									editor:{type:'hiddenbox',options:{id:'bsgroupsNo_',name:'bsgroupsNo'}}
								},
								{field : 'bsgroupsName', title : '商业集团', align:'left',width : 90, 
									editor:{type:'searchboxname',options:{id:'bsgroupsName_',name:'bsgroupsName',readonly:true}}
								},
								{field : 'organNo', hidden:true,title : '管理城市', align:'left',width : 60, 
									editor:{type:'hiddenbox',options:{id:'organNo_', name:'organNo'}}
								},
								{field : 'organName', title : '管理城市', width : 60, align:'left',
									editor:{type:'searchboxname',options:{readonly:true,id:'organName_',name:'organName',width:'180px'}}
								},
								{field : 'companyNo',hidden:true, title : '公司编码', align:'left',width : 80, 
									editor:{type:'hiddenbox',options:{id:'companyNo_1',name:'companyNo'}}
								},
								{field : 'companyName', title : '公司', align:'left',width : 180, 
									editor:{type:'searchboxname',options:{id:'companyName_1',name:'companyName',readonly:true,width:'180px'}}
								},
								{field : 'remark',title : '备注',width : 150,align:'left',
									editor:{type:'validatebox'}
								}
							]"
							jsonExtend='{
								onDblClickRow:function(rowIndex, rowData){
									//双击方法   
									billBacksectionSplit.editRow(rowIndex,rowData);
								}
							}'
						/>
						<div data-options="region:'south',border:false">
							<#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
