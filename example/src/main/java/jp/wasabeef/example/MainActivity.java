package jp.wasabeef.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FlipBottomInXAnimator;
import jp.wasabeef.recyclerview.animators.FlipLeftInYAnimator;
import jp.wasabeef.recyclerview.animators.FlipRightInYAnimator;
import jp.wasabeef.recyclerview.animators.FlipTopInXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideBottomAnimator;
import jp.wasabeef.recyclerview.animators.SlideLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideTopAnimator;


/**
 * Created by Wasabeef on 2015/01/03.
 */
public class MainActivity extends ActionBarActivity {

    enum Type {
        FadeIn("FadeIn", new FadeInAnimator()),
        ScaleIn("ScaleIn", new ScaleInAnimator()),
        FlipTopInX("FlipTopInX", new FlipTopInXAnimator()),
        FlipBottomInX("FlipBottomInX", new FlipBottomInXAnimator()),
        FlipLeftInY("FlipLefIntY", new FlipLeftInYAnimator()),
        FlipRightInY("FlipRightInY", new FlipRightInYAnimator()),
        SlideLeft("SlideLeft", new SlideLeftAnimator()),
        SlideRight("SlideRight", new SlideRightAnimator()),
        SlideTop("SlideTop", new SlideTopAnimator()),
        SlideBottom("SlideBottom", new SlideBottomAnimator());

        private String mTitle;
        private BaseItemAnimator mAnimator;

        Type(String title, BaseItemAnimator animator) {
            mTitle = title;
            mAnimator = animator;
        }

        public BaseItemAnimator getAnimator() {
            return mAnimator;
        }

        public String getTitle() {
            return mTitle;
        }
    }

    private static String[] data = new String[]{
            "Apple", "Ball", "Camera", "Day", "Egg", "Foo", "Google", "Hello", "Iron", "Japan",
            "Coke", "Dog", "Cat", "Yahoo", "Sony", "Canon", "Fujitsu", "USA", "Nexus", "LINE",
            "Haskell", "C++", "Java", "Go", "Swift", "Objective-c", "Ruby", "PHP", "Bash", "ksh",
            "C", "Groovy", "Kotlin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeInAnimator());
        final MainAdapter adapter = new MainAdapter(this, new ArrayList<>(Arrays.asList(data)));
        recyclerView.setAdapter(adapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (Type type : Type.values()) {
            spinnerAdapter.add(type.getTitle());
        }
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recyclerView.setItemAnimator(Type.values()[position].getAnimator());
                recyclerView.getItemAnimator().setAddDuration(1000);
                recyclerView.getItemAnimator().setRemoveDuration(1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add("newly added item", 3);
            }
        });

        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(3);
            }
        });
    }
}
