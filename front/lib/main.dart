import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'pages/home.dart' as home;
import 'pages/video.dart' as video;
import 'pages/cry.dart' as cry;
import 'pages/myPage.dart' as mypage;
import './style.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  var selected_tab = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: theme,
      home: Scaffold(
        appBar: AppBar(
          title: Image.asset('assets/Logo.png'),
          centerTitle: true,
          backgroundColor: Color(0xffFFDEDE),
        ),
        body: [
          home.Home(),
          video.Video(),
          cry.Cry(),
          mypage.MyPage()
        ][selected_tab],
        bottomNavigationBar: BottomNavigationBar(
          backgroundColor: Color(0xffFFDEDE),
          type: BottomNavigationBarType.fixed,
          showUnselectedLabels: true,
          currentIndex: selected_tab,
          onTap: (i) {
            setState(() {
              selected_tab = i;
            });
          },
          items: [
            BottomNavigationBarItem(
                icon: Icon(Icons.home_outlined),
                label: '홈',
                activeIcon: Icon(Icons.home)),
            BottomNavigationBarItem(
                icon: Icon(Icons.personal_video_outlined),
                label: '실시간 영상',
                activeIcon: Icon(Icons.personal_video)),
            BottomNavigationBarItem(
                icon: Icon(Icons.volume_up_outlined),
                label: '울음기록',
                activeIcon: Icon(Icons.volume_up)),
            BottomNavigationBarItem(
                icon: Icon(Icons.child_care_outlined),
                label: '마이페이지',
                activeIcon: Icon(Icons.child_care)),
          ],
        ),
      ),
    );
  }
}
