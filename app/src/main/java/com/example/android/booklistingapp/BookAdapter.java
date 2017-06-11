package com.example.android.booklistingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Book currentBook = getItem(position);

        // Find the ImageView in the book_list_item.xml layout with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        // Loading an Image from a Url
        Picasso.with(getContext()).load(currentBook.getImage()).into(imageView);

        // Find the TextView in the book_list_item.xml layout with the ID title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        // Find the TextView in the book_list_item.xml layout with the ID author
        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentBook.getAuthor());

        // Find the TextView in the book_list_item.xml layout with the ID publisher
        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        publisherView.setText(currentBook.getPublisher());

        // Find the TextView in the book_list_item.xml layout with the ID published date
        TextView publishedDateView = (TextView) listItemView.findViewById(R.id.published_date);
        publishedDateView.setText(currentBook.getPublishedDate());

        // Find the TextView in the book_list_item.xml layout with the ID page count
        TextView pageCountView = (TextView) listItemView.findViewById(R.id.page_count);
        pageCountView.setText(currentBook.getPageCount());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}