import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";
import Logo from "../../../assets/Logo.png";

function TopBar() {
  return (
    <>
      <Box className="sticky z-10 h-[8vh] lg:px-8 lg:py-4 bg-[#FFDEDE] ">
        <AppBar position="sticky">
          <Toolbar className="bg-[#FFDEDE] flex justify-center">
              <img className="h-[8vh]" src={Logo} alt="" />
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
