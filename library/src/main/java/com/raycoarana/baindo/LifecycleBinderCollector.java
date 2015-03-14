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
import com.raycoarana.baindo.events.AbstractEventBind;
import com.raycoarana.baindo.events.OnCreateEventBind;
import com.raycoarana.baindo.events.OnDestroyEventBind;
import com.raycoarana.baindo.events.OnPauseEventBind;
import com.raycoarana.baindo.events.OnResumeEventBind;
import com.raycoarana.baindo.events.OnStartEventBind;
import com.raycoarana.baindo.events.OnStopEventBind;
import com.raycoarana.baindo.intent.IntentBind;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.state.StateBind;
import com.raycoarana.baindo.viewmodel.Command;

import java.util.ArrayList;
import java.util.List;

public class LifecycleBinderCollector {

    private final ArrayList<IntentBind> mIntentBinds = new ArrayList<>();
    private final ArrayList<StateBind> mStateBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnCreateEventBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnDestroyEventBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnPauseEventBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnResumeEventBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnStartEventBinds = new ArrayList<>();
    private final ArrayList<AbstractEventBind> mOnStopEventBinds = new ArrayList<>();
    private Intent mCurrentIntent;
    private Bundle mSavedInstanceState;

    public FinalBindTarget<Command> collect(OnCreateEventBind eventBind) {
        collectEvent(eventBind, mOnCreateEventBinds);
        return eventBind;
    }

    public FinalBindTarget<Command> collect(OnDestroyEventBind eventBind) {
        collectEvent(eventBind, mOnDestroyEventBinds);
        return eventBind;
    }

    public FinalBindTarget<Command> collect(OnPauseEventBind eventBind) {
        collectEvent(eventBind, mOnPauseEventBinds);
        return eventBind;
    }

    public FinalBindTarget<Command> collect(OnResumeEventBind eventBind) {
        collectEvent(eventBind, mOnResumeEventBinds);
        return eventBind;
    }

    public FinalBindTarget<Command> collect(OnStartEventBind eventBind) {
        collectEvent(eventBind, mOnStartEventBinds);
        return eventBind;
    }

    public FinalBindTarget<Command> collect(OnStopEventBind eventBind) {
        collectEvent(eventBind, mOnStopEventBinds);
        return eventBind;
    }

    private void collectEvent(AbstractEventBind eventBind, List<AbstractEventBind> eventBinds) {
        synchronized (mIntentBinds) {
            eventBinds.add(eventBind);
        }
    }

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

    public void onCreate() {
        dispatchEvent(mOnCreateEventBinds);
    }

    public void onDestroy() {
        dispatchEvent(mOnDestroyEventBinds);
    }

    public void onResume() {
        dispatchEvent(mOnResumeEventBinds);
    }

    public void onPause() {
        dispatchEvent(mOnPauseEventBinds);
    }

    public void onStart() {
        dispatchEvent(mOnStartEventBinds);
    }

    public void onStop() {
        dispatchEvent(mOnStopEventBinds);
    }

    private void dispatchEvent(List<AbstractEventBind> eventBinds) {
        synchronized (eventBinds) {
            for(AbstractEventBind eventBind : eventBinds) {
                eventBind.execute();
            }
        }
    }
}
