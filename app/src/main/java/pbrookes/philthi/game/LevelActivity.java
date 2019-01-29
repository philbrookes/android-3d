package pbrookes.philthi.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LevelActivity extends Activity {

    private LevelView levelView;
    public String levelName;
    public boolean cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        levelName = intent.getStringExtra("level");
        cont = intent.getBooleanExtra("continue", false);

        setContentView(R.layout.activity_app);
        levelView = new LevelView(this);
        levelView.setEGLContextClientVersion(2);
        levelView.setRenderer(new LevelRenderer(this));
        setContentView(levelView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
