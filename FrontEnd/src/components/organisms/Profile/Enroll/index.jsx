import React, { useState } from "react";
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
  console.log(deviceData);

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
  console.log(serialNumberText);
  const [serialNumber, setSerialNumber] = useRecoilState(serialNumberAtom);
  const serialNumberHandler = () => {
    setSerialNumber(serialNumberText);
    console.log(serialNumber);
  };

  return (
    <>
      <button onClick={openModal}>
        <p>기기 등록</p>
      </button>
      <Modal
        ariaHideApp={false}
        isOpen={IsOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel="Example Modal"
      >
        {serialNumber === deviceData.serial_number ? (
          <input
            value={serialNumber}
            placeholder="기기번호를 입력해주세요."
            onChange={(e) => {
              setSerialNumberText(e.target.value);
            }}
          />
        ) : (
          <input
            placeholder="기기번호를 입력해주세요."
            onChange={(e) => {
              setSerialNumberText(e.target.value);
            }}
          />
        )}

        <button
          type="submit"
          onClick={() => {
            closeModal();
            serialNumberHandler();
          }}
        >
          확인
        </button>
      </Modal>
    </>
  );
}

export default Enroll;
