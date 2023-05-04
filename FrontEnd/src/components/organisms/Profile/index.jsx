import React, { useState, useEffect } from "react";
import axios from "axios";

import bummy from '../../../assets/bummy.png';

function BabyProfile() {
    const [data, setData] = useState([]);

    let d_day;

    useEffect(() => {
        async function loadData() {
            const response = await axios.get('http://todaktodak.kr:8080/baby/info/2')
            console.log(response.data);
            setData(response.data);
        }
        loadData()
},[]);
 

    return (
        <div className="h-96 text-center">
            <div className=''>
                <div className="flex justify-center">
                    <img className="rounded-full w-44 h-44" src={bummy} alt='' />
                </div>
                <div>
                    <p>{ data.baby_nickname }</p>
                    <p>{ data.baby_gender } { data.baby_id }</p>
                </div>
                <div className="flex justify-around">
                    <div>
                        <p>{data.baby_birth_year}년 {data.baby_birth_month}월 {data.baby_birth_day}일</p>
                        <p>{data.baby_jodiak} / { data.baby_constellation }</p>
                    </div>
                    <div>D+9,625{ d_day }</div>
                </div>
            </div>
      </div>
  );
}

export default BabyProfile;
