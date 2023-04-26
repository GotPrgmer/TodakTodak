import 'package:flutter/material.dart';

class GenderName extends StatelessWidget {
  const GenderName({Key? key}) : super(key: key);

  final gender = '남';
  final name = '백승범';

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            child: Text(gender),
          ),
          Container(
            margin: EdgeInsets.fromLTRB(10, 0, 0, 0),
            child: Text(name),
          )
        ],
      ),
    );
  }
}
