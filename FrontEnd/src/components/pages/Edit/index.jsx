import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useLocation } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { jwtToken } from "../../../states/recoilHomeState";

function Edit() {
  const navigate = useNavigate();
  const navigateToProfile = () => {
    navigate("/profile");
  };

  const { register, handleSubmit, watch } = useForm();

  const jwt_token = useRecoilValue(jwtToken);

  const onSubmit = (info) => {
    console.log(info);
    const formData = new FormData();

    const request = {
      babyNickname: info.nickname,
      babyGender: info.gender,
      babyBirthYear: Number(info.year),
      babyBirthMonth: Number(info.month),
      babyBirthDay: Number(info.day),
      babyName: info.name,
    };

    formData.append(
      "request",
      new Blob([JSON.stringify(request)], { type: "application/json" })
    );
    if (info.image && info.image.length > 0) {
      formData.append("babyImage", info.image[0]);
    }

    fetch(
      `https://todaktodak.kr:8080/api/baby/info/update/${data.state.baby_id}`,
      {
        method: "PATCH",
        body: formData,
        headers: {
          Authorization: `Bearer ${jwt_token}`,
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        navigate("/");
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };

  const date = new Date();

  const data = useLocation();
  console.log(data);

  const [imagePreview, setImagePreview] = useState(data.state.baby_image_url);
  const image = watch("image");
  useEffect(() => {
    if (image && image.length > 0) {
      const file = image[0];
      setImagePreview(URL.createObjectURL(file));
    }
  }, [image]);

  const gender =
    data.state.baby_gender === "unknown" ? "other" : data.state.baby_gender;
  const year =
    data.state.baby_birth_year === "" ? "" : data.state.baby_birth_year;
  const month =
    data.state.baby_birth_month === "" ? "" : data.state.baby_birth_month;
  const day = data.state.baby_birth_day === "" ? "" : data.state.baby_birth_day;

  return (
    <div className="h-screen w-full text-center font-new bg-cover bg-[url('https://i.pinimg.com/564x/8a/1a/34/8a1a34828d8650b0bbd96dcd71e2dafa.jpg')] flex items-center">
      <form
        className=""
        onSubmit={handleSubmit(onSubmit)}
        encType="multipart/form-data"
      >
        <div className="flex justify-center">
          <label htmlFor="picture">
            <input
              style={{ display: "none" }}
              {...register("image")}
              id="picture"
              type="file"
            />
            <img
              className="rounded-full w-44 h-44"
              src={imagePreview}
              alt=""
            ></img>
          </label>
        </div>

        <div className="flex justify-center mt-3">
          <input
            className="block bg-transparent text-center text-2xl"
            placeholder={data.state.baby_nickname}
            {...register("nickname", { required: true, maxLength: 10 })}
          />
        </div>

        <div className="flex justify-center mt-3">
          <select
            className="block bg-transparent text-center w-20"
            defaultValue={gender}
            {...register("gender", { required: true })}
          >
            <option value="other">성별</option>
            <option value="male">남자</option>
            <option value="female">여자</option>
          </select>

          <input
            className="block bg-transparent text-center w-20"
            placeholder="이름"
            {...register("name", { required: true, maxLength: 10 })}
          />
        </div>

        <div className="flex justify-center mt-3">
          <select
            className="bg-transparent text-center w-20"
            defaultValue={year}
            {...register("year", { required: true })}
          >
            <option value="">년</option>
            {Array.from({ length: 35 }, (_, i) => date.getFullYear() - i).map(
              (i) => (
                <option key={i} value={i}>
                  {i}
                </option>
              )
            )}
          </select>

          <select
            className="bg-transparent text-center w-20"
            defaultValue={month}
            {...register("month", { required: true })}
          >
            <option value="">월</option>
            {Array.from({ length: 12 }, (_, i) => 1 + i).map((i) => (
              <option key={i} value={i}>
                {i}
              </option>
            ))}
          </select>

          <select
            className="bg-transparent text-center w-20"
            defaultValue={day}
            {...register("day", { required: true })}
          >
            <option value="">일</option>
            {Array.from({ length: 31 }, (_, i) => 1 + i).map((i) => (
              <option key={i} value={i}>
                {i}
              </option>
            ))}
          </select>
        </div>

        <div className="w-screen mt-5">
          <button onClick={navigateToProfile}>취소</button>
          <input className="ml-20" type="submit" value="완료" />
        </div>
      </form>
    </div>
  );
}

export default Edit;
