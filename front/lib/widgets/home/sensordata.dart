import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:mqtt_client/mqtt_client.dart';
import 'package:mqtt_client/mqtt_server_client.dart';

class SensorDataPage extends StatefulWidget {
  @override
  _SensorDataPageState createState() => _SensorDataPageState();
}

class _SensorDataPageState extends State<SensorDataPage> {
  String _connectionStatus = 'origin';
  String _receivedMessage =
      '{"C": "disconnect", "H":"disconnect"}'; // Store received message
  late MqttServerClient client;

  @override
  void initState() {
    super.initState();
    _connectToMQTT();
  }

  Future<void> _connectToMQTT() async {
    String clientId = '';
    String awsEndpoint =
        'aw9r86eiouxek-ats.iot.ap-northeast-2.amazonaws.com'; // e.g., 'xxxxxxxxxxxxx-ats.iot.us-west-2.amazonaws.com'
    String topic = 'raspi/data'; // e.g., 'my/topic'

    final rootCA = await rootBundle.load('assets/certs/RootCA.pem');
    final deviceCert =
        await rootBundle.load('assets/certs/DeviceCertificate.crt');
    final privateKey = await rootBundle.load('assets/certs/Private.key');

    SecurityContext securityContext = SecurityContext(withTrustedRoots: false)
      ..setTrustedCertificatesBytes(rootCA.buffer.asUint8List())
      ..useCertificateChainBytes(deviceCert.buffer.asUint8List())
      ..usePrivateKeyBytes(privateKey.buffer.asUint8List());

    client = MqttServerClient(awsEndpoint, clientId)
      ..port = 8883
      ..secure = true
      ..logging(on: true)
      ..securityContext = securityContext
      ..onConnected = () {
        setState(() {
          _connectionStatus = 'Connected';
        });
        print('Connected to MQTT');
        _subscribeToTopic(client, topic); // Subscribe to topic
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

  void _subscribeToTopic(MqttServerClient client, String topic) {
    client.subscribe(topic, MqttQos.atLeastOnce);

    client.updates?.listen((List<MqttReceivedMessage<MqttMessage>> c) {
      final MqttPublishMessage recMess = c[0].payload as MqttPublishMessage;
      final String payload =
          MqttPublishPayload.bytesToStringAsString(recMess.payload.message);
      print('Connection state: ${client.connectionStatus?.state}');
      print('Subscription status: ${client.getSubscriptionsStatus(topic)}');

      setState(() {
        _receivedMessage = payload;
      });
      print('Received message: $payload');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // Text(
          //   _connectionStatus,
          //   style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          // ),
          // Text(
          //   _receivedMessage,
          //   style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          // ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Stack(
                children: <Widget>[
                  Image.asset('assets/temperature.png'),
                  Positioned(
                    top: 60,
                    left: 66,
                    child: Text(
                        jsonDecode(_receivedMessage)['C'].toString() + '°C'),
                  )
                ],
              ),
              Stack(
                children: [
                  Image.asset('assets/humidity.png'),
                  Positioned(
                    top: 60,
                    left: 66,
                    child: Text(
                        jsonDecode(_receivedMessage)['H'].toString() + '%'),
                  )
                ],
              ),
            ],
          )
        ],
      ),
    );
  }
}
