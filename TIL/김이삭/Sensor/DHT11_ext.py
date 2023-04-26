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

# Set folder path and file paths
folder_path = f"ap-northeast-2/"
config_file_path = folder_path + "aws_config.txt"
rootCA_file_path = folder_path + "rootCA.pem"
certificate_file_path = folder_path + "certificate.pem.crt"
private_key_file_path = folder_path + "private.pem.key"

client.tls_set(ca_certs=rootCA_file_path, certfile=certificate_file_path, keyfile=private_key_file_path, tls_version=ssl.PROTOCOL_SSLv23) # Set the TLS configuration
client.tls_insecure_set(True) # Disable certificate validation

# Read AWS endpoint and region from file
with open(config_file_path, "r") as f:
    aws_endpoint = f.readline().strip()
    aws_region = f.readline().strip()

client.connect(aws_endpoint, 8883, 60) # Connect to AWS IoT

class TemperatureDataError(Exception):
    pass


def publishData(txt):
    while (True):
        try:
            print(txt)
            temperature_c = dhtDevice.temperature
            humidity = dhtDevice.humidity

            if temperature_c is None:
                raise TemperatureDataError("Temperature data is missing")

            temperature_f = temperature_c * (9 / 5) + 32
            print("Temp: {:.1f} F / {:.1f} C Humidity: {}% ".format(temperature_f, temperature_c, humidity))

            # 반올림하여 소수점 두 자리까지 표현
            rounded_temperature_c = round(temperature_c, 2)
            rounded_temperature_f = round(temperature_f, 2)

            client.publish("raspi/data", payload=json.dumps({"C": rounded_temperature_c, "F": rounded_temperature_f, "H": humidity}), qos=0, retain=False)
            time.sleep(1)

        except KeyboardInterrupt:
            pass
            print('KeyboardInterrupt')
            exit()
        except RuntimeError as e:
            print("Reading from DHT failure: ", e.args)
            client.publish("raspi/data", payload=json.dumps({"C": None, "F": None, "H": None}), qos=0, retain=False)
        except TemperatureDataError as e:
            print(e)
            client.publish("raspi/data", payload=json.dumps({"C": None, "F": None, "H": None}), qos=0, retain=False)
            time.sleep(1)

       
_thread.start_new_thread(publishData,("Spin-up new Thread...",))
client.loop_forever()
