/*******************************************************************************
 * Copyright 2011 Ivan Shubin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.oculus.experior.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    public static String readFile(String path) throws FileNotFoundException, IOException {
        return readFile(new File(path));
    }

    public static String readFile(File file) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(file);
        char buffer[] = new char[(int) file.length()];
        reader.read(buffer);
        return new String(buffer);
    }

    public static String generatePath(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        String convertedDate = strDate;
        convertedDate = convertedDate.replace("-", File.separator);
        return convertedDate;
    }

    public static void mkdirs(String path) {

        String dirs[] = null;

        if (File.separator.equals("\\")) {
            dirs = path.split("\\\\");
        } else
            dirs = path.split("/");

        String cpath = "";
        for (int i = 0; i < dirs.length; i++) {
            cpath += File.separator + dirs[i];

            File file = new File(cpath);
            System.out.println(file.getAbsolutePath());
            if (file.exists()) {

            } else
                file.mkdir();
        }
    }

    public static String getFileType(String fileName) {
        for (int i = fileName.length() - 1; i >= 0; i--) {
            if (fileName.charAt(i) == '.') {
                return fileName.substring(i + 1);
            }
        }
        return null;
    }

    public static String getFileSimpleName(String fileName) {
        for (int i = fileName.length() - 1; i >= 0; i--) {
            if (fileName.charAt(i) == '.') {
                return fileName.substring(0, i);
            }
        }
        return fileName;
    }

    public static void main(String[] args) {

    }
}
