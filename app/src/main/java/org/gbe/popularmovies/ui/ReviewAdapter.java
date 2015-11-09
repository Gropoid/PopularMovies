package org.gbe.popularmovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.gbe.popularmovies.R;
import org.gbe.popularmovies.model.Review;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, int resource, List<Review> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);
        VH viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
            viewHolder = new VH(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH)convertView.getTag();
        }
        viewHolder.tvAuthor.setText(review.getAuthor());
        viewHolder.tvContent.setText(review.getContent());
        return convertView;
    }

    static class VH {
        @Bind(R.id.review_author)
        TextView tvAuthor;

        @Bind(R.id.review_content)
        TextView tvContent;

        public VH(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
