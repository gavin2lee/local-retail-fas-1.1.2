/**
 * *********************************** easyui-editor扩展
 * *****************************************
 */
// jquery类级插件
(function () {
    var path = resourcesUrl;
    var gmsPath = "/gms";
    if (window.location.href.indexOf('dev') >= 0) {
        path = '';
        gmsPath = "http://dev.gms.belle.net.cn/" + gmsPath;
    }
    if (typeof seajs !== 'undefined') {
        seajs.config({
            "base": "/fas",
            map: [
                // [".js",".js?v=" + window.version]
                [/^(.*\.(?:css|js))(.*)$/i, '$1?' + window.version]
            ],
            "paths": {
                "gms": gmsPath + "/resources/js",
                "fas": path + "/fas/resources/js",
                "bill": gmsPath + "/resources/js/bill"
            }
        });
    }
    seajs.use(["gms/authority"], function (authority) {
        window.user = authority.getUserInfo();
    });
})();

// 替换原有easyui 的form的扩展方法，原load和reset方法存在bug
(function ($) {

    // 检查对象是否为空
    function isNotBlank(obj) {
        if (!obj || typeof obj == 'undefined' || obj == '') {
            if ('0' == obj) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * submit the form
     */
    function ajaxSubmit(target, options) {
        options = options || {};
        var param = {};
        if (options.onSubmit) {
            if (options.onSubmit.call(target, param) == false) {
                return;

            }
        }
        var form = $(target);
        if (options.url) {
            form.attr('action', options.url);
        }
        var frameId = 'easyui_frame_' + (sysDateReal.getTime());
        var frame = $(
            '<iframe id=' + frameId + ' name=' + frameId + '></iframe>')
            .attr(
                'src',
                window.ActiveXObject ? 'javascript:false'
                    : 'about:blank').css({
                position: 'absolute',
                top: -1000,
                left: -1000
            });
        var t = form.attr('target'), a = form.attr('action');
        form.attr('target', frameId);
        var paramFields = $();
        try {
            frame.appendTo('body');
            frame.bind('load', cb);
            for (var n in param) {
                var f = $('<input type="hidden" name="' + n + '">').val(
                    param[n]).appendTo(form);
                paramFields = paramFields.add(f);
            }
            checkState();
            form[0].submit();
        } finally {
            form.attr('action', a);
            t ? form.attr('target', t) : form.removeAttr('target');
            paramFields.remove();
        }
        function checkState() {
            var f = $('#' + frameId);
            if (!f.length) {
                return
            }
            try {
                var s = f.contents()[0].readyState;
                if (s && s.toLowerCase() == 'uninitialized') {
                    setTimeout(checkState, 100);
                }
            } catch (e) {
                cb();
            }
        }

        var checkCount = 10;

        function cb() {
            var frame = $('#' + frameId);
            if (!frame.length) {
                return
            }
            frame.unbind();
            var data = '';
            try {
                var body = frame.contents().find('body');
                data = body.html();
                if (data == '') {
                    if (--checkCount) {
                        setTimeout(cb, 100);
                        return;
                    }
                    // return;
                }
                var ta = body.find('>textarea');
                if (ta.length) {
                    data = ta.val();
                } else {
                    var pre = body.find('>pre');
                    if (pre.length) {
                        data = pre.html();
                    }
                }
            } catch (e) {

            }
            if (options.success) {
                options.success(data);
            }
            setTimeout(function () {
                frame.unbind();
                frame.remove();
            }, 100);
        }
    }

    function post(target, options) {
        options = options || {};
        var param = {};
        var form = $(target);
        var url = options.url;
        var data1 = {};
        $.each($('input,textarea,select', form), function (i, input) {
            var name = $(input).attr('name');
            if (name)
                data1[name] = $(input).val();
        });
        var data2 = _getCombo(target);
        param = $.extend(param, data1, data2);
        if (options.onSubmit) {
            options.onSubmit.call(target, param);
        }
        var def = $.Deferred();
        $.post(url, param).success(function (data, state, xhr) {
            if (data) {
                if (isNotBlank(data.errorMessage)) {
                    showError(data.errorMessage + " " + data.errorDefined);
                    def.reject();
                } else {
                    if (data.hasOwnProperty('errorCode')) {
                        def.reject();
                    } else {
                        if (options.success) {
                            options.success.call(this, JSON.stringify(data));
                        }
                        def.resolve(data);
                    }
                }
            } else {
                showError('操作失败,请联系管理员!');
                def.reject();
            }
        }).error(function () {
            def.reject();
        });
        return def;
    }

    function _getCombo(target) {
        var form = $(target);
        var cc = $.fn.form.plugins;
        var data = {};
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('getValue') && plug.methods.hasOwnProperty('getNameValue')) {
                var editors = form.find(".easyui-" + name);
                if (editors != undefined && editors.length > 0) {
                    $.each(editors, function (i, jq) {
                        var editor = $(jq);
                        var options = editor[name]('options');
                        if (!isNotBlank(options.isNormarl)) {
                            if (isNotBlank(options.inputValueFeild)) {
                                data[options.inputValueFeild] = editor[name]('getValue');// 提交时获取no
                            } else if (isNotBlank(options.valueFeild)) {
                                data[options.valueFeild] = editor[name]('getValue');// 提交时获取no
                            }
                            if (isNotBlank(options.inputNameFeild)) {
                                data[options.inputNameFeild] = editor[name]('getNameValue');// 提交时获取name
                            } else if (isNotBlank(options.valueNameFeild)) {
                                data[options.valueNameFeild] = editor[name]('getNameValue');// 提交时获取name
                            }
                            if (isNotBlank(options.attachValueFeild)) {
                                data[options.attachValueFeild] = editor[name]('getAttachValue');// 提交时获取附加参数
                            }
                        }
                    });
                }
            }
        });

        for (var i = 0; i < cc.length; i++) {
            var type = cc[i];
            var cmbs = form.find("." + type + '-f');
            $.each(cmbs, function (i, e) {
                var c = $(e);
                var name = c.attr('comboName');
                if (!data.hasOwnProperty(name)) {
                    var val = null;
                    if (c[type]('options').multiple) {
                        val = c[type]('getValues');
                    } else {
                        val = c[type]('getValue');
                    }
                    data[name] = val;
                }
            });
        }
        return data;
    }

    // daixiaowei
    function _disable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('disable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('disable');// 禁用控件
                }
            }
        });
    }

    function _enable(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('enable')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('enable');// 启用控件
                }
            }
        });
    }

    // 设置默认值 取权限数据
    function _setDefaultValue(target) {
        var form = $(target);
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('setDefaultValue')) {
                var editor = form.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('setDefaultValue');// 设置默认值 取权限数据
                }
            }
        });
    }

    /**
     * load form data if data is a URL string type load from remote site,
     * otherwise load from local data object.
     */
    function load(target, data) {
        if (!$.data(target, 'form')) {
            $.data(target, 'form', {
                options: $.extend({}, $.fn.form.defaults)
            });
        }
        var opts = $.data(target, 'form').options;
        if (typeof data == 'string' && data !== "") {
            var param = {};
            if (opts.onBeforeLoad.call(target, param) == false)
                return;
            var def = $.Deferred();
            $.ajax({
                url: data,
                data: param,
                dataType: 'json',
                success: function (data) {
                    _load(data);
                    def.resolve(data);
                },
                error: function () {
                    opts.onLoadError.apply(target, arguments);
                    def.reject();
                }
            });
            return def;
        } else {
            _load(data);
        }

        function _load(data) {
            var form = $(target);
            for (var name in data) {
                var val = data[name];
                var rr = _checkField(name, val);
                // 方法提前，防止直接load input, element造成combo不触发onchange事件
                if (!_loadCombo(name, val)) {
                    if (!rr.length) {
                        var count = _loadOther(name, val);
                        if (!count) {
                            $('input[name="' + name + '"]', form).val(val);
                            $('textarea[name="' + name + '"]', form).val(val);
                            $('select[name="' + name + '"]', form).val(val);
                        }
                    }
                }
            }
            opts.onLoadSuccess.call(target, data);
            validate(target);
        }

        /**
         * check the checkbox and radio fields
         */
        function _checkField(name, val) {
            var rr = $(target).find('input[name="' + name + '"][type=radio], input[name="' + name + '"][type=checkbox]');
            rr._propAttr('checked', false);
            rr.each(function () {
                var f = $(this);
                if (f.val() == String(val) || $.inArray(f.val(), $.isArray(val) ? val : [val]) >= 0) {
                    f._propAttr('checked', true);
                }
            });
            return rr;
        }

        function _loadOther(name, val) {
            var count = 0;
            var pp = ['numberbox', 'slider'];
            for (var i = 0; i < pp.length; i++) {
                var p = pp[i];
                var f = $(target).find('input[' + p + 'Name="' + name + '"]');
                if (f.length) {
                    f[p]('setValue', val);
                    count += f.length;
                }
            }
            return count;
        }

        function _loadExtend(name, val) {
            var result = false;
            $.each($.fn.form.explugins, function (i, type) {
                var plug = $.fn[type];
                var form = $(target);
                if (!result && plug.methods.hasOwnProperty('setValue')) {
                    var editor = form.find(".easyui-" + type);
                    if (editor != undefined && editor.length > 0) {
                        var options = editor[type]('options');
                        if (isNotBlank(options.isNormarl)) {
                            result = false;
                        } else {
                            var field = options.valueFeild;
                            if (isNotBlank(options.inputValueFeild)) {
                                field = options.inputValueFeild;
                            }
                            if (field == name) {
                                editor[type]('setValue', val);
                                result = true;
                            }
                        }
                    }
                }
            });
            return result;
        }

        function _loadCombo(name, val) {
            var result = false;
            if (_loadExtend(name, val)) {
                result = true;
            } else {
                var form = $(target);
                var cc = $.fn.form.plugins;
                var c = form.find('[comboName="' + name + '"]');
                if (c.length) {
                    for (var i = 0; i < cc.length; i++) {
                        var type = cc[i];
                        if (c.hasClass(type + '-f')) {
                            if (c[type]('options').multiple) {
                                c[type]('setValues', val);
                            } else {
                                c[type]('setValue', val);
                            }
                            result = true;
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * clear the form fields
     */
    function clear(target) {
        /** 将该段代码执行提前，防止直接clear input element造成combo不触发onchange事件 * */
        var t = $(target);
        var plugins = ['combo', 'combobox', 'combotree', 'combogrid', 'slider', 'managercity', 'bizCity'];
        for (var i = 0; i < plugins.length; i++) {
            var plugin = plugins[i];
            var r = t.find('.' + plugin + '-f');
            if (r.length && r[plugin]) {
                r[plugin]('clear');
            }
        }
        // validate(target);错误的触发combobox层弹出
        /** -------------------------------end ----------------------* */
        $('input,select,textarea', target).each(
            function () {
                var t = this.type, tag = this.tagName.toLowerCase();
                if (t == 'text' || t == 'hidden' || t == 'password'
                    || tag == 'textarea') {
                    if (this.className != 'disableClear') {
                        this.value = '';
                    }
                } else if (t == 'file') {
                    var file = $(this);
                    var newfile = file.clone().val('');
                    newfile.insertAfter(file);
                    if (file.data('validatebox')) {
                        file.validatebox('destroy');
                        newfile.validatebox();
                    } else {
                        file.remove();
                    }
                } else if (t == 'checkbox' || t == 'radio') {
                    this.checked = false;
                } else if (tag == 'select') {
                    this.selectedIndex = -1;
                }
            });

        // 清空查询精灵
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('clear')) {
                var editor = t.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('clear');// 清空控件
                }
            }
        });
    }

    /**
     * 清空控件QueryParams
     */
    function clearQueryParams(target) {
        var t = $(target);
        // 清空查询精灵
        $.each($.fn.form.explugins, function (i, name) {
            var plug = $.fn[name];
            if (plug.methods.hasOwnProperty('clearQueryParams')) {
                var editor = t.find(".easyui-" + name);
                if (editor != undefined && editor.length > 0) {
                    editor[name]('clearQueryParams');// 清空控件QueryParams
                }
            }
        });
    }

    function reset(target) {
        target.reset();
        var t = $(target);
        var plugins = ['combo', 'combobox', 'combotree', 'combogrid',
            'datebox', 'datetimebox', 'spinner', 'timespinner',
            'numberbox', 'numberspinner', 'slider', 'managercity', 'bizCity'];
        for (var i = 0; i < plugins.length; i++) {
            var plugin = plugins[i];
            var r = t.find('.' + plugin + '-f');
            if (r.length && r[plugin]) {
                r[plugin]('reset');
            }
        }
        validate(target);
    }

    /**
     * set the form to make it can submit with ajax.
     */
    function setForm(target) {
        var options = $.data(target, 'form').options;
        var form = $(target);
        form.unbind('.form').bind('submit.form', function () {
            setTimeout(function () {
                ajaxSubmit(target, options);
            }, 0);
            return false;
        });
    }

    function validate(target) {
        if ($.fn.validatebox) {
            var t = $(target);
            t.find('.validatebox-text:not(:disabled)').validatebox('validate');
            var invalidbox = t.find('.validatebox-invalid');
            invalidbox.filter(':not(:disabled):first').focus();
            return invalidbox.length == 0;
        }
        return true;
    }

    function setValidation(target, novalidate) {
        $(target).find('.validatebox-text:not(:disabled)').validatebox(
            novalidate ? 'disableValidation' : 'enableValidation');
    }

    // var submit = $.fn.form.methods.submit;
    $.fn.form.methods.submit2 = $.fn.form.methods.submit;
// $.fn.form.methods.load = function (jq, data) {
// return load(jq[0], data);
// };
    var _innerGetData = $.fn.form.methods.getData;
    $.fn.form.methods.getData = function (jq, data) {
        $('.easyui-shopCommon').shopCommon('load');
        $('.easyui-itemCommon').itemCommon('load');
        var data = _innerGetData($(jq), data);
        return data;
    };

// $.fn.form.methods.clear = function (jq) {
// return jq.each(function () {
// clear(this);
// });
// };

    $.fn.form.methods.clearQueryParams = function (jq) {
        return jq.each(function () {
            clearQueryParams(this);
        });
    };

    $.fn.form.methods.reset = function (jq) {
        return jq.each(function () {
            reset(this);
        });
    };
    // $.fn.form.methods.submit =
    $.fn.form.methods.post = function (jq, param) {
        return post(jq[0], param);
    };

    $.fn.form.methods.getCombo = function (jq) {
        return _getCombo(jq[0]);
    };

    $.fn.form.methods.disable = function (jq) {
        return _disable(jq[0]);
    };

    $.fn.form.methods.enable = function (jq) {
        return _enable(jq[0]);
    };

    $.fn.form.methods.setDefaultValue = function (jq, data) {
        _setDefaultValue(jq[0], data);
    };

    $.fn.form.explugins = [];

    $.fn.form.plugins = ['combobox', 'combotree', 'combogrid', 'datetimebox',
        'datebox', 'combo'];
})(jQuery);


(function ($) {
    // t.datagrid(val.hidden ? "showColumn" : "hideColumn", val.field);
    var _showColumn = $.fn.datagrid.methods.showColumn;
    var _hideColumn = $.fn.datagrid.methods.hideColumn;

    function getKey(jq) {
        if (window.user != null)
            return window.user.id + ":" + window.location.pathname + ":" + $(jq).attr('id');
        return null;
    }

    function saveColumnsState(jq) {
        var key = getKey(jq);
        if (!key)
            return;
        var config = {};
        var columns = $(jq).datagrid('options').columns;
        $.each(columns, function (i, cols) {
            $.each(cols, function (j, col) {
                    if (col.hidden != null && col.title)
                        config[col.title] = col.hidden;
                }
            );
        });
        window.localStorage.setItem(key, JSON.stringify(config));
    }

    $.fn.datagrid.methods.showColumn = function (jq, field) {
        _showColumn(jq, field);
        saveColumnsState(jq);
    }
    $.fn.datagrid.methods.hideColumn = function (jq, field) {
        _hideColumn(jq, field);
        saveColumnsState(jq);
    }

})(jQuery);

