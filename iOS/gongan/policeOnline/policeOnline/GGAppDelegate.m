//
//  GGAppDelegate.m
//  policeOnline
//
//  Created by dong yiming on 13-4-27.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGAppDelegate.h"
#import "GGMainVC.h"
#import "GGAPIService.h"
#import "GGVersionInfo.h"

#import "GGTestVC.h"

BMKMapManager* _mapManager;
@implementation GGAppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // 要使用百度地图，请先启动BaiduMapManager
	_mapManager = [[BMKMapManager alloc]init];
	BOOL ret = [_mapManager start:@"" generalDelegate:self];
	if (!ret) {
		NSLog(@"manager start failed!");
	}
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    self.window = [[UIWindow alloc] initWithFrame:screenBounds];
    
#if 0       // for test
    self.viewController = [[GGTestVC alloc] initWithNibName:@"GGTestVC" bundle:nil];
#else       // for real
    self.viewController = [[GGMainVC alloc] initWithNibName:@"GGMainVC" bundle:nil];
#endif
    _nc = [[UINavigationController alloc] initWithRootViewController:_viewController];
    self.window.rootViewController = _nc;
    
    [_nc.navigationBar setTintColor:[GGSharedColor lightNavy]];
    
    [self.window makeKeyAndVisible];
    
    //开始动画
    UIImageView *splashView = [[UIImageView alloc] initWithFrame:screenBounds];    
    if (screenBounds.size.height <= 480.0) {
        splashView.image = [UIImage imageNamed:@"Default@2x.png"];
    } else {
        splashView.image = [UIImage imageNamed:@"Default-568h@2x.png"];
    }
    [self.window addSubview:splashView];
    [self.window bringSubviewToFront:splashView];  //放到最顶层;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1.0];
    [UIView setAnimationTransition:UIViewAnimationTransitionNone forView: self.window cache:YES];
    [UIView setAnimationDelegate:self];
    [UIView setAnimationDidStopSelector:@selector(startupAnimationDone:finished:context:)];
    splashView.alpha = 0.0;
    splashView.frame = CGRectMake(-60, -85, 440, 635);
    [UIView commitAnimations];
    //结束;
    
    [self checkVersionUpdate:@"1.2"];
    return YES;
}


-(void)checkVersionUpdate:(NSString *) currentVersion
{
    [[GGAPIService sharedInstance] CheckUpdateWithCurrentVersion:currentVersion aCompletion:^(GGVersionInfo *versionInfo)
    {
        if (versionInfo != nil) {
            if([versionInfo.verName floatValue] > [currentVersion floatValue])
            {
                NSMutableString * updateString = [[NSMutableString alloc]initWithString:versionInfo.updates] ;
                NSRange range = NSMakeRange(0, [updateString length]);
                [updateString replaceOccurrencesOfString:@"#" withString:@"\r" options:NSCaseInsensitiveSearch range:range];
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"更新提示" message:updateString delegate:self cancelButtonTitle:@"稍后" otherButtonTitles:@"更新", nil];
                [alert show];
                alert.tag = 7789;
            }
        }
        else
        {
            [self alertNetError];
        }

    }];
}


- (void)alertNetError{
    
    UIAlertView *alertView=[[UIAlertView alloc] initWithTitle:nil message:@"网络异常，请检查网络配置..." delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    [alertView setTag:110];
    [alertView show];
}

#pragma mark -UIAlertViewDelegate
- (void)willPresentAlertView:(UIAlertView *)alertView{
    UIView * view = [alertView.subviews objectAtIndex:2];
    if([view isKindOfClass:[UILabel class]]){
        UILabel* label = (UILabel*) view;
        label.textAlignment = UITextAlignmentLeft;
    }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (alertView.tag == 7788 && buttonIndex == 0)
    {
        [self enterITunesToUpdate];
    }
    else if (alertView.tag == 7789 && buttonIndex == 1)
    {
        [self enterITunesToUpdate];
    }
}


-(void)enterITunesToUpdate
{    
    NSURL * iTunesUrl = [NSURL URLWithString:@"http://itunes.apple.com/cn/app/id427457043?mt=8&ls=1"];
    [[UIApplication	sharedApplication] openURL:iTunesUrl];
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
    DLog(@"BACK");
//    [[NSNotificationCenter defaultCenter] postNotificationName:@"reportPosition" object:nil];
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
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
