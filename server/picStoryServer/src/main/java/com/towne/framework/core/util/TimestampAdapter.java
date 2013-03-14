package com.towne.framework.core.util;

import java.sql.Timestamp;  
import java.util.Date;  
  
import javax.xml.bind.annotation.adapters.XmlAdapter; 

/**
 * 这里用java.sql.Timestamp 表示无法和xml绑定的类
 * 然后使用@XmlJavaTypeAdapter标记到使用java.sql.Timestamp类的地方
 * 例如
 * @XmlRootElement  
   public class InfoDTO {  
   private Timestamp createTime;  
   ...  
   @XmlJavaTypeAdapter(Timestamp.class)  
    public Timestamp getCreateTime() {  
   return this.createTime;  
  }  
}  
 * @author towne
 **/
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {  
	  
	   public Date marshal(Timestamp t) {  
	     return new Date(t.getTime());  
	   }  
	  
	   public Timestamp unmarshal(Date d) {  
	     return new Timestamp (d.getTime());  
	   }  
	  
	}  
