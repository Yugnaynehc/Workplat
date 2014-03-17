package com.donnfelker.android.bootstrap.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.donnfelker.android.bootstrap.core.Work;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.donnfelker.android.bootstrap.R;

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
        return new int[]{R.id.iv_icon, R.id.tv_type};
    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected void update(final int position, final Work work) {
        setText(1, String.format("%1$s %2$s", work.getType(), work.getDate()));
    }
}
