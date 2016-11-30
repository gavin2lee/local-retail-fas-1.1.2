<!DOCTYPE html>
<html>
<head>
    <title>批发结算单打印</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style>
		table,td,th {
			border-collapse: collapse,cellpadding:1;
			font-size:12px;
		}
		td{
			height:20px;
		}
		tr{
			line-height:20px;
		}
		span{
			width:30px;
		}
		.mgright{
			 margin-right:20px;
		};
	</style>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/js/LodopFuncs.js?version=${version}>"></script>
	<script type="text/javascript">
			function print_page(ygDialog){
				LODOP=getLodop(); 
				//=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
				if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
					return false;
				}
				LODOP.PRINT_INIT("批发结算单");
				LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");//第一个参数，0：打印机缺省设置,1：纵向，2：横向
				//表中
				//打印单据内容
				var strStyle="<style>.cell td,.cell th {border-width: 1px;border-style: solid;border-collapse: collapse;font-size:12px}</style>";
				LODOP.ADD_PRINT_HTM(10,10,"95%","95%",strStyle+$('#contentDiv').html());
				LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
				LODOP.SET_SHOW_MODE ("LANDSCAPE_DEFROTATED",true);	
				LODOP.SET_PRINT_STYLE("FontSize",12);
				//表尾
				LODOP.PREVIEW();//预览
			}
	</script>
</head>
<body>
 <div id="contentDiv" style="page-break-after:always">
		<div id="printContentDiv" style="width:100%">
			<h2 align="center">加盟商对账表</h2>
			<table  style="width:100%;font-size:14px;magin:auto;line-height:30px;" cellspacing="0" cellpadding="2" >
					<col width="10%" />
					<col width="35%" />
					<col width="10%" />
					<col width="15%" />
		            <col width="10%" />
		            <col width="20%" />
				
				<tr>
					<td>供&nbsp;&nbsp;票&nbsp;&nbsp;方:</td> 
					<td id="salerName"></td> 
					<td>销售金额:</td> 
					<td id="outAmount"></td> 
					<td>保&nbsp;&nbsp;证&nbsp;&nbsp;金:</td> 
					<td id="Bond"></td>
				</tr>
				<tr>
					<td>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户:</td> 
					<td id="buyerName"></td>
					<td>扣项金额:</td> 
					<td id="DebitAmount"></td> 
					<td>上月余额:</td> 
					<td id="lastMonthBalance"></td> 
				</tr>
				<tr>
					<td>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td> 
					<td id="balanceStart"></td> 
					<td>返利金额:</td> 
					<td id="rebateAmount"></td> 
					<td>本月汇款:</td> 
					<td id="remittanceThisMonth"></td> 
				</tr>
				<tr>
					<td>销售数量:</td> 
					<td id="sendQty"></td> 
					<td>应收金额:</td> 
					<td id="balanceAmount"></td> 
					<td>月末余额:</td> 
					<td id="endBalance"></td> 
				</tr>
			</table>
		</div>
		<br/><br/><br/>
		
		<div id="centerContentDiv" style="width:100%">
			<table style="width:100%;margin-right:10px;border-collapse:collapse" border="1" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
				<thead id="tableHeader" style="font-size:13px;">
				</thead>    
				<tbody id="tableBody"  style="font-size:13px;">      
				</tbody>  
			</table>
		</div>
		<br/>
		<!-- <div id="downContentDiv" style="width:100%">
			<table  style="width:100%;border-collapse:collapse" border="1" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
				<thead id="tableHeaderSecond" style="font-size:13px;">
				</thead>    
				<tbody id="tableBodySecond"  style="font-size:13px;">      
				</tbody>  
			</table>
		</div>-->
		
		<br/><br/><br/><br/><br/>
		<div id="printFoot" style="word-wrap:break-word;font-size:12px;width:100%"  align="top">
			<!-- <span style="font-size:16px;">销售合计:</span>
			<span style="font-size:16px;" id="balanceAmount"></span>
			<span style="font-size:16px;margin-left:60px;">数量:</span>
			<span style="font-size:16px;" id="sendQty"></span>
			<span style="font-size:16px;margin-left:60px;">返利:</span>
			<span style="font-size:16px;" id="rebateAmount"></span>
			<span style="font-size:16px;margin-left:60px;">保证金:</span>
			<span style="font-size:16px;" id="Bond"></span>
			<span style="font-size:16px;margin-left:60px;">金额:</span>
			<span style="font-size:16px;" id="sendAmount"></span>
			<span style="font-size:16px;margin-left:60px;" id="outAmount">本月发生额:</span>
			<span style="font-size:16px;"></span>
			<br/><br/>-->
			<span style="font-size:16px;">审核:</span>
			<span style="margin-right:140px;font-size:16px;" id="auditor"></span>
			<span style="font-size:16px;">制单:</span>
			<span style="margin-right:140px;font-size:16px;" id="createUser"></span>
			<span style="font-size:16px;">打印时间:</span>
			<span style="font-size:16px;" id="printTime"></span>
		</div>
			
		</div>
	</div>
</body>
</html>