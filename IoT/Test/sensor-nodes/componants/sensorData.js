import React from 'react';

import { Amplify, Auth }  from 'aws-amplify';
import awsconfig from './../aws-exports';

import { AWSIoTProvider } from '@aws-amplify/pubsub/lib/Providers';

Amplify.configure(awsconfig);

const AWS_CONFIGS = {
    identityPoolId: awsconfig.aws_cognito_identity_pool_id,

}

// Apply plugin with configuration
Amplify.addPluggable(new AWSIoTProvider({
    aws_pubsub_region: 'ap-northeast-2',
    aws_pubsub_endpoint: 'wss://aw9r86eiouxek-ats.iot.ap-northeast-2.amazonaws.com/mqtt',
}));

Auth.currentCredentials().then((info) => {
    const cognitoIdentityId = info.identityId;
    console.log(cognitoIdentityId);
});

// class Sensors extends React.Component{
//     constructor(props) {
//         super(props);
//         this.state = {
//           sensorMsg: '{"null": 0}'
//         };
//     }

//     componentDidMount(){
//         PubSub.subscribe('raspi/data').subscribe({
//           next: data => {
//             try{
//               this.setState({ sensorMsg: data.value });
//             }
//             catch (error){
//               console.log("Error, are you sending the correct data?");
//             }
//           },
//           error: error => console.error(error),
//           close: () => console.log('Done'),
//         });
//       }

//     render(){
//         const { sensorMsg } = this.state;
//         let sensorData = sensorMsg[this.props.name];

//         return(
//             <div className="Sensor">
//                 <Card style={{ width: '18rem' }}>
//                     <Card.Body>
//                         <Card.Title>{this.props.name}</Card.Title>
//                         <Card.Text> 
//                             { sensorData } { this.props.unit }
//                         </Card.Text>
//                     </Card.Body>
//                 </Card>
//                 <style jsx>{
//                 `
//                 .Sensor {
//                         box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
//                         transition: 0.3s;
//                     }
                    
//                     .Sensor:hover {
//                         box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
//                     }
//                     `
//                 }
//                 </style>
//             </div>
//         )
//     }
// }

export default Sensors;