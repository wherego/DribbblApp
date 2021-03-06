package com.dribbb.sun.dribbblapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.dribbb.sun.dribbblapp.base.BaseViewHolder;
import com.dribbb.sun.dribbblapp.viewholder.SelectedViewHolder;
import com.dribbb.sun.model.Shot;
import com.dribbb.sun.service.retrofit.ApiFactory;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by sunbinqiang on 4/10/16.
 */
public class SelectedShotAdapter extends ListRecyclerViewAdapter<Shot>{

    private Context mContext;
    private String  mRequestUrl;
    private Observable<Shot[]> mObservable;
    private Map<String, String> mQueryMap = new HashMap<>();
    public SelectedShotAdapter(Context context, String key, String param){
        mContext = context;
        if(!TextUtils.isEmpty(key)) {
            mQueryMap.put(key, param);
        }
    }

    public SelectedShotAdapter(Context context, Observable<Shot[]> observable){
        mContext = context;
        mObservable = observable;
    }

    @Override
    protected Shot[] getResult(Gson gson, JSONArray object) {
        return gson.fromJson(object.toString(), Shot[].class);
    }

    @Override
    protected BaseViewHolder onCreateItemView(ViewGroup parent) {
        return new SelectedViewHolder(mContext, parent);
    }

    @Override
    protected void onBindItemView(RecyclerView.ViewHolder holder, int position) {
        ((SelectedViewHolder)holder).setShots(getList().get(position));
    }


    @Override
    public Observable<Shot[]> getObservable() {
        if(mObservable != null){
            return mObservable;
        }
        return ApiFactory.getRequestService().getShots(String.valueOf(mPage), mQueryMap);
    }
}
