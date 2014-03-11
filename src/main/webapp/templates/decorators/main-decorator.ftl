<#import "${_contextTemplates}/tags/util.ftl" as util>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${title}</title>
<meta charset="utf-8">
<link rel="stylesheet" href="<@util.url "/css/ui-lightness/jquery-ui-1.10.4.custom.min.css" />" type="text/css" media="screen">
<script src="<@util.url "/js/jquery-1.10.2.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/jquery-ui-1.10.4.custom.min.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.utils.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.main.js" />" type="text/javascript"></script>
${head!""}
</head>
<body>
---------Psychedelic Artists-------

new application build-test

<#if _user??>
Welcome: ${_user}
<a href="<@util.url "/j_spring_security_logout" ""/>" >Logout</a>
<#else>
<a href="<@util.url "/login" />" >Login</a>
</#if>

<#if _errorpage??>
	<#include _errorpage>
<#else>
	${body}
</#if>
</body>
</html>