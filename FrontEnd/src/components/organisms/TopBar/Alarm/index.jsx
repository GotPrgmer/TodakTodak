import React from "react";
import { useRecoilState, useRecoilValue } from "recoil";
import Modal from "react-modal";
import {
  alarmDataAtom,
  modalStateAtom,
  isReadAlarmAtom,
} from "../../../../states/recoilAlarmState";
import ReadAlarm from "../../../../assets/ReadAlarm.png";
import unReadAlarm from "../../../../assets/unReadAlarm.png";
import { bottomBarAtom } from "../../../../states/recoilHomeState";

function Alarm() {
  const customStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      height: "80%",
      width: "85%",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
    },
  };

  const [bottomBar, setBottomBar] = useRecoilState(bottomBarAtom);
  const [alarmData, setAlarmData] = useRecoilState(alarmDataAtom);
  const [isReadAlarm, setIsReadAlarm] = useRecoilState(isReadAlarmAtom);
  const IsOpen = useRecoilValue(modalStateAtom);

  let readCount = 0;
  for (let i = 0; i < alarmData.length; i++) {
    if (alarmData[i].isRead === true) {
      readCount++;
    }
  }

  if (readCount === alarmData.length) {
    setIsReadAlarm(true);
  }

  return (
    <>
      <div className="h-[8vh]">
        <Modal
          ariaHideApp={false}
          isOpen={IsOpen}
          style={customStyles}
          contentLabel="Example Modal"
        >
          {alarmData.map((alarm, idx) => {
            return (
              <div
                className="sc grid grid-cols-1 items-center justify-center text-center bg-white rounded-t-lg md:rounded-t-none md:rounded-tl-lg md:border-r dark:bg-gray-800 dark:border-gray-700 font-mun"
                key={idx}
              >
                {alarm.isRead === false ? (
                  <a
                    onClick={() => {
                      const updatedAlarm = { ...alarm, isRead: true };
                      const updatedAlarmData = alarmData.map((a) =>
                        a.id === alarm.id ? updatedAlarm : a
                      );
                      setAlarmData(updatedAlarmData);
                      setBottomBar(1);
                    }}
                    href="/video"
                    className="flex justify-center pt-1 pl-3 pr-3 mb-5 block max-w-l items-center bg-gray-200 border border-gray-200 rounded-lg shadow hover:bg-blue-300 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700"
                  >
                    <div className="flex justify-center">
                      <img src={unReadAlarm} alt="" className="w-10 h-10" />
                      <div>
                        <p className="mt-2 text-xs ml-5 mr-3 text-left ">
                          {alarm.body.split(",")[0]}
                        </p>
                      </div>
                    </div>
                    <div className="flex flex-col text-xs">
                      <div>{alarm.body.split(",")[1].split(" ")[0]}</div>
                      <div>{alarm.body.split(",")[1].split(" ")[1]}</div>
                    </div>
                  </a>
                ) : (
                  <a
                    onClick={() => {
                      setBottomBar(1);
                    }}
                    href="/video"
                    className="flex justify-center pt-1 pl-3 pr-3 mb-5 block max-w-l items-center bg-gray-200 border border-gray-200 rounded-lg shadow hover:bg-blue-300 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700"
                  >
                    <div className="flex justify-center">
                      <img src={ReadAlarm} alt="" className="w-10 h-10" />
                      <div>
                        <p className="mt-2 text-xs ml-5 mr-3 text-left ">
                          {alarm.body.split(",")[0]}
                        </p>
                      </div>
                    </div>
                    <div className="flex flex-col text-xs">
                      <div>{alarm.body.split(",")[1].split(" ")[0]}</div>
                      <div>{alarm.body.split(",")[1].split(" ")[1]}</div>
                    </div>
                  </a>
                )}
              </div>
            );

            // <div>{alarm["title"]}</div>;
          })}
        </Modal>
      </div>
    </>
  );
}

export default Alarm;
