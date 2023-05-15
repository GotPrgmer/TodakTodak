import React, { useEffect, useState } from "react";
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
import Crylist from "./../../organisms/Cry/index";
import ModalCalender from "../../organisms/Cry/Calendar";

import { useRecoilValue } from "recoil";
import { jwtToken } from "../../../states/recoilHomeState";
import axios from "axios";
import { ExpirationPlugin } from 'workbox-expiration';

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
  const week = ["일", "월", "화", "수", "목", "금", "토"];
  const [date, setDate] = useState([
    today.getFullYear(),
    today.getMonth() + 1,
    today.getDate(),
  ]);

  const dateSelect = (date) => {
    setDate(date);
    setClickedDate(labels[4]);
    setColors([
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(255, 191, 135)",
    ]);
  };

  const jwt_token = useRecoilValue(jwtToken);
  // const jwt_token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0Iiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTY4Mzk0NDYzMCwiZXhwIjoxNjgzOTU1NDMwfQ.q96DBOpshNwgQIGO1NXG-HSXxCxneVQQrrrwC9zRet0'
  
  const [labels, setLabels] = useState([]);
  const [values, setValues] = useState([]);
  const [clickedDate, setClickedDate] = useState(`${date[0]}-${date[1] >= 10 ? date[1] : '0' + date[1]}-${date[2] >= 10 ? date[2] : '0' + date[2]}`);
  const [cryLogs, setCryLogs] = useState({});

  useEffect(() => {
    async function loadData() {
      const response = await axios.get(
        `https://todaktodak.kr:8080/api/cry/logging?babyId=2&year=${date[0]}&month=${date[1]}&day=${date[2]}`,
        {
          headers: {
            Authorization: `Bearer ${jwt_token}`,
          },
        }
      );
      let array = response.data['cry_log']
      setValues(array.map(function (element) {
        return element.cryCounts
      }));

      setLabels(array.map(function (element) {
        return element.date
      }));

      // console.log(array)

      let logs = {}
      for (let i in array) {
        // console.log(array[i])
        logs[array[i].date] = array[i].log
      }
      // console.log(logs)
      setCryLogs(logs)
    }
    loadData();
  }, [date]);

  useEffect(() => {
    setClickedDate(labels[4])
  }, [cryLogs]);
  
  // console.log(clickedDate)
  // console.log(cryLogs[clickedDate])

  const [isClicked, setIsClicked] = useState(false);

  // 더 보기 버튼 구현
  const [isMoreBtn, setIsMoreBtn] = useState(true);

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
    maintainAspectRatio: false,
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
          max: 25,
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
        setClickedDate(labels[element[0]["index"]]);
        setIsClicked(false);
        setIsMoreBtn(true);
      }
    },
  };

  const [colors, setColors] = useState([
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(240, 240, 240)",
    "rgba(255, 191, 135)",
  ]);

  const label = labels.map(function (element) {
    return element.substring(5,7) +'/' + element.substring(8,10) + ' (' + week[new Date(element).getDay()] + ')'
  })

  const setColorsHandler = (idx) => {
    const newColors = [
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
      "rgba(240, 240, 240)",
    ];
    newColors[idx] = "rgba(255, 191, 135)";
    setColors(newColors);
  };

  const data = {
    labels: label,
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
            return value
              .toFixed(0)
              .toString()
              .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
          },
        },
      },
    ],
  };

  const btnClick = () => {
    setIsClicked(true);
    setIsMoreBtn(false);
  };

  return (
    <>
      <TopBar />
      <div className={`${isMoreBtn ? "h-[85vh]" : "h-screen"}  px-5`}>
        <div className="w-full pt-5">
          <ModalCalender dateSelect={dateSelect} />
        </div>

        <div className="font-new">
          <div className="h-[45vh]">
            <Bar options={options} data={data}/>
          </div>
          <div className="mt-10 px-3">
            <div className="flex justify-between mb-3">
              <p className="text-xl font-semibold">울음기록 <span className="text-lg">{clickedDate.substring(5,7) +'/' + clickedDate.substring(8,10) + ' (' + week[new Date(clickedDate).getDay()] + ')'}</span></p>
              <button className={`${isMoreBtn
                && cryLogs[clickedDate]
                && cryLogs[clickedDate].length >= 3
                ? "" : "hidden"} text-green-400 font-semibold`} onClick={btnClick}>더 보기</button>
            </div>
            <Crylist logs={cryLogs[clickedDate]} isClicked={isClicked}/>
          </div>
        </div>
      </div>

      <BottomBar />
    </>
  );
}

export default Cry;
