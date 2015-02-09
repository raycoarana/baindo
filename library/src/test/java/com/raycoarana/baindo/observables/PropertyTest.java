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

package com.raycoarana.baindo.observables;

import com.raycoarana.baindo.test.UnitTestSuite;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Observer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class PropertyTest extends UnitTestSuite {

    @Mock
    private Object mSomeValue;

    @Mock
    private Observer mObserver;

    private Property<Object> mProperty;

    @Test
    public void shouldSaveValue() {
        givenAProperty();
        whenSetSomeValue();
        thenTheValueCanBeRetrieved();
    }

    @Test
    public void shouldRetrievedDefaultValue() {
        givenAPropertyWithDefaultValue();
        whenSetSomeValue();
        thenTheValueCanBeRetrieved();
    }

    @Test
    public void shouldNotifyWhenChange() {
        givenAProperty();
        givenThatAnObserverIsAddedToTheProperty();
        whenSetSomeValue();
        thenObserverIsNotifiedOfTheChanged();
    }

    @Test
    public void shouldNotifyNotWhenChangeWhenIsTheSender() {
        givenAProperty();
        givenThatAnObserverIsAddedToTheProperty();
        whenSetSomeValueOmitingSender();
        thenObserverIsNotifiedOfTheChanged();
    }

    private void givenThatAnObserverIsAddedToTheProperty() {
        mProperty.addObserver(mObserver);
    }

    private void givenAProperty() {
        mProperty = new Property<>();
    }

    private void givenAPropertyWithDefaultValue() {
        mProperty = new Property<>(mSomeValue);
    }

    private void whenSetSomeValue() {
        mProperty.setValue(mSomeValue);
    }

    private void whenSetSomeValueOmitingSender() {
        mProperty.setValue(mSomeValue, mObserver);
    }

    private void thenTheValueCanBeRetrieved() {
        assertThat(mProperty.getValue(), is(mSomeValue));
    }

    private void thenObserverIsNotifiedOfTheChanged() {
        mObserver.update(eq(mProperty), any());
    }

}
