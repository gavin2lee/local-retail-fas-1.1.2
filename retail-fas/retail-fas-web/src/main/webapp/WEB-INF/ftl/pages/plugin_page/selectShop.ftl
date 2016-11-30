<#-- 设置店铺是否可多选 -->
<#assign multipleShop="${RequestParameters['multipleShop']!'false'}" >
<div class="easyui-layout" style="width:100%;height:99%;">
	<div data-options="region:'north',border:false">
    	 <@p.toolbar id="queryShopPageToolbar" listData=[
			 {"id":"query-shop-btn-search","title":"查询","iconCls":"icon-search","action":"shopSupport.queryShop()","type":0},
             {"id":"query-shoh-btn-remove","title":"清空","iconCls":"icon-empty","action":"shopSupport.clearQueryShop()","type":0},
             {"id":"query-shoh-btn-sure","title":"确定","iconCls":"icon-save","type":0}
           ]
		/>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout" data-options="fit:true"   >
       		<div data-options="region:'north',border:false">
            	<div class="search-div">
		 			<form name="queryShopPageSearchForm" id="queryShopPageSearchForm" method="post">
			            <table class="form-tb">
						    <col width="110" />
						    <col  />
						    <col width="110" />
						    <col />
						    <col width="70" />
						    <col />
						    <col width="70"/>
						    <col />
						    <tbody>
						    	<tr>
								  	<th align="right" width="110px">公司名称：</th>
								  	<td align="left" width="90">
									  	<input class="ipt easyui-company" data-options="multiple:true,inputNoField:'companyNoOfQueryShopPage',inputNameField:'companyNameOfQueryShopPage'" name="companyName" id="companyNameOfQueryShopPage"/>
										<input type="hidden" name="multiCompanyNo" id="companyNoOfQueryShopPage"/>
									</td>
									<th align="right" width="110px">商业集团：</th>
									<td align="left" width="90">
										<input class="ipt easyui-bsgroups" name="bsgroupsName" id="bsgroupsNameOfQueryShopPage" data-options="multiple:true,inputNoField:'bsgroupsNoOfQueryShopPage',inputNameField:'bsgroupsNameOfQueryShopPage'"/>
										<input type="hidden" name="multiBsgroupsNo" id="bsgroupsNoOfQueryShopPage"/>
									</td>
									<th align="right" width="110">商场：</th>
									<td align="left" width="120">
										<input class="ipt easyui-mall" name="mallName" id="mallNameOfQueryShopPage" data-options="multiple:true,filterBsgroups:'bsgroupsNoOfQueryShopPage',inputNoField:'mallNoOfQueryShopPage',inputNameField:'mallNameOfQueryShopPage'"/>
										<input type="hidden" name="multiMallNo" id="mallNoOfQueryShopPage"/>
									</td>
									<th align="right" width="110">品牌：</th>
		                    		<td align="left" width="120">
		                    			<input class="easyui-brand ipt" name="brandName" id="brandNameOfQueryShopPage" data-options="multiple:true,inputNoField:'brandNoOfQueryShopPage',inputNameField:'brandNameOfQueryShopPage'"/>
		                    			<input type="hidden" name="multiBrandNo" id="brandNoOfQueryShopPage"/>
		                    		</td>
								</tr>
						    </tbody>
						</table>   
			        </form>
		      	</div>
		    </div>
		    <div data-options="region:'center',border:false">
		    	<div class="easyui-layout" data-options="fit:true">
    				<div data-options="region:'west',split:false" title="管理城市" style="width:250px;">
    					<div class="easyui-layout" data-options="fit:true">
			           		<div data-options="region:'north',border:false" style="height:38px">
			           			 <@p.toolbar id="queryShopPageOrganToolbar" listData=[] />
					    	</div>
						    <div data-options="region:'center',border:false">
						    	<@p.datagrid id="pluginShopOrganDataGrid"  loadUrl="/organ/get_biz.json" saveUrl="" defaultColumn="" title=""
						              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  checkOnSelect="true" selectOnCheck="true"
							           rownumbers="false" singleSelect="false" 
							           onCheck="shopSupport.organCheck" onUncheck="shopSupport.organCheck" onCheckAll="shopSupport.organCheck" onUncheckAll="shopSupport.organCheck"
							           columnsJsonList="[
							                  {field : 't', checkbox:true, width : 30},
							                  {field : 'organNo', title : '编号', width : 80, align : 'left'},
							                  {field : 'name', title : '名称', width : 90, align : 'left'}
							              ]" 
								          jsonExtend='{
						                       onDblClickRow:function(rowIndex, rowData){
							                	 
							                   }
						                 }'
						         />
						      </div>
						  </div>
    					</div>
					    <div data-options="region:'center'" title="经营城市">
					    	<div class="easyui-layout" data-options="fit:true">
					           <div data-options="region:'north',border:false" style="height:38px">
					           			 <@p.toolbar id="queryShopPageBizCityToolbar" listData=[] />
							    </div>
							    <div data-options="region:'center',border:false">
							    	<@p.datagrid id="pluginShopBizCityDataGrid"  loadUrl="" saveUrl="" defaultColumn="" title=""
							              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  checkOnSelect="true" selectOnCheck="true"
								           rownumbers="false" singleSelect="false" 
								           onCheck="shopSupport.bizCityCheck" onUncheck="shopSupport.bizCityCheck" onCheckAll="shopSupport.bizCityCheck" onUncheckAll="shopSupport.bizCityCheck"
								           columnsJsonList="[
								                  {field : 't', checkbox:true, width : 30},
								                  {field : 'organNo', title : '编号', width : 70, align : 'left'},
								                  {field : 'name', title : '名称', width : 80, align : 'left'}
								              ]" 
									          jsonExtend='{
							                       onDblClickRow:function(rowIndex, rowData){
								                	 
								                   }
							                 }'
							         />
							      </div>
							  </div>
					    </div>
					    <div data-options="region:'east',split:false" title="店铺" style="width:280px;">
					    	<div class="easyui-layout" data-options="fit:true">
					    		 <div data-options="region:'north',border:false" style="height:38px">
					           			 <@p.toolbar id="queryShopPageShopToolbar" listData=[] />
							    </div>
							    <div data-options="region:'center',border:false">
							    	<#if multipleShop=='true'>
							        	<@p.datagrid id="pluginShopShopDataGrid"  loadUrl="" saveUrl="" defaultColumn="" title=""
								              isHasToolBar="false"    onClickRowEdit="false" pagination="false"  checkOnSelect="true" selectOnCheck="true"
									           rownumbers="false" singleSelect="false" pageSize="20" showDisplayMsg="false"
									           onLoadSuccess="shopSupport.clickShopPagination"
									           columnsJsonList="[
									                  {field : 't', checkbox:true, width : 30},
									                  {field : 'shopNo', title : '编号', width : 80, align : 'left'},
									                  {field : 'shortName', title : '名称', width : 140, align : 'left'}
									              ]" 
										          jsonExtend='{
							                           onDblClickRow:function(rowIndex, rowData){
									                	 
									                   }
								                 }'
							             />
							    	<#else>
							        	<@p.datagrid id="pluginShopShopDataGrid"  loadUrl="" saveUrl="" defaultColumn="" title=""
								              isHasToolBar="false"    onClickRowEdit="false" pagination="false"   checkOnSelect="true" selectOnCheck="true"
									           rownumbers="false" singleSelect="false" pageSize="20" showDisplayMsg="false"
									           onLoadSuccess="shopSupport.clickShopPagination"
									           columnsJsonList="[
									                  {field : 'shopNo', title : '编号', width : 80, align : 'left'},
									                  {field : 'shortName', title : '名称', width : 140, align : 'left'}
								              	]" 
									          	jsonExtend='{
							                           onDblClickRow:function(rowIndex, rowData){
									                		
									                   }
								                 }'
							             />
							    	</#if>
						      </div>
					  	  </div>
					  </div>
					   <div data-options="region:'south'" style="height:30px">
					   		<div class="easyui-pagination" style="border:1px solid #ccc;" id="shopPagination"
						        data-options="
						            total: 0,
						            pageSize: 500,
						            pageList:[10,20,50,100,200,300,400,500],
						            showPageList:true">
							</div>
					   </div>
				  </div>
			  </div>
		 </div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		toolSearch({
		    appendTo:$('#queryShopPageOrganToolbar'), //添加位置，默认为$('#toolbar')
		    items:[{text:'名称',name:'organ'}], //项目下拉列表
		    collapsible:false, //是否显示下拉箭头
		    callback:function(value){
		    	//点击搜索后触发函数
		    	shopSupport.queryOrgan(value);
		    }
		});
		
		toolSearch({
		    appendTo:$('#queryShopPageBizCityToolbar'), //添加位置，默认为$('#toolbar')
		    items:[{text:'名称',name:'bizCity'}], //项目下拉列表
		    collapsible:false, //是否显示下拉箭头
		    callback:function(value){
		    	//点击搜索后触发函数
		    	shopSupport.queryBizCity(value);
		    }
		});
		
		toolSearch({
		    appendTo:$('#queryShopPageShopToolbar'), //添加位置，默认为$('#toolbar')
		    items:[{text:'名称',name:'queryShopPageShopName'}], //项目下拉列表
		    collapsible:false, //是否显示下拉箭头
		    callback:function(value){
		    	//点击搜索后触发函数
		    	shopSupport.queryShop(value);
		    }
		});
		
		$(".searchbox-text").each(function(){
			$(this).val("");
		});
	});
</script>