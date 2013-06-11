//
//  GGAPIService.m
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGAPIService.h"
#import "GGDbManager.h"
#import "SBJson.h"
#import "GGArea.h"
#import "GGPoliceman.h"
#import "GGWantedCategory.h"
#import "GGVersionInfo.h"
#import "GGWanted.h"

@implementation GGAPIService
{
    NSMutableArray  *_policemans;
    NSMutableArray  *_areas;
    NSMutableArray  *_wantedCategorys;
    NSMutableArray  *_wanteds;
    GGVersionInfo   *_verson;
}

DEF_SINGLETON(GGAPIService)

- (id)init
{
    self = [super init];
    if (self) {
        _policemans = [NSMutableArray array];
        _areas = [NSMutableArray array];
        _wantedCategorys = [NSMutableArray array];
        _wanteds  = [NSMutableArray array];
    }
    return self;
}

/**
 * <h2>检查升级的版本号</h2>
 * <br/>
 * @param currentVersion             当前版本号
 * @param aCompletion:GGVersionInfo  回调更新UI
 * @return
 */
-(void) CheckUpdateWithCurrentVersion:(NSString *) currentversion aCompletion:(void(^)(GGVersionInfo * info))aCompletion
{
    [GGSharedAPI checkUpdateWithCurrentVersion:currentversion callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        _verson = [parser parseGetVersionInfo];
        if (aCompletion) {
            aCompletion(_verson);
        }
    }];
}

/**
 * <h2>删除sqlite中警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)deletePolicemanFromDB:(GGPoliceman *)man aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [[GGDbManager sharedInstance] deletePolicemanByID:man.ID];
    
    //刷新 arr
    NSMutableArray *arr = [self getAllPolicemanFromDB];
    if (aCompletion) {
        aCompletion(arr);
    }
}

/**
 * <h2>删除sqlite中警员信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)deletePolicemanFromDB:(void(^)(NSMutableArray * arr))aCompletion

{
    for (GGPoliceman *man in _policemans) {
        [[GGDbManager sharedInstance] deletePolicemanByID:man.ID];
    }
    //刷新 arr
    NSMutableArray *arr = [self getAllPolicemanFromDB];
    if (aCompletion) {
        aCompletion(arr);
    }
}

/**
 * <h2>获取sqlite中警员信息</h2>
 * <br/>
 * @return
 */
-(NSMutableArray *)getAllPolicemanFromDB 
{
    NSArray *policemans = [[GGDbManager sharedInstance] getAllPolicemans];
    
    [_policemans removeAllObjects];
    for (GGPoliceman *policeman in policemans) {
        DLog(@"area -- id:%lld, name:%@", policeman.ID, policeman.name);
        [_policemans addObject:policeman];
    }
    return _policemans;
}

/**
 * <h2>加载sqlite中警员信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)loadALLPolicemanFromDB:(void(^)(NSMutableArray * arr))aCompletion
{
    NSMutableArray *arr = [self getAllPolicemanFromDB];
    aCompletion(arr);
}

/**
 * <h2>向sqlite中插入警员信息,收藏警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)insertPolicemanToDB:(GGPoliceman *)man aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success =  [[GGDbManager sharedInstance] insertPoliceman:man];
    if (aCompletion) {
        aCompletion(success);
    }
}

/**
 * <h2>删除 sqlite 警员信息</h2>
 * <br/>
 * @param GGPoliceman  警员信息
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)delPolicemanFromDB:(GGPoliceman *)man aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success =  [[GGDbManager sharedInstance] deletePolicemanByID:man.ID];
    if (aCompletion) {
        aCompletion(success);
    }
}

/**
 * <h2>向sqlite中插入警员信息</h2>
 * <br/>
 * @return
 */
-(void)insertPolicemanToDB
{
    for (GGPoliceman *man in _policemans) {
        [[GGDbManager sharedInstance] insertPoliceman:man];
    }
}

/**
 * <h2>获取root悬赏信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedRootCategory:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getWantedRootCategory:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetWantedRootCategory];
        DLog(@"%@", arr);
        [_wantedCategorys removeAllObjects];
        for (GGWantedCategory *data in arr) {
            DLog(@"area -- id:%lld, name:%@", data.ID, data.name);
            [_wantedCategorys addObject:data];
        }
        if (aCompletion) {
            aCompletion(_wantedCategorys);
        }
    }];
}

/**
 * <h2>获取次级悬赏信息类别</h2>
 * <br/>
 * @param subCategoryID 类别ID
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedSubCategory:(long long)subCategoryID aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getWantedSubCategoryWithID:subCategoryID callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *array = [parser parseGetWantedRootCategory];
        DLog(@"%@", array);
        if (parser.typeID == 0)
        {
            [_wantedCategorys removeAllObjects];
            for (GGWantedCategory *data in array) {
                DLog(@"area -- id:%lld, name:%@", data.ID, data.name);
                [_wantedCategorys addObject:data];
                [array arrayByAddingObjectsFromArray:_wantedCategorys];
            }
        }
        else
        {
            [_wanteds removeAllObjects];
            for (GGWanted *data in array) {
                DLog(@"area -- id:%lld, name:%@", data.ID, data.title);
                [_wanteds addObject:data];
                [array arrayByAddingObjectsFromArray:_wanteds];
            }
        }
        if (aCompletion) {
            aCompletion(array);
        }
    }];
}

/**
 * <h2>获取民警片区信息</h2>
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getArea:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getAreas:^(id operation, id aResultObject, NSError *anError) {
        
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        [_areas removeAllObjects];
        for (GGArea *area in arr) {
            DLog(@"area -- id:%lld, address:%@", area.ID, area.address);
            [_areas addObject:area];
        }
        if (aCompletion) {
            aCompletion(_areas);
        }
    }];
}

/**
 * <h2>获取民警片区信息</h2>
 * <br/>
 * @param anAreaID     民警片区ID
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getPolicemanByAreaID:(long long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getPolicemanByAreaID:anAreaID callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetPoliceman];
        DLog(@"%@", arr);
        [_policemans removeAllObjects];
        for (GGPoliceman *policeman in arr) {
            DLog(@"area -- id:%lld, name:%@", policeman.ID, policeman.name);
            [_policemans addObject:policeman];
        }
        if (aCompletion) {
            aCompletion(_policemans);
        }
    }];
}

/**
 * <h2>搜索警务片区</h2>
 * <br/>
 * @param Keyword      搜索关键字
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)searchArea:(NSString *) Keyword aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI searchAreaByKeyword:Keyword callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        [_areas removeAllObjects];
        for (GGArea *area in arr) {
            DLog(@"area -- id:%lld, address:%@", area.ID, area.address);
            [_areas addObject:area];
        }
        if (aCompletion) {
            aCompletion(_areas);
        }
    }];
}
@end
