import React, { useCallback, useState } from "react";
import Modal from "react-modal";
import { useRecoilState, useRecoilValue } from "recoil";
import {
  deviceDataAtom,
  serialNumberAtom,
} from "../../../../states/recoilHomeState";

function Enroll() {
  const customStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      height: "auto",
      width: "85%",
      marginRight: "-50%",
      marginTop: "10%",
      transform: "translate(-50%, -50%)",
    },
  };

  const deviceData = useRecoilValue(deviceDataAtom);

  const [IsOpen, setIsOpen] = useState(false);

  // Modal을 Open하는 함수
  const openModal = () => {
    setIsOpen(true);
  };

  // Modal을 Close하는 함수
  const closeModal = () => {
    setIsOpen(false);
  };
  const [serialNumberText, setSerialNumberText] = useState("");
  const [serialNumber, setSerialNumber] = useRecoilState(serialNumberAtom);

  const serialNumberHandler = () => {
    setSerialNumber(serialNumberText);
  };

  const resetSerialNumberHandler = useCallback(() => {
    setSerialNumberText("");
    setSerialNumber("");
    closeModal();
  }, []);

  return (
    <>
      <button onClick={openModal}>
        <p>기기 등록</p>
      </button>
      <Modal
        shouldCloseOnOverlayClick={false}
        ariaHideApp={false}
        isOpen={IsOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="Example Modal"
      >
        {serialNumber === deviceData.serial_number ? (
          <div>
            <input
              readOnly
              value={serialNumber}
              placeholder="기기번호를 입력해주세요."
              onChange={(e) => {
                setSerialNumberText(e.target.value);
                e.preventDefault();
              }}
              className="block w-full p-4 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 sm:text-md focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            />
          </div>
        ) : (
          <div>
            <input
              placeholder="기기번호를 입력해주세요."
              onChange={(e) => {
                setSerialNumberText(e.target.value);
              }}
              className="block w-full p-4 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 sm:text-md focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            />
          </div>
        )}

        <div className="mt-5 left-20px font-mun flex justify-center">
          <button
            type="submit"
            onClick={() => {
              serialNumberHandler();
              closeModal();
            }}
            className="rounded hover:rounded-lg bg-blue-300 mr-3 pl-4 pr-4 pt-1 pb-1 font-new"
          >
            확인
          </button>
          <button
            type="button"
            onClick={resetSerialNumberHandler}
            className="rounded hover:rounded-lg bg-red-300 mr-3 pl-4 pr-4 pt-1 pb-1 font-new"
          >
            초기화
          </button>
        </div>
      </Modal>
    </>
  );
}

export default Enroll;
