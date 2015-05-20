package com.zhanghui.appface.mvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.zhanghui.appface.service.RedirectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhanghui.appface.common.IpSeeker;
import com.zhanghui.appface.common.IpSeeker.IpData;
import com.zhanghui.appface.common.RequestUtils;
import com.zhanghui.appface.domain.AreaManage;
import com.zhanghui.appface.domain.DistributeArea;
import com.zhanghui.appface.service.AdManageService;
import com.zhanghui.appface.service.AreaManageService;
import com.zhanghui.appface.service.DistributeAreaService;

//导航首页
@Controller
@RequestMapping("/get")
public class NavigationController{
	private final static Logger log = LoggerFactory
			.getLogger(NavigationController.class);
	@Resource
	private DistributeAreaService distributeAreaService;
	@Resource
	private AdManageService adManageService;
	@Resource
	private AreaManageService areaManageService;
    @Resource
    private RedirectService redirectService;
	
	protected final String NAV_INDEX;//导航页

	public NavigationController(){
		NAV_INDEX=String.format("/%s/index-en", "nav");
	}
	
	@RequestMapping("/navigation")
	public String navigation(@RequestParam(value = "from",required = false) String from, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		model.addAttribute("from", from);
		String ip = RequestUtils.getRemoteIp(request);
		IpData ipData = IpSeeker.ipData(ip);
        String redirectUrl = redirectService.redirect(ipData);
        if(redirectUrl!=null){
            response.sendRedirect(redirectUrl);
            return null;
        }
		List<AreaManage> list = areaManageService.getAreaManageCache();
		int size=0;//用于统计翻滚广告的页数
		int picSize=0;//用于统计一行四列图片广告的页数
		int iconSize=0;//用于统计一行三列的广告页数
		for(AreaManage a : list){
		//-------------------以后需要更改ipData----------------------------	
			if(a.getAreaType()==1&&a.getSupportcountriesObject().contains(ipData.shortcut)){
				if(a.getAreaNum()>size){
					size=a.getAreaNum();
				}
			}
			if(a.getAreaType()==4&&a.getSupportcountriesObject().contains(ipData.shortcut)){
				picSize++;
			}
			if(a.getAreaType()==5&&a.getSupportcountriesObject().contains(ipData.shortcut)){
				iconSize++;
			}
		}
		size=size%4==0?size/4:size/4+1;
		model.addAttribute("ads", adManageService.getAdManageMapCache());
		model.addAttribute("size",size);
		model.addAttribute("picSize",picSize);
		model.addAttribute("iconSize",iconSize);
		model.addAttribute("areas", areaManageService.getAreaManageByCountryCache().get(ipData.shortcut));
		model.addAttribute("distributeAreas", distributeAreaService.getDistributeAreaCache());
		return  NAV_INDEX;
	}
	
}
