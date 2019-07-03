package org.apache.commons.lang3;

import java.lang.reflect.Array;

public class ArrayUtilsCalculations {
	
	/**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
	protected static void reverseObject(final Object array, final int startIndexInclusive, final int endIndexExclusive) {
    	if (array == null) {
            return;
        } 
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(Array.getLength(array), endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
        	tmp = Array.get(array, j);
            Array.set(array, j, Array.get(array, i));
            Array.set(array, i, tmp);
            j--;
            i++;
        }
    }
	
	/**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new boolean[] array.
     * @since 2.1
     */
	 protected static Object addAllObject(final Object array1, final Object array2) {
	        if (array1 == null) {
	            return array2;
	        } else if (array2 == null) {
	            return array1;
	        }
	        int length1 = Array.getLength(array1);
	        int length2 = Array.getLength(array2);
	        
	        final Object newArray = Array.newInstance(array2.getClass().getComponentType(), 
	        		length1 + length2);

	        System.arraycopy(array1, 0, newArray, 0, length1);
	        System.arraycopy(array2, 0, newArray, length1, length2);
	        return newArray;
	    }
	
	/**
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array The array to copy, must not be {@code null}.
     * @param newArrayComponentType If {@code array} is {@code null}, create a
     * size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */
    protected static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
        if (array != null) {
            final int arrayLength = Array.getLength(array);
            final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }
    
    /**
     * Swaps a series of elements in the given int array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    protected static void swapObject(final Object array, int offset1, int offset2, int len) {    	
        if (array == null || Array.getLength(array) == 0 || offset1 >= Array.getLength(array) 
        		|| offset2 >= Array.getLength(array)) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, Array.getLength(array) - offset1), Array.getLength(array) - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final Object aux = Array.get(array, offset1);
            Array.set(array, offset1, Array.get(array, offset2));
            Array.set(array, offset2, aux);
        }
    }
    
    /**
     * Shifts the order of a series of elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    protected static void shiftObject(final Object array, int startIndexInclusive, int endIndexExclusive, int offset) {
    	if (array == null) {
            return;
        }
        if (startIndexInclusive >= Array.getLength(array) - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= Array.getLength(array)) {
            endIndexExclusive = Array.getLength(array);
        }
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1) {
            return;
        }
        offset %= n;
        if (offset < 0) {
            offset += n;
        }
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while (n > 1 && offset > 0) {
            final int n_offset = n - offset;

            if (offset > n_offset) {
                swapObject(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swapObject(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swapObject(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }
    
    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static Object insertObject(final int index, final Object array, final Object values) {
        if (array == null) {
            return null;
        }
        if (values == null || Array.getLength(values) == 0) {
        	final Object cloneArray = Array.newInstance(array.getClass().getComponentType(), Array.getLength(array));
        	System.arraycopy(array, 0, cloneArray, 0, Array.getLength(array));
            return cloneArray;
        }
        if (index < 0 || index > Array.getLength(array)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + Array.getLength(array));
        }

        final Object result = Array.newInstance(array.getClass().getComponentType(), Array.getLength(array) + Array.getLength(values));

        System.arraycopy(values, 0, result, index, Array.getLength(values));
        if (index > 0) {
            System.arraycopy(array, 0, result, 0, index);
        }
        if (index < Array.getLength(array)) {
            System.arraycopy(array, index, result, index + Array.getLength(values), Array.getLength(array) - index);
        }
        return result;
    }
    

}
