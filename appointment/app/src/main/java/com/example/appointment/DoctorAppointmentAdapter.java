package com.example.appointment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.ViewHolder> implements Filterable {
    private static final String LOG_TAG = DoctorAppointmentAdapter.class.getName();
    private ArrayList<Appointment> mAppointmentData;
    private ArrayList<Appointment> mAppointmentDataAll;
    private Context mContext;
    private int lastPosition = -1;

    public DoctorAppointmentAdapter(Context context, ArrayList<Appointment> itemsData) {
        this.mAppointmentData = itemsData;
        this.mAppointmentDataAll = itemsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DoctorAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorAppointmentAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.doctor_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder( DoctorAppointmentAdapter.ViewHolder holder, int position) {
        Appointment currentItem = mAppointmentData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mAppointmentData.size();
    }

    @Override
    public Filter getFilter() {
        return appointmentFiler;
    }

    private Filter appointmentFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Appointment> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0 ) {
                results.count = mAppointmentDataAll.size();
                results.values = mAppointmentDataAll;
            } else {
                String filterPattern = constraint.toString().toLowerCase();
                for(Appointment item : mAppointmentDataAll) {
                    if( item.getActors().contains(constraint)) {
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAppointmentData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTypeText;
        private TextView mSlotsAvailableNumberText;
        private TextView mStartTimeText;
        private TextView mEndTimeText;
        private TextView mDateText;
        private TextView mDescriptionText;
        private Button mBookButton;

        public ViewHolder(View itemView) {
            super(itemView);

            mTypeText = itemView.findViewById(R.id.appointmentTypeEditText);
            mSlotsAvailableNumberText = itemView.findViewById(R.id.availableSlotsNumber);
            mStartTimeText = itemView.findViewById(R.id.startTimeTextView);
            mEndTimeText = itemView.findViewById(R.id.endTimeTextView);
            mDateText = itemView.findViewById(R.id.appointmentDateTextView);
            mDescriptionText = itemView.findViewById(R.id.descriptionTextView);
            itemView.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoctorActivity)mContext).editAppointment(mAppointmentData.get(0).getId());
                }
            });
            itemView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoctorActivity)mContext).deleteAppointment(mAppointmentData.get(0).getId());
                }
            });

        }

        public void bindTo(Appointment currentItem) {
            mTypeText.setText(currentItem.getAppointmentType());
            // String slotsString = String.valueOf(currentItem.getSlots());
            mSlotsAvailableNumberText.setText(String.valueOf(currentItem.getSlots()));
            mStartTimeText.setText(currentItem.getStart());
            mEndTimeText.setText(currentItem.getEnd());
            mDateText.setText(currentItem.getDate());
            mDescriptionText.setText(currentItem.getDescription());
        }
    }
}
