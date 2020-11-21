package view;

import java.util.LinkedHashMap;

import annotation.View;

@View
public class ModelMap extends LinkedHashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelMap() {
	}

	public ModelMap(String attributeName, Object attributeValue) {
		addAttribute(attributeName, attributeValue);
	}

	public ModelMap addAttribute(String attributeName, Object attributeValue) {
		put(attributeName, attributeValue);
		return this;
	}

	public boolean containsAttribute(String attributeName) {
		return containsKey(attributeName);
	}
}
