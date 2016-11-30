/**
 * Fas Home
 * Author: wu.h1
 * Date: 2015/7/3
 */

fasHome = {
    init : function(){
        this.bindEvents();
    },
    bindEvents : function(){
        var _self = this;

        $('.func-delete').live('click',function(){
            $(this).toggleClass('active');
            $('.fas-funcs').find('.fas-func-item').toggleClass('active');
        });

        $('.fas-func-add .fas-func-item').live('click',function(){
            $(this).toggleClass('selected');
        });

        $(window).resize(function(){
            _self.resize();
        });
        _self.resize();
    },
    tabNav : function(){
        $('.sp-tab-nav li:not(.disabled)').click(function() {
            $(this).siblings().removeClass('active');
            $(this).toggleClass('active');
        });
    },
    resize : function(){
        setTimeout(this.resizeLayout,300);
    },
    resizeLayout : function(){
        $('.finance-column').each(function(){
            var columnWidth = $(this).width();
            var panel1Height = $(this).find('.fas-home-panel').outerHeight();
            var containerHeight = $('#mainTabs').find('.tabs-panels').height();
            var panel2 = $(this).children('.panel').eq(1);
            var panel2Height = containerHeight - panel1Height - 60;
            var panel2ContentHeight = panel2Height - 43;

            $(this).find('.fas-home-panel').panel('resize', {width:columnWidth});
            panel2.find('.fas-home-panel').panel('resize', {height:panel2Height});
            $('.fas-tasks').height(panel2ContentHeight);
        });
    }
}

fasHome.init();