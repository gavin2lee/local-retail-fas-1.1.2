<div id="templateFormPanel" class="easyui-dialog" data-options="closed:true"> 
	<div class="easyui-layout" data-options="fit:true,border:false" >
	   	<form name="dataForm" id="templateDataForm"  method="post"  class="pd10">
 			<div data-options="region:'north',border:false,height:250" class="pd15">
				<div class="easyui-panel" data-options="title:'模板',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="5" cellspacing="5" class="form-tb">
						<tr>
							<th width="110" align='right'>
			           			<span class="ui-color-red">*</span>选择模板客户：
			           		</th>
			           		<td width="140" align='left'>
			           			<input class="ipt"  name="customerNameTemplate" id="customerNameTemplate" data-options="required:true" />
			           			<#-- <input class="easyui-wholesale_zone_template_customer ipt"  name="customerNameTemplate" id="customerNameTemplate" data-options="required:true" /> -->
			           		</td>
			           		<th width="110" align='right'>
			                	<span class="ui-color-red">*</span>客户：
			                </th>
			            	<td width="140" align='left'>
			                	<input class="easyui-wholesale_zone_select_customer ipt" name="customerNameMulti" id="customerNameMulti" data-options="multiple:'true',required:true,inputWidth:200,inputNoField:'customerInfoStr',inputNameField:'customerNameMulti'"/>
			                	<input type="hidden"  name="customerInfoStr" id="customerInfoStr"/>
			                </td>
					   </tr>
                       <tr>
                       		<th width="110" align='right'>公司：</th>
                        	<td width="140" align='left'>
                            	<input class="ipt readonly" readonly="readonly"  name="companyName" id="companyName" />
                            	<input type="hidden" name="companyNo" id="companyNo"/>
                            </td>
                            <th width="110" align='right'>模板客户： </th>
                        	<td width="140" align='left'>
                            	<input class="ipt readonly" readonly="readonly" name="customerName" id="customerName"/>
                            	<input type="hidden"  name="customerNo" id="customerNo"/>
                            </td>
                        </tr>
                        <tr>
                        	<th width="110" align='right'>条款：</th>
                        	<td width="140" align='left'>
                        		<input class="ipt readonly" readonly="readonly" name="termName" id="termName"/>
                        		<input type="hidden" name="termNo" id="termNo" />
                        	</td>
							<th width="110" align='right'>启用保证金控制：</th>
                            <td width="140" align='left'>
                            	<input readonly="readonly" type="checkbox" name="marginControlFlagText" id="marginControlFlagText" />
                            	<input type="hidden" name="marginControlFlag" id="marginControlFlag" />
                            </td>
                        </tr>
                        <tr>
                            <th width="110" align='right'>保证金额度：</th>
                            <td width="140" align='left'><input class="ipt readonly" readonly="readonly" style='width:190px' name="marginAmount" id="marginAmount" data-options="min:0,precision:2"/></td>
                            <th width="110" align='right'>保证金余额：</th>
                            <td width="140" align='left'><input class="ipt readonly" readonly="readonly" style='width:190px' name="marginRemainderAmount" id="marginRemainderAmount" data-options="min:0,precision:2"/></td>
                        </tr>
					</table>	
				</div>
			</div>
	</form>
</div>
</div>
</body>
</html>