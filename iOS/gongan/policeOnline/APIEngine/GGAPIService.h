//
//  GGAPIService.h
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GGVersionInfo.h"
#import "GGPoliceman.h"
#import "GGWanted.h"

@interface GGAPIService : NSObject
AS_SINGLETON(GGAPIService)


/**
 * <h2>检查升级的版本号</h2>
 * <br/>
 * @param currentVersion             当前版本号
 * @param aCompletion:GGVersionInfo  回调更新UI
 * @return
 */
-(void) CheckUpdateWithCurrentVersion:(NSString *) currentversion aCompletion:(void(^)(GGVersionInfo * info))aCompletion;


/**
 * <h2>删除sqlite中警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)deletePolicemanFromDB:(GGPoliceman *)man aCompletion:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>删除sqlite中警员信息</h2>
 * <br/>
* @param aCompletion  回调更新UI
 * @return
 */
-(void)deletePolicemanFromDB:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>获取sqlite中警员信息</h2>
 * <br/>
 * @return
 */
-(NSArray *)getAllPolicemanFromDB;

/**
 * <h2>加载sqlite中警员信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)loadALLPolicemanFromDB:(void(^)(NSArray * arr))aCompletion;

/**
 * <h2>向sqlite中插入警员信息,收藏警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)insertPolicemanToDB:(GGPoliceman *)man aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>删除 sqlite 警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)delPolicemanFromDB:(GGPoliceman *)man aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>获取root悬赏信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedRootCategory:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>获取root悬赏信息</h2>
 * @param anAreaID  区域id
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedRootCategoryWithAreaID:(long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion;
/**
 * <h2>获取次级悬赏信息类别</h2>
 * <br/>
 * @param subCategoryID 类别ID
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedSubCategory:(long long)subCategoryID aCompletion:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>获取民警片区信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getArea:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>获取民警片区信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getAreaById:(long long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>获取民警片区信息</h2>
 * <br/>
 * @param anAreaID     民警片区ID
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getPolicemanByAreaID:(long long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>搜索警务片区</h2>
 * <br/>
 * @param Keyword      搜索关键字
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)searchArea:(NSString *) Keyword aCompletion:(void(^)(NSMutableArray * arr))aCompletion;

/**
 * <h2>判断是否收藏了警员</h2>
 * <br/>
 * @param Keyword      搜索关键字
 * @return
 */
-(void)hasPolicemanWithID:(long long)aPolicemanID
              aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>插入本地通缉令信息</h2>
 * <br/>
 * @param (GGWanted *)aWanted 
 * @return
 */
-(void)insertWanted:(GGWanted *)aWanted aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>删除本地通缉令信息</h2>
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)deleteWantedByID:(long long)aWantedID aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>获取本地通缉令信息</h2>
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)getAllWanted:(void(^)(NSArray * arr))aCompletion;

/**
 * 
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)hasWantedWithID:(long long)aWantedID aCompletion:(void(^)(BOOL success))aCompletion;

/**
 * <h2>获取程序支持的区域信息</h2>
 * <br/>
 * @return
 */
-(void)getLocateAreas:(void(^)(NSArray * arr))aCompletion;

/**
 * <h2>获取区域－模块对应信息</h2>
 * <br/>
 * @return
 */
-(void)getFunctionsAll:(void(^)(NSArray * arr))aCompletion;


/**
 *<h2>警员评价</h2>
 * @param (long long)plId
 * @param (int)evaluate
 * @
 */
-(void)addPoliceEvaluateWithPolice:(long long)plId
                          evaluate:(int)evaluate
                            unitId:(long)unitId
                       aCompletion:(void(^)(long flag))aCompletion;


@end
