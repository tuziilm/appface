package com.zhanghui.appface.persistence;

import com.zhanghui.appface.domain.SysUser;

/**
 * ibatis����ϵͳ�û����Mapper�ӿ�
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public interface SysUserMapper extends BaseMapper<SysUser>{

	SysUser getByUsername(String username);
}