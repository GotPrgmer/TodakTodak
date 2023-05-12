import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { babyPK, jwtToken } from "../../../states/recoilHomeState";
import Load from "../../../assets/loading.gif";

function Loading() {
  const navigate = useNavigate();
  const navigateToHome = () => {
    navigate("/profile");
  };

  const [babyData, setBabyData] = useRecoilState(babyPK);
  const [tokenData, setTokenData] = useRecoilState(jwtToken);

  const [date, setDate] = useState([]);

  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get("code");
    // const REST_API_KEY = 'c55a221f6dd9d3c800523222b6024866';
    // const REDIRECT_URI = 'http://localhost:3000/login/oauth2/code/kakao';
    console.log("인가코드 : " + code);

    async function transferCode() {
      await axios
        .get(
          `https://todaktodak.kr:8080/api/login/oauth2/code/kakao?code=${code}`
        )
        .then((response) => {
          console.log(response);
          // console.log(response.data.jwt_token)
          setBabyData(response.data.baby_ids);
          setTokenData(response.data.jwt_token);
          if (response.data.jwt_token) {
            navigateToHome();
          }
        })
        .catch((error) => console.log("!!!" + error));
    }

    transferCode();
  }, []);

  return (
    <>
      <div className="w-screen h-screen font-new bg-contain grid place-items-center bg-[#FED000]">
        <img className="w-screen" src={Load} alt="" />
      </div>
    </>
  );
}

export default Loading;
