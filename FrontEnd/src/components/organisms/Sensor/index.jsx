import { useState, useEffect } from "react";
import { Amplify, PubSub } from "aws-amplify";
import { AWSIoTProvider } from "@aws-amplify/pubsub/lib/Providers";
import awsConfig from "./awsConfig.json";
import { Auth } from "aws-amplify";
import temp from "../../../assets/temperature.png";
import humid from "../../../assets/humidity.png";
import {
  deviceDataAtom,
  serialNumberAtom,
} from "../../../states/recoilHomeState";
import { useRecoilValue } from "recoil";

function init(awsConfig) {
  Amplify.configure({
    Auth: {
      userPoolId: awsConfig.cognitoUserPoolId,
      userPoolWebClientId: awsConfig.cognitoUserPoolClientId,
      identityPoolId: awsConfig.cognitoIdentityPoolId,
      region: awsConfig.region,
    },
  });

  Amplify.addPluggable(
    new AWSIoTProvider({
      aws_pubsub_region: awsConfig.region,
      aws_pubsub_endpoint: `wss://${awsConfig.mqttBrokerEndpoint}/mqtt`,
    })
  );
}

Auth.currentCredentials().then((creds) => console.log(creds));

function SensorDataPage() {
  const deviceData = useRecoilValue(deviceDataAtom);
  const serialNumber = useRecoilValue(serialNumberAtom);

  init(awsConfig);
  const [values, setValues] = useState({ C: 25, H: 34 });

  useEffect(() => {
    PubSub.subscribe(awsConfig.topic, {
      provider: "AWSIoTProvider",
    }).subscribe({
      next: (data) => {
        setValues(data.value);
      },
      error: (error) => console.log(error),
    });
  });

  return [
    deviceData.serial_number === serialNumber ? (
      <div className="w-full flex text-2xl h-[25vh]">
        <div className="relative w-1/2 h-[20vh] flex items-center justify-center">
          <img src={temp} alt="" />
          <div className="z-1 absolute mt-24">
            <p className="text-center font-bold">
              {values.C} <span className="text-xl">°C</span>
            </p>
            <p className="text-lg text-center">온도</p>
          </div>
        </div>
        <div className="relative w-1/2 h-[20vh] flex items-center justify-center">
          <img src={humid} alt="" />
          <div className="z-1 absolute mt-24">
            <p className="text-center font-bold">
              {values.H} <span className="text-xl">%</span>
            </p>
            <p className="text-lg text-center">습도</p>
          </div>
        </div>
      </div>
    ) : (
      <div className="w-full flex text-2xl h-[25vh]">
        <div className="relative w-1/2 h-[20vh] flex items-center justify-center">
          <img src={temp} alt="" />
          <div className="z-1 absolute mt-24">
            <p className="text-center font-bold">
              - <span className="text-xl">°C</span>
            </p>
            <p className="text-lg text-center">온도</p>
          </div>
        </div>
        <div className="relative w-1/2 h-[20vh] flex items-center justify-center">
          <img src={humid} alt="" />
          <div className="z-1 absolute mt-24">
            <p className="text-center font-bold">
              - <span className="text-xl">%</span>
            </p>
            <p className="text-lg text-center">습도</p>
          </div>
        </div>
      </div>
    ),
  ];
}

export default SensorDataPage;
