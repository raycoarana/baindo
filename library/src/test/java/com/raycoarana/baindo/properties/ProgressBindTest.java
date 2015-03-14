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

import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.raycoarana.baindo.observables.AbstractProperty;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ProgressBindTest extends AbstractObservableBindTest<Integer> {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer NEW_VALUE = 42;

    @Captor
    private ArgumentCaptor<SeekBar.OnSeekBarChangeListener> mOnSeekBarChangeListenerArgumentCaptor;

    public ProgressBindTest() {
        super(DEFAULT_VALUE, NEW_VALUE);
    }


    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotSeekBarAndBindIsWrite() {
        givenABaseBind();
        givenAProgressBarView();
        givenThatBindIsBindedAsWriteOnly();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailIfViewIsNotSeekBarAndBindIsReadWrite() {
        givenABaseBind();
        givenAProgressBarView();
        givenThatBindIsBindedAsReadWrite();
        whenPropertyUpdated();
        thenViewIsNotChanged();
    }

    private void givenAProgressBarView() {
        mView = mock(ProgressBar.class);
    }

    @Override
    protected BaseObservableBind<Integer, AbstractProperty<Integer>> getBind() {
        return new ProgressBind(mBindableSource, mWorkDispatcher);
    }

    @Override
    protected void givenACompatibleView() {
        mView = mock(SeekBar.class);
    }

    @Override
    protected void whenViewChanged() {
        verify(((SeekBar) mView), atMost(2)).setOnSeekBarChangeListener(mOnSeekBarChangeListenerArgumentCaptor.capture());
        if (mOnSeekBarChangeListenerArgumentCaptor.getAllValues().size() > 0) {
            SeekBar.OnSeekBarChangeListener listener = mOnSeekBarChangeListenerArgumentCaptor.getValue();
            if (listener != null) {
                listener.onProgressChanged((SeekBar) mView, NEW_VALUE, true);
            }
        }
    }

    @Override
    protected void verifyViewChanged(Integer value) {
        verify(((SeekBar) mView)).setProgress(value);
    }

    @Override
    protected void verifyViewNotChanged(Integer value) {
        verify(((SeekBar) mView), never()).setProgress(value);
    }

}
