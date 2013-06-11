//
//  GGUtils.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-9.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGUtils : NSObject

+(CGRect)setX:(float)aX rect:(CGRect)aRect;
+(CGRect)setY:(float)aY rect:(CGRect)aRect;
+(CGRect)setW:(float)aW rect:(CGRect)aRect;
+(CGRect)setH:(float)aH rect:(CGRect)aRect;

+(NSArray *)arrayWithArray:(NSArray *)anArray maxCount:(NSUInteger)aIndex;
//+(void)call:(NSString *)aPhoneNumber;
+(void)call:(NSString *)aPhoneNumber webView:(UIWebView *)phoneCallWebView;

+(NSDate *)NSStringDateToNSDate:(NSString *)anString;
+(NSString *)NSDateToNSStringDate:(NSDate *)anDate;


+(id)safeObjectAtIndex:(int)aIndex inArray:(NSArray*)anArray;
@end
