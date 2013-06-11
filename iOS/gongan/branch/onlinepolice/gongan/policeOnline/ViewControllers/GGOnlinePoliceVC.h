//
//  GGOnlinePoliceVC.h
//  policeOnline
//
//  Created by towne on 13-4-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGBaseViewController.h"

@interface GGOnlinePoliceVC : GGBaseViewController<UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate>
@property (copy) NSString *naviTitleString;

-(void)resetSearch;
//重置搜索，即恢复到没有输入关键字的状态
-(void)handleSearchForTerm:(NSString *)searchTerm;
//处理搜索，即把不包含searchTerm的值从可变数组中删除

@end
