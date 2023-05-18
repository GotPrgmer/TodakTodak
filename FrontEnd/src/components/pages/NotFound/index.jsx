import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";

function NotFound() {
  return (
    <>
      <TopBar />
      <div className="flex flex-col justify-center items-center mt-[10vh]">
        <img
          className="object-cover"
          src="https://todak-s3-free-bucket.s3.ap-northeast-2.amazonaws.com/assets/DwAW1676947841_197213620.png"
          alt="asdf"
        />
        <div className="font-bold text-xl">여기 맞아? 기기 번호는</div>
      </div>
      <BottomBar />
    </>
  );
}

export default NotFound;
