//
//  GGGuardTipVC.m
//  policeOnline
//
//  Created by towne on 13-4-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGWantedVC.h"
#import "GGWantedCategory.h"
#import "GGWantedCategoryCell.h"
#import "GGSearchBar.h"
#import "GGAPIService.h"
#import "GGUtils.h"
#import "GGWanted.h"
#import "GGWantedCell.h"
#import "GGWebVC.h"
#import "GGGlobalValue.h"

@interface GGWantedVC ()
@property (strong, nonatomic)   UITableView *table;
@property (strong, nonatomic)   GGSearchBar *search;
@property (strong,nonatomic)    NSMutableArray *mutableKeys;
@property (strong,nonatomic)    GGWantedCategory * category;
@end

@implementation GGWantedVC
@synthesize table = _table;
@synthesize search = _search;
@synthesize mutableKeys = _mutableKeys;
@synthesize category = _category;

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
//    self.navigationItem.title = _naviTitleString;
    [self setMyTitle:_naviTitleString];
    CGRect frame = CGRectMake(0,0,320.0,44.0);
    _search = [[GGSearchBar alloc] initWithFrame:frame];
    _search.delegate = self;
//    [self.view addSubview:self.search];
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
    if (self.category == nil) {
        [[GGAPIService sharedInstance] getWantedRootCategoryWithAreaID:[[GGGlobalValue sharedInstance].provinceId longValue] aCompletion:^(NSMutableArray * arr){
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
    else
    {
        [[GGAPIService sharedInstance] getWantedSubCategory:_category.ID aCompletion:^(NSMutableArray * arr)
        {
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

}

#pragma mark tableView相关delegate和dataSource

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 60;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
	[tableView deselectRowAtIndexPath:indexPath animated:YES];
    id column = [_mutableKeys objectAtIndex:indexPath.row];
    if ([column isKindOfClass:[GGWantedCategory class]]) {
        GGWantedCategory  *category = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableKeys];
        GGWantedVC *vc= [[GGWantedVC alloc] init];
        [vc setCategory:category];
        vc.naviTitleString = @"通缉令";
        [self.navigationController pushViewController:vc animated:YES];
    }
    else
    {
        DLog(@"进入通缉令详情");
        int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
        GGWanted * _wanted = [_mutableKeys objectAtIndex:indexPath.row];
        [[GGAPIService sharedInstance] hasWantedWithID:_wanted.ID aCompletion:^(BOOL _wantedkeep)
         {
             GGWebVC *vc = [[GGWebVC alloc] init];
             vc.urlStr = [NSString stringWithFormat:@"%@/mobile-clueInfo.rht?contentId=%lld&unitId=%d", GGN_STR_TEST_SERVER_URL, _wanted.ID,unitid];
             vc.naviTitleString = _wanted.title;
             vc.wanted = _wanted;
             vc.wantedKeep = !_wantedkeep;
             [self.navigationController pushViewController:vc animated:YES];
         }];
        

    }
    
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int objCount=[_mutableKeys count];//实际的数目
    return objCount ;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    id column = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableKeys];
    if ([column isKindOfClass:[GGWantedCategory class]]) {
        GGWantedCategory  *category  = column;
        GGWantedCategoryCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGWantedCategoryCell"];
        
        if (cell == nil) {
            cell = [[GGWantedCategoryCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGWantedCategoryCell"];
        }
        
        [cell updateWithGGWantedCategory:category];
        return  cell;
    }
    else {
        GGWanted * wanted = column;

        GGWantedCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGWantedCell"];
        
        if (cell == nil) {
            cell = [[GGWantedCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGWantedCell"];
        }
        
        [cell updateWithGGWanted:wanted];
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

    if (self.category == nil) {
        //搜索category
//        [[GGAPIService sharedInstance] searchArea:searchTerm aCompletion:^(NSMutableArray * arr){
//            _category = arr;
//            [_table reloadData];
//            [_search resignFirstResponder];
//        }];
    }
    else
    {
        DLog(@"搜索WANTED");
    }
    
}

-(void)resetSearch
{//重置搜索
    if (self.category == nil) {
        [[GGAPIService sharedInstance]  getWantedRootCategory:^(NSMutableArray * arr){
            _mutableKeys = arr;
            [_table reloadData];
        }];
    }
    else
    {
//        [[GGAPIService sharedInstance] getPolicemanByAreaID:_area.ID aCompletion:^(NSMutableArray * arr)
//         {
//             self.mutableAreaKeys = arr;
//             [_table reloadData];
//         }];
    }
    
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewDidUnload
{
    [self setTable:nil];
    [self setSearch:nil];
    [self setCategory:nil];
    [super viewDidUnload];
    
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

@end
