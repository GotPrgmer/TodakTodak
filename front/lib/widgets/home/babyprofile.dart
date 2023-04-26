import 'package:flutter/material.dart';
import 'molecules/gendername.dart' as gendername;
import 'molecules/birthday.dart' as birthday;

class Profile extends StatelessWidget {
  const Profile({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final double containerHeight = MediaQuery.of(context).size.height;

    return Container(
      decoration: BoxDecoration(
          image: DecorationImage(
              fit: BoxFit.fill, image: AssetImage('assets/background.jpg'))),
      child: Stack(
        children: [
          Positioned(
            top: 0,
            right: 0,
            child: IconButton(
              icon: Icon(Icons.edit_note),
              onPressed: () {},
            ),
          ),
          Positioned(
            top: (containerHeight / 4) - 100,
            child: SizedBox(
              height: 300,
              width: MediaQuery.of(context).size.width,
              child: Center(
                child: Expanded(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      CircleAvatar(
                        backgroundImage: AssetImage('assets/bummy.png'),
                        radius: 100,
                      ),
                      Text('우리버미💖'),
                      gendername.GenderName(),
                      birthday.Birthday(),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
