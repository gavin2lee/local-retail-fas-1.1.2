var depositCash = new Object();
//当前用户
var currentUser;

$(function() {
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
	$("#s").hide();
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#startOutStart").val(firstDay.format("yyyy-MM-dd"));
	$("#endOutEnd").val(lastDay.format("yyyy-MM-dd"));
	
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
	// 给保存按钮绑定函数事件
	$("#btn-save").on('click', function() {
		depositCash.save();
	});
});

depositCash.editRowIndex = -1;

//根据存现核对状态显示操作信息，实现处理
depositCash.operate = function(value,row,index) {
	var result='';
	if(row.id != undefined){
		if(row.status == '1'){
			result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>反确认</a>';
		}else{
			result = result + '<a href=javascript:void(0) id='+index+' onclick=operate('+index+','+index+') style=cursor:pointer;text-decoration:underline;color:blue;>确认</a>';
		}
	}
	
	return result;
}

//跳转处理进行确认反确认操作
function operate(id,i) {
	var rows=$("#dataGridDiv").datagrid('getRows');
	var row=rows[i];
	if($("#"+id).text() == '确认'){
		$("#"+id).text("反确认");
		row.status=1;
		row.auditor=currentUser.loginName;
		row.auditTime=new Date().format("yyyy-MM-dd hh:mm:ss");
	}else if($("#"+id).text() == '反确认'){
		$("#"+id).text("确认");
		row.status=0;
		row.auditor=null;
		row.auditTime=null;
	}
	//更新行数据
	$("#dataGridDiv").datagrid('updateRow',{index:i,row:row});
	var updateRows=[row];
	var changeRows = {
			updated : JSON.stringify(updateRows)
	};
	//保存到数据库
	depositCash.saveToDB(BasePath + '/deposit_cash/do_save', changeRows);
}

//批量进行确认或反确认操作
depositCash.batchOperate = function(status){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
	if(checkedRows.length == 0){
		showWarn("请选择操作的记录");
		return ;
	}
	var message = "";
	var changeRows = [];
	for(var i=0;i<checkedRows.length;i++){
		var row=checkedRows[i];
		if(status==row.status&&status==1){
			showWarn("不能确认财务已确认记录!!");
			return ;
		}else if(status==row.status&&status==0){
			showWarn("不能反确认财务未确认记录!!");
			return ;
		}
	}
	for(var i=0;i<checkedRows.length;i++){
		var row = checkedRows[i];
		if(row.id){
			changeRows.push(row);
		}
	}
	
	var tips = "";
	if(status == 1){
		tips = "确认"
	}else if(status == 0){
		tips = "反确认";
	}
	$.messager.confirm("确认","确定要"+tips+"选中记录吗?",function(r) {
		if (r) {
			$.each(changeRows, function(index, row) {
				row.status = status;
			});
			var data = {
					updated : JSON.stringify(changeRows),
			};
			ajaxRequestAsync(BasePath + '/deposit_cash/do_save',data,function(result){
				if(result){
					showSuc('操作成功');
					$('#dataGridDiv').datagrid('reload');
				}else{
					showError('操作失败');
				}
			});
		}
	});
};

//编辑
depositCash.edit = function (rowIndex, data) {
	if (depositCash.endEdit()) {
		$('#dataGridDiv').datagrid('beginEdit', rowIndex);
		depositCash.editRowIndex = rowIndex;
		$("#shopName_").combogrid("disable");
	}
}

//保存
depositCash.save = function() {
	if (depositCash.endEdit()) {
		if (!depositCash.validationBeforeSave()) {
			return;
		}

		var url = BasePath + '/deposit_cash/save';
		var updatedRows = $('#dataGridDiv').datagrid('getChanges',
				'updated');
		var changeRows = {
			updated : JSON.stringify(updatedRows)
		};
		depositCash.saveToDB(url, changeRows);
	}
};

