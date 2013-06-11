//
//  GGDbManager.h
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@class GGPoliceman;
@class GGWanted;

@interface GGDbManager : NSObject
AS_SINGLETON(GGDbManager)

-(BOOL)insertPoliceman:(GGPoliceman *)aPoliceman;
-(BOOL)deletePolicemanByID:(long long)aPolicemanID;
-(NSArray *)getAllPolicemans;
-(BOOL)hasPolicemanWithID:(long long)aPolicemanID;

-(BOOL)insertWanted:(GGWanted *)aWanted;
-(BOOL)deleteWantedByID:(long long)aWantedID;
-(NSArray *)getAllWanted;
-(BOOL)hasWantedWithID:(long long)aWantedID;

@end
