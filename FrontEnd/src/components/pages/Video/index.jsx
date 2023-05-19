import React, { useEffect } from "react";
import VideoComponent from "./VideoComponent";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  babyPK,
  bottomBarAtom,
  deviceDataAtom,
  jwtToken,
  serialNumberAtom,
} from "../../../states/recoilHomeState";
import NotFound from "../NotFound";

function Video() {
  const [bottomBar, setBottomBar] = useRecoilState(bottomBarAtom);
  const babyId = useRecoilValue(babyPK);
  const jwt_token = useRecoilValue(jwtToken);
  const deviceData = useRecoilValue(deviceDataAtom);
  const serialNumber = deviceData.serial_number;
  const serialNumberText = useRecoilValue(serialNumberAtom);

  useEffect(() => {
    setBottomBar(1);
  }, []);

  return (
    <>
      {serialNumberText === serialNumber ? (
        <VideoComponent
          babyId={babyId}
          jwtToken={jwt_token}
          deviceData={deviceData}
          serialNumber={serialNumber}
        />
      ) : (
        <NotFound />
      )}
    </>
  );
}

export default Video;
