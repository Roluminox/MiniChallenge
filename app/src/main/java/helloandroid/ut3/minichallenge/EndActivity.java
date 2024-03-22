package helloandroid.ut3.minichallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity {
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Button mRestartButton = findViewById(R.id.restart_buttton);
        mRestartButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Button mAccueilButton = findViewById(R.id.accueil_button);
        mAccueilButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        });

        scoreText = findViewById(R.id.score_text);

        Intent intent = getIntent();
        if(intent != null) {
            String score = intent.getStringExtra("score");
            if(score != null) {
                scoreText.setText(score);
            }
        }
    }
}
