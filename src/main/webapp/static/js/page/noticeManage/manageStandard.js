
require.config({
    baseUrl :  BEEN.STATIC_ROOT
});
require(['lib/jquery','modules/baseModule','util/request','modules/bridge'
         ,'util/Headertip','modules/dialog/orderManageDialog','widget/AjaxPager','lib/juicer'],
    function($,baseModule,request,bridge,Headertip,orderManageDialog,AjaxPager){

    var index = {
        init:function(){
           //取得所有学校
           this._getAllschoolLists();
           //点击操作按钮弹出模态框
           this._operateOrder();
           //搜索功能
           this._searchOrders();
        },
        //取得所有订单
        _getAllschoolLists:function(){
        	
        	AjaxPager.init({
		      elem: $("#pager"), 
		      pagerUrl:"/noticeManage/getlist",
		      current:1,
		      handle:index._dealPageData,
           });
        },
        //处理所有分页的数据
        _dealPageData:function(ret){
        	var tpl = '{@each orders as item,index}'+
			 '<tr>'+
				'<td>'+
					'${item.order_id}'+
				'</td>'+
				'<td>${item.order_query_id}</td>'+
				'<td>${item.order_create_time}</td>'+
				'<th>${item.school_name}</th>'+
				'<th>${item.building_name}</th>'+
				'<th>${item.dormitory_name}</th>'+
				'<td>${item.staff_name}</td>'+
				'<td>${item.order_status|orderStatus}</td>'+
				'<td order_query_id="${item.order_query_id}" order_id="${item.order_id}" school="${item.school_name}" staff_name="${item.staff_name}"  building="${item.building_name}" dormitory="${item.dormitory_name}"  order_status="${item.order_status}" ><a class="btn  btn-default btn-sm J-operateOrder" href="javascript:;">操作</a></td>'+
			'</tr>'+
		  '{@/each}';
			var orderStatus = function(data){
				//订单状态，10表示未配送，20表示配送中，30表示配送完成，
				//40表示结算完成，50表示返回入库
				var status = '';
				switch(data){
					case 10: status = "未配送";break;
					case 20: status = "配送中";break;
					case 30: status = "配送完成";break;
					case 40: status = "结算完成";break;
					case 50: status = "返回入库";break;
					default:return;
				}
			   return status;
			};
			juicer.register('orderStatus', orderStatus); //注册自定义函数
			var temp = juicer(tpl, ret.data);
			$("#J-orderList").html(temp);
        },
        //点击操作按钮弹出模态框
        _operateOrder:function(){
        	$("#J-orderList").on("click",".J-operateOrder",function(){


        		//弹出模态框
        		orderManageDialog.init(this);
        		
        	});
        },
        //搜索功能
        _searchOrders:function(){
        	$("#J-search").click(function(){
        		var orderOrper = $("#J-orderOrPerseon").val();
        		
        		var key = orderOrper == "0" ? "order_id" : "staff_name";
        		
        		var order_status = $("#J-orderStatus").val();
                    
        		var value = $("#J-value").val();
        		var val = {
        				
        		};
        		 if(order_status != 0){
        			 val = {
					    order_status:order_status,
	        			key:key,
	        			value:value 
        			 }
        		 }
        		 AjaxPager.init({
       		      elem: $("#pager"), 
       		      pagerUrl:"/orderManage/getAllOrder",
       		      current:1,
       		      handle:index._dealPageData,
       		      val:val
                  });
        	});
        }
      
    };

    index.init();

});