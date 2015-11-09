package org.gbe.popularmovies.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import org.gbe.popularmovies.R;
import org.gbe.popularmovies.model.Review;
import org.parceler.Parcels;

import java.util.List;

public class ReviewFragment extends Fragment {

    private static final String ARG_REVIEWS = "ARG_REVIEWS";
    MovieListActivity hostActivity;
    private List<Review> reviews;
    private AbsListView mListView;
    private ListAdapter mAdapter;

    public static ReviewFragment newInstance(List<Review> reviews) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REVIEWS, Parcels.wrap(reviews));
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviews = Parcels.unwrap(getArguments().getParcelable(ARG_REVIEWS));
        }
        mAdapter = new ReviewAdapter(getActivity(),
                R.layout.review_item, reviews);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            hostActivity = (MovieListActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must be a MovieListActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostActivity = null;
    }
}
