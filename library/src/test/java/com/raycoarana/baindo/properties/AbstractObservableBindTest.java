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

import com.raycoarana.baindo.observables.AbstractProperty;

import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public abstract class AbstractObservableBindTest<T> extends AbstractBindTest<T, BaseObservableBind<AbstractProperty<T>>> {

    public AbstractObservableBindTest(T defaultValue, T newValue, boolean writeSupported) {
        super(defaultValue, newValue, writeSupported);
    }

    public AbstractObservableBindTest(T defaultValue, T newValue) {
        super(defaultValue, newValue, true);
    }

    @Test
    public void shouldUpdatePropertyWhenViewChanges() {
        givenABaseBind();
        givenACompatibleView();
        givenThatBindIsBindedAsReadWrite();
        whenViewChanged();
        thenPropertyIsChanged();
    }

    @Test
    public void shouldNotUpdatePropertyOnceUnbinded() {
        givenABaseBind();
        givenACompatibleView();
        givenThatBindIsBindedAsReadWrite();
        givenThatIsUnbinded();
        whenViewChanged();
        thenPropertyIsNotChanged();
    }

    @Test
    public void shouldNotUpdatePropertyIfReadOnly() {
        givenABaseBind();
        givenACompatibleView();
        givenThatBindIsBindedAsReadOnly();
        whenViewChanged();
        thenPropertyIsNotChanged();
    }

    protected abstract void whenViewChanged();

    private void thenPropertyIsChanged() {
        verify(mProperty).setValue(mNewValue, mBaseBind);
    }

    private void thenPropertyIsNotChanged() {
        verify(mProperty, never()).setValue(mNewValue, mBaseBind);
    }

}
