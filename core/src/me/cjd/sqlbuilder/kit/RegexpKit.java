package me.cjd.sqlbuilder.kit;

import java.util.regex.Pattern;

/**
 * 正则表达式工具
 * @author Mr.cjd
 */
public class RegexpKit {
	
	/**
	 * 判断方法
	 * @param regexp 正则表达式
	 * @param input 需要判断的字串
	 * @return 返回成立或不成立
	 */
	public final static boolean test(String regexp, String input){
		return Pattern.compile(regexp).matcher(input).matches();
	}
	
}