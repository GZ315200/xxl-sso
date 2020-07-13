package com.xxl.sso.server.service.impl;

import com.xxl.sso.server.core.model.UserInfo;
import com.xxl.sso.server.core.result.ReturnT;
import com.xxl.sso.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gygeszean
 */
@Service
public class UserServiceImpl implements UserService {

	private static List<UserInfo> mockUserList = new ArrayList<>();

	static {
		for (int i = 0; i < 5; i++) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserid(1000 + i);
			userInfo.setUsername("user" + (i > 0 ? String.valueOf(i) : ""));
			userInfo.setPassword("123456");
			mockUserList.add(userInfo);
		}
	}

	@Override
	public ReturnT<UserInfo> findUser(String username, String password) {
		if (StringUtils.isBlank(username)) {
			return ReturnT.fail("Please input username.");
		}
		if (StringUtils.isBlank(password)) {
			return  ReturnT.fail("Please input password.");
		}
		// mock user
		for (UserInfo mockUser : mockUserList) {
			if (mockUser.getUsername().equals(username) && mockUser.getPassword().equals(password)) {
				return ReturnT.success(null,mockUser);
			}
		}
		return ReturnT.fail("username or password is invalid.");
	}
}
