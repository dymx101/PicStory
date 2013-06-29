//
//  GGAreaFunction.m
//  policeOnline
//
//  Created by towne on 13-6-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGAreaFunction.h"

@implementation GGAreaFunction

-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    self.areaId = [aData objectForKey:@"areaId"];
    self.functionIds = [aData objectForKey:@"functionIds"];
}
@end
