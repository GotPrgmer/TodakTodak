import time
import paho.mqtt.client as mqtt
import ssl
import json
import _thread
import board
import adafruit_dht

#GPIO Mode (BOARD / BCM)
dhtDevice = adafruit_dht.DHT11(board.D18) # DHT11 센서를 GPIO 18번 핀에 연결

def on_connect(client, userdata, flags, rc): # The callback for when the client receives a CONNACK response from the server.
    print("Connected to AWS IoT: " + str(rc)) # rc is the error code returned when connecting to the broker

client = mqtt.Client()
client.on_connect = on_connect # Assign on_connect function to callback
client.tls_set(ca_certs='us-east-1/rootCA.pem', certfile='us-east-1/certificate.pem.crt', keyfile='us-east-1/private.pem.key', tls_version=ssl.PROTOCOL_SSLv23) # Set the TLS configuration
client.tls_insecure_set(True) # Disable certificate validation
client.connect("aw9r86eiouxek-ats.iot.us-east-1.amazonaws.com", 8883, 60) # Connect to AWS IoT

def publishData():
    while (True):
        try:
            temperature_c = dhtDevice.temperature
            temperature_f = temperature_c * (9 / 5) + 32
            humidity = dhtDevice.humidity
            print("Temp: {:.1f} F / {:.1f} C Humidity: {}% ".format(temperature_f, temperature_c, humidity))

            client.publish("raspi/data", payload=json.dumps({"섭씨": temperature_c, "화씨": temperature_f, "습도": humidity}), qos=0, retain=False)
            time.sleep(1)
            
        except KeyboardInterrupt:
            pass
            print('KeyboardInterrupt')
            exit()
        except RuntimeError as e:
            print("Reading from DHT failure: ", e.args)
       
_thread.start_new_thread(publishData,("Spin-up new Thread...",))
client.loop_forever()
