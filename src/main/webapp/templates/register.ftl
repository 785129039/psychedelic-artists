<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Registration.title" />
</title>
</head>
<body>
	<@form.form commandName="user" method="POST" renderTitle=true baseCaption="Registration" additionalErrors=["passwordsMatch", "userExist"]>
		<@form.inputText path="name" />
		<@form.inputText path="email" />
		<@form.inputPassword path="password" />
		<@form.inputPassword path="matchPassword" />
	</@form.form>
</body>
</html>