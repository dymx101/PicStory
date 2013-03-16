package com.towne.framework.core.utils;


public class PicturePipleUtil {
    public static String getPictureUrl(String oldUrl, String size) {
        if (oldUrl != null && oldUrl.indexOf(".jpg") != -1) {
            String[] sp = oldUrl.split(".jpg");
            if (sp.length == 1) {
                oldUrl =  sp[0] + "_" + size + ".jpg";
            }
        }
//        if (oldUrl != null && oldUrl.indexOf(".yihaodian.com") != -1) {
//        	oldUrl = oldUrl.replace("yihaodian", "yihaodianimg");
//        	Random ran = new Random();
//        	int num = ran.nextInt(2);
//        	String str = (num + 1) + "";
//        	oldUrl = "http://d" + str + oldUrl.substring(oldUrl.indexOf(".yihaodianimg.com"));
//        }
        return oldUrl;

    }

    public static String getPictureUrl(String oldUrl, String size, Long picId) {
        if (oldUrl != null && oldUrl.indexOf(".jpg") != -1) {
            String[] sp = oldUrl.split(".jpg");
            if (sp.length == 1) {
                oldUrl =  sp[0] + "_" + size + ".jpg";
            }
        }
        String[] strs = oldUrl.split("/");
        String newUrl = "";
        if (strs.length == 8) {
        	newUrl = strs[0] + "/" + strs[1] + "/" + "d11.yihaodianimg.com" + 
        		"/" + strs[3] + "/" + strs[4] + "/" + strs[5] + strs[6] +
        		"/" + picId%512 + "/" + picId/512%512 + "/" + strs[7];
        	oldUrl = newUrl;
        } 
        return oldUrl;

    }
    
    public static String getGrouponPictureUrl(String oldUrl, String oldUrl358x218, String size) {
    	String result = oldUrl;
    	if (oldUrl358x218 != null) {
    		result = oldUrl358x218.replace("358x218", size);
    	} else if (oldUrl != null) {
    		result = oldUrl.replace("380x380", size);
    	}
        return result;
    }
}
