<#import "/tags/util.ftl" as util>

<div class="box-detail-nav">
	<ul class="tabs-header">
		<#assign _detailUrl="#">
		<#if entity.id??>
		<#assign _detailUrl=_th.getUrl("/my/detail/"+_implclass?lower_case+"/"+entity.id, _sc) >
		</#if>
		<li><a href="${_detailUrl}" class="<#if _th.isUrlPart('CONTAINS', ["detail"])>selected</#if>"><@util.message "Record.detail.title" /></a></li>
		<#if entity.id??>
			<li><a href="<@util.url "/my/upload/"+_implclass?lower_case+"/"+entity.id />" class="<#if _th.isUrlPart('CONTAINS',["upload"])>selected</#if>"><@util.message "Record.upload.title" /></a></li>
		</#if>		
	</ul>
</div>
