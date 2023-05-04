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

  return (
    <div>
      <Box className=" fixed bottom-0 left-0 z-50 w-full h-16 bg-white border-t border-gray-200 dark:bg-gray-700 dark:border-gray-600">
        <BottomNavigation
          showLabels
          value={value}
          onChange={(event, newValue) => {
            setValue(newValue);
          }}
        >
          <BottomNavigationAction
            label="홈"
            icon={<ChildCareIcon />}
            value={0}
            component={Link}
            to="/"
          />
          <BottomNavigationAction
            label="실시간영상"
            icon={<CameraIndoorIcon />}
            value={1}
            component={Link}
            to="/video"
            onClick={props.joinSession}
          />
          <BottomNavigationAction
            label="울음기록"
            icon={<VolumeUpIcon />}
            value={2}
            component={Link}
            to="/cry"
          />
          <BottomNavigationAction
            label="마이페이지"
            icon={<PersonIcon />}
            value={3}
            component={Link}
            to="/mypage"
          />
        </BottomNavigation>
      </Box>
    </div>
  );
}

export default BottomBar;
