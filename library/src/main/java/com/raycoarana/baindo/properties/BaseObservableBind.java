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

package com.raycoarana.baindo.properties;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.binding.BindLevel;

import java.util.Observable;
import java.util.Observer;

public abstract class BaseObservableBind<T extends Observable> extends BaseBind<T> implements Observer {

    public BaseObservableBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    public BaseObservableBind(BindableSource bindableSource, WorkDispatcher workDispatcher, BindLevel bindLevel) {
        super(bindableSource, workDispatcher, bindLevel);
    }

    /**
     * Bind event listeners
     */
    protected abstract void bindView();

    /**
     * Unbind event listeners
     */
    protected abstract void unbindView();

    /**
     * Update the view's state with the new value
     */
    protected abstract void updateView();

    @Override
    public void update(Observable observable, Object o) {
        doInUIThread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (state == State.BINDED) {
                        updateView();
                    }
                }
            }
        });
    }

    protected void bind() {
        synchronized (this) {
            onBind();
            state = State.BINDED;
        }
    }

    @Override
    protected void onUnbind() {
        if (mBindWay == BindWay.READ_WRITE || mBindWay == BindWay.WRITE_ONLY) {
            unbindView();
        }
        if (mBindWay == BindWay.READ_WRITE || mBindWay == BindWay.READ_ONLY) {
            mTarget.deleteObserver(this);
        }
    }

    private void onBind() {
        if (mBindWay == BindWay.READ_WRITE || mBindWay == BindWay.READ_ONLY) {
            updateView();
            mTarget.addObserver(this);
        }
        if (mBindWay == BindWay.READ_WRITE || mBindWay == BindWay.WRITE_ONLY) {
            bindView();
        }
    }

}
