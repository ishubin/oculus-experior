/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
