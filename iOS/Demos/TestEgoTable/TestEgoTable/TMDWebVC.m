//
//  TMDWebVC.m
//  TestEgoTable
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "TMDWebVC.h"

@interface TMDWebVC ()

@property (retain, nonatomic) IBOutlet UIWebView *webView;

@end

@implementation TMDWebVC
{
    UIScrollView* currentScrollView;
}
@synthesize webView;

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
    
    webView.delegate = self;
    
    [webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"http://www.baidu.com"]]];
    
    for (UIView *subView in webView.subviews) {
        if ([subView isKindOfClass:[UIScrollView class]]) {
            currentScrollView = (UIScrollView *)subView;
            currentScrollView.delegate = (id)self;
        }
    }
    
    PullToRefreshView *pull = [[PullToRefreshView alloc] initWithScrollView:currentScrollView];
    pull.delegate = self;
    pull.tag = 998;
    [currentScrollView addSubview:pull];
}

-(void)pullToRefreshViewShouldRefresh:(PullToRefreshView *)view
{
    [webView reload];
}

-(void)webViewDidFinishLoad:(UIWebView *)webView
{
    [(PullToRefreshView *)[self.view viewWithTag:998] finishedLoading];
}

- (void)viewDidUnload
{
    [self setWebView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)dealloc {
    [webView release];
    [super dealloc];
}
@end
