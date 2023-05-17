import React from "react";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";

function NotFound() {
  return (
    <>
      <TopBar />
      <div> 기기 번호가 일치하지 않습니다.</div>
      <BottomBar />
    </>
  );
}

export default NotFound;
