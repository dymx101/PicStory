//
//  MSAppDelegate.m
//  MedicalStore
//
//  Created by Dong Yiming on 7/13/13.
//  Copyright (c) 2013 Dong Yiming. All rights reserved.
//

#import "MSAppDelegate.h"

#import "AKTabBarController.h"
#import "MSHomeVC.h"
#import "MSCategoryVC.h"
#import "MSCartVC.h"
#import "MSAccountVC.h"

@implementation MSAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    _window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    // If the device is an iPad, we make it taller.
    _tabBarController = [[AKTabBarController alloc] initWithTabBarHeight:(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) ? 70 : 50];
    [_tabBarController setMinimumHeightToDisplayTitle:40.0];

    

    NSMutableArray *vcs = [NSMutableArray array];
    [vcs addObject:[[UINavigationController alloc] initWithRootViewController:[MSHomeVC new]]];
    [vcs addObject:[[UINavigationController alloc] initWithRootViewController:[MSCategoryVC new]]];
    [vcs addObject:[[UINavigationController alloc] initWithRootViewController:[MSCartVC new]]];
    [vcs addObject:[[UINavigationController alloc] initWithRootViewController:[MSAccountVC new]]];
    
    [_tabBarController setViewControllers:vcs];
    
    [[UINavigationBar appearance] setTintColor:[UIColor darkGrayColor]];
    
    _window.rootViewController = _tabBarController;
    [_window makeKeyAndVisible];
    return YES;
    
    
    
    
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
