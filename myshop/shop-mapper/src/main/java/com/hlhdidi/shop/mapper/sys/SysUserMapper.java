package com.hlhdidi.shop.mapper.sys;

import com.hlhdidi.shop.pojo.sys.SysUser;

public interface SysUserMapper {

	SysUser findByName(String userName);

}
