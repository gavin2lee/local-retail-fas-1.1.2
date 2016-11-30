<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>地区价检查和更新</title>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/price_check/area_price_check.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
		<@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action" : "priceCheck.search()", "type":0},
	         {"id":"btn-clear","title":"清空","iconCls":"icon-empty", "action" : "priceCheck.clear()","type":0},
             {"id":"btn-direct","title":"更新价格","iconCls":"icon-ok", "action" : "priceCheck.costUpdate()","type":139},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export", "action" : "priceCheck.doExport('/area_price_check/export')","type":4}
	       ]
		/>
	</div>
   
	<div data-options="region:'center',border:false" >
	  <div class="easyui-layout" data-options="fit:true" id="subLayout">
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
							<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
							<td>
								<input class="easyui-company ipt"   name="saleCompanyName" id="saleCompanyNameId" 
								data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0',inputNoField:'saleCompanyNoId',inputNameField:'saleCompanyNameId',inputWidth:160,isDefaultData : false,required:true"/>
								<input type="hidden" name="saleCompanyNo" id="saleCompanyNoId"/>
							</td>
						    <th><span class="ui-color-red">*</span>品&nbsp;&nbsp;牌&nbsp;&nbsp;部： </th>
							<td>
								<input class="easyui-brandunit ipt"   name="brandUnitName" id="brandUnitNameId" data-options="multiple:true,inputNoField:'brandUnitNoId',inputNameField:'brandUnitNameId',inputWidth:160,required:true"/>
								<input type="hidden" name="brandUnitNo" id="brandUnitNoId"/>
							</td>
							<th><span class="ui-color-red">*</span>发出日期： </th>
							<td>
								<input class="easyui-datebox ipt"  name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'" style="width: 150px;" readonly="true"/>
							</td>
							<th>至： </th>
							<td>
								<input class="easyui-datebox ipt"   name="sendDateEnd" id="sendDateEnd" data-options="required:true,minDate:'sendDateStart'" style="width: 150px;" readonly="true"/>
							</td>
						</tr>
						<tr>
							<th>商品编码： </th>
							<td>
								<input class="easyui-item ipt"  name="itemName" id="itemNameId" data-options="inputCodeField:'itemCodeCondition',inputNameField:'itemNameId',inputWidth:160"/>
								<input type="hidden" name="itemCode" id="itemCodeCondition" />
							</td>
							<th>单据编号： </th>
							<td>
								<input class="easyui-validatebox ipt"   name="billNo" id="billNo" style="width: 150px;"/>
							</td>
							<th><span class="l-btn-text icon-xq l-btn-icon-left" id="columnDsc">tips</span></th>
							<td></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th><span class="ui-color-red">*</span>异常类型： </th>
							<td>
								<input type="radio" name="exceptionType" id="TypeOneId" value="1" checked="checked"/>&nbsp;&nbsp;与维护的地区价不一致
							</td>
							<th></th>
							<td>
								<input type="radio" name="exceptionType" id+"TypeTwoId" value="2"/>&nbsp;&nbsp;与出库单据地区价不一致
							</td>
							<th></th>
							<td>
								<input type="radio" name="exceptionType" id+"TypeThreeId" value="0"/>&nbsp;&nbsp;未维护地区价
							</td>
							<th></th>
							<td></td>
						</tr>						
					</tbody>
				</table>
			</form>
		 </div>
	  </div>
	  
      <div data-options="region:'center',border:false">
    	 <div class="easyui-tabs" id="mainTab" data-options="fit:true,plain:true,border:false" >
			<div title="出库单据" >
			     <@p.datagrid id="saleDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
			      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
		          	 columnsJsonList="[
	                  {field : 'salerName', title : '公司', width : 180,align:'left',halign:'center'},	
	                  {field : 'buyerName',title : '客户',width : 180,align:'left',halign:'center'},
	                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},	
	                  {field : 'cost', title : '单价', width : 90,align:'right',halign:'center'},
					  {field : 'brandUnitName', title : '品牌部', width : 100},
	                  {field : 'brandName', title : '品牌', width : 100},
	                  {field : 'sendDate', title : '发出日期', width : 100},
	                  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
	                  {field : 'billTypeName', title : '单据类型', width : 100,halign:'center'}
	                ]" 
            	 />
			</div>
			
			<div title="入库单据" >
				<@p.datagrid id="buyDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
				 onClickRowEdit="false" pagination="true" rownumbers="true"   pageSize="20" showFooter="true"
		           columnsJsonList="[
	                  {field : 'salerName', title : '公司', width : 180,align:'left',halign:'center'},	
	                  {field : 'buyerName',title : '客户',width : 180,align:'left',halign:'center'},
	                  {field : 'itemCode', title : '商品编码', width : 150,align:'left',halign:'center'},	
	                  {field : 'itemName', title : '商品名称', width : 180,align:'left',halign:'center'},	
	                  {field : 'cost', title : '单价', width : 90,align:'right',halign:'center'},
					  {field : 'brandUnitName', title : '品牌部', width : 100},
	                  {field : 'brandName', title : '品牌', width : 100},
                      {field : 'sendDate', title : '发出日期', width : 100},
                      {field : 'receiveDate', title : '接收日期', width : 100},
	                  {field : 'originalBillNo', title : '单据编号', width : 150,align:'left',halign:'center'},
	                  {field : 'billTypeName', title : '单据类型', width : 100,halign:'center'}
                  ]" 
	             />
			</div>
	   	</div>
	  </div>
	  
	</div>
  </div>
</body>
</html>