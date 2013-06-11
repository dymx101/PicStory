//
//  GGMainVC.m
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGMainVC.h"
#import "GGWebVC.h"
#import "GGOnlinePoliceVC.h"
#import "GGWantedVC.h"
#import "GGMyFavoriteVC.h"

@interface GGMainVC ()
@property (weak, nonatomic) IBOutlet UIImageView *ivBg;
@property (weak, nonatomic) IBOutlet UIButton *btnReportPolice;
@property (weak, nonatomic) IBOutlet UIButton *btnOnlinePolice;
@property (weak, nonatomic) IBOutlet UIButton *btnWanted;
@property (weak, nonatomic) IBOutlet UIButton *btnServiceWindow;
@property (weak, nonatomic) IBOutlet UIButton *btnServiceGuide;
@property (weak, nonatomic) IBOutlet UIButton *btnGuardTip;
@property (weak, nonatomic) IBOutlet UIButton *btnBreakRule;
@property (weak, nonatomic) IBOutlet UIButton *btnMyFavorite;

@end

@implementation GGMainVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [GGGps sharedInstance].delegate = self;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = @"微公安";
    if (IS_WIDESCREEN) {
        _ivBg.image = GGSharedImagePool.mainBgWide;
        CGRect ivBgRc = _ivBg.frame;
        ivBgRc.size.width = GGSharedImagePool.mainBgWide.size.width;
        ivBgRc.size.height = GGSharedImagePool.mainBgWide.size.height;
        ivBgRc.origin.y = self.view.bounds.size.height - ivBgRc.size.height;
        _ivBg.frame = ivBgRc;
    }
    else
    {
        CGRect ivBgRc = _ivBg.frame;
        ivBgRc.origin.y = self.view.bounds.size.height - ivBgRc.size.height;
        _ivBg.frame = ivBgRc;
    }
}

- (void)viewDidUnload {
    [self setIvBg:nil];
    [self setBtnReportPolice:nil];
    [self setBtnOnlinePolice:nil];
    [self setBtnWanted:nil];
    [self setBtnServiceWindow:nil];
    [self setBtnServiceGuide:nil];
    [self setBtnGuardTip:nil];
    [self setBtnBreakRule:nil];
    [self setBtnMyFavorite:nil];
    [super viewDidUnload];
}

#pragma mark - actions
-(IBAction)reportPoliceAction:(id)sender
{
    DLog(@"reportPoliceAction");
    [GGUtils call:@"110"];
    [[GGGps sharedInstance] startUpdate];
}

-(IBAction)onlinePoliceAction:(id)sender
{
    DLog(@"onlinePoliceAction");
    GGOnlinePoliceVC *vc = [[GGOnlinePoliceVC alloc] init];
    vc.naviTitleString = @"在线公安";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)wantedAction:(id)sender
{
    DLog(@"wantedAction");
    GGWantedVC *vc = [[GGWantedVC alloc] init];
    vc.naviTitleString = @"通缉令";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)serviceWindowAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-getServiceWindowList.rht"];
    vc.naviTitleString = @"服务窗口";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)serviceGuideAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-column.rht?contentType=1"];
    vc.naviTitleString = @"服务指南";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)guardTipAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-column.rht?contentType=4"];
    vc.naviTitleString = @"防范提示";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)breakRuleAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-searchIllegalCar.rht"];
    vc.naviTitleString = @"违章查询";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)myFavoriteAction:(id)sender
{
    GGMyFavoriteVC *vc = [[GGMyFavoriteVC alloc] init];
    vc.naviTitleString = @"我的收藏";
    [self.navigationController pushViewController:vc animated:YES];
    
}

#pragma mark - gps delegate
-(void)gps:(GGGps *)aGPS gotLongitude:(float)aLongitude latitude:(float)aLatitude
{
    DLog(@"long:%f, lat:%f", aLongitude, aLatitude);
    [[GGGps sharedInstance] stopUpdate];
}

@end
