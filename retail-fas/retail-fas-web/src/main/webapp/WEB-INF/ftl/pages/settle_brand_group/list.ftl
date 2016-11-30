<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>品牌组管理</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/settle_brand_group/SettleBrandGroup.js?version=${version}"></script>
</head>
<body>
<@p.commonSetting search_url="/list.json" 
			  save_url="/save_all" 
			  update_url="/save_all" 
			  del_url="/do_delete"
			  export_url="/do_fas_export"
			  export_title="品牌组信息导出"
			  export_type="common"
			  enable_url="/do_enable"
			  unable_url="/do_unable"
			  datagrid_id="dataGridDiv" 
			  search_form_id="searchForm" 
			  data_form_id="dataForm"
			  dialog_width="700" 
			  dialog_height="auto"
			  primary_key="id"/>
    <div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true" >
        <div data-options="title:'品牌组设置'">
            <div class="easyui-layout" data-options="fit:true">
            	<div data-options="region:'north',border:false">
                	 <@p.toolbar id="toolbar" listData=[
						 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
			             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()","type":0},
			             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"dialog.toAdd()", "type":1},
			             {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"dialog.toUpdate()","type":2},
			             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"dialog.doDel()","type":3},
			             {"id":"btn-enable","title":"启用","iconCls":"icon-unlock","action":"dialog.doEnable()","type":27},
			             {"id":"btn-unable","title":"停用","iconCls":"icon-lock","action":"dialog.doUnable()","type":100},
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
	                        	    <col width="80" />
	                        	 	<col />
	                        	 	<col width="80" />
	                        	 	<col />
	                        	 	<col width="80" />
									<col />
	                                <tbody>
	                                    <tr>
	                                    	<th>品牌组编码：</th><td><input class="ipt"  name="groupNoLike" id="groupNoLike" /></td>
	                                    	<th>品牌组名称：</th><td><input class="ipt"  name="name" id="name_" /></td>
	                                    	<th>状态： </th>
											<td>
												<input class="ipt easyui-statusbox"  name="status" id="status" />
											</td>
	                                    </tr>
	                                </tbody>
	                            </table>
							 </form>
	                    </div>
	                </div>
	                <div data-options="region:'center',border:false" style="height:350px;">
	                	  <@p.subdatagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
				              isHasToolBar="false"   onClickRowEdit="false" pagination="true" checkOnSelect="false" selectOnCheck="false"
					           rownumbers="true" singleSelect="true"
					           columnsJsonList="[
					                  {field : 't', checkbox:true, width : 30, notexport:true},
					                  {field : 'groupNo', title : '品牌组编码', width : 100, align : 'left', sortField:'group_no',sortable:true, seq : 1},
					                  {field : 'name', title : '品牌组名称', width : 120, align : 'left', sortField:'name',sortable:true, seq : 2},
					                  {field : 'remark', title : '备注', width : 150, align : 'left', sortField:'remark',sortable:true, seq : 4},
					                  {field:'statusName',title : '状态',width : 100, align : 'center', sortField:'status',sortable:true, seq : 3},
				  			  		  {field:'updateUser',title : '修改人',width : 100, align : 'left', sortField:'update_user',sortable:true, seq : 5},
						  			  {field:'updateTime',title : '修改时间',width : 140, align : 'center', sortField:'update_time',sortable:true, seq : 6}
					                 ]" 
						          jsonExtend='{
			                           onDblClickRow:function(rowIndex, rowData){
					                	  //双击方法
					                   	   dialog.toUpdate(rowData);
					              }}'
					              loadSubGridUrl="/settle_brand_group_rel/query"
				             	  subPagination="false"
				             	  subGridColumnsJsonList="[
					                  {field : 'brandUnitNo', title : '品牌部编码', width : 80, align : 'left', notexport:true},
					                  {field : 'brandUnitName', title : '品牌部名称', width : 120, align : 'left', notexport:true},
					                  {field:'updateUser',title : '修改人',width : 80, align : 'left', notexport:true},
						  			  {field:'updateTime',title : '修改时间',width : 130, align : 'center', notexport:true}
				             	]" 
		                 	/>
	                </div>
              	</div>
     		</div>
            <#-- 修改页面 -->
         	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
		    	<div class="easyui-layout" data-options="fit:true,border:false" >
		     		<div data-options="region:'north',border:false,height:140" class="pd15">
						<div id="div1" class="easyui-panel" data-options="title:'品牌组信息',fieldset:true,fit:true,collapsible:false">
					    	<form name="dataForm" id="dataForm" method="post" class="pd10">
								<table cellpadding="1" cellspacing="1" class="form-tb">
									<input type="hidden" name="id" id="groupId">
									<input type="hidden" id="organTypeNo" name="organTypeNo">
							   		<tr>
										<td width="110" align='right'>
											<span class="ui-color-red">*</span>
											品牌组编码：
										 </td>
										<td width="140" align='left'>
											<input class="easyui-validatebox ipt" style="width:140px;" name="groupNo" id="groupNo" data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']"/>
										</td>
										<td width="110" align='right'>
											<span class="ui-color-red">*</span>
											品牌组名称：
										</td>
										<td width="140" align='left'>
											<input class="easyui-validatebox ipt" style="width:140px;" name="name" id="name" data-options="required:true,validType:['unNormalData','maxLength[10]']"/>
										</td>
									</tr>
									<tr>
								     	<td width="110" align='right'>备注：</td>
								      	<td colspan="4">
								      		<input class="easyui-validatebox ipt" style="width:98%" name="remark" id="remark" data-options="validType:'maxLength[100]'"/>
								      	</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<div data-options="region:'center',border:false"style="padding-top:0;">
						<div class="easyui-layout" data-options="fit:true" id="subLayout">
	               			<div data-options="region:'north',border:false">
						    	<div class="search-div">
			                        <form id="dtlSearchForm" name="dtlSearchForm"  method="post">
			                        	 <table class="form-tb">
			                        	    <col width="80" />
			                        	 	<col />
			                        	 	<col width="80" />
			                        	 	<col />
			                                <tbody>
			                                    <tr>
			                                    	<th>品牌部编码：</th><td><input class="ipt" name="brandUnitNo" id="brandUnitNoCondition"/></td>
			                                    	<th>品牌部名称：</th>
			                                    	<td>
			                                    		<input class="ipt"  name="brandUnitName" id="brandUnitNameCondition"/>
			                                    		<input type="button" name="queryDtlBtn" id="queryDtlBtn" value="查&nbsp;&nbsp;询" onclick="dialog.searchDtl()"/>
			                                    		<input type="button" name="clearDtlBtn" id="clearDtlBtn" value="清&nbsp;&nbsp;空" onclick="dialog.clearDtl()"/>
			                                    	</td>
			                                    </tr>
			                                </tbody>
			                            </table>
									 </form>
								</div>
							</div>
							<div data-options="region:'center',border:false" style="padding-top:0;">
							  	 <@p.datagrid id="dtlDataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
						              isHasToolBar="false" onClickRowEdit="false" pagination="false" checkOnSelect="true" selectOnCheck="true"
							           rownumbers="true" singleSelect="false" onLoadSuccess="dialog.loadSuccess"
					           			columnsJsonList="[
								              {field : 't', checkbox:true, width : 30, notexport:true},
							                  {field : 'brandUnitNo', title : '品牌部编码', width : 100, align : 'left'},
							                  {field : 'name', title : '品牌部名称', width : 120, align : 'left'},
							                  {field : 'updateUser', title : '修改人', width : 100, align : 'left'},
							                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
						                 ]"   
						        	/>
					      	</div>
						 </div>
					</div>
					  
			   </div>
            </div>
            
            <#-- 查看页面 -->
         	<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
		    	<div class="easyui-layout" data-options="fit:true,border:false" >
		     		<div data-options="region:'north',border:false,height:140" class="pd15">
						<div id="div1" class="easyui-panel" data-options="title:'品牌组信息',fieldset:true,fit:true,collapsible:false">
					    	<form name="dataFormView" id="dataFormView" method="post" class="pd10">
								<table cellpadding="1" cellspacing="1" class="form-tb">
									<input type="hidden" name="id" id="groupId">
									  <input type="hidden" id="organTypeNo" name="organTypeNo">
							   		<tr>
										<td width="110" align='right'>
											<span class="ui-color-red">*</span>
											品牌组编码：
										 </td>
										<td width="140" align='left'>
											<input class="ipt disabled" style="width:140px;" name="groupNo" readonly="true"/>
										</td>
										<td width="110" align='right'>
											<span class="ui-color-red">*</span>
											品牌组名称：
										</td>
										<td width="140" align='left'>
											<input class="ipt disabled" style="width:140px;" name="name" readonly="true"/>
										</td>
									</tr>
									<tr>
								     	<td width="110" align='right'>备注：</td>
								      	<td colspan="4">
								      		<input class="ipt disabled" style="width:98%" name="remark" readonly="true"/>
								      	</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<div data-options="region:'center',border:false"style="padding-top:0;">
						<div class="easyui-layout" data-options="fit:true" id="subLayout">
							<div data-options="region:'center',border:false" style="padding-top:0;">
							  	 <@p.datagrid id="dtlDataGridDivView"  loadUrl="" saveUrl=""   defaultColumn=""   title=""
						              isHasToolBar="false" onClickRowEdit="false" pagination="false" checkOnSelect="true" selectOnCheck="true"
							           rownumbers="true" singleSelect="false" onLoadSuccess="dialog.loadSuccess"
					           			columnsJsonList="[
							                  {field : 'brandUnitNo', title : '品牌部编码', width : 100, align : 'left'},
							                  {field : 'brandUnitName', title : '品牌部名称', width : 120, align : 'left'},
							                  {field : 'updateUser', title : '修改人', width : 100, align : 'left'},
							                  {field : 'updateTime', title : '修改时间', width : 140, align : 'center'}
						                 ]"   
						        	/>
					      	</div>
						 </div>
					</div>
					  
			   </div>
            </div>
            
        </div>
    </div>
</body>
</html>