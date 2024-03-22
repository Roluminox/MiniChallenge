package helloandroid.ut3.minichallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class StartActivity extends Activity {
    Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ConstraintLayout layout = findViewById(R.id.background_start_layout);
        layout.setBackground(ContextCompat.getDrawable(this.getBaseContext(), R.drawable.background));

        mStartButton = findViewById(R.id.start_button);
        mStartButton.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
