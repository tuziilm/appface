package com.zhanghui.appface.common;

import com.google.common.io.Files;
import com.zhanghui.appface.exception.UploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpLoads {
	private final static Log log=LogFactory.getLog(UpLoads.class);
	/**
	 * 批量上传文件
	 * @param files
	 * @param ut
	 * @return
	 * @throws UploadException
	 */
	public static List<String> upload(List<MultipartFile> files,UploadType ut) throws UploadException{
		List<String> paths=new ArrayList<String>(files.size());
		for(MultipartFile file : files){
			paths.add(upload(file, ut));
		}
		return paths;
	}
	/**
	 * 上传
	 * @param file  上传文件对象
	 * @return  返回文件上传的路径
	 * @throws Exception
	 */
	public static String upload(MultipartFile file,UploadType ut) throws UploadException{
		if(file==null || file.isEmpty()){
			throw new UploadException("上传文件为空！");
		}
		String oriFilename = file.getOriginalFilename();
		String extension=Files.getFileExtension(oriFilename);
		if(!ut.supportFileType(extension)){
			throw new UploadException("上传文件格式不正确，支持的格式为："+ut.getFileTypesString());
		}
		if(!ut.supportFileSize(file.getSize())){
			throw new UploadException("上传文件太大，文件须在"+ut.getMaxSize()+"以内！");
		}
		String relativeFilename = prepareUpload(ut, oriFilename, extension);
		
		try {
			file.transferTo(new File(Config.UPLOAD_ROOT_DIR+"/"+relativeFilename));
			return relativeFilename;
		} catch (Exception e) {
			throw new UploadException("文件上传失败！");
		}
	}
	/**
	 * 准备上传文件
	 * @param ut
	 * @param oriFilename
	 * @param extension
	 * @return
	 */
	public static String prepareUpload(UploadType ut, String oriFilename,
			String extension) {
		Date date=new Date();
		String relativeDir=ut.getModule()+"/"+new SimpleDateFormat("yyyy/MM").format(date);
		String relativeFilename=relativeDir+"/"+new SimpleDateFormat("ddHHmmssSSS").format(date)+"-"+oriFilename.hashCode()+"."+extension;
		//已当前系统时间的年，月作为文件夹的名称，若不存在，则创建
		File dir=new File(Config.UPLOAD_ROOT_DIR+"/"+relativeDir);
		//判断该文件路径是否存在，如果不存在，则创建
		if(!dir.exists()){
			dir.mkdirs();
		}
		return relativeFilename;
	}
	/**
	 * 根据批量删除指定路径下的文件
	 * @param filePathList  文件路径集合  List<String>
	 * @throws Exception 
	 */
	public static void deleteFileQuitely(List<String> filePathList)throws Exception{
		try {
			for(String path:filePathList){
				File dir=new File(Config.UPLOAD_ROOT_DIR+"/"+path);
				if(dir.exists()){
					dir.delete();
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
}
