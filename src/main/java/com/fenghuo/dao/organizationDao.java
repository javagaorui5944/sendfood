package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Dormitory_cus;
import com.fenghuo.domain.building;
import com.fenghuo.domain.country;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.region;
import com.fenghuo.domain.school;
import com.fenghuo.domain.staffs;

@Component
public interface organizationDao {
	
	/*
	 * 区查找
	 * region表
	 * 根据区id查找
	 */
	@Select("select region_id,region_name from region where region_id=#{0} and region_status=10")
	List<region> getRegionbyId(long id);
	
	/*
	 * 区查找
	 * region_staff表
	 * 根据区id查找
	 * 根据员工id查找
	 */
	@Select("select staff_id from region_staff where region_id=#{0} and region_staff_status=10")
	List<Long> listStaffbyRId(long id);
	
	@Select("select region_id from region_staff where staff_id=#{0} and region_staff_status=10")
	List<Long> listRegionbyId(long staff_id);
	
	/*
	 * 学校查找
	 * school表
	 * 根据学校id查找
	 * 根据区id查找
	 * 查找最新插入的学校的id
	 */
	@Select("select school_id,region_id,school_name,school_code from school where school_id=#{0} and school_status=10")
	List<school> getSchoolbyId(long id);

	@Select("select * from school where region_id=#{0} and school_status=10")
	List<school> listSchoolbyRegion(long id);

	@Select("select max(school_id) from school")
	int getLastS();
	
	/*
	 * 学校查找
	 * school_staff表
	 * 根据学校id查找
	 * 根据员工id查找
	 */
	@Select("select staff_id from school_staff where school_id=#{0} and school_staff_status=10")
	List<Long>listStaffbySId(long id);

	@Select("select school_id from school_staff where staff_id=#{0} and school_staff_status=10")
	List<Long> listSchoolbyId(long staff_id);
	
	/*
	 * 楼栋查找
	 * building表
	 * 根据楼栋id查找
	 * 查询最新插入的楼栋id
	 */
	@Select("select building_id,school_id,building_name,building_code from building where building_id=#{0} and building_status=10")
	List<building>getbuildingbyId(long id);
	
	@Select("select max(building_id) from building")
	int getLastB();
	
	/*
	 * 楼栋查找
	 * building_staff表
	 * 根据员工id查找楼栋id
	 * 根据楼栋id查找
	 * 根据学校id查找
	 * 根据员工id查找他管辖的楼栋数量
	 */
	@Select("select building_id from building_staff where staff_id=#{0} and building_staff_status=10")
	List<Long> listbuildingbyStaff(long staff_id);

	@Select("select staff_id from building_staff where building_id=#{0} and building_staff_status=10")
	List<Long>listStaffbyBId(long id);

	@Select("select building_id,school_id,building_name,building_code from building where school_id=#{id} and building_status=10")
	List<building> listbuildingbySchool(long id);

	@Select("select count(*) from building_staff where staff_id=#{0}")
	int listBuildingbyId(long staff_id);
	
	/*
	 * 寝室查找
	 * 根据楼栋id查找
	 * 根据寝室id查找员工id
	 * 根据员工id查找他管辖寝室的数量
	 */
	@Select("select dormitory_id,building_id,staff_id,dormitory_name,dormitory_code,date from dormitory where staff_id=#{0} and dormitory_status=10")
	List<Dormitory_cus> getdormitorybystaffId(long staff_id);
	
	@Select("select dormitory_id,building_id,staff_id,dormitory_name,dormitory_code,date from dormitory where dormitory_id=#{0} and dormitory_status=10")
	List<dormitory> getdormitorybyId(long id);
	
	@Select("select dormitory_id,building_id,staff_id,dormitory_name,dormitory_code,date from dormitory where building_id=#{0} and dormitory_status=10")
	List<dormitory> getdormitorybyBId(long id);

	@Select("select staff_id from dormitory where dormitory_id=#{0}")
	List<Long> getStaffbyDId(long id);
	
	@Select("select count(*) from dormitory where staff_id=#{0}")
	int listDormitorybyId(long staff_id);
	
	/*
	 * 员工查找
	 * 根据员工id查找
	 * 列出未分配人员
	 * 根据员工id查找权限
	 */
	@Select("select staff_id,staff_name from staff where staff_id=#{0} and staff_status=10")
	List<staffs> getStaffbyId(long id);
	
	@Select("select staff_id,staff_name from staff where staff_rank=0 and staff_status=10")
	List<staffs> listXstaff();
	
	@Select("select staff_rank from staff where staff_id=#{0} and staff_status=10")
	List<Long> getType(long id);
	
	/*
	 * 修改员工权限
	 */
	@Update("update staff set staff_rank=#{1} where staff_id=#{0}")
	int setType(long staff_id, int staff_rank);
	
	/*
	 * 根据员工id和楼栋id删除中间表
	 */	
	@Delete("delete from building_staff where staff_id=#{0} and building_id=#{1}")
	int deleteBuilding_staff1(long staff_id, long building_id);
	
	/*
	 * 根据员工id和学校id删除中间表
	 */	
	@Delete("delete from school_staff where staff_id=#{0} and school_id=#{1}")
	int deleteSchool_staff1(long staff_id, long school_id);	
	
