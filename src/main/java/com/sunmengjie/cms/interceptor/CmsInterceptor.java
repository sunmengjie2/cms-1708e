package com.sunmengjie.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.sunmengjie.cms.common.CmsContant;
import com.sunmengjie.cms.entity.User;

public class CmsInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User loginUser = (User) request.getSession().getAttribute(CmsContant.USER_KEY);
		
		if(loginUser==null) {
			response.sendRedirect("login");
			return false;
		}
		
		//放行 
		return true;
	}
}
