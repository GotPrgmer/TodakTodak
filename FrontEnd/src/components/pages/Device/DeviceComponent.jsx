// // class형
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import React, { Component } from "react";
import UserVideoComponent from "./UserVideoComponent";
import * as tf from "@tensorflow/tfjs";
import * as tmImage from "@teachablemachine/image";
// import BottomBar from "../../organisms/BottomBar";
// import TopBar from "../../organisms/TopBar";

// const APPLICATION_SERVER_URL =
//   process.env.NODE_ENV === "production" ? "" : "https://demos.openvidu.io/";

const APPLICATION_SERVER_URL = "https://todaktodak.kr:8080/"; // spring 서버 주소
// const APPLICATION_SERVER_URL = "https://demos.openvidu.io/";
// ------------------------------------------------------------------------------------------------------

// 클래스형

class DeviceComponent extends Component {
  constructor(props) {
    super(props);

    this.babyId = props.babyId[0].toString();
    this.jwtToken = props.jwtToken;
    this.deviceData = props.deviceData;
    // console.log(this.babyId);

    // These properties are in the state's component in order to re-render the HTML whenever their values change
    this.state = {
      // mySessionId: "todak000001",
      mySessionId: this.deviceData.serial_number, // SessionId는 기기에서 고정한다.
      myUserName: "todak" + this.babyId, // UserName은 기기에서 고정한다.
      session: undefined,
      mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
      publisher: undefined,
      subscribers: [],
      tokenList: [],
      model: undefined,
      webcam: undefined,
      maxPredictions: undefined,
      url: "https://teachablemachine.withgoogle.com/models/_6gIxAahL/",
      modelURL: undefined,
      metadataURL: undefined,
    };
    console.log(this.state.myUserName);

    this.joinSession = this.joinSession.bind(this); // 세션에 참여
    this.leaveSession = this.leaveSession.bind(this); // 세션 나가기
    this.init = this.init.bind(this);
    this.loop = this.loop.bind(this);
    this.predict = this.predict.bind(this);
    // 카메라 전후 변경(필요없음)
    // this.switchCamera = this.switchCamera.bind(this);
    // SessionId 변경(필요없음)
    // this.handleChangeSessionId = this.handleChangeSessionId.bind(this);
    // UserName 변경(필요없음)
    // this.handleChangeUserName = this.handleChangeUserName.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this); // 메인 비디오 스트림
    this.onbeforeunload = this.onbeforeunload.bind(this);
  }

  componentDidMount() {
    // 컴포넌트가 마운트 되었을 때
    window.addEventListener("beforeunload", this.onbeforeunload);
  }

  componentWillUnmount() {
    window.removeEventListener("beforeunload", this.onbeforeunload);
  }

  // leaveSession 함수 - 2
  onbeforeunload(event) {
    this.leaveSession(); // 세션 나가기
  }

  // SessionId 변경
  // handleChangeSessionId(e) {
  //   this.setState({
  //     mySessionId: e.target.value,
  //   });
  // }

  // UserName 변경
  // handleChangeUserName(e) {
  //   this.setState({
  //     myUserName: e.target.value,
  //   });
  // }

  handleMainVideoStream(stream) {
    // 메인 비디오 스트림
    if (this.state.mainStreamManager !== stream) {
      this.setState({
        mainStreamManager: stream,
      });
    }
  }

