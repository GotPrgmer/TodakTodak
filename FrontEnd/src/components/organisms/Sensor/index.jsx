import { useState, useEffect } from 'react';
import { Amplify, PubSub } from 'aws-amplify';
import { AWSIoTProvider } from '@aws-amplify/pubsub/lib/Providers';
import awsConfig from './awsConfig.json';
import { Auth } from 'aws-amplify';
import temp from "../../../assets/temperature.png";
import humid from "../../../assets/humidity.png";

function init(awsConfig) {
    Amplify.configure({
        Auth: {
            userPoolId: awsConfig.cognitoUserPoolId,
            userPoolWebClientId: awsConfig.cognitoUserPoolClientId,
            identityPoolId: awsConfig.cognitoIdentityPoolId,
            region: awsConfig.region,
        }
    });

    Amplify.addPluggable(new AWSIoTProvider({
        aws_pubsub_region: awsConfig.region,
        aws_pubsub_endpoint: `wss://${awsConfig.mqttBrokerEndpoint}/mqtt`,
    }));
}

Auth.currentCredentials().then(creds => console.log(creds));

function SensorDataPage() {
    init(awsConfig);
    const [values, setValues] = useState({ C: 25, H: 34 });

    useEffect(() => {
        PubSub.subscribe(awsConfig.topic, {
            provider: 'AWSIoTProvider'
        }).subscribe({
          next: (data) => {
              setValues(data.value);
            },
            error: (error) => console.log(error)
        })
    })

    return (
      <div className='w-full flex text-2xl mt-10'>
        <div className='w-1/2 grid place-items-center'>
          <img src={temp} alt="" />
          <div className='grid place-items-center z-1 absolute mt-24'>
            <p className='font-bold'>{ values.C } <span className='text-xl'>°C</span></p>
            <p className='text-lg'>Temperature</p>
          </div>
        </div>
        <div className='w-1/2 grid place-items-center'>
          <img src={humid} alt="" />
          <div className='grid place-items-center z-1 absolute mt-24'>
            <p className='font-bold'>{ values.H } <span className='text-xl'>%</span></p>
            <p className='text-lg'>Humidity</p>
          </div>
        </div>
      </div>
    );
  }

export default SensorDataPage;