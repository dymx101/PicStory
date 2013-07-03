//
//  GGPoliceDetailViewController.m
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGPoliceDetailViewController.h"
#import "GGAPIService.h"
#import "GGGlobalValue.h"

#define RELOADGGPOLICEMAN  @"RELOADGGPOLICEMAN"

@interface GGPoliceDetailViewController ()
{
    UIWebView * phoneCallWebView;
}
@property (weak, nonatomic) IBOutlet UIScrollView *svContent;
@property (weak, nonatomic) IBOutlet UIImageView *ivPhoto;
@property (weak, nonatomic) IBOutlet UIButton *btnAddFavor;
@property (weak, nonatomic) IBOutlet UILabel *lblName;
@property (weak, nonatomic) IBOutlet UILabel *lblNumber;
@property (weak, nonatomic) IBOutlet UILabel *lblStation;
@property (weak, nonatomic) IBOutlet UIButton *btnPhone;
@property (weak, nonatomic) IBOutlet UIButton *btnStationPhone;
@property (weak, nonatomic) IBOutlet UIButton *btnSuperviserPhone;

@end

@implementation GGPoliceDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        phoneCallWebView = [[UIWebView alloc] initWithFrame:CGRectZero];
    }
    return self;
}

-(IBAction)getMyfavorite:(id)sender
{
    DLog(@"getMyfavorite");
    if (_keep) {
        [[GGAPIService sharedInstance] insertPolicemanToDB:_policeman aCompletion:^(BOOL success){
            if (success == YES) {
                [self showError:@"收藏成功"];
                dispatch_async(dispatch_get_main_queue(), ^{
                    [[NSNotificationCenter defaultCenter] postNotificationName:RELOADGGPOLICEMAN object:nil];
                });
            }
            else
            {
                [self showError:@"您已经收藏，无需重复收藏"];
            }
        }];
    }
    else
    {
        [[GGAPIService sharedInstance] delPolicemanFromDB:_policeman aCompletion:^(BOOL success){
            if (success == YES) {
                [self showError:@"取消成功"];
                dispatch_async(dispatch_get_main_queue(), ^{
                    [[NSNotificationCenter defaultCenter] postNotificationName:RELOADGGPOLICEMAN object:nil];
                });
            }
        }];
        
    }
    
}

-(IBAction)CALL:(id)sender
{
    if ([sender isKindOfClass:[UIButton class]]) {
        UIButton * btn = sender;
        if (btn.tag == 101) {
            [GGUtils call:_policeman.phone webView:phoneCallWebView];
        }
        if (btn.tag == 102) {
            [GGUtils call:_policeman.stationPhone webView:phoneCallWebView];
        }
        if (btn.tag == 103) {
            [GGUtils call:_policeman.superviserPhone webView:phoneCallWebView];
        }
    }
}

-(void)showError:(NSString *)error
{
    UIAlertView *alertView=[[UIAlertView alloc] initWithTitle:nil message:error delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    [alertView setTag:110];
    [alertView show];
}


#pragma mark 设置alert点击操作
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (alertView.tag == 110) {
        [self.navigationController popViewControllerAnimated:YES];
    }
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = _naviTitleString;
    self.view.backgroundColor = [UIColor whiteColor];
    
    self.ivPhoto.layer.borderColor = GGSharedColor.lightGray.CGColor;
    self.ivPhoto.layer.borderWidth = 2.f;
    self.ivPhoto.layer.cornerRadius = 2.f;
    
    NSURL *nsurl = [NSURL URLWithString:[NSString stringWithFormat:@"%@%@", [GGApi apiBaseUrl],_policeman.photo]];
    
    [self.ivPhoto setImageWithURL:nsurl];
    
    [self.lblName setText:_policeman.name];
    [self.lblNumber setText:_policeman.number];
    [self.lblStation setText:_policeman.stationName];
    [self.btnPhone setTitle:_policeman.phone forState:UIControlStateNormal];
    [self.btnStationPhone setTitle:_policeman.stationPhone forState:UIControlStateNormal];
    [self.btnSuperviserPhone setTitle:_policeman.superviserPhone forState:UIControlStateNormal];
    
    if (_keep)
        [self.btnAddFavor setTitle:@"收藏" forState:UIControlStateNormal];
    else
        [self.btnAddFavor setTitle:@"取消收藏" forState:UIControlStateNormal];
    
    
    //初始化视图容器
    UIView *container = [[UIView alloc] initWithFrame:CGRectMake(160, 330, 100, 120)];
    //初始化单选按钮控件
    GGRadioButton *rb1 = [[GGRadioButton alloc] initWithGroupId:@"first group" index:0];
    GGRadioButton *rb2 = [[GGRadioButton alloc] initWithGroupId:@"first group" index:1];
    GGRadioButton *rb3 = [[GGRadioButton alloc] initWithGroupId:@"first group" index:2];
    //设置Frame
    rb1.frame = CGRectMake(10,30,22,22);
    rb2.frame = CGRectMake(10,60,22,22);
    rb3.frame = CGRectMake(10,90,22,22);
    //添加到视图容器
    [container addSubview:rb1];
    [container addSubview:rb2];
    [container addSubview:rb3];
    
    //初始化第一个单选按钮的UILabel
    UILabel *label1 =[[UILabel alloc] initWithFrame:CGRectMake(40, 30, 100, 20)];
    label1.backgroundColor = [UIColor clearColor];
    label1.text = @"满意";
    [container addSubview:label1];
    
    UILabel *label2 =[[UILabel alloc] initWithFrame:CGRectMake(40, 60, 100, 20)];
    label2.backgroundColor = [UIColor clearColor];
    label2.text = @"基本满意";
    [container addSubview:label2];
    
    UILabel *label3 =[[UILabel alloc] initWithFrame:CGRectMake(40, 90, 100, 20)];
    label3.backgroundColor = [UIColor clearColor];
    label3.text = @"不满意";
    [container addSubview:label3];
    
    [self.svContent addSubview:container];
    //按照GroupId添加观察者
    [GGRadioButton addObserverForGroupId:@"first group" observer:self];
}

//代理方法
-(void)radioButtonSelectedAtIndex:(NSUInteger)index inGroup:(NSString *)groupId{
    NSLog(@"changed to %d in %@",index,groupId);
    int aEvaluate = index+1;
    NSNumber * area = [GGGlobalValue sharedInstance].provinceId;
    [[GGAPIService sharedInstance] addPoliceEvaluateWithPolice:_policeman.ID evaluate:aEvaluate unitId:[area intValue] aCompletion:^(long flag) {
        if (flag == 1) {
            [self showError:@"您的评价已经提交到服务器,谢谢您的参与"];
        }
        else
        {
            [self showError:@"您今天已对该警员评价过, 请勿重复评价"];
        }
        
    }];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload {
    [self setPoliceman:nil];
    [self setSvContent:nil];
    [self setIvPhoto:nil];
    [self setBtnAddFavor:nil];
    [self setLblName:nil];
    [self setLblNumber:nil];
    [self setLblStation:nil];
    [self setBtnPhone:nil];
    [self setBtnStationPhone:nil];
    [self setBtnSuperviserPhone:nil];
    [super viewDidUnload];
}
@end
