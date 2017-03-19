package com.hlhdidi.shop.interfaces.buyer;

public interface SessionProvider {
	public void setToGlobalSession(String key,String value);
	public String getFromGlobalSession(String key);
}
