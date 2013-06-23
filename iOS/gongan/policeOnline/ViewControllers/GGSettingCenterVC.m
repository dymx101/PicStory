//
//  GGSettingCenterVC.m
//  policeOnline
//
//  Created by Dong Yiming on 6/23/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGSettingCenterVC.h"

#import "GGProfileVC.h"

@interface GGSettingCenterVC ()

@end

@implementation GGSettingCenterVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"设置中心";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

-(IBAction)modifyProfile:(id)sender
{
    GGProfileVC *vc = [GGProfileVC new];
    [self.navigationController pushViewController:vc animated:YES];
}

@end
