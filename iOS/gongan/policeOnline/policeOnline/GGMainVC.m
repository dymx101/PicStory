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
#import "MMDrawerBarButtonItem.h"
#import "GGAppDelegate.h"
#import "GGLocateArea.h"
#import "GGAreaFunction.h"
#import "GGAPIService.h"
#import "GGArchive.h"
#import "GGProvince.h"
#import "GGCluesVC.h"

//1.九宫格这个需要调接口的，但是他们那边现在没做，所有可以先写成配置文件
//区域数据：“武汉""老河口""襄阳""孝感""宜昌""荆州""十堰""黄石""黄冈""马口"
//0        1        2      3      4       5    (其他)
//大类是这几个区域 分别对应区域 position 0－5
//每个position 对应的九宫格数据为  0 ： 1,2,3
//1 ： 1,2,3,4
//2 :   1,2,3,4,5
//3 :    1,2,3,4,5,6
//4 :     1,2,3,4,5,6,7
//5 :     1,2,3,4,5,6,7,8
//else:     1,2,3,4,5,6,7,8,9



@interface GGMainVC ()<BMKGeneralDelegate>
{
    UIWebView * phoneCallWebView;
    NSString * pcName;
    NSString * pcPhone;
    
    //定位信息
    NSDictionary * area_dic;
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

@property (weak, nonatomic) IBOutlet UILabel *lblReportPolice;
@property (weak, nonatomic) IBOutlet UILabel *lblOnlinePolice;
@property (weak, nonatomic) IBOutlet UILabel *lblWanted;
@property (weak, nonatomic) IBOutlet UILabel *lblServiceWindow;
@property (weak, nonatomic) IBOutlet UILabel *lblServiceGuide;
@property (weak, nonatomic) IBOutlet UILabel *lblGuardTip;
@property (weak, nonatomic) IBOutlet UILabel *lblBreakRule;
@property (weak, nonatomic) IBOutlet UILabel *lblMyFavorite;
@property (weak, nonatomic) IBOutlet UILabel *lblPoliceInfomation;

@property (weak, nonatomic) BMKUserLocation * userLocation;

@property(nonatomic,strong) UIButton *theButton;
@property(nonatomic,strong) BMKMapManager * mapManager;
@property(nonatomic,strong) BMKMapView * mapView;

@end

@implementation GGMainVC
{
    NSArray         *_locations;
    NSArray         *_buttonIndexDic;
    
    NSArray         *_allButtons;
    NSArray         *_allTitles;
    
    NSArray         *_profile;
    
    NSUInteger      _currentPositonIndex;
    
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // 要使用百度地图，请先启动BaiduMapManager
        _mapManager = [[BMKMapManager alloc]init];
        [_mapManager start:@"" generalDelegate:self];
        
        _mapView = [[BMKMapView alloc] init];
        [_mapView setHidden:YES];
        _mapView.delegate = self;
        _mapView.showsUserLocation = YES;
        
        phoneCallWebView = [[UIWebView alloc] initWithFrame:CGRectZero];
        
        [self setMyTitle:@"微公安"];
        //启动的时候设置一个状态
        [GGGlobalValue sharedInstance].isFirstLaunch  = YES;
        //地区默认定位在老河口
        [GGGlobalValue sharedInstance].provinceId = [NSNumber numberWithInt:2];
        [GGGlobalValue sharedInstance].provinceName = OTSSTRING(@"老河口");
        
        //区域信息
        __block NSMutableArray * allProvince = [NSMutableArray array];
        [[GGAPIService sharedInstance] getLocateAreas:^(NSArray *arr) {
            if (arr !=nil) {
                for (GGLocateArea * locate in arr) {
                    //热门地区 排除襄阳
                    if ([locate.superId intValue] != 0) {
                        [allProvince addObject:locate];
                    }
                }
                [GGGlobalValue sharedInstance].locations = allProvince;
            }
            else
            {
                [self alertNetError];
            }
        }];
        
        //区域模块信息
        __block NSMutableArray  * _areafunctions = [NSMutableArray array];
        [[GGAPIService sharedInstance] getFunctionsAll:^(NSArray *arr) {
            if (arr !=nil) {
                for (GGAreaFunction * aFunction in arr) {
                    NSArray *aArray = [[aFunction functionIds] componentsSeparatedByString:@","];
                    [_areafunctions addObject:aArray];
                }
                _buttonIndexDic = [NSArray arrayWithArray:_areafunctions];
                [GGGlobalValue sharedInstance].areafunctions = arr;
                GGAreaFunction * firstareafuntion = [arr objectAtIndex:0];
                DLog(@">>firstareafuntion area id %@", firstareafuntion.areaId);
                [self updateIconsWithCaseIndex:0];
            }
            else
            {
                [self alertNetError];
            }
        }];
        
        //        _locations = @[@"武汉", @"老河口", @"襄阳", @"孝感", @"宜昌", @"荆州", @"十堰", @"黄石", @"黄冈", @"马口"];
        //       0       1       2           3       4       5       6       7       8       9
        
        //每个position 对应的九宫格数据为  0 ： 1,2,3
        //1 ： 1,2,3,4
        //2 :   1,2,3,4,5
        //3 :    1,2,3,4,5,6
        //4 :     1,2,3,4,5,6,7
        //5 :     1,2,3,4,5,6,7,8
        //else:     1,2,3,4,5,6,7,8,9
        
        //        _buttonIndexDic = [NSArray arrayWithObjects:
        //                           [NSArray arrayWithObjects:@(0), @(1), @(2), nil]                             // 0
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), nil]                     // 1
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), @(4), nil]               // 2
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), @(4), @(5), nil]                     // 3
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), @(4), @(5), @(6), nil]               // 4
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), @(4), @(5), @(6), @(7), nil]         // 5
        //                           , [NSArray arrayWithObjects:@(0), @(1), @(2), @(3), @(4), @(5), @(6), @(7), @(8), nil]   // 6
        //                           , nil];
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _allButtons = [NSArray arrayWithObjects: _btnReportPolice, _btnOnlinePolice, _btnGuardTip
                   , _btnBreakRule, _btnServiceGuide, _btnServiceWindow
                   , _btnPoliceInfomation, _btnWanted, _btnMyFavorite, nil];
    
    _allTitles = [NSArray arrayWithObjects: _lblReportPolice, _lblOnlinePolice, _lblGuardTip
                  , _lblBreakRule, _lblServiceGuide, _lblServiceWindow
                  , _lblPoliceInfomation, _lblWanted, _lblMyFavorite, nil];
    
    //    self.navigationItem.leftBarButtonItem = nil;
    [self setMenuButton];
    [self setNaviButtonLocationType];
    self.navigationItem.leftBarButtonItem.customView.frame = CGRectMake(0, 0, 86, 44);
    [self setNaviLeftButtonText:[GGGlobalValue sharedInstance].provinceName edgeInsets:UIEdgeInsetsMake(0, 20, 0, 0)];
    [self observeNotification:GG_NOTIFY_PROVINCE_CHANGED];
    
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
    
    [NSThread detachNewThreadSelector:@selector(handleLocation) toTarget:self withObject:nil];
}

