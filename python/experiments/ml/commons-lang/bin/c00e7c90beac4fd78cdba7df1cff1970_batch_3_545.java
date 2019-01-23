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
package org.apache.commons.lang3.mutable;

/**
 * A mutable <code>float</code> wrapper.
 * <p>
 * Note that as MutableFloat does not extend Float, it is not treated by String.format as a Float parameter.
 *
 * @see Float
 * @since 2.1
 */
public class MutableFloat extends Number implements Comparable<MutableFloat>, Mutable<Number> {

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID =5787169186L

    ;
    /** The mutable value. */ private floatvalue

    ;
    /**
     * Constructs a new MutableFloat with the default value of zero.
     */ publicMutableFloat( )
        {super()
    ;

    }
    /**
     * Constructs a new MutableFloat with the specified value.
     *
     * @param value  the initial value to store
     */ publicMutableFloat( final floatvalue )
        {super()
        ;this. value =value
    ;

    }
    /**
     * Constructs a new MutableFloat with the specified value.
     *
     * @param value  the initial value to store, not null
     * @throws NullPointerException if the object is null
     */ publicMutableFloat( final Numbervalue )
        {super()
        ;this. value =value.floatValue()
    ;

    }
    /**
     * Constructs a new MutableFloat parsing the given string.
     *
     * @param value  the string to parse, not null
     * @throws NumberFormatException if the string cannot be parsed into a float
     * @since 2.5
     */ publicMutableFloat( final Stringvalue )
        {super()
        ;this. value =Float.parseFloat(value)
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Gets the value as a Float instance.
     *
     * @return the value as a Float, never null
     */@
    Override public FloatgetValue( )
        { returnFloat.valueOf(this.value)
    ;

    }
    /**
     * Sets the value.
     *
     * @param value  the value to set
     */ public voidsetValue( final floatvalue )
        {this. value =value
    ;

    }
    /**
     * Sets the value from any Number instance.
     *
     * @param value  the value to set, not null
     * @throws NullPointerException if the object is null
     */@
    Override public voidsetValue( final Numbervalue )
        {this. value =value.floatValue()
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Checks whether the float value is the special NaN value.
     *
     * @return true if NaN
     */ public booleanisNaN( )
        { returnFloat.isNaN(value)
    ;

    }
    /**
     * Checks whether the float value is infinite.
     *
     * @return true if infinite
     */ public booleanisInfinite( )
        { returnFloat.isInfinite(value)
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Increments the value.
     *
     * @since 2.2
     */ public voidincrement( )
        {value++
    ;

    }
    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately prior to the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance before it was incremented
     * @since 3.5
     */ public floatgetAndIncrement( )
        { final float last =value
        ;value++
        ; returnlast
    ;

    }
    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately after the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance after it is incremented
     * @since 3.5
     */ public floatincrementAndGet( )
        {value++
        ; returnvalue
    ;

    }
    /**
     * Decrements the value.
     *
     * @since 2.2
     */ public voiddecrement( )
        {value--
    ;

    }
    /**
     * Decrements this instance's value by 1; this method returns the value associated with the instance
     * immediately prior to the decrement operation. This method is not thread safe.
     *
     * @return the value associated with the instance before it was decremented
     * @since 3.5
     */ public floatgetAndDecrement( )
        { final float last =value
        ;value--
        ; returnlast
    ;

    }
    /**
     * Decrements this instance's value by 1; this method returns the value associated with the instance
     * immediately after the decrement operation. This method is not thread safe.
     *
     * @return the value associated with the instance after it is decremented
     * @since 3.5
     */ public floatdecrementAndGet( )
        {value--
        ; returnvalue
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Adds a value to the value of this instance.
     *
     * @param operand  the value to add, not null
     * @since 2.2
     */ public voidadd( final floatoperand )
        {this. value +=operand
    ;

    }
    /**
     * Adds a value to the value of this instance.
     *
     * @param operand  the value to add, not null
     * @throws NullPointerException if the object is null
     * @since 2.2
     */ public voidadd( final Numberoperand )
        {this. value +=operand.floatValue()
    ;

    }
    /**
     * Subtracts a value from the value of this instance.
     *
     * @param operand  the value to subtract
     * @since 2.2
     */ public voidsubtract( final floatoperand )
        {this. value -=operand
    ;

    }
    /**
     * Subtracts a value from the value of this instance.
     *
     * @param operand  the value to subtract, not null
     * @throws NullPointerException if the object is null
     * @since 2.2
     */ public voidsubtract( final Numberoperand )
        {this. value -=operand.floatValue()
    ;

    }
    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately after the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @return the value associated with this instance after adding the operand
     * @since 3.5
     */ public floataddAndGet( final floatoperand )
        {this. value +=operand
        ; returnvalue
    ;

    }
    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately after the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @throws NullPointerException if {@code operand} is null
     * @return the value associated with this instance after adding the operand
     * @since 3.5
     */ public floataddAndGet( final Numberoperand )
        {this. value +=operand.floatValue()
        ; returnvalue
    ;

    }
    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately prior to the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @return the value associated with this instance immediately before the operand was added
     * @since 3.5
     */ public floatgetAndAdd( final floatoperand )
        { final float last =value
        ;this. value +=operand
        ; returnlast
    ;

    }
    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately prior to the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @throws NullPointerException if {@code operand} is null
     * @return the value associated with this instance immediately before the operand was added
     * @since 3.5
     */ public floatgetAndAdd( final Numberoperand )
        { final float last =value
        ;this. value +=operand.floatValue()
        ; returnlast
    ;

    }
    //-----------------------------------------------------------------------
    // shortValue and byteValue rely on Number implementation
    /**
     * Returns the value of this MutableFloat as an int.
     *
     * @return the numeric value represented by this object after conversion to type int.
     */@
    Override public intintValue( )
        { return(int )value
    ;

    }
    /**
     * Returns the value of this MutableFloat as a long.
     *
     * @return the numeric value represented by this object after conversion to type long.
     */@
    Override public longlongValue( )
        { return(long )value
    ;

    }
    /**
     * Returns the value of this MutableFloat as a float.
     *
     * @return the numeric value represented by this object after conversion to type float.
     */@
    Override public floatfloatValue( )
        { returnvalue
    ;

    }
    /**
     * Returns the value of this MutableFloat as a double.
     *
     * @return the numeric value represented by this object after conversion to type double.
     */@
    Override public doubledoubleValue( )
        { returnvalue
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Gets this mutable as an instance of Float.
     *
     * @return a Float instance containing the value from this mutable, never null
     */ public FloattoFloat( )
        { returnFloat.valueOf(floatValue())
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Compares this object against some other object. The result is <code>true</code> if and only if the argument is
     * not <code>null</code> and is a <code>Float</code> object that represents a <code>float</code> that has the
     * identical bit pattern to the bit pattern of the <code>float</code> represented by this object. For this
     * purpose, two float values are considered to be the same if and only if the method
     * {@link Float#floatToIntBits(float)}returns the same int value when applied to each.
     * <p>
     * Note that in most cases, for two instances of class <code>Float</code>,<code>f1</code> and <code>f2</code>,
     * the value of <code>f1.equals(f2)</code> is <code>true</code> if and only if <blockquote>
     *
     * <pre>
     *   f1.floatValue() == f2.floatValue()
     * </pre>
     *
     * </blockquote>
     * <p>
     * also has the value <code>true</code>. However, there are two exceptions:
     * <ul>
     * <li>If <code>f1</code> and <code>f2</code> both represent <code>Float.NaN</code>, then the
     * <code>equals</code> method returns <code>true</code>, even though <code>Float.NaN==Float.NaN</code> has
     * the value <code>false</code>.
     * <li>If <code>f1</code> represents <code>+0.0f</code> while <code>f2</code> represents <code>-0.0f</code>,
     * or vice versa, the <code>equal</code> test has the value <code>false</code>, even though
     * <code>0.0f==-0.0f</code> has the value <code>true</code>.
     * </ul>
     * This definition allows hashtables to operate properly.
     *
     * @param obj  the object to compare with, null returns false
     * @return <code>true</code> if the objects are the same; <code>false</code> otherwise.
     * @see java.lang.Float#floatToIntBits(float)
     */@
    Override public booleanequals( final Objectobj )
        { return obj instanceof
            MutableFloat &&Float.floatToIntBits(((MutableFloat )obj).value ) ==Float.floatToIntBits(value)
    ;

    }
    /**
     * Returns a suitable hash code for this mutable.
     *
     * @return a suitable hash code
     */@
    Override public inthashCode( )
        { returnFloat.floatToIntBits(value)
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Compares this mutable to another in ascending order.
     *
     * @param other  the other mutable to compare to, not null
     * @return negative if this is less, zero if equal, positive if greater
     */@
    Override public intcompareTo( final MutableFloatother )
        { returnFloat.compare(this.value ,other.value)
    ;

    }
    //-----------------------------------------------------------------------
    /**
     * Returns the String value of this mutable.
     *
     * @return the mutable value as a string
     */@
    Override public StringtoString( )
        { returnString.valueOf(value)
    ;

}