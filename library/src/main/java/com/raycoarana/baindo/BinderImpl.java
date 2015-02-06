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

import com.raycoarana.baindo.binding.BindTarget;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.observables.AbstractCollectionProperty;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.properties.CheckedBind;
import com.raycoarana.baindo.properties.CommandBind;
import com.raycoarana.baindo.properties.EnabledBind;
import com.raycoarana.baindo.properties.InvisibilityBind;
import com.raycoarana.baindo.properties.ProgressBind;
import com.raycoarana.baindo.properties.TextBind;
import com.raycoarana.baindo.properties.UIActionBind;
import com.raycoarana.baindo.properties.VisibilityBind;
import com.raycoarana.baindo.renderer.AdapterBind;
import com.raycoarana.baindo.renderer.AdapterFactory;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Creates binders
 */
class BinderImpl implements Binder, Unbindable {

    private final WorkDispatcher mWorkDispatcher;
    private BindableSource mBindableSource;
    private BinderDelegate mBinderDelegate;
    private Unbindable mViewPropertyBind;

    public BinderImpl(BindableSource bindableSource, WorkDispatcher workDispatcher, BinderDelegate binderDelegate) {
        mBindableSource = bindableSource;
        mWorkDispatcher = workDispatcher;
        mBinderDelegate = binderDelegate;
    }

    /**
     * @see com.raycoarana.baindo.Binder#isChecked()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> isChecked() {
        return setAsSourceOfBind(new CheckedBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#enable()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> enable() {
        return setAsSourceOfBind(new EnabledBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#visibility()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> visibility() {
        return setAsSourceOfBind(new VisibilityBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#invisibility()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> invisibility() {
        return setAsSourceOfBind(new InvisibilityBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#click()
     */
    @Override
    public ViewToBindSelector<Command> click() {
        return setAsSourceOfBind(new CommandBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#progress()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Integer>> progress() {
        return setAsSourceOfBind(new ProgressBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#text()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<CharSequence>> text() {
        return setAsSourceOfBind(new TextBind(mBindableSource, mWorkDispatcher));
    }

    @Override
    public <T> BindTarget<AbstractProperty<T>> uiAction(UIAction<T> uiAction) {
        return setAsSourceOfBind(new UIActionBind<>(uiAction, mWorkDispatcher));
    }

    @Override
    public <T> ViewToBindSelector<AbstractCollectionProperty<T>> adapterWithFactory(AdapterFactory<T> adapterFactory) {
        return setAsSourceOfBind(new AdapterBind<>(mBindableSource, mWorkDispatcher, mBinderDelegate, adapterFactory));
    }



    private <T extends Unbindable> T setAsSourceOfBind(T t) {
        mViewPropertyBind = t;
        return t;
    }

    /**
     * Unbind the view property from the view model
     */
    @Override
    public void unbind() {
        if(mViewPropertyBind != null) {
            mViewPropertyBind.unbind();
        }
        mBindableSource = null;
    }

}
