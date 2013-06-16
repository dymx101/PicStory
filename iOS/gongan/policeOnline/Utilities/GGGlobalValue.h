//
//  GGGlobalValue.h
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGGlobalValue : NSObject

AS_SINGLETON(GGGlobalValue);

@property(nonatomic,retain)NSNumber *provinceId;                //当前省份id
@property(nonatomic,retain)NSString *provinceName;              //当前省份名称
@property(nonatomic,retain)NSString *gpsProvinceName;           //定位到的省份名称
@property(nonatomic,assign)BOOL isFirstLaunch;                  //是否是第一次启动客户端
@property(nonatomic,assign)BOOL isLaunch;                       //是启动客户端还是从后台调起
@property(nonatomic,retain)NSArray *allProvince;                //所有的省份(省份里包含市，市里面包含区)
@end
