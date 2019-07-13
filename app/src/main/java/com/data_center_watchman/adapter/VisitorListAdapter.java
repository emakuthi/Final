package com.data_center_watchman.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.data_center_watchman.R;
import com.data_center_watchman.model.Visitor;

import java.util.Date;
import java.util.List;

public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.VisitorViewHolder>{

    private List<Visitor> visitorList;


    public VisitorListAdapter(List<Visitor> visitorList) {
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
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {

        holder.fullName.setText("Name: "+visitorList.get(position).getFullName());
        holder.idNumber.setText("IdNo/EkNO: "+visitorList.get(position).getIdNumber());
        holder.reason.setText("Reason: "+visitorList.get(position).getReason());
        holder.checkedIn.setText("checked In: "+ visitorList.get(position).getTimeIn());
        holder.company.setText("Type Of Visitor: "+ visitorList.get(position).getCompany());

    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }

    public class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, idNumber, reason, company, checkedIn;
        CardView cardView1;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.visitor_Name);
            idNumber = itemView.findViewById(R.id.idnumber);
            reason = itemView.findViewById(R.id.reason_for_visit);
            company = itemView.findViewById(R.id.visitor_type);
            checkedIn = itemView.findViewById(R.id.checkedIn);
            cardView1 = itemView.findViewById(R.id.cardviewContent);
        }
    }
}
