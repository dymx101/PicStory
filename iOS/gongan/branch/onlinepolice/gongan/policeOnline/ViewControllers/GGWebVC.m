//
//  GGWebVC.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-21.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGWebVC.h"

@interface GGWebVC ()
@property (weak, nonatomic) IBOutlet UIWebView *viewWeb;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnSurfBack;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnRefresh;
@property (weak, nonatomic) IBOutlet UIBarButtonItem *btnSurfForward;

@end

@implementation GGWebVC
//{
//    UIWebView   *_webview;
//}

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
    self.navigationItem.title = _naviTitleString;
    [self.btnSurfBack setAction:@selector(surfBackAction:)];
    [self.btnRefresh setAction:@selector(refreshAction:)];
    [self.btnSurfForward setAction:@selector(surfForwardAction:)];
    
    self.navigationItem.leftBarButtonItem =  [[UIBarButtonItem alloc] initWithTitle:@"返回" style:UIBarButtonItemStylePlain target:self action:@selector(backButtonClicked:)];
    
    [_viewWeb loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:_urlStr]]];
    DLog(@"webview loading: {%@}", _urlStr);
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
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
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
