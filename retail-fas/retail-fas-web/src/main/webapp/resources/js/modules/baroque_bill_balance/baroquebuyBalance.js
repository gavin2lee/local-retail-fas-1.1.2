var baroqueBuyBalance;

var nullErrorMessage = "不存在当前单据";

var nullCheckMessage = "请选中需要操作的单据!";

var deleteErrorMessage = "只允许删除制单状态的单据!";

var backErrorMessage = "只允许反审核已审核状态的单据!";

var auditErrorMessage = "只允许审核制单状态的单据!";

var balanceParams = {
    mainTabUrl: BasePath + '/baroque_bill_balance/buy_tabMain.htm',
    listUrl: BasePath + '/baroque_bill_balance/list.json',
    addUrl: BasePath + '/baroque_bill_balance/save_bill',
    updateUrl: BasePath + '/baroque_bill_balance/update',
    deleteUrl: BasePath + '/baroque_bill_balance/delete_bill',
    batchDeleteUrl: BasePath + '/baroque_bill_balance/batch_delete_bill',
    verifyUrl: BasePath + '/baroque_bill_balance/verify',
    batchVerifyUrl: BasePath + '/baroque_bill_balance/batch_verify',
    balanceType: 17,
    dtlDataGrids: [{
        id: 'buyEnterDataGrid',
        title: '验收单',
        tabUrl: BasePath + '/baroque_bill_balance/buy_tab_enter.htm',
        listUrl: BasePath + '/baroque_bill_balance/buy_tab_enter.json',
        queryParams: {
            multiBillType: '(1304)'
        }
    }, {
        id: 'buyInvoiceDataGrid',
        title: '发票信息',
        tabUrl: BasePath + '/baroque_bill_balance/buy_tab_invoice.htm',
        listUrl: BasePath + '/baroque_bill_balance/buy_tab_invoice.json'
    }, {
        id: 'buyPayDataGrid',
        title: '请款信息',
        tabUrl: BasePath + '/baroque_bill_balance/buy_tab_pay.htm',
        listUrl: BasePath + '/baroque_bill_balance/buy_tab_pay.json'
    }]
};

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

function setVal(dialog) {
    var data = $('#mainDataForm').form('getData');
    dialog.$('#billNo').append(data.billNo);
    dialog.$('#statusName').append(data.statusName);
    dialog.$('#billName').append(data.billName);
    var currency = data.currency;
    if (currency == '0') {
        dialog.$('#currency').append("人民币");
    } else if (currency == '1') {
        dialog.$('#currency').append("美元");
    } else if (currency == '2') {
        dialog.$('#currency').append("欧元");
    } else if (currency == '3') {
        dialog.$('#currency').append("日元");
    } else if (currency == '4') {
        dialog.$('#currency').append("英镑");
    } else if (currency == '5') {
        dialog.$('#currency').append("港币");
    }
    dialog.$('#buyerName').append(data.buyerName);
    dialog.$('#salerName').append(data.salerName);
    dialog.$('#brandUnitName').append(data.brandUnitName);
    dialog.$('#categoryName').append(data.categoryName);
    dialog.$('#deductionAmount').append(data.deductionAmount);
    dialog.$('#balanceDate').append(data.balanceDate);
    dialog.$('#outQty').append(data.outQty);
    dialog.$('#returnQty').append(data.returnQty);
    dialog.$('#customReturnQty').append(data.customReturnQty);
    dialog.$('#balanceQty').append(data.balanceQty);
    dialog.$('#outAmount').append(data.outAmount);
    dialog.$('#returnAmount').append(data.returnAmount);
    dialog.$('#customReturnAmount').append(data.customReturnAmount);
    dialog.$('#balanceAmount').append(data.balanceAmount);
    dialog.$('#extendCategoryName').append(data.extendCategoryName);
    dialog.$('#remark').append(data.remark);
    dialog.$('#createUser').append($('#createUser').text());
    dialog.$('#auditor').append($('#auditor').text());
    dialog.$('#createTime').append($('#createTime').text());
    dialog.$('#auditTime').append($('#auditTime').text());
    dialog.$('#balanceStartDate').append(data.balanceStartDate);
    dialog.$('#balanceEndDate').append(data.balanceEndDate);
    dialog.$('#printTime').append(getCurrentNowTime());
}