/**
 *  功能:解析定位
 */
-(void) handleLocation {
    
    //首先更新位置
    self.userLocation = nil;
    [_mapManager start:@"" generalDelegate:self];
    do
    {
        [[NSRunLoop currentRunLoop]runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.1]];
        
    }while (!self.userLocation);
    [_mapManager stop];
    
    CLGeocoder *Geocoder=[[CLGeocoder alloc]init];
    CLGeocodeCompletionHandler handler = ^(NSArray *place, NSError *error) {
        for (CLPlacemark *placemark in place) {
            area_dic = [placemark addressDictionary];
            NSString * city = [area_dic objectForKey:@"City"]; //市
            NSString * locality = [area_dic objectForKey:@"SubLocality"];//地区
            DLog(@"area_dic %@ %@",city,locality);
            for (GGLocateArea * locate in _locations) {
                if ([locality rangeOfString:locate.prepare2].location != NSNotFound) {
                    [self setNaviLeftButtonText:[GGGlobalValue sharedInstance].provinceName edgeInsets:UIEdgeInsetsMake(0, 20, 0, 0)];
                    [GGGlobalValue sharedInstance].provinceId = locate.areaId;
                    [GGGlobalValue sharedInstance].provinceName = OTSSTRING(locate.address);
                    GGProvince * ggprovice = [[GGProvince alloc] init];
                    int modIndex = [ggprovice getProvinceModelIndex:[GGGlobalValue sharedInstance].provinceId];
                    [self updateIconsWithCaseIndex:modIndex];
                    break;
                }
            }
        }
        [GGGlobalValue sharedInstance].isFirstLaunch = NO;
    };
    CLLocation *loc = [[CLLocation alloc] initWithLatitude:_userLocation.location.coordinate.latitude longitude:_userLocation.location.coordinate.longitude];
    //首次启动的时候才解析地址
    if ([GGGlobalValue sharedInstance].isFirstLaunch) {
        [Geocoder reverseGeocodeLocation:loc completionHandler:handler];
    }
}


