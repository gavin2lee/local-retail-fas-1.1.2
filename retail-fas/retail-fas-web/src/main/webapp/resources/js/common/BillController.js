
var currentUser;

var nullMessage = "不存在当前单据";

var nullCheckMessage = "请选中需要操作的单据!";

var auditMessage = "只允许提交制单或打回状态的单据";

var backMessage = "只允许撤销提交的单据!";

var deleteMessage = "只允许删除制单或打回状态的单据!";

var reverseMessage = "不允许反向审批操作!";

var repeatMessage = "不允许重复审批操作!";

var bypassMessage = "不允许越级审批操作";

var backAuditMessage = "只允许打回审批状态的单据!";

var auditMessage1 = "只允许审批已提交的单据!";

var submitMessageReq = "你确定要提交该条单据?";

var auditMessageReq = "你确定要审批该条单据?";

var backMessageReq = "你确定要撤销该条单据?";

var batchSubmitMessageReq = "你确定要提交选中的单据?";

var batchAuditMessageReq = "你确定要审批选中的单据?";

var batchBackMessageReq = "你确定要撤销选中的单据?";
	
function getErrorMessage(currStatus ,operStatus){
	if(currStatus ===""){
		return nullMessage;
	}
	if(typeof operStatus == 'undefined'){
		if(currStatus != 0 && currStatus != 99){
			return deleteMessage;
		}
	}
	if(operStatus == 1){
		if(currStatus!=0 && currStatus!=99){
			return auditMessage;
		}
	}else if(operStatus == 0){
		if(currStatus!=1){
			return backMessage;
		}
	}else if(operStatus > 1){
		if(currStatus==0 || currStatus==99){
			return auditMessage1;
		}else if(operStatus - currStatus == 2 || operStatus - currStatus == 4){
			return bypassMessage;
		}else if(operStatus == currStatus ){
			return repeatMessage;
		}else if(operStatus - currStatus < 0){
			return reverseMessage;
		}
	}
	return "";
}

function getBatchErrorMessage(checkRows ,operStatus){
	if(checkRows.length ==0){
		return nullCheckMessage;
	}
	var errorMessage = "";
	$.each(checkRows, function(index,item){
		errorMessage = getErrorMessage(item.status,operStatus);
		if(errorMessage!=""){
			return false;
		}
		if(operStatus == 0 && (item.paymentNo&&item.paymentNo!= '' )){
			errorMessage = "已关联下游单据的发票不允许反审核!";
			return false;
		}
	});
	return errorMessage;
}

