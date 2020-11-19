package servlet;

import java.util.Map;

public class ModelAndView {
	private String view;	//返回地址
	private ModelMap model;	//模型元素
	private String prefix="/WEB-INF";
	private String suffix="";
	public ModelAndView() {
		model=new ModelMap();
	}
	public void setView(String view) {
		this.view=prefix+view;
	}
	public String getView() {
		return view;
	}
	public void addAttribute(String s,Object o) {
		model.add(s,o);
	}
	public Map<String,Object> getModel() {
		return model.getMap();
	}
//	public String getView() {
//		return view;
//	}
}
