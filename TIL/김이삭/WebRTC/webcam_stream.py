import cv2 # OpenCV library import
from flask import Flask, render_template, Response # Flask 관련 모듈
import imutils # image 영상 처리를 위한 library
import time

app = Flask(__name__) # Flask App 객체 생성

@app.route('/') # Flask 루트 경로(/)를 정의
def index():
    return render_template('index.html') # index.html 템플릿을 렌더링하여 반환

def gen():
    try:
        cap = cv2.VideoCapture(0) # 시스템에 연결된 기본 웹캠을 사
        prev_frame_time = 0  # 이전 프레임 시간을 저장할 변수를 초기화합니다.
        frame_counter = 0
        start_time = time.time()
        fps = 0.0
        
        while True:
            ret, frame = cap.read() # 웹캠에서 프레임을 읽기
            if not ret: # ret이 0이거나 false라면 종료
                break
            
            
            # 현재 프레임 시간을 계산하고 FPS를 계산합니다.
            frame_counter += 1
            current_frame_time = time.time()
            elapsed_time = current_frame_time - start_time
            
            # start_time과 frame_counter를 다시 초기화하여 다음 1초 간격의 FPS를 계산할 수 있도록 합니다.
            if elapsed_time >= 1:
                fps = frame_counter / elapsed_time
                start_time = current_frame_time
                frame_counter = 0

            # 프레임에 FPS 텍스트를 추가합니다.
            cv2.putText(frame, f"FPS: {fps:.2f}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
            
            frame = imutils.resize(frame, width=400) # 프레임의 크기를 조절
            (flag, encodedImage) = cv2.imencode(".jpg", frame) # JPEG 이미지로 인코딩

            if not flag: # 인코딩이 안되면 다시 영상을 받는다. 성공한 경우에 이미지를 스트림에 추가
                continue
    

            # 인코딩된 이미지를 멀티파트 스트림에 추가하고, 멀티파트 스트림의 다음 부분을 생성
            yield (b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' + bytearray(encodedImage) + b'\r\n')
            
    except Exception as e:
        print("Error :", e)

# Flask 앱의 /video_feed 경로를 정의
@app.route('/video_feed')
def video_feed():
    return Response(gen(), mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)


