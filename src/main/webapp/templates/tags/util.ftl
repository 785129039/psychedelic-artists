<#macro url path context=_sc>
<#t>${_th.getUrl(path, context)}
</#macro>
<#macro message code arguments=[]>
	<#t>${getMessage(code, arguments)}
</#macro>
<#function isNull text>
	<#if text=="" || text=="_NULL_">
	<#return true>
	</#if>
	<#return false>
</#function>
<#function getMessage code arguments=[]>
	<#return _th.getMessage(code, arguments)>
</#function>