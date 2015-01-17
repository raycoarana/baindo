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

public class Property<T> extends AbstractProperty<T> {

    private T mValue;

    public Property() {
    }

    public Property(T defaultValue) {
        mValue = defaultValue;
    }

    @Override
    public T getValue() {
        return mValue;
    }

    @Override
    public void onValueChanged(T newValue) {
        mValue = newValue;
    }

}
