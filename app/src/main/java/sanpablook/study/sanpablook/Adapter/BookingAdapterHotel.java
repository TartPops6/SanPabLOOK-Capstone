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

public class BookingAdapterHotel extends RecyclerView.Adapter<BookingAdapterHotel.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public BookingAdapterHotel(List<Map<String, Object>> bookings) {
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
        if (booking.get("place") != null) {
            holder.txtPending.setText(booking.get("place").toString());
        }
        if (booking.get("bookingID") != null) {
            holder.txtBookingID.setText(booking.get("bookingID").toString());
        }
        if (booking.get("fullName") != null) {
            holder.txtCustomerName.setText(booking.get("fullName").toString());
        }
        if (booking.get("date") != null) {
            holder.txtBookingDate.setText(booking.get("date").toString());
        }
        if (booking.get("time") != null) {
            holder.txtBookingTime.setText(booking.get("time").toString());
        }
        if (booking.get("guest") != null) {
            holder.txtGuestCount.setText(booking.get("guest").toString());
        }
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