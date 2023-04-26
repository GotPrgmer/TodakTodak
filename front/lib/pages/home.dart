import 'package:flutter/material.dart';
import 'package:front/widgets/home/babyprofile.dart' as profile;
import 'package:front/widgets/home/sensordata.dart' as sensordata;

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Flexible(
            child: profile.Profile(),
            flex: 3,
          ),
          Flexible(
            child: sensordata.SensorDataPage(),
            flex: 1,
          )
        ],
      ),
    );
  }
}