function BillController(){
	
    this.curRowIndex = -1;
    
	var _self = this;
	
    if(typeof BillController.prototype._initialized == 'undefined'){
    	
        BillController.prototype.init = function(modulePath, dtlModulePath){
        	_self.modulePath = modulePath;
        	_self.dtlModulePath = dtlModulePath;
        };
        
    	BillController.prototype.initPage = function(){// 页面初始化
    		_self.addMainTab(); 
    		_self.initDtlTab();
    		_self.initRef();
    		_self.initAdd();
        };
    	
    	BillController.prototype.initAdd = function(){// 新增初始化
    		_self.clearMainData();
    		_self.clearDtlData();
    		_self.initAddClass();
    		_fasCategoryUrl = '';
        };
    	
    	BillController.prototype.save = function(){// 保存
    		if(_self.doValidate()){
				var rows = $('#dtlDataGrid').datagrid('getRows');
    			if(rows.length == 0){
    				showInfo('请添加明细后在进行保存!');
    				return ;
    			}
    			_self.doSubmit();
    			_self.search();
    		}
        };

    	BillController.prototype.doValidate = function(){ // 验证
    		if(!($('#status').val()!=''&& $('#status').val()!=0 && $('#status').val()!=99)){
	    		if($('#mainDataForm').form('validate')){
	    			if(_self.endEdit()){
	    				return true;
	    			}
	    		}
	    		return false;
    		}
    		showInfo("该单据已确认,不允许变更!");
    		return false;
    	};
    	
    	BillController.prototype.doSubmit = function(){ // 提交
			var pkVal = $('#id').val();
			var url = _self.modulePath + '/post';
			if(pkVal !=''){// 新增 
				url = _self.modulePath + '/put';
			}
			$('#mainDataForm').form('submit',{
				url : url,
				onSubmit:function(param){
					param.status = 0;
					if($('#id').val()==""){
						param.createUser = currentUser.username;
						param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					}else{
						param.updateUser = currentUser.username;
						param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					}
				},
				success:function(data){
					if(data){
						showSuc('保存成功');
						var jsonData = JSON.parse(data);
						_self.loadMainData(jsonData);
						_self.saveDtl();
						_self.loadDtlListData(jsonData);
						_self.resetEditClass();
					}else{
						showError('保存失败');
					}
				}
			});
    	};
    	
    	BillController.prototype.del = function(){// 删除
    		var errorMessage = getErrorMessage($('#status').val());
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
    		$.messager.confirm("确认","你确定要删除该条单据?",function(r) {
    			if (r) {
    				var url =  _self.modulePath + '/delete';
    				var id = $('#id').val();
    				var billNo = $('#billNo').val();
    				var balanceType = $('#balanceType').val();
    				if(balanceType==''){
    					balanceType=null;
    				}
    				var params = {idList : id+','+billNo+','+balanceType};
    				ajaxRequestAsync(url,params,function(data){
    					if(data){
    						showSuc('删除成功');
    						_self.clearMainData();
    						_self.clearDtlData();
    						_self.initAdd();
    						_self.search();
    					}else{
    						showError('删除失败');
    					}
    				});
    			}
    		});
    	};

    	BillController.prototype.batchDel = function(){// 批量删除
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		var errorMessage = getBatchErrorMessage(checkedRows);
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
			$.messager.confirm("确认","你选中了"+checkedRows.length+"条单据，确定要删除?",function(r) {
				if (r) {
					var url =  _self.modulePath + '/delete';
					var idList ="";
					$.each(checkedRows, function(index, row) {
						idList+=row.id+','+row.billNo+','+row.balanceType+";";
					});
					var params = {idList : idList.substring(0, idList.length-1)};
					ajaxRequestAsync(url,params,function(count){
						if(count){
							showSuc('删除成功');
							_self.search();
						}else{
							showError('删除失败');
						}
						
					});
				}
			});
    	};
    	
    	BillController.prototype.operate = function(status){// 审核 -作废
    		if(_self.changeFlag || $("tr[class*=datagrid-row-editing]").length > 0){
    			showInfo("请先保存后、再继续操作!");
    			return ;
    		}
    		var errorMessage = getErrorMessage($('#status').val(),status);
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
			var message = "";
			if(status == 0){
				if($('#paymentNo').val() && $('#paymentNo').val() != ''){
					showInfo("已关联下游单据的发票不允许反审核!");
					return ;
				 }
				message = backMessageReq;
			}else if(status == 1){
				 message = submitMessageReq;
			}else if(status > 1){
				 message = auditMessageReq;
			}
			$.messager.confirm("确认",message,function(r) {
				if (r) {
					var data = {
							billNo : $('#billNo').val(),
							status : status,
							auditor : currentUser.username,
							auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
					};
					ajaxRequestAsync(_self.modulePath + '/verify',data,function(result){
						if(result){
							showSuc('操作成功');
							_self.loadMainData(result); 
							_self.resetEditClass();
							_self.search();
						}else{
							showError('操作失败');
						}
					});
				}
			});
    	};
    	
    	BillController.prototype.batchOperate = function(status){// 批量 审核-作废
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		var errorMessage = getBatchErrorMessage(checkedRows,status);
			if(errorMessage!=""){
				showInfo(errorMessage);
				return ;
			}	
    		var message = "";
    		if(status == 0){
				message = batchBackMessageReq;
			}else if(status == 1){
				 message = batchSubmitMessageReq;
			}else if(status > 1){
				 message = batchAuditMessageReq;
			}
			$.messager.confirm("确认",message,function(r) {
				if (r) {
					var billNo = "";
					$.each(checkedRows, function(index, row) {
						billNo +=row.billNo+",";
					});
					var data = {
							billNo : billNo.substring(0,billNo.length - 1),
							status : status,
							auditor : currentUser.username,
							auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
					};
					ajaxRequestAsync(_self.modulePath + '/verify',data,function(result){
						if(result){
							showSuc('操作成功');
							_self.search();
						}else{
							showError('操作失败');
						}
					});
				}
			});
    	};

    	
    	BillController.prototype.back = function(status){// 打回
    		var currStatus = $('#status').val();
    		if(currStatus ===""){
    			showInfo(nullMessage);
    			return ;
    		}
    		if(currStatus < 2 || currStatus == 99){
    			showInfo("只允许打回审批中的单据!");
    			return ;
    		}
			$.messager.confirm("确认","你确定要打回该条单据?",function(r) {
				if (r) {
					var data = {
							billNo : $('#billNo').val(),
							status : status,
							auditor : currentUser.username,
							auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
					};
					ajaxRequestAsync(_self.modulePath + '/verify',data,function(result){
						if(result){
							showSuc('操作成功');
							_self.loadMainData(result); 
							_self.resetEditClass();
							_self.search();
						}else{
							showError('操作失败');
						}
					});
				}
			});
    	};
    	
    	BillController.prototype.batchBack = function(status){// 打回
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		if(checkedRows.length == 0){
    			showInfo(nullCheckMessage);
    			return ;
    		}
    		var errMessage = "";
    		$.each(checkedRows, function(index, row) {
				if(row.status < 2 || row.status == 99){
					errMessage = "只允许打回审批状态的单据!";
					return false;
				}
			});
    		if(errMessage!=""){
    			showInfo(errMessage);
    			return ;
    		}
			$.messager.confirm("确认","你确定要打回选中的单据?",function(r) {
				if (r) {
					var billNo = "";
					$.each(checkedRows, function(index, row) {
						billNo +=row.billNo+",";
					});
					var data = {
							billNo : billNo.substring(0,billNo.length - 1),
							status : status,
							auditor : currentUser.username,
							auditTime : new Date().format("yyyy-MM-dd hh:mm:ss")
					};
					ajaxRequestAsync(_self.modulePath + '/verify',data,function(result){
						if(result){
							showSuc('操作成功');
							_self.search();
						}else{
							showError('操作失败');
						}
					});
				}
			});
    	};
    	
    	BillController.prototype.loadDetail = function(rowIndex, rowData) {// 点击切换到明细
    		_self.clearMainData();
    		_self.curRowIndex = rowIndex;
    		$('#flag').val('true');	
    		_self.loadData(rowData);
    		_self.resetEditClass();
    		returnTab('mainTab', '单据明细');
    	};
    	
    	BillController.prototype.loadData = function(rowData){// 加载数据
    		_self.loadMainData(rowData);
    		_self.loadDtlListData();
    		returnTab('dtlTab','单据明细');
        };
    	
    	BillController.prototype.loadMainData = function(rowData){// 加载表头数据
    		_self.changeFlag = false;
    		$('#mainDataForm').form('load', rowData);
    		// 底部单据状态显示栏
    		$('#createUser').html(rowData.createUser);
    		$('#createTime').html(rowData.createTime);
    		$('#auditor').html(rowData.auditor);
    		$('#auditTime').html(rowData.auditTime);
    		$('#remark').change(function(){
    			_self.changeFlag = true;
    		});
        };
    	
    	BillController.prototype.clear = function(){// 清空
    		$('#searchForm').form("clear");
    		$('#searchForm').find("input[name!=balanceType]").val("");
    	};

    	BillController.prototype.search = function(){// 查询
    	  if($('#searchForm').length > 0){
    		  var params = $('#searchForm').form('getData');
    		  params.queryCondition = $('#queryCondition').val();
    		  params.isHQ = $('#isHQ').val();
    		  var url = _self.modulePath + '/list.json';
        	  $( '#mainDataGrid').datagrid( 'options' ).queryParams= params;
        	  $( '#mainDataGrid').datagrid( 'options' ).url= url;
        	  $( '#mainDataGrid').datagrid( 'load' );
    	  }
    	};

    	BillController.prototype.upBill = function() {// 上单
    		if (_self.curRowIndex < 0) {
    			showInfo('不存在当前单据');
    			return;
    		}
    		_self.type = 1;
    		preBill('mainDataGrid', _self.curRowIndex,
    				_self.type, _self.callBackBill);
    	};

    	BillController.prototype.downBill = function() {// 下单
    		if (_self.curRowIndex < 0) {
    			showInfo('不存在当前单据');
    			return;
    		}
    		_self.type = 2;
    		preBill('mainDataGrid', _self.curRowIndex,
    				_self.type, _self.callBackBill);
    	};

    	BillController.prototype.callBackBill = function(curRowIndex, rowData) {// 上下单回调
    		if (_self.type == 1 || _self.type == 2) {
    			if (rowData != null && rowData != '' && rowData != []) {
    				_self.loadDetail(curRowIndex, rowData);
    				_self.type = 0;
    			} else {
    				if (_self.type == 1) {
    					showInfo('已经是第一单');
    				} else {
    					showInfo('已经是最后一单');
    				}
    			}
    		}
    	};

    	BillController.prototype.addDtl = function(){// 新增明细
    		if(_self.enableEdit){
    			if(_self.endEdit()){
        			$('#dtlDataGrid').datagrid('appendRow',{});
        		    var length = $('#dtlDataGrid').datagrid('getRows').length;
        		    _self.beginEdit(length-1);
        		    return true;
        		}
        		return false;
    		}
    	};

    	BillController.prototype.editDtl = function(rowIndex){// 编辑明细
    		if(_self.enableEdit){
	    		if(_self.endEdit()){
	    			_self.beginEdit(rowIndex);
	    			return true;
	    		}
	    		return false;
    		}
    	};

    	BillController.prototype.delDtl = function(){// 删除明细
    		if(_self.enableEdit){
	    		var checkedRows = $('#dtlDataGrid').datagrid('getChecked');
	    		var rowIndex;
	    		$.each(checkedRows, function(index, row) {
	    			rowIndex = $('#dtlDataGrid').datagrid('getRowIndex',row);
	    			$('#dtlDataGrid').datagrid('deleteRow',rowIndex);
	    		});
    		}
    	};
    	
    	BillController.prototype.beginEdit = function(editIndex){// 开始明细行编辑
    		$('#dtlDataGrid').datagrid('beginEdit',editIndex);
    	};
    	
    	BillController.prototype.endEdit = function(){// 结束明细行编辑
    		var editTr = $("tr[class*=datagrid-row-editing]");
    		if(editTr.length > 0){
    			var editRowIndex = editTr.attr("datagrid-row-index");
    			if($('#dtlDataGrid').datagrid('validateRow', editRowIndex)){
        			$('#dtlDataGrid').datagrid('endEdit',editRowIndex);
        			return true;
        		}else{
        			return false;
        		}
    		} 
    		return true;
    	};
    	
    	BillController.prototype.saveDtl = function(){// 保存明细 
    		if(_self.endEdit()){
    			var insertedData = $('#dtlDataGrid').datagrid('getChanges','inserted');
        		var updatedData = $('#dtlDataGrid').datagrid('getChanges','updated');
        		var deletedData = $('#dtlDataGrid').datagrid('getChanges','deleted');
        		var billNo = $('#billNo').val();
        		$.each(insertedData, function(index, row) {
        			row.billNo = billNo;
        		});
        		var data = {
        			inserted : JSON.stringify(insertedData),
        			updated : JSON.stringify(updatedData),
        			deleted : JSON.stringify(deletedData),
        		};
        		ajaxRequestAsync(_self.dtlModulePath + '/save',data,function(result){
        			if(result){
        			  	$('#dtlDataGrid').datagrid('acceptChanges');
        			  	$( '#dtlDataGrid').datagrid( 'options' ).queryParams= {billNo:billNo};
    				    $( '#dtlDataGrid').datagrid( 'options' ).url = _self.dtlModulePath + '/list.json';
    				    $( '#dtlDataGrid').datagrid( 'load' );
        				showSuc('保存成功');
        			}else{
        				showError('保存失败');
        			}
        		});
			}
    	};

    	BillController.prototype.addMainTab = function() {// 初始化单据查询tab
    		$('#mainTab').tabs('add', {
    			title : '单据列表',
    			selected : false,
    			closable : false,
    			href : _self.modulePath + '/list_tabMain.htm' ,
    			onLoad : function(panel) {
    				common_util.initPage('searchForm');
    			}
    		});
    		_self.refreshTabs();
    	};

    	BillController.prototype.refreshTabs = function() {// 切换选项卡刷新单据查询的dataGrid
    		$('#mainTab').tabs({
    			onSelect : function(title, index) {
    				$('#mainDataGrid').datagrid('resize', {
    					width : function() {
    						return document.body.clientWidth;
    					}
    				});
    				$('#easyui-panel-id').panel('resize', {
    					width : function() {
    						return document.body.clientWidth;
    					}
    				});
    				$('#queryConditionDiv').panel('resize', {
    					width : function() {
    						return document.body.clientWidth;
    					}
    				});
    			},
    			onLoad : function(panel) {
    				$('#queryConditionDiv').panel('resize', {
    					width : function() {
    						return document.body.clientWidth;
    					}
    				});
    			}
    		});
    	};
    	
    	
    	BillController.prototype.initAddClass = function(){//  新增样式
    		$("#mainDataForm").find("input[id!=balanceType]").val('');
    		$("#mainDataForm").find("input[singleSearch]").combogrid('enable');
    		$("#mainDataForm").find("input[multiSearch]").combogrid('enable');
    		$("#mainDataForm").find("input[combobox]").combobox('enable');
    		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('enable');
    		$("#mainDataForm").find("input[class*=disableEdit]").attr("readonly",true).addClass("readonly");
    		$("#mainDataForm").find("input:not([class*=disableEdit])").removeAttr("readonly").removeClass("readonly");
    		$("#mainDataForm").find("input[combobox=tsCurrency]").combobox('setValue','001');
    		$("#mainDataForm").find("input[name=billDate]").datebox('setValue',new Date().format("yyyy-MM-dd"));
        	_self.enableEdit = true;
    	};
    	
    	BillController.prototype.resetEditClass = function(){// 重置编辑样式
    		$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
    		$("#mainDataForm").find("input[singleSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[multiSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[combobox]").combobox('disable');
    		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
    		if($('#status').val()!=0 && $('#status').val()!=99 ){
    			_self.enableEdit = false;
    		}else{
    			$('#remark').removeAttr("readonly").removeClass("readonly");
    			_self.enableEdit = true;
    		}
    	};
    	
    	BillController.prototype.clearMainData = function(){// 清空表头数据
    		$('#mainDataForm').form("clear");
    		$('#mainDataForm').find("input[name!=balanceType]").val("");
    		$('#bottombarMx').find('span').text("");
    	};

    	BillController.prototype.clearDtlData = function(){// 清空明细数据
		   if($('#dtlDataGrid').length > 0){
				deleteAllGridCommon('dtlDataGrid');
				$('#dtlDataGrid').datagrid('acceptChanges');
			}
		   if($('#refDataGrid').length > 0){
				deleteAllGridCommon('refDataGrid');
				$('#refDataGrid').datagrid('acceptChanges');
			}
    	};
    	
		BillController.prototype.toOperateLog = function(moduleNo){
    		if($('#billNo').val() == ''){
    			showInfo('不存在当前单据!');
    			return ;
    		}
    		dgSelector({
    			title: "日志明细", 
    			href: BasePath + "/operate_log/operate_detail?dataNo=" + $('#billNo').val()+"&moduleNo="+moduleNo, 
    			queryUrl: BasePath + "/operate_log/list.json",
    			width: 600, 
    			height: 500, 
    			isFrame: false
    		});
		}; 
		
		BillController.prototype.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){//导出
			var $dg = $('#'+dataGridId);
			var params=$dg.datagrid('options').queryParams;
			var columns=$dg.datagrid('options').columns;
			var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
			var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
			
			var columnsNew = [];
			$.each(columns,function(index,item){
				var dataArray = [];
				var i = 0;
				$.each(item,function(rowIndex,rowData){
					if(rowData.hidden){
						return ;
					}
					var v_object = {};
					v_object.field = rowData.field;
					v_object.title = rowData.title;
					v_object.width = rowData.width;
					if(rowData.hidden == 'true' || rowData.notexport){
						return ;
					}
					dataArray[i] = v_object;
					i++;
				});
				columnsNew[index] = dataArray;
			});
			
			var exportColumns=JSON.stringify(columnsNew);
			
			var url=BasePath+exportUrl;
			
			var dataRow=$dg.datagrid('getRows');

			$("#exportExcelForm").remove();
			
			$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
			
			var fromObj=$('#exportExcelForm');
			
			if(dataRow.length>0){
			    fromObj.form('submit', {
					url: url,
					onSubmit: function(param){
						if(params!=null&&params!={}){
							$.each(params,function(i){
								param[i]=params[i];
							});
						}
						if(otherParams!=null&&otherParams!={}){
							$.each(otherParams,function(i){
								param[i]=otherParams[i];
							});
						}
						param.exportColumns=exportColumns;
						param.fileName=excelTitle;
						param.pageNumber = v_pageNumber;
						param.pageSize = v_pageSize;
						
					},
					success: function(){
						
				    }
			   });
			}else{
				alert('查询记录为空，不能导出!',1);
			}
		}

		BillController.prototype.getRowSelect = function(select, noField, nameField, required){
			var selectObj = _self.rowSelect[select];
			selectObj.noField = noField;
			selectObj.nameField = nameField;
			if(required){
				selectObj.validatebox = {
			    	options:{
			    		required:true
			    	}
			    }
			}
			return  selectObj;
		}

		BillController.prototype.initCurrencyData = function() {
			ajaxRequestAsync(BasePath + '/common_util/getCurrency',null,function(data){
				if(data.length > 0){
					_self.currencyJsonData = data;
					$.each(data,function(index,item){
						_self.currencyData[item.code]=item.name;
					});
				}
			});
		};
		
		BillController.prototype.doImport = function(templateName,url,successFn,errorFn) {
			if(_self.enableEdit){
				$.importExcel.open({
					'submitUrl' : BasePath + url,
					'templateName' : templateName,
					success : successFn,
					error : errorFn
				});
			}
		};
		
		BillController.prototype.initSettleMethodData = function() {
			ajaxRequestAsync(BasePath + '/common_util/getSettleMethod',null,function(data){
				if(data.length > 0){
					_self.settleMethodJsonData = data;
					$.each(data,function(index,item){
						_self.settleMethodData[item.code]=item.name;
					});
				}
			});
		};
		

		BillController.prototype.initDtlTab = function() {// 初始化明细Tab
			var dtl_url = _self.modulePath + '/area/to_dtl';
			var ref_url = _self.modulePath + '/area/to_ref';
			if($('#isHQ').val() == 'true'){
				dtl_url = _self.modulePath + '/hq/to_dtl';
				ref_url = _self.modulePath + '/hq/to_ref';
			}
			billController.addDtlTab('单据明细',dtl_url);
			billController.addDtlTab('源单信息',ref_url);
			$('#dtlTab').tabs({    
			    onSelect:function(title){
			    	if(title == '单据明细'){
			    		_self.loadDtlListData();
			    	}else{
			    		_self.loadRefInfo();
			    	}
			    }    
			}); 
			returnTab('dtlTab','单据明细');
		};

		BillController.prototype.setAccountInfo = function(code){
			ajaxRequestAsync( BasePath + '/common_util/getAccountInfo',{code:code},function(data){
				billController.bankName = data.bankName;
				billController.bankAccount = data.bankAccount;
			});
		}
		
		BillController.prototype.addDtlTab = function(title,href){// 添加明细Tab
			$('#dtlTab').tabs('add', {
				title : title,
				selected : false,
				closable : false,
				href : href,
				onLoad : function(panel){
					if(title == '单据明细'){
						billController.loadDtlListData();
					}else{
						if($('#isHQ').val()==''){
							billController.loadRefInfo();
							$('#refDataGrid').datagrid('hideColumn','categoryName');
						}
					}
				}
			});
		};

		BillController.prototype.loadDtlListData = function(){// 加载明细数据
			var billNo = $('#billNo').val();
			var dg = $('#dtlDataGrid');
			if(dg.length >0){
				if(billNo !=""){
					setTimeout(function(){
						dg.datagrid( 'options' ).queryParams= {billNo:billNo};
						dg.datagrid( 'options' ).url = _self.dtlModulePath + '/list.json';
						dg.datagrid( 'load' );
					},300);
				}else{
					dg.datagrid('loadData',{total:0,rows:[]}); 
				}
			}
		};

		BillController.prototype.loadRefInfo = function(){// 加载源单信息
			setTimeout(function(){
				var billNo =  $('#refBillNo').combogrid("getValue");
				var dg = $('#refDataGrid');
				if(billNo !="" && dg.length > 0){
					dg.datagrid( 'options' ).queryParams= {billNo:billNo};
					dg.datagrid( 'options' ).url = _self.modulePath + '/ref_list';
					dg.datagrid( 'load' );
				}else{
					dg.datagrid('loadData',{total:0,rows:[]}); 
				}
			},300);
		};

		BillController._initialized = true;
    }
}



