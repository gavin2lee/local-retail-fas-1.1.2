/**
 * Created by user on 2016/6/8.
 */
function BaroqueRegionCost() {
    this.url = '/fas/baroque_purchase_region_cost/updateRegionCost';
    this.currentTabName = 'item';
    this.doExport = function () {
        var tab = $('#mainTab').tabs('getSelected');
        var index = $('#mainTab').tabs('getTabIndex', tab);
        if (0 == index) {
            $.fas.exportExcel({
                dataGridId: "dataGridDiv",
                exportUrl: "/baroque_purchase_region_cost/export",
                exportTitle: "地区价单据明细导出",
            });
        } else {
            $.fas.exportExcel({
                dataGridId: "sumDataGrid",
                exportUrl: "/baroque_purchase_region_cost/sum_export",
                exportTitle: "地区价单据汇总导出",
            });
        }
    };
    this.search = function(){
        $this = this;
        if ($this.currentTabName==='group') {
            $.fas.search({
                searchFormId: $this.setting.searchFormId,
                dataGridId: $this.setting.sumDataGridId,
                searchUrl: $this.options.sumSearchUrl
            });
        }else{
            $.fas.search({
                searchFormId: $this.setting.searchFormId,
                dataGridId: $this.setting.dataGridId,
                searchUrl: $this.options.searchUrl
            });
        }
    };
    this.updateRegionCost = function () {
        $this = this;
        if (!this.validateRows()) {
            return false;
        }
        $.messager.confirm("确认","你确定要更新选中单据的价格?",function(r) {
            if (r) {
                var ids = $this.getSelectedItem();
                $.messager.progress({
                    title: '请稍后',
                    msg: '正在处理中...'
                });
                $.ajax({
                    type: "POST",
                    url: $this.url,
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
    this.getSelectedItem = function () {
        var rows = $("#" + this.setting.dataGridId).datagrid("getChecked"),
            json = '';
        for (var i = 0; i < rows.length; i++) {
            json += rows[i].originalBillNo + '@' + rows[i].itemNo;
            json += ';';
        }
        json = json.substring(0, json.length - 1);
        return json;
    };
    this.validateRows = function () {
        var checkedRows = $("#" + this.setting.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
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
    this.tabSelected = function (title) {
        var $this = this;
        if (title == '单据汇总') {
            $this.currentTabName = 'group';
            $('#styleNameCon').combobox('disable');
            $('#styleNameCon').combobox('clear');
            $('#styleNoCon').val('');
        }else{
            $this.currentTabName = 'item';
            $('#styleNameCon').combobox('enable');
            $('#styleNameCon').combobox('clear');
            $('#styleNoCon').val('');
        }
    };
    this.fomrmatStatus = function(value, rowData, rowIndex){
    	console.log(value);
    	return value == '1'?'手工':'';
    };
    this.openEditor = function(row){
    	if(row.balanceNo && row.balanceNo!='') {
            showWarn("该单据：" + row.balanceNo + "已生成结算单！");
            return false;
        }
    	$('#updateBuyAdlForm').form('clear');
    	$('#updateBuyAdlForm').form('load',row);
    	let $this = this;
    	ygDialog({
    		title : '汇率税率修改',
    		target : $('#updateBuyAdl'),
    		width : 450,
    		height : 280,
    		buttons : [ {
    			text : '保存',
    			iconCls : 'icon-save',
    			handler : function(dialog) {
    				$this.saveEditor(dialog);
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
    this.getFormData = function(formId){
    	var row =  $(formId).serializeArray();
    	var obj = null;
    	if(row){
    		obj = {};
        	row.map(c=>obj[c.name] = c.value);
    	}
    	return obj;
    };
    this.saveEditor = function(dialog){
    	var url = '/fas/baroque_purchase_region_cost/updateBuyAdl',
    	    $this = this;
        if (!$('#updateBuyAdlForm').form('validate')) {
            return;
        }
	    var postData = this.getFormData('#updateBuyAdlForm');
    	if(!postData){
    		showWarn("无法获取修改值,更新失败!");
    	}
    	$.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            async: true,
            data:postData,
            success: function (data) {
				if (data) {
                    if (data.success == true) {
                        showWarn("更新成功!");
                        $this.search();
                        dialog.close();
                    } else {
                        showWarn(data['mgr']);
                    }
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                showWarn("更新失败,请重试!");
            }
        });
    }
    this.setting = {
        searchFormId: "searchForm",
        dataGridId: "dataGridDiv",
        sumDataGridId: 'sumDataGrid',
        searchUrl: "/baroque_purchase_region_cost/list.json",
        sumSearchUrl: '/baroque_purchase_region_cost/sumList',
        exportTitle: "巴洛克地区价计算",
        exportUrl: "/do_fas_export",
        updateOption: {
            vat: 2,
            discount: 3
        }
    };
}
var baroqueRegionCost = null;

$(function () {
    $.fas.extend(BaroqueRegionCost, FasDialogController);
    baroqueRegionCost = new BaroqueRegionCost();
    baroqueRegionCost.init("/baroque_purchase_region_cost",
        baroqueRegionCost.setting);
    $('#mainTab').tabs({
        border: false,
        onSelect: function (title) {
            baroqueRegionCost.tabSelected(title);
        }
    });
});
