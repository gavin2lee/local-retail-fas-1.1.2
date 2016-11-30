<#-- 
id:
title:
iconCls:
action:
type: 1-新增 2-修改 3-删除 4-审单 5-导出       0代表不验证权限的按钮

验证： checkPower("${thisPowerV?number}",4)==true
tabsData:tab页切换
-->
<#macro toolbar  id='cur_toolbar'  listData='[]' tabsData=[]  >
<div id="${id}"></div>
<#assign thisPowerV="${thisPower!0}" >
	<script type="text/javascript">
		  var toolbarList=[];
		  <#list listData as item>
		      <#assign flag=1>
		      <#if (item.type)?? >
		          <#if (item.type!=0)>
		                <#if  checkPower("${thisPowerV?number}",item.type)==false>
		                     <#assign flag=0>
		                </#if>
		          </#if>
		          var this_bar={};
		          this_bar.text='${item.title}';
		          <#if flag==1>
		          		this_bar.id='${item.id}';
		          		this_bar.iconCls='${item.iconCls}';
		          		this_bar.handler=function(){${item.action}};
		              <#else>
		                this_bar.iconCls='${item.iconCls}-dis';
		                this_bar.disabled=true;
		           </#if>
		           toolbarList.push(this_bar);
		           toolbarList.push('-');
		      </#if> 
		  </#list>
		  toolbarList.pop();
		  
		  var tabList=[];
		  <#if tabsData ??>
			  <#list tabsData as vals>
					var this_tab={};
					this_tab.target='${vals.target}';
					this_tab.title='${vals.title}';
					this_tab.selected='${vals.selected}';
					tabList.push(this_tab);
			  </#list>
		  </#if>
		  tabList.pop();
		  
		  $('#${id}').toolbar({items:toolbarList,tabs:tabList});
	</script>
</#macro>
