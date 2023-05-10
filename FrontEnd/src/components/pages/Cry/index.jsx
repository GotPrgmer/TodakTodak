import React, {useEffect, useState} from "react";
import TopBar from "../../organisms/TopBar";
import { Bar } from "react-chartjs-2";
import BottomBar from "../../organisms/BottomBar";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import Crylist from './../../organisms/Cry/index';
import ModalCalender from "../../organisms/Cry/Calendar";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  ChartDataLabels,
  Title,
  Tooltip,
  Legend
);

function Cry() {
  let today = new Date(); 
  const week = ['일', '월', '화', '수', '목', '금', '토'];
  const [date, setDate] = useState([today.getFullYear(), today.getMonth() + 1, today.getDate()])

  const dateSelect = (date) => {
    setDate(date);
  };

  useEffect(() => { console.log(date)}, [date]);

  // useEffect 에 넣어야 할것들!!
  let labels = ['5/4(목)', '5/5(금)', '5/6(토)', '5/7(일)', '5/8(월)',];
  let values = [10, 11, 12, 4, 5];
  let cryLogs = {
    '5/4(목)': [],
    '5/5(금)': [],
    '5/6(토)': [
      ['1', '15:59', '16:00'],
      ['1', '16:22', '16:23']
    ],
    '5/7(일)': [],
    '5/8(월)': [
      ['2', '12:32', '12:34'],
      ['3', '13:46', '13:49'],
      ['1', '15:59', '16:00'],
      ['1', '16:22', '16:23'],
    ],
  };
  
  const [clickedDate, setClickedDate] = useState(labels[4])
  // console.log(clickedDate)
  // console.log(cryLogs[clickedDate])
  
  const [isClicked, setIsClicked] = useState(false);

  // const [modalOpen, setModalOpen] = useState(false);
  // const showModal = () => {
  //   setModalOpen(true);
  // };
  // const closeModal = () => {
  //   setModalOpen(false)
  // }
  
  // const [pickDate, setPickDate] = useState(new Date());
  // const changeDate = (selectedDate) => {
  //   setPickDate(selectedDate)
  //   closeModal()
  // }

  const options = {
    indexAxis: "x",
    plugins: {
      tooltip: {
        enabled: false, // 그래프 호버시, 모달창 안나오게 하기
      },
      legend: {
        // 범례 스타일링
        display: false,
      },
      datalabels: {
        // display: true,
      },
    },

    scales: {
      // x축 y축에 대한 설정
      y: {
        axis: "y",
        border: {
          dash: [4, 4],
          display: false,
        },
        grid: {
          drawTicks: false,
          color: function (context) {
            // 시작 y열 안보이게 하기
            if (context.tick.value === 0) {
              return "rgba(0, 0, 0, 0)";
            }
            return "rgba(0, 0, 0, 0.1)";
          },
        },
        ticks: {
          display: false,
          max: 25
        },
        afterDataLimits: (scale) => {
          scale.max = scale.max * 1.05;
        },
      },
      x: {
        axis: "x",
        border: {
          display: false,
        },
        grid: {
          display: false,
        },
        // ticks: {
        //   callback: function (value) {
        //     return this.getLabelForValue(value).substring(0, 6)
        //   }
        // }
      },
    },
    onClick: function (evt, element) {
      if (element.length > 0) {
        setColorsHandler(element[0]["index"]);
        setClickedDate(labels[element[0]['index']])
        setIsClicked(false)
      }
    },
  };

  const [colors, setColors] = useState([
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(54, 162, 235)",
  ]);

  const setColorsHandler = (idx) => {
    const newColors = [
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
    ];
    newColors[idx] = "rgba(54, 162, 235)";
    setColors(newColors);
  };

  const data = {
    labels,
    datasets: [
      {
        axis: "x",
        type: "bar",
        backgroundColor: colors,
        borderRadius: 5,
        borderSkipped: false,
        data: values,
        datalabels: {
          anchor: "end",
          align: "top",
          color: "black",
          formatter: function (value) {
            return value.toFixed(0).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
          },
        },
      },
    ],
  };

  const btnClick = () => {
    setIsClicked(true)
  };

  return (
    <>
      <TopBar />
      <div>
        <ModalCalender dateSelect={dateSelect} />
      </div>
      
      <div className="w-screen h-fit p-5">
        <Bar options={options} data={data} height={320} />
        <div>
          <div className="flex justify-between mt-3">
            <p>울음기록</p>
            <p>{clickedDate}</p>
            <button onClick={btnClick}>더 보기</button>
          </div>
          <Crylist logs={cryLogs[clickedDate]} isClicked={isClicked} />
        </div>
      </div>
      
      <BottomBar />
    </>
  );
}

export default Cry;
