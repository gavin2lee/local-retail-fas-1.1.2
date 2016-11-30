<!DOCTYPE html>
<html>
<head>
    <title>简单打印</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<style>
		table,td,th {
			border-width: 1px;
			border-style: solid;
			border-collapse: collapse,cellpadding:1;
			font-size:12px;
		};
	</style>
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/js/LodopFuncs.js?version=${version}>"></script>
	<script type="text/javascript">
			function print_page(ygDialog){
				LODOP=getLodop(); 
				//=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
				if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
					return false;
				}
				LODOP.PRINT_INIT(params.title);
				LODOP.SET_PRINT_PAGESIZE(params.intOrient,0,0,"A4");//第一个参数，0：打印机缺省设置,1：纵向，2：横向
				//表中
				//打印单据内容
				var strStyle="<style>.cell td,.cell th {border-width: 1px;border-style: solid;border-collapse: collapse;font-size:12px}</style>";
				LODOP.ADD_PRINT_HTM(10,10,"95%","95%",strStyle+$('#contentDiv').html());
				LODOP.SET_PRINT_STYLEA(0,"Vorient",3);		
				LODOP.SET_PRINT_STYLE("FontSize",12);
				//表尾
				LODOP.PREVIEW();//预览
			}
	</script>
</head>
<body>
 <div id="contentDiv" style="page-break-after:always">
		<div id="printContentDiv" align="top" style="width:100%">
			<table id="printTable" align="center" border="1" cellspacing="0" cellpadding="1" width="100%" style="border-collapse:collapse" bordercolor="#333333">
				<thead id="tableHeader" style="font-size:10px;">
				</thead>    
				<tbody id="tableBody"  style="font-size:10px;">      
				</tbody>     
			</table>
			<div id="printFoot" style="word-wrap:break-word;">
			</div>
		</div>
</div>
</body>
</html>