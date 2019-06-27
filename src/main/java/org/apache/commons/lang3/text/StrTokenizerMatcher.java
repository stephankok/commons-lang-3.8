package org.apache.commons.lang3.text;


import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class StrTokenizerMatcher implements Cloneable {
	private StrMatcher delimMatcher = StrMatcher.splitMatcher();
	private StrMatcher quoteMatcher = StrMatcher.noneMatcher();
	private StrMatcher ignoredMatcher = StrMatcher.noneMatcher();
	private StrMatcher trimmerMatcher = StrMatcher.noneMatcher();
	private boolean emptyAsNull = false;
	private boolean ignoreEmptyTokens = true;

	public StrMatcher getDelimMatcher() {
		return delimMatcher;
	}

	public void setDelimMatcher(StrMatcher delimMatcher) {
		this.delimMatcher = delimMatcher;
	}

	public StrMatcher getQuoteMatcher() {
		return quoteMatcher;
	}

	public void setQuoteMatcher(StrMatcher quoteMatcher) {
		this.quoteMatcher = quoteMatcher;
	}

	public StrMatcher getIgnoredMatcher() {
		return ignoredMatcher;
	}

	public void setIgnoredMatcher(StrMatcher ignoredMatcher) {
		this.ignoredMatcher = ignoredMatcher;
	}

	public StrMatcher getTrimmerMatcher() {
		return trimmerMatcher;
	}

	public void setTrimmerMatcher(StrMatcher trimmerMatcher) {
		this.trimmerMatcher = trimmerMatcher;
	}

	public boolean getEmptyAsNull() {
		return emptyAsNull;
	}

	public void setEmptyAsNull(boolean emptyAsNull) {
		this.emptyAsNull = emptyAsNull;
	}

	public boolean getIgnoreEmptyTokens() {
		return ignoreEmptyTokens;
	}

	public void setIgnoreEmptyTokens(boolean ignoreEmptyTokens) {
		this.ignoreEmptyTokens = ignoreEmptyTokens;
	}

	/**
	* Reads character by character through the String to get the next token.
	* @param srcChars   the character array being tokenized
	* @param start   the first character of field
	* @param len   the length of the character array being tokenized
	* @param workArea   a temporary work area
	* @param tokenList   the list of parsed tokens
	* @return  the starting position of the next field (the character immediately after the delimiter), or -1 if end of string found
	*/
	public int readNextToken(final char[] srcChars, int start, final int len, final StrBuilder workArea,
			final List<String> tokenList) {
		while (start < len) {
			final int removeLen = Math.max(ignoredMatcher.isMatch(srcChars, start, start, len),
					trimmerMatcher.isMatch(srcChars, start, start, len));
			if (removeLen == 0 || delimMatcher.isMatch(srcChars, start, start, len) > 0
					|| quoteMatcher.isMatch(srcChars, start, start, len) > 0) {
				break;
			}
			start += removeLen;
		}
		if (start >= len) {
			addToken(tokenList, StringUtils.EMPTY);
			return -1;
		}
		final int delimLen = delimMatcher.isMatch(srcChars, start, start, len);
		if (delimLen > 0) {
			addToken(tokenList, StringUtils.EMPTY);
			return start + delimLen;
		}
		final int quoteLen = quoteMatcher.isMatch(srcChars, start, start, len);
		if (quoteLen > 0) {
			return readWithQuotes(srcChars, start + quoteLen, len, workArea, tokenList, start, quoteLen);
		}
		return readWithQuotes(srcChars, start, len, workArea, tokenList, 0, 0);
	}

	/**
	* Reads a possibly quoted string token.
	* @param srcChars   the character array being tokenized
	* @param start   the first character of field
	* @param len   the length of the character array being tokenized
	* @param workArea   a temporary work area
	* @param tokenList   the list of parsed tokens
	* @param quoteStart   the start position of the matched quote, 0 if no quoting
	* @param quoteLen   the length of the matched quote, 0 if no quoting
	* @return  the starting position of the next field (the character immediately after the delimiter, or if end of string found, then the length of string
	*/
	public int readWithQuotes(final char[] srcChars, final int start, final int len, final StrBuilder workArea,
			final List<String> tokenList, final int quoteStart, final int quoteLen) {
		workArea.clear();
		int pos = start;
		boolean quoting = quoteLen > 0;
		int trimStart = 0;
		while (pos < len) {
			if (quoting) {
				if (isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
					if (isQuote(srcChars, pos + quoteLen, len, quoteStart, quoteLen)) {
						workArea.append(srcChars, pos, quoteLen);
						pos += quoteLen * 2;
						trimStart = workArea.size();
						continue;
					}
					quoting = false;
					pos += quoteLen;
					continue;
				}
				workArea.append(srcChars[pos++]);
				trimStart = workArea.size();
			} else {
				final int delimLen = delimMatcher.isMatch(srcChars, pos, start, len);
				if (delimLen > 0) {
					addToken(tokenList, workArea.substring(0, trimStart));
					return pos + delimLen;
				}
				if (quoteLen > 0 && isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
					quoting = true;
					pos += quoteLen;
					continue;
				}
				final int ignoredLen = ignoredMatcher.isMatch(srcChars, pos, start, len);
				if (ignoredLen > 0) {
					pos += ignoredLen;
					continue;
				}
				final int trimmedLen = trimmerMatcher.isMatch(srcChars, pos, start, len);
				if (trimmedLen > 0) {
					workArea.append(srcChars, pos, trimmedLen);
					pos += trimmedLen;
					continue;
				}
				workArea.append(srcChars[pos++]);
				trimStart = workArea.size();
			}
		}
		addToken(tokenList, workArea.substring(0, trimStart));
		return -1;
	}

	/**
	* Adds a token to a list, paying attention to the parameters we've set.
	* @param list   the list to add to
	* @param tok   the token to add
	*/
	public void addToken(final List<String> list, String tok) {
		if (StringUtils.isEmpty(tok)) {
			if (ignoreEmptyTokens) {
				return;
			}
			if (emptyAsNull) {
				tok = null;
			}
		}
		list.add(tok);
	}

	/**
	* Checks if the characters at the index specified match the quote already matched in readNextToken().
	* @param srcChars   the character array being tokenized
	* @param pos   the position to check for a quote
	* @param len   the length of the character array being tokenized
	* @param quoteStart   the start position of the matched quote, 0 if no quoting
	* @param quoteLen   the length of the matched quote, 0 if no quoting
	* @return  true if a quote is matched
	*/
	public boolean isQuote(final char[] srcChars, final int pos, final int len, final int quoteStart,
			final int quoteLen) {
		for (int i = 0; i < quoteLen; i++) {
			if (pos + i >= len || srcChars[pos + i] != srcChars[quoteStart + i]) {
				return false;
			}
		}
		return true;
	}

	public Object clone() {
		try {
			return (StrTokenizerMatcher) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}