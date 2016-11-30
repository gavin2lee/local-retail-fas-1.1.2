var datasource = (function() {
	var datas = {
		status : [
         {
      		"label" : "-1",
      		"text" : "全部",
      		"value" : "-1→全部"
      	} ,      
		   {
			"label" : "0",
			"text" : "制单"
		}, {
			"label" : "1",
			"text" : "确认"
		}, {
			"label" : "3",
			"text" : "审核"
		}, {
			"label" : "99",
			"text" : "作废"
		}  ]
	};
	datas.get = function(name) {
		return this[name];
	};
	datas.forBillHeader = function(name) {
		var statusObj=$.extend({},this[name]);
		var status=[];
		$.each(statusObj,function(i,item){
			if(!isNaN(i)){
				status.push(item);
			}
		});
		status.splice(0,1);
		status.splice(status.length-1);
		return status;
	};
	return datas;
})();

//datagrid 头部加红色标识
var redFlag="<span class='ui-color-red' style='font-size:20px'>*</span>";

//检查对象是否为空
function isNotBlank(obj) {
	if (!obj || typeof obj == 'undefined' || obj == '') {
		if('0' == obj){
			return true;
		}
		return false;
	}
	return true;
}

//比较表单控件的值 判读是否被修改
function formIsDirty(form) {
	if(!form){
		form=document.forms["mainDataForm"];//这里默认是单据明细的表单 暂时写死
	}
	if(form){
		for (var i = 0; i < form.elements.length; i++) {
	        var element = form.elements[i];
	        var type = element.type;
	        if (type == "checkbox" || type == "radio") {
	            if ((element.checked != element.defaultChecked).toString()=='true') {
	                return true;
	            }
	        }else if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
	            if ((element.value !== element.defaultValue).toString()=='true') {
	                return true;
	            }
	        }else if (type == "select-one" || type == "select-multiple") {
	            for (var j = 0; j < element.options.length; j++) {
	                if ((element.options[j].selected != element.options[j].defaultSelected).toString()=='true') {
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
}

//设置表单控件的defaultValue值
function changeFormDefaultValue(form) {
	if(!form){
		form=document.forms["mainDataForm"];//这里默认是单据明细的表单 暂时写死
	}
	if(form){
		for (var i = 0; i < form.elements.length; i++) {
	        var element = form.elements[i];
	        var type = element.type;
	        if (type == "checkbox" || type == "radio") {
	        	element.defaultChecked=element.checked;
	        }else if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
	        	element.defaultValue=element.value;
	        }else if (type == "select-one" || type == "select-multiple") {
	            for (var j = 0; j < element.options.length; j++) {
	            	element.options[j].defaultSelected=element.options[j].selected;
	            }
	        }
	    }
	}
}

//重载ajaxRequest 加入同步异步参数
function ajaxRequestWithAsync(url, reqParam, async, callback) {
	$.ajax({
		async : async,
		type : 'POST',
		url : url,
		data : reqParam,
		cache : async,
		success : callback
	});
};



/***************包含所有GMS单据类所用到的公共函数*******************/
/** ************************************************************** */
//document.domain = "belle.net.cn";


function Envirment() {
	this.setData = function(key, data) {
		if (!data)
			delete $.fas.env.window._store[key];
		else
			$.fas.env.window._store[key] = data;
	};
	
	this.getData = function(key) {
		return $.fas.env.window._store[key];
	};
}
$.fas.env = new Envirment();
$(function($) {
	var win = window;
	$.fas.env.window = win;
	$.fas.env.window._store = {};
});
/** ************************************************************** */



// 单据控制器
function BillController() {
	this.autoAaddDtl=true;//保存表头的时候是否自动添加明细
	this.bill = {
		status : -1
	};
}

//新增单据时，预处理（可以加默认值）
BillController.prototype.addBeforeHandle=function(){
	$("#mainDataForm").form("setDefaultValue");
	//各单据类自己实现
};

// 新增单据
BillController.prototype.add = function() {
	var self=this;
	if(fas.common.formDirtyNoAlert()){
		$.messager.confirm("确认", "当前表单未保存，是否直接新增？", function(r) {
			if (r) {
				add(self);
			}
		});
	}else{
		add(self);
	}
	
	function add(self){
		ctrl.billHeader.mainForm.form('clearQueryParams');
		self.setBill({
			status : 0
		});
		self.addBeforeHandle();
		setTimeout(function() {
			$("#mainDataForm").navigation('focus');
		}, 100);
	}
};

// 切换tab
BillController.prototype.returnTabs = function(type) {
	//设置全局变量标识现在是明细tab还是查询列表tab
	if(type==1){
		fas.data.atList=true;
	}else{
		fas.data.atList=false;
	}
	this.billHeader.returnTabs(type);
};

// 作废单据
BillController.prototype.cancel = function(statusFlag) {
	if (typeof statusFlag == 'undefined') {
		statusFlag = 'confirm';
	}
	var self = this;
	if (!this.bill || !this.bill.billNo) {
		showInfo("请选择相应的单据再作废！");
		return;
	}
	this.checkStatus(statusFlag).then(function(data1) {
		$.messager.confirm("确认", "确定要作废该条单据?", function(r) {
			if (r) {
				var url = "cancel";
				var params = {
					billNo : self.bill.billNo,
					shardingFlag:self.bill.shardingFlag
				};
				$.post(url, params, function(result) {
					
					if (result) {
						if (isNotBlank(result.errorMessage)) {
							showError(result.errorMessage + " " + result.errorDefined);
						} else {
							self.setBill(result, 1);
							showSuc('作废成功!');
						}
					} else {
						showInfo('作废失败,请联系管理员!', 2);
					}
				});
			}
		});
	});
};

// 删除单据
BillController.prototype.del = function(statusFlag) {
	var self = this;
	if (!this.bill || !this.bill.billNo) {
		showInfo("请选择相应的单据再删除！");
		return;
	}
	this.checkStatus(statusFlag).then(function(data1) {
		$.messager.confirm("确认", "确定要删除该条单据?", function(r) {
			if (r) {
				var url = "delete"+"?billNo="+ self.bill.billNo;
				var params = {
					shardingFlag:self.bill.shardingFlag
				};
				$.post(url, params, function(result) {
					if (result) {
						if (isNotBlank(result.errorMessage)) {
							showError(result.errorMessage + " " + result.errorDefined);
						} else {
							self.setBill({});
							showSuc('删除成功!');
						}
					} else {
						showInfo('删除失败,请联系管理员!', 2);
					}
					
				});
			}
		});
	});
};

/**
 * 保存单据 saveType 保存类型 1：保存头挡 2：保存明细 3:保存按钮，强制保存全部
 */
BillController.prototype.save = function(saveType, statusFlag) {
	$("#billNo").focus();//必须 让单据明细编辑状态失去焦点才能getchanges
	if (typeof statusFlag == 'undefined') {
		statusFlag = 'addSave';
	}
	var self = this;
	// 校验表单数据
	if (!self.validate()) {
		return false;
	}
	this.checkStatus(statusFlag).then(function(data1) {
		if(saveType && saveType!=3){
			if (!($("#dtlDataGrid").datagrid("getRows") && $("#dtlDataGrid").datagrid("getRows").length > 0)) {
				var datas = $("#dtlDataGrid").datagrid('getChanges');
				if (!(datas && datas.length > 0)) {
					saveType = 1;
				}
			}
		}else{
			saveType=null;//清空saveType，保存全部
		}
		if (saveType) {
			if (saveType == 1) {
				self.billHeader.lock();
				var data=$.extend({},self.billHeader.mainForm.form('getData'),self.billHeader.mainForm.form('getCombo'));
				self.setBill(data, 0);//0 不刷新
				setTimeout(function() {
					if(self.autoAaddDtl){
						self.billDetail.add();
					}
				}, 500);// 后新增一行明细
			} else if (saveType == 2) {
				return self.billDetail.save();
			}
		} else {
			// 保存顺序 颠倒(非尺码横排 索赔单使用)
			if (self.saveInversions) {
				var result = true;
				self.billDetail.save(self.bill);
				if (result) {
					var def = self.billHeader.save().then(function(data) {
						self.setBill(data, 1);
						return data;
					}).fail(function(data) {
						result = false;
					});
					def.then(function(data) {
						result = true;
					}).fail(function(data) {
						result = false;
					});
				}
				return result;
			} else {
				var def = self.billHeader.save().then(function(data) {
					self.setBill(data, 1);
					return data;
				}).fail(function(data) {
//					self.reject(data);// 返回非成功状态时使用
					return false;
				});
				def.then(function(data) {
					return self.billDetail.save(data);
				}).fail(function(data) {
					return false;
				});
			}
		}
	});
};

// 单据确认
BillController.prototype.confirm = function(statusFlag,myUrl) {
	if (typeof myUrl == 'undefined') {
		myUrl = "confirm";
	}
	var self = this;
	$.messager.confirm("确认", "确定要确认该条单据?", function(r) {
		if (r) {
			self.confirmHandler(statusFlag, myUrl);
		}

	});
};

// 单据确认1
BillController.prototype.confirmHandler = function(statusFlag, myUrl) {
	var self = this;
	if (!self.billDetail.endEdit()) {// 先校验并结束编辑
		return;
	}
	// 判断页面表单是否被修改
	if (fas.common.formDirtyNoAlert()) {
		showWarn("表单已被修改，请先保存！");
	}else{
		var self=this;
		var url = myUrl;
		var params = {
				billNo : ctrl.bill.billNo,
				shardingFlag:ctrl.bill.shardingFlag
		};
		
		$.post(url, params).then(function(data) {
			if (data) {
				if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
					var errorData = $.parseJSON(data.errorDefined);
					$.fas.common.validateInventory(errorData);
				} else {
					if (isNotBlank(data.errorMessage)) {
						showError(data.errorMessage + " " + data.errorDefined);
					} else {
						if (isNotBlank(data.warnMessageData)) {
							ctrl.showWarnMessage(data.warnMessageData);
						} else if(isNotBlank(data.qtyMessageData)){
							ctrl.qtyMessage(data.qtyMessageData);
						}else{
							showSuc('确认成功!');
							ctrl.search();
							$('#mainDataForm').form('load', data);
							$("#buyerNameCon").combobox("disable");
							$("#salerNameCon").combobox("disable");
							$("#supplierNameId").combobox("disable");
						}
					}
				}
			} else {
				showInfo('确认失败,请联系管理员!');
			}
			
		});
	}
};

// 确认仓库出库时,先校验库存是否足够 仓库支持负库存出出库
BillController.prototype.checkInventory = function(myUrl,isBatch,bill){
	var self=this;
	//需要在所有出库表:仓出店 移仓出库 跨区调货出库 批发/团购出库 借用 库存数量调整 原残退厂发货/地区客残鉴定处理设置该参数qtyField
	if(self.qtyField){
		if(!self.checkType){
			self.checkType='normal';
		}
		//标识无须校验noCheck
		if(self.checkType=='noCheck'){
			return true;
		}
		var paramBill=ctrl.bill;
		if(isBatch){
			paramBill=bill;
		}
		
		var url = "checkInventory";
		var storeNo=paramBill.storeNoFrom;
		if(!isNotBlank(storeNo)){
			storeNo=paramBill.storeNo;
			if(!isNotBlank(storeNo)){
				storeNo=paramBill.sendStoreNo;
				if(!isNotBlank(storeNo)){
					storeNo=paramBill.saleStoreNo;
				}
			}
		}
		var orderUnitNo=paramBill.orderUnitNoFrom;
		if(!isNotBlank(orderUnitNo)){
			orderUnitNo=paramBill.orderUnitNo;
		}
		
		var billType=paramBill.billType;
		//调货出库单billtype有4种类型，特殊处理
		if(billType=='1320'||billType=='1327'||billType=='1322'||billType=='1328'){
			billType='1371';
		}
		//批发出库单 billType=='1335'&&bizType=='21' 批发销售，特殊处理
		if((billType=='1335'&&paramBill.bizType=='21')||(billType=='1335'&&paramBill.bizType=='23')){
			billType='13353';
		}
		//批发出库单 billType=='1335'&&bizType=='13' 物料出库，特殊处理
		if(billType=='1335'&&paramBill.bizType=='13'){
			billType='133513';
		}
		//批发出库单 billType=='1335'&&bizType=='14' 辅料出库，特殊处理
		if(billType=='1335'&&paramBill.bizType=='14'){
			billType='133514';
		}
		var params = {
			billNo : paramBill.billNo,
			shardingFlag:paramBill.shardingFlag,
			tableName:ctrl.billDetail.tableName,
			qtyField:self.qtyField,
			storeNo:storeNo,
			orderUnitNo:orderUnitNo,
			checkType:self.checkType,
			billType:billType
		};
		if(billType=='1347'){
			params.searchInventoryType="Y"; 
		}
		var bo=false;
		ajaxRequestWithAsync(url, params, false, function(result){
			if (result) {
				if (isNotBlank(result.errorMessage)) {
					
					showError(result.errorMessage + " " + result.errorDefined);
					bo = false;
				} else {
					if(result.success){
						bo = true;
					}else{
						if(isBatch){
							if(result.allowDone){
								bo = true;
							}else{
								bo = false;
							}
						}else{
							$.fas.common.validateInventory(result,myUrl,result.allowDone);
							bo = false;
						}
					}
				}
			}
		});
		return bo;
	}else{
		return true;
	}
};

// 单据审核
BillController.prototype.verify = function(statusFlags) {
	var self = this;
	if (!self.bill || !self.bill.billNo) {
		showInfo("请选择相应的单据再审核！");
		return;
	}
	// 校验表单数据
	if (!self.validate()) {
		return;
	}
	// 判断页面表单是否被修改 给提示
	if ($.fas.common.formDirty()) {
		return;
	}
	$.messager.confirm("确认", "确定要审核该条单据?", function(r) {
		if (r) {
			
			if(!statusFlags){
				statusFlags=statusFlag.CONFIRM;
			}
			self.checkStatus(statusFlags).then(function(data) {
			if ($.fas.common.checkHasDetails(self.bill.billNo, self.billDetail.dtlModulePath,self.bill.shardingFlag)) {
					var url = "verify";
					var params = {
						billNo : self.bill.billNo,
						shardingFlag : self.bill.shardingFlag
					};
					$.post(url, params).then(function(data) {
						if (data) {
							if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
								var errorData = $.parseJSON(data.errorDefined);
								$.fas.common.validateInventory(errorData);
							} else if (isNotBlank(data.errorMessage)) {
								showError(data.errorMessage + " " + data.errorDefined);

							} else {
								if (isNotBlank(data.warnMessageData)) {
									ctrl.showWarnMessage(data.warnMessageData);
								} else {
									showSuc('审核成功!');
								}
								self.setBill(data, 1);
							}

						} else {
							showInfo('审核失败,请联系管理员!');
						}
						
					});

				}
			});
		}
	});

};

// 完结单据
BillController.prototype.over = function() {
	var self = this;
	if (!self.bill || !self.bill.billNo) {
		showInfo("请选择相应的单据再完结！");
		return;
	}
	// 校验表单数据
	if (!self.validate()) {
		return;
	}
	// 判断页面表单是否被修改 给提示
	if ($.fas.common.formDirty()) {
		return;
	}
	
	$.messager.confirm("确认", "确定要完结该条单据?", function(r) {
		if (r) {
			
			self.checkStatus(statusFlag.OVER).then(function(data1) {
				var url = "over";
				var params = {
					billNo : self.bill.billNo,
					shardingFlag : self.bill.shardingFlag
				};
				
				$.post(url, params).then(function(data) {
					if (data) {
						if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
							var errorData = $.parseJSON(data.errorDefined);
							$.fas.common.validateInventory(errorData);
						} else {
							if (isNotBlank(data.errorMessage)) {
								showError(data.errorMessage + " " + data.errorDefined);
							} else {
								showSuc('完结成功!');
								self.setBill(data, 1);
							}
						}
					} else {
						showInfo('完结失败,请联系管理员!');
					}
					
				});
			});
		}
	});
};

