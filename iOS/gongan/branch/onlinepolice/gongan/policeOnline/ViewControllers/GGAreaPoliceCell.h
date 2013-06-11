//
//  GGAreaPoliceCell.h
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GGArea.h"

@interface GGAreaPoliceCell : UITableViewCell
@property(nonatomic, strong)  UILabel *NameLbl;

/**
 *  功能:刷新显示
 */
- (void)updateWithGGArea:(GGArea *)area;
@end
