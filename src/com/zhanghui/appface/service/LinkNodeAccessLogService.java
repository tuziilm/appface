package com.zhanghui.appface.service;

import com.zhanghui.appface.domain.LinkNodeAccessLog;
import com.zhanghui.appface.persistence.LinkNodeAccessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LinkNodeAccessLog service
 */
@Service
public class LinkNodeAccessLogService extends BaseService<LinkNodeAccessLog> {
    @Autowired
    public void setLinkNodeAccessLogMapper(LinkNodeAccessLogMapper linkNodeAccessLogMapper){
        this.mapper = linkNodeAccessLogMapper;
    }
}
