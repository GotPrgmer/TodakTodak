// // class형
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import React, { Component } from "react";
import UserVideoComponent from "./UserVideoComponent";
import TopBar from "../../organisms/TopBar";
import BottomBar from "../../organisms/BottomBar";
import baby from "../../../assets/Logo.png";
// const APPLICATION_SERVER_URL =
//   process.env.NODE_ENV === "production" ? "" : "https://demos.openvidu.io/";
// console.log(process.env.NODE_ENV);

const APPLICATION_SERVER_URL = "https://todaktodak.kr:8080/";
// const APPLICATION_SERVER_URL = "https://demos.openvidu.io/";
// ------------------------------------------------------------------------------------------------------

// 클래스형
class Video extends Component {
  constructor(props) {
    super(props);

    this.babyId = props.babyId[0].toString();
    this.jwtToken = props.jwtToken;
    this.deviceData = props.deviceData;
    console.log("deviceData", this.deviceData);

    // These properties are in the state's component in order to re-render the HTML whenever their values change
    this.state = {
      // SessionId는 Camera Serial Number(로그인 후 시도)
      mySessionId: "todaktodak1013",
      // mySessionId: this.deviceData.session_id,
      // UserName은 로그인 한 후 생성되는 pk 번호
      // myUserName: "Participant" + Math.floor(Math.random() * 100),
      myUserName: this.babyId,
      session: undefined,
      mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
      publisher: undefined,
      subscribers: [],
      tokenList: [],
      babyPK: undefined,
    };
    // console.log(this.state["mySessionId"]);

    this.code = new URL(document.location.toString()).searchParams.get("code");
    this.joinSession = this.joinSession.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    // 카메라 전후 변경(필요없음)
    // this.switchCamera = this.switchCamera.bind(this);
    // SessionId 변경(필요없음)
    // this.handleChangeSessionId = this.handleChangeSessionId.bind(this);
    // UserName 변경(필요없음)
    // this.handleChangeUserName = this.handleChangeUserName.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
    this.onbeforeunload = this.onbeforeunload.bind(this);
  }

  componentDidMount() {
    window.addEventListener("beforeunload", this.onbeforeunload);
  }

  componentWillUnmount() {
    window.removeEventListener("beforeunload", this.onbeforeunload);
  }

  // leaveSession 함수 - 2
  onbeforeunload(event) {
    this.leaveSession();
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

  handleBabyInfo(code) {
    axios
      .get(
        `https://todaktodak.kr:8080/api/login/oauth2/code/kakao?code=${code}`
      )
      .then((response) => {
        let babyPk = response.data.babyId;
        console.log(babyPk);
      });
  }

  handleMainVideoStream(stream) {
    if (this.state.mainStreamManager !== stream) {
      this.setState({
        mainStreamManager: stream,
      });
    }
  }

  deleteSubscriber(streamManager) {
    let subscribers = this.state.subscribers;
    let index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      this.setState({
        subscribers: subscribers,
      });
    }
  }

