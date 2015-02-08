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

package com.raycoarana.baindo.renderer;

import android.view.View;

import com.pedrogomez.renderers.Renderer;
import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.Binder;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.UnbindableCollector;
import com.raycoarana.baindo.UnbindableCollectorProvider;

public abstract class BinderRenderer<T> extends Renderer<T> implements BindableSource {

    private UnbindableCollectorProvider mUnbindableCollectorProvider;
    private BinderDelegate mBinderDelegate;
    private UnbindableCollector mUnbindableCollector = new UnbindableCollector();

    void injectDelegate(BinderDelegate binderDelegate) {
        mBinderDelegate = binderDelegate;
    }

    void injectUnbindableCollectorProvider(UnbindableCollectorProvider unbindableCollectorProvider) {
        mUnbindableCollectorProvider = unbindableCollectorProvider;
        mUnbindableCollector = unbindableCollectorProvider.get();
    }

    @Override
    protected void setUpView(View rootView) {}

    @Override
    protected void hookListeners(View rootView) {}

    @Override
    public void onRecycle(T content) {
        super.onRecycle(content);

        this.unbind();
    }

    void unbind() {
        mUnbindableCollector.unbindAndReleaseAll();
    }

    protected Binder bind() {
        return mBinderDelegate.bind(this, mUnbindableCollector);
    }

    @Override
    public void render() {
        bindViews();
    }

    protected abstract void bindViews();

    @Override
    public View findViewById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        BinderRenderer clone = (BinderRenderer) super.clone();
        clone.mUnbindableCollector = mUnbindableCollectorProvider.get();
        return clone;
    }

}