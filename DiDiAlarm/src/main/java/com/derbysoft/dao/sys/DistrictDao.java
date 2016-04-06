package com.derbysoft.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.derbysoft.entity.sys.SYS_District;

import dy.hrtworkframe.dao.BaseDaoImpl;
import dy.hrtworkframe.db.SQLUtil;
import dy.hrtworkframe.util.CheckUtil;

@Transactional
@Repository("systemDistrictDao")
public class DistrictDao extends BaseDaoImpl {
	public List<SYS_District> findProvince() {
		String sql = SQLUtil.getQuerySQL(SYS_District.class) + " where DistrictLevel='1'";
		
		return this.query(SYS_District.class, sql);
	}
	
	
	public List<SYS_District> findCity(String provinceID) {
		String sql = SQLUtil.getQuerySQL(SYS_District.class) + " where DistrictLevel='2'";
		if (CheckUtil.isNotNullStr(provinceID)) {
			sql += " and ProvinceID='" + provinceID + "' ";
		}
		return this.query(SYS_District.class, sql);
	}
	
	
	public List<SYS_District> findCounty(String cityID) {
		String sql = SQLUtil.getQuerySQL(SYS_District.class) + " where DistrictLevel='3'";
		if (CheckUtil.isNotNullStr(cityID)) {
			sql += " and CityID='" + cityID + "' ";
		}
		return this.query(SYS_District.class, sql);
	}
}
