import 'package:flutter/material.dart';
import 'molecules/gendername.dart' as gendername;
import 'molecules/birthday.dart' as birthday;
import 'editbabyprofile.dart' as editprofile;

class Profile extends StatefulWidget {
  const Profile({Key? key}) : super(key: key);

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  final nickname = '우리버미💖';
  final picturePath = 'assets/bummy.png';
  final gender = '남';
  final name = '백승범';
  final year = 1996;
  final month = 11;
  final day = 11;
  final D_day = 9625;
  final animal = '쥐';
  final star = '전갈';

  @override
  Widget build(BuildContext context) {
    final double containerHeight = MediaQuery.of(context).size.height;

    return Container(
      decoration: BoxDecoration(
          image: DecorationImage(
              fit: BoxFit.fill, image: AssetImage("assets/background.jpg"))),
      child: Stack(
        children: [
          Positioned(
            top: 0,
            right: 0,
            child: IconButton(
              icon: Icon(Icons.edit_note),
              onPressed: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => editprofile.EditProfile(
                            nickname: nickname,
                            picturePath: picturePath,
                            gender: gender,
                            name: name,
                            year: year,
                            month: month,
                            day: day,
                            D_day: D_day,
                            animal: animal,
                            star: star)));
              },
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
                        backgroundImage: AssetImage(picturePath),
                        radius: 100,
                      ),
                      Text(nickname),
                      Container(
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
                      ),
                      Container(
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
                      )
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
