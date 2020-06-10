package com.main.voiceoperationapp.logger;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Logger extends Thread {
    private Context context;
    public Logger(Context context) {
        this.context = context;
    }
    @Override
    public void run() {
        Process proc = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        final String pId =  Integer.toString(android.os.Process.myPid());
        try {
            proc = Runtime.getRuntime().exec(new String[] { "logcat", "-v", "time"});
            reader = new BufferedReader(new InputStreamReader(proc.getInputStream()), 1024);
            String line;
            while ( true ) {
                line = reader.readLine();
                if (line.length() == 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }
                if (line.indexOf(pId) != -1) {
                    try {
                        OutputStream out;
                        out = context.openFileOutput("log.text", Context.MODE_PRIVATE|Context.MODE_APPEND);
                        writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
                        writer.println(line);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (writer != null) {
                            writer.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}