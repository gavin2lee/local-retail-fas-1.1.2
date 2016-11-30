	<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
		<div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataFormView" method="post" class="pd10">
	     	 <div data-options="region:'north',border:false,height:160" class="pd15">
					<div class="easyui-panel" data-options="title:'基本信息',fieldset:true,fit:true,collapsible:false">
						<table cellpadding="1" cellspacing="1" class="form-tb">
						   <input type="hidden" name="id" id="ids">
						   <tr height="40">
						      <td width="110" align='right'>
						      	<span class="ui-color-red">*</span>条款编码：
						      </td>
						      <td width="140" align='left'>
						       		<input class="easyui-validatebox ipt" style="width:140px" name="termNo" id="termNos" data-options="required:true,validType:['unNormalData','engNum','maxLength[10]']"/>
						      </td>
						      <td width="110" align='right'>
						      	<span class="ui-color-red">*</span>条款名称：
						      </td>
						       <td width="140" align='left'>
						      		<input class="easyui-validatebox ipt" style="width:140px" name="name" id="nameIdView" data-options="required:true,validType:['unNormalData','maxLength[20]']"/>
						      </td>
						   </tr>
						   <tr height="40">
						   	  <td width="110" align='right'>
						   	  	<span class="ui-color-red">*</span>公司名称：
						   	  </td>
						      <td width="140" align='left'>
						      <input class="easyui-company readonly" name="companyName" id="companyNameOne" 
						      		data-options="required:true, inputWidth:150"/>
						      </td>
						   </tr>
						</table>
					</div>
				</div>
		<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
				    <div class="easyui-tabs" data-options="fit:true,collapsible:false">
				    	<div title="条款明細">
				    		<div class="easyui-layout" data-options="fit:true">
				    			<div data-options="region:'north',border:false">
									<@p.toolbar id="dtltoolbarView" listData=[
											 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add","type":0},
								             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del","type":0}
								           ]
								  	/>
								  	</div>
									<div data-options="region:'center',border:false">
									  <@p.datagrid id="dtlDataGridDivView"
							    			loadUrl="" saveUrl=""   defaultColumn="" 
								    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="false" selectOnCheck="false"
								    	    height="300" width="" onClickRowEdit="false" singleSelect="true"
										    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
						           			columnsJsonList="[
									              {field : 'controlPoint',title:'控制点',width:100, formatter:function(value, row){return row.controlPointName;} },
									              {field : 'controlPointName',hidden : true, title:'控制点名称',width:120 },
									              {field : 'advanceType',title:'预收类型',width:100, formatter:function(value, row){return row.advanceTypeName;}},
								                  {field : 'advanceScale',title:'预收比例（%）',width:100  },
								                  {field : 'remark',title:'描述',width:180}
							                 ]"   
								             jsonExtend='{onDblClickRow:function(rowIndex, rowData){
												
								             }}' 
							        	/>
						         </div>
				        <div>
					</div>
				</div>
		 	</form>	
   		</div>
