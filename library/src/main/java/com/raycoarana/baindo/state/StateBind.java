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

import android.os.Bundle;

import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.binding.FinalBindTarget;
import com.raycoarana.baindo.observables.AbstractProperty;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class StateBind<T> implements FinalBindTarget<AbstractProperty<T>> {

    private final WorkDispatcher mWorkDispatcher;
    private AbstractProperty<T> mTarget;
    private final String mKey;
    private ValueSaver<T> mValueSaver;

    public StateBind(WorkDispatcher workDispatcher, String key) {
        mWorkDispatcher = workDispatcher;
        mKey = key;
    }

    @Override
    public void to(AbstractProperty<T> target) {
        mTarget = target;
        tryBuildValueSaver();
    }

    private void tryBuildValueSaver() {
        if(mValueSaver == null) {
            T value = mTarget.getValue();
            if (value != null) {
                mValueSaver = ValueSaverFactory.getInstance().build(value);
            }
        }
    }

    public void onUpdate(final Bundle state) {
        mWorkDispatcher.doInBackgroundThread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                mTarget.setValue((T) state.get(mKey));
            }
        });
    }

    public void onSave(Bundle state) {
        T value = mTarget.getValue();
        tryBuildValueSaver();
        if(mValueSaver != null) {
            mValueSaver.save(state, mKey, value);
        }
    }

}
