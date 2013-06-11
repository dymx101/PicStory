//
//  GGNApi.h
//  WeiGongAnApp
//
//  Created by dong yiming on 13-3-22.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>  

typedef void(^GGApiBlock)(id operation, id aResultObject, NSError* anError);


@interface GGApi : AFHTTPClient

// singleton method to get a shared api all over the app
+ (GGApi *)sharedApi;

-(void)canceAllOperations;

-(void)_execPostWithPath:(NSString *)aPath params:(NSDictionary *)aParams callback:(GGApiBlock)aCallback;
-(void)_execGetWithPath:(NSString *)aPath params:(NSDictionary *)aParams callback:(GGApiBlock)aCallback;

-(void)getAreas:(GGApiBlock)aCallback;
-(void)getPolicemanByAreaID:(long long)anAreaID callback:(GGApiBlock)aCallback;
-(void)searchAreaByKeyword:(NSString *)aKeyword callback:(GGApiBlock)aCallback;
-(void)getWantedRootCategory:(GGApiBlock)aCallback;
-(void)getWantedSubCategoryWithID:(long long)aColumnID callback:(GGApiBlock)aCallback;
-(void)checkUpdateWithCurrentVersion:(NSString *)aCurrentVersion  callback:(GGApiBlock)aCallback;
@end

#define GGSharedAPI [GGApi sharedApi]

