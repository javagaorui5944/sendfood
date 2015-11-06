package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Default_order;
@Component
public interface Default_OrderDao {
	
	@Insert("insert into default_order (school_id,default_order_name,default_order_status) "
			+ "values (#{school_id}),#{default_order_name},#{default_order_status})")
	public int InsertDefaultOrder(Default_order df_odr);
	
	@Select("select default_order_id,default_order_name from default_order where school_id=#{0} and default_order_status=#{1}")
	public List<Default_order> getDefaultOrderBySchId(long school_id,int default_order_status);
	
	@Select("select default_order_id from default_order where school_id=#{school_id} and default_order_status=10")
	public Long getDefaultOrder(long school_id);
	@Select("select default_order_id from default_order where school_id=#{school_id} and default_order_status=10")
	public List<Long> getDefaultOrder1(long school_id);
	
	@Select("SELECT default_order_id,default_order_name FROM default_order WHERE school_id=#{0} AND default_order_status=10 ")
	public List<Default_order> getDefaultOrder_IdNameBySchool_id(long school_id);
	
	@Select("select max(default_order_id) from default_order where school_id in (select school_id from dormitory "
			+ "where dormitory_id=#{0}) and default_order_status=10")
	public long getDefaultOrderBydormitoryId(long dormitoryId);
	
	/**
	 * 获取该寝室未配送订单
	 * @param dormitory_id
	 * @return
	 */
	@Select("select count(*) from orders where dormitory_id=#{dormitory_id} and order_status<30")
	public int getNoSendOrder(long dormitory_id);
	
	/**
	 * 获取标准套餐所属学校
	 */
	@Select("select school_id from default_order where default_order_id=#{default_order_id}")
	public int getSchoolByDefaultOrder(long default_order_id);
	
	/**
	 * 添加新标准订单
	 */
	@Insert("insert into default_order values (null,#{0},null,#{1},10)")
	public int addDefaultOrder(long school_id,String default_order_name);
	
	/**
	 * 查找新添加的标准订单编号
	 */
	@Select("select max(default_order_id) from default_order where school_id=#{0} and default_order_name=#{1} and default_order_status=10")
	public long getNewOrderId(long school_id,String default_order_name);
	
	/**
	 * 将标准订单状态修改
	 */
	@Update("update default_order set default_order_status = #{1} where default_order_id = #{0}")
	public long updateStatus(long default_order_id,int default_order_status);
	
	/**
	 * 将标准订单状态修改
	 */
	@Update("update default_order set default_order_status = #{1} where default_order_id = #{0}")
	public void updateStatus_1(long default_order_id,int default_order_status);
	
	
	@Select("SELECT default_order_id FROM dormitory WHERE dormitory_id=#{0}")
	public Long getDefaultOrderByDormitory_Id(Long dormitory_id);
	
	
	
	
	
	
	
	
	
	
	
}
