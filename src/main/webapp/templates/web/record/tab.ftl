<#import "/tags/util.ftl" as util>
<#if entity.id??>
<div class="box-detail-nav">
	<ul class="tabs-header">
		<li><a href="<@util.url "/my/"+_implclass?lower_case+"/"+entity.id />" class="<#if _th.isUrlPart('CONTAINS', ["my/sample"])>selected</#if>"><@util.message "Record.detail.title" /></a></li>
		<li><a href="<@util.url "/upload/"+_implclass?lower_case+"/"+entity.id />" class="<#if _th.isUrlPart('CONTAINS',["upload/sample"])>selected</#if>"><@util.message "Record.upload.title" /></a></li>
	</ul>
</div>
</#if>