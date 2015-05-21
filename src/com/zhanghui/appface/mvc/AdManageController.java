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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.common.Query.NameQuery;
import com.zhanghui.appface.common.RemarkStatusForm;
import com.zhanghui.appface.common.UniqIdGen;
import com.zhanghui.appface.common.UpLoads;
import com.zhanghui.appface.common.UploadType;
import com.zhanghui.appface.domain.AdManage;
import com.zhanghui.appface.domain.LinkNode;
import com.zhanghui.appface.exception.UploadException;
import com.zhanghui.appface.mvc.annotation.Ids;
import com.zhanghui.appface.service.AdManageService;
import com.zhanghui.appface.service.AdTypeService;
import com.zhanghui.appface.service.LinkNodeService;

//ƥ�������������
@Controller
@RequestMapping("/ad_manage")
public class AdManageController
		extends
		CRUDController<AdManage, AdManageService, AdManageController.AdManageForm, NameQuery> {
	private final static Logger log = LoggerFactory
			.getLogger(AdManageController.class);
	@Resource
	private AdTypeService adTypeService;
	@Resource
	private LinkNodeService linkNodeService;
	
	public AdManageController() {
		super("ad_manage");
	}

	@Resource
	public void setAdManageService(AdManageService adManageService) {
		this.service = adManageService;
	}
	
	public static class AdManageForm extends RemarkStatusForm<AdManage> {
		@Size(min=1, message="������Ʋ���Ϊ��")
		private String name;
		@Size(min=1, message="��ʾ���Ʋ���Ϊ��")
		private String showname;
		private Integer type;
		private MultipartFile adPic;
		private MultipartFile adIcon;
		@Size(min=1, message="������Ӳ���Ϊ��")
		private String link;
		private String code;//���LinkNodeCode
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public void setAdPic(MultipartFile adPic) {
			this.adPic = adPic;
		}
		public MultipartFile getAdPic() {
			return adPic;
		}
		
		public void setAdIcon(MultipartFile adIcon) {
			this.adIcon = adIcon;
		}
		
		public MultipartFile getAdIcon() {
			return adIcon;
		}
		
		public String getShowname() {
			return showname;
		}

		public void setShowname(String showname) {
			this.showname = showname;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
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

		@Override
		public AdManage newObj() {
			return new AdManage();
		}

		@Override
		public void populateObj(AdManage obj) {
			obj.setName(name);
			obj.setShowname(showname);
			obj.setType(type);
			obj.setLink(link);
			obj.setCode(code);
		}
	}

	@Override
	public void innerSave(AdManageForm form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		//ִ�и��»򱣴�Ĳ���
		AdManage adManage = form.toObj();
		try {
			//���£������ͼƬ���ϴ���û���򲻴��κδ���ʹ��֮ǰ�ϴ���ͼƬ
			if (form.isModified()) {
				if (!form.adPic.isEmpty()) {
					String picture = UpLoads.upload(form.adPic, UploadType.PIC);
					adManage.setPicture(picture);
				}
				if (!form.adIcon.isEmpty()) {
					String banner = UpLoads.upload(form.adIcon, UploadType.ICON);
					adManage.setBanner(banner);
				}
			}else {
				if (!form.adPic.isEmpty()) {
					String picture = UpLoads.upload(form.adPic, UploadType.PIC);
					adManage.setPicture(picture);
				} else {
					adManage.setPicture("��");
				}
				if (!form.adIcon.isEmpty()) {
					String banner = UpLoads.upload(form.adIcon, UploadType.ICON);
					adManage.setBanner(banner);
				} else {
					adManage.setBanner("��");
				}
			}
		}catch(UploadException e){
			errors.addError(new ObjectError("upload", "�ϴ�ʧ�ܣ�"));
			return;
		}
		try{
			LinkNode linkNode;//���ڴ洢ͳ�Ƶ���Ϣ����������
			if(Strings.isNullOrEmpty(adManage.getCode())){
				linkNode = new LinkNode();
				linkNode.setCode(UniqIdGen.LINK_NODE_CODE_GEN.gen());
			}else{
				linkNode = linkNodeService.getByCodeCache(adManage.getCode()).clone();
			}
			linkNode.setName(form.getShowname());
			linkNode.setLink(form.getLink());
			linkNode.setRemark(form.getName());
			linkNodeService.saveOrUpdate(linkNode);
			adManage.setCode(linkNode.getCode());
			service.saveOrUpdate(adManage);
			
		}catch(DuplicateKeyException e){
			errors.addError(new ObjectError("database", "�����Ѿ����ڣ�"));
		}
	}
	 @Override
	 protected boolean preList(int page, Paginator paginator, NameQuery query, Model model) {
	       paginator.setNeedTotal(true);//��ҳ
	       model.addAttribute("adTypeMap", adTypeService.getAdTypeMapCache());
	       return super.preList(page, paginator, query, model);
	   }	
	 
	 @Override
	protected void postCreate(Model model) {
		 model.addAttribute("adTypes", adTypeService.getAdTypesCache());
	}
	 @Override
	protected void postModify(int id, AdManage obj, Model model) {
		 model.addAttribute("adTypes", adTypeService.getAdTypesCache());
	}
	 @Override
	 @RequestMapping(value="/delete/{ids}",method=RequestMethod.POST)
	public String delete(@Ids("ids") int[] ids, HttpServletRequest request) {
		for(int i = 0;i<ids.length;i++){
			linkNodeService.delete(linkNodeService.getByCodeCache(service.getById(ids[i]).getCode()).getId());
		}
		return super.delete(ids, request);
	}
}
