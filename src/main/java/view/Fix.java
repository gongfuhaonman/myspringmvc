package view;

import annotation.View;

@View
public class Fix {
	public String prefix;//前缀
	public String suffix;//后缀
	public String defaultview;//默认视图
	public Fix(String prefix, String suffix, String defaultview) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		this.defaultview = defaultview;
	}
}
