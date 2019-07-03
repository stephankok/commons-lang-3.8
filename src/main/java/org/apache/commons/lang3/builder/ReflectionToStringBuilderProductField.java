package org.apache.commons.lang3.builder;


import java.util.Arrays;

public class ReflectionToStringBuilderProductField {
	private String[] excludeFieldNames;

	public String[] getExcludeFieldNames2() {
		return excludeFieldNames;
	}

	/**
	* @return   Returns the excludeFieldNames.
	*/
	public String[] getExcludeFieldNames() {
		return this.excludeFieldNames.clone();
	}

	/**
	* Sets the field names to exclude.
	* @param excludeFieldNamesParam  The excludeFieldNames to excluding from toString or <code>null</code>.
	* @return   <code>this</code>
	*/
	public ReflectionToStringBuilder setExcludeFieldNames(ReflectionToStringBuilder reflectionToStringBuilder,
			final String... excludeFieldNamesParam) {
		if (excludeFieldNamesParam == null) {
			this.excludeFieldNames = null;
		} else {
			this.excludeFieldNames = ReflectionToStringBuilder.toNoNullStringArray(excludeFieldNamesParam);
			Arrays.sort(this.excludeFieldNames);
		}
		return reflectionToStringBuilder;
	}
}