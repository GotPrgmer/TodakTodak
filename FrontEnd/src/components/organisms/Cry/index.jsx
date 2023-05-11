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
                <div className="mt-3 flex justify-between">
                    <div className="flex">
                        <img src={babyface} alt="" />
                        <p className="ml-3 text-xl my-auto">{log[0]} 분</p>
                    </div>
                    <div className="flex my-auto">
                        <span>{log[1]} ~ {log[2]}</span>
                    </div>
                </div>
            </div>
        )
    } else {
        logItem = logs.map((log, index) => 
            <div key={index}>
                <div className="mt-3 flex justify-between">
                    <div className="flex">
                        <img src={babyface} alt="" />
                        <p className="ml-3 text-xl my-auto">{log[0]} 분</p>
                    </div>
                    <div className="flex my-auto">
                        <span>{log[1]} ~ {log[2]}</span>
                    </div>
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