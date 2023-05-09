import React, { useState } from "react";
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import Modal from "react-modal";
import CalendarIcon from "../../../../assets/calendar.png";
import moment from "moment/moment";

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    height: "80%",
    width: "85%",
    marginRight: "-50%",
    marginTop: "10%",
    transform: "translate(-50%, -50%)",
  },
};

function ModalCalender(props) {
  
  const [IsOpen, setIsOpen] = useState(false);
  
  // Modal을 Open하는 함수
  const openModal = () => {
    setIsOpen(true);
  };
  
  // Modal을 Close하는 함수
  const closeModal = () => {
    setIsOpen(false);
  };
  
  const [value, onChange] = useState(new Date());
  const week = ['일', '월', '화', '수', '목', '금', '토'];
  const [pickDate, setPickDate] = useState(new Date());

  const submitDate = (date) => {
    props.dateSelect([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
  };

  return (
    <>
      <div>
        <div>
          <button className="flex" onClick={openModal}>
            <img src={CalendarIcon} alt="" />
            <p>{moment(pickDate).format("YYYY년 MM월 DD일") + '(' + week[moment(pickDate).day()] + ')'} </p>
          </button>

          <Modal
            ariaHideApp={false}
            isOpen={IsOpen}
            onRequestClose={closeModal}
            style={customStyles}
            contentLabel="Example Modal">   

            <div>
              <Calendar onChange={onChange} value={value} calendarType="US" />
            </div>
            <div className="mt-5 left-20px font-mun flex justify-center">
          <button
            onClick={() => {
                  closeModal();
                  setPickDate(value);
                  submitDate(value);
            }}
            className="rounded hover:rounded-lg bg-blue-300 mr-3 pl-4 pr-4 pt-1 pb-1"
          >
            확인
          </button>

          <button
            onClick={closeModal}
            className="rounded hover:rounded-lg bg-red-300 mr-3 pl-4 pr-4 pt-1 pb-1"
          >
            취소
          </button>
        </div>
        </Modal>
      </div>

    </div>
    </>
  );
}

export default ModalCalender;
