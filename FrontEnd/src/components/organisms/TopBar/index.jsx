import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";
import Logo from "../../../assets/Logo.png";

function TopBar() {
  return (
    <>
      <Box className="sticky inset-0 z-10 h-max max-w-full rounded-none lg:px-8 lg:py-4">
        <AppBar position="sticky">
          <Toolbar className="bg-[#FFDEDE] flex justify-center">
              <img className="h-[4rem]" src={Logo} alt="" />
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
