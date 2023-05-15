import React, { Component } from 'react';
import OpenViduVideoComponent from './OvVideo';
import './UserVideo.css';

export default class UserVideoComponent extends Component {
    // constructor(props) {
    //     super(props);

    //     this.handleVideoClicked = this.handleVideoClicked.bind(this);
    // }
    // handleVideoClicked(event) {
    //     // Triggered when clicking on the video
    //     if(this.props.mainVideoStream){
    //         this.props.mainVideoStream(this.props.streamManager);
    //     }
    // }


    getNicknameTag() {
        // Gets the nickName of the user
        return JSON.parse(this.props.streamManager.stream.connection.data).clientData;
    }

    render() {
        return (
            <div>
                {this.props.streamManager !== undefined ? (
                    <div className="streamcomponent">
                        {/* <OpenViduVideoComponent streamManager={this.props.streamManager} /> */}
                        <div><p>{this.getNicknameTag()}</p></div>
                    </div>
                ) : null}
            </div>
        );
    }
}
