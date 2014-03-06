<#import "/tags/form.ftl" as form>
<html>
<head>
<title>
	Registration
</title>
</head>
<body>
	<@form.form commandName="user" method="POST" baseCaption="Registration" additionalErrors=["passwordsMatch", "userExist"]>
		<@form.inputText path="email" />
		<@form.inputPassword path="password" />
		<@form.inputPassword path="matchPassword" />
	</@form.form>
	
	
</body>
</html>