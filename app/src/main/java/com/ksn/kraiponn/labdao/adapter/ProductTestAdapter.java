package com.ksn.kraiponn.labdao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.manager.ProductTestItem;

import java.util.List;

public class ProductTestAdapter
        extends RecyclerView.Adapter<ProductTestAdapter.ProTestViewHolder>{
    private Context mContext;
    private List<ProductTestItem> mDataSet;

    public ProductTestAdapter(Context mContext, List<ProductTestItem> mDataSet) {
        this.mContext = mContext;
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public ProTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(
                R.layout.item_product_list_test, parent, false
        );

        ProTestViewHolder vHolder = new ProTestViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProTestViewHolder holder, int position) {
        ProductTestItem item = mDataSet.get(position);
        holder.tvId.setText(item.id + "");
        holder.tvProName.setText(item.productName);
        holder.tvDetail.setText(item.detail);
        Ion.with(mContext)
                .load(item.baseUrl + item.image)
                .intoImageView(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ProTestViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvProName;
        public TextView tvDetail;
        public ImageView imgProduct;

        public ProTestViewHolder(View cv) {
            super(cv);
            tvId = cv.findViewById(R.id.text_pro_id);
            tvProName = cv.findViewById(R.id.text_pro_name);
            tvDetail = cv.findViewById(R.id.text_pro_detail);
            imgProduct = cv.findViewById(R.id.image_product);
        }
    }

}

