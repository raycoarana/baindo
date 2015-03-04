/*
 * Copyright 2015 Rayco Araña
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.raycoarana.baindo.state;

import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;

import com.raycoarana.baindo.state.saver.BooleanArrayValueSaver;
import com.raycoarana.baindo.state.saver.BooleanValueSaver;
import com.raycoarana.baindo.state.saver.ByteArrayValueSaver;
import com.raycoarana.baindo.state.saver.ByteValueSaver;
import com.raycoarana.baindo.state.saver.CharSequenceArrayValueSaver;
import com.raycoarana.baindo.state.saver.CharSequenceValueSaver;
import com.raycoarana.baindo.state.saver.CharacterArrayValueSaver;
import com.raycoarana.baindo.state.saver.CharacterValueSaver;
import com.raycoarana.baindo.state.saver.DoubleArrayValueSaver;
import com.raycoarana.baindo.state.saver.DoubleValueSaver;
import com.raycoarana.baindo.state.saver.FloatArrayValueSaver;
import com.raycoarana.baindo.state.saver.FloatValueSaver;
import com.raycoarana.baindo.state.saver.IntegerArrayValueSaver;
import com.raycoarana.baindo.state.saver.IntegerValueSaver;
import com.raycoarana.baindo.state.saver.LongArrayValueSaver;
import com.raycoarana.baindo.state.saver.LongValueSaver;
import com.raycoarana.baindo.state.saver.ParcelableArrayValueSaver;
import com.raycoarana.baindo.state.saver.ParcelableValueSaver;
import com.raycoarana.baindo.state.saver.SerializableValueSaver;
import com.raycoarana.baindo.state.saver.ShortArrayValueSaver;
import com.raycoarana.baindo.state.saver.ShortValueSaver;
import com.raycoarana.baindo.state.saver.SizeFValueSaver;
import com.raycoarana.baindo.state.saver.SizeValueSaver;
import com.raycoarana.baindo.state.saver.StringArrayValueSaver;
import com.raycoarana.baindo.state.saver.StringValueSaver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ValueSaverFactory {

    private static ValueSaverFactory sInstance;

    private final Map<Class, ValueSaver> mTypeMap = new HashMap<>();

    private ValueSaverFactory() {
        mTypeMap.put(Byte.class, new ByteValueSaver());
        mTypeMap.put(byte[].class, new ByteArrayValueSaver());
        mTypeMap.put(Serializable.class, new SerializableValueSaver());
        mTypeMap.put(String.class, new StringValueSaver());
        mTypeMap.put(String[].class, new StringArrayValueSaver());
        mTypeMap.put(Float.class, new FloatValueSaver());
        mTypeMap.put(float[].class, new FloatArrayValueSaver());
        mTypeMap.put(Double.class, new DoubleValueSaver());
        mTypeMap.put(double[].class, new DoubleArrayValueSaver());
        mTypeMap.put(Short.class, new ShortValueSaver());
        mTypeMap.put(short[].class, new ShortArrayValueSaver());
        mTypeMap.put(Integer.class, new IntegerValueSaver());
        mTypeMap.put(int[].class, new IntegerArrayValueSaver());
        mTypeMap.put(Long.class, new LongValueSaver());
        mTypeMap.put(long[].class, new LongArrayValueSaver());
        mTypeMap.put(Boolean.class, new BooleanValueSaver());
        mTypeMap.put(boolean[].class, new BooleanArrayValueSaver());
        mTypeMap.put(Character.class, new CharacterValueSaver());
        mTypeMap.put(char[].class, new CharacterArrayValueSaver());
        mTypeMap.put(CharSequence.class, new CharSequenceValueSaver());
        mTypeMap.put(CharSequence[].class, new CharSequenceArrayValueSaver());
        mTypeMap.put(Parcelable.class, new ParcelableValueSaver());
        mTypeMap.put(Parcelable[].class, new ParcelableArrayValueSaver());
        mTypeMap.put(Size.class, new SizeValueSaver());
        mTypeMap.put(SizeF.class, new SizeFValueSaver());
    }

    public synchronized static ValueSaverFactory getInstance() {
        if (sInstance == null) {
            sInstance = new ValueSaverFactory();
        }
        return sInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> ValueSaver<T> build(T value) {
        Class<?> valueClass = value.getClass();
        ValueSaver<T> valueSaver = mTypeMap.get(valueClass);
        if (valueSaver == null) {
            if (value instanceof Parcelable) {
                valueSaver = mTypeMap.get(Parcelable.class);
            } else if (value instanceof Serializable) {
                valueSaver = mTypeMap.get(Serializable.class);
            } else if (value instanceof CharSequence) {
                valueSaver = mTypeMap.get(CharSequence.class);
            } else {
                throw new IllegalArgumentException("Not supported value class " + valueClass.getName());
            }
        }
        return valueSaver;
    }

}
