package com.zhanghui.appface.mvc;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.common.Query.NameQuery;
import com.zhanghui.appface.common.RemarkStatusForm;
import com.zhanghui.appface.domain.AreaManage;
import com.zhanghui.appface.domain.DistributeArea;
import com.zhanghui.appface.mvc.annotation.Ids;
import com.zhanghui.appface.service.AdManageService;
import com.zhanghui.appface.service.AdTypeService;
import com.zhanghui.appface.service.AreaManageService;
import com.zhanghui.appface.service.DistributeAreaService;

//匹配广告位置管理界面
@Controller
@RequestMapping("/area_manage")
public class AreaManageController
		extends
		CRUDController<AreaManage, AreaManageService, AreaManageController.AreaManageForm, NameQuery> {
	private final static Logger log = LoggerFactory
			.getLogger(AreaManageController.class);
	
	@Resource
	private AdTypeService adTypeService;
	@Resource
	private AdManageService adManageService;
	@Resource
	private DistributeAreaService distributeAreaService;
	
	protected final String DISTRIBUTE_AREA;
	
	public AreaManageController() {
		super("area_manage");
		DISTRIBUTE_AREA=String.format("/%s/distribute", "area_manage");
	}

	@Resource
	public void setAreaManageService(AreaManageService areaManageService) {
		this.service = areaManageService;
	}
	
	public static class AreaManageForm extends RemarkStatusForm<AreaManage> {
		@NotNull(message="位置名称不能为空")
		@Size(min=1, message="位置名称不能为空")
		private String area;
		private Integer adType;
		@NotNull(message="位置排序不能为空")
		private Integer areaNum;
		private Integer areaType;
		@Size(min=1, message="国家不能为空")
		private String supportCountries;
		
		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public Integer getAdType() {
			return adType;
		}

		public void setAdType(Integer adType) {
			this.adType = adType;
		}

		public Integer getAreaNum() {
			return areaNum;
		}

		public void setAreaNum(Integer areaNum) {
			this.areaNum = areaNum;
		}

		public Integer getAreaType() {
			return areaType;
		}

		public void setAreaType(Integer areaType) {
			this.areaType = areaType;
		}

		public String getSupportCountries() {
			return supportCountries;
		}

		public void setSupportCountries(String supportCountries) {
			this.supportCountries = supportCountries;
		}

		@Override
		public AreaManage newObj() {
			return new AreaManage();
		}

		@Override
		public void populateObj(AreaManage obj) {
			obj.setArea(area);
			obj.setAdType(adType);
			obj.setAreaNum(areaNum);
			obj.setAreaType(areaType);
			obj.setSupportCountries(supportCountries);
		}
	}

	@Override
	public void innerSave(AreaManageForm form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		//执行更新或保存的操作
		AreaManage areaManage = form.toObj();
		if(areaManage.getAreaType()==null){
			areaManage.setAreaType(0);
		}
		try{
			Boolean flag = false; //标记该国家是否创建过相同的广告位置
			Iterator<String> s2=areaManage.getSupportcountriesObject().iterator();
			for(AreaManage a :service.getAreaManageCache()){
				//如果是修改的话就不跟自己的数据进行比较
				if(a.getId()==areaManage.getId()){
					continue;
				}
				if(a.getAreaNum()==areaManage.getAreaNum()&&a.getAreaType()==areaManage.getAreaType()){
					while(s2.hasNext()){
						if(a.getSupportcountriesObject().contains(s2.next())){
							flag=true;
						}
					}
				}
			}
			if(!flag){
				service.saveOrUpdate(areaManage);
			}else{
				errors.addError(new ObjectError("database", "所选国家中包含已创建过相同广告位置的国家！"));
			}
		}catch(DuplicateKeyException e){
			errors.addError(new ObjectError("database", "代号已经存在！"));
		}
	}
	 @Override
	 protected boolean preList(int page, Paginator paginator, NameQuery query, Model model) {
	       paginator.setNeedTotal(true);//分页
	       return super.preList(page, paginator, query, model);
	   }
	 @Override
	 @RequestMapping(value="/delete/{ids}",method=RequestMethod.POST)
	public String delete(@Ids("ids")int[] ids, HttpServletRequest request) {
		for(int i = 0;i<ids.length;i++){
			if(distributeAreaService.getById(ids[i])!=null){
				distributeAreaService.delete(ids[i]);//删除位置的时候同时将该位置分配的广告也删除
			}
		} 
		return super.delete(ids, request);
	}
	 @Override
	protected void postModify(int id, AreaManage obj, Model model) {
		model.addAttribute("adTypes",adTypeService.getAdTypesCache());
		super.postModify(id, obj, model);
	}
	@RequestMapping(value="/distribute/{id}")
	public String distribute(@PathVariable("id") int id,Model model){
			AreaManage areaManage = service.get(id);
			int areaType = 0;//form表单的列数
			if(areaManage.getAreaType()==1||areaManage.getAreaType()==0){
				areaType=1;
			}else if(areaManage.getAreaType()==2){
				areaType=2;
			}else if(areaManage.getAreaType()==3||areaManage.getAreaType()==4){
				areaType=4;
			}else if(areaManage.getAreaType()==5){
				areaType=3;
			}
			model.addAttribute("areaId",id);
			model.addAttribute("areaNum",areaManage.getAreaNum());
			model.addAttribute("areaType", areaType);
			model.addAttribute("adTypes",adTypeService.getAdTypesCache());
			model.addAttribute("ads", adManageService.getAdManageCache());
			model.addAttribute("distributeArea", distributeAreaService.getById(id));
			return DISTRIBUTE_AREA;
		}
	@RequestMapping(value="/distributeSave",method=RequestMethod.POST)
	public String distributeSave(@Valid DistributeSaveForm form,BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException{
		DistributeArea dis=new DistributeArea();
		if (!errors.hasErrors()) {
			try{
				dis.setAreaNum(form.getAreaNum());
				dis.setId(form.getId());
				dis.setAdIds(form.getAdIds());
				dis.setAdTypeIdsObject(form.getAdTypeIds());
			    distributeAreaService.saveOrUpdate(dis);
			}catch(DuplicateKeyException e){
				errors.addError(new ObjectError("database", "代号已经存在！"));
			} 
		if (errors.hasErrors()) {
			model.addAttribute("errors", errors);
			return DISTRIBUTE_AREA;
		}
	}
		return REDIRECT_LIST_PAGE;
}
	
	public static class DistributeSaveForm{
		private Integer areaNum;
		private Integer id;
		private Integer[] adTypeIds;
		private Integer[] adIds;
		
		public Integer getAreaNum() {
			return areaNum;
		}
		public void setAreaNum(Integer areaNum) {
			this.areaNum = areaNum;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer[] getAdTypeIds() {
			return adTypeIds;
		}
		public void setAdTypeIds(Integer[] adTypeIds) {
			this.adTypeIds = adTypeIds;
		}
		public Integer[] getAdIds() {
			return adIds;
		}
		public void setAdIds(Integer[] adIds) {
			this.adIds = adIds;
		}
	}
}
