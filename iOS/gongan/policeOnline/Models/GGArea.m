//
//  GGTag.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGArea.h"

@implementation GGArea

-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.ID = [[aData objectForKey:@"areaId"] longLongValue];
    self.address = [aData objectForKey:@"address"];
}

@end
