/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.apache.commons.lang3.builder;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class DiffBuilderPrimitives {
	private final List<Diff<?>> diffs;
	private final boolean objectsTriviallyEqual;

	public DiffBuilderPrimitives(boolean testTriviallyEqual, Object lhs, Object rhs) {
		this.diffs = new ArrayList<>();
		this.objectsTriviallyEqual = testTriviallyEqual && (lhs == rhs || lhs.equals(rhs));
	}

	public List<Diff<?>> getDiffs() {
		return diffs;
	}

	/**
	* <p> Test if two  {@code  Objects} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  Object}
	* @param rhs the right hand  {@code  Object}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final Object lhs, final Object rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs == rhs) {
			return diffBuilder;
		}
		Object objectToTest;
		if (lhs != null) {
			objectToTest = lhs;
		} else {
			objectToTest = rhs;
		}
		if (objectToTest.getClass().isArray()) {
			if (objectToTest instanceof boolean[]) {
				return append(fieldName, (boolean[]) lhs, (boolean[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof byte[]) {
				return append(fieldName, (byte[]) lhs, (byte[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof char[]) {
				return append(fieldName, (char[]) lhs, (char[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof double[]) {
				return append(fieldName, (double[]) lhs, (double[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof float[]) {
				return append(fieldName, (float[]) lhs, (float[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof int[]) {
				return append(fieldName, (int[]) lhs, (int[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof long[]) {
				return append(fieldName, (long[]) lhs, (long[]) rhs, diffBuilder);
			}
			if (objectToTest instanceof short[]) {
				return append(fieldName, (short[]) lhs, (short[]) rhs, diffBuilder);
			}
			return append(fieldName, (Object[]) lhs, (Object[]) rhs, diffBuilder);
		}
		if (lhs != null && lhs.equals(rhs)) {
			return diffBuilder;
		}
		diffs.add(new Diff<Object>(fieldName) {
			private static final long serialVersionUID = 1L;

			@Override
			public Object getLeft() {
				return lhs;
			}

			@Override
			public Object getRight() {
				return rhs;
			}
		});
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  boolean} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  boolean}
	* @param rhs the right hand  {@code  boolean}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final boolean lhs, final boolean rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Boolean>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Boolean getLeft() {
					return Boolean.valueOf(lhs);
				}

				@Override
				public Boolean getRight() {
					return Boolean.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  boolean[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  boolean[]}
	* @param rhs the right hand  {@code  boolean[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final boolean[] lhs, final boolean[] rhs,
			DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Boolean[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Boolean[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Boolean[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  byte} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  byte}
	* @param rhs the right hand  {@code  byte}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final byte lhs, final byte rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Byte>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Byte getLeft() {
					return Byte.valueOf(lhs);
				}

				@Override
				public Byte getRight() {
					return Byte.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  byte[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  byte[]}
	* @param rhs the right hand  {@code  byte[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final byte[] lhs, final byte[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Byte[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Byte[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Byte[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  char} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  char}
	* @param rhs the right hand  {@code  char}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final char lhs, final char rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Character>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Character getLeft() {
					return Character.valueOf(lhs);
				}

				@Override
				public Character getRight() {
					return Character.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  char[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  char[]}
	* @param rhs the right hand  {@code  char[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final char[] lhs, final char[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Character[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Character[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Character[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  double} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  double}
	* @param rhs the right hand  {@code  double}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final double lhs, final double rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (Double.doubleToLongBits(lhs) != Double.doubleToLongBits(rhs)) {
			diffs.add(new Diff<Double>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Double getLeft() {
					return Double.valueOf(lhs);
				}

				@Override
				public Double getRight() {
					return Double.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  double[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  double[]}
	* @param rhs the right hand  {@code  double[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final double[] lhs, final double[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Double[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Double[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Double[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  float} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  float}
	* @param rhs the right hand  {@code  float}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final float lhs, final float rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (Float.floatToIntBits(lhs) != Float.floatToIntBits(rhs)) {
			diffs.add(new Diff<Float>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Float getLeft() {
					return Float.valueOf(lhs);
				}

				@Override
				public Float getRight() {
					return Float.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  float[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  float[]}
	* @param rhs the right hand  {@code  float[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final float[] lhs, final float[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Float[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Float[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Float[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  int} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  int}
	* @param rhs the right hand  {@code  int}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final int lhs, final int rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Integer>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Integer getLeft() {
					return Integer.valueOf(lhs);
				}

				@Override
				public Integer getRight() {
					return Integer.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  int[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  int[]}
	* @param rhs the right hand  {@code  int[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final int[] lhs, final int[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Integer[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Integer[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Integer[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  long} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  long}
	* @param rhs the right hand  {@code  long}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final long lhs, final long rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Long>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Long getLeft() {
					return Long.valueOf(lhs);
				}

				@Override
				public Long getRight() {
					return Long.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  long[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  long[]}
	* @param rhs the right hand  {@code  long[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final long[] lhs, final long[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Long[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Long[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Long[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  short} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  short}
	* @param rhs the right hand  {@code  short}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final short lhs, final short rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (lhs != rhs) {
			diffs.add(new Diff<Short>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Short getLeft() {
					return Short.valueOf(lhs);
				}

				@Override
				public Short getRight() {
					return Short.valueOf(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  short[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  short[]}
	* @param rhs the right hand  {@code  short[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final short[] lhs, final short[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Short[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Short[] getLeft() {
					return ArrayUtils.toObject(lhs);
				}

				@Override
				public Short[] getRight() {
					return ArrayUtils.toObject(rhs);
				}
			});
		}
		return diffBuilder;
	}

	/**
	* <p> Test if two  {@code  Object[]} s are equal. </p>
	* @param fieldName the field name
	* @param lhs the left hand  {@code  Object[]}
	* @param rhs the right hand  {@code  Object[]}
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	*/
	public DiffBuilder append(final String fieldName, final Object[] lhs, final Object[] rhs, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		if (!Arrays.equals(lhs, rhs)) {
			diffs.add(new Diff<Object[]>(fieldName) {
				private static final long serialVersionUID = 1L;

				@Override
				public Object[] getLeft() {
					return lhs;
				}

				@Override
				public Object[] getRight() {
					return rhs;
				}
			});
		}
		return diffBuilder;
	}

	public void validateFieldNameNotNull(final String fieldName) {
		Validate.isTrue(fieldName != null, "Field name cannot be null");
	}

	/**
	* <p> Append diffs from another  {@code  DiffResult} . </p> <p> This method is useful if you want to compare properties which are themselves Diffable and would like to know which specific part of it is different. </p> <pre> public class Person implements Diffable&lt;Person&gt; { String name; Address address; // implements Diffable&lt;Address&gt; ... public DiffResult diff(Person obj) { return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE) .append("name", this.name, obj.name) .append("address", this.address.diff(obj.address)) .build(); } } </pre>
	* @param fieldName the field name
	* @param diffResult the  {@code  DiffResult}  to append
	* @return  this
	* @throws IllegalArgumentException if field name is  {@code  null}
	* @since  3.5
	*/
	public DiffBuilder append(final String fieldName, final DiffResult diffResult, DiffBuilder diffBuilder) {
		validateFieldNameNotNull(fieldName);
		Validate.isTrue(diffResult != null, "Diff result cannot be null");
		if (objectsTriviallyEqual) {
			return diffBuilder;
		}
		for (final Diff<?> diff : diffResult.getDiffs()) {
			append(fieldName + "." + diff.getFieldName(), diff.getLeft(), diff.getRight(), diffBuilder);
		}
		return diffBuilder;
	}
}