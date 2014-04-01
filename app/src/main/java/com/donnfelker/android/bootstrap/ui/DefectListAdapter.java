package com.donnfelker.android.bootstrap.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Defect;
import java.util.List;

/**
 * Created by Feather on 14-4-1.
 */
public class DefectListAdapter extends AlternatingColorListAdapter<Defect> {

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public DefectListAdapter(final LayoutInflater inflater, final List<Defect> items,
                           final boolean selectable) {
        super(R.layout.defect_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public DefectListAdapter(final LayoutInflater inflater, final List<Defect> items) {
        super(R.layout.defect_list_item, inflater, items);
    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_device, R.id.tv_description};
    }

    @Override
    protected void update(final int position, final Defect defect) {
        super.update(position, defect);
        setText(0, defect.getDeviceid());
        setText(1, defect.getDescription());
    }
}
