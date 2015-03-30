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
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> isChecked();

    /**
     * Selects the Enable property as the source of the binding
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> enabled();

    /**
     * Selects the Visibility property as the source of the binding
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> visibility();

    /**
     * Selects the Visibility property as the source of the binging, but mapping it in the
     * opposite way of what visibility do. True value will make the view to be gone, False value
     * will make the view to be shown.
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Boolean, AbstractProperty<Boolean>> invisibility();

    /**
     * Selects the OnClickListener event as the source of the binding
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Void, Command> click();

    /**
     * Selects the OnLongClickListener event as the source of the binding
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Void, Command> longClick();

    /**
     * Selects the Progress property as the source of the binding
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Integer, AbstractProperty<Integer>> progress();

    /**
     * Selects the Text property as the source of the binding
     *
     * @param <T> type of the property to bind
     * @return object to select view to bind
     */
    <T extends CharSequence> ViewToBindSelector<CharSequence, AbstractProperty<T>> text();

    /**
     * Selects an UI Action as the source of the binding. I will be executed every time the
     * property is changed.
     *
     * @param <T> type of the property to bind
     * @param <U> base type of the property to bind
     * @param uiAction UIAction to execute every time the property is changed
     * @return object to select the target of the bind
     */
    <T extends U, U> BindToTarget<U, AbstractProperty<T>> uiAction(UIAction<T> uiAction);

    /**
     * Prepares an adapter's binding with its factory
     *
     * @param <T> type of the property to bind
     * @param adapterFactory factory of the adapter to use in this binding
     * @return object to select view to bind
     */
    <T> ViewToBindSelector<T, AbstractCollectionProperty<T>> adapterWithFactory(AdapterFactory<T> adapterFactory);

    /**
     * Selects the Selected item index property of an AdapterView
     *
     * @return object to select view to bind
     */
    ViewToBindSelector<Integer, AbstractProperty<Integer>> selectedIndex();

    /**
     * Selects the current Intent action as source of the binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<AbstractProperty<String>> intentAction();

    /**
     * Selects the current Intent data as source of the binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<AbstractProperty<String>> intentData();

    /**
     * Selects the current Intent type as source of the binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<AbstractProperty<String>> intentType();

    /**
     * Selects the current Intent extra with the selected key as source of the binding.
     *
     * @param <T> type of the property to bind
     * @param key extra key of the intent
     * @return object to select the target of the bind
     */
    <T> FinalBindTarget<AbstractProperty<T>> intentExtraWithKey(String key);

    /**
     * Binds the saved instance state with the selected key as source of binding.
     *
     * @param <T> type of the property to bind
     * @param key state key of the saved instance state bundle
     * @return object to select the target of the bind
     */
    <T> FinalBindTarget<AbstractProperty<T>> stateWithKey(String key);

    /**
     * Binds onCreate() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onCreate();

    /**
     * Binds onDestroy() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onDestroy();

    /**
     * Binds onStart() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onStart();

    /**
     * Binds onStop() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onStop();

    /**
     * Binds onResume() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onResume();

    /**
     * Binds onPause() as source of binding.
     *
     * @return object to select the target of the bind
     */
    FinalBindTarget<Command> onPause();

}
