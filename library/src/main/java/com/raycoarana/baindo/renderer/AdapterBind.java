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

import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.BinderDelegate;
import com.raycoarana.baindo.Unbindable;
import com.raycoarana.baindo.UnbindableCollectorProvider;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractCollectionProperty;
import com.raycoarana.baindo.properties.BaseObservableBind;

public class AdapterBind<T> extends BaseObservableBind<AbstractCollectionProperty<T>> {

    private AdapterFactory<T> mAdapterFactory;
    private BinderDelegate mBinderDelegate;
    private UnbindableCollectorProvider mUnbindableCollectorProvider;

    public AdapterBind(BindableSource bindableSource,
                       WorkDispatcher workDispatcher,
                       BinderDelegate binderDelegate,
                       AdapterFactory<T> adapterFactory,
                       UnbindableCollectorProvider unbindableCollectorProvider) {
        super(bindableSource, workDispatcher, FINAL_BIND_LEVEL);
        mBinderDelegate = binderDelegate;
        mAdapterFactory = adapterFactory;
        mUnbindableCollectorProvider = unbindableCollectorProvider;
    }

    protected void bind() {
        synchronized (this) {
            bindView();
            updateView();
            mTarget.addObserver(this);
            state = State.BINDED;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void bindView() {
        if(mView instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) mView;
            BaseAdapter baseAdapter = mAdapterFactory.build(mTarget);
            if(baseAdapter instanceof BinderRendererAdapter) {
                BinderRendererAdapter binderRendererAdapter = (BinderRendererAdapter) baseAdapter;
                binderRendererAdapter.injectBinderDelegate(mBinderDelegate);
                binderRendererAdapter.injectUnbindableCollectorProvider(mUnbindableCollectorProvider);
            }
            adapterView.setAdapter(baseAdapter);
        } else {
            throw new IllegalArgumentException(String.format("Can't bind a list to view type %s, it must be an AdapterView",
                    mView.getClass().getName()));
        }
    }

    @Override
    protected void unbindView() {
        mAdapterFactory = null;
        AdapterView adapterView = (AdapterView) mView;
        if(adapterView.getAdapter() instanceof Unbindable) {
            ((Unbindable)adapterView.getAdapter()).unbind();
        }
    }

    @Override
    protected void updateView() {
        AdapterView adapterView = (AdapterView) mView;
        BaseAdapter adapter = (BaseAdapter) adapterView.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
