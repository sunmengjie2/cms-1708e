package com.sunmengjie.cms.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.service.UserService;

public class CmsInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		
		
		if(loginUser!=null) {
			//放行 
			return true;
		}
		
		//从cookie当中获取用户信息
		User user = new User();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if("username".equals(cookies[i].getName())) {
			user.setUsername(cookies[i].getValue());
			}
			if("userpwd".equals(cookies[i].getName())) {
				user.setPassword(cookies[i].getValue());
			}
		}
		
		//说明cookie 中存放的用户信息不完整
		if(null==user.getUsername() || null == user.getPassword()) {
			response.sendRedirect("/user/login");
			return false;
		}
		
		//利用cookie中用户信息进行登录操作
		 loginUser = userService.login(user);
		 if(loginUser!=null) {
			 request.getSession().setAttribute(CmsContant.USER_KEY, loginUser);
			 return true;
		 }
		
		response.sendRedirect("/user/login");
		return false;
		
	}
}
