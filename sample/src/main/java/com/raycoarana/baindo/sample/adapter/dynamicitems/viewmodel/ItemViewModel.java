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

import com.raycoarana.baindo.observables.Property;
import com.raycoarana.baindo.sample.app.MessageService;
import com.raycoarana.baindo.sample.adapter.dynamicitems.services.TimerService;
import com.raycoarana.baindo.viewmodel.Command;

import java.util.TimerTask;

public class ItemViewModel {

    private static final int ZERO = 0;
    private final int mId;
    private int mTicks;
    private TimerTask mTimer;

    public Property<CharSequence> Ticks = new Property<>();

    public Command ShowCommand = () -> MessageService.show(Ticks.getValue().toString());
    
    public ItemViewModel(int id) {
        mId = id;
        mTicks = ZERO;
        mTimer = TimerService.getInstance().create(this::tick);
    }

    private void tick() {
        mTicks++;
        Ticks.setValue(mId + " => Ticks: " + mTicks);
        if(mTicks == 0) {
            mTimer.cancel();
        }
    }

}
