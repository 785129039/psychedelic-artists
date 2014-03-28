<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>

<form action="${entity.id}" method="post">
	<input type="hidden" name="_method" value="put" />
	<#assign _currentRating=0>
	<#if yourrating??>
	<#assign _currentRating=yourrating?number>
	</#if>
	<#assign _maxRating=5>
	<#list 1.._maxRating as i>
		<input <#if !(canrate!true)>disabled="disabled"</#if> name="statistic.rating" type="radio" value="${i}" <#if i==_currentRating>checked="checked"</#if> class="record-rating"/> 
	</#list>
</form>
<span style="padding-left:10px;">/ ${entity.statistic.ratingPercent}%</span>
