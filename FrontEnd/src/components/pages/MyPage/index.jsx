import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import NextArrow from "../../../assets/nextarrow.png";
import './index.css';


function MyPage() {
  return (
    <>
      <TopBar />
      <div className="font-new h-screen bg-gray-100 overflow-hidden">
        <div className="h-1/5 pt-5 text-lg">
          <p className="text-lg mb-5 mx-5 text-slate-500">로그인</p>
          <div className="bg-white w-full py-2">
            <div className="my-2 px-5 flex justify-between">
              <p>kakao</p>
              <div><button className="text-cyan-600">로그아웃</button></div>
            </div>
            <hr className="mx-5"/>
            <div className="my-2 px-5 flex justify-between">
              <p>자동로그인</p>
              <div className="h-full align-middle my-auto">
                <label className="flex items-center relative w-max cursor-pointer select-none">
                  <input type="checkbox" className="appearance-none transition-colors cursor-pointer w-14 h-7 rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-black focus:ring-blue-500 bg-red-500" />
                  <span className="absolute font-medium text-xs uppercase right-1 text-white"> OFF </span>
                  <span className="absolute font-medium text-xs uppercase right-8 text-white"> ON </span>
                  <span class="w-7 h-7 right-7 absolute rounded-full transform transition-transform bg-gray-200" />
                </label>
              </div>
            </div>
          </div>
        </div>

        <div className="h-1/5 text-lg mt-5">
          <p className="text-lg mb-5 mx-5 text-slate-500">푸시 알림</p>
          <div className="bg-white w-full py-2">
            <div className="my-2 px-5 flex justify-between">
              <p>알림 설정</p>
              <div className="w-3 h-full align-middle my-auto"><img src={NextArrow} alt="" /></div>
            </div>
            <hr className="mx-5"/>
            <div className="my-2 px-5 flex justify-between">
              <p>알림함</p>
              <div className="w-3 h-full align-middle my-auto"><img src={NextArrow} alt="" /></div>
            </div>
          </div>
        </div>

        <div className="h-1/5 text-lg">
          <p className="text-lg mb-5 mx-5 text-slate-500">앱 정보</p>
          <div className="bg-white w-full py-2">
            <div className="my-2 px-5">
              <p>현재버전</p>
            </div>
            <hr className="mx-5"/>
            <div className="my-2 px-5 flex justify-between">
              <p>오픈소스 라이선스</p>
              <div className="w-3 h-full align-middle my-auto"><img src={NextArrow} alt="" /></div>
            </div>
          </div>
        </div>

        <div className="h-1/5 text-lg">
          <p className="text-lg mb-5 mx-5 text-slate-500">회사 정보</p>
          <div className="bg-white w-full py-2">
            <div className="my-2 px-5">
              <p>회사소개 영상</p>
            </div>
            <hr className="mx-5"/>
            <div className="my-2 px-5">
              <p>팀원 소개</p>
            </div>
          </div>
        </div>

      </div>
      <BottomBar />
    </>
  );
}

export default MyPage;
