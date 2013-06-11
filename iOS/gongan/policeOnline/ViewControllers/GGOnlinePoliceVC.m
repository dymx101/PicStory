//
//  GGOnlinePoliceVC.m
//  policeOnline
//
//  Created by towne on 13-4-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGOnlinePoliceVC.h"
#import "GGSearchBar.h"
#import "GGArea.h"
#import "GGUtils.h"
#import "GGAreaPoliceCell.h"
#import "GGOnlinePoliceCell.h"
#import "GGAPIService.h"
#import "GGPoliceDetailViewController.h"

@interface GGOnlinePoliceVC()
@property (strong, nonatomic)   UITableView *table;
@property (strong, nonatomic)   GGSearchBar *search;
@property (strong,nonatomic)    NSMutableArray *mutableAreaKeys;
@property (strong,nonatomic)    GGArea * area;
@end

@implementation GGOnlinePoliceVC
@synthesize table = _table;
@synthesize search = _search;
@synthesize mutableAreaKeys = _mutableAreaKeys;
@synthesize area = _area;



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
    // Do any additional setup after loading the view from its nib.
    self.navigationItem.title = _naviTitleString;
    CGRect frame = CGRectMake(0,0,320.0,44.0);
    _search = [[GGSearchBar alloc] initWithFrame:frame];
    _search.delegate = self;
    [self.view addSubview:self.search];
    [self inittable];
    [self requestAsync];
}

-(void)inittable
{
    _table = [[UITableView alloc] initWithFrame:CGRectMake(0, 44, self.view.frame.size.width, self.view.frame.size.height-88)];
    [_table setBackgroundColor:[UIColor clearColor]];
    _table.delegate = self;
    _table.dataSource = self;
    [self.view addSubview:_table];
}


-(void)requestAsync
{
    [self showLoadingHUD];
    if (self.area == nil) {
        [[GGAPIService sharedInstance] getArea:^(NSMutableArray * arr){
            if (arr != nil) {
                _mutableAreaKeys = arr;
                [_table reloadData];
            }
            else
            {
                [self alertNetError];
            }
            [self hideLoadingHUD];
        }];
    }
    /*
    else
    {
        [[GGAPIService sharedInstance] getPolicemanByAreaID:_area.ID aCompletion:^(NSMutableArray * arr)
         {
             self.mutableAreaKeys = arr;
             [_table reloadData];
             [self hideLoadingHUD];
         }];
    }
     */
    
}

#pragma mark tableView相关delegate和dataSource

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 60;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	[tableView deselectRowAtIndexPath:indexPath animated:YES];
    id column = [_mutableAreaKeys objectAtIndex:indexPath.row];
    /*
    if ([column isKindOfClass:[GGArea class]]) {
        GGArea  *area = [_mutableAreaKeys objectAtIndex:indexPath.row];
        GGOnlinePoliceVC *vc= [[GGOnlinePoliceVC alloc] init];
        [vc setArea:area];
        vc.naviTitleString = @"在线公安";
        [self.navigationController pushViewController:vc animated:YES];
    }
    else
    {
        DLog(@"进入详情");
        GGPoliceman * man = [_mutableAreaKeys objectAtIndex:indexPath.row];
        GGPoliceDetailViewController * pdcv = [[GGPoliceDetailViewController alloc] init];
        pdcv.naviTitleString = man.name;
        pdcv.policeman = man;
        pdcv.keep = YES;
        [self.navigationController pushViewController:pdcv animated:YES];
    }
     */
    if ([column isKindOfClass:[GGArea class]]) {
        GGArea  *area = [_mutableAreaKeys objectAtIndex:indexPath.row];
        [self showLoadingHUD];
        [[GGAPIService sharedInstance] getPolicemanByAreaID:area.ID aCompletion:^(NSMutableArray * arr)
         {
             //每个片区只有一个警员
             DLog(@"进入详情");
             GGPoliceman * man =  [arr objectAtIndex:0];
             [[GGAPIService sharedInstance] hasPolicemanWithID:man.ID aCompletion:^(BOOL keep)
              {
                  GGPoliceDetailViewController * pdcv = [[GGPoliceDetailViewController alloc] init];
                  pdcv.naviTitleString = man.name;
                  pdcv.policeman = man;
                  pdcv.keep = !keep;
                  [self.navigationController pushViewController:pdcv animated:YES];
                  [self hideLoadingHUD];
              }];
         }];
    }
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int objCount=[_mutableAreaKeys count];//实际的数目
    return objCount ;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    id column = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableAreaKeys];
    if ([column isKindOfClass:[GGArea class]]) {
        GGArea  *area  = column;
        GGAreaPoliceCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGAreaPoliceCell"];
        
        if (cell == nil) {
            cell = [[GGAreaPoliceCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGAreaPoliceCell"];
        }
        [cell updateWithGGArea:area];
        return  cell;
    }
    else {
        GGPoliceman  *man  = column;
        GGOnlinePoliceCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGOnlinePoliceCell"];
        
        if (cell == nil) {
            cell = [[GGOnlinePoliceCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGOnlinePoliceCell"];
        }
        [cell updateWithGGPoliceman:man];
        return cell;
    }
}



#pragma UISearchBarDelegate
-(void)searchBarSearchButtonClicked:(UISearchBar *)searchBar
{//按软键盘右下角的搜索按钮时触发
    NSString *searchTerm=[searchBar text];
    //读取被输入的关键e字
    [self handleSearchForTerm:searchTerm];
}

-(void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText
{//搜索条输入文字修改时触发
    if([searchText length]==0)
    {//如果无文字输入
        [self resetSearch];
        [_table reloadData];
        return;
    }
    [self handleSearchForTerm:searchText];
    //有文字输入就把关键字传给handleSearchForTerm处理
}
-(void)searchBarCancelButtonClicked:(UISearchBar *)searchBar
{//取消按钮被按下时触发
    [self resetSearch];
    //重置
    searchBar.text=@"";
    _search.showsCancelButton = NO;
    //输入框清空
    [_search resignFirstResponder];
    //重新载入数据，隐藏软键盘
}

-(void)handleSearchForTerm:(NSString *)searchTerm
{//处理搜索
    
    _search.showsCancelButton = YES;
    if (self.area == nil) {
        [[GGAPIService sharedInstance] searchArea:searchTerm aCompletion:^(NSMutableArray * arr){
            if (arr !=nil) {
                _mutableAreaKeys = arr;
                [_table reloadData];
            }
            else
            {
                [self alertNetError];
            }

            [_search resignFirstResponder];
        }];
    }
    else
    {
        DLog(@"搜索警察");
    }
    
}

-(void)resetSearch
{//重置搜索
    if (self.area == nil) {
        [[GGAPIService sharedInstance] getArea:^(NSMutableArray * arr){
            if (arr != nil) {
                _mutableAreaKeys = arr;
                [_table reloadData];
            }
            else
            {
                [self alertNetError];
            }

        }];
    }
    else
    {
        [[GGAPIService sharedInstance] getPolicemanByAreaID:_area.ID aCompletion:^(NSMutableArray * arr)
         {
             if (arr != nil) {
                 self.mutableAreaKeys = arr;
                 [_table reloadData];
             }
             else
             {
                 [self alertNetError];
             }

         }];
    }
    
}

- (void)viewDidUnload
{
    [self setTable:nil];
    [self setSearch:nil];
    [self setArea:nil];
    [super viewDidUnload];
    
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
