<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算路径查询</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common_ajax.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/settle_path/settle_path.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/settle_path_query/list.json" 
					  export_url="/settle_path_query/do_fas_export"
					  export_title="结算路径信息导出"
					  export_type="complex"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" />
	<div data-options="region:'north',border:false">
    	<#-- 工具栏  -->
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
				 {"id":"btn-remove","title":"清空","iconCls":"icon-remove", "type":0},
		         {"id":"btn-export","title":"导出","iconCls":"icon-export", "type":4}
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
									<th>路径名称： </th><td><input class="ipt"  name="pathName" id="pathName" /></td>
									<th>结算公司： </th><td><input class="ipt"  name="companyName" id="companyName" /></td>
									<th>大类： </th><td><input class="ipt"  name="categoryName" id="categoryName_" /></td>
									<th>新旧款： </th>
									<td>
										<input class="easyui-combobox ipt"  name="styleNo" id="styleNo_"/>
									</td>
								</tr>
								<tr>
									<th>状态： </th>
									<td>
										<select class="easyui-combobox" name="status" style="width:130px;" id="status">
										 	 <option value=""></option>
								      	 	 <option value="1">已启用</option>
								      	 	 <option value="2">已停用</option>
								      	 </select>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div data-options="region:'center',border:false">
		    	<@p.subdatagrid id="dataGridDiv" name="name" 
		    	loadUrl="" saveUrl=""   defaultColumn=""
		    	    isHasToolBar="false"  divToolbar=""  
		    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
				    pagination="true" rownumbers="false" enableHeaderClickMenu="false"
				    pageSize="1"
		           	columnsJsonList="[
				              {field:'pathNo',title : '路径代号',width : 70, align : 'left'},
				              {field:'name',title : '路径名称',width : 100, align : 'left'},
				              {field:'billBasis',title : '单据依据',width : 80, align : 'left', formatter : settle_path.formatter.bill_basis},
				              {field:'billType',title : '单据类型',width : 80, align : 'left', formatter : settle_path.formatter.bill_type},
				              {field:'settleCategoryName',title : '大类',width : 70, align : 'left'},
				              {field:'styleName',title : '新旧款',width : 70, align : 'left',formatter : function(value){
				              																				if(value == null || value == ''){
				              																					return '全部';
				              																				}
				              																				return value;
				              																			}
				              },
				              {field:'brandName',title:'品牌',width:120, align : 'left'},
				              {field:'startDate',title:'启用日期',width:100, align : 'left'},
				  			  {field:'endDate',title : '终止日期',width : 100, align : 'left'},
				  			  {field:'auditStatus',title : '审核状态',width : 70, align : 'left', formatter : fas_common.formatter.audit},
				  			  {field:'status',title : '状态',width : 70, align : 'left', formatter : fas_common.formatter.status}
		             ]"
		             loadSubGridUrl="/settle_path_dtl/query_settle_path"
		             subPagination="false"
		             subGridColumnsJsonList="[
		             	  {field : 'pathOrder', title : '路径次序', width : 80, align : 'left'},
					      {field : 'companyNo', title : '公司编码', width : 80, align : 'left'},
					      {field : 'companyName', title : '公司名称', width : 120, align : 'left'},
					      {field : 'financialBasisText', title : '结算依据', width : 100, align : 'left'},
					      {field : 'updateTime', title : '修改日期', width : 140, align : 'left'}
		             ]" 
		        	/>
			 </div>
		</div> 
	</div>
</body>
</html>