//
//  GGDataModel.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGDataModel.h"

@implementation GGDataModel
+(id)model
{
    return  [[self alloc] init];
}

-(void)parseWithData:(NSDictionary *)aData
{
    if (aData)
    {
        NSAssert([aData isKindOfClass:[NSDictionary class]], @"data must be a dictionary.");
    }
    // to be implemented in subclass
}

-(NSString *)intervalStringWithDate:(long long)aDate
{
    NSDate *date = [NSDate dateWithTimeIntervalSince1970:aDate / 1000];
    long long interval = -[date timeIntervalSinceNow];
    int days = interval / (3600 * 24);
    long secondsInADay = interval % (3600 * 24);
    
    if (days < 1)
    {
        int hours = secondsInADay / 3600;
        if (hours > 0)
        {
            return [NSString stringWithFormat:@"%dh ago", hours];
        }
        else
        {
            return @"just now";
        }
    }
    else if (days < 31)
    {
        return [NSString stringWithFormat:@"%dd ago", days];
    }
    else
    {
        int months = days / 31;
        return [NSString stringWithFormat:@"%dm ago", months];
    }
    
    return nil;
}
@end
