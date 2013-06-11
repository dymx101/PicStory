//
//  GGUtils.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-9.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGUtils.h"

@implementation GGUtils

+(CGRect)setX:(float)aX rect:(CGRect)aRect
{
    aRect.origin.x = aX;
    return aRect;
}

+(CGRect)setY:(float)aY rect:(CGRect)aRect
{
    aRect.origin.y = aY;
    return aRect;
}

+(CGRect)setW:(float)aW rect:(CGRect)aRect
{
    aRect.size.width = aW;
    return aRect;
}

+(CGRect)setH:(float)aH rect:(CGRect)aRect
{
    aRect.size.height = aH;
    return aRect;
}

+(UIInterfaceOrientation)interfaceOrientation
{
    return [[UIApplication sharedApplication] statusBarOrientation];
}

+(UIDeviceOrientation)deviceOrientation
{
    return [[UIDevice currentDevice] orientation];
}

+(NSArray *)arrayWithArray:(NSArray *)anArray maxCount:(NSUInteger)aIndex
{
    NSMutableArray *returnedArray = nil;
    
    if (anArray.count && aIndex)
    {
        int count = 0;
        returnedArray = [NSMutableArray array];
        for (id item in anArray)
        {
            [returnedArray addObject:item];
            count++;
            if (count > aIndex - 1)
            {
                break;
            }
        }
    }
    
    return returnedArray;
}

+(void)call:(NSString *)aPhoneNumber webView:(UIWebView *)phoneCallWebView
{

    NSString *cleanedString = [[aPhoneNumber componentsSeparatedByCharactersInSet:[[NSCharacterSet characterSetWithCharactersInString:@"0123456789-+()"] invertedSet]] componentsJoinedByString:@""];
    NSURL *phoneURL = [NSURL URLWithString:[NSString stringWithFormat:@"tel:%@", cleanedString]];

    [phoneCallWebView loadRequest:[NSURLRequest requestWithURL:phoneURL]];
    
//    [[UIApplication sharedApplication] openURL:telURL]; 
}

// date
+(NSDate *)NSStringDateToNSDate:(NSString *)anString
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    
    [dateFormatter setDateFormat:@"yyyy/MM/dd"];
    
    NSDate *date = [dateFormatter dateFromString:anString];
    
    
    return date;
}


+(NSString *)NSDateToNSStringDate:(NSDate *)anDate
{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    
    //#towne 对于格式 2013-01-19 23:59:59 截取成 2013－01－19 并且这里需要做时区的判定
    NSDate *date = [NSDate date];
    
    NSTimeZone *zone = [NSTimeZone systemTimeZone];
    
    NSInteger interval = [zone secondsFromGMTForDate: date];
    
    anDate= [anDate  dateByAddingTimeInterval: -interval];
    
    [dateFormatter setDateFormat:@"yyyy/MM/dd HH:mm:ss"];
    
    NSString *anString = [dateFormatter stringFromDate:anDate];
    
    anString = [anString substringToIndex:10];
    
    return anString;
    
}


+(id)safeObjectAtIndex:(int)aIndex inArray:(NSArray*)anArray
{
    if (anArray && aIndex >=0 && aIndex < [anArray count])
    {
        return [anArray objectAtIndex:aIndex];
    }
    
    return nil;
}
@end