  // joinSession
  joinSession() {
    // --- 1) Get an OpenVidu object ---

    this.OV = new OpenVidu();

    // --- 2) Init a session ---

    this.setState(
      {
        session: this.OV.initSession(),
      },
      () => {
        var mySession = this.state.session;

        // --- 3) Specify the actions when events take place in the session ---

        // On every new Stream received...
        mySession.on("streamCreated", (event) => {
          // Subscribe to the Stream to receive it. Second parameter is undefined
          // so OpenVidu doesn't create an HTML video by its own
          var subscriber = mySession.subscribe(event.stream, undefined);
          var subscribers = this.state.subscribers;
          subscribers.push(subscriber);
          // console.log("subscribers", subscribers);

          // Update the state with the new subscribers
          this.setState({
            subscribers: subscribers,
          });
        });

        // On every Stream destroyed...
        mySession.on("streamDestroyed", (event) => {
          // Remove the stream from 'subscribers' array
          this.deleteSubscriber(event.stream.streamManager);
        });

        // On every asynchronous exception...
        mySession.on("exception", (exception) => {
          console.warn(exception);
        });

        // --- 4) Connect to the session with a valid user token ---

        // Get a token from the OpenVidu deployment
        this.getToken().then((token) => {
          console.log("오픈비두 연결시도");
          console.log("connectionToken", token["connection_token"]);
          // let tokenList = this.state.tokenList;
          // tokenList.push(token);
          // console.log(tokenList);
          // console.log(tokenList);
          // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
          // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
          mySession
            .connect(token["connection_token"], {
              clientData: this.state.myUserName,
            })
            .then(async () => {
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
                mirror: true, // Whether to mirror your local video or not
              });

              // --- 6) Publish your stream ---

              mySession.publish(publisher);

              // Obtain the current video device in use
              var devices = await this.OV.getDevices();
              var videoDevices = devices.filter(
                (device) => device.kind === "videoinput"
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
              console.log("There was an error connecting to the session:");
              console.log(error);
            });
        });
      }
    );
  }

  // leaveSession 함수 - 1
  // 방을 나가는 함수
  // todaktodak 서비스에서는 필요없음
  leaveSession() {
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---

    const mySession = this.state.session;

    if (mySession) {
      mySession.disconnect();
    }

    // Empty all properties...
    this.OV = null;
    this.setState({
      session: undefined,
      subscribers: [],
      mySessionId: "todaktodak1013",
      // mySessionId: this.deviceData["session_id"],
      // myUserName: "Participant" + Math.floor(Math.random() * 100),
      myUserName: this.babyId,
      mainStreamManager: undefined,
      publisher: undefined,
    });
  }

  // 카메라 전후 변경 기능
  // todak Service에서 필요없음
  // async switchCamera() {
  //   try {
  //     const devices = await this.OV.getDevices();
  //     var videoDevices = devices.filter(
  //       (device) => device.kind === "videoinput"
  //     );

  //     if (videoDevices && videoDevices.length > 1) {
  //       var newVideoDevice = videoDevices.filter(
  //         (device) => device.deviceId !== this.state.currentVideoDevice.deviceId
  //       );

  //       if (newVideoDevice.length > 0) {
  //         // Creating a new publisher with specific videoSource
  //         // In mobile devices the default and first camera is the front one
  //         var newPublisher = this.OV.initPublisher(undefined, {
  //           videoSource: newVideoDevice[0].deviceId,
  //           publishAudio: true,
  //           publishVideo: true,
  //           mirror: true,
  //         });

  //         //newPublisher.once("accessAllowed", () => {
  //         await this.state.session.unpublish(this.state.mainStreamManager);

  //         await this.state.session.publish(newPublisher);
  //         this.setState({
  //           currentVideoDevice: newVideoDevice[0],
  //           mainStreamManager: newPublisher,
  //           publisher: newPublisher,
  //         });
  //       }
  //     }
  //   } catch (e) {
  //     console.error(e);
  //   }
  // }

  // parentFunction = (data) => {
  //   console.log(data);
  // };

  render() {
    const mySessionId = this.state.mySessionId;
    // const myUserName = this.state.myUserName;

    return (
      <>
        <TopBar />
        <div>
          {this.state.session === undefined ? (
            // ? this.joinSession()
            ////////////////////////////////////////////////////////////////////////////////////////
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
                  <div className="text-center">
                    <button className="font-new text-3xl">
                      아기 보러가기!
                      <img
                        src="data:image/jpeg;base64,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"
                        alt=""
                      />
                      클릭!
                    </button>
                    {/* <input
                      className="btn btn-lg"
                      name="commit"
                      type="image"
                      src="../../../assets/babyone.png"
                      alt="아기 보러가기"
                    /> */}
                  </div>
                  {/* <JoinButton /> */}
                </form>
              </div>
            </div>
          ) : ///////////////////////////////////////////////////////////////////////////////////////////////
          null}

          {this.state.session !== undefined ? (
            // <div id="session">
            <>
              <div id="session-header">
                <h1 id="session-title">{mySessionId}</h1>
              </div>
              <div id="video-container" className="vertical-center ">
                {/* w-[699px] h-[1050px] rounded-[30px] */}
                <UserVideoComponent streamManager={this.state.subscribers[0]} />
              </div>
            </>
          ) : //</div>
          null}
        </div>
        <BottomBar
          joinSession={this.joinSession}
          leaveSession={this.leaveSession}
          handleBabyInfo={this.handleBabyInfo}
        />
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
    // const sessionId = await this.createSession(this.state.mySessionId);
    const sessionId = this.state.mySessionId;
    return await this.createToken(sessionId);
  }

  // // Session 생성
  // async createSession(sessionId) {
  //   // // Before
  //   // const response = await axios.post(
  //   // After
  //   console.log(sessionId);
  //   const response = await axios.post(
  //     // After
  //     APPLICATION_SERVER_URL + "api/iot/sessions",
  //     // // Before
  //     // APPLICATION_SERVER_URL + "api/sessions",

  //     { customSessionId: sessionId },
  //     {
  //       headers: {
  //         // After
  //         Authorization: `Bearer ${this.jwtToken}`,
  //         "Content-Type": "application/json;charset=UTF-8",
  //       },
  //     }
  //   );
  //   console.log(response);
  //   return response.data; // The sessionId
  // }

  // Session 입장에 필요한 Token
  async createToken(sessionId) {
    // // Before
    // const response = await axios.post(
    // After
    const response = await axios.patch(
      // // Before
      // APPLICATION_SERVER_URL + "api/sessions/" + sessionId + "/connections",
      // After
      APPLICATION_SERVER_URL +
        "api/sessions/" +
        sessionId +
        "/connections/" +
        this.babyId,
      {},
      {
        headers: {
          // After
          Authorization: `Bearer ${this.jwtToken}`,
          "Content-Type": "application/json;charset=UTF-8",
        },
      }
    );
    // console.log(response.data);
    return response.data; // The token
  }
}

export default Video;
