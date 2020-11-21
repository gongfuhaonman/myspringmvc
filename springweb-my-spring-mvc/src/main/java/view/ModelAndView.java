package view;

import annotation.Autowired;
import annotation.View;

@View
public class ModelAndView {
	
	private String view;
	
	@Autowired
	private ModelMap mp;
	
	public String getViewName() {
		return view;
	}

	public void setViewName(String viewName) {
		this.view = viewName;
	}
	
	public void addObject(String s,Object o) {
		mp.addAttribute(s, o);
	}
	
	public Object getObject(String s) {
		return mp.get(s);
	} 
	
	public ModelMap getall() {
		return mp;
	}
}
