//
//  GGPath.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGPath.h"

@implementation GGPath

+(NSString*)docPath
{
    static NSString *__docPath;
    if (__docPath == nil) {
        __docPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    }
    return __docPath;
}

+(NSString*)cachePath
{
    static NSString *__cachePath;
    if (__cachePath == nil) {
        __cachePath = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    }
    return __cachePath;
}

+(NSString *)savedDataPath
{
    static NSString *__savedDataPath;
    if (__savedDataPath == nil) {
        __savedDataPath = [[self docPath] stringByAppendingPathComponent:@"savedData"];
    }
    return __savedDataPath;
}

+(NSString *)pathCurrentUserData
{
    return [[self savedDataPath] stringByAppendingPathComponent:@"currentUser.plist"];
}

+(NSString *)ensurePathExists:(NSString *)aPath
{
    NSError *error;
    BOOL success = [[NSFileManager defaultManager] createDirectoryAtPath:aPath withIntermediateDirectories:YES attributes:nil error:&error];
    if (!success) {
        DLog(@"Error creating data path: %@", [error localizedDescription]);
    }
    return aPath;
}

+(void)removePath:(NSString *)aPath
{
    [[NSFileManager defaultManager] removeItemAtPath:aPath error:nil];
}

@end
