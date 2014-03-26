<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<html>
<head>
<title>
	<@util.message _implclass + ".form.title" />
</title>
<link rel="stylesheet" href="<@util.url "/css/upload.css" ""/>" type="text/css" media="projection,screen,print">

<script src="<@util.url "/js/jquery.form-plugin.js" ""/>" type="text/javascript"></script>
<script src="<@util.url "/js/jquery.fileupload.js" ""/>" type="text/javascript"></script>

<script>
$(document).ready(function() {
	createFileUpload();
});

function createFileUpload() {
	$("#fileuploader").uploadFile({
		url:"<@util.url "/my/upload/"+_implclass?lower_case+"/"+entity.id+"?_simple=true" />",
		dragDropStr: "<span><b><@util.message "Fileupload.dragDropStr" /></b></span>",
		abortStr:"<@util.message "Fileupload.abortStr" />",
		cancelStr:"<@util.message "Fileupload.cancelStr" />",
		doneStr:"<@util.message "Fileupload.doneStr" />",
		multiDragErrorStr: "<@util.message "Fileupload.multiDragErrorStr" />",
		extErrorStr:"<@util.message "Fileupload.extErrorStr" />",
		sizeErrorStr:"<@util.message "Fileupload.sizeErrorStr" />",
		uploadErrorStr:"<@util.message "Fileupload.uploadErrorStr" />",
		onSuccess: function (e, data) {
			$('#form-anchor').html(data);
			createFileUpload();
        }
	});
}

</script>
</head>
<body>
<div id="form-anchor">
	<#include "tab.ftl">
	<@form.form commandName="entity" renderTitle=true baseCaption=_implclass + ".upload" renderSaveButton=false additionalErrors=["file"]>
		<div class="row">
				<div class="message message-announce">
					<p><@util.message "Record.upload.announce" /></p>
				</div>
				<div id="fileuploader"><@util.message "Fileupload.upload.button" /></div>	
			</div>
	</@form.form>
</div>
</body>
</html>