// 扩展差异回款的editor
$.extend($.fn.datagrid.defaults.editors, {
	diffback: {
        init: function (container, options) {
        	var id = options.id, name = options.name;
            var idbox = $("<input type='text' name='"+name+"' class='ipt' id='"+id+"' />").appendTo(container);
            idbox.validatebox(options);
            idbox.iptSearch({
				width : options.inputWidth || 140,
				clickFn: function () {
					var diffDtlId = $("#diffDtlId").val();
					var diffBillNo = $("#diffbackBillNo").val();
					var balanceNo = $("#balanceNo").val();
					var params = $.param({diffBillNo:diffBillNo,balanceNo:balanceNo,diffDtlId:diffDtlId});
					var diffEditorIndex = $.fas.assistEditIndex;
					ygDialog({
						title : "差异回款设置",
						href : BasePath + "/mall_shopbalance/selectShopBalanceDiffBack?" + params,
						width : options.panelWidth || 480,
						height : options.panelHeight || 'auto',
						isFrame : false,
						buttons: [{
				            text : '确定',
				            handler: function(dialog) {
				            	var backDatas = [];
				            	var effectRow = getChangeTableDataCommon("balanceDiffBackDG");
				            	backDatas.push({name : 'inserted', value : effectRow.inserted});
				            	backDatas.push({name : 'updated', value : effectRow.updated});
				            	backDatas.push({name : 'deleted', value : effectRow.deleted});
				            	backDatas.push({name : 'diffDtlId', value : diffDtlId});
				            	backDatas.push({name : 'diffBillNo', value : diffBillNo});
				            	backDatas.push({name : 'balanceNo', value : balanceNo});
			            	    ajaxRequestAsync(BasePath + '/bill_shop_balance_back/saveAndReturnAmount', backDatas, function(result){
			            	    	if(result && result.amount != null && $.trim(result.amount) != '') {
			            	    		$("#diffBackAmount").val($.fas.floatFixed(result.amount));
			            	    		//获取扣费差异的值
			            	    		var diffAmount = $.fas.getEditorVal({
			            	    			dataGridId : "balanceDiffDataGrid",
			            	    			rowIndex : diffEditorIndex,
			            	    			field : "diffAmountVal"
			            	    		});
			            	    		// 获取调整金额的值
			            	    		var changeAmount = $.fas.getEditorVal({
			            	    			dataGridId : "balanceDiffDataGrid",
			            	    			rowIndex : diffEditorIndex,
			            	    			field : "changeAmount"
			            	    		});
			            	    		
			            	    		diffAmount = diffAmount ? diffAmount : 0.00;
			            	    		changeAmount = changeAmount ? changeAmount : 0.00;
			            	    		// 获取上期差异余额字段的值
			            	    		var diffBalance = $.fas.getEditorVal({
			            	    			dataGridId : "balanceDiffDataGrid",
			            	    			rowIndex : diffEditorIndex,
			            	    			field : "preDiffBalance"
			            	    		});
			            	    		diffBalance = diffBalance ? diffBalance : 0.00;
//			            	    		diffBalance = parseFloat(diffBalance) + parseFloat(result.amount) + parseFloat(diffAmount) + parseFloat(changeAmount);
			            	    		diffBalance = parseFloat(diffBalance) + parseFloat(result.amount) + parseFloat(changeAmount);
			            	    		$.fas.setEditorVal({
			            	    			dataGridId : "balanceDiffDataGrid",
			            	    			rowIndex : diffEditorIndex,
			            	    			field : "diffBalance",
			            	    			dataType : "numberbox",
			            	    			value : diffBalance
			            	    		});
			            	    		// 如果差异余额为0，则状态默认变成已完成
			                			if(diffBalance == 0) {
			                				$("#diffStatus").combobox("setValue", "1");
			                			} else {
			                				$("#diffStatus").combobox("setValue", "0");
			                			}
			            	    		// 设置扣费差异字段的值
//			            	    		diffAmount = parseFloat(result.amount) + parseFloat(diffAmount);
			                			diffAmount = parseFloat(result.amount);
			            	    		$.fas.setEditorVal({
			            	    			dataGridId : "balanceDiffDataGrid",
			            	    			rowIndex : diffEditorIndex,
			            	    			field : "diffAmount",
			            	    			dataType : "numberbox",
			            	    			value : diffAmount
			            	    		});
		            	    			dialog.close();
			            	    	} else {
			            	    		showError('保存失败');
			            	    	}
			            	    });
				            }
				        },
				        {
				            text: '取消',
				            handler: function(dialog) {
				                dialog.close();
				            }
				        }]
					});
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
	}
});

function ShopBalanceDiffBackEditor() {
	var $this = this;
	// 新增行
	this.insertRow = function() {
		 $.fas.addEditorRow({
	        dataGridId : "balanceDiffBackDG",
	        initRow :  {
	        	balanceNo:$("#balanceNo").val(),
	        	diffBillNo:$("#diffbackBillNo").val(),
	        	billNo:$("#diffDtlId").val(),
	        	rootDiffId:$("#rootDiffId").val()
	        },
	        beforeAdd : $this.modifyBackDateEditor
	    });
	};
	
	// 删除行
	this.deleteRow = function() {
		 $.fas.deleteEditorRow({
	        dataGridId : "balanceDiffBackDG"
	    });
	};
	
	//  修改行
	this.editRow = function(rowIndex, rowData) {
		$.fas.editEditorRow({
	        dataGridId : "balanceDiffBackDG",
	        rowIndex : rowIndex,
			row : rowData,
			initRow :  {
				balanceNo:$("#balanceNo").val(),
				diffBillNo:$("#diffbackBillNo").val(),
				billNo:$("#diffDtlId").val(),
				rootDiffId:$("#rootDiffId").val()
			},
	        beforeUpdate : $this.modifyBackDateEditor
	    });
	};
	
	// 设置回款日期的范围
	this.modifyBackDateEditor = function() {
		$("#balanceDiffBackDG").datagrid("removeEditor", "backDate");
		$("#balanceDiffBackDG").datagrid("addEditor", {field : "backDate", 
			editor : {type:'datebox',
				options:{
      				required:true,
      				minDate:$("#balanceStartDate").val(),
      				maxDate:$("#balanceEndDate").val()
				}
			}
		});
	}
};

var shopBalanceDiffBackEditor = new ShopBalanceDiffBackEditor();
