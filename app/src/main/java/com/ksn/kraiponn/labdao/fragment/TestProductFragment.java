package com.ksn.kraiponn.labdao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.adapter.ProductTestAdapter;
import com.ksn.kraiponn.labdao.manager.ProductTestItem;

import java.util.ArrayList;

public class TestProductFragment extends Fragment {
    private RecyclerView rcv;
    private ArrayList<ProductTestItem> mDataSet;
    private ProductTestAdapter adapter;
    private final String baseURL =
            "http://192.168.79.1/ATOM/dao/";


    public TestProductFragment() {

    }

    public static TestProductFragment newInstance() {
        TestProductFragment fm = new TestProductFragment();
        Bundle args = new Bundle();
        fm.setArguments(args);
        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_test_products,
                container, false
        );

        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        rcv = rootView.findViewById(R.id.recyclerView);
        mDataSet = new ArrayList<>();

        Ion.with(getContext())
                .load(baseURL + "product-list.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for (int i=0; i<result.size(); i++) {
                            ProductTestItem item = new ProductTestItem();
                            JsonObject object = (JsonObject) result.get(i);
                            item.id = object.get("id").getAsInt();
                            item.productName = object.get("name").getAsString();
                            item.detail = object.get("detail").getAsString();
                            item.image = object.get("image").getAsString();
                            item.baseUrl = baseURL + "images/";
                            mDataSet.add(item);
                        }

                        adapter = new ProductTestAdapter(getContext(), mDataSet);
                        rcv.setAdapter(adapter);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });
    }


}
