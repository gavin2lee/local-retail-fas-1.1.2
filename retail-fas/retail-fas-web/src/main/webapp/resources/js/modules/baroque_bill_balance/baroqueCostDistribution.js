/**
 * Created by user on 2016/6/8.
 */
function BaroqueCostDistribution() {
    this.openDistribute = function () {
        $('#dlg').dialog('open');
        $('#inputPurchaseFee').val('');
    };
    this.saveDistribute = function () {
        if (!this.validateRows()) {
            return false;
        }
        var purchaseFee = $('#inputPurchaseFee').val();
        if (!purchaseFee) {
            showWarn("请先填入分配金额!");
            return false;
        }
        var originalNos = this.getSelectedItem();
        $.ajax({
            type: "POST",
            url: "/fas/baroque_purchase_cost_distribution/distribute_cost",
            dataType: 'json',
            async: false,
            data: {
                originalNos: originalNos,
                fee : purchaseFee
            },
            success: function (msg) {
                if (msg == true) {
                    //refresh page
                    BaroqueCostDistribution.prototype.search.call(this);
                } else {
                    showWarn("分配失败!");
                }
            }
        });
    };
    this.typeIn = function () {
        $('#dlg').dialog('close');
    };
    this.cancal = function () {
        $('#inputPurchaseFee').val('');
        $('#dlg').dialog('close');
    };
    this.getSelectedItem = function () {
        var ids = '';
        var rows = $("#" + this.setting.dataGridId).datagrid("getChecked");
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].originalBillNo;
            if (i != rows.length - 1) {
                ids += ','
            }
        }
        return ids;
    };
    this.validateRows = function () {
        var checkedRows = $("#" + this.setting.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要分配的记录!");
            return false;
        }
        return true;
    };
    this.setting = {
        searchFormId: "searchForm",
        dataGridId: "dataGridDiv",
        searchUrl: "/list.json",
        exportTitle : "采购费用分配",
		exportUrl : "/do_fas_export"
    };
}
var baroqueCostDistribution = null;
$(function () {
    $.fas.extend(BaroqueCostDistribution, FasDialogController);
    baroqueCostDistribution = new BaroqueCostDistribution();
    baroqueCostDistribution.init("/baroque_purchase_cost_distribution", baroqueCostDistribution.setting);
    $('#dlg').dialog('close');
});

