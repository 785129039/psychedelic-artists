<#import "util.ftl" as util />
<#assign springform=JspTaglibs["http://www.springframework.org/tags/form"]>

<#macro form commandName baseCaption="_NULL_" action="" isFilter=false method="auto" renderTitle=false renderSaveButton=true class="" customForm=false id="_NULL_" additionalErrors=[] showRequired=true>
	<#assign _formBaseCaption=baseCaption>
	<#assign _showRequireIndex=showRequired>
	<#global _formModel=_th.createFormModel(commandName)>
	<#local _method=method>
	<#if _method=="auto">
		<#local _method=_th.getRestMethod(_formModel.formObject)>	
	</#if>
	<#local _id=id>
	<#if util.isNull(_id)>
		<#local _id=commandName>
	</#if>
	<#if !customForm>
		<@springform.form action=action commandName=commandName method=_method cssClass=class id=_id>
			<#if renderTitle>
			<div class="form-header">
				<h2 class="title"><@util.message _formBaseCaption + ".title"/></h2>
			</div>
			</#if>
			
			<#if !isFilter>
				<div class="form-content">
					<div class="form-content-inner">
						<@renderErrors additionalErrors=additionalErrors />
						<div class="row">
						<#nested>
						</div>
					</div>
				</div>
				<#if renderSaveButton>
					<div class="form-footer">
						<div class="form-footer-inner">
							<a href="javascript:$('form#${commandName}').submit();" class="btn">
								<span><@util.message "form.actions.save.button" /></span>															
							</a>
						</div>					
					</div>
				</#if>
			<#else>
				<#nested>
			</#if>
		</@springform.form>
	<#else>
		<#nested />
	</#if>
</#macro>

<#macro inputText path renderLabel=true defaultCaption="_NULL_" id="_NULL_" labelArguments=[] displayFieldError=true>
	<@input path=path renderLabel=renderLabel type="text" defaultCaption=defaultCaption id=id  labelArguments=labelArguments  displayFieldError=displayFieldError/>
</#macro>
<#macro inputPassword path renderLabel=true defaultCaption="_NULL_" id="_NULL_" labelArguments=[] displayFieldError=true>
	<@input path=path renderLabel=renderLabel type="password" defaultCaption=defaultCaption id=id  labelArguments=labelArguments  displayFieldError=displayFieldError/>
</#macro>
<#macro inputDate path defaultCaption="_NULL_" id="_NULL_" labelArguments=[] displayFieldError=true>
	<@input path=path type="text" spanClass="inpDate-fix" defaultCaption=defaultCaption id=id  labelArguments=labelArguments  displayFieldError=displayFieldError/>
</#macro>

<#macro inputNumber path defaultCaption="_NULL_" id="_NULL_" labelArguments=[] displayFieldError=true>
	<@input path=path type="text" class="inp-number" defaultCaption=defaultCaption id=id  labelArguments=labelArguments displayFieldError=displayFieldError/>
</#macro>

<#macro input type path renderLabel=true value="_NULL_" defaultCaption="_NULL_" id="_NULL_" class="" spanClass="" labelArguments=[] displayFieldError=true>
	<#assign _fieldModel=_formModel.getFieldModel(path)>
	<#assign _fieldError=_fieldModel.hasError()>
	<#local _id=resolveStyleId(path, id)>
	<p>
		<#if renderLabel>
			<@printLabel defaultCaption=defaultCaption path=path id=_id labelArguments=labelArguments/>
		</#if>
		<#local _value=value>
		<#if util.isNull(_value)>
			<#local _value=_formModel.readFormatedValue(path)>
		</#if>
		<span class="inp-fix <#if _fieldError>inp-error</#if> ${spanClass}">
			<input type="${type}" id="${_id}" name="${path}" class="inp-text ${class}" value="${_value}"/>
		</span>
		<#if displayFieldError>
			<@renderFieldError fieldModel=_fieldModel />
		</#if>
	</p>
</#macro>

<#macro textarea path renderLabel=true value="_NULL_" defaultCaption="_NULL_" id="_NULL_" class="" labelArguments=[]>
	<#assign _fieldModel=_formModel.getFieldModel(path)>
	<#assign _fieldError=_fieldModel.hasError()>
	<#local _id=resolveStyleId(path, id)>
	<p>
		<#if renderLabel>
			<@printLabel defaultCaption=defaultCaption path=path id=_id labelArguments=labelArguments/>
		</#if>
		<span class="inp-fix <#if _fieldError>inp-error</#if>">
		<textarea rows="5" class="inp-text" name="${path}">${_formModel.readFormatedValue(path)}</textarea>
		</span>
		<@renderFieldError fieldModel=_fieldModel />
	</p>
</#macro>

<#macro localizedInputText path defaultCaption="_NULL_">
	<#list _languages as lang>
		<#local _value=_formModel.readLocalizedValue(path, lang.id)>
		<#local _defaultCaption = defaultCaption>
		<#if util.isNull(_defaultCaption)>
		<#local _defaultCaption=buildMessageString(_formBaseCaption, path)>
		</#if>
		<@input type="text" path="${path}.asMap[${lang.id}]" value=_value defaultCaption=_defaultCaption labelArguments=[lang.id]/>
	</#list>
</#macro>

