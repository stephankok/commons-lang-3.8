package org.apache.commons.lang3.text;


import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StrSubstitutorReplacer {
	private StrSubstitutorReplacerSuffix strSubstitutorReplacerSuffix = new StrSubstitutorReplacerSuffix();
	private StrSubstitutorReplacerDelimiter strSubstitutorReplacerDelimiter = new StrSubstitutorReplacerDelimiter();
	private StrSubstitutorReplacerPrefix strSubstitutorReplacerPrefix = new StrSubstitutorReplacerPrefix();
	private char escapeChar;
	private boolean preserveEscapes = false;
	private boolean enableSubstitutionInVariables;

	public char getEscapeChar() {
		return escapeChar;
	}

	public void setEscapeChar(char escapeChar) {
		this.escapeChar = escapeChar;
	}

	public boolean getPreserveEscapes() {
		return preserveEscapes;
	}

	public void setPreserveEscapes(boolean preserveEscapes) {
		this.preserveEscapes = preserveEscapes;
	}

	public StrMatcher getPrefixMatcher() {
		return strSubstitutorReplacerPrefix.getPrefixMatcher();
	}

	public StrMatcher getSuffixMatcher() {
		return strSubstitutorReplacerSuffix.getSuffixMatcher();
	}

	public StrMatcher getValueDelimiterMatcher() {
		return strSubstitutorReplacerDelimiter.getValueDelimiterMatcher();
	}

	public boolean getEnableSubstitutionInVariables() {
		return enableSubstitutionInVariables;
	}

	public void setEnableSubstitutionInVariables(boolean enableSubstitutionInVariables) {
		this.enableSubstitutionInVariables = enableSubstitutionInVariables;
	}

	/**
	* Replaces all the occurrences of variables within the given source builder with their matching values from the resolver. <p> Only the specified portion of the builder will be processed. The rest of the builder is not processed, but it is not deleted.
	* @param source   the builder to replace in, null returns zero
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the builder to be processed, must be valid
	* @return  true if altered
	*/
	public boolean replaceIn(final StrBuilder source, final int offset, final int length,
			StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		return substitute(source, offset, length, strSubstitutor);
	}

	/**
	* Internal method that substitutes the variables. <p> Most users of this class do not need to call this method. This method will be called automatically by another (public) method. <p> Writers of subclasses can override this method if they need access to the substitution process at the start or end.
	* @param buf   the string builder to substitute into, not null
	* @param offset   the start offset within the builder, must be valid
	* @param length   the length within the builder to be processed, must be valid
	* @return  true if altered
	*/
	public boolean substitute(final StrBuilder buf, final int offset, final int length, StrSubstitutor strSubstitutor) {
		return substitute(buf, offset, length, null, strSubstitutor) > 0;
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source string as a template.
	* @param source   the string to replace in, null returns null
	* @return  the result of the replace operation
	*/
	public String replace(final String source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(source);
		if (substitute(buf, 0, source.length(), strSubstitutor) == false) {
			return source;
		}
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source string as a template. <p> Only the specified portion of the string will be processed. The rest of the string is not processed, and is not returned.
	* @param source   the string to replace in, null returns null
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the array to be processed, must be valid
	* @return  the result of the replace operation
	*/
	public String replace(final String source, final int offset, final int length, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		if (substitute(buf, 0, length, strSubstitutor) == false) {
			return source.substring(offset, offset + length);
		}
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source array as a template. The array is not altered by this method.
	* @param source   the character array to replace in, not altered, null returns null
	* @return  the result of the replace operation
	*/
	public String replace(final char[] source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(source.length).append(source);
		substitute(buf, 0, source.length, strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source array as a template. The array is not altered by this method. <p> Only the specified portion of the array will be processed. The rest of the array is not processed, and is not returned.
	* @param source   the character array to replace in, not altered, null returns null
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the array to be processed, must be valid
	* @return  the result of the replace operation
	*/
	public String replace(final char[] source, final int offset, final int length, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		substitute(buf, 0, length, strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source buffer as a template. The buffer is not altered by this method. <p> Only the specified portion of the buffer will be processed. The rest of the buffer is not processed, and is not returned.
	* @param source   the buffer to use as a template, not changed, null returns null
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the array to be processed, must be valid
	* @return  the result of the replace operation
	*/
	public String replace(final StringBuffer source, final int offset, final int length,
			StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		substitute(buf, 0, length, strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables within the given source buffer with their matching values from the resolver. The buffer is updated with the result. <p> Only the specified portion of the buffer will be processed. The rest of the buffer is not processed, but it is not deleted.
	* @param source   the buffer to replace in, updated, null returns zero
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the buffer to be processed, must be valid
	* @return  true if altered
	*/
	public boolean replaceIn(final StringBuffer source, final int offset, final int length,
			StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		if (substitute(buf, 0, length, strSubstitutor) == false) {
			return false;
		}
		source.replace(offset, offset + length, buf.toString());
		return true;
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source as a template. The source is not altered by this method. <p> Only the specified portion of the buffer will be processed. The rest of the buffer is not processed, and is not returned.
	* @param source   the buffer to use as a template, not changed, null returns null
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the array to be processed, must be valid
	* @return  the result of the replace operation
	* @since  3.2
	*/
	public String replace(final CharSequence source, final int offset, final int length,
			StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		substitute(buf, 0, length, strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source builder as a template. The builder is not altered by this method. <p> Only the specified portion of the builder will be processed. The rest of the builder is not processed, and is not returned.
	* @param source   the builder to use as a template, not changed, null returns null
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the array to be processed, must be valid
	* @return  the result of the replace operation
	*/
	public String replace(final StrBuilder source, final int offset, final int length, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		substitute(buf, 0, length, strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables within the given source builder with their matching values from the resolver. The builder is updated with the result. <p> Only the specified portion of the buffer will be processed. The rest of the buffer is not processed, but it is not deleted.
	* @param source   the buffer to replace in, updated, null returns zero
	* @param offset   the start offset within the array, must be valid
	* @param length   the length within the buffer to be processed, must be valid
	* @return  true if altered
	* @since  3.2
	*/
	public boolean replaceIn(final StringBuilder source, final int offset, final int length,
			StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		final StrBuilder buf = new StrBuilder(length).append(source, offset, length);
		if (substitute(buf, 0, length, strSubstitutor) == false) {
			return false;
		}
		source.replace(offset, offset + length, buf.toString());
		return true;
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source buffer as a template. The buffer is not altered by this method.
	* @param source   the buffer to use as a template, not changed, null returns null
	* @return  the result of the replace operation
	*/
	public String replace(final StringBuffer source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(source.length()).append(source);
		substitute(buf, 0, buf.length(), strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source builder as a template. The builder is not altered by this method.
	* @param source   the builder to use as a template, not changed, null returns null
	* @return  the result of the replace operation
	*/
	public String replace(final StrBuilder source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder(source.length()).append(source);
		substitute(buf, 0, buf.length(), strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables in the given source object with their matching values from the resolver. The input source object is converted to a string using <code>toString</code> and is not altered.
	* @param source   the source to replace in, null returns null
	* @return  the result of the replace operation
	*/
	public String replace(final Object source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		final StrBuilder buf = new StrBuilder().append(source);
		substitute(buf, 0, buf.length(), strSubstitutor);
		return buf.toString();
	}

	/**
	* Replaces all the occurrences of variables within the given source builder with their matching values from the resolver.
	* @param source   the builder to replace in, updated, null returns zero
	* @return  true if altered
	*/
	public boolean replaceIn(final StrBuilder source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		return substitute(source, 0, source.length(), strSubstitutor);
	}

	/**
	* Sets the variable prefix matcher currently in use. <p> The variable prefix is the character or characters that identify the start of a variable. This prefix is expressed in terms of a matcher allowing advanced prefix matches.
	* @param prefixMatcher   the prefix matcher to use, null ignored
	* @return  this, to enable chaining
	* @throws IllegalArgumentException  if the prefix matcher is null
	*/
	public StrSubstitutor setVariablePrefixMatcher(final StrMatcher prefixMatcher, StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerPrefix.setVariablePrefixMatcher(prefixMatcher, strSubstitutor);
	}

	/**
	* Sets the variable prefix to use. <p> The variable prefix is the character or characters that identify the start of a variable. This method allows a string prefix to be easily set.
	* @param prefix   the prefix for variables, not null
	* @return  this, to enable chaining
	* @throws IllegalArgumentException  if the prefix is null
	*/
	public StrSubstitutor setVariablePrefix(final String prefix, StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerPrefix.setVariablePrefix(prefix, strSubstitutor);
	}

	/**
	* Sets the variable suffix matcher currently in use. <p> The variable suffix is the character or characters that identify the end of a variable. This suffix is expressed in terms of a matcher allowing advanced suffix matches.
	* @param suffixMatcher   the suffix matcher to use, null ignored
	* @return  this, to enable chaining
	* @throws IllegalArgumentException  if the suffix matcher is null
	*/
	public StrSubstitutor setVariableSuffixMatcher(final StrMatcher suffixMatcher, StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerSuffix.setVariableSuffixMatcher(suffixMatcher, strSubstitutor);
	}

	/**
	* Sets the variable suffix to use. <p> The variable suffix is the character or characters that identify the end of a variable. This method allows a string suffix to be easily set.
	* @param suffix   the suffix for variables, not null
	* @return  this, to enable chaining
	* @throws IllegalArgumentException  if the suffix is null
	*/
	public StrSubstitutor setVariableSuffix(final String suffix, StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerSuffix.setVariableSuffix(suffix, strSubstitutor);
	}

	/**
	* Sets the variable default value delimiter matcher to use. <p> The variable default value delimiter is the character or characters that delimit the variable name and the variable default value. This delimiter is expressed in terms of a matcher allowing advanced variable default value delimiter matches. <p> If the <code>valueDelimiterMatcher</code> is null, then the variable default value resolution becomes disabled.
	* @param valueDelimiterMatcher   variable default value delimiter matcher to use, may be null
	* @return  this, to enable chaining
	* @since  3.2
	*/
	public StrSubstitutor setValueDelimiterMatcher(final StrMatcher valueDelimiterMatcher,
			StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerDelimiter.setValueDelimiterMatcher(valueDelimiterMatcher, strSubstitutor);
	}

	/**
	* Sets the variable default value delimiter to use. <p> The variable default value delimiter is the character or characters that delimit the variable name and the variable default value. This method allows a string variable default value delimiter to be easily set. <p> If the <code>valueDelimiter</code> is null or empty string, then the variable default value resolution becomes disabled.
	* @param valueDelimiter   the variable default value delimiter string to use, may be null or empty
	* @return  this, to enable chaining
	* @since  3.2
	*/
	public StrSubstitutor setValueDelimiter(final String valueDelimiter, StrSubstitutor strSubstitutor) {
		return strSubstitutorReplacerDelimiter.setValueDelimiter(valueDelimiter, strSubstitutor);
	}

	/**
	* Replaces all the occurrences of variables with their matching values from the resolver using the given source as a template. The source is not altered by this method.
	* @param source   the buffer to use as a template, not changed, null returns null
	* @return  the result of the replace operation
	* @since  3.2
	*/
	public String replace(final CharSequence source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return null;
		}
		return replace(source, 0, source.length(), strSubstitutor);
	}

	/**
	* Replaces all the occurrences of variables within the given source buffer with their matching values from the resolver. The buffer is updated with the result.
	* @param source   the buffer to replace in, updated, null returns zero
	* @return  true if altered
	*/
	public boolean replaceIn(final StringBuffer source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		return replaceIn(source, 0, source.length(), strSubstitutor);
	}

	/**
	* Replaces all the occurrences of variables within the given source buffer with their matching values from the resolver. The buffer is updated with the result.
	* @param source   the buffer to replace in, updated, null returns zero
	* @return  true if altered
	* @since  3.2
	*/
	public boolean replaceIn(final StringBuilder source, StrSubstitutor strSubstitutor) {
		if (source == null) {
			return false;
		}
		return replaceIn(source, 0, source.length(), strSubstitutor);
	}

	/**
	* Replaces all the occurrences of variables in the given source object with their matching values from the map.
	* @param < V >  the type of the values in the map
	* @param source   the source text containing the variables to substitute, null returns null
	* @param valueMap   the map with the values, may be null
	* @return  the result of the replace operation
	*/
	public static <V> String replace(final Object source, final Map<String, V> valueMap) {
		return new StrSubstitutor(valueMap).replace(source);
	}

	/**
	* Replaces all the occurrences of variables in the given source object with their matching values from the map. This method allows to specify a custom variable prefix and suffix
	* @param < V >  the type of the values in the map
	* @param source   the source text containing the variables to substitute, null returns null
	* @param valueMap   the map with the values, may be null
	* @param prefix   the prefix of variables, not null
	* @param suffix   the suffix of variables, not null
	* @return  the result of the replace operation
	* @throws IllegalArgumentException  if the prefix or suffix is null
	*/
	public static <V> String replace(final Object source, final Map<String, V> valueMap, final String prefix,
			final String suffix) {
		return new StrSubstitutor(valueMap, prefix, suffix).replace(source);
	}

	/**
	* Replaces all the occurrences of variables in the given source object with their matching values from the system properties.
	* @param source   the source text containing the variables to substitute, null returns null
	* @return  the result of the replace operation
	*/
	public static String replaceSystemProperties(final Object source) {
		return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(source);
	}

	/**
	* Recursive handler for multiple levels of interpolation. This is the main interpolation method, which resolves the values of all variable references contained in the passed in text.
	* @param buf   the string builder to substitute into, not null
	* @param offset   the start offset within the builder, must be valid
	* @param length   the length within the builder to be processed, must be valid
	* @param priorVariables   the stack keeping track of the replaced variables, may be null
	* @return  the length change that occurs, unless priorVariables is null when the int represents a boolean flag as to whether any change occurred.
	*/
	public int substitute(final StrBuilder buf, final int offset, final int length, List<String> priorVariables,
			StrSubstitutor strSubstitutor) {
		final StrMatcher pfxMatcher = strSubstitutorReplacerPrefix.getPrefixMatcher();
		final StrMatcher suffMatcher = strSubstitutorReplacerSuffix.getSuffixMatcher();
		final char escape = escapeChar;
		final StrMatcher valueDelimMatcher = strSubstitutorReplacerDelimiter.getValueDelimiterMatcher();
		final boolean substitutionInVariablesEnabled = enableSubstitutionInVariables;
		final boolean top = priorVariables == null;
		boolean altered = false;
		int lengthChange = 0;
		char[] chars = buf.buffer;
		int bufEnd = offset + length;
		int pos = offset;
		while (pos < bufEnd) {
			final int startMatchLen = pfxMatcher.isMatch(chars, pos, offset, bufEnd);
			if (startMatchLen == 0) {
				pos++;
			} else {
				if (pos > offset && chars[pos - 1] == escape) {
					if (preserveEscapes) {
						pos++;
						continue;
					}
					buf.deleteCharAt(pos - 1);
					chars = buf.buffer;
					lengthChange--;
					altered = true;
					bufEnd--;
				} else {
					final int startPos = pos;
					pos += startMatchLen;
					int endMatchLen = 0;
					int nestedVarCount = 0;
					while (pos < bufEnd) {
						if (substitutionInVariablesEnabled
								&& (endMatchLen = pfxMatcher.isMatch(chars, pos, offset, bufEnd)) != 0) {
							nestedVarCount++;
							pos += endMatchLen;
							continue;
						}
						endMatchLen = suffMatcher.isMatch(chars, pos, offset, bufEnd);
						if (endMatchLen == 0) {
							pos++;
						} else {
							if (nestedVarCount == 0) {
								String varNameExpr = new String(chars, startPos + startMatchLen,
										pos - startPos - startMatchLen);
								if (substitutionInVariablesEnabled) {
									final StrBuilder bufName = new StrBuilder(varNameExpr);
									substitute(bufName, 0, bufName.length(), strSubstitutor);
									varNameExpr = bufName.toString();
								}
								pos += endMatchLen;
								final int endPos = pos;
								String varName = varNameExpr;
								String varDefaultValue = null;
								if (valueDelimMatcher != null) {
									final char[] varNameExprChars = varNameExpr.toCharArray();
									int valueDelimiterMatchLen = 0;
									for (int i = 0; i < varNameExprChars.length; i++) {
										if (!substitutionInVariablesEnabled && pfxMatcher.isMatch(varNameExprChars, i,
												i, varNameExprChars.length) != 0) {
											break;
										}
										if ((valueDelimiterMatchLen = valueDelimMatcher.isMatch(varNameExprChars,
												i)) != 0) {
											varName = varNameExpr.substring(0, i);
											varDefaultValue = varNameExpr.substring(i + valueDelimiterMatchLen);
											break;
										}
									}
								}
								if (priorVariables == null) {
									priorVariables = new ArrayList<>();
									priorVariables.add(new String(chars, offset, length));
								}
								checkCyclicSubstitution(varName, priorVariables);
								priorVariables.add(varName);
								String varValue = strSubstitutor.resolveVariable(varName, buf, startPos, endPos);
								if (varValue == null) {
									varValue = varDefaultValue;
								}
								if (varValue != null) {
									final int varLen = varValue.length();
									buf.replace(startPos, endPos, varValue);
									altered = true;
									int change = substitute(buf, startPos, varLen, priorVariables, strSubstitutor);
									change = change + varLen - (endPos - startPos);
									pos += change;
									bufEnd += change;
									lengthChange += change;
									chars = buf.buffer;
								}
								priorVariables.remove(priorVariables.size() - 1);
								break;
							}
							nestedVarCount--;
							pos += endMatchLen;
						}
					}
				}
			}
		}
		if (top) {
			return altered ? 1 : 0;
		}
		return lengthChange;
	}

	/**
	* Checks if the specified variable is already in the stack (list) of variables.
	* @param varName   the variable name to check
	* @param priorVariables   the list of prior variables
	*/
	public void checkCyclicSubstitution(final String varName, final List<String> priorVariables) {
		if (priorVariables.contains(varName) == false) {
			return;
		}
		final StrBuilder buf = new StrBuilder(256);
		buf.append("Infinite loop in property interpolation of ");
		buf.append(priorVariables.remove(0));
		buf.append(": ");
		buf.appendWithSeparators(priorVariables, "->");
		throw new IllegalStateException(buf.toString());
	}
}