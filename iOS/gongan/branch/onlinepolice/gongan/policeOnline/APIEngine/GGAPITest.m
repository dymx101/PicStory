//
//  GGAPITest.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-8.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGAPITest.h"
#import "SBJson.h"
#import "GGArea.h"
#import "GGPoliceman.h"
#import "GGWantedCategory.h"
#import "GGDbManager.h"
#import "GGVersionInfo.h"
#import "GGWanted.h"

@implementation GGAPITest
{
    NSMutableArray  *_policemans;
}
DEF_SINGLETON(GGAPITest)

- (id)init
{
    self = [super init];
    if (self) {
        _policemans = [NSMutableArray array];
    }
    return self;
}

-(void)run
{
//    [self _testGetWantedRootCategory];
    [self _testGetWantedSubCategory];
}

-(void)_testCheckUpdateWithCurrentVersion
{
    [GGSharedAPI checkUpdateWithCurrentVersion:@"1.2" callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        GGVersionInfo * verson = [parser parseGetVersionInfo];
        DLog(@"%@", verson);
    }];
}

-(void)_testOperatePolicemanDB
{
    [self _testGetPoliceman:^{
        [self _testInsertPolicemanToDB];
        [self _testGetAllPolicemanFromDB];
        [self _testDeletePolicemanFromDB];
        [self _testGetAllPolicemanFromDB];
    }];
}

-(void)_testDeletePolicemanFromDB
{
    for (GGPoliceman *man in _policemans) {
        [[GGDbManager sharedInstance] deletePolicemanByID:man.ID];
    }
}

-(void)_testGetAllPolicemanFromDB
{
    NSArray *policemans = [[GGDbManager sharedInstance] getAllPolicemans];
    
    [_policemans removeAllObjects];
    for (GGPoliceman *policeman in policemans) {
        DLog(@"area -- id:%lld, name:%@", policeman.ID, policeman.name);
        [_policemans addObject:policeman];
    }
}

-(void)_testInsertPolicemanToDB
{
    for (GGPoliceman *man in _policemans) {
        [[GGDbManager sharedInstance] insertPoliceman:man];
    }
}

-(void)_testGetWantedSubCategory
{
    [GGSharedAPI getWantedSubCategoryWithID:111 callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetWantedRootCategory];
        DLog(@"%@", arr);
        if (parser.typeID == 0)
        {
            for (GGWantedCategory *data in arr) {
                DLog(@"area -- id:%lld, name:%@", data.ID, data.name);
            }
        }
        else
        {
            for (GGWanted *data in arr) {
                DLog(@"area -- id:%lld, name:%@", data.ID, data.title);
            }
        }
    }];
}

-(void)_testGetWantedRootCategory
{
    [GGSharedAPI getWantedRootCategory:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetWantedRootCategory];
        DLog(@"%@", arr);
        for (GGWantedCategory *data in arr) {
            DLog(@"area -- id:%lld, name:%@", data.ID, data.name);
            
        }
    }];
}

-(void)_testGetAreas:(void(^)(void))aCompletion
{
    [GGSharedAPI getAreas:^(id operation, id aResultObject, NSError *anError) {
        
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        for (GGArea *area in arr) {
            DLog(@"area -- id:%lld, address:%@", area.ID, area.address);
        }
    }];
}

-(void)_testGetPoliceman:(void(^)(void))aCompletion
{
    [GGSharedAPI getPolicemanByAreaID:39 callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetPoliceman];
        DLog(@"%@", arr);
        
        [_policemans removeAllObjects];
        for (GGPoliceman *policeman in arr) {
            DLog(@"area -- id:%lld, name:%@", policeman.ID, policeman.name);
            [_policemans addObject:policeman];
        }
        
        if (aCompletion) {
            aCompletion();
        }
    }];
}

-(void)_testSearchArea
{
    [GGSharedAPI searchAreaByKeyword:@"江" callback:^(id operation, id aResultObject, NSError *anError) {
        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
        NSMutableArray *arr = [parser parseGetAreas];
        DLog(@"%@", arr);
        for (GGArea *area in arr) {
            DLog(@"area -- id:%lld, address:%@", area.ID, area.address);
        }
    }];
}

@end
