# MovieListTest
Load Movies from TMDB API and poppulate a list on your device

## Requirements

 - Minimum android version 23
 - Permissions : Internet, Network state

## Installation
Clone this repository with the following command

    git clone https://github.com/hmalik144/MovieListTest/

then open with android studio.

## Features

 - SOLID coding principles applied to keep code clean and easy to read 
 - Android navigation library
 - Livedata, with lifecycle aware results
 - Data persistence with room
 - Picasso for image caching
 - Recycler view for easy user list display
 - Favourite/unfavourite movies
 
## If I had more time
 
 - Add animations for recycler list population.
 - Paginate the new entry with a smoother animation.

## Architectural Pattern

MVVM - Model View Viewmodel
SOLID coding principle

## Jetpack

* [AndroidX](https://developer.android.com/jetpack/androidx)

### Unit test
 - RepositoryTest.kt
 - MainViewModelText.kt
 - MoviesRoomDatabaseTest.kt
 
## Built With

* [Kodein](https://github.com/Kodein-Framework/Kodein-DI) - Painless Kotlin Dependency Injection
* [Retrofit](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc
* [Picasso](https://square.github.io/picasso/) - A powerful image downloading and caching library for Android
* [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [Mockito](https://github.com/mockito/mockito) - Most popular Mocking framework for unit tests written in Java

## Authors

* **Haider Malik** - *Android Developer* 
