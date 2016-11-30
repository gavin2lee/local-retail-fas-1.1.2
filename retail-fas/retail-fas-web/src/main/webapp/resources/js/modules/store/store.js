var store = {};

store.statusData = [{    
    "label":"0",
    "text":"正常", 
    "value":"0→正常" 
},{    
    "label":"1",
    "text":"关闭", 
    "value":"1→关闭"  
}];

store.ajaxRequest = function(url,reqParam,callback){
	$.ajax({
		  type: 'POST',
		  url: url,
		  data: reqParam,
		  cache: true,
		  success: callback
	});
};

store.loadDataGrid = function(node){
	if(!node)return;
	var runCount = 0;
	var queryParams = {storeNoHead:node.id};
    $('#dataGridJG').datagrid({'url':BasePath+'/store/auth_list.json?storeNoHead='+node.id,'title':"机构列表",'pageNumber':1,queryParams:queryParams,
    	onLoadSuccess:function(data){
        	runCount++;
            if(data.total == 0 && runCount<2){
            	// 没有记录的话.表格加载本身
            	queryParams = {storeNo:node.id};
            	$('#dataGridJG').datagrid({'url':BasePath+'/store/auth_list.json?storeNo='+node.id,'title':"机构列表",'pageNumber':1,queryParams:queryParams });
            }
       }	
    });
};

store.loadDetail = function(storeNo){
	var url = BasePath+'/store/get';
	var reqParam={
		   "storeNo":storeNo
     };
	store.ajaxRequest(url,reqParam,function(data){
		$('#dataForm').form('load',data);
	});
};

//查询机构信息
store.searchStore = function(){
	var fromObjStr=convertArray($('#searchForm').serializeArray());
	var queryMxURL=BasePath+'/store/auth_list.json';
    //3.加载明细
    $( "#dataGridJG").datagrid( 'options' ).queryParams= eval("(" +fromObjStr+ ")");
    $( "#dataGridJG").datagrid( 'options' ).url=queryMxURL;
    $( "#dataGridJG").datagrid( 'load' );
    
};

//主要是推荐这个函数。它将jquery系列化后的值转为name:value的形式。
function convertArray(o) { 
	var v = {};
	for ( var i in o) {
		if (typeof (v[o[i].name]) == 'undefined')
			v[o[i].name] = o[i].value;
		else
			v[o[i].name] += "," + o[i].value;
	}
	return JSON.stringify(v);
} 

//清楚查询条件
store.searchStoreClear = function(){
	$('#searchForm').form("clear");
};

store.clearAll = function(){
	$('#dataForm').form("clear");
};

store.initBrand = function(){
	$('#sysNo').combobox({
		 url:BasePath+'/initCache/getLookupDtlsList.htm?lookupcode=SYS_NO&authFlag=true',
	     valueField:"itemvalue",
	     textField:"itemnamedetail",
	     panelHeight:"150"
	  });
	
	$('#sysNoCondition').combobox({
		 url:BasePath+'/initCache/getLookupDtlsList.htm?lookupcode=SYS_NO&authFlag=true',
	     valueField:"itemvalue",
	     textField:"itemnamedetail",
	     panelHeight:"150",
	     loadFilter:function(data){
	    	 var tempData = [];
	    	 tempData[tempData.length] = {itemvalue:'',itemnamedetail:'全选'};
	    	 for(var i=0;i<data.length;i++){
	    		tempData[tempData.length] = data[i];
	    	 }
	    	 return tempData;
	     }
	  });
};

store.initZone = function(){
	$('#zoneNo').combobox({
		url:BasePath+'/store/queryStoreTree.htm?id=-1',
	     valueField:"id",
	     textField:"text",
	     panelHeight:"auto"
	 });
	
	$('#zoneNoCondition').combobox({
		url:BasePath+'/store/queryStoreTree.htm?id=-1',
	     valueField:"id",
	     textField:"text",
	     panelHeight:"auto",
	     loadFilter:function(data){
	    	 var tempData = [];
	    	 tempData[tempData.length] = {id:'',text:'全选'};
	    	 for(var i=0;i<data.length;i++){
	    		tempData[tempData.length] = data[i];
	    	 }
	    	 return tempData;
	     }
	 });
};

store.initStoreType = function(){
	$('#storeType').combobox({
		url:BasePath+'/initCache/getLookupDtlsList.htm?lookupcode=STORE_TYPE',
	     valueField:"itemvalue",
	     textField:"itemnamedetail",
	     panelHeight:"auto"
	  });
};

store.initStoreTypeCondition = function(){
	$('#storeTypeCondition').combobox({
		url:BasePath+'/initCache/getLookupDtlsList.htm?lookupcode=STORE_TYPE',
	     valueField:"itemvalue",
	     textField:"itemnamedetail",
	     panelHeight:"auto",
	     loadFilter:function(data){
	    	 var tempData = [];
	    	 tempData[tempData.length] = {itemvalue:'',itemnamedetail:'全选'};
	    	 for(var i=0;i<data.length;i++){
	    		if(data[i].itemvalue!=11){
	    			tempData[tempData.length] = data[i];
	    		}
	    	 }
	    	 return tempData;
	     }
	  });
};

store.initStatus = function(){
	$('#status').combobox({
	     valueField:"label",
	     textField:"value",
	     data:store.statusData,
	     panelHeight:"auto"
	 });
	
	
	$('#statusCondition').combobox({
	     valueField:"label",
	     textField:"value",
	     data:store.statusData,
	     panelHeight:"auto",
	     loadFilter:function(data){
	    	 var tempData = [];
	    	 tempData[tempData.length] = {label:'',value:'全选'};
	    	 for(var i=0;i<data.length;i++){
	    		tempData[tempData.length] = data[i];
	    	 }
	    	 return tempData;
	     }
	 });
};

store.initDataTree = function(){
	$('#dataTreeId').tree({     
        url:BasePath+'/store/queryStoreTree.htm',  
        onClick : function (node) {
        	if(node.state && node.state=='closed'){
        		$(this).tree('expand', node.target);
        	}else{
        		store.clearAll();
        		store.loadDataGrid(node);
        	}
        },
        onExpand : function (node) {
        	$(this).tree('select', node.target);
        	store.clearAll();
        	store.loadDataGrid(node);
        }
    });
};

exportExcel=function(){
	exportExcelBaseInfo('dataGridJG','/store/auth_do_export.htm','机构管理信息导出');
};

$(document).ready(function(){
	store.initBrand();
	store.initStoreType();
	store.initZone();
	store.initStatus();
	store.initStoreTypeCondition();
	store.initDataTree();
	
	var queryParams = {storeNoHead:'-1'};
	$('#dataGridJG').datagrid({'url':BasePath+'/store/auth_list.json','title':"机构列表",'pageNumber':1,queryParams:queryParams });
	
});