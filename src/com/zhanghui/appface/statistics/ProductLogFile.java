package com.zhanghui.appface.statistics;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhanghui.appface.common.IpSeeker;
import com.zhanghui.appface.common.LogModule;
import com.zhanghui.appface.mvc.callback.LinkNodeController;

public class ProductLogFile {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger("com.zhanghui.appface.common.LogStatistics");
		Set<String> ipSet = new HashSet<String>();
		for(int i=0;i<=100000;i++){
			Random random = new Random();
			int a = random.nextInt(255)+1;
			int b = random.nextInt(255)+1;
			int c = random.nextInt(255)+1;
			int d = random.nextInt(255)+1;
			String ip = a+"."+b+"."+c+"."+d;
			ipSet.add(ip);
			char SEP='\u0001';
			StringBuilder msg=new StringBuilder();
			msg.append(LogModule.LinkNode).append(SEP)
			.append("toss").append(SEP)
			.append(System.currentTimeMillis()).append(SEP);
			append(msg,ip).append(SEP)
			.append(IpSeeker.ipData(ip).shortcut).append(SEP).append("/callback/link_node/lnc-4729b05fd72841cfa76075bf4142fb2b").append(SEP).append(SEP);
			append(msg,"http://tracking.softgames.de/aff_c?offer_ref=toss-a-paper-cpa&aff_ref=affiliate__zhanghuitech");
			logger.info(msg.toString());
		}
		System.out.println(ipSet.size());
	}

	private static StringBuilder append(StringBuilder builder, String msg){
		if(msg!=null){
			builder.append(msg);
		}
		return builder;
	}
}
