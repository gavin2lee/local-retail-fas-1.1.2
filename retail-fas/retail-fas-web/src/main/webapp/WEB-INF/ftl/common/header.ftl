<!--界面上直接用   ${basePath}  -->
<#assign BasePath = springMacroRequestContext.getContextPath()/>

<script>
	<@env_identify env="dev">
	 document.domain = 'belle.net.cn';
	</@env_identify>
	   var BasePath = '${springMacroRequestContext.getContextPath()}';
	   var options = [${options}];
	   var resourcesUrl = '${resourcesUrl}';
	   window.version='${version}'; 
	   var staticurl = '${staticFileUrl}';
</script>
<link type="text/css" rel="stylesheet" href="${resourcesUrl}/fas/resources/css/home.css" />
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/sea.js?version=${version}"></script>
<script type="text/javascript"  src="${staticFileUrl}/boot.js?version=${version}" ></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/other-lib/common.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/easyui_dataGrid.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/common/js/easyui.validate.extends.js?version=${version}"></script>

<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.2.0.js?version=${version}"></script>
<script type="text/javascript" src="${resourcesUrl}/fas/resources/js/common/fas.plugin.support.js?version=${version}"></script>
<script type="text/javascript" src="${staticFileUrl}/assets/js/libs/sea.js?version=${version}"></script>
<!-- 重写toFixed方法，解决5不进位的问题  -->
<script type="text/javascript">
	Number.prototype.toFixed=function (d) { 
	    var s=this+""; 
	    if(!d)d=0; 
	    if(s.indexOf(".")==-1)s+="."; 
	    s+=new Array(d+1).join("0"); 
	    if(new RegExp("^(-|\\+)?(\\d+(\\.\\d{0,"+(d+1)+"})?)\\d*$").test(s)){
	        var s="0"+RegExp.$2,pm=RegExp.$1,a=RegExp.$3.length,b=true;
	        if(a==d+2){
	            a=s.match(/\d/g); 
	            if(parseInt(a[a.length-1])>4){
	                for(var i=a.length-2;i>=0;i--){
	                    a[i]=parseInt(a[i])+1;
	                    if(a[i]==10){
	                        a[i]=0;
	                        b=i!=1;
	                    }else break;
	                }
	            }
	            s=a.join("").replace(new RegExp("(\\d+)(\\d{"+d+"})\\d$"),"$1.$2");
	
	        }if(b)s=s.substr(1); 
	        return (pm+s).replace(/\.$/,"");
	    }
	    return this+"";
	};
</script>