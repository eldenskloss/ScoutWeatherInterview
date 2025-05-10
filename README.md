Scout Weather Interview APP

**Requirements**
•	Create a weather app.
•	Use third party libraries only if necessary. 
•	Use the Weather API to fetch weather data.
•	Create a free API key for the Weather API here: https://www.weatherapi.com 
•	Ensure you handle API keys and rate limits appropriately. 
•	Focus on creating a basic, clean, intuitive UI. 
•	Don’t stress over design; we aim to assess your app coding skills and ability to implement functional requirements.  

**2 Screens with navigation:**
•	7 Day forecast screen
o	Create a screen with a scrollable list view of the 7 Day forecast (including today) in Fahrenheit.
o	The app should get the users device location (latitude and longitude) and then pass that into the forecast endpoint to get the 7 Day forecast.
o	Each row should show the average, min and max temperatures. 
o	Each row should show a text condition and image icon for that condition. For example, “Sunny” [sun icon]. Use the icon URL provided in the “condition” JSON block provided by the API.
o	Tapping on a row should take you to the Forecast details screen.
•	Forecast details screen 
o	Show the details of the forecast day that was tapped on.
The details screen should show a larger UI with the same information as the row, but also add:
o	The user’s location name and region from the API. E.g. “Fremont, California”
o	Chance of rain
o	Chance of snow
o	Average humidity

**Additional requirements:**
•	Add a header on the 7 Day forecast screen with a button or switch to toggle between Fahrenheit and Celsius. 
o	Toggling the button/switch should update the list view temperatures without making a new network request to the Weather API
•	Add a mock analytics system that is triggered upon user events. The system should call a function/method when the user interacts with the UI such as clicking on weather details, toggling Fahrenheit/Celsius, or wherever else you think would be useful.
o	 You don’t need to connect to an existing analytics platform or add a backend service to the app, you can just have the event print the analytics tag string to console/logcat

**Technical Requirements **
•	Use MVVM. If you feel strongly about this, use the pattern of your choice
•	Implement proper error handling for API requests
•	Implement basic unit tests

Submission 
•	Provide a GitHub repository link with your solution.
•	Include a README with setup instructions and any assumptions made
![image](https://github.com/user-attachments/assets/e975a210-42b9-4acb-b490-7d354dcc869a)
