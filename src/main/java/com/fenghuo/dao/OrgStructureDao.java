package com.fenghuo.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;
@Component
public interface OrgStructureDao {
	
	
	@SelectProvider(type=SqlProvider.class, method="getOrgById")
	public List<Object> getOrgById(@Param("level")int level,@Param("orgId")int orgId);
	
  static class SqlProvider{
	  @SuppressWarnings("unchecked")
		public static String getOrgById(Map<String,Object> params){
			String SELECT_KEYS = "*";
			BEGIN();
            SELECT(SELECT_KEYS);
          //1表示仓库管理员，10表示管理寝室，20表示楼栋，30表示学校，40表示区县
            int level = (Integer)params.get("level");
            if(level == 10){
            	 FROM("dormitory");
            	 WHERE("dormitory_id = #{orgId} and dormitory_status = 10");
            }else if(level == 20 ){
            	FROM("building");
            	WHERE("building_id = #{orgId} and building_status = 10");
            }else if(level == 30){
            	FROM("shcool");
            	WHERE("shcool_id = #{orgId} and shcool_status = 10");
            }else if(level == 40){
            	FROM("region");
            	WHERE("region_id = #{orgId} and region_status = 10");
            }
            
			String sql_prefix = SQL();
//			if(offset != null && pageSize != null){
//				sql_prefix += " order by id desc limit #{offset}, #{page_size}";
//			}
			return sql_prefix;
			
		}
	}
}
