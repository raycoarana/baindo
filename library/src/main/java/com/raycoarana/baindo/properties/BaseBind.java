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
import com.raycoarana.baindo.binding.BindTarget;
import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Base class to create bind between a view property and a Command/Property of a ViewModel.
 */
public abstract class BaseBind<T> implements ViewToBindSelector<T>, BindTarget<T>, Unbindable, BindLevel {

    private BindableSource mBindableSource;
    private WorkDispatcher mWorkDispatcher;
    protected View mView;
    protected T mTarget;
    protected BindWay mBindWay;

    public BaseBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        mBindableSource = bindableSource;
        mWorkDispatcher = workDispatcher;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(int)
     */
    @Override
    public BindTarget<T> of(int viewId) {
        mView = mBindableSource.findViewById(viewId);
        return this;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(android.view.View)
     */
    @Override
    public BindTarget<T> of(View view) {
        mView = view;
        return this;
    }

    /**
     * @see com.raycoarana.baindo.binding.ViewToBindSelector#of(android.view.MenuItem)
     */
    @Override
    public BindTarget<T> of(MenuItem menuItem) {
        throw new RuntimeException("Can't use this binder with a menu item");
    }

    /**
     * @see com.raycoarana.baindo.binding.BindTarget#to(Object)
     */
    @Override
    public BindLevel to(T target) {
        mTarget = target;
        if(target instanceof Command) {
            bind();
            return COMMAND_BIND_LEVEL;
        } else {
            return this;
        }
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
        onUnbind();
        destroy();
    }

    /**
     * Remove any reference to any other object
     */
    public void destroy() {
        mBindableSource = null;
        mWorkDispatcher = null;
        mView = null;
        mTarget = null;
    }

    /**
     * @see com.raycoarana.baindo.binding.BindTarget#using
     */
    /*
    @Override
    public BindTarget<T> using(AdapterFactory<? extends T> adapterFactory) {
        throw new RuntimeException("Not implemented");
    }*/

    /**
     * Execute some work on the UI thread.
     */
    public void doInUIThread(Runnable runnable) {
        mWorkDispatcher.doInUIThread(runnable);
    }

    /**
     * Execute some work on the ViewModel background thread.
     */
    public void doInBackgroundThread(Runnable runnable) {
        mWorkDispatcher.doInBackgroundThread(runnable);
    }

    private static final BindLevel COMMAND_BIND_LEVEL = new BindLevel() {
        @Override
        public void readOnly() {
            throw new IllegalStateException("Commands don't need a Bind Level. Remove readOnly() from the bind statement.");
        }

        @Override
        public void writeOnly() {
            throw new IllegalStateException("Commands don't need a Bind Level. Remove writeOnly() from the bind statement.");
        }

        @Override
        public void readWrite() {
            throw new IllegalStateException("Commands don't need a Bind Level. Remove readWrite() from the bind statement.");
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