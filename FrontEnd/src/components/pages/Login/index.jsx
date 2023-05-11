import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { babyPK, jwtToken } from "../../../states/recoilHomeState";

function KakaoLogin() {
  const jwt_token = useRecoilValue(jwtToken);
  const babyLists = useRecoilValue(babyPK);
  const navigate = useNavigate();
  const navigateToHome = () => {
    navigate("/profile");
  };

  const REST_API_KEY = "c55a221f6dd9d3c800523222b6024866";
  // const REDIRECT_URI = 'https://todaktodak.kr/api/login/oauth2/code/kakao';
  const REDIRECT_URI = "http://localhost:3000/api/login/oauth2/code/kakao";

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  useEffect(() => {
    if (jwt_token) {
      console.log(babyLists[0]);
      navigateToHome();
    }
  }, [])

  return (
    <>
      <div className="w-screen h-screen font-new">

        <a href={KAKAO_AUTH_URL}>Kakao Login</a>
      </div>
      <h1 className="font-new">
        <div> PWA Download 5트 </div>
      </h1>
    </>
  );
}

export default KakaoLogin;
