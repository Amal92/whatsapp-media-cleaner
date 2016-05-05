/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amal.whatsapp.Utils;

import com.amal.whatsapp.Models.StorageSize;


// TODO: Auto-generated Javadoc

public class StorageUtil {

    // storage, G M K B
    public static String convertStorage(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    public static StorageSize convertStorageSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        StorageSize sto = new StorageSize();
        if (size >= gb) {

            sto.suffix = "GB";
            sto.value = (float) size / gb;
            return sto;
        } else if (size >= mb) {

            sto.suffix = "MB";
            sto.value = (float) size / mb;

            return sto;
        } else if (size >= kb) {


            sto.suffix = "KB";
            sto.value = (float) size / kb;

            return sto;
        } else {
            sto.suffix = "B";
            sto.value = (float) size;

            return sto;
        }


    }

}
