<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>库存成本调整单</title>
<#include "/WEB-INF/ftl/common/header.ftl" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/cost/costImport.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_inv_cost_adjust/start_load.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_inv_cost_adjust/bill_inv_cost_adjust.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_inv_cost_adjust/operate.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
   <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'">
            <input type='hidden' id='pid' >
            <div class="easyui-layout" data-options="fit:true">
                 	<div data-options="region:'north',border:false" style="height:28px;">
						<@p.toolbar id="mainbar" listData=[
							{"id":"mian_btn_search","title":"浏览","iconCls":"icon-search","action":"ctrl.returnTabs(1)","type":0},
							{"id":"add","title":"新增","iconCls":"icon-add","action":"ctrl.add()","type":1} ,
							{"id":"save","title":"保存","iconCls":"icon-save","action":"ctrl.save()","type":7} ,
							{"id":"del","title":"删除","iconCls":"icon-del","action":"ctrl.del()","type":3} ,
							{"id":"confirm","title":"确认","iconCls":"icon-aduit","action":"ctrl.confirm()","type":18} ,
							{"id":"prev","title":"上单","iconCls":"icon-prev","action":"ctrl.prev()","type":0} ,
							{"id":"next","title":"下单","iconCls":"icon-next","action":"ctrl.next()","type":0} ,
							{"id":"export","title":"导出","iconCls":"icon-export","action":"ctrl.exportExcel()","type":4}
						 ]/>
	                </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			               <div data-options="region:'north',border:false">
		                    <div class="search-div">
		                        <#-- 主档信息  -->
		                        <form id="mainDataForm"  method="post">
		                        	 <table class="form-tb">
		                        	    <col width="80px"/>
		                        	 	<col />
		                        	 	<col width="80px"/>
		                        	 	<col />
		                        	 	<col width="80px"/>
		                        	 	<col />
		                                <tbody>
		                                    <tr>
		                                        <th>单据编号：</th>
		                                        <td><input class="readonly ipt" name="billNo" id="billNo" data-options=""/></td>
		                                        <th>单据状态：</th>
												<td><input  class='ipt' disabled name="status" id="status"  /></td>  
												<th><span class="ui-color-red">*</span>调整年份：</th>
		                                        <td>
		                                        	<input class="easyui-combobox ipt" id="year" name="year" required="true"/>
		                                        </td>
		                                    </tr>
		                                    <tr>
		                                    	<th><span class="ui-color-red">*</span>调整月份：</th>
		                                        <td><input class="easyui-combobox ipt" name="month" id="month" required="true"/></td>	
		                                        <th><span class="ui-color-red">*</span>公司名称：</th>
		                                        <td colspan="3">
		                                        	<input class="ipt easyui-company"  name="companyName" id="companyName" data-options="inputWidth:170,required:true"/>
													<input type="hidden"  name="companyNo" id="companyNo" 	/>	
		                                        </td>
		                                    </tr>
		                                    <tr>
		                                        <th>备注：</th>
		                                        <td colspan="7"><input class="easyui-validatebox ipt" style="width:99%" name="remark" id="remark" data-options=""/></td>
		                                    </tr>
		                                </tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                 <div data-options="region:'center',border:false" style="height:350px;">
		                	<div class="easyui-layout" data-options="fit:true,border:false">
		                    <div data-options="region:'north',border:false">
		                    	<@p.toolbar id="toolbar3" listData=[
									 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "ctrl.addDetail()", "type":2},
						             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "ctrl.deleteDtl()","type":2}
						             {"id":"btn-import","title":"明细导入","iconCls":"icon-import","action":"ctrl.importDetail()","type":2}
						           ]
								/>
		                    </div>
		                     <div data-options="region:'center',border:false">
		                    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
								isHasToolBar="false" divToolbar="" height="387"    pageSize="500" 
								onClickRowEdit="true" onClickRowAuto="false" pagination="true" rownumbers="true"
								columnsJsonList="[ {
										field : 'itemCode',
										title : '商品编码',
										width : 120,
										editor : {
											type : 'itemEditor',
											options : {
												required : true,
											    tipPosition:'none',
												clickFn : function(data) {
													billInvCostAdjust.clickItemFn(data);
												}
											}
										}
									}, {
										field : 'itemName',
										title : '商品名称',
										width : 140,
										align : 'left',
										editor : {
											type : 'readonlytext',
										}
									}, {
											title : 'itemNo',
											field : 'itemNo',
											hidden : true,
											notexport : true,
											editor : {
												type : 'readonlytext',
											}
										},
										{
											field : 'brandNo',
											title : '品牌编码',
											width : 80,
											align : 'left',
											editor : {
												type : 'readonlytext',
											}
										},
										{
										field : 'sizeKind',
										title : '尺寸分类',
										width : 90,
										align : 'left',
										editor : {
											type : 'readonlytext'
										}
									},{
										field : 'adjustCost',
										title : '调整的成本',
										width : 120, 
										align : 'right',
										editor : {
											type : 'numberbox',
											options : {
												required : true,
											    tipPosition:'none',
												min:0,
												precision:4,
												onChange : function() { 
						 							billInvCostAdjust.invCostAdjustChange($(this).val());
						 						}
											}
										}
									}, {
										field : 'unitCost',
										title : '调整前成本',
										width : 120,
										align : 'right',
										editor : {
											type : 'readonlytext'
										}
									}, {
										field : 'closingQty',
										title : '当前调整的数量',
										width : 120,
										align : 'right',
										editor : {
											type : 'readonlytext'
										}
									}, {
										field : 'closingAmount',
										title : '系统当前调整的余额',
										width : 120,
										align : 'right',
										editor : {
											type : 'readonlytext'
										}
									}, {
										field : 'adjustAmount',
										title : '调整后的余额',
										hidden : true,
										width : 120,
										align : 'right',
										editor : {
											type : 'readonlytext'
										}
									}, {
										field : 'diverAmount',
										title : '库存成本调整差异',
										width : 120,
										align : 'right',
										editor : {
											type : 'readonlytext'
										}
									}]" 
								jsonExtend="{onDblClickRow:function(rowIndex,rowData){
									billInvCostAdjust.selectItemHandel(rowIndex, rowData);
									}}" />
			                </div>
			                </div>
		                </div>
		              <div data-options="region:'south',border:false">
                   		 <div id="bottom" style="padding:1px;vertical-align:middle;background:#F4F4F4;height:25px;"></div>
                	</div>
             	</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>