<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>体总请款单</title>
<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/modules/top_sports/applyForPayment.js?version=${version}"></script>
</head>
<body class="easyui-layout">

<div id="mainTab" class="easyui-tabs" data-options="fit:true,plain:true,border:false">
	<div data-options="title:'单据明细'">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false" class="toolbar-region">
	        	 <@p.toolbar id="top_bar" listData=[
		    		{"id":"top_btn_scan","title":"浏览","iconCls":"icon-search","action":"returnTab('mainTab', '单据查询')","type":0},
					{"id":"top_btn_add","title":"新增","iconCls":"icon-add","action":"applyForPayment.add()","type":0},
					{"id":"top_btn_save","title":"保存","iconCls":"icon-save","action":"applyForPayment.save()","type":7},
					{"id":"top_btn_del","title":"删除","iconCls":"icon-del","action":"applyForPayment.del()","type":3},
					{"id":"top_btn_back","title":"上单","iconCls":"icon-prev","action":"applyForPayment.upBill()","type":0} ,
					{"id":"top_btn_next","title":"下单","iconCls":"icon-next","action":"applyForPayment.downBill()","type":0},
					{"id":"top_btn_sure","title":"审核","iconCls":"icon-aduit","action":"applyForPayment.audit(1)","type":31},
					{"id":"top_btn_unsure","title":"反审核","iconCls":"icon-aduit","action":"applyForPayment.audit(0)","type":32}							
				 ]/>	
	        </div>
	        
			<div data-options="region:'center',border:false" style="height:200px;">		
				<div class="easyui-layout" data-options="fit:true" id="subLayout">
					<div data-options="region:'north',border:false">
					  <div class="search-div">
	                     <form name="mainDataForm" id="mainDataForm" method="post">
	                        <input type="hidden" name="id" id="id"/></td>
							<table class="form-tb">
								<col width="100" />
								<col />
								<col width="100" />
								<col />
								<col width="100" />
								<col />	
								<col width="100" />
								<col />							
								<tbody>
								<tr>
									<th>单据编号： </th>
									<td>
										<input class="easyui-validatebox ipt"  name="billNo" id="billNo"  style="width: 160px;"/>
									</td>
									<th>单据状态：</th>
						    		<td>
						    			<input class="easyui-validatebox  ipt"  name="tsStatusName" id="statusNameId" style="width: 150px;"/>
						    			<input type="hidden"  name="status" id="statusId" />
						    		</td>
									<th><span class="ui-color-red">*</span>日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期： </th>
									<td>
										<input class="easyui-datebox  ipt"  name="billDate" id="billDate" />
									</td>
									<th>请款金额： </th>
									<td>
										<input class="easyui-validatebox  ipt" name="allAmount" id="allAmountId" />
									</td>
									</tr>	
									<tr>
										<th><span class="ui-color-red">*</span>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司： </th>
										<td>
											<input class="easyui-company  ipt"  name="buyerName" id="buyerNameId" 
											data-options="queryUrl: BasePath + '/base_setting/company/list.json?params=notGroupLeadRole',required:true,inputNoField:'buyerNoId',inputNameField:'buyerNameId',inputWidth:170,isDefaultData : false"/>
											<input type="hidden" name="buyerNo" id="buyerNoId"/>
										</td>
										<th><span class="ui-color-red">*</span>供&nbsp;&nbsp;应&nbsp;&nbsp;商： </th>
										<td>
											<input class="easyui-supplier ipt"  name="salerName" id="salerNameId" data-options="required:true,inputNoField:'salerNoId',inputNameField:'salerNameId',inputWidth:160"/>
											<input type="hidden" name="salerNo" id="salerNoId" />
										</td>
										<th><span class="ui-color-red">*</span>结算单号： </th>
										<td>
											<input  class="ipt" name="balanceNo"  id="balanceNoId" data-options="required:true"/>  
										</td>
										<th>结算金额： </th>
										<td>
											<input class="easyui-validatebox  ipt" name="balanceAmount" id="balanceAmountId" />
										</td>
									</tr>	
									<tr>
									    <th>币&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</th>
										<td>
											 <input class="easyui-combobox"  name="currencyName" id="currencyNameId"  style="width:170px;"
											  data-options="valueField:'currencyCode',textField:'currencyName',width:'auto',url:BasePath + '/area_balance_common/getCurrency',
												   onSelect: function(rec){    
												   		 $('#currencyNameId').val(rec.name);   
												   		 $('#currencyId').val(rec.currencyCode);   
		   										 	}
											 "/>
											 <input type="hidden" name="currencyNo" id="currencyId"/>
										</td>
									    <th>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注： </th>
										<td>
											<input class="easyui-validatebox ipt"  name="remark"   id="remarkId" style="width: 150px;"/>
										</td>
										<th></th>
							    		<td></td>
							    		<th></th>
							    		<td></td>
									</tr>											
									</tbody>
								</table>
							</form>
					</div>	
				</div>
				
			<div data-options="region:'center',border:false" >		
				<div id="dtlTab"  class="easyui-tabs" data-options="fit:true,plain:true,border:false">
				
				</div>		
			</div>
		 	<div data-options="region:'south',border:false">
                <#include  "/WEB-INF/ftl/common/fas_bill_bottom.ftl">
        	</div>
        	
            </div>
		</div>
            	
	   </div>
	</div>
</div>

</body>
</html>