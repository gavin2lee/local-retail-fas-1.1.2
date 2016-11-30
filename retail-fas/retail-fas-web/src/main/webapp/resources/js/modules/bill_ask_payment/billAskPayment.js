var billController ;

function Bill(){}

Bill.prototype = new BillController();

Bill.prototype.beginEdit = function(editIndex){// 开始明细行编辑
	billController.initFasCategory();
	BillController.prototype.beginEdit.call(this, editIndex);
	var ed = $("#dtlDataGrid").datagrid('getEditor',{index:editIndex,field:'amount'});
	$(ed.target).bind('blur',function(){
		Bill.prototype.setAllAmount();
	});
};

//add by Ning.ly
function getCurrentNowTime() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var day = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	if (minute < 10) {
		minute = '0' + minute;
	}
	var second = now.getSeconds();
	if (second < 10) {
		second = '0' + second;
	}
	var nowdate = year + "-" + month + "-" + day + " " + hour + ":" + minute
			+ ":" + second + " ";
	return nowdate;
}

//将输入的数字金额转换成对应的中文大写金额
//idNumber输入的数字金额，idCHN输出的中文大写金额
function TransformNumberIntoCHN(allAmount) {
	var idCHN=null;
	var number = allAmount;
    if (!/^(0|[1-9]\d*)(\.\d{1,2})?$/.test(number)) {
        /*document.getElementById(idCHN).value = "";*/
        number = "";
        return false;
    }
    var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
    number += "00";
    var point = number.indexOf('.');
    if (point >= 0) {
        number = number.substring(0, point) + number.substr(point + 1, 2);
    }
    unit = unit.substr(unit.length - number.length);
    for (var i = 0; i < number.length; i++) {
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(number.charAt(i)) + unit.charAt(i);
    }
    //document.getElementById(idCHN).value = str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
    idCHN = str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");

    return idCHN;
}

function print(){
	var previewUrl = BasePath + '/print/preview?viewName=plase_bill_print';
	var mainData =  $('#mainDataForm').form('getData');
	var dtlData = $('#dtlDataGrid').datagrid('getRows');
	
	//打开待打印数据页面
	ygDialog({
		isFrame: true,
		cache : false,
		title : '请款单',
		modal:true,
		showMask: false,
		fit : true,
		href : previewUrl,
		buttons:[{
			text:'打印',
			iconCls:'icon-print',
			handler:'print_page'
		}],
		onLoad:function(win,dialog){
			var number=null;
			var type=dtlData[0].settleMethodNo;
			var checkArry = dialog.$('input[type="checkbox"]');
			for (var i = 0; i < checkArry.length; i++) { 
                if(checkArry[i].value == type.toString()){
                	if(checkArry[i].value=="0"){
                		dialog.$('#bank').attr("checked",'true');
                	}
                	if(checkArry[i].value=="1"){
                		dialog.$('#cash').attr("checked",'true');
                	}
                }
        	}
			for(var i=0;i<dtlData.length;i++){
				var object=dtlData[i];
				number+=object.qty;
			}
			dialog.$('#salerName').append( mainData.salerName);
			dialog.$('#salerNo').append(mainData.salerNo);
			dialog.$('#remark').append(mainData.remark);
			dialog.$('#allAmount').append(mainData.allAmount);
			dialog.$('#otherBank').append(dtlData[0].otherBank);
			dialog.$('#brandName_EN').append(dtlData[0].brandName);
			dialog.$('#brandName').append(dtlData[0].brandName);
			dialog.$('#otherBankAccount').append(dtlData[0].otherBankAccount);
			dialog.$('#qty').append(number);
			dialog.$('#idCHN').append(TransformNumberIntoCHN(mainData.allAmount));
			dialog.$('#printTime').append(getCurrentNowTime());
			dialog.$('#billDate').append(mainData.billDate);
			dialog.print_page(this,false);
			win.close();
		}
	});
}

