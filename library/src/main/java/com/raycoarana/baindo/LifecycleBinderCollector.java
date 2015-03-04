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

package com.raycoarana.baindo;

import android.content.Intent;
import android.os.Bundle;

import com.raycoarana.baindo.binding.FinalBindTarget;
import com.raycoarana.baindo.intent.IntentBind;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.state.StateBind;

import java.util.ArrayList;

public class LifecycleBinderCollector {

    private final ArrayList<IntentBind> mIntentBinds = new ArrayList<>();
    private final ArrayList<StateBind> mStateBinds = new ArrayList<>();
    private Intent mCurrentIntent;
    private Bundle mSavedInstanceState;

    public <T> FinalBindTarget<AbstractProperty<T>> collect(IntentBind<T> intentBind) {
        synchronized (mIntentBinds) {
            mIntentBinds.add(intentBind);
            intentBind.onNewIntent(mCurrentIntent);
        }
        return intentBind;
    }

    public void updateIntent(Intent intent) {
        mCurrentIntent = intent;
        synchronized (mIntentBinds) {
            for(IntentBind intentBind : mIntentBinds) {
                intentBind.onNewIntent(intent);
            }
        }
    }

    public <T> FinalBindTarget<AbstractProperty<T>> collect(StateBind<T> stateBind) {
        synchronized (mStateBinds) {
            mStateBinds.add(stateBind);
            if(mSavedInstanceState != null) {
                stateBind.onUpdate(mSavedInstanceState);
            }
        }
        return stateBind;
    }

    public void updateSavedInstanceState(Bundle bundle) {
        synchronized (mStateBinds) {
            mSavedInstanceState = bundle;
            if(mSavedInstanceState != null) {
                for (StateBind stateBind : mStateBinds) {
                    stateBind.onUpdate(bundle);
                }
            }
        }
    }

    public void saveInstanceState(Bundle bundle) {
        synchronized (mStateBinds) {
            for(StateBind stateBind : mStateBinds) {
                stateBind.onSave(bundle);
            }
        }
    }

}
