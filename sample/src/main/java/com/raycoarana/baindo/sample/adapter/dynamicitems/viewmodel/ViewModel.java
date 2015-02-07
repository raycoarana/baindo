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

package com.raycoarana.baindo.sample.adapter.dynamicitems.viewmodel;

import android.util.Log;

import com.raycoarana.baindo.observables.CollectionProperty;
import com.raycoarana.baindo.observables.Property;
import com.raycoarana.baindo.viewmodel.Command;

public class ViewModel {

    private static final int ZERO = 0;

    private int mNextId = ZERO;

    public Property<Boolean> CanRemoveItems = new Property<>(false);
    public CollectionProperty<ItemViewModel> Items = new CollectionProperty<>();

    public Command AddCommand = () -> {
        Items.add(new ItemViewModel(mNextId++));
        CanRemoveItems.setValue(true);
    };

    public Command RemoveCommand = () -> {
        Items.remove(0).StopCommand.execute();
        CanRemoveItems.setValue(Items.size() > 0);
    };

}
