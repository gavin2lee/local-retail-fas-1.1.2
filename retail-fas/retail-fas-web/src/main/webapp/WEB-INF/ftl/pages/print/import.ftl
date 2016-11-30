<!DOCTYPE html>
<html>
<head>
    <title>导入</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style>#info{color:red}</style>
	<#include  "/WEB-INF/ftl/common/header.ftl" >
	<script>
		function validate(){
			 var uploadFile = $('#uploadFile').val();
			 var fileListType="xlsx";
			 if(uploadFile==""){
			 	 $('#info').html("请选择{0}文件".format(fileListType));
			 	 parent.$.importExcel.expand(500,360);
				 return false;
			 }
			 var destStr = uploadFile.substring(uploadFile.lastIndexOf(".")+1,uploadFile.length);
			 if(fileListType != destStr){
				 $('#info').html("只允许上传{0}文件".format(fileListType));
				 parent.$.importExcel.expand(500,360);
				 return false;
			 }
			 return true;
		}
		$(function(){
			$.messager.progress('close');
			var errorInfo=unescape("${error!''}");
			if(errorInfo != ""){
				$('#info').html(errorInfo);
				parent.$.importExcel.expand(500,360);
			}else{
				<#if success??>
					$('#info').html('数据导入成功，立即刷新页面');
					parent.$.importExcel.expand(500,360);
					parent.$.importExcel.success("${success!''}");
				</#if>
			}
		});
		
	</script>
</head>
<body class="easyui-layout">
  <br/>
  <div style="margin-left:20px">
	 <form name="uploadForm" action="" onsubmit="return validate()" id="uploadForm" enctype="multipart/form-data" method="post">
	 	 &nbsp;&nbsp;&nbsp;&nbsp选择文件:<input type="file" class="easyui-inputfile" name="fileData" id="uploadFile" readonly="true" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
	 </form>
  </div>
  <br/><br/><br/>
  <div id="error-info" style="padding:10px;">    
	    <p style="margin-left:10px" id="info"></p>
  </div>    
</body>

</html>