function batchPrint(){
	var previewUrl = BasePath + '/print/preview?viewName=plase_bill_print';
	
	var mainDataGrid = $('#mainDataGrid').datagrid('getChecked');
	if(mainDataGrid.length==0){
		alert("请选择要批量打印的数据");
	}else{
		$.messager.confirm("提示","是否打印选中的单据?",function(r) {
			if (r) {
				if(mainDataGrid.length>1){
					showSuc('已在打印，请查看打印机。');
				}
				ygDialog({
					isFrame: true,
					cache : false,
					title : '请款单',
					modal:true,
					showMask: false,
					fit : true,
					href : previewUrl,
					buttons:[{
						text:'打印',
						iconCls:'icon-print',
						handler:'print_page'
					}],
					onLoad:function(win,dialog){
						for(var i=0;i<mainDataGrid.length;i++){
							dialog.$('#brandName_EN').html('');
							dialog.$('#brandName').html('');
							dialog.$('#salerName').html('');
							dialog.$('#allAmount').html('');
							dialog.$('#idCHN').html('');
							dialog.$('#billDate').html('');
							dialog.$('#printTime').html('');
							dialog.$('#salerNo').html('');
							dialog.$('#remark').html('');
							dialog.$('#qty').html('');
							dialog.$('#otherBank').html('');
							dialog.$('#otherBankAccount').html('');
							dialog.$('#bank').prop("checked",'false');
							dialog.$('#cash').prop("checked",'false');
							var number=null;
							var object=mainDataGrid[i];
							var billNo = object.billNo;
							ajaxRequestAsync(BasePath + '/bill_ask_payment_dtl/list.json',{billNo:billNo},function(data){
								var type=data.rows[0].settleMethodNo;
								var checkArry = dialog.$('input[type="checkbox"]');
								for (var i = 0; i < checkArry.length; i++) { 
					                if(checkArry[i].value == type.toString()){
					                	if(checkArry[i].value=="0"){
					                		dialog.$('#bank').attr("checked",'true');
					                	}
					                	if(checkArry[i].value=="1"){
					                		dialog.$('#cash').attr("checked",'true');
					                	}
					                }
					        	}
								
								for(var i=0;i<data.rows.length;i++){
									var object=data.rows[i];
									number+=object.qty;
								}
								dialog.$('#qty').append(number);
								dialog.$('#otherBank').append(data.rows[0].otherBank);
								dialog.$('#otherBankAccount').append(data.rows[0].otherBankAccount);

								dialog.$('#brandName').append(data.rows[0].brandName);
								dialog.$('#brandName_EN').append(data.rows[0].brandName);
							});
							
							dialog.$('#remark').append(object.remark);
							dialog.$('#billDate').append(object.billDate);
							dialog.$('#salerName').append(object.salerName);
							dialog.$('#salerNo').append(object.salerNo);
							dialog.$('#allAmount').append(object.allAmount);
							dialog.$('#idCHN').append(TransformNumberIntoCHN(object.allAmount));
							dialog.$('#printTime').append(getCurrentNowTime());
							if(mainDataGrid.length>1){
								dialog.print_page(this,true);
							}else{
								dialog.print_page(this,false);
							}
						}
						win.close();
					}
				});
			}
		});
	}
	
	
}

//显示巴洛克的列
function showBaroqueColumns(){
	if(currentUser.organTypeNo.indexOf("U010105")!= -1){
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyName');
		$('#mainDataGrid').datagrid('showColumn','conversionFactor');
		$('#mainDataGrid').datagrid('showColumn','targetCurrencyAmount');
	}
}

Bill.prototype.addDtl = function(row){// 新增明细
	if(billController.enableEdit){
		if(billController.endEdit()){
			if(row){
				$('#dtlDataGrid').datagrid('appendRow',{
					settleMethodNo:0,
					otherBank:billController.bankName,
					otherBankAccount:billController.bankAccount,
					amount:row.balanceAmount,
					qty:row.balanceQty,
					brandName : row.brandUnitName,
					brandNo : row.brandUnitNo
				});
				$('#allAmount').val(row.balanceAmount);
			}else{
				$('#dtlDataGrid').datagrid('appendRow',{
					settleMethodNo:0,
					otherBank:billController.bankName,
					otherBankAccount:billController.bankAccount
				});
			}
		    var length = $('#dtlDataGrid').datagrid('getRows').length;
		    billController.beginEdit(length-1);
		    return true;
		}
		return false;
	}
};

var _fasCategoryUrl = '';

Bill.prototype.initFasCategory = function(){// 初始化财务大类
	var buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
	if(buyerNo!=''){
		_fasCategoryUrl =  BasePath + '/common_util/getPageFasCategory?status=1&companyNo='+buyerNo;
	}else{
		_fasCategoryUrl =  '';
	}
	$("#dtlDataGrid").datagrid("removeEditor", "categoryName");
	$("#dtlDataGrid").datagrid("addEditor", {field : "categoryName", 
		editor : {type:'test_combogrid',
			options:{
				type:'financialCategory',
				datagridId:'dtlDataGrid',
				valueField:'categoryNo',
      			queryUrl:'',
      			onShowPanelFunction:function(input){
      				var dg = input.combogrid('grid');
      				var queryParams = input.combo('options').queryParams;
      				dg.datagrid( 'options' ).queryParams = queryParams;
      				dg.datagrid( 'options' ).url = _fasCategoryUrl;
      				dg.datagrid( 'load' );
      			}
			}
		}
	});
};

