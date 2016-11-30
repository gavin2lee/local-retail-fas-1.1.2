var baReceipt = new Object();

//查询
baReceipt.search = function() {
	if($('#searchForm').form('validate')){
		var params = $('#searchForm').form('getData');
		var url = BasePath + '/ba_receipt/list.json';
		baReceipt.selectDg.datagrid('options').queryParams= params;
		baReceipt.selectDg.datagrid('options').url= url;
		baReceipt.selectDg.datagrid('load');
	}
};

//清空
baReceipt.clear = function() {
	$('#searchForm').form("clear");
	$('#searchForm').find("tbody").find("input").val("");
};

// 导出
baReceipt.doExport = function(){
	var searchType = $('#searchType').val();
	if(searchType == 'bill'){
		pe_util.doExport('sumDataGrid', '/ba_receipt/export_data', '验收单列表-单据汇总',{searchType:searchType});
	}else{
		pe_util.doExport('dtlDataGrid', '/ba_receipt/export_data', '验收单列表-单据明细',{searchType:searchType});
	}
};

baReceipt.validateRows = function () {
    var checkedRows = $('#dtlDataGrid').datagrid('getChecked');// 获取所有勾选checkbox的行
    if (checkedRows.length < 1) {
        showWarn("请选择要更新的记录!");
        return false;
    }else{
        for(var i =0;i<checkedRows.length;i++){
            var item = checkedRows[i];
            if(item.balanceNo && item.balanceNo!='') {
                showWarn("该单据：" + item.balanceNo + "已生成结算单！");
                return false;
            }
        }
    }
    return true;
};

baReceipt.getSelectedItem = function () {
    var rows = $('#dtlDataGrid').datagrid('getChecked'),
        json = '';
    for (var i = 0; i < rows.length; i++) {
        json += rows[i].originalBillNo + '@' + rows[i].itemNo;
        json += ';';
    }
    json = json.substring(0, json.length - 1);
    return json;
};

baReceipt.saveTagPrice = function(dialog){
	var  url = BasePath + '/ba_receipt/updateTagPrice',
		 params = $('#updateTagPriceForm').form('getData');
	if ($('#updateTagPriceForm').form('validate') == false) {
		return false;
	}
	var  panel = new $.fas.ProgressPanel({
			title:"牌价更新",
			url:url,
			onclose:function(){
				dialog.close();
				showSuc('操作成功');
			}
	}, params);
	panel.open();
};

baReceipt.updateTagPrice = function(){
	$('#updateTagPriceForm').form('clear');
	let $this = this;
	ygDialog({
		title : '更新牌价',
		target : $('#updateTagPricePanel'),
		width : 700,
		height : 250,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(dialog) {
				$this.saveTagPrice(dialog);
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function(dialog) {
				dialog.close();
			}
		}]
	});
}

//更新价格
baReceipt.updateRate = function(){
	var  url = BasePath + '/ba_receipt/updateCost',
		 params = $('#searchForm').form('getData'),
         rows = $('#dtlDataGrid').datagrid('getChecked'),
		 ids = '',
         $this = this;
    if (!this.validateRows()) {
        return false;
    }
    var ids = $this.getSelectedItem();
    $.messager.confirm("确认","你确定要更新选中单据的价格?",function(r) {
        if (r) {
            $.messager.progress({
                title: '请稍后',
                msg: '正在处理中...'
            });
            $.ajax({
                type: "POST",
                url: url,
                dataType: 'json',
                async: true,
                data: {
                    ids: ids
                },
                success: function (data) {
                    $.messager.progress('close');
                    if (data) {
                    	 if (data.success == true) {
                         	 showWarn(data['mgr']);
                             $this.search();
                         } else {
                         	showWarn("更新失败!");
                         }
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    $.messager.progress('close');
                    showWarn("更新失败!");
                }
            });
        }
    });
};

//生成结算单
baReceipt.generateBalance = function(){
	var url = BasePath + '/ba_receipt/generate_balance';
	var params = $('#searchForm').form('getData');
	var dataRow=baReceipt.selectDg.datagrid('getChecked');
	if(dataRow.length >0){
		for(var i =0; i < dataRow.length; i++){
			var item = dataRow[i];
			if(item.balanceNo && item.balanceNo!='') {
				showWarn("该单据：" + item.balanceNo + "已生成结算单！");
				return false;
			}
		}
		params.checkedRows = JSON.stringify(dataRow);
		$.messager.confirm("确认","你确定要生成已勾选单据的结算单?",function(r) {
			if (r) {
				baReceipt.doSubmit(url,params,"balance"); 
			}
		});
	}else{
		showInfo("请勾选需要操作的单据!");
	}
};

//表单提交
baReceipt.doSubmit = function(url,params,type){
	$("#submitForm").remove();
	$("<form id='submitForm'  method='post'></form>").appendTo("body"); 
	$.messager.progress({
		title:'请稍后',
		msg:'正在处理...'
	}); 
	$('#submitForm').form('submit',{
		url : url,
		onSubmit:function(param){
			if(params && params!={}){
				$.each(params,function(i){
					param[i]=params[i];
				});
			}
		},
		success:function(data){
			showSuc('操作成功');
			$.messager.progress('close');
			setTimeout(function(){
				baReceipt.search();
			},500);
			if(type == "balance"){
				if(JSON.parse(data).count >0){
					showInfo("成功生成"+JSON.parse(data).count+"条结算单!");
				}
			}
		},
		error:function(){
			showError('操作失败');
			$.messager.progress('close');
		}
	});
};

baReceipt.selectDg;

$(function(){
	baReceipt.selectDg = $('#sumDataGrid');
	$('#searchType').val('bill');
	$('#styleNameCon').combobox('disable');
	$('#styleNameCon').combobox('clear');
	$('#styleNoCon').val('');
	$('#dtlTab').tabs({    
	    onSelect:function(title){
	    	if(title == '单据汇总'){
	    		baReceipt.selectDg = $('#sumDataGrid');
	    		$('#searchType').val('bill');
				$('#styleNameCon').combobox('disable');
				$('#styleNameCon').combobox('clear');
				$('#styleNoCon').val('');
	    	}else if(title == '单据明细'){
	    		baReceipt.selectDg = $('#dtlDataGrid');
	    		$('#searchType').val('item');
				$('#styleNameCon').combobox('enable');
				$('#styleNameCon').combobox('clear');
				$('#styleNoCon').val('');
	    	}
	    }    
	}); 
});



