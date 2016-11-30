<div class="easyui-layout" data-options="fit:true,border:false">
	<#--按钮-->
	<div data-options="region:'north',border:false" class="toolbar-region">
				<@p.toolbar id="terminalToolbar" listData=[
					{"id":"btn-search","title":"查询","iconCls":"icon-search","action":"terminalAccount.searchData()", "type":0},
					{"id":"btn-remove","title":"清空","iconCls":"icon-remove","action":"terminalAccount.clear()", "type":0},
					{"id":"top_btn_aduit","title":"启用","iconCls":"icon-aduit","action":"terminalAccount.batchOperate(0)","type":0},	
			 		{"id":"top_btn_cancel","title":"停用","iconCls":"icon-aduit","action":"terminalAccount.batchOperate(1)","type":0},
					{"id":"btn-import-main","title":"导入","iconCls":"icon-import","action":"terminalAccount.importExcel()", "type":6},
					{"id":"btn-export","title":"导出","iconCls":"icon-export","action":"terminalAccount.exportExcel()", "type":4}]/>
	</div>
	
	 <div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true" id="terminalSubLayout">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
					   <#-- 主档信息  -->
		               <form name="terminalForm" id="terminalForm" method="post">
						<table class="form-tb">
							<col width="80" />
							<col />
							<col width="80" />
							<col />
							<col width="80" />
							<col />	
							<col width="80" />
							<col />							
							<tbody>
								<tr> 
						    	<td align="right" width="80">公司名称：</td>
								<td align="left" width="140">
									<input class="easyui-company ipt" name="companyName" id="companyName1" 
											data-options="inputWidth:200,inputNoField:'companyNo1', inputNameField:'companyName1'"  />
									<input type="hidden" name="companyNo" id="companyNo1"/>
								</td>
								<td align="right" width="110">商场名称：</td>
								<td>
									<input class="easyui-mall ipt" name="mallName" id="mallName1"
											data-options="inputWidth:200,inputNoField:'mallNo1', inputNameField:'mallName1'"/>
									<input type="hidden" name="mallNo" id="mallNo1"/>
								</td>	
							 	<td align="right" width="110">店铺名称：</td>
						     	<td align="left" width="140">
						     		<input id="shopName1" class="easyui-shopCommon" disabled="disabled"/>
									<input type="hidden" name="shopNo" id="shopNo1"/>
						     	</td>
						     	<th>管理城市： </th>
		                        <td>
									<input class="easyui-organ ipt" name="organNames" id="organNames" data-options="multiple:'true',inputWidth:160,inputNoField:'organNos1',inputNameField:'organNames'"/> 
									<input type="hidden" name="organNos1" id="organNos1"/>
								</td>
						     </tr>
						     <tr>
						     	<td align="right" width="80">终端号：</td>
						     	<td align="left" width="140">
						     		<input type="text" name="terminalNumber" id="terminalNumber" style="width:198px" />
						     	</td>
						     	<td align="right" width="110">刷卡行：</td>
						     	<td align="left" width="140">
						     		<input type="text" name="creditCardBank" id="creditCardBank" style="width:198px" />
						     	</td>
						     	<td align="right" width="110">终端绑定账号：</td>
						     	<td align="left" width="140">
						     		<input type="text" name="creditCardAccount" id="creditCardAccount" />
						     	</td>
						     </tr>
							</tbody>
						</table>
					</form>
						</div>
					</div>
					
				<div  data-options="region:'center',border:false">
	     		<div class="easyui-layout" data-options="fit:true" id="subLayout">	
			 	<#--工具栏   -->    
				<div data-options="region:'north',border:false">
				<@p.toolbar id="terminalToolbar2" listData=[
					 {"id":"btn-insert","title":"新增行","iconCls":"icon-add", "action" : "terminalAccount.add()", "type":1},
		             {"id":"btn-remove","title":"删除行","iconCls":"icon-remove", "action" : "terminalAccount.del()","type":3},
		             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "terminalAccount.save()", "type":7}
		           ]
				/>
            	</div>
						
					<#--列表-->
			        <div data-options="region:'center',border:false">
					<@p.datagrid id="terminalAccountDataGrid"  loadUrl="" saveUrl=""   defaultColumn="" 
		              isHasToolBar="false"    onClickRowEdit="false" pagination="true" 
			           rownumbers="true"  emptyMsg = "" pageSize="20" showFooter="true"
			           columnsJsonList="[
			                  	{field : 't',checkbox:true,width : 30,align:'center',notexport:true},
				                {field : 'id',hidden : 'true',align:'center',notexport:true},
								{field : 'shopNo', title : '店铺编码', width : 120,
									editor:{
										type:'searchboxname',
										options:{
											id:'shopNo_',
											name:'shopNo_',
											readonly:true,
										}
									}
								},	
								{field : 'shopName',title : '店铺名称',width : 150,align:'left',
									editor:{
										type:'shop',
										options:{
											id:'shopName_',
											name:'shopName_',
											required:true
										}
									}
								},
				                 {field : 'terminalNumber',title : '终端号',width : 100,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['account','length[5,21]']
				                  		}
				                 	},exportType:'text'
				                 },
				                 {field : 'creditCardBank',title : '刷卡行',width : 100,align:'left',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['unNormalData','length[3,30]']
				                  		}
				                 }},
				                 {field : 'creditCardAccount',title : '终端绑定账号',width : 130,align:'center',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['account','length[5,25]']
				                  		}
				                 	},exportType:'text'
				                 },
				                 {field : 'creditCardType',title : '支付方式',width : 100,align:'left',
									editor:{
				                  		type:'combobox',
				                  		options:{
				                  			valueField:'id',
				                  			textField:'name',
				                  			data:[{id:'支付宝',name:'支付宝'},{id:'商场券',name:'商场券'},{id:'银行卡',name:'银行卡'},{id:'现金券',name:'现金券'},{id:'微信支付',name:'微信支付'}],
				                  			panelHeight:'auto'
				                  		}
				                 }},
				                 {field : 'creditCardRate',title : '刷卡费率',width : 60,align:'center',
									editor:{
				                  		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['rate']
				                  		}
				                 }},
				                 {field : 'status',title : '状态',width : 100,align:'left',formatter: function(value,row,index){if(value == '0'){return '启用'}else if(value == '1'){ return '停用'}}},
				                 {field : 'companyNo', title : '公司编码', width : 120, 
				                	editor:{
				                		type:'searchboxname',
				                		options:{
				                			id:'companyNo_',
				                			name:'companyNo_',
				                			readonly:true,
				                		}
				                	}
				                },
				                	
				                {field : 'companyName',title : '公司名称',width : 180,align:'left',
				                	editor:{
				                  		type:'searchboxname',
				                  		options:{
				                  			id:'companyName_',
				                  			name:'companyName_',
				                  			readonly:true,
				                  			width:'200px',
				                  			validType:['unNormalData','length[5,10]']
				                  		}
				                  	}
				                 },
				                 {field : 'mallNo', title : '商场编码', width : 120, 
				                 	editor:{
				                 		type:'searchboxname',
				                 		options : {
				                 			id : 'mallNo_',
				                 			name : 'mallNo_',
				                 			readonly:true
				                 		}
				                 	},exportType:'text'
				                 },	 	
				                {field : 'mallName',title : '商场名称',width : 150,align:'left',
				                	editor:{
				                  		type:'searchboxname',
				                  		options:{
				                  			id:'mallName_',
				                  			name:'mallName_',
				                  			readonly:true,
				                  			width:'180px',
				                  			validType:['unNormalData','length[5,10]']
				                  		}
				                }},
				                {field : 'merchantsNo', title : '商户编码', width : 120, 
				                 	editor:{
				                 		type:'validatebox',
				                  		options:{
				                  			required:true,
				                  			validType:['unNormalData','length[3,15]']
				                  		}
				                 	},exportType:'text'
				                 },
				                 {field : 'createUser',title : '建档人',width : 80,align:'left'}, 
				                 {field : 'createTime',title : '建档时间',width : 130,align:'center'},
				                 {field : 'updateUser',title : '修改人',width : 80,align:'left'}, 
				                 {field : 'updateTime',title : '修改时间',width : 130,align:'center'}
			              ]"
			              jsonExtend='{
	                          onDblClickRow:function(rowIndex, rowData){
			                   	  if(rowData.id != undefined){
					          			if(rowData.status!=0){
											terminalAccount.edit(rowIndex, rowData) ;
											return;
										}
										showWarn("终端账号已启用!");
		          					}
			                   }
			         }' 
               		  />
					</div>
					</div>
					</div>
				</div>	
	
	
</div>
