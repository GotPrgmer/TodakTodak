import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  babyPK,
  bottomBarAtom,
  jwtToken,
} from "../../../states/recoilHomeState";
import Logo from "../../../assets/Logo.png";
import KakaoBtn from "../../../assets/kakaologin.png";
import Babyone from "../../../assets/babyone.png";

function Login() {
  const [bottomBar, setBottomBarAtom] = useRecoilState(bottomBarAtom);
  const jwt_token = useRecoilValue(jwtToken);
  // console.log(jwt_token);
  const babyLists = useRecoilValue(babyPK);
  const navigate = useNavigate();
  const navigateToHome = () => {
    navigate("/profile");
  };

  const REST_API_KEY = "c55a221f6dd9d3c800523222b6024866";
  const REDIRECT_URI = "https://todaktodak.kr/api/login/oauth2/code/kakao";
  // const REDIRECT_URI = "http://localhost:3000/api/login/oauth2/code/kakao";

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  useEffect(() => {
    if (jwt_token) {
      setBottomBarAtom(0);
      navigateToHome();
    }
  }, []);

  return (
    <>
      <div className="relative w-screen h-screen font-new bg-contain grid place-items-center bg-[url('https://i.pinimg.com/236x/99/3a/d8/993ad857f55996def0822f3294758ef3.jpg')]">
        <div className="w-full text-center">
          {/* <img className="w-36 mx-auto" src={Babyone} alt="" />` */}
          <img src={Logo} alt="" className="w-5/6 mx-auto" />
          <p className="text-2xl font-bamin font-thin text-yellow-900">
            초보 부모를 위한
          </p>
          <p className="text-2xl mt-1 font-bamin font-thin text-yellow-900">
            영유아 토탈 케어 서비스
          </p>
          <div className="mt-5">
            <a href={KAKAO_AUTH_URL}>
              <img src={KakaoBtn} alt="" className="mx-auto" />
            </a>
          </div>
        </div>
        <div className="absolute bottom-0 z-50 w-full flex justify-center">
          <img className="w-2/5" src={Babyone} alt="" />
        </div>
      </div>
    </>
  );
}

export default Login;
