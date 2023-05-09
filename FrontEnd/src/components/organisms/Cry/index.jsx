import React from "react";
import babyface from "../../../assets/babyface.png";

function Crylist(props) {
    const logs = props.logs;
    // console.log(logs)
    // console.log(props.isClicked)
    
    let logItem;

    if (!props.isClicked) {
        logItem = logs.map((log, index) => 
            <div className={`${index <= 1 ? "" : "hidden"}`} key={log.toString()}>
                <div className="flex justify-between mt-3">
                    <img src={babyface} alt="" />
                    <p>{log[0]} 분</p>
                    <p>{log[1]}</p>
                    <p>{log[2]}</p>
                </div>
            </div>
        )
    } else {
        logItem = logs.map((log, index) => 
            <div key={index}>
                <div className="flex justify-between mt-3">
                    <img src={babyface} alt="" />
                    <p>{log[0]} 분</p>
                    <p>{log[1]}</p>
                    <p>{log[2]}</p>
                </div>
            </div>
        )
    }
        
    
    return (
        <div>
            <ul>{ logItem }</ul> 
        </div>
      );
}

export default Crylist;