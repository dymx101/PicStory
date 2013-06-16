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

@class GGTestVC;

@interface GGAppDelegate : UIResponder <UIApplicationDelegate,BMKGeneralDelegate, RDVTabBarControllerDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) UIViewController *viewController;
@property (strong, nonatomic) UINavigationController *nc;
@property (strong, nonatomic)   RDVTabBarController *tabBarController;
@end

#define GGSharedDelegate ((GGAppDelegate *)[UIApplication sharedApplication].delegate)