//
//  GGOnlinePoliceCell.m
//  policeOnline
//
//  Created by towne on 13-4-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGOnlinePoliceCell.h"

@implementation GGOnlinePoliceCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
//        UIImage * background = [UIImage imageNamed:@"cellbg@2x.png"];
//        self.backgroundView = [[UIImageView alloc] initWithImage:background];
        UIImageView *bg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 70)];
        bg.image = [UIImage imageNamed:@"cell_bg"];
        self.backgroundView = bg;
        
        UIImageView *selBg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 70)];
        selBg.image = [UIImage imageNamed:@"cell_bg_sel"];
        self.selectedBackgroundView = selBg;
        
        self.NameLbl = [[UILabel alloc] initWithFrame:CGRectMake(10, 10, 170, 38)];
        self.NameLbl.numberOfLines = 2;
        self.NameLbl.font = [UIFont systemFontOfSize:16];
        self.NameLbl.backgroundColor = [UIColor clearColor];
        self.NameLbl.text= @"towne";
        [self.contentView addSubview:self.NameLbl];
        
        //小箭头
        UIImage* arrowImage = [UIImage imageNamed:@"cell_arrow"];
        UIImageView* arrowView = [[UIImageView alloc] initWithFrame:
                                  CGRectMake(285,
                                             (66-arrowImage.size.height)/2,
                                             arrowImage.size.width,
                                             arrowImage.size.height)];
        [arrowView setImage:arrowImage];
        [self addSubview:arrowView];
    }
    return self;
}

- (void)updateWithGGPoliceman:(GGPoliceman *) man;
{
    self.NameLbl.text =  man.name;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}

- (void)dealloc
{
    self.NameLbl = nil;
}

@end
