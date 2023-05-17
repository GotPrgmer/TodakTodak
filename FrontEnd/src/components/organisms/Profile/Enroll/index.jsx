import React, { useState } from "react";
import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { deviceDataAtom } from "../../../../states/recoilHomeState";

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
  console.log(IsOpen);

  // Modal을 Open하는 함수
  const openModal = () => {
    setIsOpen(true);
  };

  // Modal을 Close하는 함수
  const closeModal = () => {
    setIsOpen(false);
  };

  const [serialNumber, setSerialNumber] = useState("");
  console.log(serialNumber);
  const [serialNumberText, setSerialNumberText] = useState([]);
  const serialNumberTextHandler = (event) => {
    setSerialNumberText(serialNumber);
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
        <input
          value={serialNumber}
          placeholder="기기번호를 입력해주세요."
          onChange={(e) => {
            setSerialNumber(e.target.value);
          }}
        ></input>
        <button
          type="submit"
          onClick={() => {
            closeModal();
            serialNumberTextHandler();
          }}
        >
          확인
        </button>
      </Modal>
    </>
  );
}

export default Enroll;
