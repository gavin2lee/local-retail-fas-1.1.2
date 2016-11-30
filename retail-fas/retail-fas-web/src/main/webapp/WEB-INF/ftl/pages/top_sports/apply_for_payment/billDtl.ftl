<div class="easyui-layout" data-options="fit:true,plain:true">
	<div data-options="region:'center',border:false">
	  	<div class="easyui-layout" data-options="fit:true,plain:true" >
			<div data-options="region:'north',border:false" class="toolbar-region">
				<@p.toolbar id="toolbar3" listData=[
					 {"id":"btn-insert","title":"新增明细","iconCls":"icon-add-dtl", "action" : "applyForPayment.insertRow()", "type":0},
		             {"id":"btn-remove","title":"删除明细","iconCls":"icon-del-dtl", "action" : "applyForPayment.deleteRow()","type":0}
		           ]
				/>
	        </div>
		            
	  	    <div data-options="region:'center',border:false" >
		     <@p.datagrid id="dataDGridJG"  loadUrl="" saveUrl=""   defaultColumn="" isHasToolBar="false"   
		      onClickRowEdit="false" pagination="true"  rownumbers="true"   pageSize="20" showFooter="true"
	          	 columnsJsonList="[
	          	  {field:'ck',checkbox:true,notexport:true},
	          	  {field : 'billNo', hidden:true,notexport:true},	
	              {field : 'settleMethodNo', title : '结算方式', width : 100,align:'center',halign:'center',
	                formatter:function(value,index,row){return  datas[value]},
	              	editor:{
	              	        type:'combobox',
	              			options:{
	              				required:true,
	              				valueField:'code',
	              				textField:'name',
	              				url : BasePath+'/common_util/getSettleMethod'
	              			}
	              		}
	              },
	              {field : 'amount', title : '金额', width : 100,align:'right',halign:'center',
	              	editor:{
	              		type:'numberbox',
	              		options:{
	                  		required:true, 
	                  		precision:2, 
	                  		validType:['maxLength[12]'],
	                  		onChange:function(){applyForPayment.setAllAmount();}
	              		}}
	              },	
	              {field : 'sysExpirationDate', title : '系统到期日', width : 100,align:'center'},	
	              {field : 'paymentDate', title : '要求付款日', width : 100,align:'center',
	              	editor:{
	              		type:'datebox',
	              		options:{
	                  		required:true
	              		}}
	              },	
	              {field : 'otherBank', title : '开户行', width : 150,align:'left',halign:'center',
	              	editor:{
	                  	type:'validatebox',
	                  	options:{validType:['unNormalData','maxLength[30]']}
	                 }
	              },	
	              {field : 'otherBankAccount', title : '对方账号', width : 150,align:'left',halign:'center',
	              	editor:{
	                  	type:'validatebox',
	                  	options:{
	                  		validType:['unNormalData','maxLength[30]']
	                  	}
	                }
	              },	
				  {field : 'qty', title : '数量', width : 80,align:'right',halign:'center',
				  		editor:{
				  			type:'numberbox',
				  			options:{required:true, validType:['maxLength[9]']}
				  		}
				  },
	              {field : 'brandName', title : '品牌部', width : 80,align:'center',halign:'center'},
	              {field : 'categoryName', title : '财务大类', width : 100,align:'center',halign:'center'}	,
	              {field : 'remark', title : '备注', width : 150,align:'center',
	              	editor:{
	                  	type:'validatebox',
	                  	options:{validType:'maxLength[200]'}
	                 }
	              }
	            ]" 
	            jsonExtend='{
	                   onDblClickRow:function(rowIndex, rowData){
	                   	  applyForPayment.editRow(rowIndex);
	             	 }
	     		}'
	    	 />
			</div>
		</div>
	</div>	
</div>	