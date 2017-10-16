package com.bat.iron.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    Switch s;
    TextView tv;
    BluetoothAdapter ba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        s=(Switch)findViewById(R.id.switch1);
        tv=(TextView)findViewById(R.id.textView2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(i);
            }
        });
        ba=BluetoothAdapter.getDefaultAdapter();
        if(ba==null)
            Toast.makeText(getApplicationContext(), "Bluetooth Not working", Toast.LENGTH_SHORT).show();

        boolean status=ba.isEnabled();
        if(status) {
            String op="Device status :ON";
            String dn=ba.getName();
            String mac=ba.getAddress();
            op=op+"\nDevice :"+dn+"\nMac Address :"+mac;
            tv.setText(op);
            tv.setTextColor(Color.BLUE);
            s.setChecked(true);
        }

        else {
            tv.setText("Device Status :OFF");
            tv.setTextColor(Color.RED);
            s.setChecked(false);
        }

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ba.enable();
                    String op="Device status:ON";
                    String dn=ba.getName();
                    String mac=ba.getAddress();
                    op=op+"\nDevice :"+dn+"\nMac Address :"+mac;
                    tv.setText(op);
                    tv.setTextColor(Color.BLUE);
                }
                else
                {
                    ba.disable();
                    tv.setText("Device Status :OFF");
                    tv.setTextColor(Color.RED);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object []device=ba.getBondedDevices().toArray();
                String data="Other Devices\n----------";
                for(int i=0;i<device.length;i++)
                {
                    BluetoothDevice d=(BluetoothDevice)device[i];
                    String dn=d.getName();
                    data=data+"\n"+dn;
                }
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
