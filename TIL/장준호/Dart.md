# Dart



# 1. Dart 시작

- [DartPad](https://dartpad.dev/)에 접속

# 2. Hello World

```dart
void main() {
  print('Hello JJH'); // Hello JJH
}
```

# 3. 변수 선언

> 변수 선언

```dart
void main() {
  // variable
  // Code는 무조건 순서대로 실행이 된다.
  var name = '장준호';

  print(name); // 장준호

  var name2 = "쨍준쨍";

  print(name2); // 쨍준쨍

  // 변수 재할당
  name = "Flutter";

  print(name); // Flutter

  // 변수 재선언 오류
  var name = "장준호 Again"; // Info: Previous declaration of 'name'.
}
```

# 4. 변수 Type

> 정수(integer)

```dart
void main() {
  // 정수
  // integer
  int number1 = 10;

  print(number1); // 10

  int number2 = 15;

  print(number2); // 15

  int number3 = -20;

  print(number3); // -20
}
```

> 실수(double)

```dart
void main() {
  // 실수
  // double
  double number1 = 2.5;
  double number2 = 0.5;

  // 더하기
  print(number1 + number2); // 3

  // 빼기
  print(number1 - number2); // 2

  // 나누기
  print(number1 / number2); // 5

  // 곱하기
  print(number1 * number2); // 1.25
}
```

> 참(true) / 거짓(false)

```dart
void main() {
  // 맞다 / 틀리다
  // Boolean 
  bool isTrue = true;
  bool isFalse = false;

  print(isTrue);
  print(isFalse);
}
```

> 문자열(String)

```dart
void main() {
  // 글자 타입
  // String
  String name = "장준호";
  String name2 = "쨍준쨍";

  print(name); // 장준호
  print(name2); // 쨍준쨍

  // var String
  var name3 = "쨍준쨍준";
  var number = 20;

  print(name3.runtimeType); // String

  Map<String, Map<int, List<double>>> testType = {};
  var testType = {};
}
```

```dart
void main() {
  // 글자 타입
  // String
  String name = "장준호";
  String name2 = "쨍준쨍";

  print(name + name2); // 장준호쨍준쨍
  print(name + ' ' + name2); // 장준호 쨍준쨍

  print('${name.runtimeType} ${name2}'); // String 쨍준쨍

  print('$name.runtimeType $name2'); // 장준호.runtimeType 쨍준쨍
}
```

> dynamic

```dart
void main() {
  dynamic name = "장준호";

  print(name); // 장준호

  dynamic number = 1;

  print(number); // 1

  var name2 = "쨍준쨍";

  print(name2); // 쨍준쨍

  print(name.runtimeType); // String
  print(name2.runtimeType); // String

  // dynamic은 재할당이 가능
  name = 2;

  // var은 재할당이 불가능
  name2 = 5;
}
```
