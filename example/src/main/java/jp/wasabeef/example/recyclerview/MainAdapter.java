package jp.wasabeef.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Wasabeef on 2015/01/03.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private Context mContext;
  private List<String> mDataSet;

  public MainAdapter(Context context, List<String> dataSet) {
    mContext = context;
    mDataSet = dataSet;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Picasso.with(mContext).load(R.drawable.chip).into(holder.image);
    holder.text.setText(mDataSet.get(position));
  }

  @Override public int getItemCount() {
    return mDataSet.size();
  }

  public void remove(int position) {
    mDataSet.remove(position);
    notifyItemRemoved(position);
  }

  public void add(String text, int position) {
    mDataSet.add(position, text);
    notifyItemInserted(position);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView text;

    public ViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image);
      text = (TextView) itemView.findViewById(R.id.text);
    }
  }
}
