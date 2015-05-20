package com.zhanghui.appface.mvc;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.common.Query.NameQuery;
import com.zhanghui.appface.common.RemarkStatusForm;
import com.zhanghui.appface.common.UniqIdGen;
import com.zhanghui.appface.domain.LinkNode;
import com.zhanghui.appface.service.LinkNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LinkNodeController
 */
@Controller
@RequestMapping("/link_node")
public class LinkNodeController extends CRUDController<LinkNode, LinkNodeService, LinkNodeController.LinkNodeForm, NameQuery>{
    private final static Logger log= LoggerFactory.getLogger(LinkNodeController.class);
	public LinkNodeController() {
		super("link_node");
	}
	@Resource
	public void setLinkNodeService(LinkNodeService linkNodeService){
		this.service=linkNodeService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        return super.preList(page, paginator, query, model);
    }

    @Override
	public void innerSave(LinkNodeForm form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
        //执行更新或保存的操作
		try{
			service.saveOrUpdate(form.toObj());
		}catch(DuplicateKeyException e){
			errors.addError(new ObjectError("database", "代号已经存在！"));
		}
	}

    public static class LinkNodeForm extends RemarkStatusForm<LinkNode> {
        private String name;
        private String link;

        @Override
        public LinkNode newObj() {
            return new LinkNode();
        }

        @Override
        public void populateObj(LinkNode linkNode) {
            linkNode.setName(name);
            if(!isModified()){
                linkNode.setCode(UniqIdGen.LINK_NODE_CODE_GEN.gen());
            }
            linkNode.setLink(link);
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
