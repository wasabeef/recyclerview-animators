package jp.wasabeef.recyclerview.animators.holder;
import android.support.annotation.NonNull;

import java.util.List;

public interface AnimateChangeViewHolder {

    boolean canAnimateChange(@NonNull List<Object> payloads);

}
