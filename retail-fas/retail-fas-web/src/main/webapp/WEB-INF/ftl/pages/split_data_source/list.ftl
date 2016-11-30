<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>单据日志查询</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/split_data_source/split_data_source.js?version=${version}"></script>
</head>
<body>
	<@p.commonSetting search_url="/split_data_source/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" />
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0}
	           ]
		  />
	</div>
    <div data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
					<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="90" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>单据编码： </th>
									<td>
										<input class="ipt"  name="billNo" id="billNo" />
									</td>
									<th>开始日期： </th>
									<td>
										<input class="easyui-datebox ipt"  name="startDate" id="startDate" />
									</td>
									<th>结束日期： </th>
									<td>
										<input class="easyui-datebox ipt"  name="endDate" id="endDate" />
									</td>
									<th>拆单状态： </th>
									<td>
										<select class="easyui-combobox" name="status" style="width:90px;" id="status">
										 	 <option value=""></option>
								      	 	 <option value="0">成功</option>
								      	 	 <option value="1">失败</option>
								      	 </select>
									</td>
								</tr>
								<tr>
									<th colspan="2">(季节：A-春	B-夏     C-秋     D-冬)</th>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div data-options="region:'center',border:false">
		    	<@p.subdatagrid id="dataGridDiv"
		    	loadUrl="" saveUrl=""   defaultColumn="" checkOnSelect="true"
		    	    isHasToolBar="false"  divToolbar=""  
		    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
				    pagination="true" rownumbers="true" enableHeaderClickMenu="false"
		           	columnsJsonList="[
		           			  {field : 't', checkbox:true, width : 30, notexport:true},
				              {field:'billNo',title : '单据编码',width : 100, align : 'left'},
				              {field:'billBasisName',title : '单据依据',width : 90, align : 'left'},
				              {field:'supplierNo',title : '供应商编码',width : 100, align : 'left'},
				              {field:'companyNo',title : '结算公司编码',width : 90, align : 'left'},
				              {field:'itemNo',title : '货号',width : 140, align : 'left'},
				              {field:'categoryNo',title : '大类编码',width : 80, align : 'left'},
				              {field:'brandNo',title : '品牌编码',width : 80, align : 'left'},
				              {field:'brandName',title : '品牌名称',width : 80, align : 'left'},
				              {field:'years',title:'年份',width:80, align : 'left'},
				  			  {field:'season',title : '季节',width : 80, align : 'left'},
				  			  {field:'sendOutDate',title : '发货日期',width : 90, align : 'left'},
				  			  {field:'taxRate',title : '税率',width : 70, align : 'left'}
		             ]"
		             loadSubGridUrl="/split_data_source/split_dtl"
		             subPagination="false"
		             subGridColumnsJsonList="[
		             	  {field : 'billNo', title : '单据编码', width : 100, align : 'left'},
					      {field : 'salerNo', title : '卖方编码', width : 100, align : 'left'},
					      {field : 'buyerNo', title : '买方编码', width : 100, align : 'left'},
					      {field : 'sendOutQty', title : '发货数量', width : 80, align : 'left'},
					      {field : 'cost', title : '含税价格', width : 80, align : 'left'},
					      {field : 'exclusiveCost', title : '不含税价格', width : 80, align : 'left'},
					      {field : 'billFlag', title : '单据标志', width : 100, align : 'left',formatter : split_data_source.formatter.billFlag}
		             ]" 
		        	/>
			 </div>
		</div> 
	</div>
</body>
</html>