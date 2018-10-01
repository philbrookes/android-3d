package pbrookes.philthi.game;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import pbrookes.philthi.android3d.Engine;

public class App extends Activity {

    private AppView appView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        appView = new AppView(this);
        appView.setEGLContextClientVersion(2);
        appView.setRenderer(new AppRenderer(this));
        setContentView(appView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
