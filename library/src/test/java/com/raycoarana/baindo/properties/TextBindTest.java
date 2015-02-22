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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.raycoarana.baindo.observables.AbstractProperty;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TextBindTest extends AbstractObservableBindTest<CharSequence> {

    private static final String DEFAULT_VALUE = "default_value";
    private static final String NEW_VALUE = "new_value";

    @Captor
    private ArgumentCaptor<TextWatcher> mTextWatcherArgumentCaptor;

    public TextBindTest() {
        super(DEFAULT_VALUE, NEW_VALUE);
    }

    @Override
    protected BaseObservableBind<AbstractProperty<CharSequence>> getBind() {
        return new TextBind(mBindableSource, mWorkDispatcher);
    }

    @Override
    protected void givenACompatibleView() {
        mView = mock(TextView.class);
    }

    @Override
    protected void whenViewChanged() {
        verify(((TextView) mView), atMost(2)).addTextChangedListener(mTextWatcherArgumentCaptor.capture());
        if (mTextWatcherArgumentCaptor.getAllValues().size() > 0) {
            TextWatcher listener = mTextWatcherArgumentCaptor.getValue();
            if (listener != null) {
                Editable editable = mock(Editable.class);
                when(editable.toString()).thenReturn(NEW_VALUE);
                listener.afterTextChanged(editable);
            }
        }
    }

    @Override
    protected void verifyViewChanged(CharSequence value) {
        verify(((TextView) mView)).setText(value);
    }

    @Override
    protected void verifyViewNotChanged(CharSequence value) {
        verify(((TextView) mView), never()).setText(value);
    }

}
