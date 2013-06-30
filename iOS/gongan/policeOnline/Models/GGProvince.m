//
//  GGProvince.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGProvince.h"
#import "NSArray+safe.h"
#import "GGLocateArea.h"
#import "GGAPIService.h"
#import "GGGlobalValue.h"
#import "GGAreaFunction.h"

@implementation GGProvince

/**
 *  功能:获取所有省份名称
 *  返回:所有省份名称
 */
-(NSArray *)getAllProvinceName
{
    
    return _provinceArray;
}

/**
 *  功能:根据省份id获取省份名称
 *  provinceId:省份id
 *  返回:省份名称
 */
- (NSString *)getProvinceNameFromId:(NSNumber *)provinceId
{
    NSString *provinceName = @"";
    int provinceIdValue = [provinceId intValue];
    NSArray * locations  = [GGGlobalValue sharedInstance].locations;
    for(GGLocateArea * location in locations)
    {
        if ([location.areaId intValue] == provinceIdValue) {
            provinceName = location.address;
            break;
        }
    }
    return provinceName;
}

/**
 *  功能:根据省份名称获取省份id
 *  provinceName:省份名称
 *  返回:省份id
 */
- (NSNumber *)getProvinceIdFromName:(NSString *)provinceName
{
    NSNumber * provinceIdValue = [NSNumber numberWithInt:2];
    NSArray * locations  = [GGGlobalValue sharedInstance].locations;
    for(GGLocateArea * location in locations)
    {
        if ([location.address isEqualToString:provinceName]) {
            provinceIdValue = location.areaId;
            break;
        }
    }
    return  provinceIdValue;
}

/**
 *  功能:根据省份ID返回省份模块的索引
 *  provinceName:省份名称
 *  返回：模块的索引
 */
- (int) getProvinceModelIndex:(NSNumber *)provinceId
{
    int modIdex = 0 ; // areafunctions 中的 模块索引
    NSArray * areafunctions  = [GGGlobalValue sharedInstance].areafunctions;
    for (int k = 0; k < [areafunctions count]; k++) {
        GGAreaFunction * areafunction = [areafunctions objectAtIndex:k];
        if ([areafunction.areaId intValue] == [provinceId intValue]) {
            modIdex = k;
            break;
        }
    }
    return  modIdex;
}

@end
