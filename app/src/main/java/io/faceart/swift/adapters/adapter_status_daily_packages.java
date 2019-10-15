package io.faceart.swift.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.faceart.swift.R;
import io.faceart.swift.data_models.model_daily_package_item;

public class adapter_status_daily_packages extends RecyclerView.Adapter<adapter_status_daily_packages.model_order_daily_item_holder> {


    public ArrayList<model_daily_package_item> m_orderList =null;
    public Context mContext;


    public adapter_status_daily_packages(ArrayList<model_daily_package_item> orderList, Context context) {
        this.m_orderList = orderList;
        this.mContext = context;
    }


    @Override
    public model_order_daily_item_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_item_today_package_order, parent, false);

        return new model_order_daily_item_holder(view);
    }

    @Override
    public int getItemCount() {
        int size= m_orderList.size();
        return  size;
    }





    @Override
    public void onBindViewHolder(@NonNull model_order_daily_item_holder holder, final int position) {
        final model_daily_package_item order = m_orderList.get(position);


        holder.mb_name.setText(order.mb_name);
        holder.mb_address.setText(order.mb_address);
        holder.mb_distance.setText(order.mb_distance);
        holder.mb_zone.setText(order.mb_zone);
        if(order.status)
        {
            holder.mb_parcel_type.setImageResource(R.drawable.icon_circle_deliverd);
            holder.parcel_type_bottom_bar.setBackgroundColor(Color.parseColor("#90703090"));
            holder.mb_parcel_type_background.setBackgroundResource(R.drawable.round_daily_package_active);
        }
        else
        {
            holder.mb_parcel_type.setImageResource(R.drawable.icon_circle_reattempt);
            holder.parcel_type_bottom_bar.setBackgroundColor(Color.parseColor("#90f15b22"));
            holder.mb_parcel_type_background.setBackgroundResource(R.drawable.round_daily_package_deactive);
        }
    }


    public class model_order_daily_item_holder extends RecyclerView.ViewHolder {



        public TextView mb_name ;
        public TextView mb_address ;
        public TextView mb_distance ;
        public TextView mb_zone ;
        public ImageView mb_parcel_type;
        public LinearLayout parcel_type_bottom_bar;
        public ConstraintLayout mb_parcel_type_background;
        public model_order_daily_item_holder(View itemView) {
            super(itemView);


            mb_name =itemView.findViewById(R.id.mb_name); ;
            mb_address =itemView.findViewById(R.id.mb_address);
            mb_distance =itemView.findViewById(R.id.mb_distance);
            mb_zone =itemView.findViewById(R.id.mb_zone);
            mb_parcel_type =itemView.findViewById(R.id.parcel_type);
            parcel_type_bottom_bar =itemView.findViewById(R.id.parcel_type_bottom_bar);
            mb_parcel_type_background =itemView.findViewById(R.id.parcel_type_background);
        }


    }
    public void update_list(){
        notifyDataSetChanged();
    }
}
