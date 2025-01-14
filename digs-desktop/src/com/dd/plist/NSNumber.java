/*
 * plist - An open source library to parse and generate property lists
 * Copyright (C) 2011 Daniel Dreibrodt, Keith Randall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dd.plist;

import java.io.IOException;

/**
 * A number whose value is either an integer, a real number or boolean.
 * @author Daniel Dreibrodt
 */
public class NSNumber extends NSObject {

    /**
     * Indicates that the number's value is an integer.
     * The number is stored as a Java <code>long</code>.
     * Its original value could have been char, short, int, long or even long long.
     **/
    public static final int INTEGER = 0;

    /**
     * Indicates that the number's value is a real number.
     * The number is stored as a Java <code>double</code>.
     * Its original value could have been float or double.
     **/
    public static final int REAL = 1;
    
    /** 
     * Indicates that the number's value is boolean.
     **/
    public static final int BOOLEAN = 2;

    private int type;

    private long longValue;
    private double doubleValue;
    private boolean boolValue;

    /**
     * Parses integers and real numbers from their binary representation.
     * <i>Note: real numbers are not yet supported.</i>
     * @param bytes The binary representation
     * @param type The type of number
     * @see #INTEGER
     * @see #REAL
     */
    public NSNumber(byte[] bytes, int type) {
        switch(type) {
            case INTEGER : {
                doubleValue = longValue = BinaryPropertyListParser.parseLong(bytes);
                break;
            }
            case REAL : {
                doubleValue = BinaryPropertyListParser.parseDouble(bytes);
		longValue = (long)doubleValue;
                break;
            }
            default : {
                throw new IllegalArgumentException("Type argument is not valid.");
            }
        }
        this.type = type;
    }

    /**
     * Creates a number from its textual representation.
     * @param text The textual representation of the number.
     * @see Boolean#parseBoolean(java.lang.String)
     * @see Long#parseLong(java.lang.String)
     * @see Double#parseDouble(java.lang.String)
     * @throws IllegalArgumentException If the text does not represent an integer, real number or boolean value.
     */
    public NSNumber(String text) {
        try {
            long l = Long.parseLong(text);
            doubleValue = longValue = l;
            type = INTEGER;
        } catch(Exception ex) {
            try {
                double d = Double.parseDouble(text);
                longValue = (long)(doubleValue = d);
                type = REAL;
            } catch(Exception ex2) {
                try {
                    boolValue = Boolean.parseBoolean(text);
                    type = BOOLEAN;
                    doubleValue = longValue = boolValue?1:0;
                } catch(Exception ex3) {
                    throw new IllegalArgumentException("Given text neither represents a double, int nor boolean value.");
                }
            }
        }
    }

    /**
     * Creates an integer number.
     * @param i The integer value.
     */
    public NSNumber(int i) {
        doubleValue = longValue = i;
        type = INTEGER;
    }

    /**
     * Creates a real number.
     * @param d The real value.
     */
    public NSNumber(double d) {
        longValue = (long) (doubleValue = d);
        type = REAL;
    }

    /**
     * Creates a boolean number.
     * @param b The boolean value.
     */
    public NSNumber(boolean b) {
        boolValue = b;
        doubleValue = longValue = b?1:0;
        type = BOOLEAN;
    }

    /**
     * Gets the type of this number's value.
     * @return The type flag.
     * @see #BOOLEAN
     * @see #INTEGER
     * @see #REAL
     */
    public int type() {
        return type;
    }

    /**
     * The number's boolean value.
     * @return <code>true</code> if the value is true or non-zero, false</code> otherwise.
     */
    public boolean boolValue() {
        if(type==BOOLEAN)
            return boolValue;
        else
            return longValue!=0;
    }

    /**
     * The number's long value.
     * @return The value of the number as long
     */
    public long longValue() {
        return longValue;
    }

    /**
     * The number's int value.
     * <i>Note: Even though the number's type might be INTEGER it can be larger than a Java int.
     * Use intValue() only if you are certain that it contains a number from the int range.
     * Otherwise the value might be innaccurate.</i>
     * @return The value of the number as int
     */
    public int intValue() {
        return (int)longValue;
    }

    /**
     * The number's double value.
     * @return The value of the number as double.
     */
    public double doubleValue() {
        return doubleValue;
    }

    /**
     * The number's float value.
     * WARNING: Possible loss of precision if the value is outside the float range.
     * @return The value of the number as float.
     */
    public float floatValue() {
        return (float)doubleValue;
    }

    /**
     * Checks whether the other object is a NSNumber of the same value.
     * @param obj The object to compare to.
     * @return Whether the objects are equal in terms of numeric value and type.
     */
    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof NSNumber)) return false;
	NSNumber n = (NSNumber) obj;
	return type == n.type && longValue == n.longValue && doubleValue == n.doubleValue && boolValue == n.boolValue;
    }

    @Override
    public int hashCode() {
        int hash = type;
        hash = 37 * hash + (int) (this.longValue ^ (this.longValue >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.doubleValue) ^ (Double.doubleToLongBits(this.doubleValue) >>> 32));
        hash = 37 * hash + (boolValue() ? 1 : 0);
        return hash;
    }


    @Override
    public String toString() {
        switch(type) {
            case INTEGER : {
                return String.valueOf(longValue());
            }
            case REAL : {
                return String.valueOf(doubleValue());
            }
            case BOOLEAN : {
                return String.valueOf(boolValue());
            }
            default : {
                return super.toString();
            }
        }
    }

    @Override
    public void toXML(StringBuilder xml, int level) {
        indent(xml, level);
        switch(type) {
            case INTEGER : {
                xml.append("<integer>");
                xml.append(longValue());
                xml.append("</integer>");
		break;
            }
            case REAL : {
                xml.append("<real>");
                xml.append(doubleValue());
                xml.append("</real>");
		break;
            } case BOOLEAN : {
                if(boolValue())
                    xml.append("<true/>");
                else
                    xml.append("<false/>");
		break;
            }
        }
    }
    
    void toBinary(BinaryPropertyListWriter out) throws IOException {
	switch (type()) {
	    case INTEGER : {
		if (longValue() < 0) {
			out.write(0x13);
			out.writeBytes(longValue(), 8);
		} else if (longValue() <= 0xff) {
			out.write(0x10);
			out.writeBytes(longValue(), 1);
		} else if (longValue() <= 0xffff) {
			out.write(0x11);
			out.writeBytes(longValue(), 2);
		} else if (longValue() <= 0xffffffffL) {
			out.write(0x12);
			out.writeBytes(longValue(), 4);
		} else {
			out.write(0x13);
			out.writeBytes(longValue(), 8);
		}
		break;
	    }
	    case REAL : {
		out.write(0x23);
		out.writeDouble(doubleValue());
		break;
	    }
	    case BOOLEAN : {
		out.write(boolValue() ? 0x09 : 0x08);
		break;
	    }
	}
    }
}
