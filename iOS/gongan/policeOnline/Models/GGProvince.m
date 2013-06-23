//
//  GGProvince.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGProvince.h"
#import "NSArray+safe.h"

@implementation GGProvince

/**
 *  功能:获取所有省份名称
 *  返回:所有省份名称
 */
+ (NSArray *)getAllProvinceName
{
    
    NSArray *allProvinceName = [NSArray arrayWithObjects:OTSSTRING(@"武汉"), OTSSTRING(@"老河口"), OTSSTRING(@"襄阳"), OTSSTRING(@"孝感"), OTSSTRING(@"宜昌"), OTSSTRING(@"荆州"), OTSSTRING(@"十堰"), OTSSTRING(@"黄石"), OTSSTRING(@"黄冈"), OTSSTRING(@"马口"), nil];
     //       0       1       2           3       4       5       6       7       8       9
    
    return allProvinceName;
}

/**
 *  功能:根据省份id获取省份名称
 *  provinceId:省份id
 *  返回:省份名称
 */
+ (NSString *)getProvinceNameFromId:(NSNumber *)provinceId
{
    int provinceIdValue = [provinceId intValue];
    NSArray *allProvinceName = [self getAllProvinceName];
    NSString *provinceName = [allProvinceName safeObjectAtIndex:provinceIdValue];
    return provinceName;
}

/**
 *  功能:根据省份名称获取省份id
 *  provinceName:省份名称
 *  返回:省份id
 */
+ (NSNumber *)getProvinceIdFromName:(NSString *)provinceName
{
    NSArray *allProvinceName = [self getAllProvinceName];
    int index = [allProvinceName indexOfObject:provinceName];
    if (index != NSNotFound) {
        return [NSNumber numberWithInt:index];
    } else {
        return [NSNumber numberWithInt:0];
    }
}

@end
