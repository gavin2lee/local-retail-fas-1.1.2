<!--货管单位多选查询面板-->
<#assign BasePath = springMacroRequestContext.getContextPath()/>
<script>
	   var BasePath = '${springMacroRequestContext.getContextPath()}';
</script>
<div  class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
		 <table width="100%">
				<tr class="datagrid-toolbar">
					<td>
						 <@p.toolbar id="itemSearchTool" listData=[ 
			   				 {"id":"dgSelectorSearchBtn","title":"查询","iconCls":"icon-search","type":0},
			   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-remove","type":0},
			   				 {"id":"dgSelectorConfirmBtn","title":"确定","iconCls":"icon-save","type":0} 		                       
						  ]/>
					</td>
				</tr>
			</table>	
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true" id="subLayout">
			<div data-options="region:'north',border:false" >
				<div class="search-div">
           			<form name="dialog_SarchForm" id="dialog_SarchForm" method="post" >
                	 <table class="form-tb">
                	    <col width="80" />
				        <col />
				        <col width="110" />
				        <col />
                        <tbody>
                            <tr>
                            	  <th style="width:150px">大区</th>
								  <td><input class='easyui-combobox' name="zoneNo" id="zoneNo"  style="width:100px"/></td>
								  <th style="width:150px">管理城市</th>
								  <td><input class='easyui-combobox' name="organNo" id="organNo" data-options="
										valueField : 'organNo',
										textField : 'name',
										panelHeight : 150,
										editable : false"  style="width:100px"/>
								   </td>
								  <th style="width:150px">货管单位编号</th>
								  <td><input class="ipt"  name="orderUnitNo" id="orderUnitNo"  style="width:80px"/></td>
								  <th style="width:150px">货管单位名称</th>
								  <td><input class="ipt"  name="name" id="name"  style="width:80px"/></td>
							   </tr>
                        </tbody>
                    </table>
					</form>
				</div>
			</div>
			<div id="dialog_SearchDataGridDiv" data-options="region:'center',border:false">
			    <@p.datagrid id="dialog_SearchDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
			       isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"    pagination="true" 
			       rownumbers="false" idField="orderUnitNo" singleSelect="false"
			       columnsJsonList="[
			       		  {field : 'ck',checkbox:true},
			          	  {field : 'orderUnitNo',title : '货管单位编号',width : 150,align:'left',sortable : true,sortField:'order_unit_no'},
		                  {field : 'name',title : '货管单位名称',width : 200,align:'left',sortable : true,sortField:'name'},
		                  {field : 'organName',title : '管理城市',width : 260,align:'left',sortable : true,sortField:'organ_name'}
		                 ]"   
			       />
 			</div>
	    </div>
	</div>
</div>

<script>
	$(function(){
		$('#zoneNo').combobox({
			url : BasePath + '/zone_info/get_biz',
			valueField : "zoneNo",
			textField : "name",
			panelHeight : 150,
			editable : false,
			onSelect:function(data){
				$('#organNo').combobox('clear');
				var url = BasePath + '/organ/get_biz?zoneNo='+data.zoneNo+'&organLevel=1';
            	$('#organNo').combobox('reload', url);    
			}
		});
	});
</script>

