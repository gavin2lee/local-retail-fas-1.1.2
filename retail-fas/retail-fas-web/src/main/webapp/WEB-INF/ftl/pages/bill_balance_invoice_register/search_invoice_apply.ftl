<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" class="toolbar-region">
	    <#-- 工具栏  -->
		<@p.toolbar id="toolbar" listData=[
			{"id":"btn-search1-apply","title":"查询","iconCls":"icon-search","type":0} ,
			{"id":"btn-confirm1-apply","title":"确定","iconCls":"icon-ok","type":0} ,
			{"id":"btn-clean1-apply","title":"清空","iconCls":"icon-empty", "type":0}
	     ]/>
	</div>

	<div  data-options="region:'center',border:false">
	    <div class="easyui-layout" data-options="fit:true" id="subLayout">
			<#--搜索start-->
	        <div data-options="region:'north',border:false" >
	            <div class="search-div">
	                <form name="subSearchForm" id="subSearchForm" method="post">
	                	<input type="hidden" name="balanceType" id="invoiceType" value="${invoiceType}">
		       		 	<table class="form-tb">
	       		 		 	<col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <col width="100" />
						    <col />
						    <tbody>
			       		 		<tr>
			       		 			<th>公司名称：</th>
			       		 			<td align="left">
			       		 				<input class="easyui-company ipt"  name="salerName" id="salerNameId" 
			       		 				data-options="inputNoField:'salerNoIds', inputNameField:'salerNameId'" />
										<input type="hidden" name="salerNo" id="salerNoIds"/>
										<input type="hidden" name="status" id="status" value="2"/>
									</td>
			       		 			<th>客户名称：</th>
			       		 			<td align="left">
			       		 				<input class="easyui-customerInvoiceInfo ipt" name="buyerName" id="buyerNameIds" 
			       		 				data-options="inputNoField:'buyerNoIds', inputNameField:'buyerNameIds'" />
										<input type="hidden" name="buyerNo" id="buyerNoIds"/>
									</td>
								</tr>
								<tr>
									<th>申请单号：</th>
			       		 			<td align="left"><input class="easyui-validatebox ipt" name="billNo" id="billNo"/></td>
									<th>品牌： </th>
									<td>
										<input class="easyui-brand  ipt"  name="brandName" id="brandNameId" 
										 data-options="queryUrl: BasePath + '/brand/list.json?status=1',inputNoField:'brandNoId',inputNameField:'brandNameId',inputWidth:130,multiple:true"/>
										<input type="hidden" name="brandNo" id="brandNoId"/>
									</td>
			       		 		</tr>
			       		 	</tbody>
		       		 	</table>
					</form>
	            </div>
	        </div>
		  
		  	<div data-options="region:'center',border:false">
				<@p.datagrid id="searchSourceDG"  loadUrl="" saveUrl=""  defaultColumn="" 
                   isHasToolBar="false" height="415" divToolbar="" onClickRowEdit="false" pagination="true"
                   rownumbers="false" singleSelect = "false"
	               columnsJsonList="[
	               	   {title : '请选择',field : 'chk1',width : 60,checkbox:true}, 
	               	   {field : 'billNo',title : '申请单号',width : 150}, 
	               	   {field : 'salerName',title : '公司名称',width : 200}, 
	               	   {field : 'buyerName',title : '客户名称',width : 200}, 
	               	   {field : 'amount',title : '开票金额',width : 100} ,
	               	   {field : 'preInvoice',title : '预开票',width : 100 ,
	               	   formatter: billCommonInvoiceRegister.preInvoiceformatter}
	                 ]" 
				/>
			</div>
	    </div>
	</div>
</div>
