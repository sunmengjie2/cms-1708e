package com.sunmengjie.cms.service;

import javax.validation.Valid;

import com.sunmengjie.cms.entity.User;

public interface UserService {

	User getUserByUsername(String username);

	int register(@Valid User user);

	User login(User user);

}
