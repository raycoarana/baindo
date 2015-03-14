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

package com.raycoarana.baindo.properties;

import android.widget.AdapterView;
import android.widget.SeekBar;

import com.raycoarana.baindo.observables.AbstractProperty;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SelectedIndexBindTest extends AbstractObservableBindTest<Integer> {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer NEW_VALUE = 42;

    @Captor
    private ArgumentCaptor<AdapterView.OnItemSelectedListener> mOnItemSelectedListenerArgumentCaptor;

    public SelectedIndexBindTest() {
        super(DEFAULT_VALUE, NEW_VALUE);
    }

    @Override
    protected BaseObservableBind<Integer, AbstractProperty<Integer>> getBind() {
        return new SelectedIndexBind(mBindableSource, mWorkDispatcher);
    }

    @Override
    protected void givenACompatibleView() {
        mView = mock(AdapterView.class);
    }

    @Override
    protected void whenViewChanged() {
        verify(((AdapterView) mView), atMost(2)).setOnItemSelectedListener(mOnItemSelectedListenerArgumentCaptor.capture());
        if (mOnItemSelectedListenerArgumentCaptor.getAllValues().size() > 0) {
            AdapterView.OnItemSelectedListener listener = mOnItemSelectedListenerArgumentCaptor.getValue();
            if (listener != null) {
                listener.onItemSelected((AdapterView) mView, null, NEW_VALUE, -1);
            }
        }
    }

    @Override
    protected void verifyViewChanged(Integer value) {
        verify(((AdapterView) mView)).setSelection(value);
    }

    @Override
    protected void verifyViewNotChanged(Integer value) {
        verify(((AdapterView) mView), never()).setSelection(value);
    }

}
