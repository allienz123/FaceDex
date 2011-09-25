package com.facedex;
import com.facedex.service.FacedexService;
import com.facedex.service.FacedexService.LocalBinder;
//import com.facedex.service.MessengerService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartFacesActivity extends Activity {
	FacedexService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);      
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to FacedexService
        // Bind to the service
        bindService(new Intent(this, FacedexService.class), mConnection,
            Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    
    /**
     * Asynctask using the service
     */
    private class NewPhotoTask extends AsyncTask<Void, Integer, String> {
        protected String doInBackground(Void... urls) {
            //return mService.getRandomNumber();
        	return mService.getLOL();
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            gotResultFromService(result);        	
        }
    }
    
    public void gotResultFromService(String result) {
		Toast.makeText(this, "lol: " + result, Toast.LENGTH_LONG).show();
		
	}
    
    
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

    	public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            new NewPhotoTask().execute();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


	}