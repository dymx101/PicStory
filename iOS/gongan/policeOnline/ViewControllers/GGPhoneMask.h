//
//  GGPhoneMask.h
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGPhoneMask : NSObject
AS_SINGLETON(GGPhoneMask)

/**
 *  功能:往tabbar view上添加一个view controller
 */
- (void)addMaskVC:(UIViewController *)aVC animated:(BOOL)aAnimated alpha:(CGFloat)aAlpha;

/**
 *  功能:移除tabbar view上添加的view controller
 */
- (void)dismissMaskVCAnimated:(BOOL)aAnimated;



@end
