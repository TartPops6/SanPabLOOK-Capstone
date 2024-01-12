package sanpablook.study.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public BookingAdapter(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.txtPending.setText(booking.get("place").toString());
        holder.txtBookingID.setText(booking.get("bookingID").toString());
        holder.txtCustomerName.setText(booking.get("fullName").toString());
        holder.txtBookingDate.setText(booking.get("date").toString());
        holder.txtBookingTime.setText(booking.get("time").toString());
        holder.txtGuestCount.setText(booking.get("guest").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView txtPending, txtBookingID, txtCustomerName, txtBookingDate, txtBookingTime, txtGuestCount;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            txtPending = itemView.findViewById(R.id.establishmentNamePending);
            txtBookingID = itemView.findViewById(R.id.valueOfPendingBookingNumber);
            txtCustomerName = itemView.findViewById(R.id.valueOfPendingCustomerName);
            txtBookingDate = itemView.findViewById(R.id.valueOfPendingBookingDate);
            txtBookingTime = itemView.findViewById(R.id.valueOfPendingBookingTime);
            txtGuestCount = itemView.findViewById(R.id.valueOfPendingNumberOfGuests);
        }
    }
}