//
//  GGUserDefault.m
//  policeOnline
//
//  Created by Dong Yiming on 6/23/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGUserDefault.h"

#define KEY_REPROT_MY_LOCATION    @"KEY_REPROT_MY_LOCATION"
#define KEY_REPROT_MY_NAME    @"KEY_REPROT_MY_NAME"
#define KEY_REPROT_MY_PHONE    @"KEY_REPROT_MY_PHONE"

#define KEY_MY_PHONE    @"KEY_MY_PHONE"
#define KEY_MY_NAME    @"KEY_MY_NAME"

@implementation GGUserDefault

+(BOOL)reportMyLocation
{
    return [[NSUserDefaults standardUserDefaults] boolForKey:KEY_REPROT_MY_LOCATION];
}

+(BOOL)reportMyPhone
{
    return [[NSUserDefaults standardUserDefaults] boolForKey:KEY_REPROT_MY_PHONE];
}

+(BOOL)reportMyName
{
    return [[NSUserDefaults standardUserDefaults] boolForKey:KEY_REPROT_MY_NAME];
}

+(NSString *)myName
{
    return [[NSUserDefaults standardUserDefaults] stringForKey:KEY_MY_NAME];
}

+(NSString *)myPhone
{
    return [[NSUserDefaults standardUserDefaults] stringForKey:KEY_MY_PHONE];
}

+(void)saveReportMyLocation:(BOOL)aReport
{
    [[NSUserDefaults standardUserDefaults] setBool:aReport forKey:KEY_REPROT_MY_LOCATION];
}

+(void)saveReportMyPhone:(BOOL)aReport
{
    [[NSUserDefaults standardUserDefaults] setBool:aReport forKey:KEY_REPROT_MY_PHONE];
}

+(void)saveReportMyName:(BOOL)aReport
{
    [[NSUserDefaults standardUserDefaults] setBool:aReport forKey:KEY_REPROT_MY_NAME];
}

+(void)saveMyName:(NSString *)aName
{
    [[NSUserDefaults standardUserDefaults] setObject:(aName ? aName : @"") forKey:KEY_MY_NAME];
}

+(void)saveMyPhone:(NSString *)aPhone
{
    [[NSUserDefaults standardUserDefaults] setObject:(aPhone ? aPhone : @"") forKey:KEY_MY_PHONE];
}

@end
