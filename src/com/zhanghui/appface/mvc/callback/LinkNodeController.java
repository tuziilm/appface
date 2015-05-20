package com.zhanghui.appface.mvc.callback;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.appface.common.IpSeeker;
import com.zhanghui.appface.common.LogModule;
import com.zhanghui.appface.common.LogStatistics;
import com.zhanghui.appface.common.RequestUtils;
import com.zhanghui.appface.domain.LinkNode;
import com.zhanghui.appface.domain.LinkNodeAccessLog;
import com.zhanghui.appface.service.LinkNodeAccessLogService;
import com.zhanghui.appface.service.LinkNodeService;

/**
 * LinkNodeController
 */
@Controller("cbLinkNodeController")
@RequestMapping("/callback")
public class LinkNodeController {
    @Resource
    private LinkNodeService linkNodeService;

    @RequestMapping("/link_node/{code}")
    public String linkNode(@Valid Form form, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!errors.hasErrors()){
            LinkNode linkNode= linkNodeService.getByCodeCache(form.code);
            if(linkNode!=null){
                LogStatistics.log(LogModule.LinkNode, linkNode.getName(), request, form.code, form.from, linkNode.getLink());
                return "redirect:"+linkNode.getLink();
            }
        }
        response.sendError(HttpStatus.SC_NOT_FOUND);
        return null;
    }

    public static class Form {
        @NotNull
        private String code;
        private String from;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
