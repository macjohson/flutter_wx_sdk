package com.huanshao.wx_sdk.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.huanshao.wx_sdk.Constant
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import java.lang.Exception

class WXEntryActivity: Activity(), IWXAPIEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if(intent != null){
            handleIntent(intent)
        }
    }

    override fun onReq(p0: BaseReq?) {
        finish()
    }

    override fun onResp(p0: BaseResp?) {
        if (p0 != null && p0 is SendAuth.Resp) {
            val code = p0.code
            Constant.events.success(code)
        }
        finish()
    }

    private fun handleIntent(intent: Intent){
        try{
            Constant.wxApi?.handleIntent(intent, this)
        }catch (e: Exception){
            e.printStackTrace()
            finish()
        }
    }

}