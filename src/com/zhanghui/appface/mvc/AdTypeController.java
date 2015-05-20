package com.zhanghui.appface.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.common.Query.NameQuery;
import com.zhanghui.appface.common.RemarkStatusForm;
import com.zhanghui.appface.domain.AdType;
import com.zhanghui.appface.service.AdTypeService;

//匹配广告分类管理界面
@Controller
@RequestMapping("/ad_type")
public class AdTypeController
		extends
		CRUDController<AdType, AdTypeService, AdTypeController.AdTypeForm, NameQuery> {
	private final static Logger log = LoggerFactory
			.getLogger(AdTypeController.class);

	public AdTypeController() {
		super("ad_type");
	}

	@Resource
	public void setAdTypeService(AdTypeService adTypeService) {
		this.service = adTypeService;
	}
	
	public static class AdTypeForm extends RemarkStatusForm<AdType> {
		@Size(min=1, message="广告分类名称不能为空")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public AdType newObj() {
			return new AdType();
		}

		@Override
		public void populateObj(AdType obj) {
			obj.setName(name);
		}
	}

	@Override
	public void innerSave(AdTypeForm form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		//执行更新或保存的操作
		try{
			service.saveOrUpdate(form.toObj());
		}catch(DuplicateKeyException e){
			errors.addError(new ObjectError("database", "代号已经存在！"));
		}
	}
	 @Override
	 protected boolean preList(int page, Paginator paginator, NameQuery query, Model model) {
	       paginator.setNeedTotal(true);
	       return super.preList(page, paginator, query, model);
	   }	
}
