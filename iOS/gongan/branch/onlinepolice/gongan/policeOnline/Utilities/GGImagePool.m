//
//  GGImagePool.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-18.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGImagePool.h"

@implementation GGImagePool
DEF_SINGLETON(GGImagePool)

- (id)init
{
    self = [super init];
    if (self) {
        _placeholder = [UIImage imageNamed:@"placeholder.png"];
        _stretchShadowBgWite = [[UIImage imageNamed:@"shadowedBgWhite"] resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10)];
        _bgNavibar = [UIImage imageNamed:@"bgNavibar"];
        _mainBgWide = [UIImage imageNamed:@"bg_h1136"];
    }
    return self;
}



@end
