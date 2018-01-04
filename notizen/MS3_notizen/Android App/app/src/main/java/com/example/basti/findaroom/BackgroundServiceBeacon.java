package com.example.basti.findaroom;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Basti on 31.12.2017.
 */

public class BackgroundServiceBeacon extends IntentService {

    public BackgroundServiceBeacon(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dataString = intent.getDataString();

    }
}
