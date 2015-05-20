package com.zhanghui.appface.mvc;

import com.google.common.base.Strings;
import com.zhanghui.appface.common.IdForm;
import com.zhanghui.appface.common.Query;
import com.zhanghui.appface.domain.Id;
import com.zhanghui.appface.mvc.annotation.Ids;
import com.zhanghui.appface.service.BaseService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * 实现增删改查的控制器
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public abstract class CRUDController<T extends Id, S extends BaseService<T>, F extends IdForm, Q extends Query> extends ListController<T, S, Q>{
	protected final String CREATE_PAGE;
	protected final String REDIRECT_LIST_PAGE;
	
	public CRUDController(String model){
		super(model);
		CREATE_PAGE=String.format("/%s/create", model);
		REDIRECT_LIST_PAGE=String.format("redirect:/%s/list", model);
	}
	
	@RequestMapping("/create")
	public String create(Model model){
		postCreate(model);
		return CREATE_PAGE;
	}
	
	public abstract void innerSave(F form,BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response);
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@Valid F form,BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		model.addAttribute("form", form);
		String queryString=request.getParameter("_queryString");
		if (!errors.hasErrors()) {
			innerSave(form, errors, model, request, response);
		}

		if (errors.hasErrors()) {
			model.addAttribute("errors", errors);
			onSaveError(form, errors, model, request, response);
			return CREATE_PAGE;
		}
		return Strings.isNullOrEmpty(queryString)?REDIRECT_LIST_PAGE:REDIRECT_LIST_PAGE+"?"+queryString;
	}
	
	protected void onSaveError(F form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping(value="/delete/{ids}",method=RequestMethod.POST)
	public String delete(@Ids("ids") int[] ids, HttpServletRequest request){
		service.delete(ids);
        String queryString=request.getParameter("_queryString");
        return Strings.isNullOrEmpty(queryString)?REDIRECT_LIST_PAGE:REDIRECT_LIST_PAGE+"?"+queryString;
	}
	
	@RequestMapping("/modify/{id}")
	public String modify(@PathVariable("id") int id,Model model){
		T obj = service.get(id);
		model.addAttribute("form", obj);
		postModify(id, obj, model);
		return CREATE_PAGE;
	}
	
	protected void postModify(int id, T obj, Model model){
	}
	protected void postCreate(Model model){
	}
}
