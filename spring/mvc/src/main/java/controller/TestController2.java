package controller;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RestController;
import servlet.ModelAndView;
@RestController

public class TestController2 {
	@RequestMapping(value="/test2.do")
	public int Hello(int a,int b) {
		return a+b;
	}
}