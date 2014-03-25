<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message _implclass + ".form.title" />
</title>
<link rel="stylesheet" href="<@util.url "/css/upload.css" ""/>" type="text/css" media="projection,screen,print">

<script src="<@util.url "/js/jquery.fileupload.js" ""/>" type="text/javascript"></script>
<script>
$(document).ready(function()
{
	$("#fileuploader").uploadFile({
		url:"<@util.url "/my/upload/"+_implclass?lower_case+"/"+entity.id />",
		dragDropStr: "<span><b><@util.message "Fileupload.dragDropStr" /></b></span>",
		abortStr:"<@util.message "Fileupload.abortStr" />",
		cancelStr:"<@util.message "Fileupload.cancelStr" />",
		doneStr:"<@util.message "Fileupload.doneStr" />",
		multiDragErrorStr: "<@util.message "Fileupload.multiDragErrorStr" />",
		extErrorStr:"<@util.message "Fileupload.extErrorStr" />",
		sizeErrorStr:"<@util.message "Fileupload.sizeErrorStr" />",
		uploadErrorStr:"<@util.message "Fileupload.uploadErrorStr" />"
	});
});
</script>
</head>
<body>
	<#include "tab.ftl">
	
	<div class="form-content">
		<div class="form-content-inner">
			<div class="message message-title">
				<p class="header-title"><@util.message _implclass + ".upload.title" /></p>
			</div>
			<div class="row">
				<div class="message message-announce">
					<p><@util.message "Record.upload.announce" /></p>
				</div>
				<div id="fileuploader"><@util.message "Fileupload.upload.button" /></div>	
			</div>
		</div>

	</div>
</body>
</html>