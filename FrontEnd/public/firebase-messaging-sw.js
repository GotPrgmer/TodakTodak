/* eslint-disable no-undef */
// import { initializeApp } from "firebase/app";
// import { getMessaging, onBackgroundMessage } from "firebase/messaging/sw";

// // Initialize the Firebase app in the service worker by passing in
// // your app's Firebase config object.
// // https://firebase.google.com/docs/web/setup#config-object
// const firebaseApp = initializeApp({
//   apiKey: "AIzaSyC28lmSh_y2INMvoK4DuOUCPngBObbMkNM-key",
//   authDomain: "todaktodak-6846e.firebaseapp.com",
//   databaseURL: "https://todaktodak-6846e.firebaseio.com",
//   projectId: "todaktodak-6846e",
//   storageBucket: "todaktodak-6846e.appspot.com",
//   messagingSenderId: "964401813700",
//   appId: "1:964401813700:web:ea0d9e4fc146ea76531191",
//   measurementId: "G-P2DYY794B6",
// });

// // Retrieve an instance of Firebase Messaging so that it can handle background
// // messages.
// const messaging = getMessaging(firebaseApp);

// onBackgroundMessage(messaging, (payload) => {
//   console.log(
//     "[firebase-messaging-sw.js] Received background message ",
//     payload
//   );
//   // Customize notification here
//   const notificationTitle = "Background Message Title";
//   const notificationOptions = {
//     body: "Background Message body.",
//     icon: "/firebase-logo.png",
//   };

//   self.registration.showNotification(notificationTitle, notificationOptions);
// });
// [END messaging_on_background_message_modular]

// ---------------------------------------------------------------------------------

// importScripts("/__/firebase/9.2.0/firebase-app-compat.js");
// importScripts("/__/firebase/9.2.0/firebase-messaging-compat.js");
// importScripts("/__/firebase/init.js");

importScripts("https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js");

const firebaseConfig = {
  apiKey: "AIzaSyC28lmSh_y2INMvoK4DuOUCPngBObbMkNM",
  authDomain: "todaktodak-6846e.firebaseapp.com",
  // Test Code(databaseURL)
  // databaseURL: "https://todaktodak-6846e.firebaseio.com",
  projectId: "todaktodak-6846e",
  storageBucket: "todaktodak-6846e.appspot.com",
  messagingSenderId: "964401813700",
  appId: "1:964401813700:web:ea0d9e4fc146ea76531191",
  measurementId: "G-P2DYY794B6",
};

const app = firebase.initializeApp(firebaseConfig);
// Test Code
// const messaging = firebase.messaging(app);
// 성공 Code
const messaging = firebase.messaging();

messaging.onMessage(messaging, (payload) => {
  console.log("Message received.", payload);
});

// messaging.onBackgroundMessage(function (payload) {
//   console.log(
//     "[firebase-messaging-sw.js] Received background message ",
//     payload
//   );
//   // Customize notification here
//   const notificationTitle = "Background Message Title";
//   const notificationOptions = {
//     body: "Background Message body.",
//     icon: "/firebase-logo.png",
//   };

//   self.registration.showNotification(notificationTitle, notificationOptions);
// });
