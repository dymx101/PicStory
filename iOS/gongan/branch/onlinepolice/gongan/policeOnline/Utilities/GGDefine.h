//
//  GGDefine.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-1.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>

//
// #define GGN_STR_PRODUCTION_SERVER_URL               @"http://rhtsoft.gnway.net:8888/mobile"
#define GGN_STR_PRODUCTION_SERVER_URL                   @"http://www.right-soft.net/mobile"


//#define CURRENT_ENV 3
//
//#undef CURRENT_SERVER_URL
//#if (CURRENT_ENV == 1)
//#define CURRENT_SERVER_URL         GGN_STR_PRODUCTION_SERVER_URL
//#elif (CURRENT_ENV == 2)
//#define CURRENT_SERVER_URL         GGN_STR_DEMO_SERVER_URL
//#elif (CURRENT_ENV == 3)
//#define CURRENT_SERVER_URL         GGN_STR_CN_SERVER_URL
//#elif (CURRENT_ENV == 4)
//#define CURRENT_SERVER_URL         GGN_STR_STAGING_SERVER_URL
//#endif
//

#define APP_CODE_VALUE      @"09ad5d624c0294d1"
#define APP_CODE_IPHONE     @"78cfc17502a1e05a"
#define APP_CODE_IPAD       @"c0d67d02e7c74d36"

//
#undef	__INT
#define __INT( __x )			[NSNumber numberWithInt:(NSInteger)(__x)]
#undef	__UINT
#define __UINT( __x )			[NSNumber numberWithUnsignedInt:(NSUInteger)(__x)]
#undef	__LONG
#define __LONG( __x )			[NSNumber numberWithLong:(long)(__x)]
#undef	__LONGLONG
#define __LONGLONG( __x )			[NSNumber numberWithLongLong:(long long)(__x)]
#undef	__FLOAT
#define	__FLOAT( __x )			[NSNumber numberWithFloat:(float)(__x)]
#undef	__DOUBLE
#define	__DOUBLE( __x )			[NSNumber numberWithDouble:(double)(__x)]
#undef	__BOOL
#define	__BOOL( __x )			[NSNumber numberWithBool:(BOOL)(__x)]

// 判断是否为IPAD
#define ISIPADDEVICE (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad)

// 单例
#undef	AS_SINGLETON
#define AS_SINGLETON( __class ) \
+ (__class *)sharedInstance;

#undef	DEF_SINGLETON
#define DEF_SINGLETON( __class ) \
+ (__class *)sharedInstance \
{ \
static dispatch_once_t once; \
static __class * __singleton__; \
dispatch_once( &once, ^{ __singleton__ = [[__class alloc] init]; } ); \
return __singleton__; \
}

//LOG
#ifdef DEBUG
#define DLog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__);
#else
#define DLog(...)
#endif

#define ALog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__);

#ifdef DEBUG
#define DALog(fmt, ...)  { UIAlertView *alert = [[OTSAlertView alloc] initWithTitle:[NSString stringWithFormat:@"%s\n [Line %d] ", __PRETTY_FUNCTION__, __LINE__] message:[NSString stringWithFormat:fmt, ##__VA_ARGS__]  delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil]; [alert show]; }
#else
#define DALog(...)
#endif

//
#define GG_KEY_BOARD_HEIGHT_IPHONE_PORTRAIT 216.f
#define GG_KEY_BOARD_HEIGHT_IPHONE_LANDSCAPE 162.f


//Let's rename the original macro IS_WIDESCREEN:
#define IS_WIDESCREEN ( fabs( ( double )[ [ UIScreen mainScreen ] bounds ].size.height - ( double )568 ) < DBL_EPSILON )
//And let's add model detection macros:
#define IS_IPHONE ( [ [ [ UIDevice currentDevice ] model ] isEqualToString: @"iPhone" ] )
#define IS_IPOD   ( [ [ [ UIDevice currentDevice ] model ] isEqualToString: @"iPod touch" ] )
//This way, we can ensure we have an iPhone model AND a widescreen, and we can redefine the IS_IPHONE_5 macro:
#define IS_IPHONE_5 ( IS_IPHONE && IS_WIDESCREEN )


#define PATH_OF_APP_HOME    NSHomeDirectory()
#define PATH_OF_TEMP        NSTemporaryDirectory()
#define PATH_OF_DOCUMENT    [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0]

//
@interface GGDefine : NSObject
@end
