//
//  GGUserDefault.h
//  policeOnline
//
//  Created by Dong Yiming on 6/23/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGUserDefault : NSObject


+(BOOL)reportMyLocation;
+(BOOL)reportMyPhone;
+(BOOL)reportMyName;

+(NSString *)myName;
+(NSString *)myPhone;

+(void)saveReportMyLocation:(BOOL)aReport;
+(void)saveReportMyPhone:(BOOL)aReport;
+(void)saveReportMyName:(BOOL)aReport;
+(void)saveMyName:(NSString *)aName;
+(void)saveMyPhone:(NSString *)aPhone;

@end
