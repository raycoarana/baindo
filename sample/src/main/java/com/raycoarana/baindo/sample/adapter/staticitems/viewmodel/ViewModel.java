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

package com.raycoarana.baindo.sample.adapter.staticitems.viewmodel;

import com.raycoarana.baindo.observables.CollectionProperty;
import com.raycoarana.baindo.observables.Property;
import com.raycoarana.baindo.viewmodel.Command;

import java.util.Random;

public class ViewModel {

    private Random mRandom = new Random();

    public Property<Boolean> CanRemoveItems = new Property<>(false);
    public CollectionProperty<String> Items = new CollectionProperty<>();

    public Command AddCommand = () -> {
        String newValue = calculateNewValue();
        Items.add(newValue);
        CanRemoveItems.setValue(true);
    };

    private String calculateNewValue() {
        return String.valueOf(Math.abs(mRandom.nextInt() % 100));
    }

    public Command RemoveCommand = () -> {
        Items.remove(0);
        CanRemoveItems.setValue(Items.size() > 0);
    };

}
