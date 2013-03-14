//
//  TMDViewController.m
//  TestEgoTable
//
//  Created by Yim Daniel on 13-2-26.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "TMDViewController.h"
#import "TMDWebVC.h"

#define THIS_PAGE_SIZE   10


@interface TMDViewController ()
@property (retain) UITableView       *tableView;
@end

@implementation TMDViewController
{
    PullToRefreshView   *pull;
    NSUInteger          _count;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _count = THIS_PAGE_SIZE;
	
    self.tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.delegate = self;
    _tableView.dataSource = self;
    
    [self.view addSubview:_tableView];
    
    pull = [[PullToRefreshView alloc] initWithScrollView:(UIScrollView *)self.tableView];
    pull.delegate = self;
    [self.tableView addSubview:pull];
}

-(void)viewDidAppear:(BOOL)animated
{
    TMDWebVC *vc = [[[TMDWebVC alloc] init] autorelease];
    [self presentModalViewController:vc animated:YES];
}

-(void)dealloc
{
    [_tableView release];
    
    [super dealloc];
}

-(void)reloadTableData
{
    // call to reload your data
    
    dispatch_async(dispatch_get_global_queue(0, 0), ^{
    
        _count += THIS_PAGE_SIZE;
        [NSThread sleepForTimeInterval:1];
        
        dispatch_async(dispatch_get_main_queue(), ^{
        
            [self.tableView reloadData];
            [pull finishedLoading];
            
        });
    
    });
}


#pragma mark - pull delegate
-(void)pullToRefreshViewShouldRefresh:(PullToRefreshView *)view
{
    NSLog(@"pulled");
    [self reloadTableData];
}

#pragma mark - table data source
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellId = @"cellId";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellId];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellId] autorelease];
    }
    
    cell.textLabel.text = [NSString stringWithFormat:@"%i", indexPath.row];
    
    return cell;
}

@end
