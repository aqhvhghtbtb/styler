/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 * <p>Operations on arrays, primitive arrays (like {@code int[]}) and
 * primitive wrapper arrays (like {@code Integer[]}).
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null}
 * array input. However, an Object array that contains a {@code null}
 * element may throw an exception. Each method documents its behaviour.
 *
 * <p>#ThreadSafe#
 * @since 2.0
 */
public class ArrayUtils {

    /**
     * An empty immutable {@code Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * An empty immutable {@code Class} array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
    /**
     * An empty immutable {@code String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * An empty immutable {@code long} array.
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    /**
     * An empty immutable {@code Long} array.
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
    /**
     * An empty immutable {@code int} array.
     */
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    /**
     * An empty immutable {@code Integer} array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
    /**
     * An empty immutable {@code short} array.
     */
    public static final short[] EMPTY_SHORT_ARRAY = new short[0];
    /**
     * An empty immutable {@code Short} array.
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
    /**
     * An empty immutable {@code byte} array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    /**
     * An empty immutable {@code Byte} array.
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
    /**
     * An empty immutable {@code double} array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    /**
     * An empty immutable {@code Double} array.
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
    /**
     * An empty immutable {@code float} array.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    /**
     * An empty immutable {@code Float} array.
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
    /**
     * An empty immutable {@code boolean} array.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    /**
     * An empty immutable {@code Boolean} array.
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
    /**
     * An empty immutable {@code char} array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    /**
     * An empty immutable {@code Character} array.
     */
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    /**
     * The index value when an element is not found in a list or array: {@code -1}.
     * This value is returned by methods in this class and can also be used in comparisons with values returned by
     * various method from {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>ArrayUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as <code>ArrayUtils.clone(new int[] {2})</code>.
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.
     */
    public ArrayUtils() {
      super();
    }


    // NOTE: Cannot use {@code} to enclose text which includes {}, but <code></code> is OK


    // Basic methods handling multi-dimensional arrays
    //-----------------------------------------------------------------------
    /**
     * <p>Outputs an array as a String, treating {@code null} as an empty array.
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     *
     * <p>The format is that of Java source code, for example <code>{a,b}</code>.
     *
     * @param array  the array to get a toString for, may be {@code null}
     * @return a String representation of the array, '{}' if null array input
     */
    public static String toString(final Object array) {
        return toString(array, "{}");
    }

