# Android Basics Nanodegree - BookListingApp

## Project Overview
This project is a chance for you to combine and practice everything you learned in this section of the Nanodegree program. You will be making an app that lists books.

The goal is to design and create the structure of a **Book Listing** app which would allow a user to get a list of published books on a given topic. You will be using the google books api in order to fetch results and display them to the user.

## Why this project?
In the most recent portion of the Nanodegree program, you learned about the web and about how to get data from an api. You also learned how to parse that data and display it to a user. This project gives you the ability to practice those skills, which will be key in developing any app which makes use of a backend server, real time data, or interactions over the internet.

## What will I learn?
This project is about combining various ideas and skills we’ve been practicing throughout the course. They include:

* Fetching data from an API
* Using an AsyncTask
* Parsing a JSON response
* Creating a list based on that data and displaying it to the user.

## Build Your Project
For this project, you will be creating a book listing app. A user should be able to enter a keyword, press the search button, and recieve a list of published books which relate to that keyword.

To achieve this, you'll make use of the Google Books API. This is a well-maintained API which returns information in a JSON format.

We suggest first exploring the API and learning what information it returns given a particular query. An example query that we found useful was

  https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1

Once you've explored the API, begin work in Android Studio. You'll want a simple layout initially, with an editable TextView and a 'search' button.

Then, you'll want to build the AsyncTask that queries the API. This is a complex step, so be sure to reference the course materials when needed.

Once you've queried the API, you'll need to parse the result. This will involve storing the information returned by the API in a custom class.

Finally, you'll use the List and Adapter pattern to populate a list on the user's screen with the information stored in the custom objects you wrote earlier.

<img src="https://user-images.githubusercontent.com/14216599/27298297-ba3f769e-5530-11e7-9758-47b45bc89aa8.png" width="250" hspace="20"/> <img src="https://user-images.githubusercontent.com/14216599/27450326-0df10388-5794-11e7-9379-ba7b7e8a789c.png" width="250"/> 

<img src="https://user-images.githubusercontent.com/14216599/27450347-22b9a3d8-5794-11e7-862e-53e5bc852643.png" width="250" hspace="20"/> <img src="https://user-images.githubusercontent.com/14216599/27450447-689ba2a2-5794-11e7-9478-f5a465709ba2.png" width="250"/>




