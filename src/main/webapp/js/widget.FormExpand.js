/*create widget namespace*/
var Widget = Widget || {};
// Rozbaleni/Zabaleni formulare
Widget.FormExpand = function(element){
	this.element = $(element);
	this.options = null;
}
Widget.FormExpand.prototype = {
	defaults: {
		classOpen: 'form-expand-openx',
		duration: 350,
		toogleBtn: null
	},
	create: function(){
		this.options = $.extend({}, this.defaults, this.options, this.element.data('options'));
		this.element.data("Widget_FormExpand", this); // je potřeba pro přístup k instancím pluginu 
		this.init();
		return this;
	},
	init: function(){
		var self = this;

		this.box = self.element;
		this.header = $('.expand-header', this.box);
		this.content = $('.form-expand-content', this.box);
		this.btnOpen = $('<a class="form-button-open" href="#"><span class="ico ico-unpack"></span>Rozšířené vyhledávání</a>');
		this.btnClose = $('<a class="form-button-close" href="#"><span class="ico ico-pack"></span>Základní vyhledávání</a>');
		this.existsheaderBtn = ($('.btns', this.header).length > 0) ? true : false;
		
		if (this.existsheaderBtn) {
			this.headerBtn = $('.btns', this.header); 
		} else {
			this.headerBtn = $('<div class="btns"></div>');
		}
		
		this._makeFake();
		this._bind();
	},

	_makeFake: function(){
		var self = this;
		
		if (self.options.toogleBtn == null) { 
			self.headerBtn.prepend(self.btnOpen);
			self.headerBtn.prepend(self.btnClose);
		}
		
		if (!$(self.box).hasClass(self.options.classOpen))
		{// Neni pozadavek na zobrazeni rozbaleneho formulare - zabalime formular
			self.content.hide();
			self.btnClose.hide();
		} else 
		{
			self.content.show();
			self.btnOpen.hide();
		}
		
		if (!self.existsheaderBtn && self.options.toogleBtn == null) {
			$('.title', self.header).after(self.headerBtn);
		}
	},
	
	_bind: function(){
		var self = this;
		
		if (self.options.toogleBtn == null) { 
			// Open form
			self.btnOpen.click(function(){
				self.btnOpen.hide();
				self.btnClose.show();
				self.open();

				return false;
			});
	
			// Close form
			self.btnClose.click(function(){
				self.btnOpen.show();
				self.btnClose.hide();
				self.close();

				return false;
			});
		} else {
			// Je nadefinovan vlastni toogle tlacitko pro otevreni/zavreni formulare
			$(self.options.toogleBtn, self.header).click(function(){
				if(self.content.is(':visible')){
					self.close();
				} else {
					self.open();
				}
				return false;	
			
			});
		}
	},
	
	// Open form
	open: function(){
		var self = this;

		self.box.addClass(self.options.classOpen);
		self.content.slideDown(self.options.duration);
	},
	
	// Close form
	close: function(){
		var self = this;

		self.box.removeClass(self.options.classOpen);
		self.content.slideUp(self.options.duration);
	}
}

$.fn.widget_FormExpand = function(options, args){
	return this.each(function() {
		
		new Widget.FormExpand(this, options).create();
	});
};