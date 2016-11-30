
var regionPriceRule = {};
regionPriceRule.initAddRuleData = [
                                   	    {"value":"1","text":"厂进价"},
                                        {"value":"3","text":"总部成本"}
                                 ];

regionPriceRule.loadDetail = function(rowData){
	fas_common.loadDetail(rowData);
	$('#twoLevelCategory').combogrid('setValues',rowData.twoLevelCategoryName.split(','));
	$('#yearCode').combo('setValues',rowData.yearCode.split(','));
}

regionPriceRule.initTwoLevelCategory = function(){
	var _this = $('#twoLevelCategory');
	var _no =  $('#twoLevelCategoryNo');
	var _name =  $('#twoLevelCategoryName');
	_this.combogrid({
		panelWidth:280,
	    panelHeight : 250,
        mode:'remote',
        multiple:true,
        idField:'name',
        textField:'name',
        url : BasePath + '/common_util/getPageCategory?levelid=2&rows=1000',
        separator:',',
        selectOnCheck : true,
        checkOnSelect : true,
        frozenColumns:[[{field:'ck',checkbox:true,width:30}]],
        columns:[[
        {field:'code',title:'编码',width:80,align:'left'},
        {field:'name',title:'名称',width:150,align:'left'}
        ]],
        pagination:false,
        onHidePanel:function(){
        	var rows = _this.combogrid('grid').datagrid('getSelections');
        	var callback = _this.combo('options').callback;
    		if(rows.length > 0){
    			var code = '';
    			var name = '';
    			$.each(rows,function(index,item){
    				code +=item.code+',';
    				name +=item.name+',';
    			});
    			_no.val(code.substring(0,code.length-1));
    			_name.val(name.substring(0,name.length-1))
        	}else{
        		_no.val('');
        		_name.val('');
        		_this.combogrid('clear');
        	}
        }
	});
}

regionPriceRule.addBasisFormatter = function(value, rowData, rowIndex) {
	for ( var i = 0; i < regionPriceRule.initAddRuleData.length; i++) {
		if (regionPriceRule.initAddRuleData[i].value == value) {
			return regionPriceRule.initAddRuleData[i].text;
		}
	}
};

regionPriceRule.initSupplierGroup = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/supplier_group/get_audited_group.json?auditStatus=1',
		valueId : 'groupNo',    
		textId : 'groupName',
		width : 160
	});
};

regionPriceRule.initMultiSupplierGroup = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/supplier_group/get_audited_group.json?auditStatus=1',
		width : 160,
		height : "auto",
		valueId : 'groupNo',    
		textId : 'groupName',
		multiple : true,
		selectFun : function(newValue) {
			var names = $('#supplierGroupNos').combobox('getText');
			$('#supplierGroupName').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#supplierGroupNos').combobox('getText');
			$('#supplierGroupName').val(names);
		}
	});
};

regionPriceRule.initSettleNewStyle = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/settle_new_style/get_biz?status=1',
		valueId : 'styleNo',    
		textId : 'name',
		width : 160,
		height : 'auto'
	});
};

regionPriceRule.initYearCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueId : 'itemvalue',    
		textId : 'itemname',
		multiple : true,
		width : 160,
		selectFun : function(newValue) {
			var names = $('#yearCode').combobox('getText');
			$('#year').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#yearCode').combobox('getText');
			$('#year').val(names);
		}
	});
};

regionPriceRule.initQueryYearCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};

regionPriceRule.initSeasonCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=SELL_SEASON&addAllFlag=true',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160,
		selectFun : function(data) {
			if(data) {
				$("#seasonName").val(data.itemname);
			} else {
				$("#seasonName").val("");
			}
		}
	});
};

regionPriceRule.initQuerySeasonCombo = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/initCache/getLookupDtlsList.htm?lookupcode=SELL_SEASON&addAllFlag=true',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160
	});
};

//初始化加价依据
regionPriceRule.initAddRule = function(comboboxId) {
	$('#'+comboboxId).combobox({
		data:regionPriceRule.initAddRuleData,
		valueField:"value",
		textField:"text",
		panelHeight:"auto",
		width : 160,
		editable:false
	});
};