Bill.prototype.delDtl = function(){// 删除明细
	BillController.prototype.delDtl.call(this);
	Bill.prototype.setAllAmount();
};

Bill.prototype.setAllAmount = function(){// 设置总金额
	var rows = $("#dtlDataGrid").datagrid('getRows');
	var allAmount = 0;
	var allQty = 0;
	var editTr = $("tr[class*=datagrid-row-editing]");
	var editRowIndex = -1;
	if(editTr.length > 0){
		editRowIndex = editTr.attr("datagrid-row-index");
		var ed = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'amount'});
		var qtyEd = $("#dtlDataGrid").datagrid('getEditor',{index:editRowIndex,field:'qty'});
		var amount = $(ed.target).numberbox('getValue');
		var qty = $(qtyEd.target).numberbox('getValue');
		if(''!=amount){
			allAmount += parseFloat(amount);
		}
		if(''!=qty){
			allQty += parseFloat(qty);
		}
	} 
	for(var i =0,iLength = rows.length; i<iLength; i++){
		var rowIndex = $("#dtlDataGrid").datagrid('getRowIndex',rows[i]);
		var amount = rows[i].amount;
		var qty = rows[i].qty;
		if(editRowIndex != rowIndex){
			if(''!=amount){
				allAmount += parseFloat(amount);
			}
			if(''!=qty){
				allQty += parseFloat(qty);
			}
		}
	}
	$('#allAmount').val(allAmount.toFixed(2));
	$('#allQty').val(allQty);
};

Bill.prototype.initDtlTab = function() {// 初始化明细Tab
	var dtl_url = billController.modulePath + '/area/to_dtl';
	var ref_url = billController.modulePath + '/area/to_ref';
	if($('#isHQ').val() == 'true'){
		dtl_url = billController.modulePath + '/hq/to_dtl';
		ref_url = billController.modulePath + '/hq/to_ref';
	}
	billController.addDtlTab('单据明细',dtl_url);
	billController.addDtlTab('结算单信息',ref_url);
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '单据明细'){
	    		billController.loadDtlListData();
	    	}else{
	    		billController.loadRefInfo();
	    	}
	    }    
	}); 
	returnTab('dtlTab','单据明细');
};

Bill.prototype.initRef = function(){// 初始化关联单据信息
	var _this = $('#refBillNo');
	_this.combogrid({
        panelWidth:560,
	    panelHeight : 250,
	    width : 140,
        mode:'remote',
        idField:'billNo',
        textField:'billNo',
        url : '',
        columns:[[
	        {field:'billNo',title:'结算单号',width:150},
	        {field:'balanceTypeName',title:'结算类型',width:100},
	        {field:'balanceDate',title:'结算日期',width:80},
	        {field:'balanceAmount',title:'结算金额',width:80,align:'right'},
	        {field:'createUser',title:'制单人',width:80},
	        {field:'createTime',title:'制单时间',width:150},
	        {field:'auditor',title:'审批人',width:80},
	        {field:'auditTime',title:'审批时间',width:150}
        ]],
        onHidePanel:function(){
        	var row = _this.combogrid('grid').datagrid('getSelected');
        	billController.setRef(row);
        },
        onShowPanel:function(){
        	billController.loadRef();
        }
    });
};

Bill.prototype.setRef = function(row){// 设置关联单据信息
	if(row){
		$('#refAmount').val(row.balanceAmount);
		$('#balanceType').val(row.balanceType);
		$('#dtlDataGrid').datagrid('loadData',{total:0,rows:[]}); 
		billController.addDtl(row);
	}else{
		billController.clearRef();
	}
};

Bill.prototype.clearRef = function(){// 清空关联单据信息
	$('#refBillNo').combogrid('clear');
	$('#refAmount').val('');
	$('#balanceType').val('');
};

Bill.prototype.loadRef = function(){// 加载关联单据查询列表
	var salerNo = $('#mainDataForm').find("input[name=salerNo]").val();
	var buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
	var dg = $('#refBillNo').combogrid('grid');
	if(salerNo!= '' && buyerNo!=''){
		var queryParams = {salerNo:salerNo,buyerNo:buyerNo};
		dg.datagrid( 'options' ).queryParams = queryParams;
		dg.datagrid( 'options' ).url = billController.modulePath + '/search_ref?isHQ='+$('#isHQ').val();
		dg.datagrid( 'load' );
	}else{
		dg.datagrid('loadData',{total:0,rows:[]}); 
	}
};

