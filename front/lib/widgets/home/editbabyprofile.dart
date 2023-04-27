import 'package:flutter/material.dart';
import 'babyprofile.dart' as babyprofile;
import 'dart:io';
import 'package:image_picker/image_picker.dart';

class EditProfile extends StatefulWidget {
  const EditProfile(
      {Key? key,
      this.nickname,
      this.picturePath,
      this.gender,
      this.name,
      this.year,
      this.month,
      this.day,
      this.D_day,
      this.animal,
      this.star})
      : super(key: key);

  final nickname;
  final picturePath;
  final gender;
  final name;
  final year, month, day, D_day, animal, star;

  @override
  State<EditProfile> createState() => _EditProfileState();
}

class _EditProfileState extends State<EditProfile> {
  XFile? _imageFile;
  final ImagePicker _picker = ImagePicker();

  @override
  Widget build(BuildContext context) {
    final double containerHeight = MediaQuery.of(context).size.height;

    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
      },
      child: Scaffold(
          body: Column(
        children: [
          Container(
            decoration: BoxDecoration(
                image: DecorationImage(
                    fit: BoxFit.fill,
                    image: AssetImage('assets/background.jpg'))),
            // margin: EdgeInsets.fromLTRB(0, 30, 0, 0),
            height: containerHeight,
            child: Row(
              children: [
                SizedBox(
                  width: MediaQuery.of(context).size.width,
                  child: Stack(
                    children: <Widget>[
                      Positioned(
                          top: 30,
                          child: TextButton(
                              onPressed: () {
                                Navigator.pop(context);
                              },
                              child: Text('취소'))),
                      Positioned(
                          top: 30,
                          right: 0,
                          child:
                              TextButton(onPressed: () {}, child: Text('완료'))),
                      Positioned(
                        // top: (containerHeight / 4) - 100,
                        child: SizedBox(
                          width: MediaQuery.of(context).size.width,
                          child: Center(
                            child: Expanded(
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  InkWell(
                                    onTap: () async {
                                      var picker = ImagePicker();
                                      var image = await picker.pickImage(
                                          source: ImageSource.gallery);
                                      setState(() {
                                        _imageFile = image;
                                      });
                                    },
                                    child: CircleAvatar(
                                      backgroundImage: _imageFile == null
                                          ? AssetImage(widget.picturePath)
                                              as ImageProvider<Object>?
                                          : FileImage(File(_imageFile!.path))
                                              as ImageProvider<Object>?,
                                      radius: 100,
                                    ),
                                  ),
                                  Container(
                                    width: 100,
                                    child: TextField(
                                      decoration: InputDecoration(
                                          hintText: widget.nickname),
                                    ),
                                  ),
                                  Container(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        Container(
                                          child: DropdownGender(
                                              gender: widget.gender),
                                        ),
                                        Container(
                                          width: 100,
                                          margin:
                                              EdgeInsets.fromLTRB(10, 0, 0, 0),
                                          child: TextField(
                                            decoration: InputDecoration(
                                                hintText: widget.name),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Container(
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        DropdownYear(year: widget.year),
                                        DropdownMonth(month: widget.month),
                                        DropdownDay(day: widget.day)
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
                ),
              ],
            ),
          )
        ],
      )),
    );
  }
}

class DropdownGender extends StatefulWidget {
  const DropdownGender({Key? key, this.gender}) : super(key: key);

  final gender;

  @override
  State<DropdownGender> createState() => _DropdownGenderState();
}

class _DropdownGenderState extends State<DropdownGender> {
  List<String> dropdownList = ['-', '남', '여'];
  String? selectedDropdown;

  @override
  void initState() {
    super.initState();
    selectedDropdown = widget.gender ?? '-';
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton(
      value: selectedDropdown,
      items: dropdownList.map((String item) {
        return DropdownMenuItem<String>(
          child: Text('$item'),
          value: item,
        );
      }).toList(),
      onChanged: (dynamic value) {
        setState(() {
          selectedDropdown = value;
        });
      },
    );
  }
}

class DropdownYear extends StatefulWidget {
  const DropdownYear({Key? key, this.year}) : super(key: key);

  final year;

  @override
  State<DropdownYear> createState() => _DropdownYearState();
}

class _DropdownYearState extends State<DropdownYear> {
  List<int> dropdownList = [1999, 1998, 1997, 1996];
  int? selectedDropdown;

  @override
  void initState() {
    super.initState();
    selectedDropdown = widget.year ?? '-';
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton(
      value: selectedDropdown,
      items: dropdownList.map((int item) {
        return DropdownMenuItem<int>(
          child: Text('$item'.toString()),
          value: item,
        );
      }).toList(),
      onChanged: (dynamic value) {
        setState(() {
          selectedDropdown = value;
        });
      },
    );
  }
}

class DropdownMonth extends StatefulWidget {
  const DropdownMonth({Key? key, this.month}) : super(key: key);

  final month;

  @override
  State<DropdownMonth> createState() => _DropdownMonthState();
}

class _DropdownMonthState extends State<DropdownMonth> {
  List<int> dropdownList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  int? selectedDropdown;

  @override
  void initState() {
    super.initState();
    selectedDropdown = widget.month ?? '-';
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton(
      value: selectedDropdown,
      items: dropdownList.map((int item) {
        return DropdownMenuItem<int>(
          child: Text('$item'.toString()),
          value: item,
        );
      }).toList(),
      onChanged: (dynamic value) {
        setState(() {
          selectedDropdown = value;
        });
      },
    );
  }
}

// ///////////////////////////////////////////////////
class DropdownDay extends StatefulWidget {
  const DropdownDay({Key? key, this.day}) : super(key: key);

  final day;

  @override
  State<DropdownDay> createState() => _DropdownDayState();
}

class _DropdownDayState extends State<DropdownDay> {
  List<int> dropdownList = [11, 12, 13, 14, 15, 16];
  int? selectedDropdown;

  @override
  void initState() {
    super.initState();
    selectedDropdown = widget.day ?? '-';
  }

  @override
  Widget build(BuildContext context) {
    return DropdownButton(
      value: selectedDropdown,
      items: dropdownList.map((int item) {
        return DropdownMenuItem<int>(
          child: Text('$item'.toString()),
          value: item,
        );
      }).toList(),
      onChanged: (dynamic value) {
        setState(() {
          selectedDropdown = value;
        });
      },
    );
  }
}
