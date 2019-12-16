package com.sunmengjie.cms.service.impl;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunmengjie.cms.common.CmsUtils;
import com.sunmengjie.cms.entity.User;
import com.sunmengjie.cms.mapper.UserMapper;
import com.sunmengjie.cms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	/**
	 * 用户名唯一验证
	 */
	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return mapper.findUserByName(username);
	}
	
	/**
	 * 注册
	 */
	@Override
	public int register(@Valid User user) {
		// TODO Auto-generated method stub
		
		//计算密文
		String encrypt = CmsUtils.encry(user.getPassword(), user.getPassword().substring(2, 5));
		
		user.setPassword(encrypt);
		return mapper.register(user);
	}
	
	
	/**
	 * 登录
	 */
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		user.setPassword(CmsUtils.encry(user.getPassword(), user.getPassword().substring(2, 5)));
		 User loginuser = mapper.findByPwd(user);
		 
		 return loginuser;
	}
}