-(void)updateIconsWithCaseIndex:(NSUInteger)aCaseIndex
{
    _currentPositonIndex = aCaseIndex;
//    _currentPositonIndex = MIN(6, _currentPositonIndex);
    
    for (UIButton *btn in _allButtons)
    {
        btn.hidden = YES;
    }
    
    NSArray *indexs = _buttonIndexDic[_currentPositonIndex];
    
    for (id index in indexs)
    {
        UIButton *btn = _allButtons[([index intValue]-1)];
        btn.hidden = NO;
    }
    
    for (UILabel *lbl in _allTitles)
    {
        lbl.hidden = YES;
    }
    
    for (id index in indexs)
    {
        UILabel *lbl = _allTitles[([index intValue]-1)];
        lbl.hidden = NO;
    }
}

/**
 * 功能:左键设菜单
 */
-(void)setMenuButton
{
    //MMDrawerBarButtonItem * leftDrawerButton = [[MMDrawerBarButtonItem alloc] initWithTarget:self action:@selector(leftDrawerButtonPress:)];
    UIBarButtonItem *leftDrawerButton = [[UIBarButtonItem alloc] initWithTitle:@"设置" style:UIBarButtonItemStyleBordered target:self action:@selector(leftDrawerButtonPress:)];
    [self.navigationItem setRightBarButtonItem:leftDrawerButton animated:YES];
}

/**
 * 功能:右键定位省份的设置
 */
-(void)setNaviButtonLocationType
{
    
    UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 86, 44)];
    UIBarButtonItem *btnItem = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.leftBarButtonItem = btnItem;
    [button addTarget:self action:@selector(leftBtnClicked:) forControlEvents:UIControlEventTouchUpInside];
    
    _theButton = (UIButton *)btnItem.customView;
    
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
    //if (!UIEdgeInsetsEqualToEdgeInsets(edgeInsets, UIEdgeInsetsZero)) {
    _theButton.contentEdgeInsets = edgeInsets;
    //}
}

/*
 *功能:点击左按钮，进行省份切换
 */
-(void)leftBtnClicked:(id)sender{
    GGChangeProvinceVC *changeProvinceVC = [[GGChangeProvinceVC alloc] initWithNibName:nil bundle:nil];
    UINavigationController *baseNC = [[UINavigationController alloc] initWithRootViewController:changeProvinceVC];
    
    [[GGPhoneMask sharedInstance] addMaskVC:baseNC animated:YES alpha:1.0];
    
}

//消息监听
- (void)handleNotification:(NSNotification *)notification
{
    if ([notification.name isEqualToString:GG_NOTIFY_PROVINCE_CHANGED]) {
        [self setNaviLeftButtonText:[GGGlobalValue sharedInstance].provinceName edgeInsets:UIEdgeInsetsMake(0, 20, 0, 0)];
        DLog(@">>> proviceid %d",[[GGGlobalValue sharedInstance].provinceId integerValue]);
         GGProvince * ggprovice = [[GGProvince alloc] init];
        int modIndex = [ggprovice getProvinceModelIndex:[GGGlobalValue sharedInstance].provinceId];
        [self updateIconsWithCaseIndex:modIndex];
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
    [self setBtnPoliceInfomation:nil];
    [self setLblPoliceInfomation:nil];
    [super viewDidUnload];
}

#pragma mark - actions
-(IBAction)reportPoliceAction:(id)sender
{
    DLog(@"reportPoliceAction");
    [NSThread detachNewThreadSelector:@selector(reportPosition) toTarget:self withObject:nil];
    dispatch_queue_t  _disPatchQueue  = dispatch_queue_create([[NSString stringWithFormat:@"%@.%@", [self.class description], self] UTF8String], NULL);
    dispatch_async(_disPatchQueue, ^{
        
//        [self reportPosition];
        
        [[NSRunLoop mainRunLoop]runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];//the next time through the run loop.
        
        dispatch_async(dispatch_get_main_queue(), ^{
            
            NSString *number = @"95555";// 此处读入电话号码
            NSString *num = [[NSString alloc] initWithFormat:@"tel://%@",number];
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:num]];
        });
    });
}

