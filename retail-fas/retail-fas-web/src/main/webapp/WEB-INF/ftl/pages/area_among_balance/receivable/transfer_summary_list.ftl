<div class="easyui-layout" data-options="fit:true,border:false">
	<#--按钮-->
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.billToolBar type="area_among_contrastList"/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="mainLayout">
			<div data-options="region:'north',border:false">
				<#--搜索start-->
				<div class="search-div">
					 <form name="searchForm" id="searchForm" method="post">
					 	<input type="hidden" name="RToS" id="RToS"/>
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
									<th>调出公司： </th>
									<td>
										<input class="easyui-company ipt"  name="salerName" id="salerNameCond" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=groupLeadRole',inputNoField:'salerNoCond',inputNameField:'salerNameCond',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="salerNo" id="salerNoCond"/>
									</td>
									<th>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
									<td>
										<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameCond" 
										data-options="multiple:true,inputNoField:'brandUnitNoCond',inputNameField:'brandUnitNameCond',inputWidth:160"/>
										<input type="hidden" name="brandUnitNo" id="brandUnitNoCond"/>
									</td>
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameCond" 
										data-options="multiple:true,inputNoField:'brandNoCond',inputNameField:'brandNameCond',inputWidth:160"/>
										<input type="hidden" name="brandNo" id="brandNoCond"/>
									</td>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td>
										<input class="easyui-combobox"   name="oneLevelCategoryName" id="oneLevelCategoryNameCond" 
										data-options="width:160,url: BasePath + '/category/get_biz?levelid=1&status=1',valueField : 'categoryNo',textField : 'name',multiple:true,
											onChange: function(rec){
												var val = $('#oneLevelCategoryNameCond').combobox('getValues').join(',');
												$('#oneLevelCategoryNoCond').val(val);
											}"/>
										<input type="hidden" name="oneLevelCategoryNo" id="oneLevelCategoryNoCond"/>
									</td>
								</tr>					
								<tr>
								    <th>调入公司： </th>
									<td>
										<input class="easyui-company ipt"   name="buyerName" id="buyerNameCond" 
										data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',inputWidth:160,isDefaultData : false"/>
										<input type="hidden" name="buyerNo" id="buyerNoCond"/>
									</td>
									<th>商品编码： </th>
									<td>
										<input class="easyui-item ipt"  name="itemName" id="itemNameCond" data-options="inputCodeField:'itemCodeCond',inputNameField:'itemNameCond',inputWidth:160"/>
										<input type="hidden" name="itemCode" id="itemCodeCond" />
									</td>
									<th>日期范围： </th>
									<td>
										<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStartCond" data-options="maxDate:'sendDateEndCond'" style="width: 150px;" readonly="true"/>
									</td>
									<th>至： </th>
									<td>
										<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEndCond" data-options="minDate:'sendDateStartCond'" style="width: 150px;" readonly="true"/>
									</td>
								</tr>
								<tr>
									<th>调出管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organNoFromName" id="organNoFromNameCond" 
										data-options="inputNoField:'organNoFromCond',inputNameField:'organNoFromNameCond',inputWidth:160"/>
										<input type="hidden" name="organNoFrom" id="organNoFromCond" />
									</td>
								    <th>调出货管单位： </th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromCond" 
										data-options="inputNoField:'orderUnitNoFromCond',inputNameField:'orderUnitNameFromCond',inputWidth:160"/>
										<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromCond"/>
									</td>
									<th>调入管理城市：</th>
									<td>
										<input class="easyui-organ ipt"   name="organName" id="organNameCond" 
										data-options="inputNoField:'organNoCond',inputNameField:'organNameCond',inputWidth:160"/>
										<input type="hidden" name="organNo" id="organNoCond" />
									</td>
									<th>调入货管单位：</th>
									<td>
										<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameCond" 
										data-options="inputNoField:'orderUnitNoCond',inputNameField:'orderUnitNameCond',inputWidth:160"/>
										<input type="hidden" name="orderUnitNo" id="orderUnitNoCond" />
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="contrastDg"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			               rownumbers="true"  pageSize="20" showFooter="true"
			               frozenColumns="[
			               		  {field : 'zoneName', title : '调入大区', width : 80,align:'center',halign:'center'},
				                  {field : 'buyerName', title : '调入公司', width : 200,align:'left',halign:'center',
				                  	formatter:function(value,row){
			                  		if(row.salerName=='合计'){
			                  			return '合计';
			                  		}	
			                  		    return value;
			                  		}
			                  	  },
			               		]"
				           columnsJsonList="[
	                 			  {field : 'organName', title : '调入管理城市', width : 100,align:'center',halign:'center'},
								  {field : 'orderUnitName', title : '调入货管单位', width : 100,align:'center',halign:'center'},
	                 			  {field : 'receiveStoreName', title : '收货仓', width : 100,align:'left',halign:'center'},
								  {field : 'brandUnitName', title : '品牌部', width : 80,align:'center'},
				                  {field : 'brandName', title : '品牌', width : 100,align:'center'},	
				                  {field : 'oneLevelCategoryName', title : '大类', width : 60,align:'center'},	
								  {field : 'twoLevelCategoryName', title : '二级大类', width : 60,align:'center'},	
				                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
				                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},
				                  {field : 'cost', title : '单价', width : 80,align:'right',halign:'center'},
				                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},	
				                  {field : 'sendAmount', title : '出库金额', width : 100,align:'right',halign:'center'},
				                  {field : 'receiveQty', title : '接收数量', width : 80,align:'right',halign:'center'},	
				                  {field : 'receiveAmount', title : '已收金额', width : 100,align:'right',halign:'center'},
	               				  {field : 'organNameFrom', title : '调出管理城市', width : 100,align:'center',halign:'center'},
				                  {field : 'orderUnitNameFrom', title : '调出货管单位', width : 100,align:'center',halign:'center'},
	               				  {field : 'sendStoreName', title : '发货仓', width : 100,align:'left',halign:'center'},
				                  {field : 'salerName', title : '调出公司', width : 200,align:'left',halign:'center',
				                  	formatter:function(value,row){
			                  		if(row.salerName=='合计'){
			                  			return '';
			                  		}	
			                  		    return value;
			                  		}
				                  },
				                  {field : 'zoneNameFrom', title : '调出大区', width : 80,align:'center',halign:'center'}
				               ]" 
					      />
			</div>
		 </div>
	</div>
	
</div>
