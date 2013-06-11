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
}
@end
