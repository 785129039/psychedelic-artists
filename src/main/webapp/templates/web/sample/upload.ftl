<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Sample.form.title" />
</title>
</head>
<body>
	<@form.form commandName="entity" baseCaption="Sample.add" enctype="multipart/form-data" renderTitle=true>
			<@form.file path="file" defaultCaption="Label.file"/>
	</@form.form>
</body>
</html>