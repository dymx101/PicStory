//
//  GGClueReport.m
//  policeOnline
//
//  Created by Dong Yiming on 6/15/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGClueReport.h"

@interface GGClueReport ()

@end

@implementation GGClueReport

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
    
    self.navigationItem.title = @"线索征集";
    
    
}

#pragma mark - scroll view delegate
-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

-(IBAction)submit:(id)sender
{
    
}

-(IBAction)addPicture:(id)sender
{
    UIActionSheet *shit = [[UIActionSheet alloc] initWithTitle:@"添加照片" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照", @"相册",nil];
    [shit showInView:self.view];
}

#pragma mark - action sheet
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
}

@end