function print() {
    var previewUrl = BasePath
        + '/print/preview?viewName=headquarters_manufacturer_print';
    var $dg = $('#exportExcelDG');
    var columns = $dg.datagrid('options').columns;
    var balanceType = $('#balanceType').val();
    var billNo = $('#billNo').val();
    ygDialog({
        isFrame: true,
        cache: false,
        title: '巴罗克结算单',
        modal: true,
        showMask: false,
        fit: true,
        href: previewUrl,
        buttons: [{
            text: '打印',
            iconCls: 'icon-print',
            handler: 'print_page'
        }],
        onLoad: function (win, dialog) {
            var tableHeader = dialog.$('#tableHeader');
            var tableBody = dialog.$('#tableBody');
            var columnsLength = columns.length;

            // 1过滤表头不需要打印的column 静态表头配置了printable=false都将不打印
            var grepColumns = $.grep(columns[columnsLength - 1],
                function (o, i) {
                    if ($(o).attr('printable') == false) {
                        return true;
                    }
                    return false;
                }, true);
            // 过滤大类列头
            var filterColumns = [];
            $.each(columns, function (i, n) {
                if ($(n).attr("printable") == undefined) {
                    filterColumns.push(n);
                }
            });
            /* filterColumns.push(grepColumns); */

            if (columnsLength >= 1) {
                // 品牌大类汇总
                dialog.$("#hiddenDiv").remove();
                dialog
                    .$(
                        "<div id='hiddenDiv' style='display:none;'><table id='exportExcelDG' ></table><div>")
                    .appendTo("body");
                dialog.$("#exportExcelDG").datagrid({
                    columns: filterColumns
                });
                var tbody = dialog.$("#exportExcelDG").prev(0).find(
                    '.datagrid-htable').find('tbody');
                tableHeader.append(tbody.html());
                setVal(dialog);
            }
            // 组装明细汇总表体
            ajaxRequestAsync(
                BasePath + '/bill_balance/hq/item_gather.json?balanceNo='
                + billNo + '&balanceType=' + balanceType
                + '&print=1',
                null,
                function (result) {
                    if (result.rows != undefined) {
                        var rows = result.rows;
                        var footerRows = result.footer;
                        for (var i = 0; i < rows.length; i++) {
                            var row = rows[i];
                            // 拼接表体信息
                            var bodyTrNode = '<tr>';
                            var bodyTdNode = '';
                            $(grepColumns)
                                .each(
                                    function (i, node) {
                                        var field = $(node).attr(
                                            'field');
                                        var value = row[field + ''];
                                        if (value == undefined) {
                                            bodyTdNode += '<td align="center">'
                                                + '' + '</td>';
                                        } else {
                                            bodyTdNode += '<td align="center">'
                                                + value
                                                + '</td>';
                                        }
                                    });
                            bodyTrNode += bodyTdNode + '</tr>';
                            // 填充表体
                            tableBody.append(bodyTrNode);
                        }

                        // 添加合计项
                        var bodyTrNode2 = '<tr>';
                        var bodyTdNode2 = '';
                        var footerRow = footerRows[0];
                        if (footerRow != undefined) {
                            $(grepColumns)
                                .each(
                                    function (i, node) {
                                        var field = $(node).attr(
                                            'field');
                                        var value = footerRow[field
                                        + ''];
                                        if (value == undefined) {
                                            bodyTdNode2 += '<td align="center">'
                                                + '' + '</td>';
                                        } else {
                                            bodyTdNode2 += '<td align="center">'
                                                + value
                                                + '</td>';
                                        }
                                    });
                            bodyTrNode2 += bodyTdNode2 + '</tr>';
                            tableBody.append(bodyTrNode2);
                        }
                    }
                });

            dialog.print_page(this);
            win.close();
        }
    });
}

var notExportFlag = false;

var brandNotExportFalg = false;

var supplierNoRepeat = false;

var mergeFlag = false;

