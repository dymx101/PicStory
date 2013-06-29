//
//  GGSettingVC.m
//  policeOnline
//
//  Created by Dong Yiming on 6/15/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGSettingVC.h"
#import "GGGlobalValue.h"
#import "GGChangeProvinceVC.h"
#import "GGPhoneMask.h"

@interface GGSettingVC ()

@property(nonatomic,strong) UIButton *theButton;

@end

@implementation GGSettingVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [self setMyTitle:@"设置中心"];
        //地区默认定位在老河口
        [GGGlobalValue sharedInstance].provinceId = [NSNumber numberWithInt:2];
        [GGGlobalValue sharedInstance].provinceName = OTSSTRING(@"老河口");
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self setNaviLeftButtonLocationType];
    self.navigationItem.leftBarButtonItem.customView.frame = CGRectMake(0, 0, 56, 44);
    [self setNaviLeftButtonText:[GGGlobalValue sharedInstance].provinceName edgeInsets:UIEdgeInsetsMake(0, 17, 0, 0)];
}

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

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
