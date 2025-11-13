package com.example.projeeeeeeeeeect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.Models.StatusType;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Report> reportList;
    private OnItemClickListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());

    // Interface for handling clicks
    public interface OnItemClickListener {
        void onItemClick(Report report);
    }

    public ReportAdapter(List<Report> reportList, OnItemClickListener listener) {
        this.reportList = reportList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the row layout 'list_item_report.xml'
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        // Gets the report at the current position
        Report report = reportList.get(position);

        // Binds the data to the views
        holder.bind(report, listener);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    // Updates the list with new data from the API
    public void setReports(List<Report> newReports) {
        this.reportList.clear();
        this.reportList.addAll(newReports);
        notifyDataSetChanged(); // Refreshes the RecyclerView
    }

    // --- ViewHolder Inner Class ---
    class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView textViewReportId;
        TextView textViewReportStatus;
        TextView textViewReportUser;
        TextView textViewReportTimestamp;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            // Finds the views in 'list_item_report.xml'
            textViewReportId = itemView.findViewById(R.id.textViewReportId);
            textViewReportStatus = itemView.findViewById(R.id.textViewReportStatus);
            textViewReportUser = itemView.findViewById(R.id.textViewReportUser);
            textViewReportTimestamp = itemView.findViewById(R.id.textViewReportTimestamp);
        }

        public void bind(final Report report, final OnItemClickListener listener) {
            // Set the data
            textViewReportId.setText("Report #" + report.getId());

            if (report.getStatusType() != null) {
                textViewReportStatus.setText(report.getStatusType().getName());
            } else {
                textViewReportStatus.setText("Unknown");
            }

            textViewReportUser.setText("Reported by: User ID " + report.getUserId());
            if(report.getDateReported() != null && !report.getDateReported().isEmpty()) {
                textViewReportTimestamp.setText("On: " + report.getDateReported());
            } else {
                textViewReportTimestamp.setText("On: Unknown date");
            }
            // Set the click listener for the entire row
            itemView.setOnClickListener(v -> listener.onItemClick(report));
        }
    }
}