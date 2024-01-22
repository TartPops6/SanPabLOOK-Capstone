package sanpablook.study.sanpablook.Adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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

        Button buttonCancelBooking;
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
            //dialog cancel
            buttonCancelBooking = itemView.findViewById(R.id.buttonCancelBooking);

            buttonCancelBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogCancelBooking(view);
                }

                //dialog for decline
                private void showDialogCancelBooking(View view) {
                    final Dialog dialog = new Dialog(itemView.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_cancel_booking);

                    Button buttonConfirm = dialog.findViewById(R.id.buttonConfirm);
                    Button buttonBack = dialog.findViewById(R.id.buttonBack);

                    buttonConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(view.getContext(), "You cancelled this booking", Toast.LENGTH_SHORT).show();
                        }
                    });

                    buttonBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                }
            });
        }
    }
}