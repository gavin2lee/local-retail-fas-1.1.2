var shopBalanceDate = new Object();

var date = new Date();
var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

//当前用户
shopBalanceDate.currentUser;

//当前编辑行
shopBalanceDate.editRowIndex = -1;

// 主表模块路径
shopBalanceDate.modulePath = BasePath + '/shop_balance_date';

// 导出
shopBalanceDate.exportExcel = function() {
	$.fas.exportExcel({
		dataGridId : "dtlDataGrid",
		exportUrl : "/shop_balance_date/do_fas_export",
		exportTitle : "商场门店结算期列表导出"
	});
};

//清空
shopBalanceDate.clear = function() {
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
	$('input[type=checkbox]').removeAttr('checked');
};

//查询
shopBalanceDate.search = function() {
	var valid = $("#searchForm").form('validate');
	if(valid == false){
		return;
	}
	var params = $('#searchForm').form('getData');
	var url = shopBalanceDate.modulePath + '/list.json';
    $('#dtlDataGrid').datagrid('options').queryParams= params;
    $('#dtlDataGrid').datagrid('options').url= url;
    $('#dtlDataGrid').datagrid('load');
};


shopBalanceDate.check = function(rowIndex,data){
	var getStatusValue=data.balanceFlag;
	if(data.balanceFlag == 0){
		 showWarn("已生成预估结算单，不能修改！");
		 return true;
	 }
	if(data.balanceFlag == 2){
		showWarn("结算单已生成,不能修改");
		return true;
	}
	if(data.billalready == 2){
		showWarn("已开票,不能修改");
		return true;
	}
	return false;
};

//新增
shopBalanceDate.add = function() {
	
	if(shopBalanceDate.endEdit()){
		
		saveToDB();
		
		$('#dtlDataGrid').datagrid('insertRow', {
			index : 0,
			row : {}
		});
	    $('#dtlDataGrid').datagrid('beginEdit', 0);
	    shopBalanceDate.editRowIndex = 0;
	    
//	    initializationDateField(firstDay,lastDay,0); 
		
	}
	
	 var valueStartDate = $('#dtlDataGrid').datagrid('getEditor', {
			'index' : shopBalanceDate.editRowIndex,
			'field' : 'balanceStartDate'
		});
		
		var valueEndDate = $('#dtlDataGrid').datagrid('getEditor', {
			'index' : shopBalanceDate.editRowIndex,
			'field' : 'balanceEndDate'
		});
		
		check(valueStartDate.target.val(),valueEndDate.target.val());
		
};

//修改
shopBalanceDate.edit = function(rowIndex,data) {
	if(shopBalanceDate.endEdit()){
		
		saveToDB();
		$('#dtlDataGrid').datagrid('beginEdit',rowIndex);
		shopBalanceDate.editRowIndex = rowIndex;
		
		
	}
	var valueStartDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceStartDate'
	});
	
	var valueEndDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceEndDate'
	});
	
	check(valueStartDate.target.val(),valueEndDate.target.val());
};


function appendRowMethod(row){
	
	$('#dtlDataGrid').datagrid('appendRow',{
		companyNo : row.companyNo,
		companyName : row.companyName,
		bsgroupsNo : row.bsgroupsNo,
		bsgroupsName : row.bsgroupsName,
		mallNo : row.mallNo,
		mallName : row.mallName,
		shopNo : row.shopNo,
		shortName : row.shortName,
		//default will be '未结算'
		balanceFlag : 1,
		deductStatus : 1
	});
	
	//edit status
	var length = $('#dtlDataGrid').datagrid('getRows').length;
	shopBalanceDate.editRowIndex = (length-1);
//	shopBalanceDate.edit(length-1);
	
	$('#dtlDataGrid').datagrid('beginEdit',length-1);
    shopBalanceDate.editRowIndex = length-1;
	
};

