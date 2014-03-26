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
		<@grid.datalist idColumn="id" renderButtons=false isAjax=true>
			<@grid.column name="id" />
			<@grid.column name="name" isDetail=true ajax=true />
		</@grid.datalist>
	</@grid.grid>
</body>
</html>