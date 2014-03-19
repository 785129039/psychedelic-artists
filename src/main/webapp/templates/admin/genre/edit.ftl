<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Genre.form.title" />
</title>
</head>
<body>
	<@form.form commandName="entity" baseCaption="Genre" reload=true isAjax=true>
		<@form.inputText path="name"/>
	</@form.form>
</body>
</html>