function spacialCopyWay(checkedRows){
	
	if(checkedRows.length > 1){
		var shopArray = new Array();
		for(var i = 0;i < checkedRows.length;i++){
			var row = checkedRows[i];
			var flag = false;
			var index = undefined;
			for(var j = 0; j < shopArray.length; j++) {
				if(shopArray[j].key == row.shopNo+'|'+row.month) {
					flag = true;
					index = j;
					break;
				}
			}
			var rows = [];
			if(typeof index != 'undefined') {
				rows = shopArray[index].rows;
			}
			rows.push(row);
			if(!flag) {
				shopArray.push({shopNo:row.shopNo,key:row.shopNo+'|'+row.month,rows:rows});
			} else {
				shopArray[index].rows = rows;
			} 
			
		}
		
		for(var i = 0;i < shopArray.length;i++){
			
			var rows = shopArray[i];
			if(rows.rows.length > 1){
				
				var balanceStartDateStr = rows.rows[0].balanceStartDate.split("-");
				var balanceEndDateStr = rows.rows[0].balanceEndDate.split("-");
				
				var smallestTime = new Date(balanceStartDateStr[0],balanceStartDateStr[1],balanceStartDateStr[2]);
				var bigestTime = new Date(balanceEndDateStr[0],balanceEndDateStr[1],balanceEndDateStr[2]);
				
				for(var t = 0;t < rows.rows.length;t++){
					
					var new_startDateStr = rows.rows[t].balanceStartDate.split("-");
					var new_endDateStr = rows.rows[t].balanceEndDate.split("-");
					
					var new_startDate = new Date(new_startDateStr[0],new_startDateStr[1],new_startDateStr[2]);
					var new_endDate = new Date(new_endDateStr[0],new_endDateStr[1],new_endDateStr[2]);
					
					if(new_startDate.getTime() < smallestTime.getTime()){
						smallestTime = new_startDate;
						balanceStartDateStr = (new_startDate.getFullYear() + "-" + new_startDate.getMonth() +"-"+ new_startDate.getDate()).split("-");
					}
					
					if(new_endDate.getTime() > bigestTime.getTime()){
						bigestTime = new_endDate;
						
						balanceEndDateStr = (new_endDate.getFullYear() + "-" + new_endDate.getMonth() +"-"+ new_endDate.getDate()).split("-");
					}
				}
				
				
				var _newDateTimeEnd = getNewDateAfterDays(balanceEndDateStr,1);
				_newDateTimeEnd = AddMonths(_newDateTimeEnd,-1);
				_newDateTimeEnd = _newDateTimeEnd.split("-");
				var year = parseInt(_newDateTimeEnd[0]);
				var month = parseInt(_newDateTimeEnd[1]);
				var day =  parseInt(_newDateTimeEnd[2]);
				_newDateTimeEnd = new Date(year,month,day);
				
				//整月结算期
				if(_newDateTimeEnd.getTime() ==  smallestTime.getTime()){
					
					//起始时间加一个月,结束时间+1天+1月-1天
					for(var t = 0;t < rows.rows.length;t++){
						
						var _row = rows.rows[t];
						var _newStartDate = AddMonths(_row.balanceStartDate,1);
						var _newEndDate = getNewDateAfterDays(AddMonths(getNewDateAfterDays(_row.balanceEndDate,1),1),-1);
						appendRowMethod(_row);
						dealWithStartTimeAndEndTime(_row,_newStartDate,_newEndDate);
						
					}
					
				}else{
					for(var t = 0;t < rows.rows.length;t++){
						appendRowMethod(rows.rows[t]);
						dealWithStartTimeAndEndTime(rows.rows[t],null,null);
					}
				}
				
				/*var day = parseInt(balanceStartDateStr[2]);
				//如果最小时间的天不是当月第一天,即不是正月
				if( day != 1){
					
					for(var t = 1;t <= rows.rows.length;t++){
						appendRowMethod(rows.rows[t]);
						dealWithStartTimeAndEndTime(rows.rows[t],null,null);
					}
					
				}else{
					var year = parseInt(balanceEndDateStr[0]);
					var month = parseInt(balanceEndDateStr[1]);
					var day = parseInt(balanceEndDateStr[2]);
					
					var new_date = new Date(year,month,0);
					
					var new_day = new_date.getDate();
					if(day != new_day){
						for(var t = 0;t < rows.rows.length;t++){
							appendRowMethod(rows.rows[t]);
							dealWithStartTimeAndEndTime(rows.rows[t],null,null);
						}
					}else{
						
						//起始时间加一个月,结束时间+1天+1月-1天
						for(var t = 0;t < rows.rows.length;t++){
							
							var _row = rows.rows[t];
							var _newStartDate = AddMonths(_row.balanceStartDate,1);
							var _newEndDate = getNewDateAfterDays(AddMonths(getNewDateAfterDays(_row.balanceEndDate,1),1),-1);
							appendRowMethod(_row);
							dealWithStartTimeAndEndTime(_row,_newStartDate,_newEndDate);
							
						}
					}
				}*/
				
			}else{
				
				appendRowMethod(rows.rows[0]);
				dealWithStartTimeAndEndTime(rows.rows[0],null,null);
				
			}
			
		}
	}
	else {
		for(var t = 0;t < checkedRows.length;t++){
			var row = checkedRows[t];
	    	appendRowMethod(row);
	    	
	    	//处理结算月,起始时间-结束时间
	    	dealWithStartTimeAndEndTime(row,null,null);
		}
	}
	
};

function checkObjExistsArray(checked,obj){
	
	var flag = false;
	if(checked != null){
		for(var i = 0;i < checked.length;i++){
			if(obj == checked[i]){
				flag = true;
				return flag;
			}
		}
	}
};

