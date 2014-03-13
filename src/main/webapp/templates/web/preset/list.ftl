<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message "Preset.list.title" />
</title>
</head>
<body>
	<@grid.grid data=entities baseCaption="Label">
		<@grid.filter>
			<@form.inputText path="name" />
		</@grid.filter>
		<@grid.datalist idColumn="id" renderButtons=false>
			<@grid.column name="name" isDetail=true />
			<@grid.column name="genres";e, f, v, n>
				<#list v as _row>
					${_row.name}<#if _row_has_next>,</#if>
				</#list>
			</@grid.column>
			<@grid.column name="tags";e, f, v, n>
				<#list v as _row>
					${_row.name}<#if _row_has_next>,</#if>
				</#list>
			</@grid.column>
			<@grid.renderActionButtons renderEdit=false renderCancel=false />
		</@grid.datalist>
	</@grid.grid>
</body>
</html>