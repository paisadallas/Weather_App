# Weather_App
# Libraries Used
- Hilt Dependency injection to provide constructor dependency injection to classes in the application
- Retrofit to provide access to the backend API endpoints
- Coroutines and Single Observables to run API network requests on background threads
- AndroidX to provide Lifecycle and LiveData functionality to the app
- Data binding to bind the inflated layout files to instances running in the application code.
- Picasso to provide API Images


# Architecture Design
![mvvm-architecture.png](https://postimg.cc/4HN8GmG3)

The main component in the MVVM design pattern are :

View - Informs the ViewModel about the user’s actions
ViewModel - Exposes streams of data relevant to the View
DataModel - Abstracts the data source. The ViewModel works with the DataModel to get and save the data.