function initializationDateField (firstDay,lastDay,index){
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : index,
		'field' : 'shouldBillDate'
	});
	valueEd.target.val(firstDay.format("yyyy-MM-dd"));
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : index,
		'field' : 'incomePaymentsDate'
	});
	valueEd.target.val(lastDay.format("yyyy-MM-dd"));
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : index,
		'field' : 'invoiceShouldSendDate'
	});
	valueEd.target.val(firstDay.format("yyyy-MM-dd"));
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : index,
		'field' : 'invoiceShouldArraiveDate'
	});
	valueEd.target.val(lastDay.format("yyyy-MM-dd"));
};

//复制
shopBalanceDate.copyOneLine = function(){
	var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
    
	if(checkedRows.length < 1){
		showWarn("请选择要复制的结算期设置!");
		return;
	}
	
	//add one more line , and padding the data
	spacialCopyWay(checkedRows);
    	
};

function dealWithStartTimeAndEndTime(row,_newStartDate,_newEndDate){
	
	var length = $('#dtlDataGrid').datagrid('getRows').length;
    var newBalanceStartDate = '';
    var newBalanceEndDate = '';
    
    if(_newStartDate != null){
    	newBalanceStartDate = _newStartDate;
    }else{
    	//起始时间
    	newBalanceStartDate = getNewDateAfterDays(row.balanceEndDate,1);
    }
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : length-1,
		'field' : 'balanceStartDate'
	});
	valueEd.target.val(newBalanceStartDate);
	
	if(_newEndDate != null){
    	newBalanceEndDate = _newEndDate;
    }else {
		//终止时间
		var newDateTimeEnd = getNewDateAfterDays(row.balanceEndDate,1);
		//获取旧结算期间隔天数
		var days = daysBetweenTwoDate(row.balanceStartDate,row.balanceEndDate)+1;
		var year = row.month.substr(0,4);
		var month = row.month.substr(4,2);
		var d = new Date(year,month,0);
		if(days == d.getDate()){
			var newMonthTimeEnd = AddMonths(newDateTimeEnd,1);//推迟一月
			newBalanceEndDate = getNewDateAfterDays(newMonthTimeEnd,-1);//往后递推一月
		}else{
			newBalanceEndDate = getNewDateAfterDays(newDateTimeEnd,days-1);//按指定天数递推
		}
    }
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : length-1,
		'field' : 'balanceEndDate'
	});
	valueEd.target.val(newBalanceEndDate);
	
	//结算月
	var newDate = getMonthOfTheDate(newBalanceEndDate);
//	var newDate = AddMonths(row.month,1);
    
    var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : length-1,
		'field' : 'month'
	});
	valueEd.target.val(newDate);
};



// 截止时间失去焦点事件,自动带出结算月
shopBalanceDate.balanceDateBoxOnBlur = function(balanceEndDateObj){
	
	if(balanceEndDateObj.target.value == ''){
		return;
	}
	
	var valueStartDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceStartDate'
	});
	
	var valueEndDate = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceEndDate'
	});
	
	
	var newDate = getMonthOfTheDate(balanceEndDateObj.target.value);
    
    var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'month'
	});
	valueEd.target.val(newDate);
	
	check(valueStartDate.target.val(),valueEndDate.target.val());
};

function check(startTime,endTime){              
    if(startTime.length>0 && endTime.length>0){     
        var startTmp=startTime.split("-");  
        var endTmp=endTime.split("-");  
        var sd=new Date(startTmp[0],startTmp[1],startTmp[2]);  
        var ed=new Date(endTmp[0],endTmp[1],endTmp[2]);  
        if(sd.getTime()>ed.getTime()){   
            alert("开始日期不能大于结束日期");  
            
            var newBalanceEndDate = '';
            
            if(endTime != null){
            	newBalanceEndDate = endTime;
            }
            
            var vlese='';
            var valueEndDate = $('#dtlDataGrid').datagrid('getEditor', {
        		'index' : shopBalanceDate.editRowIndex,
        		'field' : 'balanceEndDate'
        	});
            
            valueEndDate.target.val(vlese);
            return false;     
        }     
    }     
    return true;     
}    

// 结算月失去焦点事件,自动带出起始时间&结束时间
shopBalanceDate.dateBoxOnblur = function(month){
	
	if(month.target.value == ''){
		return;
	}
	
	var yearMonth = month.target.value;
	var year = parseInt(yearMonth.substr(0,4));
	var month = parseInt(yearMonth.substr(4,6));
	
	var firstdate = year + '-' + month + '-01';  
    var day = new Date(year,month,0);   
    var lastdate = year + '-' + month + '-' + day.getDate();
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceStartDate'
	});
	valueEd.target.val(firstdate);

	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : 'balanceEndDate'
	});
	valueEd.target.val(lastdate);
};

