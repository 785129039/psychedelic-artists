$(document).ready(function() {
	$.utils.wrapLinks($('body'));
	$.utils.wrapForms($('body'));
	$('select.box').selectize({plugins: ['remove_button']});
	$('input.tag').selectize({
		plugins: ['remove_button', 'restore_on_backspace'],
	    delimiter: ',',
	    persist: false,
	    create: function(input) {
	        return {
	            value: input,
	            text: input
	        }
	    }
	});
	$('.form-expand').widget_FormExpand();
});