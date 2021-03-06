<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message "Overview.list.title" />
</title>
<script src="<@util.url "/js/selectize.ajax.js" ""/>" type="text/javascript"></script>
<script>
$(document).ready(function() {
$._selectize.create('select.tags').initialize({url:'<@util.url "/ajax/load/tags/" ""/>', value:'id', text:'name'});
});
</script>
</head>
<body>


	<@grid.grid data=entities baseCaption="Label">
		<@grid.filter>
			<div class="col col-f-1 grid-2f">
				<@form.inputText path="name" />
			</div>
			<div class="col col-f-3 grid-2f">
				<@form.select path="genre" items=genres valueKey="id" labelKey="name"  multiple=true class="box" defaultCaption="Label.genres"/>
				<@form.select path="tag" items=tags valueKey="id" labelKey="name"  multiple=true class="tags box" selectized=false defaultCaption="Label.tags"/>
			</div>
		</@grid.filter>
		<@grid.datalist renderButtons=false showNewButton=false idColumn="id" renderCheckbox=false>
			<@grid.column name="name" isDetail=true />
			<@grid.column name="type"; e, f, v, n>
				<a href="${_th.getUrl("/browse/"+_th.evaluate("type", e)?lower_case+"/", "/web")}"><@util.message _th.evaluateAsString("type.code", e) /></a>
			</@grid.column>
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
			<@grid.customColumn title=util.getMessage("Label.rating") name="statistic.ratingPercent"; r>
				<td>${_th.evaluateAsString("statistic.ratingPercent", r)}%</td>
			</@grid.customColumn>
			<@grid.customColumn title=util.getMessage("Label.downloads") name="statistic.downloads"; r>
				<td>${_th.evaluateAsString("statistic.downloads", r)}x</td>
			</@grid.customColumn>
			<@grid.column name="user.name" />
		</@grid.datalist>
	</@grid.grid>
	
</body>
</html>