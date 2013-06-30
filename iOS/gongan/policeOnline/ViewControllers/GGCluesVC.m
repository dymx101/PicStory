//
//  GGCluesVC.m
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGCluesVC.h"
#import "GGClue.h"
#import "GGAPIService.h"
#import "GGClueCell.h"
#import "GGWebVC.h"


@interface GGCluesVC ()
@property (strong, nonatomic)   UITableView *table;
@property (strong,nonatomic)    NSMutableArray *mutableKeys;
@end

@implementation GGCluesVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        [self setMyTitle:@"线索征集"];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    _mutableKeys = [NSMutableArray array];
    [self inittable];
    [self requestAsync];
}

-(void)inittable
{
    _table = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height-44)];
    [_table setBackgroundColor:[UIColor clearColor]];
    _table.delegate = self;
    _table.dataSource = self;
    [self.view addSubview:_table];
}

-(void)requestAsync
{
    [self showLoadingHUD];
    [[GGAPIService sharedInstance] getCluesRoot:^(NSMutableArray *arr) {
        if (arr != nil) {
            [_mutableKeys removeAllObjects];
            [_mutableKeys addObjectsFromArray:arr];
            [_table reloadData];
        }
        else
        {
            [self alertNetError];
        }
        [self hideLoadingHUD];
    }];
}

#pragma mark tableView相关delegate和dataSource

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 60;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	[tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    DLog(@"进入线索征集webview");
    GGClue * clue = [_mutableKeys objectAtIndex:indexPath.row];
    
    GGWebVC *vc = [[GGWebVC alloc] init];
    vc.urlStr = [NSString stringWithFormat:@"%@/mobile-clueInfo.rht?contentType=403&contentId=%lld", GGN_STR_TEST_SERVER_URL, clue.ID];
    vc.naviTitleString = clue.title;
    vc.clue = clue;
    [self.navigationController pushViewController:vc animated:YES];
    
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int objCount=[_mutableKeys count];//实际的数目
    return objCount ;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    id column = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableKeys];
    
    GGClue * clue = column;
    
    GGClueCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGClueCell"];
    
    if (cell == nil) {
        cell = [[GGClueCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGClueCell"];
    }
    [cell updateWithGGClue:clue];
    return cell;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
