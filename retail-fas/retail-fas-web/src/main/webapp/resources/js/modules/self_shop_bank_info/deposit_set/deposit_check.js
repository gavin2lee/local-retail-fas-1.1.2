var depositCheck = new Object();

$(function() {
	//绑定店铺通用查询
	$("#shopName").filterbuilder({
        type:'organ',
        organFlag: 2,
        roleType:'bizCity', 
        onSave : function(result) { 
        	var value = $(this).filterbuilder('getValue');
        	$("#shopNo").val(value);
        }
    });
});

var changeFirstColor = function(value,row,index){
	//根据shop_no 获取店铺存现设置信息
	var params = {shopNo:row.shopNo};
	var result = null;
	ajaxRequestAsync( BasePath + '/deposit_set/getDepositSet',params,function(res){
		result = res;
	});
	
	if(null != result){
		if(value > result.beyondLastDepositDate){
			return 'color:red;';
		}
	}
}

var changeSecondColor = function(value,row,index){
	//根据shop_no 获取店铺存现设置信息
	var params = {shopNo:row.shopNo};
	var result = null;
	ajaxRequestAsync( BasePath + '/deposit_set/getDepositSet',params,function(res){
		result = res;
	});
	
	if(null != result){
		if(value > result.depositDiff){
			return 'color:red;';
		}
	}
}