//增加天 
function AddDays(date,value) 
{ 
	date.setDate(date.getDate()+value); 
};

//增加月 
function AddMonths(date,value){ 
	
	var newDate;
	var year;
	var month;
	var day;
	
	if(date == null){
		return null;
	}
	
	if(date.length == 6){
		var monthDay = date.substr(0,4)+'-'+date.substr(4,6)+'-01';
		newDate = nextMonth(monthDay);
		day = 0;
	}else{
		newDate = nextMonth(date);
		day = newDate.getDate();
		if(day < 10){
			day = '0'+day;
		}
	}
	
	year =  newDate.getFullYear();
	month = newDate.getMonth()+value;
	
	//加判断如果归还月分大于12  表示当前月是12月还书日期应该是下一年的1月 
	if(month > 12){
	    month = '0'+1;
	    year = year + 1; 
	}else if(month < 10){
		month = '0'+month;
	}
	
	if(day == 0){
		newDate = year+''+month;
	}else{
		newDate = year+'-'+month+'-'+day;
	}
	return newDate;
};

//在日期的基础上加上天数
function getNewDateAfterDays(dateTemp,days)
{
    //可以加上错误处理
    var dateTime = new Date(dateTemp)
    dateTime = dateTime.valueOf()
    dateTime = dateTime + days * 24 * 60 * 60 * 1000
    dateTime = new Date(dateTime);
    var m = dateTime.getMonth() + 1;
    if(m.toString().length == 1){
        m='0'+m;
    }
    var d = dateTime.getDate();
    if(d.toString().length == 1){
        d='0'+d;
    }
    
    return dateTime.getFullYear() + "-" + m + "-" + d;
};

//获取传入日期的年和月
function getMonthOfTheDate(dateObj){
    var date = dateObj.split("-");
    return date[0]+date[1];
};

//求出两个日期相差天数
function daysBetweenTwoDate(startDate,  endDate){    //sDate1和sDate2是2006-12-18格式  
	
	var splitDate ;
	
	splitDate  =  startDate.split("-");
	var newStartDate = new Date(splitDate[0],splitDate[1],splitDate[2]);
	
	splitDate = endDate.split("-");
	var newEndDate = new Date(splitDate[0],splitDate[1],splitDate[2]);
	
	var dayTimes = newEndDate.getTime() - newStartDate.getTime();
	var days = (dayTimes).toFixed(2)/86400000;
	
	return days;
};

//结束时间特殊处理
function AddMethodEndTime(date,value){
	
	if(date == null){
		return null;
	}
	
	var newDate = nextMonth(date);
	var year;var month;var day;
	
	var partDate = date.split('-');
	var newDay = new Date(partDate[0],partDate[1],0);
	
	if(partDate[2] > newDate.getDate()){
		
		year = parseInt(partDate[0]);
		month = (parseInt(partDate[1])+1);
		var newDayTemp = new Date(partDate[0],month,0);
		day = newDayTemp.getDate();
		
	}else{
		year =  newDate.getFullYear();
		month = newDate.getMonth()+value;
		day = newDate.getDate();
	}
	
	if(day < 10){
		day = '0'+day;
	}
	if(month > 12){
	    month = '0'+1;
	    year = year + 1; 
	}else if(month < 10){
		month = '0'+month;
	}
	return year + '-' + month + '-' + day;
};

//在dateTemp 时间之上加几天,生成加后的日期
function generateNewDate(dateTemp,days)
{
    //可以加上错误处理
    var dateTime = new Date(dateTemp);
    dateTime = dateTime.valueOf();
    dateTime = dateTime + days * 24 * 60 * 60 * 1000;
    dateTime = new Date(dateTime);
    var m = dateTime.getMonth() + 1;
    if(m.toString().length == 1){
        m='0'+m;
    }
    var d = dateTime.getDate();
    if(d.toString().length == 1){
        d='0'+d;
    }
    alert(dateTime.getFullYear() + "-" + m + "-" + d);
};



//起始时间特殊处理
function AddMethodStartTime(date,endDate,value){
	
	if(date == null){
		return null;
	}
	
	var newDate = nextMonth(date);
	var year;var month;var day;
	
	var partDate = date.split('-');
	var newDay = new Date(partDate[0],partDate[1],0);
	
	if(partDate[2] > newDate.getDate()){
		
		if(endDate != null){
			var tempStr = endDate.split('-');
			year = tempStr[0];
			month = parseInt(tempStr[1]);
			day = (parseInt(tempStr[2]) + 1);
		}else{
			year = parseInt(partDate[0]);
			month = (parseInt(partDate[1])+1);
			day = 1;
		}
	}else{
		year =  newDate.getFullYear();
		month = newDate.getMonth()+value;
		day = newDate.getDate();
	}
	
	if(day < 10){
		day = '0'+day;
	}
	if(month > 12){
	    month = '0'+1;
	    year = year + 1; 
	}else if(month < 10){
		month = '0'+month;
	}
	return year + '-' + month + '-' + day;
};