function exportSubmit(url, checkRows, mergeFlag) {
    $.messager.progress({
        title: '请稍后',
        msg: '正在处理，请耐心等候...',
        text: ''
    });
    $("#exportExcelForm").remove();
    $("<form id='exportExcelForm'  method='post'></form>").appendTo("body");
    ;
    var fromObj = $('#exportExcelForm');
    fromObj.form('submit', {
        url: url,
        onSubmit: function (param) {
            param.mergeFlag = mergeFlag;
            param.checkRows = JSON.stringify(checkRows);
        },
        success: function () {
            messager.progress('close');
        }
    });
    var interval = setInterval(function () {
        ajaxRequestAsync(BasePath + '/print_balance/getExportFlag', null,
            function (data) {
                console.info(JSON.stringify(data));
                if (data && data[currentUser.userid]
                    && data[currentUser.userid] == 'true') {
                    $.messager.progress('close');
                    window.clearInterval(interval);
                }
            });
    }, 3000);
    setTimeout(function () {
        if (interval) {
            window.clearInterval(interval);
            $.messager.progress('close');
        }
    }, 300000);
}

function exportBalance(flag) {
    notExportFlag = false;
    brandNotExportFalg = false;
    mergeFlag = false;
    var checkRows = $('#mainDataGrid').datagrid('getChecked');
    if (checkRows.length > 0) {
        var url = BasePath + '/print_balance/export_balance_data';
        if (flag == 'gather') {
            url = BasePath + '/print_balance/export_balance_gather_data';
        }
        var year = checkRows[0].balanceEndDate.substring(0, 7);
        var brandUnitNo = checkRows[0].brandUnitNo;
        $.each(checkRows, function (index, item) {
            if (year != item.balanceEndDate.substring(0, 7)) {
                notExportFlag = true;
                return false;
            }
            if (brandUnitNo != item.brandUnitNo) {
                brandNotExportFalg = true;
                return false;
            }
        });

        if (notExportFlag) {
            showWarn("请勿选择跨月的结算单!");
            notExportFlag = false;
            return;
        }
        if (brandNotExportFalg) {
            showWarn("请勿选择跨品牌部的结算单!");
            brandNotExportFalg = false;
            return;
        }

        $.each(checkRows, function (indexI, itemI) {
            $.each(checkRows, function (indexJ, itemJ) {
                if (itemI.salerNo == itemJ.salerNo && indexI != indexJ) {
                    supplierNoRepeat = true;
                    return false;
                }
            });
            if (supplierNoRepeat) {
                return false;
            }
        });
        if (supplierNoRepeat && flag != 'gather') {
            $.messager.confirm("确认", "确定要合并厂商生成明细对账单吗?", function (r) {
                if (r) {
                    exportSubmit(url, checkRows, true);
                } else {
                    exportSubmit(url, checkRows, false);
                }
            });
        } else {
            exportSubmit(url, checkRows, false);
        }
    } else {
        showInfo("请选中需要生成的结算单!");
    }
}