  deleteSubscriber(streamManager) {
    // 세션에 참여한 사람들의 스트림을 subscribers에서 삭제
    let subscribers = this.state.subscribers;
    let index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      this.setState({
        subscribers: subscribers,
      });
    }
  }

  // Load the image model and setup the webcam
  async init() {
    const modelURL = this.state.url + "model.json";
    const metadataURL = this.state.url + "metadata.json";
    this.state.model = modelURL;
    this.state.metadataURL = metadataURL;
    // load the model and metadata
    // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
    // or files from your local hard drive
    // Note: the pose library adds "tmImage" object to your window (window.tmImage)
    this.state.model = await tmImage.load(modelURL, metadataURL);
    // console.log("model")
    // console.log(this.state.model)
    this.state.maxPredictions = this.state.model.getTotalClasses();
    // console.log("prediction")
    // console.log("prediction" + this.state.maxPredictions)

    // Convenience function to setup a webcam
    const flip = true; // whether to flip the webcam
    const size = 200;
    this.state.webcam = new tmImage.Webcam(size, size, flip); // width, height, flip
    await this.state.webcam.setup(); // request access to the webcam
    await this.state.webcam.play();
    window.requestAnimationFrame(this.loop);

    // append elements to the DOM
    // document.getElementById("webcam-container").appendChild(webcam.canvas);
    // labelContainer = document.getElementById("label-container");
    // for (let i = 0; i < maxPredictions; i++) { // and class labels
    //     labelContainer.appendChild(document.createElement("div"));
    // }
  }
  async loop() {
    this.state.webcam.update(); // update the webcam frame
    await this.predict();
    console.log("i'm loop");
    window.requestAnimationFrame(this.loop);
  }
  // run the webcam image through the image model
  async predict() {
    // predict can take in an image, video or canvas html element
    const prediction = await this.state.model.predict(this.state.webcam.canvas);
    for (let i = 0; i < this.state.maxPredictions; i++) {
      const classPrediction =
        prediction[i].className + ": " + prediction[i].probability.toFixed(2);
      // labelContainer.childNodes[i].innerHTML = classPrediction;
      console.log(classPrediction);
    }
  }
  // joinSession
  joinSession() {
    // 세션에 참여
    // --- 1) Get an OpenVidu object ---

    this.OV = new OpenVidu();

    // --- 2) Init a session ---

    this.setState(
      {
        session: this.OV.initSession(), // Initialize a session
      },
      () => {
        var mySession = this.state.session; // 세션 생성

        // --- 3) Specify the actions when events take place in the session ---

        // On every new Stream received...
        mySession.on("streamCreated", (event) => {
          // Subscribe to the Stream to receive it. Second parameter is undefined
          // so OpenVidu doesn't create an HTML video by its own
          var subscriber = mySession.subscribe(event.stream, undefined); // 세션에 참여한 사람들의 스트림을 받아옴
          var subscribers = this.state.subscribers; // 세션에 참여한 사람들
          subscribers.push(subscriber); // 세션에 참여한 사람들의 스트림을 subscribers에 저장
          // console.log("subscribers", subscribers);

          // Update the state with the new subscribers
          this.setState({
            subscribers: subscribers, // 세션에 참여한 사람들의 스트림을 subscribers에 저장
          });
        });

        // tmImage
        console.log("init");
        this.init();

        // On every Stream destroyed...
        mySession.on("streamDestroyed", (event) => {
          // 세션에 참여한 사람이 나갔을 때
          // Remove the stream from 'subscribers' array
          this.deleteSubscriber(event.stream.streamManager); // 세션에 참여한 사람들의 스트림을 subscribers에서 삭제
        });

        // On every asynchronous exception...
        mySession.on("exception", (exception) => {
          // 세션의 예외처리
          console.warn(exception); // 예외처리
        });

        // --- 4) Connect to the session with a valid user token ---

        // Get a token from the OpenVidu deployment
        this.getToken().then((token) => {
          let tokenList = this.state.tokenList;
          tokenList.push(token);
          // console.log(tokenList);
          // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
          // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
          mySession
            .connect(token, { clientData: this.state.myUserName }) // 세션에 참여한 사람을 세션에 연결
            .then(async () => {
              // then은 token을 받아오고 세션에 연결이 되면 실행
              // --- 5) Get your own camera stream ---

              // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
              // element: we will manage it on our own) and with the desired properties
              let publisher = await this.OV.initPublisherAsync(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              // --- 6) Publish your stream ---

              mySession.publish(publisher); // 세션에 참여한 사람의 스트림을 세션에 발행

              // Obtain the current video device in use
              var devices = await this.OV.getDevices(); // 현재 사용중인 비디오 장치를 가져옴
              var videoDevices = devices.filter(
                // 현재 사용중인 비디오 장치를 필터링
                (device) => device.kind === "videoinput" // 비디오 장치만 필터링
              );
              var currentVideoDeviceId = publisher.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .getSettings().deviceId;
              var currentVideoDevice = videoDevices.find(
                (device) => device.deviceId === currentVideoDeviceId
              );

              // Set the main video in the page to display our webcam and store our Publisher
              this.setState({
                currentVideoDevice: currentVideoDevice,
                mainStreamManager: publisher,
                publisher: publisher,
              });
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message
              );
            });
        });
      }
    );
  }

  // leaveSession 함수 - 1
  // 방을 나가는 함수
  // todaktodak 서비스에서는 필요없음 -> 기기에서는 필요없음
  leaveSession() {
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---

    const mySession = this.state.session;

    if (mySession) {
      mySession.disconnect(); // 세션을 끊음
    }

    // Empty all properties...
    this.OV = null;
    this.setState({
      session: undefined,
      subscribers: [],
      // mySessionId: "SessionA",
      mySessionId: this.deviceData.serial_number,
      // myUserName: "Participant" + Math.floor(Math.random() * 100),
      myUserName: "todak" + this.babyId,
      mainStreamManager: undefined,
      publisher: undefined,
    });
  }

  render() {
    const mySessionId = this.state.mySessionId;
    // const myUserName = this.state.myUserName;

    return (
      <>
        {/* <TopBar /> */}
        <div className="container">
          {this.state.session === undefined ? (
            <div id="join">
              {/* <div id="img-div">
                <img
                  src="resources/images/openvidu_grey_bg_transp_cropped.png"
                  alt="OpenVidu logo"
                />
              </div> */}
              <div id="join-dialog" className="jumbotron vertical-center">
                {/* <h1> Join a video session </h1> */}
                <form className="form-group" onSubmit={this.joinSession}>
                  {/* userName 변경 form */}
                  {/* <p>
                    <label>Participant: </label>
                    <input
                      className="form-control"
                      type="text"
                      id="userName"
                      value={myUserName}
                      onChange={this.handleChangeUserName}
                      required
                    />
                  </p> */}
                  {/* SessionId 변경 form */}
                  {/* <p>
                    <label> Session: </label>
                    <input
                      className="form-control"
                      type="text"
                      id="sessionId"
                      value={mySessionId}
                      onChange={this.handleChangeSessionId}
                      required
                    />
                  </p> */}
                  <p className="text-center">
                    <input
                      className="btn btn-lg btn-success"
                      name="commit"
                      type="submit"
                      value="JOIN"
                    />
                  </p>
                </form>
              </div>
            </div>
          ) : null}

          {this.state.session !== undefined ? (
            <div id="session">
              <div id="session-header">
                <h1 id="session-title">{mySessionId}</h1>
                <input
                  className="btn btn-large btn-danger"
                  type="button"
                  id="buttonLeaveSession"
                  onClick={this.leaveSession}
                  value="Leave session"
                />
                {/* <input
                  className="btn btn-large btn-success"
                  type="button"
                  id="buttonSwitchCamera"
                  onClick={this.switchCamera}
                  value="Switch Camera"
                /> */}
              </div>

              {/* {this.state.mainStreamManager !== undefined ? (
                <div id="main-video" className="col-md-6">
                  <UserVideoComponent
                    streamManager={this.state.mainStreamManager}
                  />
                </div>
              ) : null} */}
              <div id="video-container" className="col-md-6">
                {/* {this.state.publisher !== undefined ? (
                  <div
                    className="stream-container col-md-6 col-xs-6"
                    onClick={() =>
                      this.handleMainVideoStream(this.state.publisher)
                    }
                  >
                    <UserVideoComponent streamManager={this.state.publisher} />
                  </div>
                ) : null} */}
                {this.state.subscribers.map((sub, i) => (
                  <div
                    key={sub.id}
                    className="stream-container col-md-6 col-xs-6"
                    onClick={() => this.handleMainVideoStream(sub)}
                  >
                    {/* <span>{sub.id}</span> */}
                    <UserVideoComponent streamManager={sub} />
                  </div>
                ))}
              </div>
            </div>
          ) : null}
        </div>
        {/* <BottomBar joinSession={this.joinSession} /> */}
      </>
    );
  }

  /**
   * --------------------------------------------
   * GETTING A TOKEN FROM YOUR APPLICATION SERVER
   * --------------------------------------------
   * The methods below request the creation of a Session and a Token to
   * your application server. This keeps your OpenVidu deployment secure.
   *
   * In this sample code, there is no user control at all. Anybody could
   * access your application server endpoints! In a real production
   * environment, your application server must identify the user to allow
   * access to the endpoints.
   *
   * Visit https://docs.openvidu.io/en/stable/application-server to learn
   * more about the integration of OpenVidu in your application server.
   */

  async getToken() {
    const sessionId = await this.createSession(this.state.mySessionId);
    return await this.createToken(sessionId);
  }

  // Session 생성
  async createSession(sessionId) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/sessions",
      { customSessionId: sessionId },
      {
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
        },
      }
    );
    return response.data; // The sessionId
  }

  // Session 입장에 필요한 Token
  async createToken(sessionId) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/sessions/" + sessionId + "/connections",
      {},
      {
        headers: { "Content-Type": "application/json;charset=UTF-8" },
      }
    );
    return response.data; // The token
  }
}

export default DeviceComponent;