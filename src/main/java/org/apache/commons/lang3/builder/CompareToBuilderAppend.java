package org.apache.commons.lang3.builder;


public class CompareToBuilderAppend {
	/**
	* <p>Compares two <code>Object</code>s via reflection.</p> <p>Fields can be private, thus <code>AccessibleObject.setAccessible</code> is used to bypass normal access control checks. This will fail under a security manager unless the appropriate permissions are set.</p> <ul> <li>Static fields will not be compared</li> <li>If the <code>compareTransients</code> is <code>true</code>, compares transient members.  Otherwise ignores them, as they are likely derived fields.</li> <li>Compares superclass fields up to and including <code>reflectUpToClass</code>. If <code>reflectUpToClass</code> is <code>null</code>, compares all superclass fields.</li> </ul> <p>If both <code>lhs</code> and <code>rhs</code> are <code>null</code>, they are considered equal.</p>
	* @param lhs   left-hand object
	* @param rhs   right-hand object
	* @param compareTransients   whether to compare transient fields
	* @param reflectUpToClass   last superclass for which fields are compared
	* @param excludeFields   fields to exclude
	* @return  a negative integer, zero, or a positive integer as <code>lhs</code> is less than, equal to, or greater than <code>rhs</code>
	* @throws NullPointerException   if either <code>lhs</code> or <code>rhs</code> (but not both) is <code>null</code>
	* @throws ClassCastException   if <code>rhs</code> is not assignment-compatible with <code>lhs</code>
	* @since  2.2 (2.0 as <code>reflectionCompare(Object, Object, boolean, Class)</code>)
	*/
	public static int reflectionCompare(final Object lhs, final Object rhs, final boolean compareTransients,
			final Class<?> reflectUpToClass, final String... excludeFields) {
		if (lhs == rhs) {
			return 0;
		}
		if (lhs == null || rhs == null) {
			throw new NullPointerException();
		}
		Class<?> lhsClazz = lhs.getClass();
		if (!lhsClazz.isInstance(rhs)) {
			throw new ClassCastException();
		}
		final CompareToBuilder compareToBuilder = new CompareToBuilder();
		CompareToBuilder.reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
		while (lhsClazz.getSuperclass() != null && lhsClazz != reflectUpToClass) {
			lhsClazz = lhsClazz.getSuperclass();
			CompareToBuilder.reflectionAppend(lhs, rhs, lhsClazz, compareToBuilder, compareTransients, excludeFields);
		}
		return compareToBuilder.toComparison();
	}

	/**
	* <p>Appends to the <code>builder</code> the <code>compareTo(Object)</code> result of the superclass.</p>
	* @param superCompareTo   result of calling <code>super.compareTo(Object)</code>
	* @return  this - used to chain append calls
	* @since  2.0
	*/
	public CompareToBuilder appendSuper(final int superCompareTo, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(superCompareTo);
		return compareToBuilder;
	}

	/**
	* Appends to the <code>builder</code> the comparison of two <code>long</code>s.
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final long lhs, final long rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Long.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>long</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(long,long)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final long[] lhs, final long[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* Appends to the <code>builder</code> the comparison of two <code>int</code>s.
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final int lhs, final int rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Integer.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>int</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(int,int)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final int[] lhs, final int[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* Appends to the <code>builder</code> the comparison of two <code>short</code>s.
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final short lhs, final short rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Short.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>short</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(short,short)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final short[] lhs, final short[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* Appends to the <code>builder</code> the comparison of two <code>char</code>s.
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final char lhs, final char rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Character.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>char</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(char,char)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final char[] lhs, final char[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* Appends to the <code>builder</code> the comparison of two <code>byte</code>s.
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final byte lhs, final byte rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Byte.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>byte</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(byte,byte)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final byte[] lhs, final byte[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the comparison of two <code>double</code>s.</p> <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p> <p>It is compatible with the hash code generated by <code>HashCodeBuilder</code>.</p>
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final double lhs, final double rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Double.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>double</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(double,double)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final double[] lhs, final double[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the comparison of two <code>float</code>s.</p> <p>This handles NaNs, Infinities, and <code>-0.0</code>.</p> <p>It is compatible with the hash code generated by <code>HashCodeBuilder</code>.</p>
	* @param lhs   left-hand value
	* @param rhs   right-hand value
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final float lhs, final float rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		compareToBuilder.setComparison(Float.compare(lhs, rhs));
		return compareToBuilder;
	}

	/**
	* <p>Appends to the <code>builder</code> the deep comparison of two <code>float</code> arrays.</p> <ol> <li>Check if arrays are the same using <code>==</code></li> <li>Check if for <code>null</code>, <code>null</code> is less than non-<code>null</code></li> <li>Check array length, a shorter length array is less than a longer length array</li> <li>Check array contents element by element using  {@link #append(float,float)} </li> </ol>
	* @param lhs   left-hand array
	* @param rhs   right-hand array
	* @return  this - used to chain append calls
	*/
	public CompareToBuilder append(final float[] lhs, final float[] rhs, CompareToBuilder compareToBuilder) {
		if (compareToBuilder.toComparison() != 0) {
			return compareToBuilder;
		}
		if (lhs == rhs) {
			return compareToBuilder;
		}
		if (lhs == null) {
			compareToBuilder.setComparison(-1);
			return compareToBuilder;
		}
		if (rhs == null) {
			compareToBuilder.setComparison(1);
			return compareToBuilder;
		}
		if (lhs.length != rhs.length) {
			compareToBuilder.setComparison(lhs.length < rhs.length ? -1 : 1);
			return compareToBuilder;
		}
		for (int i = 0; i < lhs.length && compareToBuilder.toComparison() == 0; i++) {
			append(lhs[i], rhs[i], compareToBuilder);
		}
		return compareToBuilder;
	}
}