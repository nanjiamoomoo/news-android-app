# News Recommendation App

## **Project Description**
An Android recommendation app that allows users to watch latest news, search and save news.

## **Project Architecture**
The project is built with Android MVVC (Model-View-ViewModel) Architecture pattern. 

* The View — that informs the ViewModel about the user’s actions
* The ViewModel — exposes streams of data relevant to the View
* The DataModel — abstracts the data source. The ViewModel works with the DataModel to get and save the data.

## **Packages**
**_`ui`_**
* Defined Fragments with views to inform ViewModels about the user’s actions.
* Defined ViewModels that works with Model to get and save the data.
* Used RecyclerView with adapter and layout manager to display the content on the screen.
* Used Picasso library to display the news content image.

**_`model`_**
* Defined the Java model classes that map to the server-side JSON format data and tables in the Room database. 

**_`repository`_**
* Used Retrofit to implement the NewsApi interface. And utilized the retrofit method to get data from endpoint and and hold the data in LiveData.

**_`network`_**
* Configured Retrofit instance and defined REST APIs to send request to the endpoint and returns response. 

**_`database_**
* Defined Room database and APIs. 

## **Page Navigation**
Utilized Jetpack navigation component to control page navigation (navigate between multiple fragments) in the MainActivity bottom bar. 
There are 3 fragments in one activity. Details see below.

### **Fragments**
**_`HomeFragment`_**
Display topHeadlineNews and utilized CardStackView library (based on RecyclerView) allow users to swipe view items for like and unlike.

**_`SearchFragment`_**
Allow users to search news based on keyword. Utilized RecyclerView to display each view item in the defined layout. 

**_`SaveFragment`_**
