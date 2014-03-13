<#import "${_contextTemplates}/tags/util.ftl" as util>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${title}</title>
<meta charset="utf-8">
<link rel="stylesheet" href="<@util.url "/css/ui-lightness/jquery-ui-1.10.4.custom.min.css" />" type="text/css" media="projection,screen,print">
<link rel="stylesheet" href="<@util.url "/css/selectize.css" />" type="text/css" media="projection,screen,print">
<link rel="stylesheet" href="<@util.url "/css/content.css" />" type="text/css" media="projection,screen,print">
<link rel="stylesheet" href="<@util.url "/css/layout.css" />" type="text/css" media="projection,screen,print">
<link rel="stylesheet" href="<@util.url "/css/grid.css" />" type="text/css" media="projection,screen,print">
	
<script src="<@util.url "/js/jquery-1.10.2.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/jquery-ui-1.10.4.custom.min.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/selectize.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.wrapper.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.main.js" />" type="text/javascript"></script>

${head!""}
</head>
<#--body class="home">
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
</body-->


<body <#if _th.isUrlPart(["login", "logout"])>class="page-login"</#if>>
<div class="fixed">
	<div id="header">
		<div class="row row-main">
			<div class="header-inner">
				<div class="header-inner-inner">
					<p id="user-menu">
						<#if _user??>
						<span class="ico ico-person"></span>${_user}
						<a href="<@util.url "/j_spring_security_logout" ""/>"><span class="ico ico-logout"></span>Odhlásit se</a>
						<#else>
						<a href="<@util.url "/login" />"><span class="ico ico-logout"></span>Prihlasi</a>
						</#if>
					</p>
					
					<div id="main-menu">
						<ul class="reset">
							<li><a href="<@util.url "/sample/" "/web"/>">Samply</a></li>
							<li><a href="<@util.url "/preset/" "/web"/>">Presety</a></li>
						</ul>
					</div>
				</div>
			</div>			
		</div>
	</div>
</div>
	<div id="main">
		<div class="row-main">
			<#if _errorpage??>
				<#include _errorpage>
			<#else>
				${body}
			</#if>
		
		</div>
	</div>

	<div id="footer">
	<p id="copyright">© <a href="/">Psychedelic Artists</a>, všechna práva vyhrazena</p>
	</div>
	<div id="wrapper"></div>
</html>