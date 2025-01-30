package imo.t;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		TextView testText = findViewById(R.id.test_text);
		TextEditor textEditor = findViewById(R.id.text_editor);

		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmonoregular.ttf");

		testText.setTypeface(typeface);
		textEditor.setTypeface(typeface);

		testText.setText("this is a text from a textview with a custom font");
		textEditor.setText("this is a text from the custom view with a custom font");
    }
}
