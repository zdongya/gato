package com.caiyi.financial.jzadmin.mapper;

import java.util.List;

import com.caiyi.financial.jzadmin.entity.ResFormMap;
import com.caiyi.financial.jzadmin.mapper.base.BaseMapper;

public interface ResourcesMapper extends BaseMapper {
	public List<ResFormMap> findChildlists(ResFormMap map);

	public List<ResFormMap> findRes(ResFormMap map);

	public void updateSortOrder(List<ResFormMap> map);
	
	public List<ResFormMap> findUserResourcess(String userId);
	
}
