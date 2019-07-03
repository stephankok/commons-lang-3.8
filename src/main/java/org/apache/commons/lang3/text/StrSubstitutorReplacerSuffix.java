package org.apache.commons.lang3.text;


public class StrSubstitutorReplacerSuffix {
	private StrMatcher suffixMatcher;

	public StrMatcher getSuffixMatcher() {
		return suffixMatcher;
	}

	/**
	* Sets the variable suffix matcher currently in use. <p> The variable suffix is the character or characters that identify the end of a variable. This suffix is expressed in terms of a matcher allowing advanced suffix matches.
	* @param suffixMatcher    the suffix matcher to use, null ignored
	* @return   this, to enable chaining
	* @throws IllegalArgumentException   if the suffix matcher is null
	*/
	public StrSubstitutor setVariableSuffixMatcher(final StrMatcher suffixMatcher, StrSubstitutor strSubstitutor) {
		if (suffixMatcher == null) {
			throw new IllegalArgumentException("Variable suffix matcher must not be null!");
		}
		this.suffixMatcher = suffixMatcher;
		return strSubstitutor;
	}

	/**
	* Sets the variable suffix to use. <p> The variable suffix is the character or characters that identify the end of a variable. This method allows a string suffix to be easily set.
	* @param suffix    the suffix for variables, not null
	* @return   this, to enable chaining
	* @throws IllegalArgumentException   if the suffix is null
	*/
	public StrSubstitutor setVariableSuffix(final String suffix, StrSubstitutor strSubstitutor) {
		if (suffix == null) {
			throw new IllegalArgumentException("Variable suffix must not be null!");
		}
		return setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix), strSubstitutor);
	}
}