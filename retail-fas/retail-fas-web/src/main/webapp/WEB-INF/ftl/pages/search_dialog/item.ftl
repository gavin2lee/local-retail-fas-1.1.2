<!--商品查询面板-->
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
			   				 {"id":"dgSelectorClearBtn","title":"清空","iconCls":"icon-remove","type":0}	 		                       
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
                	    <col width="80"/>
                	    <col />
                	    <col width="80"/>
                	    <col />
                	    <col width="80"/>
                	    <col />
                	    <col width="80"/>
                	    <col />
                        <tbody>
                            <tr>
                                <th>商品编码:</th>
                                <td><input class="ipt" name="codeCondition" id="codeCondition" /></td>
                                <th>商品名称:</th>
                                <td><input class="ipt" name="itemName" id="nameCondition" /></td>
                                <th>商品状态:</th>
                                <td><input class="easyui-combobox  ipt" name="status" id="statusCondition" data-options="valueField:'value',textField:'text',data: fas.data.itemStatusData"/></td>  
                            	<th>大类:</th>
                            	<td><input class="easyui-combobox ipt" name="rootCategoryNo" id="rootCategoryNo"  data-options="valueField:'categoryNo',textField:'name',url:'${BasePath}/category/get_biz?levelid=1',panelHeight:150"/></td>
                            </tr>
                            <tr>
                                <th>品牌:</th>
                                <td><input class="easyui-combobox ipt"  name="brandNo" id="brandNoCondition" 
                                 	data-options="valueField:'brandNo',textField:'name',url:'${BasePath}/brand/get_biz',panelHeight:150
                                 	,formatter: function(row){
                                      	var opts = $(this).combobox('options');
										return row[opts.textField]=row[opts.valueField]+'->'+row[opts.textField];
									}"/></td>
                                <!--  <th>品牌名称:</th>
                                <td><input class="ipt"  name="brandName" id="brandNameCondition" /></td> -->
                                <th>年份:</th>
                                <td>
                                	<input class="easyui-datebox readonly ipt"  name="years" id="yearCondition" data-options="dateFmt:'yyyy'" />
                                </td>
                            </tr> 
                        </tbody>
                    </table>
					</form>
				</div>
			</div>
			<div id="dialog_SearchDataGridDiv" data-options="region:'center',border:false">
			    <@p.datagrid id="dialog_SearchDataGrid"  loadUrl="" saveUrl=""  defaultColumn=""  
			       isHasToolBar="false" divToolbar="" height="415"  onClickRowEdit="false"    pagination="true" 
			       rownumbers="true" idField="itemNo" singleSelect="false"
			       columnsJsonList="[
			          	{field : 'code',title : '商品编码',width : 120,sortable : true,sortField:'code'},
			          	{field : 'name',title : '商品名称',width : 150,align:'left',sortable : true,sortField:'name'},
			          	{field : 'brandName',title : '商品品牌',width : 100,align:'left',sortable : true,sortField:'brand_name'}, 	
			          	{field : 'statusName',title : '状态',width : 60, sortable : true,sortField:'status'},
			          	{field : 'yearsName',title : '年份',width : 60,sortable : true,sortField:'years_name'},
			          	{field : 'sellSeasonName',title : '季节',width : 60,sortable : true,sortField:'sell_season_name',hidden:true},
			          	{field : 'month',title : '月份',width : 60,sortable : true,sortField:'month',hidden:true},
			          	{field : 'supplierName',title : '供应商名称',width : 120,align:'left',sortable : true,sortField:'supplier_name'}, 
			          	{field : 'colorNo',title : '颜色编码',width : 80,sortable : true,sortField:'color_no',hidden:true}, 
			          	{field : 'purchaseTaxPrice',title : '进价',width : 50,align:'right',sortable : true,sortField:'purchase_tax_price',hidden:true},
			          	{field : 'tagPrice',title : '牌价',width : 60,align:'right',sortable : true,sortField:'tag_price',hidden:true},
			          	{field : 'suggestTagPrice',title : '建议牌价',width : 80,align:'right',sortable : true,sortField:'suggest_tag_price',hidden:true},
			          	{field : 'styleNo',title : '商品款号',width : 80,sortable : true,sortField:'style_no',hidden:true},
			          	{field : 'ingredients',title : '主料',width : 50,formatter:fas.common.ingredientsFormatter,sortable : true,sortField:'ingredients',hidden:true},
			          	{field : 'sysNo',title : '所属业务单元',width : 100,sortable : true,sortField:'sys_no',hidden:true},
			          	{field : 'shoeModel',title : '款型',width : 50,sortable : true,sortField:'shoe_model'}, 
			          	{field : 'enName',title : '商品英文名',width : 120,sortable : true,sortField:'en_name'},  
			          	{field : 'saleDate',title : '建议上柜日',width : 100,sortable : true,sortField:'sale_date'},
			            {field : 'itemNo',title : '系统编码',width : 120,hidden:true},
			          	{field : 'brandNo',title : '商品品牌编码',width : 80,hidden:true},
			         ]" 
			       />

 			</div>
	    </div>
	</div>
</div>
