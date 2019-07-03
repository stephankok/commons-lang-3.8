package org.apache.commons.lang3.text;


import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class StrTokenizerMatcherConfiguration implements Cloneable {
	private boolean emptyAsNull = false;
	private boolean ignoreEmptyTokens = true;

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
	* Adds a token to a list, paying attention to the parameters we've set.
	* @param list    the list to add to
	* @param tok    the token to add
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

	public Object clone() {
		try {
			return (StrTokenizerMatcherConfiguration) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}