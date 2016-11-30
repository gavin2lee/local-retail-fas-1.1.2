<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
		  <@p.toolbar id="subToolbar"  listData=[
		    	{"title":"查询","iconCls":"icon-search","action":"oweTheGuest.searchSum()", "type":0},
		        {"title":"清空","iconCls":"icon-empty","action":"oweTheGuest.clearData()", "type":0},
		        {"title":"导出","iconCls":"icon-export","action":"oweTheGuest.doExport('contrastDg','/owe_the_guest/export','前期欠客本期发出明细表','earlier')", "type":4}
   		  ]/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="mainLayout">
			<div data-options="region:'north',border:false">
				<div class="search-div">
					 <form name="searchForm" id="searchForm" method="post">
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
									<th><span class="ui-color-red">*</span>日期范围： </th>
									<td>
										<input class="easyui-datebox ipt"  name="dateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"  readonly="true"/>
									</td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-color-red">*</span>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="dateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" readonly="true"/>
									</td>
									<th><span class="ui-color-red">*</span>公司名称：</th>
									<td>
									    <input class="easyui-company ipt" name="companyName" id="companyNameId"  
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130,required:true"/>
										<input type="hidden" name="companyNo" id="companyNoId"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" 
										data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:130"/>
										<input type="hidden" name="brandNo" id="brandNoId"/>
									</td>
								</tr>	
								<tr>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</th>
									<td>
										<input class="easyui-categorybox"   name="categoryNo"  data-options="width:130"/>
									</td>
								    <th>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺：</th>
									<td>
                        				 <input id="shopNameId"/>
                        				 <input type="hidden" name="shopNo" id="shopNoId"/>
									</td>
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameCond" data-options="inputCodeField:'itemCodeCond',inputNameField:'itemNameCond',inputWidth:130"/>
										<input type="hidden" name="itemCode" id="itemCodeCond" />
									</td>
									<th>销售单号：</th>
									<td>
										<input class="easyui-validatebox ipt" name="orderNo" id="orderNoCon" style="width:120px;"/>
									</td>
								</tr>							
							</tbody>
						</table>
					</form>
				</div>
			</div>
			
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="contrastDg"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
		               rownumbers="true"  pageSize="20" showFooter="true"
		                    rowStyler="function(index,row){
							     if (row.earlyOweCurrSendQty == 0){
							         return 'background-color:#AFEEEE;';
							     }
					 		  }" 
				           columnsJsonList="[
							  {field : 'shopNo', title : '店铺编码', width : 80,align:'left',halign:'center'},
			           		  {field : 'shopName', title : '店铺名称', width : 135,align:'left',halign:'center'},
			           		  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center',halign:'center'},
			                  {field : 'brandName', title : '品牌', width : 80,align:'center',halign:'center'},
			                  {field : 'categoryName', title : '大类', width : 80,halign:'center'},
			                  {field : 'itemCode',title : '商品编码',width : 135,align:'left',halign:'center'},
			                  {field : 'itemName',title : '商品名称',width : 150,align:'left',halign:'center'},
			                  {field : 'sizeNo',title : '尺码',width : 60,align:'right',halign:'center'},
			                  {field : 'unitCost', title : '成本', width : 80,align:'right',halign:'center'},
               				  {field : 'saleDate', title : '销售日期', width : 100,align:'center',halign:'center'},
               				  {field : 'saleQty', title : '销售数量', width : 80,align:'right',halign:'center'},
               				  {field : 'unitCostSum', title : '成本总额', width : 100,align:'right',halign:'center'},
			                  {field : 'sendDate', title : '发出日期', width : 100,align:'center',halign:'center'},
			                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
							  {field : 'earlyOweCurrSendQty', title : '前期欠客本期发出数量', width : 150,align:'right',halign:'center'},
			                  {field : 'organName', title : '管理城市', width : 80,align:'center'},
			                  {field : 'busiCityName', title : '经营城市', width : 80,align:'center'},
							  {field : 'companyName', title : '公司名称', width : 200,align:'left',halign:'center'},
							  {field : 'orderNo', title : '销售单号', width : 150,align:'left',halign:'center'},
  							  {field : 'refundNo', title : '退款单号', width : 150,align:'left',halign:'center'}
				            ]" 
				      />
			</div>
		 </div>
	</div>
	
</div>
