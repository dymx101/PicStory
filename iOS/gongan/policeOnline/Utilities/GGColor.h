//
//  OTSColor.h
//  OneStore
//
//  Created by Yim Daniel on 13-1-17.
//  Copyright (c) 2013年 OneStore. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GGDefine.h"

@interface GGColor : NSObject
AS_SINGLETON(GGColor)

-(UIColor *)clear;
-(UIColor *)darkRed;
-(UIColor *)orange;
-(UIColor *)white;
-(UIColor *)black;
-(UIColor *)gray;
-(UIColor *)lightGray;
-(UIColor *)veryLightGray;
-(UIColor *)darkGray;
-(UIColor *)ironGray;
-(UIColor *)bgGray;
-(UIColor *)silver;
-(UIColor *)lightNavy;
@end

#define GGSharedColor [GGColor sharedInstance]
