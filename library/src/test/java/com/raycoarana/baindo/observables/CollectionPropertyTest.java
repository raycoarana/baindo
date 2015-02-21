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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CollectionPropertyTest extends UnitTestSuite {

    private static final int ZERO = 0;
    private static final int SOME_POSITION = 5;
    private static final int SOME_POSITION_2 = 7;
    private static final Object[] SOME_TYPED_ARRAY = new Object[0];

    @Mock
    private Observer mObserver;

    @Mock
    private Object mSomeItem1;

    @Mock
    private Object mSomeItem2;

    @Mock
    private List<Object> mInnerList;

    @Mock
    private List<Object> mNewInnerList;

    private CollectionProperty<Object> mCollectionProperty;
    private List<Object> mSomeListOfItems;
    private int mSize;

    @Test
    public void shouldCallAddOfInnerListAndNotifyChanges() {
        givenACollectionProperty();
        givenAnObserver();
        whenAdd();
        thenAnItemIsAdded();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallAddAtPositionOfInnerListAndNotifyChanges() {
        givenACollectionProperty();
        givenAnObserver();
        whenAddAtPosition();
        thenAnItemIsAddedAtPosition();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallAddAllOfInnerListAndNotifyChanges() {
        givenACollectionProperty();
        givenAnObserver();
        givenAListOfItems();
        whenAddAll();
        thenAllItemsAreAdded();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallAddAllAtPositionOfInnerListAndNotifyChanges() {
        givenACollectionProperty();
        givenAnObserver();
        givenAListOfItems();
        whenAddAllAtPosition();
        thenAllItemsAreAddedAtPosition();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallClearOfInnerListAndNotifyChange() {
        givenACollectionProperty();
        givenAnObserver();
        whenClear();
        thenInnerListClearMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallContainsOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenContains();
        thenInnerListContainsMethodIsCalled();
    }

    @Test
    public void shouldCallContainsAllOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        givenAListOfItems();
        whenContainsAll();
        thenInnerListContainsAllMethodIsCalled();
    }

    @Test
    public void shouldCallGetOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenGet();
        thenInnerListGetMethodIsCalled();
    }

    @Test
    public void shouldCallIndexOfOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenIndexOf();
        thenInnerListIndexOfMethodIsCalled();
    }

    @Test
    public void shouldCallIsEmptyOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenIsEmpty();
        thenInnerListIsEmptyMethodIsCalled();
    }

    @Test
    public void shouldCallIteratorOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenIterator();
        thenInnerListIteratorMethodIsCalled();
    }

    @Test
    public void shouldCallLastIndexOfOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenLastIndexOf();
        thenInnerListLastIndexOfMethodIsCalled();
    }

    @Test
    public void shouldCallListIteratorOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenListIterator();
        thenInnerListListIteratorMethodIsCalled();
    }

    @Test
    public void shouldCallListIteratorAtPositionOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenListIteratorAtPosition();
        thenInnerListListIteratorAtPositionMethodIsCalled();
    }

    @Test
    public void shouldCallRemoveOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenRemove();
        thenInnerListRemoveMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallRemoveAtPositionOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenRemoveAtPosition();
        thenInnerListRemoveAtPositionMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallRemoveAllOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        givenAListOfItems();
        whenRemoveAll();
        thenInnerListRemoveAllMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallRetainAllOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        givenAListOfItems();
        whenRetainAll();
        thenInnerListRetainAllMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallSetAtLocationOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenSetAtLocation();
        thenInnerListSetAtLocationMethodIsCalled();
        thenObserverIsNotified();
    }

    @Test
    public void shouldCallSizeOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenSize();
        thenInnerListSizeMethodIsCalled();
    }

    @Test
    public void shouldCallSubListOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenSubList();
        thenInnerListSubListMethodIsCalled();
    }

    @Test
    public void shouldCallToArrayOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenToArray();
        thenInnerListToArrayMethodIsCalled();
    }

    @Test
    public void shouldCallToArrayTypedOfInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenToArrayTyped();
        thenInnerListToArrayTypedMethodIsCalled();
    }

    @Test
    public void shouldSwapInnerList() {
        givenACollectionProperty();
        givenAnObserver();
        whenSwap();
        thenAnyMethodDelegateInTheNewInnerList();
        thenObserverIsNotified();
    }

    @Test
    public void shouldHaveADefaultInnerList() {
        givenACollectionPropertyWithDefaultInnerList();
        whenSize();
        thenRealSizeIsReturned();
    }

    private void givenACollectionPropertyWithDefaultInnerList() {
        mCollectionProperty = new CollectionProperty<>();
    }

    private void givenACollectionProperty() {
        when(mInnerList.add(any())).thenReturn(true);
        when(mInnerList.addAll(any(Collection.class))).thenReturn(true);
        when(mInnerList.addAll(anyInt(), any(Collection.class))).thenReturn(true);
        when(mInnerList.remove(any())).thenReturn(true);
        when(mInnerList.removeAll(any(Collection.class))).thenReturn(true);
        when(mInnerList.retainAll(any(Collection.class))).thenReturn(true);
        mCollectionProperty = new CollectionProperty<>(mInnerList);
    }

    private void givenAnObserver() {
        mCollectionProperty.addObserver(mObserver);
    }

    private void givenAListOfItems() {
        mSomeListOfItems = Arrays.asList(mSomeItem1, mSomeItem2);
    }

    private void whenAddAll() {
        mCollectionProperty.addAll(mSomeListOfItems);
    }

    private void whenAddAllAtPosition() {
        mCollectionProperty.addAll(SOME_POSITION, mSomeListOfItems);
    }

    private void whenAdd() {
        mCollectionProperty.add(mSomeItem1);
    }

    private void whenAddAtPosition() {
        mCollectionProperty.add(SOME_POSITION, mSomeItem1);
    }

    private void whenClear() {
        mCollectionProperty.clear();
    }

    private void whenContains() {
        mCollectionProperty.contains(mSomeItem1);
    }

    private void whenContainsAll() {
        mCollectionProperty.containsAll(mSomeListOfItems);
    }

    private void whenGet() {
        mCollectionProperty.get(SOME_POSITION);
    }

    private void whenIndexOf() {
        mCollectionProperty.indexOf(mSomeItem1);
    }

    private void whenIsEmpty() {
        mCollectionProperty.isEmpty();
    }

    private void whenIterator() {
        mCollectionProperty.iterator();
    }

    private void whenLastIndexOf() {
        mCollectionProperty.lastIndexOf(mSomeItem1);
    }

    private void whenListIterator() {
        mCollectionProperty.listIterator();
    }

    private void whenListIteratorAtPosition() {
        mCollectionProperty.listIterator(SOME_POSITION);
    }

    private void whenRemove() {
        mCollectionProperty.remove(mSomeItem1);
    }

    private void whenRemoveAtPosition() {
        mCollectionProperty.remove(SOME_POSITION);
    }

    private void whenRemoveAll() {
        mCollectionProperty.removeAll(mSomeListOfItems);
    }

    private void whenRetainAll() {
        mCollectionProperty.retainAll(mSomeListOfItems);
    }

    private void whenSetAtLocation() {
        mCollectionProperty.set(SOME_POSITION, mSomeItem1);
    }

    private void whenSize() {
        mSize = mCollectionProperty.size();
    }

    private void whenSubList() {
        mCollectionProperty.subList(SOME_POSITION, SOME_POSITION_2);
    }

    private void whenToArray() {
        mCollectionProperty.toArray();
    }

    private void whenToArrayTyped() {
        mCollectionProperty.toArray(SOME_TYPED_ARRAY);
    }

    private void whenSwap() {
        mCollectionProperty.swap(mNewInnerList);
    }

    private void thenAnItemIsAddedAtPosition() {
        verify(mInnerList).add(SOME_POSITION, mSomeItem1);
    }

    private void thenAnItemIsAdded() {
        verify(mInnerList).add(mSomeItem1);
    }

    private void thenAllItemsAreAdded() {
        verify(mInnerList).addAll(mSomeListOfItems);
    }

    private void thenAllItemsAreAddedAtPosition() {
        verify(mInnerList).addAll(SOME_POSITION, mSomeListOfItems);
    }

    private void thenObserverIsNotified() {
        verify(mObserver).update(eq(mCollectionProperty), any());
    }

    private void thenInnerListClearMethodIsCalled() {
        verify(mInnerList).clear();
    }

    private void thenInnerListContainsMethodIsCalled() {
        verify(mInnerList).contains(mSomeItem1);
    }

    private void thenInnerListContainsAllMethodIsCalled() {
        verify(mInnerList).containsAll(mSomeListOfItems);
    }

    private void thenInnerListGetMethodIsCalled() {
        verify(mInnerList).get(SOME_POSITION);
    }

    private void thenInnerListIndexOfMethodIsCalled() {
        verify(mInnerList).indexOf(mSomeItem1);
    }

    private void thenInnerListIsEmptyMethodIsCalled() {
        verify(mInnerList).isEmpty();
    }

    private void thenInnerListIteratorMethodIsCalled() {
        verify(mInnerList).iterator();
    }

    private void thenInnerListLastIndexOfMethodIsCalled() {
        verify(mInnerList).lastIndexOf(mSomeItem1);
    }

    private void thenInnerListListIteratorMethodIsCalled() {
        verify(mInnerList).listIterator();
    }

    private void thenInnerListListIteratorAtPositionMethodIsCalled() {
        verify(mInnerList).listIterator(SOME_POSITION);
    }

    private void thenInnerListRemoveMethodIsCalled() {
        verify(mInnerList).remove(mSomeItem1);
    }

    private void thenInnerListRemoveAtPositionMethodIsCalled() {
        verify(mInnerList).remove(SOME_POSITION);
    }

    private void thenInnerListRemoveAllMethodIsCalled() {
        verify(mInnerList).removeAll(mSomeListOfItems);
    }

    private void thenInnerListRetainAllMethodIsCalled() {
        verify(mInnerList).retainAll(mSomeListOfItems);
    }

    private void thenInnerListSetAtLocationMethodIsCalled() {
        verify(mInnerList).set(SOME_POSITION, mSomeItem1);
    }

    private void thenInnerListSizeMethodIsCalled() {
        verify(mInnerList).size();
    }

    private void thenInnerListSubListMethodIsCalled() {
        verify(mInnerList).subList(SOME_POSITION, SOME_POSITION_2);
    }

    private void thenInnerListToArrayMethodIsCalled() {
        verify(mInnerList).toArray();
    }

    private void thenInnerListToArrayTypedMethodIsCalled() {
        verify(mInnerList).toArray(SOME_TYPED_ARRAY);
    }

    private void thenAnyMethodDelegateInTheNewInnerList() {
        mCollectionProperty.size();
        verifyNoMoreInteractions(mInnerList);
        verify(mNewInnerList).size();
    }

    private void thenRealSizeIsReturned() {
        assertThat(ZERO, is(mSize));
    }

}
