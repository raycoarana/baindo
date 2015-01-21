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

package com.raycoarana.baindo.sample.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.raycoarana.baindo.sample.check.view.CheckActivity;
import com.raycoarana.baindo.sample.click.view.ClickActivity;
import com.raycoarana.baindo.sample.progress.view.ProgressActivity;
import com.raycoarana.baindo.sample.text.view.TextActivity;
import com.raycoarana.baindo.sample.uiaction.view.UIActionActivity;

import java.lang.ref.WeakReference;

public class NavigationService {

    private static NavigationService sInstance;

    private Context mContext;
    private WeakReference<Activity> mCurrentActivityReference;

    public static NavigationService getInstance() {
        if(sInstance == null) {
            sInstance = new NavigationService();
        }
        return sInstance;
    }

    public void updateContext(Context context) {
        mContext = context;
    }

    public void updateCurrentActivity(Activity activity) {
        mCurrentActivityReference = new WeakReference<>(activity);
    }

    public void navigateToClickActivity() {
        startActivity(new Intent(mContext, ClickActivity.class));
    }

    public void navigateToTextActivity() {
        startActivity(new Intent(mContext, TextActivity.class));
    }

    public void navigateToCheckActivity() {
        startActivity(new Intent(mContext, CheckActivity.class));
    }

    public void navigateToProgressActivity() {
        startActivity(new Intent(mContext, ProgressActivity.class));
    }

    public void navigateToUIActionActivity() {
        startActivity(new Intent(mContext, UIActionActivity.class));
    }

    private void startActivity(Intent intent) {
        Activity currentActivity = mCurrentActivityReference.get();
        if(currentActivity != null) {
            currentActivity.startActivity(intent);
        }
    }
}
