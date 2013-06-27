//
//  GGLocateArea.m
//  policeOnline
//
//  Created by towne on 13-6-27.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGLocateArea.h"

@implementation GGLocateArea

-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    self.address = [aData objectForKey:@"address"];
    self.prepare2 = [aData objectForKey:@"prepare2"];
    self.areaId  = [aData objectForKey:@"areaId"];
    self.superId = [aData objectForKey:@"superId"];
}

@end
