import React, { useState, useEffect } from "react";
import axios from "axios";
import editIcon from '../../../assets/edit.png';
import { useNavigate } from "react-router-dom";

function BabyProfile() {
    const [data, setData] = useState([]);

    const navigate = useNavigate();
    const navigateToEdit = () => { navigate('/edit', { state: data }); };

    let d_day;

    useEffect(() => {
        async function loadData() {
            const response = await axios.get('http://todaktodak.kr:8080/baby/info/2')
            // console.log(response.data);
            setData(response.data);
        }
        loadData()
    },[]);

    return (
        <div className="h-96 text-center bg-cover rounded-b-[30px] bg-[url('https://i.pinimg.com/564x/8a/1a/34/8a1a34828d8650b0bbd96dcd71e2dafa.jpg')]">
            <div className='h-80 py-5'>
                <div className="flex justify-end mr-3">
                    <button><img className="w-7" src={editIcon} alt="" onClick={navigateToEdit}/></button>
                </div>
                <div className="flex justify-center">
                    <img className="rounded-full w-44 h-44" src={data.baby_image_url} alt='' />
                </div>
                <div>
                    <p className="text-xl font-bold mt-3">{ data.baby_nickname }</p>
                    <p className="font-semibold mt-1">{ data.baby_gender } | { data.baby_id }</p>
                </div>
                <div className="flex justify-around mt-3">
                    <div>
                        <p>{data.baby_birth_year}년 {data.baby_birth_month}월 {data.baby_birth_day}일</p>
                        <p className="mt-1">{data.baby_jodiak} / { data.baby_constellation }</p>
                    </div>
                    <div className="flex items-center">
                        <p className="text-xl">D+ 9,625{d_day}</p>
                    </div>
                </div>
            </div>
      </div>
  );
}

export default BabyProfile;