package com.zhanghui.appface.statistics.common;

/**
 * @author <a href="mailto:pangkunyi@gmail.com">Calvin Pang</a>
 */
public class LineEntry extends ValidLineEntry{
    public String name;
    public String identity;
    public long time;
    public String ip;
    protected String[] entries;

    public LineEntry load(String line){
        entries = line.split(Config.SEP,-1);
        if(entries.length>3){
            name=entries[0];
            identity=entries[1];
            try{
                time=Long.valueOf(entries[2]);
            }catch(Exception e){
                valid=false;
            }
            ip=entries[3];
        }else{
            valid=false;
        }
        if(!valid){
            System.out.println("invalid line: "+line);
        }
        return this;
    }
}
