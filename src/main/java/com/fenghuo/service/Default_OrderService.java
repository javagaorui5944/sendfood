package com.fenghuo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.BuildingDao;
import com.fenghuo.dao.Default_OrderDao;
import com.fenghuo.dao.DormitoryDao;
import com.fenghuo.dao.Order_ItemDao;
import com.fenghuo.dao.SchoolDao;
import com.fenghuo.dao.SnackDao;
import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.building;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.order_item;
import com.fenghuo.domain.school;
import com.fenghuo.model.codeAndAdderss;
import com.fenghuo.redisDao.OrderRedisDao;
import com.fenghuo.util.CommonUtil;

@Service
public class Default_OrderService {
	@Autowired
	private Default_OrderDao default_OrderDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private DormitoryDao dormitoryDao;
	@Autowired
	private Order_ItemDao order_itemDao;
	@Autowired
	private SnackDao snackDao;
	@Autowired
	private OrderRedisDao orderRedisDao;

	public int InsertDefaultOrder(Default_order df_odr) {
		return default_OrderDao.InsertDefaultOrder(df_odr);
	}

	public codeAndAdderss getOrderCode(long school_id, long building_id,
			long dormitory_id) {
		school school = schoolDao.getSchoolCode(school_id);
		building building = buildingDao.getBuildingCode(building_id);
		dormitory dormitory = dormitoryDao.getDormitoryCode(dormitory_id);
		codeAndAdderss caa = new codeAndAdderss();
		caa.setCode(school.getSchool_code() + "-" + building.getBuilding_code()
				+ "-" + dormitory.getDormitory_code());
		caa.setAddress(school.getSchool_name() + "-"
				+ building.getBuilding_name() + "-"
				+ dormitory.getDormitory_name());
		return caa;
	}
	public codeAndAdderss getOrderCode1(	long dormitory_id) {
		
		dormitory dormitory = dormitoryDao.getDormitoryCode(dormitory_id);
		building building = buildingDao.getBuildingCode(dormitory.getBuilding_id());
		school school = schoolDao.getSchoolCode(building.getSchool_id());//inner joinschool on building.school_id=school.school_id 
		
		codeAndAdderss caa = new codeAndAdderss();
		caa.setCode(school.getSchool_code() + "-" + building.getBuilding_code()
				+ "-" + dormitory.getDormitory_code());
		caa.setAddress(school.getSchool_name() + "-"
				+ building.getBuilding_name() + "-"
				+ dormitory.getDormitory_name());
		return caa;
	}

	public List<Default_order> getDefaultOrderBySchId(long school_id,
			int default_order_status) {
		return default_OrderDao.getDefaultOrderBySchId(school_id,
				default_order_status);
	}

	public long getDefaultOrder(long school_id) {
		return default_OrderDao.getDefaultOrder(school_id);
	}
	public long getDefaultOrderByDormitory_Id(long dormitory_id) {
		return default_OrderDao.getDefaultOrderByDormitory_Id(dormitory_id);
	}

	public long getDefaultOrderBydormitoryId(long dormitoryId) {
		return default_OrderDao.getDefaultOrderBydormitoryId(dormitoryId);
	}

	public int getNoSendOrder(long dormitory_id) {
		return default_OrderDao.getNoSendOrder(dormitory_id);
	}

	/**
	 * 获取标准套餐所属学校
	 */
	public long getSchoolByDefaultOrder(long default_order_id) {
		return default_OrderDao.getSchoolByDefaultOrder(default_order_id);
	}

	/**
	 * 获取标准套餐所属学校
	 */
	public long addDefaultOrder(long school_id, String default_order_name) {
		if (default_OrderDao.addDefaultOrder(school_id, default_order_name) == 1) {
			return default_OrderDao
					.getNewOrderId(school_id, default_order_name);
		} else {
			return 0;
		}
	}

	public void saveOrder2Redis(JSONObject jo, long school_id) {
		orderRedisDao.redis_addOrder(jo, school_id);
	}

