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

package com.raycoarana.baindo.sample.progress.viewmodel;

import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.Property;

public class ViewModel {

    private static final int PENGING_PERCENT = 100;

    private int mPercentDone = 0;

    public AbstractProperty<Integer> PercentDone = new AbstractProperty<Integer>() {
        @Override
        public Integer getValue() {
            return mPercentDone;
        }

        @Override
        public void onValueChanged(Integer newValue) {
            mPercentDone = newValue;
            PercentLeft.setValue(PENGING_PERCENT - newValue);
        }
    };

    public Property<Integer> PercentLeft = new Property<>(PENGING_PERCENT);

}