//初始化结算大类
regionPriceRule.initSettleCategory = function(comboboxId) {
	fas_common.initCombox ({
		id : comboboxId,
		url : '/settle_category/get_biz.json?status=1',
		valueId : 'settleCategoryNo',    
		textId : 'name',
		panelHeight : "auto",
		width : 160
	});
};

regionPriceRule.initMultiSettleCategory = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/settle_category/get_biz.json?status=1',
		width : 160,
		height : "auto",
		valueId : 'settleCategoryNo',    
		textId : 'name',
		multiple : true,
		selectFun : function(newValue) {
			var names = $('#settleCategoryNos').combobox('getText');
			$('#categoryName').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#settleCategoryNos').combobox('getText');
			$('#categoryName').val(names);
		}
	});
};

regionPriceRule.initQueryBrandUnit = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/brand_unit/get_biz.json',
		width : 160,
		height : "auto",
		valueId : 'brandUnitNo',    
		textId : 'name'
	});
};

//初始化品牌
regionPriceRule.initBrandUnit = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/brand_unit/get_biz.json',
		width : 160,
		height : "auto",
		valueId : 'brandUnitNo',    
		textId : 'name',
		multiple : true,
		selectFun : function(newValue) {
			var names = $('#brandUnitNos').combobox('getText');
			$('#brandUnitName').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#brandUnitNos').combobox('getText');
			$('#brandUnitName').val(names);
		}
	});
};

//初始化地区
regionPriceRule.initMultiZones = function(comboboxId){
	fas_common.initCombox ({
		id : comboboxId,
		url : '/zone_info/getPriceZone',
		width : 160,
		height : "auto",
		valueId : 'zoneNo',    
		textId : 'name',
		multiple : true,
		selectFun : function(newValue) {
			var names = $('#zoneNos').combobox('getText');
			$('#zoneName').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#zoneNos').combobox('getText');
			$('#zoneName').val(names);
		}
	});
};

//初始化地区
regionPriceRule.initZoneNo = function(comboboxId) {
	fas_common.initCombox ({
		id : comboboxId,
		url : '/zone_info/getPriceZone',
		valueId : 'zoneNo',    
		textId : 'name',
		width : 160
	});
};

