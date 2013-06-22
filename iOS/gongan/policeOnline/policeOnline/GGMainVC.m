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
#import "UIDevice+IdentifierAddition.h"
#import "GGClueReportVC.h"
#import "GGGlobalValue.h"
#import "GGChangeProvinceVC.h"
#import "GGPhoneMask.h"

@interface GGMainVC ()
{
    BMKMapView* _mapView;
    UIWebView * phoneCallWebView;
    NSString * pcName;
    NSString * pcPhone;
    
    //定位城市
    NSString * cityStr;
    NSString * cityName;
}
@property (weak, nonatomic) IBOutlet UIImageView *ivBg;
@property (weak, nonatomic) IBOutlet UIButton *btnReportPolice;
@property (weak, nonatomic) IBOutlet UIButton *btnOnlinePolice;
@property (weak, nonatomic) IBOutlet UIButton *btnWanted;
@property (weak, nonatomic) IBOutlet UIButton *btnServiceWindow;
@property (weak, nonatomic) IBOutlet UIButton *btnServiceGuide;
@property (weak, nonatomic) IBOutlet UIButton *btnGuardTip;
@property (weak, nonatomic) IBOutlet UIButton *btnBreakRule;
@property (weak, nonatomic) IBOutlet UIButton *btnMyFavorite;
@property (weak, nonatomic) IBOutlet UIButton *btnPoliceInfomation;
@property (weak, nonatomic) BMKUserLocation * userLocation;

@property(nonatomic,strong) UIButton *theButton;

@end

@implementation GGMainVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        //        [GGGps sharedInstance].delegate = self;
        _mapView = [[BMKMapView alloc] init];
        [_mapView setHidden:YES];
        _mapView.delegate = self;
        _mapView.showsUserLocation = YES;
        phoneCallWebView = [[UIWebView alloc] initWithFrame:CGRectZero];
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        pcName = [defaults objectForKey:@"ggname"];
        pcPhone = [defaults objectForKey:@"ggtel"];
        
        [self setMyTitle:@"微公安"];
        [GGGlobalValue sharedInstance].provinceId = [NSNumber numberWithInt:1];
        [GGGlobalValue sharedInstance].provinceName = OTSSTRING(@"上海");
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
//    self.navigationItem.leftBarButtonItem = nil;
    [self setNaviLeftButtonLocationType];
    self.navigationItem.leftBarButtonItem.customView.frame = CGRectMake(0, 0, 56, 44);
    [self setNaviLeftButtonText:[GGGlobalValue sharedInstance].provinceName edgeInsets:UIEdgeInsetsMake(0, 17, 0, 0)];
    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(reportPosition) name:@"reportPosition" object:nil];
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

/**
 * 功能:左键定位省份的设置
 */
-(void)setNaviLeftButtonLocationType
{
    
    UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 44, 44)];
    UIBarButtonItem *btnItem = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.leftBarButtonItem = btnItem;
    [button addTarget:self action:@selector(leftBtnClicked:) forControlEvents:UIControlEventTouchUpInside];
    
    _theButton = (UIButton *)self.navigationItem.leftBarButtonItem.customView;
    
    UIImage *normalImage;
    UIImage *highlightImage;
    
    normalImage = [UIImage imageNamed:@"at_normal"];
    highlightImage = [UIImage imageNamed:@"at_hover"];
    
    _theButton.showsTouchWhenHighlighted = YES;
    [_theButton setBackgroundImage:normalImage forState:UIControlStateNormal];
    [_theButton setBackgroundImage:highlightImage forState:UIControlStateHighlighted];
}

/**
 * 功能:左键定位省份的设置
 */
-(void)setNaviLeftButtonText:(NSString *)text edgeInsets:(UIEdgeInsets)edgeInsets
{
    [_theButton setTitle:text forState:UIControlStateNormal];
    [_theButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    _theButton.titleLabel.font = [UIFont boldSystemFontOfSize:13.0];
    _theButton.titleLabel.shadowOffset = CGSizeMake(1.0, -1.0);
    _theButton.contentHorizontalAlignment = UIControlContentHorizontalAlignmentLeft;
    if (!UIEdgeInsetsEqualToEdgeInsets(edgeInsets, UIEdgeInsetsZero)) {
        _theButton.contentEdgeInsets = edgeInsets;
    }
}

/*
 *功能:点击左按钮，进行省份切换
 */
-(void)leftBtnClicked:(id)sender{
    GGChangeProvinceVC *changeProvinceVC = [[GGChangeProvinceVC alloc] initWithNibName:nil bundle:nil];
    UINavigationController *baseNC = [[UINavigationController alloc] initWithRootViewController:changeProvinceVC];
    
    [[GGPhoneMask sharedInstance] addMaskVC:baseNC animated:YES alpha:1.0];
    
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
    [self setBtnPoliceInfomation:nil];
    [super viewDidUnload];
}

#pragma mark - actions
-(IBAction)reportPoliceAction:(id)sender
{
    DLog(@"reportPoliceAction");
//    [NSThread detachNewThreadSelector:@selector(reportPosition) toTarget:self withObject:nil];
//    //创建操作队列
//    NSOperationQueue *operationQueue = [[NSOperationQueue alloc] init];
//    //设置队列中最大的操作数
//    [operationQueue setMaxConcurrentOperationCount:1];
//    //创建操作（最后的object参数是传递给selector方法的参数）
//    NSInvocationOperation *operation = [[NSInvocationOperation alloc] initWithTarget:self selector:@selector(reportPosition) object:nil];
//    //将操作添加到操作队列
//    [operationQueue addOperation:operation];

//    [[NSRunLoop mainRunLoop]runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
//    [GGUtils call:@"10010" webView:phoneCallWebView];

    
    dispatch_queue_t  _disPatchQueue  = dispatch_queue_create([[NSString stringWithFormat:@"%@.%@", [self.class description], self] UTF8String], NULL);
    
    dispatch_async(_disPatchQueue, ^{
        
        [self reportPosition];
        
        [[NSRunLoop mainRunLoop]runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];//the next time through the run loop.
        
        dispatch_async(dispatch_get_main_queue(), ^{
            
            NSString *number = @"10010";// 此处读入电话号码
            NSString *num = [[NSString alloc] initWithFormat:@"tel://%@",number];
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:num]];
            
        });
    });
}

