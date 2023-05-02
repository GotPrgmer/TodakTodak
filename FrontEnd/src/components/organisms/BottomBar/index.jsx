import React from "react";
import { Link } from "react-router-dom";
import { Box, BottomNavigation, BottomNavigationAction } from "@mui/material";
import ChildCareIcon from "@mui/icons-material/ChildCare";
import CameraIndoorIcon from "@mui/icons-material/CameraIndoor";
import VolumeUpIcon from "@mui/icons-material/VolumeUp";
import PersonIcon from "@mui/icons-material/Person";
import { useRecoilState } from "recoil";
import { bottomBarAtom } from "../../../states/recoilHomeState";

function BottomBar() {
  const [value, setValue] = useRecoilState(bottomBarAtom);
  // const [value, setValue] = useState(0);

  return (
    <div>
      <Box className=" fixed bottom-0">
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
            component={Link}
            to="/"
          />
          <BottomNavigationAction
            label="실시간영상"
            icon={<CameraIndoorIcon />}
            component={Link}
            to="/video"
          />
          <BottomNavigationAction
            label="울음기록"
            icon={<VolumeUpIcon />}
            component={Link}
            to="/cry"
          />
          <BottomNavigationAction
            label="마이페이지"
            icon={<PersonIcon />}
            component={Link}
            to="/mypage"
          />
        </BottomNavigation>
      </Box>
    </div>
  );
}

export default BottomBar;
