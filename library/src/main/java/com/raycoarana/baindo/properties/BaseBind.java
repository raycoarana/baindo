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

import android.view.MenuItem;
import android.view.View;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.Unbindable;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.binding.BindLevel;
import com.raycoarana.baindo.binding.BindToTarget;
import com.raycoarana.baindo.binding.ViewToBindSelector;

/**
 * Base class to create bind between a view property and a Command/Property of a ViewModel.
 */
public abstract class BaseBind<Type, Property> implements ViewToBindSelector<Type, Property>, BindToTarget<Type, Property>, Unbindable, BindLevel {

    protected enum State { BINDED, UNBINDED }

    private BindableSource mBindableSource;
    private WorkDispatcher mWorkDispatcher;
    private BindLevel mBindLevel;
    protected View mView;
    protected Property mTarget;
    protected BindWay mBindWay = BindWay.READ_WRITE;
    protected State state;

    public BaseBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        this(bindableSource, workDispatcher, null);
        mBindLevel = this;
    }

    public BaseBind(BindableSource bindableSource, WorkDispatcher workDispatcher, BindLevel bindLevel) {
        mBindableSource = bindableSource;
        mWorkDispatcher = workDispatcher;
        mBindLevel = bindLevel;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(int)
     */
    @Override
    public BindToTarget<Type, Property> of(int viewId) {
        mView = mBindableSource.findViewById(viewId);
        return this;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(android.view.View)
     */
    @Override
    public BindToTarget<Type, Property> of(View view) {
        mView = view;
        return this;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(android.view.MenuItem)
     */
    @Override
    public BindToTarget<Type, Property> of(MenuItem menuItem) {
        throw new RuntimeException("Can't use this binder with a menu item");
    }

    /**
     * @see BindToTarget#to(Object)
     */
    @Override
    public BindLevel to(Property target) {
        mTarget = target;
        if(mBindLevel == FINAL_BIND_LEVEL) {
            bind();
        }
        return mBindLevel;
    }

    /**
     * Do the needed work to bind the view and the view model property
     */
    protected abstract void bind();

    /**
     * Do the needed work to unbind the view and the view model property
     */
    protected abstract void onUnbind();

    /**
     * @see com.raycoarana.baindo.Unbindable#unbind()
     */
    @Override
    public void unbind() {
        synchronized (this) {
            onUnbind();
            destroy();
            state = State.UNBINDED;
        }
    }

    /**
     * Remove any reference to any other object
     */
    private void destroy() {
        mBindableSource = null;
        mWorkDispatcher = null;
        mView = null;
        mTarget = null;
    }

    /**
     * Execute some work on the UI thread.
     *
     * @param runnable code to execute in the UI Thread
     */
    public void doInUIThread(Runnable runnable) {
        synchronized (this) {
            if(state == State.BINDED) {
                mWorkDispatcher.doInUIThread(runnable);
            }
        }
    }

    /**
     * Execute some work on the ViewModel background thread.
     *
     * @param runnable code to execute in the background thread
     */
    public void doInBackgroundThread(Runnable runnable) {
        synchronized (this) {
            if(state == State.BINDED) {
                mWorkDispatcher.doInBackgroundThread(runnable);
            }
        }
    }

    protected static final BindLevel FINAL_BIND_LEVEL = new BindLevel() {
        @Override
        public void readOnly() {
            throw new IllegalStateException("This bind doesn't need a direction. Remove readOnly() from bind statement.");
        }

        @Override
        public void writeOnly() {
            throw new IllegalStateException("This bind doesn't need a direction. Remove writeOnly() from bind statement.");
        }

        @Override
        public void readWrite() {
            throw new IllegalStateException("This bind doesn't need a direction. Remove readWrite() from bind statement.");
        }
    };

    @Override
    public void readOnly() {
        mBindWay = BindWay.READ_ONLY;
        bind();
    }

    @Override
    public void writeOnly() {
        mBindWay = BindWay.WRITE_ONLY;
        bind();
    }

    @Override
    public void readWrite() {
        mBindWay = BindWay.READ_WRITE;
        bind();
    }

}