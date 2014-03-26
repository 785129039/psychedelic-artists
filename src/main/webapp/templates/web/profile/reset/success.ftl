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
			<p><@util.message "PasswordReset.send.success" /></p>
	</@form.form>
</body>
</html>