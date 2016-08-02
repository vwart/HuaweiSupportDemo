package de.grad.huaweisupportdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "[MainActivity]";
    private File latestLogFile = null;
    private TextView latestFileTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView info = (TextView) findViewById(R.id.info_text);
        TextView src = (TextView) findViewById(R.id.source);
        latestFileTxt = (TextView) findViewById(R.id.latest_file);
        info.setText("We can reproduce the missing log in our office on:\n" +
                "Model: HUAWEI M2-801L\n" +
                "Build: M2-801LV100R001C100B005\n" +
                "Android-Version: 5.1.1\n" +
                "Baseband: 21.600.05.00.010\n" +
                "Kernel: 3.10.74-g7ff3040 android@localhost #2 Mon Jan 18 16:47:34 CST 2016\n" +
                "EMUI: EMUI 3.1\n" +
                "Custom Version: CUSTC100D002\n" +
                "IMEI: 867104020595619");
        src.setText(
            "String fileName = \"logcat_\"+System.currentTimeMillis()+\".txt\";\n" +
                "            File outputFile = new File(this.getExternalFilesDir(\"directory\"),fileName);\n" +
                "            Process process1 = Runtime.getRuntime().exec(\"logcat -f \"+outputFile.getAbsolutePath());");
    }

    public void produceLog(View v) {
        Log.d(TAG, "d  ## MainActivity.produceLog ## PRODUCED LOG ##");
        Log.i(TAG, "i  ## MainActivity.produceLog ## PRODUCED LOG ##");
        Log.v(TAG, "v  ## MainActivity.produceLog ## PRODUCED LOG ##");
        Log.e(TAG, "e  ## MainActivity.produceLog ## PRODUCED LOG ##", new NullPointerException("## EXCEPTION PRODUCED LOG ##"));
        Toast.makeText(this, "Log produced on all levels (i.e. 'd', 'i', 'v' and 'e')", Toast.LENGTH_SHORT).show();
    }

    public void exportLog(View v){
        try {
            Log.d(TAG, "d  ## MainActivity.exportLog");
            Log.i(TAG, "i  ## MainActivity.exportLog");
            Log.v(TAG, "v  ## MainActivity.exportLog");
            Log.e(TAG, "e  ## MainActivity.exportLog", new NullPointerException("some test exception"));

            String fileName = "logcat_"+System.currentTimeMillis()+".txt";
            File outputFile = new File(this.getExternalFilesDir("directory"),fileName);
            Process process1 = Runtime.getRuntime().exec("logcat -f "+outputFile.getAbsolutePath());
            Toast.makeText(this, "Log file >" + fileName + "< exported to " + outputFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            latestLogFile = new File(outputFile, fileName);
            if(latestFileTxt != null)
                latestFileTxt.setText(latestLogFile.getAbsolutePath());
            {

//                Process process = Runtime.getRuntime().exec("log -d");
//                BufferedReader bufferedReader = new BufferedReader(
//                        new InputStreamReader(process.getInputStream()));
//
//                StringBuilder log = new StringBuilder();
//                String line = "";
//                while ((line = bufferedReader.readLine()) != null) {
//                    log.append(line);
//                }
//                actualLog = log.toString();
            }
            //clear log after writing
//            Runtime.getRuntime().exec("logcat -c");
        }
        catch (Exception e) {
            Toast.makeText(this, "Exception caught: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void openLatestLogFile(View v) {
        try {
            if(latestLogFile == null)
                Toast.makeText(this, "No log file exported during this session", Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(latestLogFile), "text/plain");
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception caught: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
