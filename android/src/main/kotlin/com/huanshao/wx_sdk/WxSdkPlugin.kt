package com.huanshao.wx_sdk

import androidx.annotation.NonNull
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** WxSdkPlugin */
class WxSdkPlugin: FlutterPlugin, EventChannel.StreamHandler {
  private lateinit var eventChannel: EventChannel
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    Constant.wxApi = WXAPIFactory.createWXAPI(flutterPluginBinding.applicationContext, Constant.wxAppId)
    Constant.wxApi.registerApp(Constant.wxAppId)
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "wx_sdk/wx-login")
    eventChannel.setStreamHandler(this)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel.setStreamHandler(null)
  }

  override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
    Constant.events = events

    val req = SendAuth.Req()

    req.scope = "snsapi_userinfo"
    req.state = "com.chinapeace.lincang/wx-login"
    Constant.wxApi.sendReq(req)
  }

  override fun onCancel(arguments: Any?) {

  }
}
