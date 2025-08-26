import React, { useEffect, useState } from 'react';
import { SafeAreaView, Button, Text } from 'react-native';
import { GoogleSignin, statusCodes } from '@react-native-google-signin/google-signin';

/**
 * A very basic React Native app with Google sign‑in support.
 *
 * To get this working, you must supply a valid Web Client ID from
 * the Google Cloud Console. Replace `YOUR_WEB_CLIENT_ID_HERE` in the
 * configuration below with your client ID. See the documentation for
 * `@react-native-google-signin/google-signin` for more details on
 * configuring your application for both Android and iOS platforms.
 */
const App = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Configure the Google Signin SDK. The webClientId is required for
    // offline access and should match the OAuth client created in Google
    // Cloud Console.
    GoogleSignin.configure({
      webClientId: 'YOUR_WEB_CLIENT_ID_HERE',
    });
  }, []);

  const signInWithGoogle = async () => {
    try {
      await GoogleSignin.hasPlayServices();
      const info = await GoogleSignin.signIn();
      setUserInfo(info);
      setError(null);
    } catch (e) {
      // Translate common error codes into user‑friendly messages.
      switch (e.code) {
        case statusCodes.SIGN_IN_CANCELLED:
          setError('User cancelled the login request');
          break;
        case statusCodes.IN_PROGRESS:
          setError('Sign‑in is already in progress');
          break;
        case statusCodes.PLAY_SERVICES_NOT_AVAILABLE:
          setError('Google Play Services not available or outdated');
          break;
        default:
          setError('An unexpected error occurred');
      }
    }
  };

  const signOut = async () => {
    try {
      await GoogleSignin.signOut();
      setUserInfo(null);
    } catch (e) {
      setError('Unable to sign out');
    }
  };

  return (
    <SafeAreaView style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      {userInfo ? (
        <>
          <Text style={{ marginBottom: 16 }}>Signed in as: {userInfo.user.email}</Text>
          <Button title="Sign Out" onPress={signOut} />
        </>
      ) : (
        <Button title="Sign In with Google" onPress={signInWithGoogle} />
      )}
      {error && <Text style={{ marginTop: 16, color: 'red' }}>{error}</Text>}
    </SafeAreaView>
  );
};

export default App;
