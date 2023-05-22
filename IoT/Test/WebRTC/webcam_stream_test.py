import cv2
import imutils
import time
import ffmpeg
import subprocess
from fastapi import FastAPI
from fastapi.responses import StreamingResponse
from starlette.responses import HTMLResponse

app = FastAPI()


@app.get("/", response_class=HTMLResponse)
async def index():
    return """
    <html>
        <head>
            <title>Webcam Stream</title>
        </head>
        <body>
            <img src="/video_feed" />
        </body>
    </html>
    """

def gen():
    try:
        cap = cv2.VideoCapture(0)

        command = "ffmpeg -re -i - -f rtsp rtsp://todaktodak.kr:8888/test"
        # command = "ffmpeg -re -f v4l2 -i /dev/video0 -f alsa -i default -c:v libx264 -c:a aac -f rtsp rtsp://todaktodak.kr:554/test"

        pipe = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE)

        while True:
            ret, frame = cap.read()
            if not ret:
                break

            frame = imutils.resize(frame, width=400)
            (flag, encodedImage) = cv2.imencode(".jpg", frame)

            if not flag:
                continue

            # Send the frame to the RTSP server
            pipe.stdin.write(encodedImage.tobytes())
            
            # Yield the encoded frame to the StreamingResponse
            yield (b'--frame\r\n' b'Content-Type: image/jpeg\r\n\r\n' + bytearray(encodedImage) + b'\r\n')

    except Exception as e:
        print("Error :", e)

@app.get("/video_feed")
async def video_feed():
    return StreamingResponse(gen(), media_type="multipart/x-mixed-replace;boundary=frame")

if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
