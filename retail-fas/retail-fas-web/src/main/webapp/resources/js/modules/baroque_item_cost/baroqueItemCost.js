/**
 * Created by user on 2016/7/20.
 */
function BaroqueItemCost() {
    this.currentDate = null;
    this.monthData = null;
    this.initView = function () {
        $this = this;
        initCurrentDate($this);
        var currentDate = new Date();
        var currentYear = currentDate.getFullYear();
        var currentMonth = currentDate.getMonth() + 1;
        $("#monthCondition").initCombox({
            data: $this.monthData,
            valueField: "value",
            textField: "text",
            panelHeight: "auto",
            width: 160,
            required: true,
            editable: false,
            value: currentMonth
        });
        $('#yearCondition').combobox({
            url: BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
            valueField: 'itemname',
            textField: 'itemname',
            panelHeight: "auto",
            width: 160,
            required: true,
            editable: false,
            value: currentYear
        });
        $("#genarateMonth").initCombox({
            data: $this.monthData,
            valueField: "value",
            textField: "text",
            panelHeight: "auto",
            width: 160,
            required: true,
            editable: false,
            value: currentMonth
        });
        $('#genarateYear').combobox({
            url: BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
            valueField: 'itemname',
            textField: 'itemname',
            panelHeight: "auto",
            width: 160,
            required: true,
            editable: false,
            value: currentYear
        });
    };
    var initCurrentDate = function ($this) {
        var now = new Date(),
            currentMonth = now.getMonth() + 1,
            currentYear = currentMonth == 0 ? now.getFullYear() - 1 : now.getFullYear();
        currentMonth = currentMonth == 0 ? 12 : currentMonth;
        $this.currentDate = {
            month: currentMonth,
            year: currentYear
        };
        $this.monthData = [];
        for (var i = 1; i <= 12; i++) {
            $this.monthData.push({
                'text': i.toString(),
                'value': i.toString()
            });
        }
    };

    this.generateHeadquarterCost = function () {
        $this = this;
        ygDialog({
            title: '生成单位成本',
            target: $('#genarateFormPanel'),
            width: 680,
            height: 300,
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: function (dialog) {
                    baroqueItemCost.generateSave(dialog);
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function (dialog) {
                    dialog.close();
                }
            }]
        });
    };
    this.generateSave = function (dialog) {
        var $this = this;
        if (!$('#genarateDataForm').form('validate')) {
            return false;
        }
        if (!$this.checkCompanySettleDate()) {
			showError('公司结账日未设置或已关账!');
			return false;
		}
		if(!$this.checkCompanyBrandUnitSettleDate()){
			showError('公司品牌部结账日未设置或已关账!');
			return false;
		}
        $.messager.confirm("确认","你确定要生成成本?",function(r) {
            if (r) {
                $.messager.progress({
                    title: '请稍后',
                    msg: '正在处理中...'
                });
                $.ajax({
                    type: "POST",
                    url: '/fas/baroque_item_cost/generateItemCost',
                    dataType: 'json',
                    async: true,
                    data:  $('#genarateDataForm').form('getData'),
                    success: function (data) {
                        $.messager.progress('close');
                        if (data) {
                            if (data.success == true) {
                                showWarn("生成成功!");
                                $('#genarateDataForm').form('clear');
                                $("input[type='hidden']", '#genarateFormPanel').val('');
                                dialog.close();
                                $this.search();
                            } else {
                                showWarn("生成失败!");
                            }
                        }
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        $.messager.progress('close');
                        showWarn("生成失败!");
                    }
                });
            }
        });
    };
    this.generateParasm = function () {

    };
    this.checkCompanySettleDate = function(){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/company_utils/check_settle_date.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  canDo = true;
				  }
			  }
	   });
	 	return canDo;
	};
	
	this.checkCompanyBrandUnitSettleDate = function (){
		var canDo=false;
		// 2.业务校验
		$.ajax({
			  type: 'POST',
			  url: BasePath + "/brand_utils/check_brand_unit_settle_date.json",
			  data: $('#genarateDataForm').form('getData'),
			  cache: true,
			  async:false, // 一定要
			  dataType: 'json', 
			  success: function(result){
				  if(result && result.success){
					  canDo = true;
				  }
			  }
	   });
	 	return canDo;
	};
}

var baroqueItemCost;

$(function () {
    $.fas.extend(BaroqueItemCost, FasDialogController);
    baroqueItemCost = new BaroqueItemCost();
    baroqueItemCost.initView();
    baroqueItemCost.init("/baroque_item_cost", {
        dataGridId: "dataGridDiv",
        searchBtn: "btn-search",
        searchUrl: "/list.json",
        searchFormId: "searchForm",
        exportTitle: "加权成本导出",
        exportUrl: "/export",
        exportType: "common"
    });
});
