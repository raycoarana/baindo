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

import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.CollectionProperty;
import com.raycoarana.baindo.observables.Property;
import com.raycoarana.baindo.viewmodel.Command;

import java.util.Random;

public class ViewModel {

    private Random mRandom = new Random();

    public Property<Boolean> CanRemoveItems = new Property<>(false);
    public CollectionProperty<String> Items = new CollectionProperty<>();
    public Property<CharSequence> SelectedItem = new Property<>();
    public AbstractProperty<Integer> SelectedItemIndex = new AbstractProperty<Integer>() {

        @Override
        public void onValueChanged(Integer newValue) {
            mValue = newValue;
            String selectedItem = newValue != null ? Items.get(newValue) : null;
            SelectedItem.setValue(selectedItem);
        }

    };

    public Command AddCommand = new Command() {
        @Override
        public void execute() {
            String newValue = calculateNewValue();
            Items.add(newValue);
            CanRemoveItems.setValue(true);
        }
    };

    private String calculateNewValue() {
        return String.valueOf(Math.abs(mRandom.nextInt() % 100));
    }

    public Command RemoveCommand = new Command() {
        @Override
        public void execute() {
            Items.remove(0);
            Integer selectedItemIndex = SelectedItemIndex.getValue() - 1;
            if (selectedItemIndex < 0) {
                selectedItemIndex = Items.size() > 0 ? 0 : null;
            }
            SelectedItemIndex.setValue(selectedItemIndex);
            CanRemoveItems.setValue(Items.size() > 0);
        }
    };

}
