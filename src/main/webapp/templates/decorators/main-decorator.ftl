<#import "${_contextTemplates}/tags/util.ftl" as util>
<#import "${_contextTemplates}/tags/security.ftl" as security>
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
<link rel="stylesheet" href="<@util.url "/css/menu.css" />" type="text/css" media="projection,screen,print">
<link rel="stylesheet" href="<@util.url "/css/fancybox/jquery.fancybox.css" />" type="text/css" media="projection,screen,print">

<script src="<@util.url "/js/jquery-1.10.2.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/jquery-ui-1.10.4.custom.min.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/selectize.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.wrapper.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.FormExpand.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/widget.main.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/menu.js" />" type="text/javascript"></script>
<script src="<@util.url "/js/jquery.fancybox.pack.js" />" type="text/javascript"></script>
${head!""}
</head>
<body <#if _th.isUrlPart(["login", "logout", "loginfailed", "register/"])>class="page-login"</#if>>
<div class="fixed">
	<div id="header">
		<div class="row row-main">
			<div class="header-inner">
				<div class="header-inner-inner">
					<p id="user-menu">
						<#if _user??>
						<span class="ico ico-person"></span>${_user}
						<a href="<@util.url "/j_spring_security_logout" ""/>"><span class="ico ico-logout"></span><@util.message "Label.logout" /></a>
						<#else>
						<a href="<@util.url "/login" />"><span class="ico ico-logout"></span><@util.message "Label.login" /></a>
						<a href="<@util.url "/register/" />"><span class="ico ico-logout"></span><@util.message "Label.register" /></a>
						</#if>
					</p>
				</div>
			</div>		
		</div>
		<div id="menu">
	  <ul class="menu">
	    <li <#if _th.isUrlPart('CONTAINS', ["sample"])>class="current"</#if>><a href="#" class="parent"><span><@util.message "Menu.samples" /></span></a>
	      <ul>
	        <li><a href="<@util.url "/browse/sample/" "/web" />"><span><@util.message "Menu.browse" /></span></a> </li>
	        <@security.authorize ["ROLE_ADMIN", "ROLE_USER"]>
	        <li><a href="<@util.url "/my/sample/" "/web" />"><span><@util.message "Menu.add" /></span></a></li>
	        </@security.authorize>
	      </ul>
	    </li>
	    <li <#if _th.isUrlPart('CONTAINS', ["preset"])>class="current"</#if>><a href="#" class="parent"><span><@util.message "Menu.presets" /></span></a>
	      <ul>
	        <li><a href="<@util.url "/browse/preset/" "/web" />"><span><@util.message "Menu.browse" /></span></a> </li>
	        <@security.authorize ["ROLE_ADMIN", "ROLE_USER"]>
	        <li><a href="<@util.url "/my/preset/" "/web" />"><span><@util.message "Menu.add" /></span></a></li>
	        </@security.authorize>
	      </ul>
	    </li>
	  </ul>
	</div>
	<a href="http://apycom.com/" style="display:none;"></a>
	</div>
	
</div>
	<div id="main">
		<div class="row-main">
			${body}
		</div>
	</div>

	<div id="footer">
	<p id="copyright">© <a href="/">Psychedelic Artists</a>, všechna práva vyhrazena</p>
	</div>
	<div id="wrapper"></div>
</html>