    /**
     * <p>Outputs an array as a String handling {@code null}s.
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     *
     * <p>The format is that of Java source code, for example <code>{a,b}</code>.
     *
     * @param array  the array to get a toString for, may be {@code null}
     * @param stringIfNull  the String to return if the array is {@code null}
     * @return a String representation of the array
     */
    public static String toString(final Object array, final String stringIfNull) {
        if (array == null) {
            return stringIfNull;
        }
        return new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE).append(array).toString();
    }

    /**
     * <p>Get a hash code for an array handling multi-dimensional arrays correctly.
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.
     *
     * @param array  the array to get a hash code for, {@code null} returns zero
     * @return a hash code for the array
     */
    public static int hashCode(final Object array) {
        return new HashCodeBuilder().append(array).toHashCode();
    }

    /**
     * <p>Compares two arrays, using equals(), handling multi-dimensional arrays
     * correctly.
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.
     *
     * @param array1  the left hand array to compare, may be {@code null}
     * @param array2  the right hand array to compare, may be {@code null}
     * @return {@code true} if the arrays are equal
     * @deprecated this method has been replaced by {@code java.util.Objects.deepEquals(Object, Object)} and will be
     * removed from future releases.
     */
    @Deprecated
    public static boolean isEquals(final Object array1, final Object array2) {
        return new EqualsBuilder().append(array1, array2).isEquals();
    }

    // To map
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the given array into a {@link java.util.Map}. Each element of the array
     * must be either a {@link java.util.Map.Entry} or an Array, containing at least two
     * elements, where the first element is used as key and the second as
     * value.
     *
     * <p>This method can be used to initialize:
     * <pre>
     * // Create a Map mapping colors.
     * Map colorMap = ArrayUtils.toMap(new String[][] {
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}});
     * </pre>
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an array whose elements are either a {@link java.util.Map.Entry} or
     *  an Array containing at least two elements, may be {@code null}
     * @return a {@code Map} that was created from the array
     * @throws IllegalArgumentException  if one element of this Array is
     *  itself an Array containing less then two elements
     * @throws IllegalArgumentException  if the array contains elements other
     *  than {@link java.util.Map.Entry} and an Array
     */
    public static Map<Object, Object> toMap(final Object[] array) {
        if (array == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            final Object object = array[i];
            if (object instanceof Map.Entry<?, ?>) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '"
                        + object
                        + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    // Generic array
    //-----------------------------------------------------------------------
    /**
     * <p>Create a type-safe generic array.
     *
     * <p>The Java language does not allow an array to be created from a generic type:
     *
     * <pre>
    public static &lt;T&gt; T[] createAnArray(int size) {
        return new T[size]; // compiler error here
    }
    public static &lt;T&gt; T[] createAnArray(int size) {
        return (T[]) new Object[size]; // ClassCastException at runtime
    }
     * </pre>
     *
     * <p>Therefore new arrays of generic types can be created with this method.
     * For example, an array of Strings can be created:
     *
     * <pre>
    String[] array = ArrayUtils.toArray("1", "2");
    String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
     * </pre>
     *
     * <p>The method is typically used in scenarios, where the caller itself uses generic types
     * that have to be combined into an array.
     *
     * <p>Note, this method makes only sense to provide arguments of the same type so that the
     * compiler can deduce the type of the array itself. While it is possible to select the
     * type explicitly like in
     * <code>Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))</code>,
     * there is no real advantage when compared to
     * <code>new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}</code>.
     *
     * @param  <T>   the array's element type
     * @param  items  the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     * @since  3.0
     */
    public static <T> T[] toArray(final T... items) {
        return items;
    }

    // Clone
    //-----------------------------------------------------------------------
    /**
     * <p>Shallow clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param <T> the component type of the array
     * @param array  the array to shallow clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static long[] clone(final long[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static int[] clone(final int[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static short[] clone(final short[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static char[] clone(final char[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static byte[] clone(final byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static double[] clone(final double[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static float[] clone(final float[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static boolean[] clone(final boolean[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    // nullToEmpty
    //-----------------------------------------------------------------------
    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * @param array  the array to check for {@code null} or empty
     * @param type   the class representation of the desired array
     * @param <T>  the class type
     * @return the same array, {@code public static} empty array if {@code null}
     * @throws IllegalArgumentException if the type argument is null
     * @since 3.5
     */
    public static <T> T[] nullToEmpty(final T[] array, final Class<T[]> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null");
        }

        if (array == null) {
            return type.cast(Array.newInstance(type.getComponentType(), 0));
        }
        return array;
    }


    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Object[] nullToEmpty(final Object[] array) {
        if (isEmpty(array)) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 3.2
     */
    public static Class<?>[] nullToEmpty(final Class<?>[] array) {
        if (isEmpty(array)) {
            return EMPTY_CLASS_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static String[] nullToEmpty(final String[] array) {
        if (isEmpty(array)) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static long[] nullToEmpty(final long[] array) {
        if (isEmpty(array)) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static int[] nullToEmpty(final int[] array) {
        if (isEmpty(array)) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static short[] nullToEmpty(final short[] array) {
        if (isEmpty(array)) {
            return EMPTY_SHORT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static char[] nullToEmpty(final char[] array) {
        if (isEmpty(array)) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static byte[] nullToEmpty(final byte[] array) {
        if (isEmpty(array)) {
            return EMPTY_BYTE_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static double[] nullToEmpty(final double[] array) {
        if (isEmpty(array)) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static float[] nullToEmpty(final float[] array) {
        if (isEmpty(array)) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static boolean[] nullToEmpty(final boolean[] array) {
        if (isEmpty(array)) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Long[] nullToEmpty(final Long[] array) {
        if (isEmpty(array)) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Integer[] nullToEmpty(final Integer[] array) {
        if (isEmpty(array)) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Short[] nullToEmpty(final Short[] array) {
        if (isEmpty(array)) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Character[] nullToEmpty(final Character[] array) {
        if (isEmpty(array)) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Byte[] nullToEmpty(final Byte[] array) {
        if (isEmpty(array)) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Double[] nullToEmpty(final Double[] array) {
        if (isEmpty(array)) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Float[] nullToEmpty(final Float[] array) {
        if (isEmpty(array)) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Boolean[] nullToEmpty(final Boolean[] array) {
        if (isEmpty(array)) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }

    // Subarrays
    //-----------------------------------------------------------------------
    /**
     * <p>Produces a new array containing the elements between
     * the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * <p>The component type of the subarray is always the same as
     * that of the input array. Thus, if the input is an array of type
     * {@code Date}, the following usage is envisaged:
     *
     * <pre>
     * Date[] someDates = (Date[]) ArrayUtils.subarray(allDates, 2, 5);
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(Object[], int, int)
     */
    public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        final Class<?> type = array.getClass().getComponentType();
        if (newSize <= 0) {
            @SuppressWarnings("unchecked") // OK, because array is of type T
            final T[] emptyArray = (T[]) Array.newInstance(type, 0);
            return emptyArray;
        }
        @SuppressWarnings("unchecked") // OK, because array is of type T
        final
        T[] subarray = (T[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code long} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(long[], int, int)
     */
    public static long[] subarray(final long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_LONG_ARRAY;
        }

        final long[] subarray = new long[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code int} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(int[], int, int)
     */
    public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_INT_ARRAY;
        }

        final int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code short} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(short[], int, int)
     */
    public static short[] subarray(final short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_SHORT_ARRAY;
        }

        final short[] subarray = new short[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code char} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(char[], int, int)
     */
    public static char[] subarray(final char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_CHAR_ARRAY;
        }

        final char[] subarray = new char[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code byte} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(byte[], int, int)
     */
    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY;
        }

        final byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code double} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(double[], int, int)
     */
    public static double[] subarray(final double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_DOUBLE_ARRAY;
        }

        final double[] subarray = new double[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code float} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(float[], int, int)
     */
    public static float[] subarray(final float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_FLOAT_ARRAY;
        }

        final float[] subarray = new float[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * <p>Produces a new {@code boolean} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(boolean[], int, int)
     */
    public static boolean[] subarray(final boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }

        final boolean[] subarray = new boolean[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    // Is same length
    //-----------------------------------------------------------------------
    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * <p>Any multi-dimensional aspects of the arrays are ignored.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final Object[] array1, final Object[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final long[] array1, final long[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final int[] array1, final int[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final short[] array1, final short[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final char[] array1, final char[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final byte[] array1, final byte[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final double[] array1, final double[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final float[] array1, final float[] array2) {
        return getLength(array1) == getLength(array2);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final boolean[] array1, final boolean[] array2) {
        return getLength(array1) == getLength(array2);
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Returns the length of the specified array.
     * This method can deal with {@code Object} arrays and with primitive arrays.
     *
     * <p>If the input array is {@code null}, {@code 0} is returned.
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array  the array to retrieve the length from, may be null
     * @return The length of the array, or {@code 0} if the array is {@code null}
     * @throws IllegalArgumentException if the object argument is not an array.
     * @since 2.1
     */
    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * <p>Checks whether two arrays are the same type taking into account
     * multi-dimensional arrays.
     *
     * @param array1 the first array, must not be {@code null}
     * @param array2 the second array, must not be {@code null}
     * @return {@code true} if type of arrays matches
     * @throws IllegalArgumentException if either array is {@code null}
     */
    public static boolean isSameType(final Object array1, final Object array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    // Reverse
    //-----------------------------------------------------------------------
    /**
     * <p>Reverses the order of the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final Object[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final long[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final int[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final short[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final char[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final byte[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final double[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final float[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final boolean[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

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
    public static void reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
     *            the starting index. Under value (&lt;0) is promoted to 0, over value (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Under value (&lt; start index) results in no
     *            change. Over value (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

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
    public static void reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    // Swap
    //-----------------------------------------------------------------------
    /**
     * Swaps two elements in the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 2) -&gt; ["3", "2", "1"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 0) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 1, 0) -&gt; ["2", "1", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 5) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], -1, 1) -&gt; ["2", "1", "3"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final Object[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true], 0, 2) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 0) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 1, 0) -&gt; [false, true, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 5) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], -1, 1) -&gt; [false, true, true]</li>
     * </ul>
     *
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final long[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final int[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final short[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final char[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final byte[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final double[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final float[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps two elements in the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final boolean[] array, final int offset1, final int offset2) {
        if (array == null || array.length == 0) {
            return;
        }
        swap(array, offset1, offset2, 1);
    }

    /**
     * Swaps a series of elements in the given boolean array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 0, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], -3, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 3, 3) -&gt; [false, false, true, true]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final boolean[] array, int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final boolean aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given byte array.
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
    public static void swap(final byte[] array, int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final byte aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given char array.
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
    public static void swap(final char[] array, int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final char aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given double array.
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
    public static void swap(final double[] array,  int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final double aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given float array.
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
    public static void swap(final float[] array, int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final float aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }

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
    public static void swap(final int[] array,  int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final int aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given long array.
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
    public static void swap(final long[] array,  int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final long aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    /**
     * Swaps a series of elements in the given array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 2, 1) -&gt; ["3", "2", "1", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 0, 1) -&gt; ["1", "2", "3", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 2, 0, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], -3, 2, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 3, 3) -&gt; ["4", "2", "3", "1"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final Object[] array,  int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final Object aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

   /**
    * Swaps a series of elements in the given short array.
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
    public static void swap(final short[] array,  int offset1, int offset2, int len) {
        if (array == null || array.length == 0 || offset1 >= array.length || offset2 >= array.length) {
            return;
        }
        if (offset1 < 0) {
            offset1 = 0;
        }
        if (offset2 < 0) {
            offset2 = 0;
        }
        if (offset1 == offset2) {
            return;
        }
        len = Math.min(Math.min(len, array.length - offset1), array.length - offset2);
        for (int i = 0; i < len; i++, offset1++, offset2++) {
            final short aux = array[offset1];
            array[offset1] = array[offset2];
            array[offset2] = aux;
        }
    }

    // Shift
    //-----------------------------------------------------------------------
    /**
     * Shifts the order of the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final Object[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final long[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final int[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final short[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final char[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final byte[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final double[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final float[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final boolean[] array, final int offset) {
        if (array == null) {
            return;
        }
        shift(array, 0, array.length, offset);
    }

    /**
     * Shifts the order of a series of elements in the given boolean array.
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
    public static void shift(final boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
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
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of a series of elements in the given byte array.
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
    public static void shift(final byte[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
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
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of a series of elements in the given char array.
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
    public static void shift(final char[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
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
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of a series of elements in the given double array.
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
    public static void shift(final double[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
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
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
        }
    }

    /**
     * Shifts the order of a series of elements in the given float array.
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
    public static void shift(final float[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
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
                swap(array, startIndexInclusive, startIndexInclusive + n - n_offset,  n_offset);
                n = offset;
                offset -= n_offset;
            } else if (offset < n_offset) {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset,  offset);
                startIndexInclusive += offset;
                n = n_offset;
            } else {
                swap(array, startIndexInclusive, startIndexInclusive + n_offset, offset);
                break;
            }
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
    public static void shift(final int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if (array == null) {
            return;
        }
        if (startIndexInclusive >= array.length - 1 || endIndexExclusive <= 0) {
            return;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = endIndexExclusive - startIndexInclusive;
        if (n <= 1 )
            {
        return
    ;
    } offset %=n
    ; if( offset <0 )
        { offset +=n
    ;
    }
    // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
    // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/ while( n > 1 && offset >0 )
        { final int n_offset = n -offset

        ; if( offset >n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive + n -n_offset  ,n_offset)
            ; n =offset
            ; offset -=n_offset
        ; } else if( offset <n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset  ,offset)
            ; startIndexInclusive +=offset
            ; n =n_offset
        ; } else
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset ,offset)
            ;break
        ;
    }
}

}
/**
     * Shifts the order of a series of elements in the given long array.
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
     */ public static voidshift( finallong[ ]array , intstartIndexInclusive , intendIndexExclusive , intoffset )
    { if( array ==null )
        {return
    ;
    } if( startIndexInclusive >=array. length - 1 || endIndexExclusive <=0 )
        {return
    ;
    } if( startIndexInclusive <0 )
        { startIndexInclusive =0
    ;
    } if( endIndexExclusive >=array.length )
        { endIndexExclusive =array.length
    ;
    } int n = endIndexExclusive -startIndexInclusive
    ; if( n <=1 )
        {return
    ;
    } offset %=n
    ; if( offset <0 )
        { offset +=n
    ;
    }
    // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
    // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/ while( n > 1 && offset >0 )
        { final int n_offset = n -offset

        ; if( offset >n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive + n -n_offset  ,n_offset)
            ; n =offset
            ; offset -=n_offset
        ; } else if( offset <n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset  ,offset)
            ; startIndexInclusive +=offset
            ; n =n_offset
        ; } else
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset ,offset)
            ;break
        ;
    }
}

}
/**
     * Shifts the order of a series of elements in the given array.
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
     */ public static voidshift( finalObject[ ]array , intstartIndexInclusive , intendIndexExclusive , intoffset )
    { if( array ==null )
        {return
    ;
    } if( startIndexInclusive >=array. length - 1 || endIndexExclusive <=0 )
        {return
    ;
    } if( startIndexInclusive <0 )
        { startIndexInclusive =0
    ;
    } if( endIndexExclusive >=array.length )
        { endIndexExclusive =array.length
    ;
    } int n = endIndexExclusive -startIndexInclusive
    ; if( n <=1 )
        {return
    ;
    } offset %=n
    ; if( offset <0 )
        { offset +=n
    ;
    }
    // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
    // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/ while( n > 1 && offset >0 )
        { final int n_offset = n -offset

        ; if( offset >n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive + n -n_offset  ,n_offset)
            ; n =offset
            ; offset -=n_offset
        ; } else if( offset <n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset  ,offset)
            ; startIndexInclusive +=offset
            ; n =n_offset
        ; } else
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset ,offset)
            ;break
        ;
    }
}

}
/**
     * Shifts the order of a series of elements in the given short array.
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
     */ public static voidshift( finalshort[ ]array , intstartIndexInclusive , intendIndexExclusive , intoffset )
    { if( array ==null )
        {return
    ;
    } if( startIndexInclusive >=array. length - 1 || endIndexExclusive <=0 )
        {return
    ;
    } if( startIndexInclusive <0 )
        { startIndexInclusive =0
    ;
    } if( endIndexExclusive >=array.length )
        { endIndexExclusive =array.length
    ;
    } int n = endIndexExclusive -startIndexInclusive
    ; if( n <=1 )
        {return
    ;
    } offset %=n
    ; if( offset <0 )
        { offset +=n
    ;
    }
    // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
    // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/ while( n > 1 && offset >0 )
        { final int n_offset = n -offset

        ; if( offset >n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive + n -n_offset  ,n_offset)
            ; n =offset
            ; offset -=n_offset
        ; } else if( offset <n_offset )
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset  ,offset)
            ; startIndexInclusive +=offset
            ; n =n_offset
        ; } else
            {swap(array ,startIndexInclusive , startIndexInclusive +n_offset ,offset)
            ;break
        ;
    }
}

}
// IndexOf search

// ----------------------------------------------------------------------
// Object IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given object in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalObject[ ]array , final ObjectobjectToFind )
    { returnindexOf(array ,objectToFind ,0)
;

}
/**
     * <p>Finds the index of the given object in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalObject[ ]array , final ObjectobjectToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } if( objectToFind ==null )
        { for( int i =startIndex ; i <array.length ;i++ )
            { if(array[i ] ==null )
                { returni
            ;
        }
    } } else
        { for( int i =startIndex ; i <array.length ;i++ )
            { if(objectToFind.equals(array[i]) )
                { returni
            ;
        }
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given object within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalObject[ ]array , final ObjectobjectToFind )
    { returnlastIndexOf(array ,objectToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given object in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than
     * the array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalObject[ ]array , final ObjectobjectToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } if( objectToFind ==null )
        { for( int i =startIndex ; i >=0 ;i-- )
            { if(array[i ] ==null )
                { returni
            ;
        }
    } } else if(array.getClass().getComponentType().isInstance(objectToFind) )
        { for( int i =startIndex ; i >=0 ;i-- )
            { if(objectToFind.equals(array[i]) )
                { returni
            ;
        }
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the object is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalObject[ ]array , final ObjectobjectToFind )
    { returnindexOf(array ,objectToFind ) !=INDEX_NOT_FOUND
;

}
// long IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finallong[ ]array , final longvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finallong[ ]array , final longvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finallong[ ]array , final longvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finallong[ ]array , final longvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finallong[ ]array , final longvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// int IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalint[ ]array , final intvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalint[ ]array , final intvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalint[ ]array , final intvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalint[ ]array , final intvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalint[ ]array , final intvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// short IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalshort[ ]array , final shortvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalshort[ ]array , final shortvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalshort[ ]array , final shortvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalshort[ ]array , final shortvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalshort[ ]array , final shortvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// char IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */ public static intindexOf( finalchar[ ]array , final charvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */ public static intindexOf( finalchar[ ]array , final charvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */ public static intlastIndexOf( finalchar[ ]array , final charvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */ public static intlastIndexOf( finalchar[ ]array , final charvalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     * @since 2.1
     */ public static booleancontains( finalchar[ ]array , final charvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// byte IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalbyte[ ]array , final bytevalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalbyte[ ]array , final bytevalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalbyte[ ]array , final bytevalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalbyte[ ]array , final bytevalueToFind , intstartIndex )
    { if( array ==null )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalbyte[ ]array , final bytevalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// double IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finaldouble[ ]array , final doublevalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value within a given tolerance in the array.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finaldouble[ ]array , final doublevalueToFind , final doubletolerance )
    { returnindexOf(array ,valueToFind ,0 ,tolerance)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finaldouble[ ]array , final doublevalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finaldouble[ ]array , final doublevalueToFind , intstartIndex , final doubletolerance )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } final double min = valueToFind -tolerance
    ; final double max = valueToFind +tolerance
    ; for( int i =startIndex ; i <array.length ;i++ )
        { if(array[i ] >= min &&array[i ] <=max )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finaldouble[ ]array , final doublevalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value within a given tolerance in the array.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finaldouble[ ]array , final doublevalueToFind , final doubletolerance )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE ,tolerance)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finaldouble[ ]array , final doublevalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @param tolerance  search for value within plus/minus this amount
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finaldouble[ ]array , final doublevalueToFind , intstartIndex , final doubletolerance )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } final double min = valueToFind -tolerance
    ; final double max = valueToFind +tolerance
    ; for( int i =startIndex ; i >=0 ;i-- )
        { if(array[i ] >= min &&array[i ] <=max )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finaldouble[ ]array , final doublevalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
/**
     * <p>Checks if a value falling within the given tolerance is in the
     * given array.  If the array contains a value within the inclusive range
     * defined by (value - tolerance) to (value + tolerance).
     *
     * <p>The method returns {@code false} if a {@code null} array
     * is passed in.
     *
     * @param array  the array to search
     * @param valueToFind  the value to find
     * @param tolerance  the array contains the tolerance of the search
     * @return true if value falling within tolerance is in array
     */ public static booleancontains( finaldouble[ ]array , final doublevalueToFind , final doubletolerance )
    { returnindexOf(array ,valueToFind ,0 ,tolerance ) !=INDEX_NOT_FOUND
;

}
// float IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalfloat[ ]array , final floatvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalfloat[ ]array , final floatvalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalfloat[ ]array , final floatvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalfloat[ ]array , final floatvalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalfloat[ ]array , final floatvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// boolean IndexOf
//-----------------------------------------------------------------------
/**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intindexOf( finalboolean[ ]array , final booleanvalueToFind )
    { returnindexOf(array ,valueToFind ,0)
;

}
/**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
     *  array input
     */ public static intindexOf( finalboolean[ ]array , final booleanvalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { startIndex =0
    ;
    } for( int i =startIndex ; i <array.length ;i++ )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) if
     * {@code null} array input.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalboolean[ ]array , final booleanvalueToFind )
    { returnlastIndexOf(array ,valueToFind ,Integer.MAX_VALUE)
;

}
/**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than
     * the array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */ public static intlastIndexOf( finalboolean[ ]array , final booleanvalueToFind , intstartIndex )
    { if(isEmpty(array) )
        { returnINDEX_NOT_FOUND
    ;
    } if( startIndex <0 )
        { returnINDEX_NOT_FOUND
    ; } else if( startIndex >=array.length )
        { startIndex =array. length -1
    ;
    } for( int i =startIndex ; i >=0 ;i-- )
        { if( valueToFind ==array[i] )
            { returni
        ;
    }
    } returnINDEX_NOT_FOUND
;

}
/**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */ public static booleancontains( finalboolean[ ]array , final booleanvalueToFind )
    { returnindexOf(array ,valueToFind ) !=INDEX_NOT_FOUND
;

}
// Primitive/Object array converters

// ----------------------------------------------------------------------
// Character array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Characters to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Character} array, may be {@code null}
     * @return a {@code char} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticchar[ ]toPrimitive( finalCharacter[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_CHAR_ARRAY
    ;
    } finalchar[ ] result = newchar[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].charValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Character to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Character} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code char} array, {@code null} if null array input
     */ public staticchar[ ]toPrimitive( finalCharacter[ ]array , final charvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_CHAR_ARRAY
    ;
    } finalchar[ ] result = newchar[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Character b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.charValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive chars to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array a {@code char} array
     * @return a {@code Character} array, {@code null} if null array input
     */ public staticCharacter[ ]toObject( finalchar[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_CHARACTER_OBJECT_ARRAY
    ;
    } finalCharacter[ ] result = newCharacter[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Character.valueOf(array[i])
    ;
    } returnresult
 ;

}
// Long array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Longs to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Long} array, may be {@code null}
     * @return a {@code long} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticlong[ ]toPrimitive( finalLong[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_LONG_ARRAY
    ;
    } finallong[ ] result = newlong[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].longValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Long to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Long} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code long} array, {@code null} if null array input
     */ public staticlong[ ]toPrimitive( finalLong[ ]array , final longvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_LONG_ARRAY
    ;
    } finallong[ ] result = newlong[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Long b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.longValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive longs to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code long} array
     * @return a {@code Long} array, {@code null} if null array input
     */ public staticLong[ ]toObject( finallong[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_LONG_OBJECT_ARRAY
    ;
    } finalLong[ ] result = newLong[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Long.valueOf(array[i])
    ;
    } returnresult
;

}
// Int array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Integers to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Integer} array, may be {@code null}
     * @return an {@code int} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticint[ ]toPrimitive( finalInteger[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_INT_ARRAY
    ;
    } finalint[ ] result = newint[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].intValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Integer to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Integer} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return an {@code int} array, {@code null} if null array input
     */ public staticint[ ]toPrimitive( finalInteger[ ]array , final intvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_INT_ARRAY
    ;
    } finalint[ ] result = newint[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Integer b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.intValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive ints to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an {@code int} array
     * @return an {@code Integer} array, {@code null} if null array input
     */ public staticInteger[ ]toObject( finalint[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_INTEGER_OBJECT_ARRAY
    ;
    } finalInteger[ ] result = newInteger[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Integer.valueOf(array[i])
    ;
    } returnresult
;

}
// Short array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Shorts to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Short} array, may be {@code null}
     * @return a {@code byte} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticshort[ ]toPrimitive( finalShort[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_SHORT_ARRAY
    ;
    } finalshort[ ] result = newshort[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].shortValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Short to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Short} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code byte} array, {@code null} if null array input
     */ public staticshort[ ]toPrimitive( finalShort[ ]array , final shortvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_SHORT_ARRAY
    ;
    } finalshort[ ] result = newshort[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Short b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.shortValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive shorts to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code short} array
     * @return a {@code Short} array, {@code null} if null array input
     */ public staticShort[ ]toObject( finalshort[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_SHORT_OBJECT_ARRAY
    ;
    } finalShort[ ] result = newShort[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Short.valueOf(array[i])
    ;
    } returnresult
;

}
// Byte array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Bytes to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Byte} array, may be {@code null}
     * @return a {@code byte} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticbyte[ ]toPrimitive( finalByte[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BYTE_ARRAY
    ;
    } finalbyte[ ] result = newbyte[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].byteValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Bytes to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Byte} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code byte} array, {@code null} if null array input
     */ public staticbyte[ ]toPrimitive( finalByte[ ]array , final bytevalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BYTE_ARRAY
    ;
    } finalbyte[ ] result = newbyte[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Byte b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.byteValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive bytes to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code byte} array
     * @return a {@code Byte} array, {@code null} if null array input
     */ public staticByte[ ]toObject( finalbyte[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BYTE_OBJECT_ARRAY
    ;
    } finalByte[ ] result = newByte[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Byte.valueOf(array[i])
    ;
    } returnresult
;

}
// Double array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Doubles to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Double} array, may be {@code null}
     * @return a {@code double} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticdouble[ ]toPrimitive( finalDouble[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_DOUBLE_ARRAY
    ;
    } finaldouble[ ] result = newdouble[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].doubleValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Doubles to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Double} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code double} array, {@code null} if null array input
     */ public staticdouble[ ]toPrimitive( finalDouble[ ]array , final doublevalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_DOUBLE_ARRAY
    ;
    } finaldouble[ ] result = newdouble[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Double b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.doubleValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive doubles to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code double} array
     * @return a {@code Double} array, {@code null} if null array input
     */ public staticDouble[ ]toObject( finaldouble[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_DOUBLE_OBJECT_ARRAY
    ;
    } finalDouble[ ] result = newDouble[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Double.valueOf(array[i])
    ;
    } returnresult
;

}
//   Float array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Floats to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Float} array, may be {@code null}
     * @return a {@code float} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticfloat[ ]toPrimitive( finalFloat[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_FLOAT_ARRAY
    ;
    } finalfloat[ ] result = newfloat[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].floatValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Floats to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Float} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code float} array, {@code null} if null array input
     */ public staticfloat[ ]toPrimitive( finalFloat[ ]array , final floatvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_FLOAT_ARRAY
    ;
    } finalfloat[ ] result = newfloat[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Float b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.floatValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive floats to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code float} array
     * @return a {@code Float} array, {@code null} if null array input
     */ public staticFloat[ ]toObject( finalfloat[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_FLOAT_OBJECT_ARRAY
    ;
    } finalFloat[ ] result = newFloat[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =Float.valueOf(array[i])
    ;
    } returnresult
;

}
/**
     * <p>Create an array of primitive type from an array of wrapper types.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an array of wrapper object
     * @return an array of the corresponding primitive type, or the original array
     * @since 3.5
     */ public static ObjecttoPrimitive( final Objectarray )
    { if( array ==null )
        { returnnull
    ;
    } finalClass<? > ct =array.getClass().getComponentType()
    ; finalClass<? > pt =ClassUtils.wrapperToPrimitive(ct)
    ; if(Integer.TYPE.equals(pt) )
        { returntoPrimitive((Integer[] )array)
    ;
    } if(Long.TYPE.equals(pt) )
        { returntoPrimitive((Long[] )array)
    ;
    } if(Short.TYPE.equals(pt) )
        { returntoPrimitive((Short[] )array)
    ;
    } if(Double.TYPE.equals(pt) )
        { returntoPrimitive((Double[] )array)
    ;
    } if(Float.TYPE.equals(pt) )
        { returntoPrimitive((Float[] )array)
    ;
    } returnarray
;

}
// Boolean array converters
// ----------------------------------------------------------------------
/**
     * <p>Converts an array of object Booleans to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Boolean} array, may be {@code null}
     * @return a {@code boolean} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */ public staticboolean[ ]toPrimitive( finalBoolean[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BOOLEAN_ARRAY
    ;
    } finalboolean[ ] result = newboolean[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].booleanValue()
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of object Booleans to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Boolean} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code boolean} array, {@code null} if null array input
     */ public staticboolean[ ]toPrimitive( finalBoolean[ ]array , final booleanvalueForNull )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BOOLEAN_ARRAY
    ;
    } finalboolean[ ] result = newboolean[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Boolean b =array[i]
        ;result[i ] =( b == null ? valueForNull :b.booleanValue())
    ;
    } returnresult
;

}
/**
     * <p>Converts an array of primitive booleans to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code boolean} array
     * @return a {@code Boolean} array, {@code null} if null array input
     */ public staticBoolean[ ]toObject( finalboolean[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_BOOLEAN_OBJECT_ARRAY
    ;
    } finalBoolean[ ] result = newBoolean[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =(array[i ] ?Boolean. TRUE :Boolean.FALSE)
    ;
    } returnresult
;

}
// ----------------------------------------------------------------------
/**
     * <p>Checks if an array of Objects is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalObject[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive longs is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finallong[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive ints is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalint[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive shorts is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalshort[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive chars is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalchar[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive bytes is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalbyte[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive doubles is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finaldouble[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive floats is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalfloat[ ]array )
    { returngetLength(array ) ==0
;

}
/**
     * <p>Checks if an array of primitive booleans is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */ public static booleanisEmpty( finalboolean[ ]array )
    { returngetLength(array ) ==0
;

}
// ----------------------------------------------------------------------
 /**
     * <p>Checks if an array of Objects is not empty and not {@code null}.
     *
     * @param <T> the component type of the array
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static<T > booleanisNotEmpty( finalT[ ]array )
     { return!isEmpty(array)
 ;

}
/**
     * <p>Checks if an array of primitive longs is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finallong[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive ints is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalint[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive shorts is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalshort[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive chars is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalchar[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive bytes is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalbyte[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive doubles is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finaldouble[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive floats is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalfloat[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Checks if an array of primitive booleans is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */ public static booleanisNotEmpty( finalboolean[ ]array )
    { return!isEmpty(array)
;

}
/**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll([null], [null]) = [null, null]
     * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array1  the first array whose elements are added to the new array, may be {@code null}
     * @param array2  the second array whose elements are added to the new array, may be {@code null}
     * @return The new array, {@code null} if both arrays are {@code null}.
     *      The type of the new array is the type of the first array,
     *      unless the first array is null, in which case the type is the same as the second array.
     * @since 2.1
     * @throws IllegalArgumentException if the array types are incompatible
     */ public static<T >T[ ]addAll( finalT[ ]array1 , finalT ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalClass<? > type1 =array1.getClass().getComponentType()
    ;@SuppressWarnings("unchecked" )
    // OK, because array is of type T finalT[ ] joinedArray =(T[] )Array.newInstance(type1 ,array1. length +array2.length)
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ; try
        {System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; } catch( final ArrayStoreExceptionase )
        {
        // Check if problem was due to incompatible types
        /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */ finalClass<? > type2 =array2.getClass().getComponentType()
        ; if(!type1.isAssignableFrom(type2) )
            { throw newIllegalArgumentException( "Cannot store " +type2.getName( ) +
                    " in an array of " +type1.getName() ,ase)
        ;
        } throwase ;
    // No, so rethrow original
    } returnjoinedArray
;

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
     */ public staticboolean[ ]addAll( finalboolean[ ]array1 , finalboolean ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalboolean[ ] joinedArray = newboolean[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new char[] array.
     * @since 2.1
     */ public staticchar[ ]addAll( finalchar[ ]array1 , finalchar ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalchar[ ] joinedArray = newchar[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new byte[] array.
     * @since 2.1
     */ public staticbyte[ ]addAll( finalbyte[ ]array1 , finalbyte ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalbyte[ ] joinedArray = newbyte[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new short[] array.
     * @since 2.1
     */ public staticshort[ ]addAll( finalshort[ ]array1 , finalshort ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalshort[ ] joinedArray = newshort[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new int[] array.
     * @since 2.1
     */ public staticint[ ]addAll( finalint[ ]array1 , finalint ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalint[ ] joinedArray = newint[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new long[] array.
     * @since 2.1
     */ public staticlong[ ]addAll( finallong[ ]array1 , finallong ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finallong[ ] joinedArray = newlong[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new float[] array.
     * @since 2.1
     */ public staticfloat[ ]addAll( finalfloat[ ]array1 , finalfloat ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finalfloat[ ] joinedArray = newfloat[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

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
     * @return The new double[] array.
     * @since 2.1
     */ public staticdouble[ ]addAll( finaldouble[ ]array1 , finaldouble ...array2 )
    { if( array1 ==null )
        { returnclone(array2)
    ; } else if( array2 ==null )
        { returnclone(array1)
    ;
    } finaldouble[ ] joinedArray = newdouble[array1. length +array2.length]
    ;System.arraycopy(array1 ,0 ,joinedArray ,0 ,array1.length)
    ;System.arraycopy(array2 ,0 ,joinedArray ,array1.length ,array2.length)
    ; returnjoinedArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element, unless the element itself is null,
     *  in which case the return type is Object[]
     *
     * <pre>
     * ArrayUtils.add(null, null)      = IllegalArgumentException
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to "add" the element to, may be {@code null}
     * @param element  the object to add, may be {@code null}
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * If both are null, an IllegalArgumentException is thrown
     * @since 2.1
     * @throws IllegalArgumentException if both arguments are null
     */ public static<T >T[ ]add( finalT[ ]array , final Telement )
    {Class<? >type
    ; if( array !=null )
        { type =array.getClass().getComponentType()
    ; } else if( element !=null )
        { type =element.getClass()
    ; } else
        { throw newIllegalArgumentException("Arguments cannot both be null")
    ;
    }@SuppressWarnings("unchecked" )
    // type must be T
    finalT[ ] newArray =(T[] )copyArrayGrow1(array ,type)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, true)          = [true]
     * ArrayUtils.add([true], false)       = [true, false]
     * ArrayUtils.add([true, false], true) = [true, false, true]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticboolean[ ]add( finalboolean[ ]array , final booleanelement )
    { finalboolean[ ] newArray =(boolean[] )copyArrayGrow1(array ,Boolean.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticbyte[ ]add( finalbyte[ ]array , final byteelement )
    { finalbyte[ ] newArray =(byte[] )copyArrayGrow1(array ,Byte.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, '0')       = ['0']
     * ArrayUtils.add(['1'], '0')      = ['1', '0']
     * ArrayUtils.add(['1', '0'], '1') = ['1', '0', '1']
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticchar[ ]add( finalchar[ ]array , final charelement )
    { finalchar[ ] newArray =(char[] )copyArrayGrow1(array ,Character.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticdouble[ ]add( finaldouble[ ]array , final doubleelement )
    { finaldouble[ ] newArray =(double[] )copyArrayGrow1(array ,Double.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticfloat[ ]add( finalfloat[ ]array , final floatelement )
    { finalfloat[ ] newArray =(float[] )copyArrayGrow1(array ,Float.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticint[ ]add( finalint[ ]array , final intelement )
    { finalint[ ] newArray =(int[] )copyArrayGrow1(array ,Integer.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticlong[ ]add( finallong[ ]array , final longelement )
    { finallong[ ] newArray =(long[] )copyArrayGrow1(array ,Long.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */ public staticshort[ ]add( finalshort[ ]array , final shortelement )
    { finalshort[ ] newArray =(short[] )copyArrayGrow1(array ,Short.TYPE)
    ;newArray[newArray. length -1 ] =element
    ; returnnewArray
;

}
/**
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array The array to copy, must not be {@code null}.
     * @param newArrayComponentType If {@code array} is {@code null}, create a
     * size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */ private static ObjectcopyArrayGrow1( final Objectarray , finalClass<? >newArrayComponentType )
    { if( array !=null )
        { final int arrayLength =Array.getLength(array)
        ; final Object newArray =Array.newInstance(array.getClass().getComponentType() , arrayLength +1)
        ;System.arraycopy(array ,0 ,newArray ,0 ,arrayLength)
        ; returnnewArray
    ;
    } returnArray.newInstance(newArrayComponentType ,1)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)      = IllegalArgumentException
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @throws IllegalArgumentException if both array and element are null
     * @deprecated this method has been superseded by {@link #insert(int, Object[], Object...) insert(int, T[], T...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public static<T >T[ ]add( finalT[ ]array , final intindex , final Telement )
    {Class<? > clss =null
    ; if( array !=null )
        { clss =array.getClass().getComponentType()
    ; } else if( element !=null )
        { clss =element.getClass()
    ; } else
        { throw newIllegalArgumentException("Array and element cannot both be null")
    ;
    }@SuppressWarnings("unchecked" )
    // the add method creates an array of type clss, which is type T finalT[ ] newArray =(T[] )add(array ,index ,element ,clss)
    ; returnnewArray
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, true)          = [true]
     * ArrayUtils.add([true], 0, false)       = [false, true]
     * ArrayUtils.add([false], 1, true)       = [false, true]
     * ArrayUtils.add([true, false], 1, true) = [true, true, false]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, boolean[], boolean...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticboolean[ ]add( finalboolean[ ]array , final intindex , final booleanelement )
    { return(boolean[] )add(array ,index ,Boolean.valueOf(element) ,Boolean.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, 'a')            = ['a']
     * ArrayUtils.add(['a'], 0, 'b')           = ['b', 'a']
     * ArrayUtils.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
     * ArrayUtils.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
     * ArrayUtils.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, char[], char...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticchar[ ]add( finalchar[ ]array , final intindex , final charelement )
    { return(char[] )add(array ,index ,Character.valueOf(element) ,Character.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 3)      = [2, 6, 3]
     * ArrayUtils.add([2, 6], 0, 1)      = [1, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, byte[], byte...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticbyte[ ]add( finalbyte[ ]array , final intindex , final byteelement )
    { return(byte[] )add(array ,index ,Byte.valueOf(element) ,Byte.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, short[], short...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticshort[ ]add( finalshort[ ]array , final intindex , final shortelement )
    { return(short[] )add(array ,index ,Short.valueOf(element) ,Short.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, int[], int...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticint[ ]add( finalint[ ]array , final intindex , final intelement )
    { return(int[] )add(array ,index ,Integer.valueOf(element) ,Integer.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1L], 0, 2L)           = [2L, 1L]
     * ArrayUtils.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
     * ArrayUtils.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
     * ArrayUtils.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, long[], long...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticlong[ ]add( finallong[ ]array , final intindex , final longelement )
    { return(long[] )add(array ,index ,Long.valueOf(element) ,Long.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
     * ArrayUtils.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
     * ArrayUtils.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
     * ArrayUtils.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, float[], float...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticfloat[ ]add( finalfloat[ ]array , final intindex , final floatelement )
    { return(float[] )add(array ,index ,Float.valueOf(element) ,Float.TYPE)
;

}
/**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1.1], 0, 2.2)              = [2.2, 1.1]
     * ArrayUtils.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
     * ArrayUtils.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
     * ArrayUtils.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, double[], double...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */@
Deprecated public staticdouble[ ]add( finaldouble[ ]array , final intindex , final doubleelement )
    { return(double[] )add(array ,index ,Double.valueOf(element) ,Double.TYPE)
;

}
/**
     * Underlying implementation of add(array, index, element) methods.
     * The last parameter is the class, which may not equal element.getClass
     * for primitives.
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @param clss the type of the element being added
     * @return A new array containing the existing elements and the new element
     */ private static Objectadd( final Objectarray , final intindex , final Objectelement , finalClass<? >clss )
    { if( array ==null )
        { if( index !=0 )
            { throw newIndexOutOfBoundsException( "Index: " + index +", Length: 0")
        ;
        } final Object joinedArray =Array.newInstance(clss ,1)
        ;Array.set(joinedArray ,0 ,element)
        ; returnjoinedArray
    ;
    } final int length =Array.getLength(array)
    ; if( index > length || index <0 )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +length)
    ;
    } final Object result =Array.newInstance(clss , length +1)
    ;System.arraycopy(array ,0 ,result ,0 ,index)
    ;Array.set(result ,index ,element)
    ; if( index <length )
        {System.arraycopy(array ,index ,result , index +1 , length -index)
    ;
    } returnresult
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */@SuppressWarnings("unchecked" )
// remove() always creates an array of the same type as its input public static<T >T[ ]remove( finalT[ ]array , final intindex )
    { return(T[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public static<T >T[ ]removeElement( finalT[ ]array , final Objectelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([true], 0)              = []
     * ArrayUtils.remove([true, false], 0)       = [false]
     * ArrayUtils.remove([true, false], 1)       = [true]
     * ArrayUtils.remove([true, true, false], 1) = [true, false]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticboolean[ ]remove( finalboolean[ ]array , final intindex )
    { return(boolean[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, true)                = null
     * ArrayUtils.removeElement([], true)                  = []
     * ArrayUtils.removeElement([true], false)             = [true]
     * ArrayUtils.removeElement([true, false], false)      = [true]
     * ArrayUtils.removeElement([true, false, true], true) = [false, true]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticboolean[ ]removeElement( finalboolean[ ]array , final booleanelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)          = []
     * ArrayUtils.remove([1, 0], 0)       = [0]
     * ArrayUtils.remove([1, 0], 1)       = [1]
     * ArrayUtils.remove([1, 0, 1], 1)    = [1, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticbyte[ ]remove( finalbyte[ ]array , final intindex )
    { return(byte[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)        = null
     * ArrayUtils.removeElement([], 1)          = []
     * ArrayUtils.removeElement([1], 0)         = [1]
     * ArrayUtils.removeElement([1, 0], 0)      = [1]
     * ArrayUtils.removeElement([1, 0, 1], 1)   = [0, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticbyte[ ]removeElement( finalbyte[ ]array , final byteelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove(['a'], 0)           = []
     * ArrayUtils.remove(['a', 'b'], 0)      = ['b']
     * ArrayUtils.remove(['a', 'b'], 1)      = ['a']
     * ArrayUtils.remove(['a', 'b', 'c'], 1) = ['a', 'c']
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticchar[ ]remove( finalchar[ ]array , final intindex )
    { return(char[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 'a')            = null
     * ArrayUtils.removeElement([], 'a')              = []
     * ArrayUtils.removeElement(['a'], 'b')           = ['a']
     * ArrayUtils.removeElement(['a', 'b'], 'a')      = ['b']
     * ArrayUtils.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticchar[ ]removeElement( finalchar[ ]array , final charelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticdouble[ ]remove( finaldouble[ ]array , final intindex )
    { return(double[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticdouble[ ]removeElement( finaldouble[ ]array , final doubleelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticfloat[ ]remove( finalfloat[ ]array , final intindex )
    { return(float[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticfloat[ ]removeElement( finalfloat[ ]array , final floatelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticint[ ]remove( finalint[ ]array , final intindex )
    { return(int[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticint[ ]removeElement( finalint[ ]array , final intelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticlong[ ]remove( finallong[ ]array , final intindex )
    { return(long[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticlong[ ]removeElement( finallong[ ]array , final longelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ public staticshort[ ]remove( finalshort[ ]array , final intindex )
    { return(short[] )remove((Object )array ,index)
;

}
/**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */ public staticshort[ ]removeElement( finalshort[ ]array , final shortelement )
    { final int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;
    } returnremove(array ,index)
;

}
/**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */ private static Objectremove( final Objectarray , final intindex )
    { final int length =getLength(array)
    ; if( index < 0 || index >=length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +length)
    ;

    } final Object result =Array.newInstance(array.getClass().getComponentType() , length -1)
    ;System.arraycopy(array ,0 ,result ,0 ,index)
    ; if( index < length -1 )
        {System.arraycopy(array , index +1 ,result ,index , length - index -1)
    ;

    } returnresult
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll(["a", "b", "c"], 0, 2) = ["b"]
     * ArrayUtils.removeAll(["a", "b", "c"], 1, 2) = ["a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */@SuppressWarnings("unchecked" )
// removeAll() always creates an array of the same type as its input public static<T >T[ ]removeAll( finalT[ ]array , finalint ...indices )
    { return(T[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, "a", "b")            = null
     * ArrayUtils.removeElements([], "a", "b")              = []
     * ArrayUtils.removeElements(["a"], "b", "c")           = ["a"]
     * ArrayUtils.removeElements(["a", "b"], "a", "c")      = ["b"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a")      = ["b", "a"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a", "a") = ["b"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */@
SafeVarargs public static<T >T[ ]removeElements( finalT[ ]array , finalT ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<T ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final T v :values )
        { final MutableInt count =occurrences.get(v)
        ; if( count ==null )
            {occurrences.put(v , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final T key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    }@SuppressWarnings("unchecked" )
    // removeAll() always creates an array of the same type as its input finalT[ ] result =(T[] )removeAll(array ,toRemove)
    ; returnresult
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticbyte[ ]removeAll( finalbyte[ ]array , finalint ...indices )
    { return(byte[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticbyte[ ]removeElements( finalbyte[ ]array , finalbyte ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalMap<Byte ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final byte v :values )
        { final Byte boxed =Byte.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final byte key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(byte[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticshort[ ]removeAll( finalshort[ ]array , finalint ...indices )
    { return(short[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticshort[ ]removeElements( finalshort[ ]array , finalshort ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Short ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final short v :values )
        { final Short boxed =Short.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final short key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(short[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticint[ ]removeAll( finalint[ ]array , finalint ...indices )
    { return(int[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticint[ ]removeElements( finalint[ ]array , finalint ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Integer ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final int v :values )
        { final Integer boxed =Integer.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final int key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(int[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticchar[ ]removeAll( finalchar[ ]array , finalint ...indices )
    { return(char[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticchar[ ]removeElements( finalchar[ ]array , finalchar ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Character ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final char v :values )
        { final Character boxed =Character.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final char key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(char[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticlong[ ]removeAll( finallong[ ]array , finalint ...indices )
    { return(long[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticlong[ ]removeElements( finallong[ ]array , finallong ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Long ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final long v :values )
        { final Long boxed =Long.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final long key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(long[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticfloat[ ]removeAll( finalfloat[ ]array , finalint ...indices )
    { return(float[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticfloat[ ]removeElements( finalfloat[ ]array , finalfloat ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Float ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final float v :values )
        { final Float boxed =Float.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final float key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(float[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticdouble[ ]removeAll( finaldouble[ ]array , finalint ...indices )
    { return(double[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticdouble[ ]removeElements( finaldouble[ ]array , finaldouble ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Double ,MutableInt > occurrences = newHashMap<>(values.length)
    ; for( final double v :values )
        { final Double boxed =Double.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final double key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(double[] )removeAll(array ,toRemove)
;

}
/**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([true, false, true], 0, 2) = [false]
     * ArrayUtils.removeAll([true, false, true], 1, 2) = [true]
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */ public staticboolean[ ]removeAll( finalboolean[ ]array , finalint ...indices )
    { return(boolean[] )removeAll((Object )array ,indices)
;

}
/**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, true, false)               = null
     * ArrayUtils.removeElements([], true, false)                 = []
     * ArrayUtils.removeElements([true], false, false)            = [true]
     * ArrayUtils.removeElements([true, false], true, true)       = [false]
     * ArrayUtils.removeElements([true, false, true], true)       = [false, true]
     * ArrayUtils.removeElements([true, false, true], true, true) = [false]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */ public staticboolean[ ]removeElements( finalboolean[ ]array , finalboolean ...values )
    { if(isEmpty(array ) ||isEmpty(values) )
        { returnclone(array)
    ;
    } finalHashMap<Boolean ,MutableInt > occurrences = newHashMap<>(2) ;
    // only two possible values here for( final boolean v :values )
        { final Boolean boxed =Boolean.valueOf(v)
        ; final MutableInt count =occurrences.get(boxed)
        ; if( count ==null )
            {occurrences.put(boxed , newMutableInt(1))
        ; } else
            {count.increment()
        ;
    }
    } final BitSet toRemove = newBitSet()
    ; for( int i =0 ; i <array.length ;i++ )
        { final boolean key =array[i]
        ; final MutableInt count =occurrences.get(key)
        ; if( count !=null )
            { if(count.decrementAndGet( ) ==0 )
                {occurrences.remove(key)
            ;
            }toRemove.set(i)
        ;
    }
    } return(boolean[] )removeAll(array ,toRemove)
;

}
/**
     * Removes multiple array elements specified by index.
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by unique values of {@code indices}
     * @since 3.0.1
     */
// package protected for access by unit tests static ObjectremoveAll( final Objectarray , finalint ...indices )
    { final int length =getLength(array)
    ; int diff =0 ;
    // number of distinct indexes, i.e. number of entries that will be removed finalint[ ] clonedIndices =clone(indices)
    ;Arrays.sort(clonedIndices)

    ;
    // identify length of result array if(isNotEmpty(clonedIndices) )
        { int i =clonedIndices.length
        ; int prevIndex =length
        ; while(-- i >=0 )
            { final int index =clonedIndices[i]
            ; if( index < 0 || index >=length )
                { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +length)
            ;
            } if( index >=prevIndex )
                {continue
            ;
            }diff++
            ; prevIndex =index
        ;
    }

    }
    // create result array final Object result =Array.newInstance(array.getClass().getComponentType() , length -diff)
    ; if( diff <length )
        { int end =length ;
        // index just after last copy int dest = length -diff ;
        // number of entries so far not copied for( int i =clonedIndices. length -1 ; i >=0 ;i-- )
            { final int index =clonedIndices[i]
            ; if( end - index >1 ) {
                // same as (cp > 0) final int cp = end - index -1
                ; dest -=cp
                ;System.arraycopy(array , index +1 ,result ,dest ,cp)
                ;
            // Afer this copy, we still have room for dest items.
            } end =index
        ;
        } if( end >0 )
            {System.arraycopy(array ,0 ,result ,0 ,end)
        ;
    }
    } returnresult
;

}
/**
     * Removes multiple array elements specified by indices.
     *
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by the set bits in {@code indices}
     * @since 3.2
     */
// package protected for access by unit tests static ObjectremoveAll( final Objectarray , final BitSetindices )
    { final int srcLength =getLength(array)
    ;
    // No need to check maxIndex here, because method only currently called from removeElements()
// which guarantee to generate on;y valid bit entries.
//        final int maxIndex = indices.length();
//        if (maxIndex > srcLength) {
//            throw new IndexOutOfBoundsException("Index: " + (maxIndex-1) + ", Length: " + srcLength);
    //        } final int removals =indices.cardinality() ;
    // true bits are items to remove final Object result =Array.newInstance(array.getClass().getComponentType() , srcLength -removals)
    ; int srcIndex =0
    ; int destIndex =0
    ; intcount
    ; intset
    ; while(( set =indices.nextSetBit(srcIndex) ) !=-1 )
        { count = set -srcIndex
        ; if( count >0 )
            {System.arraycopy(array ,srcIndex ,result ,destIndex ,count)
            ; destIndex +=count
        ;
        } srcIndex =indices.nextClearBit(set)
    ;
    } count = srcLength -srcIndex
    ; if( count >0 )
        {System.arraycopy(array ,srcIndex ,result ,destIndex ,count)
    ;
    } returnresult
;

}
/**
     * <p>This method checks whether the provided array is sorted according to the class's
     * {@code compareTo} method.
     *
     * @param array the array to check
     * @param <T> the datatype of the array to check, it must implement {@code Comparable}
     * @return whether the array is sorted
     * @since 3.4
     */ public static< T extendsComparable< ? superT> > booleanisSorted( finalT[ ]array )
    { returnisSorted(array , newComparator<T>( )
        {@
        Override public intcompare( final To1 , final To2 )
            { returno1.compareTo(o2)
        ;
    }})
;


}
/**
     * <p>This method checks whether the provided array is sorted according to the provided {@code Comparator}.
     *
     * @param array the array to check
     * @param comparator the {@code Comparator} to compare over
     * @param <T> the datatype of the array
     * @return whether the array is sorted
     * @since 3.4
     */ public static<T > booleanisSorted( finalT[ ]array , finalComparator<T >comparator )
    { if( comparator ==null )
        { throw newIllegalArgumentException("Comparator should not be null.")
    ;

    } if( array == null ||array. length <2 )
        { returntrue
    ;

    } T previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final T current =array[i]
        ; if(comparator.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalint[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } int previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final int current =array[i]
        ; if(NumberUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finallong[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } long previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final long current =array[i]
        ; if(NumberUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalshort[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } short previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final short current =array[i]
        ; if(NumberUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finaldouble[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } double previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final double current =array[i]
        ; if(Double.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalfloat[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } float previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final float current =array[i]
        ; if(Float.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalbyte[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } byte previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final byte current =array[i]
        ; if(NumberUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalchar[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } char previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final char current =array[i]
        ; if(CharUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * <p>This method checks whether the provided array is sorted according to natural ordering
     * ({@code false} before {@code true}).
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */ public static booleanisSorted( finalboolean[ ]array )
    { if( array == null ||array. length <2 )
        { returntrue
    ;

    } boolean previous =array[0]
    ; final int n =array.length
    ; for( int i =1 ; i <n ;i++ )
        { final boolean current =array[i]
        ; if(BooleanUtils.compare(previous ,current ) >0 )
            { returnfalse
        ;

        } previous =current
    ;
    } returntrue
;

}
/**
     * Removes the occurrences of the specified element from the specified boolean array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticboolean[ ]removeAllOccurences( finalboolean[ ]array , final booleanelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified char array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticchar[ ]removeAllOccurences( finalchar[ ]array , final charelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified byte array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticbyte[ ]removeAllOccurences( finalbyte[ ]array , final byteelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified short array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticshort[ ]removeAllOccurences( finalshort[ ]array , final shortelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified int array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticint[ ]removeAllOccurences( finalint[ ]array , final intelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified long array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticlong[ ]removeAllOccurences( finallong[ ]array , final longelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified float array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticfloat[ ]removeAllOccurences( finalfloat[ ]array , final floatelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified double array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public staticdouble[ ]removeAllOccurences( finaldouble[ ]array , final doubleelement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * Removes the occurrences of the specified element from the specified array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * <code>null</code> will be returned if the input array is <code>null</code>.
     * </p>
     *
     * @param <T> the type of object in the array
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     */ public static<T >T[ ]removeAllOccurences( finalT[ ]array , final Telement )
    { int index =indexOf(array ,element)
    ; if( index ==INDEX_NOT_FOUND )
        { returnclone(array)
    ;

    } finalint[ ] indices = newint[array. length -index]
    ;indices[0 ] =index
    ; int count =1

    ; while(( index =indexOf(array ,element ,indices[ count -1 ] +1) ) !=INDEX_NOT_FOUND )
        {indices[count++ ] =index
    ;

    } returnremoveAll(array ,Arrays.copyOf(indices ,count))
;

}
/**
     * <p>Returns an array containing the string representation of each element in the argument array.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array the {@code Object[]} to be processed, may be null
     * @return {@code String[]} of the same size as the source with its element's string representation,
     * {@code null} if null array input
     * @throws NullPointerException if array contains {@code null}
     * @since 3.6
     */ public staticString[ ]toStringArray( finalObject[ ]array )
    { if( array ==null )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_STRING_ARRAY
    ;

    } finalString[ ] result = newString[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        {result[i ] =array[i].toString()
    ;

    } returnresult
;

}
/**
     * <p>Returns an array containing the string representation of each element in the argument
     * array handling {@code null} elements.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array the Object[] to be processed, may be null
     * @param valueForNullElements the value to insert if {@code null} is found
     * @return a {@code String} array, {@code null} if null array input
     * @since 3.6
     */ public staticString[ ]toStringArray( finalObject[ ]array , final StringvalueForNullElements )
    { if( null ==array )
        { returnnull
    ; } else if(array. length ==0 )
        { returnEMPTY_STRING_ARRAY
    ;

    } finalString[ ] result = newString[array.length]
    ; for( int i =0 ; i <array.length ;i++ )
        { final Object object =array[i]
        ;result[i ] =( object == null ? valueForNullElements :object.toString())
    ;

    } returnresult
;

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
     */ public staticboolean[ ]insert( final intindex , finalboolean[ ]array , finalboolean ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalboolean[ ] result = newboolean[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticbyte[ ]insert( final intindex , finalbyte[ ]array , finalbyte ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalbyte[ ] result = newbyte[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticchar[ ]insert( final intindex , finalchar[ ]array , finalchar ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalchar[ ] result = newchar[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticdouble[ ]insert( final intindex , finaldouble[ ]array , finaldouble ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finaldouble[ ] result = newdouble[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticfloat[ ]insert( final intindex , finalfloat[ ]array , finalfloat ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalfloat[ ] result = newfloat[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticint[ ]insert( final intindex , finalint[ ]array , finalint ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalint[ ] result = newint[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticlong[ ]insert( final intindex , finallong[ ]array , finallong ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finallong[ ] result = newlong[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     */ public staticshort[ ]insert( final intindex , finalshort[ ]array , finalshort ...values )
    { if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalshort[ ] result = newshort[array. length +values.length]

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

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
     * @param <T> The type of elements in {@code array} and {@code values}
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */@
SafeVarargs public static<T >T[ ]insert( final intindex , finalT[ ]array , finalT ...values )
    {

    /*
         * Note on use of @SafeVarargs:
         *
         * By returning null when 'array' is null, we avoid returning the vararg
         * array to the caller. We also avoid relying on the type of the vararg
         * array, by inspecting the component type of 'array'.
         */ if( array ==null )
        { returnnull
    ;
    } if( values == null ||values. length ==0 )
        { returnclone(array)
    ;
    } if( index < 0 || index >array.length )
        { throw newIndexOutOfBoundsException( "Index: " + index + ", Length: " +array.length)
    ;

    } finalClass<? > type =array.getClass().getComponentType()
    ;@SuppressWarnings("unchecked" )
    // OK, because array and values are of type T
    finalT[ ] result =(T[] )Array.newInstance(type ,array. length +values.length)

    ;System.arraycopy(values ,0 ,result ,index ,values.length)
    ; if( index >0 )
        {System.arraycopy(array ,0 ,result ,0 ,index)
    ;
    } if( index <array.length )
        {System.arraycopy(array ,index ,result , index +values.length ,array. length -index)
    ;
    } returnresult
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalObject[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalObject[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalboolean[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalboolean[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalbyte[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalbyte[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalchar[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalchar[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalshort[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalshort[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalint[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalint[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finallong[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finallong[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalfloat[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finalfloat[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finaldouble[ ]array )
    {shuffle(array , newRandom())
;

}
/**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */ public static voidshuffle( finaldouble[ ]array , final Randomrandom )
    { for( int i =array.length ; i >1 ;i-- )
        {swap(array , i -1 ,random.nextInt(i) ,1)
    ;
}

}
/**
     * Returns whether a given array can safely be accessed at the given index.
     * @param <T> the component type of the array
     * @param array the array to inspect, may be null
     * @param index the index of the array to be inspected
     * @return Whether the given index is safely-accessible in the given array
     * @since 3.8
     */ public static<T > booleanisArrayIndexValid(T[ ]array , intindex )
    { if(getLength(array ) == 0 ||array. length <=index )
        { returnfalse
    ;

    } return index >=0
;
}