regionPriceRule.checkExistFun = function(url,checkColumnJsonData){
	var checkExist=false;
 	$.ajax({
		  type: 'POST',
		  url: url,
		  data: checkColumnJsonData,
		  cache: true,
		  async:false, // 一定要
		  dataType: 'text', 
		  success: function(totalData){
			  totalData = parseInt(totalData,10);
			  if(totalData>0){
				  checkExist=true;
			  }
		  }
     });
 	return checkExist;
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

//额外操作
var extra_operate = {
	//新增保存时的校验规则
	checkSave : function(){
		if($("#addBasis").combobox("getValue") == "3"){
			if(isNotBlank($("#addPrice").numberbox("getValue"))
					&& isNotBlank($("#addDiscount").numberbox("getValue"))){
				showError('加价依据为总部成本,加价和加折只能选一个!');
				return false;
			}
			if(!(isNotBlank($("#addPrice").numberbox("getValue")))
					&& !(isNotBlank($("#addDiscount").numberbox("getValue")))){
				showError('加价依据为总部成本,加价和加折必选一个!');
				return false;
			}
		}
		if($("#addBasis").combobox("getValue") == "1"){
			if(isNotBlank($("#addPrice").numberbox("getValue"))
					&& isNotBlank($("#addDiscount").numberbox("getValue"))){
				showError('加价依据为厂进价,加价和加折只能选一个!');
				return false;
			}
			if(!(isNotBlank($("#addPrice").numberbox("getValue")))
					&& !(isNotBlank($("#addDiscount").numberbox("getValue")))){
				showError('加价依据为厂进价,加价和加折必选一个!');
				return false;
			}
		}
		//校验ruleNO是否重复
		var checkRuleNoData = {"addRuleNo" : $("#addRuleNo").val()};
		var checkUrl = BasePath + "/region_price_rule/get_count.json";
		
		if (regionPriceRule.checkExistFun(checkUrl, checkRuleNoData)) {
			showError('规则编码已经存在,不能重复!');
			return false;
		}
		return true;
	},
	checkDel :  function(checkedRows){
		var checkResult = true;
		$.each(checkedRows, function(index, item) {
			//校验规则是否已经被使用
			var checkReferedRuleData = {"addRuleNo" : item.addRuleNo};
			var checkUrl = BasePath + "/region_price_rule/check_rule_refered.json";
			if (regionPriceRule.checkExistFun(checkUrl, checkReferedRuleData)) {
				checkResult = false;
			}
		});
		if(!checkResult){
			showError('该规则已被价格维护使用,不能删除!');
			return false;
		};
		return true;
	},
	// 进入新增页面初始化
	initAdd : function(){
		//初始化供应商组
		regionPriceRule.initMultiSupplierGroup("supplierGroupNos");
		//初始化加价依据
		regionPriceRule.initAddRule("addBasis");
		//初始化年份
		//初始化结算大类
		regionPriceRule.initMultiSettleCategory("settleCategoryNos");
		//初始化结算新旧款
		regionPriceRule.initSettleNewStyle("styleType");
		regionPriceRule.initYearCombo("yearCode");
		//初始化季节
		regionPriceRule.initSeasonCombo("season");
		//初始化品牌组
		regionPriceRule.initBrandUnit("brandUnitNos");
		//初始化大区
		regionPriceRule.initMultiZones("zoneNos");
	},
	// 载入页面后的样式设置
	loadedAdd: function(){
		$('#id').val("");
		$("#seasonName").val("");
		$("#year").val("");
		$("#dataForm input[class*='easyui-numberbox']").numberbox("clear");
		$("#addRuleNo").removeClass("disabled").removeAttr("readonly", true);
		
		//新增时默认是新款
		$("#radio1").attr("checked","checked");
		$("#yearCode").combobox("disable");
		$("#season").combobox("disable");
		$("#addPrice").addClass("disabled").attr("readonly", true);
		$("#addDiscount").addClass("disabled").attr("readonly", true);
		$("#discountRate").addClass("disabled").attr("readonly", true);
		
		regionPriceRule.setStyleTyeClass();
		// 给单选按钮绑定函数事件
		$("input[name='newStyleFlag']").on("click", function() {
			if($(this).val() == "1"){
				$("#styleType").combobox("enable");
				$("#styleType").combobox("setValue","").combobox("setText","");
				$("#yearCode").combobox("setValue","").combobox("setText","");
				$("#year").val("");
				$("#season").combobox("setValue","").combobox("setText","");
				$("#seasonName").val("");
				$("#styleType").combobox("setValue","").combobox("setText","");
				$("#yearCode").combobox("disable");
				$("#season").combobox("disable");
			}else if($(this).val() == "0"){
				$("#styleType").combobox("setValue","").combobox("setText","");
				$("#styleType").combobox("disable");
				$("#yearCode").combobox("enable");
				$("#season").combobox("enable");
				$("#yearCode").combobox("setValue","").combobox("setText","");
				$("#year").val("");
				$("#season").combobox("setValue","").combobox("setText","");
				$("#seasonName").val("");
			}
			//设置新旧款或年份、季节的样式
			regionPriceRule.setStyleTyeClass();
		});
	},
	// 进入修改页面的初始化
	initUpdate: function(rowData){
		//校验规则是否已经被使用
		var checkReferedRuleData = {"addRuleNo" : rowData.addRuleNo};
		var checkUrl = BasePath + "/region_price_rule/check_rule_refered.json";
		
		if (regionPriceRule.checkExistFun(checkUrl, checkReferedRuleData)) {
			showError('该规则已被价格维护使用,不能修改!');
			return false;
		}
		//初始化年份
		regionPriceRule.initYearCombo("yearCode");
		//初始化季节
		regionPriceRule.initSeasonCombo("season");
		//初始化加价依据
		regionPriceRule.initAddRule("addBasis");
		//初始化供应商组
		regionPriceRule.initMultiSupplierGroup("supplierGroupNos");
		//初始化结算新旧款
		regionPriceRule.initSettleNewStyle("styleType");
		//初始化结算大类
		regionPriceRule.initMultiSettleCategory("settleCategoryNos");
		//初始化大区
		regionPriceRule.initMultiZones("zoneNos");
		//初始化品牌组
		regionPriceRule.initBrandUnit("brandUnitNos");
	},
	// 载入修改页面后的样式设置
	loadedUpdate: function(rowData){
		$("#addRuleNo").addClass("disabled").attr("readonly", true);
		//是新旧款
		if($("input[name='newStyleFlag']:checked").val() == "1"){
			$("#yearCode").combobox("setValue","").combobox("setText","");
			$("#year").val("");
			$("#season").combobox("setValue","").combobox("setText","");
			$("#seasonName").val("");
			$("#yearCode").combobox("disable");
			$("#season").combobox("disable");
			$("#styleType").combobox("enable");
		}else if($("input[name='newStyleFlag']:checked").val() == "0"){
			$("#styleType").combobox("setValue","").combobox("setText","");
			$("#styleType").combobox("disable");
			$("#yearCode").combobox("enable");
			$("#season").combobox("enable");
		}
		//设置修改操作时，新旧款、年份、季节的值
		regionPriceRule.setComboboxVal(rowData);
		if($("#addBasis").combobox("getValue") == "3" || $("#addBasis").combobox("getValue") == "1"){
			//总部成本价
			$("#addPrice").removeClass("disabled").removeAttr("readonly");
			$("#addDiscount").removeClass("disabled").removeAttr("readonly");
			$("#discountRate").addClass("disabled").attr("readonly", true);
		}else if($("#addBasis").combobox("getValue") == "2"){
			//牌价
			$("#addPrice").addClass("disabled").attr("readonly", true);
			$("#addDiscount").addClass("disabled").attr("readonly", true);
			// 如果选择的是牌价，则折扣字段必填
			$("#discountRate").removeClass("disabled").removeAttr("readonly");
		}
		
		// 给单选按钮绑定函数事件
		$("input[name='newStyleFlag']").on("click", function() {
			if($(this).val() == "1"){
				$("#styleType").combobox("enable");
				$("#styleType").combobox("setValue","").combobox("setText","");
				$("#yearCode").combobox("setValue","").combobox("setText","");
				$("#year").val("");
				$("#season").combobox("setValue","").combobox("setText","");
				$("#seasonName").val("");
				$("#yearCode").combobox("disable");
				$("#season").combobox("disable");
			}else if($(this).val() == "0"){
				$("#styleType").combobox("setValue","").combobox("setText","");
				$("#styleType").combobox("disable");
				$("#yearCode").combobox("enable");
				$("#season").combobox("enable");
				$("#year").val("");
				$("#season").combobox("setValue","").combobox("setText","");
				$("#seasonName").val("");
			}
			//设置新旧款或年份、季节的样式
			regionPriceRule.setStyleTyeClass();
		});
	},
	checkUpdate: function(){
		if($("#addBasis").combobox("getValue") == "3"){
			if(isNotBlank($("#addPrice").numberbox("getValue"))
					&& isNotBlank($("#addDiscount").numberbox("getValue"))){
				showError('加价依据为总部成本,加价和加折只能选一个!');
				return false;
			}
			if(!(isNotBlank($("#addPrice").numberbox("getValue")))
					&& !(isNotBlank($("#addDiscount").numberbox("getValue")))){
				showError('加价依据为总部成本,加价和加折必选一个!');
				return false;
			}
		}
		if($("#addBasis").combobox("getValue") == "1"){
			if(isNotBlank($("#addPrice").numberbox("getValue"))
					&& isNotBlank($("#addDiscount").numberbox("getValue"))){
				showError('加价依据为厂进价,加价和加折只能选一个!');
				return false;
			}
		}
		return true;
	}
};

//设置新旧款或年份、季节的样式
regionPriceRule.setStyleTyeClass = function() {
	var styleType = $("input[name='newStyleFlag']:checked").val();
	//如果"是否新旧款类型"选择了"否"，则设置季节和年份选项必填,同时移除新旧款必填的设置
	if(styleType == '0') {
		$('#styleType').combobox({
		    required:false,
		    novalidate : true
		});
		$('#yearCode').combobox({
		    required:true,
		    novalidate : false
		});
		$('#season').combobox({
		    required:true,
		    novalidate : false
		});
		$("#styleType").combobox("disable");
		$("#yearCode").combobox("enable");
		$("#season").combobox("enable");
	} else if(styleType == '1') {
		//如果"是否新旧款类型"选择了"是"，则设置新旧款选项必填,同时移除季节和年份必填的设置
		$('#styleType').combobox({
		    required:true,
		    novalidate : false
		});
		$('#yearCode').combobox({
		    required:false,
		    novalidate : true
		});
		$('#season').combobox({
		    required:false,
		    novalidate : true
		});
		$("#styleType").combobox("enable");
		$("#yearCode").combobox("disable");
		$("#season").combobox("disable");
	}
};

regionPriceRule.setComboboxVal = function(rowData) {
	if(rowData && typeof rowData != 'undefined') {
		$('#styleType').combobox("setValue", rowData.styleType);
		$('#yearCode').combobox("setValue", rowData.yearCode);
		$('#season').combobox("setValue", rowData.season);
	}
};

//给加价依据绑定函数事件
regionPriceRule.bindAddBasisEvent = function() {
		if($("#addBasis").combobox("getValue") == "3" || $("#addBasis").combobox("getValue") == "1"){
			//总部成本
			$("#discountRate").numberbox("clear");
			$("#discountRate").addClass("disabled").attr("readonly", true);
			$("#addPrice").numberbox("clear");
			$("#addDiscount").numberbox("clear");
			$("#addPrice").removeClass("disabled").removeAttr("readonly");
			$("#addDiscount").removeClass("disabled").removeAttr("readonly");
		}else if($("#addBasis").combobox("getValue") == "2"){
			//牌价
			$("#addPrice").numberbox("clear");
			$("#addDiscount").numberbox("clear");
			$("#addPrice").addClass("disabled").attr("readonly", true);
			$("#addDiscount").addClass("disabled").attr("readonly", true);
			$("#discountRate").numberbox("clear");
			$("#discountRate").removeClass("disabled").removeAttr("readonly");
		}
};

regionPriceRule.addYear = function(){
	var checkedRows = $('#dataGridDiv').datagrid('getChecked');
	if(checkedRows.length ==0){
		showError("请勾选需要操作的记录！");
		return;
	}
	$('#addYearForm').form('clear');
	$('#addYearCode').val('');
	$('#addYear').val('');
	fas_common.initCombox ({
		id : 'addYearCode',
		url : '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
		valueId : 'itemvalue',    
		textId : 'itemname',
		width : 160,
		selectFun : function(newValue) {
			var names = $('#addYearCode').combobox('getText');
			$('#addYear').val(names);
		},
		unSelectFun : function(newValue) {
			var names = $('#addYearCode').combobox('getText');
			$('#addYear').val(names);
		}
	});
	ygDialog({
		title : '增加年份',
		target : $('#addYearFormPanel'),
		width : 420,
		height : 280,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				var fromObj = $('#addYearForm');
				var validateForm = fromObj.form('validate');
				if (validateForm == false) {
					return;
				}
				$.messager.progress({
					title:'请稍后',
					msg:'正在处理中...'
				}); 
				fromObj.form('submit',{
					url : BasePath + '/region_price_rule/add_year',
					onSubmit : function(param){
						param.checkedRows = JSON.stringify(checkedRows);
					},
					success:function(result){
						if(result){
							  showSuc('生成成功!');
							  $("#btn-search").click();
							  $("#addYearFormPanel").window('close');
							  $.messager.progress('close');
						  } else {
							  showError('操作失败,请联系管理员!');
							  $.messager.progress('close');
						  }
					},
					error:function(){
						$.messager.progress('close');
						showError('操作失败');
					}
				});
				dialog.close();
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

$(document).ready(function(){
	//初始化年份
	regionPriceRule.initQueryYearCombo("yearCodeCondition");
	//初始化季节
	regionPriceRule.initQuerySeasonCombo("seasonCondition");
	//初始化加价依据
	regionPriceRule.initAddRule("addBasisCondition");
	//初始化供应商组
	regionPriceRule.initSupplierGroup("supplierGroupNoCondition");
	//初始化结算新旧款
	regionPriceRule.initSettleNewStyle("styleTypeCondition");
	//初始化结算大类
	regionPriceRule.initSettleCategory("categoryNoCondition");
	//初始化大区
	regionPriceRule.initZoneNo("zoneNoCondition");
	//初始化品牌部
	regionPriceRule.initQueryBrandUnit("brandUnitNoCondition");	
	//初始化二级大类
	regionPriceRule.initTwoLevelCategory();
});
