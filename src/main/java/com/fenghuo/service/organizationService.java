package com.fenghuo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.organizationDao;
import com.fenghuo.domain.building;
import com.fenghuo.domain.country;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.organization;
import com.fenghuo.domain.region;
import com.fenghuo.domain.school;
import com.fenghuo.domain.staffs;

@Service
public class organizationService {
private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private organizationDao organizationdao;
	
	/**
	 * 列出组织架构
	 * 判断type的值调用不同的Dao
	 * */	
	public List<organization> listOrganization(int type,long staff_id){
		int i=0,n=0;
		//级别是楼栋时
		if(type==20){
			//全部的楼栋
			List<organization> organizationBs=new ArrayList<organization>();
			//每个楼栋的处理
			List<Long> buildings=organizationdao.listbuildingbyStaff(staff_id);
			for(i=0;i<buildings.size();i++){
				//单个的楼栋
				List <building> building=organizationdao.getbuildingbyId(buildings.get(i));
				organization oB=new organization();
				if(building.isEmpty()){
				}else{
				oB.setId(building.get(0).getBuilding_id());
				oB.setPid(building.get(0).getSchool_id());
				oB.setName(building.get(0).getBuilding_name());
				oB.setCode(building.get(0).getBuilding_code()); 
				oB.setType(20);
				}
				//全部的寝室
				List<organization> organizationDs=new ArrayList<organization>();
				//每个寝室的处理
				List<dormitory> dormitorys=organizationdao.getdormitorybyBId(buildings.get(i));
				for(n=0;n<dormitorys.size();n++){
					//单个寝室
					organization oD=new organization();
					oD.setId(dormitorys.get(n).getDormitory_id());
					oD.setPid(dormitorys.get(n).getBuilding_id());
					oD.setName(dormitorys.get(n).getDormitory_name());
					oD.setCode(dormitorys.get(n).getDormitory_code());
					oD.setDate(dormitorys.get(n).getDate());
					oD.setType(10);
					//寝室的负责人
					List<staffs> staffD=organizationdao.getStaffbyId(dormitorys.get(n).getStaff_id());
					oD.setStaff(staffD);
					organizationDs.add(oD);
				}
				oB.setChildren(organizationDs);
				//楼栋负责人
				List <staffs> staffBs=new ArrayList<staffs>();
				List <Long> Bstaff=organizationdao.listStaffbyBId(buildings.get(i));
				for(n=0;n<Bstaff.size();n++){
					List <staffs> staffB=organizationdao.getStaffbyId(Bstaff.get(n));
					staffBs.addAll(staffB);
				}
				oB.setStaff(staffBs);
				organizationBs.add(oB);
			}
			return organizationBs;
		}
		//级别是学校时
		if(type==30){
			int b=0;
			//全部的学校
			List <organization> organizationSs=new ArrayList<organization>();
			//每个学校的处理
			List<Long> schools=organizationdao.listSchoolbyId(staff_id);
			for(b=0;b<schools.size();b++){
				//单个学校
				organization oS=new organization();
				if(schools.isEmpty()){
				}else{
				List<school> school=organizationdao.getSchoolbyId(schools.get(b));
				oS.setId(school.get(0).getSchool_id());
				oS.setPid(school.get(0).getRegion_id());
				oS.setName(school.get(0).getSchool_name());
				oS.setCode(school.get(0).getSchool_code()); 
				oS.setType(30);
				}
				//所有楼栋
				List <organization> organizationBs=new ArrayList<organization>();
				//每个楼栋的处理
				List<building> buildings=organizationdao.listbuildingbySchool(schools.get(b));
				for(i=0;i<buildings.size();i++){
					//单个的楼栋
					organization oB=new organization();
					oB.setId(buildings.get(i).getBuilding_id());
					oB.setPid(buildings.get(i).getSchool_id());
					oB.setName(buildings.get(i).getBuilding_name());
					oB.setCode(buildings.get(i).getBuilding_code()); 
					oB.setType(20);
					//所有的寝室
					List <organization> organizationDs=new ArrayList<organization>();
					List<dormitory> dormitorys=organizationdao.getdormitorybyBId(buildings.get(i).getBuilding_id());
					for(n=0;n<dormitorys.size();n++){
						//单个寝室
						organization oD=new organization();
						oD.setId(dormitorys.get(n).getDormitory_id());
						oD.setPid(dormitorys.get(n).getBuilding_id());
						oD.setName(dormitorys.get(n).getDormitory_name());
						oD.setCode(dormitorys.get(n).getDormitory_code());
						oD.setDate(dormitorys.get(n).getDate());
						oD.setType(10);
						List<staffs> staffD=organizationdao.getStaffbyId(dormitorys.get(n).getStaff_id());
						oD.setStaff(staffD);
						organizationDs.add(oD);
					}
					oB.setChildren(organizationDs);
					//楼栋负责人
					List <staffs> staffBs=new ArrayList<staffs>();
					List <Long> Bstaff=organizationdao.listStaffbyBId(buildings.get(i).getBuilding_id());					
					for(n=0;n<Bstaff.size();n++){
						List <staffs> staffB=organizationdao.getStaffbyId(Bstaff.get(n));
						staffBs.addAll(staffB);
					}
					oB.setStaff(staffBs);
					organizationBs.add(oB);
				}
				oS.setChildren(organizationBs);
				//学校负责人
				List <staffs> staffs=new ArrayList<staffs>();
				List <Long> Sstaff=organizationdao.listStaffbySId(schools.get(b));				
				for(i=0;i<Sstaff.size();i++){
					List<staffs> staff=organizationdao.getStaffbyId(Sstaff.get(i));
					staffs.addAll(staff);
				}
				oS.setStaff(staffs);
				organizationSs.add(oS);
			}
			return organizationSs;
		}
		//级别是区时
		if(type==40){
			int p,b;
			//所有的区
			List<organization> organizationRs=new ArrayList<organization>();
			//单个区的处理
			List<Long> regions=organizationdao.listRegionbyId(staff_id);
			for(p=0;p<regions.size();p++){
				//单个区
				organization oR=new organization();
				List<region> region=organizationdao.getRegionbyId(regions.get(p));
				oR.setId(region.get(0).getRegion_id());
				oR.setName(region.get(0).getRegion_name());
				oR.setType(40);
				//全部的学校
				List <organization> organizationSs=new ArrayList<organization>();
				//每个学校的处理
				List<school> schools=organizationdao.listSchoolbyRegion(regions.get(p));
				for(b=0;b<schools.size();b++){
					//单个学校
					organization oS=new organization();
					if(schools.isEmpty()){
					}else{
					List<school> school=organizationdao.getSchoolbyId(schools.get(b).getSchool_id());
					oS.setId(school.get(0).getSchool_id());
					oS.setPid(school.get(0).getRegion_id());
					oS.setName(school.get(0).getSchool_name());
					oS.setCode(school.get(0).getSchool_code()); 
					oS.setType(30);
					}
					//所有楼栋
					List <organization> organizationBs=new ArrayList<organization>();
					//每个楼栋的处理
					List<building> buildings=organizationdao.listbuildingbySchool(schools.get(b).getSchool_id());
					for(i=0;i<buildings.size();i++){
						//单个的楼栋
						organization oB=new organization();
						oB.setId(buildings.get(i).getBuilding_id());
						oB.setPid(buildings.get(i).getSchool_id());
						oB.setName(buildings.get(i).getBuilding_name());
						oB.setCode(buildings.get(i).getBuilding_code()); 
						oB.setType(20);
						//所有的寝室
						List <organization> organizationDs=new ArrayList<organization>();
						List<dormitory> dormitorys=organizationdao.getdormitorybyBId(buildings.get(i).getBuilding_id());
						for(n=0;n<dormitorys.size();n++){
							//单个寝室
							organization oD=new organization();
							oD.setId(dormitorys.get(n).getDormitory_id());
							oD.setPid(dormitorys.get(n).getBuilding_id());
							oD.setName(dormitorys.get(n).getDormitory_name());
							oD.setCode(dormitorys.get(n).getDormitory_code());
							oD.setDate(dormitorys.get(n).getDate());
							oD.setType(10);
							List<staffs> staffD=organizationdao.getStaffbyId(dormitorys.get(n).getStaff_id());
							oD.setStaff(staffD);
							organizationDs.add(oD);
						}
						oB.setChildren(organizationDs);
						//楼栋负责人
						List <staffs> staffBs=new ArrayList<staffs>();
						List <Long> Bstaff=organizationdao.listStaffbyBId(buildings.get(i).getBuilding_id());					
						for(n=0;n<Bstaff.size();n++){
							List <staffs> staffB=organizationdao.getStaffbyId(Bstaff.get(n));
							staffBs.addAll(staffB);
						}
						oB.setStaff(staffBs);
						organizationBs.add(oB);
					}
					oS.setChildren(organizationBs);
					//学校负责人
					List <staffs> staffs=new ArrayList<staffs>();
					List <Long> Sstaff=organizationdao.listStaffbySId(schools.get(b).getSchool_id());				
					for(i=0;i<Sstaff.size();i++){
						List<staffs> staff=organizationdao.getStaffbyId(Sstaff.get(i));
						staffs.addAll(staff);
					}
					oS.setStaff(staffs);
					organizationSs.add(oS);
				}
				oR.setChildren(organizationSs);
				//区负责人
				List <staffs> staffRs=new ArrayList<staffs>();
				List<Long> Rstaff=organizationdao.listStaffbyRId(region.get(0).getRegion_id());
				for(b=0;b<Rstaff.size();b++){
					List<staffs> staffR=organizationdao.getStaffbyId(Rstaff.get(b));
					staffRs.addAll(staffR);
				}
				oR.setStaff(staffRs);
				organizationRs.add(oR);
			}
			return organizationRs;
		}
		//级别是国家时
		if(type==50){
			//国家
			List<organization> organizationCs=getCountry();
			List<staffs> staffC=organizationdao.getStaffbyId(staff_id);
			organizationCs.get(0).setStaff(staffC);
			//所有的区
			List<organization> organizationRs=new ArrayList<organization>();
			List<Long> regions=organizationdao.getAllRegionId();
			for(int p=0;p<regions.size();p++){
				//单个区
				organization oR=new organization();
				List<region> region=organizationdao.getRegionbyId(regions.get(p));
				oR.setId(region.get(0).getRegion_id());
				oR.setName(region.get(0).getRegion_name());
				oR.setType(40);
				List <staffs> staffRs=new ArrayList<staffs>();
				List<Long> Rstaff=organizationdao.listStaffbyRId(region.get(0).getRegion_id());
				for(int b=0;b<Rstaff.size();b++){
					List<staffs> staffR=organizationdao.getStaffbyId(Rstaff.get(b));
					staffRs.addAll(staffR);
				}
				oR.setStaff(staffRs);
				organizationRs.add(oR);
			}
			organizationCs.get(0).setChildren(organizationRs);
			return organizationCs;
		}
		return null;
	}
	
