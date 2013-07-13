//
//  FourthViewController.m
//  AKTabBar Example
//
//  Created by Ali KARAGOZ on 04/05/12.
//  Copyright (c) 2012 Ali Karagoz. All rights reserved.
//

#import "MSAccountVC.h"

@interface MSAccountVC ()

@end

@implementation MSAccountVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"账户";
    }
    return self;
}

-(void)viewDidLoad
{
    [super viewDidLoad];
}

- (NSString *)tabImageName
{
	return @"tab_account";
}

@end
