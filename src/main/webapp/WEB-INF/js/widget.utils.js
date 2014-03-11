(function( $, undefined ) {
	
	function Utils() {
		this._defaults = {
				rest_input_name: '_method',
				text_button_ok:'Ok',
				text_button_no:'No',
				dialog_text:'Opravdu chcete akci uskutecnit?',
				ajax_fail_text: 'Pri odesilani dat doslo k chybe.',
		};
		return this;
	}
	function AhrefWrapper(href) {
		this.href = href;
		return this;
	}
	function UIAlert() {
		this.uialert = $('<div />');
		$('body').append(this.alertDiv);
		return this;
	}
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
				if(!_instance.destination) {
					_instance.destination = $('<div />');
					$('body').append(_instance.destination);
				}
				$.ajax({
					data: _data,
					type: _type,
					url: _instance.link() + "?_simple=true",
					async: _instance.async(),
				}).done(function(result) {
					_instance.destination.html(result);
					$.utils.wrapForms(_instance.destination);
					_instance.destination.dialog({width:"auto"});
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
	$.extend(Utils.prototype, {
		setDefaults: function(settings) {
			extendRemove(this._defaults, settings || {});
			return this;
		},
		wrapForms: function(root) {
			
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
				if(!_instance.area) {
					_instance.area = $("<div />");
					$('body').append(_instance.area);		
				}
				_instance.area.text(_instance._defaults.dialog_text);
				_instance.area.dialog({
					buttons: [
			          {
			            text: _instance._defaults.text_button_ok,
			            click: function() {
			              href.process();
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
			} else {
				href.process();
			}
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
	$.utils = new Utils();
	$.uialert = new UIAlert();
})(jQuery);