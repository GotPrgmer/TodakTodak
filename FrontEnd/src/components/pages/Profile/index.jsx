import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import BabyProfile from "../../organisms/Profile";
// import Sensor from "../../organisms/Sensor";

// import { useEffect } from "react";
// import axios from "axios";
import { useNavigate } from "react-router-dom";

function Profile() {

  const navigate = useNavigate();
  const navigateToLogin = () => { navigate('/login'); };

  return (
    <div>
        <TopBar />
        <div className="pt-16">
              <BabyProfile />
        {/* <Sensor/> */}
        <button onClick={navigateToLogin}>로그인 페이지</button>
        </div>
        <BottomBar />
    </div>
  );
}

export default Profile;