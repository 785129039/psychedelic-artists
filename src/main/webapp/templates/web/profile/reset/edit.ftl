<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Registration.title" />
</title>
</head>
<body>
	<@form.form commandName="user" method="POST" renderTitle=true baseCaption="PasswordReset" additionalErrors=["passwordsMatch", "badOldPassword"]>
			<@form.inputPassword path="oldPassword" />
			<@form.inputPassword path="password" />
			<@form.inputPassword path="matchPassword" />
	</@form.form>
</body>
</html>