<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Registration.title" />
</title>
</head>
<body>
	<@form.form commandName="form" method="POST" renderSaveButton=false renderTitle=true baseCaption="PasswordReset">
			<p><@util.message "PasswordReset.send.error" /></p>
			<p><a href="${_th.getUrl("/resetpassword/", "/web")}"><@util.message "Label.forgetPassword" /></a></p>
	</@form.form>
</body>
</html>