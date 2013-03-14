package com.towne.framework.common.model;

import java.io.Serializable;

public class Trader implements Serializable {

	private static final long serialVersionUID = 718163362496998144L;
    private String            traderName           = null; /**商户号*/
    private String            traderPassword       = null; /**商户密码*/
//    private String            clientSystem         = null;/**系统版本*/
//    private String            clientVersion        = null;/**客户端版本*/
//    private String            protocol             = null;/**协议(Webservice,HTTPJSON)*/
//    private String            interfaceVersion     = null;/**接口版本*/
    private String            userToken            = null;/**临时token*/
//    private Double            latitude             = null;/**当前纬度*/
//    private Double            longitude            = null;/**当前经度*/
//    private String            deviceCode           = null;/** 客户端唯一标识*/
//    private String           deviceCodeNotEncrypt  = null; /**客户端唯一标识（未加密）*/
    
    /**
     * get 商户号
     * @return traderName
     */
    public String getTraderName() {
        return traderName;
    }

    /**
     * set 商户号
     * @param traderName
     */
    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    /**
     * get 商户密码
     * @return traderPassword
     */
    public String getTraderPassword() {
        return traderPassword;
    }

    /**
     * set 商户密码
     * @param traderPassword
     */
    public void setTraderPassword(String traderPassword) {
        this.traderPassword = traderPassword;
    }

//    /**
//     * get 系统(WAP,PalmOS、Symbian、Windows mobile、WinCE、Linux和Android、iPhoneOS、黑莓)
//     * @return clientSystem
//     */
//    public String getClientSystem() {
//        return clientSystem;
//    }
//
//    /**
//     * set get 系统(WAP,PalmOS、Symbian、Windows mobile、WinCE、Linux和Android、iPhoneOS、黑莓)
//     * @param clientSystem
//     */
//    public void setClientSystem(String clientSystem) {
//        this.clientSystem = clientSystem;
//    }
//
//    /**
//     * get 客户端版本
//     * @return clientVersion
//     */
//    public String getClientVersion() {
//        return clientVersion;
//    }
//
//    /**
//     * set 客户端版本
//     * @param clientVersion
//     */
//    public void setClientVersion(String clientVersion) {
//        this.clientVersion = clientVersion;
//    }
//
//    /**
//     * get 协议(Webservice,HTTPXML)
//     * @return protocol
//     */
//    public String getProtocol() {
//        return protocol;
//    }
//
//    /**
//     * set 协议(Webservice,HTTPXML)
//     * @param protocol
//     */
//    public void setProtocol(String protocol) {
//        this.protocol = protocol;
//    }
//
//    /**
//     * set 接口版本
//     * @return interfaceVersion
//     */
//    public String getInterfaceVersion() {
//        return interfaceVersion;
//    }
//
//    /**
//     * get 接口版本
//     * @param interfaceVersion
//     */
//    public void setInterfaceVersion(String interfaceVersion) {
//        this.interfaceVersion = interfaceVersion;
//    }

    /**
     * get 临时token
     * @return userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * set 临时token
     * @param userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

//	/**
//	 * get 当前经度
//	 * @return the latitude
//	 */
//	public Double getLatitude() {
//		return latitude;
//	}
//
//	/**
//	 * set 当前经度
//	 * @param latitude the latitude to set
//	 */
//	public void setLatitude(Double latitude) {
//		this.latitude = latitude;
//	}
//
//	/**
//	 * get 设备唯一号
//	 * @return the deviceCode
//	 */
//	public String getDeviceCode() {
//		return deviceCode;
//	}
//
//	/**
//	 * set 设备唯一号
//	 * @param deviceCode the deviceCode to set
//	 */
//	public void setDeviceCode(String deviceCode) {
//		this.deviceCode = deviceCode;
//	}
//
//	/**
//	 * get 当前纬度
//	 * @return the longitude
//	 */
//	public Double getLongitude() {
//		return longitude;
//	}
//
//	/**
//	 * set 当前纬度
//	 * @param longitude the longitude to set
//	 */
//	public void setLongitude(Double longitude) {
//		this.longitude = longitude;
//	}
//
//	/**
//	 * get 设备唯一号
//	 * @return
//	 */
//	public String getDeviceCodeNotEncrypt() {
//		return deviceCodeNotEncrypt;
//	}
//
//	/**
//	 * set 设备唯一号
//	 * @param deviceCodeNotEncrypt
//	 */
//	public void setDeviceCodeNotEncrypt(String deviceCodeNotEncrypt) {
//		this.deviceCodeNotEncrypt = deviceCodeNotEncrypt;
//	}
}
