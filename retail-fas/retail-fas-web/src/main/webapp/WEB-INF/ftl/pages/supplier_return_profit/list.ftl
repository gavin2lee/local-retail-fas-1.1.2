<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>供应商返利表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/return_profit/supplierReturnProfit.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"selfDialog.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "action":"selfDialog.clear()", "type":0},
             {"id":"btn-insert","title":"新增","iconCls":"icon-add", "action":"selfDialog.add()", "type":1},
             {"id":"btn-del","title":"删除","iconCls":"icon-remove", "action":"selfDialog.del()", "type":3},
             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action":"selfDialog.save()", "type":7},
             <!--{"id":"btn-copy","title":"复制","iconCls":"icon-copy", "type":8},-->
             {"id":"btn-import","title":"导入","iconCls":"icon-import", "action":"selfDialog.importExcel()", "type":6},
             {"id":"btn-export","title":"导出","iconCls":"icon-export", "action":"selfDialog.exportExcel()", "type":4}
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
									<th>公司：</th>
							 		<td>
								 		<input class="ipt easyui-company" name="companyName" id="companyName" 
								 		data-options="inputWidth:160,inputNoField:'companyNo',inputNameField:'companyName'"/>
				      					<input type="hidden" name="companyNo" id="companyNo"/>
			      					</td>
									<th>供应商名称：</th>
				                    <td>
				                    	<input class="easyui-supplier ipt"  name="supplierName" id="supplierName"  data-options="inputNoField:'supplierNo',inputNameField:'supplierName',inputWidth:160"/>
				                   		<input type="hidden" name="supplierNo" id="supplierNo"/>
				                   	</td>
				                   	<th align="right" width="80">日期：</th>
						    		<td align="left">
						    			<input class="easyui-datebox ipt" style="width: 100px;"  name="sendDateStart" id="sendDateStart" data-options="maxDate:'sendDateEnd'"/>
										 - <input class="easyui-datebox ipt" style="width: 100px;" name="sendDateEnd" id="sendDateEnd" data-options="minDate:'sendDateStart'"/>
						    		</td>
							 	</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn=""   
		              isHasToolBar="false" onClickRowEdit="false" pagination="true"  checkOnSelect="true" selectOnCheck="true"
			           rownumbers="true" singleSelect="false" 
			           columnsJsonList="[
		                  	  {field : 'id',hidden:true,notexport:true},
		                  	  {field : 'companyName',title : '公司',width: 150,align:'left',align:'center'},
			                  {field : 'supplierName',title : '供应商',width: 150,align:'left',align:'center'},
			                  {field : 'generateDate',title : '日期',width: 100,align:'left',align:'center'},
			                  {field : 'amount',title : '返利金额',width: 100,align:'left',align:'center',exportType:'number'},
			                  {field : 'remark',title : '返利原因',width: 100,align:'left',align:'center'},
			                  {field : 'invoiceStatus',title : '是否开票',width: 100,align:'left',align:'center'},
			                  {field : 'invoiceNo',title : '发票号',width: 100,align:'left',align:'center'},
			                  {field : 'invoiceDate',title : '发票日期',width: 100,align:'left',align:'center'},
			                  {field : 'updateUser',title : '修改人',width: 100,align:'left',align:'center'},
			                  {field : 'updateTime',title : '修改时间',width: 100,align:'left',align:'center'},
			                  {field : 'typeName',title : '是否系统生成',width: 100,align:'left',align:'center'}
			              ]" 
				          jsonExtend='{}'
                 />
			</div>
	 	</div>
	</div>
	
	<!-- 新增返利-->
	<div id="genarateFormPanel" class="easyui-dialog" data-options="closed:true">
		<form name="genarateDataForm" id="genarateDataForm" method="post"  class="pd10">
			<div id="dtl_detail">
				<div id="div1" class="easyui-panel" style="width:500px;"  title="供应商" data-options="title:'识别信息',fieldset:true,cls:'mt10'">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<col width="80"/>
						<col/>
						<col width="80"/>
						<col/>
						<tr height="40">
							<th><span class="ui-color-red">*</span>公司：</th>
					 		<td>
						 		<input class="ipt easyui-company" name="companyName" id="companyName1" data-options="inputWidth:140,inputNoField:'companyNo1',inputNameField:'companyName',required:true"/>
		      					<input type="hidden" name="companyNo" id="companyNo1"/>
	      					</td>
							<th><span class="ui-color-red">*</span>供应商：</th>
		                    <td>
		                    	<input class="easyui-supplier ipt"  name="supplierName" id="supplierName1"  data-options="inputWidth:140,inputNoField:'supplierNo1',inputNameField:'supplierName',required:true"/>
		                   		<input type="hidden" name="supplierNo" id="supplierNo1"/>
		                   	</td>
						</tr>
						<tr height="40">
							<th><span class="ui-color-red">*</span>日期：</th>
				    		<td align="left">
				    			<input class="easyui-datebox ipt" name="sendDateStart" id="sendDateStart1" style="width:130px" data-options="inputWidth:120,maxDate:'sendDateEnd1',required:true"/>
				    		</td>
				    		<th>——</th>
				    		<td>
				    			<input class="easyui-datebox ipt" name="sendDateEnd" id="sendDateEnd1" style="width:130px" data-options="inputWidth:120,minDate:'sendDateStart1',required:true"/>
				    		</td>
						</tr>
						<tr height="40">
							<th><span class="ui-color-red">*</span>商品：</th>
					 		<td>
					 			<input class="ipt easyui-item" name="itemCode" id="itemCode"
					 			 data-options="inputWidth:140,inputNoField:'itemNo', inputCodeField:'itemCode', required:true"/>
					  			<input type="hidden" name="itemNo" id="itemNo"/>
					  		</td>
							<th><span class="ui-color-red">*</span>品牌:</th>
							<td>
								<input class="easyui-brand ipt"   name="brandName" id="brandName" data-options="inputWidth:140,inputNoField:'brandNo',inputNameField:'brandName', required:true"/>
							    <input type="hidden" name="brandNo" id="brandNo"/>
							</td>
						</tr>
						<tr height="40">
							<th><span class="ui-color-red">*</span>返利率(%)：</th>
							<td><input class="easyui-numberbox" min="0" max="100" missingMessage="请输入0~100之间的整数" data-options="required:true" type="text" name="returnProfitRate" id="returnProfitRate" style="width:138px" placeholder="0.00"/></td>
							<th><span class="ui-color-red">*</span>返利基数：</th>
						    <td><input class="ipx easyui-combobox" name="returnProfitType" id="returnProfitType" style="width:140px" data-options="inputWidth:140,valueField:'value',textField:'text',data:datasource.returnProfitTypes,required:true" /></td>
						</tr>
						<tr height="40">
							<th>返利原因：</th>
							<td><input type="text" name="remark" id="remark" style="width:138px"/></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
</body>
</html>