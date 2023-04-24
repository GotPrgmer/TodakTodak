import 'dart:async';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:mqtt_client/mqtt_client.dart';
import 'package:mqtt_client/mqtt_server_client.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: SensorDataPage(),
    );
  }
}

class SensorDataPage extends StatefulWidget {
  @override
  _SensorDataPageState createState() => _SensorDataPageState();
}

class _SensorDataPageState extends State<SensorDataPage> {
  String _connectionStatus = 'Disconnected';
  String _receivedMessage = ''; // 받은 메시지를 저장
  late MqttServerClient client;

  @override
  void initState() {
    super.initState();
    _connectToMQTT();
  }

  Future<void> _connectToMQTT() async {
    String clientId = '';
    String awsEndpoint = 'aw9r86eiouxek-ats.iot.us-east-1.amazonaws.com'; // e.g., 'xxxxxxxxxxxxx-ats.iot.us-west-2.amazonaws.com'

    final rootCA = await rootBundle.load('assets/certs/RootCA.pem');
    final deviceCert = await rootBundle.load('assets/certs/DeviceCertificate.crt');
    final privateKey = await rootBundle.load('assets/certs/Private.key');

    SecurityContext securityContext = SecurityContext.defaultContext;
    securityContext.setTrustedCertificatesBytes(rootCA.buffer.asUint8List());
    securityContext.useCertificateChainBytes(deviceCert.buffer.asUint8List());
    securityContext.usePrivateKeyBytes(privateKey.buffer.asUint8List());

    client = MqttServerClient(awsEndpoint, clientId)
      ..port = 8883
      ..secure = true
      ..logging(on: false)
      ..securityContext = securityContext
      ..onConnected = () {
        setState(() {
          _connectionStatus = 'Connected';
        });
        print('Connected to MQTT');
        _subscribeToTopic(client); // 구독 추가
      }
      ..onDisconnected = () {
        setState(() {
          _connectionStatus = 'Disconnected';
        });
        print('Disconnected from MQTT');
      }
      ..pongCallback = () {
        print('Ping response received from MQTT');
      };

    try {
      await client.connect();
    } catch (e) {
      print('Exception: $e');
      client.disconnect();
    }
  }

  // 구독 함수 추가
  void _subscribeToTopic(MqttServerClient client) {
    const String topic = 'raspi/data';
    client.subscribe(topic, MqttQos.atLeastOnce);

    client.updates?.listen((List<MqttReceivedMessage<MqttMessage>> c) {
      final MqttPublishMessage recMess = c[0].payload as MqttPublishMessage;
      final String payload =
      MqttPublishPayload.bytesToStringAsString(recMess.payload.message);
      print('Connection state: ${client.connectionStatus?.state}');
      print('Subscription status: ${client.getSubscriptionsStatus(topic)}');

      setState(() { // 받은 메시지를 상태 변수에 저장
        _receivedMessage = payload;
      });
      print('Received message: $payload');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('MQTT Connection Status')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'Connection Status:',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 10),
            Text(
              _connectionStatus,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 20),
            Text(
              'Received Message:',
              style: TextStyle(fontSize: 24),
            ),
            SizedBox(height: 10),
            Text(
              _receivedMessage,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            )
          ],
        ),
      ),
    );
  }
}

