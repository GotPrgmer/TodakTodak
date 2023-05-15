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
  // console.log(deviceData);
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

// -----------------------------------------------------------------------------------------------
// import React, { useEffect } from "react";
// import axios from "axios";
// import VideoComponent from "./VideoComponent";
// import { useRecoilState, useRecoilValue } from "recoil";
// import {
//   babyPK,
//   deviceDataAtom,
//   jwtToken,
// } from "../../../states/recoilHomeState";

// function Video() {
//   const babyId = useRecoilValue(babyPK);
//   const jwt_token = useRecoilValue(jwtToken);
//   const [deviceData, setDeviceData] = useRecoilState(deviceDataAtom);
//   // console.log(deviceData);
//   const serialNumber = deviceData.serial_number;

//   useEffect(() => {
//     async function loadData() {
//       const response = await axios.get(
//         `https://todaktodak.kr:8080/api/device/info/${babyId}`,
//         {
//           headers: {
//             Authorization: `Bearer ${jwt_token}`,
//           },
//         }
//       );
//       setDeviceData(response.data);
//     }
//     loadData();
//   }, []);

//   return (
//     <>
//       <VideoComponent
//         babyId={babyId}
//         jwtToken={jwt_token}
//         deviceData={deviceData}
//         serialNumber={serialNumber}
//       />
//     </>
//   );
// }

// export default Video;