	/*
	 * 根据员工id和区id删除中间表
	 */	
	@Delete("delete from region_staff where staff_id=#{0} and region_id=#{1}")
	int deleteRegion_staff1(long staff_id, long region_id);	
	
	/*
	 * 添加寝室级别组织,并增加相关配货人员管理寝室的数量;
	 */	
	@Insert("insert into dormitory(building_id,staff_id,dormitory_code,dormitory_name,dormitory_password,date,dormitory_status) "
			+ "value(#{0},#{1},#{2},#{3},123456,#{4},10);")
	int addDormitory(long pid,long staff_id,String Organization_name,String code,String date);
	
	/*
	 * 修改寝室级别组织,并更新相关配货人员管理寝室的数量;
	 */		
	@Update("update dormitory set building_id=#{0},staff_id=#{1},dormitory_code=#{3},dormitory_name=#{2},date=#{4} where dormitory_id=#{5};")
	int updateDormitory(long pid,long staff_id,String Organization_name,String code,String date,long id);
	
	/*
	 * 删除寝室级别组织;
	 */		
	@Delete("delete from dormitory where dormitory_id=#{0};")
	int deleteDormitory(long id);
	
	/*
	 * 添加楼栋级别组织
	 * 添加员工与楼栋中间表
	 */	
	@Insert("insert into building(school_id,building_name,building_code,building_status) value(#{0},#{1},#{2},10);")
	int addBuilding(long pid,String Organization_name,String code);
	
	@Insert("insert into building_staff(staff_id,building_id,building_start_time,building_staff_status) value(#{0},#{1},(select sysdate()),10)")
	int addBuilding_staff(long staff_id, long id);
	
	/*
	 * 修改楼栋级别组织
	 */	
	@Update("update building set school_id=#{0},building_code=#{2},building_name=#{1} where building_id=#{3};")
	int updateBuilding(long pid,String Organization_name,String code,long id);	
	
	/*
	 * 删除楼栋级别组织
	 * 删除员工与楼栋中间表
	 */		
	@Delete("delete from building where building_id=#{0};")
	int deleteBuilding(long id);
	
	@Delete("delete from building_staff where building_id=#{0}")
	int deleteBuilding_staff(long id);
	
	/*
	 * 添加学校级别组织
	 * 添加员工与学校中间表
	 * 添加仓库
	 */	
	@Insert("insert into school(region_id,school_name,school_code,school_status) value(#{0},#{1},#{2},10);")
	int addSchool(long pid,String Organization_name,String code);
	
	@Insert("insert into school_staff(staff_id,school_id,school_start_time,school_staff_status) value(#{0},#{1},(select sysdate()),10);")
	int addSchool_staff(long staff_id, long school_id);
	
	@Insert("insert into storage(school_id,storage_name) value(#{1},#{0})")
	int addStorage(String name, long school_id);
	
	/*
	 * 修改学校级别组织
	 */	
	@Update("update school set region_id=#{0},school_code=#{2},school_name=#{1} where school_id=#{3};")
	int updateSchool(long pid,String Organization_name,String code,long id);
	
	/*
	 * 删除学校级别组织
	 * 删除员工与学校中间表
	 * 删除仓库
	 */
	@Delete("delete from school where school_id=#{0};")
	int deleteSchool(long id);
	
	@Delete("delete from school_staff where school_id=#{0}")
	int deleteSchool_staff(long id);
	
	@Delete("delete from storage where school_id=#{0}")
	int deleteStorage(long id);
	
	/*
	 * 添加区级别组织
	 * 添加员工与区中间表
	 */	
	@Insert("insert into region(region_name,region_status) value(#{0},10);")
	int addRegion(String Organization_name);
	
	@Insert("insert into region_staff(staff_id,region_id,region_start_time,region_staff_status) value(#{0},#{1},(select sysdate()),10)")
	int addRegion_staff(long staff_id, long id);
	
	@Select("select max(region_id) from region")
	int getLastR();
	
	/*
	 * 修改区级别组织
	 */	
	@Update("update region set region_name=#{0} where region_id=#{1};")
	int updateRegion(String Organization_name,long id);
	
	/*
	 * 删除区级别组织
	 * 删除员工与区中间表
	 */
	@Delete("delete from region where region_id=#{0};")
	int deleteRegion(long id);
	
	@Delete("delete from region_staff where region_id=#{0}")
	int deleteRegion_staff(long id);
	
	/*
	 * 检查组织是否已经存在
	 */
	@Select("select count(*) from dormitory where dormitory_name=#{0} and building_id=#{1}")
	int checkDormitory(String name, long pid);
	@Select("select count(*) from building where building_name=#{0} and school_id=#{1}")
	int checkBuilding(String name, long pid);
	@Select("select count(*) from school where school_name=#{0} and region_id=#{1}")
	int checkSchool(String name, long pid);

	@Select("select country_id,country_name from country where country_status=10")
	List<country> getCountry();
	
	@Select("select region_id from region")
	List<Long> getAllRegionId();

	@Select("select staff_id,staff_name from staff where staff_rank=30 or staff_rank=40 and staff_status=10")
	List<staffs> getStaffRS();
}
