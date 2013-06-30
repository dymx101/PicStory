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
+ (NSString *)apiBaseUrl;

-(void)canceAllOperations;

-(void)_execPostWithPath:(NSString *)aPath params:(NSDictionary *)aParams callback:(GGApiBlock)aCallback;
-(void)_execGetWithPath:(NSString *)aPath params:(NSDictionary *)aParams callback:(GGApiBlock)aCallback;

-(void)getAreas:(GGApiBlock)aCallback;
-(void)getPolicemanByAreaID:(long long)anAreaID callback:(GGApiBlock)aCallback;
-(void)searchAreaByKeyword:(NSString *)aKeyword callback:(GGApiBlock)aCallback;
-(void)searchAreaByKeyword:(NSString *)aKeyword AreaID:(long)anAreaID callback:(GGApiBlock)aCallback;
-(void)getWantedRootCategory:(GGApiBlock)aCallback;
-(void)getWantedRootCategoryWithAreaID:(long)anAreaID callback:(GGApiBlock)aCallback;
-(void)getWantedSubCategoryWithID:(long long)aColumnID callback:(GGApiBlock)aCallback;
-(void)checkUpdateWithCurrentVersion:(NSString *)aCurrentVersion  callback:(GGApiBlock)aCallback;

#define REPORT_AREA_ID  2
-(NSString *)uniqueNumber;

#warning 上报位置,注意测试此接口时使用 http://rhtsoft.gnway.net:8888/mobile/
-(void)reportPoliceWithAreaID:(long)anAreaID
                  mbNum:(NSString *)aMbNum
                  pcNum:(NSString *)aPcNum
                   mapX:(float)aMapX
                   mapY:(float)aMapY
               callback:(GGApiBlock)aCallback;

//报警接口第二版 附带报警人及报警电话信息
-(void)reportPoliceWithAreaIDV2:(long)anAreaID
                          mbNum:(NSString *)aMbNum
                          pcNum:(NSString *)aPcNum
                           mapX:(float)aMapX
                           mapY:(float)aMapY
                         pcName:(NSString *)aPcName
                        pcPhone:(NSString *)aPcPhone
                       callback:(GGApiBlock)aCallback;

//机器编码
#define PHONE_PLATFORM @"iOS"
-(void)reportDeviceCode:(NSString *)aDeviceCode
               callback:(GGApiBlock)aCallback;
//v2 的接口

//返回程序支持的区域
-(void)getLocateAreas:(GGApiBlock)aCallback;

//警员评价
-(void)addPoliceEvaluateWithPolice:(long long)plId
                          evaluate:(int)evaluate
                            unitId:(long)unitId
                          callback:(GGApiBlock)aCallback;

//线索征集
-(void)getCluesRootCategory:(GGApiBlock)aCallback;
-(void)getCluesSubCategoryWithID:(long long)aColumnID callback:(GGApiBlock)aCallback;
-(void)getCluesSubCategoryWithContentID:(long long)aContentID callback:(GGApiBlock)aCallback;

//线索图片上传
//接口地址：http://rhtsoft.gnway.net:8888/mobile/fileUpload
//参数：
//contentId: 线索文章id
//clContext: 线索内容
//clPhoneId：手机编号
//clPhone：手机号码
//约定：图片拍摄时间为文件名
//参数请按照顺序传入。
//无返回参数。
//-(void)CluesFileUpload
-(void)reportClueWithContentID:(long long)aContentID
                      clueText:(NSString *)aClueText
                       phoneID:(NSString *)aPhoneID
                         phone:(NSString *)aPhone
                        images:(NSArray *)aImages
                      callback:(GGApiBlock)aCallback;

//获取功能模块
-(void)getFunctionsAll:(GGApiBlock)aCallback;

@end

#define GGSharedAPI [GGApi sharedApi]

