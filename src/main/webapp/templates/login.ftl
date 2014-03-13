<#import "/tags/util.ftl" as util>
<#import "/tags/form.ftl" as form>
<html>
<head>
<title>Login Page</title>
</head>
<body>	
	<div class="form-header">
		<h2 class="title"><@util.message "Label.login.title" /></h2>
	</div>
	<form action="<@util.url "/j_spring_security_check" ""/>" method="POST" name="" id="login-form" class="form">
		<div class="form-content">
			<div class="form-content-inner">
				<#if error??>
					<div class="message message-error">
						<p><@util.message "Login.failed" /></p>
					</div>				
				</#if>
				<p>
					<label for="" class="label <#if error??>error</#if>"><@util.message "Registration.email" />:</label><br>
					<span class="inp-fix  <#if error??>inp-fix-error</#if>">
						<input type="text" name="j_username" id="" class="inp-text">
					</span>
				</p>
				<p>
					<label for="" class="label <#if error??>error</#if>"<@util.message "Registration.password" />:</label><br>
					<span class="inp-fix <#if error??>inp-fix-error</#if>">
						<input type="password" name="j_password" id="" class="inp-text">
					</span>
				</p>
				<p class="right">
					<@form.link href=_th.getUrl("/register/", "/web")>
						<@util.message "Label.register" />
					</@form.link>
					<@form.link href="javascript:$('#login-form').submit()">
						<@util.message "Label.login" />
					</@form.link>
					
				</p>
			</div>
		</div>
	</form>
</body>
</html>