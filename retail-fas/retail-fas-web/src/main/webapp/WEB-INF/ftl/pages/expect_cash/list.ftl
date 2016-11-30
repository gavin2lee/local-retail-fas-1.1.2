<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><#if Request.flagStr?exists>${Request.flagStr}</#if></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include  "/WEB-INF/ftl/common/header.ftl" >
    <script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.2.0.js?version=${version}"></script>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/expect_cash/expect_cash.js?version=${version}"></script>
</head>
<script>
	//注销状态的颜色深度
	function cellStyler(value,row,index){
	        if(row.field1=='无效'){
			   return 'background-color:#ffee00;color:red;font-weight:bold';
	        }
	}
</script>
</head>
<body class="easyui-layout">
	   <div data-options="region:'north',border:false" class="toolbar-region">
	     <@p.toolbar  id="toolbar" listData=[
			 {"id":"btn-search","title":"查询","iconCls":"icon-search","action":"expect_cash.doSearch();", "type":0},
             {"id":"btn-clear","title":"清空","iconCls":"icon-empty","action":"expect_cash.doSearchClear();", "type":0},
             {"id":"btn-save","title":"保存","iconCls":"icon-save", "action" : "editor.save()", "type":7},
             {"id":"btn_verify","title":"审核","iconCls":"icon-aduit","action":"expect_cash.verify()","type":31},
             {"id":"btn-anti-audit","title":"反审核","iconCls":"icon-aduit","action":"expect_cash.doAntiAudit()","type":32},
	         {"id":"btn-export","title":"导出","iconCls":"icon-export","action":"expect_cash.exportExcel()","type":4}
	          <!--
	         {"id":"btn-undo","title":"删除","iconCls":"icon-del","action":"expect_cash.deleteRecord()","type":3},     
	         {"id":"btn-print","title":"打印","iconCls":"icon-print","action":"expect_cash.print()","type":20}
	         {"id":"btn-edit","title":"修改","iconCls":"icon-edit","action":"expect_cash.showEditButton()","type":2},
	         {"id":"btn_verify","title":"退款","iconCls":"icon-refund","action":"expect_cash.showAddRefund()","type":5}-->
           ]
		/>
	  </div>
	<div  data-options="region:'center',border:false">
    	<div class="easyui-layout" data-options="fit:true">
			<!--搜索start-->
			<div data-options="region:'north',border:false" >
				<div class="search-div">
			      	<form name="searchForm" id="searchForm" method="post">
						<table class="form-tb">
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
						 <col width="100"/>
						 <col/>
		                 <tbody>
		                 	<tr>
							   <th>公司名称：</th>
							   <td>
									<input class="ipt easyui-company"  data-options="required:false" name="companyName" id="companyName"/>
									<input type="hidden" name="companyNo" id="companyNo"/>
							   </td>									
							   <th>管理城市： </th>
							   <td>
								    <input class="easyui-organ ipt"  data-options="inputNoField:'organNoTemp',inputNameField:'organName',multiple:true,inputWidth:130" name="organName" id="organName"/>
								    <input type="hidden" name="organNoTemp" id="organNoTemp"/>
							   </td>
							   <th>店铺：</th>
							   <td>
							   		<input class="ipt easyui-shop" data-options="multiple:true,inputNoField:'shopNoTemp'"  name="shopName" id="shopName" style="width:300px;"/>
									<input type="hidden" name="shopNoTemp" id="shopNoTemp"/>
							   </td>
							   <th>&nbsp;&nbsp;&nbsp;&nbsp;收款日期：</th>
					    	   <td>
					    			<input class="easyui-validatebox easyui-datebox ipt" style="width:90px;" name="startOutDate" id="createTimeStart" data-options="required:false,maxDate:'createTimeEnd'"/>
					    	   </td>
							    <th>&nbsp;&nbsp;-&nbsp;&nbsp;</th>
								<td>
									<input class="easyui-validatebox easyui-datebox ipt" style="width:90px;" name="endOutDate" id="createTimeEnd" data-options="required:false,minDate:'createTimeStart'"/>
								</td>
							</tr>
						 </tbody>
						 </table>
					</form>
				</div>
			</div>
	        <div data-options="region:'center',border:false">
		        <div class="easyui-layout" data-options="fit:true">
					<div data-options="region:'center',border:false" id="divDataGrid">
				      <@p.datagrid id="dataGridDiv"  loadUrl="" saveUrl=""   defaultColumn=""   
			              isHasToolBar="false"     reduceHeight="109" onClickRowEdit="false" selectOnCheck="true" checkOnSelect="true"   pagination="true"
				           rownumbers="true" singleSelect="false"   
				           columnsJsonList="[
				                  {field : 'id',checkbox:true,width : 30,notexport:true},
				                  {field : 'shopName',title : '店铺',width : 120,align:'left',halign:'center'},
				                  {field : 'shopNo',title : '店铺',width : 120,align:'center', hidden:true, editor:{type:'hiddenbox',options:{id:'shopNo_',name:'shopNo'}}},
				                  {field : 'businessTypeExpectStr',title : '类型',width : 80,align:'center', 
				                  formatter: function(value,row,index){
											switch(row.businessType) {
												case '1':
												return '商场预收';
													break;
												case '2':
												default:
												return '定金预收';
													break;
											}
									 }},
				                  {field : 'businessType',width : 90,align:'left',hidden:true,notexport:true},		
				                  {field : 'businessFlagStr',title : '类别',width : 60,align:'center',
				                  formatter: function(value,row,index){
											switch(row.businessFlag) {
												case '1':
												return '收款';
													break;
												case '2':
												default:
												return '退款';
													break;
											}
									 }},
				                  {field : 'businessFlag',width : 90,align:'left',hidden:true,notexport:true},			                                
				                  {field : 'currencyTypeStr',title : '币种',width : 60,align:'center',
				                  formatter: function(value,row,index){
											switch(row.currencyType) {
												default:
												return '人民币';
													break;
											}
									 }},
				                  {field : 'currencyType',width : 90,align:'left',hidden:true,notexport:true},
				                  {field : 'month',title : '结算月',width : 70,align:'center',
				                  editor:{
									type:'combogrid',
									options:{
										required:false,
										id:'month_',
										name:'month_',
										inputWidth:60,
									    required:true,
										idField:'month',
										textField:'month',
										noField:'month_',
										inputNoField:'month',
										url:'',
										paramMap:[{name:'shopNo', field:'shopNo'}],
										columns:[[ 
										    {field : 'month',title : '结算月',width : 80, align:'center',halign:'center'},
										]],
										onShowPanel:function(){
												if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
													var url = BasePath+'/shop_balance_date/list.json?noBalanceFlag=1&' 
								 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
								 	            	$(this).combogrid('grid').datagrid('options').url= url;
								 	            	$(this).combogrid('grid').datagrid('load');
												}
												else {
													showWarn('请先选择店铺！');
													return;
												}
											},
											callback:function(data){
												if(data){
													$('#month_').val(data.month);
												}
											}
										}
								  }},
								  {field : 'brandUnitNo',hidden : true,title : '品牌', align:'left',width : 80, notexport:true,
			                   			editor:{
			                   				type:'hiddenbox',
											options:{
												id:'brandUnitNo_',
												name:'brandUnitNo'
											}
										}
				                  },
								  {field : 'brandUnitName', title : '品牌部名称', width : 100,halign:'center',
										editor:{
											type:'combogrid',
											options:{
												id:'brandUnitName_',
												name:'brandUnitName',
												inputNoField:'brandUnitNo_',
												required:true,
												idField:'brandUnitName',
												textField:'brandUnitName',
												noField:'brandUnitNo',
												columns:[[
													{field : 'brandUnitNo',title : '品牌编码',width : 100, halign : 'center', align : 'left'},
													{field : 'brandUnitName',title : '品牌部名称',width : 150, halign : 'center', align : 'left'}
												]],
												onShowPanel:function(){
													if($('#shopNo_').val() != null && $('#shopNo_').val() != '') {
														var url = BasePath+'/shop_brand/list.json?' 
									 	            		+ jQuery.param({shopNo:$('#shopNo_').val()});
									 	            	$(this).combogrid('grid').datagrid('options').url= url;
									 	            	$(this).combogrid('grid').datagrid('load');
													}
													else {
														showWarn('请先选择店铺！');
														return;
													}
												},
											}
										}
									},
								  {field : 'categoryNo',hidden : true,notexport:true, 
								  		editor:{
								  				type:'hiddenbox',
								  				options:{id:'categoryNo'}
								  	    }
								  },
				                  {field : 'name',title : '大类',width : 90,align:'center',
				                  	editor:{
				                  	    type:'categorycombobox',
				                  	    options:{
				                  	       id:'categoryName',
				                  	       required:true,
				                  	       width:80
				                  	    }
				                  	}
				                  },
				                  {field : 'rate',title : '扣率',width : 80,align:'right',	exportType:'number',							
				                  	editor:{
				                  		type:'fasnumberbox',
				                  		options:{
				                  			id:'rate',
				                  			name:'rate',
				                  			precision:2,
				                  			validType:['maxLength[12]']
				                  		}
				                  	}
				                  },
				                  {field : 'businessDate',title : '日期',width : 90,align:'center'},
				                  {field : 'assistantName',title : '店员',width : 90,align:'center'},
				                  {field : 'amount',title : '金额',width : 80,align:'right',exportType:'number'},
				                  {field : 'usedAmount',title : '已使用金额',width : 80,align:'right',exportType:'number'},
				                  {field : 'unUsedAmount',title : '未使用金额',width : 80,align:'right',exportType:'number'},
				                  {field : 'refBillNo',title : '收款单据编号',width : 130,align:'center'},	
				                  {field : 'confirmFlagStr',title : '确认状态',width : 100,align:'center',
				                  formatter: function(value,row,index){
											switch(row.confirmFlag) {
												case 1:
													return '业务已确认';
													break;
												case 2:
													return '财务已确认';
													break;
												case 0:
												default:
												return '未确认';
													break;
											}
								  }},
								  {field : 'balanceStatusStr',title : '结算状态',width : 80,align:'center',
				                  formatter: function(value,row,index){
											switch(row.balanceStatus) {
												case 2:
													return '已结算';
													break;
												case 1:
												default:
												return '未结算';
													break;
											}
								  }},
								  {field : 'balanceNo',title : '结算单号',width : 150,align:'left',halign:'center'},
				                  {field : 'proName',title : '促销活动',width : 150,align:'left',halign:'center'},
				                  {field : 'billNo',title : '单据编号',width : 150,align:'left',halign:'center'},
				                  {field : 'settleCode',title : '结算编码',width : 120,align:'left',halign:'center'},	
				                  {field : 'contactName',title : '联系人',width : 90,align:'center'},
				                  {field : 'tel',title : '联系方式',width : 120,align:'left',halign:'center'},
				                  {field : 'confirmFlag',width : 90,align:'left',hidden:true,notexport:true},			                  						                  
				                  {field : 'remark',title : '备注',width : 180,align:'left',halign:'center'},
				                  {field : 'balanceStatus',hidden:true,notexport:true},
				                  {field : 'createUser',title : '建档人',width : 60,align:'left'}, 
					              {field : 'createTime',title : '建档时间',width : 130,align:'center'},
					              {field : 'updateUser',title : '修改人',width : 60,align:'left'}, 
					              {field : 'updateTime',title : '修改时间',width : 130,align:'center'}	
				                 ]" 
					          jsonExtend='{
			                            onDblClickRow:function(rowIndex, rowData){
			                             if(!expect_cash.check(rowIndex,rowData)){
			                   	  		     editor.editRow(rowIndex, rowData);
			                   	   }
			                   	   }
			                 }'/>
		                 </div>
		          </div>
			</div>
		 </div>
	</div>     
 		
	<div id="showDialog"  class="easyui-window" title="新增信息"  
		style="width:600px;height:410px;padding:2px;display:none;"   
		data-options="iconCls:'icon-mini-tis',modal:true,resizable:false,draggable:true,collapsible:false,closed:true,closable:false, minimizable:false,maximizable:false,fitColumns:true"> 
	 	<form name="dataForm" id="dataForm" method="post">
	 		<input type="hidden" name="id" id="id"/>
	 		<input type="hidden" name="billNo" id="billNo"/>
	 		<input type="hidden" name="refBillNo" id="refBillNo"/>	 		
			<table cellpadding="1" cellspacing="1" class="form-tb">			
				<tr height="30">
			  		<td width="100" align='right'><span class="ui-color-red">*</span>类型：</td>
			      	<td width="142" align='left'>
			      		<input class="easyui-combobox ipt" style="width:156px" name="businessType" id="businessType" required="true"/>
			      	</td>
			      	<td width="100" align='right'><span class="ui-color-red">*</span>类别：</td>
			      	<td width="142" align='left'>
			      		<input class="easyui-combobox ipt" style="width:156px" name="businessFlag" id="businessFlag" required="true"/>
			      	</td>
			  	</tr>	
			
			   	<tr height="30">		   	  
			   	  	<#--
		   	  		<td width="100" align='right'>单据编号：</td>
		      		<td width="142" align='left'><input class="easyui-numberbox ipt" style="width:150px" name="billNo" id="billNo" required="true"/></td>
			      	-->
			      	<td width="100" align='right'><span id="span_settleCode" class="ui-color-red">*</span>凭证编码：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px"  name="settleCode" id="settleCode"/></td>
			   		<td width="100" align='right'>业务名称：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px"  name="businessName" id="businessName"/></td>
			   	</tr>
			   	<tr height="30">
			  		<td width="100" align='right'>促销活动：</td>
			      	<td width="142" align='left'>
			      		<input type="hidden" id="proNo" name="proNo"/>
			      		<input type="hidden" id="rateCode" name="rateCode"/>
			      		<input type="hidden" id="rate" name="rate"/>
			      		<input class="easyui-combobox ipt" style="width:156px" name="proName" id="proName"/>
			      	</td>
			      	<th>品牌：</th>
						            <td><input type="hidden" name="brandNo" id="brandNo" />
						            	<input class="easyui-combogrid ipt" style="width:156px;" type="easyui-combogrid" name="brandName" id="brandName"/></td>
			  	</tr>
			   	<tr height="30">
			   		<td width="100" align='right'>分类选择：</td>
			      	<td width="142" align='left'>
			      		<input type="hidden" id="name" name="name"/>
			      		<input name="categoryNo" id="categoryNo" style="width:156px"/>
			      	</td>
			      	<td width="100" align='right'><span class="ui-color-red">*</span>操作员：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px" readonly="readonly" name="assistantName" id="assistantName" required="true"/></td>
			   		<input type="hidden" id="assistantId" name="assistantId"/>
			   		<input type="hidden" id="assistantNo" name="assistantNo"/>
			   	</tr>
			  	<tr height="30">
			  		<td width="100" align='right'><span class="ui-color-red">*</span>日期：</td>
			      	<td width="142" align='left'><input class="easyui-datebox easyui-validatebox ipt" style="width:147px" name="businessDate" id="businessDate" required="true"/></td>
			  		<td width="100" align='right'>客户名称：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px" name="customerName" id="customerName" data-options="validType:['vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']"/></td>
			  	</tr>	
			  	<tr height="30">
			  		<td width="100" align='right'><span class="ui-color-red">*</span>联系人：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px" name="contactName" id="contactName" required="true" data-options="validType:['vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']']"/></td>
			  		<td width="100" align='right'><span class="ui-color-red">*</span>联系方式：</td>
			      	<td width="142" align='left'><input class="easyui-validatebox ipt" style="width:146px" name="tel" id="tel" required="true" data-options="validType:['vLength[0,20,\'最多只能输入20个字符\']','isValidText[\'包含无效字符\']', 'phoneOrMobile']"/></td>
			  	</tr>	
			  	<tr height="30">
			  		<td width="100" align='right'><span class="ui-color-red">*</span>金额：</td>
			      	<td width="142" align='left'>
				      	<input class="easyui-validatebox easyui-validatebox ipt" style="width:146px" name="amount" id="amount" required="true" onblur="expect_cash.editAmount()"  data-options="precision:2, validType:['maxLength[12]']" />
				      	<input type="hidden" name="unUsedAmount" id="unUsedAmount" data-options="formatter:pointFormatter"/>
				      	<input type="hidden" name="usedAmount" id="usedAmount" data-options="formatter:pointFormatter"/>
			      	</td>
			  	</tr>
			  	<tr height="30">
			      	<td width="100" align='right'>备注：</td>
			      	<td width="142" align='left' colspan="3"><textarea name="remark" id="remark" cols ="53" rows = "3" class="easyui-validatebox ipt" style="width:420px;height:80px" data-options="validType:['vLength[0,255,\'最多只能输入255个字符\']','isValidText[\'包含无效字符\']']"></textarea></td>
			  	</tr> 
			   
			  	<tr height="60">
			  	  	<td width="240" align='right' colspan="4">
			         	<div>
			            	<a id="info_save" href="javascript:expect_cash.add();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
			            	<a id="info_update" href="javascript:expect_cash.modify();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>  
			            	<a id="info_cancel" href="javascript:expect_cash.closeWindow();" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a> 
			        	</div>  
			  	  	</td>
			  	</tr>
			</table>
	 	</form>	
	</div>	
	
</body>
</html>