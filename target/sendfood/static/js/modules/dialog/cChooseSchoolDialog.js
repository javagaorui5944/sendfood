/**
 */
define(["lib/jquery","widget/dialog/cdialog",'util/request','modules/lazyload','lib/juicer'],function($,dialog,request,lazyload){
	var dialog1 = {
			"Dailog":function(ret){
				var tpl = '{@each data as item}'+
				     		 '<a href="javascript:;" data-schoolid="${item.school_id}">${item.school_name}</a>'+
					      '{@/each}';
				return juicer(tpl,ret);
			},
			"init":function(callback){
					var optdef={
							 dialog_id:'J_flay-win'	 
							,dialog_tit:'请首先选择学校'
							,dialog_body:'<div id="school-dialog">正在加载中,请稍后...</div>'//jQuery
							,dialog_footer:""
							,dialog_style:null
							,click_mask_fire:false
							,callback:function () {
							  request.post("/customer/getAllSchool",{},function(ret){
			   					$("#school-dialog").html(dialog1.Dailog(ret));
			   					
			   					//替换头部地址
			   					var tpl = '{@each data as item}'+
			   						'<li><a href="#" data-schoolid="${item.school_id}">${item.school_name}</a></li>'+
			   					 '{@/each}';
			   				    var temp =  juicer(tpl,ret);
			   					$("#J-schools").html(temp);
			   					
			   					//点击弹出框地址或者改变地址
			   					$("#school-dialog > a,#J-schools a").click(function(){
			   						$(".food-wrap").html("奋力加载中....");
									var schoolId = $(this).attr("data-schoolid");
									request.post("/order/getNextWeekOrder",{school_id:schoolId},function(ret){
										if(1 == ret.code){
											var foodtpl = '{@each snacks as item,index}'+
													'<div class="food-item {@if index % 5 == 0 && index != 0}food-item-first{@else if index == item.length}food-item-last{@/if} ">'+
													'<img data-src="http://211.155.92.139:9001/${item.snacks_pic}" class="food-img scroll-loading" />'+
													'<span class="food-name"> ${item.snacks_name} X ${item.snacks_stock_number}</span>'+
													'<span class="food-price">¥${item.snacks_sell_price}/份</span>'+
													'</div>'+
					  						      '{@/each}';
											var foodtemp = juicer(foodtpl,ret.data);
											$(".food-wrap").html(foodtemp);
										}else{
											$(".food-wrap").html(ret.msg);
										}
										lazyload();
									});
									var schoolName = $(this).html();
									$("#J-choose-sch").html(schoolName);
									$(".dialog-close").click();
									
									//点击学校改变学校id
									callback(schoolId);
								});
			   				   });  
								
							}
						 };
					   dialog.init(optdef);
				
			}
			
	};
	return dialog1.init;
		
});