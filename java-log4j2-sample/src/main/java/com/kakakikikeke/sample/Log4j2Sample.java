package com.kakakikikeke.sample;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

public class Log4j2Sample {

	private static final String TAB = "\t";

	private static Logger log;
	
	public Log4j2Sample() {
		log = LogManager.getLogger(Log4j2Sample.this);
	}
	
	public static void main(String[] args) {
		new Log4j2Sample();
		ThreadContext.put("domain", "hoge.com");
		ThreadContext.put("port", "10001");
		ThreadContext.put("username", "uername");
		ThreadContext.put("password", "password");
		log.error("This is error messages");
		log.trace("下記以降はlog4j2.xmlファイルがクラスパスに通っていないと表示されません");
		log.trace("This is trace messages");
		log.trace("kakakikikeke 20 Japan");
		log.trace("kakakikikeke" + TAB + "20" + TAB + "Japan");
	}
	
}
