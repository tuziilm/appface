package com.zhanghui.appface.common;

import java.util.UUID;

public class UniqIdGen {
    public final static UniqIdGen LINK_NODE_CODE_GEN=new UniqIdGen("lnc-", "");
	private final String prefix;
	private final String subfix;
	
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	    }
	
	public String gen(){
		UUID uuid = UUID.randomUUID();
		long mostSigBits=uuid.getMostSignificantBits();
		long leastSigBits=uuid.getLeastSignificantBits();
		return (prefix+digits(mostSigBits >> 32, 8) +
				digits(mostSigBits >> 16, 4) +
				digits(mostSigBits, 4) + 
				digits(leastSigBits >> 48, 4) +
				digits(leastSigBits, 12))+subfix;
	}
	
	public UniqIdGen(String prefix, String subfix) {
		this.prefix=prefix;
		this.subfix=subfix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getSubfix() {
		return subfix;
	}
}
