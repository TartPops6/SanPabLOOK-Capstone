package sanpablook.study.sanpablook.Adapter;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.study.sanpablook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sanpablook.study.sanpablook.ActivityRatings;

public class RecyclerCardRatings extends RecyclerView.Adapter<RecyclerCardRatings.BookingViewHolder> {

    private List<String> items;
    private Context context;

    private String imageUrl;

    public RecyclerCardRatings(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_ratings, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        String imageUrl = items.get(position);

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageRatings);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageRatings;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // image view
            imageRatings = itemView.findViewById(R.id.imageRatings);

            imageRatings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToActivityRatings(view);
                }
            });
        }

        private void goToActivityRatings(View view) {
            Intent intent = new Intent(context, ActivityRatings.class);
            context.startActivity(intent);
        }
    }
}