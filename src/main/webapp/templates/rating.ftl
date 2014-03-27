<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<#if canrate!true>
	<form action="${entity.id}" method="post">
		<input type="hidden" name="_method" value="put" />
		<#assign _currentRating=entity.statistic.rating>
		<#assign _maxRating=5>
		<#list 1.._maxRating as i>
		<input name="statistic.rating" type="radio" value="${i}" <#if i==_currentRating>checked="checked"</#if> class="record-rating"/> 
		</#list>
	</form>
<#else>
	${entity.statistic.ratingPercent}%
</#if>