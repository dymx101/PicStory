//
//  OTSAlert.h
//  OneStore
//
//  Created by Yim Daniel on 13-1-16.
//  Copyright (c) 2013年 OneStore. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGAlert : NSObject

+(void)alert:(NSString *)aMessage;
+(void)alertNetError;
+(void)alertCancelOK:(NSString *)aMessage delegate:(id)aDelegate;
+(void)alertCancelOK:(NSString *)aMessage  title:(NSString *)aTitle  delegate:(id)aDelegate;
//+(void)alertErrorForParser:(GGApiParser *)aParser;
@end
