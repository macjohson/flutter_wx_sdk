
import 'dart:async';

import 'package:flutter/services.dart';

class WxSdk {
  static const MethodChannel _channel =
      const MethodChannel('wx_sdk');

  static const EventChannel _wxLoginEventChannel =
  const EventChannel("wx_sdk/wx-login");

  static Future<String> login() async {
    final Completer completer = Completer<String>();

    _wxLoginEventChannel.receiveBroadcastStream().listen((event) {
      print(event);
      completer.complete(event);
    });
    return completer.future;
  }
}
