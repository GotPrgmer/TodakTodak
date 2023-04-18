const videpElement = document.querySelector('webcamVideo');

async function startWebcamStream(){
    try{
        const stream = await navigator.mediaDevices.getUserMedia({ video: ture, audio: false});
        videoElement.srcObject = stream;
    } catch (error) {
        console.error('WebRTC 오류: ', error);
    }

    startWebcamStream();
}