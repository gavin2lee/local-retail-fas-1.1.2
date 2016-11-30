var mallDeductionSet = new Object();

//当前用户
mallDeductionSet.currentUser;

//当前编辑行
mallDeductionSet.editRowIndex = -1;

// 主表模块路径
mallDeductionSet.modulePath = BasePath + '/mall_deduction_set';

//清空
mallDeductionSet.clear = function() {
//	$('#searchForm').form("clear");
	$('#searchForm').find("input").val("");
	$('#searchForm').find("textarea").val("");
};

//查询
mallDeductionSet.search = function() {
	var params = $('#searchForm').form('getData');
	var url = mallDeductionSet.modulePath + '/list.json';
    $('#mallDeductionDataGrid').datagrid('options').queryParams= params;
    $('#mallDeductionDataGrid').datagrid('options').url= url;
    $('#mallDeductionDataGrid').datagrid('load');
};

//新增
mallDeductionSet.add = function() {
	if(mallDeductionSet.endEdit()){
	    $('#mallDeductionDataGrid').datagrid('appendRow',{});
	    var length = $('#mallDeductionDataGrid').datagrid('getRows').length;
		$('#mallDeductionDataGrid').datagrid('beginEdit',length-1);
	    mallDeductionSet.editRowIndex = length-1;
	}
};

//修改
mallDeductionSet.edit = function(rowIndex) {
	if(mallDeductionSet.endEdit()){
		$('#mallDeductionDataGrid').datagrid('beginEdit',rowIndex);
		mallDeductionSet.editRowIndex = rowIndex;
	}
};

//删除
mallDeductionSet.del = function() {
	var delFlag = false;
    var checkedRows = $('#mallDeductionDataGrid').datagrid('getChecked');
    var rowIndex;
    $.each(checkedRows,function(index,row){
    	rowIndex = $('#mallDeductionDataGrid').datagrid('getRowIndex',row);
    	if(row.status == '1'){
      		 showWarn("已启用，不能删除！");
      		 return;
      	    }
    	$('#mallDeductionDataGrid').datagrid('deleteRow',rowIndex);
    	if(mallDeductionSet.editRowIndex == rowIndex){
    		mallDeductionSet.editRowIndex =  -1;
    	}
    });
};


mallDeductionSet.check = function(rowIndex,data){
//	 var checkedRows = $('#mallDeductionDataGrid').datagrid('getChecked');
//	 for(var i =0; i < checkedRows.length; i++){
//			var item = checkedRows[i];
//			var getstatusValue=item.status;
//			alert(getstatusValue);
//			return getstatusValue == 1;
//			};
//			$.each(checkedRows, function(index, row) {
//				alert(row.balanceNo);
//				});
//		};
   alert(date.systemInit);
	var getstatusValue=data.status;// $('#status').combobox('getValue');
	if(getstatusValue == 1){
		showWarn("已启用,不能修改");
		return getstatusValue == 1;
	}
	return getstatusValue == 1;
};

//保存
mallDeductionSet.save = function() {
	
	var boolFlag = true;
	
	if(mallDeductionSet.endEdit()){
	    var url = mallDeductionSet.modulePath + '/save';
	    var insertRows = $('#mallDeductionDataGrid').datagrid('getChanges','inserted');
	    var updatedRows = $('#mallDeductionDataGrid').datagrid('getChanges','updated');
	    var deletedRows = $('#mallDeductionDataGrid').datagrid('getChanges','deleted');
	    var changeRows = {
	    		inserted : JSON.stringify(insertRows),
	    		updated : JSON.stringify(updatedRows),
	    		deleted : JSON.stringify(deletedRows)
	};
	    
    var dataSource;
    if(insertRows.length != 0){
    	dataSource = insertRows;
    }else if(updatedRows.length != 0){
    	dataSource = updatedRows;
    }
    var noteInfo = {
    	result : 'success',
    	warnInfo : '当前编码,名称 数据已存在,不能重复'
    };
	    
    if(typeof dataSource != 'undefined'){
    	
    	$.each(dataSource, function(i,val){
    		
    		var check_url = mallDeductionSet.modulePath + '/checkIsExisting';
    		var id;
    		
    		if(typeof val.id == 'undefined'){
    			id = '';
    		}else{
    			id = val.id;
    		}
    		
    		var paramTemp = {
    				id : id,
    				code : val.code.trim(),
    				name : val.name.trim(),
    				costCode : val.costCode.trim()
    		};
    		var noteInfo = {
    			result : 'success',
    			warnInfo : '当前数据已创建,不能重复'
    		};
    		var flag = mallDeductionSet.commonCheckAjaxRequestAsync(check_url,paramTemp,noteInfo);
    		if(!flag){
    			var rowIndex = $('#mallDeductionDataGrid').datagrid("getRowIndex", val);
    			// 编辑改行数据
    			mallDeductionSet.edit(rowIndex);
    			boolFlag = false;
    		}
    		
    	});
    	
    }
	    
    if(boolFlag){
	    ajaxRequestAsync(url, changeRows, function(result){
	    	if(result){
	    		showSuc('保存成功');
	    		mallDeductionSet.search(); 
	    	}else{
	    		showSuc('保存失败');
	    	}
	    });
    }
	}
};

mallDeductionSet.commonCheckAjaxRequestAsync = function(url,paramType,noteInfo){
	var retResult = false;
	ajaxRequestAsync(url,paramType,function(result){
		if(result == noteInfo.result){
			retResult = true;
		}else{
			showWarn(noteInfo.warnInfo);
		}
	});
	return retResult;
};


//导入
mallDeductionSet.importExcel = function() {

};

// 导出
mallDeductionSet.exportExcel = function() {
	
};

//结束编辑
mallDeductionSet.endEdit = function() {
	if($('#mallDeductionDataGrid').datagrid('validateRow',mallDeductionSet.editRowIndex)){
		  $('#mallDeductionDataGrid').datagrid('endEdit',mallDeductionSet.editRowIndex);
		  return true;
	}
	return false;
};

mallDeductionSet.selectCost = {// 明细行选择总账费用类别
		validatebox:{
	    	options:{
	    		required:true
	    	}
	    },
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择总账费用类别",
				href : mallDeductionSet.modulePath + "/toSearchCost",
	    		fn : function(data, rowIndex) {
	    			mallDeductionSet.selectorCallback(data,'code','name','costCode','costName');
	    		}
	    	});
	    }
};

// 选择后回调
mallDeductionSet.selectorCallback = function(data,value,text,valueField,textField){
	var editIndex = $("tr[class*=datagrid-row-editing]").attr("datagrid-row-index");
	var valueEd = $('#mallDeductionDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : valueField
	});
	var textEd = $('#mallDeductionDataGrid').datagrid('getEditor', {
		'index' : editIndex,
		'field' : textField
	});
	valueEd.target.val(data[value]);
	textEd.target.val(data[text]);
};

mallDeductionSet.GetDate =function(){
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
	mallDeductionSet.initStatusFlag();
});

/**
 * 状态
 */
mallDeductionSet.statusformatter = function(value) {
	for ( var i = 0; i < statusFlag_status.length; i++) {
		if (statusFlag_status[i].value == value) {
			return statusFlag_status[i].text;
		}
	}
};

/**
 * 状态
 */
var statusFlag_status = [ {
	"value" : "0",
	"text" : "停用"
}, {
	"value" : "1",
	"text" : "启用"
} ];

mallDeductionSet.initStatusFlag=function(){
	$('#statusCondition').combobox({
		data : statusFlag_status,
		valueField : 'value',
		textField : 'text',
		editable : false
	});  
};