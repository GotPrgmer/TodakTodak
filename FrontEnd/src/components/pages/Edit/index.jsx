import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useForm } from "react-hook-form";

function Edit() {
    const navigate = useNavigate();
    const navigateToProfile = () => { navigate('/') };

    const { state } = useLocation();
    console.log(state)

    const { register, handleSubmit } = useForm();

    const { watch, } = useForm();
    const [imagePreview, setImagePreview] = useState("");
    const image = watch("image");
    useEffect(() => {
        if (image && image.length > 0) {
        const file = image[0];
        setImagePreview(URL.createObjectURL(file));
        }
    }, [image]);

  return (
    <>
          <div className="bg-cover bg-[url('https://i.pinimg.com/564x/8a/1a/34/8a1a34828d8650b0bbd96dcd71e2dafa.jpg')]">
              <div className="h-screen w-screen ">
                  <div className="flex">
                      <div className="w-screen mt-16">
                        <button onClick={navigateToProfile} className="ml-5">취소</button>    
                      </div>
                      <div  className="w-screen mt-16 text-right">
                        <button className="mr-5">완료</button>  
                      </div>
                  </div>
                  
                  <form onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}>
                      {/* <input
                        className="block"
                        id="nickname"
                        type="text"
                        placeholder={ state.baby_nickname }
                        {...register("nickname")}
                         
                    />
                      <input
                        className="block"
                        id="name"
                        type="text"
                        placeholder= { state.baby_id }
                        {...register("name")}    
                    /> */}
                      <input {...register("image")} id="picture" type="file"/>
                    <button type="submit">로그인</button>
                  </form>
              </div>
          </div>
    </>
  );
}

export default Edit;
