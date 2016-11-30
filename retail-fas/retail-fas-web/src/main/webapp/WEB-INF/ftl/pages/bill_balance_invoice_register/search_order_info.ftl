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
	                <input type="hidden" name="businessTypeStr" id="businessTypeStr" value="4">
			       		 	<table class="form-tb">
		       		 		 	<col width="100" />
							    <col />
							    <col width="100" />
							    <col />
							    <tbody>
				       		 		<tr> 
								    	<td align="right">公司：</td>
										<td align="left">
											<input class="easyui-validatebox easyui-company ipt"  name="companyName" id="companyName" data-options="required:true"/>
											<input type="hidden"  name="companyNo" id="companyNo" 	/>	
										</td>
										<!--<td align="right">店铺：</td>
								     	<td align="left">
								     		<input class="easyui-validatebox ipt" iptSearch="shop"  name="shopName" id="shopName" data-options="required:true" />
								     		<input type="hidden"  name="shopNo" id="shopNo" />
								     	</td>
								     <tr>
								     </tr>-->
								     	<th>日期：</th>
							    		<td ><input class="easyui-validatebox easyui-datebox ipt"  name="createTimeStart" id="createTimeStart" data-options="required:true,maxDate:'createTimeEnd'"/></td>
										<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<td><input class="easyui-validatebox easyui-datebox ipt" name="createTimeEnd" id="createTimeEnd" data-options="required:true,minDate:'createTimeStart'"/></td>
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
	               	   {field : 'orderNo',title : '单据编号',width : 150}, 
	               	   {field : 'qty',title : '数量',width : 100}, 
	               	   {field : 'allAmount',title : '订单总金额',width : 100} 
	                 ]" 
				/>
			</div>
	    </div>
	</div>
</div>
