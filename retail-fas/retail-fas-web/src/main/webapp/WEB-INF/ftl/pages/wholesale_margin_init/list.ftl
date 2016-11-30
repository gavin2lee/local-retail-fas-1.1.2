<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>客户保证金及预收款初始化</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasController.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/FasDialogController.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/list.json" 
					  save_url="/insert" 
					  update_url="/update" 
					  del_url="/save"
					  export_url="/do_fas_export"
					  export_title="客户保证金及预收款初始化信息"
					  export_type="common"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="700" 
					  dialog_height="300"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"_fasDialogController.search()", "type":0},
             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"_wholesaleMarginInit.clear()", "type":0},
             {"id":"btn-add-row","title":"新增","iconCls":"icon-add","action" : "_wholesaleMarginInit.toAdd()", "type":0},
             {"id":"btn-edit-row","title":"修改","iconCls":"icon-edit", "action" : "_wholesaleMarginInit.toUpdate()", "type":0},
             {"id":"btn-del-row","title":"删除","iconCls":"icon-del","action" : "_wholesaleMarginInit.doDel()", "type":0}
             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"_fasDialogController.exportExcel()","type":0},
             {"id":"btn-finish","title":"完成初始化","iconCls":"","action":"_wholesaleMarginInit.finishInit()","type":0}
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
	                                	<input class="easyui-company ipt" name="companyName" id="companyNameCondition" data-options="inputWidth:160,inputNoField:'companyNoCondition',inputNameField:'companyNameCondition'"/>
	                                	<input type="hidden" name="companyNo" id="companyNoCondition"/>
                                	</td>
									</td>
		                            <th>客户：</th>
		                            <td>
	                                	<input class="easyui-customer ipt" name="customerName" id="customerNameCondition" data-options="inputWidth:160,inputNoField:'customerNoCondition',inputNameField:'customerNameCondition'"/>
	                                	<input type="hidden" name="customerNo" id="customerNoCondition"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--列表-->
        	<div data-options="region:'center',border:false">
		      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
		              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
			           rownumbers="true" singleSelect="true"
			           columnsJsonList="[
			                  {field : 't', checkbox:true, width : 30, notexport:true},
			                  {field : 'companyNo',hidden : true, title:'公司编码',width:100},
			                  {field : 'companyName', title : '公司名称', width : 200, align : 'left'},
			                  {field : 'customerNo', title : '客户编码', width : 100, align : 'left'},
			                  {field : 'customerName', title : '客户名称', width : 200, align : 'left'},
			                  {field:'marginAmount',title : '合同保证金',width : 90, align : 'left'},
		  			  		  {field:'initMarginAmount',title : '初始保证金额度',width : 100, align : 'left'},
				  			  {field:'initPrePayment',title : '初始预收款',width : 90, align : 'left'},
				  			  {field:'preOrderNo',title : '预收订单号',width : 90, align : 'left'},
				  			  {field:'finishFlagText',title : '是否已完成初始化',width : 120, align : 'center'},
				  			  {field:'remark',title : '备注',width : 140, align : 'left'}
			                 ]" 
				          jsonExtend='{
	                           onDblClickRow:function(rowIndex, rowData){
			                	 _wholesaleMarginInit.toUpdate(rowData);
			              }}'
                 />
			</div>
			
			<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
			    <div class="easyui-layout" data-options="fit:true,border:false" >
				     <form name="dataForm" id="dataForm" method="post"  class="pd10">
				     	 <div data-options="region:'north',border:false,height:300" class="pd15">
							<div class="easyui-panel" data-options="title:'客户保证金及预收款初始化',fieldset:true,fit:true,collapsible:false">
								<table cellpadding="1" cellspacing="1" class="form-tb">
								   <input type="hidden" name="id" id="id">
								   <input type="hidden" name="balanceType" id="balanceType" value="7">
	                                <tr>
	                               		<th width="110" align='right'>
	                                	<span class="ui-color-red">*</span>公司：
		                                </th>
		                            	<td width="140" align='left'>
		                                	<input class="easyui-company ipt" name="companyName" id="companyName" data-options="required:true,inputWidth:200,inputNoField:'companyNo',inputNameField:'companyName'"/>
		                                	<input type="hidden" name="companyNo" id="companyNo"/>
		                                </td>
		                                <th width="110" align='right'>
		                                	<span class="ui-color-red">*</span>客户：
		                                </th>
		                            	<td width="140" align='left'>
		                                	<input class="easyui-customer ipt" name="customerName" id="customerName" data-options="required:true,inputWidth:200,inputNoField:'customerNo',inputNameField:'customerName'"/>
		                                	<input type="hidden"  name="customerNo" id="customerNo"/>
		                                </td>
	                                </tr>
	                                <tr>
	                                	<th width="110" align='right'>合同保证金 ：</th>
	                                    <td width="140" align='left'>
	                                    	<input class="readonly ipt" style='width:190px' readonly="true" name="marginAmount" id="marginAmount"/>
	                                    </td>
	                                	<th width="110" align='right'>
	                                		<span class="ui-color-red">*</span>初始保证金额度：
	                                	</th>
									    <td width="140" align='left'>
									    	<input class="easyui-validatebox easyui-numberbox ipt" style='width:190px' name="initMarginAmount"  id="initMarginAmount" data-options="required:true,min:0,precision:2"/>
									    </td>
	                                </tr>
	                                <tr>
	                                    <th width="110" align='right'>
	                                    	<span class="ui-color-red">*</span>初始预收款：
	                                    </th>
	                                    <td width="140" align='left'>
	                                    	<input class="easyui-numberbox ipt" style='width:190px' name="initPrePayment" id="initPrePayment" data-options="required:true,min:0,precision:2"/>
	                                    </td>
	                                    <th width="110" align='right'>预收订单号：</th>
	                                    <td width="140" align='left'>
	                                    	<input class="easyui-validatebox ipt" style='width:190px' name="preOrderNo" id="preOrderNo" data-options="validType:['unNormalData','engNum']"/>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th width="110" align='right'>备注 ：</th>
	                                    <td colspan ="7"><input class="ipt" name="remark" id="remark" style="width:99%"/></td>
	                                </tr>
								</table>
							</div>
						</div>
					 </form>	
			   </div>
	   		</div>
	 	</div>
	</div>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/wholesale_margin_init/WholesaleMarginInit.js?version=${version}"></script>
</body>
</html>