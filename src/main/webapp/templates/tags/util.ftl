<#macro url path context=_sc>
<#t>${_th.getUrl(path, context)}
</#macro>
<#macro message code arguments=[]>
	<#t>${getMessage(code, arguments)}
</#macro>
<#function isNull text>
	<#return _th.isNull(text)>
</#function>
<#function getMessage code arguments=[]>
	<#return _th.getMessage(code, arguments)>
</#function>