package com.towne.framework.system.filter.vo;

import java.io.Serializable;

public class CookieVO implements Serializable {
    private static final long serialVersionUID = -5380370278082922209L;
    private String            comment          = null;
    private String            domain           = null;
    private Integer           maxAge           = null;
    private String            name             = null;
    private String            path             = null;
    private Boolean           secure           = null;
    private String            value            = null;
    private Integer           version          = null;

    public CookieVO() {

    }

    public CookieVO(String name, String value, String domain, String path, Integer maxAge,
                    Boolean secure, String comment, Integer version) {
        this.name = name;
        this.value = value;
        this.domain = domain;
        this.path = path;
        this.maxAge = maxAge;
        this.secure = secure;
        this.comment = comment;
        this.version = version;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.name + "\t" + this.value + "\t" + this.domain + "\t" + this.path + "\t"
                  + this.maxAge + "\t" + this.secure + "\t" + this.comment);
        return sb.toString();
    }
}