	/**
	 * 新增组织架构
	 * 用id判断是增加还是修改
	 * 判断type的值调用不同的Dao
	 * */
	public int addOrganization(long id,long pid,int type,String Organization_name,String code,long []staff_id,String date){
		int x=0;
		List<Long> y;
		//新增
		if(id==-1){
			//上级是楼栋时
			if(type==20){
				x=organizationdao.addDormitory(pid,staff_id[0],Organization_name,code,date);
				y = organizationdao.getType(staff_id[0]);
				if(y.get(0)<10)
					x=organizationdao.setType(staff_id[0],10);
			}
			//上级是学校时
			if(type==30){
				x=organizationdao.addBuilding(pid,Organization_name,code);
				long m=organizationdao.getLastB();
				for(int i=0;i<staff_id.length;i++){
					x=organizationdao.addBuilding_staff(staff_id[i],m);
					y = organizationdao.getType(staff_id[i]);
					if(y.get(0)<20)
						x=organizationdao.setType(staff_id[i],20);
				}
			}
			//上级是区时
			if(type==40){
				String name=Organization_name+"仓库";
				x=organizationdao.addSchool(pid,Organization_name,code);
				long m=organizationdao.getLastS();
				for(int i=0;i<staff_id.length;i++){
					x=organizationdao.addSchool_staff(staff_id[i],m);
					y = organizationdao.getType(staff_id[i]);
					if(y.get(0)<30)
						x=organizationdao.setType(staff_id[i],30);
				}
				x=organizationdao.addStorage(name,m);
			}
			if(type==50){
				x=organizationdao.addRegion(Organization_name);
				long m=organizationdao.getLastR();
				for(int i=0;i<staff_id.length;i++){
					x=organizationdao.addRegion_staff(staff_id[i],m);
					y = organizationdao.getType(staff_id[i]);
					if(y.get(0)<40)
						x=organizationdao.setType(staff_id[i],40);	
				}
			}
		}
		//修改
		else{
			//上级是楼栋时
			if(type==20){
				List<Long> y1=organizationdao.getStaffbyDId(id);
				if(y1.get(0)!=staff_id[0]){
					y = organizationdao.getType(y1.get(0));
					int n=organizationdao.listDormitorybyId(y1.get(0));
					if(y.get(0)<20 && n==1)
						x=organizationdao.setType(y1.get(0),0);
					y = organizationdao.getType(staff_id[0]);
					if(y.get(0)<10)
						x=organizationdao.setType(staff_id[0],10);				
				}
				x=organizationdao.updateDormitory(pid,staff_id[0],Organization_name,code,date,id);
			}
			//上级是学校时
			if(type==30){
				List<Long> y1=organizationdao.listStaffbyBId(id);
				List<Integer> y2=new ArrayList<Integer>();
				for(int i=0;i<staff_id.length;i++){
					for(int j=0;j<y1.size();j++){
						if(y1.get(j)==staff_id[i]){
							y1.remove(j);
							y2.add(i);
							break;
						}
					}
				}
				for(int i=0;i<y1.size();i++){
					y = organizationdao.getType(y1.get(i));
					List<Long> n=organizationdao.listbuildingbyStaff(y1.get(i));
					if(y.get(0)<30 && n.size()==1){
						int m = organizationdao.listDormitorybyId(y1.get(i));
						if(m==0)
							x=organizationdao.setType(y1.get(i),0);
						else
							x=organizationdao.setType(y1.get(i),10);
					}
					x=organizationdao.deleteBuilding_staff1(y1.get(i),id);				
				}
				for(int i=0;i<staff_id.length;i++){
					boolean b=true;
					for(int j=0;j<y2.size();j++){
						if(i==y2.get(j)){
							b=false;
							break;
						}
					}
					if(b==true){
						y = organizationdao.getType(staff_id[i]);
						if(y.get(0)<20)
							x=organizationdao.setType(staff_id[i],20);
						x=organizationdao.addBuilding_staff(staff_id[i],id);
					}
				}				
				x=organizationdao.updateBuilding(pid,Organization_name,code,id);	
			}
			//上级是区时
			if(type==40){
				List<Long> y1=organizationdao.listStaffbySId(id);
				List<Integer> y2=new ArrayList<Integer>();
				for(int i=0;i<staff_id.length;i++){
					for(int j=0;j<y1.size();j++){
						if(y1.get(j)==staff_id[i]){
							y1.remove(j);
							y2.add(i);
							break;
						}
					}
				}
				for(int i=0;i<y1.size();i++){
					y = organizationdao.getType(y1.get(i));
					List<Long> n=organizationdao.listSchoolbyId(y1.get(i));
					if(y.get(0)<40 && n.size()==1){
						int m1=organizationdao.listBuildingbyId(y1.get(i));					
						if(m1!=0)
							x=organizationdao.setType(y1.get(i), 20);
						else {
							int m = organizationdao.listDormitorybyId(y1.get(i));							
							if(m==0)
								x=organizationdao.setType(y1.get(i),0);
							else
								x=organizationdao.setType(y1.get(i),10);
						}
					}
					x=organizationdao.deleteSchool_staff1(y1.get(i),id);
				}
				for(int i=0;i<staff_id.length;i++){
					boolean b=true;
					for(int j=0;j<y2.size();j++){
						if(i==y2.get(j)){
							b=false;
							break;
						}
					}
					if(b==true){
						y = organizationdao.getType(staff_id[i]);
						if(y.get(0)<30)
							x=organizationdao.setType(staff_id[i],30);
						x=organizationdao.addSchool_staff(staff_id[i],id);
					}
				}
				x=organizationdao.updateSchool(pid,Organization_name,code,id);
			}
			if(type==50){
				List<Long> y1=organizationdao.listStaffbyRId(id);
				List<Integer> y2=new ArrayList<Integer>();
				for(int i=0;i<staff_id.length;i++){
					for(int j=0;j<y1.size();j++){
						if(y1.get(j)==staff_id[i]){
							y1.remove(j);
							y2.add(i);
							break;
						}
					}
				}
				for(int i=0;i<y1.size();i++){
					y = organizationdao.getType(y1.get(i));
					List<Long> n=organizationdao.listRegionbyId(y1.get(i));
					if(n.size()==1){
						List<Long> n1=organizationdao.listSchoolbyId(y1.get(i));
						if(n1.size()<1){
							int m1=organizationdao.listBuildingbyId(y1.get(i));					
							if(m1!=0)
								x=organizationdao.setType(y1.get(i), 20);
							else {
								int m = organizationdao.listDormitorybyId(y1.get(i));							
								if(m==0)
									x=organizationdao.setType(y1.get(i),0);
								else
									x=organizationdao.setType(y1.get(i),10);
							}
						}else{
							x=organizationdao.setType(y1.get(i),30);
						}
					}
					x=organizationdao.deleteRegion_staff1(y1.get(i),id);
				}
				for(int i=0;i<staff_id.length;i++){
					boolean b=true;
					for(int j=0;j<y2.size();j++){
						if(i==y2.get(j)){
							b=false;
							break;
						}
					}
					if(b==true){
						y = organizationdao.getType(staff_id[i]);
						if(y.get(0)<40)
							x=organizationdao.setType(staff_id[i],40);
						x=organizationdao.addRegion_staff(staff_id[i],id);
					}
				}
				x=organizationdao.updateRegion(Organization_name,id);
			}
		}
		return x;
	}
	
