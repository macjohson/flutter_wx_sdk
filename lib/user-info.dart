import 'dart:convert';

import 'package:dio/dio.dart';

class UserInfo {
  final String code;
  final String appId;
  final String secret;
  static const _accessTokenUrl =
      "https://api.weixin.qq.com/sns/oauth2/access_token";
  static const _userInfoUrl = "https://api.weixin.qq.com/sns/userinfo";

  UserInfo(this.code, this.appId, this.secret);

  Future<_AccessTokenResponse?> get _accessToken async {
    final Dio dio = Dio();

    try {
      final res = await dio.get(_accessTokenUrl,
          queryParameters: _AccessTokenArguments(appId, secret, code).mapData);

      if (res.data != null) {
        return _AccessTokenResponse.fromMap(json.decode(res.data));
      }
      return null;
    } catch (e) {
      print(e);
      return null;
    }
  }

  Future<UserInfoResponse?> get userInfo async {
    final _AccessTokenResponse? _accessTokenResponse = await _accessToken;

    if (_accessTokenResponse == null) return null;

    try {
      final res = await Dio().get(_userInfoUrl, queryParameters: {
        "access_token": _accessTokenResponse.accessToken,
        "openid": _accessTokenResponse.openid
      });

      if (res.data != null) {
        return UserInfoResponse.fromJson(json.decode(res.data));
      }

      return null;
    } catch (e) {
      print(e);
      return null;
    }
  }
}

class _AccessTokenArguments {
  final String appid;
  final String secret;
  final String code;
  final String grantType = "authorization_code";

  _AccessTokenArguments(this.appid, this.secret, this.code);

  Map<String, dynamic> get mapData {
    return {
      "appid": appid,
      "secret": secret,
      "code": code,
      "grant_type": grantType
    };
  }
}

class _AccessTokenResponse {
  String? accessToken;
  int? expiresIn;
  String? refreshToken;
  String? openid;
  String? scope;
  String? unionid;

  _AccessTokenResponse(this.accessToken, this.expiresIn, this.refreshToken,
      this.openid, this.scope, this.unionid);

  _AccessTokenResponse.fromMap(Map<String, dynamic> map) {
    this.accessToken = map["access_token"];
    this.expiresIn = map["expires_in"];
    this.refreshToken = map["refresh_token"];
    this.openid = map["openid"];
    this.scope = map["scope"];
    this.unionid = map["unionid"];
  }
}

class UserInfoResponse {
  String? openid;
  String? nickname;
  int? sex;
  String? province;
  String? city;
  String? country;
  String? headimgurl;
  String? unionid;

  UserInfoResponse(this.openid, this.nickname, this.sex, this.province,
      this.city, this.country, this.headimgurl, this.unionid);

  UserInfoResponse.fromJson(Map<String, dynamic> map)
      : openid = map["openid"],
        nickname = map["nickname"],
        sex = map["sex"],
        province = map["province"],
        city = map["city"],
        country = map["country"],
        headimgurl = map["headimgurl"],
        unionid = map["unionid"];
}
