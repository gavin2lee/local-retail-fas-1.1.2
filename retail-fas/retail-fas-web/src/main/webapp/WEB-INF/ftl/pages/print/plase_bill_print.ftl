<!DOCTYPE html>
<html>
<head>
	<title>STACCATO 请款单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/js/LodopFuncs.js?version=${version}>"></script>
	<script type="text/javascript">
			function print_page(ygDialog,istrue){
				LODOP=getLodop(); 
				//=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
				if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
					return false;
				}
				LODOP.PRINT_INIT("请款单");
				LODOP.SET_PRINT_PAGESIZE(2,0,0,"A5");//第一个参数，0：打印机缺省设置,1：纵向，2：横向
				//表中
				//打印单据内容
				var strStyle="<style>.cell td,.cell th {border-width: 1px;border-style: solid;border-collapse: collapse;font-size:12px}</style>";
				LODOP.ADD_PRINT_HTM(10,10,"95%","95%",strStyle+$('#boodyDiv').html());
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
	<div id="boodyDiv" style="page-break-after:always;width:250px;">
		<div style="border-bottom:solid;width:100%;" align="center">
			<span style="font-size:25px;"><span id="brandName_EN"></span>&请 款 单 </span>(付款通知单)
		</div>
		<br/>
		<div>
			<span>部门名称:</span><span id="brandName"></span>事业部<span style="margin-left:380px;">单据日期:</span><span id="billDate"></span><br/>
			<table border="1px" style="width:100%;magin:auto;line-height:30px;"  cellspacing="0" cellpadding="2">
					<col width="14%" />
		            <col width="12%" />
		            <col width="12%" />
		            <col width="12%" />
		            <col width="12%" />
		            <col width="10%" />
		            <col width="15%" />
		            <col width="13%" />
				<tr>
					<td>收款单位全称</td>
					<td colspan="5" id="salerName"></td>
					<td>供应商代码</td>
					<td id="salerNo"></td>
				</tr>
				<tr>
					<td>付款方式</td>
					<td colspan="7">
						银行<input type="checkbox" id="bank" name="xx" value="0" style="margin-right:50px;"/>
						现金<input type="checkbox" id="cash" name="xx" value="1" style="margin-right:50px;"/>
					</td>
				</tr>
				<tr>
					<td>账号</td>
					<td colspan="3" id="otherBankAccount"></td>
					<td>开户行</td>
					<td colspan="3" id="otherBank"></td>
				</tr>
				<tr>
					<td>付款金额</td>
					<td colspan="6">人民币(大写):<span id="idCHN"></span></td>
					<td>￥:<span id="allAmount"></td>
				</tr>
				<tr>
					<td>付款事由</td>
					<td style="margin-right:20px;" colspan="7">数量:<span id="qty" style="margin-right:20px;"></span>对/件</td>
				</tr>
				<tr style="line-height:100px">
					<td>备注</td>
					<td colspan="7" id="remark"></td>
				</tr>
			</table>
		</div>
			<span>批准人</span><span style="margin-right:40px;width:40px;"></span>
			<span>会计</span><span style="margin-right:40px;width:40px;"></span>
			<span>审核</span><span style="margin-right:40px;width:40px;"></span>
			<span>部门主管</span><span style="margin-right:40px;width:40px;"></span>
			<span>经办人</span><span style="margin-right:30px;width:40px;"></span>
			<br/>
			<div align="right">
				<span style="align">打印日期:</span><span id="printTime"></span>
			</div>
	</div>
</body>
</html>