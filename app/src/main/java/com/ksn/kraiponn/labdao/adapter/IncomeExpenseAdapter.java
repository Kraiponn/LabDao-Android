package com.ksn.kraiponn.labdao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.manager.IcExpChildItem;
import com.ksn.kraiponn.labdao.manager.IcExpSectionItem;

import java.text.NumberFormat;
import java.util.List;

public class IncomeExpenseAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List mItems;
    private final int SECTION_ITEM = 0;
    private final int CHILD_ITEM = 1;
    private boolean mIsFirstChild = true;

    public IncomeExpenseAdapter(Context mContext, List<String> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == SECTION_ITEM) {
            View view = inflater.inflate(
                    R.layout.income_expense_section_layout,
                    parent, false);
            IcExpSectionHolder holder = new IcExpSectionHolder(view);
            return holder;
        } else if (viewType == CHILD_ITEM) {
            View view = inflater.inflate(
                    R.layout.income_expense_child_layout,
                    parent, false
            );
            IcExpChildHolder holder = new IcExpChildHolder(view);
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {
        int type = getItemViewType(position);
        //RecyclerView.ViewHolder vHolder;

        if (type == SECTION_ITEM) {
            IcExpSectionItem item = (IcExpSectionItem) mItems.get(position);
            IcExpSectionHolder vHolder = (IcExpSectionHolder)holder;
            vHolder.tvSection.setText(item.getTextDateMonth());
            mIsFirstChild = true;
        } else if (type == CHILD_ITEM) {
            IcExpChildItem item = (IcExpChildItem) mItems.get(position);
            IcExpChildHolder vHolder = (IcExpChildHolder) holder;
            vHolder.tvTitle.setText(item.getTextTitle());
            vHolder.tvAmount.setText(item.getTextAmount());
            vHolder.imgIcon.setImageResource(item.getIcon());

            if (item.getIcon() == R.drawable.ic_minus_circle) {
                vHolder.tvAmount.setTextColor(Color.parseColor("#990000"));
            }

            //เพิ่มเติมกรณีการทำมุมของรายการให้โค้ง
            boolean isLastOfSection =
                    position < mItems.size() - 1 && getItemViewType(position + 1) == SECTION_ITEM;
            boolean isLastOfAll = position == mItems.size() - 1;
            boolean isLastChild = isLastOfSection || isLastOfAll;

            if (mIsFirstChild && isLastChild) {
                vHolder.tvTitle.getRootView().setBackgroundResource(R.drawable.one_item_state);
                mIsFirstChild = false;
            } else if (mIsFirstChild || position == 0) {
                vHolder.tvTitle.getRootView().setBackgroundResource(R.drawable.top_item_state);
                mIsFirstChild = false;
            } else if (isLastChild) {
                vHolder.tvTitle.getRootView().setBackgroundResource(R.drawable.bottom_item_state);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof IcExpSectionItem) {
            return SECTION_ITEM;
        } else if (mItems.get(position) instanceof IcExpChildItem) {
            return CHILD_ITEM;
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /************************
     *  Inner Class
     **********************/
    public class IcExpSectionHolder extends RecyclerView.ViewHolder {
        public TextView tvSection;

        public IcExpSectionHolder(View cv) {
            super(cv);
            tvSection = cv.findViewById(R.id.text_section_date_month);
        }

    }

    public class IcExpChildHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgIcon;
        public TextView tvAmount;

        public IcExpChildHolder(View cv) {
            super(cv);
            tvTitle = cv.findViewById(R.id.text_title);
            tvAmount = cv.findViewById(R.id.text_amount);
            imgIcon = cv.findViewById(R.id.image_icon);
        }

    }

}
