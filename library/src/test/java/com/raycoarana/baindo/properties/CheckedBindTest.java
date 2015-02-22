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

import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;

import com.raycoarana.baindo.observables.AbstractProperty;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CheckedBindTest extends AbstractObservableBindTest<Boolean> {

    private static final boolean NEW_VALUE = true;
    private static final boolean DEFAULT_VALUE = false;

    @Captor
    private ArgumentCaptor<CompoundButton.OnCheckedChangeListener> mOnCheckedChangeListenerArgumentCaptor;

    public CheckedBindTest() {
        super(DEFAULT_VALUE, NEW_VALUE);
    }

    @Test
    public void shouldNotTryToUnbindViewWhenNotACompoundButton() {
        givenABaseBind();
        givenACheckedView();
        givenThatBindIsBindedAsReadOnly();
        whenUnbind();
        //Should not fail
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotCompoundButtonAndBindIsWrite() {
        givenABaseBind();
        givenACheckedView();
        givenThatBindIsBindedAsWriteOnly();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotCompoundButtonAndBindIsReadWrite() {
        givenABaseBind();
        givenACheckedView();
        givenThatBindIsBindedAsReadWrite();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    private void whenUnbind() {
        givenThatIsUnbinded();
    }

    @Override
    protected BaseObservableBind<AbstractProperty<Boolean>> getBind() {
        return new CheckedBind(mBindableSource, mWorkDispatcher);
    }

    private void givenACheckedView() {
        mView = mock(CheckedTextView.class);
    }

    @Override
    protected void givenACompatibleView() {
        mView = mock(CheckBox.class);
    }

    @Override
    protected void whenViewChanged() {
        verify(((CompoundButton) mView), atMost(2)).setOnCheckedChangeListener(mOnCheckedChangeListenerArgumentCaptor.capture());
        if (mOnCheckedChangeListenerArgumentCaptor.getAllValues().size() > 0) {
            CompoundButton.OnCheckedChangeListener listener = mOnCheckedChangeListenerArgumentCaptor.getValue();
            if (listener != null) {
                listener.onCheckedChanged((CompoundButton) mView, NEW_VALUE);
            }
        }
    }

    @Override
    protected void verifyViewChanged(Boolean value) {
        verify(((CheckBox) mView)).setChecked(value);
    }

    @Override
    protected void verifyViewNotChanged(Boolean value) {
        verify(((CheckBox) mView), never()).setChecked(value);
    }

}
