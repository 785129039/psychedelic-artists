<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message _class+".list.title" />
</title>
</head>
<body>
	<@grid.grid data=entities baseCaption="Label">
		<@grid.filter>
			<@form.inputText path="name" />
		</@grid.filter>
		<@grid.datalist renderButtons=false showNewButton=false idColumn="id" renderCheckbox=false>
			<@grid.column name="name" isDetail=true />
			<@grid.column name="genres" sortable=false; e, f, v, n>
				<#list v as _row>
					<@grid.genreLink _row.id>${_row.name}</@grid.genreLink><#if _row_has_next>,</#if>
				</#list>
			</@grid.column>
			<@grid.column name="tags" sortable=false; e, f, v, n>
				<#list v as _row>
					<@grid.tagLink _row.id>${_row.name}</@grid.tagLink><#if _row_has_next>,</#if>
				</#list>
			</@grid.column>
			<@grid.column name="user.name" />
		</@grid.datalist>
	</@grid.grid>
</body>
</html>