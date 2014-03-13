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
			<div class="form-header">
				<h2 class="title"><@util.message _class+".detail.title" /></h2>
			</div>
			<div class="row detail-row">
				<div class="content col col-t-1 detail-content">
					<dl>
						<dt><@util.message "Label.name" /></dt>
						<dd>${entity.name}</dd>
						<dt><@util.message "Label.user.upload" /></dt>
						<dd>${entity.user.name}</dd>
						<dt><@util.message "Label.description" /></dt>
						<dd>${_th.formatContentText(entity.description)}</dd>
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
					</dl>
				</div>
			</div>
		</div>
		<div class="col-h-r">
			<div class="content">
				<div class="comment">
					<@form.form commandName="comment" baseCaption="Comment" enctype="multipart/form-data" method="POST" customForm=true>
						<form action="" method="post" name="" id="add-comment" class="form form-expand <#if (_formModel.formErrorsAsString?size>0)>form-expand-openx</#if>" data-options="{&quot;toogleBtn&quot;:&quot;.add-comment&quot;}">
							<div class="form-header">
								<h2 class="title"><@util.message "Comment.title" /></h2>
								<div class="btns">
									<a href="" class="btn add-comment"><span><@util.message "Comment.insertNew" /></span></a>
								</div>
							</div>
							<div class="form-content form-expand-content" style="display: none;">
								<div class="form-content-inner">
									<@form.renderResultMessages additionalErrors=[] reload=false/>
									<@form.inputText path="username" />
									<@form.textarea path="comment" />
									<p class="right">
										<button class="btn" name="" type="submit">
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