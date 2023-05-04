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

# mqtt client 연결 확인
client = mqtt.Client()
client.on_connect = on_connect # Assign on_connect function to callback

# Set folder path and file paths
folder_path = f"/home/noinjung/sensor/ap-northeast-2/" 
config_file_path = folder_path + "aws_config.txt" # AWS IoT의 엔드포인트와 리전 정보가 담긴 파일
rootCA_file_path = folder_path + "rootCA.pem" # CA 인증서
certificate_file_path = folder_path + "certificate.pem.crt" # 인증서
private_key_file_path = folder_path + "private.pem.key" # 비밀키

# SSL/TLS 설정
client.tls_set(ca_certs=rootCA_file_path, certfile=certificate_file_path, keyfile=private_key_file_path, tls_version=ssl.PROTOCOL_SSLv23) # Set the TLS configuration
client.tls_insecure_set(False) # Disable certificate validation

# Read AWS endpoint and region from file
with open(config_file_path, "r") as f:
    aws_endpoint = f.readline().strip() # 한 줄 가져와서 양쪽 공백 제거

client.connect(aws_endpoint, 8883, 60) # Connect to AWS IoT, port 8883, keepalive: 60

class TemperatureDataError(Exception):
    pass


def publishData(txt):
    # DHT11 센서에서 온도와 습도를 읽어서 AWS IoT로 publish
    while (True):
        try:
            print(txt)
            temperature_c = dhtDevice.temperature # 섭씨 온도
            humidity = dhtDevice.humidity # 습도

            if temperature_c is None:
                raise TemperatureDataError("Temperature data is missing") # 섭씨 온도가 None이면 예외 발생

            temperature_f = temperature_c * (9 / 5) + 32 # 화씨 온도
            # print("Temp: {:.1f} F / {:.1f} C Humidity: {}% ".format(temperature_f, temperature_c, humidity))

            # 반올림하여 소수점 두 자리까지 표현
            temperature_c = round(temperature_c, 2)
            temperature_f = round(temperature_f, 2)

            # AWS IoT로 publish
            client.publish("raspi/data", payload=json.dumps({"C": temperature_c, "F": temperature_f, "H": humidity}), qos=0, retain=False)
            # 1초 간격으로 센서 값을 읽음
            time.sleep(10)

        except KeyboardInterrupt: # Ctrl+C 입력 시 예외 발생
            pass
            print('KeyboardInterrupt')
            exit() # 프로그램 종료
        except RuntimeError as e:
            print("Reading from DHT failure: ", e.args)
            client.publish("raspi/data", payload=json.dumps({"C": None, "F": None, "H": None}), qos=0, retain=False)
            time.sleep(1)
        except TemperatureDataError as e:
            print(e)
            client.publish("raspi/data", payload=json.dumps({"C": None, "F": None, "H": None}), qos=0, retain=False)
            time.sleep(1)
        except OverflowError: # OverflowError 예외 처리 추가
            print("OverflowError occurred, skipping this iteration")
            time.sleep(1)

       
# 새로운 스레드를 생성하여 publishData 함수를 실행
_thread.start_new_thread(publishData,("Spin-up new Thread...",))
client.loop_forever()

