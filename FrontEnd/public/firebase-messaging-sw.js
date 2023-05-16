/* eslint-disable no-undef */
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
