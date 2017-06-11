package com.example.android.booklistingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    public static final String LOG_TAG = BookActivity.class.getName();

    /**
     * URL for book data from the Google Book API
     */
    private static final String BOOKS_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    /**
     * Adapter for the list of books
     */
    private BookAdapter bookAdapter;

    /** TextView that is displayed when the list is empty */
    //private TextView emptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of books
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookAdapter);

        // Hook up the TextView as the empty view of the ListView
        //emptyStateView = (TextView) findViewById(R.id.empty_view);
        //bookListView.setEmptyView(emptyStateView);

        // Start the AsyncTask to fetch the book data
        BookAsyncTask task = new BookAsyncTask();
        task.execute(BOOKS_URL);
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            // Clear the adapter of previous book data
            bookAdapter.clear();

            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                bookAdapter.addAll(data);
            }
        }
    }
}
