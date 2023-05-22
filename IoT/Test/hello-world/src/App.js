import React from 'react';
import logo from './logo.svg';
import './App.css';
import mqtt from 'mqtt';

const options = {
  key: `-----BEGIN RSA PRIVATE KEY-----
  MIIEowIBAAKCAQEAvTamTIBtaQ9pf9SExCM3AogTygBz7bTs7mT8xlKJ2wrLLs//
  zDktQjawJaN8fWpZ02d8lCo7s6usMvsHi9ORCX/bWjOAWg6bmX9lmb/qDT0FvGX6
  O7R3I5UCJPOPruZvVi/seqktJoGM74Pvn7PWuDQgtmljSdM8RVKmShLXDPDRX5v9
  Jj3G+To6pl6fU4+9cE6pRLVoZ0wVMpWDlp+KIMKGFPa2ePU5KBCqBlWXjh3Qcs1P
  9RjEx768ewNKL+kBQU4p+jCwLnSOArte8ItVUfPITIeUKtVi6S79v5K24zi6j7Ph
  MJIt5cciZ15huWwx9l1Y4BrcR8WOh8pLXG719wIDAQABAoIBABLLkO1cfIHJJCOs
  ipupG9fGiKCyNtHP/REZOvrATC1T/ybhRnmU3GgNYqX6fW0/2ThkuT1GjLOmQ+60
  fOVo2Klm5/Un1IIdnVs8Cm2hYLB79UvnnVm87XC9Zn8hKo6nGTwoeoccsv12NPe9
  1Vm51YRquS0lftVghEYmlZoaEP6Z6ZiSM+mKp4BUwAOIaT8vA8+Uw8IeIHxn2+sW
  JSmEx3HFGeCWBMOUP27z6Pi8KYagN0N7b1+rS0B1BT58DyQH9qMEMeRu8sMbOd2E
  NstcjQbUyZZ2C9VZ65UbSQoLkYfgHcLnDpHhyMRtzfwuNEFk/PMO/ijMtwB6pntL
  7YUxP/ECgYEA3a0Ktyewq36lejHHBf9CakyADCpYa8DYRkKeGIIurpVfaeyIfhcb
  BW5p+B28D0xcjfzNMZ8sFsCDRRfs8xMiQBC6LCg3cN82tKOZPGQx7JVZVFE2ehEp
  YySOUaG2RI/3VxeDJE/oqWLd1EUFtVsRHpQXaGC2surhz2r63FDZpbUCgYEA2oLV
  /jLUepq9AZCnJACICS/lcpkmERPqylattxE39QFCZ/y0yxO3Aahf1fuWiIpbfj7X
  MBEUwRInWBrCH20IZlABMrnLqbfsQK+qf683VtRIpHVkrH0Dp7hnLLubByZIUunO
  DhWUGYqYrKuAX5+p3iMe9e65B+PxPHrbg3Mh+HsCgYBLImygWaaK6fkiap6/jJ4x
  M06vUOb5Vrq3TbsSKYWzDNPzLgWxXxl8hMkMHN/x+3rfcVEHQaEK8SNNFufsF48j
  UcgYUWVBL5b2MQEuM/Or8Y7+gixJwWlavjgyuPHzWSyHa2O6ug7s4qK3YNGnf39U
  8vLgIO+rwurYh7URWaH7hQKBgAiqXaAqFeES1+IDAY3c84ghvSCxUCAnQQ1rKJJ0
  /VobBa4oCGdxTbWkHsDWrF6X1g8IoPIIC2GovaSx0vb6FPYxwApp8di2MYlTLhjI
  MxKfco4LFcX9YzK4jmbovbon24BmQ0StuGTW6T0XxAyGwW7Mx8KLCA13Da8PVDs/
  Sp6XAoGBAMeLrcKOfiNNlj+kYn7UGaM7KITyzqO9JrBF6XxizpPmhutYDNsTRczx
  MIfe6ttVrdAQ2Fpom39fmjP6eByCEzBJuNrx5HEbhY2YUsI0zmPSfGrw40F/dgry
  7BRkPp2nDI6WbNw+iZeWS8Amh0XaUogSxAVLKt/NT5c1y0ly0KWz
  -----END RSA PRIVATE KEY-----`,
  cert: `-----BEGIN CERTIFICATE-----
  MIIDWjCCAkKgAwIBAgIVAKyvvnQM9zmNnAjxF+DbvdYzzG4aMA0GCSqGSIb3DQEB
  CwUAME0xSzBJBgNVBAsMQkFtYXpvbiBXZWIgU2VydmljZXMgTz1BbWF6b24uY29t
  IEluYy4gTD1TZWF0dGxlIFNUPVdhc2hpbmd0b24gQz1VUzAeFw0yMzA0MjEwMTM1
  MTZaFw00OTEyMzEyMzU5NTlaMB4xHDAaBgNVBAMME0FXUyBJb1QgQ2VydGlmaWNh
  dGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC9NqZMgG1pD2l/1ITE
  IzcCiBPKAHPttOzuZPzGUonbCssuz//MOS1CNrAlo3x9alnTZ3yUKjuzq6wy+weL
  05EJf9taM4BaDpuZf2WZv+oNPQW8Zfo7tHcjlQIk84+u5m9WL+x6qS0mgYzvg++f
  s9a4NCC2aWNJ0zxFUqZKEtcM8NFfm/0mPcb5OjqmXp9Tj71wTqlEtWhnTBUylYOW
  n4ogwoYU9rZ49TkoEKoGVZeOHdByzU/1GMTHvrx7A0ov6QFBTin6MLAudI4Cu17w
  i1VR88hMh5Qq1WLpLv2/krbjOLqPs+Ewki3lxyJnXmG5bDH2XVjgGtxHxY6Hyktc
  bvX3AgMBAAGjYDBeMB8GA1UdIwQYMBaAFMzbBrwtb5Vbv6iv6k8on0VYw0dIMB0G
  A1UdDgQWBBTQb7C9f9bmnIplE9USbYCC1ezS8jAMBgNVHRMBAf8EAjAAMA4GA1Ud
  DwEB/wQEAwIHgDANBgkqhkiG9w0BAQsFAAOCAQEAWIdfusi3+q1wBnWNjGzMJfWJ
  zEz/uEFLgAswpVv5eKUqvldmGWg2xpjPst6+jjfDJZuv/43auJ3Ohh+pHLKRh/m6
  UQQh6j8/+0TOwix0zTdekXXmIhkWkIQV06yWglakN2yWM4dl6uNajpwrUQhd/E3P
  xskwZjJsdEQCPfKLxL26TzmFbvdVDxVFW2a9KbwZY3rbHqeQgregsoPrQu7OElA/
  zgqd/BA+PkAlldMV4Hqx2mZCNq2ZKatxLgs7Mmh1/qDog6GvE/8Sle4lrYCBljbj
  GFqp1UM6GVfqzSXNfHKg1VOh4F3r8Q9NzAitXS1xMYbi4FR4WAwlFIaVABgxHA==
  -----END CERTIFICATE-----`,
  ca: `-----BEGIN CERTIFICATE-----
  MIIDQTCCAimgAwIBAgITBmyfz5m/jAo54vB4ikPmljZbyjANBgkqhkiG9w0BAQsF
  ADA5MQswCQYDVQQGEwJVUzEPMA0GA1UEChMGQW1hem9uMRkwFwYDVQQDExBBbWF6
  b24gUm9vdCBDQSAxMB4XDTE1MDUyNjAwMDAwMFoXDTM4MDExNzAwMDAwMFowOTEL
  MAkGA1UEBhMCVVMxDzANBgNVBAoTBkFtYXpvbjEZMBcGA1UEAxMQQW1hem9uIFJv
  b3QgQ0EgMTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALJ4gHHKeNXj
  ca9HgFB0fW7Y14h29Jlo91ghYPl0hAEvrAIthtOgQ3pOsqTQNroBvo3bSMgHFzZM
  9O6II8c+6zf1tRn4SWiw3te5djgdYZ6k/oI2peVKVuRF4fn9tBb6dNqcmzU5L/qw
  IFAGbHrQgLKm+a/sRxmPUDgH3KKHOVj4utWp+UhnMJbulHheb4mjUcAwhmahRWa6
  VOujw5H5SNz/0egwLX0tdHA114gk957EWW67c4cX8jJGKLhD+rcdqsq08p8kDi1L
  93FcXmn/6pUCyziKrlA4b9v7LWIbxcceVOF34GfID5yHI9Y/QCB/IIDEgEw+OyQm
  jgSubJrIqg0CAwEAAaNCMEAwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMC
  AYYwHQYDVR0OBBYEFIQYzIU07LwMlJQuCFmcx7IQTgoIMA0GCSqGSIb3DQEBCwUA
  A4IBAQCY8jdaQZChGsV2USggNiMOruYou6r4lK5IpDB/G/wkjUu0yKGX9rbxenDI
  U5PMCCjjmCXPI6T53iHTfIUJrU6adTrCC2qJeHZERxhlbI1Bjjt/msv0tadQ1wUs
  N+gDS63pYaACbvXy8MWy7Vu33PqUXHeeE6V/Uq2V8viTO96LXFvKWlJbYK8U90vv
  o/ufQJVtMVT8QtPHRh8jrdkPSHCa2XV4cdFyQzR1bldZwgJcJmApzyMZFo6IQ6XU
  5MsI+yMRQ+hDKXJioaldXgjUkK642M4UwtBV8ob2xJNDd2ZhwLnoQdeXeGADbkpy
  rqXRfboQnoZsG4q5WTP468SQvvG5
  -----END CERTIFICATE-----`,
  clientId: 'test-device',
  host: 'aw9r86eiouxek-ats.iot.ap-northeast-2.amazonaws.com',
  protocol: 'wss',
  port: 443,
};

const client = mqtt.connect(`wss://${options.host}:443/mqtt`, options);

const topic = 'raspi/data';

client.on('connect', () => {
  console.log('connect');

  client.subscribe(topic);

  const connectionMsg = { message: "successful connection" };
  client.publish(topic, JSON.stringify(connectionMsg));
});

client.on("message", (topic, payload) => {
  console.log("topic: ", topic, "\nmessage :", payload.toString());

  const msg = JSON.parse(payload.toString());
  const alarmMsg = { message: "alarm" };
  if (Number(msg.speed) > 90) {
    console.log("over speed");
    client.publish(topic, JSON.stringify(alarmMsg));
  }
});

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <button onClick={() => client.publish(topic, JSON.stringify({ message: "hello" }))}>Send</button>
        <button onClick={() => client.publish(topic, JSON.stringify({ message: "alarm" }))}>Alarm</button>
      </header>
    </div>
  );
}

export default App;
