<!DOCTYPE html>
<html>
<head>
    <title>门店结算单打印</title>
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
			function print_page(ygDialog,istrue){
				LODOP=getLodop(); 
				//=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
				if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
					return false;
				}
				LODOP.PRINT_INIT("门店结算单");
				LODOP.SET_PRINT_PAGESIZE(2,0,0,"A4");//第一个参数，0：打印机缺省设置,1：纵向，2：横向
				//表中
				//打印单据内容
				var strStyle="<style>.cell td,.cell th {border-width: 1px;border-style: solid;border-collapse: collapse;font-size:12px}</style>";
				LODOP.ADD_PRINT_HTM(10,10,"95%","95%",strStyle+$('#contentDiv').html());
				LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
				LODOP.SET_SHOW_MODE ("LANDSCAPE_DEFROTATED",true);	
				LODOP.SET_PRINT_STYLE("FontSize",12);
				//表尾
				if(istrue){//批量打印
					LODOP.PRINT();//直接打印
				}else{
					LODOP.PREVIEW();//预览
				}
			}
	</script>
</head>
<body>
 <div id="contentDiv" style="page-break-after:always">
		<div id="printContentDiv" style="width:100%">
			<table  style="width:100%;font-size:14px;magin:auto;line-height:20px;"  cellspacing="0" cellpadding="2" >
					<col width="6%" />
		            <col width="25%" />
		            <col width="8%" />
		            <col width="18%" />
		            <col width="6%" />
		            <col width="17%" />
		            <col width="6%" />
		            <col width="16%" />
		        <tr align="center">
		        	<th colspan="7" align="center"  style="font-size:16px;">开票结算单</th> 
		        </tr>
				<tr>
					<!--<td>商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;场:</td>
					<td colspan="1" id="mallName"></td>-->
					<td>店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺:</td>
					<td id="shortName"></td>
					<td>品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌:</td>
					<td id="brandName"></td>
					<td>结&nbsp;&nbsp;&nbsp;&nbsp;算&nbsp;&nbsp;&nbsp;&nbsp;期:</td>
					<td id="startEndDate"></td>
					<td colspan="2">已&nbsp;打&nbsp;印&nbsp;次数:</td>
				</tr>
				<tr>
					<td>开&nbsp;&nbsp;票&nbsp;单&nbsp;位:</td>
					<td id="invoiceName"></td>
					<td>纳税人识别号:</td>
					<td id="taxpayerNo"></td>
					<td>合&nbsp;&nbsp;同&nbsp;扣&nbsp;率:</td>
					<td id="contractRateName"></td>
					<td>实&nbsp;&nbsp;&nbsp;际&nbsp;&nbsp;&nbsp;扣&nbsp;率:</td>
					<td id="actualRateName"></td>
				</tr>
				<tr>
					<td>供&nbsp;&nbsp;票&nbsp;单&nbsp;位:</td>
					<td id="companyName"></td>
					<td>发&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;号&nbsp;码:</td>
					<td id="invoiceNo"></td>
					<td>开&nbsp;&nbsp;票&nbsp;日&nbsp;期:</td>
					<td id="invoiceApplyDate"></td>
					<td>开&nbsp;&nbsp;&nbsp;票&nbsp;&nbsp;&nbsp;金&nbsp;额:</td>
					<td id="mallBillingAmount"></td>
				</tr>
				<tr>
					<td>商&nbsp;&nbsp;场&nbsp;报&nbsp;数:</td>
					<td  id="mallNumberAmount"></td>
					<td>系&nbsp;&nbsp;&nbsp;统&nbsp;&nbsp;&nbsp;销&nbsp;售:</td>
					<td id="systemSalesAmount"></td>
					<td>报&nbsp;&nbsp;数&nbsp;差&nbsp;异:</td>
					<td id="salesDiffamount"></td>
					<td>预&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款:</td>
					<td id="usedPrepaymentAmount"></td>
					
				</tr>
				<tr>
					<td>原&nbsp;&nbsp;因&nbsp;备&nbsp;注:</td>
					<td colspan="7" id="remark"></td>
				</tr>
				<tr>
					<td>结&nbsp;&nbsp;算&nbsp;描&nbsp;述:</td>
					<td colspan="7" id="balanceDesc"></td>
				</tr>
			</table>
		</div>
		<br/>
		<div id="contentDiv" style="page-break-after:always">
			<table style="float:left;width:30%;margin-right:10px;border-collapse:collapse" border="1" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
				<thead id="tableHeader" style="font-size:13px;">
				</thead>    
				<tbody id="tableBody"  style="font-size:13px;">      
				</tbody>  
			</table>
			<table  style="width:68%;border-collapse:collapse" border="1" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
				<thead id="tableHeaderSecond" style="font-size:13px;">
				</thead>    
				<tbody id="tableBodySecond"  style="font-size:13px;">      
				</tbody>  
			</table>
			
			<br/><br/>
			<div id="downDiv">
				<table border="1px" style="width:97%;border-collapse:collapse" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
					<thead id="tableHeaderDown" style="font-size:12px;">
					</thead>  
					<tbody id="tableBodyDown"  style="font-size:12px;">    
					</tbody> 
				</table>
			</div>
			
			<br/><br/>
			<!--Foot -->
			<div id="printFoot" style="word-wrap:break-word;font-size:12px;width:100%"  align="top">
				<span style="font-size:16px;">制单:</span>
				<span style="margin-right:180px;font-size:16px;" id="createUser"></span>
				<span style="font-size:16px;">确认:</span>
				<span style="margin-right:180px;font-size:16px;" id="updateUser"></span>
				<span style="font-size:16px;">审核:</span>
				<span style="margin-right:180px;font-size:16px;" id="auditor"></span>
				<span style="font-size:16px;">修改:</span>
				<span id="updateUser" style="font-size:16px;"></span>
				<br/>
				<br/>
				<span style="font-size:18px;font-size:16px;">领导签字:</span><span style="font-size:16px;"></span>
				<span style="margin-left:630px;font-size:16px;">打印:</span>
				<span></span> <span style="font-size:16px;" id="printTime"></span>
			</div>
			
		</div>
	</div>
</body>
</html>