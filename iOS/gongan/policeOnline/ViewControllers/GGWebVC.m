//
//  GGWebVC.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-21.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGWebVC.h"
#import "GGAPIService.h"
#import "GGWanted.h"
#import "GGClueReportVC.h"
#import "GGClue.h"

#define RELOADGGWANTED     @"RELOADGGWANTED"

@interface GGWebVC ()
@property (weak, nonatomic) IBOutlet UIWebView *viewWeb;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnSurfBack;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnRefresh;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnSurfForward;

@end

@implementation GGWebVC


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        [[NSURLCache sharedURLCache] removeAllCachedResponses];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title = _naviTitleString;
    [self.btnSurfBack setAction:@selector(surfBackAction:)];
    [self.btnRefresh setAction:@selector(refreshAction:)];
    [self.btnSurfForward setAction:@selector(surfForwardAction:)];
    
    self.navigationItem.leftBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"返回" style:UIBarButtonItemStylePlain target:self action:@selector(backButtonClicked:)];
    
    // 通缉令的时候做一个特殊处理
    if(_wanted != nil){
        
        if (_wantedKeep) {
            self.navigationItem.rightBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"收藏" style:UIBarButtonItemStylePlain target:self action:@selector(myFavoriteClick:)];
        }
        else
        {
            self.navigationItem.rightBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"取消收藏" style:UIBarButtonItemStylePlain target:self action:@selector(myFavoriteClick:)];
        }
    }
    
    //线索征集的时候做一个特殊处理
    if (_clue !=nil) {
        self.navigationItem.rightBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"提供线索" style:UIBarButtonItemStylePlain target:self action:@selector(addAnClue)];
    }
    [_viewWeb loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:_urlStr]]];
    DLog(@"webview loading: {%@}", _urlStr);
}


/**
 *  功能：添加收藏
 */
-(IBAction)myFavoriteClick:(id)sender
{
    DLog(@"myFavoriteClick");
    
    if (_wantedKeep) {
        [[GGAPIService sharedInstance] insertWanted:_wanted aCompletion:^(BOOL success){
            if (success == YES) {
                [self showError:@"收藏成功"];
                [[NSNotificationCenter defaultCenter] postNotificationName:RELOADGGWANTED object:nil];
            }
            else
            {
                [self showError:@"您已经收藏，无需重复收藏"];
            }
        }];
    }
    else
    {
        [[GGAPIService sharedInstance] deleteWantedByID:_wanted.ID aCompletion:^(BOOL success){
            if (success == YES) {
                [self showError:@"取消成功"];
                [[NSNotificationCenter defaultCenter] postNotificationName:RELOADGGWANTED object:nil];
            }
        }];
    }
    
}

/**
 *  功能：提供线索
 */
-(void) addAnClue
{
    NSLog(@"addAnClue+ %@",self.clue);
    GGClueReportVC *vc = [GGClueReportVC new];
    vc.contentID = _clue.ID;
    [self.navigationController pushViewController:vc animated:YES];
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


-(void)dealloc
{
    _viewWeb.delegate = nil;
}



-(void)backButtonClicked:(id)sender
{
    if (_viewWeb.canGoBack)
    {
        [_viewWeb goBack];
    }
    else
    {
        [self.navigationController popViewControllerAnimated:YES];
    }
//    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - web view delegate
- (void)webViewDidStartLoad:(UIWebView *)webView
{
    [self showLoadingHUD];
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    [self hideLoadingHUD];
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error
{
    [self alertNetError];
    [self hideLoadingHUD];
}

- (void)viewDidUnload {
    [self setViewWeb:nil];
    [self setBtnSurfBack:nil];
    [self setBtnRefresh:nil];
    [self setBtnSurfForward:nil];
    [super viewDidUnload];
}

#pragma mark - actions
-(IBAction)surfBackAction:(id)sender
{
    if ([_viewWeb canGoBack])
    {
        [_viewWeb goBack];
    }
}

-(IBAction)refreshAction:(id)sender
{
    [_viewWeb reload];
}

-(IBAction)surfForwardAction:(id)sender
{
    if ([_viewWeb canGoForward])
    {
        [_viewWeb goForward];
    }
}

@end
