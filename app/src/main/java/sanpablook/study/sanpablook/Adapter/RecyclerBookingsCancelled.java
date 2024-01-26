package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class RecyclerBookingsCancelled extends RecyclerView.Adapter<RecyclerBookingsCancelled.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerBookingsCancelled(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_bookings_cancelled, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.establishmentNameCancelled.setText(booking.get("place").toString());
        holder.valueOfCancelledBookingNumber.setText(booking.get("bookingID").toString());
        holder.valueOfCancelledCustomerName.setText(booking.get("fullName").toString());
        holder.valueOfCancelledBookingDate.setText(booking.get("date").toString());
        holder.valueOfCancelledBookingTime.setText(booking.get("time").toString());
        holder.valueOfCancelledNumberOfGuests.setText(booking.get("guest").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView establishmentNameCancelled, valueOfCancelledBookingNumber, valueOfCancelledCustomerName, valueOfCancelledBookingDate, valueOfCancelledBookingTime, valueOfCancelledNumberOfGuests;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            establishmentNameCancelled = itemView.findViewById(R.id.establishmentNameCancelled);
            valueOfCancelledBookingNumber = itemView.findViewById(R.id.valueOfCancelledBookingNumber);
            valueOfCancelledCustomerName = itemView.findViewById(R.id.valueOfCancelledCustomerName);
            valueOfCancelledBookingDate = itemView.findViewById(R.id.valueOfCancelledBookingDate);
            valueOfCancelledBookingTime = itemView.findViewById(R.id.valueOfCancelledBookingTime);
            valueOfCancelledNumberOfGuests = itemView.findViewById(R.id.valueOfCancelledNumberOfGuests);
        }
    }
}