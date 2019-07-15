package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	int sizePlus1;
    int i, start;
    boolean match ;
    boolean lastMatch;
    
    public StringSplitter() {
    	sizePlus1 = 1;
        i = 0;
        start = 0;
        match = false;
        lastMatch = false;
    }

	public String[] splitWorker(final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final List<String> list = new ArrayList<>();
        
        if (separatorChars == null) {
            // Null separator means use whitespace
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                	addSubstring(str, max, preserveAllTokens, len, list);
                    continue;
                }
                skip();
            }
        } else if (separatorChars.length() == 1) {
            // Optimise 1 character case
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                	addSubstring(str, max, preserveAllTokens, len, list);
                    continue;
                }
                skip();
            }
        } else {
            // standard case
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                	addSubstring(str, max, preserveAllTokens, len, list);
                    continue;
                }
                skip();
            }
        }
        if (match || preserveAllTokens && lastMatch) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[list.size()]);
    }
	
	private void addSubstring(String str, int max, boolean preserveAllTokens, 
			int len, List<String> list) 
	{
		if (match || preserveAllTokens) {
            lastMatch = true;
            if (sizePlus1++ == max) {
                i = len;
                lastMatch = false;
            }
            list.add(str.substring(start, i));
            match = false;
        }
        start = ++i;
	}
	
	private void skip()
	{
        lastMatch = false;
        match = true;
        i++;
	}

}
