//
//  SecondViewController.m
//  AKTabBar Example
//
//  Created by Ali KARAGOZ on 04/05/12.
//  Copyright (c) 2012 Ali Karagoz. All rights reserved.
//

#import "MSCategoryVC.h"

@interface MSCategoryVC ()

@end

@implementation MSCategoryVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.title = @"分类";
    }
    return self;
}

- (NSString *)tabImageName
{
	return @"tab_category";
}

@end
