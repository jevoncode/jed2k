package org.dkf.jed2k.protocol;

import org.dkf.jed2k.exception.ErrorCode;
import org.dkf.jed2k.exception.JED2KException;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.dkf.jed2k.Utils.sizeof;

public class UInt16 extends UNumber implements Comparable<UInt16>{

    private static final long serialVersionUID = -6821055240959745390L;

    /**
     * A constant holding the minimum value an <code>unsigned short</code> can
     * have, 0.
     */
    public static final int   MIN_VALUE        = 0x0000;

    /**
     * A constant holding the maximum value an <code>unsigned short</code> can
     * have, 2<sup>16</sup>-1.
     */
    public static final int   MAX_VALUE        = 0xffff;

    private short value;

    public UInt16(){
        value = 0;
    }

    public UInt16(byte value){
        this.value = (short)value;
    }

    public UInt16(short value){
        this.value = value;
    }

    public UInt16(int value){
        this.value = (short)value;
    }

    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        assert(src.order() == ByteOrder.LITTLE_ENDIAN);
        try {
            value = src.getShort();
        } catch(BufferUnderflowException e) {
            throw new JED2KException(ErrorCode.BUFFER_UNDERFLOW_EXCEPTION);
        } catch(Exception e) {
            throw new JED2KException(ErrorCode.BUFFER_GET_EXCEPTION);
        }
        return src;
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        assert(dst.order() == ByteOrder.LITTLE_ENDIAN);
        return dst.putShort(value);
    }

    @Override
    public double doubleValue() {
        return value & MAX_VALUE;
    }

    @Override
    public float floatValue() {
        return value & MAX_VALUE;
    }

    @Override
    public int intValue() {
        return value & MAX_VALUE;
    }

    @Override
    public long longValue() {
        return value & MAX_VALUE;
    }

    @Override
    public UInt16 assign(short value){
        this.value = value;
        return this;
    }

    @Override
    public int compareTo(UInt16 o) {
        if (intValue() < o.intValue()) return -1;
        if (intValue() > o.intValue()) return 1;
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof UInt16) {
            return value == ((UInt16) obj).value;
        }

        return false;
    }

    @Override
    public UNumber assign(byte value) {
        this.value = value;
        return this;
    }

    @Override
    public UNumber assign(int value) {
        this.value = (short)value;
        return this;
    }

    @Override
    public UNumber assign(long value) {
        this.value = (short)value;
        return this;
    }

    @Override
    public int bytesCount() {
        return sizeof(value);
    }

    @Override
    public String toString() {
        return "uint16{" + intValue() + "}";
    }

}