$.fas = {

    filterColumns: function (jq, columns, authority) {
        window.user = authority.getUserInfo();
        var organTypeNo = window.user.organTypeNo;

        function getKey(jq) {
            return window.user.id + ":" + window.location.pathname + ":" + $(jq).attr('id');
        }

        function loadColumnsState(jq) {
            var key = getKey(jq);
            var config = window.localStorage.getItem(key);
            if (!config)
                return {};
            else
                return $.parseJSON(config);
        }

        function filterOrgan(col) {
            var organType = col.organType;
            if (!organType) return;
            var types = organType.split(',');
            col.hidden = true;
            for (var a = 0; a < types.length; a++) {
                if (types[a] == organTypeNo) {
                    col.hidden = false;
                    break;
                }
            }
            if (col.hidden) {
                if ('editor' in col) {
                    if ('options' in col.editor) {
                        if ('required' in col.editor.options) {
                            col.editor.options.required = false;
                        }
                    }
                }
            }
        }

        function reloadColTitle(col) {
            var title = col.title;
            if (!title || title.indexOf(':') < 0) return;
            var vals = title.split(',');
            for (var i = 0; i < vals.length; i++) {
                var ts = vals[i].split(':');
                if (ts[1] == organTypeNo)
                    col.title = ts[0];
            }
        }

        config = loadColumnsState(jq);

        $.each(columns, function (i, cols) {
            $.each(cols, function (j, col) {
                    filterOrgan(col);
                    reloadColTitle(col);
                    if (config.hasOwnProperty(col.title)) {
                        col.hidden = config[col.title];
                    }
                }
            );
        });

    },
    // 扩展$.extend() 方法
    extend: function (Child, Parent) {
        Child.prototype = new Parent();
        Child.prototype.super = Parent.prototype;
        Child.prototype.constructor = Child;
        return Child;
    },
    // 重新加载页脚数据
    reloadDataGridFooter: function (options) {
        var footerRows = $("#" + options.dataGridId).datagrid("getFooterRows");
        if (footerRows && footerRows.length > 0) {
            var oldFieldVal = $.fas.getFieldEditor({
                rowIndex: options.rowIndex,
                dataGridId: options.dataGridId,
                field: options.field
            }).numberbox("getValue");
            oldFieldVal = oldFieldVal ? parseFloat(oldFieldVal) : 0;
            var fieldVal = options.value;
            if (fieldVal == '-') {
                return;
            }
            fieldVal = parseFloat(options.value);
            if (oldFieldVal != fieldVal) {
                var oldTotalVal = footerRows[0][options.field] ? parseFloat(footerRows[0][options.field]) : 0;
                var newTotalVal = parseFloat(oldTotalVal) + fieldVal - oldFieldVal;
                footerRows[0][options.field] = $.fas.floatFixed(newTotalVal, 2);
                $("#" + options.dataGridId).datagrid('reloadFooter');
            }
        }
    },
    showMsg: function (msg, showTime) {
        top.$.messager.show({
            title: '提示消息',
            msg: msg || "操作成功！",
            timeout: 0 || showTime,
            position: 'bottomRight',
            showType: 'slide'
        });
    },
    // 序列化表单数据
    serializableForm: function (formId, rowSize) {
        var formParams = [];
        $("#" + formId + " input[type!='hidden']").each(function () {
            var params = {};
            // 过滤掉combobox和combogrid类型的字段（这种方式获取有问题）
            if ($(this).hasClass('combobox-f')) {
                var fieldName = $(this).attr("comboname");
                params.field = fieldName;
                params.value = $("#" + fieldName).combobox("getText");
                params.name = $(this).parents("td").prev("th").text();
                params.colspan = $(this).parent().attr("colspan");
                params.rowSize = rowSize;
                formParams.push(params);
            } else if ($(this).hasClass('combo-text')) {// 不处理

            } else if ($(this).hasClass('combogrid-f')) {
                var fieldName = $(this).attr("comboname");
                params.field = fieldName;
                params.value = $("#" + fieldName).combogrid("getValue");
                params.name = $(this).parents("td").prev("th").text();
                params.colspan = $(this).parent().attr("colspan");
                params.rowSize = rowSize;
                formParams.push(params);
            } else if ($(this).hasClass('easyui-numberbox')) {
                params.field = $(this).attr("numberboxname");
                params.value = $(this).numberbox("getValue");
                params.name = $(this).parents("td").prev("th").text();
                params.colspan = $(this).parent().attr("colspan");
                params.rowSize = rowSize;
                formParams.push(params);
            } else {
                params.field = $(this).attr("name");
                params.value = $(this).val();
                params.name = $(this).parents("td").prev("th").text();
                params.colspan = $(this).parent().attr("colspan");
                params.rowSize = rowSize;
                formParams.push(params);
            }
        });
        return formParams;
    },
    // 增加tip提示
    tooltip: function (options) {
        $("#" + options.id).tooltip({
            position: 'right',
            content: '<span>' + options.content + '</span>',
            onShow: function () {
                $(this).tooltip('tip').css({
                    borderColor: '#666',
                    backgroundColor: '#B0C4DE'
                });
            }
        });
    },
    formatFieldValue: function (paramMap) {
        // 将json格式的字段转换成json格式的值，如{name:'companyNo',
        // field:'companyNo'},转换后的值为{companyNo:$("#companyNo).val()}
        var fieldValues = {};
        if (typeof paramMap == 'undefined' && paramMap == null) {
            return fieldValues;
        }
        $.each(paramMap, function (index, item) {
            fieldValues[item.name] = $("#" + item.field).val();
        });
        return fieldValues;
    },
    // 保留小数位数
    floatFixed: function (value, length) {
        length = length ? length : 2;
        if (typeof value !== 'number') {
            return parseFloat(value).toFixed(length);
        } else {
            return value.toFixed(length)
        }
    },
    // 公共ajax请求（不是异步，ajax方法执行完之后，再执行后面的js的代码）
    ajaxRequestAsync: function (url, reqParam, callback) {
        var returlVal = "";
        $.ajax({
            type: 'POST',
            url: url,
            data: reqParam,
            cache: true,
            async: false,
            dataType: 'json',
            success: function (data) {
                returlVal = callback(data);
            }
        });
        return returlVal;
    },
    // 查询方法
    search: function (options) {
        var defaults = {
            searchFormId: "searchForm",
            dataGridId: "dataGridDiv",
            searchUrl: "",
            hasSearchForm: true,
            loadType: "reload"
        };
        options = $.extend(defaults, options);
        if (options.hasSearchForm) {
            var valid = $("#" + options.searchFormId).form('validate');
            if (valid == false) {
                return;
            }

            var reqParam = $("#" + options.searchFormId).form("getData");
            var item = $('#' + defaults.searchFormId).find('.easyui-itemCommon');
            if ($.isFunction(item.filterbuilder)) {
                var itemObj = item.filterbuilder('getValue');
                if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 1) {
                    reqParam.itemNo = "";
                    reqParam.itemSql = itemObj.codeList;
                } else if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 2) {
                    reqParam.itemNo = itemObj.codeList;
                } else {
                    reqParam.itemNo = itemObj;
                }
            }


            // 组装请求参数
            $("#" + options.dataGridId).datagrid('options').queryParams = reqParam;
        }
        var queryMxURL = BasePath + options.searchUrl;
        $("#" + options.dataGridId).datagrid('options').url = queryMxURL;
        // Reload the rows. Same as the 'load' method but stay on current page.
        $("#" + options.dataGridId).datagrid(options.loadType);
// if(params.checkboxIndex) {
// $(":checkbox:eq("+(params.checkboxIndex + 1)+")").attr("checked", false);
// }
    },
    // 清空
    clear: function (formId) {
        formId = formId || "searchForm";
        $("#" + formId).form("clear");
        $("#" + formId).find("input").val("");
        $("#" + formId).find("textarea").val("");
    },
    // 弹出框新增页面
    ygDialogAdd: function (options) {
        if ((document.documentElement.clientHeight - 60) < options.dialogHeight) {
            options.dialogHeight = document.documentElement.clientHeight - 60;
        }
        // 如果需要在新增操作时，进行一些其他的逻辑处理，可自定义extra_operate.initAdd函数
        if (typeof options.initAdd === 'function') {
            options.initAdd();
        }
        ygDialog({
            title: '新增',
            target: $('#myFormPanel'),
            width: options.dialogWidth,
            height: options.dialogHeight,
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: function (dialog) {
                    if (typeof options.saveFunction === 'function') {
                        options.saveFunction(options.obj);
                    }
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function (dialog) {
                    dialog.close();
                }
            }]
        });
        if (typeof options.addDataFormClass === 'function') {
            options.addDataFormClass(options.dataFormId);
        }
        if (typeof options.loadedAdd === 'function') {
            options.loadedAdd();
        }
    },
    // 弹出框修改页面
    ygDialogUpdate: function (options) {
        if ((document.documentElement.clientHeight - 60) < options.dialogHeight) {
            options.dialogHeight = document.documentElement.clientHeight - 60;
        }
        var rowData = options.rowData;
        if (!rowData) {
            // 获取所有勾选checkbox的行
            var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");
            if (checkedRows.length < 1) {
                showWarn('请选择要修改的记录!', 1);
                return;
            }
            if (checkedRows.length > 1) {
                showWarn('只能选择一条记录进行修改!');
                return;
            }
            rowData = rowData || checkedRows[0];
        }
        if (typeof options.initUpdate === 'function') {
            options.initUpdate();
        }
        if (typeof options.addDataFormClass === 'function') {
            options.addDataFormClass();
        }
        var initUpdateFlag = true;
        if (typeof options.checkInitUpdate === 'function') {
            initUpdateFlag = options.checkInitUpdate(rowData);
        }
        if (!initUpdateFlag) {
            return;
        }
        var checkUpdateField = options.checkUpdateField ? options.checkUpdateField : "status";
        if ((rowData.updateCheck && rowData.updateCheck == 'true') || ( rowData[checkUpdateField] && rowData[checkUpdateField] == '1')) {
            var dataForm = $('#' + options.dataFormId + 'View').length > 0 ? $('#' + options.dataFormId + 'View') : $('#' + options.dataFormId);
            var panel = $('#myFormPanelView').length > 0 ? $('#myFormPanelView') : $('#myFormPanel');
            dataForm.form('load', rowData);
            ygDialog({
                title: '查看',
                target: panel,
                width: options.dialogWidth,
                height: options.dialogHeight,
                buttons: [{
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function (dialog) {
                        dialog.close();
                    }
                }]
            });
            // 获取工具栏按钮
            var linkbuttons = $("#dtltoolbarView").find("a");
            for (var i = 0; i < linkbuttons.length; i++) {
                $(linkbuttons[i]).linkbutton('disable');
            }
        } else {
            $('#' + options.dataFormId).form('load', rowData);
            ygDialog({
                title: '修改',
                target: $('#myFormPanel'),
                width: options.dialogWidth,
                height: options.dialogHeight,
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function (dialog) {
                        options.updateFunction(options.obj);
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function (dialog) {
                        dialog.close();
                    }
                }]
            });
        }
        if (typeof options.loadedUpdate === 'function') {
            options.loadedUpdate(rowData);
        }
    },
    // 新增操作
    add: function (options) {
        var url = BasePath + options.url;
        var formObj = $("#" + options.dataFormId);
        var validateFlag = true, addFlag = true;
        if (typeof options.validateForm === 'function') {
            validateFlag = options.validateForm(formObj);
        }
        if (!validateFlag) {
            return;
        }
        if (typeof options.checkAdd === 'function') {
            addFlag = options.checkAdd();
        }
        if (!addFlag) {
            return;
        }
        formObj.form('submit', {
            url: url,
            dataType: 'json',
            onSubmit: function (extraParams) {
                if (typeof options.buildAddSubmitParams === 'function') {
                    var submitParams = options.buildAddSubmitParams();
                    if (submitParams != null && submitParams.length > 0) {
                        for (var i = 0; i < submitParams.length; i++) {
                            var paramName = submitParams[i].name;
                            extraParams[paramName] = submitParams[i].value;
                        }
                    }
                }
            },
            success: function (result) {
                options.successAddFn(result, options);
            },
            error: function () {
                showError('新增失败,请联系管理员!');
            }
        });
    },
    // 修改操作
    update: function (options) {
        var url = BasePath + options.url;
        var formObj = $("#" + options.dataFormId);
        var validateFlag = true, updateFlag = true;
        if (typeof options.validateForm === 'function') {
            validateFlag = options.validateForm(formObj);
        }
        if (!validateFlag) {
            return;
        }
        if (typeof options.checkUpdate === 'function') {
            updateFlag = options.checkUpdate();
        }
        if (!updateFlag) {
            return;
        }
        formObj.form('submit', {
            url: url,
            dataType: 'json',
            onSubmit: function (extraParams) {
                if (typeof options.buildUpdateSubmitParams === 'function') {
                    var submitParams = options.buildUpdateSubmitParams();
                    if (submitParams != null && submitParams.length > 0) {
                        for (var i = 0; i < submitParams.length; i++) {
                            var paramName = submitParams[i].name;
                            extraParams[paramName] = submitParams[i].value;
                        }
                    }
                }
            },
            success: function (result) {
                options.successUpdateFn(result, options);
            },
            error: function () {
                showError('修改失败,请联系管理员!');
            }
        });
    },
    // 删除
    del: function (options) {
        var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要删除的记录!");
            return;
        }
        var url = BasePath + options.url, delFlag = true, deleteLen = "";
        // 校验数据是否可删除
        if (typeof options.checkDel === 'function') {
            delFlag = options.checkDel(checkedRows);
        }
        if (!delFlag) {
            return;
        }
        if (typeof checkedRows != "undefined" && checkedRows != null) {
            deleteLen = checkedRows.length;
        }
        $.messager.confirm("确认", "你确定要删除这" + deleteLen + "条数据", function (r) {
            if (r) {
                // 如果需要自己组装删除数据，则可自定义
                var deleteList = options.buildDelData(checkedRows);
                if (deleteList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(deleteList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.delCallback(options, result);
                    });
                } else {
                    showError("删除失败!");
                }
            }
        });
    },
    // 删除单据
    delBill: function (options) {
        var url = BasePath + options.url, delFlag = true, deleteLen = "";
        // 校验数据是否可删除
        if (typeof options.checkDel === 'function') {
            delFlag = options.checkDel(options);
        }
        if (!delFlag) {
            return;
        }
        $.messager.confirm("确认", "你确定要删除这条数据", function (r) {
            if (r) {
                // 如果需要自己组装删除数据，则可自定义
                var deleteList = options.buildDelData(options);
                if (deleteList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(deleteList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.delCallback(options, result);
                    });
                } else {
                    showError("删除失败!");
                }
            }
        });
    },
    // 启用
    enable: function (options) {
        var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要启用的记录!");
            return;
        }
        var url = BasePath + options.url, enableFlag = true, enableLen = "";
        // 校验数据是否可删除
        if (typeof options.checkEnable === 'function') {
            enableFlag = options.checkEnable(checkedRows);
        }
        // 校验数据是否可激活
        if (!enableFlag) {
            return;
        }
        if (typeof checkedRows != "undefined" && checkedRows != null) {
            enableLen = checkedRows.length;
        }
        $.messager.confirm("确认", "你确定要启用这" + enableLen + "条数据", function (r) {
            if (r) {
                var enableList = options.buildEnableData(checkedRows);
                if (enableList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(enableList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.enableCallback(options, result);
                    });
                } else {
                    showError("操作失败!");
                }
            }
        });
    },
    // 停用
    unable: function (options) {
        var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要停用的记录!");
            return;
        }
        var url = BasePath + options.url, unableFlag = true, unableLen = "";
        // 校验数据是否可删除
        if (typeof options.checkUnable === 'function') {
            unableFlag = options.checkUnable(checkedRows);
        }
        // 校验数据是否可注销
        if (!unableFlag) {
            return;
        }
        if (typeof checkedRows != "undefined" && checkedRows != null) {
            unableLen = checkedRows.length;
        }
        $.messager.confirm("确认", "你确定要停用这" + unableLen + "条数据", function (r) {
            if (r) {
                var unableList = options.buildUnableData(checkedRows);
                if (unableList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(unableList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.unableCallback(result, options);
                    });
                } else {
                    showError("操作失败!");
                }
            }
        });
    },
    // 审核
    audit: function (options) {
        var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要审核的记录!");
            return;
        }
        var url = BasePath + options.url, auditFlag = true, auditLen = "";
        // 校验数据是否可审核
        if (typeof options.checkAudit === 'function') {
            auditFlag = options.checkAudit(checkedRows);
        }
        // 校验数据是否可审核
        if (!auditFlag) {
            return;
        }
        if (typeof checkedRows != "undefined" && checkedRows != null) {
            auditLen = checkedRows.length;
        }
        $.messager.confirm("确认", "你确定要审核这" + auditLen + "条数据", function (r) {
            if (r) {
                var auditList = options.buildAuditData(checkedRows);
                if (auditList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(auditList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.auditCallback(result, options);
                    });
                } else {
                    showError("操作失败!");
                }
            }
        });
    },
    // 反审核
    antiAudit: function (options) {
        var checkedRows = $("#" + options.dataGridId).datagrid("getChecked");// 获取所有勾选checkbox的行
        if (checkedRows.length < 1) {
            showWarn("请选择要反审核的记录!");
            return;
        }
        var url = BasePath + options.url, antiAuditFlag = true, antiAuditLen = "";
        // 校验数据是否可反审核
        if (typeof options.checkAntiAudit === 'function') {
            antiAuditFlag = options.checkAntiAudit(checkedRows);
        }
        // 校验数据是否可反审核
        if (!antiAuditFlag) {
            return;
        }
        if (typeof checkedRows != "undefined" && checkedRows != null) {
            antiAuditLen = checkedRows.length;
        }
        $.messager.confirm("确认", "你确定要反审核这" + antiAuditLen + "条数据", function (r) {
            if (r) {
                var antiAuditList = options.buildAntiAuditData(checkedRows);
                if (antiAuditList.length > 0) {
                    var effectRow = new Object();
                    effectRow["deleted"] = JSON.stringify(antiAuditList);
                    ajaxRequest(url, effectRow, function (result) {
                        options.antiAuditCallback(result, options);
                    });
                } else {
                    showError("操作失败!");
                }
            }
        });
    },
    // 导出
    exportExcel: function (options) {
        var defaults = {
            searchFormId: "searchForm",
            dataGridId: "dataGridDiv",
            exportUrl: "/do_fas_export",
            exportTitle: "导出",
            exportType: "common",
            async: false
        };
        options = $.extend(defaults, options);
        var $dg = $("#" + options.dataGridId);
        var queryParams = $dg.datagrid('options').queryParams;
        var grepColumns = $dg.datagrid('options').columns;
        var subGrepColumns = $dg.datagrid('options').subColumns;
        var Fcolumns = $dg.datagrid('options').frozenColumns;
        var count = 0;
        if ($dg.datagrid('getData'))
            count = $dg.datagrid('getData').total;
        // 添加冻结列
        if (Fcolumns.length > 0) {
            for (var i = Fcolumns[0].length - 1; i >= 0; i--) {
                if (!Fcolumns[0][i]['expander']) {
                    grepColumns[0].unshift(Fcolumns[0][i]);
                }
            }
        }
        // 添加冻结列(第二行) 参照期间结存排版
        if (Fcolumns.length > 1) {
            for (var i = Fcolumns[1].length - 1; i >= 0; i--) {
                if (!Fcolumns[1][i]['expander']) {
                    grepColumns[1].unshift(Fcolumns[1][i]);
                }
            }
        }

        var columns = [], firstHeaderColumns = [];
        if (grepColumns && grepColumns.length > 1) {
            columns = $.grep(grepColumns[1], function (o, i) {
                if ($(o).attr("notexport") == true) {
                    return true;
                }
                return false;
            }, true);
            firstHeaderColumns = $.grep(grepColumns[0], function (o, i) {
                if ($(o).attr("notexport") == true) {
                    return true;
                }
                return false;
            }, true);
        } else {
            columns = $.grep(grepColumns[0], function (o, i) {
                if ($(o).attr("notexport") == true) {
                    return true;
                }
                return false;
            }, true);
        }

        // 移除冻结列
        if (Fcolumns.length > 0) {
            for (var i = Fcolumns[0].length - 1; i >= 0; i--) {
                if (!Fcolumns[0][i]['expander']) {
                    grepColumns[0].splice($.inArray(Fcolumns[0][i], grepColumns[0]), 1);
                }
            }
        }
        // 移除冻结列(第二行)
        if (Fcolumns.length > 1) {
            for (var i = Fcolumns[1].length - 1; i >= 0; i--) {
                if (!Fcolumns[1][i]['expander']) {
                    grepColumns[1].splice($.inArray(Fcolumns[1][i], grepColumns[1]), 1);
                }
            }
        }

        // 获取排序字段，由于sortName只能获取field字段，所以需要转换
        var sortName = $dg.datagrid('options').sortName;
        var sortField = "", sortOrder = $dg.datagrid('options').sortOrder;
        if (sortName && columns) {
            for (var i = 0; i < columns.length; i++) {
                if (sortName == columns[i].field) {
                    sortField = columns[i].sortField;
                    break;
                }
            }
        }
        var subColumns = [];
        if (typeof subGrepColumns != 'undefined'
            && subGrepColumns != null
            && subGrepColumns != "") {
            subColumns = $.grep(subGrepColumns[0], function (o, i) {
                if ($(o).attr("notexport") == true) {
                    return true;
                }
                return false;
            }, true);
        }
        var exportColumns = JSON.stringify(columns);
        var exportSubColumns = JSON.stringify(subColumns);
        var dataRow = $dg.datagrid('getRows');
        var ps = $.extend({}, queryParams);
        ps.exportColumns = exportColumns;
        ps.exportSubColumns = exportSubColumns;
        ps.firstHeaderColumns = JSON.stringify(firstHeaderColumns);
        ps.fileName = options.exportTitle;
        ps.exportType = options.exportType;
        ps.orderByField = sortField;
        ps.orderBy = sortOrder;
        ps.count = count;
        // if(queryParams != null && queryParams != {}) {
        // $.each(queryParams, function(i) {
        // params[i] = queryParams[i];
        // });
        // }
        if (dataRow.length == 0) {
            showWarn('查询记录为空，不能导出!');
            return;
        }
        if (!options.async) {
            $("#exportExcelForm").remove();
            $("<form id='exportExcelForm' method='post'></form>").appendTo("body");
            $('#exportExcelForm').form('submit', {
                url: BasePath + options.exportUrl,
                onSubmit: function (params) {
                    $.extend(params, ps);
                }
            });
        }
        else {
            var panel = new $.fas.ExportPanel(options, ps);
            panel.open();
        }

    },
    editIndex: undefined,
    // 新增行
    addEditorRow: function (options) {
        var defaults = {
            dataGridId: "dtlDataGridDiv",
            initRow: {},
            comboboxData: {}
        };
        options = $.extend(defaults, options);
        $("#" + options.dataGridId).addEditorRow(options);
        // 光标定位到第一个编辑框
        var firstEditorId = $.fas.getFirstEditorId(options.dataGridId);
        if (firstEditorId) {
            setFocus($("#" + firstEditorId));
        }
    },
    // 修改行
    editEditorRow: function (options) {
        var defaults = {
            dataGridId: "dtlDataGridDiv",
            rowIndex: 0,
            row: {},
            initRow: {}
        };
        options = $.extend(defaults, options);
        $("#" + options.dataGridId).editEditorRow(options);
        // 光标定位到第一个编辑框
// var firstEditorId = $.fas.getFirstEditorId(options.dataGridId);
// if(firstEditorId) {
// setFocus($("#" + firstEditorId));
// }
    },
    // 删除行
    deleteEditorRow: function (options) {
        var defaults = {
            dataGridId: "dtlDataGridDiv",
            checkDel: function (checkedRows) {
                return true;
            }
        };
        options = $.extend(defaults, options);
        $("#" + options.dataGridId).deleteEditorRow(options);
    },
    // 结束行编辑
    endEditing: function (dataGridId) {
        dataGridId = dataGridId || 'dtlDataGridDiv';
        var tempObj = $('#' + dataGridId);
        tempObj.datagrid("unselectAll");
        var rowArr = tempObj.datagrid('getRows');
        for (var i = 0; i < rowArr.length; i++) {
            if (tempObj.datagrid('validateRow', i)) {
                tempObj.datagrid('endEdit', i);
            } else {
                return false;
            }
        }
        return true;
    },
    getFieldEditor: function (options) {
        var rowIndex = options.rowIndex || $.fas.editIndex;
        var editor = $("#" + options.dataGridId).datagrid('getEditor', {
            'index': rowIndex,
            'field': options.field
        });
        return $(editor.target);
    },
    // 获取editor的值
    getEditorVal: function (options) {
        var rowIndex = options.rowIndex || $.fas.editIndex;
        var editor = $("#" + options.dataGridId).datagrid('getEditor', {
            'index': rowIndex,
            'field': options.field
        });
        var editorVal = "";
        var target = editor.target;
        var ed = $.fn.datagrid.defaults.editors[editor.type];
        if (ed) {
            editorVal = ed.getValue(target, options.field);
        }
        return editorVal;
    },
    // 设置editor的值
    setEditorVal: function (options) {
        var rowIndex = options.rowIndex || $.fas.editIndex;
        var editor = $("#" + options.dataGridId).datagrid('getEditor', {
            'index': rowIndex,
            'field': options.field
        });
        if (editor) {
            if (options.dataType === 'numberbox') {
                $(editor.target).numberbox("setValue", options.value);
            } else if (options.dataType === 'combogrid') {
                $(editor.target).combogrid("setValue", options.value);
            } else if (options.dataType === 'combobox') {
                $(editor.target).combobox("setValue", options.value);
            } else if (options.dataType === 'datebox') {
                $(editor.target).datebox("setValue", options.value);
            } else {
                $(editor.target).val(options.value);
            }
        }
    },
    // 校验行数据是否重复
    checkRowUnique: function (options) {
        var defaults = {
            dataGridId: "dtlDataGridDiv",
            uniqueField: ""
        };
        options = $.extend(defaults, options);
        var rows = $("#" + options.dataGridId).datagrid("getRows");
        if (rows.length == 0) {
            return true;
        }
        var fieldNos = [];
        $.each(rows, function (index, row) {
            fieldNos.push(row[options.uniqueField] + '');
        });
        var fieldNoLen = fieldNos.length;
        $.unique(fieldNos);
        var uniqueFieldNoLen = fieldNos.length;
        if (fieldNoLen != uniqueFieldNoLen) {
            showWarn("有重复数据，请修改后再保存！");
            return false;
        }
        return true;
    },
    getFirstEditorId: function (dataGridId) {
        var grepColumns = $("#" + dataGridId).datagrid('options').columns;
        var columns = $.grep(grepColumns[0], function (o, i) {
            if ($(o).attr("checkbox") == true ||
                ($(o).attr("hidden") == true || $(o).attr("hidden") == 'true')) {
                return true;
            }
            return false;
        }, true);
        var editorId = "";
        if (columns[0].editor && columns[0].editor.options) {
            editorId = columns[0].editor.options.id;
        }
        if (!editorId) {
            return "";
        }
        return editorId;
    }
};
// jquery对象级插件
(function ($) {
    // 插入一行数据
    $.fn["addEditorRow"] = function (options) {
        var defaults = {
            initRow: {},
            comboboxData: {},
            buildAddData: {}
        };
        options = $.extend(defaults, options);
        this.each(function () {
            var _this = $(this);
            var dataGridId = options.dataGridId;
            if (endEditing(dataGridId)) {
                _this.siblings("div[class='datagrid-empty-msg']").hide();
                var rowLen = _this.datagrid('getRows').length;
                if (typeof rowLen == 'undefined' || rowLen < 0) {
                    rowLen = 0;
                }
                if (typeof options.beforeAdd === 'function') {
                    options.beforeAdd(rowLen);
                }
                var rowObj = options.rowData || options.initRow;
                if (typeof options.buildAddData === 'function') {
                    rowObj = options.buildAddData(rowLen);
                }
                $.fas.editIndex = rowLen;
                _this.datagrid('insertRow', {
                    index: rowLen,
                    row: rowObj
                });
                _this.datagrid('selectRow', rowLen);
                _this.datagrid('beginEdit', rowLen);
                if (typeof options.afterAdd === 'function') {
                    options.afterAdd(rowLen);
                }
            }
        });
        return this;
    };

    // 修改一行数据
    $.fn["editEditorRow"] = function (options) {
        var defaults = {
            rowIndex: 0,
            row: {},
            initRow: {},
            beforeUpdate: function (rowIndex, row) {
            },
            afterUpdate: function (rowIndex, row) {
            },
            buildUpdateData: {}
        };
        options = $.extend(defaults, options);
        this.each(function () {
            var _this = $(this);
            var dataGridId = _this.attr("id");
            if (endEditing(dataGridId)) {
                options.beforeUpdate(options.rowIndex, options.row);
                $.fas.editIndex = options.rowIndex;
                var rowObj = options.initRow;
                if (typeof options.buildUpdateData === 'function') {
                    rowObj = options.buildUpdateData(options.rowIndex, options.row);
                }
                _this.datagrid('updateRow', {
                    index: options.rowIndex,
                    row: rowObj
                });
                _this.datagrid('clearSelections');
                _this.datagrid('selectRow', options.rowIndex);
                _this.datagrid('beginEdit', options.rowIndex);
                if (typeof options.afterUpdate === 'function') {
                    options.afterUpdate(options.rowIndex, options.row);
                }
            }
        });
        return this;
    };

    // 删除一行数据
    $.fn["deleteEditorRow"] = function (options) {
        var defaults = {
            checkDel: function (checkedRows) {
                return true;
            }
        };
        options = $.extend(defaults, options);
        this.each(function () {
            var _this = $(this);
            var checkedRows = _this.datagrid('getChecked');
            if (!options.checkDel(checkedRows)) {
                return;
            }
            $.each(checkedRows, function (index, row) {
                var rowIndex = _this.datagrid('getRowIndex', row);
                _this.datagrid('deleteRow', rowIndex);
                if (undefined != $.fas.editIndex && $.fas.editIndex == rowIndex) {
                    editIndex = undefined;
                }
            });
        });
        return this;
    };

    // 结束编辑
    $.fn["endEditing"] = function () {
        this.each(function () {
            var dataGridId = $(this).attr("id");
            return endEditing(dataGridId);
        });
        return this;
    };

    function endEditing(dataGridId) {
        var tempObj = $('#' + dataGridId);
        var rowArr = tempObj.datagrid('getRows');
        tempObj.datagrid("unselectAll");
        for (var i = 0; rowArr && i < rowArr.length; i++) {
            if (tempObj.datagrid('validateRow', i)) {
                tempObj.datagrid('endEdit', i);
            } else {
                tempObj.datagrid('selectRow', i);
                return false;
            }
        }
        return true;
    };
})(jQuery);

