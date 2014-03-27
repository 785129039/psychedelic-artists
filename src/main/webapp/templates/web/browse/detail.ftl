<#import "/tags/form.ftl" as form>
<#import "/tags/util.ftl" as util>
<#import "/tags/grid.ftl" as grid>
<html>
<head>
<title>
	<@util.message _class+".detail.title" />
</title>
</head>
<body>
	<div class="box-detail row">
		<div class="content col col-t-1">
			<div class="row detail-row">
				<div class="message message-title">
					<p class="header-title"><@util.message _class+".detail.title" /></p>
				</div>
				<div class="content col col-t-1 detail-content">
					<dl>
						<dt><@util.message "Label.name" /></dt>
						<dd>${entity.name}</dd>
						<dt><@util.message "Label.user.upload" /></dt>
						<dd>${entity.user.name}</dd>
						<dt <#if (entity.description?? && entity.description?length>0)>class="popup"</#if>><@util.message "Label.description" />
						<div class="popup-content" style="display:none">${_th.formatContentText(entity.description)}</div>
						</dt>
						<dd>
							<#if entity.description??>
								${_th.formatContentText(entity.description, 40)}
							</#if>
						</dd>
						<dt><@util.message "Label.genres" /></dt>
						<dd>
							<#list entity.genres as g>
								<#t><@grid.genreLink g.id>${g.name}</@grid.genreLink><#if g_has_next>, </#if>
							</#list>
						</dd>
						<#if (entity.tags?size>0)>
							<dt><@util.message "Label.tagNames" /></dt>
							<dd>
								<#list entity.tags as t>
									<#t><@grid.tagLink t.id>${t.name}</@grid.tagLink><#if t_has_next>, </#if>
								</#list>
							</dd>
						</#if>
						<dt><@util.message "Label.rating" /></dt>
						<dd>
							<#include "/rating.ftl" />
						</dd>
					</dl>
					
				</div>
			</div>
		</div>
		<div class="col-h-r">
			<div class="content">
				<div class="comment">
					<@form.form commandName="comment" baseCaption="Comment" method="POST" customForm=true>
						<form action="${entity.id}" method="post" name="" id="add-comment" class="form form-expand <#if (_formModel.formErrorsAsString?size>0)>form-expand-openx</#if>" data-options="{&quot;toogleBtn&quot;:&quot;.add-comment&quot;}">
							<div class="row detail-row">
								<div class="message message-title">
									<p class="header-title"><@util.message "Comment.title" /></p>
								</div>
								<div class="expand-header">
									<div class="btns ">
										<a href="" class="btn add-comment"><span><@util.message "Comment.insertNew" /></span></a>
									</div>
								</div>
							</div>
							
								
							
							<div class="form-content form-expand-content form-append" style="display: none;">
								<div class="form-content-inner">
									<@form.renderResultMessages additionalErrors=[] reload=false/>
									<@form.inputText path="username" />
									<@form.textarea path="comment" />
									<p class="right">
										<button class="btn btn-big" name="" type="submit">
											<span><@util.message "Comment.add" /></span>
										</button>
									</p>
								</div>
							</div>
						</form>
						
					</@form.form>
					<#assign _filteredList=entities>
					
					<#if (_filteredList.data?size) == 0>
						<ul class="reset list">
							<li class="item odd">
								<div class="item-inner">
									<div class="item-header"></div>
									<div class="item-content">
										<@util.message "Comment.notfound" />
									</div>
								</div>
							</li>
						<ul>
					<#else>
						
						<ul class="reset list">
							<#list _filteredList.data as c>
								
										<li class="item odd">
											<div class="item-inner">
												<div class="item-header">
													<strong>30.01.2014</strong> 20:47 | ${c.username}
												</div>
												<div class="item-content">
													${_th.formatContentText(c.comment)}
												</div>
											</div>
										</li>
									
							</#list>
						</ul>
						
						<@grid.pagination _filteredList=_filteredList/>
					</#if>	
				</div>
			</div>
		</div>
	</div>
</body>
</html>