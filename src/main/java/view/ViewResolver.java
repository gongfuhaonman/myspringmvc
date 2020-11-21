package view;

import annotation.Autowired;
import annotation.View;

@View
public class ViewResolver {

	@Autowired
	public Fix fix;
	
	public String solve(String s) {
		return fix.prefix+s+fix.suffix;		
	}
	
	public String solve() {  //default path
		return fix.prefix+fix.defaultview;		
	}
	
}
