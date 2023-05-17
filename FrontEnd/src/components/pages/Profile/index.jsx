import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import BabyProfile from "../../organisms/Profile";
import { useNavigate } from "react-router-dom";
import SensorDataPage from "../../organisms/Sensor/index";
import { useRecoilValue } from "recoil";
import { deviceDataAtom } from "../../../states/recoilHomeState";

function Profile() {
  const deviceData = useRecoilValue(deviceDataAtom);
  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/");
  };

  return (
    <div className="font-new">
      <TopBar />
      <div className="h-[85vh]">
        <BabyProfile />
        <SensorDataPage />
      </div>
      <BottomBar />
    </div>
  );
}

export default Profile;
