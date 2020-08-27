package jp.wasabeef.example.recyclerview

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.BaseItemAnimator
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator
import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

/**
 * Created by Daichi Furiya / Wasabeef on 2020/08/26.
 */
class AnimatorSampleActivity : AppCompatActivity() {

  internal enum class Type(val animator: BaseItemAnimator) {
    FadeIn(FadeInAnimator()),
    FadeInDown(FadeInDownAnimator()),
    FadeInUp(FadeInUpAnimator()),
    FadeInLeft(FadeInLeftAnimator()),
    FadeInRight(FadeInRightAnimator()),
    Landing(LandingAnimator()),
    ScaleIn(ScaleInAnimator()),
    ScaleInTop(ScaleInTopAnimator()),
    ScaleInBottom(ScaleInBottomAnimator()),
    ScaleInLeft(ScaleInLeftAnimator()),
    ScaleInRight(ScaleInRightAnimator()),
    FlipInTopX(FlipInTopXAnimator()),
    FlipInBottomX(FlipInBottomXAnimator()),
    FlipInLeftY(FlipInLeftYAnimator()),
    FlipInRightY(FlipInRightYAnimator()),
    SlideInLeft(SlideInLeftAnimator()),
    SlideInRight(SlideInRightAnimator()),
    SlideInDown(SlideInDownAnimator()),
    SlideInUp(SlideInUpAnimator()),
    OvershootInRight(OvershootInRightAnimator(1.0f)),
    OvershootInLeft(OvershootInLeftAnimator(1.0f))
  }

  private val adapter = MainAdapter(this, SampleData.LIST.toMutableList())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animator_sample)

    setSupportActionBar(findViewById(R.id.tool_bar))
    supportActionBar?.setDisplayShowTitleEnabled(false)

    val recyclerView = findViewById<RecyclerView>(R.id.list)
    recyclerView.apply {
      itemAnimator = SlideInLeftAnimator()
      adapter = this@AnimatorSampleActivity.adapter

      layoutManager = if (intent.getBooleanExtra(MainActivity.KEY_GRID, true)) {
        GridLayoutManager(context, 2)
      } else {
        LinearLayoutManager(context)
      }
    }


    val spinner = findViewById<Spinner>(R.id.spinner)
    val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
    for (type in Type.values()) {
      spinnerAdapter.add(type.name)
    }
    spinner.adapter = spinnerAdapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        recyclerView.itemAnimator = Type.values()[position].animator
        recyclerView.itemAnimator?.addDuration = 500
        recyclerView.itemAnimator?.removeDuration = 500
      }

      override fun onNothingSelected(parent: AdapterView<*>) {
        // no-op
      }
    }

    findViewById<View>(R.id.add).setOnClickListener { adapter.add("newly added item", 1) }

    findViewById<View>(R.id.del).setOnClickListener { adapter.remove(1) }
  }

}
