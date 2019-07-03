package org.apache.commons.lang3.text;


public class StrSubstitutorReplacerPrefix {
	private StrMatcher prefixMatcher;

	public StrMatcher getPrefixMatcher() {
		return prefixMatcher;
	}

	/**
	* Sets the variable prefix matcher currently in use. <p> The variable prefix is the character or characters that identify the start of a variable. This prefix is expressed in terms of a matcher allowing advanced prefix matches.
	* @param prefixMatcher    the prefix matcher to use, null ignored
	* @return   this, to enable chaining
	* @throws IllegalArgumentException   if the prefix matcher is null
	*/
	public StrSubstitutor setVariablePrefixMatcher(final StrMatcher prefixMatcher, StrSubstitutor strSubstitutor) {
		if (prefixMatcher == null) {
			throw new IllegalArgumentException("Variable prefix matcher must not be null!");
		}
		this.prefixMatcher = prefixMatcher;
		return strSubstitutor;
	}

	/**
	* Sets the variable prefix to use. <p> The variable prefix is the character or characters that identify the start of a variable. This method allows a string prefix to be easily set.
	* @param prefix    the prefix for variables, not null
	* @return   this, to enable chaining
	* @throws IllegalArgumentException   if the prefix is null
	*/
	public StrSubstitutor setVariablePrefix(final String prefix, StrSubstitutor strSubstitutor) {
		if (prefix == null) {
			throw new IllegalArgumentException("Variable prefix must not be null!");
		}
		return setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix), strSubstitutor);
	}
}