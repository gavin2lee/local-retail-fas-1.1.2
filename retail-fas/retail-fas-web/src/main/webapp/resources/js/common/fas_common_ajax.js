var fas_common_ajax = {};

// 公共异步请求
fas_common_ajax.ajaxRequest = function(url, reqParam, callback) {
	$.ajax({
		type : 'POST',
		url : url,
		data : reqParam,
		cache : true,
		dataType : 'json',
		success : callback
	});
};

//公共ajax请求（不是异步，ajax方法执行完之后，再执行后面的js的代码）
fas_common_ajax.ajaxRequestAsync = function(url, reqParam, callback) {
	var returlVal = "";
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  async : false,
		  dataType : 'json',
		  success: function(data) {
			  returlVal = callback(data);
		  }
	});
	return returlVal;
};