-(void)reportPosition
{
    if(self.userLocation!=nil)
    {
        //首先更新位置
        self.userLocation = nil;
        [_mapManager start:@"" generalDelegate:self];
        do
        {
            [[NSRunLoop currentRunLoop]runUntilDate:[NSDate dateWithTimeIntervalSinceNow:0.1]];
            
        }while (!self.userLocation);
        [_mapManager stop];
        float _mapxx = 0.0;
        float _mapyy = 0.0;
        _profile = (NSArray *)[GGArchive unarchiveDataWithFileName:@"profile.plist"];
        // 需要判断是否发射本地位置信息，姓名，手机电话
        if ([GGUserDefault reportMyLocation]) {
            _mapxx = self.userLocation.location.coordinate.latitude;
            _mapyy = self.userLocation.location.coordinate.longitude;
        }
        if ([_profile count] > 0 && [GGUserDefault reportMyName]) {
            pcName = [_profile objectAtIndex:0];
        }
        else
        {
            pcName = @"";
        }
        if ([_profile count] > 0 && [GGUserDefault reportMyPhone]) {
            pcPhone = [_profile objectAtIndex:1];
        }
        else
        {
           pcPhone = @"";
        }

        [GGSharedAPI reportPoliceWithAreaIDV2:[[GGGlobalValue sharedInstance].provinceId longValue] mbNum:[UIDevice macaddress] pcNum:[GGSharedAPI uniqueNumber] mapX:_mapxx mapY:_mapyy pcName:pcName pcPhone:pcPhone callback:^(id operation, id aResultObject, NSError *anError) {
            GGApiParser *parser = [GGApiParser parserWithRawData:aResultObject];
            long typeid = [[[parser apiData] objectForKey:@"typeId"] longValue];
            DLog(@">>>> %ld",typeid);
            if (typeid == 0) {
                [GGSharedAPI reportPoliceWithAreaIDV2:[[GGGlobalValue sharedInstance].provinceId longValue] mbNum:[UIDevice macaddress] pcNum:[GGSharedAPI uniqueNumber] mapX:_mapxx mapY:_mapyy pcName:pcName pcPhone:pcPhone callback:^(id operation, id aResultObject, NSError *anError) {
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


//服务指南变成办事大厅
-(IBAction)serviceWindowAction:(id)sender
{
    int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&unitId=%d&r=%d", GGN_STR_TEST_SERVER_URL, @"mobile-column.rht?contentType=3",unitid,arc4random()%1000];
    vc.naviTitleString = @"办事大厅";
    [self.navigationController pushViewController:vc animated:YES];

}

//服务窗口变成线索征集
-(IBAction)serviceGuideAction:(id)sender
{
    //    GGWebVC *vc = [[GGWebVC alloc] init];
    //    int unitId = [[GGGlobalValue sharedInstance].provinceId intValue];
    //    vc.urlStr = [NSString stringWithFormat:@"%@/%@?r=%d&unitId=%d", GGN_STR_PRODUCTION_SERVER_URL, @"mobile-getServiceWindowList.rht",arc4random()%1000,unitId];
    //    vc.naviTitleString = @"服务窗口";
    GGCluesVC *vc = [GGCluesVC new];
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)guardTipAction:(id)sender
{
    int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&unitId=%d&r=%d", GGN_STR_TEST_SERVER_URL, @"mobile-column.rht?contentType=4",unitid,arc4random()%1000];
    vc.naviTitleString = @"防范提示";
    [self.navigationController pushViewController:vc animated:YES];
}

-(IBAction)breakRuleAction:(id)sender
{
    int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@?r=%d&unitId=%d", GGN_STR_TEST_SERVER_URL, @"mobile-searchIllegalCar.rht",arc4random()%1000,unitid];
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
    int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/%@&r=%d&unitId=%d", GGN_STR_TEST_SERVER_URL, @"mobile-column.rht?contentType=139",arc4random()%1000,unitid];
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
    // if the location is older than 30s ignore
    if (fabs([userLocation.location.timestamp timeIntervalSinceDate:[NSDate date]]) > 30.0)
    {
        return;
    }
    self.userLocation = userLocation;    
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

#pragma mark - Button Handlers
-(void)leftDrawerButtonPress:(id)sender{
    [GGSharedDelegate.drawerVC toggleDrawerSide:MMDrawerSideRight animated:YES completion:nil];
}

@end
