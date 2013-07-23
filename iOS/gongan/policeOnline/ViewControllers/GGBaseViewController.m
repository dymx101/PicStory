//
//  GGBaseViewController.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-3.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGBaseViewController.h"
#import "MBProgressHUD.h"
#import "GGColor.h"

@interface GGBaseViewController ()

@end

@implementation GGBaseViewController
{
    __weak MBProgressHUD *hud;
}

+(GGBaseViewController *)createInstance
{
    return [[self alloc] init];
}

-(void)setMyTitle:(NSString *)aTitle
{
    self.title = aTitle;
    self.navigationItem.title = aTitle;
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
    self.navigationController.navigationBar.tintColor = GGSharedColor.lightNavy;
    
    self.navigationItem.leftBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"返回" style:UIBarButtonItemStylePlain target:self action:@selector(backButtonClicked:)];
    
    DLog(@"%@'s view loaded", NSStringFromClass([self class]));
}

-(void)backButtonClicked:(id)sender
{
    [self.navigationController popViewControllerAnimated:YES];
}

-(void)showLoadingHUDTxt:(NSString *) txt
{
    [hud hide:YES];
    hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeAnnularDeterminate;
    hud.labelText = txt;
}

-(void)showLoadingHUD
{
    //[MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [hud hide:YES];
    hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeIndeterminate;
    hud.labelText = @"Loading";
}

- (void)alertNetError{
    
    UIAlertView *alertView=[[UIAlertView alloc] initWithTitle:nil message:@"网络繁忙，请稍后重试..." delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    [alertView setTag:110];
    [alertView show];
}

-(void)hideLoadingHUD
{
    [hud hide:YES];
}

@end
