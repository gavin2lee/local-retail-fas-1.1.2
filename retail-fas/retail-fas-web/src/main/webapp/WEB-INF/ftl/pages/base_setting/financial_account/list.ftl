<!DOCTYPE html>
<head>
    <title>公司账套维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas_common.js?version=${version}"></script>
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/base_setting/financial_account.js?version=${version}"></script>
</head>
<body class="easyui-layout">
	<@p.commonSetting search_url="/base_setting/financial_account/list.json" 
					  save_url="/base_setting/financial_account/insert" 
					  update_url="/base_setting/financial_account/update" 
					  del_url="/base_setting/financial_account/save" 
					  enable_url="/base_setting/financial_account/do_enable"
					  unable_url="/base_setting/financial_account/do_unable"
					  datagrid_id="dataGridJG" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="650" 
					  dialog_height="500"
					  primary_key="id"/>

	<div data-options="region:'north',border:false" class="toolbar-region">
		 <@p.toolbar id="toolbar" listData=[
				 {"id":"btn-search","title":"查询","iconCls":"icon-search", "type":0},
	             {"id":"btn-remove","title":"清空","iconCls":"icon-empty", "type":0},
	             {"id":"btn-add","title":"新增","iconCls":"icon-add", "type":1},
	             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","type":2},
	             {"id":"btn-del","title":"删除","iconCls":"icon-del","type":3},
	             {"id":"btn-enable","title":"启用","iconCls":"icon-lock","type":27},
	             {"id":"btn-unable","title":"停用","iconCls":"icon-unlock","type":100}
	           ]
			/>
	</div>
	 		
	<div  data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
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
		       		 			<th>公司名称：</th>
		       		 			<td>
		       		 				<input class="easyui-company ipt" name="companyName" id="companyNameCondition"
		       		 					data-options="inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:160,isDefaultData:false"
		       		 				/>
		       		 				<input type="hidden" name="companyNo" id="companyNoCondition"/>
		       		 			</td>
		       		 			<th>NC账套编号：</th>
		       		 			<td>
		       		 				<input class="easyui-validatebox ipt"  name="ncId" id="ncIdCondition"/>
		       		 			</td>
		       		 			<th>承担总部职能：</th>
		       		 			<td>
		       		 				<input class="easyui-combobox" name="groupLeadRole" id="groupLeadRoleCond" 
		       		 				data-options="valueField:'label',textField:'value',data:[{label:'1',value:'是'},{label:'0',value:'否'}],width:130"/>
		       		 			</td>
		       		 			<th></th>
		       		 			<td></td>
		       		 		</tr>
		       		 		</tbody>
		       		 	</table>
					</form>
				</div>
			</div>
			<#--列表-->
	        <div data-options="region:'center',border:false">
			      <@p.datagrid id="dataGridJG"  loadUrl="" saveUrl=""   defaultColumn="" 
			              isHasToolBar="false" onClickRowEdit="false" pagination="true" 
			               checkOnSelect="true"  rownumbers="true"   
				           columnsJsonList="[
				                  {field : 't',checkbox:true,width : 30},
				                  {field : 'id',hidden : 'true',align:'center'},
				             	  {field : 'companyNo',title : '公司编码',width : 80,align:'left',halign:'center'},
				                  {field : 'companyName',title : '公司名称',width : 200,align:'left',halign:'center'},
				                  {field : 'groupLeadRole',title : '承担总部职能',width : 100,align:'center',
				                  	 formatter:function(value,row,index){
					                  	if (row.groupLeadRole==1){
											return '是';
										} else if(row.groupLeadRole==0){
											return '否';
										}else{
											return '';
										}
				                  	}
				                  },
				                  {field : 'zoneName',title : '大区',width : 80,align:'center',halign:'center'},
				                  {field : 'priceZoneName',title : '默认价格区',width : 90,align:'center',halign:'center'},
				                  {field : 'ncId',title : 'NC账套编号',width : 100,align:'left',halign:'center'},
				                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
				                  {field : 'createUser',title : '建档人',width : 80,align:'center'}, 
				                  {field : 'createTime',title : '建档时间',width : 140,align:'center'},
				                  {field : 'updateUser',title : '修改人',width : 80,align:'center'}, 
				                  {field : 'updateTime',title : '修改时间',width : 140,align:'center'},
				                  {field : 'childCompany',title : '是否分公司',width : 80,align:'center',
				                  	 formatter:function(value,row,index){
					                  	if (row.childCompany==1){
											return '是';
										} else if(row.childCompany==0){
											return '否';
										}else {
											return '';
										}
				                  	}
				                  },
				                  {field : 'parentCompanyName',title : '父项公司',width : 200,align:'left',halign:'center'}, 
				                  {field : 'supplierName',title : '相关供应商',width : 180,align:'left',halign:'center'},
				                  {field : 'revenueJournalType',title : '结转收入凭证类别',width : 100,align:'center'}, 
				                  {field : 'costJournalType',title : '结转成本凭证类别',width : 100,align:'center'},
				                  {field : 'liabilitiesJournalType',title : '存贷负债凭证类别',width : 100,align:'center'},
				                  {field : 'internalJournalType',title : '结转对内收入凭证类别',width : 100,align:'center'},
				                  {field : 'assistProject01',title : '辅助核算类别1',width : 100,align:'center'},
				                  {field : 'assistProject02',title : '辅助核算类别2',width : 100,align:'center'},
				                  {field : 'assistProject03',title : '辅助核算类别3',width : 100,align:'center'},
				                  {field : 'assistProject04',title : '辅助核算类别4',width : 100,align:'center'},
				                  {field : 'assistProject05',title : '辅助核算类别5',width : 100,align:'center'},
				                  {field : 'assistProject06',title : '辅助核算类别6',width : 100,align:'center'},
				                  {field : 'assistProject07',title : '辅助核算类别7',width : 100,align:'center'},
				                  {field : 'assistProject08',title : '辅助核算类别8',width : 100,align:'center'},
				                  {field : 'remark',title : '备注',width : 200,align:'left',halign:'center'}
				                 ]" 
					          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
			                              $("#supplierName").supplier("clear");
			                              fas_common.loadDetail(rowData);
					                   }
			         }'/>
			</div>
		 </div>
	</div>
	
