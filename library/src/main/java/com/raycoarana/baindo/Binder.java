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
import com.raycoarana.baindo.observables.AbstractProperty;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Let you create binding between properties of your views and your ViewModels
 */
public interface Binder {

    /**
     * Selects the IsChecked property as the source of the binding
     */
    ViewToBindSelector<AbstractProperty<Boolean>> isChecked();

    /**
     * Selects the Visibility property as the source of the binding
     */
    ViewToBindSelector<AbstractProperty<Boolean>> visibility();

    /**
     * Selects the Visibility property as the source of the binging, but mapping it in the
     * opposite way of what visibility do. True value will make the view to be gone, False value
     * will make the view to be shown.
     */
    ViewToBindSelector<AbstractProperty<Boolean>> invisibility();

    /**
     * Selects the OnClickListener event as the source of the binding
     */
    ViewToBindSelector<Command> click();

    /**
     * Selects the Progress property as the source of the binding
     */
    ViewToBindSelector<AbstractProperty<Integer>> progress();

    /**
     * Selects the Text property as the source of the binding
     */
    ViewToBindSelector<AbstractProperty<CharSequence>> text();

    /**
     * Selects an UI Action as the source of the binding. I will be executed every time the
     * property is changed.
     */
    <T> BindTarget<AbstractProperty<T>> uiAction(UIAction<T> uiAction);

}
