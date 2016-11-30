<style type="text/css">
table{border-collapse:collapse;border:none;}
table tbody tr td{
	  border:1px solid black;
	  font-size:9px;
	  font-weight:normal;
	}
table caption{
		border:0px;
		text-align: center;
		font-size:12px;
		font-weight: bold;
		line-height:35px;
	}
</style>

<#list expectCashs as  expectCash>
	<table class="form-tb"  style="width:350px;font-size:9px;" align="center">
	  <caption>预收款单</caption>
	  <tr>
	    <td width="60" align='center'>类型:</td>
	    <td width="100" align='left'>${expectCash.businessTypeExpectStr}</td>
	    <td width="60" align='center'>类别:</td>
	    <td width="100" align='left'>${expectCash.businessFlagStr}</td>
	  </tr>
	  <tr>
	    <td align='center'>凭证编码:</td>
	    <td align='left'>${expectCash.settleCode}</td>
	    <td align='center'>业务名称:</td>
	    <td align='left'>${expectCash.businessName}</td>
	  </tr>
	  <tr>
	    <td align='center'>操作员:</td>
	    <td align='left'>${expectCash.assistantName}</td>
	    <td align='center'>日期:</td>
	    <td align='left'>
	    	<#if expectCash.businessDate??>
	      		${expectCash.businessDate?string("yyyy-MM-dd")}
	        <#else>
	      	</#if> 
	    </td>
	  </tr>
	  <tr>
	    <td align='center'>客户名称:</td>
	    <td align='left'>${expectCash.customerName}</td>
	    <td align='center'>联系人:</td>
	    <td align='left'>${expectCash.contactName}</td>
	  </tr>
	  <tr>
	    <td align='center'>联系方式:</td>
	    <td align='left'>${expectCash.tel}</td>
	    <td align='center'>金额:</td>
	    <td align='left'>${expectCash.amount}</td>
	  </tr>
	  <tr>
	    <td align='center'>备注:</td>
	    <td align='left' colspan="3">${expectCash.remark}</td>
	  </tr>
	</table>
</#list>