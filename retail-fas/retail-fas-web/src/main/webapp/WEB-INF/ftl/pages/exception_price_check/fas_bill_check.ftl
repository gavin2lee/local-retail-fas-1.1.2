<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar-query" listData=[
			 {"id":"btn-search-fas","title":"查询","iconCls":"icon-search","type":0},
             {"id":"btn-remove-fas","title":"清空","iconCls":"icon-empty","type":0},
             {"id":"btn-update-fas","title":"异常价格更新","iconCls":"icon-build-some","type":89},
             {"id":"btn-export-fas","title":"导出","iconCls":"icon-export", "type":4}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<#--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="billSearchForm" id="billSearchForm" method="post">
						<table class="form-tb">
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<col width="100" />
							<col />
							<tbody>
								<tr>
									<th><span class="ui-color-red">*</span>异常类型： </th>
	                     			<td>
	                     				<input class="easyui-combobox ipt" name="billBalanceType" id="billBalanceType" 
	                     				data-options="required:true"/>
	                     			</td>
	                     			<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</th>
	                     			<td>
	                     				<input class="ipt easyui-company" name="companyNames" id="companyName_" 
	                     				data-options="multiple:true, required:true, inputNoField:'companyNo_', inputNameField:'companyName_'"/>
	                     				<input type="hidden" name="companyNos" id="companyNo_"/>
									</td>
	                     			<th><span class="ui-color-red">*</span>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
	                     			<td>
	                     				<input class="ipt easyui-brand" name="brandNames" id="brandNames_" 
	                     				data-options="multiple: false, required:true, inputNoField:'brandNos_', inputNameField:'brandNames_'"/>
	                     				<input type="hidden" name="brandNos" id="brandNos_"/>
									</td>
									<th><span class="ui-color-red">*</span>单据日期：</th>
						    		<td>
						    			<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/>
						    		</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;-- &nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> 
										<input class="easyui-datebox ipt"  name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="billDataGridDiv" loadUrl="" saveUrl="" defaultColumn="" title=""
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" pageSize="20"
			           columnsJsonList="[
						  {field : 'itemCode',title : '商品编码',width : 150,align:'left',halign:'center'},
						  {field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},
						  {field : 'brandName',title : '品牌',width : 100,align:'center',halign:'center'},
						  {field : 'priceZone',title : '价格大区',width : 80,align:'center',halign:'center'},
						  {field : 'supplierNo',title : '供应商编码',width : 100,align:'left',halign:'center'},
						  {field : 'saleDate',title : '单据日期',width : 100,align:'center',halign:'center'}
			           ]" 
                 />
			</div>
	 	</div>
	</div>
</div>