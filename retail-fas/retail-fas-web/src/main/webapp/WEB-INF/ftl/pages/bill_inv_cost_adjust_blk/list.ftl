<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>巴洛克库存成本调整单</title>
<#include "/WEB-INF/ftl/common/header.ftl" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_inv_cost_adjust_blk/bill_inv_cost_adjust.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_inv_cost_adjust_blk/bill_inv_cost_adjust_dtl.js?version=${version}"></script></head>
<body>
<@p.commonSetting search_url="/bill_inv_cost_adjust_blk/list.json" 
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  export_url="/bill_inv_cost_adjust_blk/do_fas_export"
					  export_title="巴洛克库存成本调整单"
					  primary_key="id"/>
<div class="easyui-panel" data-options="fit:true,border:false">
	<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true">
		
		<div data-options="title:'主单据'">
            <div class="easyui-layout" data-options="fit:true">
            	<div data-options="region:'north',border:false">
            		<@p.toolbar id="main_bar" listData=[
					    {"id":"main_btn_add","title":"新增","iconCls":"icon-add","action":"ctrl.returnTab(1)","type":0},
						{"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
						{"id":"btn-remove","title":"清空","iconCls":"icon-empty","type":0},
						{"id":"main-btn-del","title":"删除","iconCls":"icon-del","action":"ctrl.batchDel()","type":3},		         
				        {"id":"main-btn-aduit","title":"确认","iconCls":"icon-aduit","action":"ctrl.batchConfirm()","type":18},	
						{"id":"btn-export","title":"导出","iconCls":"icon-export","type":4}
					]/>
					<div class="search-div">
				        <form name="searchForm" id="searchForm" method="post">
				            <table class="form-tb" >
							    <col width="100"/><col/>
							    <col width="100"/><col/>
							    <col width="100"/><col/>
							    <col width="100"/><col/>
							    <tbody>
					       		 	 <tr>
									    <th><span class="ui-color-red">*</span>公司名称：</th>
									 	<td>
									 		<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:170,required:true,inputNoField:'companyNo', inputNameField:'companyName'"/>
											<input type="hidden"  name="companyNo" id="companyNo" 	/>
				                         </td>
									    <th>单据编号：</th>
									    <td><input class="ipt" name="billNo" id="billNo"/></td>
									    <th>单据状态：</th>
				   						<td><input class="easyui-combobox" style="width:130px" name="status" id="status" /></td>
				   					</tr>
				   					<tr>
									    <th>调整年份：</th>
									    <td><input class="ipt" name="year" id="year"/></td>
									    <th>调整月份：</th>
				   						<td><input class="ipt" name="month" id="month" /></td>
				   					</tr>
							    </tbody>
							</table>   
				        </form>
			        </div>
            	</div>
            	<!-- 主单-->
            	<div data-options="region:'center',border:false">
					<@p.datagrid id="dataGridDiv" isHasToolBar="false" onClickRowEdit="false" singleSelect="false" pageSize="10" 
						columnsJsonList="[
							{field : 'ck',notexport : true,checkbox : true},
							{field : 'billNo',title : '单据编号',width : 140,align:'left'},
							{field : 'statusStr',title : '单据状态',width : 80,align:'left'},
							{field : 'companyNo',title : '公司编码',width : 120,align:'left'},
							{field : 'companyName',title : '公司名称',width : 180,align:'left'},
							{field : 'year',title : '调整年份',width : 80,align:'right'},
							{field : 'month',title : '调整月份',width : 80,align:'right'},
							{field : 'createUser',title : '制单人',width : 100,align:'left'},
							{field : 'createTime',title : '制单时间',width : 150},
							{field : 'auditor',title : '审核人',width : 100,align:'left'},
							{field : 'auditTime',title : '审核时间',width : 150}
						]" 
						jsonExtend='{
						onDblClickRow:function(rowIndex, rowData){
							ctrl.billList.onMainDblClickRow(rowIndex,rowData);
						}
					}'/>
				</div>
            	
            </div>
		</div>
		
	</div>
</div
</body>