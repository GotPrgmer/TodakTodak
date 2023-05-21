import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";

function NotFound() {
  return (
    <>
      <TopBar />
      <div className="flex flex-col justify-center items-center mt-[20vh]">
        <img
          className="w-[50%]"
          src="https://todak-s3-free-bucket.s3.ap-northeast-2.amazonaws.com/assets/image-removebg-preview (15) 1.png"
          alt="asdf"
        />
        <div className="font-bold text-xl mb-1">잘못된 주소이거나</div>
        <div className="font-bold text-xl">기기를 등록해주세요. </div>
      </div>
      <BottomBar />
    </>
  );
}

export default NotFound;
