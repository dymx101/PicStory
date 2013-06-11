//
//  GGRuntimeData.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-1.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGRuntimeData.h"
//#import "GGMember.h"
#import "GGPath.h"

#define kDataKeyCurrentUser @"kDataKeyCurrentUser"
#define kDefaultKeyRunedBefore @"kDefaultKeyRunedBefore"

@implementation GGRuntimeData
DEF_SINGLETON(GGRuntimeData)

-(id)init
{
    self = [super init];
    if (self) {
        //[self loadCurrentUser];
        [self _loadRunedBefore];
    }
    return self;
}


-(BOOL)isFirstRun
{
    BOOL firstRun = !_runedBefore;
    if (firstRun) {
        _runedBefore = YES;
        [self saveRunedBefore];
    }
    return firstRun;
}

//-(NSString *)accessToken
//{
//    return self.currentUser.accessToken;
//}

-(void)_loadRunedBefore
{
    _runedBefore = [[NSUserDefaults standardUserDefaults] boolForKey:kDefaultKeyRunedBefore];
}

-(void)saveRunedBefore
{
    [[NSUserDefaults standardUserDefaults] setBool:_runedBefore forKey:kDefaultKeyRunedBefore];
}

//-(void)saveCurrentUser
//{
//    [GGPath ensurePathExists:[GGPath savedDataPath]];
//    
//    NSMutableData *data = [[NSMutableData alloc] init];
//    NSKeyedArchiver *archiver = [[NSKeyedArchiver alloc] initForWritingWithMutableData:data];
//    [archiver encodeObject:self.currentUser forKey:kDataKeyCurrentUser];
//    [archiver finishEncoding];
//    [data writeToFile:[GGPath pathCurrentUserData] atomically:YES];
//}
//
//-(void)loadCurrentUser
//{
//    NSData *data = [[NSData alloc] initWithContentsOfFile:[GGPath pathCurrentUserData]];
//    if (data) {
//        NSKeyedUnarchiver *unarchiver = [[NSKeyedUnarchiver alloc] initForReadingWithData:data];
//        self.currentUser = [unarchiver decodeObjectForKey:kDataKeyCurrentUser];
//    }
//}
//
//-(void)resetCurrentUser
//{
//    [GGPath removePath:[GGPath pathCurrentUserData]];
//    self.currentUser = nil;
//}
@end
