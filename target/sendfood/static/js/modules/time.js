/**
 * 初始化日期控件，该文件必须在jquery-ui系列JS文件引用之后引用
 */
define(['lib/jquery','lib/jquery-ui'],function($){
	
    $(".date-picker").datepicker();
    $.datepicker.setDefaults({
    	 prevText: '<上月',  
         prevStatus: '显示上月',  
         prevBigText: '<<',  
         prevBigStatus: '显示上一年',  
         nextText: '下月>',  
         nextStatus: '显示下月',  
         nextBigText: '>>',  
         nextBigStatus: '显示下一年', 
        dateFormat: "yy-mm-dd",
        dayNames : ["日","一","二","三","四","五","六"],
        dayNamesMin : ["日","一","二","三","四","五","六"],
        monthNames: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
        monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
    });
  // $(".dp").datepicker({ changeMonth: true, changeYear: true, showMonthAfterYear: true, yearRange: "2010:2014" });
});

