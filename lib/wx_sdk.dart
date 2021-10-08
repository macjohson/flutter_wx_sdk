
import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:wx_sdk/user-info.dart';

enum ShareImageType{
  session,
  timeline
}

extension shareImageTyped on ShareImageType{
  String get name => describeEnum(this);

  String get typeString{
    switch(this){
      case ShareImageType.session:
        return "session";
      case ShareImageType.timeline:
        return "timeline";
      default:
        return "";
    }
  }
}

class WxSdk {
  static const EventChannel _wxLoginEventChannel =
  const EventChannel("wx_sdk/wx-login");

  static const MethodChannel _methodChannel =
  const MethodChannel("wx_sdk");

  static Future<String> get _code async {
    final Completer completer = Completer<String>();

    _wxLoginEventChannel.receiveBroadcastStream().listen((event) {
      completer.complete(event);
    });
    return completer.future as FutureOr<String>;
  }

  static Future<UserInfoResponse?> login(String appId, String secret) async {
    final code = await _code;

    final UserInfo userInfo = UserInfo(code, appId, secret);

    return await userInfo.userInfo;
  }

  static Future<void> init(String appId) async {
    await _methodChannel.invokeMethod("init-sdk", {
      "appId": appId
    });
  }

  static Future<void> shareMiniProgram(
      String title,
      String description,
      Uint8List thumbData,
      String webpageUrl,
      String path,
      String userName
      ) async {
    await _methodChannel.invokeMethod("share-mini-program", {
      "title": title,
      "description": description,
      "thumbData": thumbData,
      "webpageUrl": webpageUrl,
      "path": path,
      "userName": userName
    });
  }

  static Future<void> openMiniProgram(String userName) async {
    await _methodChannel.invokeMethod("open-mini-program", {"userName": userName});
  }

  static Future<void> shareImage({Uint8List? image,required ShareImageType type}) async {
    await _methodChannel.invokeMethod("share-image", {
      "image": image,
      "type": type.typeString
    });
  }
}
