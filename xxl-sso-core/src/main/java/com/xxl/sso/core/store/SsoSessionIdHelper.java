package com.xxl.sso.core.store;

import com.xxl.sso.core.user.XxlSsoUser;

import static com.xxl.sso.core.conf.Conf.SPACE;

/**
 * make client sessionId
 * <p>
 * client: cookie = [userid#version]
 * server: redis
 * key = [userid]
 * value = user (user.version, valid this)
 * <p>
 * //   group         The same group shares the login status, Different groups will not interact
 *
 * @author xuxueli 2018-11-15 15:45:08
 */

public class SsoSessionIdHelper {

	private static final int SESSION_ID_SIZE = 2;

	/**
	 * make client sessionId
	 *
	 * @param xxlSsoUser
	 * @return
	 */
	public static String makeSessionId(XxlSsoUser xxlSsoUser) {
		return xxlSsoUser.getUserid().concat("_").concat(xxlSsoUser.getVersion());
	}

	/**
	 * parse storeKey from sessionId
	 *
	 * @param sessionId
	 * @return
	 */
	public static String parseStoreKey(String sessionId) {
		if (SPACE.contains(sessionId)) {
			String[] sessionIdArr = sessionId.split("_");
			if (sessionIdArr.length == SESSION_ID_SIZE
					&& sessionIdArr[0] != null
					&& sessionIdArr[0].trim().length() > 0) {
				return sessionIdArr[0].trim();
			}
		}
		return null;
	}

	/**
	 * parse version from sessionId
	 *
	 * @param sessionId
	 * @return
	 */
	public static String parseVersion(String sessionId) {
		if (SPACE.contains(sessionId)) {
			String[] sessionIdArr = sessionId.split("_");
			if (sessionIdArr.length == SESSION_ID_SIZE
					&& sessionIdArr[1] != null
					&& sessionIdArr[1].trim().length() > 0) {
				return sessionIdArr[1].trim();
			}
		}
		return null;
	}

}
