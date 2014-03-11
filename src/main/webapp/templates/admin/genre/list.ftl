<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message "Genre.list.title" />
</title>
</head>
<body>
	<@grid.grid data=entities baseCaption="Genre">
		<@grid.filter>
			<@form.inputText path="name" />
		</@grid.filter>
		<@grid.datalist idColumn="id" renderButtons=false>
			<@grid.column name="id" isId=true />
			<@grid.column name="name" isDetail=true />
			<@grid.renderActionButtons renderEdit=false renderCancel=false />
		</@grid.datalist>
	</@grid.grid>
</body>
</html>