	/**
	 * 删除组织架构
	 * 用id判断是增加还是修改
	 * 判断type的值调用不同的Dao
	 * */	
	public int deleteOrganization(long id){
		int x=0;
		List<Long> y;
/*		if(type==40){
			List<Long> y1=organizationdao.listStaffbyRId(id);
			for(int i=0;i<y1.size();i++){
				y = organizationdao.getType(y1.get(i));
				List<Long> n=organizationdao.listRegionbyId(y1.get(i));
				if(n.size()==1){
					List<Long> n1=organizationdao.listSchoolbyId(y1.get(i));
					if(n1.size()<1){
						int m1=organizationdao.listBuildingbyId(y1.get(i));					
						if(m1!=0)
							x=organizationdao.setType(y1.get(i), 20);
						else {
							int m = organizationdao.listDormitorybyId(y1.get(i));							
							if(m==0)
								x=organizationdao.setType(y1.get(i),0);
							else
								x=organizationdao.setType(y1.get(i),10);
						}
					}else{
						x=organizationdao.setType(y1.get(i),30);
					}
				}
			}
			List<school> p = organizationdao.listSchoolbyRegion(id);
			for(int i=0;i<p.size();i++){
				deleteOrganization(p.get(i).getSchool_id(),30);
			}
			x=organizationdao.deleteRegion(id);
			x=organizationdao.deleteRegion_staff(id);
		}
		if(type==30){
			List<Long> y1=organizationdao.listStaffbySId(id);
			for(int i=0;i<y1.size();i++){
				y = organizationdao.getType(y1.get(i));
				List<Long> n=organizationdao.listSchoolbyId(y1.get(i));
				if(y.get(0)<40 && n.size()==1){
					int m1=organizationdao.listBuildingbyId(y1.get(i));					
					if(m1!=0)
						x=organizationdao.setType(y1.get(i), 20);
					else {
						int m = organizationdao.listDormitorybyId(y1.get(i));							
						if(m==0)
							x=organizationdao.setType(y1.get(i),0);
						else
							x=organizationdao.setType(y1.get(i),10);
					}
				}
			}
			List<building> p = organizationdao.listbuildingbySchool(id);
			for(int i=0;i<p.size();i++){
				deleteOrganization(p.get(i).getBuilding_id(),20);
			}
			x=organizationdao.deleteSchool(id);
			x=organizationdao.deleteSchool_staff(id);
			x=organizationdao.deleteStorage(id);
		}
		if(type==20){
			List<Long> y1=organizationdao.listStaffbyBId(id);
			for(int i=0;i<y1.size();i++){
				y = organizationdao.getType(y1.get(i));
				List<Long> n=organizationdao.listbuildingbyStaff(y1.get(i));
				if(y.get(0)<30 && n.size()==1){
					int m = organizationdao.listDormitorybyId(y1.get(i));
					if(m==0)
						x=organizationdao.setType(y1.get(i),0);
					else
						x=organizationdao.setType(y1.get(i),10);
				}
			}
			List<dormitory> p = organizationdao.getdormitorybyBId(id);
			for(int i=0;i<p.size();i++){
				deleteOrganization(p.get(i).getDormitory_id(),10);
			}
			x=organizationdao.deleteBuilding(id);
			x=organizationdao.deleteBuilding_staff(id);
		}
		if(type==10){*/
			List<Long> y1=organizationdao.getStaffbyDId(id);
			y = organizationdao.getType(y1.get(0));
			int n=organizationdao.listDormitorybyId(y1.get(0));
			if(y.get(0)<20 && n==1)
				x=organizationdao.setType(y1.get(0),0);
			x=organizationdao.deleteDormitory(id);
		/*}*/
		return x;
	}
	
