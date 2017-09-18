package id.magga.yourgram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by magga on 9/13/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {

    private Context context;
    private List<ParseObject> listParseObject;

    public MyRecyclerViewAdapter(Context context, List<ParseObject> listParseObject) {
        this.context = context;
        this.listParseObject = listParseObject;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image, null);
        CustomViewHolder cust = new CustomViewHolder(view);
        return cust;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final ParseObject obj = listParseObject.get(position);

        if (obj.getParseFile("image").getUrl() != ""){
            Picasso.with(context)
                    .load(obj.getParseFile("image").getUrl())
                    .into(holder.imageView);
        }

        holder.txtDateUploaded.setText(obj.getCreatedAt().toString());
        holder.txtCaption.setText(obj.getString("caption"));
    }

    @Override
    public int getItemCount() {
        return listParseObject.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView txtDateUploaded;
        protected TextView txtCaption;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.txtDateUploaded = (TextView) itemView.findViewById(R.id.txtDateUploaded);
            this.txtCaption = (TextView) itemView.findViewById(R.id.txtCaption);
        }
    }
}
