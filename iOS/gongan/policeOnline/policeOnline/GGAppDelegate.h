//
//  GGAppDelegate.h
//  policeOnline
//
//  Created by dong yiming on 13-4-27.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"
#import "RDVTabBarController.h"
#import "MMDrawerController.h"

@class GGTestVC;

@interface GGAppDelegate : UIResponder <UIApplicationDelegate,BMKGeneralDelegate, RDVTabBarControllerDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UIViewController *viewController;
@property (strong, nonatomic) UINavigationController *nc;
@property (strong, nonatomic)   RDVTabBarController *tabBarController;
@property (strong, nonatomic) MMDrawerController *drawerVC;
@end

#define GGSharedDelegate ((GGAppDelegate *)[UIApplication sharedApplication].delegate)

#define GGSharedTabBar    (((GGAppDelegate*)[UIApplication sharedApplication].delegate).tabBarController)