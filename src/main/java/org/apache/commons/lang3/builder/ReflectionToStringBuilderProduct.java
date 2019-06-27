package org.apache.commons.lang3.builder;


import java.lang.reflect.Field;
import org.apache.commons.lang3.ClassUtils;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectionToStringBuilderProduct {
	private boolean appendStatics = false;
	private boolean appendTransients = false;
	private String[] excludeFieldNames;

	public boolean getAppendStatics() {
		return appendStatics;
	}

	public void setAppendStatics(boolean appendStatics) {
		this.appendStatics = appendStatics;
	}

	public boolean getAppendTransients() {
		return appendTransients;
	}

	public void setAppendTransients(boolean appendTransients) {
		this.appendTransients = appendTransients;
	}

	/**
	* Returns whether or not to append the given <code>Field</code>. <ul> <li>Transient fields are appended only if  {@link #isAppendTransients()}  returns <code>true</code>. <li>Static fields are appended only if  {@link #isAppendStatics()}  returns <code>true</code>. <li>Inner class fields are not appended.</li> </ul>
	* @param field The Field to test.
	* @return  Whether or not to append the given <code>Field</code>.
	*/
	public boolean accept(final Field field) {
		if (field.getName().indexOf(ClassUtils.INNER_CLASS_SEPARATOR_CHAR) != -1) {
			return false;
		}
		if (Modifier.isTransient(field.getModifiers()) && !appendTransients) {
			return false;
		}
		if (Modifier.isStatic(field.getModifiers()) && !appendStatics) {
			return false;
		}
		if (this.excludeFieldNames != null && Arrays.binarySearch(this.excludeFieldNames, field.getName()) >= 0) {
			return false;
		}
		return !field.isAnnotationPresent(ToStringExclude.class);
	}

	/**
	* @return  Returns the excludeFieldNames.
	*/
	public String[] getExcludeFieldNames() {
		return this.excludeFieldNames.clone();
	}

	/**
	* Sets the field names to exclude.
	* @param excludeFieldNamesParam The excludeFieldNames to excluding from toString or <code>null</code>.
	* @return  <code>this</code>
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

	/**
	* Builds a String for a toString method excluding the given field names.
	* @param object The object to "toString".
	* @param excludeFieldNames The field names to exclude
	* @return  The toString value.
	*/
	public static String toStringExclude(final Object object, final String... excludeFieldNames) {
		return new ReflectionToStringBuilder(object).setExcludeFieldNames(excludeFieldNames).toString();
	}
}