Bill.prototype.save = function(){// 保存
	if($('#status').val()!=3 ){
		if(billController.doValidate()){
			var rows = $('#dtlDataGrid').datagrid('getRows');
			if(rows.length == 0){
				showInfo('请添加明细后在进行保存!');
				return ;
			}
			if($('#mainDataForm').find("input[name=balanceNo]").val() != ''){
				var refAmount = $('#refAmount').val();
				var amount = $('#allAmount').val();
				if(parseFloat(refAmount) != parseFloat(amount)){
					showError("请款金额与结算单金额不一致,不允许保存!");
					return ;
				}else{
					// 保存
	    			billController.doSubmit();
	    			billController.search();
				}
			}else{
				// 保存
				billController.doSubmit();
				billController.search();
			}
		}
	}else{
		showInfo("该单据流程已完结,不允许变更!");
	}
	
};

function callback(rowData, _this, _next){
	if(rowData){
		if(_next.val()!=rowData.code){
			_next.val(rowData.code);
			billController.clearRef();
		}
		if(_next.attr("name").indexOf("saler")!= -1){
			billController.setAccountInfo(rowData.code);
		}
	}else{
		_next.val('');
		_this.combogrid('clear');
		billController.clearRef();
	}
	var buyerNo = $('#mainDataForm').find("input[name=buyerNo]").val();
	if(buyerNo!=''){
		_fasCategoryUrl =  BasePath + '/common_util/getPageFasCategory?status=1&companyNo='+buyerNo;
	}else{
		_fasCategoryUrl =  '';
	}
}

Bill.prototype.addMainTab = function() {// 初始化单据查询tab
	var url = billController.modulePath + '/area/list_tabMain.htm';
	if($('#isHQ').val() == 'true'){
		url = billController.modulePath + '/hq/list_tabMain.htm';
	}
	$('#mainTab').tabs('add', {
		title : '单据列表',
		selected : false,
		closable : false,
		href : url ,
		onLoad : function(panel) {
			if($('#isHQ').val() == 'true'){
				$('#searchForm').find('input[combobox=balanceType]').attr('showType', 'HQ_VENDOR,AREA_OTHER');
				$('#searchForm').find('input[multiSearch=company]').attr('notGroupLeadRole',true);
			}else{
				$('#searchForm').find('input[combobox=balanceType]').attr('showType', 'AREA_AMONG,AREA_BUY,HQ_INSTEADOF_BUY,HQ_WHOLESALE');
				$('#searchForm').find('input[multiSearch=company]').attr('groupLeadRole',true);
			}
			common_util.initPage('searchForm');
		}
	});
	billController.refreshTabs();
};

var exportFields = [
    {
	title : '单据编码',
	field : 'billNo'
	},
    {
	title : '单据日期',
	field : 'billDate'
	},
    {
	title : '公司',
	field : 'buyerName'
	},
    {
	title : '供应商',
	field : 'salerName'
	},
    {
	title : '结算单号',
	field : 'balanceNo'
	},	
    {
	title : '结算单金额',
	field : 'balanceAmount'
	},	
	{
	title : '请款金额',
	field : 'allAmount'
	},
	{
	title : '币别',
	field : 'currencyNo'
	},
    {
	title : '备注',
	field : 'remark'
	}	
]


// 初始化
$(function(){
	billController = new Bill();
	billController.init(BasePath + '/bill_ask_payment', BasePath + '/bill_ask_payment_dtl');
	billController.initPage();
	setTimeout(function(){
		returnTab('mainTab', '单据列表');
	},300);
	//以超链接方式直接访问详细页面
	var isHQ = $('#isHQ').val();
	var billNoMenu = $("#billNoMenu").val();
	if(billNoMenu != null && billNoMenu != ''){
		var params = {billNo:billNoMenu};
		if(isHQ == 'true'){
			params = {billNo:billNoMenu,isHQ:true};
		}
		ajaxRequestAsync( BasePath + '/bill_ask_payment/list.json', params, function(data){
			var obj = data.rows[0];
			if(obj != null && obj != ''){
				setTimeout(function(){
					billController.loadDetail(0, obj);
				},500);
			}else {
				alert("单号："+ billNoMenu +"有误！", 1);
			}
		});
	}
	
	//加载预警列表
	loadWarnMessage();
});
