//
//  GGLeftDrawerVC.m
//  policeOnline
//
//  Created by Dong Yiming on 6/22/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGLeftDrawerVC.h"
#import "GGAppDelegate.h"

#import "GGClueReportVC.h"
#import "GGSettingCenterVC.h"

@interface GGLeftDrawerVC ()

@end

@implementation GGLeftDrawerVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}


#pragma mark - 线索征集
-(IBAction)reportCLue:(id)sender
{
    [GGSharedDelegate.drawerVC closeDrawerAnimated:YES completion:nil];
    
    UINavigationController *centerVC = (UINavigationController *)[GGSharedDelegate.drawerVC centerViewController];
    
    GGClueReportVC *vc = [GGClueReportVC new];
    [centerVC pushViewController:vc animated:YES];
}

-(IBAction)settingCenter:(id)sender
{
    [GGSharedDelegate.drawerVC closeDrawerAnimated:YES completion:^(BOOL completed){
        
        UINavigationController *centerVC = (UINavigationController *)[GGSharedDelegate.drawerVC centerViewController];
        
        GGSettingCenterVC *vc = [GGSettingCenterVC new];
        [centerVC pushViewController:vc animated:YES];
        
    }];
    
    
    
}

@end
