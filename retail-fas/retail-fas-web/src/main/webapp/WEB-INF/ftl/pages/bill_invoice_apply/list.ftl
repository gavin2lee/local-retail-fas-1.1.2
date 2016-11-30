<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>开票申请单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_invoice_apply/bill_invoice_apply.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_editor.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_bill.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,cls:'pd10',border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="top_bar" listData=[
							{"id":"bill-btn-add","title":"新增","iconCls":"icon-add","type":0},
							{"id":"bill-btn-save","title":"保存","iconCls":"icon-save","type":0},
							{"id":"bill-btn-prev","title":"上单","iconCls":"icon-prev","type":0} ,
							{"id":"bill-btn-next","title":"下单","iconCls":"icon-next","type":0},
							{"id":"bill-btn-audit","title":"审核","iconCls":"icon-aduit","type":0},
							{"id":"bill-btn-antiAudit","title":"反审核","iconCls":"icon-antiAduit","type":0}
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
	                        <form id="mainDataForm"  method="post">
			               		<div data-options="region:'north',border:false">
		                    		<div class="search-div">
		                        	<#-- 主档信息  -->
			                        	 <input type="hidden" name="id" id="id">
			                        	 <table class="form-tb">
			                        	 	<col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                        	 	<col width="100px"/>
			                        	 	<col />
			                                <tbody>
											   	<tr height="40">
											      <td width="110" align='right'>开票方：</td>
											      <td width="140" align='left'>
											      		<input class="easyui-validatebox readonly ipt" style="width:140px" name="companyName" id="companyName" required="true" readonly="true"/>
											      		<input type="hidden" name="companyNo" id="companyNo"/>
											      </td>
											      <td width="110" align='right'>客户编码：</td>
											       <td width="140" align='left'>
											      		<input class="easyui-validatebox readonly ipt" style="width:140px" name="customerNo" id="customerNo" required="true" readonly="true"/>
											      </td>
											   	  <td width="110" align='right'>纳税人识别号：</td>
											      <td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="taxRegistryNo" id="taxRegistryNo" readonly="true"/>
											      </td>
											   </tr>
											   <tr height="40">
											   	  <td width="110" align='right'>申请开票日期：</td>
											      <td width="140" align='left'>
											      		<input class="easyui-datebox ipt" style="width:140px" name="invoiceApplyTime" id="invoiceApplyTime"/>
											      </td>
											      <td width="110" align='right'>客户名称：</td>
											       <td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="customerName" id="customerName" readonly="true"/>
											      </td>
											   	  <td width="110" align='right'>开户行：</td>
											      <td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="bankName" id="bankName" readonly="true"/>
											      </td>
											   </tr>
											   <tr height="40">
											   	  <td width="110" align='right'>客户交票日期：</td>
											      <td width="140" align='left'>
											      		<input class="easyui-datebox ipt" style="width:140px" name="payTime" id="payTime"/>
											      </td>
											      <td width="110" align='right'>收票方名称：</td>
											       <td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="payUnit" id="payUnit" readonly="true"/>
											      </td>
											   	  <td width="110" align='right'>账号：</td>
											      <td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="bankAccount" id="bankAccount" readonly="true"/>
											      </td>
											   </tr>
											   <tr height="40">
											   	  <td width="110" align='right'>单据编码：</td>
											      <td width="140" align='left'>
											      		<input class="easyui-validatebox ipt" style="width:140px" name="billNo" id="billNo" required="true"/>
											      </td>
											      <td width="110" align='right'>发票号：</td>
											       <td width="140" align='left'>
											      		<input class="ipt" style="width:140px" name="invoiceNo" id="invoiceNo" required="true"/>
											      </td>
											   	  <td width="110" align='right'>发票类型：</td>
											      <td width="140" align='left'>
											      	<select class="easyui-combobox" name="invoiceType" style="width:140px;" id="invoiceType" required="true">
											      	 	 <option value="1">增值票</option>
											      	 	 <option value="0">普通发票</option>
											      	 </select>
											      </td>
											   </tr>
											   <tr height="40">
											   	  <td width="110" align='right'>是否预开票：</td>
											      <td width="140" align='left'>
											      	<select class="easyui-combobox" name="preInvoice" style="width:140px;" id="preInvoice" >
											      	 	 <option value="0">是</option>
											      	 	 <option value="1">否</option>
											      	 </select>
											      </td>
											   </tr>
											   <#-- 地址信息 -->
											    <tr height="40">
											   	 	<td width="110" align='right'>客户地址：</td>
											      	<td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="address" id="address" readonly="true"/>
											      	</td>
											      	<td width="110" align='right'>联系人：</td>
											      	<td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="contactName" id="contactName" readonly="true"/>
											      	</td>
											   	  	<td width="110" align='right'>联系电话：</td>
											      	<td width="140" align='left'>
											      		<input class="readonly ipt" style="width:140px" name="tel" id="tel" readonly="true"/>
											      	</td>
											   	</tr>
											   	<tr height="40">
											   	 	<td width="110" align='right'>邮寄地址：</td>
											      	<td width="140" align='left'>
											      		<input class="easyui-validatebox ipt" style="width:140px" name="mailingAddress" id="mailingAddress"/>
											      	</td>
											      	<td width="110" align='right'>快递日期：</td>
											      	<td width="140" align='left'>
											      		<input class="easyui-datebox ipt" style="width:140px" name="deliveryTime" id="deliveryTime"/>
											      	</td>
											   	  	<td width="110" align='right'>快递单号：</td>
											      	<td width="140" align='left'>
											      		<input class="ipt" style="width:140px" name="deliveryNo" id="deliveryNo" />
											      	</td>
											   </tr>
											   	<tr height="40">
											   	 	<td width="110" align='right'>接收日期：</td>
											      	<td width="140" align='left'>
											      		<input class="easyui-datebox ipt" style="width:140px" name="receiveTime" id="receiveTime"/>
											      	</td>
											      	<td width="110" align='right'>快递公司：</td>
											      	<td width="140" align='left'>
											      		<input class="ipt" style="width:140px" name="express" id="express"/>
											      	</td>
											   	  	<td width="110" align='right'>备注：</td>
											      	<td width="140" align='left'>
											      		<input class="ipt" style="width:140px" name="remark" id="remark" />
											      	</td>
											   </tr>
		                                	</tbody>
		                            	</table>
		                    		</div>
		                		</div>
		                		<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
			    					<div class="easyui-tabs" data-options="fit:true,collapsible:false">
								    	<div title="单据明细">
											<@p.toolbar id="dtltoolbar" listData=[
													 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add", "action" : "fas_common_editor.insertRow()", "type":0},
										             {"id":"btn-edit-detail","title":"修改行","iconCls":"icon-edit","action" : "fas_common_editor.editRow()", "type":0},
										             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","action" : "fas_common_editor.deleteRow()", "type":0}
										           ]
											  />
											  <@p.datagrid id="dtlDataGridDiv"
									    			loadUrl="" saveUrl=""   defaultColumn="" 
										    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false" selectOnCheck="false"
										    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
												    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
								           			columnsJsonList="[
											              {field : 'ck',checkbox:true,notexport:true},
										                  {field : 'balanceStartDate',title:'结算开始时间',width:100, editor:{type:'datebox', options:{required:true}}},
										                  {field : 'balanceEndDate',title:'结算结束时间',width:100, editor:{type:'datebox', options:{required:true}}},
										                  {field : 'brandNo',title:'品牌',width:100,formatter:function(value, row){
																														return row.brandName;
																												  }, editor:{type:'fascombobox',options:{
										                  																				id:'brandNo',
										                  																				name:'brandNo',
										                  																				url:'/brand/get_biz',
										                  																				valueField:'brandNo',
										                  																				textField:'name',
										                  																				width:100,
										                  																				onSelect:function(data){
										                  																					$('#brandName').val(data.name);
										                  																				}
										                  																			}
										                  																		}
										                  },
										                  {field : 'brandName',hidden : true, title:'品牌名称',width:120,editor:{type:'hiddenbox',
										                  																	options:{
										                  																		id:'brandName',
										                  																		name:'brandName'
																															}
																														}
										                  },
										                  {field : 'categoryNo',title:'大类',width:100, formatter:function(value, row){
																														return row.categoryName;
																												  },editor:{type:'fascombobox',options:{
										                  																				id:'categoryNo',
										                  																				name:'categoryNo',
										                  																				url:'/category/get_biz',
										                  																				valueField:'categoryNo',
										                  																				textField:'name',
										                  																				width:100,
										                  																				onSelect:function(data){
										                  																					$('#categoryName').val(data.name);
										                  																				}
										                  																			}
										                  																		}
										                  },
										                  {field : 'categoryName',hidden : true, title:'大类名称',width:120,editor:{type:'hiddenbox',
										                  																	options:{
										                  																		id:'categoryName',
										                  																		name:'categoryName'
																															}
																														}
										                  },
										                  {field : 'sendOutQty',title:'数量', width:70, editor:{type:'validatebox',options:{required:true}}},
										                  {field : 'cost',title:'单价', width:70, editor:{type:'validatebox',options:{required:true}}},
										                  {field : 'taxRate',title:'税率', width:70, editor:{type:'validatebox',options:{required:true}}}
									                 ]"   
										             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
															fas_common_editor.editRow(rowIndex, rowData);
										             }}' 
									        	/>
									        </div>
             							</div>
             						</div>
						 	</form>
		              	</div>
             		</div>
	                <div data-options="region:'south',border:false">
	                    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</body>
</html>