	/**
	 * 列出员工
	 * type:查看人员级别
	 * staff_id:查看人员ID
	 * */
	public JSONObject listAllstaff(long staff_id,int type){
		HashSet<staffs> staff=new HashSet<staffs>();
		List<staffs> staffx=organizationdao.listXstaff();		
		if(type==20){
			//楼栋负责人为他自己
			List<staffs> staffb=organizationdao.getStaffbyId(staff_id);
			staff.add(staffb.get(0));
			//对每一栋楼进行处理
			List<Long> a=organizationdao.listbuildingbyStaff(staff_id);
			for(int i=0;i<a.size();i++){
				//每一个寝室的处理
				List<dormitory> a11=organizationdao.getdormitorybyBId(a.get(i));
				for(int n=0;n<a11.size();n++){
					//其中一个寝室的负责人
					List<staffs> a111=organizationdao.getStaffbyId(a11.get(n).getStaff_id());
					if(a111.isEmpty()){
					}else{
					staff.add(a111.get(0));
					}
				}	
			}
		}else if(type==30){
			//学校负责人为他本人
			List<staffs> staffs=organizationdao.getStaffbyId(staff_id);
			staff.add(staffs.get(0));
			//每个学校的处理
			List<Long> x=organizationdao.listSchoolbyId(staff_id);
			for(int b = 0;b<x.size();b++){
				//每个楼栋的处理
				List<building> a=organizationdao.listbuildingbySchool(x.get(b));
				for(int i=0;i<a.size();i++){
					//楼栋的负责人
					List<Long> a1=organizationdao.listStaffbyBId(a.get(i).getBuilding_id());
					for(int u=0;u<a1.size();u++){
						List<staffs> m=organizationdao.getStaffbyId(a1.get(u));
						if(m.isEmpty()){
						}else{
						staff.add(m.get(0));
						}
					}
					//其中一个楼栋的所有寝室负责人处理
					List<dormitory> a11=organizationdao.getdormitorybyBId(a.get(i).getBuilding_id());
					for(int n=0;n<a11.size();n++){
						List<staffs> a111=organizationdao.getStaffbyId(a11.get(n).getStaff_id());
						if(a111.isEmpty()){
						}else{
						staff.add(a111.get(0));
						}
					}
					
				};
			}
		}else if(type==40){
			//区负责人是他本人
			List<staffs> staffr=organizationdao.getStaffbyId(staff_id);
			staff.add(staffr.get(0));
			//每个区的处理
			List<Long> k1=organizationdao.listRegionbyId(staff_id);
			for(int p=0;p<k1.size();p++){
				//每个学校的处理
				List<school> x=organizationdao.listSchoolbyRegion(k1.get(p));
				for(int b = 0;b<x.size();b++){
					//学校负责人
					List<Long> t=organizationdao.listStaffbySId(x.get(b).getSchool_id());
					for(int b1=0;b1<t.size();b1++){
						List<staffs> t1=organizationdao.getStaffbyId(t.get(b1));
						if(t1.isEmpty()){
						}else{
						staff.add(t1.get(0));
						}
					}
					//每个楼栋的处理
					List<building> a=organizationdao.listbuildingbySchool(x.get(b).getSchool_id());
					for(int i=0;i<a.size();i++){
						//单个楼栋的负责人
						List<Long> a1=organizationdao.listStaffbyBId(a.get(i).getBuilding_id());
						for(int u=0;u<a1.size();u++){
							List<staffs> m=organizationdao.getStaffbyId(a1.get(u));
							if(m.isEmpty()){
							}else{
							staff.add(m.get(0));
							}
						}
						//每个寝室的处理
						List<dormitory> a11=organizationdao.getdormitorybyBId(a.get(i).getBuilding_id());
						for(int n=0;n<a11.size();n++){
							List<staffs> a111=organizationdao.getStaffbyId(a11.get(n).getStaff_id());
							if(a111.isEmpty()){
							}else{
								staff.add(a111.get(0));
							}
						}
					}					
				}				
			}
		}else if(type==50){
			List<staffs> sta=organizationdao.getStaffRS();
			staff.addAll(sta);
		}
	    Map<Long, staffs> map = new HashMap<Long, staffs>();
	    Set<staffs> tempSet = new HashSet<staffs>();
	    for(staffs p : staff) {
	    	if(map.get(p.getStaff_id()) == null ) {
	    		map.put(p.getStaff_id(), p);                        
	    	} else {
	    		tempSet.add(p);
	    	}
	    }
	    staff.removeAll(tempSet);
		JSONObject json=new JSONObject();
		json.put("fenpei", staff);
		json.put("weifenpei", staffx);
		return json;
	}

