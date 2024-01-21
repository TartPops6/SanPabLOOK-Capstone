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

public class RecyclerBookingsConfirmed extends RecyclerView.Adapter<RecyclerBookingsConfirmed.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerBookingsConfirmed(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_bookings_confirmed, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.establishmentNameConfirmed.setText(booking.get("place").toString());
        holder.valueOfConfirmedBookingNumber.setText(booking.get("bookingID").toString());
        holder.valueOfConfirmedCustomerName.setText(booking.get("fullName").toString());
        holder.valueOfConfirmedBookingDate.setText(booking.get("date").toString());
        holder.valueOfConfirmedBookingTime.setText(booking.get("time").toString());
        holder.valueOfConfirmedNumberOfGuests.setText(booking.get("guest").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        Button buttonReview;
        TextView establishmentNameConfirmed, valueOfConfirmedBookingNumber, valueOfConfirmedCustomerName, valueOfConfirmedBookingDate, valueOfConfirmedBookingTime, valueOfConfirmedNumberOfGuests;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            establishmentNameConfirmed = itemView.findViewById(R.id.establishmentNameConfirmed);
            valueOfConfirmedBookingNumber = itemView.findViewById(R.id.valueOfConfirmedBookingNumber);
            valueOfConfirmedCustomerName = itemView.findViewById(R.id.valueOfConfirmedCustomerName);
            valueOfConfirmedBookingDate = itemView.findViewById(R.id.valueOfConfirmedBookingDate);
            valueOfConfirmedBookingTime = itemView.findViewById(R.id.valueOfConfirmedBookingTime);
            valueOfConfirmedNumberOfGuests = itemView.findViewById(R.id.valueOfConfirmedNumberOfGuests);
            buttonReview = itemView.findViewById(R.id.buttonReview);
        }
    }
}