(function($) {
	var defaults = {
		submitUrl : '',
		templateName : '',
		params : {},
		success:function(){
			
		},
		error:function(){
			
		}
	};

	var importExcel = {
		opts : {},
		dialog : null,
		open : function(o) {
			opts = o;
			var $uploadForm = null;
			var $errorInfo = null;
			dialog = ygDialog({
				isFrame: true,
				cache : false,
				title : '导入',
				modal:true,
				showMask: false,
				width:'400',
				height:'160',
				href : BasePath + "/common_util/toImport",
				buttons:[{
					text:'上传',
					iconCls:'icon-upload',
					handler:function(self){
						if($errorInfo.text().trim()!=""){
							return ;
						}
						$uploadForm.form('submit',{
							url : o.submitUrl,
							success:function(data){
								if(data){
									if(typeof o.success=="function"){
										o.success(data);
									}else{
										showSuc("导入成功!");
									}
								}else{
									if(typeof o.error=="function"){
										o.error(data);
									}else{
										showError("导入失败");
									}
								}
								self.close();
							}
						});
					}
				},{
					text:'下载模板',
					iconCls:'icon-download',
					handler:function(){
						window.location.href=BasePath + '/common_util/download?fileName='+ o.templateName;
					}
				}],
				onLoad:function(win,dialog){
					$errorInfo = dialog.$('#errorInfo');
					$uploadForm = dialog.$('#uploadForm');
				}
			});
		},
		close : function(){
			dialog.panel('close');
		},
		success : function(){
			opts.success.call();
		},
		error : function(){
			opts.error.call();
		}
	};

	$.importExcel = function(options) {
		$.fn.importExcel.open(options);
	};

	$.importExcel.open = function(options) {
		var opts = $.extend({}, defaults, options);
		importExcel.open(opts);
	};

	$.importExcel.success = function(){
		return importExcel.success();
	};
	
	$.importExcel.error = function(){
		return importExcel.error();
	};
	
	$.importExcel.colse = function(){
		return importExcel.close();
	};
})(jQuery);

//初始化
$(function(){
	$('#mainTab').tabs('hideHeader');
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});
























