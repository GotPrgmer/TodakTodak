import React, { useEffect } from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import BabyProfile from "../../organisms/Profile";
import { useNavigate } from "react-router-dom";
import SensorDataPage from "../../organisms/Sensor/index";
import { useRecoilState, useRecoilValue } from "recoil";
import { bottomBarAtom, deviceDataAtom } from "../../../states/recoilHomeState";

function Profile() {
  const [bottomBar, setBottomBar] = useRecoilState(bottomBarAtom);
  const deviceData = useRecoilValue(deviceDataAtom);
  const navigate = useNavigate();
  const navigateToLogin = () => {
    navigate("/");
  };

  useEffect(() => {
    setBottomBar(0);
  }, []);

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
