package pbrookes.philthi.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        Button newGameButton = (Button) findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, LevelActivity.class);
                myIntent.putExtra("level", "level_1"); //Optional parameters
                MainMenuActivity.this.startActivity(myIntent);
            }
        });
        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, LevelActivity.class);
                myIntent.putExtra("continue", true);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
