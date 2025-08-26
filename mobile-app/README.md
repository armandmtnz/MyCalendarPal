# MyCalendarPal Mobile App

This directory contains a basic React Native mobile application that implements Google authentication using the
`@react-native-google-signin/google-signin` package. It serves as a starting point for a mobile version of
the MyCalendarPal project.

## Setup

1. Install dependencies using **npm** or **yarn**:

   ```bash
   npm install
   # or
   yarn install
   ```

2. Configure Google Sign‑In:
   - Create an OAuth client in the Google Cloud Console and obtain a **Web Client ID**.
   - In `App.js`, replace the placeholder `YOUR_WEB_CLIENT_ID_HERE` with your actual Web Client ID.
   - For platform‑specific setup (Android/iOS), follow the instructions in the
     [`@react-native-google-signin/google-signin` documentation](https://github.com/react-native-google-signin/google-signin).

3. Run the app:

   ```bash
   npm run android   # run on Android device or emulator
   npm run ios       # run on iOS simulator or device
   ```

## Notes

- This is an initial skeleton. Building a fully functioning mobile application requires additional configuration
  (such as adding native project files under `android/` and `ios/`) which are not included here.
- Ensure that your development environment is set up for React Native. The [React Native Getting Started guide](https://reactnative.dev/docs/environment-setup) provides platform-specific instructions.
