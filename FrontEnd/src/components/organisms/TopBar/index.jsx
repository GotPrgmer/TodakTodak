import React from "react";
import { Box, AppBar, Toolbar } from "@mui/material";

function TopBar() {
  return (
    <>
      <Box sx={{ display: "flex" }}>
        <AppBar>
          <Toolbar>
            <div>토닥토닥</div>
          </Toolbar>
        </AppBar>
      </Box>
    </>
  );
}

export default TopBar;
