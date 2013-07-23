//
//  GGAppDelegate.m
//  policeOnline
//
//  Created by dong yiming on 13-4-27.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGAppDelegate.h"

#import "GGMainVC.h"
#import "GGSettingVC.h"
#import "GGClueReportVC.h"

#import "GGAPIService.h"
#import "GGVersionInfo.h"
#import "GGColor.h"
#import "GGTestVC.h"

#import "GGLeftDrawerVC.h"

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
    
    UIViewController *leftDrawerVC = [[GGLeftDrawerVC alloc] init];
    
    _drawerVC = [[MMDrawerController alloc] initWithCenterViewController:_nc rightDrawerViewController:leftDrawerVC];
    
    [_drawerVC setMaximumLeftDrawerWidth:100];
    [_drawerVC setMaximumRightDrawerWidth:200];
    [_drawerVC setOpenDrawerGestureModeMask:MMOpenDrawerGestureModeAll];
    [_drawerVC setCloseDrawerGestureModeMask:MMCloseDrawerGestureModeAll];
    
//    [self setupViewControllers];    
//    self.window.rootViewController = _tabBarController;
    self.window.rootViewController = _drawerVC;
    
//    [[UINavigationBar appearance] setTintColor:[GGSharedColor lightNavy]];
    
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
    
    UIAlertView *alertView=[[UIAlertView alloc] initWithTitle:nil message:@"网络繁忙，请稍后重试..." delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
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

#pragma mark - Methods

- (void)setupViewControllers
{
    //id obj = @(1);
    //NSLog(@"%@", NSStringFromClass([obj class]));
    
    UIViewController *nc1 = [[UINavigationController alloc] initWithRootViewController:[GGMainVC createInstance]];
    UIViewController *nc2 = [[UINavigationController alloc] initWithRootViewController:[GGClueReportVC createInstance]];
    UIViewController *nc3 = [[UINavigationController alloc] initWithRootViewController:[GGSettingVC createInstance]];
    
    _tabBarController = [[RDVTabBarController alloc] init];
    [_tabBarController setViewControllers:@[nc1, nc2, nc3]];
    
    [self customizeTabBarForController:_tabBarController];
}

- (void)customizeTabBarForController:(RDVTabBarController *)tabBarController
{
    UIImage *finishedImage = [[UIImage imageNamed:@"tabbar_selected_background"]
                              resizableImageWithCapInsets:UIEdgeInsetsMake(0, 1, 0, 0)];
    
    UIImage *unfinishedImage = [[UIImage imageNamed:@"tabbar_unselected_background"]
                                resizableImageWithCapInsets:UIEdgeInsetsMake(0, 1, 0, 0)];
    
    //[tabBarController setTabBarHeight:63];
    
    // 安装 一键报警按钮 to tabbar
    NSMutableArray * itemsWithExtra = [NSMutableArray array];
    [itemsWithExtra addObjectsFromArray:tabBarController.tabBar.items];
    RDVTabBarItem *extraItem = [[RDVTabBarItem alloc] init];
    //[extraItem setTitle:@"一键报警" forState:UIControlStateNormal];
    [itemsWithExtra addObject:extraItem];
    tabBarController.tabBar.items = itemsWithExtra;
    
    UIButton *alertBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    alertBtn.frame = CGRectMake(20, 5, 40, 40);
    alertBtn.backgroundColor = [GGSharedColor silver];
    [alertBtn setTitle:@"警" forState:UIControlStateNormal];
    [alertBtn setTitleColor:[GGSharedColor darkRed] forState:UIControlStateNormal];
    [alertBtn setTitleColor:[UIColor redColor] forState:UIControlStateHighlighted];
    [alertBtn addTarget:self action:@selector(dummy) forControlEvents:UIControlEventTouchUpInside];
    
    alertBtn.layer.cornerRadius = 20.f;
    alertBtn.layer.borderColor = [GGSharedColor darkRed].CGColor;
    alertBtn.layer.borderWidth = 4;
    alertBtn.layer.shadowColor = [GGSharedColor black].CGColor;
    alertBtn.layer.shadowOffset = CGSizeMake(2, 2);
    alertBtn.layer.shadowRadius = 3.f;
    
    [extraItem addSubview:alertBtn];
    
    // 自定义tabbar item 图片
    for (RDVTabBarItem *item in [[tabBarController tabBar] items])
    {
        [item setBackgroundSelectedImage:finishedImage withUnselectedImage:unfinishedImage];
        
        //UIImage *image = [UIImage imageNamed:@"first"];
        //[item setFinishedSelectedImage:image withFinishedUnselectedImage:image];
    }
}

-(void)dummy
{
    [GGAlert alert:@"一键报警"];
}

- (BOOL)tabBarController:(RDVTabBarController *)tabBarController shouldSelectViewController:(UIViewController *)viewController
{
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
    DLog(@"BACK");
//    [[NSNotificationCenter defaultCenter] postNotificationName:@"reportPosition" object:nil];
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
