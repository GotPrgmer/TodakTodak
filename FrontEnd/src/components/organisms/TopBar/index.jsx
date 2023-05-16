import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";
import Logo from "../../../assets/Logo.png";
import Alarm from "./Alarm";
import { useRecoilState } from "recoil";
import { modalStateAtom } from "../../../states/recoilAlarmState";

function TopBar() {
  const [IsOpen, setIsOpen] = useRecoilState(modalStateAtom);

  // Modal을 Open하는 함수
  const openModal = () => {
    setIsOpen(true);
  };

  return (
    <>
      <Box className="sticky z-10 h-[8vh] lg:px-8 lg:py-4 bg-[#FFDEDE] ">
        <AppBar position="sticky">
          <Toolbar className="bg-[#FFDEDE] flex justify-center">
            <img className="h-[8vh]" src={Logo} alt="" />
            <div>
              <button onClick={openModal}>알람</button>
            </div>
            <div>
              <Alarm />
            </div>
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
