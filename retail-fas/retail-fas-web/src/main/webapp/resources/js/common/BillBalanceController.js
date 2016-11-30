var currentUser;

var nullMessage = "不存在当前单据";

var nullCheckMessage = "请选中需要操作的单据!";

var deleteMessage = "只允许删除制单状态的单据!";

var reverseHqConfirmMessage = "只允许反确认总部财务确认的单据!";

var confirmHqMessage = "只允许确认制单状态的单据!"; 

var reverseAreaConfirmMessage = "只允许反确认地区财务确认或未经总部开票的单据!"; 

var confirmAreaMessage = "只允许确认总部财务确认的单据!"; 

var backMessage = "该单据状态不允许打回!"; 

var busConfirmMessage = "只允许业务确认制单或打回状态的单据!";

var fasConfirmMessage = "只允许财务确认经过业务确认的单据!";

function BillBalanceController(){

    this.curRowIndex = -1;
    
    this.settleMethodData = new Object();

	var _self = this;
	
    if(typeof BillBalanceController.prototype._initialized == 'undefined'){
    	
        BillBalanceController.prototype.init = function(params){
        	_self.params = params;
        	_self.defaultDG = params.dtlDataGrids[0]; 
        	_self.initSettleMethodData();
        	_self.initExtendCategory();
        };
        
        BillBalanceController.prototype.initSettleMethodData = function(){
    		ajaxRequestAsync(BasePath + '/common_util/getSettleMethod',null,function(data){
    			if(data.length > 0){
    				$.each(data,function(index,item){
    					_self.settleMethodData[item.code]=item.name;
    				});
    			}
    		});
    	};

        BillBalanceController.prototype.initExtendCategory = function(){
        		var url = BasePath + '/common_util/toSelectExtendCategory';
        		$('#extendCategoryName').iptSearch({
        			clickFn : function() {	
        				ygDialog({
        					title : '选择扩展分类',
        					href : url,
        					width : 550,
        					height : '400',
        					isFrame : true,
        					modal : true,
        					showMask : true,
        					buttons: [{
        						id:'sure',
        			            text: '确认',
        			            handler: function(dialog) {
        			            	var rows = _self.getRows();
        			            	if(rows.length >0){
        								var code = '';
        								var name = '';
        								var queryCondition = '';
        								$.each(rows,function(index,item){
        									var extendCategoryCode = item.extendCategoryCode;
        									var extendCategoryName = item.extendCategoryName;
        									if(typeof(extendCategoryCode) != 'undefined' && extendCategoryCode!=''){
        										code += extendCategoryCode+'/';
        										name += extendCategoryName+'/';
        										var type = item.extendCategoryType;
        										var codeCondition = common_util.multiFormat(extendCategoryCode);
        										if(type == '品牌'){
        											queryCondition += ' AND T.brand_no IN ' +codeCondition;
        										}else if(type == '二级大类'){
        											queryCondition += ' AND LEFT(T.category_no,4) IN ' +codeCondition;
        										}else if(type == '三级大类'){
        											queryCondition += ' AND LEFT(T.category_no,6) IN ' +codeCondition;
        										}else if(type == '性别'){
        											queryCondition += ' AND T.gender IN ' +codeCondition;
        										}
        									}
        								});
    									$('#extendCategoryNo').val(code.substring(0,code.length-1));
            			            	$('#extendCategoryName').val(name.substring(0,name.length-1));
            			            	$('#extendCategoryCondition').val(queryCondition);
        							}
        			            	dialog.close();
        			            }
        			        },
        			        {
        			            text: '取消',
        			            handler: function(dialog) {
        			                dialog.close();
        			            }
        			        }],
        					onLoad : function(win, content) {
        						_self.getRows = content.getRows;
        					}
        				});
        			}
        		});
		};
    	
        BillBalanceController.prototype.getErrorMessage = function(currStatus ,operStatus){
        	if (currStatus === ''){
        		return nullMessage;
        	}
        	if(typeof operStatus == 'undefined'){
        		if(currStatus != 0 && currStatus!=99){
        			return deleteMessage;
        		}
        	}
        	if(_self.params.balanceType == 2){
        		if($('#isArea').val() !="true"){// 总部
        			if(operStatus == 0 && currStatus!= 2){// 反确认
            			return reverseHqConfirmMessage;
            		}else if(operStatus == 2 && currStatus!=0 && currStatus!=99){// 确认
            			return confirmHqMessage;
            		}
        		}else{// 地区
        			if(operStatus == 2 && currStatus!= 4){// 反确认
        				return reverseAreaConfirmMessage;
        			}else if(operStatus == 4 && currStatus!=2){// 确认
        				return confirmAreaMessage;
        			}else if(operStatus == 99 && (currStatus==0 || currStatus==99)){// 打回
        				return backMessage;
        			}
        		}
        	}else{
        		if(operStatus == 1 && currStatus!= 0 && currStatus!= 99){// 总部业务确认
        			return busConfirmMessage;
        		}else if(operStatus == 2 && currStatus!=1){// 总部财务确认
        			return fasConfirmMessage;
        		}else if(operStatus == 99 && (currStatus==0 || currStatus==99)){// 打回
    				return backMessage;
    			}
        	}
        	return "";
        };

        BillBalanceController.prototype.getBatchErrorMessage = function(checkedRows ,operStatus){
        	if(checkedRows.length ==0){
        		return nullCheckMessage;
        	}
        	var errorMessage = "";
        	$.each(checkedRows, function(index,item){
        		errorMessage = _self.getErrorMessage(item.status,operStatus);
        		if(errorMessage!=""){
        			return false;
        		}
				if(operStatus == 99 && ( (item.askPaymentNo&&item.askPaymentNo!= '') || 
						(item.invoiceNo&&item.invoiceNo != '') || (item.invoiceApplyNo&&item.invoiceApplyNo != '') )){
					errorMessage = "已关联下游单据的结算单不允许打回!";
    				return false;
    			}
        	});
        	return errorMessage;
        };

        
    	BillBalanceController.prototype.initPage = function(){// 页面初始化
    		_self.initMainTab();
    		_self.initDtlTab();
    		_self.initAdd();
        };
    	
        BillBalanceController.prototype.initMainTab = function() {// 初始化单据查询tab
      	$('#mainTab').tabs('add', {
      		title : '单据列表',
      		selected : false,
      		closable : false,
      		href : _self.params.mainTabUrl +'?isArea='+$('#isArea').val(),
      		onLoad : function(panel) {
      			common_util.initPage('searchForm');
      			common_util.initDate('searchForm');
      		}
      	});
      	_self.refreshTabs();
      };
      
      	BillBalanceController.prototype.refreshTabs = function() {// 切换选项卡刷新单据查询的dataGrid
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

        BillBalanceController.prototype.initDtlTab = function() {// 初始化明细Tab
        	var dgs = _self.params.dtlDataGrids;
        	var iLength = dgs.length;
        	for(var i=0; i < iLength; i++){
        		_self.addDtlTab(dgs[i]);
        	}
        	$('#dtlTab').tabs({  
        	    onSelect:function(title){   
        	    	for(var i=0; i < iLength; i++){
                		if(dgs[i].title == title){
                			_self.loadDtlDataGrid(dgs[i]);
                		}
                	}
        	    }
        	}); 
        	returnTab('dtlTab',_self.defaultDG.title);
        };
        
        BillBalanceController.prototype.addDtlTab = function(dgObj){// 添加明细Tab
        	var tabUrl = dgObj.tabUrl;
        	tabUrl = tabUrl + '?isArea='+$('#isArea').val();
        	$('#dtlTab').tabs('add', {
        		title : dgObj.title,
        		selected : false,
        		closable : false,
        		href : tabUrl,
        		onLoad : function(panel){
        			_self.loadDtlDataGrid(dgObj);
        		}
        	});
        };
        
    	BillBalanceController.prototype.initAdd = function(){// 新增初始化
    		_self.clearMainData();
    		_self.clearDtlData();
    		_self.initAddClass();
    		common_util.initDate();
        };
    	
    	BillBalanceController.prototype.save = function(){// 保存
    		if(_self.doValidate() && _self.endEdit()){
    			var params = $('#mainDataForm').form('getData');
    			params.balanceType = _self.params.balanceType;
    			if($('#id').val() ==''){// 新增校验
    	    		var data = _self.updatePrice(params);
    	    		if(data.errorMessage && data.errorMessage!=''){
						showError(data.errorMessage);
					}else{
			    		if(data.priceRangeCondition && ''!= data.priceRangeCondition){
			    			$.messager.confirm("确认","存在核价未处理的商品，将不参与结算，是否继续生成结算单?",function(r) {
			        			if (r) {
			        				_self.doSubmit(data.priceRangeCondition);
					    			_self.search();
			        			}
			    			});
			    		}else{
			    			_self.doSubmit();
			    			_self.search();
			    		}
					}
    			}else{
    				_self.doSubmit();
	    			_self.search();
    			}
    		}
        };

    	BillBalanceController.prototype.doValidate = function(){ // 验证
    		if($('#status').val()!=0 && $('#status').val()!=99 ){
    			showInfo("该单据已确认,不允许变更!");
    			return false;
    		}
    		return $('#mainDataForm').form('validate');
    		
    	};
    	
    	BillBalanceController.prototype.doSubmit = function(priceRangeCondition){ // 提交
			var url = _self.params.addUrl;
			if($('#id').val() !=''){
				url = _self.params.updateUrl;
			}
			$.messager.progress({
				title:'请稍后',
				msg:'正在处理中...',
				text:''
			}); 
			$('#mainDataForm').form('submit',{
				url : url,
				onSubmit:function(param){
					if($('#id').val()==""){
						param.priceRangeCondition = priceRangeCondition;
						param.createUser = currentUser.username;
						param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					}else{
						param.updateUser = currentUser.username;
						param.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
					}
					param.status=0;
					param.balanceType = _self.params.balanceType;
				},
				success:function(data){
					if(data){
						showSuc('保存成功');
						var jsonData = JSON.parse(data);
						_self.loadMainData(jsonData);
						_self.saveDtl();
						var tab = $('#dtlTab').tabs('getSelected');
						var index = $('#dtlTab').tabs('getTabIndex',tab);
						_self.loadDtlDataGrid(_self.params.dtlDataGrids[index]);
						_self.resetEditClass();
						$.messager.progress('close');
					}else{
						showError('单据数据异常，请检查商品核价是否处理！');
						$.messager.progress('close');
					}
					
				},
				error:function(){
					showError('操作失败');
					$.messager.progress('close');
				}
			});
    	};
    	
    	BillBalanceController.prototype.del = function(){// 删除
    		var errorMessage = _self.getErrorMessage($('#status').val());
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
    		$.messager.confirm("确认","你确定要删除该条单据?",function(r) {
    			if (r) {
    				var url =  _self.params.deleteUrl;
    				var params = {id : $('#id').val()+','+$('#billNo').val(), balanceType : _self.params.balanceType};
    				ajaxRequestAsync(url,params,function(data){
    					if(data){
    						showSuc('删除成功');
    			    		_self.initAdd();
    			    		_self.search();
    					}else{
    						showError('删除失败');
    					}
    				});
    			}
    		});
    		
    	};


    	BillBalanceController.prototype.batchDel = function(){// 批量删除
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		var errorMessage = _self.getBatchErrorMessage(checkedRows);
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
			$.messager.confirm("确认","你选中了"+checkedRows.length+"条单据，确定要删除?",function(r) {
				if (r) {
					var url =  _self.params.deleteUrl;
					var id = "";
					$.each(checkedRows, function(index, row) {
						id += row.id+','+row.billNo+";";
					});
					var params = {id : id.substring(0, id.length-1),balanceType : _self.params.balanceType};
	    			$.messager.progress({
	    				title:'请稍后',
	    				msg:'正在处理中...'
	    			}); 
					ajaxRequestAsync(url,params,function(data){
						if(data){
							showSuc('删除成功');
							_self.search();
						}else{
							showError('删除失败');
						}
						$.messager.progress('close');
					});
				}
			});
    	};

    	BillBalanceController.prototype.operate = function(status){// 审核
    		var errorMessage = _self.getErrorMessage($('#status').val(), status);
    		if(errorMessage!=""){
    			showInfo(errorMessage);
    			return ;
    		}
    		var message = "你确定要确认这条单据?"
    		if(status == 99){
    			message = "你确定要打回这条单据?"
    			if(($('#askPaymentNo').val()&&$('#askPaymentNo').val() != '') || ($('#invoiceNo').val()&&$('#invoiceNo').val() != '') || ($('#invoiceApplyNo').val()&&$('#invoiceApplyNo').val() != '')){
    				showInfo("已关联下游单据的结算单不允许打回!");
    				return ;
    			}
    		}
			$.messager.confirm("确认",message,function(r) {
				if (r) {
					var data = {
							status : status,
							billNo : $('#billNo').val()
					};
					ajaxRequestAsync(_self.params.verifyUrl,data,function(result){
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
    	
    	BillBalanceController.prototype.reverseOperate = function(status){// 反审核
    		var currStatus = $('#status').val();
    		if(status == 0 && currStatus!= 1){
    			showInfo("只允许反确认业务确认的单据!");
    			return ;
    		}else if(status == 1 && currStatus!= 2){
    			showInfo("只允许反确认财务确认的单据!");
    			return ;
    		}else if(($('#askPaymentNo').val()&&$('#askPaymentNo').val() != '') || ($('#invoiceNo').val()&&$('#invoiceNo').val() != '') || ($('#invoiceApplyNo').val()&&$('#invoiceApplyNo').val() != '')){
    			showInfo("已关联下游单据的结算单不允许反确认!");
				return ;
    		}
			$.messager.confirm("确认","你确定反确认该条单据?",function(r) {
				if (r) {
					var data = {
							status : status,
							billNo : $('#billNo').val()
					};
					ajaxRequestAsync(_self.params.verifyUrl,data,function(result){
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
    	
    	BillBalanceController.prototype.batchOperate = function(status){// 批量 审核
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		var errorMessage = _self.getBatchErrorMessage(checkedRows,status);
			if(errorMessage!=""){
				showInfo(errorMessage);
				return ;
			}	
			var message = "你确定要确认选中的单据?"
    		if(status == 99){
    			message = "你确定要打回选中的单据?"
    		}else if(status == 0 || (status == 2 && $('#isArea').val() == 'true')){
    			message = "你确定要反确认选中的单据?"
    		}
			$.messager.confirm("确认",message,function(r) {
				if (r) {
					$.messager.progress({
	    				title:'请稍后',
	    				msg:'正在处理中...'
	    			}); 
					var billNo = "";
					$.each(checkedRows, function(index, row) {
						billNo+= row.billNo+",";
					});
					var data = {
							status : status,
							billNo : billNo.substring(0,billNo.length-1)
					};
					ajaxRequestAsync(_self.params.verifyUrl,data,function(result){
						if(result){
							showSuc('操作成功');
							_self.search();
						}else{
							showError('操作失败');
						}
						setTimeout(function(){
							$.messager.progress('close');
						},300);
					});
				}
			});
    	};

       	BillBalanceController.prototype.reverseBatchOperate = function(status){// 批量 反审核
    		var checkedRows = $('#mainDataGrid').datagrid('getChecked');
    		var errMessage = "";
    		if(checkedRows.length == 0){
    			errMessage = nullCheckMessage;
    		}else{
    			$.each(checkedRows, function(index, item){
    				if(status == 0 && item.status!=1){
    					errMessage = "只允许反确认业务确认的单据!";
    					return false;
    				}else if(status == 1 && item.status!=2){
    					errMessage = "只允许反确认财务确认的单据!";
    					return false;
    				}else if( (item.askPaymentNo&&item.askPaymentNo!= '') || 
    						(item.invoiceNo&&item.invoiceNo != '') || (item.invoiceApplyNo&&item.invoiceApplyNo != '') ){
    					errMessage = "已关联下游单据的结算单不允许反确认!";
    					return false;
    				}
    			});
    		}
    		if(errMessage !=""){
    			showInfo(errMessage)
    			return ;
    		}
			$.messager.confirm("确认","你确定反确认选中单据?",function(r) {
				if (r) {
					$.messager.progress({
	    				title:'请稍后',
	    				msg:'正在处理中...'
	    			}); 
					var billNo = "";
					$.each(checkedRows, function(index, row) {
						billNo+= row.billNo+",";
					});
					var data = {
							status : status,
							billNo : billNo.substring(0,billNo.length-1)
					};
					ajaxRequestAsync(_self.params.verifyUrl,data,function(result){
						if(result){
							showSuc('操作成功');
							_self.search();
						}else{
							showError('操作失败');
						}
						setTimeout(function(){
							$.messager.progress('close');
						},300);
					});
				}
			});
    	};
    	
    	BillBalanceController.prototype.loadDetail = function(rowIndex, rowData) {// 点击切换到明细
    		_self.curRowIndex = rowIndex;
    		_self.loadMainData(rowData);
    		_self.loadDtlDataGrid(_self.defaultDG);
    		returnTab('dtlTab', _self.defaultDG.title);
    		returnTab('mainTab', '单据明细');
    		_self.resetEditClass();
    	};
    	
    	BillBalanceController.prototype.loadMainData = function(rowData){// 加载表头数据
    		_self.clearMainData();
    		$('#mainDataForm').form('load', rowData);
    		// 底部单据状态显示栏
    		$('#createUser').html(rowData.createUser);
    		$('#createTime').html(rowData.createTime);
			$('#auditor').html(rowData.auditor);
    		$('#auditTime').html(rowData.auditTime);
        };
    	
        BillBalanceController.prototype.loadDtlDataGrid = function(dgObj){// 加载明细数据
        	var billNo = $('#billNo').val();
        	var balanceType = _self.params.balanceType;
        	var dg = $('#'+dgObj.id);
        	var url = dgObj.listUrl;
        	var params = typeof(dgObj.queryParams) == 'undefined' ? new Object() : dgObj.queryParams;
        	if("" != billNo && dg.length > 0){
        		params.isArea = $('#isArea').val();
        		params.balanceNo = billNo;
        		params.balanceType = balanceType;
        		setTimeout(function(){
        			dg.datagrid( 'options' ).queryParams= params;
        			dg.datagrid( 'options' ).url= url;
        			dg.datagrid( 'load' );
        		},300)
    			
    		}
        };  

    	BillBalanceController.prototype.clear = function(){// 清空
    		$('#searchForm').form("clear");
    		$('#searchForm').find("input").val("");
    	};

    	BillBalanceController.prototype.search = function(){// 查询
			 if($('#searchForm').length > 0){
		    	var params = $('#searchForm').form('getData');
		    	var url = _self.params.listUrl;
		    	params.balanceType = _self.params.balanceType;
		    	params.isArea = $('#isArea').val();
	    	   $( '#mainDataGrid').datagrid( 'options' ).queryParams= params;
	    	   $( '#mainDataGrid').datagrid( 'options' ).url= url;
	    	   $( '#mainDataGrid').datagrid( 'load' );
			 }
    	};

    	BillBalanceController.prototype.upBill = function() {// 上单
    		if (BillBalanceController.prototype.curRowIndex < 0) {
    			showInfo('不存在当前单据');
    			return;
    		}
    		_self.type = 1;
    		preBill('mainDataGrid', _self.curRowIndex,
    				_self.type, _self.callBackBill);
    	};

    	BillBalanceController.prototype.downBill = function() {// 下单
    		if (_self.curRowIndex < 0) {
    			showInfo('不存在当前单据');
    			return;
    		}
    		_self.type = 2;
    		preBill('mainDataGrid', _self.curRowIndex,
    				_self.type, _self.callBackBill);
    	};

    	BillBalanceController.prototype.callBackBill = function(curRowIndex, rowData) {// 上下单回调
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

    	BillBalanceController.prototype.showDialog = function(){// 打开批量生成结算单选择页面
    		$('#dataForm').form('clear');
    		$('#dataForm').find("input").val("");
    		common_util.initDate('dataForm');
    		ygDialog({
    			title : '结算单生成条件设置',
    			target : $('#myFormPanel'),
    			width :  360,
    			height : 500,
    			buttons : [{
    				text : '确认',
    				iconCls : 'icon-save',
    				handler : function(dialog) {
    					_self.generationBill(dialog);
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
    	
    	BillBalanceController.prototype.generationBill = function(dialog){// 生成结算单
    		
	    		var fromObj = $('#dataForm');
	    		//1.校验必填项
	    		var validateForm = fromObj.form('validate');
	    		if (validateForm == false) {
	    			return;
	    		}
	    		var checkParams = fromObj.form('getData');
	    		checkParams.balanceType = _self.params.balanceType;
	    		var data = _self.updatePrice(checkParams);
	    		if(data.errorMessage && data.errorMessage!=''){
	    			showError(data.errorMessage);
	    			return;
	    		}
	    		if(data.priceRangeCondition && ''!= data.priceRangeCondition){
	    			$.messager.confirm("确认","存在核价未处理的商品，将不参与结算，是否继续生成结算单?",function(r) {
	        			if (r) {
	        				$.messager.progress({
	            				title:'请稍后',
	            				msg:'正在生成结算单...',
	            				interval:1000
	            			}); 
	            			$('#dataForm').form('submit',{
	            				url : _self.params.addUrl,
	            				onSubmit:function(param){
	            					param.priceRangeCondition = data.priceRangeCondition;
	            					param.currency = 0;
	            					param.status = 0;
	            					param.createUser = currentUser.username;
	            					param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
	            					param.balanceType = _self.params.balanceType;
	            					param.isBatch = 'true';
	            				},
	            				success:function(data){
	            					if(data){
	            						showSuc('操作成功');
	            						dialog.close();
	            						_self.search();
	            					}else{
	            						showError('单据数据异常，请检查商品核价是否处理！');
	            					}
	            					$.messager.progress('close');
	            				},
	            				error:function(){
	            					$.messager.progress('close');
	            					showError('操作失败');
	            				}
	            			});
	        			}
	    			});
	    		}else{
    				$.messager.progress({
        				title:'请稍后',
        				msg:'正在生成结算单...',
        				interval:1000
        			}); 
        			$('#dataForm').form('submit',{
        				url : _self.params.addUrl,
        				onSubmit:function(param){
        					param.currency = 0;
        					param.status = 0;
        					param.createUser = currentUser.username;
        					param.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
        					param.balanceType = _self.params.balanceType;
        					param.isBatch = 'true';
        				},
        				success:function(data){
        					if(data){
        						showSuc('操作成功');
        						dialog.close();
        						_self.search();
        					}
        					$.messager.progress('close');
        				},
        				error:function(){
        					$.messager.progress('close');
        					showError('操作失败');
        				}
        			});
	    		}
    			
    	};    	
    	
    	BillBalanceController.prototype.initAddClass = function(){// 新增样式
    		$("#mainDataForm").find("input[singleSearch]").combogrid('enable');
    		$("#mainDataForm").find("input[multiSearch]").combogrid('enable');
    		$("#mainDataForm").find("input[combobox]").combobox('enable');
    		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('enable');
    		$("#mainDataForm").find("input[class*=disableEdit]").attr("readonly",true).addClass("readonly");
    		$("#mainDataForm").find("input:not([class*=disableEdit])").removeAttr("readonly").removeClass("readonly");  
    		$("#mainDataForm").find("input[combobox=currency]").combobox('setValue',0);
    		$("#mainDataForm").find("i").show();
    	};
    	
    	BillBalanceController.prototype.resetEditClass = function(){// 样式重置
    		$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
    		$("#mainDataForm").find("input[singleSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[multiSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[combobox]").combobox('disable');
    		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
    		$("#mainDataForm").find("i").hide();
    		if(($('#status').val()==0 || $('#status').val()==99)&& $("#isArea").val() !="true"){
    			$('#billName,#remark').removeAttr("readonly").removeClass("readonly");
    		}
    	};
    	
    	BillBalanceController.prototype.clearMainData = function(){// 清空表头数据
    		$('#mainDataForm').form("clear");
    		$('#mainDataForm').find("input").val("");
    		$('#bottombarMx').find('span').text("");
    	};

    	BillBalanceController.prototype.clearDtlData = function(){// 清空明细数据
    		var dgs = _self.params.dtlDataGrids;
    		var iLength = dgs.length;
    		for(var i=0; i < iLength; i++){
    			var dgId = dgs[i].id;
    			if($('#'+dgId).length > 0){
        			$('#'+dgId).datagrid('loadData', {total : 0,rows : [],footer : []});
    			}
    		}
    	};

		/**
		 * 基础资料的导出
		 * @param mainForm 需要导出的表头form
		 * @param dataGridId  导出数据的表格ID
		 * @param exportUrl 导出数据的URL   基础资料一般都是 /模块名/do_export.htm     *如机构:/store/do_export 
		 * @param excelTitle excel文件名
		 * @param checkFlag 导出数据的表格是否有复选框(有复选框后台自动去掉该列)  0--无  1--有
		 * @param reduceColnumName 不用导出的列名
		 */
		BillBalanceController.prototype.exportBalance = function (){
			var billNo =  $('#billNo').val();
			if(billNo == ""){
				showInfo("不存在当前单据!");
				return ;
			}
			var exportDataGrid = $('#exportExcelDG');
			var columns=exportDataGrid.datagrid('options').columns;
			var columnsNew = [];
			$.each(columns,function(index,item){
				var dataArray = [];
				var i = 0;
				$.each(item,function(rowIndex,rowData){
					if(rowData.hidden == 'true' || rowData.notexport){
						return ;
					}
					var v_object = {};
					v_object.field = rowData.field;
					v_object.title = rowData.title;
					v_object.width = rowData.width;
					dataArray[i] = v_object;
					i++;
				});
				columnsNew[index] = dataArray;
			});
			
			var exportColumns=JSON.stringify(columnsNew);
			
			var url=BasePath+'/bill_balance/hq/balance_export';
			$("#exportExcelForm").remove();
			$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
			var fromObj=$('#exportExcelForm');
			
		    fromObj.form('submit', {
				url: url,
				onSubmit: function(param){
					param.fileName='结算单明细';
					param.balanceType = _self.params.balanceType;
					param.billNo = billNo;
					param.exportColumns=exportColumns;
				},
				success: function(){
					
			    }
		   });
		}
		
		BillBalanceController.prototype.batchExportBalance = function (){
			var checkedRows =  $('#mainDataGrid').datagrid('getChecked');
			if(checkedRows.length == 0){
				showInfo("请选中需要导出的单据!");
				return ;
			}
			var billNo = "";
			$.each(checkedRows,function(index,item){
				billNo+=item.billNo+",";
			});
			var exportDataGrid = $('#exportExcelDG');
			var columns=exportDataGrid.datagrid('options').columns;
			var columnsNew = [];
			$.each(columns,function(index,item){
				var dataArray = [];
				var i = 0;
				$.each(item,function(rowIndex,rowData){
					if(rowData.hidden == 'true' || rowData.notexport){
						return ;
					}
					var v_object = {};
					v_object.field = rowData.field;
					v_object.title = rowData.title;
					v_object.width = rowData.width;
					dataArray[i] = v_object;
					i++;
				});
				columnsNew[index] = dataArray;
			});
			
			var exportColumns=JSON.stringify(columnsNew);
			
			var url=BasePath+'/bill_balance/hq/balance_export';
			$("#exportExcelForm").remove();
			$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
			var fromObj=$('#exportExcelForm');
			
		    fromObj.form('submit', {
				url: url,
				onSubmit: function(param){
					param.fileName='结算单明细';
					param.isArea = $('#isArea').val();
					param.balanceType = _self.params.balanceType;
					param.billNo = billNo.substring(0,billNo.length - 1);
					param.exportColumns=exportColumns;
				},
				success: function(){
					
			    }
		   });
		}
		
		BillBalanceController.prototype.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){//导出
			var $dg = $('#'+dataGridId);
			var params=$dg.datagrid('options').queryParams;
			var columns=$dg.datagrid('options').columns;
			var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
			var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
			var columnsNew = [];
			var Fcolumns= $dg.datagrid('options').frozenColumns;
			if(Fcolumns[0].length>0){
				columns=Fcolumns.concat(columns);
			}
			$.each(columns,function(index,item){
				var dataArray = [];
				var i = 0;
				$.each(item,function(rowIndex,rowData){
					if(rowData.notexport){
						return ;
					}
					var v_object = {};
					v_object.field = rowData.field;
					v_object.title = rowData.title;
					v_object.width = rowData.width;
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
		}; 
		
		BillBalanceController.prototype.toOperateLog = function(){
    		if($('#billNo').val() == ''){
    			showInfo('不存在当前单据!');
    			return ;
    		}
    		dgSelector({
    			title: "日志明细", 
    			href: BasePath + "/operate_log/operate_detail?dataNo=" + $('#billNo').val()+"&moduleNo=2", 
    			queryUrl: BasePath + "/operate_log/list.json",
    			width: 600, 
    			height: 500, 
    			isFrame: false
    		});
		}; 
		
    	BillBalanceController.prototype.toCreateBalance = function(){
    		var url = BasePath + '/bill_balance/hq/to_custom_create_balance?balanceType='+_self.params.balanceType;
			ygDialog({
				title : '选择关联单据',
				href : url,
				width : 1000,
				modal : true,
				showMask : true,
				isFrame : true,
				maximized : true,
				top : 0,
				buttons: [{
					id:'sure',
		            text: '生成结算单',
		            handler: function(dialog) {
		            	if(_self.validateBalance()){
			            	var params = _self.getMainData();
			            	var checkedRows = _self.getCheckBill();
			            	if(checkedRows ==null){
			            		return ;
			            	}
			            	params.isUserDefined = 1;
			            	params.createUser = currentUser.username;
							params.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
			            	params.checkedRows = JSON.stringify(checkedRows);
							$.messager.progress({
			    				title:'请稍后',
			    				msg:'正在处理中...'
			    			}); 
			            	ajaxRequestAsync( BasePath + '/bill_balance/hq/custom_create_balance', params ,function(data){
			            		if(data){
					            	dialog.close();
									showSuc('保存成功');
									_self.loadMainData(data);
									_self.loadDtlDataGrid(_self.defaultDG);
									returnTab('dtlTab',_self.defaultDG.title);
									_self.resetEditClass();
									_self.search();
								}else{
					            	dialog.close();
									showError('保存失败');
								}
			            		$.messager.progress('close');
			            	});
		            	}
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
					_self.getMainData = content.getMainData;
					_self.validateBalance = content.validateBalance;
				}
			});
		}; 
		
    	BillBalanceController.prototype.toBalanceAdjust = function(){
    		if($('#status').val() == ""){
    			showInfo('不存在当前单据!');
    			return ;
    		}
    		if($('#status').val() == 5 || $('#status').val() == 2){
    			showInfo('该单据财务确认或已请款,不允许在做调整!');
    			return ;
    		}
    		var url = BasePath + '/bill_balance/hq/to_balance_adjust?balanceType='+_self.params.balanceType;
			ygDialog({
				title : '结算调整',
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
		            	var params = $('#mainDataForm').form('getData');
		            	params.deductionRows = JSON.stringify(checkedRows.deductionRows);
		            	params.imperfectRows = JSON.stringify(checkedRows.imperfectRows);
		            	ajaxRequestAsync( BasePath + '/bill_balance/hq/balance_adjust', params, function(data){
		            		if(data){
		            			 dialog.close();
								showSuc('保存成功');
								_self.loadMainData(data);
								var deductionDG = _self.params.dtlDataGrids[2];
								_self.loadDtlDataGrid(deductionDG);
								returnTab('dtlTab',deductionDG.title);
								_self.resetEditClass();
								_self.search();
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
		}; 

    	BillBalanceController.prototype.validateDtl = function(){// 获取明细编辑错误信息
    		if($('#billNo').val()==''){
    			showWarn("请先保存单据表头信息!");
    			return false;
    		}else{
    			if($('#status').val() >= 2 && $('#status').val()!=99){
    				showWarn("单据已财务确认,不允许变更!");
    				return false;
    			}
    		}
    		return _self.endEdit();
    	};

    	
		BillBalanceController.prototype.addDtl = function(){// 新增明细
			if(_self.validateDtl()){
    			$('#deductionDataGrid').datagrid('appendRow',{deductionDate:new Date().format("yyyy-MM-dd"),deductionQty:0});
    		    var length = $('#deductionDataGrid').datagrid('getRows').length;
    		    _self.beginEdit(length-1);
    		    return true;
    		}
			
    		return false;
    	};

    	BillBalanceController.prototype.editDtl = function(rowIndex){// 编辑明细
    		if(_self.validateDtl()){
    			_self.beginEdit(rowIndex);
    			return true;
    		}
    		return false;
    	};

    	BillBalanceController.prototype.delDtl = function(){// 删除明细
			if($('#status').val() >= 2 && $('#status').val()!=99){
				showWarn("单据已财务确认,不允许变更!");
			}else{
	    		var checkedRows = $('#deductionDataGrid').datagrid('getChecked');
	    		var rowIndex;
	    		$.each(checkedRows, function(index, row) {
	    			rowIndex = $('#deductionDataGrid').datagrid('getRowIndex',row);
	    			$('#deductionDataGrid').datagrid('deleteRow',rowIndex);
	    		});
	    		_self.setAllAmount();
    		}
    	};
    	
    	BillBalanceController.prototype.beginEdit = function(editIndex){// 开始明细行编辑
    		$('#deductionDataGrid').datagrid('beginEdit',editIndex);
    		var edAmount = $("#deductionDataGrid").datagrid('getEditor',{index:editIndex,field:'deductionAmount'});
    		$(edAmount.target).bind('blur',function(){
    			_self.setAllAmount();
    		});
    		var edQty = $("#deductionDataGrid").datagrid('getEditor',{index:editIndex,field:'deductionQty'});
    		$(edQty.target).bind('blur',function(){
    			_self.setAllAmount();
    		});
    	};
    	

    	BillBalanceController.prototype.setAllAmount = function(){// 设置总金额
    		var rows = $("#deductionDataGrid").datagrid('getRows');
    		var allAmount = 0;
    		var allQty = 0;
    		var editTr = $("tr[class*=datagrid-row-editing]");
    		var editRowIndex = -1;
    		if(editTr.length > 0){
    			editRowIndex = editTr.attr("datagrid-row-index");
    			var edAmount = $("#deductionDataGrid").datagrid('getEditor',{index:editRowIndex,field:'deductionAmount'});
    			var edQty = $("#deductionDataGrid").datagrid('getEditor',{index:editRowIndex,field:'deductionQty'});
    			var amount = $(edAmount.target).numberbox('getValue');
    			var qty = $(edQty.target).numberbox('getValue');
    			if(''!=amount){
    				allAmount += parseFloat(amount);
    			}
    			if(''!=qty){
    				allQty += parseFloat(qty);
    			}
    		} 
    		for(var i =0,iLength = rows.length; i<iLength; i++){
    			var rowIndex = $("#deductionDataGrid").datagrid('getRowIndex',rows[i]);
    			var amount = rows[i].deductionAmount;
    			var qty = rows[i].deductionQty;
    			if(editRowIndex != rowIndex){
    				if(''!=amount){
    					allAmount += parseFloat(amount);
    				}
    				if(''!=qty){
    					allQty += parseFloat(qty);
    				}
    			}
    		}
    		var deductionAmount = $('#deductionAmount').val();
    		var deductionQty = $('#deductionQty').val();
    		var balanceAmount = $('#balanceAmount').val();
    		var balanceQty = $('#balanceQty').val();
    		$('#deductionAmount').val(allAmount);
    		$('#deductionQty').val(allQty);
    		$('#balanceAmount').val(parseFloat(balanceAmount)+ parseFloat(deductionAmount)-parseFloat(allAmount));
    		$('#balanceQty').val(parseFloat(balanceQty) + parseFloat(deductionQty) - parseFloat(allQty));
    	};
    	
    	BillBalanceController.prototype.endEdit = function(){// 结束明细行编辑
    		var editTr = $("#deductionTab").find("tr[class*=datagrid-row-editing]");
    		if(editTr.length > 0){
    			var editRowIndex = editTr.attr("datagrid-row-index");
    			if($('#deductionDataGrid').datagrid('validateRow', editRowIndex)){
        			$('#deductionDataGrid').datagrid('endEdit',editRowIndex);
        			return true;
        		}else{
        			return false;
        		}
    		} 
    		return true;
    	};
    	
    	BillBalanceController.prototype.saveDtl = function(){// 保存明细 
    		if($('#deductionDataGrid').length > 0 && _self.endEdit()){
    			var billNo = $('#billNo').val();
    			var insertedData = $('#deductionDataGrid').datagrid('getChanges','inserted');
        		var updatedData = $('#deductionDataGrid').datagrid('getChanges','updated');
        		var deletedData = $('#deductionDataGrid').datagrid('getChanges','deleted');
        		$.each(insertedData, function(index, row) {
        			row.balanceNo = billNo;
        			row.balanceType = _self.params.balanceType;
        			row.createUser = currentUser.username;
        			row.createTime = new Date().format("yyyy-MM-dd hh:mm:ss");
        			row.salerNo = $('#mainDataForm').find("input[name=salerNo]").val();
        			row.buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
        			row.salerName = $('#mainDataForm').find("input[name=salerName]").val();
        			row.buyerName = $('#mainDataForm').find("input[name=buyerName]").val();
        		});
        	    $.each(updatedData,function(index, row){
        	    	row.updateUser = currentUser.username;
        	    	row.updateTime = new Date().format("yyyy-MM-dd hh:mm:ss");
        	    });
        		var data = {
        			inserted : JSON.stringify(insertedData),
        			updated : JSON.stringify(updatedData),
        			deleted : JSON.stringify(deletedData),
        		};
        		ajaxRequestAsync(BasePath + '/other_deduction/save',data,function(result){
        			if(result){
        			  	$( '#deductionDataGrid').datagrid('acceptChanges');
        			  	$( '#deductionDataGrid').datagrid( 'options' ).queryParams= {balanceNo:billNo};
    				    $( '#deductionDataGrid').datagrid( 'options' ).url = BasePath + '/other_deduction/list.json';
    				    $( '#deductionDataGrid').datagrid( 'load' );
        				showSuc('保存成功');
        			}else{
        				showError('保存失败');
        			}
        		});
			}
    	};
    	BillBalanceController.prototype.updatePrice = function(params){
    		$("#updatePriceForm").remove();
			$("<form id='updatePriceForm'  method='post'></form>").appendTo("body"); ;
			var resultData ;
			$.messager.progress({
				title:'请稍后',
				msg:'正在进行价格校验...',
				text:''
			}); 
			ajaxRequestAsync(BasePath +'/bill_balance/hq/handle_before_balance',params,function(data){
				resultData = data;
			});
			$.messager.progress('close');
			return resultData;
    	}
		BillBalanceController._initialized = true;
    }
}

//初始化
$(function(){
	toolSearch({
        appendTo:$('#top_bar'), //添加位置，默认为$('#toolbar')
        target:$('#subLayout'), //控制对象，默认为$('#subLayout')
        collapsible:true //是否显示下拉箭头
	});
	$('#mainTab').tabs('hideHeader');
	if($('#billNoMenu').length == 0 || $('#billNoMenu').val() == ''){
		setTimeout(function(){
			returnTab('mainTab', '单据列表');
		},300);
	}
	ajaxRequestAsync( BasePath + '/common_util/getCurrentUser',null,function(data){
		currentUser = data;
	});
});