// 单据校验
BillController.prototype.validate = function() {
	var result = false;
	if (this.billHeader.validate()) {
		if (this.billDetail.validate()) {
			result = true;
		} else {
			result = false;
		}
	} else {
		result = false;
	}
	return result;
};

// 单据检测状态
BillController.prototype.checkStatus = function(flag) {
	var def = $.Deferred();
	var self = this;
	var fo = false;
	if (flag != 'addSave') {
		if (isNotBlank(self.bill.billNo)) {
			fo = true;
		} else {
			showWarn("请选择新单或先保存头挡！");
			setTimeout(function() {
				def.reject();
			}, 1);
		}
	} else {
		if (isNotBlank(self.bill.billNo)) {
			fo = true;
		} else {
			def.resolve();
		}
	}
	if (fo) {
		if (!self.bill) {
			return;
		}
		$.get('get', {
			billNo : self.bill.billNo,
			shardingFlag : self.bill.shardingFlag
		}).then(function(data) {
			if (isNotBlank(data.errorMessage)) {
				showError(data.errorMessage + " " + data.errorDefined);
				def.reject();
			} else {
				 var data = {
				 status : $("#status").parent().find("span").find("input").last().val().trim()
				 //billNo : self.bill.billNo
				 };
				if (!isNotBlank(flag) || flag == 'addSave') {
					flag = "put";// 默认要校验建单状态
				}
				switch (flag) {
				case "over":// 完结操作
					if (!(data.status == 50)) {
						def.reject({
							status : data.status
						});
						showInfo("只有未完结状态的单才能完结,请重新选择单据！");
						
					}
					break;
				case "put":// 判断是否是制单状态
					if(self.checkDiffrences){
						if (data.status != 0) {
							def.reject({
								status : data.status
							});
							showInfo("该单据不是制单或确认状态,请重新选择!");
							
						}
					}else if(self.checkTransport){
						if(data.status != 0){
							def.reject({
								status : data.status
							});
							showInfo("该单据不是制单或提交状态,请重新选择!");
							
						}
					}else{
						if (data.status != 0) {
							def.reject({
								status : data.status
							});
							showInfo("该单据不是制单状态,请重新选择!");
							
						}
					}
					break;
				case "verify":// 判断是否是审核状态
					if (data.status != 3) {
						def.reject({
							status : data.status
						});
						showInfo("该单据不是审核状态,请重新选择!");
						
					}
					break;
				case "confirm":// 判断是否是确认状态
					if (data.status != 5) {
						def.reject({
							status : data.status
						});
						showInfo("该单据不是确认状态,请重新选择!");
						
					}
					break;
				case "send":// 判断是否是已发未收
					if (data.status != 1) {
						def.reject({
							status : data.status
						});
						showInfo("该单据不是已发未收状态,请重新选择!");
						
					}
					break;
				case "received":// 判断是否是已发未收
					if (data.status != 2) {
						def.reject({
							status : data.status
						});
						showInfo("该单据不是已收货状态,请重新选择单据!");
						
					}
					break;
				case "handing":// 判断是否是处理中（开始盘点）状态
					if (data.status != 6) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是处理中,请重新选择");
						
					}
					break;
				case "unpick":
					if (data.status != 10) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是未拣货,请重新选择");
						
					}
					break;
				case "submit":
					if (data.status != 0 && data.status != 20) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是制单或者待审批状态,请重新选择");
						
					}
					break;
				case "waitverify":
					if (data.status != 20) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是待审批状态,请重新选择");
						
					}
					break;
				case "verifyedwaitsend":
					if (data.status != 21) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是已核待发状态,请重新选择");
						
					}
					break;
				 case "handled":// 判断是否是处理中（开始盘点）状态
					if (data.status != 7) {
						def.reject({
							status : data.status
						});
						showInfo("单据状态不是已处理状态,请重新选择");
						
					}
					break;
				}
				setTimeout(function() {
					def.resolve(data);
				}, 5);
			}
		});
	}
	return def.promise();
};

// 单据检测状态
BillController.prototype.checkStatusSimple = function(billStatus) {
	if (typeof billStatus == 'undefined') {
		billStatus = billStatusEnums.MAKEBILL;
	}
	if (this.bill.status != billStatus) {
		if (billStatus == billStatusEnums.SEND) {
			showInfo("该单据不是已发未收状态，不能再对此单据进行操作！");
		} else if(billStatus == billStatusEnums.RECEIVED){
			showInfo("该单据不是已收货状态，不能再对此单据进行操作！");
		}else{
			showInfo("该单据不是制单状态，不能再对此单据进行操作！");
		}
		return false;
	}
	return true;
};

//获取单据状态
BillController.prototype.getBillStatus = function(billNo) {

	var status='';
	ajaxRequestWithAsync('get', {billNo : billNo}, false, function(data){
		if (isNotBlank(data.errorMessage)) {
			showError(data.errorMessage + " " + data.errorDefined);
			status='-1';
		} else {
			status=data.status;
		}
	});
	return status;
};

