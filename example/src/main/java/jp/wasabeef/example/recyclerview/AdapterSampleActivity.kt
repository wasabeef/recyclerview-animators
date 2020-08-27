package jp.wasabeef.example.recyclerview

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.AnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
import jp.wasabeef.recyclerview.animators.FadeInAnimator

/**
 * Created by Daichi Furiya / Wasabeef on 2020/08/26.
 */
class AdapterSampleActivity : AppCompatActivity() {

  internal enum class Type {
    AlphaIn {
      override operator fun get(context: Context): AnimationAdapter {
        return AlphaInAnimationAdapter(MainAdapter(context, SampleData.LIST.toMutableList()))
      }
    },
    ScaleIn {
      override operator fun get(context: Context): AnimationAdapter {
        return ScaleInAnimationAdapter(MainAdapter(context, SampleData.LIST.toMutableList()))
      }
    },
    SlideInBottom {
      override operator fun get(context: Context): AnimationAdapter {
        return SlideInBottomAnimationAdapter(MainAdapter(context, SampleData.LIST.toMutableList()))
      }
    },
    SlideInLeft {
      override operator fun get(context: Context): AnimationAdapter {
        return SlideInLeftAnimationAdapter(MainAdapter(context, SampleData.LIST.toMutableList()))
      }
    },
    SlideInRight {
      override operator fun get(context: Context): AnimationAdapter {
        return SlideInRightAnimationAdapter(MainAdapter(context, SampleData.LIST.toMutableList()))
      }
    };

    abstract operator fun get(context: Context): AnimationAdapter
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_adapter_sample)

    setSupportActionBar(findViewById(R.id.tool_bar))
    supportActionBar?.setDisplayShowTitleEnabled(false)

    val recyclerView = findViewById<RecyclerView>(R.id.list)
    recyclerView.layoutManager = if (intent.getBooleanExtra(MainActivity.KEY_GRID, true)) {
      GridLayoutManager(this, 2)
    } else {
      LinearLayoutManager(this)
    }

    val spinner = findViewById<Spinner>(R.id.spinner)
    spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1).apply {
      for (type in Type.values()) add(type.name)
    }
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        recyclerView.adapter = Type.values()[position][view.context].apply {
          setFirstOnly(true)
          setDuration(500)
          setInterpolator(OvershootInterpolator(.5f))
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>) {
        // no-op
      }
    }

    recyclerView.itemAnimator = FadeInAnimator()
    val adapter = MainAdapter(this, SampleData.LIST.toMutableList())
    recyclerView.adapter = AlphaInAnimationAdapter(adapter).apply {
      setFirstOnly(true)
      setDuration(500)
      setInterpolator(OvershootInterpolator(.5f))
    }
  }
}
