//
//  GGVersionInfo.m
//  policeOnline
//
//  Created by dong yiming on 13-4-29.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGVersionInfo.h"

@implementation GGVersionInfo
-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.verCode = [aData objectForKey:@"verCode"];
    self.verName = [aData objectForKey:@"verName"];
    self.updates = [aData objectForKey:@"updates"];
}
@end