//获取单据状态并校验状态是否正确
BillController.prototype.checkListStatus = function(billNo,shardingFlag,flag) {
	var result=true;
	var billStatus = this.getBillStatus(billNo,shardingFlag);
	switch (flag) {
		case billStatusEnums.CONFIRM:// 完结操作
			if (!(billStatus == 50)) {
				result=false;
			}
			break;
		case billStatusEnums.MAKEBILL:// 判断是否是制单状态
			if(self.checkDiffrences){
				if (billStatus == 0) {
					result=false;
				}
			}else if(self.checkTransport){
				if(billStatus != 1 ){
					result=false;
				}
			}else{
				if (billStatus != 1) {
					result=false;
				}
			}
			break;
		case "put1":
			if (billStatus != 1 ) {
				result=false;
			}
			break;
	}
	return result;
};

//获取单据状态不对的时候应该弹出什么提示
BillController.prototype.checkListStatusMessage = function(flag) {
	var result="";
	switch (flag) {
		case billStatusEnums.MAKEBILL:// 判断是否是制单状态
			if(flag.status == 0){
				result="该单据是制单状态不能反确认,请重新选择!";
			}
			break;
	}
	return result;
};

// 删除单据(批量)
BillController.prototype.batchDel = function() {
	this.billList.batchDel();
};

// 审核单据(批量)
BillController.prototype.batchVerify = function() {
	this.billList.batchVerify();
};

// 确认单据(批量)
BillController.prototype.batchConfirm = function(billStatus, myUrl) {
	this.billList.batchConfirm(billStatus, myUrl);
};

// 作废单据(批量)
BillController.prototype.batchCancel = function() {
	this.billList.batchCancel();
};

// 完结单据(批量)
BillController.prototype.batchOver = function() {
	this.billList.batchOver();
};

// 下一单
BillController.prototype.next = function() {
	this.billList.moveNext();
};

// 上一单
BillController.prototype.prev = function() {
	this.billList.movePre();
};

// 单据查询
BillController.prototype.search = function(paramsIds) {
	this.billList.search(paramsIds);
};

// 单据查询清空表单
BillController.prototype.clear = function() {
	this.billList.clear();
};

// 新增明细
BillController.prototype.addDetail = function(statusFlag) {
	if (typeof statusFlag == 'undefined') {
		statusFlag = 'addSave';
	}
	var self = this;
	// 判断头挡是否保存，没有保存则先保存头挡再新增明细
	this.checkStatus(statusFlag).then(function(data1) {
		if (!self.bill.billNo) {
			// 先保存单据
			if (self.save(1) == false) {
				return;
			}
		} else{
			self.billDetail.add();// 后新增一行明细
		}
	});
};

// 删除明细
BillController.prototype.deleteDtl = function(statusFlag) {
	if (typeof statusFlag == 'undefined') {
		statusFlag = 'addSave';
	}
	var self = this;
	this.checkStatus(statusFlag).then(function(data1) {
		self.billDetail.del(statusFlag);
	});
};

// 清空明细 直接删除表数据
BillController.prototype.deleteAllDtl = function(statusFlag) {
	if (typeof statusFlag == 'undefined') {
		statusFlag = 'addSave';
	}
	var self = this;
	this.checkStatus(statusFlag).then(function(data1) {
		self.billDetail.deleteAll();
	});
};

/**
 * 填充单据 刷新单据
 * 
 * @param val
 * @param refreshType
 *            刷新类型 1：刷新头挡和工具栏 2：刷新明细 0：不刷新
 */
BillController.prototype.setBill = function(val, refreshType) {
	//设置全局变量标识现在是明细tab还是查询列表tab
//	$.fas.data.atList=false;
	
	if (this.bill == val)
		return;
	// noinspection JSPotentiallyInvalidUsageOfThis
	this.bill = val;
	this.billDetail.bill = val;
	this.billHeader.bill = val;
	this.toolbars.bill = val;
	if(refreshType!=null&&refreshType==0){
		return;
	}
	this.refresh(refreshType);
};

/**
 * 刷新 refreshType 刷新类型 1：刷新头挡和工具栏 2：刷新明细
 */
BillController.prototype.refresh = function(refreshType) {
	if (refreshType) {
		if (refreshType == 1) {
			this.billHeader.refresh();
			this.toolbars.refresh();
		} else if (refreshType == 2) {
			this.billDetail.refresh();
		} else {
			this.billHeader.refresh();
			this.billDetail.refresh();
			this.toolbars.refresh();
		}
	} else {
		this.billHeader.refresh();
		this.billDetail.refresh();
		this.toolbars.refresh();
	}
	setTimeout(function() {
		changeFormDefaultValue();
	}, 2000);// 设置表单默认值 好监听表单是否被修改过
};

// 导入整张单（甚至多个单）
BillController.prototype.import = function(data) {
	this.billDetail.import(data);
};

// 导入
BillController.prototype.importDetail = function(data) {
	this.billDetail.importDetail(data);
};

//导入  作业单通用导入
BillController.prototype.importDetailForWorkBill = function() {
	this.billDetail.importDetailForWorkBill();
};

// 打印atListPage不为空说名是查询列表操作
BillController.prototype.print = function(atListPage,showSalePrice) {
	this.billDetail.print(atListPage,showSalePrice);
};

// 导出 atListPage不为空说名是查询列表操作
BillController.prototype.exportExcel = function(atListPage) {
	this.billDetail.exportExcel(atListPage);
};

//导出  作业单通用导出
BillController.prototype.exportExcelForWorkBill = function() {
	this.billDetail.exportExcelForWorkBill();
};

//自定义列导出明细（与导出不同 此功能为定制化明细导出 仓出店单）
BillController.prototype.exportDetail = function() {
	this.billDetail.exportDetail();
};

// 初始化
BillController.prototype.init = function(toolbar, header, detail, list) {
	var self = this;
	$.fas.env.ctrl = this;
	this.toolbars = toolbar;// new Toolbars();
	this.billHeader = header;// new BillHeader();
	this.billList = list;// new BillList();
	this.billDetail = detail;// new BillDetail();
	if (this.billDetail.hasSizeHorizontal) {
		this.billDetail.endColNamesDtl.push(this.billDetail.columns.brandNo);// 统一加上品牌编码
		$.each(this.billDetail.endColNamesDtl, function(i, item) {
			// 统一加上编辑器（方便取值）
			if (!item.hasOwnProperty("editor")) {
				item.editor = {
					type : 'readonlytext'
				};
			}
			// 金额字段使用千分位格式，保留2位小数
			if ((item.field.toLowerCase().indexOf('price')>=0 || item.field.toLowerCase().indexOf('cost')>=0) && !item.hasOwnProperty("formatter")) {
				item.formatter = function(value, row, index) {
					if(value){
						return priceHandle(value);
					}
					return '';
				};
			}
		});
	}

	this.toolbars.ctrl = this;
	this.billHeader.ctrl = this;
	this.billList.ctrl = this;
	this.billDetail.ctrl = this;

	this.billList.init();
	this.toolbars.init();
	this.billDetail.init();

	this.billList.onRowChange(function() {
		self.setBill(self.billList.selectedBill);
	});
	
	$('#mainDataForm').navigation({
		onLast : function() {
			if (!self.bill.billNo || self.bill.billNo == "") {
				self.addDetail();
				return;
			}
			var data = self.billDetail.getData();
			if (data && data.length > 0)
				self.billDetail.edit(0);
			else
				self.addDetail();
		}
	});
	toolSearch({
		appendTo : $('#mainbar')
	});// 工具栏加折叠效果
	
	$(".form-tb").find('col[width]').attr("width",90);
};

// 通常一个单 ,只有一种警告信息 所以先定义BillController 如有多个请用warnMessageData区分
BillController.prototype.showWarnMessage = function(warnMessageData, bts) {
	if (warnMessageData) {
		var warnMessageDialog = $('#warnMessageDialog');
		// $('#warnMessageDialog').dialog({title: "你的title"});
		if (!bts) {// 默认按钮 ,覆盖请传[]
			bts = [ {
				iconCls : 'icon-ok',
				text : '继续',
				handler : function() {
					warnMessageDialog.dialog("close");
					ctrl.confirmHandler('addSave', "confirm" + "?prettyConfirm=Y");
				}
			}, {
				iconCls : 'icon-close',
				text : '取消',
				handler : function() {
					warnMessageDialog.dialog("close");
				}
			} ];
		}
		;
		warnMessageDialog.dialog({
			buttons : bts
		});
		$('#warnMessageDialog').show().window('open');
		$('#warnMessageDatagrid').datagrid('loadData', warnMessageData);
	}
};

// 单据确认时,如果有差异弹出有效的提示信息
BillController.prototype.diffMessage = function(diffMessageData) {
	if (diffMessageData) {
		var diffMessageDialog = $('#diffMessageDialog');
		bts = [ {
			iconCls : 'icon-close',
			text : '确认',
			handler : function() {
				diffMessageDialog.dialog("close");
				// 下面是到货单直接收货的情况使用
				try {
					billReceiptWork.closeReceiptWork();
				} catch (e) {
				}
			}
		} ];
		diffMessageDialog.dialog({
			buttons : bts
		});
		$('#diffMessageDialog').show().window('open');
		$('#diffMessageData').datagrid('loadData', diffMessageData);
	}
};

BillController.prototype.qtyMessage = function(qtyMessageData) {
	if (qtyMessageData) {
		//warnQtyDialog  qtyMessageDatagrid
		var qtyMessageDialog = $('#outInventoryDialog');
		if (qtyMessageData.allowDone) {
			bts = [ {
				iconCls : 'icon-ok',
				text : '继续',
				handler : function() {
					qtyMessageDialog.dialog("close");
					ctrl.confirmHandler('addSave', "confirm" + "?prettyConfirm=checkQty");
				}
			}, {
				iconCls : 'icon-close',
				text : '取消',
				handler : function() {
					qtyMessageDialog.dialog("close");
				}
			} ];
		}else{
			bts = [{
				iconCls : 'icon-close',
				text : '取消',
				handler : function() {
					qtyMessageDialog.dialog("close");
				}
			} ];
		}
		qtyMessageDialog.dialog({
			buttons : bts,
			title: '以下明细可配存不足,请检查'
		});
		qtyMessageDialog.show().window('open');
		$('#dataGridOutInventory').datagrid('loadData', qtyMessageData);
	}
};

// 单据头挡
function BillHeader() {
	this.mainForm = $('#mainDataForm');
	this.statusData = datasource.forBillHeader('status');
	this.init();
	this.isLock = true;
	this.valideType = false;
}

//新增单据时，预处理（可以加默认值）
BillHeader.prototype.returnTabsBeforeHandle=function(){
	//各单据类自己实现
};

// 切换tab
BillHeader.prototype.returnTabs = function(type) {
	var self = this;
	returnTab('mainTab', type);
	self.returnTabsBeforeHandle();
	if (type == 1) // 浏览tab
		return;
	if (!isNotBlank(this.bill)) {
		ctrl.add();
	}
	setTimeout(function() {
		self.mainForm.navigation('focus');
	}, 100);
};

