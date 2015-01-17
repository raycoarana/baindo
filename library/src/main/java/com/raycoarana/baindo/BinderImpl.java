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

import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.properties.CommandBind;
import com.raycoarana.baindo.properties.TextBind;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Creates binders
 */
class BinderImpl implements Binder, Unbindable {

    private final WorkDispatcher mWorkDispatcher;
    private BindableSource mBindableSource;
    private Unbindable mViewPropertyBind;

    public BinderImpl(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        mBindableSource = bindableSource;
        mWorkDispatcher = workDispatcher;
    }

    /**
     * @see com.raycoarana.baindo.Binder#click()
     */
    @Override
    public ViewToBindSelector<Command> click() {
        return setAsSourceOfBind(new CommandBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#text()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<CharSequence>> text() {
        return setAsSourceOfBind(new TextBind(mBindableSource, mWorkDispatcher));
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
