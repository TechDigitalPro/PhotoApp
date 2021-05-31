package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadRawResources {
    public static String readTextFileFromRawResource(Context context, int resourceId) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resourceId)));
        StringBuilder body = new StringBuilder();
        while (true) {
            String nextLine = null;
            try {
                nextLine = bufferedReader.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (nextLine == null) {
                return body.toString();
            }
            body.append(nextLine);
            body.append('\n');
        }
    }
}
