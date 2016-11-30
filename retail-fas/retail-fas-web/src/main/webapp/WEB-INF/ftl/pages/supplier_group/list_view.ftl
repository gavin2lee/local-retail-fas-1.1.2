<!-- 新增修改-->
<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true">
	<div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataFormView" method="post" class="pd10">
	     	<div data-options="region:'north',border:false,height:150" class="pd15">
				<div id="divView" class="easyui-panel" data-options="title:'供应商组信息',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="1" cellspacing="1" class="form-tb">
				   		<tr>
				   			<th width="110" align='right'>
								<span class="ui-color-red">*</span>
								供应商组编码：
							</th>
		        		    <td><input class="easyui-validatebox ipt" name="groupNo" id="groupNos" data-options="required:true,validType:'length[2,18]'" /></td>
		        		    <th width="110" align='right'>
		        		    	<span class="ui-color-red">*</span>
		        		    	供应商组名称：
		        		    </th>
		        		    <td><input class="easyui-validatebox ipt" name="groupName" id="groupName" data-options="required:true,validType:'length[2,100]'"/></td>
		        		</tr>
		        		<tr>
			         		<th width="110" align='right'>
		        		    	<span class="ui-color-red">*</span>
		        		    	启用日期：
		        		    </th>
		        		    <td><input class="easyui-validatebox easyui-datebox ipt" name="enableTime" id="enableTime" data-options="disabled:true" /></td>
		        		    <th width="110" align='right'>
		        		    	<span class="ui-color-red">*</span>
		        		    	终止日期：
		        		    </th>
		        		    <td><input class="easyui-validatebox easyui-datebox ipt" name="disableTime" id="disableTime" data-options="disabled:true" /></td>
		        		</tr>
		        		<tr>
		        		    <th>备注：</th>
		        		    <td><input class="easyui-validatebox ipt" name="remark" id="remark" data-options="validType:'length[0,100]'" /></td>
		        		</tr>
					</table>
				</div>
			</div>
			<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
				<div class="easyui-tabs" data-options="fit:true,collapsible:false" id="mainTabView">
			    	<div title="供应商组明细">
							<@p.toolbar  id="dtltoolbarView" listData=[
								 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add",  "type":0},
							     {"id":"btn-edit-detail","title":"修改行","iconCls":"icon-edit", "type":0},
							     {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","type":0},
							     {"id":"btn-import-detail","title":"导入","iconCls":"icon-import","type":0}
							   ]
							/>
							<@p.datagrid id="dtlDataGridDivView"
								    isHasToolBar="false"  divToolbar=""  checkOnSelect="false"
								    height="300" width="" onClickRowEdit="false" singleSelect="true" selectOnCheck="false"
								    pagination="true" rownumbers="true" enableHeaderClickMenu="false"
									columnsJsonList="[
							              {field : 'ck',checkbox:true,notexport:true},
							              {field : 'supplierNo',title:'供应商编码',width:170  },
							              {field : 'supplierName',title:'供应商名称',width:170  },
							              {field : 'createUser', title : '创建人', width : 90, align : 'left'},
							              {field : 'createTime', title : '创建时间', width : 140, align : 'left'}
							         ]"   
							         jsonExtend='{onDblClickRow:function(rowIndex, rowData){
									             }
						             }' 
								/>
					</div>
		</div>
	</div>  
</form>
</div>
</div>

				<!-- 未分组供应商列表展示 -->
				<div id="supplierNoGroup" class="easyui-dialog" data-options="closed:true">
					<div class="easyui-layout" data-options="fit:true,border:false" >
					     <form name="dataForm" id="dataForm" method="post" class="pd10">
					     	<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
							  	<@p.datagrid id="supplierNoGroupDataGridDiv" loadUrl="" saveUrl=""   defaultColumn="" 
							    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false"
							    	    height="300" width="" onClickRowEdit="false" singleSelect="true" selectOnCheck="false"
									    pagination="true" rownumbers="true" enableHeaderClickMenu="false" fitColumns="true"
					           			columnsJsonList="[
								              {field : 'supplierNo',title:'供应商编码',width:170, align:'center'},
							                  {field : 'fullName',title:'供应商名称',width:170, align:'center'}
						                 ]"   
							             jsonExtend='{
							             	//双击方法
							             	onDblClickRow:function(rowIndex, rowData){
											//fas_common_editor.editRow(rowIndex, rowData);
							             }}'
						        	/>
							        </div>
							    </div>
							</div>  
						 </form>	
					</div>
			   	</div>

