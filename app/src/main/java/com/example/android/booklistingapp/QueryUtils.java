package com.example.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractFeatureFromJSON(jsonResponse);

        // Return the list of {@link Book}s
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //Id the url is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { //HTTP_OK = 200
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error Response Status: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "Problem retrieving the book JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractFeatureFromJSON(String BookJSON) {
        String publisherIndicator = "Publisher: ";
        String publishedDateIndicator = "Published Date: ";
        String pagesIndicator = "Pages: ";

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(BookJSON)) {
            return null;
        }

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(BookJSON);

            // Getting JSON Array node
            JSONArray itemsArray = jsonObj.getJSONArray("items");

            // looping through All features
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject bookObject = itemsArray.getJSONObject(i);
                JSONObject volumeInfoObject = bookObject.getJSONObject("volumeInfo");

                // get the image url
                JSONObject imageLinks = volumeInfoObject.getJSONObject("imageLinks");
                String image = checkIfJsonElementExists(imageLinks, "smallThumbnail");

                // get the title of the book
                String title = checkIfJsonElementExists(volumeInfoObject, "title");

                // get the authors array
                JSONArray authorsArray = volumeInfoObject.getJSONArray("authors");
                Log.i(LOG_TAG, "" + authorsArray);

                // get the authors as a concatenated string
                String authors = returnAuthors(authorsArray);

                // get the publisher string
                String publisher = publisherIndicator + checkIfJsonElementExists(volumeInfoObject, "publisher");
                //Log.i(LOG_TAG, "" + publisher);

                // get the published date string
                String publishedDate = publishedDateIndicator + checkIfJsonElementExists(volumeInfoObject, "publishedDate");

                // get the page count string
                String pageCount = pagesIndicator + checkIfJsonElementExists(volumeInfoObject, "pageCount");

                Book book = new Book(image, title, authors, publisher, publishedDate, pageCount);
                books.add(book);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        // Return the list of books
        return books;
    }

    // Checks the items of the authors array and returns a single author or multiple
    private static String returnAuthors(JSONArray authorsArray) throws JSONException {
        String authors = "";
        for (int i = 0; i < authorsArray.length(); i++) {
            authors = authorsArray.getString(0);
            if (authorsArray.length() > 1) {
                authors += ", " + authorsArray.getString(i);
            }
        }
        return authors;
    }

    // Checks whether a specified parameter exists and returns it otherwise returns -
    private static String checkIfJsonElementExists(JSONObject object, String jsonElement) throws JSONException {
        String element;
        if (object.has(jsonElement)) {
            element = object.getString(jsonElement);
        } else {
            element = "-";
        }
        return element;
    }
}