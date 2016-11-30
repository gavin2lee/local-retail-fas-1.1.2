jQuery.BasicDeal = function(common_setting){
	
	var default_setting = {
			searchUrl : "",
			saveUrl : "",
			updateUrl : "",
			delUrl : "",
			datagridId : "dataGridDiv",
			searchFormId : "searchForm",
			formPanelId : "uploadForm",
			dataFormId : "dataForm",
			dialogWidth : 800,
			dialogHeight : 330,
			primaryKey : "id",
			searchBtn : "btn-search",
			clearBtn : "btn-remove",
			addBtn : "btn-add",
			editBtn : "btn-edit",
			delBtn : "btn-del",
			exportBtn : "btn-export",
			exportUrl : "",
			exportTitle : "",
			exportType : "",
			importBtn : "btn-import",
			enableBtn : "btn-enable",
			unableBtn : "btn-unable",
			auditBtn : "btn-audit",
			antiAuditBtn : "btn-anti-audit"
	};
	var setting = $.extend(default_setting,common_setting);
	
	function Initialization(){}
		
	Initialization.prototype.init = function(){
		var _this = this;
		$("#" + setting.searchBtn).on("click",function(){
			_this.searchBtnEvent({
				formId : setting.searchFormId,
				url : setting.searchUrl,
				dataGridId : setting.datagridId
			});
		});
		
		$("#" + setting.clearBtn).on("click",function(){
			_this.clearBtnEvent({
				searchFormId : setting.searchFormId
			});
		});
		
		$("#" + setting.exportBtn).on("click",function(){
			_this.exportExcel({
				formId : setting.searchFormId,
				dataGridId : setting.datagridId,
				url : setting.exportUrl,
				title : setting.exportTitle,
				exportType : setting.exportType
			});	
		});
	};
	
	Initialization.prototype.searchBtnEvent = function(params){
		
		var fromObj = $('#' + params.formId);
		
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm) {
			var fromObjStr = convertArray($("#" + params.formId).serializeArray());
			var queryMxURL = BasePath + params.url;
		
			// 2.加载明细 注意请求方式 restful风格 get
			$("#" + params.dataGridId).datagrid('options').queryParams = eval("("
					+ fromObjStr + ")");
			$("#" + params.dataGridId).datagrid('options').url = queryMxURL;
			$("#" + params.dataGridId).datagrid('load');
			$(":checkbox:eq(0)").attr("checked", false);
		}
	};
	
	Initialization.prototype.clearBtnEvent = function(params){
		
		$('#' + params.searchFormId).form("clear");
		$(':input','#' + params.searchFormId).not(
					':button, :submit, :reset').val('').removeAttr('checked').removeAttr('selected');
	};
	
	Initialization.prototype.exportExcel = function(paramSet){
		
		var fromObj = $('#' + paramSet.formId);
		
		var validateForm = fromObj.form('validate');
		// 1.校验必填项
		if (validateForm == false) {
			return;
		}
		
		var $dg = $("#" + paramSet.dataGridId);
		var params = $dg.datagrid('options').queryParams;
		var grepColumns = $dg.datagrid('options').columns;
		
		var columns = $.grep(grepColumns[0], function(o, i) {
			if ($(o).attr("notexport") == true) {
				return true;
			}
			return false;
		}, true);
		
		var exportColumns = JSON.stringify(columns);
		var url = BasePath + paramSet.url;
		
		
		var dataRow = $dg.datagrid('getRows');

		$("#exportExcelForm").remove();
		$("<form id='exportExcelForm' method='post'></form>").appendTo("body");
		var fromObj = $('#exportExcelForm');
		if (dataRow.length > 0) {
			fromObj.form('submit', {
				url : url,
				onSubmit : function(param) {
					param.exportColumns = exportColumns;
					param.exportSubColumns = null;
					param.fileName = paramSet.title;
					param.exportType = paramSet.exportType || '';
					if(params != null && params != {}) {
						$.each(params, function(i) {
							param[i] = params[i];
						});
					}
				},
				success : function() {

				}
			});
		} else {
			showWarn('查询记录为空，不能导出!');
		}
		
	};
	
	var initialization = new Initialization();
	initialization.init();
};