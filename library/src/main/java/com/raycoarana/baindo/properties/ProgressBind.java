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

import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.raycoarana.baindo.BindableSource;
import com.raycoarana.baindo.WorkDispatcher;
import com.raycoarana.baindo.observables.AbstractProperty;

public class ProgressBind extends BaseObservableBind<AbstractProperty<Integer>> implements SeekBar.OnSeekBarChangeListener {

    public ProgressBind(BindableSource bindableSource, WorkDispatcher workDispatcher) {
        super(bindableSource, workDispatcher);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
        doInBackgroundThread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (state == State.BINDED) {
                        mTarget.setValue(progress);
                    }
                }
            }
        });
    }

    @Override
    protected void bindView() {
        ProgressBar progressBar = getView();
        if(progressBar instanceof SeekBar) {
            ((SeekBar)progressBar).setOnSeekBarChangeListener(this);
        } else {
            throw new IllegalStateException("WRITE or READ_WRITE mode is not allowed in progress bind");
        }
    }

    @Override
    protected void unbindView() {
        ProgressBar progressBar = getView();
        if(progressBar instanceof SeekBar) {
            ((SeekBar)progressBar).setOnSeekBarChangeListener(null);
        } else {
            throw new IllegalStateException("WRITE or READ_WRITE mode is not allowed in progress bind");
        }
    }

    @Override
    protected void updateView() {
        ProgressBar progressBar = getView();
        Integer value = mTarget.getValue();
        progressBar.setProgress(value != null ? value : 0);
    }

    private ProgressBar getView() {
        return (ProgressBar) mView;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
