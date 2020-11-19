package controller;

import annotation.Autowired;
import annotation.Component;
import annotation.RequestMapping;
import annotation.RestController;
import mymvc.CalculateServlet;

@RestController
@Component(name = "calculateController")
public class CalculateController {
	//注意不要输入中文字符（），不要空格
	@Autowired(name="calculateServlet")
	CalculateServlet cs;
	@RequestMapping(value="/calculate.do")
	public String Compute(String str) {
		str=str.replace(' ', '+');
		System.out.println(str);
		return str+"="+cs.compute(str);
	}
}
