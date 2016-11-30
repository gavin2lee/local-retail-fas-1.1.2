<!DOCTYPE html>
<html>
<head>
    <title>导入</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script>
		$(function(){
			$('#file').change(function(){
				 $('#errorInfo').text('');
				var file = $('#file').val();
				if(file ==""){
					 $('#errorInfo').text('请选择导入文件');
					 return ;
				}
				var suffix = file.substring(file.lastIndexOf(".")+1,file.length);
			 	if("xlsx" != suffix ){
			 	  $('#errorInfo').text('请选择.xlsx文件');
			 	}
			});
		});
		
	</script>
</head>
<body class="easyui-layout">
	<div>
	  <div style="margin-left:60px;margin-top:25px;height:30px">
		 <form name="uploadForm" action="" id="uploadForm" enctype="multipart/form-data" method="post">
		 	<input type="file"  class="easyui-inputfile" name="file" id="file" readonly="true" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
		 	<input type="hidden" name="balanceType" id="balanceType" value="${balanceType}"/>
		 </form>
	  </div>
	   <div style="margin-left:60px;height:20px;width:220px;text-align:right">
	   		<span id="errorInfo" style="color:red">请选择导入文件<span>
	  </div>
	 </div>
</body>
</html>