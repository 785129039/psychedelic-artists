<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Sample.form.title" />
</title>
</head>
<body>
	<@form.form commandName="entity" baseCaption="Sample" enctype="multipart/form-data" renderTitle=true>
		<@form.inputText path="name" />
		<@form.file path="file" />
		<@form.textarea path="description" />
		<@form.inputText path="tagNames" isTag=true />
		<@form.select path="genres" items=genres valueKey="id" labelKey="name" multiple=true class="box" />
	</@form.form>
</body>
</html>