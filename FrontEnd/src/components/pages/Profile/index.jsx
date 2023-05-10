import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import BabyProfile from "../../organisms/Profile";
// import Sensor from "../../organisms/Sensor";

// import { useEffect } from "react";
// import axios from "axios";
import { useNavigate } from "react-router-dom";
import SensorDataPage from "../../organisms/Sensor/index";

function Profile() {
  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/");
  };

  return (
    <div>
      <TopBar />
      <div>
        <BabyProfile />
        {/* <Sensor/> */}
        <button onClick={navigateToLogin}>로그인 페이지</button>
        <SensorDataPage/>
        </div>
        <BottomBar />
    </div>
  );
}

export default Profile;
