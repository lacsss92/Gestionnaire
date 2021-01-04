package com.example.gestionnaire;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.renderscript.ScriptGroup.Input;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.Debug;

public class BackgroundService extends Service {

    private Object EventSystem;
    private Object TouchPhase;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Check if there is a touch
        /*if (Input.touchCount > 0 && Input.GetTouch(0).phase == TouchPhase.Began)
        {
            // Check if finger is over a UI element
            if (EventSystem.current.IsPointerOverGameObject(Input.GetTouch(0).fingerId))
            {
                Debug.Log("Touched the UI");
            }
        }*/
        
        return super.onStartCommand(intent, flags, startId);
    }
}
