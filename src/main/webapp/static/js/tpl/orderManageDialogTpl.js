define(function(){
	var dialogtpl = '<div class="panel panel-default">'+
		 '<div class="panel-tab">'+
		    '<ul class="wizard-steps" id="wizardTab">'+
		         '<li class="">'+
		             '<a href="javascript:;" data-toggle="tab">未配送</a>'+
		         '</li>'+
		         '<li class="">'+
		             '<a href="javascript:;" data-toggle="tab">配送中</a>'+
		         '</li>'+
		         '<li class="">'+
		             '<a href="javascript:;"  data-toggle="tab">配送完成</a>'+
		         '</li>'+
		         '<li class="">'+
		             '<a href="javascript:;" data-toggle="tab">完成结算</a>'+
		         '</li>'+
		         '<li class="">'+
		             '<a href="javascript:;"  data-toggle="tab">返货入库</a>'+
		         '</li>'+
		     '</ul>'+
		 '</div>'+
		 '<div class="panel-body">'+
		     '<div class="tab-content">'+
		         '<div class="tab-pane fade active in" id="wizardContent1">'+
				 '<div class="loading" style="width:100px;height:100px;position:relative;left:50%;margin-left:-50px;top:50%;"></div>'+
		         '</div>'+
		     '</div>'+
		 '</div>'+
		 '<div class="panel-footer" style="height:30px">'+
		       '<div class="pull-right" style="width:30%">'+
		         '<div class="progress progress-striped active m-bottom-none">'+
		             '<div class="progress-bar progress-bar-success" id="wizardProgress" style="width: 33%;"></div>'+
		         '</div>'+
		     '</div>'+
		 '</div>'+
		'</div>';
	var order10 = '<h4>订单信息&nbsp;&nbsp;订单创建时间：&nbsp;&nbsp;${order_create_time}</h4>'+
			        '<ul class="order-msg">'+
					    '<li><span>订单号</span><span>${order_query_id}</span>'+
					    '<span>学校</span><span>${school}</span>'+
						'<span>楼栋</span><span>${building}</span>'+
						'<span>寝室</span><span>${dormitory}</span></li>'+
					'</ul>'+
					'<h4>商品信息</h4>'+
					 '&nbsp;&nbsp;订单总价格：<span style="color:red">${order_cost_money}</span>&nbsp;&nbsp;元</h4>'+
					'<table class="table table-bordered table-condensed table-hover table-striped">'+
					    '<tr>'+
					        '<th>序号</th>'+
					        '<th>商品条形码</th>'+
					        '<th>商品名</th>'+
					        '<th>售价</th>'+
					        '<th>商品数量</th>'+
					    '</tr>'+
					    '<tbody>'+
							'{@each orderitemlist as item,index}'+
							'<tr>'+
								'<td>${index}</td>'+
								'<td>${item.snacks_bar_code}</td>'+
								'<td>${item.snacks_name}</td>'+
								'<td>${item.snacks_sell_price}</td>'+
								'<td>${item.snacks_number}</td>'+
							'</tr>'+
							'{@/each}'+
					    '</tbody>'+
					'</table>'+
					'<h4>配送人员修改（正常情况下不用修改）</h4>'+
					'<select class="input-sm form-control inline" id="J-sendPerson" style="width:130px;">'+
						'{@each personlist.fenpei as item,index}'+
						  '<option value="${item.staff_id}" {@if item.staff_name == staff_name}selected {@/if}>${item.staff_name}</option>'+
						'{@/each}'+
					'</select>'+
					'<a class="btn btn-default btn-sm" style="margin-left:10px;" id="J-revisePerson" >确认配送人员并开始配送</a>';

    var order20 = '<h4>订单信息&nbsp;&nbsp;订单创建时间：&nbsp;&nbsp;${order_create_time}</h4>'+
        '<ul class="order-msg">'+
        '<li><span>订单号</span><span>${order_query_id}</span>'+
        '<span>学校</span><span>${school}</span>'+

        '</li>'+
        '<li><span>楼栋</span><span>${building}</span>'+
        '<span>寝室</span><span>${dormitory}</span></li>'+
        '</ul>'+
        '<h4>商品信息(&nbsp;&nbsp;配送人员<span style="margin-left:10px;color:red;font-size:14px;">${staff_name}</span>)</h4>'+
         '&nbsp;&nbsp;订单总价格：<span style="color:red">${order_cost_money}</span>&nbsp;&nbsp;元</h4>'+
        '<table class="table table-bordered table-condensed table-hover table-striped">'+
        '<tr>'+
        '<th>序号</th>'+
        '<th>商品条形码</th>'+
        '<th>商品名</th>'+
        '<th>售价</th>'+
        '<th>商品数量</th>'+
        '</tr>'+
        '<tbody>'+
        '{@each orderitemlist as item,index}'+
        '<tr>'+
        '<td>${index}</td>'+
        '<td>${item.snacks_bar_code}</td>'+
        '<td>${item.snacks_name}</td>'+
        '<td>${item.snacks_sell_price}</td>'+
        '<td>${item.snacks_number}</td>'+
        '</tr>'+
        '{@/each}'+
        '</tbody>'+
        '</table>';


    var order30 = '<h4>订单信息&nbsp;&nbsp;订单创建时间：&nbsp;&nbsp;${order_create_time}</h4>'+
						'<ul class="order-msg">'+
							'<li><span>订单号</span><span>${order_query_id}</span>'+
							'<span>学校</span><span>${school}</span>'+

							'</li>'+
							'<li><span>楼栋</span><span>${building}</span>'+
							'<span>寝室</span><span>${dormitory}</span></li>'+
						'</ul>'+
						'<h4>商品信息(&nbsp;&nbsp;配送人员<span style="margin-left:10px;color:red;font-size:14px;">${staff_name}</span>)</h4>'+
						 '&nbsp;&nbsp;订单总价格：<span style="color:red">${order_cost_money}</span>&nbsp;&nbsp;元</h4>'+
						'<table class="table table-bordered table-condensed table-hover table-striped">'+
							'<tr>'+
								'<th>序号</th>'+
								'<th>商品条形码</th>'+
								'<th>商品名</th>'+
								'<th>售价</th>'+
								'<th>商品数量</th>'+
								'<th>消耗数量</th>'+
							'</tr>'+
							'<tbody>'+
								'{@each orderitemlist as item,index}'+
								'<tr>'+
									'<td>${index}</td>'+
									'<td>${item.snacks_bar_code}</td>'+
									'<td>${item.snacks_name}</td>'+
									'<td>${item.snacks_sell_price}</td>'+
									'<td>${item.snacks_number}</td>'+
                                    '<td><input type="text" style="width: 100px;" placeholder="请填入数字" class="form-control consumeNumber" snacks_number = "${item.snacks_number}" snacks_id="${item.snacks_id}" /></td>'+
								'</tr>'+
								'{@/each}'+
							'</tbody>'+
						'</table>';



		var order40 = '<h4>订单信息&nbsp;&nbsp;订单创建时间：&nbsp;&nbsp;${order_create_time}</h4>'+
            '<ul class="order-msg">'+
            '<li><span>订单号：&nbsp;&nbsp;</span><span>${order_query_id}&nbsp;&nbsp;</span>'+
            '<span>学校：&nbsp;&nbsp;</span><span>${school}&nbsp;&nbsp;</span>'+
            '<span>楼栋：&nbsp;&nbsp;</span><span>${building}&nbsp;&nbsp;</span>'+
            '<span>寝室：&nbsp;&nbsp;</span><span>${dormitory}&nbsp;&nbsp;</span></li>'+
            '</ul>'+
            '<h4>商品信息' +
            '&nbsp;&nbsp;订单销售总价格：<span style="color:red">${order_cost_money}</span>&nbsp;&nbsp;元</h4>'+
            '<table class="table table-bordered table-condensed table-hover table-striped">'+
            '<tr>'+
            '<th>序号</th>'+
            '<th>商品条形码</th>'+
            '<th>商品名</th>'+
            '<th>售价</th>'+
            '<th>剩余数量</th>'+
            '<th>消耗数量</th>'+
            '</tr>'+
            '<tbody>'+
            '{@each view as item,index}'+
                '<tr>'+
                    '<td>${index}</td>'+
                    '<td>${item.snacks_bar_code}</td>'+
                    '<td>${item.snacks_name}</td>'+
                    '<td>${item.snacks_sell_price}</td>'+
                    '<td>${item.snacks_number - item.eat_number}</td>'+
                    '<td>${item.eat_number}</td>'+
               '</tr>'+
            '{@/each}'+
            '</tbody>'+
            '</table>'+
            '<h4>订单备注<span style="margin-left:10px;color:red;font-size:14px;">配送人员&nbsp;&nbsp;${staff_name}</span></h4>'+
                '<textarea class="msg form-control" id="order_note" style="height: 80px;" placeholder="请输入当前订单完成情况，比如配送是否及时，返货入库是否及时，消费评价如何等等"></textarea>';

    var order50 = '<h4>订单信息&nbsp;&nbsp;订单创建时间：&nbsp;&nbsp;${order_create_time}</h4>'+
            '<ul class="order-msg">'+
            '<li><span>订单号：&nbsp;&nbsp;</span><span>${order_query_id}&nbsp;&nbsp;</span>'+
            '<span>学校：&nbsp;&nbsp;</span><span>${school}&nbsp;&nbsp;</span>'+
            '<span>楼栋：&nbsp;&nbsp;</span><span>${building}&nbsp;&nbsp;</span>'+
            '<span>寝室：&nbsp;&nbsp;</span><span>${dormitory}&nbsp;&nbsp;</span></li>'+
            '</ul>'+
            '<h4>商品信息' +
            '&nbsp;&nbsp;订单销售总价格：<span style="color:red">${order_cost_money}</span>&nbsp;&nbsp;元</h4>'+
            '<table class="table table-bordered table-condensed table-hover table-striped">'+
            '<tr>'+
            '<th>序号</th>'+
            '<th>商品条形码</th>'+
            '<th>商品名</th>'+
            '<th>售价</th>'+
            '<th>剩余数量</th>'+
            '<th>消耗数量</th>'+
            '</tr>'+
            '<tbody>'+
            '{@each view as item,index}'+
				'<tr>'+
					'<td>${index}</td>'+
					'<td>${item.snacks_bar_code}</td>'+
					'<td>${item.snacks_name}</td>'+
					'<td>${item.snacks_sell_price}</td>'+
					'<td>${item.snacks_number - item.eat_number}</td>'+
					'<td>${item.eat_number}</td>'+
			   '</tr>'+
			'{@/each}'+
            '</tbody>'+
            '</table>'+
        '<h4>订单备注<span style="margin-left:10px;color:red;font-size:14px;">配送人员&nbsp;&nbsp;${staff_name}</span></h4>'+
        '<div>${order_note}</div>';


    return {
		dialogtpl:dialogtpl,
		order10:order10,//未配送
		order20:order20,
		order30:order30,
		order40:order40,
		order50:order50
	};
});