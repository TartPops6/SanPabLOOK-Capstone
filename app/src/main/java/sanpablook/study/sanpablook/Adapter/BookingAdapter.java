package sanpablook.study.sanpablook.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.sanpablook.R;

import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.BookingsPendingActivity;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private static List<Map<String, Object>> bookings;
    static FirebaseFirestore db;

    public BookingAdapter(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
        db = FirebaseFirestore.getInstance();
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

    class BookingViewHolder extends RecyclerView.ViewHolder {

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
                public void onClick(View v) {
                    showDialogCancelBooking(v);
                }
            });
        }

        //dialog for decline
        private void showDialogCancelBooking(View view) {
            final Dialog dialog = new Dialog(itemView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_cancel_booking);

            Button buttonConfirmConfirmed = dialog.findViewById(R.id.buttonConfirmTwice);
            Button buttonBack = dialog.findViewById(R.id.buttonBack);

            buttonConfirmConfirmed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelledBooking(getBindingAdapterPosition(), dialog, view.getContext());
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
    }

    // Method to cancel a booking
    private void cancelledBooking(int position, Dialog dialog, Context context) {
        String bookingID = bookings.get(position).get("bookingID").toString();
        String userID = bookings.get(position).get("userID").toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && userID.equals(currentUser.getUid())) {
            db.collection("BookingPending").document(bookingID).update("status", "Cancelled")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            bookings.get(position).put("status", "Cancelled");
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Booking status updated to Cancelled", Toast.LENGTH_SHORT).show();
                            // Call refreshData() here
                            ((BookingsPendingActivity) context).refreshData();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("BookingAdapter", "Failed to update booking status with ID: " + bookingID, e);
                            // Print the exception message
                            Log.d("BookingAdapter", "Error: " + e.getMessage());
                        }
                    });
        } else {
            Log.d("BookingAdapter", "Cannot update booking status with ID: " + bookingID + " because it does not belong to the current user");
        }
    }
}