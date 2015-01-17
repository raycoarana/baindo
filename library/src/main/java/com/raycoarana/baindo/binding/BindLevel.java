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

package com.raycoarana.baindo.binding;

/**
 * The bind level or direction, it means, how the data flow between the View and the ViewModel.
 *
 * The default behavior is readOnly, it only flows from the ViewModel to the View.
 */
public interface BindLevel {

    /**
     * The value is only read from the ViewModel but not write when it changes in the View.
     * It's the default behavior.
     */
    void readOnly();

    /**
     * The value is only write in the ViewModel but not read from it when it changes.
     */
    void writeOnly();

    /**
     * The value is read from the ViewModel and write in it every time it changes.
     *
     * Be aware that in this mode and in combination of other binding to the same property you
     * could create a infinite loop between the UI thread and the Background thread, reading
     * and writing continuously the same value.
     */
    void readWrite();

}
