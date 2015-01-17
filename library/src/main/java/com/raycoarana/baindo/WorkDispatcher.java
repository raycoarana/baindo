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

/**
 * Executes work at the thread you desire. If are already in that thread, simple runs it. If not,
 * post it at the appropriate thread.
 */
public interface WorkDispatcher {

    /**
     * Ensures that this Runnable is executed in the UI Thread.
     */
    void doInUIThread(Runnable runnable);

    /**
     * Ensures that this Runnable is executed in the ViewModel Background Thread.
     */
    void doInBackgroundThread(Runnable runnable);

}