function printBalance() {
    var previewUrl = BasePath + '/print/preview?viewName=balance_print';
    ygDialog({
        isFrame: true,
        cache: false,
        title: '厂商结算单',
        modal: true,
        showMask: false,
        fit: true,
        href: previewUrl,
        buttons: [{
            text: '打印',
            iconCls: 'icon-print',
            handler: 'print_page'
        }],
        onLoad: function (win, dialog) {
            var checkRows = $('#mainDataGrid').datagrid('getChecked');
            if (checkRows.length > 0) {
                var balanceNo = "";
                $.each(checkRows, function (index, item) {
                    balanceNo += item.bill_no + ',';
                });
                var tableHeader = dialog.$('#tableHeader');
                var tableBody = dialog.$('#tableBody');
                ajaxRequestAsync(
                    BasePath + '/print_balance/get_print_data',
                    {
                        checkRows: JSON.stringify(checkRows)
                    },
                    function (data) {
                        var bill = data.bill;
                        var colmunList = data.colmunList;
                        var rowList = data.rowList;
                        var footerList = data.footerList;
                        var title = bill.balanceEndDate.substring(0, 4)
                            + "年" + bill.balanceEndDate.substring(5, 7)
                            + "月 (全月)" + bill.brandUnitNo + "品牌对账单";
                        var supplierName = bill.salerName;
                        dialog.$('#bill_title').text(title);
                        dialog.$('#bill_supplier').text(supplierName);
                        var sendSize = colmunList.length - 8;
                        var firstRow = "<tr><th aligh='center' colspan='"
                            + sendSize
                            + "'>发货地区、数量、金额</th><th aligh='center' colspan='4'>残鞋</th><th aligh='center' colspan='4'>退货</th><th aligh='center'>其他扣项</th></tr>"
                        var secondRow = "<tr>"
                        $.each(colmunList, function (index, item) {
                            var width = item.width;
                            secondRow += "<th aligh='center'>" + item.title
                                + "</th>"
                        });
                        secondRow += "</tr>";
                        tableHeader.append(firstRow);
                        tableHeader.append(secondRow);
                        var ckIndex = 1;
                        $.each(rowList, function (index, row) {
                            var tr_row = "<tr>";
                            $.each(colmunList, function (index, colmun) {
                                var field = colmun['field' + ''];
                                var value = row[field];
                                if (field == 'ck') {
                                    tr_row += '<td align="center">'
                                        + ckIndex + '</td>';
                                } else {
                                    if (value != undefined) {
                                        tr_row += '<td align="center">'
                                            + value + '</td>';
                                    } else {
                                        tr_row += '<td align="center">'
                                            + '' + '</td>';
                                    }
                                }
                            });
                            tr_row += '</tr>';
                            tableBody.append(tr_row);
                            ckIndex++;
                        });
                        var tr_footer = "<tr>";
                        tr_footer += "<td align='center' colspan='3'>合计</td>";
                        $.each(colmunList, function (index, colmun) {
                            var field = colmun['field' + ''];
                            var value = footerList[0][field];
                            if (value != undefined) {
                                tr_footer += '<td align="center">' + value
                                    + '</td>';
                            } else {
                                tr_footer += '<td align="center">' + ''
                                    + '</td>';
                            }
                        });
                        tr_footer += "<td align='center'>"
                            + bill.deductionAmount + "</td></tr>";
                        var tr_all_footer = "<tr><td align='center' colspan='3'>应收/付账款</td><td align='right' colspan='99'>"
                            + bill.balanceAmount + "</td></tr>";
                        tableBody.append(tr_footer);
                        tableBody.append(tr_all_footer);
                    });
            }
            dialog.print_page(this);
            win.close();
        }
    });

}

