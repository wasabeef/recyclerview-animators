package jp.wasabeef.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Wasabeef on 2015/01/03.
 */
public class AdapterSampleActivity extends AppCompatActivity {

  private static String[] data = new String[] {
      "Apple", "Ball", "Camera", "Day", "Egg", "Foo", "Google", "Hello", "Iron", "Japan", "Coke",
      "Dog", "Cat", "Yahoo", "Sony", "Canon", "Fujitsu", "USA", "Nexus", "LINE", "Haskell", "C++",
      "Java", "Go", "Swift", "Objective-c", "Ruby", "PHP", "Bash", "ksh", "C", "Groovy", "Kotlin",
      "Chip", "Japan", "U.S.A", "San Francisco", "Paris", "Tokyo", "Silicon Valley", "London",
      "Spain", "China", "Taiwan", "Asia", "New York", "France", "Kyoto", "Android", "Google",
      "iPhone", "iPad", "iPod", "Wasabeef"
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_adapter_sample);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

    if (getIntent().getBooleanExtra("GRID", true)) {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    } else {
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    recyclerView.setItemAnimator(new FadeInAnimator());
    MainAdapter adapter = new MainAdapter(this, new ArrayList<>(Arrays.asList(data)));
    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
    ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
    //        scaleAdapter.setFirstOnly(false);
    //        scaleAdapter.setInterpolator(new OvershootInterpolator());
    recyclerView.setAdapter(scaleAdapter);
  }
}
