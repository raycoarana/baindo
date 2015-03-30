/*
 * Copyright 2015 Rayco Araña
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.raycoarana.baindo;

import android.os.Build;

import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Field;

/**
 * Helper class to mock some fields of Android SDK
 */
public class AndroidUnitTestHelper {

    /**
     * Mock Build.VERSION.SDK_INT to KitKat version
     */
    public static void mockSDKAsKitKat() {
        mockSDK(Build.VERSION_CODES.KITKAT);
    }

    /**
     * Mock Build.VERSION.SDK_INT to Lollipop version
     */
    public static void mockSDKAsLollipop() {
        mockSDK(Build.VERSION_CODES.LOLLIPOP);
    }

    private static void mockSDK(int sdkVersionCode) {
        try {
            Field field = PowerMockito.field(Build.VERSION.class, "SDK_INT");
            field.set(null, sdkVersionCode);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
