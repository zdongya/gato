package com.caiyi.financial.jzadmin.mapper;

import java.util.List;

import com.caiyi.financial.jzadmin.entity.UserFormMap;
import com.caiyi.financial.jzadmin.mapper.base.BaseMapper;


public interface UserMapper extends BaseMapper{

	public List<UserFormMap> findUserPage(UserFormMap userFormMap);
	
}
