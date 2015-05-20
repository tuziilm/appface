package com.zhanghui.appface.common;

import java.util.UUID;

/**
 * Redis Keys
 * @author <a href="mailto:pangkunyi@gmail.com">Calvin Pang</a>
 */
public final class RedisKeys {
    /** 用于记录用户手机发送MT信息的条数, 并以此可以计算出需要发送的MT信息*/
    public final static String TERMINAL_PRODUCT_mt_RECORD_NUM_HkEY=Config.APP_NAME+":terminal:productMT:recordNum";
    /** 用于记录静默安装的应用安装列表, 值如：2,3,4*/
    public final static String SILENT_INSTALL_SUCCESS_HKEY=Config.APP_NAME+":slient_install:success:appIds";
    /** 用于记录静默安装推送的应用ID*/
    public final static String SILENT_INSTALL_RECOMMAND_APPID_HKEY=Config.APP_NAME+":slient_install:recommand:appId";

    /**
     * 用于各个数据服务的version key
     * @param clz
     * @param module
     * @return
     */
    public static String moduleServiceVersionKey(Class clz, String module){
        return Config.APP_NAME+":"+clz.getName()+":"+module+":version";
    }


    public static String uploadKey(String id){
        return String.format("%s:upload:progress:%s", Config.APP_NAME, id);
    }

    public static String otaProgressKey(){
        return String.format("%s:ota:project_package:progress:%s", Config.APP_NAME, UUID.randomUUID().toString());
    }

    public static String silentInstallForbiddenKey(String identity){
        return String.format("%s:slient_install:forbidden:%s", Config.APP_NAME, identity);
    }

}
