import 'package:flutter/material.dart';

class Home extends StatelessWidget {
  const Home({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Flexible(
            child: Container(color: Colors.blueAccent),
            flex: 3,
          ),
          Flexible(
            child: Container(color: Colors.greenAccent),
            flex: 1,
          )
        ],
      ),
    );
  }
}