// 头档刷新
BillHeader.prototype.refresh = function() {
	var self = this;
	var bill = self.bill;
	if (bill.status == -1) {
		this.lock();
		// } else if (bill && bill.billNo && bill.status != 0) {
	} else if (bill && bill.billNo) {
		this.lock();
	} else {
		this.unLock();
	}
	this.mainForm.form('clear');
	// 判断如果billNo不为空 status为空 那么加载出单据头挡
	if (isNotBlank(bill.billNo) && !isNotBlank(bill.status)) {
		
		$.post("get", {
			billNo : self.bill.billNo,
			shardingFlag : self.bill.shardingFlag
		}, function(data) {
			return data;
		}).then(function(data) {
			ctrl.bill = data;
			ctrl.billDetail.bill = data;
			ctrl.billHeader.bill = data;
			ctrl.toolbars.bill = data;
			ctrl.toolbars.refresh();// 刷新状态栏
			self.mainForm.form('load', data);
		});
	} else {
		if (isNotBlank(bill.billNo)) {
			self.mainForm.form('load', bill);
		}
	}
	
};

// 头挡校验
BillHeader.prototype.validate = function() {
	if (this.valideType) {
		if (!this.bill.billNo) {
			return false;
		}
	}
	return this.mainForm.form('validate');
};

// 头档初始化
BillHeader.prototype.init = function() {
	$('#status').combobox({
		valueField : "label",
		textField : "text",
		width : '130',
		readonly : true,
		prompt : ' ',
		hasDownArrow : false,
		data : this.statusData
	});
//	$('#status').combobox("setValue", this.statusData[0].label);
	this.lock();
};

// 头档保存
BillHeader.prototype.save = function(bill) {
	var self = this;
	var saveUrl="post2";
	return self.mainForm.form('post', {
		url : saveUrl,
		onSubmit : function(data) {
			data.id = self.bill.id;
			data = $.extend(bill, data);
			if(self.bill&&self.bill.shardingFlag){
				data.shardingFlag=self.bill.shardingFlag;
			}
			if(!data.status){
				data.status = "0";
			}
			return self.preSave(data);
		}
	}).done(function(data) {
		if (data) {
			return data;
		}
	}).fail(function(){
		
	});
};

BillHeader.prototype.preSave = function(data) {
	return data;
};

// 锁定主档
BillHeader.prototype.lock = function() {
	this.mainForm.find(".easyui-combobox").combobox('disable');
	this.mainForm.find(".easyui-combogrid").combobox('disable');
	this.mainForm.find("input[id!=remark]").attr("readOnly", true).addClass("readonly");
	this.mainForm.find("input[id=remark]").removeClass("readonly");
	this.mainForm.find(".easyui-datebox").datebox('disable');
	this.mainForm.find("input").removeClass('validatebox-invalid');
	$('#status').combobox('disable');
	$('#billNo').attr("readOnly", true);

	this.mainForm.form('disable');// 禁用查询精灵插件
	this.isLock = true;
};

// 解锁定主档
BillHeader.prototype.unLock = function() {
	this.mainForm.find(".easyui-combobox").combobox('enable');
	this.mainForm.find(".easyui-combogrid").combobox('enable');
	this.mainForm.find("input[id!=billNo]").attr("readOnly", false).removeClass("readonly");
	this.mainForm.find(".easyui-datebox").datebox('enable');
	this.mainForm.find("input").removeClass('validatebox-invalid');
	$('#billNo').attr("readOnly", true);
	$('#status').combobox('disable');
	$("#status+span").children("input").attr("readOnly", true).addClass("readonly");
	this.mainForm.form('enable');// 启用查询精灵插件
	this.isLock = false;
};

// 单据明细
function BillDetail() {
	var self = this;
	self.hasSizeHorizontal = true;// 是否是尺码横排
	self._dtlDataGrid = 'dtlDataGrid';
	self.grid = $('#' + self._dtlDataGrid);
	self.recevie = "";// 查询出库还是入库 默认空值
	self.theType = "";// 查询出库还是入库 默认空值
	self.tableName = "";// 明细表名 (必须)
	self.dtlModulePath = "";
	self.templateName = "";// 导入excel文件名
	self.templateNameBatch = "";// 导入下载excel文件名 整张单连头挡一起导入
	self.dtlExcelTitle = "default";// 导出excel文件名
	self.printViewName = "";// 打印视图名称
	self.printTitle = "";// 打印标题
	self.exportExcelParams = "";// 导出excel附加参数 {key:value}
	self.priceType = [];// 取价格类型 参见枚举fas.data.js priceTypeEnums
	self.priceFiled;//取价字段 计算总价需要 priceFiled*数量
	self.getPriceDateField = "";// 取价格时间字段配置
	self.searchType = '';// 查询类型 用于传到后台控制器自定义查询
	self.importSuffix = "";// 导入xml中配置的bizName后缀
	self.importType="";//controller 共用（包含尺码航拍和非尺码横排）
	self.bizName = "";// 导入xml中配置的bizName
	self.getPriceByorderUnitNoField = "orderUnitNo";// 获取地区价默认参数orderUnitNo可以配置取值orderUnitNoFrom
	self.singleSelect=false;//是否多选 默认false
	
	
	// 定义常见的列
	self.COMMON_COLS = {
		itemCode : {
			title : redFlag+"商品编码",
			field : "itemCode",
			width : 120,
			align : 'left',
			editor : {
				type : 'itemEditor',
				options : {
					clickFn : function(data, target){
						if(ctrl){
							ctrl.billDetail.onSelectedItem(data, target);
						}else{
							self.onSelectedItem(data, target);
						}
					},
					isRequired : true
				}
			}
		},
		itemName : {
			title : "商品名称",
			field : "itemName",
			width : 100,
			align : 'left',
			editor : {
				type : 'readonlytext'
			}
		},
		brandName : {
			title : "品牌",
			field : "brandName",
			align : 'left',
			width : 80,
			hidden : false,
			editor : {
				type : 'readonlytext'
			}
		},
		brandNo : {
			title : "品牌编码",
			field : "brandNo",
			width : 120,
			notexport : true,
			hidden : true,
			editor : {
				type : 'readonlytext'
			}
		},
		boxNo : {
			title : "箱号",
			field : "boxNo",
			align : 'left',
			width : 100,
			editor : {
				type : 'boxNoEditor',
				options : {
					required : false
				}
			}
		}
	};

	self.columns = {
		itemCode : self.COMMON_COLS.itemCode,
		itemName : self.COMMON_COLS.itemName,
		brandName : self.COMMON_COLS.brandName,
		brandNo : self.COMMON_COLS.brandNo,
		boxNo : self.COMMON_COLS.boxNo
	};

	self.frozenColumns = [ self.columns.itemCode, self.columns.itemName ];
	self.preColNamesDtl = [ self.columns.brandName, self.columns.boxNo ];
}

//取业务日期 重写目的在于验收单取地区价有特殊的业务，需要按来源单到货单的业务日期
BillDetail.prototype.getBillDate = function(priceType) {
	var billDate='';
	if (ctrl.billDetail.getPriceDateField != '') {
		billDate = $('#' + ctrl.billDetail.getPriceDateField).val();// 取业务日期来取价格
	}
	return billDate;
};

// 明细选定明细行
BillDetail.prototype.onSelectedItem = function(data, target) {
	var quotePriceTemp = 0;// 牌价 默认商品建议牌价
	var costTemp = 0;// 地区价 默认商品建议牌价
	var priceTemp = 0;// 采购价 默认商品建议牌价
	var taxRateTemp = 0;// 动态含税单价
	var headquarterTemp = 0;//总部价

	// 取价
	if (ctrl.billDetail.priceType && ctrl.billDetail.priceType.length > 0) {
		var storeNo = "";
		if (ctrl.bill) {
			if (ctrl.bill.storeNo && isNotBlank(ctrl.bill.storeNo)) {
				storeNo = ctrl.bill.storeNo;
			} else if (ctrl.bill.storeNoFrom && isNotBlank(ctrl.bill.storeNoFrom)) {
				storeNo = ctrl.bill.storeNoFrom;
			} else if (ctrl.bill.sendStoreNo && isNotBlank(ctrl.bill.sendStoreNo)) {
				storeNo = ctrl.bill.sendStoreNo;
			} 
		}
		var orderUnitNo = "";
		if (ctrl.bill) {
			if (ctrl.billDetail.getPriceByorderUnitNoField && ctrl.billDetail.getPriceByorderUnitNoField == "orderUnitNoFrom") {
				if (ctrl.bill.orderUnitNoFrom && isNotBlank(ctrl.bill.orderUnitNoFrom)) {
					orderUnitNo = ctrl.bill.orderUnitNoFrom;
				}
			} else {
				if (ctrl.bill.orderUnitNo && isNotBlank(ctrl.bill.orderUnitNo)) {
					orderUnitNo = ctrl.bill.orderUnitNo;
				}
			}
		}
		
		$.each(ctrl.billDetail.priceType, function(i, item) {
			//如果头档取不到供应商编码就取商品中的默认供应商
			if(!isNotBlank(ctrl.bill.supplierNo)){
				ctrl.bill.supplierNo=data.supplierNo;
			}
			
			$.ajax({
				url : getBasePath("/item/getBillItemPrice"),
				data : {
					itemNo : data.itemNo,
					priceType : item,
					supplierNo : ctrl.bill.supplierNo || '',
					storeNo : storeNo,
					orderUnitNo : orderUnitNo,
					billDate : ctrl.billDetail.getBillDate(item) || ''
				},
				async : false,
				success : function(result) {
					if (result != null) {
						if (isNotBlank(result.errorMessage)) {
							showError('获取价格失败!' + result.errorMessage + " " + result.errorDefined);
						} else {
							if (item == priceTypeEnums.TAGPRICE) {
								quotePriceTemp = parseFloat(result);
							} else if (item == priceTypeEnums.AREAPRICE) {
								costTemp = parseFloat(result);
							} else if (item == priceTypeEnums.PARCHASEPRICE) {
								priceTemp = parseFloat(result);
							} else if (item == priceTypeEnums.TAXCOST) {
								taxRateTemp = parseFloat(result);
							}else if (item == priceTypeEnums.HEADQUARTER) {
								headquarterTemp = parseFloat(result);
							}
						}
					} else {
						showWarn('获取价格失败,请联系管理员!');
					}
				},
				error : function(result) {
					showWarn('获取价格异常,请联系管理员!');
				}
			});
		});
	}
	// 【到货单】 自购类型（业务类型-直接）的到货单, 采购价=地区价。从PMS获取采购价即可。2015年4月16日 14:33:52曾兴安需求，无须再做控制暂时注释
//	if ($('#bizType') && $('#bizType').length > 0) {
//		var myBizType;
//		try {
//			myBizType = $('#bizType').combobox('getValue');
//		} catch (e) {
//			myBizType = $('#bizType').val();
//		}
//		if (myBizType != null && myBizType == bizTypeEnums.DIRECT) {
//			costTemp = priceTemp;
//		}
//	}

	var row = {
		itemNo : data.itemNo,
		itemCode : data.code,
		itemName : data.name,
		sizeKind : data.sizeKind,
		price : priceTemp,
		cost : costTemp,
		quotePrice : quotePriceTemp,
		taxRate : taxRateTemp,
		headquarterPrice:headquarterTemp,
		brandNo : data.brandNo,
		brandName : data.brandName,
		colorNo : data.colorNo,
		colorName : data.colorName,
		categoryNo : data.categoryNo
	};

	row.target = target;
	// 这里必须是$('#dtlDataGrid')
	$('#dtlDataGrid').sizeHorizontal('updateRow', row);
};

