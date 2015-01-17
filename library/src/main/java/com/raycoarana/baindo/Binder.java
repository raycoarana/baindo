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
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Let you create binding between properties of your views and your ViewModels
 */
public interface Binder {

    /**
     * Selects the OnClickListener event as the source of the binding
     */
    ViewToBindSelector<Command> click();

    /**
     * Selects the Text property as the source of the binding
     */
    ViewToBindSelector<AbstractProperty<CharSequence>> text();

}