-(void)reportPosition
{
    if(self.userLocation!=nil)
    {
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        pcName = [defaults objectForKey:@"ggname"];
        pcPhone = [defaults objectForKey:@"ggtel"];
        DLog(@"pcName = %@ pcPhone = %@",pcName,pcPhone);
        if (pcName == nil) {
            pcName = @"";
        }
        if (pcPhone == nil) {
            pcPhone = @"";
        }
        [GGSharedAPI reportPoliceWithAreaIDV2:REPORT_AREA_ID mbNum:[UIDevice macaddress] pcNum:[GGSharedAPI uniqueNumber] mapX:self.userLocation.location.coordinate.latitude mapY:self.userLocation.location.coordinate.longitude pcName:pcName pcPhone:pcPhone callback:^(id operation, id aResultObject, NSError *anError) {
            GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
            long typeid = [[[parser apiData] objectForKey:@"typeId"] longValue];
            DLog(@">>>> %ld",typeid);
            if (typeid == 0) {
                [GGSharedAPI reportPoliceWithAreaIDV2:REPORT_AREA_ID mbNum:[UIDevice macaddress] pcNum:[GGSharedAPI uniqueNumber] mapX:self.userLocation.location.coordinate.latitude mapY:self.userLocation.location.coordinate.longitude pcName:pcName pcPhone:pcPhone callback:^(id operation, id aResultObject, NSError *anError) {
                    GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
                    NSLog(@">> %ld",[[[parser apiData] objectForKey:@"typeId"] longValue]);
                }];
            }
        }];
    }
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
    vc.urlStr = [NSString stringWithFormat:@"%@/%@?r=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-getServiceWindowList.rht",arc4random()%1000];
    vc.naviTitleString = @"服务窗口";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)serviceGuideAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&r=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-column.rht?contentType=1",arc4random()%1000];
    vc.naviTitleString = @"服务指南";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)guardTipAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&r=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-column.rht?contentType=4",arc4random()%1000];
    vc.naviTitleString = @"防范提示";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)breakRuleAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@?r=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-searchIllegalCar.rht",arc4random()%1000];
    vc.naviTitleString = @"违章查询";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)myFavoriteAction:(id)sender
{
    GGMyFavoriteVC *vc = [[GGMyFavoriteVC alloc] init];
    vc.naviTitleString = @"我的收藏";
    [self.navigationController pushViewController:vc animated:YES];
    
}

-(IBAction)policeInfomationAction:(id)sender
{
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&r=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-column.rht?contentType=139",arc4random()%1000];
    vc.naviTitleString = @"警方资讯";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)clueReportAction:(id)sender
{
    GGClueReportVC *vc = [[GGClueReportVC alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - map
- (void)mapView:(BMKMapView *)mapView didUpdateUserLocation:(BMKUserLocation *)userLocation
{
    self.userLocation = userLocation;
    CLGeocoder *Geocoder=[[CLGeocoder alloc]init];//CLGeocoder用法参加之前博客
    CLGeocodeCompletionHandler handler = ^(NSArray *place, NSError *error) {
        for (CLPlacemark *placemark in place) {
            cityStr=placemark.thoroughfare;
            cityName=placemark.locality;
            NSLog(@"city %@",cityStr);//获取街道地址
            NSLog(@"cityName %@",cityName);//获取城市名
            break;
        }
    };
    CLLocation *loc = [[CLLocation alloc] initWithLatitude:userLocation.location.coordinate.latitude longitude:userLocation.location.coordinate.longitude];
    [Geocoder reverseGeocodeLocation:loc completionHandler:handler];
}

- (void)mapView:(BMKMapView *)mapView didFailToLocateUserWithError:(NSError *)error
{
	if (error != nil)
		NSLog(@"locate failed: %@", [error localizedDescription]);
	else {
		NSLog(@"locate failed");
	}
	
}

- (void)mapViewWillStartLocatingUser:(BMKMapView *)mapView
{
	NSLog(@"start locate");
}

#pragma mark - gps delegate
/*
 -(void)gps:(GGGps *)aGPS gotLongitude:(float)aLongitude latitude:(float)aLatitude
 {
 DLog(@"long:%f, lat:%f", aLongitude, aLatitude);
 DLog(@"mbNUM:%@",[UIDevice macaddress]);
 [GGSharedAPI reportPoliceWithAreaID:REPORT_AREA_ID mbNum:[UIDevice macaddress] pcNum:[GGSharedAPI uniqueNumber] mapX:aLatitude mapY:aLongitude callback:^(id operation, id aResultObject, NSError *anError) {
 //        GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
 }];
 [[GGGps sharedInstance] stopUpdate];
 }
 */

@end
