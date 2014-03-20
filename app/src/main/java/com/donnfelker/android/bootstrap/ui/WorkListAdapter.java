package com.donnfelker.android.bootstrap.ui;

import android.graphics.Color;
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
        String str = "无";
        String icon = "fa-question";
        int color = Color.rgb(255, 0, 0);
        FontAwesomeText fat = getView(0, FontAwesomeText.class);
        switch (Substation.indexOf(work.getType())) {
        case 1:
            str = "全面巡检"; icon = "fa-check-square"; break;
        case 2:
            str = "日常巡检"; icon = "fa-tag"; break;
        case 3:
            str = "雷雨特殊巡检"; icon = "fa-bolt"; break;
        case 4:
            str = "雪天特殊巡检"; icon = "fa-spinner"; break;
        case 5:
            str = "大雾特殊巡检"; icon = "fa-cog"; break;
        case 6:
            str = "大风特殊巡检"; icon = "fa-cog"; break;
        case 7:
            str = "夜间熄灯特殊巡检"; icon = "fa-lightbulb-o"; break;
        case 8:
            str = "设备异常缺陷跟踪特殊巡检"; icon = "fa-bell"; break;
        case 9:
            str = "红外线测试作业"; icon = "fa-long-arrow-up"; break;
        case 10:
            str = "主变冷却器切换试验作业"; icon = "fa-exchange"; break;
        case 11:
            str = "事故照明切换作业"; icon = "fa-sun-o"; break;
        case 12:
            str = "蓄电池定期测试作业"; icon = "fa-adjust"; break;
        case 13:
            str = "轮换作业"; icon = "fa-refresh"; break;
        case 14:
            str = "设备定期维护作业"; icon = "fa-wrench"; break;
        case 15:
            str = "道闸操作作业"; icon = "fa-random"; break;
        default:
            break;
        }
        work.setStage("待完成");
        fat.setIcon(icon);
        fat.setTextColor(color);
        setText(1, String.format("%s", str));
        setText(2, String.format("%s", work.getStage()));
    }
}
