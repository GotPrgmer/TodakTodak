import React, { useState, useEffect } from 'react';
import { Amplify, PubSub } from 'aws-amplify';
import { AWSIoTProvider } from '@aws-amplify/pubsub';

Amplify.configure({
  Auth: {
    identityPoolId: '',
    region: 'ap-northeast-2',
  },
});

const SensorDataPage = () => {
  const [connectionStatus, setConnectionStatus] = useState('origin');
  const [receivedMessage, setReceivedMessage] = useState('{"C": "disconnect", "H":"disconnect"}');

  useEffect(() => {
    connectToMQTT();
  }, []);
  

  const connectToMQTT = async () => {
    const topic = 'raspi/data';

    PubSub.addPluggable(
      new AWSIoTProvider({
        // aws_pubsub_region: 'ap-northeast-2',
        aws_pubsub_endpoint: 'aw9r86eiouxek-ats.iot.ap-northeast-2.amazonaws.com',
      })
    );

    try {
      PubSub.subscribe(topic).subscribe({
        next: data => {
          console.log(`Received message: ${JSON.stringify(data.value)}`);
          setReceivedMessage(JSON.stringify(data.value));
        },
        error: error => console.error(error),
        close: () => {
          setConnectionStatus('Disconnected');
          console.log('Disconnected from MQTT');
        },
      });

      setConnectionStatus('Connected');
      console.log('Connected to MQTT');
    } catch (error) {
      console.error(`Error subscribing to ${topic}: ${error}`);
    }
  }

  return (
    <div>
      <div>{connectionStatus}</div>
      <div>{receivedMessage}</div>
    </div>
  );
}

export default SensorDataPage;
      // <div style={{ display: 'flex', justifyContent: 'space-evenly', alignItems: 'center' }}>
      //     {/* <img src={temperatureImage} alt="Temperature" /> */}
      //       {JSON.parse(receivedMessage)['C']}°C
      //     {/* <img src={humidityImage} alt="Humidity" /> */}
      //       {JSON.parse(receivedMessage)['H']}%
      // </div>