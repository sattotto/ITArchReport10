package com.github.sattotto.itarchreport10;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Random;

public class DiceRoll extends Service {

    private int DiceCount;
    private int DiceType;
    private int[] DiceResult;

    public DiceRoll() {  }

    private final DiceRollAIDL.Stub mBinder = new DiceRollAIDL.Stub() {
        @Override
        public int getDiceCount() throws RemoteException {
            return DiceCount;
        }

        @Override
        public int getDiceType() throws RemoteException {
            return DiceType;
        }

        @Override
        public void setDiceType(int count, int type) throws RemoteException {
            DiceCount = count;
            DiceType  = type;
        }

        @Override
        public int[] getDiceRollResult() throws RemoteException {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (DiceCount > 10) {
                DiceResult = new int[1];
                DiceResult[0] = 0;
                return DiceResult;
            } else {
                DiceResult = new int[DiceCount];
            }
            Random dice = new Random();

            for (int i = 0;i < DiceCount;i++) {
                DiceResult[i] = dice.nextInt(DiceType) + 1;
            }

            return DiceResult;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {}
    };

    @Override
    public IBinder onBind(Intent intent) { return mBinder; }
}
