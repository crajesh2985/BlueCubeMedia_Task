package bluecubemedia.app.com.currencyconverter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import bluecubemedia.app.com.bluecubemedia_sdk.model.HistoryData;

public class HistoryRecyclerAdapter  extends RecyclerView.Adapter<HistoryRowDataViewHolder>{

    private List<HistoryData> historyDatas;
    private Context mContext;

    HistoryRecyclerAdapter(Context context,List<HistoryData> historyDatas){
        mContext=context;
        this.historyDatas = historyDatas;
    }


    @Override
    public HistoryRowDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row_layout, viewGroup, false);
        HistoryRowDataViewHolder historyRowDataViewHolder = new HistoryRowDataViewHolder(v);
        return historyRowDataViewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryRowDataViewHolder historyRowDataViewHolder, int position) {
        historyRowDataViewHolder.tv_from.setText(historyDatas.get(position).getFrom());
        historyRowDataViewHolder.tv_to.setText(historyDatas.get(position).getTo());
        historyRowDataViewHolder.tv_amount.setText(historyDatas.get(position).getAmount());
        historyRowDataViewHolder.rate.setText(historyDatas.get(position).getFinial_rate());

    }
    @Override
    public int getItemCount() {
        return historyDatas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

