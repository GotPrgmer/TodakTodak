import React, { useState, useEffect } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import axios from "axios";
import "./firebase";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";
import KakaoLogin from "./components/pages/Login";
import Loading from "./components/pages/Loading";
import Device from "./components/pages/Device";
import { useRecoilValue } from "recoil";
import { babyPK, jwtToken } from "./states/recoilHomeState";

function App() {
  const babyData = useRecoilValue(babyPK);
  // const [information, setInformation] = useState([]);
  // console.log(information);
  // const babyLists = useRecoilValue(babyPK);
  // console.log(babyLists);
  // const jwt_token = useRecoilValue(jwtToken);
  // console.log(jwt_token);

  // useEffect(() => {
  //   async function loadData() {
  //     const response = await axios.get(
  //       `https://todaktodak.kr:8080/api/baby/info/${babyLists[0]}`,
  //       {
  //         headers: {
  //           Authorization: `Bearer ${jwt_token}`,
  //         },
  //       }
  //     );
  //     setInformation(response.data);
  //   }
  //   loadData();
  // }, []);

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<KakaoLogin />} />
          <Route path="/profile" element={<Profile />} />
          <Route
            path="/video"
            element={<Video />}
            render={(props) => <Video babyData={babyData} {...props} />}
          />
          <Route path="/cry" element={<Cry />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/edit" element={<Edit />} />
          {/* <Route path="/login" element={<KakaoLogin />} /> */}
          <Route path="/api/login/oauth2/code/kakao" element={<Loading />} />
          <Route path="/device" element={<Device />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
