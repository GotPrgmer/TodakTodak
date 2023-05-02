import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import BabyProfile from "../../organisms/Profile";
// import Sensor from "../../organisms/Sensor";

// import { useEffect } from "react";
// import axios from "axios";

function Profile() {

  return (
    <div>
        <TopBar />
        <div className="pt-16">
              <BabyProfile />
              {/* <Sensor/> */}
        </div>
        <BottomBar />
    </div>
  );
}

export default Profile;