<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>内购业务类型</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	   <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/inside_biz_type/insideBizType.js?version=${version}"></script>
</head>
<body class="easyui-layout">

<#--最外层框-->
<div data-options="region:'center',border:false">
<#--tool层-->
	<div id="subLayout" class="easyui-layout" data-options="fit:true,border:false">
		<#--按钮-->
		<div data-options="region:'north',border:false" class="toolbar-region">
			 <@p.toolbar id="toolbar" listData=[
					 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"dialog.search()","type":0},
		             {"id":"btn-remove","title":"清空","iconCls":"icon-empty","action":"dialog.clear()", "type":0},
		             {"id":"btn-add","title":"新增","iconCls":"icon-add","action":"insideBizTypeEditor.showDialog()", "type":1},
		             {"id":"btn-del","title":"删除","iconCls":"icon-del","action":"insideBizTypeEditor.del()","type":3},
		             {"id":"btn-enable","title":"启用","iconCls":"icon-lock","action":"dialog.doEnable()","type":27},
		             {"id":"btn-unable","title":"停用","iconCls":"icon-unlock","action":"dialog.doUnable()","type":100}
		           ]
				/>
		</div>
			
		<div data-options="region:'center',border:false" style="height:200px;">		
			<div class="easyui-layout" data-options="fit:true" id="subLayout">
				<div data-options="region:'north',border:false">
					 <#--表头-->
					<div class="search-div">
				     	 <#-- 主档信息  -->
	                       <form name="mainDataForm" id="mainDataForm" method="post">
	                        <input type="hidden" name="id" /></td>
							<table class="form-tb">
								<col width="100" />
								<col />
								<col width="100" />
								<col />
								<col width="100" />
								<col />							
								<tbody>
									<th>公司名称：</th>
			       		 			<td>
			    						<input class="easyui-company ipt" name="companyName" id="companyNameCondition"
			    							data-options="inputNoField:'companyNoCondition',inputNameField:'companyNameCondition',inputWidth:180"
			    						/>
			       		 				<input type="hidden" name="companyNo" id="companyNoCondition"/>
			       		 			</td>
			       		 			<th>业务类型编号：</th>
			       		 			<td><input class="easyui-validatebox ipt"  name="bizTypeCodeLike" id="bizTypeCodeCondition"/></td>
			       		 			<th>业务类型名称：</th>
			       		 			<td><input class="easyui-validatebox ipt" name="bizTypeNameLike" id="bizTypeNameCondition"/></td>
							</tbody>
							</table>
							</form>
					</div>
				</div>
					
				<#--列表-->
				<div data-options="region:'center',border:false" style="height:350px;">
                	 <@p.subdatagrid id="dataGridJG"  loadUrl="/inside_biz_type/list.json?sort=create_time&&order=desc" saveUrl=""   defaultColumn=""   title=""
				              isHasToolBar="false"    onClickRowEdit="false"    pagination="true"  checkOnSelect="false" selectOnCheck="false"
					           rownumbers="true" singleSelect="true"  
					           columnsJsonList="[
					                  {field : 't',checkbox:true,width : 30},
					                  {field : 'id',hidden : 'true',align:'center'},
					                  {field : 'companyName',title : '公司名称',width : 250,align:'left',halign:'center'},
					                  {field : 'bizTypeCode',title : '业务类型编号',width : 100,align:'center'},
					                  {field : 'bizTypeName',title : '业务类型名称',width : 100,align:'left',halign:'center'},
					                  {field : 'carryOverCost',title : '是否结转成本',width : 100,align:'center',
					                  	formatter:function(val){
					                  		if(val==0){
					                  			return '否'
					                  		}else if(val==1){
					                  			return '是'
						                  		}
						                  	}
					                  },
					                  {field : 'statusName',title : '启用状态',width : 80,align:'center',halign:'center'},
					                  {field : 'remark',title : '备注',width : 200,align:'left',halign:'center'},
					                  {field : 'createUser',title : '建档人',width : 100,align:'center'}, 
					                  {field : 'createTime',title : '建档时间',width : 150,align:'center'},
					                  {field : 'updateUser',title : '修改人',width : 100,align:'center'}, 
					                  {field : 'updateTime',title : '修改时间',width : 150,align:'center'},
					                 ]" 
					                 jsonExtend='{
				                            onDblClickRow:function(rowIndex, rowData){
			                   	   				insideBizTypeEditor.showDialog(rowData);
			                   	   				}
				         			}'
				         />
				</div>
			</div>
			</div>
	</div>

	<div id="myFormPanel" class="easyui-dialog" data-options="closed:true"> 
	    <div class="easyui-layout" data-options="fit:true,border:false" >
		     <form name="dataForm" id="dataForm" method="post"  class="pd10">
		     	 <input type="hidden" name="id" id="id">
		     	 <div data-options="region:'north',border:false,height:140" class="pd15">
					<div class="easyui-panel" data-options="title:'内购业务类型信息',fieldset:true,fit:true,collapsible:false">
						<table cellpadding="1" cellspacing="1" class="form-tb">
						<tr>
							<th><span class="ui-color-red">*</span>公司名称：</th>
							<td>
								<input class="easyui-company ipt" readonly="true" name="companyName" id="companyNameId" 
								data-options="required:true,inputNoField:'companyNoId',inputNameField:'companyNameId',inputWidth:130"/>
								<input type="hidden" name="companyNo" id="companyNoId"/>
							</td>
							<th><span class="ui-color-red">*</span>业务类型编号：</th>
							<td>
								<input class="easyui-validatebox" name="bizTypeCode" id="bizTypeCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']" />  
							</td>
						</tr>
						<tr>
							<th><span class="ui-color-red">*</span>业务类型名称：</th>
							<td>
								<input class="easyui-validatebox"  name="bizTypeName" id="bizTypeNameId" data-options="required:true,validType:'unNormalData'" />
							</td>
							<th>成本结转：</th>
							<td>
								<input type="radio"  name="carryOverCost"  value="1"/>是
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio"  name="carryOverCost"  value="0"/>否
						    </td>
						</tr>
					    <tr>
							<th>备注：</th>
							<td colspan="3">
								<input class="easyui-validatebox ipt" name="remark" id="remark" data-options="validType:'maxLength[200]'" style="width:100%"/>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
				<div id="mainTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
				  	<div title="店铺明细">
			    		<div class="easyui-layout" data-options="fit:true">
				    		<div data-options="region:'north',border:false">
						    	<#-- 工具栏  -->
						  		<@p.toolbar id="dtltoolbar" listData=[
										 {"id":"btn-add-detail","title":"新增","iconCls":"icon-add-dtl", "action" : "insideBizTypeDetailEditor.clickFn()", "type":0},
							             {"id":"btn-del-detail","title":"删除","iconCls":"icon-del-dtl","action" : "insideBizTypeDetailEditor.delDtl()", "type":0}
							           ]
								  />
						  	</div>
					 		<div data-options="region:'center',border:false" >
					 			<@p.datagrid id="dtlDataGrid" emptyMsg = ""
									isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
									columnsJsonList="[
									 	{field : 't', checkbox:true, width : 30, notexport:true},
									 	{field : 'dltType',title : '明细类型',hidden : 'true',align:'center',notexport:true},
										{field:'shopNo',title:'店铺编码',width:120, editor:{type:'textbutton',options:insideBizTypeEditor.selectShop}},
										{field:'shopName',title:'店铺名称',width:300, editor:{type:'readonlytext'}}]"
								  		 jsonExtend='{onDblClickRow:function(rowIndex, rowData){
																	editor.editRow(rowIndex, rowData);
													             }}' 
								  />
					 		</div>
			        	</div>
					</div>
				</div>
			</div>
		 </form>	
   </div>
   </div>
   <#-- 查看页面 -->
		 <div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
			    <div class="easyui-layout" data-options="fit:true,border:false" >
				     <form name="dataFormView" id="dataFormView" method="post"  class="pd10">
				     	 <div data-options="region:'north',border:false,height:140" class="pd15">
							<div class="easyui-panel" data-options="title:'内购业务类型信息',fieldset:true,fit:true,collapsible:false">
								<table cellpadding="1" cellspacing="1" class="form-tb">
								<tr>
									<th><span class="ui-color-red">*</span>公司名称：</th>
									<td>
										<input class="easyui-company readonly" readonly="true" name="companyName" id="companyNameOne" 
										data-options="required:true,inputNoField:'companyNo_',inputNameField:'companyName_',inputWidth:130"/>
									</td>
									
									<th><span class="ui-color-red">*</span>业务类型编号：</th>
									<td>
										<input class="easyui-validatebox" name="bizTypeCode" id="bizTypeCodeId"  data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']" />  
									</td>
								<tr>
								<tr>
									<th><span class="ui-color-red">*</span>业务类型名称：</th>
									<td>
										<input class="easyui-validatebox"  name="bizTypeName" id="bizTypeNameId" data-options="required:true,validType:'unNormalData'" />
									</td>
									<th>成本结转：</th>
									<td>
										<input type="radio"  name="carryOverCost"  value="1"/>是
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio"  name="carryOverCost"  value="0"/>否
								    </td>
								</tr>
							    <tr>
									<th>备注：</th>
									<td>
										<input class="easyui-validatebox ipt" name="remark" id="remark" data-options="validType:'maxLength[200]'"/>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
						</div>
						<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
						    <div id="viewMainTab" class="easyui-tabs" data-options="fit:true,collapsible:false">
						    	<div title="店铺明细">
						    		<div class="easyui-layout" data-options="fit:true">
								 		<div data-options="region:'center',border:false">
										  <@p.datagrid id="dtlDataGridView"
								    			loadUrl="" saveUrl=""   defaultColumn="" 
									    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="false"
									    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
											    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
							           			columnsJsonList="[
									                  {field : 'shopNo',title:'店铺编号',width:170, align : 'left'},
									                  {field : 'shopName',title:'店铺名称',width:170, align : 'left'}
								                 ]"   
								        	/>
								        </div>
						        </div>
							</div>
						</div>
					 </form>	
			   </div>
		   </div>
		   
        </div>
    </div> 
		   
      
</div>
</body>
</html>