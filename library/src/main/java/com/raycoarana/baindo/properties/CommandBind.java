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

package com.raycoarana.baindo.properties;

import android.view.View;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.viewmodel.Command;

/**
 * Binds/unbind a Command to a view's OnClickListener event.
 */
public class CommandBind extends BaseBind<Void, Command> {

    public CommandBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher, FINAL_BIND_LEVEL);
    }

    @Override
    protected void bind() {
        synchronized (this) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doInBackgroundThread(new Runnable() {
                        @Override
                        public void run() {
                            execute();
                        }
                    });
                }
            });
            state = State.BINDED;
        }
    }

    private void execute() {
        synchronized (this) {
            if (state == State.BINDED) {
                mTarget.execute();
            }
        }
    }

    @Override
    protected void onUnbind() {
        mView.setOnClickListener(null);
    }

}