function nextMonth(dtStr){  
   var s = dtStr.split("-");
   var yy = parseInt(s[0]); 
   var mm = parseInt(s[1])-1;
   var dd = parseInt(s[2]);
   var dt = new Date(yy,mm,dd);dt.setMonth(dt.getMonth()+1);
   if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + 1) )
   	{
	   	dt = new Date(dt.getYear(),dt.getMonth(),0);
	}
   return dt;
};

function formatDatebox(value) {
    if (value == null || value == '') {
      return '';
    }
    var dt;
    if (value instanceof Date) {
      dt = value;
    } else {
      dt = new Date(value);
    }
    return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
};


//增加年 
function AddYears(date,value) 
{ 
	date.setFullYear(date.getFullYear()+value); 
}; 

//删除
shopBalanceDate.del = function() {
	
	var delFlag = false;
    var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
    var rowIndex;
    var deletedRows = [];
    var _index = 0;
    
    $.messager.confirm("确认","你确定要删除数据", function(r) {
        if(r) {
            
        	if(!shopBalanceDate.endEdit()){
        		return;
        	}
        	
        	$.each(checkedRows,function(index,row){
            	rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
            	if(row.balanceFlag == '0'){
             		 showWarn("已生成预估结算单，不能删除！");
             		 return;
             	 }
            	if(row.balanceFlag == '2'){
              		 showWarn("已生成结算单，不能删除！");
              		 return;
              	 }
            	index++;
            	deletedRows[index] = row;
            	$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
            	if(shopBalanceDate.editRowIndex == rowIndex){
            		shopBalanceDate.editRowIndex =  -1;
            	}
            	
            });
            
            if(deletedRows.length > 0){
            	
            	var url = shopBalanceDate.modulePath + '/validationRepeat';
            	var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
        	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
        	    var changeRows = {
        	    		deleted : JSON.stringify(deletedRows),
        	    		inserted : JSON.stringify(insertRows),
        	    		updated : JSON.stringify(updatedRows)
        	    };

        	    ajaxRequestAsync(url, changeRows, function(result){
        	    	if(typeof result.error == 'undefined' || result.error == null){
        	    		showSuc('删除成功');
        	    	}else{
        	    		alert(result.error);
        	    		showSuc('删除失败');
        	    	}
        	    });
            	
            }
        	
        }
    });
    
};


shopBalanceDate.validateIsAccurate = function(insertRows,updatedRows,deletedRows){
	
	/**
     * when do the insert or update , check the data is illegal or not first
     */
    	var url_Validation = shopBalanceDate.modulePath + '/validationRepeat';
    	var url = shopBalanceDate.modulePath + '/save';
    	var dataParam ;
    	if(insertRows != null){
    		dataParam = insertRows;
    	}
    	if(updatedRows != null){
    		dataParam = updatedRows;
    	}
    	$.each(dataParam,function(index,dataInfo){
    		
    		if(compareTwoDate(dataInfo.balanceStartDate,dataInfo.balanceEndDate)){
    			var id ;
    			if(typeof dataInfo.id == 'undefined' || dataInfo.id == null){
    				id = '';
    			}else{
    				id = dataInfo.id;
    			}
    			
		    	var reParam = {
		    		id : id,
		    		shopNo : dataInfo.shopNo,
		    		balanceStartDate : dataInfo.balanceStartDate,
		    		balanceEndDate : dataInfo.balanceEndDate,
		    		month : dataInfo.month
		    	};
		    	shopBalanceDate.commonAjaxRequest(url_Validation,reParam,function(resultInfo){
		    		if(resultInfo.error != null){
		    			showWarn(resultInfo.error);
		    			if((index+1) == dataParam.length){
		    				shopBalanceDate.search();
		    			}else{
		    				return false;
		    			}
		    		}else{
		    			
		    			//because this is a circle , every time just can one
		    			var currentRows = new Array();
		    			currentRows[0] = dataInfo;
		    			
		    			if(insertRows != null){
		    				insertRows = currentRows; 
		    			}else{
		    				insertRows = new Array();
		    			}
		    			if(updatedRows != null){
		    				updatedRows = currentRows;
		    			}else{
		    				updatedRows = new Array();
		    			}
		    			
		    			var changeRows = {
		    		    		inserted : JSON.stringify(insertRows),
		    		    		updated : JSON.stringify(updatedRows),
		    		    		deleted : JSON.stringify(deletedRows)
		    		    };
		    		    ajaxRequestAsync(url, changeRows, function(result){
		    		    	
		    		    });
		    		}
		    	});
	    	}
    		
    	});
    	
		showSuc('保存成功');
		shopBalanceDate.search(); 
};


