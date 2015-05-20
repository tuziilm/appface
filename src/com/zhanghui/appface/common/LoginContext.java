package com.zhanghui.appface.common;

import com.zhanghui.appface.domain.SysUser;

import javax.servlet.http.HttpSession;

/**
 * 用以保存登录的相关信息，线程安全
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public final class LoginContext {
	public final static String USER_SESSION_KEY="user_session_key";
	private final static ThreadLocal<User> userHolder=new ThreadLocal<User>(){
		@Override
		protected User initialValue() {
			return null;
		}
	};
	
	public static void clear(){
		userHolder.remove();
	}
	
	private static void set(User user){
		userHolder.set(user);
	}
	
	public static User get(){
		return userHolder.get();
	}
	
	public static boolean isAdmin(){
		User user = userHolder.get();
		return user==null?false:user.systemUserType==SystemUserType.ADMIN;
	}
	
	public static String getUsername(){
		User user = userHolder.get();
		return user==null?null:user.username;
	}
	
	public static String getUsername(String defaultUsername){
		User user = userHolder.get();
		return user==null?defaultUsername:user.username;
	}
	
	public static Integer getUid(){
		User user = userHolder.get();
		return user==null?null:user.uid;
	}
	
	public static boolean checkLogin(HttpSession session){
		if(session==null)
			return false;
		User user = (User)session.getAttribute(USER_SESSION_KEY);
		if(user==null)
			return false;
		else{
			set(user);
			return true;
		}
	}
	
	public static void doLogin(SysUser sysUser, HttpSession session){
		if(sysUser.getId()==null || sysUser.getUsername()==null || sysUser.getSysUserType()==null || session==null)
			return;
		User user = new User();
		user.uid=sysUser.getId();
		user.username=sysUser.getUsername();
		user.systemUserType=SystemUserType.valueOf(sysUser.getSysUserType());
        user.uuid = session.getId();
		if(user.systemUserType==SystemUserType.UNKNOWN)
			return;
		user.privilege=sysUser.getPrivilege();
		session.setAttribute(USER_SESSION_KEY, user);
		set(user);
	}
	
	public final static class User{
		public Integer uid;
		public String username;
		public SystemUserType systemUserType;
		public String privilege;
        public String uuid;
	}
	
}
