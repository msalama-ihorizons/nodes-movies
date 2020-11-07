# Nodes Movies Task

Nodes Movies prototype application to allow users to search for a specific movie and list them with its title, genres, rate and release dates. 
and also user could check more details about the movie like movie cast and also the similar movies. User can add movies to his favourites list and rate the movie as well.

# Features 

•	User will able to search for specific movie

•	User will be able to see more details about the movie.

•	User will be able to add movies to his favourites list. 

•	User will be able to rate movies. 


# Technical Features

•	Model-View-ViewModel architecture.

•	Kotlin Programming language 

•	Kotlin Coroutines to handle all asynchronous tasks   

•	Android Jetpack

    o LiveData to notify view with any data changes
  
    o Lifecycle handle lifecycle state changes 
  
    o ViewModel allows data to survive configuration changes like screen rotations 
  
    o Room to save data locally on SQLite so user can use the app offline 
  
    o Hilt to handle dependency injection 
  
•	Retrofit for https network calls

•	Junit4, Hamcrest, AndroidX Test, and AndroidX architecture components core for building unit tests

# Prototype Packages 

•	api: it contains movies DB apis and its responses models

•	model: contains all model classes.

•	repository: contains repositories classes to fetch data and handle all business logic.

•	db: for local database creation and its operations like insert and select

•	di: to provide app third party libraries dependencies like Room and Retrofit

•	ui: contains view classes along with their ViewModel.

•	test: contains all unit tests classes

# Libraries

•	Retrofit 

•	GSON

•	Hilt

•	Room

•	Junit4

•	Robolectric

•	Mockito

# How it works 

Once app is open for the first time, Spider man Movies will be loaded from Movies DB APIs.

When user click on search button, will be able to type and search for specifc movie and once he clicks on one of movies, app will open details activity for displaying more details about the selected movie and also to show movie's cast, and similar movies. 
User could click on rating button to rate the movie.

# Screen Shots
![Alt text](/app/shots/1.jpg?raw=true "")
![Alt text](/app/shots/2.jpg?raw=true "")
![Alt text](/app/shots/3.jpg?raw=true "")
![Alt text](/app/shots/4.jpg?raw=true "")





