<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message "Sample.list.title" />
</title>
</head>
<body>
	<@grid.grid data=entities baseCaption="Sample">
		<@grid.filter class="form filter">
		<div class="col col-f-1 grid-2f">
			<@form.inputText path="name" />
		</div>
			<div class="col col-f-3 grid-2f">
				
			</div>
		</@grid.filter>
		<@grid.datalist idColumn="id" renderButtons=false renderCheckbox=false>
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