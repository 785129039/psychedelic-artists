(function( $, undefined ) {
	
	function SelectizeAjaxFactory(){
		return this;
	}
	function create(selector) {
		return new SelectizeAjax(selector);
	}
	function SelectizeAjax(selector) {
		this.config = {};
		this.selector = selector;
		return this;
	}
	$.extend(SelectizeAjaxFactory.prototype, {
		create: function(selector) {
			return new SelectizeAjax(selector);
		}
	});
	
	$.extend(SelectizeAjax.prototype, {
		initialize: function(config) {
			var _i = this;
			_i.config = config;
			_i.selectized = $(this.selector).selectize({plugins: ['remove_button'], onType:function(str) {
				_i.typed(str, $(this));
			}});
		},
	
		typed: function(str, instance) {
			var _instance = this;
			var _cfg = _instance.config;
			
			$.ajax({
	  			type: 'get',
	  			url: _cfg.url,
	  			dataType: 'json',
	  			data: {'text':str},	  			
	  			success: function(result) {
	  				for(i in result) {
	  					var data = result[i];
	  					_instance.selectized[0].selectize.addOption({
	  			            value: data[_cfg.value],
	  			            text: data[_cfg.text]
	  			        });
	  				}
	  				_instance.selectized[0].selectize.open();
	  			}
	  		});
		},
	});
	$._selectize = new SelectizeAjaxFactory();
})(jQuery);