// 明细刷新
BillDetail.prototype.refresh = function() {
	if($("#detailTab").length>0){
		try{
			$("#detailTab").tabs('select', 0);//切换到第一个tab
		}catch(e){}
	}
	this._loadDetail();
	if (!this.bill.billNo) {
		this.clear();
	}
};

// 明细清除
BillDetail.prototype.clear = function() {
	var $dg = this.grid;
	$dg.datagrid('options').url = "";// 这里一定要设置url为空
	var rows = $dg.datagrid('getRows');
	if (rows) {
		while (rows.length > 0) {
			$dg.datagrid('deleteRow', 0);
			// 这样写解决每次清除后datagrid('getChanges', "deleted"); 删除之前的行问题
			rows = $dg.datagrid("getRows");
			// $dg.datagrid("loadData", rows);
		}
		$dg.datagrid('acceptChanges');
	}
	$dg.datagrid('editIndex', -1);
	$dg.datagrid('reloadFooter',[]);//清空footer
};

// 明细初始化
BillDetail.prototype.init = function() {
	var self = this;
	var $dg = self.grid;
	if (self.hasSizeHorizontal) {
		var options = {
			module : self.dtlModulePath,
			frozenCols : self.frozenColumns,
			preCols : self.preColNamesDtl,
			endCols : self.endColNamesDtl,
			recevie : self.recevie,
			theType : self.theType,
			billDtlTableName : self.tableName,
			showFooter : true,
			singleSelect:self.singleSelect
		};
		if (self.pageSize) {
			options.pageSize = self.pageSize;
		}
		$dg.sizeHorizontal(options);
	}
};

BillDetail.prototype.showTotalInPage = function() {
	var self = this;
	var page = self.grid.datagrid('getPager');
	var totalStr = self.calcTotal(self.totalOptions);
	if (totalStr) {
		if ($("#totalFooterDiv")) {
			$(".pagination-info", page).before("<div id='totalFooterDiv' style='float: left; margin-left: 150px; line-height: 30px;font-weight: bold'></div>");
		}
		$("#totalFooterDiv").html(totalStr);
	}
};

// 页脚价格合计(可以扩展为 页脚价格和总数量汇总)
BillDetail.prototype.calcTotal = function() {
	var self = this;
	var options = self.totalOptions;
	if (options == null) {
		options = {
			"allCounts" : '总数量',
			"allPrice" : "总金额"
		};
	}
	var rows = self.grid.datagrid('getRows');
	var total = 0;
	for ( var field in options) {
		// 格式化价格
		if (field.toLowerCase().indexOf("cost") != -1 || field.toLowerCase().indexOf("price") != -1) {
			$(rows).each(function(i, v) {
				total += parseFloat(v[field] || 0);
			});
			total = parseFloat(total).toFixed(2);
			break;
		}
	}
	return priceHandle(total);
};

// 明细添加
BillDetail.prototype.add = function(row) {
	row = row || {};
	var self = this;
	if (self.hasSizeHorizontal) {
		self.grid.sizeHorizontal('append', row);
		//*************加尺码数量底部合计动态变化
		var rows = self.grid.datagrid('getFooterRows');
		if(rows&&rows.length==0){
			var footRow={allCounts:''};
			for(var i=1;i<51;i++){
				footRow['v'+i]='';
			}
			var footRows=new Array();
			footRows.push(footRow);
			self.grid.datagrid("reloadFooter",footRows);
		}
	} else {
		var rows = self.grid.datagrid('getRows');
		if(rows.length>0){
			if (!fas.common.endEditing("dtlDataGrid")) {
				return;
			}
		}
		$.data($("#dtlDataGrid")[0], 'editIndex', rows.length);// 设置当前编辑行的行数
		$.fn.datagrid.methods.bindColumnsOptions($("#" + self._dtlDataGrid + ""));
		addDataGridCommon(self._dtlDataGrid);
		fas.common.validateGrid("dtlDataGrid", $("#dtlDataGrid").datagrid('editIndex'));
		fas.common.addNavigation();//非尺码横排加键盘导航
	}
};

// 明细删除
BillDetail.prototype.del = function() {
	var self = this;
	var grid = self.grid;
	var rows = grid.datagrid('getSelections');
	var editIndex = grid.datagrid('editIndex');
	if (rows && rows.length > 0) {
		$.messager.confirm("确认", "确定要删除选中的明细么？", function(r) {
			if (r) {
				$.each(rows, function(i, row) {
					var rowIndex = grid.datagrid('getRowIndex', row);
					grid.datagrid('deleteRow', rowIndex);
					if (editIndex == rowIndex)
						grid.datagrid('editIndex', -1);
				});
			}
		});
	} else {
		showWarn("请选择一条明细删除！");
	}
};

BillDetail.prototype.getData = function() {
	var data = this.grid.datagrid('getData');
	if (data)
		return data.rows;
	return [];
};

BillDetail.prototype.edit = function(index) {
	this.grid.datagrid('beginEditing', index);
	$.fas.common.addNavigation();//非尺码横排加键盘导航
};

// 明细保存
BillDetail.prototype.save = function(data) {
	var self = this;
	var shardingFlag=self.bill.shardingFlag;
	if(data){
		shardingFlag=data.shardingFlag;
	}
	if (self.hasSizeHorizontal) {
		self.grid.sizeHorizontal('save', {billNo:self.bill.billNo,shardingFlag:shardingFlag}).done(function(data) {
			if (isNotBlank(data.errorMessage)) {
				showError(data.errorMessage + " " + data.errorDefined);
			} else {
				if (isNotBlank(data.warnMessageData)) {
					if(isNotBlank(data.warnMessageData[0].itemCode)){//区别是库存不足还是无效调货，无效调货是itemCode,库存不足是code
						ctrl.showWarnMessage(data.warnMessageData,[]);
					}else{
						$.fas.common.validateInventory(data.warnMessageData,'',false);
					}
				}
				self.refresh();
			}
		}).fail(function(data) {
			return false;
		});
	} else {
		if (!self.endEdit()) {
			return false;
		}
		var url = self.dtlModulePath;
		// 判断有没有自定义保存明细提交方法
		if (self.saveMethod) {
			url += self.saveMethod;
		} else {
			url += "save";
		}
		var effectRow = getChangeTableDataCommon(self._dtlDataGrid);
		var billNoTemp = self.bill.billNo;
		var companyNo = self.bill.companyNo;
		var fo = true;
		$.each(effectRow, function(i, itemStr) {
			// 将Sting强制转换成json格式的数据
			var itemObj = $.parseJSON(itemStr);
			$.each(itemObj, function(j, item) {
				if (item.billNo && item.billNo != billNoTemp) {
					fo = false;
				}
			});
		});
		if (fo) {
			effectRow["billNo"] = billNoTemp;
			effectRow["companyNo"] = companyNo;
			if (isNotBlank(self.theType)) {
				param['theType'] = self.theType;
			}
			
			ajaxRequestWithAsync(url, effectRow, false, function(result) {
				
				if (result) {
					if (isNotBlank(result.errorMessage)) {
						showError('单据明细保存失败!' + result.errorMessage + " " + result.errorDefined);
						return false;
					} else if (result.warnMessageData) {
						ctrl.showWarnMessage(result.warnMessageData, []);
					} else {
						showSuc('单据保存成功!');
					}
					self._loadDetail();
					return true;
				} else {
					showError('单据明细保存失败,请联系管理员!');
					return false;
				}

			});
		}
	}
};

// 确认是否结束编辑
BillDetail.prototype.endEdit = function() {
	var self = this;
	if (self.hasSizeHorizontal) {
		return this.grid.sizeHorizontal('validate');
	} else {
		if (fas.common.endEditing("dtlDataGrid")) {
			return true;
		} else {
			return false;
		}
	}
};

// 明细校验
BillDetail.prototype.validate = function() {
	// 判断明细是否有行信息 没有则只保存头挡
	if (this.grid.datagrid("getRows") && this.grid.datagrid("getRows").length <= 0) {
		return true;
	}
	if (this.hasSizeHorizontal) {
		return this.grid.sizeHorizontal('validate');
	} else {
		if (this.endEdit()) {
			return true;
		}
	}
	return false;
};

// 明细加载
BillDetail.prototype._loadDetail = function() {
	var self = this;
	if (self.hasSizeHorizontal) {
		self.grid.sizeHorizontal('initHead');
		self.grid.sizeHorizontal('load', {billNo:self.bill.billNo,shardingFlag:self.bill.shardingFlag});
	} else {
		var url = self.dtlModulePath;
		if (self.loadDetailMethod) {
			url += self.loadDetailMethod;
		} else {
			url += "list.json";
		}
		url += '?billNo=' + self.bill.billNo;
		if (self.bill.shardingFlag) {
			url += '&shardingFlag=' + self.bill.shardingFlag;
		}
		if (self.bill.refBillNo) {
			url += '&refBillNo=' + self.bill.refBillNo;
		}
		if (self.bill.billType) {
			url += '&billType=' + self.bill.billType;
		}
		self.grid.datagrid({
			url : url,
			showFooter:true
		});
	}
};

/**
 * 清空明细 params: tableName:对应的表名 params:dtlDataGrid :对应的dataGrid的id
 * params:pkValue 单据billNo
 */
