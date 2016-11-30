<div id="myFormPanelView" class="easyui-dialog" data-options="closed:true"> 
    <div class="easyui-layout" data-options="fit:true,border:false" >
	     <form name="dataForm" id="dataFormView" method="post"  class="pd10">
	     	 <div data-options="region:'north',border:false,height:140" class="pd15">
				<div id="div1" class="easyui-panel" data-options="title:'结算路径信息',fieldset:true,fit:true,collapsible:false">
					<table cellpadding="1" cellspacing="1" class="form-tb">
						<input type="hidden" name="id" id="id">
					   		<tr>
								<td width="110" align='right'>
									<span class="ui-color-red">*</span>
									路径编码：
								</td>
								<td width="120" align='left'><input class="ipt disabled" style="width:140px;" name="pathNo"  readonly="true"/></td>
								<td width="110" align='right'>
									<span class="ui-color-red">*</span>
									路径名称：
								</td>
								<td width="120" align='left'><input class=" ipt disabled" style="width:140px;" name="name" readonly="true" /></td>
								<td width="110" align='right'>
									单据类型：
								</td>
								<td width="120" align='left'>
									 <input class="ipt" name="billTypes" id="billTypesView" />
								</td>
							</tr>
							<tr>
								<td width="110" align='right'>
									<span class="ui-color-red">*</span>
									结算大类：
								</td>
								<td width="120" align='left'>
									<input class="easyui-settlecategorybox ipt"  name="settleCategoryNo" id="settleCategoryNoView" data-options="width:150"/>
								</td>
								<td width="110" align='right'>
									<span class="ui-color-red">*</span>
									新旧款：
								</td>
								<td width="120" align='left'>
									 <input class="easyui-newstylebox ipt"  name="styleNo" id="styleNoView" data-options="width:150,url:BasePath+'/settle_new_style/contain_all_item?status=1'"/>
								</td>
								<th width="110" align='right'>原残退厂单 ：</th>
                                <td width="120" align='left'>
                                	<input type="checkbox"  name="returnOwnFlag" id="returnOwnFlagView" value="1"/>
                                </td>
							</tr>
							<tr>  
						     	<td width="110" align='right'>起始日：</td>
						      	<td width="120" align='left'><input class="easyui-datebox ipt" style="width:140px" name="startDate"  data-options="disabled:true"/></td>
						     	<td width="110" align='right'>结束日：</td>
						     	<td width="120" align='left'><input class="easyui-datebox ipt" style="width:140px" name="endDate" data-options="disabled:true"/></td>
							</tr>
						</table>
				</div>
			</div>
			<div data-options="region:'center',border:false" class="pd15" style="padding-top:0;">
			    <div class="easyui-tabs" data-options="fit:true,collapsible:false" id="mainTabView">
			    	<div title="结算公司">
			    		<div class="easyui-layout" data-options="fit:true">
				    		<div data-options="region:'north',border:false">
						    	 <@p.toolbar id="dtltoolbarView" listData=[
										 {"id":"btn-add-detail","title":"新增行","iconCls":"icon-add-dtl", "type":0},
							             {"id":"btn-del-detail","title":"删除行","iconCls":"icon-del-dtl","type":0},
							             {"id":"btn-copy","title":"结算公司复制","iconCls":"icon-copy","type":0}
							           ]
								  	/>
							</div>
					 		<div data-options="region:'center',border:false">
							  	<@p.datagrid id="companyDataGridDivView"
							    	loadUrl="" saveUrl=""   defaultColumn="" 
							    	    isHasToolBar="false"  divToolbar=""  checkOnSelect="true" selectOnCheck="true"
							    	    height="300" width="" onClickRowEdit="false" singleSelect="false"
									    pagination="false" rownumbers="true" enableHeaderClickMenu="false"
					           			columnsJsonList="[
							                  {field : 'pathOrder',title:'路径次序', width:70, align : 'right'},
							                  {field : 'companyName',title:'公司名称',width:260, align : 'left', editor:{}},
							                  {field : 'companyNo',title:'公司编码',width:100, align : 'left', editor:{type:'readonlybox',options:{
							                  																			id:'companyNo',
							                  																			name:'companyNo', 
							                  																			width:'100px'
							                  																		}
							                  																	}
							                  },
							                  {field : 'financialBasis',title :'结算依据', width:120, align : 'center', formatter:function(value, row){return row.financialBasisText;},editor:{}},
											  {field : 'financialBasisText',hidden : true, title:'结算依据',width:120,editor:{type:'hiddenbox',
						                  																	options:{
						                  																		id:'financialBasisText',
						                  																		name:'financialBasisText'
																											}
																										}
						                      }
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
 </div>