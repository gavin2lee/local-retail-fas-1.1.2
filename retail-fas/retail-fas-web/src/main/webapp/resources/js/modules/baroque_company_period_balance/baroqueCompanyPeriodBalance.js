/**
 * Created by user on 2016/8/25.
 */
var BaroqueCompanyPeriodBalance = function(){
    var _monthData = [];
    var _initMonthData = function(){
        _monthData = [];
        for(var i = 1;i<=12;i++){
            _monthData.push({
                text: i.toString(),
                value: i.toString()
            })
        }
    }
    this.render = function(){
        var currentDate = new Date(),
            currentYear = currentDate.getFullYear(),
            currentMonth = currentDate.getMonth() + 1;
        _initMonthData();
        $("#monthCondition").initCombox({
            data:_monthData,
            valueField:"value",
            textField:"text",
            panelHeight:"auto",
            width : 160,
            editable:false,
            required:true,
            value: currentMonth
        });
        $('#yearCondition').combobox({
            url : BasePath + '/initCache/getLookupDtlsList.htm?lookupcode=YEAR',
            valueField : 'itemname',
            textField : 'itemname',
            panelHeight:"auto",
            width : 130,
            editable:false,
            required:true,
            value: currentYear
        });
    }
}

var baroqueCompanyPeriodBalance;

(function(){
   $(function(){
       $.fas.extend(BaroqueCompanyPeriodBalance, FasDialogController);
       baroqueCompanyPeriodBalance = new BaroqueCompanyPeriodBalance();
       baroqueCompanyPeriodBalance.init("/baroque_company_period_balance", {
           dataGridId : "dataGridDiv",
           searchBtn : "btn-search",
           searchUrl : "/baroque_company_period_balance.json",
           searchFormId : "searchForm",
           exportTitle : "巴洛克-公司期间结存导出",
           exportUrl : "/do_fas_export",
           exportType : "common"
       });
       baroqueCompanyPeriodBalance.render();
   })
}())