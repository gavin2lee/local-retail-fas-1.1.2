<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>价格异常检查</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/exceptionPriceCheck.js?version=${version}"></script>
</head>
<input type="hidden" id ="checkType" name="checkType" value="${checkType}" />
<input type="hidden" id ="warnPostUrl" name="warnPostUrl" value="${warnPostUrl}" />
<body>
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'GMS价格异常检查'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	 <@p.toolbar id="toolbar" listData=[
							 {"id":"btn-search-gms","title":"查询","iconCls":"icon-search","type":0},
				             {"id":"btn-remove-gms","title":"清空","iconCls":"icon-empty","type":0},
				             {"id":"btn-update-gms","title":"更新异常价格","iconCls":"icon-build-some","type":89},
				             {"id":"btn-check-gms","title":"检查异常价格","iconCls":"icon-aduit","type":88}
				           	 {"id":"btn-export-gms","title":"导出","iconCls":"icon-export", "type":4}
				           ]
						/>
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="searchForm" name="searchForm"  method="post">
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
		                                    	<th><span class="ui-color-red">*</span>公司名称：</th>
												<td>
													<input class="easyui-company"  name="companyName" id="companyName" data-options="required:true,inputWidth:170"/>
													<input type="hidden"  name="companyNo" id="companyNo" 	/>
											    </td>
												<th>单据编号：</th>
			       		 						<td><input class="easyui-validatebox ipt" name="billNo" id="billNo" /></td>
	                     						<th>单据日期：</th>
			       		 						<td>
			       		 							<input class="easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/>
		       		 							</td>
		       		 							<th>&nbsp;&nbsp;&nbsp;至：&nbsp;&nbsp;&nbsp;</th>
		       		 							<td>
			       		 							<input class="easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/>
			       		 						</td>
		                                    </tr>
		                                     <tr>
	                     						<th>单据类型： </th>
	                     						<td><input class="easyui-combobox ipt" name="billType" id="billType" data-options="width:170,multiple:true"/></td>
												<th>商品编码：</th>
			       		 						<td>
			       		 							<input class="easyui-item" name="itemCode" id="itemCodeCondition" data-options="inputWidth:130"/>
  													<input type="hidden" name="itemNo" id="itemNo"/>
  												</td>
												<th></th>
						       		 			<td></td>
						       		 			<th></th>
						       		 			<td></td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div data-options="region:'center',border:false" style="height:350px;">
		                	 <@p.subdatagrid id="dataGridDiv" loadUrl="" saveUrl="" defaultColumn="" title=""
						              isHasToolBar="false" onClickRowEdit="false" pagination="true" checkOnSelect="true" selectOnCheck="true"
							           rownumbers="true" singleSelect="false"  pageSize="20"
							           columnsJsonList="[
							                    {field : 't',checkbox:true,width:30, notexport:true},
							                    {field : 'id',hidden:'true',align:'center',notexport:true},
							                    {field : 'exceptionReason',title : '异常原因',width : 130,align:'left',halign:'center',styler:changeColor},
							                    {field : 'billNo',title : '单据编码',width : 130,align:'left',halign:'center'},
							                    {field : 'billType',hidden : 'true',width : 120,align:'left',halign:'center',notexport:true},
												{field : 'billTypeName',title : '单据类型',width : 120,align:'left',halign:'center'},
												{field : 'billDate',title : '单据日期',width : 100,align:'center',halign:'center'},
												{field : 'supplierNo',hidden : 'true',width : 80,align:'left',halign:'center',notexport:true},
												{field : 'orderUnitNo',title : '货管单位编码',width : 100,align:'left',halign:'center'},
												{field : 'itemCode',title : '商品编码',width : 150,align:'left',halign:'center'},
												{field : 'itemName',title : '商品名称',width : 180,align:'left',halign:'center'},
												{field : 'sizeNo',title : '尺码',width : 80,align:'right',halign:'center'},
												{field : 'cost',title : '单据价格',width : 100,align:'right',halign:'center'},
												{field : 'costFromName',title : '取价类型',width : 100,align:'center',halign:'center'},
												{field : 'baseCost',title : '实际价格',width : 80,align:'right',halign:'center'},
												{field : 'createTime',title : '创建时间',width : 150,align:'left',halign:'center'}
							              ]" 
								          jsonExtend='{
					                           onDblClickRow:function(rowIndex, rowData){
							                   }
						                 }'
				                 />
		                </div>
		              </div>
             	</div>
            </div>
        </div>
       </div> 
		<div id="myFormPanel" class="easyui-dialog" data-options="closed:true" > 
		<form  id="dataForm" method="post">
			<input type="hidden" id="id" name="id" />
			<table  class="form-tb">
				<col width="100" />
			    <col />
			    <tbody>
				<tr>
					<th><span class="ui-color-red">*</span>公司名称：</th>
					<td width="200px;" height="40px;">
						<input class="easyui-company"  name="companyName" id="companyName" data-options="required:true,inputWidth:150"/>
						<input type="hidden"  name="companyNo" id="companyNo" 	/>
				    </td>
				    <th>货管单位：</th>
					<td>
						<input class="easyui-orderUnit" name="orderUnitNameTemp" id="orderUnitNameTemp" 
							data-options="inputNoField:'orderUnitNoTemp',inputNameField:'orderUnitNameTemp',inputWidth:170"/>
						<input type="hidden" name="orderUnitNoTemp" id="orderUnitNoTemp"/>
					</td>
				</tr>
				<tr>
					<th>单据类型：</th>
					<td width="200px;" height="40px;">
						<input class="easyui-combobox" name="billBalanceTypeTemp" id="billBalanceTypeTemp" data-options="inputWidth:50,multiple:true"/>
				    </td>
				    <th><span class="ui-color-red">*</span>单据日期：</th>
					<td ><input class="easyui-datebox ipt" data-options="width:'80px',required:true,dateFmt:'yyyy-MM'" id="billDateTemp" />
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>