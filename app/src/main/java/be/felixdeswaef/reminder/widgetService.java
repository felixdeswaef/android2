package be.felixdeswaef.reminder;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.RemoteViewsService;


public class widgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new widgetAdapter(this, intent);
    }
}