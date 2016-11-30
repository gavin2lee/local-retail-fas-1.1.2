<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title>采购入库调整单</title>
<#include "/WEB-INF/ftl/common/header.ftl" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/common_util/pe_util.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_purchase_adjust/billPurchaseAdjustBak.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true" >
        <div data-options="title:'单据明细'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="toor_bar" listData=[
                    	    {"id":"mian_btn_search","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据列表')","type":0},
							{"id":"add","title":"新增","iconCls":"icon-add","action":"billPurchaseAdjustBak.initAdd()","type":1} ,
							{"id":"save","title":"保存","iconCls":"icon-save","action":"billPurchaseAdjustBak.save()","type":7} ,
							{"id":"del","title":"删除","iconCls":"icon-del","action":"billPurchaseAdjustBak.del()","type":3} ,
							{"id":"confirm","title":"确认","iconCls":"icon-aduit","action":"billPurchaseAdjustBak.operate(1)","type":18} ,
							{"id":"reconfirm","title":"反确认","iconCls":"icon-cancel","action":"billPurchaseAdjustBak.operate(0)","type":10},
							{"id":"prev","title":"上单","iconCls":"icon-prev","action":"billPurchaseAdjustBak.upBill()","type":0} ,
							{"id":"next","title":"下单","iconCls":"icon-next","action":"billPurchaseAdjustBak.downBill()","type":0} 
						 ]/>	
                    </div>
                    <div data-options="region:'center',border:false">
                    	<div class="easyui-layout" data-options="fit:true" id="subLayout">
			                <div data-options="region:'north',border:false">
		                	 	 <div class="search-div" data-options="fit:true">
			                        <#-- 主档信息  -->
			                        <form id="mainDataForm"  method="post">
			                        	 <input id="id" hidden="true" name ="id"/>
			                        	 <input id="balanceNo" hidden="true" name ="balanceNo"/>
			                        	 <table class="form-tb">
											<col width="80" />
										    <col  />
										    <col width="80" />
										    <col />
										    <col width="80" />
										    <col />
										    <col width="80"/>
										    <col />
			                               <tbody>
			                               		<tr>
			                                        <th>单据编号：</th>
			                                        <td><input class="ipt disableEdit" name="billNo" id="billNo"/></td>	
			                                        <th>单据状态：</th>
			                                        <td><input class="ipt disableEdit" name="statusStr"/><input type="hidden" id="status" name="status"/></td>	
			                                        <th><span class="ui-color-red">*</span>单据日期：</th>
			                                        <td><input class="ipt easyui-datebox easyui-validatebox"  name="purchaseDate"/></td>	
			                                    </tr>
			                                    <tr>
			                                        <th><span class="ui-color-red">*</span>供应商：</th>
			                                        <td><input class="ipt" singleSearch="supplier" name="supplierName" data-options="required:true"/><input type="hidden" name="supplierNo"/></td>	
			                                        <th><span class="ui-color-red">*</span>总部公司：</th>
			                                        <td><input class="ipt" singleSearch="company" notGroupLeadRole name="salerName" data-options="required:true"/><input type="hidden" name="salerNo"/></td>	
			                                        <th><span class="ui-color-red">*</span>地区公司：</th>
			                                        <td><input class="ipt" singleSearch="company" name="buyerName" data-options="required:true"/><input type="hidden" name="buyerNo"/></td>	
			                                        <th><span class="ui-color-red">*</span>到期日：</th>
			                                        <td><input class="ipt easyui-datebox easyui-validatebox" name="dueDate"/></td>	
			                                    </tr>
			                                    <tr>
			                                    	<th>备注：</th>
			                                        <td colspan ="7"><input class="easyui-validatebox  ipt" id="remark" name="remark" style="width:99%"/></td>	 
		                                    	</tr>
			                                </tbody>
			                            </table>
									 </form>
			                    </div>
			               	</div>
			                <div data-options="region:'center',border:false" style="height:350px;">
		                	 	<div class="easyui-layout" data-options="fit:true,border:false">
								    <div data-options="region:'north',border:false" >
								    	<@p.toolbar id="dtl_toor_bar" listData=[
											{"id":"foot_btn_pre","title":"新增明细","iconCls":"icon-add-dtl","action":"billPurchaseAdjustBak.addDtl()","type":40} ,
											{"id":"foot_btn_next","title":"删除明细","iconCls":"icon-del-dtl","action":"billPurchaseAdjustBak.delDtl()","type":43},
										 	{"id":"foot_btn_import","title":"导入明细","iconCls":"icon-import","action":"billPurchaseAdjustBak.doImport()","type":47}
										 ]/>
								    </div>
								    <div data-options="region:'center',border:false" id="dtlDGTab">
								    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
										isHasToolBar="false" divToolbar=""    pageSize="500" 
										onClickRowAuto="false" 
										columnsJsonList="[
											{field:'ck',checkbox:true,notexport:true},
											{field:'itemNo',title:'商品ID',width:150,hidden:'true',editor:{type:'readonlytext'}},
											{field:'itemCode',title:'商品编码',width:120, editor:{type:'test_combogrid',options:{type:'item',required:true,callback:billPurchaseAdjustBak.itemCallBack}}},	
											{field:'itemName',title:'商品名称',width:150,editor:{type:'readonlytext'}},
											{field:'brandNo',hidden:'true',title:'品牌',width:100,notexport:true, editor:{type:'readonlytext'}},
											{field:'brandName',title:'品牌',width:100,editor:{type:'readonlytext'}},
											{field:'categoryNo',title:'大类编码',width:100,hidden:'true',editor:{type:'readonlytext'}},
											{field:'categoryName',title:'大类名称',width:100,hidden:'true',editor:{type:'readonlytext'}},
											{field:'supplierAmount',title:'厂商额',width:120,editor:{type:'numberbox',options:{required:true,precision:2,validType:'maxLength[20]'}}},
											{field:'referAmount',title:'中间额',width:120,editor:{type:'numberbox',options:{required:true,precision:2,validType:'maxLength[20]'}}},
											{field:'amount',title:'地区额',width:120,editor:{type:'numberbox',options:{required:true,precision:2,validType:'maxLength[20]'}}},
											{field:'remark',title:'备注',width:220,editor:{type:'validatebox'}}
											]"		
										 jsonExtend="{onDblClickRow:function(rowIndex,rowData){
										 	billPurchaseAdjustBak.editDtl(rowIndex);
										 }}"/>
								    </div>
								</div>
		                	</div>
			            </div>
	             	</div>
	                <div data-options="region:'south',border:false">
	                    <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
	                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>