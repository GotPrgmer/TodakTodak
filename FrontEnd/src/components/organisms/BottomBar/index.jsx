import React from "react";
import { Link } from "react-router-dom";
import { Box, BottomNavigation, BottomNavigationAction } from "@mui/material";
import ChildCareIcon from "@mui/icons-material/ChildCare";
import CameraIndoorIcon from "@mui/icons-material/CameraIndoor";
import VolumeUpIcon from "@mui/icons-material/VolumeUp";
import PersonIcon from "@mui/icons-material/Person";
import { useRecoilState } from "recoil";
import { bottomBarAtom } from "../../../states/recoilHomeState";

function BottomBar(props) {
  const [value, setValue] = useRecoilState(bottomBarAtom);
  console.log(value);

  return (
    <div>
      <Box className="bg-[#FFDEDE] fixed bottom-0 left-0 z-50 w-full h-[8vh]  border-t border-gray-200 dark:bg-gray-700 dark:border-gray-600">
        <BottomNavigation
          showLabels
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
          sx={{ bgcolor: "#FFDEDE", color: "#fff" }}
        >
          <BottomNavigationAction
            label={<span style={{ fontFamily: "new" }}>홈</span>}
            icon={<ChildCareIcon />}
            value={0}
            component={Link}
            to="/profile"
            onClick={props.leaveSession}
          />
          <BottomNavigationAction
            label={<span style={{ fontFamily: "new" }}>실시간영상</span>}
            icon={<CameraIndoorIcon />}
            value={1}
            component={Link}
            to="/video"
            // onClick={props.joinSession}
            // onClick={props.handleBabyInfo}
          />
          <BottomNavigationAction
            label={<span style={{ fontFamily: "new" }}>울음기록</span>}
            icon={<VolumeUpIcon />}
            value={2}
            component={Link}
            to="/cry"
            onClick={props.leaveSession}
          />
          <BottomNavigationAction
            label={<span style={{ fontFamily: "new" }}>마이페이지</span>}
            icon={<PersonIcon />}
            value={3}
            component={Link}
            to="/mypage"
            onClick={props.leaveSession}
          />
        </BottomNavigation>
      </Box>
    </div>
  );
}

export default BottomBar;
