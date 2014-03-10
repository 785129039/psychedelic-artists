<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Genre.form.title" />
</title>
</head>
<body>
	<@form.form commandName="entity" baseCaption="Genre">
		<@form.inputText path="name" />
	</@form.form>
</body>
</html>