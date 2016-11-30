<!DOCTYPE html>
<html>
<head>
    <title>结算单打印</title>
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
				LODOP.PRINT_INIT("结算单");
				LODOP.SET_PRINT_PAGESIZE(2,0,0,"A4");//第一个参数，0：打印机缺省设置,1：纵向，2：横向
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
			<h2 align="center" id ="bill_title">结算单</h2>
			供应商：<span align="left" id ="bill_supplier"></span>
			<table style="width:100%;margin-right:10px;border-collapse:collapse" border="1" cellspacing="0" cellpadding="1" width="100%" bordercolor="#333333">
				<thead id="tableHeader" style="font-size:13px;">
				</thead>    
				<tbody id="tableBody"  style="font-size:13px;">      
				</tbody>  	
			</table>
		</div>
</div>
</body>
</html>