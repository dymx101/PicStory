//
//  GGPath.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGPath : NSObject
+(NSString*)docPath;
+(NSString *)savedDataPath;
+(NSString *)pathCurrentUserData;

+(NSString *)ensurePathExists:(NSString *)aPath;
+(void)removePath:(NSString *)aPath;
@end
