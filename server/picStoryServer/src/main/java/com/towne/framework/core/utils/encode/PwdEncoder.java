package com.towne.framework.core.utils.encode;

public interface PwdEncoder {
	/**
	 * 密码加密
	 * 
	 * @param rawPass
	 *            未加密密码
	 * @return 加密后密码
	 */
	public String encodePassword(String rawPass);

	/**
	 * 验证密码是否正确
	 * 
	 * @param encPass
	 *            加密密码
	 * @param rawPass
	 *            未加密密码
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass);
}
