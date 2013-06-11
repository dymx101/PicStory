//
//  GGBaseViewController.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-3.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGBaseViewController.h"


@interface GGBaseViewController ()

@end

@implementation GGBaseViewController
{
    __weak MBProgressHUD *hud;
}

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
    self.view.backgroundColor = GGSharedColor.silver;
}

-(void)showLoadingHUD
{
    //[MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [hud hide:YES];
    hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeIndeterminate;
    hud.labelText = @"Loading";
}

-(void)hideLoadingHUD
{
    [hud hide:YES];
}

@end
