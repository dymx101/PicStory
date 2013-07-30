//
//  GGClueCell.m
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGClueCell.h"

@implementation GGClueCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {

        UIImageView *bg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 70)];
        bg.image = [UIImage imageNamed:@"cell_bg"];
        self.backgroundView = bg;
        
        UIImageView *selBg = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 70)];
        selBg.image = [UIImage imageNamed:@"cell_bg_sel"];
        self.selectedBackgroundView = selBg;
        self.NameLbl = [[UILabel alloc] initWithFrame:CGRectMake(10, 10, 280, 38)];
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

- (void)updateWithGGClue:(GGClue *)clue
{
    self.NameLbl.text =  clue.title;
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
