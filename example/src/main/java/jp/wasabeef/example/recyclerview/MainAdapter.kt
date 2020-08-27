package jp.wasabeef.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * Created by Daichi Furiya / Wasabeef on 2020/08/26.
 */
class MainAdapter(private val context: Context, private val dataSet: MutableList<String>) :
  RecyclerView.Adapter<MainAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false)
    return ViewHolder(v)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    Picasso.get().load(R.drawable.chip).into(holder.image)
    holder.text.text = dataSet[position]
  }

  override fun getItemCount(): Int {
    return dataSet.size
  }

  fun remove(position: Int) {
    dataSet.removeAt(position)
    notifyItemRemoved(position)
  }

  fun add(text: String, position: Int) {
    dataSet.add(position, text)
    notifyItemInserted(position)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var image: ImageView = itemView.findViewById<View>(R.id.image) as ImageView
    var text: TextView = itemView.findViewById<View>(R.id.text) as TextView

  }
}
