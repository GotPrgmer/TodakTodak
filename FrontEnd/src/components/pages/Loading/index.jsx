import React, { useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";



function Loading() {
    const navigate = useNavigate();
    const navigateToHome = () => { navigate('/'); };  

    useEffect(() => {
        const params = new URL(document.location.toString()).searchParams;
        const code = params.get("code");
        // const REST_API_KEY = 'c55a221f6dd9d3c800523222b6024866';
        // const REDIRECT_URI = 'http://localhost:3000/login/oauth2/code/kakao';
        console.log('인가코드 : ' + code)

        async function transferCode() {
            await axios.get(`http://todaktodak.kr:8080/login/oauth2/callback/kakao?code=${code}`)
            // await axios.get(`http://todaktodak.kr:8080/login/kakao?code=${code}`)
                .then((response) => {
                    console.log(response)
                    // console.log(response.data.jwt_token)

                    if (response.data.jwt_token) {
                        navigateToHome()
                    }
                })
                .catch((error) => console.log(error))
        }

        transferCode()

    }, []);

  return (
    <>
      <div>LoadingPage</div>
    </>
  );
}

export default Loading;