<#macro select path items="_NULL_" valueKey="_NULL_" labelKey="_NULL_" defaultCaption="_NULL_" id="_NULL_" localizedLabel=false >
	<#local _value=_formModel.readFormatedValue(path)>
	<#--TODO throw exception (when items is set then valueKey and labelKey cannot be null)
		
		<#if items!="_NULL_" && (valueKey=="_NULL_" || labelKey=="_NULL_")>
			
		</#if>
	-->
	<#assign _fieldModel=_formModel.getFieldModel(path)>
	<#assign _fieldError=_fieldModel.hasError()>
	<#local _id=resolveStyleId(path, id)>
	<p>
	<@printLabel defaultCaption=defaultCaption path=path id=_id/>
	<span class="select-fix">
	<select name="${path}" class=" <#if _fieldError>error-inp</#if>" id="${_id}" style="width:100%">
		<#nested>
		<#if !util.isNull(items)>
			<#list items as i>
				<#local _itemValue=util.evaluateAsString(valueKey, i)>
				<#local _itemLabel=util.evaluateAsString(labelKey, i)>
				<#if localizedLabel>
					<#local _itemLabel=util.getMessage(_itemLabel)>
				</#if>
				<option value="${_itemValue}" <#if _itemValue==_value>selected="selected"</#if>>${_itemLabel}</option>
			</#list>
		</#if>
	</select>
	</span>
	<@renderFieldError fieldModel=_fieldModel />
	</p>
</#macro>

<#macro checkbox path defaultCaption="_NULL_" defaultLabel="_NULL_" id="_NULL_" value="_NULL_" foreignValue="true" breakLabel=true labelFirst=true>
	<#local _val=value>
	<#if util.isNull(_val) || _val=="">
		<#local _val=_formModel.readFormatedValue(path)>
	<#else>
		<#local _val="on">
	</#if>
	
	<#local _checkedString="">
	<#if (_val=="true" || _val=="on")>
		<#local _checkedString="checked='checked'">
	</#if>
	<#local _id=resolveStyleId(path, id)>
	<#if labelFirst>
		<#assign _fieldModel=_formModel.getFieldModel(path)>
		<@printLabel renderColon=labelFirst defaultLabel=defaultCaption defaultLabel=defaultLabel path=path id=_id breakLabel=breakLabel/>
	</#if>
	<input type="checkbox" id="${_id}" name="${path}" ${_checkedString} value="${foreignValue}"/>
	<input type="hidden" name="_${path}" value="on" />
	<#if !labelFirst>
		<@printLabel renderColon=labelFirst defaultLabel=defaultCaption defaultLabel=defaultLabel path=path id=_id breakLabel=breakLabel/>
	</#if>
</#macro>

<#macro checkboxList path data labelKey>
	<#if (data?size>0)>

		<#list data as d>
			<p>
				<#local _path=path + "["+d_index+"]">
				<#local _val=_formModel.readFormatedValue(_path + ".id")>
				
				<@checkbox labelFirst=false path=_path foreignValue=d.id value= _val breakLabel=false defaultLabel=util.evaluate(labelKey, d)/>
			</p>
		</#list>
	
	</#if>
</#macro>

<#macro renderErrors additionalErrors=[]>
	<#local _errors=_formModel.formErrorsAsString>
	<#if (_errors?size>0)>
		<div class="message message-error">
			<#list _errors as e>
				<p>${e}</p>
			</#list>
			<#local _addErrors=_formModel.readCustomValidationMessages(additionalErrors)>
			<#if (_addErrors?? && _addErrors?size>0)>
			<#list _addErrors as err>
				<p>${err}</p>
			</#list>
		</#if>
		</div>
	</#if>		
</#macro>

<#macro renderFieldError fieldModel>
<#local errors=fieldModel.errorMessages>
<#if fieldModel.hasError()>
	<#list errors as e>
		<br />${e.message}<#t>
	</#list>
</#if>
</#macro>

<#macro printLabel path id labelArguments=[] defaultCaption="_NULL_" defaultLabel="_NULL_" breakLabel=true renderColon=true>
	<#local _labelText="_NULL_"/>
	<#if !util.isNull(_formBaseCaption)>
		<#local _fullMessageKey=buildMessageString(_formBaseCaption, path)>
		<#local _labelText><@util.message code=_fullMessageKey arguments=labelArguments /></#local>
	</#if>
	<#if !util.isNull(defaultCaption)>
		<#local _labelText><@util.message code=defaultCaption arguments=labelArguments /></#local>
	</#if>
	<#if !util.isNull(defaultLabel)>
		<#local _labelText=defaultLabel />
	</#if>
	<#local required=_fieldModel.isRequired(_showRequireIndex)>
	
	<#if !util.isNull(_labelText)>
		<label for="${id}" class="label<#if _fieldError!false> error</#if>">${_labelText}<#t><#if renderColon>:</#if><#if required>&nbsp;<span class="req">*</span></#if></label>
		<#if breakLabel>
		<br/>
		</#if>
	</#if>
</#macro>

<#macro radioGroup path items labelKey valueKey localizedLabel=false>
<#local _fullMessageKey=buildMessageString(_formBaseCaption, path)>
<#local _value=_formModel.readFormatedValue(path)>
<p>
	<span class="label"><@util.message code=_fullMessageKey />:</span>
	<span class="inp-set">
		<#list items as i>
			<#local _itemValue=util.evaluateAsString(valueKey, i)>
			<#local _itemLabel=util.evaluateAsString(labelKey, i)>
			<#if localizedLabel>
					<#local _itemLabel=util.getMessage(_itemLabel)>
				</#if>
			<label><input type="radio" name="${path}" value="${_itemValue}" <#if _itemValue==_value>checked="checked"</#if>>${_itemLabel}</label>
			
		</#list>
		<#nested _value>
	</span>
</p>
</#macro>

<#function resolveStyleId path id>
<#local _styleId=path>
<#if !util.isNull(id)>
<#local _styleId=id>
</#if>
<#return _styleId>
</#function>

<#function buildMessageString arg1 arg2>
	<#return (arg1 + "." + arg2)>
</#function>