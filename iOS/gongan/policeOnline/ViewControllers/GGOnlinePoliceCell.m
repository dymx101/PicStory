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
//        [self.contentView setBackgroundColor:[UIColor whiteColor]];
        UIImage * background = [UIImage imageNamed:@"cellbg@2x.png"];
        self.backgroundView = [[UIImageView alloc] initWithImage:background];
        self.NameLbl = [[UILabel alloc] initWithFrame:CGRectMake(10, 10, 170, 38)];
        self.NameLbl.numberOfLines = 2;
        self.NameLbl.font = [UIFont boldSystemFontOfSize:16];
        self.NameLbl.backgroundColor = [UIColor clearColor];
        self.NameLbl.text= @"towne";
        [self.contentView addSubview:self.NameLbl];
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
