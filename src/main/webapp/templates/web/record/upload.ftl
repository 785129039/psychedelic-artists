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
	
	<@form.form commandName="entity" baseCaption=_implclass + ".upload" enctype="multipart/form-data" renderTitle=true>
			
			<div class="message message-announce">
				<p><@util.message "Record.upload.announce" /></p>
			</div>
			
			<@form.file path="file" defaultCaption="Label.file"/>
	</@form.form>
</body>
</html>