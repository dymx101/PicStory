//
//  GGPhoneMask.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGPhoneMask.h"
#import "GGColor.h"
#import "GGDefine.h"
#import "GGAppDelegate.h"

@interface GGPhoneMask()
@property(nonatomic, strong) UIViewController *maskVC;//面具view controller
@property(nonatomic, assign) BOOL showing;//是否正在显示
@end

@implementation GGPhoneMask
DEF_SINGLETON(GGPhoneMask)

/**
 *  功能:往tabbar view上添加一个view controller
 */
- (void)addMaskVC:(UIViewController *)aVC animated:(BOOL)aAnimated alpha:(CGFloat)aAlpha
{
    if (self.showing) {
        return;
    }
    self.showing = YES;
    
    if (self.maskVC != nil) {
        return;
    }
    self.maskVC = aVC;
    
    UIView *view = aVC.view;
    view.backgroundColor = [GGSharedColor clear];
    view.opaque = NO;
    view.alpha = aAlpha;
    [view.subviews enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        UIView *each = obj;
        each.opaque = NO;
        each.alpha = aAlpha;
    }];
    
    if (aAnimated) {
        //Animated
        CGRect mainRect = [[UIScreen mainScreen] bounds];
        CGRect startRect = CGRectMake(0, mainRect.size.height, mainRect.size.width, mainRect.size.height);
      //[GGSharedTabBar.view addSubview:view];
        //暂时没有 TabBar 可以 把 mask 加在 window 上面
        [GGSharedDelegate.window addSubview:view];
        view.frame = startRect;
        
        [UIView animateWithDuration:0.3 animations:^{
            view.frame = mainRect;
        } completion:^(BOOL finished) {
            //nop
        }];
    } else{
        view.frame = [[UIScreen mainScreen] bounds];
      //[GGSharedTabBar.view addSubview:view];
        //暂时没有 TabBar 可以 把 mask 加在 window 上面
        [GGSharedDelegate.window addSubview:view];
    }
}

/**
 *  功能:移除tabbar view上添加的view controller
 */
- (void)dismissMaskVCAnimated:(BOOL)aAnimated
{
    self.showing = NO;
    CGRect mainRect = [[UIScreen mainScreen] bounds];
    CGRect endRect = CGRectMake(0, mainRect.size.height, mainRect.size.width, mainRect.size.height);
    if (aAnimated) {
        [UIView animateWithDuration:0.3 animations:^{
            self.maskVC.view.frame = endRect;
        } completion:^(BOOL finished) {
            [self.maskVC.view removeFromSuperview];
            self.maskVC = nil;
        }];
    } else {
        self.maskVC.view.frame = endRect;
        [self.maskVC.view removeFromSuperview];
        self.maskVC = nil;
    }
}

@end
