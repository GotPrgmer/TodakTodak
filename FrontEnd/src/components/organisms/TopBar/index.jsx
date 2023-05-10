import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";

function TopBar() {
  return (
    <>
      <Box className="sticky inset-0 z-10 h-max max-w-full rounded-none lg:px-8 lg:py-4">
        <AppBar position="sticky">
          <Toolbar>
            <div>토닥토닥</div>
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
