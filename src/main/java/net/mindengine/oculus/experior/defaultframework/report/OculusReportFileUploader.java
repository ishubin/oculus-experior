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
package net.mindengine.oculus.experior.defaultframework.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mindengine.oculus.experior.ExperiorConfig;

public class OculusReportFileUploader {
    private String url;
    private int maxBufferSize = 1024;
    private HashMap<String, String> files = new HashMap<String, String>();

    public OculusReportFileUploader(String url) {
        this.url = url;
    }

    public void addFiles(String dirPath, boolean bRecursive) throws Exception {
        File file = new File(dirPath);
        if (!file.exists())
            throw new FileNotFoundException(dirPath);

        File[] list = file.listFiles();

        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory() && bRecursive) {
                addFiles(list[i].getAbsolutePath(), true);
            } else if (list[i].isFile()) {
                addFile(list[i].getName(), list[i].getAbsolutePath());
            }
        }
    }

    public void addFile(String fileName, String filePath) {
        files.put(fileName, filePath);
    }

    public void write(OutputStream os, String text) throws Exception {
        os.write(text.getBytes());
    }

    /**
     * Uploads all files and returns a list of paths to this files in oculus
     * system
     * 
     * @return List of paths to uploaded files in oculus system
     * @throws Exception
     */
    public List<String> upload() throws Exception {
        HttpURLConnection conn = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;

        byte[] buffer;

        URL urlUrl = new URL(url);
        conn = (HttpURLConnection) urlUrl.openConnection();
        conn.setReadTimeout(300000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        OutputStream os = conn.getOutputStream();

        Set<Map.Entry<String, String>> entries = files.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();

        int i = 0;

        while (iterator.hasNext()) {
            i++;

            Map.Entry<String, String> entry = iterator.next();
            String exsistingFileName = entry.getKey();
            String filePath = entry.getValue();

            write(os, twoHyphens + boundary + lineEnd);
            write(os, "Content-Disposition: form-data; name=\"file" + i + "\";" + " filename=\"" + exsistingFileName + "\"" + lineEnd);
            write(os, lineEnd);

            // create a buffer of maximum size
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                os.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...

            write(os, lineEnd);
            write(os, twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fileInputStream.close();
        }

        os.flush();
        os.close();

        // ------------------ read the SERVER RESPONSE

        BufferedReader inp = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String s, response = "";

        while ((s = inp.readLine()) != null) {
            response = response + s;
        }
        inp.close();

        if (response.startsWith("[uploaded]")) {
            System.out.println(response);

            String paths[] = response.split("\\[uploaded\\]");
            List<String> pathList = new ArrayList<String>();
            for (int j = 0; j < paths.length; j++) {
                if (!paths[j].isEmpty()) {
                    pathList.add(paths[j]);
                }
            }
            return pathList;
        } else {
            throw new ServerException(response);
        }
    }

    public static OculusReportFileUploader getStandardFileUploader() {
        ExperiorConfig config = ExperiorConfig.getInstance();
        return new OculusReportFileUploader(config.get(ExperiorConfig.OCULUS_URL) + "/report/upload-file");
    }

    public static void main(String[] args) throws Exception {

        OculusReportFileUploader uploader = new OculusReportFileUploader("http://localhost:8080/oculus/report/upload-file");

        uploader.addFile("file2.jpg", "d:\\temp\\deel.jpg");
        uploader.upload();
    }
}