//保存
shopBalanceDate.save = function() {
	if(shopBalanceDate.endEdit()){
	    var url = shopBalanceDate.modulePath + '/validationRepeat';
	    var url_Validation = shopBalanceDate.modulePath + '/validationRepeat';
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    
	    /*if(insertRows.length != 0 || updatedRows.length != 0){
	    	
	    	if(insertRows.length != 0){
	    		shopBalanceDate.validateIsAccurate(insertRows,null,deletedRows);
	    	}
	    	if(updatedRows.length != 0){
	    		shopBalanceDate.validateIsAccurate(null,updatedRows,deletedRows);
	    	}
	    	
	    }else{
	    	var changeRows = {
		    		inserted : JSON.stringify(insertRows),
		    		updated : JSON.stringify(updatedRows),
		    		deleted : JSON.stringify(deletedRows)
		    };
		    ajaxRequestAsync(url, changeRows, function(result){
		    	if(result){
		    		showSuc('保存成功');
		    		shopBalanceDate.search(); 
		    	}else{
		    		showSuc('保存失败');
		    	}
		    });
	    }*/
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(typeof result.error == 'undefined' || result.error == null){
	    		showSuc('保存成功');
	    		shopBalanceDate.search(); 
	    	}else{
	    		alert(result.error);
	    		showSuc('保存失败');
	    		shopBalanceDate.search();
	    	}
	    });
	}
};

function saveToDB(){
	
		shopBalanceDate.endEdit();
	
	 	var url = shopBalanceDate.modulePath + '/validationRepeat';
	 	
	 	var rows = $('#dtlDataGrid').datagrid('getChanges');
	 	if(rows.length < 1){
	 		return;
	 	}
	 	
	    var insertRows = $('#dtlDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#dtlDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#dtlDataGrid').datagrid('getChanges','deleted');
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	    };
//	    alert(JSON.stringify(changeRows));
	    if(deletedRows.length < 1){
		    if(insertRows.length == 0 && updatedRows.length == 0){
		    	return false;
		    }
	    }
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(typeof result.error == 'undefined' || result.error == null){
	    		showSuc('保存成功');
	    		shopBalanceDate.search();
	    		
	    	}else{
	    		alert(result.error);
	    		showSuc('保存失败');
	    		shopBalanceDate.search();
	    	}
	    });
}

//比较两个日期
function compareTwoDate(startDate, endDate) {
    var arr = startDate.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

    var arrs = endDate.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes >= lktimes) {
        showWarn('起始日期不能大于终止日期，请检查');
        shopBalanceDate.search(); 
    }else{    
    	return true;
    }
}

//结束编辑
shopBalanceDate.endEdit = function() {
//	if($('#dtlDataGrid').datagrid('validateRow',shopBalanceDate.editRowIndex)){
//		  $('#dtlDataGrid').datagrid('endEdit',shopBalanceDate.editRowIndex);
//		  return true;
//	}
//	return false;
	var tempObj = $('#dtlDataGrid');
	var rowArr = tempObj.datagrid('getRows');
	tempObj.datagrid("unselectAll");
    for(var i = 0; rowArr && i < rowArr.length; i++) {
    	if(tempObj.datagrid('validateRow', i)) {
    		tempObj.datagrid('endEdit', i);
    	} else {
    		tempObj.datagrid('selectRow', i);
    		return false;
    	}
    }
    return true;
};

// 选择结算主体
shopBalanceDate.selectCompany = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择结算主体',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/company/toSearchCompany',
    		fn : function(data, rowIndex) {
    			shopBalanceDate.selectorCallback(data,'companyNo','name','companyNo','companyName');
    		}
    	});
    }
};

// 选择供应商
shopBalanceDate.selectSupplier = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择供应商',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/supplier/toSearchSupplier',
    		fn : function(data, rowIndex) {
    			shopBalanceDate.selectorCallback(data,'supplierNo','fullName','supplierNo','supplierName');
    		}
    	});
    }
};

//选择内部客户
shopBalanceDate.selectInsideStore = {
	validatebox:{
    	options:{
    		required:true
    	}
    },
    clickFn:function(){
    	dgSelector({
    		title : '选择内部客户',
    		width : 580,
    		height : 450,
    		href : BasePath + '/base_setting/company/toSearchCompany',
    		fn : function(data, rowIndex) {
    			shopBalanceDate.selectorCallback(data,'companyNo','name','insideStoreNo','insideStoreName');
    		}
    	});
    }
};


