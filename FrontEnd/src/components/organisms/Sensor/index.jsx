import React from 'react';
import { useMqtt } from 'react-mqtt';

function MQTTComponent() {
  const { message, status } = useMqtt('aw9r86eiouxek-ats.iot.ap-northeast-2.amazonaws.com', {
    clientId: 'clientId-' + Math.random().toString(16).substring(2, 8),
    clean: true,
  });

  const handleMessage = (topic, message) => {
    console.log(`Received message: ${message.toString()} on topic ${topic}`);
  };

  if (status === 'connected') {
    message.subscribe('raspi/data');
    message.on('message', handleMessage);
  }

  return (
    <div>
      <h1>MQTT Example</h1>
    </div>
  );
}

export default MQTTComponent;