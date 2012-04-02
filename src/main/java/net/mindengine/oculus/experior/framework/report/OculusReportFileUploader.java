/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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
package net.mindengine.oculus.experior.framework.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.ServerException;

import net.mindengine.oculus.experior.ExperiorConfig;

public class OculusReportFileUploader {
    private String url;
    private int maxBufferSize = 1024;
    
    public OculusReportFileUploader(String url) {
        this.url = url;
    }

    private void write(OutputStream os, String text) throws Exception {
        os.write(text.getBytes());
    }
    
    /**
     * Uploads a file to oculus frontend
     * system
     * 
     * @return Id of a file in oculus frontend
     * @throws Exception
     */
    public String upload(File file) throws Exception {
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

        write(os, twoHyphens + boundary + lineEnd);
        write(os, "Content-Disposition: form-data; name=\"file1\";" + " filename=\"" + file.getName() + "\"" + lineEnd);
        write(os, lineEnd);

        // create a buffer of maximum size
        FileInputStream fileInputStream = new FileInputStream(file);

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
            String paths[] = response.split("\\[uploaded\\]");
            
            for (int j = 0; j < paths.length; j++) {
                if (!paths[j].trim().isEmpty()) {
                    return paths[j].trim();
                }
            }
        } 
        throw new ServerException("Could not read the response: " + response);
    }

    public static OculusReportFileUploader getStandardFileUploader() {
        ExperiorConfig config = ExperiorConfig.getInstance();
        return new OculusReportFileUploader(config.get(ExperiorConfig.OCULUS_URL) + "/report/upload-file");
    }
    
}
