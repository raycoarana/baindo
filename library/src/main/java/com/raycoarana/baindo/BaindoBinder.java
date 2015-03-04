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
import com.raycoarana.baindo.binding.FinalBindTarget;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.intent.IntentActionBind;
import com.raycoarana.baindo.intent.IntentDataBind;
import com.raycoarana.baindo.intent.IntentExtraBind;
import com.raycoarana.baindo.intent.IntentTypeBind;
import com.raycoarana.baindo.observables.AbstractCollectionProperty;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.properties.CheckedBind;
import com.raycoarana.baindo.properties.CommandBind;
import com.raycoarana.baindo.properties.EnabledBind;
import com.raycoarana.baindo.properties.InvisibilityBind;
import com.raycoarana.baindo.properties.ProgressBind;
import com.raycoarana.baindo.properties.SelectedIndexBind;
import com.raycoarana.baindo.properties.TextBind;
import com.raycoarana.baindo.properties.UIActionBind;
import com.raycoarana.baindo.properties.VisibilityBind;
import com.raycoarana.baindo.renderer.AdapterBind;
import com.raycoarana.baindo.renderer.AdapterFactory;
import com.raycoarana.baindo.state.StateBind;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Creates binders
 */
class BaindoBinder implements Binder {

    private final WorkDispatcher mWorkDispatcher;
    private final UnbindableCollectorProvider mUnbindableCollectorProvider;
    private final UnbindableCollector mParentUnbindableCollector;
    private final LifecycleBinderCollector mLifecycleBinderCollector;
    private BindableSource mBindableSource;
    private BinderDelegate mBinderDelegate;

    public BaindoBinder(BindableSource bindableSource,
                        WorkDispatcher workDispatcher,
                        BinderDelegate binderDelegate,
                        UnbindableCollector parentUnbindableCollector,
                        UnbindableCollectorProvider unbindableCollectorProvider,
                        LifecycleBinderCollector lifecycleBinderCollector) {
        mBindableSource = bindableSource;
        mWorkDispatcher = workDispatcher;
        mBinderDelegate = binderDelegate;
        mUnbindableCollectorProvider = unbindableCollectorProvider;
        mParentUnbindableCollector = parentUnbindableCollector;
        mLifecycleBinderCollector = lifecycleBinderCollector;
    }

    /**
     * @see com.raycoarana.baindo.Binder#isChecked()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> isChecked() {
        return mParentUnbindableCollector.collect(new CheckedBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#enabled()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> enabled() {
        return mParentUnbindableCollector.collect(new EnabledBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#visibility()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> visibility() {
        return mParentUnbindableCollector.collect(new VisibilityBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#invisibility()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Boolean>> invisibility() {
        return mParentUnbindableCollector.collect(new InvisibilityBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#click()
     */
    @Override
    public ViewToBindSelector<Command> click() {
        return mParentUnbindableCollector.collect(new CommandBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#progress()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Integer>> progress() {
        return mParentUnbindableCollector.collect(new ProgressBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#text()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<CharSequence>> text() {
        return mParentUnbindableCollector.collect(new TextBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#uiAction(com.raycoarana.baindo.binding.UIAction)
     */
    @Override
    public <T> BindTarget<AbstractProperty<T>> uiAction(UIAction<T> uiAction) {
        return mParentUnbindableCollector.collect(new UIActionBind<>(uiAction, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#adapterWithFactory(com.raycoarana.baindo.renderer.AdapterFactory)
     */
    @Override
    public <T> ViewToBindSelector<AbstractCollectionProperty<T>> adapterWithFactory(AdapterFactory<T> adapterFactory) {
        return mParentUnbindableCollector.collect(new AdapterBind<>(mBindableSource,
                                                              mWorkDispatcher,
                                                              mBinderDelegate,
                                                              adapterFactory,
                                                              mUnbindableCollectorProvider));
    }

    /**
     * @see com.raycoarana.baindo.Binder#selectedIndex()
     */
    @Override
    public ViewToBindSelector<AbstractProperty<Integer>> selectedIndex() {
        return mParentUnbindableCollector.collect(new SelectedIndexBind(mBindableSource, mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#intentAction()
     */
    @Override
    public FinalBindTarget<AbstractProperty<String>> intentAction() {
        return mLifecycleBinderCollector.collect(new IntentActionBind(mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#intentData()
     */
    @Override
    public FinalBindTarget<AbstractProperty<String>> intentData() {
        return mLifecycleBinderCollector.collect(new IntentDataBind(mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#intentType()
     */
    @Override
    public FinalBindTarget<AbstractProperty<String>> intentType() {
        return mLifecycleBinderCollector.collect(new IntentTypeBind(mWorkDispatcher));
    }

    /**
     * @see com.raycoarana.baindo.Binder#intentExtraWithKey(String)
     */
    @Override
    public <T> FinalBindTarget<AbstractProperty<T>> intentExtraWithKey(String key) {
        return mLifecycleBinderCollector.collect(new IntentExtraBind<T>(mWorkDispatcher, key));
    }

    /**
     * @see com.raycoarana.baindo.Binder#stateWithKey(String)
     */
    @Override
    public <T> FinalBindTarget<AbstractProperty<T>> stateWithKey(String key) {
        return mLifecycleBinderCollector.collect(new StateBind<T>(mWorkDispatcher, key));
    }

}
