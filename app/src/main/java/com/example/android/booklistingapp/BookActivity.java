package com.example.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
            "https://www.googleapis.com/books/v1/volumes?q=";

    /**
     * Adapter for the list of books
     */
    private BookAdapter bookAdapter;

    private EditText searchField;
    private ImageView searchIcon;
    private TextView emptyStateView;

    /**
     * TextView that is displayed when the list is empty
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        searchIcon = (ImageView) findViewById(R.id.search_icon);
        // Set background color image to transparent
        searchIcon.setBackgroundColor(0);

        // Find a reference to the search field
        searchField = (EditText) findViewById(R.id.search_field);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of books
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookAdapter);

        // Hook up the TextView as the empty view of the ListView
        emptyStateView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyStateView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        // Set an onclicklistener to the search icon image based on the user input
        searchIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    // Start the AsyncTask to fetch the book data
                    BookAsyncTask task = new BookAsyncTask();
                    task.execute(formatUrl(BOOKS_URL));
                } else {
                    // Update empty state with no connection error message
                    emptyStateView.setText(R.string.no_internet_connection);
                }
            }
        });
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

            // Set empty state text to display "No books found."
            emptyStateView.setText(R.string.no_books);

            // Clear the adapter of previous book data
            bookAdapter.clear();

            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                bookAdapter.addAll(data);
            }
        }
    }

    // Get the user input from edit text view
    private String getSearchFieldInput() {
        return searchField.getText().toString().trim();
    }

    // Format the url adding the user input to the request
    private String formatUrl(String url) {
        url = url + getSearchFieldInput() + "&maxResults=10";
        Log.v(LOG_TAG, url);
        return url;
    }
}