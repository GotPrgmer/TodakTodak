import logo from './logo.svg';
import React from 'react';
import './App.css';

import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col'

import Sensors from './components/sensorData';

import Amplify from 'aws-amplify';
import awsconfig from './aws-exports';
import { withAuthenticator } from 'aws-amplify-react'; // or 'aws-amplify-react-native';
import '@aws-amplify/ui/dist/style.css';

import { Auth } from 'aws-amplify';

Amplify.configure(awsconfig);
Auth.currentCredentials().then(creds => console.log(creds));

function App() {
  return (
    <div className="App">
      <Container className="p-4">
        <Row className="p-3 justify-content-md-center">
          <Col md="auto"> <Sensors name="Air Temp" unit="°F"/> </Col>
          <Col md="auto"> <Sensors name="Humidity" unit="%"/> </Col>
        </Row>
      </Container>
    </div>
  );
}

export default withAuthenticator(App, true);