<div id="myFormPanel" class="easyui-dialog" data-options="closed:true,width:400,height:500" > 
	<form id="dataForm" method="post">
		<input type="hidden" id="id" name="id" />
		<div data-options="region:'north',border:false,height:150" class="pd15">
			<div class="easyui-panel" data-options="title:'公司账套信息',fieldset:true,fit:true,collapsible:false" >
				<table class="form-tb">
					<col width="140" />
				    <col/>
				    <col width="140" />
				    <col/>
				    <tbody>
						<tr>
							<th>
							<span class="ui-color-red">*</span>公司编码：</th>
							<td>
								<input class="easyui-validatebox" name="companyNo" id="companyNoId" style="width:130px;" />  
							</td>
							<th>
							<span class="ui-color-red">*</span>公司名称：</th>
							<td>
								<input class="easyui-company"  name="companyName" id="companyNameId"
								  data-options="required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130"/>
							</td>
						</tr>
						<tr>
							<th>
							承担总部职能：</th>
							<td>
							  <input type="radio"  name="groupLeadRole"  value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;是
						      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					          <input type="radio"  name="groupLeadRole"  value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;否
							</td>
							<th>
							<span class="ui-color-red">*</span>NC账套编码：</th>
							<td>
								<input class="easyui-validatebox"  name="ncId" id="ncId" data-options="required:true,validType:['unNormalData','engNum','maxLength[18]']" style="width:130px;"  />
							</td>
						</tr>
						<tr>
							<th>是否分公司：</th>
							<td>
								<input class="easyui-combobox" name="childCompany" id="childCompanyId" data-options="valueField:'value',textField:'text',data:ifSubCompany" style="width:130px;" />
							</td>
							<th>父项公司：</th>
							<td>
								<input class="easyui-company"  name="parentCompanyName" id="parentCompanyNameId" 
								data-options="inputNoField:'parentCompanyId',inputNameField:'parentCompanyNameId',inputWidth:130"/>
								<input type="hidden" name="parentCompany" id="parentCompanyId"/>
							</td>
						</tr>
						<tr>
							<th>相关供应商：</th>
							<td>
								<input class="easyui-supplier"  name="supplierName" id="supplierName"
								data-options="inputNoField:'supplierNo',inputNameField:'supplierName',inputWidth:130" />
								<input type="hidden" name="supplierNo" id="supplierNo"/>
							</td>
							<th>默认价格区：</th>
							<td>
								<input class="easyui-combobox"  name="priceZone" id="priceZone" style="width:130px;"
								data-options="valueField:'value',textField:'text'"/>
							</td>
						</tr>
					 </tbody>
					</table>
			   </div>
		   </div>
				   
		  <div data-options="region:'center',border:false,height:180" class="pd15">
		  	<div class="easyui-panel" data-options="title:'附加信息',fieldset:true,fit:true,collapsible:false">
				  <table  class="form-tb">
				  	<col width="140" />
				    <col />
				    <col width="140" />
				    <col />
				    <tbody>
						<tr>
							<th>结转收入凭证类别：</th>
							<td>
								<input type="easyui-validatebox" name="revenueJournalType" />
							</td>
							<th>结转成本凭证类别：</th>
							<td>
								<input type="easyui-validatebox" name="costJournalType" />
							</td>
						</tr>
						<tr>
							<th>存货负债凭证类别：</th>
							<td>
								<input type="easyui-validatebox" name="liabilitiesJournalType" />
							</td>
							<th>结转对内收入凭证类别：</th>
							<td>
								<input type="easyui-validatebox" name="internalJournalType" />
							</td>
						</tr>
						<tr>
							<th>辅助核算类别01：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject01" />
							</td>
							<th>辅助核算类别02：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject02" />
							</td>
						</tr>
						<tr>
							<th>辅助核算类别03：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject03" />
							</td>
							<th>辅助核算类别04：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject04" />
							</td>
						</tr>
						<tr>
							<th>辅助核算类别05：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject05" />
							</td>
							<th>辅助核算类别06：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject06" />
							</td>
						</tr>
						<tr>
							<th>辅助核算类别07：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject07" />
							</td>
							<th>辅助核算类别08：</th>
							<td>
								<input type="easyui-validatebox" name="assistProject08" />
							</td>
						</tr>
						<tr>
							<th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</th>
							<td colspan="3">
								<input class="easyui-validatebox" style="width:400px"  name="remark"  />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
</div>
</body>
</html>