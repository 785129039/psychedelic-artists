<#macro authorize roles args=[_th.request] checker="defaultChecker">
	<#local permitted=_permissionHandler.permitted(roles, checker, args)>
	<#if permitted>
		<#nested>
	</#if>
</#macro>
<#macro unauthorize roles args=[_th.request] checker="defaultChecker">
	<#local permitted=_permissionHandler.permitted(roles, checker, args)>
	<#if !permitted>
		<#nested>
	</#if>
</#macro>
<#function isAuthorized roles args=[_th.request] checker="defaultChecker">
<#return _permissionHandler.permitted(roles, checker, args)>
</#function>