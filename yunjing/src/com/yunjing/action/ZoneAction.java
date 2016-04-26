package com.yunjing.action;

import java.util.List;

import com.yunjing.entity.Zone;
import com.yunjing.service.ZoneService;

public class ZoneAction extends BaseAction{
	private ZoneService zoneService;
	private List<?> zones;
	private Zone zone;
	
	/**
	 * 查询所有防区
	 * @return
	 */
	public String index(){
		try {
			zones = zoneService.getAllPaging(zone);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	
	public List<?> getZones() {
		return zones;
	}


	public void setZones(List<?> zones) {
		this.zones = zones;
	}


	public Zone getzone() {
		return zone;
	}
	public void setzone(Zone zone) {
		this.zone = zone;
	}
	public void setzoneService(ZoneService zoneService) {
		this.zoneService = zoneService;
	}
}
