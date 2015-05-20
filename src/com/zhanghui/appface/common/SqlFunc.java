package com.zhanghui.appface.common;

import com.google.common.base.Strings;

import java.util.List;

/**
 *
 * 自定义用于Sql的方法表
 * @author <a href="mailto:pangkunyi@gmail.com">Calvin Pang</a>
 */
public final class SqlFunc {
    private final static SqlFunc func=new SqlFunc();
    private SqlFunc(){}
    public final static SqlFunc get(){
        return func;
    }

    public static String searchAny(String value){
        if(Strings.isNullOrEmpty(value))
            return null;
        return String.format("%%%s%%", value);
    }

    public static String inList(List<String> datas){
        if(datas == null || datas.isEmpty())
            return "";
        StringBuilder sql =new StringBuilder();
        sql.append("(");
        for(String data : datas){
            sql.append("'").append(data).append("',");
        }
        sql.setCharAt(sql.length()-1,')');
        return sql.toString();
    }
    public boolean eq1(Number value){
        if(value == null)
            return false;
        else
            return value.doubleValue()==1;
    }

    public boolean lt(Number value, Number target){
        if(value == null)
            return false;
        else
            return value.doubleValue()<target.doubleValue();
    }
    public boolean gt0(Number value){
        return gt(value, 0);
    }
    public boolean gtM1(Number value){
        return gt(value, -1);
    }
    public boolean gt(Number value, Number target){
        if(value == null)
            return false;
        else
            return value.doubleValue()>target.doubleValue();
    }

    public boolean notNull(Object value){
        return value != null;
    }

    public boolean notEmpty(Object value){
        if(value==null)
            return false;
        if(value instanceof String){
            return !Strings.isNullOrEmpty((String) value);
        }
        return true;
    }

    public Number getM1(){
       return -1;
    }
}
