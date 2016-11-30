<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>结算路径设置</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/settle_path/SettlePath.js?version=${version}"></script>
</head>
<body>
<@p.commonSetting search_url="/list.json" 
					  save_url="/save_all" 
					  update_url="/save_all" 
					  del_url="/do_delete"
					  export_url="/do_fas_export"
					  export_title="结算路径信息导出"
					  export_type="common"
					  audit_url="/do_audit"
					  anti_audit_url="/do_audit?auditVal=0"
					  datagrid_id="dataGridDiv" 
					  search_form_id="searchForm" 
					  data_form_id="dataForm"
					  dialog_width="850" 
					  dialog_height="650"
					  primary_key="id"/>
    <div id="mainTab_" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'结算路径设置'">
            <div class="easyui-layout" data-options="fit:true">
                	<div data-options="region:'north',border:false">
                    	<@p.toolbar id="toolbar" listData=[
							 {"id":"btn-search","title":"查询","iconCls":"icon-search", "action":"dialog.search()","type":0},
							 {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()", "type":0},
					         {"id":"btn-add","title":"新增","iconCls":"icon-add", "action":"dialog.toAdd()","type":1},
			             	 {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
			             	 {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"dialog.doDel()","type":3},
					         {"id":"btn-audit","title":"审核","iconCls":"icon-aduit","action":"dialog.doAudit()","type":31},
					         {"id":"btn-anti-audit","title":"反审核","iconCls":"icon-aduit","action":"dialog.doAntiAudit()","type":32},
				             {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"dialog.exportExcel()","type":4}
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
												<th>公司名称： </th>
												<td>
													<input class="easyui-company ipt"  name="companyName" id="companyName_" 
													data-options="readonly:false, multiple: true, inputWidth:130, inputNoField:'companyNo_', inputNameField:'companyName_',isDefaultData:false"/>
													<input type="hidden" name="companyNo" id="companyNo_"/>
												</td>
												<th>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌： </th>
												<td>
													<input class="easyui-brand ipt" name="brandName" id="brandName_" data-options="multiple:true, inputWidth:130, inputNoField:'brandNo_', inputNameField:'brandName_'"/>
													<input type="hidden" name="brandNo" id="brandNo_"/>
												</td>
												<th>结算大类： </th>
												<td>
													<input class="easyui-settlecategorybox ipt"  name="settleCategoryNo" id="settleCategoryNo_" />
												</td>
												<th>新旧款： </th>
												<td>
													<input class="easyui-newstylebox ipt"  name="styleNo" id="styleNo_"  data-options="url:BasePath+'/settle_new_style/contain_all_item?status=1'"/>
												</td>
											</tr>
											<tr>
												<th>启用日期： </th>
												<td>
													<input class="easyui-datebox"  name="beginStartDate" id="beginStartDate" data-options="maxDate:'endStartDate'"/>
												</td>
												<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td>
													<input class="easyui-datebox"  name="endStartDate" id="endStartDate"  data-options="minDate:'beginStartDate'"/>
												</td>
												<th>终止日期： </th>
												<td>
													<input class="easyui-datebox"  name="beginEndDate" id="beginEndDate" data-options="maxDate:'endEndDate'"/>
												</td>
												<th>— —&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
												<td> 
													<input class="easyui-datebox"  name="endEndDate" id="endEndDate"  data-options="minDate:'beginEndDate'"/>
												</td>
											</tr>
											<tr>
											    <th>路径编码： </th>
												<td>
													<input class="ipt"  name="pathNo" id="pathNo_" />
												</td>
												<th>路径名称： </th>
												<td>
													<input class="ipt"  name="name" id="name_" />
												</td>
												<th>供应商： </th>
												<td>
													<input class="easyui-supplier ipt"  name="supplierName" id="supplierName_" 
													data-options="readonly:false, multiple: true, inputWidth:130, inputNoField:'supplierNo_', inputNameField:'supplierName_'"/>
													<input type="hidden" name="supplierNo" id="supplierNo_"/>
												</td>
												<th>审批状态： </th>
												<td>						
													<input class="easyui-combobox ipt" name="auditStatus" id="auditStatus" />		
												</td>
											</tr>
										</tbody>
		                            </table>
								 </form>
		                    </div>
		                </div>
		                <div data-options="region:'center',border:false" style="height:350px;">
		                	 <@p.subdatagrid id="dataGridDiv"
						    	loadUrl="" saveUrl=""   defaultColumn="" checkOnSelect="false"
						    	    isHasToolBar="false"  divToolbar=""  onClickRowEdit="false" singleSelect="false"
								    pagination="true" rownumbers="true" enableHeaderClickMenu="false"
						           	columnsJsonList="[
						           			  {field : 't', checkbox:true, width : 30, notexport:true},
								              {field:'pathNo',title : '路径编码',width : 100, align : 'left', sortField:'path_no',sortable:true},
								              {field:'name',title : '路径名称',width : 100, align : 'left', sortField:'name',sortable:true},
								              {field:'billTypeName',title : '单据类型',width : 125, align : 'left', sortField:'bill_type',sortable:true},
								              {field:'settleCategoryName',title : '结算大类',width : 90, align : 'center', sortField:'settle_category_no',sortable:true},
								              {field:'styleName',title : '新旧款',width : 90, align : 'center', sortField:'style_no',sortable:true, 
								              	formatter : function(value){
													if(value == null || value == ''){
														return '全部';
													}
													return value;
												}
								              },
								              {field:'brandUnitName',title:'品牌部',width:140, align : 'left',halign:'center'},
								              {field:'startDate',title:'启用日期',width:100, align : 'center', sortField:'start_date',sortable:true},
								  			  {field:'endDate',title : '终止日期',width : 100, align : 'center', sortField:'end_date',sortable:true},
								  			  {field:'auditStatusName',title : '审批状态',width : 100, align : 'center', sortField:'audit_status',sortable:true},
								  			  {field : 'createUser',title : '建档人',width : 100,align:'center',halign:'center'},
											  {field : 'createTime',title : '建档时间',width : 150,align:'center',halign:'center'},
											  {field : 'updateUser',title : '修改人',width : 100,align:'center',halign:'center'},
											  {field : 'updateTime',title : '修改时间',width : 150,align:'center',halign:'center'}
						             ]"
						             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
						           	  		  dialog.toUpdate(rowData);
						             }}' 
						             loadSubGridUrl="/settle_path_dtl/query_settle_path"
						             subPagination="false"
						             subGridColumnsJsonList="[
						             	  {field : 'pathOrder', title : '路径次序', width : 80, align : 'left',notexport:true},
									      {field : 'companyNo', title : '公司编码', width : 80, align : 'left',notexport:true},
									      {field : 'companyName', title : '公司名称', width : 120, align : 'left',notexport:true},
									      {field : 'financialBasisText', title : '结算依据', width : 100, align : 'left',notexport:true},
									      {field : 'updateTime', title : '修改日期', width : 140, align : 'center',notexport:true}
						             ]" 
					        	/>
		                </div>
		              </div>
             	</div>
             	
             	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
				    <div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataForm" id="dataForm" method="post"  class="pd10">
					     	 <div data-options="region:'north',border:false,height:140" class="pd15">
								<div id="div1" class="easyui-panel" data-options="title:'结算路径信息',fieldset:true,fit:true,collapsible:false">
									<table cellpadding="1" cellspacing="1" class="form-tb">
										<input type="hidden" name="id" id="id">
										<input type="hidden" id="organTypeNo" name="organTypeNo">
									   		<tr>
												<td width="110" align='right'>
													<span class="ui-color-red">*</span>
													路径编码：
												</td>
												<td width="120" align='left'><input class="easyui-validatebox ipt" style="width:140px;" name="pathNo" id="pathNo" data-options="required:true,validType:['unNormalData','engNum','maxLength[5]']"/></td>
												<td width="110" align='right'>
													<span class="ui-color-red">*</span>
													路径名称：
												</td>
												<td width="120" align='left'><input class="easyui-validatebox ipt" style="width:140px;" name="name" id="name" data-options="required:true,validType:['unNormalData','maxLength[20]']"/></td>
												<td width="110" align='right'>
													单据类型：
												</td>
												<td width="120" align='left'>
													 <input type="hidden" name="billBasis" id="billBasis"/> 
													 <input class="ipt" name="billTypes" id="billTypes" />
												</td>
											</tr>
											<tr>
												<td width="110" align='right'>
													<span class="ui-color-red">*</span>
													结算大类：
												</td>
												<td width="120" align='left'>
													<input class="easyui-settlecategorybox ipt"  name="settleCategoryNo" id="settleCategoryNo" data-options="required:true,width:150"/>
												</td>
												<td width="110" align='right'>
													<span class="ui-color-red">*</span>
													新旧款：
												</td>
												<td width="120" align='left'>
													 <input class="easyui-newstylebox ipt"  name="styleNo" id="styleNo" data-options="required:true,width:150,url:BasePath+'/settle_new_style/contain_all_item?status=1'"/>
												</td>
												<th width="110" align='right'>原残退厂单 ：</th>
				                                <td width="120" align='left'>
				                                	<input type="checkbox" name="returnOwnFlag" id="returnOwnFlag" value="1"/>
				                                </td>
											</tr>
											<tr>
										     	<td width="110" align='right'>起始日：</td>
										      	<td width="120" align='left'><input class="easyui-datebox ipt" style="width:140px" name="startDate" id="startDate" data-options="maxDate:'endDate'"/></td>
										     	<td width="110" align='right'>结束日：</td>
										     	<td width="120" align='left'><input class="easyui-datebox ipt" style="width:140px" name="endDate" id="endDate" data-options="minDate:'startDate'"/></td>
											</tr>
										</table>
								</div>
							</div>
							<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							    <div class="easyui-tabs" data-options="fit:true,collapsible:false" id="mainTab">
							    	<div title="结算公司">
							    		<div class="easyui-layout" data-options="fit:true">
								    		<div data-options="region:'north',border:false">
										    	 <@p.toolbar id="dtltoolbar" listData=[
														 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "action" : "editor.insertRow()", "type":0},
											             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","action" : "editor.deleteRow()", "type":0},
											             {"id":"btn-copy","title":"结算公司复制","iconCls":"icon-copy","action" : "editor.companyCopy()","type":0}
											           ]
												  	/>
											</div>
									 		<div data-options="region:'center',border:false" id="companyDataGrid">
											  	<@p.datagrid id="companyDataGridDiv"
											    	loadUrl="" saveUrl=""   defaultColumn="" 
											    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="true"
											    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
													    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
									           			columnsJsonList="[
												              {field : 'ck',checkbox:true,notexport:true},
											                  {field : 'pathOrder',title:'路径次序', width:70, align : 'right'},
											                  {field : 'companyName',title:'公司名称',width:260, align : 'left', editor:{}},
											                  {field : 'companyNo',title:'公司编码',width:100, align : 'left', editor:{type:'readonlybox',options:{
											                  																			id:'companyNo',
											                  																			name:'companyNo', 
											                  																			width:'100px'
											                  																		}
											                  																	}
											                  },
											                  {field : 'financialBasis',title :'结算依据',width:120, align : 'center', formatter:function(value, row){return row.financialBasisText;},editor:{}},
															  {field : 'financialBasisText', hidden : true,  title:'结算依据',width:120,editor:{type:'hiddenbox',
										                  																	options:{
										                  																		id:'financialBasisText',
										                  																		name:'financialBasisText'
																															}
																														}
										                      }
										                 ]"   
											             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
															editor.editRow(rowIndex, rowData);
											             }}' 
								        		/>
									        </div>
							        <div>
								</div>
							</div>
						 </form>	
				   </div>
			   </div>
			   
			   <#include  "/WEB-INF/ftl/pages/settle_path/view.ftl" >
			   
            </div>
        </div>
	</div>
</body>
</html>