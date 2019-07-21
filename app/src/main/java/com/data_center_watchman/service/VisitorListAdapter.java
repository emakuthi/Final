package com.data_center_watchman.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.data_center_watchman.R;
import com.data_center_watchman.model.Visitor;
import com.data_center_watchman.ui.CheckoutView;

import java.util.List;

public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.VisitorViewHolder>{

    private List<Visitor> visitorList;
    private Context mContext;

    public VisitorListAdapter(Context context, List<Visitor> visitorList) {
        this.mContext = context;
        this.visitorList = visitorList;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view, parent, false);
        return new VisitorViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, final int position) {

        holder.fullName.setText(visitorList.get(position).getFullName());
        holder.idNumber.setText(visitorList.get(position).getIdNumber());
        holder.reason.setText(visitorList.get(position).getReason());
        holder.checkedIn.setText(visitorList.get(position).getTimeIn());
        holder.checkedOut.setText(visitorList.get(position).getTimeOut());
        holder.company.setText(visitorList.get(position).getCompany());
        holder.crqNumber.setText(visitorList.get(position).getCrqNumber());
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        holder.visitorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CheckoutView.class);
                intent.putExtra("fullName",visitorList.get(position).getFullName());
                intent.putExtra("idNumber", visitorList.get(position).getIdNumber());
                intent.putExtra("reason", visitorList.get(position).getReason());
                intent.putExtra("checkedIn", visitorList.get(position).getTimeIn());
                intent.putExtra("company", visitorList.get(position).getCompany());
                intent.putExtra("phoneNumber", visitorList.get(position).getPhonenumber());
                intent.putExtra("crqNumber", visitorList.get(position).getCrqNumber());
                intent.putExtra("location", visitorList.get(position).getLocation());
                intent.putExtra("timeOut", visitorList.get(position).getTimeOut());
                intent.putExtra("id", visitorList.get(position).getId());

                mContext.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, idNumber, reason, company, checkedIn,visitorDetail, checkedOut, crqNumber;

        private VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.visitor_Name);
            idNumber = itemView.findViewById(R.id.idnumber);
            reason = itemView.findViewById(R.id.reason_for_visit);
            company = itemView.findViewById(R.id.visitor_type);
            checkedIn = itemView.findViewById(R.id.checkedIn);
            checkedOut = itemView.findViewById(R.id.checkedOut);
            crqNumber = itemView.findViewById(R.id.crqNumber);
            visitorDetail= itemView.findViewById(R.id.visitorDetails);

        }

    }
}
