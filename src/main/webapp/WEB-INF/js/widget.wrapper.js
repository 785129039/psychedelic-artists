(function( $, undefined ) {
	
	function Wrapper() {
		this._defaults = {
				rest_input_name: '_method',
				text_button_ok:'Ok',
				text_button_no:'No',
				dialog_text:'Opravdu chcete akci uskutecnit?',
				form_text: 'Opravdu chcete odeslat formulář?',
				ajax_fail_text: 'Pri odesilani dat doslo k chybe.',
		};
		return this;
	}
	function AhrefWrapper(href) {
		this.href = href;
		return this;
	}
	function FormWrapper(form) {
		this.form = form;
		return this;
	}
	function UIAlert() {
		this.uialert = $('<div />');
		$('body').append(this.alertDiv);
		return this;
	}
	$.extend(FormWrapper.prototype, {
		reqConfirm: function() {
			return this.form.hasClass('confirm');
		},
		reqAjax: function() {
			return this.form.hasClass('ajax');
		},
		getAction: function () {
			var action = this.form.prop('action');
			var urlPar = action.split('?');
			if(urlPar.length==1) {
				return action + "?_simple=true";
			} else {
				var _simpleParam = urlPar[1].split('_simple');
				if(_simpleParam.length==1)
				return action + "&_simple=true";
			}
			return action;
		},
		async:function() {
			return this.form.hasClass('async');
		},
		process: function() {
			var _instance = this;
			if(!$.utils.formDestination) {
				$.utils.formDestination = $('<div clas="testdiv"/>');
				$('body').append($.utils.formDestination);
				this.form.after($.utils.formDestination);
				this.form.appendTo($.utils.formDestination);
			}
			if(_instance.reqAjax()) {
				$.ajax({
					data: _instance.form.serialize(),
					type: _instance.form.prop('method'),
					url: _instance.getAction(),
					async: _instance.async(),
				}).done(function(result) {
					$.utils.formDestination.html(result);
					//$.utils.formDestination.dialog("close");
					//$.utils.formDestination.dialog({width:"auto"});
					if($(result).find('div.reload').hasClass('reload')) {
						location.reload();
						return;
					}
					$.utils.wrapForms($.utils.formDestination);
					$.utils.wrapLinks($.utils.formDestination);
				}).fail(function() {
					$.uialert.show($.utils._defaults.ajax_fail_text);
				});
			}
		}
	});
	
	$.extend(AhrefWrapper.prototype, {
		reqConfirm: function() {
			return this.href.hasClass('confirm');
		},
		reqAjax: function() {
			return this.href.hasClass('ajax');
		},
		reqRest: function() {
			return this.getRestMethod()!=null;
		},
		link: function() {
			return this.href.prop("href");
		},
		async:function() {
			return this.href.hasClass('async');
		},
		getRestMethod: function (){
			if(this.href.hasClass('put')) {
				return "put";
			} else if(this.href.hasClass('delete')) {
				return "delete";
			} else if(this.href.hasClass('post')) {
				return "post";
			} 
			return null;
		},
		process: function() {
			var _instance = this;
			if(!_instance.reqAjax()) {
				_instance.doRequest();
			} else {
				var _data = {_simple:'true'};
				var _type = 'get';
				if(_instance.reqRest()) {
					_type = 'post';
					extendRemove(_data, {_method:_instance.getRestMethod()});
				}
				if(!$.utils.hrefDestination) {
					$.utils.hrefDestination = $('<div />');
					$('body').append($.utils.hrefDestination);
				}
				$.ajax({
					data: _data,
					type: _type,
					url: _instance.link(),
					async: _instance.async(),
				}).done(function(result) {
					$.utils.hrefDestination.html(result);
					$.utils.wrapForms($.utils.hrefDestination);
					$.utils.wrapLinks($.utils.hrefDestination);
					$.utils.hrefDestination.dialog({width:"auto"});
				}).fail(function() {
					$.uialert.show($.utils._defaults.ajax_fail_text);
				});
			}
			
			
		},
		
		doRequest: function() {
			if(this.reqRest()) {
				var form = $('<form />').prop('method', 'post').prop('action', this.link());
				form.append($('<input type="hidden" name="'+$.utils._defaults.rest_input_name+'" value="'+this.getRestMethod()+'"/>'));
				$('body').append(form);
				form.submit();
			} else {
				location.href = this.link();
			}
		}
	});
	$.extend(UIAlert.prototype, {
		show:function(text) {
			var _instance = this;
			_instance.uialert.text(text);
			_instance.uialert.dialog({
				buttons: [
		          {
		            text: $.utils._defaults.text_button_ok,
		            click: function() {
		              $( this ).dialog( "close" );
		            }
		          }
		        ]
			});
		}
	});
	//confirm, prompt, ajax, post, delete, put
	$.extend(Wrapper.prototype, {
		setDefaults: function(settings) {
			extendRemove(this._defaults, settings || {});
			return this;
		},
		wrapForms: function(root) {
			var _instance = this;
			root.find('form.wrap').each(function() {
				$(this).submit(function(e) {
					e.preventDefault();
					_instance.bindForm(new FormWrapper($(this)));
				});
			});
		},
		wrapLinks: function(root) {
			var _instance = this;
			root.find('a.wrap').each(function() {
				$(this).click(function(e) {
					e.preventDefault();
					_instance.bindLink(new AhrefWrapper($(this)));
				});
			});
		},
		bindLink: function(href) {
			var _instance = this;
			if(href.reqConfirm()) {
				_instance.doPrompt(href, _instance._defaults.dialog_text);
			} else {
				href.process();
			}
		},
		bindForm: function (form) {
			var _instance = this;
			if(form.reqConfirm()) {
				_instance.doPrompt(form, _instance._defaults.form_text);
			} else {
				form.process();
			}
		},
		doPrompt:function(obj, prompt_text) {
			var _instance = this;
			if(!_instance.area) {
				_instance.area = $("<div />");
				$('body').append(_instance.area);		
			}
			_instance.area.text(prompt_text);
			_instance.area.dialog({
				buttons: [
		          {
		            text: _instance._defaults.text_button_ok,
		            click: function() {
		            	obj.process();
		              $( this ).dialog( "close" );
		            }
		          },
		          {
		            text: _instance._defaults.text_button_no,
		            click: function() {
		              $( this ).dialog( "close" );
		            }
		          }
		        ]
			});
		}
	});
	function extendRemove(target, props) {
		$.extend(target, props);
		for (var name in props) {
			if (props[name] == null) {
				target[name] = props[name];
			}
		}
		return target;
	}
	$.utils = new Wrapper();
	$.uialert = new UIAlert();
})(jQuery);