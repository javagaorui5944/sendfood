define(['lib/jquery'], function($){
	var dom = '<div id="wmHeadertip_container" style="display:none;position:fixed;z-index:100000;left:0;top:0;width:100%;clear:both;">'+
				'<div id="wmHeadertip_alert" class="alert" >'+
				'</div>'+
				'</div>';
	var curDocument = window.document;

	var wmHeadertip = {
		success : function(message,closeable,timeout,document){
					

					if(typeof arguments[0] != 'string'){
						message = '';
					}
					if(typeof arguments[1] != 'boolean'){
						closeable = true;
					}

					if(typeof arguments[2] != 'number'){
						timeout = 10000;
					}
					
					if(typeof arguments[3] == 'undefined'){
						document = window.document
					}
					curDocument = document;
					wmHeadertipWrap(message,closeable,timeout,document);
					$('#wmHeadertip_alert',document).addClass('alert-success');

				  },
		info	: function(message,closeable,timeout){
					if(typeof arguments[0] != 'string'){
						message = '';
					}
					if(typeof arguments[1] != 'boolean'){
						closeable = true;
					}
					if(typeof arguments[2] != 'number'){
						timeout = 10000;
					}
					
					if(typeof arguments[3] == 'undefined'){
						document = window.document
					}
					curDocument = document;
					wmHeadertipWrap(message,closeable,timeout,document);
					$('#wmHeadertip_alert',document).addClass('alert-info');

				  },
		warn	: function(message,closeable,timeout,document){
					if(typeof arguments[0] != 'string'){
						message = '';
					}
					if(typeof arguments[1] != 'boolean'){
						closeable = true;
					}

					if(typeof arguments[2] != 'number'){
						timeout = 15000;
					}
					
					if(typeof arguments[3] == 'undefined'){
						document = window.document
					}
					curDocument = document;
					wmHeadertipWrap(message,closeable,timeout,document);
					$('#wmHeadertip_alert',document).addClass('alert-warning');
				  },
		error	: function(message,closeable,timeout,document){
					if(typeof arguments[0] != 'string'){
						message = '';
					}
					if(typeof arguments[1] != 'boolean'){
						closeable = false;
					}

					if(typeof arguments[2] != 'number'){
						timeout = 20000;
					}
					
					if(typeof arguments[3] == 'undefined'){
						document = window.document
					}
					curDocument = document;
					wmHeadertipWrap(message,closeable,timeout,document);
					$('#wmHeadertip_alert',document).addClass('alert-danger');
				  },
	};

	function initDom(document){
		destroy(document);
		$('body',document).prepend(dom);
		$('#wmHeadertip_container',document).hide();
	}

	function wmHeadertipWrap(message,closeable,timeout,document){
		initDom(document);		
		

		if(closeable){
			$('#wmHeadertip_alert',document).addClass('alert-dismissable');
			if($('#wmHeadertip_alert button',document).length <= 0){
				$('#wmHeadertip_alert',document).append('<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</buton>');
			}

			$('#wmHeadertip_alert button',document).click(destroy);
		}
		$('#wmHeadertip_alert',document).css('text-align','center');
		$('#wmHeadertip_alert',document).append('<span id="wmHeadertip_message" style="text-align:center;">'+message+'</span>');

		$('#wmHeadertip_container',document).show();
					

		if(timeout != 0){
			setTimeout(destroy , timeout);
		}
	}

	function destroy(document){
		if($('#wmHeadertip_container', document).length > 0){
			$('#wmHeadertip_container', document).remove();
		}
	}
	
	return wmHeadertip;

});