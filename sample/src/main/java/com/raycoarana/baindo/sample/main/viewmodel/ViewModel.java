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

package com.raycoarana.baindo.sample.main.viewmodel;

import com.raycoarana.baindo.sample.app.NavigationService;
import com.raycoarana.baindo.viewmodel.Command;

public class ViewModel {

    private NavigationService mNavigationService = NavigationService.getInstance();

    public Command OpenClickSampleCommand = new Command() {
        @Override
        public void execute() {
            mNavigationService.navigateToClickActivity();
        }
    };

    public Command OpenTextSampleCommand = new Command() {
        @Override
        public void execute() {
            mNavigationService.navigateToTextActivity();
        }
    };

    public Command OpenCheckSampleCommand = new Command() {
        @Override
        public void execute() {
            mNavigationService.navigateToCheckActivity();
        }
    };

    public Command OpenProgressSampleCommand = new Command() {
        @Override
        public void execute() {
            mNavigationService.navigateToProgressActivity();
        }
    };

    public Command OpenUIActionSampleCommand = new Command() {
        @Override
        public void execute() {
            mNavigationService.navigateToUIActionActivity();
        }
    };

}
