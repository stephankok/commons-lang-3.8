package org.apache.commons.lang3.text;


import org.apache.commons.lang3.StringUtils;

public class StrSubstitutorReplacerDelimiter {
	private StrMatcher valueDelimiterMatcher;

	public StrMatcher getValueDelimiterMatcher() {
		return valueDelimiterMatcher;
	}

	/**
	* Sets the variable default value delimiter matcher to use. <p> The variable default value delimiter is the character or characters that delimit the variable name and the variable default value. This delimiter is expressed in terms of a matcher allowing advanced variable default value delimiter matches. <p> If the <code>valueDelimiterMatcher</code> is null, then the variable default value resolution becomes disabled.
	* @param valueDelimiterMatcher    variable default value delimiter matcher to use, may be null
	* @return   this, to enable chaining
	* @since   3.2
	*/
	public StrSubstitutor setValueDelimiterMatcher(final StrMatcher valueDelimiterMatcher,
			StrSubstitutor strSubstitutor) {
		this.valueDelimiterMatcher = valueDelimiterMatcher;
		return strSubstitutor;
	}

	/**
	* Sets the variable default value delimiter to use. <p> The variable default value delimiter is the character or characters that delimit the variable name and the variable default value. This method allows a string variable default value delimiter to be easily set. <p> If the <code>valueDelimiter</code> is null or empty string, then the variable default value resolution becomes disabled.
	* @param valueDelimiter    the variable default value delimiter string to use, may be null or empty
	* @return   this, to enable chaining
	* @since   3.2
	*/
	public StrSubstitutor setValueDelimiter(final String valueDelimiter, StrSubstitutor strSubstitutor) {
		if (StringUtils.isEmpty(valueDelimiter)) {
			setValueDelimiterMatcher(null, strSubstitutor);
			return strSubstitutor;
		}
		return setValueDelimiterMatcher(StrMatcher.stringMatcher(valueDelimiter), strSubstitutor);
	}
}