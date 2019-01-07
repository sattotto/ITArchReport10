package com.github.sattotto.itarchreport10;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private int[] DiceResult;
    private DiceRollAIDL myDice = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myDice = DiceRollAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myDice = null;
        }
    };

    public void ThrowDice(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this).setPositiveButton("さいころを振る", null);

        EditText diceCountText = findViewById(R.id.diceCount);
        EditText diceTypeText = findViewById(R.id.diceType);
        try {
            myDice.setDiceType(Integer.parseInt(diceCountText.getText().toString()), Integer.parseInt(diceTypeText.getText().toString()));

            alert.setMessage(myDice.getDiceCount() + "D" + myDice.getDiceType() + "のさいころを振ります").setPositiveButton("さいころを振る", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        DiceResult = myDice.getDiceRollResult();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (RemoteException e) {
            alert.setMessage("さいころをご用意できませんでした").show();
            e.printStackTrace();
        }


    }

    private void showDiceResult(int[] DiceArray) {
        AlertDialog.Builder DiceDialog = new AlertDialog.Builder(this).setPositiveButton("おけ", null);

        String resultText = "";
        for (int i = 0;i < DiceResult.length;i++) {
            resultText += DiceResult[i];
            if (i == DiceResult.length) break;
        }

        DiceDialog.setMessage("結果は " + resultText + "でした．");
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(resultText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent DiceService = new Intent(this, DiceRoll.class);
        bindService(DiceService, mConnection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
