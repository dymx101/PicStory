//
//  GGRuntimeData.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-1.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGRuntimeData : NSObject
AS_SINGLETON(GGRuntimeData)

@property (assign)  BOOL        runedBefore;  // has runed before

-(BOOL)isFirstRun;
//-(NSString *)accessToken;

-(void)saveRunedBefore;

//-(void)saveCurrentUser;
//-(void)loadCurrentUser;
//-(void)resetCurrentUser;
@end

#define GGSharedRuntimeData [GGRuntimeData sharedInstance]
