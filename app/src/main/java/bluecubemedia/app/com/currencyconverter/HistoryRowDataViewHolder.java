package bluecubemedia.app.com.currencyconverter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class HistoryRowDataViewHolder extends RecyclerView.ViewHolder {
    TextView tv_from;
    TextView tv_to;
    TextView tv_amount;
    TextView rate;

    HistoryRowDataViewHolder(View itemView) {
        super(itemView);
        tv_from= (TextView)itemView.findViewById(R.id.tv_hisfrom_value);
        tv_to = (TextView)itemView.findViewById(R.id.tv_histo_value);
        tv_amount = (TextView)itemView.findViewById(R.id.tv_hisamount_value);
        rate = (TextView)itemView.findViewById(R.id.tv_rate);
    }
}