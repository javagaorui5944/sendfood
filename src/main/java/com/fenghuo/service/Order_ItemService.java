package com.fenghuo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.Order_ItemDao;
import com.fenghuo.dao.SnackDao;
import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.Order;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.order_item;
import com.fenghuo.redisDao.OrderRedisDao;

@Service
public class Order_ItemService {

	@Autowired
	private Order_ItemDao order_itemDao;
	@Autowired
	private SnackDao snackDao;
	@Autowired
	private OrderRedisDao orderRedisDao;
	private JSONArray jsonArray;
	private int pageSize = 10;

	/*
	 * 得到标准订单下面的所有零食
	 */
	public JSONArray getAllSnacksByDefaultOrderId_1(long default_order_id) {
		List<order_item> odr_items = order_itemDao
				.getOrderitemByDefaultOrderId_1(default_order_id);
		jsonArray = new JSONArray();
		for (order_item odr_item : odr_items) {
			JSONObject jsonObj = new JSONObject();
			Snacks snack = order_itemDao.getsnacksById(odr_item.getSnacks_id());
			jsonObj.put("snacks_id", snack.getSnacks_id());
			jsonObj.put("snacks_bar_code", snack.getSnacks_bar_code());
			jsonObj.put("snacks_name", snack.getSnacks_name());
			jsonObj.put("snacks_cost_price", snack.getSnacks_cost_price());
			jsonObj.put("snacks_sell_price", snack.getSnacks_sell_price());
			jsonObj.put("snacks_number", odr_item.getSnacks_number());
			jsonArray.add(jsonObj);
		}
		return jsonArray;
	}

	/*
	 * 将旧的订单内容替换成新的
	 */
	public void updateOrderItem(long oldSnack, long newSnack) {
		if (order_itemDao.updateOrderItem(oldSnack, newSnack) > 0) {
			long school_id = order_itemDao.getSchoolIdBysnack(oldSnack);
			orderRedisDao.redis_deleteOrder(school_id);
		}
	}

	/*
	 * 将旧的订单内容替换成新的
	 */
	public List<Default_order> getDefaultOrderIdBySnack(long snack_id) {
		return order_itemDao.getDefaultOrderIdBySnack(snack_id);
	}

	/*
	 * 得到标准订单下面
	 */
	public void setOrderPrice(long default_order_id, Order order) {
		List<order_item> order_items = order_itemDao
				.getOrderitemByDefaultOrderId_2(default_order_id);
		float cost = 0;
		float sell = 0;
		for (order_item oi : order_items) {
			Snacks s = snackDao.getFood(oi.getSnacks_id());
			cost += s.getSnacks_cost_price();
			sell += s.getSnacks_sell_price();
		}
		order.setOrder_cost_money(cost);
		order.setOrder_sell_money(sell);
	}

	/*
	 * 得到标准订单下面的所有零食
	 */
	public List<Snacks> getAllSnacksByDefaultOrderId(long default_order_id) {
		List<order_item> items = order_itemDao
				.getOrderitemByDefaultOrderId(default_order_id);
		List<Snacks> snacks = new ArrayList<Snacks>();
		for (order_item oi : items) {
			Snacks s = snackDao.getSnacksById(oi.getSnacks_id());
			s.setSnacks_stock_number(oi.getSnacks_number());
			snacks.add(s);
		}
		return snacks;
	}

	/*
	 * 删除标准订单下面的零食
	 */

	public int delSnacksfromDefaultOrderById(long default_order_id,
			long snacks_id) {
		return order_itemDao.delSnacksfromDefaultOrderById(default_order_id,
				snacks_id);
	}

	/*
	 * 通过名字模糊查询，并分页
	 */
	public JSONArray getSnacksByName(String snacks_name, int pageNo,
			long school_id) {
		List<Snacks> snacks = order_itemDao.getSnacksByName(snacks_name,
				(pageNo - 1) * pageSize, pageSize, school_id);
		jsonArray = new JSONArray();
		for (Snacks s : snacks) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("snacks_id", s.getSnacks_id());
			jsonObj.put("snacks_bar_code", s.getSnacks_bar_code());
			jsonObj.put("snacks_name", s.getSnacks_name());
			jsonObj.put("snacks_cost_price", s.getSnacks_cost_price());
			jsonObj.put("snacks_number", s.getSnacks_number());
			jsonObj.put("snacks_sell_price", s.getSnacks_sell_price());
			jsonArray.add(jsonObj);
		}
		return jsonArray;
	}

	public void saveOrderItem(long default_order_id, long snacks_id,
			int snacks_number) {
		order_itemDao.saveOrderItem(default_order_id, snacks_id, snacks_number);
	}

	public List<Snacks> getOrderitemByDefaultOrderId_3(long default_order_id) {
		return order_itemDao.getOrderitemByDefaultOrderId_3(default_order_id);
	}

	public List<order_item> getOrderitemByDefaultOrderId_5(long default_order_id) {
		return order_itemDao.getOrderitemByDefaultOrderId_5(default_order_id);
	}

	public List<Snacks> getOrderitemByDefaultOrderId_4(long order_id) {
		return order_itemDao.getOrderitemByDefaultOrderId_4(order_id);
	}

	public List<Snacks> getSellitemByOrderId(long orderId) {
		return order_itemDao.getSellitemByOrderId(orderId);
	}

	public List<Snacks> getSellitemByOrderId_1(long orderId) {
		return order_itemDao.getSellitemByOrderId_1(orderId);
	}

	public int addDefault_orderTodormitory(List<Long> dormitory_id,
			long default_order_id) {
		int dormitory_id_size = dormitory_id.size();
		String x = null;
		int j = 1;
		for (long i : dormitory_id) {
			if (dormitory_id_size == 1) {
				x = i + "";
			} else if (j == 1) {
				x = i + ",";
			} else if (j == dormitory_id_size) {
				x = x + i;
			}

			else {
				x = x + i + ",";
			}
			j++;

		}
		System.out.println("xixi:" + x);
		return order_itemDao.addDefault_orderTodormitory(default_order_id, x);
	}

}
