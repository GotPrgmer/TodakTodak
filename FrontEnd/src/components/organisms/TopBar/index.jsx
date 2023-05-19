import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";
import Logo from "../../../assets/Logo.png";
import Alarm from "./Alarm";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  modalStateAtom,
  isReadAlarmAtom,
  alarmDataAtom,
} from "../../../states/recoilAlarmState";
import notification from "../../../assets/notification.png";
import unNotification from "../../../assets/unNotification.png";

function TopBar() {
  const isReadAlarm = useRecoilValue(isReadAlarmAtom);

  const [IsOpen, setIsOpen] = useRecoilState(modalStateAtom);
  const alarmData = useRecoilValue(alarmDataAtom);
  console.log(alarmData);

  // Modal을 Open하는 함수
  const openModal = () => {
    if (IsOpen === true) {
      setIsOpen(false);
    } else {
      setIsOpen(true);
    }
  };

  return (
    <>
      <Box
        sx={{ display: "flex" }}
        className="sticky z-10 h-[8vh] lg:px-8 lg:py-4 bg-[#FFDEDE] "
      >
        <AppBar component="nav">
          <Toolbar className=" ml-5vh h-5vh bg-[#FFDEDE] flex justify-center mr-1vh relative">
            <div className="h-5vh">
              <img className="h-[8vh] " src={Logo} alt="" />
            </div>
            <div className="h-[4vh] w-[4vh] absolute right-[2vh]">
              <button onClick={openModal}>
                {isReadAlarm === true ? (
                  <img src={unNotification} alt="" />
                ) : (
                  <>
                    {alarmData.title === "rolling" ? (
                      <>
                        <div className="animate-ping absolute h-24 w-24 rounded-full bg-red-400 opacity-75"></div>
                        <div className="relative  rounded-full bg-red-500"></div>
                      </>
                    ) : (
                      <img src={notification} alt="" />
                    )}
                  </>
                )}
              </button>
              <Alarm />
            </div>
            <div></div>
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
