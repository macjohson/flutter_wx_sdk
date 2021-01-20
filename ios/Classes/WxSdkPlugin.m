#import "WxSdkPlugin.h"
#if __has_include(<wx_sdk/wx_sdk-Swift.h>)
#import <wx_sdk/wx_sdk-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "wx_sdk-Swift.h"
#endif

@implementation WxSdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftWxSdkPlugin registerWithRegistrar:registrar];
}
@end
