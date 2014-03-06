<#import "${_contextTemplates}/tags/util.ftl" as util>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${title}</title>
<meta charset="utf-8">
<!--link rel="stylesheet" href="<@util.url "/css/reset.css" />" type="text/css" media="screen"-->
<script src="<@util.url "/js/jquery.2.0.min.js" />" type="text/javascript"></script>
${head!""}
</head>
<body>
---------Psychedelic Artists-------
<#if _user??>
Welcome: ${_user}
<a href="<@util.url "/j_spring_security_logout" />" >Logout</a>
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