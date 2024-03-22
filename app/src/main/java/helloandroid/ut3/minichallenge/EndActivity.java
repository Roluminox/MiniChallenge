package helloandroid.ut3.minichallenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EndActivity extends Activity {
    private Button mAccueilButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        mAccueilButton = findViewById(R.id.accueil_button);
        mAccueilButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent);
        });
    }
}
