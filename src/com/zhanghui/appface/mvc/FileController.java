package com.zhanghui.appface.mvc;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.zhanghui.appface.common.*;
import com.zhanghui.appface.service.ForbiddenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 处理文件下载的controller
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
@Controller
public class FileController implements JsonSupport{
	private final static Logger log = LoggerFactory.getLogger(FileController.class);
	@SuppressWarnings("rawtypes")
	private static Class clientAbortExceptionClz;
    @Resource
    private ForbiddenService forbiddenService;

	private void fileNotExsist(HttpServletResponse response) throws IOException{
		response.sendRedirect("/static/common/file_not_exsist.html");
	}
	private void defaultPic(HttpServletResponse response) throws IOException{
		response.sendRedirect("/static/common/pic_not_exsist.png");
	}
	private void defaultIcon(HttpServletResponse response) throws IOException{
		response.sendRedirect("/static/common/pic_not_exsist.png");
	}

    @RequestMapping("/public/pkg_diff/{identity}/{projectId}/{filename}.{extension}")
    public void pkgDiff(@PathVariable("identity") String identity,@PathVariable("filename") String filename,@PathVariable("extension") String extension, @PathVariable(value="projectId") int projectId,final HttpServletRequest request, HttpServletResponse response) throws IOException{
        // do not forbidden
        //if(!"appface".equals(identity)){
         //   boolean forbid = forbiddenService.forbidden(ForbiddenService.DOWNLOAD_FORBIDDEN_TIME, RedisKeys.silentInstallForbiddenKey(RequestUtils.getRemoteIp(request)));
         //   if(forbid){
         //       response.sendError(404);
         //       return;
         //   }
        //}
        response.sendRedirect(String.format("%s/public/pkg_diff/%s/%d/%s.%s",Config.DOWNLOAD_URL, identity, projectId, filename, extension));
    }

	/**
	 * 获取文件
	 * @param module
	 * @param year
	 * @param month
	 * @param filename
	 * @param originalFilename
	 * @param model
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/public/file/{module}/{year}/{month}/{filename}.{extension}")
	public void get(@PathVariable("module") String module,@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("filename") String filename,@PathVariable("extension") String extension, @RequestParam(value="ofn", required=false) String originalFilename,@RequestParam(value="app_id", required=false) Integer appId,Model model,HttpServletResponse response) throws IOException{
		UploadType ut = UploadType.fromModule(module);
		if(ut==null || Strings.isNullOrEmpty(extension)){
			fileNotExsist(response);
			return;
		}
		File file = new File(Config.UPLOAD_ROOT_DIR+"/"+module+"/"+year+"/"+month+"/"+filename+"."+extension);
		if(file.exists()){
			response.setContentType(UploadType.contentType(extension));
			response.setContentLength((int)file.length());
			if(ut==UploadType.APK || ut==UploadType.RICH_SMS){
				response.setHeader("Content-Disposition","attachment;filename="+(originalFilename==null?(filename+"."+extension):originalFilename));
			}
			try{
				Files.copy(file, response.getOutputStream());
			}catch(IOException ioe){
				if(clientAbortExceptionClz == null){
					if("org.apache.catalina.connector.ClientAbortException".equals(ioe.getClass().getName())){
						clientAbortExceptionClz = ioe.getClass();
						log.warn("download file client abort.{}", file.getAbsolutePath());
					}
				} else if(clientAbortExceptionClz == ioe.getClass()){
					log.warn("download file client abort.{}", file.getAbsolutePath());
				}else{
					throw ioe;
				}
			}
		}else{
			if(ut==UploadType.ICON){
				defaultIcon(response);
			}else if(ut==UploadType.PIC){
				defaultPic(response);
			}else{
				fileNotExsist(response);
			}
			return;
		}
	}
}
