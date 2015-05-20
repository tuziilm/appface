package com.zhanghui.appface.common;

import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.zhanghui.appface.exception.ParserException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * 解释apk文件
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class ApkParser {
	public final static String MANIFEST_XML="AndroidManifest.xml";
	public final static String APKTOOL_YML="apktool.yml";
	public final static String ATTR_ANDROID_VERSION_CODE="android:versionCode";
	public final static String ATTR_VERSION_CODE="versionCode";
	public final static String ATTR_ANDROID_VERSION_NAME="android:versionName";
	public final static String ATTR_VERSION_NAME="versionName";
	public final static String ATTR_ANDROID_PACKAGE="android:package";
	public final static String ATTR_PACKAGE="package";
	public final static String ATTR_ANDROID_ICON="android:icon";
	public final static String ATTR_ICON="icon";
	public final static String TAG_APPLICATION="application";
	public final static String PROP_MIN_SDK_VERSION="minSdkVersion";
	private String apkPath;
	private Integer versionCode;
	private String versionName;
	private String packageName;
	private Integer minSdkVersion;
	private String icon;
	private ApkDecoder decoder;
	private File outDir;
	public ApkParser(String apkPath){
		Preconditions.checkNotNull(apkPath, "apk should be set!");
		this.apkPath=apkPath;
	}
	
	public static void main(String[] args) throws AndrolibException, ParserException, IOException, ParserConfigurationException, SAXException {
		String apkPath="apk/2013/01/package.apk";
		ApkParser parser=null;
		try{
			parser=new ApkParser(apkPath);
			parser.parse();
			System.out.println("versionCode:"+parser.getVersionCode());
			System.out.println("versionName:"+parser.getVersionName());
			System.out.println("packageName:"+parser.getPackageName());
			System.out.println("minSdkVersion:"+parser.getMinSdkVersion());
			System.out.println("icon:"+parser.getIcon());
		}finally{
			parser.clean();
		}
	}
	
	public void parse() throws ParserException, AndrolibException, ParserConfigurationException, SAXException, IOException{
		decode();
		parseAndroidManifest();
		parseApkToolYml();
		parseIcon();
	}
	
	/**
	 * 解释icon文件
	 */
	private void parseIcon() {
		if(icon.startsWith("@")){
			icon=icon.substring(1);
		}
		String[] entry = icon.split("/",2);
		String dir=entry[0];
		String path=entry[1];
		String iconPath=checkIcon(dir,path);
		if(iconPath!=null){
			icon=iconPath;
			return;
		}
		iconPath=checkIcon(dir+"-ldpi",path);
		if(iconPath!=null){
			icon=iconPath;
			return;
		}
		iconPath=checkIcon(dir+"-mdpi",path);
		if(iconPath!=null){
			icon=iconPath;
			return;
		}
		iconPath=checkIcon(dir+"-hdpi",path);
		if(iconPath!=null){
			icon=iconPath;
			return;
		}
		iconPath=checkIcon(dir+"-xhdpi",path);
		if(iconPath!=null){
			icon=iconPath;
			return;
		}
	}
	
	/**
	 * 检测文件是否存在，如果存在则返回其绝对路径, 否则null
	 * @param dir
	 * @param path
	 * @return
	 */
	private String checkIcon(String dir, String path){
		final String basePath=(path.indexOf("/")!=-1)?path.substring(path.lastIndexOf("/")+1):path;
		String prePath=(path.indexOf("/")!=-1)?path.substring(0,path.length()-basePath.length()-1):"";
		File iconDir=new File(outDir.getAbsolutePath()+"/res"+"/"+dir+(prePath.isEmpty()?"":("/"+prePath)));
		if(iconDir.exists() && iconDir.isDirectory()){
			File[] listFiles = iconDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					String extension = Files.getFileExtension(name);
					if(UploadType.ICON.supportFileType(extension)){
						return name.startsWith(basePath);
					}
					return false;
				}
			});
			if(listFiles!=null && listFiles.length>0){
				return listFiles[0].getAbsolutePath();
			}
		}
		return null;
	}

	/**
	 * 解释apktool.yml文件
	 * @throws java.io.IOException
	 */
	private void parseApkToolYml() throws IOException {
		LineIterator lineIterator = null;
		try {
			lineIterator = FileUtils.lineIterator(new File(outDir
					.getAbsolutePath() + "/" + APKTOOL_YML));
			while (lineIterator.hasNext()) {
				String line = lineIterator.nextLine().trim();
				if(line.startsWith(PROP_MIN_SDK_VERSION)){
					int idx=line.indexOf(":",PROP_MIN_SDK_VERSION.length());
					String minSdkVersionStr=line.substring(idx+1).trim();
					if(minSdkVersionStr.startsWith("'")){
						minSdkVersionStr=minSdkVersionStr.substring(1,minSdkVersionStr.length()-1).trim();
					}
					minSdkVersion=toInt(minSdkVersionStr);
				}
			}
		} finally {
			LineIterator.closeQuietly(lineIterator);
		}
		
	}
	
	private Integer toInt(String string){
		try{
			return Integer.valueOf(string);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 解释AndroidManifest.xml文件
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws java.io.IOException
	 * @throws org.xml.sax.SAXException
	 */
	private void parseAndroidManifest() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new File(outDir.getAbsolutePath()+"/"+MANIFEST_XML));
		Node root = doc.getFirstChild();
		NamedNodeMap rootAttrMap = root.getAttributes();
		//versionCode
		Node versionCodeNode = rootAttrMap.getNamedItem(ATTR_ANDROID_VERSION_CODE);
		if(versionCodeNode==null){
			versionCodeNode = rootAttrMap.getNamedItem(ATTR_VERSION_CODE);
		}
		Preconditions.checkNotNull(versionCodeNode, "manifest tag has no versionCode attribute");
		versionCode = toInt(versionCodeNode.getNodeValue());
		//versionName
		Node versionNameNode = rootAttrMap.getNamedItem(ATTR_ANDROID_VERSION_NAME);
		if(versionNameNode==null){
			versionNameNode = rootAttrMap.getNamedItem(ATTR_VERSION_NAME);
		}
		Preconditions.checkNotNull(versionNameNode, "manifest tag has no versionName attribute");
		versionName = versionNameNode.getNodeValue();
		//package
		Node packageNode = rootAttrMap.getNamedItem(ATTR_PACKAGE);
		if(packageNode==null){
			packageNode = rootAttrMap.getNamedItem(ATTR_ANDROID_PACKAGE);
		}
		Preconditions.checkNotNull(packageNode, "manifest tag has no package attribute");
		packageName = packageNode.getNodeValue();
		//icon
		NodeList childNodes = root.getChildNodes();
		for(int i=0;i<childNodes.getLength();i++){
			Node node=childNodes.item(i);
			if(node.getNodeName().equalsIgnoreCase(TAG_APPLICATION)){
				NamedNodeMap attrMap = node.getAttributes();
				Node iconNode = attrMap.getNamedItem(ATTR_ANDROID_ICON);
				if(iconNode==null){
					iconNode = attrMap.getNamedItem(ATTR_ICON);
				}
				Preconditions.checkNotNull(iconNode, "manifest tag has no icon attribute");
				icon = iconNode.getNodeValue();
			}
		}
	}

	/**
	 * 反编译apk文件
	 * @throws ParserException 
	 * @throws brut.androlib.AndrolibException
	 */
	private void decode() throws ParserException, AndrolibException {
		File apkFile=new File(Config.UPLOAD_ROOT_DIR+"/"+apkPath);
		if(!apkFile.exists())
			throw new ParserException("apk file not exists!");
		decoder=new ApkDecoder();
		decoder.setDecodeSources((short)0);
		outDir=new File(Config.APK_DECODE_DIR+"/"+"."+System.currentTimeMillis()+"-"+apkFile.getName().hashCode());
		decoder.setOutDir(outDir);
		decoder.setApkFile(apkFile);
		decoder.decode();
	}
	
	public void clean() throws IOException{
		if(outDir!=null){
			FileUtils.deleteDirectory(outDir);
		}
	}
	
	//getters and setters
	public String getApkPath() {
		return apkPath;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(Integer minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
