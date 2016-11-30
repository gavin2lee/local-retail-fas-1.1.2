$(function () {
	var date = new Date();
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	
	$("#receiveDateStart").val(firstDay.format("yyyy-MM-dd"));
	$("#receiveDateEnd").val(lastDay.format("yyyy-MM-dd"));
});