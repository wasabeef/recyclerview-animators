package jp.wasabeef.recyclerview.animators.holder;
import android.support.annotation.NonNull;

import java.util.List;

public interface AnimateChangeViewHolder {

    boolean canReuseUpdatedViewHolder(@NonNull List<Object> payloads);

}
