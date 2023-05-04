// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import {
  getAnalytics,
  getMessaging,
  getToken,
  onMessage,
} from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyC28lmSh_y2INMvoK4DuOUCPngBObbMkNM",
  authDomain: "todaktodak-6846e.firebaseapp.com",
  projectId: "todaktodak-6846e",
  storageBucket: "todaktodak-6846e.appspot.com",
  messagingSenderId: "964401813700",
  appId: "1:964401813700:web:ea0d9e4fc146ea76531191",
  measurementId: "G-P2DYY794B6",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const messaging = getMessaging(app);

async function requestPermission() {
  console.log("권한 요청 중...");

  const permission = await Notification.requestPermission();
  if (permission === "denied") {
    console.log("알림 권한 허용 안됨");
    return;
  }

  console.log("알림 권한이 허용됨");

  const token = await getToken(messaging, {
    // vapidKey: process.env.REACT_APP_VAPID_KEY,
    vapidKey:
      "BBx4Y46uuGbAxbO9nH5zmkzGAXjQKblWLKOdaCfA9TuGGVnQlDowh5RmFC9ZM4GozywHbsvyKj5QmnEi3Hk1OdM",
  });

  if (token) console.log("token: ", token);
  else console.log("Can not get Token");

  onMessage(messaging, (payload) => {
    console.log("메시지가 도착했습니다.", payload);
    // ...
  });
}

requestPermission();
