package edu.jxsd.x3510.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
public class ConfigWindow extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("enter ConfigWindow.onCreate()!");
        
        super.onCreate(savedInstanceState);
   

    }
    
 /*   private void launchFetion() {
        Intent i = new Intent(this, FuncSelector.class);
        i.putExtra(KEY_LOGIN_NAME, mServerIP.getText().toString());
        i.putExtra(KEY_LOGIN_PASSWD, mServerPort.getText().toString());
        
        startActivity(i);
    }*/
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
//        System.out.println("enter onKeyDown() in LoginWindow!");
//        
//        if (null != loginBtnListener) {
//            View aview = getCurrentFocus();
//            loginBtnListener.onClick(aview);
//        }
        return false;
    }
    
    public static String getServerIp() {
                return serverIp;
        }
        public static String getServerPort() {
                return serverPort;
        }

    
    public static String serverIp = "192.168.0.98";  
    public static String serverPort = "8081";
}