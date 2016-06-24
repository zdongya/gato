package com.caiyi.financial.jzadmin.mapper;

import java.util.List;

import com.caiyi.financial.jzadmin.entity.RoleFormMap;
import com.caiyi.financial.jzadmin.mapper.base.BaseMapper;

public interface RoleMapper extends BaseMapper{
	public List<RoleFormMap> seletUserRole(RoleFormMap map);
	
	public void deleteById(RoleFormMap map);
}
