//
//  GGTickerSymbol.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-21.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGPoliceman.h"

//@property (copy) NSString *name;
//@property (copy) NSString *number;
//@property (copy) NSString *phone;
//@property (copy) NSString *photo;
//
//@property (copy) NSString *stationName;
//@property (copy) NSString *stationPhone;
//@property (copy) NSString *superviserPhone;

@implementation GGPoliceman

-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.ID = [[aData objectForKey:@"plId"] longLongValue];
    self.name = [aData objectForKey:@"plName"];
    self.gender = [aData objectForKey:@"plSex"];
    self.number = [aData objectForKey:@"plNumber"];
    self.phone = [aData objectForKey:@"plPhone"];
    self.photo = [aData objectForKey:@"plPhoto"];
    self.stationName = [aData objectForKey:@"stationName"];
    self.stationPhone = [aData objectForKey:@"stationPhone"];
    self.superviserPhone = [aData objectForKey:@"supervisePhone"];
}

@end
