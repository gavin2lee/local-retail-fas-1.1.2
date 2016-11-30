var wholesaleController ;

function WholesaleController(){
	
    this.curRowIndex = -1;
	var _self = this;
	
    if(typeof WholesaleController.prototype._initialized == 'undefined'){
    	
        WholesaleController.prototype.init = function(modulePath, dtlModulePath){
        	_self.modulePath = modulePath;
        	_self.dtlModulePath = dtlModulePath;
        };
        
    	WholesaleController.prototype.initPage = function(){// 页面初始化
    		_self.addMainTab(); 
    		_self.initDtlTab();
        };
    	
    	WholesaleController.prototype.loadDetail = function(rowIndex, rowData) {// 点击切换到明细
    		_self.clearMainData();
    		_self.curRowIndex = rowIndex;
    		_self.loadData(rowData);
    		_self.resetEditClass();
    		returnTab('mainTab', '单据明细');
    	};
    	
    	WholesaleController.prototype.loadData = function(rowData){// 加载数据
    		_self.loadMainData(rowData);
    		_self.loadDtlListData();
    		returnTab('dtlTab','单据明细');
        };
    	
    	WholesaleController.prototype.loadMainData = function(rowData){// 加载表头数据
    		$('#mainDataForm').form('load', rowData);
        };
    	
    	WholesaleController.prototype.clear = function(){// 清空
    		$('#searchForm').form("clear");
    		$('#companyNoCondition').val('');
    		$('#multiCustomerNo').val('');
    	};

    	WholesaleController.prototype.search = function(){// 查询
    	  if($('#searchForm').length > 0){
    		  var params = $('#searchForm').form('getData');
    		  var isHq = $("#isHq").val();
    		  params.queryCondition = $('#queryCondition').val();
    		  var url = _self.modulePath + '/list.json?isHq='+isHq;
        	  $( '#mainDataGrid').datagrid( 'options' ).queryParams= params;
        	  $( '#mainDataGrid').datagrid( 'options' ).url= url;
        	  $( '#mainDataGrid').datagrid( 'load' );
    	  }
    	};
    	
    	WholesaleController.prototype.addMainTab = function() {// 初始化单据查询tab
    		var isHq = $("#isHq").val();
    		var url = '/fas/wholesale_customer_remaining_sum/listTab?isHq='+isHq;
    		$('#mainTab').tabs('add', {
    			title : '单据列表',
    			selected : false,
    			closable : false,
    			href : url ,
    			onLoad : function(panel) {
    				common_util.initPage('searchForm');
    			}
    		});
    	};

    	WholesaleController.prototype.resetEditClass = function(){// 重置编辑样式
    		$("#mainDataForm").find("input").attr("readonly",true).addClass("readonly");
    		$("#mainDataForm").find("input[singleSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[multiSearch]").combogrid('disable');
    		$("#mainDataForm").find("input[combobox]").combobox('disable');
    		$("#mainDataForm").find("input[class*=easyui-datebox]").datebox('disable');
			$('#remark').removeAttr("readonly").removeClass("readonly");
    	};
    	
    	WholesaleController.prototype.clearMainData = function(){// 清空表头数据
    		$('#mainDataForm').form("clear");
    	};
		
		WholesaleController.prototype.doExport = function (dataGridId, exportUrl, excelTitle, otherParams){//导出
			var $dg = $('#'+dataGridId);
			var params=$dg.datagrid('options').queryParams;
			var columns=$dg.datagrid('options').columns;
			var v_pageNumber = $dg.datagrid('options').pageNumber;//当前页号
			var v_pageSize = $dg.datagrid('options').pageSize;//每页多少行记录
			
			var columnsNew = [];
			$.each(columns,function(index,item){
				var dataArray = [];
				var i = 0;
				$.each(item,function(rowIndex,rowData){
					if(rowData.hidden){
						return ;
					}
					var v_object = {};
					v_object.field = rowData.field;
					v_object.title = rowData.title;
					v_object.width = rowData.width;
					if(rowData.hidden == 'true' || rowData.notexport){
						return ;
					}
					dataArray[i] = v_object;
					i++;
				});
				columnsNew[index] = dataArray;
			});
			
			var exportColumns=JSON.stringify(columnsNew);
			
			var url=BasePath+exportUrl;
			
			var dataRow=$dg.datagrid('getRows');

			$("#exportExcelForm").remove();
			
			$("<form id='exportExcelForm'  method='post'></form>").appendTo("body"); ;
			
			var fromObj=$('#exportExcelForm');
			
			if(dataRow.length>0){
			    fromObj.form('submit', {
					url: url,
					onSubmit: function(param){
						if(params!=null&&params!={}){
							$.each(params,function(i){
								param[i]=params[i];
							});
						}
						if(otherParams!=null&&otherParams!={}){
							$.each(otherParams,function(i){
								param[i]=otherParams[i];
							});
						}
						param.exportColumns=exportColumns;
						param.fileName=excelTitle;
						param.pageNumber = v_pageNumber;
						param.pageSize = v_pageSize;
						
					},
					success: function(){
						
				    }
			   });
			}else{
				alert('查询记录为空，不能导出!',1);
			}
		}
	};
	
	
	WholesaleController.prototype.initDtlTab = function() {// 初始化明细Tab
		var dtl_url = wholesaleController.modulePath + '/to_dtl';
		_self.addDtlTab('单据明细',dtl_url);
		$('#dtlTab').tabs({    
		    onSelect:function(title){
		    	_self.loadDtlListData();
		    }    
		}); 
		returnTab('dtlTab','单据明细');
	};

		
	WholesaleController.prototype.addDtlTab = function(title,href){// 添加明细Tab
		$('#dtlTab').tabs('add', {
			title : title,
			selected : false,
			closable : false,
			href : href,
			onLoad : function(panel){
				if(title == '单据明细'){
					_self.loadDtlListData();
				}
			}
		});
	};

	WholesaleController.prototype.loadDtlListData = function(){// 加载明细数据
		var id = $('#id').val();
		var dg = $('#dtlDataGrid');
		var mainId = $('#mainDataForm').find("input[name=id]").val();
		if(dg.length >0){
			if(mainId !=""){
				setTimeout(function(){
					dg.datagrid( 'options' ).queryParams= {mainId:mainId};
					dg.datagrid( 'options' ).url = wholesaleController.dtlModulePath + '/list.json';
					dg.datagrid( 'load' );
				},300);
			}else{
				dg.datagrid('loadData',{total:0,rows:[]}); 
			}
		}
	};

	WholesaleController._initialized = true;
 }

// 初始化
$(function(){
	wholesaleController = new WholesaleController();
	wholesaleController.init(BasePath + '/wholesale_customer_remaining_sum', BasePath + '/wholesale_customer_remaining_dtl');
	wholesaleController.initPage();
	
	$('#mainTab').tabs('hideHeader');
	setTimeout(function(){
		returnTab('mainTab', '单据列表');
	},500);
	
	//加载预警列表
	loadWarnMessage();
});
