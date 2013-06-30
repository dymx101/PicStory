//
//  GGClueCell.h
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "GGClue.h"

@interface GGClueCell : UITableViewCell

@property(nonatomic, strong)  UILabel *NameLbl;

/**
 *  功能:刷新显示
 */
- (void)updateWithGGClue:(GGClue *) clue;

@end
