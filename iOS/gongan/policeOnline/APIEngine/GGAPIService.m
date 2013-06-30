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
#import "GGLocateArea.h"
#import "GGAreaFunction.h"
#import "GGClue.h"

@implementation GGAPIService
{
    NSMutableArray  *_policemans;
    NSMutableArray  *_areas;
    NSMutableArray  *_wantedCategorys;
    NSMutableArray  *_wanteds;
    NSMutableArray  *_localAreas;
    NSMutableArray  *_areaFunctions;
    NSMutableArray  *_clues;
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
        _localAreas = [NSMutableArray array];
        _areaFunctions = [NSMutableArray array];
        _clues = [NSMutableArray array];
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
    NSMutableArray *arr = [NSMutableArray arrayWithArray:[self getAllPolicemanFromDB]];
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
    NSMutableArray *arr = [NSMutableArray arrayWithArray:[self getAllPolicemanFromDB]];
    if (aCompletion) {
        aCompletion(arr);
    }
}

/**
 * <h2>获取sqlite中警员信息</h2>
 * <br/>
 * @return
 */
-(NSArray *)getAllPolicemanFromDB
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
-(void)loadALLPolicemanFromDB:(void(^)(NSArray * arr))aCompletion
{
    NSArray *arr = [self getAllPolicemanFromDB];
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
        if (arr == nil) {
            _wantedCategorys = nil;
        }
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
 * <h2>获取root悬赏信息</h2>
 * @param anAreaID  区域id
 * <br/>
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getWantedRootCategoryWithAreaID:(long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getWantedRootCategoryWithAreaID:anAreaID callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetWantedRootCategory];
        DLog(@"%@", arr);
        if (arr == nil) {
            _wantedCategorys = nil;
        }
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
        if (array == nil) {
            _wantedCategorys = nil;
        }
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
        if (arr == nil) {
            _areas = nil;
        }
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
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)getAreaById:(long long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getPolicemanByAreaID:anAreaID callback:^(id operation, id aResultObject, NSError *anError) {
        
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        if (arr == nil) {
            _areas = nil;
        }
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
 * <h2>获取民警信息</h2>
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
        if (arr == nil) {
            _policemans = nil;
        }
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
        if (arr == nil) {
            _areas = nil;
        }
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
 * <h2>搜索警务片区</h2>
 * <br/>
 * @param Keyword      搜索关键字
 * @param anAreaID     搜索的地区
 * @param aCompletion  回调更新UI
 * @return
 */
-(void)searchArea:(NSString *) Keyword AreaID:(long long)anAreaID aCompletion:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI searchAreaByKeyword:Keyword AreaID:anAreaID callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        if (arr == nil) {
            _areas = nil;
        }
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
 * <h2>判断是否收藏了警员</h2>
 * <br/>
 * @param Keyword      搜索关键字
 * @return
 */
-(void)hasPolicemanWithID:(long long)aPolicemanID
              aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success = [[GGDbManager sharedInstance] hasPolicemanWithID:aPolicemanID];
    if (aCompletion) {
        aCompletion(success);
    }
    
}

/**
 * <h2>插入本地通缉令信息</h2>
 * <br/>
 * @param (GGWanted *)aWanted
 * @return
 */
-(void)insertWanted:(GGWanted *)aWanted aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success = [[GGDbManager sharedInstance] insertWanted:aWanted];
    if (aCompletion) {
        aCompletion(success);
    }
}

/**
 * <h2>删除本地通缉令信息</h2>
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)deleteWantedByID:(long long)aWantedID aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success = [[GGDbManager sharedInstance] deleteWantedByID:aWantedID];
    if (aCompletion) {
        aCompletion(success);
    }
}

/**
 * <h2>获取本地通缉令信息</h2>
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)getAllWanted:(void(^)(NSArray * arr))aCompletion
{
    NSArray *arr = [[GGDbManager sharedInstance] getAllWanted];
    aCompletion(arr);
}

/**
 * <h2>判断是否为本地已有的通缉令信息</h2>
 * <br/>
 * @param (long long)aWantedID
 * @return
 */
-(void)hasWantedWithID:(long long)aWantedID aCompletion:(void(^)(BOOL success))aCompletion
{
    BOOL success = [[GGDbManager sharedInstance] hasWantedWithID:aWantedID];
    if (aCompletion) {
        aCompletion(success);
    }
}

/**
 * <h2>获取区域信息</h2>
 * <br/>
 * @return
 */
-(void)getLocateAreas:(void(^)(NSArray * arr))aCompletion
{
    [GGSharedAPI getLocateAreas:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetLocateArea];
        DLog(@"%@", arr);
        if (arr == nil) {
            _localAreas = nil;
        }
        [_localAreas removeAllObjects];
        for (GGLocateArea *locatearea in arr) {
            [_localAreas addObject:locatearea];
        }
        if (aCompletion) {
            aCompletion(_localAreas);
        }
    }];
}

/**
 * <h2>获取区域－模块对应信息</h2>
 * <br/>
 * @return
 */
-(void)getFunctionsAll:(void(^)(NSArray * arr))aCompletion
{
    [GGSharedAPI getFunctionsAll:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreaFunction];
        DLog(@"%@", arr);
        if (arr == nil) {
            _areaFunctions = nil;
        }
        [_areaFunctions removeAllObjects];
        for (GGAreaFunction * areafunction in arr) {
            [_areaFunctions addObject:areafunction];
        }
        if (aCompletion) {
            aCompletion(_areaFunctions);
        }
    }];
}

/**
 *<h2>警员评价</h2>
 * @param (long long)plId
 * @param (int)evaluate
 * @
 */
-(void)addPoliceEvaluateWithPolice:(long long)plId
                          evaluate:(int)evaluate
                            unitId:(long)unitId
                       aCompletion:(void(^)(long flag))aCompletion
{
    __block long flag;
    [GGSharedAPI addPoliceEvaluateWithPolice:plId evaluate:evaluate unitId:unitId callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        flag = [[[parser apiData] objectForKey:@"flag"] longValue];
        if (aCompletion) {
            aCompletion(flag);
        }
    }];
}

/**
 *<h2>获取线索征集列表</h2>
 */
-(void)getCluesRoot:(void(^)(NSMutableArray * arr))aCompletion
{
    [GGSharedAPI getCluesRoot:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *anArray = [parser parseGetClues];
        NSLog(@"%@",anArray);
        if (anArray == nil) {
            _clues = nil;
        }
        [_clues removeAllObjects];
        for (GGClue * data in anArray) {
            DLog(@"clues -- id:%lld, name:%@", data.ID, data.title);
            [_clues addObject:data];
        }
        if (aCompletion) {
            aCompletion(_clues);
        }
    }];
}

@end
