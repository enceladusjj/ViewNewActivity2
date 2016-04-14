package com.example.jb.viewnewactivity;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.Handler;

import static com.example.jb.viewnewactivity.R.id.editText;
import static com.example.jb.viewnewactivity.R.id.text;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ID = 1;
    public static final String retstrng = "bool not addressed";
    private ServerSocket serverSocket;
    private static final int SERVERPORT = 1755;
    private static final String SERVER_IP = "192.168.0.102";
    public ImageButton button_lights;
    public boolean visibleButton = true;

    private EditText textField;
    Handler updateConversationHandler;
    Thread serverThread = null;

//    WifiConfiguration wifiConfig = new WifiConfiguration();
//    wifiConfig.SSID = String.format("\"%s\"", ssid);
//    wifiConfig.preSharedKey = String.format("\"%s\"", key);
//
//    WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
//    //remember id
//    int netId = wifiManager.addNetwork(wifiConfig);
//    wifiManager.disconnect();
//    wifiManager.enableNetwork(netId, true);
//    wifiManager.reconnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (EditText) findViewById(editText);
        ImageButton buttonHeating = (ImageButton) findViewById(R.id.imageButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        updateConversationHandler = new Handler();

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;

            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {e.printStackTrace();}

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    socket = serverSocket.accept();
                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;
        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {e.printStackTrace();}
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {
                    String read = input.readLine();
                    updateConversationHandler.post(new updateUIThread(read));
                } catch (IOException e) {e.printStackTrace();}
            }
        }
    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }
        @Override
        public void run() {
            textField.setText(textField.getText().toString()+"Client(Sony Black YT9114AHHJ)"+ msg + "\n");
        }
    }

    public void onButtonClick(View v) {

        if (v.getId() == R.id.buttonFilling) {

            ImageButton buttonLights = (ImageButton) findViewById(R.id.buttonLights);

            Log.d("tag", "buttonlights1:" + String.valueOf(buttonLights.getVisibility()));
            Intent i = new Intent(MainActivity.this, heating.class);
            boolean testf = false;
            boolean testt = true;

            if(buttonLights.getVisibility()==View.VISIBLE){

                visibleButton=true;

                Log.d("tag", "visibleButton1: " + visibleButton + " testf:" + testf + " | testt:" +testt);
            }
            else {

                visibleButton=false;
                Log.d("tag", "visibleButton2: " + visibleButton + " testf:" + testf + " | testt:" +testt);
            }
            i.putExtra("visibleButton", visibleButton);
            startActivityForResult(i, REQUEST_ID);
            Log.d("tag", "new activity gestartet");
            EditText et = (EditText) findViewById(editText);
            et.setText("d");
//
//// Anfrage der Verbindungsdaten zum WLAN
//// Definition des WifiManagers
//            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//
//// Aufbau der Verbindung zum WLAN SSID
//
//            String SSID = "TP-LINK_2.4GHz_2E9E63";
//            String KEY = "52649844";
//            WifiConfiguration wifiConfig = new WifiConfiguration();
//            wifiConfig.SSID = String.format("\"%s\"", SSID);
//            wifiConfig.preSharedKey = String.format("\"%s\"", KEY);
//
//// Doppelt:
//// WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
//// remember id
//            int netId = wifiManager.addNetwork(wifiConfig);
//            wifiManager.disconnect();
//            wifiManager.enableNetwork(netId, true);
//            wifiManager.reconnect();
//            Log.d("tag", "before socket");
//            Socket client = null;
//            FileInputStream fileInputStream;
//            BufferedInputStream bufferedInputStream;
//            OutputStream outputStream;
//            Button button;
//            TextView text = null;
//            File file = new File("input.txt"); //create file instance, file to transfer or any data
//
//            Log.d("tag", "try0");
//
//
//        } else if (v.getId() == R.id.buttonSettings) {
//
//            try {
//
//                Log.d("tag", "socket2 start");
//                Socket socket2 = new Socket("192.168.0.102", 1755);
//                DataOutputStream DOS2 = new DataOutputStream(socket2.getOutputStream());
//                DataInputStream DIS = new DataInputStream(socket2.getInputStream());
//                String newStr = "";
//                DIS.readUTF();
//                //DataOutputStream DOS = new DataOutputStream(socket2.getOutputStream());
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
//                EditText et = (EditText) findViewById(editText);
//                et.setText("t");
//                socket2.close();
//                if(socket2.isConnected()){
//
//                }
//            } catch (Exception e) {System.out.print("error ?");}
//        }
//    }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ID) {
            if (resultCode == RESULT_OK) {

                boolean retValue = data.getBooleanExtra("selected", heating.selected);
                Log.d("tag1", Boolean.toString(retValue));
                CheckBox cbox = (CheckBox) findViewById(R.id.checkBox);
                button_lights = (ImageButton) findViewById(R.id.buttonLights);

                Log.d("tag2", Boolean.toString(retValue));
                if(!retValue) {

                    button_lights.setVisibility(View.INVISIBLE);
                }
                else if(retValue){

                    button_lights.setVisibility(View.VISIBLE);
                }
                else{
                    System.out.print("Hey");
                }
            }
        }
    }
}