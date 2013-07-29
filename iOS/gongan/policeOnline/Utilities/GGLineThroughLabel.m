//
//  GGLineThroughLabel.m
//  policeOnline
//
//  Created by towne on 13-7-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGLineThroughLabel.h"


@implementation GGLineThroughLabel

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        self.textColor = [UIColor whiteColor];
    }
    return self;
}

- (void)adjustMyLayout:(NSString *)text
{
    [self sizeToFit];
    
    [self setNumberOfLines:0];
    [self setTextAlignment:NSTextAlignmentCenter];
    CGSize size = [text sizeWithFont:self.font constrainedToSize:CGSizeMake(MAXFLOAT, self.frame.size.height)];
    [self setFrame:CGRectMake(0, 0, size.width, 44)];
}

-(void)setText:(NSString *)text
{
    [super setText:text];
    [self adjustMyLayout:text];
    
}

@end
