package com.imooc.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imooc.aidl.IImoocAidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtNum1;
    private EditText mEtNum2;;
    private Button mBtnAdd;
    private  EditText mEtRes;
    IImoocAidl iImoocAidl;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iImoocAidl =  IImoocAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iImoocAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        bindService();

    }
    private void initView(){
        mEtNum1 = (EditText) findViewById(R.id.et_num1);
        mEtNum2 = (EditText) findViewById(R.id.et_num2);
        mEtRes = (EditText) findViewById(R.id.et_res);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
    }
    public void onClick(View view){
        int num1 = Integer.parseInt(mEtNum1.getText().toString());
        int num2 = Integer.parseInt(mEtNum2.getText().toString());

        try {
            int res = iImoocAidl.add(num1,num2);
            mEtRes.setText(res+"");
        } catch (RemoteException e) {
            e.printStackTrace();
            mEtRes.setText("wrong");

        }

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.imooc.aidl","com.imooc.aidl.IRemoteService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
