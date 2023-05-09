import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";
import KakaoLogin from "./components/pages/Login";
import Loading from "./components/pages/Loading";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Profile />} />
          <Route path="/video" element={<Video />} />
          <Route path="/cry" element={<Cry />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/edit" element={<Edit />} />
          <Route path="/login" element={<KakaoLogin />} />
          <Route path="/api/login/oauth2/code/kakao" element={<Loading />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
