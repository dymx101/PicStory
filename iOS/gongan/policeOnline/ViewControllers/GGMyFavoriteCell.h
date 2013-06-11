//
//  GGMyFavoriteCell.h
//  policeOnline
//
//  Created by towne on 13-5-1.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GGMyFavoriteCell : UITableViewCell
@property(nonatomic, strong)  UILabel *NameLbl;

/**
 *  功能:刷新显示
 */
- (void)updateWithGGMyFavorite:(NSString *) favoriteName;

@end
