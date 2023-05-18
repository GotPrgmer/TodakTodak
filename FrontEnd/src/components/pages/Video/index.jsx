import React from "react";
import VideoComponent from "./VideoComponent";
import { useRecoilValue } from "recoil";
import {
  babyPK,
  deviceDataAtom,
  jwtToken,
  serialNumberAtom,
} from "../../../states/recoilHomeState";
import NotFound from "../NotFound";

function Video() {
  const babyId = useRecoilValue(babyPK);
  const jwt_token = useRecoilValue(jwtToken);
  const deviceData = useRecoilValue(deviceDataAtom);
  const serialNumber = deviceData.serial_number;
  const serialNumberText = useRecoilValue(serialNumberAtom);

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
