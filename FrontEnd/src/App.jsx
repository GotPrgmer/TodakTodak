import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./firebase";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";
import Loading from "./components/pages/Loading";
import Device from "./components/pages/Device";
import Login from "./components/pages/Login";

function App() {
  return (
    <>
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
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
