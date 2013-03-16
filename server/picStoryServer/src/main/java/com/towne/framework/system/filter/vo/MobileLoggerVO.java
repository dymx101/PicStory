package com.towne.framework.system.filter.vo;

import java.io.Serializable;

public class MobileLoggerVo implements Serializable {
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = -6585930265511953323L;
	/**
	 * Id
	 */
	private Long id = null;
	/**
	 * 商户号
	 */
	private String traderName = null;
	/**
	 * 商户密码
	 */
	private String traderPassword = null;
	/**
	 * 系统(WAP,PalmOS、Symbian、Windows mobile、WinCE、Linux和Android、iPhoneOS、黑莓)
	 */
	private String clientSystem = null;
	/**
	 * 客户端版本
	 */
	private String clientVersion = null;
	/**
	 * 协议(Webservice,HTTPXML)
	 */
	private String protocol = null;

	/**
	 * 接口版本
	 */
	private String interfaceVersion = null;

	/**
	 * 调用的接口的名称
	 */
	private String callInterfaceName = null;

	/**
	 * 用户的token
	 */
	private String userToken = null;

	/**
	 * 经度
	 */
	private Double longitude = null;

	/**
	 * 纬度
	 */
	private Double latitude = null;

	/**
	 * 客户端唯一标识
	 */
	private String deviceCode = null;

	/**
	 * 客户端唯一标识 （未加密）
	 */
	private String deviceCodeNotEncrypt = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public String getTraderPassword() {
		return traderPassword;
	}

	public void setTraderPassword(String traderPassword) {
		this.traderPassword = traderPassword;
	}

	public String getClientSystem() {
		return clientSystem;
	}

	public void setClientSystem(String clientSystem) {
		this.clientSystem = clientSystem;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getCallInterfaceName() {
		return callInterfaceName;
	}

	public void setCallInterfaceName(String callInterfaceName) {
		this.callInterfaceName = callInterfaceName;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}

	/**
	 * @param deviceCode
	 *            the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	/**
	 * get 设备唯一号
	 * 
	 * @return
	 */
	public String getDeviceCodeNotEncrypt() {
		return deviceCodeNotEncrypt;
	}

	/**
	 * set 设备唯一号
	 * 
	 * @param deviceCodeNotEncrypt
	 */
	public void setDeviceCodeNotEncrypt(String deviceCodeNotEncrypt) {
		this.deviceCodeNotEncrypt = deviceCodeNotEncrypt;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(this.id).append("\r\n");
		sb.append("traderName=").append(this.traderName).append("\r\n");
		sb.append("traderPassword=").append(this.traderPassword).append("\r\n");
		sb.append("clientSystem=").append(this.clientSystem).append("\r\n");
		sb.append("clientVersion=").append(this.clientVersion).append("\r\n");
		sb.append("protocol=").append(this.protocol).append("\r\n");
		sb.append("interfaceVersion=").append(this.interfaceVersion)
				.append("\r\n");
		sb.append("callInterfaceName=").append(this.callInterfaceName)
				.append("\r\n");
		sb.append("userToken=").append(this.userToken).append("\r\n");
		sb.append("longitude=").append(this.longitude).append("\r\n");
		sb.append("latitude=").append(this.latitude).append("\r\n");
		sb.append("deviceCode=").append(this.deviceCode).append("\r\n");
		return sb.toString();
	}
}
