package com.huanshao.wx_sdk

import com.tencent.mm.opensdk.openapi.IWXAPI
import io.flutter.plugin.common.EventChannel

object Constant {
    lateinit var wxApi: IWXAPI

    lateinit var events: EventChannel.EventSink

    val wxAppId = "wx190e126d6dbc24d5"
}