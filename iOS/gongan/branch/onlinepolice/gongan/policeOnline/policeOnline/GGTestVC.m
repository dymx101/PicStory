//
//  GGViewController.m
//  policeOnline
//
//  Created by dong yiming on 13-4-27.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGTestVC.h"
#import "GGAPITest.h"

@interface GGTestVC ()

@end

@implementation GGTestVC

- (void)viewDidLoad
{
    [super viewDidLoad];
	self.navigationItem.title = @"微公安";
}

-(IBAction)testAction:(id)sender
{
    [[GGAPITest sharedInstance] run];
}

@end
