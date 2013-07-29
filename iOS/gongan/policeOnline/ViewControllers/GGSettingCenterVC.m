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
@property (weak, nonatomic) IBOutlet UISwitch *switchLocation;
@property (weak, nonatomic) IBOutlet UISwitch *switchPhone;
@property (weak, nonatomic) IBOutlet UISwitch *switchName;

@end

@implementation GGSettingCenterVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
//        self.title = @"设置中心";
        [self setMyTitle:@"设置中心"];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _switchLocation.on = [GGUserDefault reportMyLocation];
    _switchPhone.on = [GGUserDefault reportMyPhone];
    _switchName.on = [GGUserDefault reportMyName];
}

-(IBAction)modifyProfile:(id)sender
{
    GGProfileVC *vc = [GGProfileVC new];
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)switchChanged:(id)sender
{
    UISwitch *switcher = sender;
    if (switcher == _switchLocation)
    {
        [GGUserDefault saveReportMyLocation:_switchLocation.on];
    }
    else if (switcher == _switchPhone)
    {
        [GGUserDefault saveReportMyPhone:_switchPhone.on];
    }
    else if (switcher == _switchName)
    {
        [GGUserDefault saveReportMyName:_switchName.on];
    }
}

- (void)viewDidUnload {
    [self setSwitchLocation:nil];
    [self setSwitchPhone:nil];
    [self setSwitchName:nil];
    [super viewDidUnload];
}
@end
