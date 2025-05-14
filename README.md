# Scout Weather Interview App
- **Skip to bottom to see setup instructions** 

## Requirements
- Create a weather app.
- Use third-party libraries only if necessary.
- Use the [Weather API](https://www.weatherapi.com) to fetch weather data.
- Create a free API key at [Weather API](https://www.weatherapi.com).
- Handle API keys and rate limits appropriately.
- Focus on a basic, clean, intuitive UI.
- Prioritize app coding skills and functional requirements over design.

## App Features
### Two Screens with Navigation
1. **7-Day Forecast Screen**
    - Display a scrollable list view of the 7-day forecast (including today) in Fahrenheit.
    - Retrieve the user’s device location (latitude and longitude) to fetch the 7-day forecast via the Weather API.
    - Each row should include:
        - Average, minimum, and maximum temperatures.
        - Text condition and corresponding image icon (e.g., “Sunny” with a sun icon) using the icon URL from the API’s “condition” JSON block.
    - Tapping a row navigates to the Forecast Details Screen.

2. **Forecast Details Screen**
    - Display details for the selected forecast day, including:
        - Information from the 7-day forecast row (average, min, max temperatures, condition, icon).
        - User’s location name and region (e.g., “Fremont, California”).
        - Chance of rain.
        - Chance of snow.
        - Average humidity.

### Additional Requirements
- **Temperature Toggle**: Add a header on the 7-Day Forecast Screen with a button or switch to toggle between Fahrenheit and Celsius.
    - Toggling should update the list view temperatures without making a new API request.
- **Mock Analytics System**:
    - Implement a mock analytics system that triggers on user interactions (e.g., clicking weather details, toggling Fahrenheit/Celsius, or other relevant events).
    - Log analytics events to the console (e.g., print the analytics tag string). No connection to an existing analytics platform or backend is required.

## Technical Requirements
- Use the **MVVM** architecture pattern (or another pattern if strongly preferred, with justification).
- Implement proper **error handling** for API requests.
- Include basic **unit tests**.

## Submission
- Provide a **GitHub repository link** with the solution.
- Include a **README** with:
    - Setup instructions.
    - Any assumptions made during development.



## Instructions to setup the project

### Prerequisites
Ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 11 (required for `jvmTarget = "11"`).
    - Download from [Oracle JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use a package manager like `sdkman` (`sdk install java 11.0.21-open`).
- **Android Studio**: Version 2024.1.1 (Koala) or later.
    - Download from [Android Studio](https://developer.android.com/studio).
- **Git**: For cloning the repository.
    - Install via [Git](https://git-scm.com/) or your package manager.
- **Internet Connection**: Required to download Gradle dependencies.

## Setup Instructions
1. **Clone the repository:**
 - git clone https://github.com/eldenskloss/ScoutWeatherInterview.git
 - cd scout-weather-interview
2. **Open Project in Android Studio:**
3. **Sync the project**
4. **Start up emulator or plug in device**
5. **Run Project**
