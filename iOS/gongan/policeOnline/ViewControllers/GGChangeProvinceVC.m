//
//  GGChangeProvinceVC.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGChangeProvinceVC.h"
#import "GGGlobalValue.h"
#import "GGAppDelegate.h"
#import "GGPhoneMask.h"
#import "GGProvince.h"
#import "NSMutableArray+Safe2.h"
#import "GGLocateArea.h"
#import "GGAPIService.h"

@interface GGChangeProvinceVC ()

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) NSMutableArray *titleArray;

@property (nonatomic, strong) GGProvince * ggProvince;

@end

@implementation GGChangeProvinceVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.hidesBottomBarWhenPushed = YES;
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    [self initTitleBar];
    [self initData];
    
}

/**
 *  功能:左按钮点击行为，可在子类重写此方法
 */
- (void)leftBtnClicked:(id)sender
{
    [[GGPhoneMask sharedInstance] dismissMaskVCAnimated:YES];
}

/**
 *  功能:数据初始化
 */
- (void)initData
{
    self.titleArray = [NSMutableArray array];
    self.ggProvince  = [[GGProvince alloc]init];
    self.ggProvince.provinceArray = [NSMutableArray array];
    
    [self.titleArray addObject:@"热门地区"];
    [self.ggProvince.provinceArray addObject:[GGGlobalValue sharedInstance].locations];
    [self initMainView];
}

/**
 *  功能:初始化title bar
 */
- (void)initTitleBar
{
    //title
    UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 44, 44)];
    [button setBackgroundImage:[UIImage imageNamed:@"title_left_btn"] forState:UIControlStateNormal];
    [button setBackgroundImage:[UIImage imageNamed:@"title_left_btn_sel"] forState:UIControlStateHighlighted];
    UIBarButtonItem *btnItem = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.leftBarButtonItem = btnItem;
    [button addTarget:self action:@selector(leftBtnClicked:) forControlEvents:UIControlEventTouchUpInside];
    
    self.navigationItem.title = [NSString stringWithFormat:@"当前地区-%@", [GGGlobalValue sharedInstance].provinceName];
}

/**
 *  功能:初始化main view
 */
- (void)initMainView
{
    UIView *topView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 54)];
    topView.backgroundColor = [UIColor colorWithRed:1.0 green:247.0/255.0 blue:211.0/255.0 alpha:1.0];
    [self.view addSubview:topView];
    
    UIImageView *topIv = [[UIImageView alloc] initWithFrame:CGRectMake(10, 15, 24, 24)];
    topIv.image = [UIImage imageNamed:@"province_icon"];
    [topView addSubview:topIv];
    
    UILabel *topLbl = [[UILabel alloc] initWithFrame:CGRectMake(44, 7, 266, 40)];
    topLbl.backgroundColor = [UIColor clearColor];
    topLbl.numberOfLines = 2;
    topLbl.text = @"因各地区公安数据不同，请根据您的所在地选择相应的地区";
    topLbl.textColor = [UIColor colorWithRed:51.0/255.0 green:51.0/255.0 blue:51.0/255.0 alpha:1.0];
    topLbl.font = [UIFont systemFontOfSize:14.0];
    [topView addSubview:topLbl];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 54, 320, self.view.bounds.size.height-54-44) style:UITableViewStylePlain];
    self.tableView.backgroundColor = [UIColor colorWithRed:250.0/255.0 green:250.0/255.0 blue:250.0/255.0 alpha:1.0];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    [self.view addSubview:self.tableView];
}

/**
 *  功能:切换到新省份
 */
- (void)switchToProvince:(NSString *)aProvinceName
{
    int provinceId = [self.ggProvince getProvinceIdFromName:aProvinceName].intValue;
    
    [GGGlobalValue sharedInstance].provinceName = aProvinceName;
    [GGGlobalValue sharedInstance].provinceId = [NSNumber numberWithInt:provinceId];
    [self postNotification:GG_NOTIFY_PROVINCE_CHANGED withObject:aProvinceName];
    [self leftBtnClicked:nil];
}

- (void)viewDidUnload
{
    [self setTableView:nil];
    [super viewDidUnload];
}

#pragma mark - UITableViewDelegate&UITableViewDataSource

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return self.titleArray.count;
}

//header
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIImageView *view = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 32)];
    view.image = [UIImage imageNamed:@"province_title_bg"];
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(10, 0, 300, 32)];
    label.backgroundColor = [UIColor clearColor];
    label.text = [self.titleArray safeObjectAtIndex:section];
    label.textColor = [UIColor colorWithRed:51.0/255.0 green:51.0/255.0 blue:51.0/255.0 alpha:1.0];
    label.font = [UIFont systemFontOfSize:14.0];
    [view addSubview:label];
    
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 32.0;
}

//cell
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSArray *arr = [self.ggProvince.provinceArray safeObjectAtIndex:section];
    return arr.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ProvinceCell"];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"ProvinceCell"];
        UIImageView *bg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        bg.image = [UIImage imageNamed:@"banmaxian2"];
        cell.backgroundView = bg;
        
        UIImageView *selBg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
        selBg.image = [UIImage imageNamed:@"banmaxian2_sel"];
        cell.selectedBackgroundView = selBg;
        
        cell.textLabel.backgroundColor = [UIColor clearColor];
        cell.textLabel.textColor = [UIColor colorWithRed:51.0/255.0 green:51.0/255.0 blue:51.0/255.0 alpha:1.0];
        cell.textLabel.font = [UIFont systemFontOfSize:16.0];
    }
    
    GGLocateArea * gglocate = [[self.ggProvince.provinceArray safeObjectAtIndex:indexPath.section] safeObjectAtIndex:indexPath.row];
    
    cell.textLabel.text = [NSString stringWithFormat:@" %@", gglocate.address];
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44.0;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    GGLocateArea * selectedGGlocate = [[self.ggProvince.provinceArray safeObjectAtIndex:[indexPath section]] safeObjectAtIndex:[indexPath row]];
    if (![[GGGlobalValue sharedInstance].provinceName isEqualToString:selectedGGlocate.address]) {
        [self switchToProvince:selectedGGlocate.address];
    }
    else
    {
        [self leftBtnClicked:nil];
    }
}


@end
