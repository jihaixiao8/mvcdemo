package cn.com.jd.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import cn.com.jd.service.TestService;

@SuppressWarnings("restriction")
public class TestServiceImpl implements TestService{
	
	@Value("${main.type}")
	private String type;
	
	@PostConstruct
	private void init(){
		System.out.println("¹þ¹þ¹þ");
	}

	public void add() throws Exception {
		if("normal".equals(type))
			System.out.println("ÆÕÍ¨Ö§¸¶");
		
	}
	
}
