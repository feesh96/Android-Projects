package org.example.matthewfishman.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Matthew on 1/8/2017.
 */

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotosList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        mPhotosList = photosList;
    }


    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Called by the layout manager whn it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) { //set up a fresh ViewHolder
        //Called by the layout manager whn it wants new data in an existing row
        if ((mPhotosList == null) || (mPhotosList.size() == 0)) {
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.emptySearchQuery);
        } else {
            Photo photoItem = mPhotosList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " --> " + position);
            Picasso.with(mContext).load(photoItem.getImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);

            holder.title.setText(photoItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return ((mPhotosList != null) && (mPhotosList.size() != 0) ? mPhotosList.size() : 1);
    }

    public Photo getPhoto(int position) {
        return ((mPhotosList != null) && (mPhotosList.size() != 0) ? mPhotosList.get(position) : null);
    }
    void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged(); //Tells registered observers (recycler) that shits changed
    }



    static class FlickrImageViewHolder extends RecyclerView.ViewHolder { //Holds view for adapter
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: Started");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}