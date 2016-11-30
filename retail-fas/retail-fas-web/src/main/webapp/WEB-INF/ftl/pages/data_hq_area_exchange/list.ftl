<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>跨区调货单收发对调</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/data_hq_area_exchange/DataHqAreaExchange.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/modules/filterbuilder/js/filterbuilder.js?version=${version}"></script>
</head>
<body class="easyui-layout">
<#--tabs层-->
<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false">
   <div data-options="title:'调货出库单'">
     	<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar-region">
	           <@p.toolbar id="toolbar-query" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dataHqArea.search()", "type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"dataHqArea.clear()", "type":0},
		             {"id":"btn-split","title":"调整","iconCls":"icon-redo", "action":"dataHqArea.showSettlePath()", "type":0}
		           ]
				/>
	        </div>
	        
	        <div data-options="region:'center',border:false" style="height:200px;">		
	           <div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
						<div class="search-div">
					      	<form name="querySearchForm" id="querySearchForm" method="post">
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
											<td><input class="easyui-billtype ipt" name="fasBillName" id="billTypeName" 
													data-options="multiple:true,width:160,billCodes:'FG13710403,FG13710404,FG13710503,FG13710504,FG13710401,FG13710501,FG13710405,FG13710505'"/>
												<input type="hidden" name="fasBillCodes" id="billTypeNo" />
											</td>
											<th>发方公司： </th>
											<td>
												<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
												data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'salerNoCondition',inputNameField:'salerNameId',multiple:true,inputWidth:160"/>
												<input type="hidden" name="salerNos" id="salerNoCondition" />
											</td>
								    		<th>收方公司： </th>
											<td>
												<input class="easyui-company ipt"  name="buyerName" id="buyerNameId" 
												data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'buyerNoCondition',inputNameField:'buyerNameId',multiple:true,inputWidth:160"/>
												<input type="hidden" name="buyerNos" id="buyerNoCondition" />
											</td>
											<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
											<td>
												<input class="easyui-supplier ipt"  name="supplierName" id="supplierNameId" data-options="inputNoField:'supplierNoCondition',inputNameField:'supplierNameId',multiple:true,inputWidth:160"/>
												<input type="hidden" name="supplierNos" id="supplierNoCondition" />
											</td>
											
										</tr>
										<tr>
											<th><span class="ui-color-red">*</span>发货日期： </th>
											<td>
												<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width:150px;" readonly="true" />
											</td>
											<th>至： </th>
											<td>
												<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width:150px;"  readonly="true"/>
											</td>
											<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
											<td>
											<input class="easyui-brand ipt"  name="brandName" id="brandNameId" data-options="inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:160"/>
												<input type="hidden" name="brandNos" id="brandNoId"/>
											</td>
											<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
											<td>
												<input class="easyui-combobox"   name="oneLevelCategoryName" id="oneLevelCategoryNameId" 
												data-options="width:160,url: BasePath + '/category/get_biz?levelid=1&status=1',valueField : 'categoryNo',textField : 'name',multiple:true,
													onChange: function(rec){
														var val = $('#oneLevelCategoryNameId').combobox('getValues').join(',');
														$('#oneLevelCategoryNoId').val(val);
													}"/>
												<input type="hidden" name="categoryNos" id="oneLevelCategoryNoId"/>
											</td>
										</tr>
										<tr>
											<th>单据编号： </th>
											<td>
												<input class="easyui-validatebox ipt"  name="originalBillNos" id="originalBillNo" style="width: 150px;"/>
											</td>
								    		<th>商品编码： </th>
											<td>
												<input class="easyui-item" name="itemName" id="itemNameId" 
												data-options="inputCodeField:'itemCodeId',inputNameField:'itemNameId',inputWidth:160"/>
												<input type="hidden" name="itemCode" id="itemCodeId"/>
											</td>
											<th>调出货管单位： </th>
											<td>
												<input class="easyui-orderUnit ipt"   name="orderUnitNameFrom" id="orderUnitNameFromId" 
												data-options="inputNoField:'orderUnitNoFromId',inputNameField:'orderUnitNameFromId',inputWidth:160"/>
												<input type="hidden" name="orderUnitNoFrom" id="orderUnitNoFromId"/>
											</td>
											<th>调入货管单位：</th>
											<td>
												<input class="easyui-orderUnit ipt"   name="orderUnitName" id="orderUnitNameId" 
												data-options="inputNoField:'orderUnitNoId',inputNameField:'orderUnitNameId',inputWidth:160"/>
												<input type="hidden" name="orderUnitNo" id="orderUnitNoId" />
											</td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
					</div>
					
					<div data-options="region:'center',border:false">
		        		<@p.datagrid id="queryDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
			              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="true" selectOnCheck="true"
				           rownumbers="true" singleSelect="false"  pageSize="20" showFooter="true"
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
			                  {field : 'cost', title : '单价', width : 100}
			               ]" 
	                 />
					</div>
			   </div>					
	        </div>
	        
     	</div>
    </div>
</div>	 

    <div id="billTypeChoosePanel" class="easyui-dialog" data-options="closed:true">
	 	<div class="easyui-layout" data-options="fit:true">
	 		<div data-options="region:'center',border:false" >
		 		<div class="easyui-layout" data-options="fit:true" style="margin-top: 15px">
		 			<form name="billTypeChooseForm" id="billTypeChooseForm" method="post">
			 			<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th><input type="radio" name="changeBillType" value="-1" checked="checked" id="type1"/></th>
									<td><label for="type1">总部至地区-冲抵</label>
									</td>
									<th><input type="radio" name="changeBillType" value="0" id="type2"/></th>
									<td><label for="type2">地区至总部-正常</label>
									</td>
								</tr>
								<tr>
									<th><input type="radio" name="changeBillType" value="-2" id="type3"/></th>
									<td><label for="type1">地区至总部-冲抵</label>
									</td>
									<th><input type="radio" name="changeBillType" value="2" id="type4"/></th>
									<td><label for="type2">总部至地区-正常</label>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
	 		</div>
	 	</div>
	</div>
</body>
</html>