//
//  GGCluesCategory.m
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGClue.h"

@implementation GGClue

//{"title":"襄阳警方悬赏万元征集线索","contentId":378}
-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.ID = [[aData objectForKey:@"contentId"] longLongValue];
    self.title = [aData objectForKey:@"title"];
}

@end
