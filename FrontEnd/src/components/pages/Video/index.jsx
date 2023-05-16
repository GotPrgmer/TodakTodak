import React, { useEffect } from "react";
import axios from "axios";
import VideoComponent from "./VideoComponent";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  babyPK,
  deviceDataAtom,
  jwtToken,
} from "../../../states/recoilHomeState";

function Video() {
  const babyId = useRecoilValue(babyPK);
  const jwt_token = useRecoilValue(jwtToken);
  const [deviceData, setDeviceData] = useRecoilState(deviceDataAtom);
  const serialNumber = deviceData.serial_number;

  useEffect(() => {
    async function loadData() {
      const response = await axios
        .get(`https://todaktodak.kr:8080/api/device/info/${babyId}`, {
          headers: {
            Authorization: `Bearer ${jwt_token}`,
          },
        })
        .then((res) => {
          setDeviceData(res.data);
          // console.log("device정보", res.data);
          return res.data;
        })
        .catch((e) => {
          console.log(e);
          return e;
        });
      console.log(response);
    }
    loadData();
  }, []);

  useEffect(() => {
    async function updateData() {
      const response = await axios
        .patch(
          `https://todaktodak.kr:8080/api/device/info/update/${babyId}`,
          {
            sessionId: "todaktodak" + (1000 + parseInt(babyId)).toString(),
          },
          {
            headers: {
              Authorization: `Bearer ${jwt_token}`,
            },
          }
        )
        .then((res) => {
          setDeviceData(res.data);
          // console.log(res.data);
          return res;
        })
        .catch((e) => {
          return e;
        });
    }
    console.log(updateData());
  }, [babyId, jwt_token]);

  return (
    <>
      <VideoComponent
        babyId={babyId}
        jwtToken={jwt_token}
        deviceData={deviceData}
        serialNumber={serialNumber}
      />
    </>
  );
}

export default Video;
