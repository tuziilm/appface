package com.zhanghui.appface.common;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IpSeeker {
    protected final static Logger log= LoggerFactory.getLogger(IpData.class);

    public static class IpData{
        public long start;
        public long end;
        public boolean isChineseIp;
        public String country;
        public String shortcut;
        IpData(String startIp, String endIp, String country, String shortcut){
            this.start = ip(startIp);
            this.end = ip(endIp);
            this.country = country;
            this.shortcut = shortcut;
            this.isChineseIp = "cn".equals(shortcut) || "hk".equals(shortcut) || "mo".equals(shortcut) || "tw".equals(shortcut);
        }

        public int compare(long ipValue){
            if(start>ipValue){
                return 1;
            }else if (end<ipValue){
                return -1;
            }else{
                return 0;
            }
        }

        @Override
        public String toString() {
            return String.format("IpData[start:%d, end:%d, isChineseIp:%s, country:%s, shortcut:%s]", start, end, isChineseIp, country, shortcut);
        }
    }

    public static IpData ipData(String ip){
        long ipValue= ip(ip);
        if(ipValue==0){
            log.error("invalid ip {}", ip);
            return null;
        }
        int len=ipDatas.length;
        int start=0;
        int end=len-1;
        while(start<=end){
            int mid = (end+start)/2;
            IpData ipData = ipDatas[mid];
            int c = ipData.compare(ipValue);
            if(c==0){
                return ipData;
            }else if(c>0){
                end=mid-1;
            }else{
                start=mid+1;
            }
        }
        log.error("ip not found {}", ip);
        return null;
    }

    public static boolean isChineseIp(String ip){
        IpData ipData = ipData(ip);
        if(ipData==null){//默认是中国
            return true;
        }
        return ipData.isChineseIp;
    }
    public static long ip(String ip){
        if(Strings.isNullOrEmpty(ip)){
            return 0L;
        }
        String[] ips = ip.split(",");
        try{
            String[] fields= ips[ips.length-1].trim().split("\\.");
            return 256L*256L*256L*Long.valueOf(fields[0])+256L*256L*Long.valueOf(fields[1])+256L*Long.valueOf(fields[2])+Long.valueOf(fields[3]);
        }catch (Exception e){
            log.error("failure to convert ip to int", e);
            return 0L;
        }
    }
    public static String ipAddr(long ipValue){
        long _3=256*256*256;
        long _2=256*256;
        long _1=256;
        String ip="";
        if(ipValue>_3){
            long val = ipValue/_3;
            ipValue = ipValue-val*_3;
            ip = ""+val;
        }else{
            ip = "0";
        }
        if(ipValue>_2){
            long val = ipValue/_2;
            ipValue = ipValue-val*_2;
            ip=ip+"."+val;
        }else{
            ip = ip +"."+0;
        }
        if(ipValue>_1){
            long val = ipValue/_1;
            ipValue = ipValue-val*_1;
            ip=ip+"."+val;
        }else{
            ip = ip +"."+0;
        }
        return ip + "." + ipValue;
    }
    private static IpData[] ipDatas = initIpDatas();

    private static IpData[] initIpDatas() {
        try{
            return Resources.readLines(Resources.getResource(IpSeeker.class, "ip.seeker.dat"), Charsets.UTF_8, new LineProcessor<IpData[]>() {
                private IpData[] ipDatas = new IpData[68266];
                private int idx = 0;
                @Override
                public boolean processLine(String s) throws IOException {
                    String[] fields= s.split("\\s+");
                    ipDatas[idx++]=new IpData(fields[0], fields[1], fields[2], fields[3]);
                    return true;
                }

                @Override
                public IpData[] getResult() {
                    return ipDatas;
                }
            });
        }catch (Exception e){
            log.error("failure to init ip datas", e);
        }
        return new IpData[0];
    }
}
