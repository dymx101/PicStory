//
//  OTSColor.m
//  OneStore
//
//  Created by Yim Daniel on 13-1-17.
//  Copyright (c) 2013年 OneStore. All rights reserved.
//

#import "GGColor.h"

#define UIColorFromRGB(rgbValue) [UIColor \
colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 \
green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

@implementation GGColor
DEF_SINGLETON(GGColor)

-(UIColor *)darkRed
{
    return UIColorFromRGB(0xAA1E1E);
}

-(UIColor *)orange
{
    return [UIColor orangeColor];
}

-(UIColor *)white
{
    return UIColorFromRGB(0xFFFFFF);
}

-(UIColor *)black
{
    return UIColorFromRGB(0);
}

-(UIColor *)gray
{
    return UIColorFromRGB(0x666666);
}

-(UIColor *)lightGray
{
    return UIColorFromRGB(0x999999);
}

-(UIColor *)veryLightGray
{
    return UIColorFromRGB(0xcccccc);
}

-(UIColor *)silver
{
    return UIColorFromRGB(0xE5E5E5);
}

-(UIColor *)darkGray
{
    return UIColorFromRGB(0x333333);
}

-(UIColor *)ironGray
{
    return UIColorFromRGB(0x393939);
}

-(UIColor *)bgGray
{
    return UIColorFromRGB(0x343434);
}

-(UIColor *)clear
{
    return [UIColor clearColor];
}

-(UIColor *)lightNavy
{
    return [UIColor
            colorWithRed:78.0/255.0
            green:156.0/255.0
            blue:206.0/255.0
            alpha:1.0];//UIColorFromRGB(0x317DD9);
}

@end