	public long updateStatus(long default_order_id, int default_order_status) {
		return default_OrderDao.updateStatus(default_order_id,
				default_order_status);
	}

	public void updateStatus_1(long default_order_id, int default_order_status) {
		default_OrderDao.updateStatus_1(default_order_id, default_order_status);
	}

	public Object getDefaultOrder_1(long school_id) {
		JSONObject jo = orderRedisDao.redis_getOrder(school_id);
		if (jo != null) {
			return jo;
		}
		System.out.println("jo:" + jo);
		List<Long> ls = default_OrderDao.getDefaultOrder1(school_id);
		List<order_item> items = new ArrayList<order_item>();
		JSONObject json1 = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		for (long l : ls) {
			JSONObject json = new JSONObject();
			items = order_itemDao.getOrderitemByDefaultOrderId(l);
			List<Snacks> snacks = new ArrayList<Snacks>();
			for (order_item oi : items) {
				Snacks s = snackDao.getSnacksById(oi.getSnacks_id());
				s.setSnacks_stock_number(oi.getSnacks_number());
				snacks.add(s);

			}
			json.put("snacks", snacks);
			json.put("default_order_id", l);
			jsonarray.add(json);

		}
		json1.put("data", jsonarray);
		return json1;
		/*
		 * Long default_order_id = default_OrderDao.getDefaultOrder(school_id);
		 * if(default_order_id == null || default_order_id == 0){ return null; }
		 * List<order_item> items =
		 * order_itemDao.getOrderitemByDefaultOrderId(default_order_id);
		 * List<Snacks> snacks = new ArrayList<Snacks>(); for(order_item oi :
		 * items){ Snacks s = snackDao.getSnacksById(oi.getSnacks_id());
		 * s.setSnacks_stock_number(oi.getSnacks_number()); snacks.add(s); }
		 * orderRedisDao.redis_addOrder(CommonUtil.defaultOrder2JSON(snacks),
		 * school_id); return snacks;
		 */

	}

	public void updateRedisOrder(long school_id) {
		/*
		 * orderRedisDao.redis_deleteOrder(school_id); Long ls =
		 * default_OrderDao.getDefaultOrder(school_id); List<order_item> items =
		 * order_itemDao.getOrderitemByDefaultOrderId(ls); List<Snacks> snacks =
		 * new ArrayList<Snacks>(); for(order_item oi : items){ Snacks s =
		 * snackDao.getSnacksById(oi.getSnacks_id());
		 * s.setSnacks_stock_number(oi.getSnacks_number()); snacks.add(s); }
		 * orderRedisDao.redis_addOrder(CommonUtil.defaultOrder2JSON(snacks),
		 * school_id);
		 */
		orderRedisDao.redis_deleteOrder(school_id);
		List<Long> ls = default_OrderDao.getDefaultOrder1(school_id);
		List<order_item> items = new ArrayList<order_item>();
		JSONObject json1 = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		for (long l : ls) {
			JSONObject json = new JSONObject();
			items = order_itemDao.getOrderitemByDefaultOrderId(l);
			List<Snacks> snacks = new ArrayList<Snacks>();
			for (order_item oi : items) {
				Snacks s = snackDao.getSnacksById(oi.getSnacks_id());
				s.setSnacks_stock_number(oi.getSnacks_number());
				snacks.add(s);

			}
			json.put("snacks", snacks);
			json.put("default_order_id", l);
			jsonarray.add(json);

		}
		json1.put("data", jsonarray);

		orderRedisDao.redis_addOrder(json1, school_id);
	}

	public JSONArray getDefaultOrder_IdNameBySchool_id(long school_id) {

		List<Default_order> l = default_OrderDao
				.getDefaultOrder_IdNameBySchool_id(school_id);

		JSONArray json = new JSONArray();
		for (Default_order o : l) {
			JSONObject jo = new JSONObject();
			jo.put("default_order_id", o.getDefault_order_id());
			jo.put("default_order_name", o.getDefault_order_name());
			json.add(jo);
		}

		return json;
	}

}
