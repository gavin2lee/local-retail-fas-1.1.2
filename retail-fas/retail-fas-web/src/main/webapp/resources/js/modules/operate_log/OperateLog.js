function openDetail(params) {
	var urlParams = "";
	if(typeof params != 'undefined' && params != null) {
		urlParams += "?dataNo="+params.dataNo+"&moduleNo="+params.moudleNo;
	}
	dgSelector({
		title: "日志明细", 
		href: BasePath + "/operate_log/operate_detail" + urlParams, 
		queryUrl: BasePath + "/operate_log/list.json",
		width: 600, 
		height: 500, 
		isFrame: false
	});
};