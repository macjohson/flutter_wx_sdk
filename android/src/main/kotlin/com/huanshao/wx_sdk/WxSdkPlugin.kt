package com.huanshao.wx_sdk

import android.content.Context
import androidx.annotation.NonNull
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.openapi.WXAPIFactory

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/** WxSdkPlugin */
class WxSdkPlugin: FlutterPlugin, EventChannel.StreamHandler, MethodChannel.MethodCallHandler {
  private lateinit var eventChannel: EventChannel
  private lateinit var methodChannel: MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    this.context = flutterPluginBinding.applicationContext

    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger, "wx_sdk/wx-login")
    eventChannel.setStreamHandler(this)

    methodChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "wx_sdk")
    methodChannel.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel.setStreamHandler(null)
    methodChannel.setMethodCallHandler(null)
  }

  override fun onListen(arguments: Any?, events: EventChannel.EventSink) {
    Constant.events = events

    if(Constant.wxApi == null){
      events.error("-1", "微信sdk未初始化", "微信sdk未初始化，请先调用init方法初始化sdk")
      return
    }

    val req = SendAuth.Req()

    req.scope = "snsapi_userinfo"
    req.state = "com.chinapeace.lincang/wx-login"
    Constant.wxApi?.sendReq(req)
  }

  override fun onCancel(arguments: Any?) {

  }

  override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
    if(call.method == "init-sdk"){
      initWxSdk(call, result)
    }else{
      if(Constant.wxApi == null){
        result.error("-1", "微信sdk未初始化", "微信sdk未初始化，请先调用init方法初始化sdk")
        return
      }

      when(call.method){
        "share-mini-program" -> shareMiniProgram(call, result)
        "open-mini-program" -> openMiniProgram(call, result)
        "share-image" -> shareImage(call, result)
      }
    }
  }

  private fun shareImage(call: MethodCall, result: MethodChannel.Result) {
    val bytes = call.argument<ByteArray>("image") as ByteArray
    val type = call.argument<String>("type") as String
    
    val imageObject = WXImageObject(bytes)
    val wxMediaMessage = WXMediaMessage().apply { 
      mediaObject = imageObject
    };
    

    val wxSendMessageToWX = SendMessageToWX.Req().apply { 
      message = wxMediaMessage
      scene = if(type == "session") SendMessageToWX.Req.WXSceneSession else SendMessageToWX.Req.WXSceneTimeline
    }

    Constant.wxApi?.sendReq(wxSendMessageToWX)

    result.success(true);

  }

  private fun initWxSdk(call: MethodCall, result: MethodChannel.Result) {
    if(Constant.wxApi != null){
      result.success(true)
      return
    }
    val appId = call.argument<String>("appId")

    Constant.wxApi = WXAPIFactory.createWXAPI(context, appId).apply { 
      registerApp(appId)
    }

    result.success(true)
  }

  private fun shareMiniProgram(call: MethodCall, result: MethodChannel.Result) {
    val title = call.argument<String>("title")
    val description = call.argument<String>("description")
    val thumbData = call.argument<ByteArray>("thumbData")
    val webpageUrl = call.argument<String>("webpageUrl")
    val path = call.argument<String>("path")
    val userName = call.argument<String>("userName")


    val miniProgramObject = WXMiniProgramObject().apply { 
      this.webpageUrl = webpageUrl
      this.path = path
      this.userName = userName
      this.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE
    }

    val wxMediaMessage = WXMediaMessage(miniProgramObject).apply {
      this.title = title
      this.description = description
      this.thumbData = thumbData
    }

    val sendMessageToWX = SendMessageToWX.Req().apply {
      message = wxMediaMessage
      scene = SendMessageToWX.Req.WXSceneSession
    }

    Constant.wxApi?.sendReq(sendMessageToWX)

    result.success(true)
  }

  private fun openMiniProgram(call: MethodCall, result: MethodChannel.Result) {
    val userName = call.argument<String>("userName")

    val req = WXLaunchMiniProgram.Req().apply {
      this.userName = userName
    }
    
    Constant.wxApi?.sendReq(req)
    result.success(true)
  }
}