depositCash.validationBeforeSave = function() {

	var flag = true;
	var updatedRows = $('#dataGridDiv').datagrid('getChanges',
			'updated');

	var dataRows;
	if (updatedRows.length > 0) {
		dataRows = updatedRows;

		$.each(dataRows, function(i, row) {
			if (row.companyNo == null || row.companyNo == "") {
				flag = false;
				showWarn('当前添加的数据中,公司数据不完整,请完善后 重试.');
			}
			if (!flag) {
				$('#dataGridDiv').datagrid('beginEdit',
						depositCash.editRowIndex);
				depositCash.editRowIndex = $('#dataGridDiv')
						.datagrid('getRowIndex', row);
				return false;
			}
		});
	}
	return flag;
};

depositCash.saveToDB = function(url, changeRows) {
	depositCash.commonAjaxRequest(url, changeRows, function(result) {
		if (result) {
			showSuc('保存成功');
			depositCash.search();
		} else {
			showSuc('保存失败');
		}
	});
};

//common ajax request 
depositCash.commonAjaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		async : false,
		dataType : 'json',
		success : callback
	});
};

//end the current edit row
depositCash.endEdit = function() {
	if ($('#dataGridDiv').datagrid('validateRow',
			depositCash.editRowIndex)) {
		$('#dataGridDiv').datagrid('endEdit',
				depositCash.editRowIndex);
		return true;
	}
	return false;
};

depositCash.removeCheckbox = function(data) {
	var r = data.rows;
	for(var i=r.length-1;i>=0;i--){
		if(!r[i].id){
			$(".datagrid-btable input:checkbox").eq(i).remove();
		}
	}
}

/**
 * 扩展方法，鼠标经过时提示单元格内容
 */
$.extend($.fn.datagrid.methods, {
	/**  
     * 开打提示功能  
     * @param {} jq  
     * @param {} params 提示消息框的样式  
     * @return {}  
     */  
    doCellTip: function(jq, params){
    	function showTip(data, td, e) {
			if ($(td).text() == "")
				return;
			data.tooltip.text($(td).text()).css({
				top : (e.pageY + 10) + 'px',
				left : (e.pageX + 20) + 'px',
				'z-index' : $.fn.window.defaults.zIndex,
				display : 'block'
			});
		};
		return jq.each(function(){
			var grid = $(this);
            var options = $(this).data('datagrid');
		    if (!options.tooltip) {
				var panel = grid.datagrid('getPanel').panel('panel');
				var defaultCls = {
					'border' : '1px solid #333',
					'padding' : '2px',
					'color' : '#333',
					'background' : '#f7f5d1',
					'position' : 'absolute',
					'max-width' : '200px',
					'border-radius' : '4px',
					'-moz-border-radius' : '4px',
					'-webkit-border-radius' : '4px',
					'display' : 'none'
				};
				var tooltip = $("<div id='celltip'></div>").appendTo('body');
				tooltip.css($.extend({}, defaultCls,params.cls));
				options.tooltip = tooltip;
				panel.find('.datagrid-body').each(function() {
					var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
					var css = 0 || ["td > div.datagrid-cell.datagrid-cell-c3-cashDiff","td > div.datagrid-cell.datagrid-cell-c3-remark"];
					$.each(css, function(i, str){
						$(delegateEle).undelegate(str,'mouseover').undelegate(str,'mouseout').undelegate(str,'mousemove').delegate(str,
						{
							'mouseover' : function(e) {
								if (params.delay) {
									if (options.tipDelayTime)
										clearTimeout(options.tipDelayTime);
									var that = this;
									options.tipDelayTime = setTimeout(function() {
												showTip(options,that,e);
									},params.delay);
								} else {
									showTip(options,this,e);
								}
							},
							'mouseout' : function(e) {
								if (options.tipDelayTime)
									clearTimeout(options.tipDelayTime);
								options.tooltip.css({'display' : 'none'});
							},
							'mousemove' : function(e) {
								var that = this;
								if (options.tipDelayTime)
									clearTimeout(options.tipDelayTime);
								//showTip(options, this, e);  
								options.tipDelayTime = setTimeout(function() {showTip(options,that,e);},params.delay);
							}
						});
					});
				});
			}
		});
    }
});

