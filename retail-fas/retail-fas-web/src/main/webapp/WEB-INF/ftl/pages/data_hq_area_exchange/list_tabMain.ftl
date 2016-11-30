<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="toolbar-region">
		  <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dataHqArea.searchEntry()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dataHqArea.clearEntry()", "type":0}
           ]
		 />
	</div>
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" >
			<div data-options="region:'north',border:false" >
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
							<th>单据类型：</th>
							<td>
								<input class="easyui-billtype ipt" name="fasBillName" id="billTypeNameCond" 
								data-options="multiple:true,width:160,billCodes:'FG13720403,FG13720404,FG13720503,FG13720504,FG13720401,FG13720501,FG13720405,FG13720505'"/>
								<input type="hidden" name="fasBillCodes" id="billTypeCond" />
							</td>
							<th>发方公司： </th>
							<td>
								<input class="easyui-company ipt"  name="salerName" id="salerNameCond" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'salerNoCond',inputNameField:'salerNameCond',multiple:true,inputWidth:160"/>
								<input type="hidden" name="salerNos" id="salerNoCond" />
							</td>
				    		<th>收方公司： </th>
							<td>
								<input class="easyui-company ipt"  name="buyerName" id="buyerNameCond" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'buyerNoCond',inputNameField:'buyerNameCond',multiple:true,inputWidth:160"/>
								<input type="hidden" name="buyerNos" id="buyerNoCond" />
							</td>
							<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
							<td>
								<input class="easyui-supplier ipt"  name="supplierName" id="supplierNameCond" 
								data-options="inputNoField:'supplierNoCond',inputNameField:'supplierNameCond',multiple:true,inputWidth:160"/>
								<input type="hidden" name="supplierNos" id="supplierNoCond" />
							</td>
						</tr>
						<tr>
							<th><span class="ui-color-red">*</span>发货日期： </th>
							<td>
								<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStartId" data-options="required:true,maxDate:'sendDateEndId'" style="width:150px;" readonly="true" />
							</td>
							<th>至： </th>
							<td>
								<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEndId" data-options="required:true,minDate:'sendDateStartId'" style="width:150px;"  readonly="true"/>
							</td>
							<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
							<td>
							    <input class="easyui-brand ipt"  name="brandName" id="brandNameCond" 
							    data-options="inputNoField:'brandNoCond',inputNameField:'brandNameCond',inputWidth:160"/>
								<input type="hidden" name="brandNos" id="brandNoCond"/>
							</td>
							<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
							<td>
								<input class="easyui-combobox"   name="oneLevelCategoryName" id="oneLevelCategoryNameCond" 
								data-options="width:160,url: BasePath + '/category/get_biz?levelid=1&status=1',valueField : 'categoryNo',textField : 'name',multiple:true,
									onChange: function(rec){
										var val = $('#oneLevelCategoryNameCond').combobox('getValues').join(',');
										$('#oneLevelCategoryNoCond').val(val);
									}"/>
								<input type="hidden" name="categoryNos" id="oneLevelCategoryNoCond"/>
							</td>
						</tr>
						<tr>
							<th>单据编号： </th>
							<td>
								<input class="easyui-validatebox ipt"  name="originalBillNos" id="originalBillNoCond" style="width: 150px;"/>
							</td>
				    		<th>商品编码： </th>
							<td>
								<input class="easyui-item" name="itemName" id="itemNameCond" 
								data-options="inputCodeField:'itemNoCond',inputNameField:'itemNameCond',inputWidth:160"/>
								<input type="hidden" name="itemCode" id="itemNoCond"/>
							</td>
							<th>调出货管单位： </th>
							<td>
								<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromCond" 
								data-options="inputNoField:'orderUnitNoFromCond',inputNameField:'orderUnitNameFromCond',inputWidth:160"/>
								<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromCond"/>
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
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="mainDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" selectOnCheck="true" 
			               checkOnSelect="true"   rownumbers="true" singleSelect="false" showFooter="true" pageSize="20"
		                   frozenColumns="[{field : 't', checkbox:true, width : 30, notexport:true}]"
				           columnsJsonList="[
				              {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
			                  {field : 'fasBillName', title : '单据类型', width : 200,align:'left',halign:'center'},
			                  {field : 'salerNo', title : '调出公司编码',  width : 120, hidden:true,halign:'center'},
			                  {field : 'salerName', title : '调出公司',  width : 180,align:'left',halign:'center'},
			                  {field : 'orderUnitNameFrom', title : '调出货管单位', width : 150,align:'center',halign:'center'},
			                  {field : 'buyerNo', title : '调入公司编码',  width : 120, hidden:true,halign:'center'},
			                  {field : 'buyerName', title : '调入公司',  width : 180,align:'left',halign:'center'},
			                  {field : 'orderUnitName', title : '调入货管单位', width : 150,align:'center',halign:'center'},
			                  {field : 'supplierNo', title : '供应商编码',  width : 120, hidden:true,halign:'center'},
			                  {field : 'supplierName', title : '供应商',  width : 180,align:'left',halign:'center'},
			                  {field : 'brandNo', title : '品牌编号',  width : 120, hidden:true},
			                  {field : 'brandName', title : '品牌', width : 80},
			                  {field : 'oneLevelCategoryNo', title : '大类编号', width : 100, hidden:true},
			                  {field : 'oneLevelCategoryName', title : '大类', width : 50},
			                  {field : 'itemNo', title : '商品编号', width : 100, hidden:true},
			                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},
			                  {field : 'itemName', title : '商品名称',  width : 180,align:'left',halign:'center'},
			                  {field : 'sendDate', title : '发货日期', width : 100},
			                  {field : 'sendQty', title : '发出数量', width : 80,align:'right',halign:'center'},
			                  {field : 'receiveQty', title : '接收数量', width : 80,align:'right',halign:'center'},
			                  {field : 'cost', title : '单价', width : 100}
			               ]" 
			            />
			</div>
		 </div>
	</div>
</div>