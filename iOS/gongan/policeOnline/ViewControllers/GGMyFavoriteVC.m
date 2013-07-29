//
//  GGMyFavoriteVC.m
//  policeOnline
//
//  Created by towne on 13-4-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGMyFavoriteVC.h"
#import "GGWanted.h"
#import "GGUtils.h"
#import "GGPoliceman.h"
#import "GGWantedCell.h"
#import "GGOnlinePoliceCell.h"
#import "GGMyFavoriteCell.h"
#import "GGAPIService.h"
#import "GGPoliceDetailViewController.h"
#import "GGWebVC.h"
#import "GGGlobalValue.h"

#define TONGJILING         @"通缉令"
#define SHEQUJINGCHA       @"社区警察"
#define DEL                @"删除"
#define RELOADGGPOLICEMAN  @"RELOADGGPOLICEMAN"
#define RELOADGGWANTED     @"RELOADGGWANTED"

@interface GGMyFavoriteVC ()
@property (strong, nonatomic)   UITableView *table;
@property (strong, nonatomic)   NSMutableArray *mutableKeys;
@end

@implementation GGMyFavoriteVC
@synthesize table = _table;
@synthesize mutableKeys = _mutableKeys;

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
    // 为空时默认我的收藏的两个栏目
    if (!_mutableKeys) {
        _mutableKeys = [NSMutableArray arrayWithObjects:TONGJILING,SHEQUJINGCHA,nil];
    }
    [self inittable];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(ReloadGGPoliceman) name:RELOADGGPOLICEMAN object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(ReloadGGWanted) name:RELOADGGWANTED object:nil];
}


-(void)inittable
{
    _table = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height-44)];
    [_table setBackgroundColor:[UIColor clearColor]];
    _table.delegate = self;
    _table.dataSource = self;
    [self.view addSubview:_table];
}

-(void)ReloadGGPoliceman
{
    if(![_mutableKeys containsObject:SHEQUJINGCHA])
    {
        [[GGAPIService sharedInstance] loadALLPolicemanFromDB:^(NSArray * arr)
         {
             [_mutableKeys removeAllObjects];
             _mutableKeys = [NSMutableArray arrayWithArray:arr];
             [_table reloadData];
         }];
    }
}

-(void)ReloadGGWanted
{
    if(![_mutableKeys containsObject:TONGJILING])
    {
        [[GGAPIService sharedInstance] getAllWanted:^(NSArray * arr)
         {
             [_mutableKeys removeAllObjects];
             _mutableKeys = [NSMutableArray arrayWithArray:arr];
             [_table reloadData];
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
    if ([column isKindOfClass:[NSString class]]) {
        if ([column isEqualToString:SHEQUJINGCHA])
        {
            [[GGAPIService sharedInstance] loadALLPolicemanFromDB:^(NSArray * arr)
             {
                 GGMyFavoriteVC *vc= [GGMyFavoriteVC alloc];
                 [vc setMutableKeys:[NSMutableArray arrayWithArray:arr]];
                 vc.naviTitleString = SHEQUJINGCHA;
                 [self.navigationController pushViewController:vc animated:YES];
             }];
            
        }
        else
        {
            [[GGAPIService sharedInstance] getAllWanted:^(NSArray * arr)
             {
                 NSMutableArray * muarr = [NSMutableArray arrayWithArray:arr];
                 GGMyFavoriteVC *vc= [GGMyFavoriteVC alloc];
                 [vc setMutableKeys:muarr];
                 vc.naviTitleString = TONGJILING;
                 [self.navigationController pushViewController:vc animated:YES];
             }];
        }
    }
    else if([column isKindOfClass:[GGPoliceman class]])
    {
        DLog(@"进入警员详情");
        GGPoliceman * man = column;
        GGPoliceDetailViewController * pdcv = [[GGPoliceDetailViewController alloc] init];
        pdcv.naviTitleString = man.name;
        pdcv.policeman = man;
        pdcv.keep = NO;
        [self.navigationController pushViewController:pdcv animated:YES];
    }
    else
    {
        DLog(@"进入通缉令详情");
        int unitid = [[GGGlobalValue sharedInstance].provinceId intValue];
        if ([column isKindOfClass:[GGWanted class]]) {
            GGWanted * _wanted = column;
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
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int objCount=0;//实际的数目
    if(![_mutableKeys containsObject:TONGJILING])
    {
        objCount = [_mutableKeys count];
    }
    else
    {
        objCount = 2;
    }
    return objCount ;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    id column = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableKeys];
    if ([column isKindOfClass:[NSString class]]) {
        NSString * myfavoriteName = column;
        GGMyFavoriteCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGMyFavoriteCell"];
        if (cell == nil) {
            cell = [[GGMyFavoriteCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGMyFavoriteCell"];
        }
        
        [cell updateWithGGMyFavorite:myfavoriteName];
        return  cell;
    }
    else if ([column isKindOfClass:[GGWanted class]]) {
        GGWanted  * wanted  = column;
        GGWantedCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGWantedCell"];
        if (cell == nil) {
            cell = [[GGWantedCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGWantedCell"];
        }
        
        [cell updateWithGGWanted:wanted];
        return  cell;
    }
    else {
        GGPoliceman * man = column;
        
        GGOnlinePoliceCell  * cell = [tableView dequeueReusableCellWithIdentifier:@"GGOnlinePoliceCell"];
        
        if (cell == nil) {
            cell = [[GGOnlinePoliceCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"GGOnlinePoliceCell"];
        }
        
        [cell updateWithGGPoliceman:man];
        return cell;
    }
}

/* 滑动删除代码 */
- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath{
    return UITableViewCellEditingStyleDelete;
}

- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath{
    return DEL;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        DLog(@">>> 滑动删除");
        id column = [GGUtils safeObjectAtIndex:indexPath.row inArray:_mutableKeys];
        if ([column isKindOfClass:[GGPoliceman class]]) {
            [[GGAPIService sharedInstance] deletePolicemanFromDB:column aCompletion:^(NSMutableArray * arr){
                _mutableKeys = arr;
                [_table reloadData];
            }];
        }
        
    }
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath{
    if ([[_mutableKeys objectAtIndex:0] isKindOfClass:[GGPoliceman class]]) {
        return YES;
    }
    else
        return NO;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    cell.backgroundColor = [UIColor whiteColor];
    
}


- (void)viewDidUnload
{
    [self setTable:nil];
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
