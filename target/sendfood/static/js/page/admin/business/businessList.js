require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request'
         ,'modules/template/uncompressed/jquery.dataTables'
    ,'modules/dialog/addDelivery'], function($,baseModule,request,dt,addDeliveryDialog) {

   var index = {
     init:function(){
         this._clickBtns();
     },
     _clickBtns:function(){
         $(".btn-revise").click(function(){
             addDeliveryDialog.init({
                 modalTitle:"配送管理",
                 initFunc:function(dialog){
                     var mbody = dialog.find(".modal-body");
                     var tpl = '';
                      mbody.html(tpl);
                 }

             });
         });
     },
     _table:function(){
    	 $('#dataTable').dataTable( {
				"bJQueryUI": true,
				"sPaginationType": "full_numbers"
			});
			$('#chk-all').click(function()	{
				if($(this).is(':checked'))	{
					$('#responsiveTable').find('.chk-row').each(function()	{
						$(this).prop('checked', true);
						$(this).parent().parent().parent().addClass('selected');
					});
				}
				else{
					$('#responsiveTable').find('.chk-row').each(function()	{
						$(this).prop('checked' , false);
						$(this).parent().parent().parent().removeClass('selected');
					});
				}
			});
			
     }
   };

     index.init();
});