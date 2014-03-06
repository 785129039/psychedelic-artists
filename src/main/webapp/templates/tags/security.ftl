<#macro authorize roles args=[] checker="defaultChecker">
	<#local permitted=_permissionHandler.permitted(roles, checker, args)>
	<#if permitted>
		<#nested>
	</#if>
</#macro>
<#macro unauthorize roles args=[] checker="defaultChecker">
	<#local permitted=_permissionHandler.permitted(roles, checker, args)>
	<#if !permitted>
		<#nested>
	</#if>
</#macro>
<#function isAuthorized roles args=[] checker="defaultChecker">
<#return _permissionHandler.permitted(roles, checker, args)>
</#function>