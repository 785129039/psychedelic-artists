<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Registration.title" />
</title>
</head>
<body>
	<@form.form commandName="form" method="POST" customButtonLabel=true renderTitle=true baseCaption="PasswordReset">
			<@form.inputText path="email" />
	</@form.form>
</body>
</html>