shopBalanceDate.selectBsgroups  = {// 明细行选择商业集团
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		title:"选择商业集团",
	    		width : 580,
	    		height : 450,
	    		href : BasePath + "/common_util/toSearchBsgroups",
	    		fn : function(data, rowIndex) {
	    			shopBalanceDate.selectorCallback(data,'bsgroupsNo','name','bsgroupsNo','bsgroupsName');
	    		}
	    	});
	    }
	};

shopBalanceDate.selectMall = {// 明细行选择选择商场
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择商场",
				href : BasePath + "/common_util/toSearchMall",
	    		fn : function(data, rowIndex) {
	    			shopBalanceDate.selectorCallback(data,'mallNo','name','mallNo','mallName');
	    		}
	    	});
	    }
};

shopBalanceDate.selectShop = {// 明细行选择选择门店
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择门店",
				href : BasePath + "/common_util/toSearchShop",
	    		fn : function(data, rowIndex) {
//	    			shopBalanceDate.selectorCallback(data,'shopNo','shortName','shopNo','shortName');
	    			var paramTemp = {
	    				shopNo : 'shopNo',
	    				shortName : 'shortName',
	    				companyNo : 'companyNo',
	    				companyName : 'companyName',
	    				bsgroupsNo : 'bsgroupsNo',
	    				bsgroupsName : 'bsgroupsName',
	    				mallNo : 'mallNo',
	    				mallName : 'mallName'
	    			};
	    			shopBalanceDate.selectorShopCallBack(data,paramTemp);
	    		}
	    	});
	    }
};

// 选择后回调
shopBalanceDate.selectorCallback = function(data,value,text,valueField,textField){
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : valueField
	});
	var textEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : textField
	});
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};


shopBalanceDate.selectorShopCallBack = function(data,paramTemp){
	
	var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : paramTemp.shopNo
	});
	var textEd = $('#dtlDataGrid').datagrid('getEditor', {
		'index' : shopBalanceDate.editRowIndex,
		'field' : paramTemp.shortName
	});
	valueEd.target.val(data[paramTemp.shopNo]);
	textEd.target.val(data[paramTemp.shortName]);
	
	var url = BasePath + "/shop/initSubInfo";
	var reqParam = {
		shopNo : data[paramTemp.shopNo]
//		,payType : 'U030301'
	};
	
	shopBalanceDate.commonAjaxRequest(url,reqParam,function(result){
		
		var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
			'index' : shopBalanceDate.editRowIndex,
			'field' : 'companyNo'
		});
		valueEd.target.val(result.companyNo);
		
		$("#companyNo").val(result.companyNo);
		$("#companyName").val(result.companyName);
		
		var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
			'index' : shopBalanceDate.editRowIndex,
			'field' : 'mallNo'
		});
		valueEd.target.val(result.mallNo);
		$("#mallNo").val(result.mallNo);
		$("#mallName").val(result.mallName);
		
		var valueEd = $('#dtlDataGrid').datagrid('getEditor', {
			'index' : shopBalanceDate.editRowIndex,
			'field' : 'bsgroupsNo'
		});
		valueEd.target.val(result.bsgroupsNo);
		$("#bsgroupsNo").val(result.bsgroupsNo);
		$("#bsgroupsName").val(result.bsgroupsName);
	});
};

shopBalanceDate.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async:false,
		dataType : 'json',
		success : callback
	});
};

