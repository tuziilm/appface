package com.zhanghui.appface.service;

import com.zhanghui.appface.domain.LinkNode;
import com.zhanghui.appface.persistence.LinkNodeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import javax.annotation.Resource;

/**
 * LinkNode service
 */
@Service
public class LinkNodeService extends MapBasedCacheSupportService<String, LinkNode> {
    @Autowired
    public void setLinkNodeMapper(LinkNodeMapper linkNodeMapper){
        this.mapper = linkNodeMapper;
    }

    @Override
    public void updateCacheMap(HashMap<String, LinkNode> update, LinkNode linkNode) {
        update.put(linkNode.getCode(), linkNode);
    }

    public LinkNode getByCodeCache(String code){
        return getCache().get(code);
    }
    
}
