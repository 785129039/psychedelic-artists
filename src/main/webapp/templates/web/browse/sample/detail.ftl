<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message "Sample.form.title" />
</title>
</head>
<body>
	${entity.name}
	
	<div id="comments">
		<#assign _filteredList=entities>
		<#if (_filteredList.data?size) == 0>
			nothing found
		<#else>
			<#list _filteredList.data as c>
				<p>
					<span>${c.username}</span>
					<div>${c.comment}</div>
				</p>
			</#list>
			<@grid.pagination _filteredList=_filteredList/>
		</#if>
		<@form.form commandName="comment" baseCaption="Comment" enctype="multipart/form-data" method="POST">
			<@form.inputText path="username" />
			<@form.textarea path="comment" />
		</@form.form>
	</div>
	
</body>
</html>