shopBalanceDate.showPanelToGeneratorBalanceDate = function(){
	$(':input','#myFormPanel').not(
	':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	
	ygDialog({
		title : '批量生成结算期',
		target : $('#myFormPanel'),
		width : 750,
		height : 180,
		onOpen:function() {
			//绑定店铺通用查询
			$("#shopNameTemp").filterbuilder({
		        type:'organ',
		        organFlag: 2,
		        roleType:'bizCity', 
		        onSave : function(result) { 
		        	var value = $(this).filterbuilder('getValue');
		        	$("#shopNoTemp").val(value);
		        }
		    });
		},
		buttons : [{
			text : '生成',
			iconCls : 'icon-save',
			handler : function(dialog) {
				/*var validate = $("#dataForm").form('validate');
				var params = $("#dataForm").form('getData');
				if(!validate){
					return;
				}
				var url = BasePath + "/shop_balance_date/generateBalanceDate";
				ajaxRequestAsync(url, params, function(result){
					if(result.success){
						showSuc('执行成功');
						dialog.close();
						shopBalanceDate.search();
					}else{
						showError(result.errorInfo);
					}
				});*/
				
				var fromObj = $('#dataForm');
				if(!fromObj.form('validate')) {
					return;
				}
				
				var url = BasePath + "/shop_balance_date/generateBalanceDate";
				fromObj.form('submit', {
					url : url,
					dataType : 'json',
					onSubmit : function(param) {
						$.messager.progress({
			    			title:'请稍后',
			    			msg:'正在处理中...'
			    		});
					},
					success : function(data) {
			        	$.messager.progress('close');
			        	showSuc('执行成功');
						dialog.close();
						shopBalanceDate.search();
					},
					error : function(result) {
			        	$.messager.progress('close');
			        	showError(result.errorInfo);
			        }
				});
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
};

shopBalanceDate.GetDate =function(){
	   var date=new Date();
	   var year=date.getYear();
	   var month=date.getMonth();
	   var str='';
	   str+='<table id="date">';
	   str+='<tr>';
	   str+='<td><a href="javascript:void(0)" onclick="DelYear()">< <</a></td>';
	   str+='<td colspan="2"><span id="year">'+year+'</span>年</td>';
	   str+='<td><a href="javascript:void(0)" onclick="AddYear()">> ></a></td>';
	   str+='</tr>';
	   for(i=0;i<3;i++){
	      str+='<tr>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(1+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(2+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(3+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(4+i*4)+'月</td>';
	      str+='</tr>';
	   }
	   str+='</table>';
	   str=str.replace('<td onclick="ChangeMonth(this)" class="td">','<td onclick="ChangeMonth(this)" class="td" style="color:red">');
	   document.write(str);
	}
	function AddYear(){
	   var year=document.getElementById("year").innerHTML;
	   year=Number(year)+1;
	   document.getElementById("year").innerHTML=year;
	}
	function DelYear(){
	   var year=document.getElementById("year").innerHTML;
	   year=Number(year)-1;
	   document.getElementById("year").innerHTML=year;
	}
	function ChangeMonth(obj){
	   var trs=document.getElementById("date").getElementsByTagName("tr");
	   for(i=1;i<trs.length;i++){
	      var tds=trs[i].getElementsByTagName("td");
	      for(j=0;j<tds.length;j++){
	          tds[j].style.color="";
	      }
	   }
	   obj.style.color="red";
	}
 
	
	function timeFormatter(date){
        return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
    }
    function timeParser(date){
        return new Date(Date.parse(date.replace(/-/g,"/")));
    }
    
    
// 初始化
$(function(){
	//绑定店铺通用查询
	$("#shopNames_").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNos_").val(value);
        }
    });

});

shopBalanceDate.importOperation = function(){
	$.importExcel.open({
		'submitUrl' : BasePath + '/shop_balance_date/do_import',
		'templateName' : '结算期管理导入.xlsx',
		success : function(result) {
			$.messager.progress('close');
			if (result) {
				if (isNotBlank(result.error)) {
					showError(result.error);
				} else {
					$.importExcel.colse();
					showSuc('数据导入成功');
					shopBalanceDate.search();
				}
			} else {
				showInfo('导入失败,请联系管理员!');
			}
		},
		error : function() {
			$.messager.progress('close');
			showWarn('数据导入失败，请联系管理员');
		}
	});
	
};

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
};

/**
 * 是否已结算状态
 */
shopBalanceDate.balanceFlagformatter = function(value) {
	
	if (typeof value == 'undefined') {
		return balanceFlag_status[0].text;
	}
	
	for ( var i = 0; i < balanceFlag_status.length; i++) {
		if (balanceFlag_status[i].value == value) {
			return balanceFlag_status[i].text;
		}
	}
};


/**
 * 是否已开票状态
 */
shopBalanceDate.billFlagformatter = function(value) {
	
	/*if (typeof value == 'undefined') {
		return billFlag_status[0].text;
	}*/
	
	for ( var i = 0; i < billFlag_status.length; i++) {
		if (billFlag_status[i].value == value) {
			return billFlag_status[i].text;
		}
	}
};

/**
 * 是否已结算状态
 */
var balanceFlag_status = [ 
{
	"value" : "-1",
	"text" : "未生成"  //预估
},
{
	"value" : "0",
	"text" : "已生成预估"
},
 {
	"value" : "1",
	"text" : "未生成"  //正式结算单
}, {
	"value" : "2",
	"text" : "已生成"
} ];

/**
 * 是否已开票
 */
var billFlag_status = [ {
	"value" : "1",
	"text" : "未开票"
}, {
	"value" : "2",
	"text" : "已开票"
} ];




var deduct_status = [ {
	"value" : "0",
	"text" : "不生成"
}, {
	"value" : "1",
	"text" : "生成"
} ];

shopBalanceDate.deductStatus = function(value) {
	if (typeof value == 'undefined') {
		return deduct_status[1].text;
	}
	for ( var i = 0; i < deduct_status.length; i++) {
		if (deduct_status[i].value == value) {
			return deduct_status[i].text;
		}
	}
};