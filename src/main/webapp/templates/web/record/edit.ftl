<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message _implclass + ".form.title" />
</title>
</head>
<body>
	<#include "tab.ftl">
	<@form.form commandName="entity" baseCaption=_implclass + ".detail" renderTitle=true>
		<div class="col grid-2t col-t-1">
			<@form.inputText path="name" defaultCaption="Label.name" />
			<@form.textarea path="description" defaultCaption="Label.description" />
		</div>
		<div class=" col grid-t col-t-3">
			<@form.inputText path="tagNames" isTag=true defaultCaption="Label.tagNames"/>
			<@form.select path="genreIds" items=genres valueKey="id" labelKey="name" multiple=true defaultCaption="Label.genres"/>
		</div>		
	</@form.form>
</body>
</html>