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

import com.raycoarana.baindo.binding.BindToTarget;
import com.raycoarana.baindo.binding.FinalBindTarget;
import com.raycoarana.baindo.binding.UIAction;
import com.raycoarana.baindo.binding.ViewToBindSelector;
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.observables.AbstractCollectionProperty;
import com.raycoarana.baindo.renderer.AdapterFactory;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Let you create binding between properties of your views and your ViewModels
 */
public interface Binder {

    /**
     * Selects the IsChecked property as the source of the binding
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> isChecked();

    /**
     * Selects the Enable property as the source of the binding
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> enabled();

    /**
     * Selects the Visibility property as the source of the binding
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> visibility();

    /**
     * Selects the Visibility property as the source of the binging, but mapping it in the
     * opposite way of what visibility do. True value will make the view to be gone, False value
     * will make the view to be shown.
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> invisibility();

    /**
     * Selects the OnClickListener event as the source of the binding
     */
    ViewToBindSelector<Void, Command> click();

    /**
     * Selects the OnLongClickListener event as the source of the binding
     */
    ViewToBindSelector<Void, Command> longClick();

    /**
     * Selects the Progress property as the source of the binding
     */
    ViewToBindSelector<Integer, AbstractProperty<Integer>> progress();

    /**
     * Selects the Text property as the source of the binding
     */
    <T extends CharSequence> ViewToBindSelector<CharSequence, AbstractProperty<T>> text();

    /**
     * Selects an UI Action as the source of the binding. I will be executed every time the
     * property is changed.
     */
    <T extends U, U> BindToTarget<U, AbstractProperty<T>> uiAction(UIAction<T> uiAction);

    /**
     * Prepares an adapter's binding with its factory
     */
    <T> ViewToBindSelector<T, AbstractCollectionProperty<T>> adapterWithFactory(AdapterFactory<T> adapterFactory);

    /**
     * Selects the Selected item index property of an AdapterView
     */
    ViewToBindSelector<Integer, AbstractProperty<Integer>> selectedIndex();

    /**
     * Selects the current Intent action as source of the binding.
     */
    FinalBindTarget<AbstractProperty<String>> intentAction();

    /**
     * Selects the current Intent data as source of the binding.
     */
    FinalBindTarget<AbstractProperty<String>> intentData();

    /**
     * Selects the current Intent type as source of the binding.
     */
    FinalBindTarget<AbstractProperty<String>> intentType();

    /**
     * Selects the current Intent extra with the selected key as source of the binding.
     */
    <T> FinalBindTarget<AbstractProperty<T>> intentExtraWithKey(String key);

    /**
     * Binds the saved instance state with the selected key as source of binding.
     */
    <T> FinalBindTarget<AbstractProperty<T>> stateWithKey(String key);

    /**
     * Binds onCreate() as source of binding.
     */
    FinalBindTarget<Command> onCreate();

    /**
     * Binds onDestroy() as source of binding.
     */
    FinalBindTarget<Command> onDestroy();

    /**
     * Binds onStart() as source of binding.
     */
    FinalBindTarget<Command> onStart();

    /**
     * Binds onStop() as source of binding.
     */
    FinalBindTarget<Command> onStop();

    /**
     * Binds onResume() as source of binding.
     */
    FinalBindTarget<Command> onResume();

    /**
     * Binds onPause() as source of binding.
     */
    FinalBindTarget<Command> onPause();

}
