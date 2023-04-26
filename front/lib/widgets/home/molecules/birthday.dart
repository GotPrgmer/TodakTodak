import 'package:flutter/material.dart';

class Birthday extends StatelessWidget {
  const Birthday({Key? key}) : super(key: key);

  final year = 1996;
  final month = 11;
  final day = 11;
  final D_day = 9625;

  final animal = '쥐';
  final star = '전갈';

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            child: Column(
              children: [
                Container(
                  child: Row(
                    children: [
                      Text(year.toString() + '년 '),
                      Text(month.toString() + '월 '),
                      Text(day.toString() + '일')
                    ],
                  ),
                ),
                Container(
                  child: Row(
                    children: [
                      Text(animal + '띠'),
                      Text('  /  '),
                      Text(star + '자리')
                    ],
                  ),
                )
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.all(20),
            child: Text('D+' + D_day.toString()),
          )
        ],
      ),
    );
  }
}
