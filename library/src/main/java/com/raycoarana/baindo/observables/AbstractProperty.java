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

import java.util.Observer;

public abstract class AbstractProperty<T> extends Observable {

    public abstract T getValue();
    public abstract void onValueChanged(T newValue);

    /**
     * Sets a new value for this property, preventing that the sender observer gets called
     * by the update event that will be fired.
     */
    public void setValue(T newValue, Observer sender) {
        this.onValueChanged(newValue);

        setChanged();
        notifyObservers(sender);
    }

    /**
     * Sets a new value for this property. Caller of this method must not be an Observer of this
     * property (or you will suffer a infinite loop). If you want to change the value of this
     * property from an Observer, use setValue(T, Observer)
     *
     * @see AbstractProperty#setValue(Object, java.util.Observer)
     */
    public void setValue(T newValue) {
        setValue(newValue, null);
    }

}