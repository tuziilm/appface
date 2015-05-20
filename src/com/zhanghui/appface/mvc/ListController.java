package com.zhanghui.appface.mvc;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.common.Query;
import com.zhanghui.appface.domain.Id;
import com.zhanghui.appface.service.BaseService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 实现列表的控制器
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public abstract class ListController<T extends Id, S extends BaseService<T>, Q extends Query> {
	protected final String NAV_LIST_PAGE;
	protected final String LIST_PAGE;
	protected S service;
	
	public ListController(String model){
		NAV_LIST_PAGE=String.format("%s/list", model);
		LIST_PAGE=String.format("/%s/list", model);
	}
	
	@RequestMapping("/list")
	public String list(Q query,BindingResult errors,Model model, HttpServletRequest request){
		int page=getPage(request);
		return innerList(page>0?page:1, query, model);
	}
	
	private int getPage(HttpServletRequest request){
		String page=request.getParameter("_page");
		if(page==null || page.isEmpty()){
			return -1;
		}
		try{
			return Integer.valueOf(page);
		}catch(Exception e){
			//do nothing
		}
		return -1;
	}
	
	@RequestMapping("/list/{page}")
	public String list(@PathVariable("page") int page, Q query,BindingResult errors, Model model){
		return innerList(page, query,model);
	}
	
	protected Paginator makePaginator(int page, Q query){
		Paginator paginator=new Paginator(page);
		paginator.setQuery(query);
		paginator.setPath(NAV_LIST_PAGE);
		return paginator;
	}
	
	private String innerList(int page, Q query,Model model){
		Paginator paginator=makePaginator(page, query);
        boolean expectHasData=preList(page, paginator, query, model);
		List<T> datas=Collections.emptyList();
		if(expectHasData){
			datas = service.list(paginator);
		}
		model.addAttribute("datas", datas);
		model.addAttribute("paginator", paginator);
		model.addAttribute("hasDatas",datas!=null&&!datas.isEmpty());
		postList(page, paginator, query, model);
		return LIST_PAGE;
	}
	
	protected void postList(int page, Paginator paginator, Q query, Model model){
	}
	/**
	 * 如果返回true，表示预测有数据，false，则预测没有数据，直接跳过数据查询
	 * @param page
	 * @param query
	 * @param model
	 * @return
	 */
	protected boolean preList(int page, Paginator paginator, Q query, Model model){
		return true;
	}
}
