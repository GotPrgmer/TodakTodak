import React from "react";
import { createBrowserRouter } from "react-router-dom";
import Profile from "./components/pages/Profile";
import Video from "./components/pages/Video";
import Cry from "./components/pages/Cry";
import MyPage from "./components/pages/MyPage";
import Edit from "./components/pages/Edit";

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
]);

export default router;
