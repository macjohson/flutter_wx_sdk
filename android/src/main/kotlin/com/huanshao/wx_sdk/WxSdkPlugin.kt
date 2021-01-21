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
class WxSdkPlugin: FlutterPlugin, EventChannel.StreamHandler, MethodCallHandler {
  private lateinit var eventChannel: EventChannel
  private lateinit var methodChannel: MethodChannel
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "wx_sdk/wx-login")
    eventChannel.setStreamHandler(this)

    methodChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "wx_sdk")
    methodChannel.setMethodCallHandler(this)

    Constant.wxApi = WXAPIFactory.createWXAPI(flutterPluginBinding.applicationContext, Constant.wxAppId)
    Constant.wxApi.registerApp(Constant.wxAppId)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel.setStreamHandler(null)
  }

  override fun onListen(arguments: Any?, @NonNull events: EventChannel.EventSink) {
    Constant.events = events

    events.success("hello")

    val req = SendAuth.Req()

    req.scope = "snsapi_userinfo"
    req.state = "com.chinapeace.lincang/wx-login"
    Constant.wxApi.sendReq(req)
  }

  override fun onCancel(arguments: Any?) {

  }

  override fun onMethodCall(call: MethodCall, result: Result) {
  }
}
