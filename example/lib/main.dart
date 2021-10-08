import 'package:flutter/material.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "微信sdk flutter插件",
      home: Scaffold(
        appBar: AppBar(
          title: Text("微信sdk flutter插件 demo"),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              FlatButton(onPressed: () async{
              }, child: Text("登录"))
            ],
          ),
        ),
      ),
    );
  }
}
