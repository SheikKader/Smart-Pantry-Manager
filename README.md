# Smart Pantry Manager

**Course:** Mobile App Development 700  
**Student Name:** Sheik Abdul Ayaaz Kader  
**Student Number:** 402413277

## Overview
Smart Pantry Manager is an Android app I built to help track what ingredients you have at home and suggest recipes you can make right now. It uses a local SQLite database to save everything, and I wrote a custom algorithm that only suggests a recipe if you have every single required ingredient in your pantry.

## Key Features
- **Add/Edit/Delete Ingredients:** You can manage your inventory (name, quantity, unit, and expiry date).
- **Strict Recipe Matching (Section 2.3):** The app checks your current stock against a list of recipes. It will only show a recipe if you have 100% of the ingredients needed.
- **Auto-Refreshing Lists:** The app uses the `onResume()` lifecycle method to automatically update the RecyclerView when you go back to the main screen, so you don't have to hit a refresh button.
- **Offline Storage:** Everything is saved locally using SQLite, so the app works without the internet.

## Technology Stack
- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite
- **UI:** RecyclerView, Material Buttons, Floating Action Button

## Main Project Files
- `MainActivity.java`: Handles the main screen and list updates.
- `PantryDb.java`: Contains the database setup, queries, and the recipe-matching logic.
- `PantryAdapter.java`: Connects the database rows to the RecyclerView layout.
- `AddIngredientActivity.java` & `EditIngredientActivity.java`: Handle the forms for adding and updating items.

## How to Run the App
1. Extract the submission ZIP file.
2. Open **Android Studio**.
3. Go to **File > Open** and select the extracted `SmartPantryManager` folder.
4. Wait for Gradle to finish syncing.
5. Click the green **Run** button at the top to launch it on an emulator or plugged-in phone.

## Video Demonstration
I have included a 10 minute screen recording 402413277 app video .mp4 in the main folder of this ZIP. It covers:
1. My GitHub commit history.
2. A live demo showing how to add items and how the strict-matching algorithm works.
3. A code walkthrough where I explain how I used Intents, the Activity Lifecycle, and RecyclerView Adapters.