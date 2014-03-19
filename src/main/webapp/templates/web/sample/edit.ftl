<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Sample.form.title" />
</title>
</head>
<body>
	<@form.form commandName="entity" baseCaption="Sample.add" renderTitle=true>
		<@form.inputText path="name" defaultCaption="Label.name" />
		<@form.textarea path="description" defaultCaption="Label.description" />
		<@form.inputText path="tagNames" isTag=true defaultCaption="Label.tagNames"/>
		<@form.select path="genres" items=genres valueKey="id" labelKey="name" multiple=true class="box" defaultCaption="Label.genres"/>
	</@form.form>
</body>
</html>