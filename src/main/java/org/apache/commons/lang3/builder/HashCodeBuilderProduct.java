package org.apache.commons.lang3.builder;


public class HashCodeBuilderProduct {
	/**
	* <p> Append a <code>hashCode</code> for a <code>boolean</code>. </p> <p> This adds <code>1</code> when true, and <code>0</code> when false to the <code>hashCode</code>. </p> <p> This is in contrast to the standard <code>java.lang.Boolean.hashCode</code> handling, which computes a <code>hashCode</code> value of <code>1231</code> for <code>java.lang.Boolean</code> instances that represent <code>true</code> or <code>1237</code> for <code>java.lang.Boolean</code> instances that represent <code>false</code>. </p> <p> This is in accordance with the <i>Effective Java</i> design. </p>
	* @param value the boolean to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final boolean value, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant + (value ? 0 : 1));
		return hashCodeBuilder;
	}

	/**
	* <p> Append a <code>hashCode</code> for a <code>boolean</code> array. </p>
	* @param array the array to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final boolean[] array, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		if (array == null) {
			hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant);
		} else {
			for (final boolean element : array) {
				append(element, hashCodeBuilder, thisIConstant);
			}
		}
		return hashCodeBuilder;
	}

	/**
	* <p> Append a <code>hashCode</code> for a <code>byte</code>. </p>
	* @param value the byte to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final byte value, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant + value);
		return hashCodeBuilder;
	}

	/**
	* <p> Append a <code>hashCode</code> for a <code>byte</code> array. </p>
	* @param array the array to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final byte[] array, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		if (array == null) {
			hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant);
		} else {
			for (final byte element : array) {
				append(element, hashCodeBuilder, thisIConstant);
			}
		}
		return hashCodeBuilder;
	}

	/**
	* <p> Append a <code>hashCode</code> for a <code>char</code>. </p>
	* @param value the char to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final char value, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant + value);
		return hashCodeBuilder;
	}

	/**
	* <p> Append a <code>hashCode</code> for a <code>char</code> array. </p>
	* @param array the array to add to the <code>hashCode</code>
	* @return  this
	*/
	public HashCodeBuilder append(final char[] array, HashCodeBuilder hashCodeBuilder, int thisIConstant) {
		if (array == null) {
			hashCodeBuilder.setITotal(hashCodeBuilder.toHashCode() * thisIConstant);
		} else {
			for (final char element : array) {
				append(element, hashCodeBuilder, thisIConstant);
			}
		}
		return hashCodeBuilder;
	}
}