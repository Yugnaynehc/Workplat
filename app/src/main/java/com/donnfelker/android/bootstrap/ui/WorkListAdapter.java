package com.donnfelker.android.bootstrap.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
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

    private FragmentActivity activity;

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
     * @param activity
     * @param items
     */
    public WorkListAdapter(final FragmentActivity activity, final List<Work> items) {
        super(R.layout.work_list_item, activity.getLayoutInflater(), items);
        this.activity = activity;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_icon, R.id.tv_type, R.id.tv_status};
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
            str = activity.getString(R.string.inspect_normal_total);
            icon = "fa-check-square";
            break;
        case 2:
            str = activity.getString(R.string.inspect_normal_daily);
            icon = "fa-tag";
            break;
        case 3:
            str = activity.getString(R.string.inspect_special_thunderstorm);
            icon = "fa-bolt";
            break;
        case 4:
            str = activity.getString(R.string.inspect_special_snowy);
            icon = "fa-spinner";
            break;
        case 5:
            str = activity.getString(R.string.inspect_special_foggy);
            icon = "fa-eye";
            break;
        case 6:
            str = activity.getString(R.string.inspect_special_windy);
            icon = "fa-cog";
            break;
        case 7:
            str = activity.getString(R.string.inspect_special_nightlight);
            icon = "fa-lightbulb-o";
            break;
        case 8:
            str = activity.getString(R.string.inspect_special_bugtrace);
            icon = "fa-bell";
            break;
        case 9:
            str = activity.getString(R.string.inspect_job_infraredtesting);
            icon = "fa-arrow-up";
            break;
        case 10:
            str = activity.getString(R.string.inspect_job_switchcooler);
            icon = "fa-exchange";
            break;
        case 11:
            str = activity.getString(R.string.inspect_job_emergencylightswitch);
            icon = "fa-sun-o";
            break;
        case 12:
            str = activity.getString(R.string.inspect_job_batteryperiodictesting);
            icon = "fa-adjust";
            break;
        case 13:
            str = activity.getString(R.string.inspect_job_deviceperiodictestingrotation);
            icon = "fa-refresh";
            break;
        case 14:
            str = activity.getString(R.string.inspect_job_deviceperiodicmaintance);
            icon = "fa-wrench";
            break;
        case 15:
            str = activity.getString(R.string.inspect_job_barriergateoperate);
            icon = "fa-random";
            break;
        default:
            break;
        }

        fat.setIcon(icon);
        fat.setTextColor(color);
        setText(1, String.format("%s", str));
        if (work.getStatus() == 0) {
            setText(2, String.format("%s", activity.getString(R.string.work_todo)))
                    .setTextColor(activity.getResources().getColor(R.color.dark_blue));
        }
        else
            setText(2, String.format("%s", activity.getString(R.string.work_done)))
                    .setTextColor(activity.getResources().getColor(R.color.dark_green));
    }
}
