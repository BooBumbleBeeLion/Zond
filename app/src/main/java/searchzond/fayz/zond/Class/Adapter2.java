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

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    private ArrayList<MainItems> items2;
    private Context context;
    Intent intent;

    public Adapter2(ArrayList<MainItems> items2, Context context) {
        this.items2 = items2;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
        return new ViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.ViewHolder holder, int position) {

        MainItems Item2 = items2.get(position);
        holder.text.setText(Item2.getMarka());
        holder.text1.setText(Item2.getThing());
        holder.text2.setText(Item2.getPlace());
        holder.text3.setText(Item2.getDay() + "." + Item2.getMonth() + "." + Item2.getYear());
        holder.text4.setText(Item2.getColor());
        holder.text5.setText(Item2.getContent());
        Picasso.get().load(Item2.getImageurl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items2.size();
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
                    MainItems Item2 = items2.get(positionIndex);
                    intent = new Intent(context, DopActivity.class);
                    intent.putExtra("image", Item2.getImageurl());
                    intent.putExtra("dopText", Item2.getMarka());
                    intent.putExtra("dopText1", Item2.getThing());
                    intent.putExtra("dopText2", Item2.getPlace());
                    intent.putExtra("dopText3", Item2.getDay() + "." + Item2.getMonth() + "." + Item2.getYear());
                    intent.putExtra("dopText4", Item2.getColor());
                    intent.putExtra("dopText5", Item2.getContent());
                    context.startActivity(intent);

                }
            });
        }
    }

}
