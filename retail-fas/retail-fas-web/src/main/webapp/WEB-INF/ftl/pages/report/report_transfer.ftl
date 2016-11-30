<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>报表</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="<@s.url "/resources/js/modules/common_util/common_util.js?version=${version}"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/js/modules/report/report_transfer.js?version=${version}"/>"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="top_bar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"report_transfer.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"report_transfer.clear()", "type":0},
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"common_util.doExport('dtlDataGrid','/report/export','总部地区调货核对表',{type:'transfer'})", "type":0}
           ]
		/>
	</div>

	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true,plain:true" id="subLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
			      		<input type="hidden" name="balanceType" value = "1"/>
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
									<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
									<td>
										<input class="easyui-brand ipt"  name="brandName" id="brandNameId" data-options="multiple:true,inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:140"/>
										<input type="hidden" name="multiBrandNo" id="brandNoId"/>
									</td>
									<th>大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类： </th>
									<td><input class="ipt" multiSearch="category"/><input type="hidden" name="multiCategoryNo"></td>
									<th>发出日期：</th>
						    		<td ><input class="easyui-datebox ipt" defaultValue="startDate" name="sendDateStart" id="sendDateStart" data-options="required:true,maxDate:'sendDateEnd'"/></td>
									<th>&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
									<td> <input class="easyui-datebox ipt" defaultValue="endDate" name="sendDateEnd" id="sendDateEnd"  data-options="required:true,minDate:'sendDateStart'"/></td>
								</tr>	
								<tr>
									<th>调出公司： </th>
									<td><input class="ipt" multiSearch="company"/><input type="hidden" name="multiSalerNo"></td>
									<th>调入公司： </th>
									<td><input class="ipt" multiSearch="company"/> <input type="hidden" name="multiBuyerNo"></td>
									<th>调出城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNoFrom"></td>
									<th>调入城市： </th>
									<td><input class="ipt" multiSearch="organ"/> <input type="hidden" name="multiOrganNo"></td>
								</tr>	
								<tr>
									<th>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
									<td><input class="ipt" multiSearch="supplier"/><input type="hidden" name="multiSupplierNo"></td>
									<th>商品编码： </th>
									<td><input class="ipt" multiSearch="item"/><input type="hidden" name="multiItemCode"></td>
								</tr>	
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
            	 	<@p.datagrid id="dtlDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" showFooter="true"
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" pageSize="20"
					           rownumbers="true"  emptyMsg=""
					           columnsJsonList="[
								             	{title : '发货方',colspan:'4'},
							                  	{title : '收货方',colspan:'4'},
							                  	{title : '商品信息',colspan:'6'},
												{title : '本月发出',colspan:'2'},
												{title : '本月发本月收',colspan:'2'},
												{title : '本月差异',colspan:'2'},
												{title : '前月发前月未收',colspan:'2'},
												{title : '前月发本月收',colspan:'2'},
												{title : '前月差异',colspan:'2'}
												],
												[
												{field : 'zoneNameFrom', title : '大区', width : 80},
								                {field : 'salerName', title : '结算公司', width : 220, align:'left'},
								                {field : 'organNameFrom', title : '管理城市', width : 80},
												{field : 'orderUnitNameFrom', title : '货管单位', width : 80},
												{field : 'zoneName', title : '大区', width : 80},
								                {field : 'buyerName', title : '结算公司', width : 220, align:'left'},
								                {field : 'organName', title : '管理城市', width : 80},
												{field : 'orderUnitName', title : '货管单位', width : 80},
												{field : 'itemCode', title : '商品编码', width : 150},
												{field : 'itemName', title : '商品名称', width : 180, align:'left'},
												{field : 'supplierName', title : '供应商', width : 180, align:'left'},
												{field : 'brandUnitName', title : '品牌部', width : 80},
												{field : 'brandName', title : '品牌', width : 80},
												{field : 'categoryName', title : '大类', width : 80},
												{field : 'currSendQty', title : '数量', width : 80},
												{field : 'currSendAmount', title : '金额', width : 100, align:'right'},
												
												{field : 'currReQty', title : '数量', width : 80},
												{field : 'currReAmount', title : '金额', width : 100, align:'right'},
												
												{field : 'currDiffQty', title : '数量', width : 80},
												{field : 'currDiffAmount', title : '金额', width : 100, align:'right'},
												
												{field : 'preSendNoReQty', title : '数量', width : 80},
												{field : 'preSendNoReAmount', title : '金额', width : 100, align:'right'},
												
												{field : 'preSendCurrReQty', title : '数量', width : 80},
												{field : 'preSendCurrReAmount', title : '金额', width : 100, align:'right'},
												
												{field : 'preDiffQty', title : '数量', width : 80},
												{field : 'preDiffAmount', title : '金额', width : 100, align:'right'}]" 
		                 	/>	
			</div>
		</div>
	</div>
</body>
</html>