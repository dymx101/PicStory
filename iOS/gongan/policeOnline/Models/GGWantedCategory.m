//
//  GGSocialProfile.m
//  WeiGongAn
//
//  Created by dong yiming on 13-4-2.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import "GGWantedCategory.h"

@implementation GGWantedCategory

//{"name":"悬赏通告","columnId":12}
-(void)parseWithData:(NSDictionary *)aData
{
    [super parseWithData:aData];
    
    self.ID = [[aData objectForKey:@"columnId"] longLongValue];
    self.name = [aData objectForKey:@"name"];
}

@end