BillDetail.prototype.deleteAll = function() {
	var self = this;
	var rows = self.grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var billNo = $('#' + fas.data.mainPrimaryKey).val();
	if(!isNotBlank(billNo)){
		showInfo('没有单据编号，不能清空明细');
		return;
	}
	$.messager.confirm("确认", "确定要清空明细?清空后不可恢复", function(r) {
		if (r) {
			var params = new Object();
			params["billNo"] = $('#' + fas.data.mainPrimaryKey).val();
			params["shardingFlag"] = ctrl.bill.shardingFlag;
			params["billType"] = ctrl.bill.billType;
			// tableName表示明细表的表名称
			params["tableName"] = self.tableName;
			params["updateTotalorNot"]= self.updateTotalorNot;//清空明细后是否 从新更新表档总数量
			var url = BasePath + '/initCache/deleteAllDtl';
			
			ajaxRequest(url, params, function(result) {
				
				if (result) {
					if (isNotBlank(result.errorMessage)) {
						showError(result.errorMessage + " " + result.errorDefined);
					} else {
						showSuc('清空成功!');
						self.clear();
					}
				} else {
					showInfo('清空失败,请联系管理员!', 2);
				}

			});
		}
	});
};

/**
 * 键盘回车事件添加明细 params:modulePath 单据主表的url
 */
BillDetail.prototype.keydownFunction = function() {
	$("#subLayout").attr("tabindex", -1);
	$("#subLayout").focus();
	$("#subLayout").unbind('keypress').bind('keypress', function(e) {
		var key = e.keyCode;
		if (key == 13) {
			var pkValue = $('#' + fas.data.mainPrimaryKey).val();
			if (!pkValue) {
				return;
			}
			ctrl.addDetail();
		}
	});
};

/**
 * 导入整张单（甚至多张单） 规则：凡是导入整张单的都加后缀"Batch"   
 * 导入整张单但是先传入部分导入字段参数都加后缀“Custom”（参见SizeHorizontalDTO.xml 节点BillTransferNtDtlCustom）
 */
