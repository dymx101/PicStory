//
//  GGProvince.h
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGProvince : NSObject

@property (nonatomic, strong) NSMutableArray * provinceArray;

/**
 *  功能:获取所有省份名称
 *  返回:所有省份名称
 */
- (NSArray *)getAllProvinceName;

/**
 *  功能:根据省份id获取省份名称
 *  provinceId:省份id
 *  返回:省份名称
 */
- (NSString *)getProvinceNameFromId:(NSNumber *)provinceId;

/**
 *  功能:根据省份名称获取省份id
 *  provinceName:省份名称
 *  返回:省份id
 */
- (NSNumber *)getProvinceIdFromName:(NSString *)provinceName;

/**
 *  功能:根据省份ID返回省份模块的索引
 *  provinceName:省份名称
 *  返回：模块的索引
 */
- (int) getProvinceModelIndex:(NSNumber *)provinceId;

@end
