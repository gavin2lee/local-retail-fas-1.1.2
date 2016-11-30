/*-----用于地区间、地区自购、总部代采、地区其他出库结算-------*/
var defalutData = {
	modulePath : '',
	mainFormId:'mainDataForm',
	searchFormId : 'searchForm',
	balanceType : '',
	mainDgId : 'mainDataGrid',
	startDateId:'startDateCond',
	endDataId:'endDateCond'
};

/**
 * 构造函数
 * @param initData
 * @param tabsParams
 * @returns
 */
function BalanceBillCommon(initData, tabsParams) {
	this.initData = initData || defalutData;
	this.tabsParams = tabsParams;
}

/**
 * 定义原型
 */
BalanceBillCommon.prototype = {
	constructor : BalanceBillCommon,
	curRowIndex : -1,// 单据查询当前选中行的索引
	// 查询
	search : function() {
		var params = $('#' + this.initData.searchFormId).form('getData');
		var url = this.initData.modulePath + '/list.json';
		params.balanceType = this.initData.balanceType;
		$('#' + this.initData.mainDgId).datagrid('options').queryParams = params;
		$('#' + this.initData.mainDgId).datagrid('options').url = url;
		$('#' + this.initData.mainDgId).datagrid('load');
	},

	// 设置tab页签
	setDtlTabs : function() {
		for ( var i = 0; i < _this.tabsParams.length; i++) {
			_this.addDtlTab(_this.tabsParams[i].title,_this.tabsParams[i].tabUrl);
		}

		$('#dtlTab').tabs(
		{
			onSelect : function(title) {
				for ( var j = 0; j < _this.tabsParams.length; j++) {
					if (_this.tabsParams[j].title == title) {
						_this.loadTabDg(_this.tabsParams[j].id,_this.tabsParams[j].listUrl,_this.tabsParams[j].queryParams);
					}
				}
			}
		});
		returnTab('dtlTab', _this.tabsParams[0].title);
	},

	// 添加页签
	addDtlTab : function(title, href) {
		$('#dtlTab').tabs('add', {
			title : title,
			selected : false,
			closable : false,
			href : href
		});
	},

	// 加载各个页签明细
	loadTabDg : function(id, url, params) {
		var billNo = $('#billNo').val();
		if ("" != billNo) {
			params.balanceNo = billNo;
			setTimeout(function() {
				$('#' + id).datagrid('options').queryParams = params;
				$('#' + id).datagrid('options').url = url;
				$('#' + id).datagrid('load');
			}, 500);
		}
	},

	// 初始化单据查询
	addMainTab : function() {
		$('#mainTab').tabs('add', {
			title : '单据查询',
			selected : false,
			closable : false,
			href : _this.initData.modulePath + '/list_tabMain.htm',
			onLoad : function(panel) {
				$("#"+_this.initData.startDateId).datebox('setValue',_this.getStartDate().format("yyyy-MM-dd"));
				$("#"+_this.initData.endDataId).datebox('setValue',_this.getEndDate().format("yyyy-MM-dd"));
			}
		});
	},

	//新增
	add:function(){
		$('#'+this.initData.mainFormId).form("clear");
		$('#'+this.initData.mainFormId+' input').removeClass("readonly").removeAttr("readonly",true).removeClass("disabled");
		$('#'+this.initData.mainFormId).find("input[class*=easyui-combobox]").combobox("enable");
		$('#'+this.initData.mainFormId).find("input[class*=easyui-datebox]").datebox('enable');
		_this.initAdd();
		_this.clearTabsData();
		_this.setCurrency();
	},
	
	//新增初始化
	initAdd : function() {
	},
	
	// tabs明细和页脚
	clearTabsData : function() {
		$('#bottombarMx').find('span').text("");
		for ( var i = 0; i < this.tabsParams.length; i++) {
			this.clearDtlData(this.tabsParams[i].id);
		}
	},

	// 清空tabs明细
	clearDtlData : function(id) {
		if ($('#' + id).length > 0) {
			var rows = $('#' + id).datagrid('getRows');
			if (rows) {
				var cloneRows = rows.slice(0);
				for ( var i = 0; i < cloneRows.length; i++) {
					$('#' + id).datagrid('deleteRow', 0);
				}
				$('#' + id).datagrid('acceptChanges');
			}
		}
	},
	
	// 设置默认币种
	setCurrency : function() {
		$("#currencyNameId").combobox(
		{
			onLoadSuccess : function() {
				var currencies = $("#currencyNameId").combobox("getData");
				for ( var i = 0; i < currencies.length; i++) {
					if (currencies[i]["standardMoney"] == 1) {
						$("#currencyNameId").combobox("setValue",currencies[i]["currencyName"]);
						$("#currencyId").val(currencies[i]["currencyCode"]);
					}
				}
			}
		});
	},
	
	//禁用mainForm
	disableMainInfo:function(){
		$('#'+this.initData.mainFormId+' input').attr("readonly", true).addClass("readonly");
		$('#'+this.initData.mainFormId).find("input[class*=easyui-combobox]").combobox('disable');
		$('#'+this.initData.mainFormId).find("input[class*=easyui-datebox]").datebox('disable');
	},
	
	//保存之前的操作
	beforeSave:function(){
		
	},
	
	//保存
	save : function() {
		var status = $('#statusId').val();
		var billNo = $('#billNo').val();
		if ($('#' + this.initData.mainFormId).form('validate')) {
			if (billNo != '') {
				if (0 < status && status < 99) {
					showInfo('已经确认,不能修改');
					return;
				}
				_this.doPut();
			} else {
				_this.saveBill();
			}
		}
	},

	// 新增保存验证
	doPost:function(){
		var fromObj = $('#' + this.initData.mainFormId);
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		var params = fromObj.form('getData');
		var url = this.initData.modulePath + '/check_data';
		ajaxRequestAsync(url, params, function(data) {
			if (data.balanceAmount == null) {
				showInfo('没有业务发生');
			} else {
				if (data.balanceAmount <= 0 ) {
					showInfo("结算金额小于等于零,不能生成结算单");
					return;
				} else {
					_this.saveBill();
				}
			}
		});
	},
	
	// 保存
	saveBill:function(){
		var fromObj = $('#' + this.initData.mainFormId);
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		fromObj.form('submit', {
			url : _this.initData.modulePath + '/save_bill',
			onSubmit : function(param) {
			},
			success : function(data) {
				if (data) {
					var jsonObj = JSON.parse(data);
					$.fas.showMsg('保存成功，结算单号为：'+jsonObj.billNo,5000);
					_this.loadFormData(jsonObj,jsonObj.status);
					_this.afterSaved(_this.tabsParams[0].id,_this.tabsParams[0].listUrl,{},jsonObj);
					_this.search();
				} else {
					showInfo('没有业务发生，请重新核对！');
				}
			}
		});
	},
	
	//修改
	doPut : function() {
		var fromObj = $('#' + this.initData.mainFormId);
		fromObj.form('submit', {
			url : _this.initData.modulePath + '/update_bill',
			onSubmit : function(param) {
			},
			success : function(data) {
				if (data) {
					$.fas.showMsg('修改成功',5000);
					var jsonObj = JSON.parse(data);
					_this.loadFormData(jsonObj,jsonObj.status);
					_this.afterSaved(_this.tabsParams[0].id,_this.tabsParams[0].listUrl,{},jsonObj);
					_this.search();
				} else {
					showError('保存失败');
				}
			}
		});
	},
	
	//选单保存
	selectSave:function(){
		var _self=new Object();
		ygDialog({
			title : '选单界面',
			href : _this.initData.modulePath + '/to_select_bill',
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			maximized : true,
			top : 0,
			showMask : true,
			buttons : [ {
				id : 'sure',
				text : '生成结算单',
				handler : function(dialog) {
					if(_self.validateBalance()){
		            	var params = _self.getMainData();
		            	var checkedRows = _self.getCheckBill();
		            	params.checkedRows = JSON.stringify(checkedRows);
		            	ajaxRequestAsync(_this.initData.modulePath + '/select_save_bill', params ,function(data){
		        			if (data.flag==true) {
		        				dialog.close();
		        				$.fas.showMsg('生成成功，结算单号为：'+data.bill.billNo,5000);
		        				_this.loadFormData(data.bill,data.bill.status);
		        				_this.afterSaved(_this.tabsParams[0].id,_this.tabsParams[0].listUrl,{},data.bill);
		        				_this.search();
		        			}else{
		        				showInfo("生成失败，请重新核对！");
		        			}
		            	});
	            	}
				}
			}, {
				text : '取消',
				handler : function(dialog) {
					dialog.close();
				}
			} ],
			onLoad : function(win, content) {
				_self.getCheckBill = content.getCheckBill;
				_self.getMainData = content.getMainData;
				_self.validateBalance = content.validateBalance;
			},
		});
	},
	
	// 批量新增之前的处理
	beforeBatchAdd : function() {
	},
	
	//转到批量生成界面
	toBatchAdd : function() {
		_this.beforeBatchAdd();
		ygDialog({
			title : '批量生成界面',
			target : $('#myFormPanel'),
			width : 400,
			height : 300,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(dialog) {
					_this.batchSave();
					$('#dataForm').form('clear');
				}
			}, {
				text : '取消',
				iconCls : 'icon-cancel',
				handler : function(dialog) {
					dialog.close();
					$('#dataForm').form('clear');
				}
			} ]
		});
	},
	
	//批量保存
	batchSave : function() {
		var fromObj = $('#dataForm');
		var validateForm = fromObj.form('validate');
		if (validateForm == false) {
			return;
		}
		fromObj.form('submit', {
			url : _this.initData.modulePath + '/batch_save_bill',
			onSubmit : function(param) {
			},
			success : function(data) {
				var jsonObj=JSON.parse(data);
				if (jsonObj.flag==true) {
					$('#myFormPanel').dialog('close');
					$.fas.showMsg('生成成功!',5000);
					_this.search();
				} else {
					showWarn('没有业务发生,不能生成结算单！');
					_this.toBatchAdd();
				}
			}
		});
	},
	
	// 保存回调
	afterSaved : function(dgId, url, params, jsonObj) {
	},
	
	// 双击加载明细
	loadDetail : function(rowIndex, rowData) {
		this.curRowIndex = rowIndex;
		_this.loadFormData(rowData, rowData.status);
		_this.afterLoadForm(rowData);
		_this.afterLoadData(rowData);
	},
	
	afterLoadForm:function(rowData){
	},
	
	afterLoadData:function(rowData){
		_this.loadTabDg(this.tabsParams[0].id,this.tabsParams[0].listUrl,this.tabsParams[0].queryParams);	
		returnTab('mainTab', '单据明细');
		returnTab('dtlTab', this.tabsParams[0].title);
	},
	
	// 加载明细
	loadFormData : function(rowData, status) {
		$('#'+this.initData.mainFormId).form('clear');
		$('#'+this.initData.mainFormId).find("input").val('');
		if (status == 0 || status == 99) {
			$('#'+this.initData.mainFormId).form('load', rowData);
			$('#'+this.initData.mainFormId+' input').removeClass("readonly").removeAttr("readonly",true).removeClass("disabled");
			$('#'+this.initData.mainFormId).find("input[class*=easyui-combobox]").combobox('disable');
			$('#'+this.initData.mainFormId).find("input[class*=easyui-datebox]").datebox('disable');
			_this.setFormClass();
		}else{
			$('#'+this.initData.mainFormId).form('load', rowData);
			$('#'+this.initData.mainFormId+' input').addClass("readonly").attr("readonly", true);
			$('#'+this.initData.mainFormId).find("input[class*=easyui-combobox]").combobox('disable');
			$('#'+this.initData.mainFormId).find("input[class*=easyui-datebox]").datebox('disable');
		}
		_this.afterSetClass(rowData);
		_this.setFooter(rowData);
	},
	
	setFormClass:function(){
	},
	
	afterSetClass:function(rowData){
	},
	
	// 底部单据状态显示栏
	setFooter : function(rowData) {
		$('#createUser').html(rowData.createUser);
		$('#createTime').html(rowData.createTime);
		$('#auditor').html(rowData.auditor);
		$('#auditTime').html(rowData.auditTime);
	},
	
	//单个删除
	del : function() {
		var $billNo = $("#billNo").val();
		var currStatus=$('#statusId').val();
		if (currStatus != 0 && currStatus != 99) {
			showInfo("只允许删除制单、打回状态的单据！");
			return;
		}
		if ($billNo) {
			$.messager.confirm("确认", "确定要删除当前单据?", function(r) {
				if (r) {
					var url = _this.initData.modulePath + '/delete_bill';
					var params = {
						idList : $billNo,
					};
					ajaxRequestAsync(url, params, function(data) {
						if (data > 0) {
							$.fas.showMsg('删除成功',5000);
							_this.add();
							_this.search();
						} else {
							showError('删除失败');
						}
					});
				}
			});
		}
	},

	//批量删除
	batchDel : function() {
		var checkedRows = $('#'+this.initData.mainDgId).datagrid('getChecked');
		var errorMessage = "";
		if (checkedRows.length == 0) {
			errorMessage = "请勾选需要操作的单据";
		} else {
			$.each(checkedRows, function(index, cVal) {
				if (cVal.status != 0 && cVal.status != 99) {
					errorMessage = "只能删除制单、打回状态的单据！";
					return false;
				}
			});
		}
		if (errorMessage != "") {
			showInfo(errorMessage);
			return;
		}	
		if (checkedRows) {
			$.messager.confirm("确认", "确定要删除选中的单据？", function(r) {
				if (r) {
					var url = _this.initData.modulePath + '/delete_bill';
					var ids_data = "";
					$.each(checkedRows, function(index, row) {
						ids_data += row.billNo + ",";
					});
					var params = {
						idList : ids_data.substring(0, ids_data.length - 1)
					};
					ajaxRequestAsync(url, params, function(data) {
						if (data > 0) {
							$.fas.showMsg('删除成功',5000);
							_this.search();
						} else {
							showError('删除失败');
						}
					});
				}
			});
		}
	},
	
	// 状态判断
	getTipsMsg : function(currStatus, operStatus) {
	},
	
	// 批量操作
	getMsgOfBatch:function(checkRows, operStatus,statusFlag) {
		if (checkRows.length == 0) {
			return "请勾选需要操作的单据";
		}
		var msg = "";
		$.each(checkRows, function(index, data) {
			if (statusFlag != '' && statusFlag == '1') {
				msg = _this.getTipsMsg(data.extendStatus, operStatus);
			} else {
				msg = _this.getTipsMsg(data.status, operStatus);
			}
			if (msg != "") {
				return false;
			}
		});
		return msg;
	},
	
	//打回状态判断
	getRedoMsg : function(operStatus, askPaymentNo, invoiceApplyNo, invoiceNo,currStatus) {
	},
	
	// 批量打回
	getMsgOfRedoBatch:function(checkRows, operStatus) {
		if (checkRows.length == 0) {
			return "请勾选需要操作的单据";
		}
		var msg = "";
		$.each(checkRows, function(index, row) {
			msg = _this.getRedoMsg(operStatus,row.askPaymentNo,row.invoiceApplyNo,row.invoiceNo,row.status);
			if (msg != "") {
				return false;
			}
		});
		return msg;
	},
	
	//单个审核
	audit : function(operStatus,operMsg) {
		var currStatus=$('#statusId').val();
		//审核、删除判断
		var msg = _this.getTipsMsg(currStatus,operStatus);
		if(msg!=""){
			showInfo(msg);
			return ;
		}
		//打回判断
		var backMsg =_this.getRedoMsg(operStatus,$("#askPaymentNo").val(),$("#invoiceApplyNo").val(),$("#invoiceNo").val(),currStatus);
		if (backMsg != "") {
			showInfo(backMsg);
			return ;
		}
		$.messager.confirm("确认", "确定"+operMsg+"当前单据?", function(r) {
			if (r) {
				var url = _this.initData.modulePath + '/audit';
				var params = {
					billNo : $("#billNo").val(),
					operStatus : operStatus,
					status : currStatus
				};
				ajaxRequestAsync(url, params, function(data) {
					if (typeof data !="undifined") {
						$.fas.showMsg('操作成功',5000);
						_this.loadFormData(data,data.status);
						_this.afterLoadForm(data);
						_this.search();
					} else {
						showError('操作失败');
					}
				});
			}
		});
	},
	
	//批量审核
	batchAudit : function(opStatus,opMsg,statusFlag) {
		var checkedRows = $('#'+this.initData.mainDgId).datagrid('getChecked');
		//审核、删除判断
		var errorMessage = _this.getMsgOfBatch(checkedRows,opStatus,statusFlag);
		if(errorMessage!=""){
			showInfo(errorMessage);
			return ;
		}
		//打回判断
		var backMsg = _this.getMsgOfRedoBatch(checkedRows,opStatus);
		if(backMsg!=""){
			showInfo(backMsg);
			return ;
		}
		
		if (checkedRows) {
			$.messager.confirm("确认", "确定"+opMsg+"选中的单据?", function(r) {
				if (r) {
					var url = _this.initData.modulePath + '/batch_audit';
					var ids_data = "";
					$.each(checkedRows, function(index, row) {
						ids_data += row.billNo + ",";
					});
					var params = {};
					params.idList = ids_data.substring(0, ids_data.length - 1);
					params.status = opStatus;
					ajaxRequestAsync(url, params, function(data) {
						if (typeof data != "undifined") {
							$.fas.showMsg('操作成功', 5000);
							_this.search();
						} else {
							showError('操作失败');
						}
					});
				}
			});
		}
	},
	
	//扣项调整判断
	adjustJudge:function(){
	},
	
	//扣项调整
	deductAdjust : function(){
		var _self=new Object();
		var msg =_this.adjustJudge();
		if(msg!=""){
			showInfo(msg);
			return ;
		}
		var url = BasePath + '/bill_balance/hq/to_balance_adjust?balanceType='+this.initData.balanceType;
		ygDialog({
			title : '扣项调整',
			href : url,
			width : 1000,
			height : 'auto',
			isFrame : true,
			modal : true,
			showMask : true,
			buttons: [{
				id:'sure',
	            text: '确认',
	            handler: function(dialog) {
	            	var checkedRows = _self.getCheckBill();
	            	var params = $('#'+_this.initData.mainFormId).form('getData');
	            	params.checkedRows = JSON.stringify(checkedRows);
	            	ajaxRequestAsync( _this.initData.modulePath+ '/deduction_adjust', params, function(data){
	            		if(data){
							dialog.close();
							$.fas.showMsg('操作成功',5000);
							_this.loadFormData(data,data.status);
							_this.afterLoadForm(data);
							_this.loadTabDg(_this.tabsParams[0].id,_this.tabsParams[0].listUrl,_this.tabsParams[0].queryParams);
							_this.search();
						}else{
							 dialog.close();
							 showError('保存失败');
						}
	            	});
	            }
	        },
	        {
	            text: '取消',
	            handler: function(dialog) {
	                dialog.close();
	            }
	        }],
			onLoad : function(win, content) {
				_self.getCheckBill = content.getCheckBill;
			}
		});
	},
	
	//上单
	upBill : function() {
		if (this.curRowIndex < 0) {
			showInfo('不存在当前单据');
			return;
		}
		type = 1;
		preBill(_this.initData.mainDgId,this.curRowIndex, type,
				_this.callBackBill);
	},

	//下单
	downBill : function() {
		if (this.curRowIndex < 0) {
			showInfo('不存在当前单据');
			return;
		}
		type = 2;
		preBill(_this.initData.mainDgId,this.curRowIndex, type,
				_this.callBackBill);
	},

	//上下单回调
	callBackBill : function(curRowIndex, rowData) {
		if (type == 1 || type == 2) {
			if (rowData != null && rowData != '' && rowData != []) {
				_this.loadDetail(curRowIndex, rowData);
				type = 0;
			} else {
				if (type == 1) {
					showInfo('已经是第一单');
				} else {
					showInfo('已经是最后一单');
				}
			}
		}
	},
	
	// 格式化单据状态
	statusFormat : function(v) {
		var combobox = $("#status");
		var value = combobox.combobox("options").valueField;
		var text = combobox.combobox("options").textField;
		var datas = combobox.combobox("getData");
		for ( var i = 0; i < datas.length; i++) {
			if (datas[i][value] == v) {
				return datas[i][text];
			}
		}
		return "";
	},
	
	// 操作日志
	operateLog : function() {
		if ($('#billNo').val() == '') {
			showInfo('当前没有单据');
			return;
		}
		dgSelector({
			title : "操作日志",
			href : BasePath + "/operate_log/operate_detail?dataNo="+ $('#billNo').val() + "&moduleNo=2",
			queryUrl : BasePath + "/operate_log/list.json",
			width : 600,
			height : 350,
			isFrame : false
		});
	},
	
	//导出
	doExport : function(formId, dataGridId, exportUrl, excelTitle,typeFlag, otherParams) {
		//设置参数
		var brandNames="";
		if (typeFlag != "" && typeFlag == 'RO01') {
			brandNames = $('#brandNameId').combogrid("getValues");
		}else if(typeFlag != "" && typeFlag == 'RO11'){
			brandNames = $('#brandNameId').val();
		}else if(typeFlag != "" && typeFlag == 'RR01'){
			brandNames = $('#brandUnitNameId').combogrid("getValues");
		}else if(typeFlag != "" && typeFlag == 'RR11'){
			brandNames = $('#brandUnitNameId').val();
		}
		
	    var $balanceNo = $("#billNo").val();
		var formData = $('#' + formId).form('getData');
		var $dg = $('#' + dataGridId);
		var params = $dg.datagrid('options').queryParams;
		var columns = $dg.datagrid('options').columns;
		var columnsNew = [];
		
		//封装导出的字段列
		$.each(columns, function(index, item) {
			var dataArray = [];
			var i = 0;
			$.each(item, function(rowIndex, rowData) {
				if (rowData.hidden) {
					return;
				}
				var v_object = {};
				v_object.field = rowData.field;
				v_object.title = rowData.title;
				v_object.width = rowData.width;
				if (rowData.hidden == 'true' || rowData.notexport) {
					return;
				}
				dataArray[i] = v_object;
				i++;
			});
			columnsNew[index] = dataArray;
		});
		
		//导出操作
		var exportColumns = JSON.stringify(columnsNew);
		var url = BasePath + exportUrl;
		var dataRow = $dg.datagrid('getRows');
		$("#exportExcelForm").remove();
		$("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
		var fromObj = $('#exportExcelForm');
		if (dataRow.length > 0) {
			fromObj.form('submit', {
				url : url,
				onSubmit : function(param) {
					if (params != null && params != {}) {
						$.each(params, function(i) {
							param[i] = params[i];
						});
					}
					if (otherParams != null && otherParams != {}) {
						$.each(otherParams, function(i) {
							param[i] = otherParams[i];
						});
					}
					param.exportColumns = exportColumns;
					param.fileName = excelTitle;
					param.formData = JSON.stringify(formData);
					param.balanceNo = $balanceNo;
					param.brandName = brandNames;
				},
				success : function() {
				}
			});
		} else {
			alert('查询记录为空，不能导出!', 1);
		}
	},
	
	//列表导出之前的业务逻辑
	beforeListExport:function(){
	},
	
	//结算单列表导出
	listExport : function(gridId,exportUrl,exportTitle) {
		var $dg = $('#'+gridId);
		var params = $dg.datagrid('options').queryParams;
		var columns = $dg.datagrid('options').columns;

		var columnsNew = [];
		$.each(columns, function(index, item) {
			var dataArray = [];
			var i = 0;
			$.each(item, function(rowIndex, rowData) {
				if (rowData.hidden) {
					return;
				}
				var v_object = {};
				v_object.field = rowData.field;
				v_object.title = rowData.title;
				v_object.width = rowData.width;
				if (rowData.hidden == 'true' || rowData.notexport) {
					return;
				}
				dataArray[i] = v_object;
				i++;
			});
			columnsNew[index] = dataArray;
		});

		var exportColumns = JSON.stringify(columnsNew);
		var url = BasePath + exportUrl;
		var dataRow = $dg.datagrid('getRows');
		$("#exportExcelForm").remove();
		$("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
		var fromObj = $('#exportExcelForm');
		if (dataRow.length > 0) {
			fromObj.form('submit', {
				url : url,
				onSubmit : function(param) {
					if (params != null && params != {}) {
						$.each(params, function(i) {
							param[i] = params[i];
						});
					}
					param.exportColumns = exportColumns;
					param.fileName = exportTitle;
				},
				success : function() {
				}
			});
		} else {
			alert('查询记录为空，不能导出!', 1);
		}
	},
	
	//明细导出之前的业务逻辑
	beforeDtlExport:function(){
	},
	
	//明细导出
	dtlExport : function(gridId,exportGrid, exportUrl, exportTitle) {
		var $dg = $('#'+gridId);
		var params = $dg.datagrid('options').queryParams;
		var columns = $('#'+exportGrid).datagrid('options').columns;
        var selectedRows=$dg.datagrid('getSelections');
        var billNos='';
        if(selectedRows.length>0){
            // 拼接单据编号
            $.each(selectedRows, function(rowIndex, rowData) {
            	billNos+=rowData.billNo+',';
            });
        }else{
        	showInfo('请勾选要导出的记录');
        	return;
        }
   
		var columnsNew = [];
		$.each(columns, function(index, item) {
			var dataArray = [];
			var i = 0;
			$.each(item, function(rowIndex, rowData) {
				if (rowData.hidden) {
					return;
				}
				var v_object = {};
				v_object.field = rowData.field;
				v_object.title = rowData.title;
				v_object.width = rowData.width;
				if (rowData.hidden == 'true' || rowData.notexport) {
					return;
				}
				dataArray[i] = v_object;
				i++;
			});
			columnsNew[index] = dataArray;
		});

		var exportColumns = JSON.stringify(columnsNew);
		var url = BasePath + exportUrl;
		var dataRow = $dg.datagrid('getRows');
		$("#exportExcelForm").remove();
		$("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
		var fromObj = $('#exportExcelForm');
		if (dataRow.length > 0) {
			fromObj.form('submit', {
				url : url,
				onSubmit : function(param) {
					if (params != null && params != {}) {
						$.each(params, function(i) {
							param[i] = params[i];
						});
					}
					param.exportColumns = exportColumns;
					param.fileName = exportTitle;
					param.billNos=billNos.substring(0,billNos.length-1);
				},
				success : function() {
				}
			});
		} else {
			alert('查询记录为空，不能导出!', 1);
		}
	},
	
	//结算起始日期
	getStartDate:function(){
		var d = new Date();
		var year = d.getFullYear();
		var cmonth = d.getMonth();
		var day = d.getDate();
		var startDate = new Date();
		startDate.setDate(1);
		if (cmonth === 0 && day <= 10) {
			startDate.setFullYear(d.getFullYear() - 1);
			startDate.setMonth(10);
		} else if (cmonth === 0 && day > 10) {
			startDate.setFullYear(d.getFullYear() - 1);
			startDate.setMonth(11);
		} else {
			if (day <= 10) {
				startDate.setMonth(cmonth - 2);
			} else {
				startDate.setMonth(cmonth - 1);
			}
		}
		startDate.setDate(26);
		return startDate;
	},
	
	//结算终止日期
	getEndDate:function(){
		var d = new Date();
		var year = d.getFullYear();
		var cmonth = d.getMonth();
		var day = d.getDate();
		var endDate = new Date();
		if (cmonth === 0 && day <= 10) {
			endDate.setFullYear(d.getFullYear() - 1);
			endDate.setMonth(11);
		} else if (cmonth === 0 && day > 10) {
			endDate.setFullYear(d.getFullYear());
			endDate.setMonth(0);
		} else {
			if (day <= 10) {
				endDate.setMonth(cmonth - 1);
			} else {
				endDate.setMonth(cmonth);
			}
		}
		endDate.setDate(25);
		return endDate;
	}
};

/**
 * 原型模式继承
 */ 
function BillObj(initData, tabsParams) {
	this.initData = initData;
	this.tabsParams = tabsParams;
	_this = this;// 创建时默认指向当前对象
}
BillObj.prototype = new BalanceBillCommon();