BillDetail.prototype.import = function(data) {
	var self = this;
	if(data){
		if(data=="Custom"){//导入带上自定义头档字段
			var importDiv=$("#importDiv");
			if(importDiv&&importDiv.length>0){
				$("#importForm").form('clearQueryParams');
				importDiv.dialog({
					title:'导入请先选择头档必要信息',
					width: 450,
				    height: 240,
				    modal: true,
				    buttons:[{
				    	text:'选择文件',
				    	handler:function(){
				    		if($("#importForm").form('validate')){
				    			var param = $('#importForm').form('getData');
				    			$.extend(param, $('#importForm').form('getCombo'));
				    			importDiv.dialog("close");
				    			doImport(data,"Custom",param);
				    		}
				    	}
				    }]
				});
			}
		}else{
			doImport(data,"Batch",data);
		}
	}else{
		doImport(data,"Batch");
	}
	
	function doImport(data,afterSuffix,params) {
		var submitUrl=self.dtlModulePath + "import" + "?bizName=" + self.bizName + afterSuffix;
		if(self.importSuffix){
			submitUrl+="&suffix="+ self.importSuffix;
		}
		if(self.bill&&self.bill.noticeDate){
			submitUrl+="&noticeDate=" + self.bill.noticeDate;
		}
		if(self.bill&&self.bill.reqSendDate){
			submitUrl+="&sendDate=" + self.bill.reqSendDate;
		}
		if(params){
			submitUrl+="&"+$.param(params);
		}
		
		var templateName=self.templateNameBatch;
		if(afterSuffix=="Custom"){
			templateName=self.templateNameCustom;
		}
		
		$.importExcel.open({
			'submitUrl' : submitUrl,
			'templateName' : templateName,
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if(isNotBlank(data.success)){
						if (!isNotBlank(data.error)) {
							showSuc('数据导入成功');
							$.importExcel.colse();
						}
						ctrl.setBill({
							"billNo" : data.success
						});
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
	}
};

/**
 * 导入明细
 */
BillDetail.prototype.importDetail = function(data) {
	var self = this;
	if (self.bill == undefined || !isNotBlank(self.bill.billNo)) {
		showWarn("请选择新单！或者先保存头挡！");
		return;
	}
	if (!self.endEdit()) {
		return;
	}
	// 判断页面表单是否被修改 给提示
	if ($.fas.common.formDirty()) {
		return;
	}
	
	var templateName = self.templateName;
	if(isNotBlank(data) && data == '横排'){
		templateName = "（横排）"+templateName;
	}
	
	ctrl.checkStatus('addSave').then(function(data1) {
		var pkValue = self.bill.billNo;
		$.importExcel.open({
			'submitUrl' : self.dtlModulePath + "import" + '?billNo=' + pkValue + "&shardingFlag=" + self.bill.shardingFlag 
			+ "&bizName=" + self.bizName + "&suffix=" + self.importSuffix+ "&importType="+self.importType+ "&templateName="+templateName,
			'templateName' : templateName,
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if(isNotBlank(data.success)){
						if (!isNotBlank(data.error)) {
							showSuc('数据导入成功');
							$.importExcel.colse();
						}
					}
					ctrl.refresh();
				} else {
					showInfo('导入失败,请联系管理员!');
				}
			},
			error : function() {
				$.messager.progress('close');
				showWarn('数据导入失败，请联系管理员');
			}
		});
	});
};

/**
 * 导入  作业单通用导入
 */
BillDetail.prototype.importDetailForWorkBill = function() {
	var self = this;
	if (self.bill == undefined || !isNotBlank(self.bill.billNo)) {
		showWarn("请选择新单！或者先保存头挡！");
		return;
	}
	if(!self.qtyField){
		showError("导入数量字段未定义！");
		return;
	}
	if (!self.endEdit()) {
		return;
	}
	// 判断页面表单是否被修改 给提示
	if ($.fas.common.formDirty()) {
		return;
	}
	ctrl.checkStatus('addSave').then(function(data1) {
		var pkValue = self.bill.billNo;
		$.importExcel.open({
			'submitUrl' : self.dtlModulePath + "import_workbill" + '?billNo=' + pkValue + "&shardingFlag=" + self.bill.shardingFlag,
			'templateName' : self.templateName,
			success : function(data) {
				$.messager.progress('close');
				if (data) {
					if(isNotBlank(data.success)){
						if (!isNotBlank(data.error)) {
							showSuc('数据导入成功');
							$.importExcel.colse();
						}
						ctrl.refresh();
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
	});
};

// 导出 atListPage不为空说明在单据列表页面操作 params 自定义参数{key:value}
BillDetail.prototype.exportExcel = function(atListPage) {
	var self = this;
	if (atListPage) {
		var rowObj = $('#' + ctrl.billList.gridId).datagrid('getChecked');
		var billNoIn = '';
		if (rowObj && rowObj.length > 0) {
			$.each(rowObj, function(index, item) {
				billNoIn += "'" + item.billNo + "',";
			});
			if (isNotBlank(billNoIn)) {
				billNoIn = billNoIn.substring(0, billNoIn.length - 1);
			}
		}
		exportExcelBaseInfo(ctrl.billList.gridId, self.modulePath + "do_fas_export.htm", ctrl.billList.listExcelTitle);
	} else {
		if (self.bill && isNotBlank(self.bill.billNo)) {
			if (fas.common.checkHasDetails(self.bill.billNo, self.dtlModulePath,self.bill.shardingFlag)) {
				if (!self.endEdit()) {
					return;
				}
			}
		} else {
			showWarn("请先选择一张单据或保存新单!");
		}
	}
};

function exportExcelBaseInfo(dataGridId, exportUrl, excelTitle,billNoIn,searchType,exportColumns,shardingFlagIn) {
	var $dg = $("#" + dataGridId + "");
	var params = $dg.datagrid('options').queryParams;
	var grepColumns = $dg.datagrid('options').columns;
	if(exportColumns){
		grepColumns=exportColumns;
	}
	
	var columns = $.grep(grepColumns[0], function(o, i) {
		if(o.title){//查询类表导出时，checkBox列没有title属性
			o.title = o.title.substring(o.title.lastIndexOf('>')+1,o.title.length);//导出时去掉星号标识
			if(o.notexport){
				return true;
			}else{
				if(o.hidden){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return true;
		}
	}, true);
	
	//去掉editor
	var grepColumnsTemp=[];
	var grepColumnsArr=columns;
	for ( var i = 0; i < grepColumnsArr.length; i++) {
		var pre={};
		var titleTemp = grepColumnsArr[i].title;
		if(!isNotBlank(titleTemp)){
			continue;
		}
		pre.title=titleTemp.substring(titleTemp.lastIndexOf('>')+1,titleTemp.length);//导出时去掉星号标识
		pre.width=grepColumnsArr[i].width;
		pre.field=grepColumnsArr[i].field;
		grepColumnsTemp.push(pre);
	}
	
	/**
	*非尺码横排的editor,如果有combobox,
	*后台导出时转换数据会失败，要把editor里面的data删除掉
	*/
	$.each(grepColumnsTemp,function(i,item){
		if(item.editor&&item.editor.options&&item.editor.options.data){
			delete item.editor.options.data;
		}
	});
	
	var exportColumns = JSON.stringify(grepColumnsTemp);
	var url = getBasePath(exportUrl);
	var dataRow = $dg.datagrid('getRows');
	$("#exportExcelForm").remove();
	$("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
	var fromObj = $('#exportExcelForm');
	if (dataRow.length > 0) {
		fromObj.form('submit', {
			url : url,
			onSubmit : function(param) {
				param.exportColumns = exportColumns;
				param.fileName = excelTitle;
				param.rows=30000;//设置导出数量最大值
				if(searchType){
					param.searchType=searchType;//查询类型 用于传到后台控制器自定义查询
				}
				if(isNotBlank(billNoIn)){
					if(billNoIn.indexOf(",")<0){
						billNoIn=billNoIn.substring(1,billNoIn.length-1);
						param.billNo=billNoIn;
					}else{
						param.billNoIn=billNoIn;
					}
				}
				if(isNotBlank(shardingFlagIn)){
//					if(shardingFlagIn.indexOf(",")<0){
//						shardingFlagIn=shardingFlagIn.substring(1,shardingFlagIn.length-1);
						param.shardingFlag=shardingFlagIn;
//					}else{
//						param.shardingFlagIn=shardingFlagIn;
//					}
				}
				if (params != null && params != {}) {
					$.each(params, function(i) {
						if(i=="billNo" && isNotBlank(param[i])){
							return true;
						}
						param[i] = params[i];
					});
				}
			},
			success : function(result) {
				if (isNotBlank(result.errorMessage)) {
					showError('操作失败!' + result.errorMessage + " " + result.errorDefined);
				} else {
					showSuc("导出成功！");
				}
			}
		});
	} else {
		showWarn('查询记录为空，不能导出!', 1);
	}
}


//导出 非尺码横排的单据明细 可以根据自定义业务重写
BillDetail.prototype.exportBillDtlExcel=function(){
	var self = this;
	exportExcelBaseInfo(self._dtlDataGrid, self.dtlModulePath + "do_fas_export.htm", self.dtlExcelTitle + self.bill.billNo, "'"
			+ self.bill.billNo + "'", self.searchType,null,self.bill.shardingFlag);
};

//导出  作业单通用导出
BillDetail.prototype.exportExcelForWorkBill = function() {
	var self = this;
	if (self.bill && isNotBlank(self.bill.billNo)) {
		if ($.fas.common.checkHasDetails(self.bill.billNo, self.dtlModulePath,self.bill.shardingFlag)) {
			if (!self.endEdit()) {
				return;
			}
			// 判断页面表单是否被修改 给提示
			if ($.fas.common.formDirty()) {
				return;
			}
			if(!self.qtyField){
				showError("导出数量字段未定义！");
				return;
			}
			exportExcelForWorkBill(self._dtlDataGrid, self.dtlModulePath + "do_fas_export_workbill.htm", self.dtlExcelTitle + self.bill.billNo, "'"
					+ self.bill.billNo + "'", self.qtyField,self.bill.shardingFlag);
		}
	} else {
		showWarn("请先选择一张单据或保存新单!");
	}
};

//自定义列导出明细（与导出不同 此功能为定制化明细导出 仓出店单）
BillDetail.prototype.exportDetail = function() {
	if(ctrl.bill&&ctrl.bill.billNo){
		exportExcelBillDetail(ctrl.billDetail.exportColumns, ctrl.billDetail.dtlModulePath + "doExportForCustom", ctrl.billDetail.dtlExcelTitle,ctrl.bill.billNo,ctrl.bill.shardingFlag);
	}else{
		showInfo("请先选择一张单据！");
	}
};

// 打印atListPage不为空说名是查询列表操作
BillDetail.prototype.print = function(atListPage,showSalePrice) {
};

// 单据查询列表
function BillList() {
	this.gridId = 'mainDataGrid';
	this.index = 0;
	this.selectedBill = null;
	this._tab = $('#mainTab');
	this.statusType = "0|1|99";// 状态类型标识 默认"制单、确认、作废"
								// 具体用法：参考fas.data.js-datasource
	this.listExcelTitle = "";// 导出标题
	this.exportExcelParams = "";// 导出excel附加参数 {key:value}
}

// 单据查询
BillList.prototype.search = function(paramsIds) {
	var self = this;
	$("#mainDataGrid").datagrid("clearChecked");// 取消全选
	// 稍微延时 查询之前会加载自定义参数
	setTimeout(function() {
		var param = null;
		// 差异报表定制
		if (paramsIds) {
			param = {
				paramsIds : paramsIds
			};
		} else {
			param = $('#searchForm').form('getData');
			$.extend(param, $('#searchForm').form('getCombo'));
		}
		if($('#searchForm').form('validate')){
			param.orderByField = "create_time";//排序方式
			self.loadData(param);
		}
	}, 800);
};

// 查询清空
BillList.prototype.clear = function() {
	$('#searchForm').form('clear');
	$('#searchForm').find("input[name!=type]").val('');
};

BillList.prototype.onRowChange = function(handler) {
	this._tab.bind('rowChange', handler);
};

BillList.prototype.moveNext = function() {
	this.move(2);
};

BillList.prototype.movePre = function() {
	this.move(1);
};

BillList.prototype.move = function(type) {
	var self = this;
	if (this.index < 0) {
		console.log('is lasted');
		return;
	}
	preBill(this.gridId, this.index, type, function(index, data) {
		if (isNotBlank(data)) {
			self.selectBill(index, data);
		} else {
			if (1 == type) {
				showWarn('这已经是第一单了！');
			} else {
				showWarn('这已经是最后一单了！');
			}
		}
	});
};

BillList.prototype.selectBill = function(index, data) {
	this.selectedBill = data;
	this.index = index;
	this._tab.trigger({
		type : "rowChange",
		source : this
	});
};

BillList.prototype.init = function() {
	var self = this;

	this._tab.tabs('add', {
		title : '单据查询',
		selected : false,
		closable : false,
		href : 'list_tabMain.htm',
		onLoad : function() {
			self.onInited();
			var grid = $('#' + self.gridId);
			grid.datagrid({
				onLoadSuccess : function() {
					onLoaded.call(self);
				},
				onDblClickRow : function(index, row) {
					self.selectBill(index, row);
					self._tab.tabs('select', '单据明细');
					$("#buyerNameCon").combobox("enable");
					$("#salerNameCon").combobox("enable");
					$("#supplierNameId").combobox("enable");
				}
//				onClickCell:function(rowIndex,field,value){
//					if("billNo" == field){
//						var row = grid.datagrid("getRows")[rowIndex];
//						self.selectBill(rowIndex, row);
//						self._tab.tabs('select', '单据明细');
//					}
//					if("refBillNo" == field){
//						var row = grid.datagrid("getRows")[rowIndex];
//						if(isNotBlank(row.refBillType) && isNotBlank(row.refBillNo)){
//							$.fas.common.jumpToDetail(row.refBillType,row.refBillNo);
//						}
//					}
//				}
			});
			resize.call(self);
		}
	});
	this._tab.tabs('hideHeader');

	function resize() {
		var grid = $('#' + this.gridId);
		this._tab.tabs({
			onSelect : function(title, index) {
				grid.datagrid('resize', {
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
			onLoad : function() {
				$('#queryConditionDiv').panel('resize', {
					width : function() {
						return document.body.clientWidth;
					}
				});
			}
		});
	}

	function onLoaded() {
		if ($('#' + this.gridId).datagrid('getRows')) {
			this.rowCount = $('#' + this.gridId).datagrid('getRows').length;
		}
		//单据查询表单在普通文本编辑框中回车查询
		$("#searchForm").enterSearch({});
		$(".form-tb").find('col[width]').attr("width",90);
	}
	
};

BillList.prototype.onInited = function() {
	var self = this;
	var statusList = self.statusType.split("|");
	self.statusData = $.grep(datasource.get('status'), function(item, index) {
		var flag = false;
		for ( var i = 0; i < statusList.length; i++) {
			if (item['label'] == statusList[i]) {
				flag = true;
				break;
			}
		}
		return flag;
	});
	var data = self.statusData;
	$('#statusBox').combobox({
		valueField : "label",
		textField : "text",
		editable : false,
		data : data
	});
	$('#statusBox').combobox('select','-1');
};

BillList.prototype.loadData = function(param) {
	var grid = $('#' + this.gridId);
	grid.datagrid('options').queryParams = param;
	grid.datagrid('options').url = "list.json";
	grid.datagrid('load');
};

// 删除单据(批量)
BillList.prototype.batchDel = function(checkFlagFlag) {
	if(!checkFlagFlag){
		checkFlagFlag=statusFlag.PUT;
	}
	var self = this;
	var grid = $('#' + self.gridId);
	var rows = grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var rowObj = grid.datagrid('getChecked');
	if (rowObj && rowObj.length < 1) {
		showWarn("请至少选择一条数据操作！");
		return;
	}
	var flag = false;
	var isNotMakeBillNos = "";
	var hasNoBillNos = "";
	for(var i=0;i<rowObj.length;i++){
		var item=rowObj[i];
		var pkValue = item.billNo;
		if (!pkValue) {
			hasNoBillNos = "请选择相应的单据再删除！";
			flag = true;
			continue;
		}
		if(!ctrl.checkListStatus(pkValue,item.shardingFlag,checkFlagFlag)){
			isNotMakeBillNos = isNotMakeBillNos + item.billNo + ",";
			flag = true;
		}
	}
	if (flag) {
		if (isNotBlank(isNotMakeBillNos)) {
			showWarn("其中" + isNotMakeBillNos + " "+ctrl.checkListStatusMessage(checkFlagFlag));
		}
		if (isNotBlank(hasNoBillNos)) {
			showWarn(hasNoBillNos);
		}
		return;
	}
	$.messager.confirm("确认", "确定要删除这" + rowObj.length + "条单据?", function(r) {
		if (r) {
			// $.messager.progress({msg:'处理中',interval:1000});
			var inventoryFlag = false;
			var dataObject = [];
			
			$.each(rowObj, function(index, item) {
				var url = "delete";
				var params = {
					billNo : item.billNo,
					shardingFlag:item.shardingFlag
				};
				$.ajax({
					url : url,
					data : params,
					async : false
				}).then(function(data) {
					if (data) {
						if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
							var errorData = $.parseJSON(data.errorDefined);
							dataObject.push(errorData);
							inventoryFlag = true;
						} else if (!inventoryFlag) {
							if (isNotBlank(data.errorMessage)) {
								showError("单据：" + item.billNo + data.errorMessage + " " + data.errorDefined);
							} else {
//								showSuc('删除成功!');
								self.search();
							}
						}
					} else {
						showInfo('删除失败,请联系管理员!');
					}
				});
			});
			
			// $.messager.progress('close');
			if (dataObject.length > 0) {
				$.fas.common.batchValidateInventory(dataObject);
			}
		}
	});
};

// 审核单据(批量)
BillList.prototype.batchVerify = function(checkFlagFlag) {
	if(!checkFlagFlag){
		checkFlagFlag="put1";
	}
	var self = this;
	var grid = $('#' + self.gridId);
	var rows = grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var rowObj = grid.datagrid('getChecked');
	if (rowObj && rowObj.length < 1) {
		showWarn("请至少选择一条数据操作！");
		return;
	}
	var flag = false;
	var isNotMakeBillNos = "";
	var hasNoBillNos = "";
	for(var i=0;i<rowObj.length;i++){
		var item=rowObj[i];
		var pkValue = item.billNo;
		if (!pkValue) {
			hasNoBillNos = "请选择相应的单据再审核！";
			flag = true;
			continue;
		}
		if(!ctrl.checkListStatus(pkValue,item.shardingFlag,checkFlagFlag)){
			isNotMakeBillNos = isNotMakeBillNos + item.billNo + ",";
			flag = true;
		}
		if (!$.fas.common.checkHasDetails(pkValue, ctrl.billDetail.dtlModulePath,item.shardingFlag)) {
			flag = true;
			return false;
		}
	}
	if (flag) {
		if (isNotBlank(isNotMakeBillNos)) {
			showWarn("其中" + isNotMakeBillNos + " "+ctrl.checkListStatusMessage(checkFlagFlag));
		}
		if (isNotBlank(hasNoBillNos)) {
			showWarn(hasNoBillNos);
		}
		return;
	}
	$.messager.confirm("确认", "确定要审核这" + rowObj.length + "条单据?", function(r) {
		if (r) {
			// $.messager.progress();
			var inventoryFlag = false;
			var dataObject = [];
			
			$.each(rowObj, function(index, item) {
				var url = "verify";
				var params = {
					billNo : item.billNo,
					shardingFlag:item.shardingFlag
				};
				$.ajax({
					url : url,
					data : params,
					async : false
				}).then(function(data) {
					if (data) {
						if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
							var errorData = $.parseJSON(data.errorDefined);
							dataObject.push(errorData);
							inventoryFlag = true;
						} else if (!inventoryFlag) {
							if (isNotBlank(data.errorMessage)) {
								showError("单据：" + item.billNo + data.errorMessage + " " + data.errorDefined);
							} else {
//								showSuc('审核成功!');
								self.search();
							}
						}
					} else {
						showInfo('审核失败,请联系管理员!');
					}
				});
			});
			
			// $.messager.progress('close');
			if (dataObject.length > 0) {
				$.fas.common.batchValidateInventory(dataObject);
			}
		}
	});
};

// 确认单据(批量)
BillList.prototype.batchConfirm = function(billStatus, myUrl) {
	if (typeof billStatus == 'undefined') {
		billStatus = billStatusEnums.MAKEBILL;
	}
	if (typeof myUrl == 'undefined') {
		myUrl = "confirm";
	}
	var self = this;
	var grid = $('#' + self.gridId);
	var rows = grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var rowObj = grid.datagrid('getChecked');
	if (rowObj && rowObj.length < 1) {
		showWarn("请至少选择一条数据操作！");
		return;
	}
	var flag = false;
	var isNotMakeBillNos = "";
	var hasNoBillNos = "";
	for(var i=0;i<rowObj.length;i++){
		var item=rowObj[i];
		var pkValue = item.billNo;
		if (!pkValue) {
			hasNoBillNos = "请选择相应的单据再确认！";
			flag = true;
			continue;
		}
		var billStatusTemp=ctrl.getBillStatus(pkValue,item.shardingFlag);
		if(self.ctrl.checkTransport){
			if(billStatusTemp!=billStatusEnums.MAKEBILL&&billStatusTemp!=billStatusEnums.SUBMIT){
				flag = true;
			}
		}else{
			if (billStatusTemp != billStatus) {
				isNotMakeBillNos = isNotMakeBillNos + item.billNo + "，";
				flag = true;
			}
		}
		if (!fas.common.checkHasDetails(pkValue, ctrl.billDetail.dtlModulePath,item.shardingFlag)) {
			flag = true;
			return false;
		}
	}
	if (flag) {
		if (isNotBlank(isNotMakeBillNos)) {
			if(self.ctrl.checkTransport){
				showWarn("其中" + isNotMakeBillNos + "为非制单状态的单据，请选择制单状态的单据进行操作！");
			}else{
				showWarn("其中" + isNotMakeBillNos + "为非制单状态的单据，请选择制单状态的单据进行操作！");
			}
		}
		if (isNotBlank(hasNoBillNos)) {
			showWarn(hasNoBillNos);
		}
		return;
	}
	$.messager.confirm("确认", "确定要确认这" + rowObj.length + "条单据?", function(r) {
		if (r) {
			// $.messager.progress({msg:'处理中',interval:1000});
			var inventoryFlag = false;
			var dataObject = [];
			
			var continueFlag=true;//是否继续循环
			var rfeshFlag=false;//是否需要刷新页面
			$(rowObj).each(function(index, item) {
				//校验负库存出库，如果库存足够，或者配置了允许负库存出库直接执行确认单据，库存不足没有配置允许负库存出库的弹窗提示，中断批量确认
				if(ctrl.checkInventory(myUrl,true,item)){
					var url = myUrl + "?prettyConfirm=Y";
					var params = {
						billNo : item.billNo,
						shardingFlag:item.shardingFlag
					};
					$.ajax({
						url : url,
						data : params,
						async : false
					}).then(function(data) {
						if (data) {
							if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
								var errorData = $.parseJSON(data.errorDefined);
								dataObject.push(errorData);
								inventoryFlag = true;
							} else if(isNotBlank(data.qtyMessageData)){
								data.qtyMessageData.allowDone=false;//批量处理只是提示没有继续
								ctrl.qtyMessage(data.qtyMessageData);								 
								continueFlag=false;
							}else if (!inventoryFlag) {
								if (isNotBlank(data.errorMessage)) {
									showError("单据：" + item.billNo + data.errorMessage + " " + data.errorDefined);
								} else {
									showSuc('确认成功!');
									rfeshFlag=true;
								}
							}
						} else {
							showInfo('确认失败,请联系管理员!');
						}
					});
					return continueFlag;
					
				}
			});
			
			if(rfeshFlag){
				self.search();				
			}
			// $.messager.progress('close');
			if (dataObject.length > 0) {
				$.fas.common.batchValidateInventory(dataObject);
			}
		}
	});
};

// 反确认单据(批量)
BillList.prototype.batchCancel = function(checkFlagFlag) {
	if(!checkFlagFlag){
		checkFlagFlag=billStatusEnums.MAKEBILL;
	}
	var self = this;
	var grid = $('#' + self.gridId);
	var rows = grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var rowObj = grid.datagrid('getChecked');
	if (rowObj && rowObj.length < 1) {
		showWarn("请至少选择一条数据操作！");
		return;
	}
	var flag = false;
	var isNotMakeBillNos = "";
	var hasNoBillNos = "";
	for(var i=0;i<rowObj.length;i++){
		var item=rowObj[i];
		var pkValue = item.billNo;
		if (!pkValue) {
			hasNoBillNos = "请选择相应的单据再反确认！";
			flag = true;
			continue;
		}
		if(!ctrl.checkListStatus(pkValue,item.shardingFlag,checkFlagFlag)){
			isNotMakeBillNos = isNotMakeBillNos + item.billNo + ",";
			flag = true;
		}
	}
	if (flag) {
		if (isNotBlank(isNotMakeBillNos)) {
			if(self.ctrl.checkTransport){
				showWarn("其中" + isNotMakeBillNos + "为非确认状态的单据，请选择确认状态的单据进行操作！");
			}else{
				showWarn("其中" + isNotMakeBillNos + "为非确认状态的单据，请选择确认状态的单据进行操作！");
			}
		}
		if (isNotBlank(hasNoBillNos)) {
			showWarn(hasNoBillNos);
		}
		return;
	}
	$.messager.confirm("确认", "确定要反确认这" + rowObj.length + "条单据?", function(r) {
		if (r) {
			// $.messager.progress({msg:'处理中',interval:1000});
			var inventoryFlag = false;
			var dataObject = [];
			
			$.each(rowObj, function(index, item) {
				var url = "confirm";
				var params = {
					billNo : item.billNo,
					status : item.status
				};
				$.ajax({
					url : url,
					data : params,
					async : false
				}).then(function(data) {
					if (data) {
						if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
							var errorData = $.parseJSON(data.errorDefined);
							dataObject.push(errorData);
							inventoryFlag = true;
						} else if (!inventoryFlag) {
							if (isNotBlank(data.errorMessage)) {
								showError("单据：" + item.billNo + data.errorMessage + " " + data.errorDefined);
							} else {
								showSuc('反确认成功!');
								self.search();
							}
						}
					} else {
						showInfo('反确认失败,请联系管理员!');
					}
				});

			});
			// $.messager.progress('close');
			
			if (dataObject.length > 0) {
				$.fas.common.batchValidateInventory(dataObject);
			}
		}
	});
};

// 完结单据(批量)
BillList.prototype.batchOver = function(checkFlagFlag) {
	if(!checkFlagFlag){
		checkFlagFlag=statusFlag.OVER;
	}
	var self = this;
	var grid = $('#' + self.gridId);
	var rows = grid.datagrid('getRows');
	if (rows && rows.length == 0) {
		showInfo('请选择要操作的数据!');
		return;
	}
	var rowObj = grid.datagrid('getChecked');
	if (rowObj && rowObj.length < 1) {
		showWarn("请至少选择一条数据操作！");
		return;
	}
	var flag = false;
	var isNotMakeBillNos = "";
	var hasNoBillNos = "";
	for(var i=0;i<rowObj.length;i++){
		var item=rowObj[i];
		var pkValue = item.billNo;
		if (!pkValue) {
			hasNoBillNos = "请选择相应的单据再完结！";
			flag = true;
			continue;
		}
		if(!ctrl.checkListStatus(pkValue,item.shardingFlag,checkFlagFlag)){
			isNotMakeBillNos = isNotMakeBillNos + item.billNo + ",";
			flag = true;
		}
		if (!$.fas.common.checkHasDetails(pkValue, ctrl.billDetail.dtlModulePath,item.shardingFlag)) {
			flag = true;
			return false;
		}
	}
	if (flag) {
		if (isNotBlank(isNotMakeBillNos)) {
			showWarn("其中" + isNotMakeBillNos + " "+ctrl.checkListStatusMessage(checkFlagFlag));
		}
		if (isNotBlank(hasNoBillNos)) {
			showWarn(hasNoBillNos);
		}
		return;
	}
	$.messager.confirm("确认", "确定要完结这" + rowObj.length + "条单据?", function(r) {
		if (r) {
			// $.messager.progress({msg:'处理中',interval:1000});
			var inventoryFlag = false;
			var dataObject = [];
			
			$.each(rowObj, function(index, item) {
				var url = "over";
				var params = {
					billNo : item.billNo,
					shardingFlag : item.shardingFlag
				};
				$.ajax({
					url : url,
					data : params,
					async : false
				}).then(function(data) {
					if (data) {
						if (data.errorDefined && data.errorDefined.indexOf('total') > 0) {
							var errorData = $.parseJSON(data.errorDefined);
							dataObject.push(errorData);
							inventoryFlag = true;
						} else if (!inventoryFlag) {
							if (isNotBlank(data.errorMessage)) {
								showError("单据：" + item.billNo + data.errorMessage + " " + data.errorDefined);
							} else {
//								showSuc('完结成功!');
								self.search();
							}
						}
					} else {
						showInfo('完结失败,请联系管理员!');
					}
				});

			});
			// $.messager.progress('close');
			
			if (dataObject.length > 0) {
				$.fas.common.batchValidateInventory(dataObject);
			}
		}
	});
};

//单据明细工具条
function Toolbars() {
	this.buttomId = 'bottom';
	this.bill = {};
	var self = this;
	var handle = function() {
		self.handle(this);
	};
}

Toolbars.prototype.refresh = function() {
//	$('#bottom').billBottom('setValue', this.bill);
};

Toolbars.prototype.init = function() {
//	$('#' + this.buttomId).billBottom(this.bill);
};

