package searchzond.fayz.zond.Class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import searchzond.fayz.zond.Activity.DopActivity;
import searchzond.fayz.zond.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<MainItems> items;
    private Context context;
    Intent intent;

    public Adapter(ArrayList<MainItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        MainItems Item = items.get(position);
        holder.text.setText(Item.getMarka());
        holder.text1.setText(Item.getThing());
        holder.text2.setText(Item.getPlace());
        holder.text3.setText(Item.getDay() + "." + Item.getMonth() + "." + Item.getYear());
        holder.text4.setText(Item.getColor());
        holder.text5.setText(Item.getContent());
        Picasso.get().load(Item.getImageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            text = itemView.findViewById(R.id.text);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            text4 = itemView.findViewById(R.id.text4);
            text5 = itemView.findViewById(R.id.text5);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positionIndex = getAdapterPosition();
                    MainItems Item = items.get(positionIndex);
                    intent = new Intent(context, DopActivity.class);
                    intent.putExtra("image", Item.getImageurl());
                    intent.putExtra("dopText", Item.getMarka());
                    intent.putExtra("dopText1", Item.getThing());
                    intent.putExtra("dopText2", Item.getPlace());
                    intent.putExtra("dopText3", Item.getDay() + "." + Item.getMonth() + "." + Item.getYear());
                    intent.putExtra("dopText4", Item.getColor());
                    intent.putExtra("dopText5", Item.getContent());
                    context.startActivity(intent);

                }
            });
        }
    }

}
