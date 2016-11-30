var billInitShopBalance = new Object();

//初始化
$(function() {
	billInitShopBalance.initSelectZone();
//	billInitShopBalance.initSelectbizCity();
//	billInitShopBalance.initSelectmall();
//	billInitShopBalance.initSelectshop();
//	billInitShopBalance.initSelectmonth();  //初始结算月
	billInitShopBalance.initSelectbrand();
});

billInitShopBalance.initSelectZone= function() {
	fas_common.initCombox({
		id : 'zoneNo',
		url : "/zone_info/get_biz",
		valueId : 'zoneNo',
		textId : 'name'
	});
};

billInitShopBalance.initSelectbizCity= function() {
	fas_common.initCombox({
		id : 'bizCityNo',
//		url : "/biz_city/get_biz",
		url : "/organ/get_biz",
		valueId : 'organCode',
		textId : 'name'
	});
};


billInitShopBalance.selectOrgan = {// 明细行选择管理城市 
	    clickFn:function(){
	    	dgSelector({
	    		width : 580,
	    		height : 450,
	    		title:"选择管理城市 ",
				href : BasePath + "/common_util/toSearchOrgan",
	    		fn : function(data, rowIndex) {
	    			selectObjUtil.selectorCallback(data,'organNo','name','organNo','organName');
	    		}
	    	});
	    }
};

billInitShopBalance.initSelectmall= function() {
	fas_common.initCombox({
		id : 'mallNo',
		url : "/mall/get_biz",
		valueId : 'mallNo',
		textId : 'name'
	});
};
//billInitShopBalance.initSelectshop= function() {
//   $('#shopNo').combobox({    
//		url : BasePath +"/shop/get_biz",
//		valueField : 'shopNo',
//		textField : 'shortName',
//		multiple:true
//	});
//}

billInitShopBalance.initSelectshop= function() {
	billInitShopBalance.initCombox({
		id : 'shopNo',
		url : "/shop/get_biz",
		valueId : 'shopNo',
		textId : 'shortName',
		multiple:true
	});
};

billInitShopBalance.initSelectmonth= function() {
	fas_common.initCombox({
		id : 'month',
		url : "/shop_balance_date/get_biz",
		valueId : 'month',
		textId : 'month'
	});
};

billInitShopBalance.initSelectbrand= function() {
	if($('#brandNo').length > 0){
	   fas_common.initCombox({
			id : 'brandNo',
			url : "/brand/get_biz",
			valueId : 'brandNo',
			textId : 'name',
	   });
	}
};


//初始化combox组件
billInitShopBalance.initCombox = function(params) {
	$("#" + params.id).combobox({
		url : BasePath + params.url,
		width : params.width || 140,
	    panelHeight : params.height || 200,
	    valueField : params.valueId,    
	    textField : params.textId,
	    multiple : params.multiple,
	    onChange : params.changeFun || function() {},
	    onSelect : params.selectFun || function() {},
	    editable : params.editable || false 
	});
};