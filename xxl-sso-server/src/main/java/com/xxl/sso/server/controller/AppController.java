package com.xxl.sso.server.controller;

import com.xxl.sso.core.login.SsoTokenLoginHelper;
import com.xxl.sso.core.store.SsoLoginStore;
import com.xxl.sso.core.store.SsoSessionIdHelper;
import com.xxl.sso.core.user.XxlSsoUser;
import com.xxl.sso.server.core.model.UserInfo;
import com.xxl.sso.server.core.result.ReturnT;
import com.xxl.sso.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * sso server (for app)
 *
 * @author xuxueli 2018-04-08 21:02:54
 */
@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
	private UserService userService;


	/**
	 * Login
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public ReturnT<String> login(String username, String password) {
		// valid login
		ReturnT<UserInfo> result = userService.findUser(username, password);
		if (result.getCode() != ReturnT.SUCCESS_CODE) {
			return ReturnT.fail(result.getMsg());
		}
		// 1、make xxl-sso user
		XxlSsoUser xxlUser = new XxlSsoUser();
		xxlUser.setUserid(String.valueOf(result.getData().getUserid()));
		xxlUser.setUsername(result.getData().getUsername());
		xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
		xxlUser.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
		xxlUser.setExpireFreshTime(System.currentTimeMillis());
		// 2、generate sessionId + storeKey
		String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);
		// 3、login, store storeKey
		SsoTokenLoginHelper.login(sessionId, xxlUser);
		// 4、return sessionId
		return ReturnT.success(null, sessionId);
	}


	/**
	 * Logout
	 *
	 * @param sessionId
	 * @return
	 */
	@PostMapping("/logout")
	public ReturnT<String> logout(String sessionId) {
		// logout, remove storeKey
		SsoTokenLoginHelper.logout(sessionId);
		return ReturnT.success();
	}

	/**
	 * logincheck
	 *
	 * @param sessionId
	 * @return
	 */
	@GetMapping("/logincheck")
	public ReturnT<XxlSsoUser> loginCheck(String sessionId) {
		// logout
		XxlSsoUser xxlUser = SsoTokenLoginHelper.loginCheck(sessionId);
		if (xxlUser == null) {
			return ReturnT.fail("sso not login.");
		}
		return ReturnT.success(null, xxlUser);
	}

}