	/**
	 * 管理组织架构
	 * type:组织等级
	 * id:组织id
	 * */
	public List<organization> listOrganizationbyId(long id, int type) {
		List<organization> organization=new ArrayList<organization>();
		if(type==10){
			List<dormitory> dormitory=organizationdao.getdormitorybyId(id);
			organization oD=new organization();
			if(dormitory.isEmpty()){
			}else{
			oD.setId(id);
			oD.setName(dormitory.get(0).getDormitory_name());
			oD.setPid(dormitory.get(0).getBuilding_id());
			oD.setCode(dormitory.get(0).getDormitory_code());
			oD.setDate(dormitory.get(0).getDate());
			List<staffs> staff=organizationdao.getStaffbyId(dormitory.get(0).getStaff_id());
			oD.setStaff(staff);
			oD.setType(10);
			organization.add(oD);
			}
		}
		if(type==20){
			List<building> building=organizationdao.getbuildingbyId(id);
			organization oB=new organization();
			if(building.isEmpty()){
			}else{
			oB.setId(id);
			oB.setName(building.get(0).getBuilding_name());
			oB.setPid(building.get(0).getSchool_id());
			oB.setCode(building.get(0).getBuilding_code());
			oB.setType(20);
			}
			List <staffs> staffBs=new ArrayList<staffs>();
			List <Long> Bstaff=organizationdao.listStaffbyBId(id);					
			for(int n=0;n<Bstaff.size();n++){
				List <staffs> staffB=organizationdao.getStaffbyId(Bstaff.get(n));
				staffBs.addAll(staffB);
			}
			oB.setStaff(staffBs);
			organization.add(oB);
		}
		if(type==30){
			List<school> school=organizationdao.getSchoolbyId(id);
			organization oS=new organization();
			if(school.isEmpty()){
			}else{
			oS.setId(id);
			oS.setName(school.get(0).getSchool_name());
			oS.setPid(school.get(0).getRegion_id());
			oS.setCode(school.get(0).getSchool_code());
			oS.setType(30);
			}
			List <staffs> staffs=new ArrayList<staffs>();
			List <Long> Sstaff=organizationdao.listStaffbySId(id);					
			for(int n=0;n<Sstaff.size();n++){
				List <staffs> staff=organizationdao.getStaffbyId(Sstaff.get(n));
				staffs.addAll(staff);
			}
			oS.setStaff(staffs);
			organization.add(oS);
		}
		if(type==40){
			List<region> region=organizationdao.getRegionbyId(id);
			organization oR=new organization();
			if(region.isEmpty()){
			}else{
			oR.setId(id);
			oR.setName(region.get(0).getRegion_name());
			oR.setType(40);
			}
			List <staffs> staffRs=new ArrayList<staffs>();
			List<Long> Rstaff=organizationdao.listStaffbyRId(id);					
			for(int n=0;n<Rstaff.size();n++){
				List <staffs> staff=organizationdao.getStaffbyId(Rstaff.get(n));
				staffRs.addAll(staff);
			}
			oR.setStaff(staffRs);
			organization.add(oR);
		}
		return organization;
	}

	public int checkOrganization(String name,int type,long pid) {
		if(type==10){
			return organizationdao.checkDormitory(name,pid);
		}else if(type==20){
			return organizationdao.checkBuilding(name,pid);
		}else if(type==30){
			return organizationdao.checkSchool(name,pid);
		}else{
			return -1;
		}
	}

	public List<organization> getCountry() {
		List<organization> organization=new ArrayList<organization>();
		List<country> country=organizationdao.getCountry();
		organization or=new organization();
		or.setId(country.get(0).getCountry_id());
		or.setName(country.get(0).getCountry_name());
		or.setType(50);
		organization.add(or);
		return organization;
	}
}
