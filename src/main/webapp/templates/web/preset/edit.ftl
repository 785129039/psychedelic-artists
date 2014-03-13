<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message "Preset.form.title" />
</title>
</head>
<body>
	<@form.form renderTitle=true commandName="entity" baseCaption="Preset.add" enctype="multipart/form-data">
		<@form.inputText path="name" defaultCaption="Label.name" />
		<@form.file path="file" defaultCaption="Label.file"/>
		<@form.textarea path="description" defaultCaption="Label.description" />
		<@form.inputText path="tagNames" isTag=true defaultCaption="Label.tagNames"/>
		<@form.select path="genres" items=genres valueKey="id" labelKey="name" multiple=true class="box" defaultCaption="Label.genres"/>
	</@form.form>
</body>
</html>