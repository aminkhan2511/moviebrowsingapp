# Movie Browsing App

A modern Android app to browse movies, view details, watch trailers, and explore posters and metadata fetched from a movie API (e.g., TMDB API).

---

## Project Setup

### Key Features
1. **Movie Details:**
   - View movie titles, descriptions, posters, and trailers.
2. **API Integration:**
   - Fetch movie metadata, posters, and trailers using an external API.
3. **UI Components:**
   - User-friendly interface designed with Material Design and ViewPager2 for smooth navigation.

---
https://github.com/user-attachments/assets/9ab51573-7ff7-4066-a738-5858355a2ae2

![WhatsApp Image 2024-12-02 at 01 02 59_3f4069c3]
(https://github.com/user-attachments/assets/a050e7c4-ac21-4c41-92ea-6c9
![WhatsApp Image 2024-12-02 at 01 02 59_ff869d4e](https://github.com/user-attachments/assets/986eddb7-9e3c-471b-9489-a5d086572b75)
15f1c957f)
![WhatsApp Image 2024-12-02 at 01 02 58_e84e8271](https://github.com/user-attachments/assets/5fb1e1f6-1b12-47b8-a107-9932af005d1c)
![WhatsApp Image 2024-12-02 at 01 03 00_be649b8b](https://github.com/user-attachments/assets/50f19bbf-d865-44b9-9ed5-d9a484aad3aa)

## Libraries and Their Justifications

### Core Libraries
1. **Navigation Components (`androidx.navigation`):**
   - Manages app navigation between fragments.
   - Simplifies deep linking and back-stack handling.

2. **Lifecycle Components (`ViewModel`, `LiveData`, `runtime-ktx`):**
   - `ViewModel`: Handles UI-related data in a lifecycle-aware manner.
   - `LiveData`: Updates the UI automatically when data changes.
   - Ensures smooth handling of configuration changes (e.g., screen rotation).

### Networking Libraries
1. **Retrofit and OkHttp:**
   - **Retrofit:** Simplifies HTTP API calls for fetching movie data.
   - **Gson Converter:** Parses JSON responses into Kotlin/Java objects.



   - **OkHttp Logging Interceptor:** Logs HTTP requests and responses for debugging.

### Image Loading
1. **Glide:**
   - Efficiently loads and caches movie posters and images.
   - Handles image transformations with minimal memory usage.

### Video Playback
1. **YouTube Player (`androidyoutubeplayer`):**
   - Integrates YouTube videos for seamless trailer playback.
   - Provides features like full-screen mode and video controls.

### UI Components
1. **Material Design (`com.google.android.material`):**
   - Modern UI elements like buttons, toolbars, and navigation drawers for a polished user experience.

2. **Circle ImageView (`de.hdodenhof:circleimageview`):**
   - Displays circular images, such as actor photos or thumbnails.

3. **ViewPager2:**
   - Enables swiping between pages/fragments, ideal for browsing movie categories or recommendations.

---

## Build and Run Process

### Steps to Build the Project
1. **Ensure Environment Setup:**
   - Android Studio with SDKs for API level 24 (minimum) and API level 35 (compile and target).
   - Gradle version aligned with project configuration.

2. **Dependencies:**
   - All required libraries are declared in the `build.gradle` file.
   - Sync the project in Android Studio to download and configure dependencies.

3. **Parcelize Plugin:**
   - The `kotlin-parcelize` plugin is added for easier data transfer between components (e.g., fragments).

4. **Retrofit Configuration:**
   - Define API service interfaces for fetching movie data.
   - Configure API key and endpoint in your project.

### Steps to Run the Project
1. **Connect an Emulator or Device:**
   - Use a physical device or emulator running Android 7.0 (API 24) or higher.

2. **Launch the App:**
   - Build and run the app in Android Studio.
   - The app will load the home screen, fetch movie data, and display it.

3. **Debugging Tools:**
   - Use Logcat to identify and debug API response issues or UI glitches.
   - Verify API requests using the OkHttp logging interceptor.

---

# Architecture: MVVM (Model-View-ViewModel)

This app is built using the MVVM (Model-View-ViewModel) architecture pattern, which ensures a clean separation of concerns and facilitates testability and scalability.

## Key Components

### Model
- Handles the data layer of the app.
- Fetches movie-related data from the API using Retrofit.
- Provides data to the ViewModel via repositories.

### View
- Represents the UI layer, such as Activity and Fragment.
- Observes data changes exposed by the ViewModel using LiveData.
- Displays movie details like posters, trailers, and descriptions.

### ViewModel
- Acts as a bridge between the Model and View.
- Contains logic to fetch and manage movie data via the repository.
- Exposes data using LiveData, ensuring lifecycle-aware updates.

## Advantages of Using MVVM
- **Separation of Concerns**: Keeps UI logic out of the Activity/Fragment, improving maintainability.
- **Lifecycle Awareness**: With LiveData, the UI reacts only when it is in a proper state.
- **Testability**: ViewModel logic can be easily unit-tested without involving the UI.
- **Scalability**: Suitable for larger apps with complex UI and data management needs.

---

## Conclusion
This movie app setup is well-organized, leveraging modern libraries to handle networking, UI, and video playback efficiently. It provides a solid foundation for browsing and interacting with movie data.

Let me know if you need further assistance with its development!
