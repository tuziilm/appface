package com.zhanghui.appface.mvc;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.appface.common.JsonSupport;
import com.zhanghui.appface.service.PageViewService;

/**
 * PV访问统计显示
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
@Controller
@RequestMapping("/pv")
public class PageViewController implements JsonSupport{
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Resource
	private PageViewService pageViewService;
	
	/**
	 * 展示PV
	 * @param module
	 * @param day
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/view/{module}/{day}")
	public String view(@PathVariable("module") String module, @PathVariable("day") String day, Model model) throws JsonProcessingException, IOException{
		model.addAttribute("module", module);
		model.addAttribute("day", day);
		String data = pageViewService.getData(day, module);
		boolean hasData=data!=null && !data.isEmpty();
		model.addAttribute("hasData", hasData);
		if(hasData){
			model.addAttribute("data", data);
		}
		String xName="刻度";
		String yName="数量";
		if(module.startsWith("MacBaseActive")){
			xName="日期";
			yName="数量";
		}
		model.addAttribute("xName", xName);
		model.addAttribute("yName", yName);
		return "/pv/view";
	}
	
	@ExceptionHandler(value=Throwable.class)
	public void handleException(Throwable e){
		log.error("catch exception", e);
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		String json="{\"/user/update/\":56,\"/user/register\":45}";
		JsonNode root = mapper.readTree(json);
		Iterator<Entry<String, JsonNode>> elements = root.getFields();
		while(elements.hasNext()){
			Entry<String, JsonNode> node = elements.next();
			System.out.println(node.getKey()+"->"+node.getValue().asText());
		}
		
	}
}
