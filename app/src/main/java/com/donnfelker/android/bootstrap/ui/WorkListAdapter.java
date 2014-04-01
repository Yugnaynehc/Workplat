package com.donnfelker.android.bootstrap.ui;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;

import java.util.List;

import static com.donnfelker.android.bootstrap.core.Constants.Substation;

/**
 * Created by Feather on 14-3-17.
 */
public class WorkListAdapter extends AlternatingColorListAdapter<Work> {

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public WorkListAdapter(final LayoutInflater inflater, final List<Work> items,
                           final boolean selectable) {
        super(R.layout.work_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public WorkListAdapter(final LayoutInflater inflater, final List<Work> items) {
        super(R.layout.work_list_item, inflater, items);
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
        super.update(position, work);
        String str = "无";
        String icon = "fa-question";
        int color = Color.rgb(0, 150, 0);
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
            str = "大雾特殊巡检"; icon = "fa-eye"; break;
        case 6:
            str = "大风特殊巡检"; icon = "fa-cog"; break;
        case 7:
            str = "夜间熄灯特殊巡检"; icon = "fa-lightbulb-o"; break;
        case 8:
            str = "设备异常缺陷跟踪特殊巡检"; icon = "fa-bell"; break;
        case 9:
            str = "红外线测试作业"; icon = "fa-arrow-up"; break;
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
        if (work.getStage() == null)
            work.setStage("待完成");
        fat.setIcon(icon);
        fat.setTextColor(color);
        setText(1, String.format("%s", str));
        setText(2, String.format("%s", work.getStage()));
    }
}
