package com.huanshao.wx_sdk

import com.tencent.mm.opensdk.openapi.IWXAPI
import io.flutter.plugin.common.EventChannel

object Constant {
    var wxApi: IWXAPI? = null

    lateinit var events: EventChannel.EventSink
}