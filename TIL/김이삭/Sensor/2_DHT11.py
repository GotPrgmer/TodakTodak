import time   # 대기시간 용 time 모듈
import board  # 데이터 송신용 board 모듈
import adafruit_dht  # DH11 센서 동작 모듈

dhtDevice = adafruit_dht.DHT11(board.D18) # DHT11 센서를 GPIO 18번 핀에 연결
while True:
    try:
        temperature_c = dhtDevice.temperature # 센서에서 온도(섭씨)를 읽어온다.
        temperature_f = temperature_c * (9 / 5) + 32 # 섭씨 온도를 화씨 온도로 변환
        humidity = dhtDevice.humidity # 센서에서 습도를 읽어온다.
        print("Temp: {:.1f} F / {:.1f} C    Humidity: {}% ".format(
                temperature_f, temperature_c, humidity)) # 온도와 습도를 출력
        time.sleep(2.0) # 2초 대기
        
    except KeyboardInterrupt: #   Ctrl+C를 누르면 종료
      pass
      print('Exit with ^C. Goodbye!')
      exit()
    except RuntimeError as e:
      # Reading doesn't always work! Just print error and we'll try again
      print("Reading from DHT failure: ", e.args)

    