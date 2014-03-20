package com.donnfelker.android.bootstrap.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.donnfelker.android.bootstrap.core.Work;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.donnfelker.android.bootstrap.R;

import static com.donnfelker.android.bootstrap.core.Constants.*;
import com.beardedhen.androidbootstrap.FontAwesomeText;

import java.util.List;

/**
 * Created by Feather on 14-3-17.
 */
public class WorkListAdapter extends SingleTypeAdapter<Work> {

    /**
     * @param inflater
     * @param items
     */
    public WorkListAdapter(final LayoutInflater inflater, final List<Work> items) {
        super(inflater, R.layout.work_list_item);

        setItems(items);
    }
    /**
     * @param inflater
     */
    public WorkListAdapter(final LayoutInflater inflater) {
        this(inflater, null);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_icon, R.id.tv_type, R.id.tv_stage};
    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected void update(final int position, final Work work) {
        String str = null;
        String icon = "fa-github";
        switch (Substation.indexOf(work.getType())) {
        case 1:
            str = "全面巡检"; icon = "fa-glass"; break;
        case 2:
            str = "日常巡检"; icon = "fa-music"; break;
        case 3:
            str = "雷雨特殊巡检"; break;
        case 4:
            str = "雪天特殊巡检"; break;
        case 5:
            str = "大雾特殊巡检"; break;
        case 6:
            str = "大风特殊巡检"; break;
        case 7:
            str = "夜间熄灯特殊巡检"; break;
        case 8:
            str = "设备异常缺陷跟踪特殊巡检"; break;
        case 9:
            str = "红外线测试作业"; break;
        case 10:
            str = "主变冷却器切换试验作业"; break;
        case 11:
            str = "事故照明切换作业"; break;
        case 12:
            str = "蓄电池定期测试作业"; break;
        case 13:
            str = "轮换作业"; break;
        case 14:
            str = "设备定期维护作业"; break;
        case 15:
            str = "道闸操作作业"; break;
        }
        getView(0, FontAwesomeText.class).setIcon(icon);
        setText(1, String.format("%s", str));
        setText(2, String.format("%s", "TODO"));
    }
}
