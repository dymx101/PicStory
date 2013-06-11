//
//  GGWanted.m
//  policeOnline
//
//  Created by dong yiming on 13-4-28.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGWanted.h"

@implementation GGWanted
-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.ID = [[aData objectForKey:@"contentId"] longLongValue];
    self.title = [aData objectForKey:@"title"];
//    self.content = @"0";
//    self.type = @"0";
//    self.rewardTime = @"0";
//    self.wantedManName = @"0";
//    self.wantedManGender = @"0";
//    self.wantedManAddress = @"0";
//    self.addTime = @"0";
//    self.updateTime = @"0";
}
@end
