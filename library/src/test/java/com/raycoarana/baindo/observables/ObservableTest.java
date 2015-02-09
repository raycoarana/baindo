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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ObservableTest extends UnitTestSuite {

    private static final int ZERO = 0;
    private static final int ONE = 1;

    @Mock
    private Observer mOtherObserver;

    @Mock
    private Observer mObserver;

    private Observable mObservable;
    private int mCountObservers;

    @Test
    public void shouldCountObservers() {
        givenAnObservable();
        givenThatAnObserverIsAddedToTheObservable();
        whenCountObservers();
        thenOneObserverIsReturned();
    }

    @Test
    public void shouldDeleteObservers() {
        givenAnObservable();
        givenThatAnObserverIsAddedToTheObservable();
        whenDeleteObservers();
        thenZeroObserverIsReturned();
    }

    @Test
    public void shouldDeleteAnObserver() {
        givenAnObservable();
        givenThatAnObserverIsAddedToTheObservable();
        givenThatOtherObserverIsAddedToTheProperty();
        whenDeleteObserverOneObserverAndGetObserversCount();
        thenOneObserverIsReturned();
    }

    @Test
    public void shouldNotifyAllObserversIfChanged() {
        givenAnObservableThatHasChanged();
        givenThatAnObserverIsAddedToTheObservable();
        whenNotifyObservers();
        thenObserverIsNotified();
    }

    @Test
    public void shouldNoNotifyAnyObservers() {
        givenAnObservable();
        givenThatAnObserverIsAddedToTheObservable();
        whenNotifyObservers();
        thenNoObserverIsNotified();
    }

    @Test
    public void shouldNotifyAllObserversWithData() {
        givenAnObservableThatHasChanged();
        givenThatAnObserverIsAddedToTheObservable();
        whenNotifyObserversWithData();
        thenObserverIsNotified();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotLetAddANullObserver() {
        givenAnObservable();
        givenThatANullObserverIsAddedToTheObservable();
    }

    @Test
    public void shouldNotAddTwoTimesTheSameObserver() {
        givenAnObservable();
        givenThatAnObserverIsAddedToTheObservable();
        givenThatAnObserverIsAddedToTheObservable();
        whenCountObservers();
        thenOneObserverIsReturned();
    }

    private void givenThatANullObserverIsAddedToTheObservable() {
        mObservable.addObserver(null);
    }

    private void givenAnObservableThatHasChanged() {
        mObservable = new Observable(){{
            setChanged();
        }};
    }

    private void givenThatAnObserverIsAddedToTheObservable() {
        mObservable.addObserver(mObserver);
    }

    private void givenAnObservable() {
        mObservable = new Property<>();
    }

    private void givenThatOtherObserverIsAddedToTheProperty() {
        mObservable.addObserver(mOtherObserver);
    }

    private void whenCountObservers() {
        mCountObservers = mObservable.countObservers();
    }

    private void whenDeleteObservers() {
        mObservable.deleteObservers();
    }

    private void whenDeleteObserverOneObserverAndGetObserversCount() {
        mObservable.deleteObserver(mObserver);
        mCountObservers = mObservable.countObservers();
    }

    private void whenNotifyObservers() {
        mObservable.notifyObservers();
    }

    private void whenNotifyObserversWithData() {
        mObservable.notifyObservers((Object) null);
    }

    private void thenZeroObserverIsReturned() {
        assertThat(mObservable.countObservers(), is(ZERO));
    }

    private void thenOneObserverIsReturned() {
        assertThat(mCountObservers, is(ONE));
    }

    private void thenObserverIsNotified() {
        verify(mObserver).update(eq(mObservable), any());
    }

    private void thenNoObserverIsNotified() {
        verify(mObserver, never()).update(eq(mObservable), any());
    }

}
