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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("NullableProblems")
public class CollectionProperty<T> extends AbstractCollectionProperty<T> implements List<T> {

    private List<T> mList;

    public CollectionProperty() {
        mList = new ArrayList<>();
    }

    public CollectionProperty(List<T> listToObserv) {
        mList = listToObserv;
    }

    public void swap(List<T> innerCollection) {
        mList = innerCollection;

        setChanged();
        notifyObservers();
    }

    @Override
    public void add(int location, T object) {
        mList.add(location, object);

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean add(T object) {
        boolean changed = mList.add(object);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        boolean changed = mList.addAll(location, collection);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean changed = mList.addAll(collection);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public void clear() {
        mList.clear();

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean contains(Object object) {
        return mList.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @Override
    public T get(int location) {
        return mList.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return mList.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return mList.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return mList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int location) {
        return mList.listIterator(location);
    }

    @Override
    public T remove(int location) {
        T item = mList.remove(location);

        setChanged();
        notifyObservers();
        return item;
    }

    @Override
    public boolean remove(Object object) {
        boolean changed = mList.remove(object);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed = mList.removeAll(collection);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean changed = mList.retainAll(collection);

        if(changed) {
            setChanged();
            notifyObservers();
        }
        return changed;
    }

    @Override
    public T set(int location, T object) {
        T item = mList.set(location, object);

        setChanged();
        notifyObservers();

        return item;
    }

    @Override
    public int size() {
        return mList.size();
    }

    @Override
    public List<T> subList(int start, int end) {
        return mList.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return mList.toArray(array);
    }
}
