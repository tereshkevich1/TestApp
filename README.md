# Android Application: Camera Functionality with Local Database and Server Integration

## Overview
This Android application is developed as part of a technical assignment, featuring camera functionality that allows users to capture photos and save them to a local database along with their geographic coordinates. The application also supports automatic photo uploads to a remote server. Additionally, it includes user authentication with registration and login capabilities.

## Technologies Used
- **Architecture**: 
  - **Single Activity Architecture**: Utilizing a single activity to manage the entire application lifecycle.
  - **MVVM (Model-View-ViewModel)**: Facilitating a clear separation of concerns and enhancing testability.
  - **Clean Architecture**: Promoting a maintainable and scalable codebase.

- **User Interface**:
  - **Fragments**: For modular UI components.
  - **Jetpack Navigation**: Implementing a Modal Navigation Drawer for seamless navigation between screens.

- **Camera Functionality**:
  - **Camera Integration**: Utilizing Intents for capturing photos.

- **Data Management**:
  - **Room**: For local storage of photos and coordinates.
  - **Retrofit**: For network requests and server communication.
  - **SharedPreferences**: For storing the authorization token securely.

- **Location Services**:
  - **Fused Location Provider Client**: For obtaining the device's location accurately.

- **Reactive Programming**:
  - **Flow** and **LiveData**: For handling asynchronous data streams and UI updates in a lifecycle-aware manner.

- **Dependency Injection**:
  - **Hilt**: For managing dependencies and promoting modular design.

## Features
- **Camera Functionality**: Capture images directly from the application.
- **Local Database Storage**: Save captured images and their corresponding location data using Room.
- **Automatic Upload**: Seamlessly upload images to a remote server.
- **User Authentication**: Allow users to register and log in securely.