/**
 * *********************************************** 表单验证
 * *******************************************************
 */
$.extend($.fn.validatebox.defaults.rules, {
    unNormalData: {
        validator: function (value, param) {
// return /^[-+]?\$/.test(value);
            return /^[a-zA-z0-9\u4E00-\u9FA5]*$/.test(value);
        },
        message: '不能输入空格和非法字符'
    },
    decimalData: {
        validator: function (value, param) {
            return /^(0(\.\d+)?|1)$/.test(value) || (value <= 1);
        },
        message: '只能输入0到1之间的小数'
    }
});

/**
 * ****************************************** 扩展easyui editor
 * *************************************************
 */
$.extend(
    $.fn.datagrid.defaults.editors, {
        // 时间控件
        fasdatetimebox: {
            init: function (container, options) {
                var defaults = {
                    id: '',
                    name: '',
                    width: 78,
                    dateFmt: 'yyyy-MM-dd',
                    required: false,
                    blur: function () {

                    }
                };
                options = $.extend(defaults, options);
                var id = options.id, name = options.name;
                var datebox = $("<input type='text' name='" + name + "' id='" + id + "'  />");
                datebox = datebox.appendTo(container);
                $("#" + id).addClass("easyui-datebox");
                $("#" + id).datebox({
                    required: options.required,
                    dateFmt: options.dateFmt,
                    panelWidth: options.width
                });
                $("#" + id).on("blur", options.blur);
                return datebox;
            },
            destroy: function (target) {
                $(target).datebox("destroy");
            },
            getValue: function (target) {
                return $(target).datebox("getValue");
            },
            setValue: function (target, value) {
                $(target).datebox("setValue", value);
            },
            resize: function (target, width) {
                $(target).datebox("resize", width);
            }
        },
        fasdatebox: {
            init: function (container, options) {
                var defaults = {
                    id: '',
                    name: '',
                    width: 78,
                    dateFmt: 'yyyyMM',
                    required: false,
                    blur: function () {

                    }
                };
                options = $.extend(defaults, options);
                var id = options.id, name = options.name;
                var datebox = $("<input type='text' name='" + name + "' id='" + id + "'  />");
                datebox = datebox.appendTo(container);
                $("#" + id).addClass("easyui-datebox");
                $("#" + id).datebox({
                    required: options.required,
                    dateFmt: options.dateFmt,
                    panelWidth: options.width
                });
                $("#" + id).on("blur", options.blur);
                return datebox;
            },
            destroy: function (target) {
                $(target).datebox("destroy");
            },
            getValue: function (target) {
                return $(target).datebox("getValue");
            },
            setValue: function (target, value) {
                $(target).datebox("setValue", value);
            },
            resize: function (target, width) {
                $(target).datebox("resize", width);
            }
        },
        // 可编辑文本的editor
        fastextbox: {
            init: function (container, options) {
                var id = options.id, name = options.name, width = options.width || 140;
                var textbox = $("<input type='text' name='" + name + "' class='ipt' id='" + id + "' width='" + width + "'/>");
                textbox = textbox.appendTo(container);
                textbox.validatebox(options);
                return textbox;
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // 可编辑number的editor
        fasnumberbox: {
            init: function (container, options) {
                var id = options.id, name = options.name, width = options.width || 140;
                var textbox = $("<input type='text' name='" + name + "' class='ipt' id='" + id + "' width='" + width + "'/>");
                textbox = textbox.appendTo(container);
                textbox.numberbox(options);
                return textbox;
            },
            getValue: function (target) {
                return $(target).numberbox("getValue");
            },
            setValue: function (target, value) {
                $(target).numberbox("setValue", value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // combogrid editor
        combogrid: {
            init: function (container, options) {
                var defaults = {
                    id: "",
                    name: "",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: '',//
                    textField: '',// 数据表中的name
                    noField: '', // 数据表中的no字段
                    inputNoField: '',// 输入字段编码
                    url: "",
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[]],
                    paramMap: []
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            options.callback(row);
                        }
                    };
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).combogrid("setValue", row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    };
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        var params = {};
                        $.each(options.paramMap, function (index, item) {
                            if (item.dataType == 'date') {

                            } else {
                                params[item.name] = $("#" + item.field).val();
                            }
                        });
                        params = $.extend(params, options.queryParams);
                        $("#" + options.id).combogrid("grid").datagrid('reload', params);// 需要重载一遍datagrid
                    };
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 搜索框editor
        searchbox: {
            init: function (container, options) {
                var id = options.id, name = options.name, textId = options.textId;
                var valueField = options.valueField, textField = options.textField;
                var idbox = $("<input type='text' name='" + name + "' class='ipt' id='" + id + "' />").appendTo(container);
                idbox.validatebox(options);
                var isFrame = true;
                if (typeof options.isFrame != 'undefined') {
                    isFrame = options.isFrame;
                }
                $("#" + id).initIptSearch({
                    title: options.title,
                    href: BasePath + options.url,
                    queryUrl: BasePath + options.queryUrl,
                    inputWidth: options.width || 140,
                    height: options.height || 500,
                    isFrame: isFrame,
                    fn: options.callback || function (data) {
                        $("#" + id).val(data[valueField]);
                        $("#" + textId).val(data[textField]);
                    }
                });
                return idbox;
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // 只读文本editor
        readonlybox: {
            init: function (container, options) {
                var id = options.id, name = options.name, width = options.width || 140;
                var namebox = $("<input type='text' name='" + name + "' style='width:" + width + "' class='disabled ipt' id='" + id + "' readonly='true'/>");
                namebox = namebox.appendTo(container);
                return namebox;
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // 只读文本editor
        searchboxname: {
            init: function (container, options) {
                var id = options.id, name = options.name, readonly = options.readonly, width = options.width || 140;
                var namebox = "";
                if (typeof readonly != 'undefined' && readonly) {
                    namebox = $("<input type='text' name='" + name + "' style='width:" + width + "' class='disabled ipt' id='" + id + "' readonly='" + readonly + "'/>");
                } else {
                    namebox = $("<input type='text' name='" + name + "' style='width:" + width + "' class='ipt' id='" + id + "'/>");
                }
                namebox = namebox.appendTo(container);
                return namebox;
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // 隐藏文本editor
        hiddenbox: {
            init: function (container, options) {
                var id = options.id, name = options.name;
                var hiddenbox = $("<input type='text' name='" + name + "' class='ipt' id='" + id + "' hidden='true'/>");
                hiddenbox = hiddenbox.appendTo(container);
                return hiddenbox;
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
            },
            resize: function (target, width) {
                $(target)._outerWidth(width)._outerHeight(22);
                $(target).parent()._outerWidth(width);
            }
        },
        // 下拉框editor
        fascombobox: {
            init: function (container, options) {
                var defaults = {
                    id: "",
                    name: "",
                    valueField: "value",
                    textField: "text",
                    url: "",
                    width: 140
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' id='" + options.id + "' />").appendTo(container);
                idbox.validatebox(options);
                if (!options.onSelect) {
                    options.onSelect = function (data) {
                        $("#" + options.id).val(data[options.valueField]);
                    }
                }
                $("#" + options.id).initCombox(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combobox("destroy");
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
                $(target).combobox("setValue", value);
            }
        },
        // 大类editor
        category: {
            init: function (container, options) {
                var defaults = {
                    id: "categoryName",
                    name: "categoryName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'categoryNo',
                    inputNoField: 'categoryNo',// 输入字段编码
                    url: BasePath + '/category/list.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    filter: function (q, row) {
                        var opts = $(this).combogrid('options');
                        return row[opts.idField].indexOf(q) == 0;
                    },
                    columns: [[
                        {field: 'categoryNo', title: '大类编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'name', title: '大类名称', width: 180, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid("destroy");
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 大类下拉editor
        categorycombobox: {
            init: function (container, options) {
                var defaults = {
                    id: "categoryNo",
                    name: "categoryNo",
                    valueField: "categoryNo",
                    textField: "name",
                    url: BasePath + "/category/get_biz.json?levelid=1",
                    inputWidth: 80
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' id='" + options.id + "' />").appendTo(container);
                idbox.validatebox(options);
                if (options && !options.onSelect) {
                    options.onSelect = function (data) {
                        $('#categoryNo').val(data[options.valueField]);
                        $('#categoryName').val(data[options.textField]);
                    }
                }
                $("#" + options.id).initCombox(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combobox("destroy");
            },
            getValue: function (target) {
                return $(target).val();
            },
            setValue: function (target, value) {
                $(target).val(value);
                $(target).combobox("setValue", value);
            }
        },
        // 品牌editor
        brand: {
            init: function (container, options) {
                var defaults = {
                    id: "brandName",
                    name: "brandName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'brandNo',
                    inputNoField: 'brandNo',// 输入字段编码
                    url: BasePath + '/brand/list.json?status=1',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'brandNo', title: '品牌编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'name', title: '品牌名称', width: 150, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 品牌部editor
        brandunit: {
            init: function (container, options) {
                var defaults = {
                    id: "brandUnitName",
                    name: "brandUnitName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'brandUnitNo',
                    inputNoField: 'brandUnitNo',// 输入字段编码
                    url: BasePath + '/brand_unit/list.json?status=1',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'brandUnitNo', title: '品牌部编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'name', title: '品牌部名称', width: 150, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 商品editor
        item: {
            init: function (container, options) {
                var defaults = {
                    id: "itemName",
                    name: "itemName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'itemNo',
                    inputNoField: 'itemNo',// 输入字段编码
                    url: BasePath + '/base_setting/item/list.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'itemNo', title: '商品编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'name', title: '商品名称', width: 150, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 供应商editor
        supplier: {
            init: function (container, options) {
                var defaults = {
                    id: "supplierName",
                    name: "supplierName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'shortName',//
                    textField: 'shortName',// 数据表中的name
                    noField: 'supplierNo',
                    inputNoField: 'supplierNo',// 输入字段编码
                    url: BasePath + '/base_setting/supplier/list.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'supplierNo', title: '供应商编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'shortName', title: '供应商简称', width: 200, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 供应商组editor
        suppliergroup: {
            init: function (container, options) {
                var defaults = {
                    id: "supplierGroupName",
                    name: "supplierGroupName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'groupName',//
                    textField: 'groupName',// 数据表中的name
                    noField: 'groupNo',
                    inputNoField: 'supplierGroupNo',// 输入字段编码
                    url: BasePath + '/supplier_group/list.json?auditStatus=1',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'groupNo', title: '供应商组编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'groupName', title: '供应商组名称', width: 200, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 结算公司editor
        company: {
            init: function (container, options) {
                var defaults = {
                    id: "companyName",
                    name: "companyName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'companyNo',
                    inputNoField: 'companyNo',// 输入字段编码
                    url: BasePath + '/base_setting/company/list.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'companyNo', title: '公司编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'name', title: '公司名称', width: 200, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 店铺editor
        shop: {
            init: function (container, options) {
                var defaults = {
                    id: "shortName",
                    name: "shortName",
                    inputWidth: 160,
                    panelWidth: 400,
                    panelHeight: 200,
                    idField: 'shortName',//
                    textField: 'shortName',// 数据表中的name
                    noField: 'shopNo',
                    inputNoField: 'shopNo',// 输入字段编码
                    url: '',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    relationData: false, // 是否关联公司等数据
                    filterCompany: '',
                    filterMall: '',
                    columns: [[
                        {field: 'shopNo', title: '店铺编码', width: 100, align: 'left'},
                        {field: 'shortName', title: '店铺简称', width: 150, align: 'left'},
                        {field: 'fullName', title: '店铺全称', width: 150, align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' inputNoField='" + options.inputNoField + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                            if (options.relationData) {
                                $.ajax({
                                    url: BasePath + "/shop/initSubInfo?" + jQuery.param({shopNo: row.shopNo}),
                                    async: false,
                                    cache: true
                                }).then(function (result) {
                                    $("#companyNo_").val(result.companyNo);
                                    $("#companyName_").val(result.companyName);
                                    $("#bsgroupsNo_").val(result.bsgroupsNo);
                                    $("#bsgroupsName_").val(result.bsgroupsName);
                                    $("#mallNo_").val(result.mallNo);
                                    $("#mallName_").val(result.mallName);
                                    $("#organNo_").val(result.organNo);
                                    $("#organName_").val(result.organName);
                                    $("#brandNo_").val(result.brandNo);
                                    $("#brandName_").val(result.brandName);
                                });
                            }
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                options.onShowPanel = function () { // 打开面板时，触发的函数
                    // 级联查询
                    var queryParams = $.extend({}, options.queryParams);
                    var paramsJson = {};
                    if (options.filterCompany && $.trim(options.filterCompany) != '') {
                        var companyNo = $("#" + options.filterCompany).val();
                        paramsJson.companyNo = companyNo;
                    }
                    if (options.filterMall && $.trim(options.filterMall) != '') {
                        var mallNo = $("#" + options.filterMall).val();
                        paramsJson.mallNo = mallNo;
                    }
                    $.extend(queryParams, paramsJson);
                    var queryUrl = $.trim(options.url) != '' ? options.url : BasePath + '/shop/list.json'; // ?status=1
                    $("#" + options.id).combogrid("grid").datagrid({
                        url: queryUrl,
                        queryParams: queryParams
                    });
                    // $("#" +
                    // options.id).combogrid("grid").datagrid('reload');//需要重载一遍datagrid
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 商场editor
        mall: {
            init: function (container, options) {
                var defaults = {
                    id: "mallName",
                    name: "mallName",
                    inputWidth: 140,
                    panelHeight: 200,
                    panelWidth: 350,
                    idField: 'name',//
                    textField: 'name',// 数据表中的name
                    noField: 'mallNo',
                    inputNoField: 'mallNo',// 输入字段名称name 默认‘’
                    queryUrl: BasePath + '/mall/list.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'mallNo', title: '商场编码', width: 150, align: 'left'},
                        {field: 'name', title: '商场名称', width: 200, align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                options.url = options.queryUrl;
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {// 选中下拉项后关闭面板，填充数据
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.idField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        // 季节editor
        seasonbox: {
            init: function (container, options) {
                var defaults = {
                    valueField: 'itemvalue',
                    textField: 'itemname',
                    width: 130,
                    title: "选择季节",
                    id: "seasonNo",
                    name: "seasonNo",
                    url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SEASON',
                    onSelect: function (data) {
                        $('#seasonNo').val(data.itemvalue);
                        $('#seasonName').val(data.itemname);
                    }
                };
                var idbox = null;
                options = $.extend(defaults, options);
                if (options.required) {
                    idbox = $("<input type='text' name='" + options.name + "' class='ipt' id='" + options.id + "' required='true'/>").appendTo(container);
                } else {
                    idbox = $("<input type='text' name='" + options.name + "' class='ipt' id='" + options.id + "'/>").appendTo(container);
                }
                idbox.validatebox(options);
                $("#" + options.id).initCombox(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combobox("destroy");
            },
            getValue: function (target) {
                return $(target).combobox('getValue');
            },
            setValue: function (target, value) {
                $(target).val(value);
                $(target).combobox('setValue', value);
            }
        },
        // 币种editor
        currency: {
            init: function (container, options) {
                var defaults = {
                    id: "currencyCode",
                    name: "currencyName",
                    inputWidth: 140,
                    panelWidth: 300,
                    panelHeight: 200,
                    idField: 'currencyCode',//
                    textField: 'currencyName',// 数据表中的name
                    noField: 'currencyCode',
                    inputNoField: 'currencyCode',// 输入字段编码
                    url: BasePath + '/base_setting/currency_management/get_biz?status=1',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'currencyCode', title: '币种编码', width: 100, halign: 'center', align: 'left'},
                        {field: 'currencyName', title: '币种名称', width: 200, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        },
        styleNo: {
            init: function (container, options) {
                var defaults = {
                    id: "currencyCode",
                    name: "currencyName",
                    inputWidth: 140,
                    panelWidth: 350,
                    panelHeight: 300,
                    idField: 'styleNo',//
                    textField: 'styleNo',// 数据表中的name
                    noField: 'styleNo',
                    inputNoField: 'styleNo',// 输入字段编码
                    url: BasePath + '/base_setting/item/chooseStyleNo.json',
                    delay: 700,
                    mode: 'remote',
                    fitColumns: true,
                    pagination: true,
                    multiple: false,
                    columns: [[
                        {field: 'styleNo', title: '款号', width: 200, halign: 'center', align: 'left'}
                    ]]
                };
                options = $.extend(defaults, options);
                var idbox = $("<input type='text' name='" + options.name + "' class='ipt' style='width:" + options.inputWidth + "px' id='" + options.id + "' />").appendTo(container);
                if (options.callback) {
                    options.onHidePanel = function () {
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        options.callback(row);
                    }
                } else {
                    options.onHidePanel = function () {
                        // 获取选择的行
                        var row = $("#" + options.id).combogrid('grid').datagrid('getSelected');
                        if (row) {
                            $("#" + options.id).val(row[options.textField]);
                            $("#" + options.inputNoField).val(row[options.noField]);
                        } else { // 没选择数据则清空编辑框
                            $("#" + options.id).combogrid('clear');
                            $("#" + options.inputNoField).val("");
                        }
                    }
                }
                if (!options.onShowPanel) {
                    options.onShowPanel = function () { // 打开面板时，触发的函数
                        $("#" + options.id).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
                    }
                }
                idbox.combogrid(options);
                return idbox;
            },
            destroy: function (target) {
                $(target).combogrid('destroy');
            },
            getValue: function (target) {
                return $(target).combogrid("getValue");
            },
            setValue: function (target, value) {
                $(target).combogrid("setValue", value);
            },
            resize: function (target, width) {
                $(target).combogrid('resize', width);
            }
        }
    });

/**
 * *********************************** jquery对象级插件
 * *******************************************
 */
(function ($) {
    // 初始化search弹出框组件
    $.fn.initIptSearch = function (options) {
        var defaults = {
            title: "查询",
            href: "",
            queryUrl: "",
            inputWidth: 140,
            width: 500,
            height: 500,
            readonly: true,
            disabled: false,
            isFrame: false,
            enableCloseButton: false,
            fn: function (data) {
            }
        };
        options = $.extend(defaults, options);
        this.each(function () {
            var $this = $(this);
            $this.iptSearch({
                width: options.inputWidth,
                readonly: options.readonly,
                disabled: options.disabled,
                enableCloseButton: options.enableCloseButton,
                clickFn: function () {
                    dgSelector(options);
                }
            });
        });
        return this;
    };

    // 初始化combox组件
    $.fn.initCombox = function (options) {
        var defaults = {
            url: "",
            data: "",
            readonly: false,
            editable: false,
            width: 140,
            panelHeight: 'auto',
            valueField: "value",
            textField: "text",
            onChange: function (data) {
            },
            onSelect: function (data) {
            },
            multiple: false
        };
        options = $.extend(defaults, options);
        this.each(function () {
            $(this).combobox(options);
        });
        return this;
    };

    // 初始化combogrid
    $.fn.initCombogrid = function (options) {
        var defaults = {
            inputWidth: 140,
            panelWidth: 420,
            panelHeight: 200,
            valueField: '',//
            textField: '',// store表中的name
            noField: '',
            inputNoField: '',// 输入字段编码no 默认‘’
            inputNameField: '',
            queryUrl: '',
            delay: 700,
            mode: 'remote',
            fitColumns: true,
            pagination: true,
            multiple: false,
            required: false
        };
        options = $.extend(defaults, options);
        this.each(function () {
            options.width = options.inputWidth;
            options.url = options.queryUrl;
            options.onShowPanel = function () { // 打开面板时，触发的函数
                $(this).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
            }
            if (!options.callback) {
                options.onHidePanel = function () {// 选中下拉项后关闭面板，填充数据
                    // 获取选择的行,可多选
                    var rows = $(this).combogrid('grid').datagrid('getSelections');
                    if (rows) {
                        var dataNames = "", dataNos = "";
                        $.each(rows, function (index, item) {
                            dataNos += item[options.noField];
                            dataNames += item[options.textField];
                            if (index < rows.length - 1) {
                                dataNos += ",";
                                dataNames += ",";
                            }
                        });
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                    } else { // 没选择数据则清空编辑框
                        $(this).combogrid('clear');
                        $("#" + options.inputNoField).val("");
                    }
                }
            }
            $(this).combogrid(options);
        });
        return this;
    };

    // 添加tab页签
    $.fn.addTab = function (options) {
        var defaults = {
            title: "",
            selected: false,
            closable: false,
            href: "",
            onLoad: function (panel) {
            }
        };
        options = $.extend(defaults, options);
        this.each(function () {
            $(this).tabs('add', options);
        });
        return this;
    };

    // 清空datagrid的数据
    $.fn.clearDataGrid = function () {
        this.each(function () {
            $(this).datagrid({
                url: ""
            });
            $(this).datagrid('loadData', {total: 0, rows: [], footer: []});
        });
        return this;
    };
})(jQuery);


/**
 * *********************************** easyui插件
 * ***********************************************
 */
/** 仓库通用查询弹出框插件 */
(function ($) {
    var _name = 'storeCommon';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).filterbuilder({
            type: 'organ',
            organFlag: options.organFlag,
            roleType: options.roleType,
            onSave: function (result) {
                var value = $(this).filterbuilder('getValue');
                if (options.callback) {
                    options.callback(value);
                } else {
                    $("#" + options.inputNoField).val(value);
                }
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        organFlag: 1,
        roleType: 'bizCity',
        inputNoField: 'storeNo'// 输入字段编码no 默认‘’
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 店铺通用查询弹出框插件 */
(function ($) {
    var _name = 'shopCommon';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).filterbuilder({
            type: 'organ',
            organFlag: options.organFlag,
            roleType: options.roleType,
            onSave: function (result) {
                _load(this, options);
            }
        });
    };

    function _load(jq, options) {
        var value = $(jq).filterbuilder('getValue');
        if (options.callback) {
            options.callback(value);
        } else {
            $("#" + options.inputNoField).val(value);
        }
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        organFlag: 6,
        roleType: 'bizCity',
        inputNoField: 'shopNo'// 输入字段编码no 默认‘’
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    $.fn[_name].methods = {
        getValue: function (jq, data) {
            return _getValue(jq, data);
        },
        load: function (jq, data) {
            if(jq.length == 0 )
                return;
            var opt = $.data(jq[0], _name).options;
            return _load(jq, opt);
        }
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 商品通用查询弹出框插件 */
(function ($) {
    var _name = 'itemCommon';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };
    function _getValue(jq, data) {
        var itemObj = data;
        if (!itemObj)
            itemObj = $(jq).filterbuilder('getValue');
        if( typeof itemObj == "string" )
        	return itemObj;
        if (options.callback) {
            options.callback(itemObj.codeList);
        } else {
            if (!(itemObj.codeList instanceof Object)) {
                if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 1) {
                    // $("#" + options.inputNoField).val(itemObj.codeList);
                    return itemObj.codeList;
                } else if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 2) {
                    return 'item_no IN (' + '\'' + itemObj.codeList.split(',').join('\',\'') + '\'' + ')';
                    // $("#" + options.inputNoField).val('item_no IN (' + '\'' +
                    // itemObj.codeList.split(',').join('\',\'') + '\'' + ')');
                } else {
                    showWarn("未知的resultType");
                    return null;
                }
            }
        }

    }

    function _load(jq, options) {
    	var itemObj = $(jq).filterbuilder('getValue');
        if (options.callback) {
            options.callback(itemObj.codeList);
        } else {
            if (!(itemObj.codeList instanceof Object)) {
                var val = _getValue(jq, itemObj);
                $("#" + options.inputNoField).val(val);
            }
        }
    }

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).filterbuilder({
            type: 'item',
            sqlFlag: 1,
            maxRowNum: options.maxRowNum,
            onSave: function (result) {
                _load(this, options);
            }
// var itemObj = $(this).filterbuilder('getValue');
// if (options.callback) {
// options.callback(itemObj.codeList);
// } else {
// if (!(itemObj.codeList instanceof Object)) {
// var val = _getValue(jq,itemObj);
// $("#" + options.inputNoField).val(val);
// if (itemObj != null && itemObj.resultType != null && itemObj.resultType == 1)
// {
// $("#" + options.inputNoField).val(itemObj.codeList);
// } else if (itemObj != null && itemObj.resultType != null &&
// itemObj.resultType == 2) {
// $("#" + options.inputNoField).val('item_no IN (' + '\'' +
// itemObj.codeList.split(',').join('\',\'') + '\'' + ')');
// } else {
// showWarn("未知的resultType");
// }


        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        maxRowNum: 100,
        inputNoField: 'itemNo'// 输入字段编码no 默认‘’
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    $.fn[_name].methods = {
        getValue: function (jq, data) {
            return _getValue(jq, data);
        },
        load: function (jq, data) {
            if(jq.length == 0 )
                return;
            var opt = $.data(jq[0], _name).options;
            return _getValue(jq, data);
        }
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 款号弹出框插件 */
(function ($) {
    var _name = 'styleNo';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }

        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: options.queryUrl,
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'styleNo', title: '款号编码', width: 150, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "",
                        dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'styleNo',//
        textField: 'styleNo',// store表中的name
        inputNoField: 'styleNo',// 输入字段编码no 默认‘’
        inputCodeField: 'styleNo',
        inputNameField: 'styleName',
        queryUrl: BasePath + '/base_setting/item/chooseStyleNo.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
            $("#" + op.inputCodeField).val(data[op.codeField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + op.inputNoField).val("");
        $("#" + op.inputCodeField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 商品弹出框插件 */
(function ($) {
    var _name = 'item';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            columns: [[
                {field: 'code', title: '商品编码', width: 150, halign: 'center', align: 'left'},
                {field: 'name', title: '商品名称', width: 200, halign: 'center', align: 'left'},
                {field: 'brandNo', title: '品牌编码', width: 80, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "", dataCodes = "";
                    $.each(rows, function (index, item) {
                        dataNos += item.itemNo;
                        dataNames += item.name;
                        dataCodes += item.code;
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                            dataCodes += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputCodeField).val(dataCodes);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
// $(jq).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'code',//
        textField: 'code',// store表中的name
        inputNoField: 'itemNo',// 输入字段编码no 默认‘’
        inputCodeField: 'itemCode',
        inputNameField: 'itemName',
        queryUrl: BasePath + '/base_setting/item/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
            $("#" + op.inputCodeField).val(data[op.codeField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + op.inputNoField).val("");
        $("#" + op.inputCodeField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 结算公司弹出框插件 */
(function ($) {
    var _name = 'company';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            pageSize: options.pageSize,
            url: options.queryUrl,
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'companyNo', title: '结算公司编码', width: 100, halign: 'center', align: 'left'},
                {field: 'name', title: '结算公司名称', width: 250, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onLoadSuccess: function (data) {
                if (options.isDefaultData && data.total == 1 && $(jq).combo('getText') == '') {
                    $(jq).combogrid('grid').datagrid('selectRow', 0);
                    // 获取选择的行,可多选
                    var rows = $(jq).combogrid('grid').datagrid('getSelections');
                    if (rows) {
                        var dataNames = "", dataNos = "";
                        $.each(rows, function (index, item) {
                            dataNos += item[options.noField];
                            dataNames += item[options.textField];
                            if (index < rows.length - 1) {
                                dataNos += ",";
                                dataNames += ",";
                            }
                        });
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                        if (options.callback) {
                            if (options.multiple) {
                                options.callback(rows);
                            } else {
                                options.callback(rows[0]);
                            }
                        }
                    } else { // 没选择数据则清空编辑框
                        _clear(jq);
                    }
                }
            }
            /*
             * onShowPanel: function () { // 打开面板时，触发的函数
             * $(jq).combogrid("grid").datagrid({ url : options.queryUrl }); }
             */
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'name',//
        textField: 'name',// store表中的name
        noField: 'companyNo',
        inputNoField: 'companyNo',// 输入字段编码no 默认‘’
        inputNameField: 'companyName',
        queryUrl: BasePath + '/base_setting/company/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        pageSize: 20,
        multiple: false,
        required: false,
        isDefaultData: true
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 发方结算公司弹出框插件 */
(function ($) {
    var _name = 'salerCompany';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            columns: [[
                {field: 'salerNo', title: '结算公司编码', width: 80, halign: 'center', align: 'left'},
                {field: 'salerName', title: '结算公司名称', width: 200, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'salerName',// Rbi
        textField: 'salerName',// store表中的name
        noField: 'salerNo',
        inputNoField: 'salerNo',// 输入字段编码no 默认‘’
        inputNameField: 'salerName',
        queryUrl: BasePath + '/bill_buy_balance/selectSalerCompany',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 收方结算公司弹出框插件 */
(function ($) {
    var _name = 'buyerCompany';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: options.queryUrl,
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            columns: [[
                {field: 'buyerNo', title: '结算公司编码', width: 100, halign: 'center', align: 'left'},
                {field: 'buyerName', title: '结算公司名称', width: 200, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid('reload');// 需要重载一遍datagrid
            }
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'buyerName',// Rbi
        textField: 'buyerName',// store表中的name
        noField: 'buyerNo',
        inputNoField: 'buyerNo',// 输入字段编码no 默认‘’
        inputNameField: 'buyerName',
        queryUrl: BasePath + '/bill_buy_balance/selectBuyerCompany',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 结算公司和客户弹出框插件 */
(function ($) {
    var _name = 'customerAndCompany';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        if (!options.multiple) {
            $(jq).iptSearch({
                width: options.inputWidth,
                readonly: options.readonly,
                clickFn: function () {
                    dgSelector({
                        title: options.title,
                        width: options.panelWidth,
                        height: options.panelHeight,
                        isFrame: options.isFrame,
                        href: options.href,
                        queryUrl: options.queryUrl,
                        enableCloseButton: options.enableCloseButton,
                        fn: function (data) {
                            if (options.callback) {
                                options.callback(data);
                            } else {
                                _setValue(jq, data);
                            }
                        }
                    });
                }
            });
        } else {
            $(jq).iptSearch({
                width: options.inputWidth,
                readonly: options.readonly,
                clickFn: function () {
                    ygDialog({
                        title: options.title,
                        href: options.multipleHref,
                        width: options.panelWidth,
                        height: options.panelHeight,
                        isFrame: options.isFrame,
                        enableCloseButton: options.enableCloseButton,
                        onLoad: function (win, content) {
                            var _this = $(this);
                            $("#dialog_SearchDataGrid").datagrid({
                                onDblClickRow: function (index, row) {
                                    if (options.callback) {
                                        options.callback();
                                    } else {
                                        _setValue(jq, row);
                                    }
                                    win.close();
                                }
                            });
                            $("#dgSelectorSearchBtn", _this.contents()).on("click", function () {
                                var reqParam = $("#dialog_SarchForm").form("getData");
                                // 组装请求参数
                                $("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
                                $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
                                $("#dialog_SearchDataGrid").datagrid('load');
                            });
                            $("#dgSelectorClearBtn", _this.contents()).on("click", function () {
                                $("#dialog_SarchForm").form("clear");
                            });
                            $("#dgSelectorSureBtn", _this.contents()).on("click", function () {
                                var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
                                var dataNos = "", dataNames = "", data = {};
                                $.each(checkedRows, function (index, item) {
                                    dataNos += item.code;
                                    dataNames += item.name;
                                    if (index < checkedRows.length - 1) {
                                        dataNos += ",";
                                        dataNames += ",";
                                    }
                                });
                                data.code = dataNos;
                                data.name = dataNames;
                                if (options.callback) {
                                    options.callback(data);
                                } else {
                                    _setValue(jq, data);
                                }
                                win.close();
                            });
                        }
                    });
                }
            });
        }
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        title: '选择客户',
        inputWidth: 140,
        panelWidth: 500,
        panelHeight: 500,
        valueFeild: 'code',//
        textFeild: 'name',// store表中的name
        inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
        inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
        href: BasePath + '/plugin_page/searchCustomerAndCompany',
        multipleHref: BasePath + '/plugin_page/multiSearchCustomerAndCompany',
        queryUrl: BasePath + '/bill_balance_invoice_register/listAll.json',
        enableCloseButton: false,
        multiple: false,
        isFrame: false,
        readonly: true
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputValueFeild).val(data[op.valueFeild]);
        $("#" + op.inputNameFeild).val(data[op.textFeild]);
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $("#" + op.valueFeild).val();
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).iptSearch("disable");
    };

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).iptSearch("enable");
    };

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputValueFeild).val("");
        $("#" + op.inputNameFeild).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 供应商弹出框插件 */
(function ($) {
    var _name = 'supplier';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            delay: options.delay,
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'supplierNo', title: '供应商编码', width: 100, align: 'left'},
                {field: 'shortName', title: '供应商名称', width: 200, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 380,
        panelHeight: 200,
        valueField: 'shortName',//
        textField: 'shortName',// 数据表中的name
        noField: 'supplierNo',
        inputNoField: 'supplierNo',// 输入字段编码no 默认‘’
        inputNameField: 'supplierName',// 输入字段名称name 默认‘’
        queryUrl: BasePath + '/base_setting/supplier/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 客户弹出框插件 */
(function ($) {
    var _name = 'customer';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        var urlString = BasePath + '/base_setting/customer/list.json?status=1';
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'customerNo', title: '客户编码', width: 150, align: 'left'},
                {field: 'shortName', title: '客户简称', width: 150, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        options.callback(rows);
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                // 客户类型
                if ($("#customerType").val() != null) {
                    if ($("#customerType").val() == 'customer') {
                        urlString = urlString + '&customerType=2';
                    }
                }
                var newOptions = $.extend({queryUrl: urlString});
                $(jq).combogrid("grid").datagrid({
                    url: newOptions.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'shortName',//
        textField: 'shortName',// store表中的name
        noField: 'customerNo',
        inputNoField: 'customerNo',// 输入字段编码no 默认‘’
        inputNameField: 'customerName',
        queryUrl: "",
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 商场弹出框插件 */
(function ($) {
    var _name = 'mall';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: '',
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'mallNo', title: '商场编码', width: 150, align: 'left'},
                {field: 'name', title: '商场名称', width: 200, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                // 级联查询
                var queryParams = $.extend({}, options.queryParams);
                if (options.filterBsgroups && $.trim(options.filterBsgroups) != '') {
                    var bsgroupsNo = $("#" + options.filterBsgroups).val();
                    $.extend(queryParams, {bsgroupsNo: bsgroupsNo});
                }
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl,
                    queryParams: queryParams
                });
// $(jq).combogrid("grid").datagrid('reload');//需要重载一遍datagrid
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 380,
        panelHeight: 200,
        valueField: 'name',//
        textField: 'name',// 数据表中的name
        noField: 'mallNo',
        inputNoField: 'mallNo',// 输入字段编码no 默认‘’
        inputNameField: 'mallName',// 输入字段名称name 默认‘’
        queryUrl: BasePath + '/mall/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false,
        filterBsgroups: "", // 关联查询的商业集团编码
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 店铺弹出框插件(关联结算公司等信息) */
// (function($) {
// var _name = 'shopRelateInfo';
// // 自定义插件
// $.fn[_name] = function(options, param) {
// if (typeof options == "string") {
// return $.fn[_name].methods[options](this, param);
// };
// options = options || {};
// return this.each(function(){
// var opts = {};
// var data = $.data(this, _name);
// if (data) {
// opts = $.extend(data.options, options);
// } else {
// opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this),
// options);
// $.data(this, _name, {
// options : opts
// });
// }
// //插件渲染方法
// render(this, opts);
// });
// };
//
// // 页面渲染及回调设值
// function render(jq, options) {
// $(jq).combogrid({
// required : options.requried,
// width : options.inputWidth,
// delay: options.delay,
// panelWidth : options.panelWidth,
// panelHeight : options.panelHeight,
// url: options.queryUrl,
// idField : options.valueField,
// textField : options.textField,
// mode : options.mode,
// pagination : options.pagination,
// multiple : options.multiple,
// fitColumns : options.fitColumns,
// columns:[[
// {field : 'shopNo',title : '店铺编码',width : 100,align:'left'},
// {field : 'shortName',title : '店铺简称',width : 150,align:'left'}
// ]],
// onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
// var row = $(jq).combogrid('grid').datagrid('getSelected');
// if(row) {
// $("#" + options.id).val(row[options.idField]);
// $("#" + options.inputNoField).val(row[options.noField]);
// $.ajax({
// url: BasePath + "/shop/initSubInfo?"+jQuery.param({shopNo:row.shopNo}),
// async:false,
// cache: true
// }).then(function(result) {
// $("#companyNo").val(result.companyNo);
// $("#companyName").combogrid("setValue", result.companyName);
// });
// } else { //没选择数据则清空编辑框
// _clear(jq);
// }
// },
// onShowPanel: function () { // 打开面板时，触发的函数
// $(jq).combogrid("grid").datagrid('reload');//需要重载一遍datagrid
// }
// });
//
//
// //*****************************************************
// //加查询精灵
// var iptOption = $.extend({}, options, {
// title : '选择店铺',
// width: 650,
// height: 'auto',
// href : BasePath + '/plugin_page/searchShop',
// queryUrl: BasePath + '/shop/list.json',
// enableCloseButton : false,
// multiple : false,
// isFrame: false,
// fn : function(data) {
// $("#" + options.inputNoField).val(data.shopNo);
// $(jq).combogrid("setValue", data.shortName);
// $.ajax({
// url: BasePath + "/shop/initSubInfo?"+jQuery.param({shopNo:data.shopNo}),
// async:false,
// cache: true
// }).then(function(result) {
// $("#companyNo").val(result.companyNo);
// $("#companyName").combogrid("setValue", result.companyName);
// });
// }
// });
// // 查询精灵
// $(jq).iptSearch({
// clickFn : function() {
// dgSelector(iptOption);
// }
// });
// };
//
// // 默认的初始化参数
// $.fn[_name].defaults = {
// inputWidth : 140,
// panelWidth: 380,
// panelHeight : 200,
// valueField: 'shortName',//
// textField: 'shortName',// 数据表中的name
// noField: 'shopNo',
// inputNoField: 'shopNo',// 输入字段编码no 默认‘’
// inputNameField: 'shopName',// 输入字段名称name 默认‘’
// queryUrl: BasePath + '/shop/list.json?status=1',
// delay: 700,
// mode : 'remote',
// fitColumns : true,
// pagination: true,
// multiple : false,
// required : false
// };
//
// // 设置值的方法
// function _setValue(jq, data) {
// if (typeof data == 'undefined' || data == null) {
// return;
// }
// var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
// if(typeof data === 'string') {
// $(jq).combogrid('setValue', data);
// } else {
// $(jq).combogrid('setValue', data[op.valueField]);
// $("#" + op.inputNoField).val(data[op.noField]);
// }
// }
//
// // 获取插件的值
// function _getValue(jq, fieldName) {
// var op = $.data(jq, _name).options;
// if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
// return $("#"+fieldName).val();
// }
// return $(jq).combogrid('getValue');
// };
//
// // 插件不可用的方法
// function _disable(jq) {
// $(jq).attr("readonly", true).combogrid("disable");
// }
// // 插件可用的方法
// function _enable(jq) {
// $(jq).attr("readonly", true).combogrid("enable");
// }
//
// // 清空查询精灵的方法
// function _clear(jq) {
// var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
// $(jq).combogrid('clear');
// $("#" + options.inputNameField).val("");
// $("#" + options.inputNoField).val("");
// $("#companyNo").val("");
// $("#companyName").val("");
// };
//
// // 对外暴露的方法
// $.fn[_name].methods = {
// options: function (jq) {
// //return $.data(jq[0], _name).options;
// return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
// },
// setValue: function (jq, data) {
// _setValue(jq[0], data);
// },
// getValue: function (jq, fieldName) {
// return _getValue(jq[0], fieldName);
// },
// clear: function (jq, param) {
// $.each(jq, function (index, item) {
// _clear(item);
// });
// },
// disable: function (jq) {
// $.each(jq, function (index, item) {
// _disable(item);
// });
//
// },
// enable: function (jq) {
// $.each(jq, function (index, item) {
// _enable(item);
// });
// }
// };
//
// // 将声明式定义属性data-options转化为options
// $.fn[_name].parseOptions = function(target) {
// return $.parser.parseOptions(target, ['data']);
// };
//
// //将自定义的插件加入 easyui 的插件组
// $.parser.plugins.push(_name);
// })(jQuery);


// /** 店铺弹出框插件 */
// (function($) {
// var _name = 'shop';
// // 自定义插件
// $.fn[_name] = function(options, param) {
// if (typeof options == "string") {
// return $.fn[_name].methods[options](this, param);
// };
// options = options || {};
// return this.each(function(){
// var opts = {};
// var data = $.data(this, _name);
// if (data) {
// opts = $.extend(data.options, options);
// } else {
// opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this),
// options);
// $.data(this, _name, {
// options : opts
// });
// }
// //插件渲染方法
// render(this, opts);
// });
// };
//
// // 页面渲染及回调设值
// function render(jq, options) {
// $(jq).combogrid({
// required : options.requried,
// width : options.inputWidth,
// delay: options.delay,
// panelWidth : options.panelWidth,
// panelHeight : options.panelHeight,
// url: options.queryUrl,
// idField : options.valueField,
// textField : options.textField,
// mode : options.mode,
// pagination : options.pagination,
// multiple : options.multiple,
// fitColumns : options.fitColumns,
// columns:[[
// {field : 'shopNo',title : '店铺编码',width : 100,align:'left'},
// {field : 'shortName',title : '店铺简称',width : 150,align:'left'}
// ]],
// onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
// // 获取选择的行,可多选
// var rows = $(jq).combogrid('grid').datagrid('getSelections');
// if(rows) {
// var dataNames = "", dataNos = "";
// $.each(rows, function(index, item) {
// dataNos += item[options.noField];
// dataNames += item[options.textField];
// if(index < rows.length - 1) {
// dataNos += ",";
// dataNames += ",";
// }
// });
// $("#" + options.inputNoField).val(dataNos);
// $("#" + options.inputNameField).val(dataNames);
// } else { //没选择数据则清空编辑框
// _clear(jq);
// }
// },
// onShowPanel: function () { // 打开面板时，触发的函数
// var queryParams = $.extend({}, $.fas.formatFieldValue(options.paramMap),
// options.queryParams);
// $(jq).combogrid("grid").datagrid('reload', queryParams);//需要重载一遍datagrid
// }
// });
// };
//
// // 默认的初始化参数
// $.fn[_name].defaults = {
// inputWidth : 140,
// panelWidth: 380,
// panelHeight : 200,
// valueField: 'shortName',//
// textField: 'shortName',// 数据表中的name
// noField: 'shopNo',
// inputNoField: 'shopNo',// 输入字段编码no 默认‘’
// inputNameField: 'shopName',// 输入字段名称name 默认‘’
// queryUrl: BasePath + '/shop/list.json?status=1',
// delay: 700,
// mode : 'remote',
// fitColumns : true,
// pagination: true,
// multiple : false,
// required : false
// };
//
// // 设置值的方法
// function _setValue(jq, data) {
// if (typeof data == 'undefined' || data == null) {
// return;
// }
// var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
// if(typeof data === 'string') {
// $(jq).combogrid('setValue', data);
// } else {
// $(jq).combogrid('setValue', data[op.valueField]);
// $("#" + op.inputNoField).val(data[op.noField]);
// }
// }
//
// // 获取插件的值
// function _getValue(jq, fieldName) {
// var op = $.data(jq, _name).options;
// if(typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
// return $("#"+fieldName).val();
// }
// return $(jq).combogrid('getValue');
// };
//
// // 插件不可用的方法
// function _disable(jq) {
// $(jq).attr("readonly", true).combogrid("disable");
// }
// // 插件可用的方法
// function _enable(jq) {
// $(jq).attr("readonly", true).combogrid("enable");
// }
//
// // 清空查询精灵的方法
// function _clear(jq) {
// var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
// $(jq).combogrid('clear');
// $("#" + options.inputNameField).val("");
// $("#" + options.inputNoField).val("");
// };
//
// // 对外暴露的方法
// $.fn[_name].methods = {
// options: function (jq) {
// //return $.data(jq[0], _name).options;
// return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
// },
// setValue: function (jq, data) {
// _setValue(jq[0], data);
// },
// getValue: function (jq, fieldName) {
// return _getValue(jq[0], fieldName);
// },
// clear: function (jq, param) {
// $.each(jq, function (index, item) {
// _clear(item);
// });
// },
// disable: function (jq) {
// $.each(jq, function (index, item) {
// _disable(item);
// });
//
// },
// enable: function (jq) {
// $.each(jq, function (index, item) {
// _enable(item);
// });
// }
// };
//
// // 将声明式定义属性data-options转化为options
// $.fn[_name].parseOptions = function(target) {
// return $.parser.parseOptions(target, ['data']);
// };
//
// //将自定义的插件加入 easyui 的插件组
// $.parser.plugins.push(_name);
// })(jQuery);


/** 店铺弹出框插件 */
(function ($) {
    var _name = 'shop';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.required,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: 350,
            panelHeight: 200,
            url: "",
            idField: "shortName",
            textField: "shortName",
            mode: options.mode,
            pagination: true,
            multiple: options.multiple,
            fitColumns: false,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'shopNo', title: '店铺编码', width: 100, align: 'left'},
                {field: 'shortName', title: '店铺简称', width: 200, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                var row = {};
                if (rows.length > 0) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item.shopNo;
                        dataNames += item.shortName;
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    row.shopNo = dataNos;
                    row.shortName = dataNames;
                    if (options.callback) {
                        options.callback(row);
                    } else {
                        $("#" + options.inputNoField).val(row.shopNo);
// $(jq).combogrid("setValue", row.shortName);
                    }
                } else { // 没选择数据则清空编辑框
// _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                // 级联查询
                var queryParams = $.extend({}, options.queryParams);
                $(jq).combogrid("grid").datagrid({
                    url: BasePath + "/shop/list.json",   // ?status=1
                    queryParams: queryParams
                });
            }
        });

        var url = options.href;
        if (options.multiple) {
            url += "?multipleShop=true";
        }
        $(jq).iptSearch({
            width: options.inputWidth,
            clickFn: function () {
                ygDialog({
                    title: options.title,
                    href: url,
                    width: options.panelWidth,
                    height: options.panelHeight,
                    enableCloseButton: options.enableCloseButton,
                    onLoad: function (dialog, content) {
                        $("#pluginShopShopDataGrid").datagrid({
                            onDblClickRow: function (index, row) {
                                if (options.callback) {
                                    options.callback(row);
                                } else {
                                    $("#" + options.inputNoField).val(row.shopNo);
// $("#" + options.inputNameField).val(row.shortName);
                                    $(jq).combogrid("setValue", row.shortName);
                                }
                                dialog.close();
                            }
                        });
                        $("#query-shoh-btn-sure").bind("click", function () {
                            var checkedRows = $("#pluginShopShopDataGrid").datagrid("getChecked");
                            if (options.callback) {
                                if (options.multiple) {
                                    options.callback(checkedRows);
                                } else {
                                    options.callback(checkedRows.length > 0 ? checkedRows[0] : null);
                                }
                            } else {
                                var shopNos = "", shopNames = "";
                                $.each(checkedRows, function (index, item) {
                                    if (index < (checkedRows.length - 1)) {
                                        shopNos += item.shopNo + ",";
                                        shopNames += item.shortName + ",";
                                    } else {
                                        shopNos += item.shopNo;
                                        shopNames += item.shortName;
                                    }
                                });
                                $("#" + options.inputNoField).val(shopNos);
// $("#" + options.inputNameField).val(shopNames);
                                $(jq).combogrid("setValue", shopNames);
                            }
                            dialog.close();
                        })
                    }
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        title: '选择店铺',
        inputWidth: 140,
        panelWidth: 850,
        panelHeight: 480,
        inputNoField: 'shopNo',// 输入字段编码no 默认‘’
        inputNameField: 'shopName',// 输入字段名称name 默认‘’
        href: BasePath + '/plugin_page/selectShop',
        enableCloseButton: false,
        multiple: false,
        isFrame: false,
        required: false,
        delay: 700,
        mode: 'remote'
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
// $(jq).val(data);
        $(jq).combogrid("setValue", data);
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
// return $(jq).val();
        return $(jq).combogrid("getValue");
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).iptSearch("disable");
    };

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).iptSearch("enable");
    };

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputNoField).val("");
        $("#" + op.inputNameField).val("");
        $(jq).combogrid("clear");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 货管单位弹出框插件 */
(function ($) {
    var _name = 'orderUnit';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            delay: options.delay,
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'orderUnitNo', title: '货管单位编号', width: 100, align: 'left'},
                {field: 'name', title: '货管单位名称', width: 200, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 380,
        panelHeight: 200,
        valueField: 'name',//
        textField: 'name',// 数据表中的name
        noField: 'orderUnitNo',
        inputNoField: 'orderUnitNo',// 输入字段编码no 默认‘’
        inputNameField: 'orderUnitName',// 输入字段名称name 默认‘’
        queryUrl: BasePath + '/order_unit/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false

    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 机构弹出框插件 */
(function ($) {
    var _name = 'store';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            delay: options.delay,
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'storeNo', title: '机构编码', width: 100, align: 'left'},
                {field: 'shortName', title: '机构名称', width: 200, align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 380,
        panelHeight: 200,
        valueField: 'shortName',//
        textField: 'shortName',// 数据表中的name
        noField: 'storeNo',
        inputNoField: 'storeNo',// 输入字段编码no 默认‘’
        inputNameField: 'storeName',// 输入字段名称name 默认‘’
        queryUrl: BasePath + '/store/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false

    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 开票信息维护弹出框插件 */
(function ($) {
    var _name = 'customerInvoiceInfo';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: options.queryUrl,
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            columns: options.columns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'clientNo', title: '客户编码', width: 100, halign: 'center', align: 'left'},
                {field: 'clientName', title: '客户名称', width: 250, halign: 'center', align: 'left'},
                {
                    field: 'status', title: '状态', width: 80, halign: 'center', align: 'left',
                    formatter: function (value, rowData, rowIndex) {
                        var dataStatusType = [{'value': '0', 'text': '备选'}, {'value': '1', 'text': '首选'}];
                        for (var i = 0; i < dataStatusType.length; i++) {
                            if (dataStatusType[i].value == value) {
                                return dataStatusType[i].text;
                            }
                        }
                        return "";
                    },
                    editor: {
                        type: 'combobox',
                        options: {
                            id: 'status',
                            data: [{'value': '0', 'text': '备选'}, {'value': '1', 'text': '首选'}],
                            valueField: 'value', textField: 'text',
                            required: true
                        }
                    }
                },
                {
                    field: 'invoiceName', title: '开票名称', width: 150, align: 'left', halign: 'center',
                    editor: {
                        type: 'validatebox',
                        options: {
                            required: true
                        }
                    }
                }
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid('reload');
                /*
                 * $(jq).combogrid("grid").datagrid({ url : options.queryUrl });
                 */
            }
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'clientName',//
        textField: 'clientName',// store表中的name
        noField: 'clientNo',
        inputNoField: 'clientNo',// 输入字段编码no 默认‘’
        inputNameField: 'clientName',
        queryUrl: BasePath + '/base_setting/invoice_info_set/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 品牌部弹出框插件 */
(function ($) {
    var _name = 'brandunit';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            pageSize: options.pageSize,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'brandUnitNo', title: '品牌部编码', width: 80, halign: 'center', align: 'left'},
                {field: 'name', title: '品牌部名称', width: 100, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 300,
        panelHeight: 200,
        valueField: 'name',//
        textField: 'name',// store表中的name
        noField: 'brandUnitNo',
        inputNoField: 'brandUnitNo',// 输入字段编码no 默认‘’
        inputNameField: 'brandUnitName',
        queryUrl: BasePath + '/brand_unit/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        pageSize: 50,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 品牌弹出框插件 */
(function ($) {
    var _name = 'brand';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combotree({
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: '',
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                checkedValue = $(jq).combotree('tree').tree('getChecked');
                var arrayObj = new Array();
                var dataNos = "", dataNames = "";
                $.each(checkedValue, function (index, row) {
                    var isLeaf = $(jq).combotree('tree').tree('isLeaf', row.target);
                    if (isLeaf) {
                        arrayObj.push(row.id);
                        dataNos += row.id;
                        dataNames += row.text;
                        if (index < checkedValue.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    }
                });
                $(jq).combotree('setValues', arrayObj);
                $("#" + options.inputNoField).val(dataNos);
                $("#" + options.inputNameField).val(dataNames);
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combotree("tree").tree({
                    url: BasePath + '/brand/selectBrandWithBrandUnit?status=1'
                });
            }
        });
        $(jq).combotree('tree').tree({
            checkbox: true,
            onSelect: function (node) {
            },
            onCheck: function (node) {
            },
            onClick: function (node) {
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 200,
        inputNoField: 'brandNo',
        inputNameField: 'brandName'
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 品牌弹出框插件 */
(function ($) {
    var _name = 'brandWithUnit';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combotree({
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: '',
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                checkedValue = $(jq).combotree('tree').tree('getChecked');
                var arrayObj = new Array();
                var dataNos = "(",
                    dataNames = "(";
                $.each(checkedValue, function (index, row) {
                    var isLeaf = $(jq).combotree('tree').tree('isLeaf', row.target);
                    if (isLeaf) {
                        arrayObj.push(row.id);
                        dataNos += "'" + row.id + "'";
                        dataNames += "'" + row.text + "'";
                        if (index < checkedValue.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    }
                });
                dataNos += ')';
                dataNames += ')';
                $(jq).combotree('setValues', arrayObj);
                $("#" + options.inputNoField).val(dataNos);
                $("#" + options.inputNameField).val(dataNames);
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combotree("tree").tree({
                    url: BasePath + '/brand/selectBrandWithBrandUnit?status=1'
                });
            }
        });
        $(jq).combotree('tree').tree({
            checkbox: true,
            onSelect: function (node) {
            },
            onCheck: function (node) {
            },
            onClick: function (node) {
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 200,
        inputNoField: 'brandNo',
        inputNameField: 'brandName'
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 活动编码弹出框插件 */
(function ($) {
    var _name = 'actioncode';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };


    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            delay: options.delay,
            width: options.inputWidth,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'proNo', title: '活动编号', width: 70, halign: 'center', align: 'left'},
                {field: 'proName', title: '活动名称', width: 100, halign: 'center', align: 'left'},
                {field: 'proStartDate', title: '促销开始时间', width: 100, halign: 'center', align: 'left'},
                {field: 'proEndDate', title: '促销结束时间', width: 100, halign: 'center', align: 'left'},
                {field: 'discount', title: '扣率', width: 70, halign: 'center', align: 'left'},
                {field: 'saleAmount', title: '销售收入', width: 80, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };


    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 520,
        panelHeight: 200,
        valueField: 'proNo',// 数据库表中的name
        textField: 'proName',// 数据库表中的name
        noField: 'proNo',
        inputNoField: 'proNo',// 输入字段编码no 默认‘’
        inputNameField: 'proName',
        queryUrl: BasePath + '/bill_shop_balance_pro_sum/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 商业集团弹出框插件 */
(function ($) {
    var _name = 'bsgroups';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            required: options.requried,
            width: options.inputWidth,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'bsgroupsNo', title: '商业集团编码', width: 150, halign: 'center', align: 'left'},
                {field: 'name', title: '商业集团名称', width: 260, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    $("#" + options.inputNoField).val(dataNos);
                    $("#" + options.inputNameField).val(dataNames);
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 420,
        panelHeight: 200,
        valueField: 'name',//
        textField: 'name',// store表中的name
        noField: 'bsgroupsNo',
        inputNoField: 'bsgroupsNo',// 输入字段编码no 默认‘’
        inputNameField: 'bsgroupsName',
        queryUrl: BasePath + '/bsgroups/list.json',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 收款弹出框插件 */
(function ($) {
    var _name = 'term';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            columns: [[
                {field: 'termNo', title: '条款编码', width: 150, halign: 'center', align: 'left'},
                {field: 'name', title: '条款名称', width: 200, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    } else {
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 300,
        panelHeight: 200,
        valueField: 'name',// 数据库表中的name
        textField: 'name',// 数据库表中的name
        noField: 'termNo',
        inputNoField: 'termNo',// 输入字段编码no 默认‘’
        inputNameField: 'termName',
        queryUrl: BasePath + '/wholesale_rece_term/list.json?status=1',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 管理城市弹出框插件 */
(function ($) {
    var _name = 'organ';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.inputWidth,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.valueField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'organNo', title: '组织编码', width: 150, halign: 'center', align: 'left'},
                {field: 'name', title: '组织名称', width: 200, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    } else {
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                    }
                } else { // 没选择数据则清空编辑框
// _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                // 级联查询
                var queryParams = $.extend({}, options.queryParams);
                if (options.filterOrgan && $.trim(options.filterOrgan) != '') {
                    var filterOrganNo = $("#" + options.filterOrgan).val();
                    $.extend(queryParams, {filterOrganNo: filterOrganNo});
                }
                $(jq).combogrid("grid").datagrid({
                    url: options.queryUrl,
                    queryParams: queryParams
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 140,
        panelWidth: 300,
        panelHeight: 200,
        valueField: 'name',// 数据库表中的name
        textField: 'name',// 数据库表中的name
        noField: 'organNo',
        inputNoField: 'organNo',// 输入字段编码no 默认‘’
        inputNameField: 'organName',
        queryUrl: BasePath + '/organ/list.json?status=1&organLevel=1',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: false,
        required: false,
        filterOrgan: "" // 经营城市通过管理城市级联
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 管理城市或经营城市下拉框插件 */
(function ($) {
    var _name = 'organbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 如果是管理城市且级联查询经营城市
            if (opts.levelType == '1' && $.trim(opts.cityFieldId) != '') {
                opts.onChange = function (newValue, oldValue) {
                    $("#" + opts.cityFieldId).combobox("clear");
                    $("#" + opts.cityFieldId).combobox("reload", BasePath + "/organ/get_biz?organLevel=2&status=1&parentNo=" + newValue);
                }
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/organ/get_biz?organLevel=1&status=1',
        panelHeight: "auto",
        valueField: 'organNo',
        textField: 'name',
        onChange: function (newValue, oldValue) {
        },
        onSelect: function (data) {
        },
        multiple: false,
        levelType: 1, // 管理城市级别
        organFieldId: 'organNo', // 管理城市字段id
        cityFieldId: 'bizCityNo', // 经营城市字段id
        required: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 品牌下拉框插件 */
(function ($) {
    var _name = 'brandbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/brand/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'brandNo',
        textField: 'name',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 结算类型下拉框插件 */
(function ($) {
    var _name = 'balancetypebox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    function render(jq, options) {
        var url = BasePath + '/con_balance_type/get_biz?status=1';
        if (options.balanceNos != null) {
            url = url + '&balanceNos=' + options.balanceNos;
        }
        if (options.partType != null) {
            url = url + '&partType=' + options.partType;
        }
        if (options.businessType != null) {
            url = url + '&businessType=' + options.businessType;
        }
        if (options.isBalance != null) {
            url = url + '&isBalance=' + options.isBalance;
        }

        $(jq).combobox({
            multiple: options.multiple,
            readonly: options.readonly,
            width: options.width,
            url: url,
            valueField: 'balanceNo',
            textField: 'balanceName',
            onChange: function (data) {
            },
            onSelect: function (data) {
            }
        });
    };


    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        width: 130,
        multiple: true,
        // 结算类型编号 用,号隔开
        balanceNos: null,
        // 主体类型(1:总部 2:地区)
        partType: null,
        // 业务类型(1:采购 2:销售)
        businessType: null,
        // 是否做结算单(0:否 1:是)
        isBalance: null
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 单据类型弹出框插件 */
(function ($) {
    var _name = 'billtype';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.width,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.idField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'billCode', title: '单据类型编码', width: 150, halign: 'center', align: 'left'},
                {field: 'billName', title: '单据类型名称', width: 300, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    } else {
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                var url = BasePath + '/con_bill_type/list.json?status=1';
                if (options.billCodes != null) {
                    var values = options.billCodes;
                    if (values && $.trim(values) != '') {
                        var billCodes = '\'' + values.split(',').join('\',\'') + '\'';
                        url = url + '&billCodes=' + billCodes;
                    }
                }
                if (options.relSys != null) {
                    url = url + '&relSys=' + options.relSys;
                }
                if (options.tableName != null) {
                    url = url + '&tableName=' + options.tableName;
                }
                if (options.transferType != null) {
                    url = url + '&transferType=' + options.transferType;
                }
                var queryParams = $.extend({}, options.queryParams);
                $(jq).combogrid("grid").datagrid({
                    url: url,
                    queryParams: queryParams
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 140,
        panelWidth: 450,
        panelHeight: 250,
        inputNoField: 'billTypeNo',// 输入字段编码no 默认‘’
        inputNameField: 'billTypeName',
        noField: 'billCode',
        idField: 'billName',
        textField: 'billName',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: true,
        required: false,
        // 单据类型编号集
        billCodes: null,
        // 来源系统
        relSys: null,
        // 所在表名
        tableName: null,
        // 出入库类型(1:出库 2:入库)
        transferType: null
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 公司结算单据类型弹出框插件 */
(function ($) {
    var _name = 'companybilltype';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combogrid({
            width: options.width,
            required: options.required,
            delay: options.delay,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            url: "",
            idField: options.idField,
            textField: options.textField,
            mode: options.mode,
            pagination: options.pagination,
            multiple: options.multiple,
            fitColumns: options.fitColumns,
            selectOnCheck: true,
            checkOnSelect: true,
            frozenColumns: [[{field: 'ck', checkbox: true, width: 30}]],
            columns: [[
                {field: 'companyBillCode', title: '编码', width: 160, halign: 'center', align: 'left'},
                {field: 'companyName', title: '公司', width: 160, halign: 'center', align: 'left'},
                {field: 'balanceName', title: '结算类型', width: 80, halign: 'center', align: 'center'},
                {field: 'sendReceiveTypeName', title: '收发类型', width: 80, halign: 'center', align: 'center'},
                {field: 'billName', title: '单据类型', width: 200, halign: 'center', align: 'left'}
            ]],
            onHidePanel: function () {// 选中下拉项后关闭面板，填充数据
                // 获取选择的行,可多选
                var rows = $(jq).combogrid('grid').datagrid('getSelections');
                if (rows) {
                    var dataNames = "", dataNos = "";
                    $.each(rows, function (index, item) {
                        dataNos += item[options.noField];
                        dataNames += item[options.textField];
                        if (index < rows.length - 1) {
                            dataNos += ",";
                            dataNames += ",";
                        }
                    });
                    if (options.callback) {
                        if (options.multiple) {
                            options.callback(rows);
                        } else {
                            options.callback(rows[0]);
                        }
                    } else {
                        $("#" + options.inputNoField).val(dataNos);
                        $("#" + options.inputNameField).val(dataNames);
                    }
                } else { // 没选择数据则清空编辑框
                    _clear(jq);
                }
            },
            onShowPanel: function () { // 打开面板时，触发的函数
                var url = BasePath + '/company_balance_bill/list.json?status=1';

                if (options.filterCompany != null) {
                    var values = $("#" + options.filterCompany).val();
                    if (values && $.trim(values) != '') {
                        var filterCompany = '\'' + values.split(',').join('\',\'') + '\'';
                        url = url + '&companyNos=' + filterCompany;
                    }
                }

                var balanceNos = null;
                if (options.balanceNos != null) {
                    balanceNos = '&balanceNos=' + options.balanceNos;
                }
                if (options.filterBalance != null) {
                    var values = $("#" + options.filterBalance).combobox('getValues').toString();
                    if (values && $.trim(values) != '') {
                        var filterBalance = '\'' + values.split(',').join('\',\'') + '\'';
                        balanceNos = '&balanceNos=' + filterBalance;
                    }
                }
                if (balanceNos != null) {
                    url = url + balanceNos;
                }

                if (options.partType != null) {
                    url = url + '&partType=' + options.partType;
                }
                if (options.businessType != null) {
                    url = url + '&businessType=' + options.businessType;
                }
                if (options.isBalance != null) {
                    url = url + '&isBalance=' + options.isBalance;
                }
                if (options.relSys != null) {
                    url = url + '&relSys=' + options.relSys;
                }
                if (options.tableName != null) {
                    url = url + '&tableName=' + options.tableName;
                }
                if (options.transferType != null) {
                    url = url + '&transferType=' + options.transferType;
                }
                var queryParams = $.extend({}, options.queryParams);
                $(jq).combogrid("grid").datagrid({
                    url: url,
                    queryParams: queryParams
                });
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 140,
        panelWidth: 700,
        panelHeight: 300,
        inputNoField: 'companyBillTypeNo',// 输入字段编码no 默认‘’
        inputNameField: 'companyBillTypeName',
        noField: 'companyBillCode',
        idField: 'companyBillCode',
        textField: 'billName',
        delay: 700,
        mode: 'remote',
        fitColumns: true,
        pagination: true,
        multiple: true,
        required: false,
        // 结算类型编号 用,号隔开
        balanceNos: null,
        // 主体类型(1:总部 2:地区)
        partType: null,
        // 业务类型(1:采购 2:销售)
        businessType: null,
        // 是否做结算单(0:否 1:是)
        isBalance: null,
        // 来源系统
        relSys: null,
        // 所在表名
        tableName: null,
        // 出入库类型(1:出库 2:入库)
        transferType: null,

        // 级联控件（公司编号input）
        filterCompany: null,
        // 级联控件（结算类型combobox）
        filterBalance: null
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (typeof data === 'string') {
            $(jq).combogrid('setValue', data);
        } else {
            $(jq).combogrid('setValue', data[op.valueField]);
            $("#" + op.inputNoField).val(data[op.noField]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $(jq).combogrid('getValue');
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).combogrid("disable");
    }

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).combogrid("enable");
    }

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $(jq).combogrid('clear');
        $("#" + options.inputNoField).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 地区下拉框插件 */
(function ($) {
    var _name = 'zonebox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        width: 130,
        url: BasePath + '/zone_info/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'zoneNo',
        textField: 'name',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false,
        onLoadSuccess: function () {
            var data = $(this).combobox('getData');
            $(this).combobox('select', data[0].zoneNo);
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 省份弹出框插件 */
(function ($) {
    var _name = 'province';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=PROVINCE',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);

})(jQuery);

/** 门店级别弹出框插件 */
(function ($) {
    var _name = 'shoplevel';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SHOP_LEVEL',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);

})(jQuery);

/** 店铺小类弹出框插件 */
(function ($) {
    var _name = 'retailtype';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SHOP_CATEGORY_B',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);

})(jQuery);

/** 店铺大类弹出框插件 */
(function ($) {
    var _name = 'salemode';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SHOP_CATEGORY_A',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);

})(jQuery);

/** 店铺小类弹出框插件 */
(function ($) {
    var _name = 'shopmulti';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SHOP_CATEGORY_C',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);

})(jQuery);

/** 季节下拉框插件 */
(function ($) {
    var _name = 'seasonbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            if (opts.addAll) {
                opts.url += "&addAllFlag=true";
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=SEASON',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false,
        addAll: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 年份下拉框插件 */
(function ($) {
    var _name = 'yearbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=YEAR',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 订货形式下拉框插件 */
(function ($) {
    var _name = 'orderFormBox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        url: BasePath + '/initCache/getLookupDtlsList?lookupcode=ORDER_STYLE',
        panelHeight: "auto",
        valueField: 'itemvalue',
        textField: 'itemname',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 大类下拉框插件 */
(function ($) {
    var _name = 'categorybox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: true,
        width: 130,
        url: BasePath + '/category/get_biz?levelid=1&status=1',
        panelHeight: "auto",
        valueField: 'categoryNo',
        textField: 'name',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 结算大类下拉框插件 */
(function ($) {
    var _name = 'settlecategorybox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 130,
        readonly: false,
        editable: false,
        url: BasePath + '/settle_category/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'settleCategoryNo',
        textField: 'name',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 新旧款下拉框插件 */
(function ($) {
    var _name = 'newstylebox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 130,
        readonly: false,
        editable: false,
        url: BasePath + '/settle_new_style/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'styleNo',
        textField: 'name',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 供应商组下拉框插件 */
(function ($) {
    var _name = 'suppliergroupbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 130,
        readonly: true,
        url: BasePath + '/supplier_group/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'groupNo',
        textField: 'groupName',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 操作状态下拉框插件 */
(function ($) {
    var _name = 'statusbox';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        width: 130,
        readonly: false,
        editable: false,
        data: [{"value": "1", "text": "已启用"}, {"value": "0", "text": "已停用"}],
        panelHeight: "auto",
        valueField: 'value',
        textField: 'text',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);

/** 币别下拉框插件 */
(function ($) {
    var _name = 'currency';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            $(this).combobox(opts);
        });
    };
    // 默认的初始化参数
    $.fn[_name].defaults = {
        readonly: false,
        editable: false,
        width: 130,
        url: BasePath + '/base_setting/currency_management/get_biz?status=1',
        panelHeight: "auto",
        valueField: 'currencyCode',
        textField: 'currencyName',
        onChange: function (data) {
        },
        onSelect: function (data) {
        },
        multiple: false
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        getValue: function (jq) {
            return $(jq[0]).combobox('getValue');
        },
        setValue: function (jq, data) {
            $(jq[0]).combobox('setValue', data);
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("disable");
            });
        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                $(jq).attr("readonly", true).combobox("enable");
            });
        },
        clear: function (jq) {
            $(jq[0]).combobox("clear");
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 结算公司或者客户弹出框插件 */
(function ($) {
    var _name = 'customerOrCompany';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).iptSearch({
            width: options.inputWidth,
            clickFn: function () {
                ygDialog({
                    title: options.title,
                    href: options.href,
                    width: options.panelWidth,
                    height: options.panelHeight,
                    isFrame: options.isFrame,
                    enableCloseButton: options.enableCloseButton,
                    onLoad: function (win, content) {
                        var _this = $(this);
                        $("#dataSourceTypeConditioin").initCombox({
                            data: [{text: "客户", value: "customer"}, {text: "公司", value: "company"}],
                            width: 80,
                            value: "customer", // 设置默认值
                            onSelect: function (data) {
                                if ("customer" == data.value) {
                                    $("#dialog_SearchDataGrid").datagrid({
                                        "columns": [[
                                            {field: 'customerNo', title: '客户编码', width: 150, align: 'left'},
                                            {field: 'fullName', title: '客户名称', width: 200, align: 'left'},
                                            {field: 'shortName', title: '客户简称', width: 150, align: 'left'}
                                        ]],
                                        url: "",
                                        onDblClickRow: function (index, row) {
                                            _setValue(jq, row);
                                            win.close();
                                        }
                                    });
                                    $("#noCondition").html("客户编码：");
                                    $("#nameCondition").html("客户名称：");
                                    $("#inputNoCondition").attr("name", "customerNo");
                                    $("#inputNameCondition").attr("name", "fullName");
                                    options.queryUrl = BasePath + '/base_setting/customer/list.json';
                                } else if ("company" == data.value) {
                                    $("#dialog_SearchDataGrid").datagrid({
                                        "columns": [[
                                            {
                                                field: 'companyNo',
                                                title: '结算公司编码',
                                                width: 150,
                                                halign: 'center',
                                                align: 'left'
                                            },
                                            {
                                                field: 'name',
                                                title: '结算公司名称',
                                                width: 260,
                                                halign: 'center',
                                                align: 'left'
                                            }
                                        ]],
                                        url: "",
                                        onDblClickRow: function (index, row) {
                                            if (options.callback) {
                                                options.callback();
                                            } else {
                                                $("#" + options.inputValueFeild).val(row.companyNo);
                                                $("#" + options.inputNameFeild).val(row.name);
                                            }
                                            win.close();
                                        }
                                    });
                                    $("#noCondition").html("公司编码：");
                                    $("#nameCondition").html("公司名称：");
                                    $("#inputNoCondition").attr("name", "companyNo");
                                    $("#inputNameCondition").attr("name", "name");
                                    options.queryUrl = BasePath + '/base_setting/company/list.json';
                                }
                                $('#dialog_SearchDataGrid').datagrid('loadData', {total: 0, rows: []});
                            }
                        });
                        // 默认页面表进来时，双击行操作后，设置回调函数
                        setDefaultDblclick(jq, win);
                        // 默认页面进入时，设置url地址为查询客户的，否则当选择公司数据后，再一次进入组件页面，则会查询公司数据
                        options.queryUrl = BasePath + '/base_setting/customer/list.json';

                        $("#dgSelectorSearchBtn", _this.contents()).on("click", function () {
                            var reqParam = $("#dialog_SarchForm").form("getData");
                            // 组装请求参数
                            $("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
                            $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
                            $("#dialog_SearchDataGrid").datagrid('load');
                        });
                        $("#dgSelectorClearBtn", _this.contents()).on("click", function () {
                            var dataSourceType = $("#dataSourceTypeConditioin").combobox("getValue");
                            $("#dialog_SarchForm").form("clear");
                            $("#dataSourceTypeConditioin").combobox("setValue", dataSourceType);
                        });
                    }
                });
            }
        });
    };

    // 默认页面表进来时，双击行操作后，设置回调函数
    function setDefaultDblclick(jq, win) {
        $("#dialog_SearchDataGrid").datagrid({
            onDblClickRow: function (index, row) {
                _setValue(jq, row);
                win.close();
            }
        });
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        title: '选择客户或公司',
        inputWidth: 140,
        panelWidth: 600,
        panelHeight: 500,
        valueFeild: 'customerNo',//
        textFeild: 'shortName',// store表中的name
        inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
        inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
        href: BasePath + '/plugin_page/searchCustomerOrCompany',
        queryUrl: BasePath + '/base_setting/customer/list.json',
        enableCloseButton: false,
        isFrame: false,
        readonly: true
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        if (op.callback) {
            op.callback();
        } else {
            $("#" + op.inputValueFeild).val(data[op.valueFeild]);
            $("#" + op.inputNameFeild).val(data[op.textFeild]);
        }
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $("#" + op.valueFeild).val();
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).iptSearch("disable");
    };

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).iptSearch("enable");
    };

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputValueFeild).val("");
        $("#" + op.inputNameFeild).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 结算公司和客户弹出框插件 */
(function ($) {
    var _name = 'customerMultiDataSource';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        if (!options.multiple) {
            $(jq).iptSearch({
                width: options.inputWidth,
                readonly: options.readonly,
                clickFn: function () {
                    dgSelector({
                        title: options.title,
                        width: options.panelWidth,
                        height: options.panelHeight,
                        isFrame: options.isFrame,
                        href: options.href,
                        queryUrl: options.queryUrl,
                        enableCloseButton: options.enableCloseButton,
                        fn: function (data) {
                            if (options.callback) {
                                options.callback(data);
                            } else {
                                _setValue(jq, data);
                            }
                        }
                    });
                }
            });
        } else {
            $(jq).iptSearch({
                width: options.inputWidth,
                readonly: options.readonly,
                clickFn: function () {
                    ygDialog({
                        title: options.title,
                        href: options.multipleHref,
                        width: options.panelWidth,
                        height: options.panelHeight,
                        isFrame: options.isFrame,
                        enableCloseButton: options.enableCloseButton,
                        onLoad: function (win, content) {
                            var _this = $(this);
                            $("#dialog_SearchDataGrid").datagrid({
                                onDblClickRow: function (index, row) {
                                    if (options.callback) {
                                        options.callback();
                                    } else {
                                        _setValue(jq, row);
                                    }
                                    win.close();
                                }
                            });
                            $("#dgSelectorSearchBtn", _this.contents()).on("click", function () {
                                var reqParam = $("#dialog_SarchForm").form("getData");
                                // 组装请求参数
                                $("#dialog_SearchDataGrid").datagrid('options').queryParams = reqParam;
                                $("#dialog_SearchDataGrid").datagrid('options').url = options.queryUrl;
                                $("#dialog_SearchDataGrid").datagrid('load');
                            });
                            $("#dgSelectorClearBtn", _this.contents()).on("click", function () {
                                $("#dialog_SarchForm").form("clear");
                            });
                            $("#dgSelectorSureBtn", _this.contents()).on("click", function () {
                                var checkedRows = $("#dialog_SearchDataGrid").datagrid("getChecked");
                                var dataNos = "", dataNames = "", data = {};
                                $.each(checkedRows, function (index, item) {
                                    dataNos += item.code;
                                    dataNames += item.name;
                                    if (index < checkedRows.length - 1) {
                                        dataNos += ",";
                                        dataNames += ",";
                                    }
                                });
                                data.code = dataNos;
                                data.name = dataNames;
                                if (options.callback) {
                                    options.callback(data);
                                } else {
                                    _setValue(jq, data);
                                }
                                win.close();
                            });
                        }
                    });
                }
            });
        }
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        title: '选择客户',
        inputWidth: 140,
        panelWidth: 500,
        panelHeight: 'auto',
        valueFeild: 'code',//
        textFeild: 'name',// store表中的name
        inputValueFeild: 'customerNo',// 输入字段编码no 默认‘’
        inputNameFeild: 'customerName',// 输入字段名称name 默认‘’
        href: BasePath + '/plugin_page/searchCustomerMultiDataSource',
        multipleHref: BasePath + '/plugin_page/multiSearchCustomerAndCompany',
        queryUrl: BasePath + '/base_setting/customer/query_multi_data_source',
        enableCloseButton: false,
        multiple: false,
        isFrame: false,
        readonly: true
    };

    // 设置值的方法
    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputValueFeild).val(data[op.valueFeild]);
        $("#" + op.inputNameFeild).val(data[op.textFeild]);
    }

    // 获取插件的值
    function _getValue(jq, fieldName) {
        var op = $.data(jq, _name).options;
        if (typeof fieldName != 'undefined' && fieldName != null && fieldName != '') {
            return $("#" + fieldName).val();
        }
        return $("#" + op.valueFeild).val();
    };

    // 插件不可用的方法
    function _disable(jq) {
        $(jq).attr("readonly", true).iptSearch("disable");
    };

    // 插件可用的方法
    function _enable(jq) {
        $(jq).attr("readonly", true).iptSearch("enable");
    };

    // 清空查询精灵的方法
    function _clear(jq) {
        var op = $.extend({}, $.fn[_name].defaults, $.data(jq, _name).options);
        $("#" + op.inputValueFeild).val("");
        $("#" + op.inputNameFeild).val("");
    };

    // 对外暴露的方法
    $.fn[_name].methods = {
        options: function (jq) {
            // return $.data(jq[0], _name).options;
            return $.extend({}, $.fn[_name].defaults, $.data(jq[0], _name).options);
        },
        setValue: function (jq, data) {
            _setValue(jq[0], data);
        },
        getValue: function (jq, fieldName) {
            return _getValue(jq[0], fieldName);
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 货管单位插件 */
(function ($) {
    var _name = 'orderUnitForCheck';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var $this = this;
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            $(this).bind("click", function () {
                // 插件渲染方法
                render($this, opts);
            })
        });
    };

    function render(jq, op) {
        dgSelector({
            title: '选择货管单位',
            width: op.width,
            height: op.height,
            isFrame: false,
            href: op.href,
            queryUrl: op.url,
            fn: function (data) {
                var dataNos = '';
                var dataNames = '';
                var result = {};
                if (typeof data.length == 'undefined') {
                    dataNos = data[op.noField];
                    dataNames = data[op.nameField];
                } else {
                    $.each(data, function (index, item) {
                        dataNos += item[op.noField] + ",";
                        dataNames += item[op.nameField] + ",";
                    });
                    if (dataNos) {
                        dataNos = dataNos.substring(0, dataNos.lastIndexOf(','));
                    }
                    if (dataNames) {
                        dataNames = dataNames.substring(0, dataNames.lastIndexOf(','));
                    }
                }
                result[op.noField] = dataNos;
                result[op.nameField] = dataNames;
                _setValue(jq, result);
            }
        });
    };

    function _setValue(jq, data) {
        if (typeof data == 'undefined' || data == null) {
            return;
        }
        var op = $.data(jq, _name).options;
        $("#" + op.inputNoField).val(data[op.noField]);
        $("#" + op.inputNameField).val(data[op.nameField]);
    }

    function _getValue(jq, field) {
        var op = $.data(jq, _name).options;
        if (op.data) {
            var value;
            if (isNotBlank(field)) {
                return op.data[field];
            } else {
                value = op.data[op.noField];
            }
            if (value) {
                return value;
            }
        }
    }

    function _getData(jq) {
        var op = $.data(jq, _name).options;
        return op.data;
    }

    function _clear(jq) {
        var op = $.data(jq, _name).options;
        if (op.data != undefined) {
            op.data[op.noField] = null;// 清空查询精灵的value
            op.data[op.nameField] = null;// 清空查询精灵的name
            _setValue(jq, op);
        }
    }

    // 默认的初始化参数
    $.fn[_name].defaults = {
        title: '选择货管单位',
        width: 700,
        height: 500,
        noField: 'orderUnitNo',// 字段编码no
        nameField: 'name',// 字段名称name
        inputNoField: '',// 输入字段编码no 默认‘’
        inputNameField: '',// 输入字段名称name 默认‘’
        isFrame: false,
        href: BasePath + '/plugin_page/orderUnitForCheck',
        url: BasePath + '/order_unit/list.json'
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    $.fn[_name].methods = {
        options: function (jq) {
            return $.data(jq[0], _name).options;
        },
        setValue: function (jq, param) {
            _setValue(jq[0], param);
        },
        getValue: function (jq) {
            return _getValue(jq[0]);
        },
        getNameValue: function (jq) {
            return _getValue(jq[0], 'getNameValue');
        },
        clear: function (jq, param) {
            $.each(jq, function (index, item) {
                _clear(item);
            });
        },
        disable: function (jq) {
            $.each(jq, function (index, item) {
                _disable(item);
            });

        },
        enable: function (jq) {
            $.each(jq, function (index, item) {
                _enable(item);
            });
        }
    };
    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


/** 业务类型下拉框插件 */
(function ($) {
    var _name = 'businessType';
    // 自定义插件
    $.fn[_name] = function (options, param) {
        if (typeof options == "string") {
            return $.fn[_name].methods[options](this, param);
        }
        ;
        options = options || {};
        return this.each(function () {
            var opts = {};
            var data = $.data(this, _name);
            if (data) {
                opts = $.extend(data.options, options);
            } else {
                opts = $.extend({}, $.fn[_name].defaults, $.fn[_name].parseOptions(this), options);
                $.data(this, _name, {
                    options: opts
                });
            }
            // 插件渲染方法
            render(this, opts);
        });
    };

    // 页面渲染及回调设值
    function render(jq, options) {
        $(jq).combotree({
            width: options.inputWidth,
            required: options.required,
            panelWidth: options.panelWidth,
            panelHeight: options.panelHeight,
            onHidePanel: function () {
                checkedValue = $(jq).combotree('tree').tree('getChecked');
                var arrayObj = new Array();
                $.each(checkedValue, function (index, row) {
                    var isLeaf = $(jq).combotree('tree').tree('isLeaf', row.target);
                    if (isLeaf) {
                        arrayObj.push(row.id);
                    }
                });
                $(jq).combotree('setValues', arrayObj);
            }
        });
        $(jq).combotree('tree').tree({
            checkbox: true,
            data: options.data,
            onSelect: function (node) {
            },
            onCheck: function (node) {
            },
            onClick: function (node) {
            }
        });
    };

    // 默认的初始化参数
    $.fn[_name].defaults = {
        inputWidth: 130,
        required: false,
        data: [{
            id: 1,
            text: '门店销售',
            state: "closed",
            children: [{
                id: 11,
                text: '正常销售'
            }, {
                id: 12,
                text: '退换货'
            }]
        }, {
            id: 2,
            text: '地区批发',
            state: "closed",
            children: [{
                id: 21,
                text: '批发-销售'
            }, {
                id: 22,
                text: '批发-过季退货'
            }, {
                id: 23,
                text: '批发-客残退货'
            }, {
                id: 24,
                text: '批发-店出'
            }, {
                id: 25,
                text: '批发-召回退货',
                organTypeNo: 'U010102'
            }]
        }, {
            id: 3,
            text: '跨区调货',
            state: "closed",
            children: [{
                id: 31,
                text: '调货出库'
            }]
        }, {
            id: 4,
            text: '其他出库',
            state: "closed",
            children: [{
                id: 41,
                text: '借用出库-内部'
            }, {
                id: 42,
                text: '借用出库-外部'
            }, {
                id: 43,
                text: '客残销售-总部承担'
            }, {
                id: 44,
                text: '客残出库（新）'
            }, {
                id: 45,
                text: '调货出库'
            }, {
                id: 46,
                text: '采购退货',
                organTypeNo: 'U010102'
            }]
        }, {
            id: 5,
            text: '内购销售',
            state: "closed",
            children: [{
                id: 51,
                text: '门店-销售'
            }, {
                id: 52,
                text: '门店-退换货'
            }, {
                id: 53,
                text: '团购-出库'
            }, {
                id: 57,
                text: '团购-退货'
            }, {
                id: 54,
                text: '客残销售-地区承担'
            }, {
                id: 55,
                text: '内销-差异'
            }, {
                id: 58,
                text: '内销-盘差'
            }, {
                id: 59,
                text: '内销-客残'
            }, {
                id: 56,
                text: '报废'
            }, {
                id: 510,
                text: '领用出库-物料'
            }]
        }]
    };

    // 将声明式定义属性data-options转化为options
    $.fn[_name].parseOptions = function (target) {
        return $.parser.parseOptions(target, ['data']);
    };

    // 将自定义的插件加入 easyui 的插件组
    $.parser.plugins.push(_name);
})(jQuery);


(function ($) {
    var tabIndex = 0;

    // 用于容错处理的空函数
    var _fn = function () {
    };

    /**
     * 快捷键事件处理
     *
     * @t EasyUI对象
     * @tt 绑定快捷键的对象
     */
    var _keyboard = function (t) {
        _keyboard.init(t);
    };

    // 初始化快捷键
    _keyboard.init = function (t, _key, _fn) {
        var options = t.data('keyboard').options;
        if (options.type == 'form') {
            var editor = getEditors(t).eq(0);
            setFocus(editor);
            bindEvent(t, 'return', this.keyHandler.right);
            bindEvent(t, 'right', this.keyHandler.right);
            bindEvent(t, 'left', this.keyHandler.left);
        } else if (options.type == 'grid') { // grid
            bindEvent(t, 'up', this.keyHandler.up);
            bindEvent(t, 'down', this.keyHandler.down);
            bindEvent(t, 'return', this.keyHandler.right);
            bindEvent(t, 'right', this.keyHandler.right);
            bindEvent(t, 'left', this.keyHandler.left);
        } else {
            // TODO
        }

        function bindEvent(t, _key, _fn) {
            t.hotKeys({type: 'keydown', key: _key, fn: _fn});
        }
    };

    // 方向键事件
    _keyboard.keyHandler = {
        up: function (e) {
            e.preventDefault();
            var t = $(this), grid = $("table.easyui-datagrid:hidden", t), target = $(e.target),
                regExp_combo = /combo/ig, regExp_date = /Wdate/ig;
            if (target.is("input") && (
                    regExp_combo.test(target.attr("class")) ||
                    regExp_date.test(target.attr("class"))
                )) {
                e.stopPropagation();
            } else {
                doUp();
            }

            function doUp() {
                var rowIndex = _getSelected(t);
                var rows = grid.datagrid('getRows').length || 1;
                var lastIndex = rows - 1;
                var idx = rowIndex == 0 ? lastIndex : rowIndex - 1;
                grid.datagrid('selectRow', idx);
            }
        },
        down: function (e) {
            e.preventDefault();
            var t = $(this), grid = $("table.easyui-datagrid:hidden", t), target = $(e.target),
                regExp_combo = /combo/ig, regExp_date = /Wdate/ig;
            if (target.is("input") && (
                    regExp_combo.test(target.attr("class")) ||
                    regExp_date.test(target.attr("class"))
                )) {
                e.stopPropagation();
            } else {
                doDown();
            }

            function doDown() {
                var rowIndex = _getSelected(t);
                var rows = grid.datagrid('getRows').length || 1;
                var lastIndex = rows - 1;
                var idx = rowIndex == lastIndex ? 0 : rowIndex + 1;
                grid.datagrid('selectRow', idx);
            }
        },
        // 光标左移
        left: function () {
            var t = $(this), options = t.keyboard('options');
            if (options.type == 'grid') {
                t = t.find(".datagrid-view2>.datagrid-body");
            }
            var editors = getEditors(t);
            var fEditor = getFocusEditor(t);
            if (fEditor.hasClass('validatebox-invalid')) {
                autoFocus(fEditor, options);
                return;
            }
            var idx = (editors.index(fEditor[0]) - 1) || 0;
            autoFocus(editors.eq(idx), options);
        },
        // 光标右移
        right: function () {
            var t = $(this), options = t.keyboard('options');
            if (options.type == 'grid') {
                t = t.find(".datagrid-view2>.datagrid-body");
            }
            var editors = getEditors(t);
            var fEditor = getFocusEditor(t);
            if (fEditor.hasClass('validatebox-invalid')) {
                autoFocus(fEditor, options);
                return;
            }
            var idx = editors.index(fEditor[0]) || 0;
            autoFocus(editors.eq(++idx), options);
// var firstColumn = null;
// $.each(columns, function(index, item) {
// if(item.type != 'checkbox' && item.type != 'hidden') {
// firstColumn = item;
// return;
// }
// });
// alert(JSON.stringify(firstColumn));
// var idx = editors.index(fEditor[0])||0;
// autoFocus(editors.eq(++idx), options);
//
// var fields = $("#shopBalanceDeductDataGrid").datagrid('getColumnFields');
// var field= fields[2];
// var editor = $("#shopBalanceDeductDataGrid").datagrid('getEditor', {
// 'index': 0,
// 'field': field
// });
// var target = editor.target;
// var inputNoField = editor.target.attr("inputNoField");
// var noFieldValIsNull = false;
// if(inputNoField) {
// noFieldValIsNull = ($("#"+inputNoField).val() == null
// || $("#"+inputNoField).val() == '') ? true : false;
// }
// if(noFieldValIsNull || fEditor.hasClass('validatebox-invalid')) {
// autoFocus(fEditor, options);
// return;
// }
// var idx = editors.index(fEditor[0])||0;
// autoFocus(editors.eq(++idx), options);
        },
        enter: function (t) {
            // TODO
        }
    };

    // 表单处理
    _keyboard.formHandler = function (t) {
        // TODO
    };

    // 表格处理
    _keyboard.gridHandler = function (t, tt) {
        // TODO
    };

    // Tab键事件
    _keyboard.tabHandler = {};

    /**
     * 获取可编辑的输入框
     */
    function getEditors(t) {
        var editors = $("input:not(:disabled,:hidden,[readonly])", t);
        /*
         * var editorArr = new Array(); $.each(editors, function(i, v){ var
         * editor = $(v); if(editor.hasClass("combo-f")){
         * editorArr.push(editor.siblings(".combo").find("input")); return true; }
         * editorArr.push(v); });
         */
        return $(editors);
    }

    /**
     * 获取当前有焦点的输入框
     */
    function getFocusEditor(t) {
        var editor = $("input:focus", t);
        return editor;
    }

    /**
     * 输入框选中并聚焦
     */
    function autoFocus(jq, param) {
        setTimeout(function () {
            jq.select().focus();
            // 日历框自动触发
            if (jq.hasClass("Wdate")) {
                jq.trigger('click');
            }
            // 触发最后一个输入框的处理事件
            if (jq.length == 0 && param && param.lastFn) {
                param.lastFn.call(this);
            }
        }, 100);
    }

    /**
     * 选中一行
     */
    function selectRow(grid, index) {
        grid.datagrid('clearSelections').datagrid('selectRow', index);
    }

    /**
     * 获取一行
     */
    function _getSelected(t) {
        var tr = $("tr.datagrid-row-selected", t);
        var idx = tr.attr("datagrid-row-index");
        return parseInt(idx);
    }

    function bind(target) {
        var t = $(target), options = t.data('keyboard').options;
        t.attr("id") ? _fn() : t.attr("id", "_easyui_keyboard_" + tabIndex++);
        t.attr("tabindex") ? _fn() : t.attr('tabindex', tabIndex);
        _keyboard(t);
    }

    $.fn.keyboard = function (options, param) {
        if (typeof options == 'string') {
            return $.fn.keyboard.methods[options](this, param);
        }
        options = options || {};
        return this.each(function () {
            if (!$.data(this, 'keyboard')) {
                $.data(this, 'keyboard', {
                    options: $.extend({}, $.fn.keyboard.defaults, $.fn.keyboard.parseOptions(this), options)
                });
            }
            bind(this);
        });
    };

    $.fn.keyboard.parseOptions = function (target) {
        var options = $.parser.parseOptions(target);
        return $.extend({}, options);
    };

    // keyboard默认方法
    $.fn.keyboard.methods = {
        options: function (jq, param) {
            return jq.data('keyboard').options;
        }
    };

    // keyboard默认属性和事件
    $.fn.keyboard.defaults = {
        type: "",
        lastFn: function () {
        }
    };

    $.parser.plugins.push("keyboard");
})(jQuery);


/**
 * 进度条弹出
 */
$(function () {
    var defaultOptions = {};

    var panel = function (options, params) {
        this.params = params;
        var commonUrl = BasePath + "/common/export/";
        this.options = options;
        var self = this;
        window.onunload = function () {
            console.log('unload');
            if (self.ticket)
                $.ajax({
                    url: commonUrl + "clear?ticket=" + self.ticket,
                    async: true
                });
        };
        this.open = function () {
            this.showPanel();
            onStart();
        };
        this.close = function () {
            if (self.stoped = true)
                self.controls.panel.window('close');
        };
        this.stop = function () {
            self.info("正在尝试停止导出...");
            $.ajax({
                url: commonUrl + "stop?ticket=" + self.ticket,
                async: true
            }).always(function () {
                self.stoped = true;
                self.controls = null;
                self.close();
            })
        };
        function onStart() {
            self.controls.bntOk.linkbutton({text: '停止'});
            self.start();
        }

        this.start = function () {
            this.stop = false;
            this.ticket = "";
            self.timer.start();
            var params = $.extend({async: true}, self.params);
            setTimeout(function () {
                $.post(BasePath + self.options.exportUrl, params).then(function (data) {
                    self.ticket = data;
                }).then(function (data) {
                    setTimeout(queryStatus, 500);
                });
            }, 500);
        };
        this.showPanel = function () {
            if (!self.controls) {
                createPanel();
                var options = {
                    iconCls: 'icon-export', modal: true, closable: false, minimizable: false,
                    maximizable: false, resizable: false, collapsible: false,
                    title: '导出',
                    width: 450,
                    height: 200
                };
                self.controls.panel.window(options);
            } else {
                self.controls.panel.window('open');
            }
            self.controls.prg.progressbar('setValue', 0);
        };

        function queryStatus() {
            var stoped = false;
            var task = function () {
                $.get(commonUrl + "status?ticket=" + self.ticket).then(function (result) {
                    var data = $.parseJSON(result);
                    if (data.status == 1) {
                        setTimeout(task, 5000);
                        refreshStatus(data);
                    }
                    else if (data.status == -1) {
                        showWarn('导出过程中存在错误.');
                        self.stoped = true;
                        self.close();
                    } else if (data.status == 2) {
                        self.stoped = true;
                        refreshStatus(null, true);
                        setTimeout(downloadFile, 200);
                    }
                });
            };
            setTimeout(task, 50);
        }

        function refreshStatus(data, finished) {
            if (finished) {
                self.controls.prg.progressbar('setValue', 100);
                return;
            }
            var count = Math.max(self.params.count, 1);
            var index = Math.min(data.index, count);
            var value = parseInt((index / count) * 100);
            self.controls.prg.progressbar('setValue', value);
            self.controls.info.text(data.index + "/" + count);
        }

        function downloadFile() {
            var url = commonUrl + "file";
            var inputs = '<input type="hidden" name="ticket" value="' + self.ticket + '"/>';
            self.close();
            jQuery('<form action="' + url + '" method="get">' + inputs + '</form>').appendTo('body').submit().remove();
        }

        function createPanel() {
            var panel = '<div>'
                + '<div class="excel-export" title="导出" >'
                + '<div style="padding:10px">' +
                '<div style="height: 24px;"><span class="timer"></span><span class="info"></span></div>'
                + '<div class="progress">'
                + '<div class="easyui-progressbar p" style="width:410px;"></div>'
                + '</div><label class="export-status"></label></div>'
                + '<div class="excel-buttons" >'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-ok" style="margin: 2px">导出</a>'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-cancel"  style="margin: 2px">关闭</a>'
                + '</div>'
                + '</div></div></div>';

            var p = $(panel).appendTo($('body'));
            var prg = $('.easyui-progressbar', p);
            $.parser.parse(p);
            self.prg = prg;

            $('.excel-cancel', p).bind('click', function () {
                self.close();
            });
            $('.excel-ok', p).bind('click', function () {
                if (self.stoped) {
                    onStart();
                }
                else {
                    $.messager.confirm('导出', '确定要停止导出么?', function (r) {
                        if (r)
                            self.stop();
                    });
                }
            });

            self.controls = {
                prg: prg,
                panel: p.first(),
                info: $('.info', p),
                timer: $('.timer', p),
                bntOk: $('.excel-ok', p)
            };
            self.timer = new timer(self.controls.timer);
        }
    };

    var timer = function (element) {
        var now = new Date();
        this.interval = null;

        this.start = function () {
            now = new Date();
            this.interval = window.setInterval(function () {
                $(element).text(formatTime(new Date() - now));
            }, 1000);
            return this;
        };

        this.stop = function () {
            window.clearInterval(this.interval);
        };

        var formatTime = function (unixTimestamp) {
            var dt = new Date(unixTimestamp);

            var hours = dt.getHours();
            var minutes = dt.getMinutes();
            var seconds = dt.getSeconds();

            // the above dt.get...() functions return a single digit
            // so I prepend the zero here when needed
            hours -= 8;
            if (hours < 10)
                hours = '0' + hours;

            if (minutes < 10)
                minutes = '0' + minutes;

            if (seconds < 10)
                seconds = '0' + seconds;

            return hours + ":" + minutes + ":" + seconds;
        };
        // return
    };

    $.fas.ExportPanel = panel;
});


/**
 * 进度条弹出
 */
$(function () {
    var defaultOptions = {};

    var panel = function (options, params) {
        this.params = params;

        var commonUrl = BasePath + "/common/export/";
        if (options.basePath)
            commonUrl = options.basePath + "/common/export/";
        this.options = options;
        var self = this;
        window.onunload = function () {
            if (self.ticket)
                $.ajax({
                    url: commonUrl + "clear?ticket=" + self.ticket,
                    async: true
                });
        };
        this.open = function () {
            this.showPanel();
            onStart();
        };
        this.close = function () {
            if (self.stoped = true) {
                self.controls.panel.window('close');
                if ($.isFunction(self.options.onclose))
                    self.options.onclose();
            }
        };
        this.stop = function () {
            self.info("正在尝试停止...");
            $.ajax({
                url: commonUrl + "stop?ticket=" + self.ticket,
                async: true
            }).always(function () {
                self.stoped = true;
                self.controls = null;
                self.close();
            })
        };

        function onStart() {
            // self.controls.bntOk.linkbutton({text: '停止'});
            self.start();
        }

        this.start = function () {
            this.stop = false;
            this.ticket = self.options.ticket;
            self.timer.start();
            var params = $.extend({async: true}, self.params);
            setTimeout(function () {
                $.post(self.options.url, params).then(function (data) {
                    self.ticket = data.ticket;
                }).then(function () {
                    setTimeout(queryStatus, 500);
                }).fail(function () {
                    showWarn('执行中存在错误.');
                    self.close();
                });
            }, 500);
        };

        this.showPanel = function () {
            if (!self.controls) {
                createPanel();
                var options = {
                    iconCls: 'icon-export', modal: true, closable: false, minimizable: false,
                    maximizable: false, resizable: false, collapsible: false,
                    title: self.options.title,
                    width: 450,
                    height: 200
                };
                self.controls.panel.window(options);
            } else {
                self.controls.panel.window('open');
            }
            self.controls.prg.progressbar('setValue', 0);
        };

        function queryStatus() {
            var task = function () {
                $.post(commonUrl + "status", {ticket: self.ticket}).then(function (result) {
                    var data = $.parseJSON(result);
                    if (data && data.status == 1) {
                        setTimeout(task, 5000);
                        refreshStatus(data);
                    }
                    else if (!data || data.status == -1) {
                        showWarn('过程中存在错误.');
                        self.stoped = true;
                        self.error = true;
                        self.controls.bntCancel.linkbutton('enable');
                        // self.close();
                    } else if (data && data.status == 2) {
                        self.stoped = true;
                        self.error = false;
                        refreshStatus(null, true);
                        self.close();
                    }
                });
            };
            setTimeout(task, 50);
        }

        function refreshStatus(data, finished) {
            if (finished) {
                self.controls.prg.progressbar('setValue', 100);
                return;
            }
            var count = data.count | self.params.count;
            var count = Math.max(count, 1);
            var index = Math.min(data.index, count);
            var value = parseInt((index / count) * 100);
            self.controls.prg.progressbar('setValue', value);
            var text = `${data.index}/${count} ${data.info}`;
            self.controls.info.text(text);
        }

        function createPanel() {
            var panel = '<div>'
                + `<div class="excel-export" title="${self.options.title}" >`
                + '<div style="padding:10px">' +
                '<div style="height: 24px;"><span class="timer"></span><span class="info"></span></div>'
                + '<div class="progress">'
                + '<div class="easyui-progressbar p" style="width:410px;"></div>'
                + '</div><label class="export-status"></label></div>'
                + '<div class="excel-buttons" >'
                    // + '<a href="javascript:void(0)" class="easyui-linkbutton
                    // excel-ok" style="margin: 2px">导出</a>'
                + '<a href="javascript:void(0)" class="easyui-linkbutton excel-cancel"  style="margin: 2px">关闭</a>'
                + '</div>'
                + '</div></div></div>';

            var p = $(panel).appendTo($('body'));
            var prg = $('.easyui-progressbar', p);
            $.parser.parse(p);
            self.prg = prg;

            $('.excel-cancel', p).bind('click', function () {
                self.close();
            });
            $('.excel-ok', p).bind('click', function () {
                if (self.stoped) {
                    onStart();
                }
                // else {
                // $.messager.confirm('停止', '确定要停止么?', function (r) {
                // if (r)
                // self.stop();
                // });
                // }
            });

            self.controls = {
                prg: prg,
                panel: p.first(),
                info: $('.info', p),
                timer: $('.timer', p),
                bntOk: $('.excel-ok', p),
                bntCancel: $('.excel-cancel', p)
            };
            self.controls.bntCancel.linkbutton('disable');

            self.timer = new timer(self.controls.timer);
        }
    };

    var timer = function (element) {
        var now = new Date();
        this.interval = null;

        this.start = function () {
            now = new Date();
            this.interval = window.setInterval(function () {
                $(element).text(formatTime(new Date() - now));
            }, 1000);
            return this;
        };

        this.stop = function () {
            window.clearInterval(this.interval);
        };

        var formatTime = function (unixTimestamp) {
            var dt = new Date(unixTimestamp);

            var hours = dt.getHours();
            var minutes = dt.getMinutes();
            var seconds = dt.getSeconds();

            // the above dt.get...() functions return a single digit
            // so I prepend the zero here when needed
            hours -= 8;
            if (hours < 10)
                hours = '0' + hours;

            if (minutes < 10)
                minutes = '0' + minutes;

            if (seconds < 10)
                seconds = '0' + seconds;

            return hours + ":" + minutes + ":" + seconds;
        };
        // return
    };

    $.fas.ProgressPanel = panel;
});