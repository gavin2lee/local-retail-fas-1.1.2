<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title>采购入库调整单</title>
<#include "/WEB-INF/ftl/common/header.ftl" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_purchase_adjust/start_load.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_purchase_adjust/billPurchaseAdjust.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/bill_purchase_adjust/operate.js?version=${version}"></script>
</head>
<body>
<div class="easyui-panel" data-options="fit:true,border:false">
   <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'单据明细'" >
            <input type='hidden' id='pid' >
            <div class="easyui-layout" data-options="fit:true">
                 	<div data-options="region:'north',border:false" style="height:28px;">
						<@p.toolbar id="mainbar" listData=[
							{"id":"mian_btn_search","title":"浏览","iconCls":"icon-search","action":"ctrl.returnTabs(1)","type":0},
							{"id":"add","title":"新增","iconCls":"icon-add","action":"ctrl.add()","type":1} ,
							{"id":"save","title":"保存","iconCls":"icon-save","action":"ctrl.save()","type":7} ,
							{"id":"del","title":"删除","iconCls":"icon-del","action":"ctrl.del()","type":3} ,
							{"id":"confirm","title":"确认","iconCls":"icon-aduit","action":"ctrl.confirm()","type":18} ,
							{"id":"top_btn_2","title":"反确认","iconCls":"icon-cancel","action":"billInvCostAdjust.financeAntiConfirm()","type":10},
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
				                        <tr>
				                       		<th><span class="ui-color-red">*</span>地区公司：</th>
				                            <td>
					                             <input class="easyui-company ipt"   name="buyerName" id="buyerNameCon" 		
					                             data-options="queryUrl: BasePath + '/base_setting/company/list.json?dataAccess=0&params=groupLeadRole',inputNoField:'buyerNoCon',inputNameField:'buyerNameCon',required:true,inputWidth:160,isDefaultData : false"/>
					                             <input type="hidden" name="buyerNo" id="buyerNoCon"/>
				                           	</td>
				                           	 <th><span class="ui-color-red">*</span>总部公司：</th>
				                            <td>
				                            	<input class="easyui-company ipt"  name="salerName" id="salerNameCon" 
				                            	data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',inputNoField:'salerNoCon',inputNameField:'salerNameCon',required:true,inputWidth:160,isDefaultData : false"/>
				                           		<input type="hidden" name="salerNo" id="salerNoCon"/>
				                            </td>
				                         	<th><span class="ui-color-red">*</span>供应商： </th>
											<td>
												<input class="easyui-supplier  ipt"  name="supplierName" id="supplierNameId" data-options="inputNoField:'salerNoId',required:true,inputNameField:'supplierNameId',inputWidth:120"/>
												<input type="hidden" name="supplierNo"  id="salerNoId"/>
											</td>                            
											<th><span class="ui-color-red">*</span>日期：</th>
				                            <td><input class="easyui-datebox easyui-validatebox  ipt" defaultValue="purchaseDate" id="purchaseDate" name="purchaseDate" data-options="required:true"/></td>
				                       		
				                        </tr>
				                        <tr>
				                       		<th>单据编号：</th>
	                                            <td><input type="hidden" name="id" id="id" /><input class="easyui-validatebox  ipt" disabled="disabled" name="billNo" id="billNo" style="width:90%"/></td>
	                                        <th>单据状态：</th>
												<td><input  class='ipt' name="status" id="status"/>
												</td>  
				                        	<th>备注：</th>
				                                <td ><input class="easyui-validatebox ipt"  id="remark" name="remark" style="width:210%"/></td>	 
				                        </tr>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                 <div data-options="region:'center',border:false" style="height:350px;">
		                	<div class="easyui-layout" data-options="fit:true,border:false">
		                    <div data-options="region:'north',border:false">
		                    	<@p.toolbar id="toolbar3" listData=[
									 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "ctrl.addDetail()", "type":0},
						             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "ctrl.deleteDtl()","type":0}
						           ]
								/>
		                    </div>
		                     <div data-options="region:'center',border:false">
		                    <@p.datagrid id="dtlDataGrid"  fit="true" fitColumns="false" emptyMsg=""
								isHasToolBar="false" divToolbar="" height="387"    pageSize="500" 
								onClickRowEdit="true" onClickRowAuto="false" pagination="true" rownumbers="true"
								columnsJsonList="[
							                  	  {
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
						                  {field : 'brandName',  title:'品牌名称',width:120,
						                  		editor:{
						                   			type:'readonlytext',
													}
							              },
			               					{
												field : 'brandNo',
												title : '品牌编码',
												width : 80,
												editor : {
													type : 'readonlytext',
												}
										},
										  {field : 'amount',title : '金额',width : 100,align : 'center',
										  	editor:{
				                  				type:'numberbox'
				                  				}
										  },
								          {field : 'remark',title : '备注',width: 180,align:'left',halign:'center',
								          	editor:{
				                  					type:'validatebox'
				                  				}
								          }]" 
						         jsonExtend='{
							         onDblClickRow:function(rowIndex, rowData){
						           	  		 editor.editRow(rowIndex, rowData);
					             			}
					             }'/>
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