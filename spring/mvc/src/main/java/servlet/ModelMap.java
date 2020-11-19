package servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ModelMap {
	private  Map<String,Object>map=new HashMap<>();
	public void add(String s,Object o) {
		map.put(s, o);
	}
	public Map<String,Object> getMap() {
		return map;
	}
}
