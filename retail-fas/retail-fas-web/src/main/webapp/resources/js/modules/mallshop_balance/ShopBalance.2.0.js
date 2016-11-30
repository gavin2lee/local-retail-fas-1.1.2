// 初始化
$(function(){
	
	//绑定店铺通用查询
	$("#shortName_main").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo_main").val(value);
        }
    });
	
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	
	if(month == 0) {
		year = year - 1;
		month = 12;
	}else		
	   month = month+1;
	
	var month = month < 10 ? '0' + month : month;
	$('#startMonth').datebox('setValue',year + '' + month-1);
	
	
//	$('#saleStartDate').datebox('setValue', year + '-' + month + '-01');
//	var  startMonth = year + '' + month;
//	alert(startMonth);
//	$("#startMonth").val(startMonth);	
//	var  day = new Date(year,month,0);
//	$('#saleEndDate').datebox('setValue', year + '-' + month + '-' + day.getDate()); 

});