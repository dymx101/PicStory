//
//  GGProfileVC.m
//  policeOnline
//
//  Created by Dong Yiming on 6/23/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGProfileVC.h"

@interface GGProfileVC ()
@property (weak, nonatomic) IBOutlet UITextField *tfName;
@property (weak, nonatomic) IBOutlet UITextField *tfPhone;
@property (weak, nonatomic) IBOutlet UIView *viewTip;

@end

@implementation GGProfileVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"个人资料";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    UIBarButtonItem *barBtn = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStyleBordered target:self action:@selector(save:)];
    self.navigationItem.rightBarButtonItem = barBtn;
    
    [self.view addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(endEditing)]];
}

- (void)viewDidUnload {
    [self setTfName:nil];
    [self setTfPhone:nil];
    [self setViewTip:nil];
    [super viewDidUnload];
}

-(void)endEditing
{
    [self.view endEditing:YES];
}

-(IBAction)save:(id)sender
{
    DLog(@"save profile");
}

@end
