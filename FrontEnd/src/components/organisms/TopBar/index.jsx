import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";
import Logo from "../../../assets/Logo.png";
import Alarm from "./Alarm";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  modalStateAtom,
  isReadAlarmAtom,
} from "../../../states/recoilAlarmState";
import notification from "../../../assets/notification.png";
import unNotification from "../../../assets/unNotification.png";

function TopBar() {
  const isReadAlarm = useRecoilValue(isReadAlarmAtom);

  const [IsOpen, setIsOpen] = useRecoilState(modalStateAtom);

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
      <Box className="sticky z-10 h-[8vh] lg:px-8 lg:py-4 bg-[#FFDEDE] ">
        <AppBar position="sticky">
          <Toolbar className="ml-5vh h-5vh bg-[#FFDEDE] flex justify-end mr-2vh">
            <div className="w-20vh h-5vh">
              <img className="h-[8vh] " src={Logo} alt="" />
            </div>
            <div className="h-[4vh] w-[4vh] ml-[11vh]">
              <button onClick={openModal}>
                {isReadAlarm === true ? (
                  <img src={unNotification} alt="" />
                ) : (
                  <img src={notification} alt="" />
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
