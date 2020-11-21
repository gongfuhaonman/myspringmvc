package controller;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RestController;
import servlet.ModelAndView;
@Controller(name = "testController")

public class TestController {
	@RequestMapping(value="/test.do")
	public ModelAndView Hello() {
		ModelAndView mv=new ModelAndView();
		mv.addAttribute("name", "ydy");
		mv.setView("/hello.jsp");
		return mv;
	}
}
