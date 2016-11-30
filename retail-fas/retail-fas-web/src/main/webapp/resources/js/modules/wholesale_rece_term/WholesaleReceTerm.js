// 弹出框
function WholesaleReceTermDialog() {
	var $this = this;
	this.initAdd = function() {
		$("#termNo").removeClass("readonly").removeAttr("readonly", true);
		$("#id").val('');
		// 清空明细数据
		$("#dtlDataGridDiv").clearDataGrid(); 
	};
	
	//检查新增的数据是否正确
	this.checkAdd = function() {
		var termNo = $('#termNo').val();
		var name = $('#name').val();
		var companyNo = $('#companyNo').val();
		var flag = true;
		ajaxRequestAsync( BasePath + '/wholesale_rece_term/check_add',{termNo:termNo,name:name},function(count){
			if(count > 0){
				showError("不允许存在重复的条款编码与名称!");
				flag = false;
			}
		});
		if(flag){
			return $this.validateData();
		}
		return false;
	};
	this.checkInitUpdate = function() {
		return true;
	};
	
	this.checkUpdate = function() {
		return $this.validateData();
	};
	
	this.loadedUpdate = function(rowData) {
		if(rowData.status == 1) {
//			showWarn("数据已启用，不允许修改!");
			$("#companyNameOne").combogrid("disable");
			$("#dataFormView input").addClass("readonly").attr("readonly", true);
			var linkbuttons = $("#dtltoolbarView").find("a");
	    	for(var i = 0; i < linkbuttons.length; i++) {
	    		$(linkbuttons[i]).linkbutton('disable');
	    	}
			$("#termNos,name").addClass("readonly").attr("readonly", true);
			$.fas.search({
				dataGridId : "dtlDataGridDivView",
				searchUrl : "/wholesale_rece_term_dtl/get_biz?termNo="+$("#termNos").val()
			});
		}else{
			$("#termNo,name").addClass("readonly").attr("readonly", true);
			$.fas.search({
				dataGridId : "dtlDataGridDiv",
				searchUrl : "/wholesale_rece_term_dtl/get_biz?termNo="+$("#termNo").val()
			});
			$.fas.editIndex = undefined;
		}
		
	};
	
	//校验数据
	this.validateData = function() {
		if($.fas.endEditing()) {
			var dtlRows = $('#dtlDataGridDiv').datagrid('getRows');
			if(dtlRows.length > 0){
				if(dtlRows.length > 3){
					showError("不允许配置多个订货控制、补货控制与发货控制点!");
					return false;
				}
				var iOrder = 0;
				var iSend = 0;
				var iReplenish = 0;
				var flag=false;
				$.each(dtlRows, function(index,item){
					if(item.controlPoint == 0){
						iOrder += 1;
					}else if(item.controlPoint == 1){
						iSend += 1;
					}else if(item.controlPoint == 2){
						iReplenish += 1;
					}
				});
				$.each(dtlRows, function(index,item){
					if( (item.controlPoint==0 || item.controlPoint==2 ) && (null == item.advanceScale || item.advanceScale==''))  {
						flag=true;
					}
				});
				if (flag) {
					showError("订货、补货比例不能为空");
					return false;
				}
				if(iOrder > 1 || iSend > 1 || iReplenish > 1){
					showError("不允许配置多个订货控制、补货控制与发货控制点!");
					return false;
				}
			}
			var rowUnique = $.fas.checkRowUnique({dataGridId:"dtlDataGridDiv",uniqueField:"controlPoint"});
			return rowUnique;
		}
		return true;
	};
}

function buildSubgridUrl(row){
	return "?termNo="+row.termNo;
}

//行编辑
function WholesaleReceTermEditor() {}

var dialog = null, editor = null;
$(function() {
	$.fas.extend(WholesaleReceTermDialog, FasDialogController);
	$.fas.extend(WholesaleReceTermEditor, FasEditorController);
	dialog = new WholesaleReceTermDialog();
	dialog.init("/wholesale_rece_term", fas_common_setting);
	
	editor = new WholesaleReceTermEditor();
	editor.init("/wholesale_rece_term", {
		dataGridDivId : 'dtlDataGrid',
		dataGridId : 'dtlDataGridDiv',
		beforeAdd : function() {
			$("#dtlDataGridDiv").datagrid("removeEditor", "advanceScale");
			$("#dtlDataGridDiv").datagrid("addEditor", {field : "advanceScale", 
				editor : {type:'fasnumberbox',
					options:{
						id:'advanceScale',
						name:'advanceScale',
              			min:0,
              			max:100
					}
				}
			});
		},
		beforeUpdate : function(rowIndex, rowData) {
			$("#dtlDataGridDiv").datagrid("removeEditor", "advanceScale");
			if(rowData.advanceType == '1') {
				$("#dtlDataGridDiv").datagrid("addEditor", {field : "advanceScale", 
					editor : {type:'readonlybox',
						options:{
							id:'advanceScale',
							name:'advanceScale'
						}
					}
				});
			} else {
				$("#dtlDataGridDiv").datagrid("addEditor", {field : "advanceScale", 
					editor : {type:'fasnumberbox',
						options:{
							id:'advanceScale',
							name:'advanceScale',
                  			min:0,
                  			max:100
						}
					}
				});
			}
		}
	});
});