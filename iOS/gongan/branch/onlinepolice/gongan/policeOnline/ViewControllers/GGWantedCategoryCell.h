//
//  GGWantedCategoryCell.h
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GGWantedCategory.h"

@interface GGWantedCategoryCell : UITableViewCell
@property(nonatomic, strong)  UILabel *NameLbl;

/**
 *  功能:刷新显示
 */
- (void)updateWithGGWantedCategory:(GGWantedCategory *) category;
@end
