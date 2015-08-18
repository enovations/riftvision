package com.sourcegasm.riftvision;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String currrentDir = new File(".").getAbsolutePath().replaceAll("/\\.", "");
        Process process = Runtime.getRuntime().exec("python "+currrentDir+"/../Sensors/temp.py");


        process.destroy();

    }
}

/*new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = process.getErrorStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null){
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