function BaroqueBuyBalance() {
    this.doAudit = function (status) {// 审核
        $this = this;
        var errorMessage = this.getErrorMessage($('#status').val(), status);
        if (errorMessage != "") {
            showInfo(errorMessage);
            return;
        }
        var message = "你确定要审核这条单据?"
        if (status == 0) {
            message = "你确定要反审核这条单据?"
            if ($('#askPaymentNo').length > 0 && $('#invoiceNo').length > 0) {
                if (($('#askPaymentNo').val() && $('#askPaymentNo').val() != '')
                    || ($('#invoiceNo').val() && $('#invoiceNo').val() != '')) {
                    showInfo("已关联下游单据的结算单不允许打回!");
                    return;
                }
            }
        }
        $.messager.confirm("确认", message, function (r) {
            if (r) {
                var data = {
                    status: status,
                    billNo: $('#billNo').val()
                };
                ajaxRequestAsync(balanceParams.verifyUrl, data,
                    function (result) {
                        if (result) {
                            showSuc('操作成功');
                            $this.loadMainData(result);
                            $this.search();
                        } else {
                            showError('操作失败');
                        }
                    });
            }
        });
    };
    this.getErrorMessage = function (currStatus, operStatus) {
        if (currStatus === '') {
            return nullMessage;
        }
        if (typeof operStatus == 'undefined') {
            if (currStatus != 0 && currStatus != 99) {
                return deleteErrorMessage;
            }
        }
        if (typeof operStatus != 'undefined') {
            if (operStatus == 0 && currStatus != 1) {
                return backErrorMessage;
            }
            if (operStatus == 1 && currStatus != 0) {
                return auditErrorMessage;
            }
        }
        return "";
    };
    this.getBatchErrorMessage = function (checkedRows, operStatus) {
        var $this = this;
        if (checkedRows.length == 0) {
            return nullCheckMessage;
        }
        var errorMessage = "";
        $.each(checkedRows, function (index, item) {
            errorMessage = $this.getErrorMessage(item.status, operStatus);
            if (errorMessage != "") {
                return false;
            }
            if (operStatus == 99
                && ((item.askPaymentNo && item.askPaymentNo != '') || (item.invoiceNo && item.invoiceNo != ''))) {
                errorMessage = "已关联下游单据的结算单不允许打回!";
                return false;
            }
        });
        return errorMessage;
    };
    this.batchDel = function () {// 批量删除
        $this = this;
        var checkedRows = $('#mainDataGrid').datagrid('getChecked');
        var errorMessage = $this.getBatchErrorMessage(checkedRows);
        if (errorMessage != "") {
            showInfo(errorMessage);
            return;
        }
        $.messager.confirm("确认", "你选中了" + checkedRows.length + "条单据，确定要删除?", function (r) {
            if (r) {
                var url = $this.params.batchDeleteUrl;
                var ids = "";
                $.each(checkedRows, function (index, row) {
                    ids += row.id + ',' + row.billNo + ";";
                });
                console.log(ids);
                var params = {ids: ids.substring(0, ids.length - 1), balanceType: $this.params.balanceType};
                $.messager.progress({
                    title: '请稍后',
                    msg: '正在处理中...'
                });
                ajaxRequestAsync(url, params, function (data) {
                    if (data) {
                        showSuc('删除成功');
                        $this.search();
                    } else {
                        showError('删除失败');
                    }
                    $.messager.progress('close');
                });
            }
        });
    };
    this.batchVerify = function (status) {// 批量审核反审核
        console.log('batchVerify called.');
        $this = this;
        var checkedRows = $('#mainDataGrid').datagrid('getChecked');
        var errorMessage = $this.getBatchErrorMessage(checkedRows, status);
        if (errorMessage != "") {
            showInfo(errorMessage);
            return;
        }
        $.messager.confirm("确认", "你选中了" + checkedRows.length + "条单据，确定要操作?", function (r) {
            if (r) {
                var url = $this.params.batchVerifyUrl;
                var ids = "";
                $.each(checkedRows, function (index, row) {
                    ids += row.billNo + ";";
                });
                var params = {
                    status: status,
                    ids: ids.substring(0, ids.length - 1),
                    balanceType: $this.params.balanceType
                };
                $.messager.progress({
                    title: '请稍后',
                    msg: '正在处理中...'
                });
                ajaxRequestAsync(url, params, function (data) {
                    if (data) {
                        showSuc('操作成功');
                        $this.search();
                    } else {
                        showError('操作失败');
                    }
                    $.messager.progress('close');
                });
            }
        });
    };
    this.generateAskPaymentBill = function () {
        $this = this;
        var checkRows = $('#mainDataGrid').datagrid('getChecked');
        if (checkRows.length == 0) {
            showInfo("请选中需要生成请款单的结算单据(未经过请款且财务确认)!");
            return;
        }
        var flag = false;
        var errorMessage = "";
        $.each(checkRows, function (index, row) {
            if (!(row.status == 1 && (row.askPaymentNo == '' || row.askPaymentNo == null))) {
                errorMessage = "请选择未请款并且财务确认的结算单!";
                return false;
            }
        });
        if (errorMessage != "") {
            showInfo(errorMessage);
            return;
        }
        $.messager.confirm("确认", "确认根据选中的结算单生成请款单?", function (r) {
            if (r) {
                var url = BasePath + "/bill_ask_payment/generate_bill_by_balance";
                var params = {checkRows: JSON.stringify(checkRows), balanceType: 1};
                ajaxRequestAsync(url, params, function (data) {
                    if (data > 0) {
                        $this.search();
                        showSuc('成功生成' + data + '条请款单');
                    } else {
                        showError('生成失败');
                    }
                });
            }
        });
    };
}
// 初始化
$(function () {
    $.fas.extend(BaroqueBuyBalance, BalanceBaseController);
    baroqueBuyBalance = new BaroqueBuyBalance();
    baroqueBuyBalance.init(balanceParams);
    baroqueBuyBalance.initPage();

    // 加载预警列表
    loadWarnMessage();
});
