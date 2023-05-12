import React, { useEffect } from "react";
import axios from "axios";
import DeviceComponent from "./DeviceComponent";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  babyPK,
  deviceDataAtom,
  jwtToken,
} from "../../../states/recoilHomeState";

function Device() {
  const babyId = useRecoilValue(babyPK);
  const jwt_token = useRecoilValue(jwtToken);
  const [deviceData, setDeviceData] = useRecoilState(deviceDataAtom);
  const serialNumber = deviceData.serial_number;

  useEffect(() => {
    async function loadData() {
      const response = await axios.get(
        `https://todaktodak.kr:8080/api/device/info/${babyId}`,
        {
          headers: {
            Authorization: `Bearer ${jwt_token}`,
          },
        }
      );
      setDeviceData(response.data);
    }
    loadData();
  }, []);

  return (
    <>
      <DeviceComponent
        babyId={babyId}
        jwtToken={jwt_token}
        deviceData={deviceData}
        serialNumber={serialNumber}
      />
    </>
  );
}

export default Device;
