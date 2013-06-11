//
//  GGOnlinePoliceCell.h
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GGPoliceman.h"
#import "GGStrikeThroughLabel.h"
#import "SDWebImageManager.h"

@interface GGOnlinePoliceCell : UITableViewCell
@property(nonatomic, strong)  UILabel *NameLbl;

/**
 *  功能:刷新显示
 */
- (void)updateWithGGPoliceman:(GGPoliceman *) man;

@end
