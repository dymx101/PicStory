package com.towne.framework.core.utils;

import java.io.Serializable;

public class FileUpload implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1697575713796531860L;
	
	private byte[] file;  
    
    public void setFile(byte[] file) {  
        this.file = file;  
    }  
  
    public byte[] getFile() {  
        return file;  
    }  
}
