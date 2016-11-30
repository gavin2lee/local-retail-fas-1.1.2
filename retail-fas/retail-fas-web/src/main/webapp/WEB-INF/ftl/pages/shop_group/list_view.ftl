<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" >
				<div class="search-div">
				     <form name="dataForm" id="dataFormView" method="post"  >
				     	 <input type="hidden" name="id" id="id"/>
						 <table class="form-tb">
		            	    <col width="120px"/>
		            	 	<col />
		            	 	<col width="120px"/>
		            	 	<col />
		                    <tbody>
							   <tr>
							   		<th>店铺分组编号：</th>
		                            <td><input class="ipt readonly" readonly="readonly"	name="shopGroupNo" id="shopGroupNo" /></td>
							        <th>店铺分组名称：</th>
							         <td><input class="ipt readonly" style="width:140px;" readonly="readonly" name="shopGroupName" id="shopGroupName" /></td>
	                             </tr>
							   <tr>
							   	 <th>公司名称：</th>
							   	   <td>
								 		<input class="easyui-company readonly" name="companyName" id="companyNameOne" 
								 			data-options="required:true,inputWidth:130,inputNoField:'companyNo_',inputNameField:'companyName_'"/>
			      					</td>
			      				  <th>客户：</th>
								   <td>
								   	<input class="easyui-validatebox readonly" name="clientName" id="clientName" data-options="required:true"/>
								   </td>
							   </tr>
							  <tr>
							   		<th>开票名称：</th>
								   <td><input  class="ipt" name="invoiceName" id="invoiceName" readonly="readonly" /></td>
								   <th>发票模板名称：</th>
								   <td>
								   	<input class="ipt" name="templateName" id="templateName" />
								   </td>
							   </tr>
							   <tr>
								   <th>生效日：</th>
								   <td><input class="easyui-datebox easyui-validatebox readonly" name="valueDate" id="valueDate" data-options="disabled:true" /></td>
		                           <th>备注：</th>
								   <td><input class="ipt"  id="remarkId" name="remark"/></td>
							   </tr>
							 </tbody>
						 </table>
				 	</form>	
				 	
			 	</div>
			 </div>
			 <div data-options="region:'center',border:false"  class="pd15" style="padding-top:0;">
			 	<div class="easyui-layout" data-options="fit:true,collapsible:false" id="mainTabView">
			 		<div data-options="region:'north',border:false" >
			 			<@p.toolbar id="dtltoolbarView" listData=[
				             {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "type":0},
							  {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","type":0}
				           ]
						/>
			 		</div>
			 		<div data-options="region:'center',border:false" >
			 			<@p.datagrid id="dtlDataGridView" emptyMsg = ""
							isHasToolBar="false"  onClickRowEdit="false"  pageSize="20" 
							columnsJsonList="[
							 	{field : 't', checkbox:true,width : 30, notexport:true},
								{field:'shopNo',title:'店铺编码',width:120, editor:{type:'textbutton',options:shopGroup.selectShop}},
								{field:'shopName',title:'店铺名称',width:300, editor:{type:'readonlytext'}}]"
						  />
			 		</div>
			 	</div>
			 </div>
		</div>
   </div>