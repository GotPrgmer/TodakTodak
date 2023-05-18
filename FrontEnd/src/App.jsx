import React from "react";
import axios from "axios";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";
import Loading from "./components/pages/Loading";
import Device from "./components/pages/Device";
import Login from "./components/pages/Login";
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { useEffect } from "react";
import { babyPK, jwtToken, deviceDataAtom } from "./states/recoilHomeState";
import { alarmDataAtom, isReadAlarmAtom } from "./states/recoilAlarmState";
import NotFound from "./components/pages/NotFound";

function App() {
  // device 정보 호출
  const babyId = useRecoilValue(babyPK);
  const jwt_token = useRecoilValue(jwtToken);
  const [deviceData, setDeviceData] = useRecoilState(deviceDataAtom);
  // console.log(deviceData);

  useEffect(() => {
    async function loadData() {
      const response = await axios
        .get(`https://todaktodak.kr:8080/api/device/info/${babyId}`, {
          headers: {
            Authorization: `Bearer ${jwt_token}`,
          },
        })
        .then((res) => {
          setDeviceData(res.data);
          // console.log("device정보", res.data);
          return res.data;
        })
        .catch((e) => {
          console.log(e);
          return e;
        });
      // console.log(response);
    }
    loadData();
  }, []);

  useEffect(() => {
    async function updateData() {
      const response = await axios
        .patch(
          `https://todaktodak.kr:8080/api/device/info/update/${babyId}`,
          {
            sessionId: "todaktodak" + (1000 + parseInt(babyId)).toString(),
          },
          {
            headers: {
              Authorization: `Bearer ${jwt_token}`,
            },
          }
        )
        .then((res) => {
          setDeviceData(res.data);
          // console.log(res.data);
          return res;
        })
        .catch((e) => {
          return e;
        });
    }
    console.log(updateData());
  }, [babyId, jwt_token]);

  // Alarm Read 관리
  const [isReadAlarm, setIsReadAlarm] = useRecoilState(isReadAlarmAtom);

  // Alarm Data 관리
  const [alarmData, setAlarmData] = useRecoilState(alarmDataAtom);
  // console.log(alarmData);

  const firebaseConfig = {
    apiKey: "AIzaSyC28lmSh_y2INMvoK4DuOUCPngBObbMkNM",
    authDomain: "todaktodak-6846e.firebaseapp.com",
    projectId: "todaktodak-6846e",
    storageBucket: "todaktodak-6846e.appspot.com",
    messagingSenderId: "964401813700",
    appId: "1:964401813700:web:ea0d9e4fc146ea76531191",
    measurementId: "G-P2DYY794B6",
  };

  function requestPermission() {
    console.log("Requesting permission...");
    if (jwt_token) {
      Notification.requestPermission().then((permission) => {
        if (permission === "granted") {
          console.log("Notification permission granted.");

          // Initialize Firebase
          const app = initializeApp(firebaseConfig);

          // Initialize Firebase Cloud Messaging and get a reference to the service
          const messaging = getMessaging(app);

          getToken(messaging, {
            vapidKey:
              "BF3U9375dYGSwWvW4-7mhysfyOYnsFf5nbWEOw3vH5_KWQ2MOhSXszPGZlXSFhbDP_rUn7OYpyfW2NsamrEmVpQ",
          })
            .then((currentToken) => {
              if (currentToken) {
                console.log("currentToken: ", currentToken);
                fetch(`https://todaktodak.kr:8080/api/user/fcmKey`, {
                  method: "PATCH",
                  body: JSON.stringify({ fcmKey: currentToken }),
                  headers: {
                    Authorization: `Bearer ${jwt_token}`,
                    "Content-Type": "application/json;charset=UTF-8",
                  },
                })
                  .then((response) => {
                    if (!response.ok) {
                      throw new Error("Network response was not ok");
                    }
                    return response.json();
                  })
                  .then((data) => {
                    // console.log(data);
                  })
                  .catch((error) => {
                    console.error("Error:", error);
                  });
              } else {
                console.log(
                  "No registration token available. Request permission to generate one."
                );
              }
            })
            .catch((err) => {
              console.log("An error occurred while retrieving token. ", err);
              // ...
            });
          onMessage(messaging, (payload) => {
            console.log(payload);
            const message = {
              id: payload.messageId,
              title: payload.notification.title,
              body: payload.notification.body,
              isRead: false,
            };
            setAlarmData((prevAlarmData) => {
              const alarmDataList = [...prevAlarmData];
              if (alarmDataList.length >= 10) {
                alarmDataList.pop();
              }
              alarmDataList.unshift(message);
              console.log("alarmTime", new Date().getTime());
              // console.log(alarmDataList);
              return alarmDataList;
            });
            setIsReadAlarm(false);
            // console.log(alarmData);
          });
        } else {
          console.log("Do not get token");
        }
      });
    }
  }

  useEffect(() => {
    requestPermission();
  }, [jwt_token]);

  return (
    <>
      <div>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/video" element={<Video />} />
            <Route path="/cry" element={<Cry />} />
            <Route path="/mypage" element={<MyPage />} />
            <Route path="/edit" element={<Edit />} />
            <Route path="/api/login/oauth2/code/kakao" element={<Loading />} />
            <Route path="/device" element={<Device />} />
            <Route path="/*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </div>
    </>
  );
}

export default App;
