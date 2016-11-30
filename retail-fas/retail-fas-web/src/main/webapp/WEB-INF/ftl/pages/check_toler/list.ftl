<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>对账容差设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/check_toler/check_toler.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"checkTolerItem.searchData()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"checkTolerItem.searchClear()", "type":0},
             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"checkTolerItem.add()", "type":0},
             
             {"id":"btn-save","title":"保存","iconCls":"icon-save","action":"checkTolerItem.save()", "type":0},
             {"id":"btn-import","title":"导入","iconCls":"icon-export","action":"pe_util.doImport('对账容差导入.xlsx','/check_toler/do_import?type=3',1,pe_util.importCallBack)","type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"pe_util.doExport('dtlDataGrid', '/check_toler/export_data', '对账容差设置')","type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
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
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<tbody>
								<tr>
									<th>公司名称：</th>
									<td>
										<input class="easyui-company ipt" name="companyName" id="companyName"
											data-options="inputNoField:'companyNo',inputNameField:'companyName',inputWidth:180"
										/>
										<input type="hidden" name="companyNo" id="companyNo"/>
									</td>									
									<th>供应商名称：</th>
									<td>
										<input class="easyui-supplier ipt" name="supplierName" id="supplierName"
											data-options="inputNoField:'supplierNo',inputNameField:'supplierName',inputWidth:180"
										/>
										<input type="hidden" name="supplierNo" id="supplierNo"/>
									</td>	
		       		 			</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn=""   rownumbers="true"
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true"  emptyMsg = ""  pageSize="20" 
			           columnsJsonList="[
			           		  {field : 't', checkbox:true, width : 30, notexport:true,},
			           		  {field : 'companyNo', title : '公司编号', hidden:'true'},
			           		  {field : 'companyName', title : '公司', width : 240},
			           		  {field : 'supplierNo', title : '供应商编号', hidden:'true'},
			                  {field : 'supplierName', title : '供应商', width : 240},
			                  {field : 'pretaxTolerLow',title : '不含税容差金额>=',width : 160,align:'left',halign:'center'},
			                  {field : 'pretaxTolerUp',title : '不含税容差金额<=',width : 160,align:'left',halign:'center'},
			                  {field : 'aftertaxTolerLow',title : '含税容差金额>=',width : 160,align:'left',halign:'center'},
			                  {field : 'aftertaxTolerUp',title : '含税容差金额<=',width : 160,align:'left',halign:'center'},
			                  {field : 'notaxTolerLow',title : '不含税*1.17与含税容差(厂商)>=',width : 200,align:'left',halign:'center'},
			                  {field : 'notaxTolerUp',title :  '不含税*1.17与含税容差(厂商)<=',width : 200,align:'left',halign:'center'},
			                  {field : 'effectiveDate',title : '生效日期',width : 150,align:'left',halign:'center'},
			                  {field : 'createUser',title : '创建人',width : 100,align:'center'},
			                  {field : 'createTime',title : '创建时间',width : 150,align:'center'},
			                  {field : 'updateUser',title : '修改人',width : 100,align:'center'},
			                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'}
			                 ]" 
			             
                 />
			</div>
	 	</div>
	</div>
	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		<@p.datagrid id="importDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  emptyMsg = ""
	           columnsJsonList="[
	                 {field : 'index', title : '行号', width : 30},
	           		 {field : 'pass',title:'是否导入成功',width:100,formatter: function(value,row,index){
							if (value == 0){
								return '否';
							}
							return '是'
						}
			         },
			         {field : 'errorInfo',title:'错误信息',width:200,formatter : pe_util.showTips},
                     {field : 'validateObj.companyNo', title : '公司编码', width : 100},
                     {field : 'validateObj.supplierNo', title : '供应商编码', width : 100},
	 				 {field : 'pretaxTolerLow',title : '税前容差金额>=',width : 160,align:'left',halign:'center'},
			         {field : 'pretaxTolerUp',title : '税前容差金额<=',width : 160,align:'left',halign:'center'},
			         {field : 'aftertaxTolerLow',title : '税后容差金额>=',width : 160,align:'left',halign:'center'},
			         {field : 'aftertaxTolerUp',title : '税后容差金额<=',width : 160,align:'left',halign:'center'},
			         {field : 'notaxTolerLow',title : '不含税*1.17与含税容差(厂商)>=',width : 200,align:'left',halign:'center'},
                     {field : 'notaxTolerUp',title :  '不含税*1.17与含税容差(厂商)<=',width : 200,align:'left',halign:'center'}
	 				 ]"	
         />
     </div>	
</body>
</html>