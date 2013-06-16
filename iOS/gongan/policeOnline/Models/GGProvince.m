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
    NSArray *allProvinceName = [NSArray arrayWithObjects:OTSSTRING(@"上海"), OTSSTRING(@"北京"), OTSSTRING(@"天津"), OTSSTRING(@"河北"), OTSSTRING(@"江苏"), OTSSTRING(@"浙江"), OTSSTRING(@"重庆"), OTSSTRING(@"内蒙古"), OTSSTRING(@"辽宁"), OTSSTRING(@"吉林"), OTSSTRING(@"黑龙江"), OTSSTRING(@"四川"), OTSSTRING(@"安徽"), OTSSTRING(@"福建"), OTSSTRING(@"江西"), OTSSTRING(@"山东"), OTSSTRING(@"河南"), OTSSTRING(@"湖北"), OTSSTRING(@"湖南"), OTSSTRING(@"广东"), OTSSTRING(@"广西"), OTSSTRING(@"海南"), OTSSTRING(@"贵州"), OTSSTRING(@"云南"), OTSSTRING(@"西藏"), OTSSTRING(@"陕西"), OTSSTRING(@"甘肃"), OTSSTRING(@"青海"), OTSSTRING(@"新疆"), OTSSTRING(@"宁夏"), OTSSTRING(@"台湾"), OTSSTRING(@"山西"), nil];
    
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
    NSString *provinceName = [allProvinceName safeObjectAtIndex:provinceIdValue-1];
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
        return [NSNumber numberWithInt:index+1];
    } else {
        return [NSNumber numberWithInt:1];
    }
}

@end
