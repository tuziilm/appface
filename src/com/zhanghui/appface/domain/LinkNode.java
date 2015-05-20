package com.zhanghui.appface.domain;

/**
 * 链接统计类
 */
public class LinkNode extends RemarkStatusId implements Cloneable{
    private String name;
    private String code;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    @Override
    public LinkNode clone() {
    	try {
			return (LinkNode)super.clone();
		} catch (CloneNotSupportedException e) {
		}
    	return null;
    }
}
