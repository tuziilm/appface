package com.zhanghui.appface.persistence;

import com.zhanghui.appface.domain.SysUser;

/**
 * ibatis操作系统用户表的Mapper接口
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public interface SysUserMapper extends BaseMapper<SysUser>{

	SysUser getByUsername(String username);
}