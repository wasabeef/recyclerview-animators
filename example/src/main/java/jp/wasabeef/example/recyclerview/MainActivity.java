package jp.wasabeef.example.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

/**
 * Created by Wasabeef on 2015/03/08.
 */
public class MainActivity extends AppCompatActivity {

  private boolean enabledGrid = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.btn_animator_sample).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, AnimatorSampleActivity.class);
        i.putExtra("GRID", enabledGrid);
        startActivity(i);
      }
    });

    findViewById(R.id.btn_adapter_sample).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, AdapterSampleActivity.class);
        i.putExtra("GRID", enabledGrid);
        startActivity(i);
      }
    });

    ((SwitchCompat) findViewById(R.id.grid)).setOnCheckedChangeListener(
        new CompoundButton.OnCheckedChangeListener() {
          @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            enabledGrid = isChecked;
          }
        });
  }
}
