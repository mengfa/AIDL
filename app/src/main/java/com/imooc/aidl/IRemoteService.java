package com.imooc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class IRemoteService extends Service {
    public IRemoteService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    private IBinder iBinder = new IImoocAidl.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    };
}
