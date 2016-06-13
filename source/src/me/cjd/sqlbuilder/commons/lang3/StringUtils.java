package me.cjd.sqlbuilder.commons.lang3;

import java.util.List;
import java.util.ArrayList;

public class StringUtils {
	
	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		if (cs1 == cs2) {
			return true;
		}
		if ((cs1 == null) || (cs2 == null)) {
			return false;
		}
		if (((cs1 instanceof String)) && ((cs2 instanceof String))) {
			return cs1.equals(cs2);
		}
		return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
	}
	
	public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
		if ((str1 == null) || (str2 == null)) {
			return str1 == str2;
		}
		if (str1 == str2) {
			return true;
		}
		if (str1.length() != str2.length()) {
			return false;
		}
		return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, str1.length());
	}
	
	public static boolean isEmpty(String str){
		return (str == null) || (trim(str).equals(""));
	}
	
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !isBlank(cs);
	}
	
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}
	
	public static String trimToEmpty(String str) {
		return str == null ? "" : str.trim();
	}
	
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	public static String[] split(String str, char separatorChar) {
		return splitWorker(str, separatorChar, false);
	}

	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	public static String[] split(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, false);
	}

	public static String[] splitByWholeSeparator(String str, String separator) {
		return splitByWholeSeparatorWorker(str, separator, -1, false);
	}

	public static String[] splitByWholeSeparator(String str, String separator, int max) {
		return splitByWholeSeparatorWorker(str, separator, max, false);
	}

	public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
		return splitByWholeSeparatorWorker(str, separator, -1, true);
	}

	public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
		return splitByWholeSeparatorWorker(str, separator, max, true);
	}
	  
	private static String[] splitByWholeSeparatorWorker(String str, String separator, int max,
			boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		if ((separator == null) || ("".equals(separator))) {
			return splitWorker(str, null, max, preserveAllTokens);
		}
		int separatorLength = separator.length();

		ArrayList<String> substrings = new ArrayList<>();
		int numberOfSubstrings = 0;
		int beg = 0;
		int end = 0;
		while (end < len) {
			end = str.indexOf(separator, beg);
			if (end > -1) {
				if (end > beg) {
					numberOfSubstrings++;
					if (numberOfSubstrings == max) {
						end = len;
						substrings.add(str.substring(beg));
					} else {
						substrings.add(str.substring(beg, end));

						beg = end + separatorLength;
					}
				} else {
					if (preserveAllTokens) {
						numberOfSubstrings++;
						if (numberOfSubstrings == max) {
							end = len;
							substrings.add(str.substring(beg));
						} else {
							substrings.add("");
						}
					}
					beg = end + separatorLength;
				}
			} else {
				substrings.add(str.substring(beg));
				end = len;
			}
		}
		return (String[]) substrings.toArray(new String[substrings.size()]);
	}

	private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<>();
		int i = 0;
		int start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if ((match) || (preserveAllTokens)) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				i++;
				start = i;
			} else {
				lastMatch = false;
				match = true;
				i++;
			}
		}
		if ((match) || ((preserveAllTokens) && (lastMatch))) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		List<String> list = new ArrayList<>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
			}
		}
		if (separatorChars.length() == 1) {
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
			}
		} else {
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
			}
		}
		if ((match) || ((preserveAllTokens) && (lastMatch))) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
	
}
