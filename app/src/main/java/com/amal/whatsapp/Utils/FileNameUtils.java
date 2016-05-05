package com.amal.whatsapp.Utils;

import android.net.Uri;

import java.io.File;

/**
 * Created by amal on 27/04/16.
 */
public class FileNameUtils {
    public static final String SCHME_FILE = "file";
    public static final String SCHME_FILE_AND_SEPARATOR = "file://";

    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    public static String getExtension(String name) {
        String ext;
        if (name.lastIndexOf(".") == -1) {
            ext = "";

        } else {
            int index = name.lastIndexOf(".");
            ext = name.substring(index + 1, name.length());
        }
        return ext;
    }

    public static String getRemovedExtensionName(String name) {
        String baseName;
        if (name.lastIndexOf(".") == -1) {
            baseName = name;

        } else {
            int index = name.lastIndexOf(".");
            baseName = name.substring(0, index);
        }
        return baseName;
    }

    public static File getChangedExtensionFile(File file, String extension, boolean overwrite) {
        File newFile = null;
        String baseName = null;
        if (file.getName().lastIndexOf(".") == -1) {
            baseName = file.getName();

        } else {
            int index = file.getName().lastIndexOf(".");
            baseName = file.getName().substring(0, index);
        }
        String bName = baseName;
        if (!extension.equals("")) {
            bName += "." + extension;
        }
        newFile = new File(file.getParent(), bName);
        int index = 1;


        if (!overwrite) {
            while (newFile.exists()) {
                String specific = "(" + index + ")";
                String tmpName = baseName + specific;
                if (!extension.equals("")) {
                    tmpName += "." + extension;
                }
                newFile = new File(file.getParent(), tmpName);
                index++;
            }
        }
        return newFile;
    }

    public static String uriToPath(Uri uri) {
        if (uri.getScheme().equals(SCHME_FILE)) {
            return uri.getPath();
        }
        return null;
    }

    public static Uri pathToUri(String path) {
        return Uri.parse(SCHME_FILE_AND_SEPARATOR + path);
    }

    public static boolean isFileUri(String uri) {
        return uri != null && uri.startsWith(SCHME_FILE_AND_SEPARATOR);
    }

    public static String uriToPath(String uri) {
        if (isFileUri(uri)) {
            return uri.substring(SCHME_FILE_AND_SEPARATOR.length());
        }
        return null;
    }
}
