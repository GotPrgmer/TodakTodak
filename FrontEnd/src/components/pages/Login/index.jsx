import React from "react";
import { useNavigate } from "react-router-dom";

function KakaoLogin() {
    const navigate = useNavigate();
    const navigateToHome = () => { navigate('/'); };    
    
    const REST_API_KEY = 'c55a221f6dd9d3c800523222b6024866';
    // const REDIRECT_URI = 'https://todaktodak.kr/api/login/oauth2/code/kakao';
    const REDIRECT_URI = 'http://localhost:3000/api/login/oauth2/code/kakao';

    const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`

    const handleLogin = () => {
        window.location.href = KAKAO_AUTH_URL;
    }

  return (
    <>
          <button onClick={navigateToHome}>Home</button>

          <h1><a href={KAKAO_AUTH_URL}>Kakao Login</a></h1>
    </>
  );
}

export default KakaoLogin;