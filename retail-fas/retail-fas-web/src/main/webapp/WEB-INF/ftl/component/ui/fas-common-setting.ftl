<#macro commonSetting  search_url datagrid_id search_form_id   
	save_url="" update_url="" del_url="" data_form_id="" dialog_width="" dialog_height ="" 
	primary_key="" export_url="" export_title="" export_type="" enable_url="" import_url="" import_title="" import_type="" upload_Form="" unable_url="" audit_url=""
	search_btn="" clear_btn="" add_btn="" edit_btn="" del_btn="" export_btn="" import_btn="" enable_btn="" unable_btn="" audit_btn="" anti_audit_btn="" anti_audit_url="">
<script type="text/javascript">
  var fas_common_setting = {
  	 "searchUrl" : '${search_url}'
  };
  <#if save_url != '' >
  	fas_common_setting.saveUrl = '${save_url}';
  	<#else>
  		fas_common_setting.saveUrl = '';
  </#if>
  <#if update_url != '' >
  	fas_common_setting.updateUrl = '${update_url}';
  	<#else>
  		fas_common_setting.updateUrl = '';
  </#if>
  <#if del_url != '' >
  	fas_common_setting.delUrl = '${del_url}';
  	<#else>
  		fas_common_setting.delUrl = '';
  </#if>
  <#if datagrid_id != '' >
  	fas_common_setting.dataGridId = '${datagrid_id}';
  	fas_common_setting.datagridId = '${datagrid_id}';
  	<#else>
  		fas_common_setting.dataGridId = 'dataGridDiv';
  		fas_common_setting.datagridId = 'dataGridDiv';
  </#if>
  <#if search_form_id != '' >
  	fas_common_setting.searchFormId = '${search_form_id}';
  	<#else>
  		fas_common_setting.searchFormId = 'searchForm';
  </#if>
  <#if data_form_id != '' >
  	fas_common_setting.dataFormId = '${data_form_id}';
  	<#else>
  		fas_common_setting.dataFormId = 'dataForm';
  </#if>
  <#if dialog_width != '' >
  	fas_common_setting.dialogWidth = '${dialog_width}';
  	<#else>
  		fas_common_setting.dialogWidth = 800;
  </#if>
  <#if dialog_height != '' >
  	fas_common_setting.dialogHeight = '${dialog_height}';
  	<#else>
  		fas_common_setting.dialogHeight = 330;
  </#if>
  <#if primary_key != '' >
  	fas_common_setting.primaryKey = '${primary_key}';
  	<#else>
  		fas_common_setting.primaryKey = 'id';
  </#if>
  
  <#if export_url != '' >
  		fas_common_setting.exportUrl = '${export_url}';
	<#else>
  		fas_common_setting.exportUrl = '';
  </#if>
  
  <#if import_url != ''>
  	fas_common_setting.importUrl = '${import_url}';
  	<#else>
  	fas_common_setting.importUrl = '';
  </#if>
  
  
  <#if export_title != '' >
  		fas_common_setting.exportTitle = '${export_title}';
	<#else>
  		fas_common_setting.exportTitle = '导出信息';
  </#if>
  
  <#if enable_url != '' >
		fas_common_setting.enableUrl = '${enable_url}';
	<#else>
		fas_common_setting.enableUrl = '';
  </#if>
  
  <#if unable_url != '' >
		fas_common_setting.unableUrl = '${unable_url}';
	<#else>
		fas_common_setting.unableUrl = '';
  </#if>
  
  <#if audit_url != '' >
		fas_common_setting.auditUrl = '${audit_url}';
	<#else>
		fas_common_setting.auditUrl = '';
  </#if>
  
  <#if upload_Form != '' >
  	fas_common_setting.formId = '${upload_Form}';
  	<#else>
  	fas_common_setting.formId = '';
  </#if>
  
  <#if search_btn != '' >
		fas_common_setting.searchBtn = '${search_btn}';
	<#else>
		fas_common_setting.searchBtn = 'btn-search';
  </#if>
  <#if clear_btn != '' >
		fas_common_setting.clearBtn = '${clear_btn}';
	<#else>
		fas_common_setting.clearBtn = 'btn-remove';
  </#if>
  <#if add_btn != '' >
		fas_common_setting.addBtn = '${add_btn}';
	<#else>
		fas_common_setting.addBtn = 'btn-add';
  </#if>
   <#if edit_btn != '' >
		fas_common_setting.editBtn = '${edit_btn}';
	<#else>
		fas_common_setting.editBtn = 'btn-edit';
  </#if>
  <#if del_btn != '' >
		fas_common_setting.delBtn = '${del_btn}';
	<#else>
		fas_common_setting.delBtn = 'btn-del';
  </#if>
   <#if export_btn != '' >
		fas_common_setting.exportBtn = '${export_btn}';
	<#else>
		fas_common_setting.exportBtn = 'btn-export';
  </#if>
  
  <#if import_btn != '' >
  	fas_common_setting.importBtn = '${import_btn}';
	<#else>
		fas_common_setting.importBtn = '';
  </#if>
  
   <#if export_type != '' >
		fas_common_setting.exportType = '${export_type}';
	<#else>
		fas_common_setting.exportType = 'common';
  </#if>
  <#if enable_btn != '' >
		fas_common_setting.enableBtn = '${enable_btn}';
	<#else>
		fas_common_setting.enableBtn = 'btn-enable';
  </#if>
  <#if unable_btn != '' >
		fas_common_setting.unableBtn = '${unable_btn}';
	<#else>
		fas_common_setting.unableBtn = 'btn-unable';
  </#if>
  <#if audit_btn != '' >
		fas_common_setting.auditBtn = '${audit_btn}';
	<#else>
		fas_common_setting.auditBtn = 'btn-audit';
  </#if>
   <#if anti_audit_btn != '' >
		fas_common_setting.antiAuditBtn = '${anti_audit_btn}';
	<#else>
		fas_common_setting.antiAuditBtn = 'btn-anti-audit';
  </#if>
  <#if anti_audit_url != '' >
		fas_common_setting.antiAuditUrl = '${anti_audit_url}';
	<#else>
		fas_common_setting.antiAuditUrl = '';
  </#if>
</script>
</#macro>
