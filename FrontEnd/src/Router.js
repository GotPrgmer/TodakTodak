import React from "react";
import { createBrowserRouter } from "react-router-dom";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";
import KakaoLogin from './components/pages/Login/index';
import Loading from "./components/pages/Loading";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Profile />,
  },
  {
    path: "/video",
    element: <Video />,
  },
  {
    path: "/cry",
    element: <Cry />,
  },
  {
    path: "/mypage",
    element: <MyPage />,
  },
  {
    path: "/edit",
    element: <Edit />,
  },
  {
    path: "/login",
    element: <KakaoLogin />,
  },
  {
    path: "/login/oauth2/code/kakao",
    element: <Loading />,
  },
]);

export default router;
