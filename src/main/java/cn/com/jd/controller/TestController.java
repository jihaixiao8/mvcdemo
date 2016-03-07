package cn.com.jd.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jd.testzk.inter.TestRegistryService;
import com.wangyin.card.user.api.facade.UserBaseFacade;

@Controller
@RequestMapping("test")
public class TestController {
	
//	@Resource
//	private TestRegistryService testRegistryService;
	@Resource
	private UserBaseFacade userService;
	
	
	
	@RequestMapping("addUser")
	public void addUser(HttpServletRequest request,HttpServletResponse response){
		try {
			userService.getUser("10000", 12);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
