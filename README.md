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
Defined ViewModels that works with Model (DataModel: abstract data source) to get and save the data.

**_`model`_**
Defines the Java model classes that map to the server-side JSON format data.

**_`repository`_**
Used Retrofit to implement the NewsApi interface. And utilized the retrofit method to get data from endpoint and and hold the data in LiveData.

**_`network`_**
Configured Retrofit instance and defined REST APIs to send request to the endpoint and returns response. 