package imo.t;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		TextView testText = findViewById(R.id.test_text);
		testText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmonoregular.ttf"));
    }
}
